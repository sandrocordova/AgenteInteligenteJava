/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package multimedias;

import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;
import java.util.Locale;
import javax.speech.synthesis.SynthesizerModeDesc;

/**
 *
 * @author Usuario
 */
public class CargarVoz {

    VoiceManager freeVM;
    Voice voice;

    public CargarVoz() {

        System.setProperty("freetts.voices", "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");
//        System.setProperty("mbrola.base", "C:\\mbrola\\mbrola");
        System.setProperty("freetts.voices", "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");
// SynthesizerModeDesc desc = new SynthesizerModeDesc(null, "general", Locale.CHINA, null, null);

        voice = freeVM.getInstance().getVoice("kevin");
//        voice = freeVM.getInstance().getVoice("mbrola_us1");
        String words = "Hola me llamo maria";
        if (voice != null) {
            voice.allocate();// Allocating Voice
            try {
                voice.setRate(180);// Setting the rate of the voice
                voice.setPitch(120);// Setting the Pitch of the voice
                voice.setVolume(3);// Setting the volume of the voice
                voice.setStyle("business");
//                SpeakText(words);// Calling speak() method

            } catch (Exception e1) {
                e1.printStackTrace();
            }

        } else {
            throw new IllegalStateException("Cannot find voice: kevin16");
        }
    }

    public void hablar(String words) {
        voice.speak(words);
    }

}

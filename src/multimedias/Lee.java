/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package multimedias;

import javax.speech.*;
import javax.speech.synthesis.*;
import java.util.*;

public class Lee {
    //Se inicializa el sintetizador de voz
    Synthesizer synth;
    public Lee() {
        //Se cargan las configuraciones de la voz
        SynthesizerModeDesc required = new SynthesizerModeDesc();
        required.setLocale(Locale.ROOT);
        Voice voice = new Voice(null, Voice.GENDER_FEMALE, Voice.GENDER_FEMALE, null);
        required.addVoice(voice);
    }
    
    //Metodo para leer el texto ingresado
    public void leer(String texto) {
        try {
            synth = Central.createSynthesizer(null);
            synth.allocate();
            synth.resume();
            synth.speakPlainText(texto, null);
            synth.waitEngineState(Synthesizer.QUEUE_EMPTY);
            synth.deallocate();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agenteinteligente;

/**
 *
 * @author Usuario
 */
import javax.speech.*;
import javax.speech.recognition.*;
import java.io.FileReader;
import java.util.Locale;
import ventanas.vista_opciones;

public class Escucha extends ResultAdapter {

    static Recognizer recognizer;
    String gst;
    public String opcion;
    vista_opciones vista;

    public Escucha() {

    }

    public void escuchar() {

        try {
            //Carga el reconocedor de la voz
            recognizer = Central.createRecognizer(new EngineModeDesc(Locale.ROOT));
            recognizer.allocate();
            //Se ubica el archivo con la gramática a reconocer
            System.out.println("c:\\Gramatica.txt");
            FileReader grammar1 = new FileReader("C:\\Gramatica2.txt");
            //Se carga el archivo con la gramática
            RuleGrammar rg = recognizer.loadJSGF(grammar1);
            rg.setEnabled(true);
            recognizer.addResultListener(new Escucha());
//            System.out.println(opcion);
            //Inicia el dictado
            System.out.println("Inicie Dictado");
            recognizer.commitChanges();
            recognizer.requestFocus();
            recognizer.resume();
        } catch (Exception e) {
            System.out.println("Exception en " + e.toString());
            e.printStackTrace();
            System.exit(0);
        }

//        try {
//            //Ponemos a "Dormir" el programa durante los ms que queremos
//            Thread.sleep(20 * 1000);
//        } catch (Exception e) {
//            System.out.println(e);
//        }
//        System.out.println("resultado despues de Dormir: " + opcion);

    }

    public String Llenar(String str) {
        while (str != "Funciona") {
//            opcion = x;
//            System.out.println("lleno: " + x);
        }
        return str;
    }

    //Método abstracto que se activa cuando se recibe una coincidencia de texto
    @Override
    public void resultAccepted(ResultEvent re) {
        try {
            //Se obtienen las coincidencias
            Result res = (Result) (re.getSource());
            //Se almacenan las coincidencias en tokens[]
            ResultToken tokens[] = res.getBestTokens();
//            System.out.println(re.getSource());
            String args[] = new String[1];
            //Generamos un String con las coincidencias
            args[0] = "";
            for (int i = 0; i < tokens.length; i++) {
                gst = tokens[i].getSpokenText();
                args[0] += gst + " ";
                System.out.print(gst + " ");

            }
//            System.out.println(recognizer.getAudioManager());

            System.out.println();
            //Condicionales para efecturar acciones acorde a los datos ingresados por voz
            if (gst.equals("Salir")) {
                System.out.println("contenido de opcon: "+opcion);
                Llenar(opcion);
//                opcion = "
//                recognizer.deallocate();
//                args[0] = "Lectura terminada";
//                System.out.println(args[0]);
//                System.exit(0);
            } else if (gst.equals("uno")) {
                recognizer.suspend();
                opcion = "Funciona 1";
                System.out.println("Ha seleccionado la opción 1");
                recognizer.resume();
            } else if (gst.equals("dos")) {

                recognizer.suspend();
                opcion = "Funciona 2";

                System.out.println("Ha seleccionado la opción 2");
                recognizer.resume();
            } else if (gst.equals("1")) {
                recognizer.suspend();
                opcion = "Funciona 1";
                System.out.println("Ha seleccionado la opción 1");
                recognizer.resume();
            } else if (gst.equals("2")) {
                recognizer.suspend();
                opcion = "Funciona";
                System.out.println("Ha seleccionado la opción 2");
                recognizer.resume();
            } else if (gst.equals("3")) {
                recognizer.suspend();
                System.out.println("Ha seleccionado la opción 3");
                recognizer.resume();
            } else if (gst.equals("tres")) {
                recognizer.suspend();
                System.out.println("Ha seleccionado la opción 3");
                recognizer.resume();
            } else {
                recognizer.suspend();

                recognizer.resume();
            }
        } catch (Exception ex) {
            System.out.println("Ha ocurrido algo inesperado " + ex);
        }
    }
}

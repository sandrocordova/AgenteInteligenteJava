/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agenteinteligente;

import baseDeDatos.Conexion;
import multimedias.Lee;
import clases.Cita;
import clases.Extension;
import clases.MedidaBioseguridad;
import clases.Sintoma;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import java.text.SimpleDateFormat;
import java.util.Date;
import com.mongodb.Mongo;
//import com.sun.speech.freetts.Voice;
//import com.sun.speech.freetts.VoiceManager;
//import com.sun.speech.freetts.Tokenizer;
//import com.sun.speech.freetts.UtteranceProcessor;
import java.util.ArrayList;

import java.io.IOException;
import ventanas.vista_sintomas;

/**
 *
 * @author USUARIO
 */
public class borradores {

    public static void main(String[] args) {
        System.out.println("inicia");
        Lee lee = new Lee();
        lee.leer("Hola mundo");

//        escucharOpcion2();
//        Voice voice;

//--------------INSERTAR SINTOMAS A LA BASE DE DATOS
//        Conexion coneccion = new Conexion();
//        Conexion coneccion1 = new Conexion();
//        Conexion coneccion2 = new Conexion();
//        Conexion coneccion3 = new Conexion();
//        Conexion coneccion4 = new Conexion();
//        Conexion coneccion5 = new Conexion();
//        Conexion coneccion6 = new Conexion();
//        Conexion coneccion7 = new Conexion();
//        Conexion coneccion8 = new Conexion();
//        Conexion coneccion9 = new Conexion();
//        Conexion coneccion10 = new Conexion();
//        Conexion coneccion11 = new Conexion();
//        Conexion coneccion12 = new Conexion();
//        Conexion coneccion13 = new Conexion();
//        coneccion.insertarSintoma("Fiebre leve",1);
//        coneccion1.insertarSintoma("Tos ",1);
//        coneccion2.insertarSintoma("Dolor de cabeza leve",1);
//        coneccion3.insertarSintoma("Síntomas de gripe",1);
//        coneccion4.insertarSintoma("Rinorrea",1);
//        coneccion5.insertarSintoma("Diarrea",1);
//        coneccion6.insertarSintoma("Dolor de espalda  ",1);
//        coneccion7.insertarSintoma("Dolor muscular",1);
//        coneccion.insertarSintoma("Nauseas",3);
//        coneccion1.insertarSintoma("Vomito",3);
//        coneccion2.insertarSintoma("Dolores musculares",3);
//        coneccion3.insertarSintoma("Disnea",3);
//        coneccion4.insertarSintoma("Cianosis",3);
//        coneccion5.insertarSintoma("Hipotension",3);
//        coneccion6.insertarSintoma("Confusion",3);
//        coneccion7.insertarSintoma("Dolor al respirar",3);
//        coneccion8.insertarSintoma("Fiebre alta",3);
//        coneccion9.insertarSintoma("Baja saturacion",3);
//        coneccion10.insertarSintoma("Tos productiva",3);
//        coneccion11.insertarSintoma("Perdida del gusto",3);
//        coneccion12.insertarSintoma("Perdida del olfato",3);
//        coneccion.insertarSintoma("Dificultad para respirar",2);
//        coneccion1.insertarSintoma("Perdida del olfato",2);
//        coneccion2.insertarSintoma("Perdida del gusto",2);
//        coneccion3.insertarSintoma("Dolor de garganta ",2);
//        coneccion4.insertarSintoma("Tos persistente",2);
//        coneccion5.insertarSintoma("Fiebre alta",2);
//        coneccion6.insertarSintoma("Dolor de cuerpo",2);
//        coneccion7.insertarSintoma("Mialgias",2);
//        coneccion8.insertarSintoma("Anosmia",2);
//--------------- FIN DE INSERTAR SINTOMAS A LA BASE DE DATOOS
//LEEr
//        String medidas = "Medidas de Bioseguridad: \n";
//        Conexion conexion = new Conexion();
//        ArrayList<MedidaBioseguridad> listaMedidas = new ArrayList<MedidaBioseguridad>();
//        listaMedidas = conexion.buscarMedidasBioseguridad();
//
//        for (int i = 0; i < listaMedidas.size(); i++) {
//            MedidaBioseguridad medida = new MedidaBioseguridad();
//            medida = listaMedidas.get(i);
//            medidas = medidas + "- " + medida.getNombre() + ". \n";
//        }
//
//        //Presentar las medidas de bioseguridad al usuario
//        vista_sintomas vista_sintomas = new vista_sintomas();
//        vista_sintomas.anuncio.setText("Medidas de bioseguridad");
//        vista_sintomas.cajaSintomas.setText(medidas);
//        vista_sintomas.botonEnviar.setText("ACEPTAR");
//        vista_sintomas.setVisible(true);
//
//        //Se leen las medidas de bioseguridad al usuario
//        Lee lee = new Lee();
//        lee.leer(medidas);
//FIN LEEER
//////////        Conexion coneccion = new Conexion();
//////////        Conexion coneccion1 = new Conexion();
//////////        Conexion coneccion2 = new Conexion();
//////////        Conexion coneccion3 = new Conexion();
//////////        Conexion coneccion4 = new Conexion();
//////////        Conexion coneccion5 = new Conexion();
//////////        Conexion coneccion6 = new Conexion();
//////////        Conexion coneccion7 = new Conexion();
//////////        coneccion.insertarMedidaBioseguridad("Lavarse las manos con frecuencia con agua y jabón","s/n");
//////////        coneccion1.insertarMedidaBioseguridad("Cubrirse al toser o estornudar","s/n");
//////////        coneccion2.insertarMedidaBioseguridad("No escupir","s/n");
//////////        coneccion3.insertarMedidaBioseguridad("Distanciamiento social","s/n");
//////////        coneccion4.insertarMedidaBioseguridad("Evite tocarse los ojos","s/n");
//////////        coneccion5.insertarMedidaBioseguridad("Uso de mascarilla","s/n");
//////////        coneccion6.insertarMedidaBioseguridad("Uso de guantes","s/n");
//////////        coneccion7.insertarMedidaBioseguridad("Al momento de acudir a un centro de salud hacerlo de preferencia solo o con un acompañante","s/n");
//        Lee lee = new Lee();
//        lee.leer("hello it´s me, sandro cordova");
//        System.setProperty("freetts.voices", "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");
//        voice = VoiceManager.getInstance().getVoice("kevin16");
//        String words = "Hola me llamo maria";
//        if (voice != null) {
//            voice.allocate();// Allocating Voice
//            try {
////                voice.setRate(190);// Setting the rate of the voice
////                voice.setPitch(150);// Setting the Pitch of the voice
//                voice.setVolume(3);// Setting the volume of the voice
//                voice.speak(words);
////                SpeakText(words);// Calling speak() method
//
//            } catch (Exception e1) {
//                e1.printStackTrace();
//            }
//
//        } else {
//            throw new IllegalStateException("Cannot find voice: kevin16");
//        }
//        VoiceManager manager = VoiceManager.getInstance();
////        System.setProperty("mbrola.base", "C:\\mbrola\\mbrola");
//        = manager.getInstance().getVoice("kevin");
//        voz.allocate();
//        voz.speak("Hola soy sandro ");
//        voz.deallocate();
//        Conexion conexion = new Conexion();
//        ArrayList<Cita> listaExtensiones = new ArrayList<Cita>();
//        listaExtensiones = conexion.buscarCitas();
//        
//        System.out.println(listaExtensiones);  
//        
//        Cita cita = new Cita();
//        cita = conexion.buscarCita(listaExtensiones, "1150261905");
//        System.out.println(cita.getCorreo());
        //Abre comunicacion con la base de datos
////////        Conexion coneccion = new Conexion();
////////        Conexion coneccion2 = new Conexion();
////////        coneccion.insertarExtension("0725496","23","Departamento de información", "False");
////////        coneccion2.insertarExtension("0725496","12","Atención al cliente", "True");
//        ArrayList<Sintoma> sintomasReferencia = new ArrayList<Sintoma>();
//
//        sintomasReferencia = conexion.buscarSintomas();
//        for (int i = 0; i < sintomasReferencia.size(); i++) {
//            Sintoma sintoma = new Sintoma();
//            sintoma = sintomasReferencia.get(i);
//            System.out.println(sintoma.getNombre());
//        }
//        DB db;
//        DBCollection tabla;
//        try {
//            Mongo mongo = new Mongo("localhost", 27017);
//            db = mongo.getDB("prueba2");
//            tabla = db.getCollection("sintomas");
//            BasicDBObject documento1 = new BasicDBObject();
//                coneccion.insertarSintoma("gripe",1);
//                coneccion.insertarSintoma("fiebre",3);
//                coneccion.insertarSintoma("tos",2);
//                coneccion.insertarSintoma("cabeza",3);
//                coneccion.insertarSintoma("insomnio",2);
//                coneccion.insertarSintoma("presion",3);
//            documento1.put("sintoma", "'" + "gripe" + "'");
//            documento1.put("prioridad", 1);
//            tabla.insert(documento1);
//            System.out.println(
//                    "BASE DE DATOS GARGADA");
//        } catch (Exception e) {
//            System.out.println("ERROR AL CARGAR LA BASE DE DATOS");
//        }
    }

    public static void escucharOpcion2() {
        String opcion = "null";
        Escucha esc = new Escucha();
        esc.escuchar();
        opcion = esc.Llenar("vacio");
            System.out.println(opcion);
//        while (opcion != "Funciona") {
//        }
        System.out.println("FIN");
    }

//        Insertar
//        BasicDBObject documento = new BasicDBObject();
//        documento.put("nombre","'"+nombre"'");
//        documento.put("nombre",edad);
//        tabla.insert(documentp);
// Extraer
//DBCursor cursor = tabla.find();
//cursor.hasNext();//lo que tenga en esa vuelta, se puede o no aplicar
//cursor.next();
//        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy/h:mm");
//        String fecha = sdf.format(new Date());
//        System.out.println(fecha);
}
//     System.out.println("My name local is " + getLocalName());
//        System.out.println("“My GUID is " + getAID().getName());
//        int menu1 = solicitar_entrada();
//        addBehaviour(new CyclicBehaviour(){
//            @Override
//            public void action() {
//            ACLMessage msg = receive();
//                if (msg!=null) {
//                    JOptionPane.showMessageDialog(null, msg.getContent());
//                }else{
//                    System.out.println("NO MENSAJE");
//                }
//            }
//        
//        });
//Añadimos un comportamiento al agente
//        if (menu1 == 1) {
//            addBehaviour(new transferir_llamada(this));
//        }
//        int menu2 = solicitar_sintomas();
//Añadimos un comportamiento al agente
//        if (menu1 == 1) {
//            addBehaviour(new transferir_llamada(this));
//        }

//    class solicitar_entrada extends OneShotBehaviour {
//        public solicitar_entrada(Agent a, int sntrada) {
//            super(a);
//        }
//
//        public void action() {
//            int entrada = Integer.parseInt(JOptionPane.showInputDialog("DIGITE UNA OPCIÓN \n"
//                    + "1. Transferir llamada \n"
//                    + "2. Centro de información COVID-19"));
//            System.out.println("Llamada transferida");
//
//        }
//    }
//    class transferir_llamada extends SimpleBehaviour {
//        public transferir_llamada(Agent a) {
//            super(a);
//        }
//
//        public void action() {
//            System.out.println("Llamada transferida");
//        }
//        private boolean finished = false;
//
//        public boolean done() {
//            return finished;
//        }
//
//    }

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
import jade.domain.FIPANames;
import java.awt.List;
import java.io.BufferedReader;
import java.io.File;

import java.io.InputStream;
import java.util.Scanner;
import java.io.FileInputStream;
import java.io.FileReader;
//import com.sun.speech.freetts.Voice;
//import com.sun.speech.freetts.VoiceManager;
//import com.sun.speech.freetts.Tokenizer;
//import com.sun.speech.freetts.UtteranceProcessor;
import java.util.ArrayList;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import multimedias.Escuchar;
import ventanas.vista_sintomas;

/**
 *
 * @author USUARIO
 */
public class datosIniciales {

    public static void main(String[] args) {

////        //------------------###########################
//////////        Escuchar escuchar = new Escuchar();
//////////        Lee lee = new Lee();
//////////        Boolean bool = true;
//////////        while (true) {
//////////            if (bool) {
//////////                lee.leer("Por favor utilice su voz para ingresar su correo electrónico");
//////////                bool = false;
//////////            }
//////////            escuchar.escucharPython();
//////////            String seleccionUsuario = escuchar.leerTxt("correo", 4);
//////////            if (seleccionUsuario.length() >= 10) {
//////////                //Solicitamos el ingreso de la edad
//////////                lee.leer("Datos encontrados .... " + seleccionUsuario);
//////////                break;
//////////            }
//////////            lee.leer("Información no encontrada");
//////////        }
//------------------###########################
//        // Inicia módulo de voz
//        //Se abre el lector para informar al usuario
//        Lee lee = new Lee();
//
//        // 2 SEGUNDOS PARA ESCUCHAR lo que dice el usuario
//        Escuchar escuchar = new Escuchar();
//        String seleccionUsuario = "";
//
//        String datoRequerido = "nombre";
//
//        while (seleccionUsuario.length()<=3) {
//            lee.leer("Por favor... utilice su voz para ingresar los síntomas que posee "
//                    + ".... Por ejemplo: Gripe ... tos ... fiebre alta ó presión alta");
//            seleccionUsuario = escuchar.leerTxt();
//            if (seleccionUsuario.length() >= 3) {
//                //Guardamos los valores ingresados por el usuario
//                //Presentamos valores encontrados
//                lee.leer("Datos encontrados .... " + seleccionUsuario);
//                break;
//            }
//            lee.leer("Información no encontrada ..... ");
//        }
//        // Fin modilo de voz
        //------------método para poder buscar coincidencias dentro de una oración
//        String texto = "Bienvenido al Sistema de llamadas teléfonicas";
//        System.out.println(texto.toLowerCase().contains(("Sistema DE llamadas").toLowerCase()));
        //-------------termina
//        String texto = "Bienvenido al Sistema de llamadas teléfonicas";
//        
//        texto = texto + " ... Opción 1, Transferir llamada a la recepción ... Opción 2, Centro de información de covid 19";
//        texto = texto + " ... Por favor, Utilice su voz para seleccionar una de las opciones";
//        
//        Lee lee = new Lee();
//        lee.leer("Hola....... soy ....... sandro");
//.----------------------------
//        try {
//                //Ponemos a "Dormir" el programa durante los ms que queremos
//                Thread.sleep(10 * 1000);
//            } catch (Exception e) {
//                System.out.println(e);
//            }
//        ----ejecutar python funciona 100% felicicdades
//------------------------------------------------------------------------------------------------------------------
//////////        try {
////////////            Runtime.getRuntime().exec("cmd /c start escuchar.py");
////////////            Runtime.getRuntime().exec("cmd archivo.py");
////////////            Runtime.getRuntime().exec("C:\\Users\\Usuario\\AppData\\Local\\Programs\\Python\\Python36\\python.exe C:\\Users\\Usuario\\Desktop\\archivo.py");
//////////
////////////            Process ps = Runtime.getRuntime().exec("cmd /c start cmd.exe /k \" cd C:\\Users\\Usuario\\Desktop py escuchar.py");
//////////            //------------------###########################
//////////            Process ps = Runtime.getRuntime().exec("cmd /c start cmd.exe /k \" cd C:\\Users\\Usuario\\Desktop && py escuchar.py");
//////////            //Esperamos a que el proceso se ejecute
//////////            ps.waitFor();
//////////            try {
//////////                Thread.sleep(2 * 5000);
//////////            } catch (Exception e) {
//////////                System.out.println(e);
//////////            }
//////////
//////////            Lee lee = new Lee();
//////////            System.out.println("estoy leyendo");
//////////            //Leemos lo que devuelve la consola
//////////            File doc = new File("C:\\Users\\Usuario\\Desktop\\respuesta.txt");
//////////
//////////            BufferedReader obj = new BufferedReader(new FileReader(doc));
//////////
//////////            lee.leer("Texto reconocido ... "+obj.readLine());
//////////            System.out.println(obj.readLine());
////////////------------------###########################
////////////            Scanner scan = new Scanner(Runtime.getRuntime().exec("cmd /c start C:\\Users\\Usuario\\Desktop\\escuchar.py").getInputStream());
////////////            String res = scan.hasNext() ? scan.next() : "";
////////////            System.out.println(res);
////////////            Runtime.getRuntime().exec("C:\\Users\\Usuario\\Desktop\\archivo.py");
//////////        } catch (Exception e) {
//////////            System.out.println("EERRRRRRORRRR");
//////////        }
//////////        
//------------------------------------------------------------------------------------------------------------------
//        try {
//            ProcessBuilder pb = new ProcessBuilder();
////            pb.command("ping", "google.com");
//            pb.command("python", "escuchar.py");
//            pb.directory(new File("C:\\Users\\Usuario\\Desktop\\"));
//            //Obtener más información del proceso
//            Process ps = pb.start();
//            
//            //Leemos lo que devuelve la consola
//            BufferedReader reader = new BufferedReader(new InputStreamReader(ps.getInputStream()));
//            String line;
//            while ((line = reader.readLine())!=null) {
//                System.out.println(line);
//            }
//            
//        } catch (Exception e) {
//            System.out.println("Error en ejecución");
//        }
//----------------------LEER TXT
//        System.out.println("inicia lectura...");
//        try {
//            InputStream ins = new FileInputStream("C:\\Users\\Usuario\\Desktop\\respuesta.txt");
//            Scanner obj = new Scanner(ins);
//            if (!obj.hasNext()) {
//                System.out.println("está vacíon");
//            }else{
//                System.out.println(obj.nextLine());
//            }
//        } catch (Exception e) {
//            System.out.println("Se detectó un error en el archivo");
//        }
//------------------leeer por voz
//        Lee lee = new Lee();
//        lee.leer("Hola soy sandro");
//        escucharOpcion2();
//        Voice voice;
//--------------INSERTAR SINTOMAS A LA BASE DE DATOS
        Escuchar escucha = new Escuchar();

//////------------------PROBAR SIMBOLOS Y NUMEROS
////        Escuchar escucha = new Escuchar();
////        String texto = escucha.buscarNumeros("uno punto uno coma uno punto tres coma tres tres cuatro arroba hotmail punto com");
////        System.out.println(texto);
////        texto = escucha.buscarSimbolos(texto);
////        System.out.println(texto);
////        System.out.println(escucha.correo(texto));
////        // ________________________________________________
//        coneccion.insertarPaciente("numero", "name", "cell", "correo", 22);
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
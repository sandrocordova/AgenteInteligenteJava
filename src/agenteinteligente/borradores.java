/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agenteinteligente;

import baseDeDatos.Conexion;
import clases.Cita;
import clases.Extension;
import clases.Sintoma;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import java.text.SimpleDateFormat;
import java.util.Date;
import com.mongodb.Mongo;
import java.util.ArrayList;

/**
 *
 * @author USUARIO
 */
public class borradores {

    public static void main(String[] args) {
        
        Conexion conexion = new Conexion();
        ArrayList<Cita> listaExtensiones = new ArrayList<Cita>();
        listaExtensiones = conexion.buscarCitas();
        
        System.out.println(listaExtensiones);  
        
        Cita cita = new Cita();
        cita = conexion.buscarCita(listaExtensiones, "1150261905");
        System.out.println(cita.getCorreo());
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

        DB db;
        DBCollection tabla;
        try {
            Mongo mongo = new Mongo("localhost", 27017);
            db = mongo.getDB("prueba2");
            tabla = db.getCollection("sintomas");
            
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
        System.out.println(
                "BASE DE DATOS GARGADA");
        } catch (Exception e) {
            System.out.println("ERROR AL CARGAR LA BASE DE DATOS");
        }

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

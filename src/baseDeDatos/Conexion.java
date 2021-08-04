/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package baseDeDatos;

import clases.Sintoma;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import java.util.ArrayList;

/**
 *
 * @author Usuario
 */
public class Conexion {

    DB BaseDatos;
    DBCollection coleccionLlamada;
    DBCollection coleccionPaciente;
    DBCollection coleccionSintoma;
    DBCollection coleccionCita;
    DBCollection coleccionSintomaPaciente;
    BasicDBObject documento = new BasicDBObject();

    public Conexion() {
        try {
            Mongo mongo = new Mongo("localhost", 27017);
            BaseDatos = mongo.getDB("CallCenterBDD");
            coleccionLlamada = BaseDatos.getCollection("llamada");
            coleccionPaciente = BaseDatos.getCollection("paciente");
            coleccionCita = BaseDatos.getCollection("cita");
            coleccionSintoma = BaseDatos.getCollection("sintomas");
            coleccionSintomaPaciente = BaseDatos.getCollection("sintoma_paciente");
            System.out.println("Base de datos conectada....");
        } catch (Exception e) {
            System.out.println("ERROR AL CARGAR LA BASE DE DATOS");
        }
    }

    public void insertarLlamada(String telefono, int seleccion) {
        documento.put("telefono", telefono);
        documento.put("seleccion", seleccion);
        coleccionLlamada.insert(documento);
//        return true;
    }

    //Inserta los sìntomas de referencia para poder comparar con los del paciente
    public boolean insertarSintoma(String sintoma, int prioridad) {
        documento.put("sintoma", sintoma);
        documento.put("prioridad", prioridad);
        coleccionSintoma.insert(documento);
        return true;
    }

    //Inserta los sintomas que posee un paciente
    public boolean insertarSintomaPaciente(String cedula, String sintoma, int prioridad) {
        documento.put("paciente_id", cedula);
        documento.put("sintoma", sintoma);
        documento.put("prioridad", prioridad);
        coleccionSintoma.insert(documento);
        return true;
    }

    //Inserta los sintomas que posee un paciente
    public boolean insertarDiagnosticoPaciente(String cedula, String diagnostico, String detalle) {
        documento.put("paciente_id", cedula);
        documento.put("diagnostico", diagnostico);
        documento.put("detalles", detalle);
        coleccionSintoma.insert(documento);
        return true;
    }

    //Inserta la cita generada para el paciente
    public boolean insertarCita(String nombres, int  edad, String cedula, 
            String correo, String sintomas, String diagnostico, String fecha) {
        documento.put("nombres", nombres);
        documento.put("edad", edad);
        documento.put("cedula", cedula);
        documento.put("correo", correo);
        documento.put("sintomas", sintomas);
        documento.put("diagnostico", diagnostico);
        documento.put("fecha", fecha);
        coleccionCita.insert(documento);
        return true;
    }

    public boolean insertarPaciente(String telefono,
            String nombre, String cedula, String correo, int edad) {
        documento.put("telefono", telefono);
        documento.put("nombre", nombre);
        documento.put("cedula", cedula);
        documento.put("correo", correo);
        documento.put("edad", edad);
        coleccionSintomaPaciente.insert(documento);
        return true;
    }

    public void mostrarPaciente() {

        DBCursor cursor = coleccionPaciente.find();
        //para obtener todos los registros de la abse de datos
        while (cursor.hasNext()) {
            System.out.println(cursor.next());
        }

    }

    //Obtener los sìntomas de referencia de la base de datos
    public ArrayList<Sintoma> buscarSintomas() {
        DBCursor cursor = coleccionSintoma.find();
        ArrayList<Sintoma> sintomasReferencia = new ArrayList<Sintoma>();
        while (cursor.hasNext()) {
            Sintoma sintoma = new Sintoma();
            DBObject object = cursor.next();
            sintoma.setPacienteId("000");
            sintoma.setNombre(String.valueOf(object.get("sintoma")));
            sintoma.setPrioridad(Integer.parseInt(String.valueOf(object.get("prioridad"))));
            sintomasReferencia.add(sintoma);
        }
        return sintomasReferencia;
    }

    public boolean actualizarPaciente(String telefonoV,
            String nombreV, String cedulaV, String correoV, int edadV,
            String telefonoN,
            String nombreN, String cedulaN, String correoN, int edadN) {
        documento.put("telefono", telefonoV);
        documento.put("nombre", nombreV);
        documento.put("cedula", cedulaV);
        documento.put("correo", correoV);
        documento.put("edad", edadV);

        BasicDBObject documentoNuevo = new BasicDBObject();

        documentoNuevo.put("telefono", telefonoN);
        documentoNuevo.put("nombre", nombreN);
        documentoNuevo.put("cedula", cedulaN);
        documentoNuevo.put("correo", correoN);
        documentoNuevo.put("edad", edadN);

        coleccionPaciente.findAndModify(documento, documentoNuevo);
        return true;
    }

    public boolean eliminarPaciente(String telefono) {
        documento.put("telefono", telefono);
        coleccionPaciente.remove(documento);
        return true;
    }

}

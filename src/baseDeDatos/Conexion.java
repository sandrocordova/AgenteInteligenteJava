/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package baseDeDatos;

import clases.Cita;
import clases.Sintoma;
import clases.Extension;
import clases.Paciente;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import java.util.ArrayList;

/**
 *
 * @author Sandro Córdova
 */
public class Conexion {

    DB BaseDatos;
    DBCollection coleccionLlamada;
    DBCollection coleccionPaciente;
    DBCollection coleccionSintoma;
    DBCollection coleccionExtension;
    DBCollection coleccionDiagnostico;
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
            coleccionExtension = BaseDatos.getCollection("extension");
            coleccionSintoma = BaseDatos.getCollection("sintomas");
            coleccionDiagnostico = BaseDatos.getCollection("diagnostico");
            coleccionSintomaPaciente = BaseDatos.getCollection("sintoma_paciente");
            System.out.println("Base de datos conectada....");
        } catch (Exception e) {
            System.out.println("ERROR AL CARGAR LA BASE DE DATOS");
        }
    }

    //Crea un registro de la llamada que ingresa al call center
    public boolean insertarLlamada(String telefono, int seleccion) {
        documento.put("telefono", telefono);
        documento.put("seleccion", seleccion);
        coleccionLlamada.insert(documento);
        return true;
    }

    //Inserta los sìntomas de referencia para poder comparar con los del paciente
    public boolean insertarSintoma(String sintoma, int prioridad) {
        documento.put("sintoma", sintoma);
        documento.put("prioridad", prioridad);
        coleccionSintoma.insert(documento);
        return true;
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
    
    //Inserta los sintomas que posee un paciente
    public boolean insertarSintomaPaciente(String cedula, String sintoma, int prioridad) {
        documento.put("paciente_id", cedula);
        documento.put("sintoma", sintoma);
        documento.put("prioridad", prioridad);
        coleccionSintomaPaciente.insert(documento);
        return true;
    }

    //Inserta los sintomas que posee un paciente
    public boolean insertarDiagnosticoPaciente(String cedula, String diagnostico, String detalle) {
        documento.put("paciente_id", cedula);
        documento.put("diagnostico", diagnostico);
        documento.put("detalles", detalle);
        coleccionDiagnostico.insert(documento);
        return true;
    }
    
    //Inserta las extensiones disponibles dentro del centro de salud
    public boolean insertarExtension(String numero, String extension, String detalle, String estado) {
        documento.put("numero", numero);
        documento.put("extension", extension);
        documento.put("detalle", detalle);
        documento.put("estado", estado);
        coleccionExtension.insert(documento);
        return true;
    }

        //Obtener las extensiones disponibles de la base de datos
    public ArrayList<Extension> buscarExtension() {
        DBCursor cursor = coleccionExtension.find();
        ArrayList<Extension> listaExtensiones = new ArrayList<Extension>();
        while (cursor.hasNext()) {
            Extension extension = new Extension();
            DBObject object = cursor.next();
            extension.setNumero(String.valueOf(object.get("numero")));
            extension.setExtension(String.valueOf(object.get("extension")));
            extension.setDetalle(String.valueOf(object.get("detalle")));
            extension.setEstado(String.valueOf(object.get("estado")));
            listaExtensiones.add(extension);
        }
        return listaExtensiones;
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

    //Se obtiene la lista de citas que se han comunicado con el call center
    public ArrayList<Cita> buscarCitas() {
        DBCursor cursor = coleccionCita.find();
        ArrayList<Cita> listaCitas = new ArrayList<Cita>();
        while (cursor.hasNext()) {
            Cita cita = new Cita();
            DBObject object = cursor.next();
            cita.setNombres(String.valueOf(object.get("nombres")));
            cita.setEdad(Integer.parseInt(String.valueOf(object.get("edad"))));
            cita.setCedula(String.valueOf(object.get("cedula")));
            cita.setCorreo(String.valueOf(object.get("correo")));
            cita.setSintomas(String.valueOf(object.get("sintomas")));
            cita.setDiagnostico(String.valueOf(object.get("diagnostico")));
            cita.setFecha(String.valueOf(object.get("fecha")));
            listaCitas.add(cita);
        }
        return listaCitas;
    }
    
    //Se obtiene la cita que coincida con el número de cédula ingresado
    public Cita buscarCita(ArrayList<Cita> listaCitas, String cedula) {
        for (int i = 0; i < listaCitas.size(); i++) {
            Cita cita = new Cita();
            cita = listaCitas.get(i);
            System.out.println(cita.getCedula());
            if (cita.getCedula().contains(cedula)) {
                return cita;
            }
        }
        return null;
    }
    
    
    public boolean actualizarCita(Cita cita, String fechaNueva) {
        documento.put("cedula", cita.getCedula());

        BasicDBObject documentoNuevo = new BasicDBObject();
        documentoNuevo.put("nombres", cita.getNombres());
        documentoNuevo.put("edad", cita.getEdad());
        documentoNuevo.put("cedula", cita.getCedula());
        documentoNuevo.put("correo", cita.getCorreo());
        documentoNuevo.put("sintomas", cita.getSintomas());
        documentoNuevo.put("diagnostico", cita.getDiagnostico());
        documentoNuevo.put("fecha", fechaNueva);

        coleccionCita.findAndModify(documento, documentoNuevo);
        return true;
    }
    
    //Elimina la cita que coincida con el numero de cedula ingresado
    public boolean eliminarCita(String cedula) {
        documento.put("cedula", cedula);
        coleccionCita.remove(documento);
        System.out.println("Cita con número de cédula: "+cedula+" ha sido eliminada");
        return true;
    }
    
    //Registra el paciente ingresado a la base de datos
    public boolean insertarPaciente(String telefono,
            String nombre, String cedula, String correo, int edad) {
        documento.put("telefono", telefono);
        documento.put("nombre", nombre);
        documento.put("cedula", cedula);
        documento.put("correo", correo);
        documento.put("edad", edad);
        coleccionPaciente.insert(documento);
        return true;
    }

    //Se obtiene la lista de pacientes que se han comunicado con el call center
    public ArrayList<Paciente> buscarPacientes() {
        DBCursor cursor = coleccionPaciente.find();
        ArrayList<Paciente> listaPacientes = new ArrayList<Paciente>();
        while (cursor.hasNext()) {
            Paciente paciente = new Paciente();
            DBObject object = cursor.next();
            paciente.setNum_telefono(String.valueOf(object.get("telefono")));
            paciente.setNombre(String.valueOf(object.get("nombre")));
            paciente.setCedula(String.valueOf(object.get("cedula")));
            paciente.setCorreo(String.valueOf(object.get("correo")));
            paciente.setEdad(Integer.parseInt(String.valueOf(object.get("edad"))));
            listaPacientes.add(paciente);
        }
        return listaPacientes;
    }
    
    //Se obtiene el paciente que coincida con el número de cédula ingresado
    public Paciente buscarPaciente(ArrayList<Paciente> listaPacientes, String cedula) {
        for (int i = 0; i < listaPacientes.size(); i++) {
            Paciente paciente = new Paciente();
            paciente = listaPacientes.get(i);
            if (paciente.getCedula().contains(cedula)) {
                return paciente;
            }
        }
        return null;
    }
    
    //Elimina al paciente con el numero de cedula ingresado
    public boolean eliminarPaciente(String cedula) {
        documento.put("cedula", cedula);
        coleccionPaciente.remove(documento);
        System.out.println("Paciente con número de cédula: "+cedula+" ha sido eliminado");
        return true;
    }

    //Busca el paciente y actualiza sus datos
    public boolean actualizarPaciente(Paciente paciente, Paciente pacienteNuevo) {
        documento.put("cedula", paciente.getCedula());

        BasicDBObject documentoNuevo = new BasicDBObject();
        documentoNuevo.put("telefono", pacienteNuevo.getNum_telefono());
        documentoNuevo.put("nombre", pacienteNuevo.getNombre());
        documentoNuevo.put("cedula", pacienteNuevo.getCedula());
        documentoNuevo.put("correo", pacienteNuevo.getCorreo());
        documentoNuevo.put("edad", pacienteNuevo.getEdad());

        coleccionPaciente.findAndModify(documento, documentoNuevo);
        return true;
    }
}

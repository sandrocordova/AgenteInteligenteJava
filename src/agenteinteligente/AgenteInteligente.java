/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agenteinteligente;

import baseDeDatos.Conexion;
import clases.Cita;
import clases.Diagnostico;
import clases.Llamada;
import clases.Paciente;
import clases.Sintoma;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.*;
import jade.lang.acl.ACLMessage;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import ventanas.vista_opciones;
import ventanas.vista_persona;
import ventanas.vista_sintomas;

/**
 *
 * @author Sandro Córdova
 */
public class AgenteInteligente extends Agent {

    private Paciente paciente;
    private Diagnostico diagnostico = new Diagnostico();
    private int seleccion;
    private String sintomas;
    private String cadenaSintomas;

    public void setup() {
        System.out.println("----AGENTE CREADO-----");
        Object[] args = getArguments();
        System.out.println("Agente en ejecución: Agente" + args[0]);

        //Se da una tarea inicial al agente inteligente
        addBehaviour(new OneShotBehaviour(this) {
            public void action() {
                //Llama al método DatosPaciente
                datosPaciente(String.valueOf(args[0]));
            }
        });

        //Tarea que el agente inteligente desarrollará de manera repetitiva
        addBehaviour(new CyclicBehaviour(this) {
            public void action() {

                if (paciente.getNombre() == null) {
                    System.out.println("vacio");
                }
            }
        });
    }

    //Método para obtener los síntomas del paciente
    private void sintomasPaciente() {
        //Llamamos a la vista para el ingreso de los datos
        vista_sintomas vista_sintomas = new vista_sintomas();
        vista_sintomas.setVisible(true);
        ActionListener al = new ActionListener() {
            //Se activa al hacer click en el botón llamar
            @Override
            public void actionPerformed(ActionEvent e) {
                //Almacena el número de la llamada que ingresa
                sintomas = vista_sintomas.cajaSintomas.getText();
                vista_sintomas.setVisible(false);
                //Llamamos al método para clasificar los síntomas
                clasificarSintomas();
            }
        };
        vista_sintomas.botonEnviar.addActionListener(al);

    }

    //Clasifica los sintomas ingresados por el paciente, los registra en la base de datos
    //y toma decisiones acorde al resultado
    private void clasificarSintomas() {
        //Se traen los sintomas de la base de datos
        //Abre comunicacion con la base de datos
        Conexion conexion = new Conexion();
        ArrayList<Sintoma> sintomasReferencia = new ArrayList<Sintoma>();
        sintomasReferencia = conexion.buscarSintomas();

        //Contadores para poder clasificar los síntomas
        int contadorPrioridadUno = 0;
        int contadorPrioridadDos = 0;
        int contadorPrioridadTres = 0;

        //Arraylist para almacenar los síntomas que el paciente tiene
        ArrayList<Sintoma> sintomasPaciente = new ArrayList<Sintoma>();
        // Comparo los sintomas de la base de datos con los ingresados por el usuario
        for (int i = 0; i < sintomasReferencia.size(); i++) {
            Sintoma sintoma = new Sintoma();
            sintoma = sintomasReferencia.get(i);
            if (sintomas.contains(sintoma.getNombre())) {
                //registra los sintomas del paciente a la base de datos
                Conexion conexion2 = new Conexion();
                conexion2.insertarSintomaPaciente(paciente.getCedula(), sintoma.getNombre(), sintoma.getPrioridad());

                sintomasPaciente.add(sintoma);
                if (sintoma.getPrioridad() == 1) {
                    contadorPrioridadUno++;
                } else if (sintoma.getPrioridad() == 2) {
                    contadorPrioridadDos++;
                } else {
                    contadorPrioridadTres++;
                }
            }
        }

        //Generamos String de sintomas con formato CSV para presentar en consola
        String cadenaSintomas = "";
        for (int i = 0; i < sintomasPaciente.size(); i++) {
            System.out.println("Sintomas encontrados: " + sintomasPaciente.get(i).getNombre());
            cadenaSintomas = cadenaSintomas + sintomasPaciente.get(i).getNombre();
            cadenaSintomas = cadenaSintomas + ",";
        }

        //Se clasfica al paciente acorde a la prioridad, se almacena en la base de datos y se toma decisiones 
        Conexion conexionDiagnostico = new Conexion();

        if (contadorPrioridadTres >= 3) {
            diagnostico.setNumCedula(paciente.getCedula());
            diagnostico.setDiagnostico("Atención medica urgente");
            diagnostico.setDetalles("se recetó medicina");
            conexionDiagnostico.insertarDiagnosticoPaciente(diagnostico.getNumCedula(), diagnostico.getDiagnostico(),
                    diagnostico.getDetalles());

            pacientePrioridadTres();
        } else if (contadorPrioridadDos < 3) {
            diagnostico.setNumCedula("cedula" + paciente.getCedula());
            diagnostico.setDiagnostico("Atención medica puede esperar");
            diagnostico.setDetalles("No hay observaciones");
            conexionDiagnostico.insertarDiagnosticoPaciente(diagnostico.getNumCedula(), diagnostico.getDiagnostico(),
                    diagnostico.getDetalles());

            pacientePrioridadDos();
        } else if (contadorPrioridadDos >= 2) {
            diagnostico.setNumCedula(paciente.getCedula());
            diagnostico.setDiagnostico("Atención medica opcional");
            diagnostico.setDetalles("No hay observaciones");
            conexionDiagnostico.insertarDiagnosticoPaciente(diagnostico.getNumCedula(), diagnostico.getDiagnostico(),
                    diagnostico.getDetalles());

            pacientePrioridadDos();
        } else if (sintomas.length() > 0) {
            diagnostico.setNumCedula(paciente.getCedula());
            diagnostico.setDiagnostico("No requiere atención medica");
            diagnostico.setDetalles("No hay observaciones");

            conexionDiagnostico.insertarDiagnosticoPaciente(diagnostico.getNumCedula(), diagnostico.getDiagnostico(),
                    diagnostico.getDetalles());

            pacientePrioridadUno();
        }

    }

    private void datosPaciente(String numero_telefono) {
        //Conexion con la base de datos
        Conexion conexion = new Conexion();

        vista_persona vista_persona = new vista_persona();
        vista_persona.setVisible(true);
        paciente = new Paciente();
        ActionListener al = new ActionListener() {
            //Se activa al hacer click en el botón llamar
            @Override
            public void actionPerformed(ActionEvent e) {
                //Almacena el número de la llamada que ingresa
                String nombre = vista_persona.cajaNombre.getText();
                String cedula = vista_persona.cajaCedula.getText();
                String correo = vista_persona.cajaCorreo.getText();
                int edad = Integer.parseInt(vista_persona.cajaEdad.getText());
                vista_persona.setVisible(false);

                paciente.setNum_telefono(numero_telefono);
                paciente.setNombre(nombre);
                paciente.setCedula(cedula);
                paciente.setCorreo(correo);
                paciente.setEdad(edad);

                //Lo registra en la base de datos
                conexion.insertarPaciente(numero_telefono, nombre, cedula, correo, edad);

                sintomasPaciente();
            }
        };
        vista_persona.botonEnviar.addActionListener(al);

    }

    private void pacientePrioridadTres() {
        generarCita();
        recetarMedicina();
        medidasBioseguridad();
    }

    private void pacientePrioridadDos() {
        generarCita();
        medidasBioseguridad();
    }

    private void pacientePrioridadUno() {
        medidasBioseguridad();
    }

    private void generarCita() {
        System.out.println("GENERAR CITA");
        Cita cita = new Cita();
        cita.setNombres(paciente.getNombre());
        cita.setEdad(paciente.getEdad());
        cita.setCedula(paciente.getCedula());
        cita.setCorreo(paciente.getCorreo());
        cita.setSintomas(cadenaSintomas);
        cita.setDiagnostico(diagnostico.getDiagnostico());
        cita.setNumTelefono(paciente.getNum_telefono());

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy/h:mm");
        String fecha = sdf.format(new Date());
        System.out.println(fecha);

        cita.setFecha(fecha);
        
        //Se almacena en la base de datos
        Conexion conexionCita = new Conexion();
        conexionCita.insertarCita(cita.getNombres(), cita.getEdad(), cita.getCedula(),
                cita.getCorreo(), cita.getSintomas(), cita.getDiagnostico(), cita.getFecha());
        
        //PResentar cita al usuario
        vista_sintomas vista_sintomas = new vista_sintomas();
        vista_sintomas.anuncio.setText("CITA GENERADA");
        vista_sintomas.cajaSintomas.setText("Nombre: " + cita.getNombres() + " \nCorreo: " + cita.getCorreo()
                + " \nFecha: " + cita.getFecha());
        vista_sintomas.botonEnviar.setText("ACEPTAR");
        vista_sintomas.setVisible(true);
    }

    private void recetarMedicina() {
        System.out.println("RECETAR MEDICINA");
    }

    private void medidasBioseguridad() {
        System.out.println("MULTIMEDIA DE MEDIDAS BIOSEGURIDAD");
    }
}

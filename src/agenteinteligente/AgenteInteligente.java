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
import clases.MedidaBioseguridad;
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
import multimedias.Lee;
import ventanas.vista_opciones;
import ventanas.vista_persona;
import ventanas.vista_sintomas;
import java.util.concurrent.TimeUnit;

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
    private int contAgentes;

    public void setup() {
        System.out.println("----AGENTE CREADO-----");
        Object[] args = getArguments();
        System.out.println("Agente en ejecución: Agente" + args[0]);
        contAgentes = Integer.parseInt(String.valueOf(args[2]));
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
//                    System.out.println("vacio");
                }
            }
        });
    }

    //Método para obtener los síntomas del paciente
    private void sintomasPaciente() {
        //Llamamos a la vista para el ingreso de los datos
        vista_sintomas vista_sintomas = new vista_sintomas();
        vista_sintomas.setVisible(true);

        Lee lee = new Lee();
        lee.leer("Por favor... ingrese los síntomas que posee ");

        ActionListener al = new ActionListener() {
            //Se activa al hacer click en el botón llamar
            @Override
            public void actionPerformed(ActionEvent e) {
                //Almacena el número de la llamada que ingresa
                sintomas = vista_sintomas.cajaSintomas.getText();
                if (sintomas.length() > 0) {
                    vista_sintomas.setVisible(false);
                    //Sección de Código para ubicar las pestañas
                    if (contAgentes == 2) {
                        vista_sintomas.setLocation(600, 270);
                    } else if (contAgentes == 3) {
                        vista_sintomas.setLocation(600, 540);
                    } else {
                        vista_sintomas.setLocation(600, 10);
                    }
                    //Termina sección
                    //Llamamos al método para clasificar los síntomas
                    clasificarSintomas();
                } else {
                    lee.leer("Información no encontrada ..... por favor intente de nuevo");
                }
            }
        };
        vista_sintomas.botonEnviar.addActionListener(al);

    }

    //Clasifica los sintomas ingresados por el paciente, los registra en la base de datos
    //y toma decisiones acorde al resultado
    private void clasificarSintomas() {
        //Se traen los sintomas de la base de datos
        Conexion conexion = new Conexion();
        ArrayList<Sintoma> sintomasReferencia = new ArrayList<Sintoma>();
        sintomasReferencia = conexion.buscarSintomas();

        /*
        Contadores para poder clasificar los síntomas
        
        El uso de tres contadores es debido a que cada síntoma tiene su prioridad
        por ende se debe usar un contador para cada prioridad.
         */
        int contadorPrioridadUno = 0;
        int contadorPrioridadDos = 0;
        int contadorPrioridadTres = 0;

        //Arraylist para almacenar los síntomas que el paciente tiene
        ArrayList<Sintoma> sintomasPaciente = new ArrayList<Sintoma>();
        // Comparo los sintomas de la base de datos con los ingresados por el usuario
        for (int i = 0; i < sintomasReferencia.size(); i++) {
            Sintoma sintoma = new Sintoma();
            sintoma = sintomasReferencia.get(i);
            //Sintomas contiene el string con los sintomas del paciente en donde se buscan los sintomas de referencia
            //Estandarizamos los síntomas de referencia y los del paciente en minúsculas
            if (sintomas.toLowerCase().contains(sintoma.getNombre().toLowerCase())) {
                //registra los sintomas del paciente a la base de datos
                Conexion conexion2 = new Conexion();
                conexion2.insertarSintomaPaciente(paciente.getCedula(), sintoma.getNombre(),
                        sintoma.getPrioridad());
                sintomasPaciente.add(sintoma);
                //Se asigana el grado de prioridad basado en los sintomas del paciente
                if (sintoma.getPrioridad() == 3) {
                    contadorPrioridadTres++;
                    System.out.println("Sintoma P3 Encontrado: " + sintoma.getNombre());
                } else if (sintoma.getPrioridad() == 2) {
                    contadorPrioridadDos++;
                    System.out.println("Sintoma P2 Encontrado: " + sintoma.getNombre());
                } else if (sintoma.getPrioridad() == 1) {
                    contadorPrioridadUno++;
                    System.out.println("Sintoma P1 Encontrado: " + sintoma.getNombre());
                }
            }
        }

        //Generamos String de sintomas con formato CSV para presentar en consola y por voz
        String cadenaSintomas = "";
        for (int i = 0; i < sintomasPaciente.size(); i++) {
            System.out.println("Sintomas encontrados: " + sintomasPaciente.get(i).getNombre());
            cadenaSintomas = cadenaSintomas + sintomasPaciente.get(i).getNombre();
            cadenaSintomas = cadenaSintomas + ",";
        }

        //Clasificación del paciente, almacenar el diagnostico en la bdd y toma decisiones 
        Conexion conexionDiagnostico = new Conexion();
        if (contadorPrioridadTres >= 3) {
            diagnostico.setNumCedula(paciente.getCedula());
            diagnostico.setDiagnostico("Atención medica urgente");
            diagnostico.setDetalles("se recetó medicina");
            conexionDiagnostico.insertarDiagnosticoPaciente(diagnostico.getNumCedula(),
                    diagnostico.getDiagnostico(), diagnostico.getDetalles());
            System.out.println("PACIENTE PRIORIDAD TRES -- Grado máximo");
            Lee lee = new Lee();
            lee.leer("Paciente clasificado como prioridad tres .... grado máximo ...");

            pacientePrioridadTres();
        } else if (contadorPrioridadTres == 2) {
            diagnostico.setNumCedula(paciente.getCedula());
            diagnostico.setDiagnostico("Atención medica opcional");
            diagnostico.setDetalles("No hay observaciones");
            conexionDiagnostico.insertarDiagnosticoPaciente(diagnostico.getNumCedula(),
                    diagnostico.getDiagnostico(), diagnostico.getDetalles());
            System.out.println("PACIENTE PRIORIDAD DOS -- Grado medio");
            Lee lee = new Lee();
            lee.leer("Paciente clasificado como prioridad dos .... grado medio ..."
                    + "Se recomienda hacer uso de la cita médica ... ");
            pacientePrioridadDos();
        } else if (contadorPrioridadDos >= 2) {
            diagnostico.setNumCedula(paciente.getCedula());
            diagnostico.setDiagnostico("Atención medica opcional");
            diagnostico.setDetalles("No hay observaciones");
            conexionDiagnostico.insertarDiagnosticoPaciente(diagnostico.getNumCedula(),
                    diagnostico.getDiagnostico(), diagnostico.getDetalles());
            System.out.println("PACIENTE PRIORIDAD DOS -- Grado medio");
            Lee lee = new Lee();
            lee.leer("Paciente clasificado como prioridad dos .... grado medio ..."
                    + "Se recomienda hacer uso de la cita médica ... ");
            pacientePrioridadDos();
        } else {
            diagnostico.setNumCedula(paciente.getCedula());
            diagnostico.setDiagnostico("No requiere atención medica");
            diagnostico.setDetalles("No hay observaciones");
            conexionDiagnostico.insertarDiagnosticoPaciente(diagnostico.getNumCedula(),
                    diagnostico.getDiagnostico(), diagnostico.getDetalles());
            System.out.println("PACIENTE PRIORIDAD UNO -- Grado leve");
            Lee lee = new Lee();
            lee.leer("Paciente clasificado como prioridad Uno .... grado leve ... ");
            pacientePrioridadUno();
        }

    }

    private void datosPaciente(String numero_telefono) {
        //Conexion con la base de datos
        Conexion conexion = new Conexion();
        vista_persona vista_persona = new vista_persona();
        vista_persona.setVisible(true);
        //Sección de Código para ubicar las pestañas
        if (contAgentes == 2) {
            vista_persona.setLocation(600, 270);
        } else if (contAgentes == 3) {
            vista_persona.setLocation(600, 540);
        } else {
            vista_persona.setLocation(600, 10);
        }
        //Termina sección
        Lee lee = new Lee();
        lee.leer("Por favor ingrese sus datos personales..... ");

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

    private void pacientePrioridadUno() {
        medidasBioseguridad();
    }

    private void pacientePrioridadDos() {
        generarCita();
        medidasBioseguridad();
    }

    private void pacientePrioridadTres() {
        generarCita();
        recetarMedicina();
        medidasBioseguridad();
    }

    //Metodo para generar una cita al paciente
    private void generarCita() {
        //Se inicializa el objeto cita
        Cita cita = new Cita();
        cita.setNombres(paciente.getNombre());
        cita.setEdad(paciente.getEdad());
        cita.setCedula(paciente.getCedula());
        cita.setCorreo(paciente.getCorreo());
        cita.setSintomas(cadenaSintomas);
        cita.setDiagnostico(diagnostico.getDiagnostico());
        cita.setNumTelefono(paciente.getNum_telefono());
        //Se establece un formato a la fecha de la cita
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy/h:mm");
        Date someDate = new Date();
        String fecha = sdf.format(new Date(someDate.getTime() + TimeUnit.DAYS.toMillis( 1 )));
        cita.setFecha(fecha);
        //Se almacena en la base de datos
        Conexion conexionCita = new Conexion();
        conexionCita.insertarCita(cita.getNombres(), cita.getEdad(), cita.getCedula(),
                cita.getCorreo(), cita.getSintomas(), cita.getDiagnostico(), cita.getFecha());
        //Presentar cita al usuario
        String citaTexto = "Se ha generado una cita\na Nombre de: " + cita.getNombres()
                + " \n con el Correo electrónico: \n"
                + cita.getCorreo() + " \n Fecha de la cita:\n " + cita.getFecha();
        vista_sintomas vista_sintomas = new vista_sintomas();
        vista_sintomas.anuncio.setText("CITA GENERADA");
        vista_sintomas.cajaSintomas.setText(citaTexto);
        vista_sintomas.botonEnviar.setText("ACEPTAR");
        vista_sintomas.setVisible(true);

        //Agregamos al boton el evento de cerrar la ventada
        ActionListener al = new ActionListener() {
            //Se activa al hacer click en el botón llamar
            @Override
            public void actionPerformed(ActionEvent e) {
                vista_sintomas.setVisible(false);
            }
        };
        vista_sintomas.botonEnviar.addActionListener(al);

        //Sección de Código para ubicar las pestañas
        if (contAgentes == 2) {
            vista_sintomas.setLocation(600, 270);
        } else if (contAgentes == 3) {
            vista_sintomas.setLocation(600, 540);
        } else {
            vista_sintomas.setLocation(600, 10);
        }
        //Termina sección
        //Se lee la información de la cita médica al usuario
        Lee lee = new Lee();
        lee.leer(citaTexto);
        System.out.println("GENERAR CITA MÉDICA");
    }

    //Metodo para recetar medicina al paciente
    private void recetarMedicina() {
        //Presentar el medicamento al usuario
        String medicamentos = "En caso de emergencia ...\n"
                + "se recomienda la administración de: " + "\nPARACETAMOL";
        vista_sintomas vista_sintomas = new vista_sintomas();
        vista_sintomas.anuncio.setText("Medicina Recomendada");
        vista_sintomas.cajaSintomas.setText(medicamentos);
        vista_sintomas.botonEnviar.setText("ACEPTAR");
        vista_sintomas.setVisible(true);

        //Agregamos al boton el evento de cerrar la ventada
        ActionListener al = new ActionListener() {
            //Se activa al hacer click en el botón llamar
            @Override
            public void actionPerformed(ActionEvent e) {
                vista_sintomas.setVisible(false);
            }
        };
        vista_sintomas.botonEnviar.addActionListener(al);

        //Sección de Código para ubicar las pestañas
        if (contAgentes == 2) {
            vista_sintomas.setLocation(600, 270);
        } else if (contAgentes == 3) {
            vista_sintomas.setLocation(600, 540);
        } else {
            vista_sintomas.setLocation(600, 10);
        }
        //Termina sección
        //Se leen los medicamentos al usuario
        Lee lee = new Lee();
        lee.leer(medicamentos);

        System.out.println("RECETAR MEDICINA");
    }

    //Metodo para presentar las medidas de bioseguridad al paciente
    private void medidasBioseguridad() {
        String medidas = "Medidas de Bioseguridad: \n";
        //Establece conexión con la base de datos
        Conexion conexion = new Conexion();
        ArrayList<MedidaBioseguridad> listaMedidas = new ArrayList<MedidaBioseguridad>();
        listaMedidas = conexion.buscarMedidasBioseguridad();
        //For s eencarga de formar el mensaje que se presentará al usuario
        for (int i = 0; i < listaMedidas.size(); i++) {
            MedidaBioseguridad medida = new MedidaBioseguridad();
            medida = listaMedidas.get(i);
            medidas = medidas + "- " + medida.getNombre() + ". \n";
        }
        //Presentar las medidas de bioseguridad al usuario
        vista_sintomas vista_sintomas = new vista_sintomas();
        vista_sintomas.anuncio.setText("Medidas de bioseguridad");
        vista_sintomas.cajaSintomas.setText(medidas);
        vista_sintomas.botonEnviar.setText("ACEPTAR");
        vista_sintomas.setVisible(true);

        //Agregamos al boton el evento de cerrar la ventada
        ActionListener al = new ActionListener() {
            //Se activa al hacer click en el botón llamar
            @Override
            public void actionPerformed(ActionEvent e) {
                vista_sintomas.setVisible(false);
            }
        };
        vista_sintomas.botonEnviar.addActionListener(al);

        //Sección de Código para ubicar las pestañas
        if (contAgentes == 2) {
            vista_sintomas.setLocation(600, 270);
        } else if (contAgentes == 3) {
            vista_sintomas.setLocation(600, 540);
        } else {
            vista_sintomas.setLocation(600, 10);
        }
        //Termina sección
        //Se leen las medidas de bioseguridad al usuario
        Lee lee = new Lee();
        lee.leer(medidas);
        System.out.println("MULTIMEDIA DE MEDIDAS BIOSEGURIDAD");
    }
}
=======
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
import clases.MedidaBioseguridad;
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
import multimedias.Lee;
import ventanas.vista_opciones;
import ventanas.vista_persona;
import ventanas.vista_sintomas;
import java.util.concurrent.TimeUnit;

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
    private int contAgentes;

    public void setup() {
        System.out.println("----AGENTE CREADO-----");
        Object[] args = getArguments();
        System.out.println("Agente en ejecución: Agente" + args[0]);
        contAgentes = Integer.parseInt(String.valueOf(args[2]));
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
//                    System.out.println("vacio");
                }
            }
        });
    }

    //Método para obtener los síntomas del paciente
    private void sintomasPaciente() {
        //Llamamos a la vista para el ingreso de los datos
        vista_sintomas vista_sintomas = new vista_sintomas();
        vista_sintomas.setVisible(true);

        Lee lee = new Lee();
        lee.leer("Por favor... ingrese los síntomas que posee ");

        ActionListener al = new ActionListener() {
            //Se activa al hacer click en el botón llamar
            @Override
            public void actionPerformed(ActionEvent e) {
                //Almacena el número de la llamada que ingresa
                sintomas = vista_sintomas.cajaSintomas.getText();
                if (sintomas.length() > 0) {
                    vista_sintomas.setVisible(false);
                    //Sección de Código para ubicar las pestañas
                    if (contAgentes == 2) {
                        vista_sintomas.setLocation(600, 270);
                    } else if (contAgentes == 3) {
                        vista_sintomas.setLocation(600, 540);
                    } else {
                        vista_sintomas.setLocation(600, 10);
                    }
                    //Termina sección
                    //Llamamos al método para clasificar los síntomas
                    clasificarSintomas();
                } else {
                    lee.leer("Información no encontrada ..... por favor intente de nuevo");
                }
            }
        };
        vista_sintomas.botonEnviar.addActionListener(al);

    }

    //Clasifica los sintomas ingresados por el paciente, los registra en la base de datos
    //y toma decisiones acorde al resultado
    private void clasificarSintomas() {
        //Se traen los sintomas de la base de datos
        Conexion conexion = new Conexion();
        ArrayList<Sintoma> sintomasReferencia = new ArrayList<Sintoma>();
        sintomasReferencia = conexion.buscarSintomas();

        /*
        Contadores para poder clasificar los síntomas
        
        El uso de tres contadores es debido a que cada síntoma tiene su prioridad
        por ende se debe usar un contador para cada prioridad.
         */
        int contadorPrioridadUno = 0;
        int contadorPrioridadDos = 0;
        int contadorPrioridadTres = 0;

        //Arraylist para almacenar los síntomas que el paciente tiene
        ArrayList<Sintoma> sintomasPaciente = new ArrayList<Sintoma>();
        // Comparo los sintomas de la base de datos con los ingresados por el usuario
        for (int i = 0; i < sintomasReferencia.size(); i++) {
            Sintoma sintoma = new Sintoma();
            sintoma = sintomasReferencia.get(i);
            //Sintomas contiene el string con los sintomas del paciente en donde se buscan los sintomas de referencia
            //Estandarizamos los síntomas de referencia y los del paciente en minúsculas
            if (sintomas.toLowerCase().contains(sintoma.getNombre().toLowerCase())) {
                //registra los sintomas del paciente a la base de datos
                Conexion conexion2 = new Conexion();
                conexion2.insertarSintomaPaciente(paciente.getCedula(), sintoma.getNombre(),
                        sintoma.getPrioridad());
                sintomasPaciente.add(sintoma);
                //Se asigana el grado de prioridad basado en los sintomas del paciente
                if (sintoma.getPrioridad() == 3) {
                    contadorPrioridadTres++;
                    System.out.println("Sintoma P3 Encontrado: " + sintoma.getNombre());
                } else if (sintoma.getPrioridad() == 2) {
                    contadorPrioridadDos++;
                    System.out.println("Sintoma P2 Encontrado: " + sintoma.getNombre());
                } else if (sintoma.getPrioridad() == 1) {
                    contadorPrioridadUno++;
                    System.out.println("Sintoma P1 Encontrado: " + sintoma.getNombre());
                }
            }
        }

        //Generamos String de sintomas con formato CSV para presentar en consola y por voz
        String cadenaSintomas = "";
        for (int i = 0; i < sintomasPaciente.size(); i++) {
            System.out.println("Sintomas encontrados: " + sintomasPaciente.get(i).getNombre());
            cadenaSintomas = cadenaSintomas + sintomasPaciente.get(i).getNombre();
            cadenaSintomas = cadenaSintomas + ",";
        }

        //Clasificación del paciente, almacenar el diagnostico en la bdd y toma decisiones 
        Conexion conexionDiagnostico = new Conexion();
        if (contadorPrioridadTres >= 3) {
            diagnostico.setNumCedula(paciente.getCedula());
            diagnostico.setDiagnostico("Atención medica urgente");
            diagnostico.setDetalles("se recetó medicina");
            conexionDiagnostico.insertarDiagnosticoPaciente(diagnostico.getNumCedula(),
                    diagnostico.getDiagnostico(), diagnostico.getDetalles());
            System.out.println("PACIENTE PRIORIDAD TRES -- Grado máximo");
            Lee lee = new Lee();
            lee.leer("Paciente clasificado como prioridad tres .... grado máximo ...");

            pacientePrioridadTres();
        } else if (contadorPrioridadTres == 2) {
            diagnostico.setNumCedula(paciente.getCedula());
            diagnostico.setDiagnostico("Atención medica opcional");
            diagnostico.setDetalles("No hay observaciones");
            conexionDiagnostico.insertarDiagnosticoPaciente(diagnostico.getNumCedula(),
                    diagnostico.getDiagnostico(), diagnostico.getDetalles());
            System.out.println("PACIENTE PRIORIDAD DOS -- Grado medio");
            Lee lee = new Lee();
            lee.leer("Paciente clasificado como prioridad dos .... grado medio ..."
                    + "Se recomienda hacer uso de la cita médica ... ");
            pacientePrioridadDos();
        } else if (contadorPrioridadDos >= 2) {
            diagnostico.setNumCedula(paciente.getCedula());
            diagnostico.setDiagnostico("Atención medica opcional");
            diagnostico.setDetalles("No hay observaciones");
            conexionDiagnostico.insertarDiagnosticoPaciente(diagnostico.getNumCedula(),
                    diagnostico.getDiagnostico(), diagnostico.getDetalles());
            System.out.println("PACIENTE PRIORIDAD DOS -- Grado medio");
            Lee lee = new Lee();
            lee.leer("Paciente clasificado como prioridad dos .... grado medio ..."
                    + "Se recomienda hacer uso de la cita médica ... ");
            pacientePrioridadDos();
        } else {
            diagnostico.setNumCedula(paciente.getCedula());
            diagnostico.setDiagnostico("No requiere atención medica");
            diagnostico.setDetalles("No hay observaciones");
            conexionDiagnostico.insertarDiagnosticoPaciente(diagnostico.getNumCedula(),
                    diagnostico.getDiagnostico(), diagnostico.getDetalles());
            System.out.println("PACIENTE PRIORIDAD UNO -- Grado leve");
            Lee lee = new Lee();
            lee.leer("Paciente clasificado como prioridad Uno .... grado leve ... ");
            pacientePrioridadUno();
        }

    }

    private void datosPaciente(String numero_telefono) {
        //Conexion con la base de datos
        Conexion conexion = new Conexion();
        vista_persona vista_persona = new vista_persona();
        vista_persona.setVisible(true);
        //Sección de Código para ubicar las pestañas
        if (contAgentes == 2) {
            vista_persona.setLocation(600, 270);
        } else if (contAgentes == 3) {
            vista_persona.setLocation(600, 540);
        } else {
            vista_persona.setLocation(600, 10);
        }
        //Termina sección
        Lee lee = new Lee();
        lee.leer("Por favor ingrese sus datos personales..... ");

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

    private void pacientePrioridadUno() {
        medidasBioseguridad();
    }

    private void pacientePrioridadDos() {
        generarCita();
        medidasBioseguridad();
    }

    private void pacientePrioridadTres() {
        generarCita();
        recetarMedicina();
        medidasBioseguridad();
    }

    //Metodo para generar una cita al paciente
    private void generarCita() {
        //Se inicializa el objeto cita
        Cita cita = new Cita();
        cita.setNombres(paciente.getNombre());
        cita.setEdad(paciente.getEdad());
        cita.setCedula(paciente.getCedula());
        cita.setCorreo(paciente.getCorreo());
        cita.setSintomas(cadenaSintomas);
        cita.setDiagnostico(diagnostico.getDiagnostico());
        cita.setNumTelefono(paciente.getNum_telefono());
        //Se establece un formato a la fecha de la cita
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy/h:mm");
        Date someDate = new Date();
        String fecha = sdf.format(new Date(someDate.getTime() + TimeUnit.DAYS.toMillis( 1 )));
        cita.setFecha(fecha);
        //Se almacena en la base de datos
        Conexion conexionCita = new Conexion();
        conexionCita.insertarCita(cita.getNombres(), cita.getEdad(), cita.getCedula(),
                cita.getCorreo(), cita.getSintomas(), cita.getDiagnostico(), cita.getFecha());
        //Presentar cita al usuario
        String citaTexto = "Se ha generado una cita\na Nombre de: " + cita.getNombres()
                + " \n con el Correo electrónico: \n"
                + cita.getCorreo() + " \n Fecha de la cita:\n " + cita.getFecha();
        vista_sintomas vista_sintomas = new vista_sintomas();
        vista_sintomas.anuncio.setText("CITA GENERADA");
        vista_sintomas.cajaSintomas.setText(citaTexto);
        vista_sintomas.botonEnviar.setText("ACEPTAR");
        vista_sintomas.setVisible(true);

        //Agregamos al boton el evento de cerrar la ventada
        ActionListener al = new ActionListener() {
            //Se activa al hacer click en el botón llamar
            @Override
            public void actionPerformed(ActionEvent e) {
                vista_sintomas.setVisible(false);
            }
        };
        vista_sintomas.botonEnviar.addActionListener(al);

        //Sección de Código para ubicar las pestañas
        if (contAgentes == 2) {
            vista_sintomas.setLocation(600, 270);
        } else if (contAgentes == 3) {
            vista_sintomas.setLocation(600, 540);
        } else {
            vista_sintomas.setLocation(600, 10);
        }
        //Termina sección
        //Se lee la información de la cita médica al usuario
        Lee lee = new Lee();
        lee.leer(citaTexto);
        System.out.println("GENERAR CITA MÉDICA");
    }

    //Metodo para recetar medicina al paciente
    private void recetarMedicina() {
        //Presentar el medicamento al usuario
        String medicamentos = "En caso de emergencia ...\n"
                + "se recomienda la administración de: " + "\nPARACETAMOL";
        vista_sintomas vista_sintomas = new vista_sintomas();
        vista_sintomas.anuncio.setText("Medicina Recomendada");
        vista_sintomas.cajaSintomas.setText(medicamentos);
        vista_sintomas.botonEnviar.setText("ACEPTAR");
        vista_sintomas.setVisible(true);

        //Agregamos al boton el evento de cerrar la ventada
        ActionListener al = new ActionListener() {
            //Se activa al hacer click en el botón llamar
            @Override
            public void actionPerformed(ActionEvent e) {
                vista_sintomas.setVisible(false);
            }
        };
        vista_sintomas.botonEnviar.addActionListener(al);

        //Sección de Código para ubicar las pestañas
        if (contAgentes == 2) {
            vista_sintomas.setLocation(600, 270);
        } else if (contAgentes == 3) {
            vista_sintomas.setLocation(600, 540);
        } else {
            vista_sintomas.setLocation(600, 10);
        }
        //Termina sección
        //Se leen los medicamentos al usuario
        Lee lee = new Lee();
        lee.leer(medicamentos);

        System.out.println("RECETAR MEDICINA");
    }

    //Metodo para presentar las medidas de bioseguridad al paciente
    private void medidasBioseguridad() {
        String medidas = "Medidas de Bioseguridad: \n";
        //Establece conexión con la base de datos
        Conexion conexion = new Conexion();
        ArrayList<MedidaBioseguridad> listaMedidas = new ArrayList<MedidaBioseguridad>();
        listaMedidas = conexion.buscarMedidasBioseguridad();
        //For s eencarga de formar el mensaje que se presentará al usuario
        for (int i = 0; i < listaMedidas.size(); i++) {
            MedidaBioseguridad medida = new MedidaBioseguridad();
            medida = listaMedidas.get(i);
            medidas = medidas + "- " + medida.getNombre() + ". \n";
        }
        //Presentar las medidas de bioseguridad al usuario
        vista_sintomas vista_sintomas = new vista_sintomas();
        vista_sintomas.anuncio.setText("Medidas de bioseguridad");
        vista_sintomas.cajaSintomas.setText(medidas);
        vista_sintomas.botonEnviar.setText("ACEPTAR");
        vista_sintomas.setVisible(true);

        //Agregamos al boton el evento de cerrar la ventada
        ActionListener al = new ActionListener() {
            //Se activa al hacer click en el botón llamar
            @Override
            public void actionPerformed(ActionEvent e) {
                vista_sintomas.setVisible(false);
            }
        };
        vista_sintomas.botonEnviar.addActionListener(al);

        //Sección de Código para ubicar las pestañas
        if (contAgentes == 2) {
            vista_sintomas.setLocation(600, 270);
        } else if (contAgentes == 3) {
            vista_sintomas.setLocation(600, 540);
        } else {
            vista_sintomas.setLocation(600, 10);
        }
        //Termina sección
        //Se leen las medidas de bioseguridad al usuario
        Lee lee = new Lee();
        lee.leer(medidas);
        System.out.println("MULTIMEDIA DE MEDIDAS BIOSEGURIDAD");
    }
}
>>>>>>> 64a0c9e3c48d89b0b1831b977748a5b6c6892c48

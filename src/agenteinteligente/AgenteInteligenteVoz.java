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
import java.text.Normalizer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import multimedias.Escuchar;
import multimedias.Lee;
import ventanas.vista_opciones;
import ventanas.vista_persona;
import ventanas.vista_sintomas;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author Sandro Córdova
 */
public class AgenteInteligenteVoz extends Agent {

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
                //Llama al método DatosPaciente como tarea inicial
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
        ActionListener al = new ActionListener() {
            //Se activa al hacer click en el botón llamar
            @Override
            public void actionPerformed(ActionEvent e) {
                //Almacena el número de la llamada que ingresa
                sintomas = vista_sintomas.cajaSintomas.getText();
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
            }
        };
        vista_sintomas.botonEnviar.addActionListener(al);

        // Inicia módulo de voz
        //Se abre el lector para informar al usuario
        Lee lee = new Lee();

        // 2 SEGUNDOS PARA ESCUCHAR lo que dice el usuario
        Escuchar escuchar = new Escuchar();
        String seleccionUsuario = "";

        Boolean bool = true;
        while (seleccionUsuario.length() <= 3) {
            if (bool) {
                //Solicitamos el ingreso de cédula
                lee.leer("Por favor... utilice su voz para ingresar los síntomas que posee "
                        + ".... Por ejemplo: Gripe ... tos ... fiebre alta .... presión alta");
                bool = false;
            }
            // 2 SEGUNDOS PARA ESCUCHAR lo que dice el usuario
            escuchar.escucharPython();
            //Leemos respuesta del usuario
            seleccionUsuario = escuchar.leerTxt("sintomas", 6);
            if (seleccionUsuario != null && seleccionUsuario.length() >= 3) {
                //Enviamos los síntomas del usuario y enviamos a clasificar 
                sintomas = seleccionUsuario;
                vista_sintomas.cajaSintomas.setText(String.valueOf(seleccionUsuario));
                vista_sintomas.setVisible(false);
                clasificarSintomas();
                break;
            }
            lee.leer("Información no encontrada ..... por favor intente de nuevo");
        }
        // Fin modilo de voz

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
            sintomas = quitaDiacriticos(sintomas);
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
            pacientePrioridadTres();
        } else if (contadorPrioridadTres == 2) {
            diagnostico.setNumCedula(paciente.getCedula());
            diagnostico.setDiagnostico("Atención medica opcional");
            diagnostico.setDetalles("No hay observaciones");
            conexionDiagnostico.insertarDiagnosticoPaciente(diagnostico.getNumCedula(),
                    diagnostico.getDiagnostico(), diagnostico.getDetalles());
            System.out.println("PACIENTE PRIORIDAD DOS -- Grado medio");
            pacientePrioridadDos();
        } else if (contadorPrioridadDos >= 2) {
            diagnostico.setNumCedula(paciente.getCedula());
            diagnostico.setDiagnostico("Atención medica opcional");
            diagnostico.setDetalles("No hay observaciones");
            conexionDiagnostico.insertarDiagnosticoPaciente(diagnostico.getNumCedula(),
                    diagnostico.getDiagnostico(), diagnostico.getDetalles());
            System.out.println("PACIENTE PRIORIDAD DOS -- Grado medio");
            pacientePrioridadDos();
        } else {
            diagnostico.setNumCedula(paciente.getCedula());
            diagnostico.setDiagnostico("No requiere atención medica");
            diagnostico.setDetalles("No hay observaciones");
            conexionDiagnostico.insertarDiagnosticoPaciente(diagnostico.getNumCedula(),
                    diagnostico.getDiagnostico(), diagnostico.getDetalles());
            System.out.println("PACIENTE PRIORIDAD UNO -- Grado leve");
            pacientePrioridadUno();
        }

    }

    public static String quitaDiacriticos(String cadena) {
        String limpio = null;
        if (cadena != null) {
            String valor = cadena;
            valor = valor.toUpperCase();
            // Normalizar texto para eliminar acentos, dieresis, cedillas y tildes
            limpio = Normalizer.normalize(valor, Normalizer.Form.NFD);
            // Quitar caracteres no ASCII excepto la enie, interrogacion que abre, exclamacion que abre, grados, U con dieresis.
            limpio = limpio.replaceAll("[^\\p{ASCII}(N\u0303)(n\u0303)(\u00A1)(\u00BF)(\u00B0)(U\u0308)(u\u0308)]", "");
            // Regresar a la forma compuesta, para poder comparar la enie con la tabla de valores
            limpio = Normalizer.normalize(limpio, Normalizer.Form.NFC);
        }
        return limpio;
    }

    //Solicita la información personal del paciente
    private void datosPaciente(String numero_telefono) {
        //Conexion con la base de datos
        Conexion conexion = new Conexion();

        //Se crea la interfaz gráfica del usuario
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

        //Inicia Interfaz gráfica
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

        //Fin interfáz gráfica
        //Creamos el objeto paciente
        paciente = new Paciente();
        paciente.setNum_telefono(numero_telefono);

        // Inicia módulo de voz
        //Se abre el lector para informar al usuario
        Lee lee = new Lee();
        Escuchar escuchar = new Escuchar();

        String datoRequerido = "cedula";
        String seleccionUsuario = "";
        Boolean bool = true;
        while (datoRequerido != null) {
            switch (datoRequerido) {
                case "cedula":
                    //------###########
                    //Solo realizar una vez
                    if (bool) {
                        //Solicitamos el ingreso de cédula
                        lee.leer("Por favor utilice su voz para ingresar su número de cédula ó pasaporte ..... ");
                        bool = false;
                    } else {
                        lee.leer("Información no encontrada, vuelva a intentarlo");
                    }
                    //------###########
                    // 10 SEGUNDOS PARA ESCUCHAR lo que dice el usuario
                    escuchar.escucharPython();
                    //Solicitamos el ingreso de la cedula
                    seleccionUsuario = escuchar.leerTxt("numeros", 5);
                    //Buscar cedula en la base de datos si existe, hasta aquí se llega 
                    ArrayList<Paciente> listaPacientes = new ArrayList<Paciente>();
                    listaPacientes = conexion.buscarPacientes();
                    if (seleccionUsuario.length() >= 3) {
                        for (int i = 0; i < listaPacientes.size(); i++) {
//                        System.out.println(listaPacientes.get(i).getCedula() + " cedulas registradas");
                            try {

                                if (String.valueOf(listaPacientes.get(i).getCedula()).contains(seleccionUsuario)) {
                                    System.out.println("COINCIDE CON LA BDD");
                                    //Guardamos los valores ingresados por el usuario
                                    paciente.setCedula(seleccionUsuario);
                                    vista_persona.cajaCedula.setText(seleccionUsuario); // se puede eliminar

                                    paciente.setNombre(listaPacientes.get(i).getNombre());
                                    vista_persona.cajaNombre.setText(listaPacientes.get(i).getNombre()); // se puede eliminar

                                    paciente.setCorreo(listaPacientes.get(i).getCorreo());
                                    vista_persona.cajaCorreo.setText(listaPacientes.get(i).getCorreo()); // se puede eliminar

                                    paciente.setEdad(listaPacientes.get(i).getEdad());
                                    vista_persona.cajaEdad.setText(String.valueOf(listaPacientes.get(i).getEdad())); // se puede eliminar

                                    //Se registra el paciente en la base de datos y llamamos al siguiente método
                                    conexion.insertarPaciente(paciente.getNum_telefono(), paciente.getNombre(), paciente.getCedula(),
                                            paciente.getCorreo(), paciente.getEdad());

                                    //Se termina la recolección de los datos del paciente
                                    datoRequerido = null;
                                    seleccionUsuario = "";
                                    //Limpiamos respuestas
                                    Escuchar escucharLimpiar = new Escuchar();
                                    escucharLimpiar.limpiarArchivo();
                                    lee.leer("Datos encontrados en la base de datos .... ");
                                    sintomasPaciente();
                                    break;
                                }

                            } catch (Exception e) {
                                System.out.println("------ERROR al convertir STR--------------");
                            }
                        }
                    }
                    //Tomamos la cedula del usuario
                    if (seleccionUsuario.length() >= 3) {
                        try {
                            int aux = Integer.parseInt(seleccionUsuario);
                        } catch (Exception e) {
                            lee.leer("Por favor ingrese un valor numérico.... ");
                            break;
                        }
                        //Guardamos los valores ingresados por el usuario
                        paciente.setCedula(seleccionUsuario);
                        vista_persona.cajaCedula.setText(seleccionUsuario); // se puede eliminar

                        //Solicitamos el ingreso de nombre
                        datoRequerido = "nombre";
                        //Limpiamos respuestas
                        Escuchar escucharLimpiar = new Escuchar();
                        escucharLimpiar.limpiarArchivo();
                        lee.leer("Paciente no registrado en la base de datos .... ");
                        bool = true;
                        break;
                    }
                    break;

                case "nombre":
                    //Solicitamos el ingreso de nombre
                    lee.leer("Por favor... utilice su voz para ingresar su nombre");
                    // 2 SEGUNDOS PARA ESCUCHAR lo que dice el usuario
                    escuchar.escucharPython();
                    //Lee los datos del usuario
                    seleccionUsuario = escuchar.leerTxt("nombre", 3);
                    //Solicitamos el nombre del usuario

                    if (seleccionUsuario.length() >= 3) {
                        //Guardamos los valores ingresados por el usuario
                        paciente.setNombre(seleccionUsuario);
                        vista_persona.cajaNombre.setText(seleccionUsuario); // se puede eliminar
                        //Solicitamos el ingreso de cédula
                        datoRequerido = "correo";
                        //Limpiamos respuestas
                        Escuchar escucharLimpiar = new Escuchar();
                        escucharLimpiar.limpiarArchivo();
                        lee.leer("Datos encontrados .... " + seleccionUsuario);
                        bool = true;
                        break;
                    }
                    lee.leer("Información no encontrada");
                    break;

                case "correo":
                    if (bool) {
                        lee.leer("Por favor utilice su voz para ingresar su correo electrónico");
                        bool = false;
                    }
                    escuchar.escucharPython();
                    seleccionUsuario = escuchar.leerTxt("correo", 5);
                    if (!seleccionUsuario.contains("@")) {
                        lee.leer("Correo electrónico no válido, por favor intente nuevamente");
                        break;
                    }
                    if (seleccionUsuario.length() >= 2) {
                        paciente.setCorreo(seleccionUsuario);
                        vista_persona.cajaCorreo.setText(seleccionUsuario); // se puede eliminar
                        //Solicitamos el ingreso de la edad
                        datoRequerido = "edad";
                        //Limpiamos respuestas
                        Escuchar escucharLimpiar = new Escuchar();
                        escucharLimpiar.limpiarArchivo();
                        lee.leer("Datos encontrados .... " + seleccionUsuario);
                        bool = true;
                        break;
                    }
                    lee.leer("Información no encontrada");
                    break;

                case "edad":
                    if (bool) {
                        //Solicitamos el ingreso de cédula
                        lee.leer("Por favor utilice su voz para ingresar su edad");
                        bool = false;
                    }
                    escuchar.escucharPython();
                    int edad = 0;
                    try {
                        edad = Integer.parseInt(escuchar.leerTxt("numeros", 3));
                    } catch (Exception e) {
                        lee.leer("Valor erróneo .... Por favor ingrese un valor numérico");
                        break;
                    }
                    //Tomamos la cedula del usuario
                    if (edad >= 18) {
                        //Guardamos los valores ingresados por el usuario
                        paciente.setEdad(edad);
                        vista_persona.cajaEdad.setText(String.valueOf(edad)); // se puede eliminar

                        //Se registra el paciente en la base de datos y llamamos al siguiente método
                        conexion.insertarPaciente(paciente.getNum_telefono(), paciente.getNombre(), paciente.getCedula(),
                                paciente.getCorreo(), paciente.getEdad());

                        //Se termina la recolección de los datos del paciente
                        datoRequerido = null;
                        //Limpiamos respuestas
                        Escuchar escucharLimpiar = new Escuchar();
                        escucharLimpiar.limpiarArchivo();
                        lee.leer("Datos encontrados .... " + edad);
                        vista_persona.setVisible(false);
                        sintomasPaciente();
                        break;
                    } else if (edad < 18) {
                        lee.leer("La edad ingresada no es válida, debe tener almenos 18 años");
                        break;
                    }
                    lee.leer("Información no encontrada");
                    break;

            }
        }
        // Fin modilo de voz
    }

    private void pacientePrioridadUno() {
        // Inicia módulo de voz
        //Se abre el lector para informar al usuario
        Lee lee = new Lee();
        lee.leer("Basado en sus síntomas, usted ha sido clasificado como paciente de prioridad baja");
        //Termina módulo de voz
        medidasBioseguridad();
    }

    private void pacientePrioridadDos() {
        // Inicia módulo de voz
        //Se abre el lector para informar al usuario
        Lee lee = new Lee();
        lee.leer("Basado en sus síntomas, usted ha sido clasificado como paciente de prioridad media"
                + "Se recomienda hacer uso de la cita médica ... ");
        //Termina módulo de voz
        generarCita();
        medidasBioseguridad();
    }

    private void pacientePrioridadTres() {
        // Inicia módulo de voz
        //Se abre el lector para informar al usuario
        Lee lee = new Lee();
        lee.leer("Basado en sus síntomas, usted ha sido clasificado como paciente de prioridad alta");
        //Termina módulo de voz
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
        String fecha = sdf.format(new Date(someDate.getTime() + TimeUnit.DAYS.toMillis(1)));
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

        //Inicia interfaz de voz
        //Se lee la información de la cita médica al usuario
        String citaTextovoz = "Se ha generado una cita médica a Nombre de: " + cita.getNombres()
                + " .... con el Correo electrónico " + cita.getCorreo() + ".... Fecha de la cita ... " + cita.getFecha();

        Lee lee = new Lee();
        lee.leer(citaTextovoz);
        //Fin interfaz voz
        System.out.println("CITA MÉDICA GENERADA");
    }

    //Metodo para recetar medicina al paciente
    private void recetarMedicina() {
        //Presentar el medicamento al usuario
        String medicamentos = "En caso de emergencia\n"
                + "se recomienda la administración de: " + "\n---PARACETAMOL---";
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
import java.text.Normalizer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import multimedias.Escuchar;
import multimedias.Lee;
import ventanas.vista_opciones;
import ventanas.vista_persona;
import ventanas.vista_sintomas;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author Sandro Córdova
 */
public class AgenteInteligenteVoz extends Agent {

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
                //Llama al método DatosPaciente como tarea inicial
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
        ActionListener al = new ActionListener() {
            //Se activa al hacer click en el botón llamar
            @Override
            public void actionPerformed(ActionEvent e) {
                //Almacena el número de la llamada que ingresa
                sintomas = vista_sintomas.cajaSintomas.getText();
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
            }
        };
        vista_sintomas.botonEnviar.addActionListener(al);

        // Inicia módulo de voz
        //Se abre el lector para informar al usuario
        Lee lee = new Lee();

        // 2 SEGUNDOS PARA ESCUCHAR lo que dice el usuario
        Escuchar escuchar = new Escuchar();
        String seleccionUsuario = "";

        Boolean bool = true;
        while (seleccionUsuario.length() <= 3) {
            if (bool) {
                //Solicitamos el ingreso de cédula
                lee.leer("Por favor... utilice su voz para ingresar los síntomas que posee "
                        + ".... Por ejemplo: Gripe ... tos ... fiebre alta .... presión alta");
                bool = false;
            }
            // 2 SEGUNDOS PARA ESCUCHAR lo que dice el usuario
            escuchar.escucharPython();
            //Leemos respuesta del usuario
            seleccionUsuario = escuchar.leerTxt("sintomas", 6);
            if (seleccionUsuario != null && seleccionUsuario.length() >= 3) {
                //Enviamos los síntomas del usuario y enviamos a clasificar 
                sintomas = seleccionUsuario;
                vista_sintomas.cajaSintomas.setText(String.valueOf(seleccionUsuario));
                vista_sintomas.setVisible(false);
                clasificarSintomas();
                break;
            }
            lee.leer("Información no encontrada ..... por favor intente de nuevo");
        }
        // Fin modilo de voz

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
            sintomas = quitaDiacriticos(sintomas);
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
            pacientePrioridadTres();
        } else if (contadorPrioridadTres == 2) {
            diagnostico.setNumCedula(paciente.getCedula());
            diagnostico.setDiagnostico("Atención medica opcional");
            diagnostico.setDetalles("No hay observaciones");
            conexionDiagnostico.insertarDiagnosticoPaciente(diagnostico.getNumCedula(),
                    diagnostico.getDiagnostico(), diagnostico.getDetalles());
            System.out.println("PACIENTE PRIORIDAD DOS -- Grado medio");
            pacientePrioridadDos();
        } else if (contadorPrioridadDos >= 2) {
            diagnostico.setNumCedula(paciente.getCedula());
            diagnostico.setDiagnostico("Atención medica opcional");
            diagnostico.setDetalles("No hay observaciones");
            conexionDiagnostico.insertarDiagnosticoPaciente(diagnostico.getNumCedula(),
                    diagnostico.getDiagnostico(), diagnostico.getDetalles());
            System.out.println("PACIENTE PRIORIDAD DOS -- Grado medio");
            pacientePrioridadDos();
        } else {
            diagnostico.setNumCedula(paciente.getCedula());
            diagnostico.setDiagnostico("No requiere atención medica");
            diagnostico.setDetalles("No hay observaciones");
            conexionDiagnostico.insertarDiagnosticoPaciente(diagnostico.getNumCedula(),
                    diagnostico.getDiagnostico(), diagnostico.getDetalles());
            System.out.println("PACIENTE PRIORIDAD UNO -- Grado leve");
            pacientePrioridadUno();
        }

    }

    public static String quitaDiacriticos(String cadena) {
        String limpio = null;
        if (cadena != null) {
            String valor = cadena;
            valor = valor.toUpperCase();
            // Normalizar texto para eliminar acentos, dieresis, cedillas y tildes
            limpio = Normalizer.normalize(valor, Normalizer.Form.NFD);
            // Quitar caracteres no ASCII excepto la enie, interrogacion que abre, exclamacion que abre, grados, U con dieresis.
            limpio = limpio.replaceAll("[^\\p{ASCII}(N\u0303)(n\u0303)(\u00A1)(\u00BF)(\u00B0)(U\u0308)(u\u0308)]", "");
            // Regresar a la forma compuesta, para poder comparar la enie con la tabla de valores
            limpio = Normalizer.normalize(limpio, Normalizer.Form.NFC);
        }
        return limpio;
    }

    //Solicita la información personal del paciente
    private void datosPaciente(String numero_telefono) {
        //Conexion con la base de datos
        Conexion conexion = new Conexion();

        //Se crea la interfaz gráfica del usuario
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

        //Inicia Interfaz gráfica
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

        //Fin interfáz gráfica
        //Creamos el objeto paciente
        paciente = new Paciente();
        paciente.setNum_telefono(numero_telefono);

        // Inicia módulo de voz
        //Se abre el lector para informar al usuario
        Lee lee = new Lee();
        Escuchar escuchar = new Escuchar();

        String datoRequerido = "cedula";
        String seleccionUsuario = "";
        Boolean bool = true;
        while (datoRequerido != null) {
            switch (datoRequerido) {
                case "cedula":
                    //------###########
                    //Solo realizar una vez
                    if (bool) {
                        //Solicitamos el ingreso de cédula
                        lee.leer("Por favor utilice su voz para ingresar su número de cédula ó pasaporte ..... ");
                        bool = false;
                    } else {
                        lee.leer("Información no encontrada, vuelva a intentarlo");
                    }
                    //------###########
                    // 10 SEGUNDOS PARA ESCUCHAR lo que dice el usuario
                    escuchar.escucharPython();
                    //Solicitamos el ingreso de la cedula
                    seleccionUsuario = escuchar.leerTxt("numeros", 5);
                    //Buscar cedula en la base de datos si existe, hasta aquí se llega 
                    ArrayList<Paciente> listaPacientes = new ArrayList<Paciente>();
                    listaPacientes = conexion.buscarPacientes();
                    if (seleccionUsuario.length() >= 3) {
                        for (int i = 0; i < listaPacientes.size(); i++) {
//                        System.out.println(listaPacientes.get(i).getCedula() + " cedulas registradas");
                            try {

                                if (String.valueOf(listaPacientes.get(i).getCedula()).contains(seleccionUsuario)) {
                                    System.out.println("COINCIDE CON LA BDD");
                                    //Guardamos los valores ingresados por el usuario
                                    paciente.setCedula(seleccionUsuario);
                                    vista_persona.cajaCedula.setText(seleccionUsuario); // se puede eliminar

                                    paciente.setNombre(listaPacientes.get(i).getNombre());
                                    vista_persona.cajaNombre.setText(listaPacientes.get(i).getNombre()); // se puede eliminar

                                    paciente.setCorreo(listaPacientes.get(i).getCorreo());
                                    vista_persona.cajaCorreo.setText(listaPacientes.get(i).getCorreo()); // se puede eliminar

                                    paciente.setEdad(listaPacientes.get(i).getEdad());
                                    vista_persona.cajaEdad.setText(String.valueOf(listaPacientes.get(i).getEdad())); // se puede eliminar

                                    //Se registra el paciente en la base de datos y llamamos al siguiente método
                                    conexion.insertarPaciente(paciente.getNum_telefono(), paciente.getNombre(), paciente.getCedula(),
                                            paciente.getCorreo(), paciente.getEdad());

                                    //Se termina la recolección de los datos del paciente
                                    datoRequerido = null;
                                    seleccionUsuario = "";
                                    //Limpiamos respuestas
                                    Escuchar escucharLimpiar = new Escuchar();
                                    escucharLimpiar.limpiarArchivo();
                                    lee.leer("Datos encontrados en la base de datos .... ");
                                    sintomasPaciente();
                                    break;
                                }

                            } catch (Exception e) {
                                System.out.println("------ERROR al convertir STR--------------");
                            }
                        }
                    }
                    //Tomamos la cedula del usuario
                    if (seleccionUsuario.length() >= 3) {
                        try {
                            int aux = Integer.parseInt(seleccionUsuario);
                        } catch (Exception e) {
                            lee.leer("Por favor ingrese un valor numérico.... ");
                            break;
                        }
                        //Guardamos los valores ingresados por el usuario
                        paciente.setCedula(seleccionUsuario);
                        vista_persona.cajaCedula.setText(seleccionUsuario); // se puede eliminar

                        //Solicitamos el ingreso de nombre
                        datoRequerido = "nombre";
                        //Limpiamos respuestas
                        Escuchar escucharLimpiar = new Escuchar();
                        escucharLimpiar.limpiarArchivo();
                        lee.leer("Paciente no registrado en la base de datos .... ");
                        bool = true;
                        break;
                    }
                    break;

                case "nombre":
                    //Solicitamos el ingreso de nombre
                    lee.leer("Por favor... utilice su voz para ingresar su nombre");
                    // 2 SEGUNDOS PARA ESCUCHAR lo que dice el usuario
                    escuchar.escucharPython();
                    //Lee los datos del usuario
                    seleccionUsuario = escuchar.leerTxt("nombre", 3);
                    //Solicitamos el nombre del usuario

                    if (seleccionUsuario.length() >= 3) {
                        //Guardamos los valores ingresados por el usuario
                        paciente.setNombre(seleccionUsuario);
                        vista_persona.cajaNombre.setText(seleccionUsuario); // se puede eliminar
                        //Solicitamos el ingreso de cédula
                        datoRequerido = "correo";
                        //Limpiamos respuestas
                        Escuchar escucharLimpiar = new Escuchar();
                        escucharLimpiar.limpiarArchivo();
                        lee.leer("Datos encontrados .... " + seleccionUsuario);
                        bool = true;
                        break;
                    }
                    lee.leer("Información no encontrada");
                    break;

                case "correo":
                    if (bool) {
                        lee.leer("Por favor utilice su voz para ingresar su correo electrónico");
                        bool = false;
                    }
                    escuchar.escucharPython();
                    seleccionUsuario = escuchar.leerTxt("correo", 5);
                    if (!seleccionUsuario.contains("@")) {
                        lee.leer("Correo electrónico no válido, por favor intente nuevamente");
                        break;
                    }
                    if (seleccionUsuario.length() >= 2) {
                        paciente.setCorreo(seleccionUsuario);
                        vista_persona.cajaCorreo.setText(seleccionUsuario); // se puede eliminar
                        //Solicitamos el ingreso de la edad
                        datoRequerido = "edad";
                        //Limpiamos respuestas
                        Escuchar escucharLimpiar = new Escuchar();
                        escucharLimpiar.limpiarArchivo();
                        lee.leer("Datos encontrados .... " + seleccionUsuario);
                        bool = true;
                        break;
                    }
                    lee.leer("Información no encontrada");
                    break;

                case "edad":
                    if (bool) {
                        //Solicitamos el ingreso de cédula
                        lee.leer("Por favor utilice su voz para ingresar su edad");
                        bool = false;
                    }
                    escuchar.escucharPython();
                    int edad = 0;
                    try {
                        edad = Integer.parseInt(escuchar.leerTxt("numeros", 3));
                    } catch (Exception e) {
                        lee.leer("Valor erróneo .... Por favor ingrese un valor numérico");
                        break;
                    }
                    //Tomamos la cedula del usuario
                    if (edad >= 18) {
                        //Guardamos los valores ingresados por el usuario
                        paciente.setEdad(edad);
                        vista_persona.cajaEdad.setText(String.valueOf(edad)); // se puede eliminar

                        //Se registra el paciente en la base de datos y llamamos al siguiente método
                        conexion.insertarPaciente(paciente.getNum_telefono(), paciente.getNombre(), paciente.getCedula(),
                                paciente.getCorreo(), paciente.getEdad());

                        //Se termina la recolección de los datos del paciente
                        datoRequerido = null;
                        //Limpiamos respuestas
                        Escuchar escucharLimpiar = new Escuchar();
                        escucharLimpiar.limpiarArchivo();
                        lee.leer("Datos encontrados .... " + edad);
                        vista_persona.setVisible(false);
                        sintomasPaciente();
                        break;
                    } else if (edad < 18) {
                        lee.leer("La edad ingresada no es válida, debe tener almenos 18 años");
                        break;
                    }
                    lee.leer("Información no encontrada");
                    break;

            }
        }
        // Fin modilo de voz
    }

    private void pacientePrioridadUno() {
        // Inicia módulo de voz
        //Se abre el lector para informar al usuario
        Lee lee = new Lee();
        lee.leer("Basado en sus síntomas, usted ha sido clasificado como paciente de prioridad baja");
        //Termina módulo de voz
        medidasBioseguridad();
    }

    private void pacientePrioridadDos() {
        // Inicia módulo de voz
        //Se abre el lector para informar al usuario
        Lee lee = new Lee();
        lee.leer("Basado en sus síntomas, usted ha sido clasificado como paciente de prioridad media"
                + "Se recomienda hacer uso de la cita médica ... ");
        //Termina módulo de voz
        generarCita();
        medidasBioseguridad();
    }

    private void pacientePrioridadTres() {
        // Inicia módulo de voz
        //Se abre el lector para informar al usuario
        Lee lee = new Lee();
        lee.leer("Basado en sus síntomas, usted ha sido clasificado como paciente de prioridad alta");
        //Termina módulo de voz
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
        String fecha = sdf.format(new Date(someDate.getTime() + TimeUnit.DAYS.toMillis(1)));
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

        //Inicia interfaz de voz
        //Se lee la información de la cita médica al usuario
        String citaTextovoz = "Se ha generado una cita médica a Nombre de: " + cita.getNombres()
                + " .... con el Correo electrónico " + cita.getCorreo() + ".... Fecha de la cita ... " + cita.getFecha();

        Lee lee = new Lee();
        lee.leer(citaTextovoz);
        //Fin interfaz voz
        System.out.println("CITA MÉDICA GENERADA");
    }

    //Metodo para recetar medicina al paciente
    private void recetarMedicina() {
        //Presentar el medicamento al usuario
        String medicamentos = "En caso de emergencia\n"
                + "se recomienda la administración de: " + "\n---PARACETAMOL---";
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

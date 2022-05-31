/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package principal;

import agenteinteligente.Escucha;
import baseDeDatos.Conexion;
import clases.Cita;
import clases.Extension;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import javax.swing.JOptionPane;
import clases.Llamada;
import clases.Paciente;
import clases.Sintoma;
import jade.core.AID;
import jade.lang.acl.ACLMessage;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Locale;
import javax.speech.Central;
import javax.speech.EngineModeDesc;
import javax.speech.recognition.Recognizer;
import javax.speech.recognition.Result;
import javax.speech.recognition.ResultAdapter;
import javax.speech.recognition.ResultEvent;
import javax.speech.recognition.ResultToken;
import javax.speech.recognition.RuleGrammar;
import multimedias.Escuchar;
import multimedias.Lee;
import ventanas.vista_administrador;
import ventanas.vista_gestion;
import static ventanas.vista_gestion.anuncioCedulaCita;
import static ventanas.vista_gestion.anuncioCedulaPaciente;
import static ventanas.vista_gestion.anuncioFecha;
import static ventanas.vista_gestion.btnActualizarFecha;
import static ventanas.vista_gestion.btnEliminarCita;
import static ventanas.vista_gestion.btnEliminarPaciente;
import static ventanas.vista_gestion.cajaCedulaCita;
import static ventanas.vista_gestion.cajaCedulaPaciente;
import static ventanas.vista_gestion.cajaFechaCita;
import ventanas.vista_llamada;
import ventanas.vista_opciones;

/**
 *
 * @author USUARIO
 */
public class Main {

    static int contAgentes = 0;
    static int contAgentes2 = 0;

    public static void main(String[] args) {

        vista_llamada vista_llama = new vista_llamada();
        vista_llama.setVisible(true);

        vista_administrador vista_administrador = new vista_administrador();
        vista_administrador.setVisible(true);

        ActionListener alAdmin = new ActionListener() {
            //Se activa al hacer click en el botón llamar
            @Override
            public void actionPerformed(ActionEvent e) {
                menuAdministrador();
            }
        };
        //creamos el escuchador
        vista_administrador.botonLlamar.addActionListener(alAdmin);

        //Bucle para escuchar constantemente las llamadas entrantes
        ActionListener al = new ActionListener() {
            //Se activa al hacer click en el botón llamar
            @Override
            public void actionPerformed(ActionEvent e) {
                //Almacena el número de la llamada que ingresa
                String numero = vista_llama.cajaNumero.getText();
                if ((numero.length() == 10 && numero.startsWith("09"))
                        || ((numero.length() == 9) && (numero.startsWith("02") || (numero.startsWith("03"))
                        || (numero.startsWith("04")) || (numero.startsWith("05")) || (numero.startsWith("07"))))) {
                    System.out.println(numero);
                    //Creamos el objeto "LLAMADA"
                    Llamada llamada = new Llamada();
                    //Asignamos número del celular/teléfono
                    llamada.setNum_telefono(numero);
                    //Reiniciamos el ingreso de nuevas llamadas
                    vista_llama.anuncio1.setText("");
                    vista_llama.cajaNumero.setText("");

                    //Se activa el metodo para llamada entrante
                    hay_llamada(llamada);
                } else {
                    vista_llama.anuncio1.setText("El número ingresado no es válido ");
                }
            }
        };
        //creamos el escuchador
        vista_llama.botonLlamar.addActionListener(al);

    }

    public static void hay_llamada(Llamada llamada) {

        //Sección para contar el numero de agentes par ubicar las vistas
        if (contAgentes2 == 4) {
            contAgentes2 = 1;
        } else {
            contAgentes2++;
        }
        //termina sección
        
//        //--------------Inicia Interfaz de voz
//        String texto = "Bienvenido al Sistema de llamadas teléfonicas";
//        
//        texto = texto + " ... Opción 1, Transferir llamada a la recepción ... Opción 2, Centro de información de covid 19";
//        texto = texto + " ... Por favor, Utilice su voz para seleccionar una de las opciones";
//        Lee lee = new Lee();
//        lee.leer(texto);
//        Escuchar escuchar = new Escuchar();
//        texto = escuchar.leerTxt();
//        lee.leer("Opción seleccionada: "+texto);
        //2 segundos a lo mucho
//        //--------------Inicia Interfaz de voz
        
        //--------------Inicia Interfaz gráfica
        vista_opciones vista_opciones = new vista_opciones();
        vista_opciones.setVisible(true);
        vista_opciones.cajaOpciones.setText("DIGITE UNA OPCIÓN \n"
                + "1. Transferir llamada \n"
                + "2. Centro de información COVID-19");
        //Sección de Código para ubicar las pestañas
        if (contAgentes2 == 2) {
            vista_opciones.setLocation(600, 270);
        } else if (contAgentes2 == 3) {
            vista_opciones.setLocation(600, 540);
        } else {
            vista_opciones.setLocation(600, 10);
        }
        //Termina sección
        ActionListener al = new ActionListener() {
            //Se activa al hacer click en el botón llamar
            @Override
            public void actionPerformed(ActionEvent e) {
                //Almacena el número de la llamada que ingresa
                int entrada = Integer.parseInt(vista_opciones.cajaOpcion.getText());
                llamada.setSeleccion(entrada);
                //Abre comunicacion con la base de datos
                Conexion coneccion = new Conexion();
                //Tomar decisión acorde a la selección del usuario
                if (llamada.getSeleccion() == 1) {
                    //Lo registra en la base de datos
                    coneccion.insertarLlamada(llamada.getNum_telefono(), llamada.getSeleccion());
                    transferir_llamada(llamada);
                    vista_opciones.setVisible(false);
                } else if (llamada.getSeleccion() == 2) {
                    //Lo registra en la base de datos
                    coneccion.insertarLlamada(llamada.getNum_telefono(), llamada.getSeleccion());
                    //Sección para contar el numero de agentes
                    if (contAgentes == 4) {
                        contAgentes = 1;
                    } else {
                        contAgentes++;
                    }
                    //termina sección
                    //llamamos a crear agente
                    crear_agente(llamada);
                    vista_opciones.setVisible(false);
                } else {
                    vista_opciones.anuncio1.setText("Opción no válida");
                }
            }
        };
        vista_opciones.botonEnviar.addActionListener(al);
        //--------------Termina Interfaz gráfica
    }

    public static void crear_agente(Llamada llamada) {
        Runtime rt = Runtime.instance();
        Profile p = new ProfileImpl();
        p.setParameter(Profile.MAIN_HOST, "localhost");
        p.setParameter(Profile.GUI, "true");
        ContainerController cc = rt.createMainContainer(p);
        AgentController ac;

        Object[] arguments = new Object[3];
        arguments[0] = llamada.getNum_telefono();
        arguments[1] = llamada.getSeleccion();
        arguments[2] = contAgentes;

        try {
            //inicia el agente inteligente y enviamos argumentos
            ac = cc.createNewAgent("Agente" + llamada.getNum_telefono(),
                    "agenteinteligente.AgenteInteligente", arguments);
            ac.start();

        } catch (Exception e) {
            System.out.println("ERRO al CREAR el Agente inteligente");
        }
    }

    public static boolean transferir_llamada(Llamada llamada) {
        Conexion conexion = new Conexion();
        ArrayList<Extension> listaExtensiones = new ArrayList<Extension>();
        listaExtensiones = conexion.buscarExtension();
        for (int i = 0; i < listaExtensiones.size(); i++) {
            Extension extension = new Extension();
            extension = listaExtensiones.get(i);
            if (extension.getEstado().contains("True")) {
                System.out.println("Su llamada ha sido transferida al:" + extension.getDetalle()
                        + " extensión: " + extension.getExtension());
                return true;
            }
        }
                return false;
    }

    public static void menuAdministrador() {
        vista_gestion vista_gestion = new vista_gestion();
        vista_gestion.setVisible(true);

        //Escuchador para listar las citas de la base de datos
        ActionListener alCita = new ActionListener() {
            //Se activa al hacer click en el botón llamar
            @Override
            public void actionPerformed(ActionEvent e) {
                vista_gestion.anuncio1.setText("Citas actuales");
                Conexion conexion = new Conexion();
                ArrayList<Cita> listaCitas = new ArrayList<Cita>();
                listaCitas = conexion.buscarCitas();
                String str = "";
                for (int i = 0; i < listaCitas.size(); i++) {
                    Cita cita = new Cita();
                    cita = listaCitas.get(i);
                    str = str + "Paciente: " + cita.getNombres()
                            + "\nCedula: " + cita.getCedula()
                            + "\nFecha de la cita: " + cita.getFecha() + "\n\n";
                }
                vista_gestion.cajaLista.setText(str);
                vista_gestion.cajaCedulaCita.setVisible(true);
                vista_gestion.cajaFechaCita.setVisible(true);
                vista_gestion.anuncioCedulaCita.setVisible(true);
                vista_gestion.anuncioFecha.setVisible(true);
                vista_gestion.btnActualizarFecha.setVisible(true);
                vista_gestion.btnEliminarCita.setVisible(true);
                vista_gestion.separador1.setVisible(true);
                vista_gestion.separador2.setVisible(true);

                vista_gestion.cajaCedulaPaciente.setVisible(false);
                vista_gestion.anuncioCedulaPaciente.setVisible(false);
                vista_gestion.btnEliminarPaciente.setVisible(false);
                vista_gestion.separador3.setVisible(false);
            }
        };
        vista_gestion.botonCitas.addActionListener(alCita);

        //Escuchador para eliminar a una cita de la base de datos
        ActionListener alEliminarCita = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vista_gestion.anuncio1.setText("Eliminar Cita");
                Conexion conexion = new Conexion();
                ArrayList<Cita> listaCitas = new ArrayList<Cita>();
                listaCitas = conexion.buscarCitas();

                Cita cita = new Cita();
                cita = conexion.buscarCita(listaCitas,
                        vista_gestion.cajaCedulaCita.getText());
                if (cita != null) {
                    conexion.eliminarCita(cita.getCedula());
                    vista_gestion.anuncio1.setText("Cita Eliminada");
                } else {
                    vista_gestion.anuncio1.setText("Cita no encontrada");
                }
            }
        };
        vista_gestion.btnEliminarCita.addActionListener(alEliminarCita);

        //Escuchador para actualizar una cita de la base de datos
        ActionListener alActualizarCita = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vista_gestion.anuncio1.setText("Actualizar Cita");
                Conexion conexion = new Conexion();
                ArrayList<Cita> listaCitas = new ArrayList<Cita>();
                listaCitas = conexion.buscarCitas();

                Cita cita = new Cita();
                cita = conexion.buscarCita(listaCitas,
                        vista_gestion.cajaCedulaCita.getText());
                if (cita != null) {
                    if (conexion.actualizarCita(cita, vista_gestion.cajaFechaCita.getText())) {
                        vista_gestion.anuncio1.setText("Cita Actualizada");
                    } else {
                        vista_gestion.anuncio1.setText("Cita no encontrada");
                    }
                } else {
                    vista_gestion.anuncio1.setText("Cita no encontrada");
                }
            }
        };
        vista_gestion.btnActualizarFecha.addActionListener(alActualizarCita);

        //Escuchador para listar los pacientes de la base de datos
        ActionListener alPaciente = new ActionListener() {
            //Se activa al hacer click en el botón llamar
            @Override
            public void actionPerformed(ActionEvent e) {
                vista_gestion.anuncio1.setText("Pacientes actuales");
                Conexion conexion = new Conexion();
                ArrayList<Paciente> listaPacientes = new ArrayList<Paciente>();
                listaPacientes = conexion.buscarPacientes();
                String str = "";
                for (int i = 0; i < listaPacientes.size(); i++) {
                    Paciente paciente = new Paciente();
                    paciente = listaPacientes.get(i);
                    str = str + "Paciente: " + paciente.getNombre()
                            + "\nCedula: " + paciente.getCedula()
                            + "\nTelefono: " + paciente.getNum_telefono() + "\n\n";
                }
                vista_gestion.cajaLista.setText(str);
                vista_gestion.cajaCedulaCita.setVisible(false);
                vista_gestion.cajaFechaCita.setVisible(false);
                vista_gestion.anuncioCedulaCita.setVisible(false);
                vista_gestion.anuncioFecha.setVisible(false);
                vista_gestion.btnActualizarFecha.setVisible(false);
                vista_gestion.btnEliminarCita.setVisible(false);
                vista_gestion.separador1.setVisible(false);
                vista_gestion.separador2.setVisible(false);

                vista_gestion.cajaCedulaPaciente.setVisible(true);
                vista_gestion.anuncioCedulaPaciente.setVisible(true);
                vista_gestion.btnEliminarPaciente.setVisible(true);
                vista_gestion.separador3.setVisible(true);
            }
        };
        vista_gestion.botonPacientes.addActionListener(alPaciente);

        //Escuchador para eliminar a un paciente de la base de datos
        ActionListener alEliminarPaciente = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vista_gestion.anuncio1.setText("Eliminar Paciente");
                Conexion conexion = new Conexion();
                ArrayList<Paciente> listaPacientes = new ArrayList<Paciente>();
                listaPacientes = conexion.buscarPacientes();

                Paciente paciente = new Paciente();
                paciente = conexion.buscarPaciente(listaPacientes,
                        vista_gestion.cajaCedulaPaciente.getText());
                if (paciente != null) {
                    conexion.eliminarPaciente(paciente.getCedula());
                    vista_gestion.anuncio1.setText("Paciente Eliminado");
                } else {
                    vista_gestion.anuncio1.setText("Paciente no encontrado");
                }
            }
        };
        vista_gestion.btnEliminarPaciente.addActionListener(alEliminarPaciente);
    }
}

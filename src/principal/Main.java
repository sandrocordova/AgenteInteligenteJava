/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package principal;

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
import java.util.ArrayList;
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

    public static void main(String[] args) {
        boolean hayLlamada = false;
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
        while (!hayLlamada) {
            ActionListener al = new ActionListener() {
                //Se activa al hacer click en el botón llamar
                @Override
                public void actionPerformed(ActionEvent e) {
                    //Almacena el número de la llamada que ingresa
                    String numero = vista_llama.cajaNumero.getText();
                    if (numero.length() >= 5) {
                        System.out.println(numero);
                        //Creamos el objeto "LLAMADA"
                        Llamada llamada = new Llamada();
                        //Asignamos número del celular/teléfono
                        llamada.setNum_telefono(numero);
//                        vista_llama.anuncio1.setVisible(false);
                        //Reiniciamos el ingreso de nuevas llamadas
                        vista_llama.cajaNumero.setText("");
                        //Se activa el metodo para llamada entrante
                        hay_llamada(llamada);

                    } else if (numero.length() <= 5) {
                        vista_llama.anuncio1.setText("Opción no válida");
                    }
                }
            };
            //creamos el escuchador
            vista_llama.botonLlamar.addActionListener(al);
        }

    }

    public static void hay_llamada(Llamada llamada) {
        vista_opciones vista_opciones = new vista_opciones();
        vista_opciones.setVisible(true);
        vista_opciones.cajaOpciones.setText("DIGITE UNA OPCIÓN \n"
                + "1. Transferir llamada \n"
                + "2. Centro de información COVID-19");
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
                    crear_agente(llamada);
                    vista_opciones.setVisible(false);
                } else {
                    vista_opciones.anuncio1.setText("Opción no válida");
                }
            }
        };
        vista_opciones.botonEnviar.addActionListener(al);
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

        try {
            ac = cc.createNewAgent("Agente" + llamada.getNum_telefono(),
                    "agenteinteligente.AgenteInteligente", arguments);
            ac.start();

        } catch (Exception e) {
            System.out.println("ERRO al CREAR el Agente inteligente");
        }
    }

    public static void transferir_llamada(Llamada llamada) {
        Conexion conexion = new Conexion();
        ArrayList<Extension> listaExtensiones = new ArrayList<Extension>();
        listaExtensiones = conexion.buscarExtension();
        for (int i = 0; i < listaExtensiones.size(); i++) {
            Extension extension = new Extension();
            extension = listaExtensiones.get(i);
            if (extension.getEstado().contains("True")) {
                System.out.println("Su llamada ha sido transferida al:" + extension.getDetalle()
                        + " extensión: " + extension.getExtension());
            }
        }
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
                    str = str+"Paciente: " + cita.getNombres()
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

                vista_gestion.cajaCedulaPaciente.setVisible(false);
                vista_gestion.anuncioCedulaPaciente.setVisible(false);
                vista_gestion.btnEliminarPaciente.setVisible(false);
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
                    str = str+"Paciente: " + paciente.getNombre()
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

                vista_gestion.cajaCedulaPaciente.setVisible(true);
                vista_gestion.anuncioCedulaPaciente.setVisible(true);
                vista_gestion.btnEliminarPaciente.setVisible(true);
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

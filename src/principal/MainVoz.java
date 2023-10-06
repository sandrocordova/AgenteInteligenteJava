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
import static principal.Main.menuDominio;
import static principal.Main.menuSimbolos;
import ventanas.vista_administrador;
import ventanas.vista_dominio;
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
import ventanas.vista_simbolos;

/**
 *
 * @author USUARIO
 */
public class MainVoz {

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

        //Llama al menú de DOMINIOS
        ActionListener alDominios = new ActionListener() {
            //Se activa al hacer click en el botón llamar
            @Override
            public void actionPerformed(ActionEvent e) {
                menuDominio();
            }
        };
        //creamos el escuchador
        vista_administrador.botonDominio.addActionListener(alDominios);

        //Llama al menú de SIMBOLOS
        ActionListener alSimbolos = new ActionListener() {
            //Se activa al hacer click en el botón llamar
            @Override
            public void actionPerformed(ActionEvent e) {
                menuSimbolos("Símbolo");
            }
        };
        //creamos el escuchador
        vista_administrador.botonSimbolo.addActionListener(alSimbolos);

        //Llama al menú de NUMEROS
        ActionListener alNumeros = new ActionListener() {
            //Se activa al hacer click en el botón llamar
            @Override
            public void actionPerformed(ActionEvent e) {
                menuSimbolos("Numero");
            }
        };
        //creamos el escuchador
        vista_administrador.botonNumero.addActionListener(alNumeros);

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
                    vista_llama.cajaNumero.setText("0900002003");

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

        //--------------Inicia Interfaz de voz
        String texto = "Bienvenido al Sistema de llamadas teléfonicas";
        texto = texto + " ... Opción 1, Transferir llamada a la recepción ... Opción 2, Centro de información de covid 19";
        texto = texto + " ... Por favor, Utilice su voz para seleccionar una de las opciones";
        Lee lee = new Lee();
        lee.leer(texto);

        // 2 SEGUNDOS PARA ESCUCHAR lo que dice el usuario
        Escuchar escuchar = new Escuchar();
        //Bucle para esperar una selección válida del cliente
        while (true) {
            //Activamos el escuchador
            escuchar.escucharPython();
            //Tomamos la respuesta del usuario y lo convertimos a int
            int seleccionUsuario = 0;
            try {
                int respuesta = Integer.parseInt(escuchar.leerTxt("seleccion", 3));
                seleccionUsuario = respuesta;
            } catch (Exception e) {
                lee.leer("Valor erróneo .... por favor ingrese una de las opciones dadas");
            }
            if (seleccionUsuario == 1) {
                llamada.setSeleccion(seleccionUsuario);
                lee.leer("Opción seleccionada: " + seleccionUsuario);
                transferir_llamada(llamada);
                break;
            } else if (seleccionUsuario == 2) {
                llamada.setSeleccion(seleccionUsuario);
                Conexion coneccion = new Conexion();
                //Lo registra en la base de datos
                coneccion.insertarLlamada(llamada.getNum_telefono(), llamada.getSeleccion());
                //Sección para contar el numero de agentes
                if (contAgentes == 4) {
                    contAgentes = 1;
                } else {
                    contAgentes++;
                }

                lee.leer("Opción seleccionada: " + seleccionUsuario);
                //limpiamos la respuesta anterior
                Escuchar escucha = new Escuchar();
                escucha.limpiarArchivo();
                //Se crea el agente
                crear_agente(llamada);
                break;
            }
            System.out.println("OPCION NO VALIDA: " + seleccionUsuario);
        }
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
                    "agenteinteligente.AgenteInteligenteVoz", arguments);
            ac.start();

        } catch (Exception e) {
            System.out.println("ERRO al CREAR el Agente inteligente");
        }
    }

    public static void menuDominio() {
        vista_dominio vista_dominio = new vista_dominio();
        vista_dominio.setVisible(true);

        //Escuchador para listar las citas de la base de datos
        ActionListener alCita = new ActionListener() {
            //Se activa al hacer click en el botón llamar
            @Override
            public void actionPerformed(ActionEvent e) {
                Conexion conexion = new Conexion();
                if (vista_dominio.cajaDominio.getText().contains(".")) {
                    String texto = vista_dominio.cajaDominio.getText();
                    int posicionPunto = texto.indexOf('.');
                    String sHastaPrimerPunto = texto.substring(0, posicionPunto);
                    conexion.insertarDominio(vista_dominio.cajaDominio.getText().toLowerCase(), sHastaPrimerPunto);
                    vista_dominio.cajaDetalle.setText("Dominio registrado: " + vista_dominio.cajaDominio.getText() + " Indentificador: " + sHastaPrimerPunto);
                    vista_dominio.cajaDominio.setText("");
                } else {
                    vista_dominio.cajaDetalle.setText("Dominio no válido");
                }

            }
        };
        vista_dominio.botonLlamar.addActionListener(alCita);
    }

    public static void menuSimbolos(String entrada) {
        vista_simbolos vista_simbolos = new vista_simbolos();
        vista_simbolos.setVisible(true);
        vista_simbolos.letreroPrincipal.setText("REGISTRAR " + entrada.toUpperCase());
        vista_simbolos.letreroSecundario.setText(entrada + ":");
        //Escuchador para listar las citas de la base de datos
        ActionListener alCita = new ActionListener() {
            //Se activa al hacer click en el botón llamar
            @Override
            public void actionPerformed(ActionEvent e) {
                if (vista_simbolos.cajaNombre.getText().length() >= 1 && vista_simbolos.cajaSimbolo.getText().length() >= 1) {
                    Conexion conexion = new Conexion();
                    conexion.insertarSimbolo(vista_simbolos.cajaNombre.getText().toLowerCase(), vista_simbolos.cajaSimbolo.getText().toLowerCase(), entrada);
                    vista_simbolos.cajaDetalle.setText(entrada + " registrado con éxito: " + vista_simbolos.cajaSimbolo.getText());
                    vista_simbolos.cajaSimbolo.setText("");
                    vista_simbolos.cajaNombre.setText("");
                } else {
                    vista_simbolos.cajaDetalle.setText("Complete todos los campos");
                }

            }
        };
        vista_simbolos.botonRegistrar.addActionListener(alCita);
    }

    public static boolean transferir_llamada(Llamada llamada) {
        Conexion conexion = new Conexion();
        ArrayList<Extension> listaExtensiones = new ArrayList<Extension>();
        listaExtensiones = conexion.buscarExtension();
        for (int i = 0; i < listaExtensiones.size(); i++) {
            Extension extension = new Extension();
            extension = listaExtensiones.get(i);
            if (extension.getEstado().contains("True")) {
                //Lo registra en la base de datos
                conexion.insertarLlamada(llamada.getNum_telefono(), llamada.getSeleccion());

                //Imprime en consola
                System.out.println("Su llamada ha sido transferida a la:" + extension.getDetalle()
                        + " extensión: " + extension.getExtension());

                //Evía al módulo de voz
                String textoTransferirLlamada = "Su llamada ha sido transferida a la:" + extension.getDetalle()
                        + " extensión: " + extension.getExtension() + " ... por favor espere mientras es atendido";
                Lee lee = new Lee();
                lee.leer(textoTransferirLlamada);
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
                            + "\nCorreo: " + paciente.getCorreo()
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

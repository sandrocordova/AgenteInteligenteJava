/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package principal;

import baseDeDatos.Conexion;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import javax.swing.JOptionPane;
import clases.Llamada;
import jade.core.AID;
import jade.lang.acl.ACLMessage;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
//                        vista_llama.anuncio1.setVisible(true);

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
            ac = cc.createNewAgent("Agente"+llamada.getNum_telefono(), "agenteinteligente.AgenteInteligente", arguments);
            ac.start();

        } catch (Exception e) {
            System.out.println("ERRO al CREAR el Agente inteligente");
        }
    }

    public static void transferir_llamada(Llamada llamada) {
        System.out.println("Su llamada ha sido transferida al departamento de secretaría");
    }

}

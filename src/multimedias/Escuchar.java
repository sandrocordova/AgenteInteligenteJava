<<<<<<< HEAD
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package multimedias;

import baseDeDatos.Conexion;
import clases.Dominios;
import clases.Numero;
import clases.Simbolo;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.util.*;

public class Escuchar {

    //Se inicializan las variables
    String respuesta;
    Lee lee = new Lee();

    public Escuchar() {
        //Se cargan las configuraciones iniciales
        //Activamos el lector de voz de Python
    }

    //Método para iniciar el escuchador de python
    public void escucharPython() {
        try {
            //Ejecutamos el archivo .py desde el CMD
            Process ps = Runtime.getRuntime().exec("cmd /c start cmd.exe /k \" cd C:\\Users\\Usuario\\Desktop && py escuchar.py");
            //Esperamos a que el proceso se ejecute
            ps.waitFor();
        } catch (Exception e) {
            System.out.println("ERROR en el TRY");
        }
    }

    public void limpiarArchivo() {
        try {
            FileWriter file = new FileWriter("C:\\Users\\Usuario\\Desktop\\respuesta.txt");
            BufferedWriter bf = new BufferedWriter(file);
            bf.write("");
            bf.close();
            System.out.println("Archivo limpiado");
        } catch (Exception e) {
            System.out.println("Archivo no encontrado");
        }
    }

    //Metodo para leer el texto ingresado por voz
    public String leerTxt(String entrada, int duracion) {

        System.out.println("Esperando respuesta del usuario");
        //Ponemos a "Dormir" el programa 
        try {
            Thread.sleep(duracion * 5000);
        } catch (Exception e) {
            System.out.println(e);
        }

        System.out.println("Lectura del archivo");
        try {
            InputStream ins = new FileInputStream("C:\\Users\\Usuario\\Desktop\\respuesta.txt");
            Scanner obj = new Scanner(ins);
            if (obj.hasNext()) {
                respuesta = obj.nextLine();
                if (entrada == "seleccion") {
                    respuesta = String.valueOf(seleccion(respuesta));
                } else if (entrada == "numeros") {
                    respuesta = String.valueOf(buscarNumeros(respuesta));
                    respuesta = String.valueOf(espacios(respuesta));
                } else if (entrada == "correo") {
                    respuesta = String.valueOf(correo(respuesta));
                } else if (entrada == "sintomas") {
                    String correoReturn = "";
                    String[] arreglo = respuesta.split(" ");
                    for (int i = 0; i < arreglo.length; i++) {
                        if (arreglo[i].contains("gri")) {
                            arreglo[i] = "gripe";
                        }
                        if (arreglo[i].contains("fie")) {
                            arreglo[i] = "fiebre";
                        }
                        if (arreglo[i].contains("f") && arreglo[i].contains("re")) {
                            arreglo[i] = "fiebre";
                        }
                        if (arreglo[i].contains("to") || arreglo[i].contains("os")) {
                            arreglo[i] = "tos";
                        }
                        if (arreglo[i].contains("cabeza")) {
                            arreglo[i] = "dolor de cabeza";
                        }
                        if (arreglo[i].contains("those")) {
                            arreglo[i] = "tos";
                        }
                        if (arreglo[i].contains("pr")) {
                            arreglo[i] = "presion alta";
                        }
                        if (arreglo[i].contains("dia") || arreglo[i].contains("rea") || arreglo[i].contains("rr")) {
                            arreglo[i] = "diarrea";
                        }
                        if (arreglo[i].contains("vo") || arreglo[i].contains("bo")) {
                            arreglo[i] = "vomito";
                        }
                        if (arreglo[i].contains("sia") || arreglo[i].contains("cia")) {
                            arreglo[i] = "cianosis";
                        }
                        if (arreglo[i].contains("ipo") || arreglo[i].contains("hipo")) {
                            arreglo[i] = "hipotension";
                        }
                        if (arreglo[i].contains("bonito")) {
                            arreglo[i] = "vomito";
                        }
                        if (arreglo[i].contains("dis")) {
                            arreglo[i] = "disnea";
                        }
                        if (arreglo[i].contains("cabeza")) {
                            arreglo[i] = "dolor de cabeza";
                        }
                        if (arreglo[i].contains("cabesa")) {
                            arreglo[i] = "dolor de cabeza";
                        }
                        if (arreglo[i].contains("cabe")) {
                            arreglo[i] = "dolor de cabeza";
                        }
                        if (arreglo[i].contains("olfa")) {
                            arreglo[i] = "perdida del olfato";
                        }
                        if (arreglo[i].contains("gus")) {
                            arreglo[i] = "perdida del gusto";
                        }
                        if (arreglo[i].contains("mia") && arreglo[i].contains("l")) {
                            arreglo[i] = "mialgias";
                        }
                        if (arreglo[i].contains("gia")) {
                            arreglo[i] = "mialgias";
                        }
                        if (arreglo[i].contains("nau")) {
                            arreglo[i] = "nauseas";
                        }
                        if (arreglo[i].contains("gar")) {
                            arreglo[i] = "dolor de garganta";
                        }
                        if (arreglo[i].contains("respirar")) {
                            arreglo[i] = "dolor al respirar";
                        }
                        if (arreglo[i].contains("ano")) {
                            arreglo[i] = "anosmia";
                        }
                        if (arreglo[i].contains("baja")) {
                            arreglo[i] = "baja saturacion";
                        }
                        if (arreglo[i].contains("pro")) {
                            arreglo[i] = "tos productiva";
                        }
                        if (arreglo[i].contains("espalda")) {
                            arreglo[i] = "dolor de espalda";
                        }
                        if (arreglo[i].contains("mus")) {
                            arreglo[i] = "dolor muscular";
                        }
                        if (arreglo[i].contains("cuerpo")) {
                            arreglo[i] = "dolor de cuerpo";
                        }
                        if (arreglo[i].contains("rino")) {
                            arreglo[i] = "rinorrea";
                        }
                        if (arreglo[i].contains("con")) {
                            arreglo[i] = "confusion";
                        }
                    }
                    for (int i = 0; i < arreglo.length; i++) {
                        correoReturn = correoReturn + arreglo[i] + " ";
                    }
                    respuesta = correoReturn;
                }
            } else {
                respuesta = "";
            }
        } catch (Exception e) {
            System.out.println("Se detectó un error en la lectura del archivo");
        }
        //Retornamos el texto del .txt
        return respuesta;
    }

    public String seleccion(String texto) {
        if (texto.contains("1") || texto.contains("uno")) {
            return "1";
        } else if (texto.contains("2") || texto.contains("dos") || texto.contains("doors") || texto.contains("door") || texto.contains("endorse")
                || texto.contains("does") || texto.contains("those")) {
            return "2";
        }
        return "";
    }

    public String buscarNumeros(String texto) {
        //obtiene los Numeros de la base de datos
        Conexion conexion = new Conexion();
        ArrayList<Numero> listaNumeros = new ArrayList<Numero>();
        listaNumeros = conexion.buscarNumeros();
        for (int i = 0; i < listaNumeros.size(); i++) {
            texto = texto.replace(listaNumeros.get(i).getNombre(), listaNumeros.get(i).getNumero());
        }

        //#####Código para limpiar los datos y dejar solo los números
        ArrayList<Character> lista = new ArrayList<>();
        for (int i = 0; i < texto.length(); i++) {
            if (Character.isDigit(texto.charAt(i))) {
                lista.add(texto.charAt(i));
            }
        }
        texto = "";
        for (int j = 0; j < lista.size(); j++) {
            texto += lista.get(j);
        }
        System.out.println("numeros encontrados CON DATOS FILTRADOS: " + texto);
        //#####

        return texto;
    }

    public String buscarSimbolos(String texto) {
        //obtiene los Numeros de la base de datos
        Conexion conexion = new Conexion();
        ArrayList<Simbolo> listaSimbolos = new ArrayList<Simbolo>();
        listaSimbolos = conexion.buscarSimbolos();
        for (int i = 0; i < listaSimbolos.size(); i++) {
            texto = texto.replace(listaSimbolos.get(i).getNombre(), listaSimbolos.get(i).getSimbolo());
        }
        return texto;
    }

    public String espacios(String texto) {
        texto = texto.replace(" ", "");
        return texto;
    }

    public String correo(String correo) {
        String correoReturn = "";
        String[] arreglo = correo.split(" ");
        //obtiene los dominios de la base de datos
        Conexion conexion = new Conexion();
        ArrayList<Dominios> listaDominios = new ArrayList<Dominios>();
        listaDominios = conexion.buscarDominios();
        int encontrado = 0;
        for (int i = 0; i < arreglo.length; i++) {
            for (int j = 0; j < listaDominios.size(); j++) {
                if (arreglo[i - 1].contains("guion") && arreglo[i].contains("bajo")
                        || arreglo[i - 1].contains("raya") && arreglo[i].contains("baja")
                        || arreglo[i - 1].contains("guión") && arreglo[i].contains("bajo")) {
                    System.out.println("EcONTRÓ EL GUION -------------------------------");
                    arreglo[i - 1] = " ";
                    arreglo[i] = "_";
                    System.out.println("------------------" + arreglo[i]);
                }
                if (arreglo[i].contains(String.valueOf(listaDominios.get(j).getIdentificador()))) {
                    if (arreglo[i - 1].contains("ar") && arreglo[i - 1].contains("ba") || arreglo[i - 1].contains("ar") && arreglo[i - 1].contains("va")) {
                        arreglo[i - 1] = "@";
                        arreglo[i] = listaDominios.get(j).getDominioCompleto();
                    } else {
                        arreglo[i] = "@" + listaDominios.get(j).getDominioCompleto();
                    }

                    for (int k = i + 1; k < arreglo.length; k++) {
                        arreglo[k] = "";

                    }
                    break;
                }
            }
        }
        for (int i = 0; i < arreglo.length; i++) {
            correoReturn = correoReturn + arreglo[i];
        }
        return correoReturn;
    }
}
=======
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package multimedias;

import baseDeDatos.Conexion;
import clases.Dominios;
import clases.Numero;
import clases.Simbolo;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.util.*;

public class Escuchar {

    //Se inicializan las variables
    String respuesta;
    Lee lee = new Lee();

    public Escuchar() {
        //Se cargan las configuraciones iniciales
        //Activamos el lector de voz de Python
    }

    //Método para iniciar el escuchador de python
    public void escucharPython() {
        try {
            //Ejecutamos el archivo .py desde el CMD
            Process ps = Runtime.getRuntime().exec("cmd /c start cmd.exe /k \" cd C:\\Users\\Usuario\\Desktop && py escuchar.py");
            //Esperamos a que el proceso se ejecute
            ps.waitFor();
        } catch (Exception e) {
            System.out.println("ERROR en el TRY");
        }
    }

    public void limpiarArchivo() {
        try {
            FileWriter file = new FileWriter("C:\\Users\\Usuario\\Desktop\\respuesta.txt");
            BufferedWriter bf = new BufferedWriter(file);
            bf.write("");
            bf.close();
            System.out.println("Archivo limpiado");
        } catch (Exception e) {
            System.out.println("Archivo no encontrado");
        }
    }

    //Metodo para leer el texto ingresado por voz
    public String leerTxt(String entrada, int duracion) {

        System.out.println("Esperando respuesta del usuario");
        //Ponemos a "Dormir" el programa 
        try {
            Thread.sleep(duracion * 5000);
        } catch (Exception e) {
            System.out.println(e);
        }

        System.out.println("Lectura del archivo");
        try {
            InputStream ins = new FileInputStream("C:\\Users\\Usuario\\Desktop\\respuesta.txt");
            Scanner obj = new Scanner(ins);
            if (obj.hasNext()) {
                respuesta = obj.nextLine();
                if (entrada == "seleccion") {
                    respuesta = String.valueOf(seleccion(respuesta));
                } else if (entrada == "numeros") {
                    respuesta = String.valueOf(buscarNumeros(respuesta));
                    respuesta = String.valueOf(espacios(respuesta));
                } else if (entrada == "correo") {
                    respuesta = String.valueOf(correo(respuesta));
                } else if (entrada == "sintomas") {
                    String correoReturn = "";
                    String[] arreglo = respuesta.split(" ");
                    for (int i = 0; i < arreglo.length; i++) {
                        if (arreglo[i].contains("gri")) {
                            arreglo[i] = "gripe";
                        }
                        if (arreglo[i].contains("fie")) {
                            arreglo[i] = "fiebre";
                        }
                        if (arreglo[i].contains("f") && arreglo[i].contains("re")) {
                            arreglo[i] = "fiebre";
                        }
                        if (arreglo[i].contains("to") || arreglo[i].contains("os")) {
                            arreglo[i] = "tos";
                        }
                        if (arreglo[i].contains("cabeza")) {
                            arreglo[i] = "dolor de cabeza";
                        }
                        if (arreglo[i].contains("those")) {
                            arreglo[i] = "tos";
                        }
                        if (arreglo[i].contains("pr")) {
                            arreglo[i] = "presion alta";
                        }
                        if (arreglo[i].contains("dia") || arreglo[i].contains("rea") || arreglo[i].contains("rr")) {
                            arreglo[i] = "diarrea";
                        }
                        if (arreglo[i].contains("vo") || arreglo[i].contains("bo")) {
                            arreglo[i] = "vomito";
                        }
                        if (arreglo[i].contains("sia") || arreglo[i].contains("cia")) {
                            arreglo[i] = "cianosis";
                        }
                        if (arreglo[i].contains("ipo") || arreglo[i].contains("hipo")) {
                            arreglo[i] = "hipotension";
                        }
                        if (arreglo[i].contains("bonito")) {
                            arreglo[i] = "vomito";
                        }
                        if (arreglo[i].contains("dis")) {
                            arreglo[i] = "disnea";
                        }
                        if (arreglo[i].contains("cabeza")) {
                            arreglo[i] = "dolor de cabeza";
                        }
                        if (arreglo[i].contains("cabesa")) {
                            arreglo[i] = "dolor de cabeza";
                        }
                        if (arreglo[i].contains("cabe")) {
                            arreglo[i] = "dolor de cabeza";
                        }
                        if (arreglo[i].contains("olfa")) {
                            arreglo[i] = "perdida del olfato";
                        }
                        if (arreglo[i].contains("gus")) {
                            arreglo[i] = "perdida del gusto";
                        }
                        if (arreglo[i].contains("mia") && arreglo[i].contains("l")) {
                            arreglo[i] = "mialgias";
                        }
                        if (arreglo[i].contains("gia")) {
                            arreglo[i] = "mialgias";
                        }
                        if (arreglo[i].contains("nau")) {
                            arreglo[i] = "nauseas";
                        }
                        if (arreglo[i].contains("gar")) {
                            arreglo[i] = "dolor de garganta";
                        }
                        if (arreglo[i].contains("respirar")) {
                            arreglo[i] = "dolor al respirar";
                        }
                        if (arreglo[i].contains("ano")) {
                            arreglo[i] = "anosmia";
                        }
                        if (arreglo[i].contains("baja")) {
                            arreglo[i] = "baja saturacion";
                        }
                        if (arreglo[i].contains("pro")) {
                            arreglo[i] = "tos productiva";
                        }
                        if (arreglo[i].contains("espalda")) {
                            arreglo[i] = "dolor de espalda";
                        }
                        if (arreglo[i].contains("mus")) {
                            arreglo[i] = "dolor muscular";
                        }
                        if (arreglo[i].contains("cuerpo")) {
                            arreglo[i] = "dolor de cuerpo";
                        }
                        if (arreglo[i].contains("rino")) {
                            arreglo[i] = "rinorrea";
                        }
                        if (arreglo[i].contains("con")) {
                            arreglo[i] = "confusion";
                        }
                    }
                    for (int i = 0; i < arreglo.length; i++) {
                        correoReturn = correoReturn + arreglo[i] + " ";
                    }
                    respuesta = correoReturn;
                }
            } else {
                respuesta = "";
            }
        } catch (Exception e) {
            System.out.println("Se detectó un error en la lectura del archivo");
        }
        //Retornamos el texto del .txt
        return respuesta;
    }

    public String seleccion(String texto) {
        if (texto.contains("1") || texto.contains("uno")) {
            return "1";
        } else if (texto.contains("2") || texto.contains("dos") || texto.contains("doors") || texto.contains("door") || texto.contains("endorse")
                || texto.contains("does") || texto.contains("those")) {
            return "2";
        }
        return "";
    }

    public String buscarNumeros(String texto) {
        //obtiene los Numeros de la base de datos
        Conexion conexion = new Conexion();
        ArrayList<Numero> listaNumeros = new ArrayList<Numero>();
        listaNumeros = conexion.buscarNumeros();
        for (int i = 0; i < listaNumeros.size(); i++) {
            texto = texto.replace(listaNumeros.get(i).getNombre(), listaNumeros.get(i).getNumero());
        }

        //#####Código para limpiar los datos y dejar solo los números
        ArrayList<Character> lista = new ArrayList<>();
        for (int i = 0; i < texto.length(); i++) {
            if (Character.isDigit(texto.charAt(i))) {
                lista.add(texto.charAt(i));
            }
        }
        texto = "";
        for (int j = 0; j < lista.size(); j++) {
            texto += lista.get(j);
        }
        System.out.println("numeros encontrados CON DATOS FILTRADOS: " + texto);
        //#####

        return texto;
    }

    public String buscarSimbolos(String texto) {
        //obtiene los Numeros de la base de datos
        Conexion conexion = new Conexion();
        ArrayList<Simbolo> listaSimbolos = new ArrayList<Simbolo>();
        listaSimbolos = conexion.buscarSimbolos();
        for (int i = 0; i < listaSimbolos.size(); i++) {
            texto = texto.replace(listaSimbolos.get(i).getNombre(), listaSimbolos.get(i).getSimbolo());
        }
        return texto;
    }

    public String espacios(String texto) {
        texto = texto.replace(" ", "");
        return texto;
    }

    public String correo(String correo) {
        String correoReturn = "";
        String[] arreglo = correo.split(" ");
        //obtiene los dominios de la base de datos
        Conexion conexion = new Conexion();
        ArrayList<Dominios> listaDominios = new ArrayList<Dominios>();
        listaDominios = conexion.buscarDominios();
        int encontrado = 0;
        for (int i = 0; i < arreglo.length; i++) {
            for (int j = 0; j < listaDominios.size(); j++) {
                if (arreglo[i - 1].contains("guion") && arreglo[i].contains("bajo")
                        || arreglo[i - 1].contains("raya") && arreglo[i].contains("baja")
                        || arreglo[i - 1].contains("guión") && arreglo[i].contains("bajo")) {
                    System.out.println("EcONTRÓ EL GUION -------------------------------");
                    arreglo[i - 1] = " ";
                    arreglo[i] = "_";
                    System.out.println("------------------" + arreglo[i]);
                }
                if (arreglo[i].contains(String.valueOf(listaDominios.get(j).getIdentificador()))) {
                    if (arreglo[i - 1].contains("ar") && arreglo[i - 1].contains("ba") || arreglo[i - 1].contains("ar") && arreglo[i - 1].contains("va")) {
                        arreglo[i - 1] = "@";
                        arreglo[i] = listaDominios.get(j).getDominioCompleto();
                    } else {
                        arreglo[i] = "@" + listaDominios.get(j).getDominioCompleto();
                    }

                    for (int k = i + 1; k < arreglo.length; k++) {
                        arreglo[k] = "";

                    }
                    break;
                }
            }
        }
        for (int i = 0; i < arreglo.length; i++) {
            correoReturn = correoReturn + arreglo[i];
        }
        return correoReturn;
    }
}
>>>>>>> 64a0c9e3c48d89b0b1831b977748a5b6c6892c48

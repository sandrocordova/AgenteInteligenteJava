/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package multimedias;

import java.io.FileInputStream;
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
        }
    }

    //Metodo para leer el texto ingresado por voz
    public String leerTxt() {
        System.out.println("Esperando respuesta del usuario");

        //Ponemos a "Dormir" el programa 
        try {
            Thread.sleep(10 * 1000);
        } catch (Exception e) {
            System.out.println(e);
        }

        System.out.println("Lectura del archivo");
        try {
            InputStream ins = new FileInputStream("C:\\Users\\Usuario\\Desktop\\respuesta.txt");
            Scanner obj = new Scanner(ins);
            if (obj.hasNext()) {
                respuesta = obj.nextLine();
            } else {
                lee.leer("Po favor repita su respuesta");
            }
        } catch (Exception e) {
            System.out.println("Se detectó un error en la lectura del archivo");
        }
        //Retornamos el texto del .txt
        return respuesta;
    }

}

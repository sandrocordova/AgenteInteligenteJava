
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases;

/**
 *
 * @author USUARIO
 */
public class Dominios {
    private String dominioCompleto;
    private String identificador;

    public Dominios() {
    }

    public Dominios(String dominioCompleto, String identificador) {
        this.dominioCompleto = dominioCompleto;
        this.identificador = identificador;
    }

    public String getDominioCompleto() {
        return dominioCompleto;
    }

    public void setDominioCompleto(String dominioCompleto) {
        this.dominioCompleto = dominioCompleto;
    }

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    
    
}

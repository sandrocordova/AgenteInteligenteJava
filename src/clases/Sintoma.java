/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases;

/**
 *
 * @author Sandro Córdova
 */
public class Sintoma {
    private String pacienteId;
    private String nombre;
    private int prioridad;

    public Sintoma() {
    }

    public Sintoma(String pacienteId, String nombre, int prioridad) {
        this.pacienteId = pacienteId;
        this.nombre = nombre;
        this.prioridad = prioridad;
    }

    public String getPacienteId() {
        return pacienteId;
    }

    public void setPacienteId(String pacienteId) {
        this.pacienteId = pacienteId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(int prioridad) {
        this.prioridad = prioridad;
    }

    
    
    
}

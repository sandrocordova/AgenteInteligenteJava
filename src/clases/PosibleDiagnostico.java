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
public class PosibleDiagnostico {
    private String enfermedad;
    private String sintoma;

    public PosibleDiagnostico() {
    }

    public PosibleDiagnostico(String enfermedad, String sintoma) {
        this.enfermedad = enfermedad;
        this.sintoma = sintoma;
    }

    public String getEnfermedad() {
        return enfermedad;
    }

    public void setEnfermedad(String enfermedad) {
        this.enfermedad = enfermedad;
    }

    public String getSintoma() {
        return sintoma;
    }

    public void setSintoma(String sintoma) {
        this.sintoma = sintoma;
    }

    
}

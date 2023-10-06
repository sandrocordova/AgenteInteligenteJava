package clases;

/**
 *
 * @author USUARIO
 */
public class Simbolo {
    private String nombre;
    private String simbolo;

    public Simbolo() {
    }

    public Simbolo(String nombre, String simbolo) {
        this.nombre = nombre;
        this.simbolo = simbolo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getSimbolo() {
        return simbolo;
    }

    public void setSimbolo(String simbolo) {
        this.simbolo = simbolo;
    }
    
}

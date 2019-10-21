package ec.edu.epn.snai.Modelo;

import java.io.Serializable;

public class Reporte implements Serializable {

    private String nombre;
    private String descripcion;

    public Reporte(String nombre, String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

}

package ec.edu.epn.snai.Modelo;

import java.io.Serializable;


public class RegistroAsistencia implements Serializable {

    private Taller idTaller;
    
    public RegistroAsistencia() {
    }

    public Taller getIdTaller() {
        return idTaller;
    }

    public void setIdTaller(Taller idTaller) {
        this.idTaller = idTaller;
    }

    
}

package ec.edu.epn.snai.Modelo;

import java.io.Serializable;


public class UDI implements Serializable {

    private Integer idUdi;
    private String udi;
    
    public UDI() {
    }

    public Integer getIdUdi() {
        return idUdi;
    }

    public void setIdUdi(Integer idUdi) {
        this.idUdi = idUdi;
    }

    public String getUdi() {
        return udi;
    }

    public void setUdi(String udi) {
        this.udi = udi;
    }

    @Override
    public String toString() {
        return "UDI{" + "idUdi=" + idUdi + ", udi=" + udi + '}';
    }    
}

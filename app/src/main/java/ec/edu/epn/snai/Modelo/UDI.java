package ec.edu.epn.snai.Modelo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


public class UDI implements Serializable {

    @SerializedName("idUdi")
    @Expose
    private Integer idUdi;

    @SerializedName("udi")
    @Expose
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

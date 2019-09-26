package ec.edu.epn.snai.Modelo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CAI implements Serializable {

    @SerializedName("idCai")
    @Expose
    private Integer idCai;

    @SerializedName("cai")
    @Expose
    private String cai;
    
    public CAI() {
    }

    public Integer getIdCai() {
        return idCai;
    }

    public void setIdCai(Integer idCai) {
        this.idCai = idCai;
    }

    public String getCai() {
        return cai;
    }

    public void setCai(String cai) {
        this.cai = cai;
    }

    @Override
    public String toString() {
        return "CAI{" + "idCai=" + idCai + ", cai=" + cai + '}';
    }
}

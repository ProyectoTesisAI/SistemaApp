package ec.edu.epn.snai.Modelo;

import java.io.Serializable;

public class DatosTipoPenalCAI implements Serializable {

    private Integer idDatosTipoPenal;
    private String tipoPenal;

    public DatosTipoPenalCAI() {
    }

    public DatosTipoPenalCAI(Integer idDatosTipoPenal) {
        this.idDatosTipoPenal = idDatosTipoPenal;
    }

    public DatosTipoPenalCAI(Integer idDatosTipoPenalCai, String tipoPenal) {
        this.idDatosTipoPenal = idDatosTipoPenalCai;
        this.tipoPenal = tipoPenal;
    }

    public Integer getIdDatosTipoPenal() {
        return idDatosTipoPenal;
    }

    public void setIdDatosTipoPenal(Integer idDatosTipoPenal) {
        this.idDatosTipoPenal = idDatosTipoPenal;
    }

    public String getTipoPenal() {
        return tipoPenal;
    }

    public void setTipoPenal(String tipoPenal) {
        this.tipoPenal = tipoPenal;
    }

    @Override
    public String toString() {
        return "DatosTipoPenalCAI{" + "idDatosTipoPenal=" + idDatosTipoPenal + ", tipoPenal=" + tipoPenal + '}';
    }
    
}

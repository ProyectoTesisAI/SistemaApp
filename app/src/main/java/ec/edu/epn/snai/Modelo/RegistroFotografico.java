package ec.edu.epn.snai.Modelo;

import android.graphics.Bitmap;
import java.io.Serializable;

public class RegistroFotografico implements Serializable {

    private Integer idRegistroFotografico;
    private String imagenAux;
    private Informe idInforme;
    private transient Bitmap foto;

    public RegistroFotografico() {
    }

    public Integer getIdRegistroFotografico() {
        return idRegistroFotografico;
    }

    public void setIdRegistroFotografico(Integer idRegistroFotografico) {
        this.idRegistroFotografico = idRegistroFotografico;
    }

    public String getImagenAux() {
        return imagenAux;
    }

    public void setImagenAux(String imagenAux) {
        this.imagenAux = imagenAux;
    }

    public Informe getIdInforme() {
        return idInforme;
    }

    public void setIdInforme(Informe idInforme) {
        this.idInforme = idInforme;
    }

    public Bitmap getFoto() {
        return foto;
    }

    public void setFoto(Bitmap foto) {
        this.foto = foto;
    }
}

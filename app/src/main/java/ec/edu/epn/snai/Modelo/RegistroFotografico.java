package ec.edu.epn.snai.Modelo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.Serializable;

public class RegistroFotografico implements Serializable {

    private Integer idRegistroFotografico;
    private byte[] imagen;
    private Informe idInforme;
    private Bitmap foto;
    
    public RegistroFotografico() {
    }

    public Integer getIdRegistroFotografico() {
        return idRegistroFotografico;
    }

    public void setIdRegistroFotografico(Integer idRegistroFotografico) {
        this.idRegistroFotografico = idRegistroFotografico;
    }

    public byte[] getImagen() {
        return imagen;
    }

    public void setImagen(byte[] imagen) {
        this.imagen=imagen;
        try{
            byte[] byteDatos = Base64.decode(imagen, Base64.DEFAULT);
            this.foto= BitmapFactory.decodeByteArray(byteDatos,0,byteDatos.length);
        }catch (Exception e){
            e.printStackTrace();
        }
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
}

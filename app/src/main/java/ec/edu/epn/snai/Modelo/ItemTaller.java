/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.epn.snai.Modelo;

import java.io.Serializable;

public class ItemTaller implements Serializable {

    private Integer idItemTaller;
    private Integer duracion;
    private String actividad;
    private String objetivoEspecifico;
    private String materiales;
    private String responsable;
    private Taller idTaller;

    public Integer getIdItemTaller() {
        return idItemTaller;
    }

    public void setIdItemTaller(Integer idItemTaller) {
        this.idItemTaller = idItemTaller;
    }

    public Integer getDuracion() {
        return duracion;
    }

    public void setDuracion(Integer duracion) {
        this.duracion = duracion;
    }

    public String getActividad() {
        return actividad;
    }

    public void setActividad(String actividad) {
        this.actividad = actividad;
    }

    public String getObjetivoEspecifico() {
        return objetivoEspecifico;
    }

    public void setObjetivoEspecifico(String objetivoEspecifico) {
        this.objetivoEspecifico = objetivoEspecifico;
    }

    public String getMateriales() {
        return materiales;
    }

    public void setMateriales(String materiales) {
        this.materiales = materiales;
    }

    public String getResponsable() {
        return responsable;
    }

    public void setResponsable(String responsable) {
        this.responsable = responsable;
    }

    public Taller getIdTaller() {
        return idTaller;
    }

    public void setIdTaller(Taller idTaller) {
        this.idTaller = idTaller;
    }

    @Override
    public String toString() {
        return "ItemTaller{" +
                "duracion=" + duracion +
                ", actividad='" + actividad + '\'' +
                ", objetivoEspecifico='" + objetivoEspecifico + '\'' +
                ", materiales='" + materiales + '\'' +
                ", responsable='" + responsable + '\'' +
                '}';
    }
}

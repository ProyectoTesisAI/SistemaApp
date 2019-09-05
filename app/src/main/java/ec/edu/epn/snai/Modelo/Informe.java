/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.epn.snai.Modelo;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

public class Informe implements Serializable {

    private Integer idInforme;
    private Date fecha;
    private Date horaInicio;
    private Date horaFin;
    private Integer numeroAdolescentes;
    private String adolescentesJustificacion;
    private String objetivoGeneral;
    private String socializacionDesarrollo;
    private String socializacionObjetivos;
    private String cierreEvaluacion;
    private String conclusiones;
    private String recomendaciones;
    private String observaciones;
    private String lugarSeccion;
    private Taller idTaller;

    public Integer getIdInforme() {
        return idInforme;
    }

    public void setIdInforme(Integer idInforme) {
        this.idInforme = idInforme;
    }
    

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Date getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(Date horaInicio) {
        this.horaInicio = horaInicio;
    }

    public Date getHoraFin() {        
        return horaFin;
    }

    public void setHoraFin(Date horaFin) {
        this.horaFin = horaFin;
    }

    public Integer getNumeroAdolescentes() {
        return numeroAdolescentes;
    }

    public void setNumeroAdolescentes(Integer numeroAdolescentes) {
        this.numeroAdolescentes = numeroAdolescentes;
    }

    public String getAdolescentesJustificacion() {
        return adolescentesJustificacion;
    }

    public void setAdolescentesJustificacion(String adolescentesJustificacion) {
        this.adolescentesJustificacion = adolescentesJustificacion;
    }

    public String getObjetivoGeneral() {
        return objetivoGeneral;
    }

    public void setObjetivoGeneral(String objetivoGeneral) {
        this.objetivoGeneral = objetivoGeneral;
    }

    public String getSocializacionDesarrollo() {
        return socializacionDesarrollo;
    }

    public void setSocializacionDesarrollo(String socializacionDesarrollo) {
        this.socializacionDesarrollo = socializacionDesarrollo;
    }

    public String getSocializacionObjetivos() {
        return socializacionObjetivos;
    }

    public void setSocializacionObjetivos(String socializacionObjetivos) {
        this.socializacionObjetivos = socializacionObjetivos;
    }

    public String getCierreEvaluacion() {
        return cierreEvaluacion;
    }

    public void setCierreEvaluacion(String cierreEvaluacion) {
        this.cierreEvaluacion = cierreEvaluacion;
    }

    public String getConclusiones() {
        return conclusiones;
    }

    public void setConclusiones(String conclusiones) {
        this.conclusiones = conclusiones;
    }

    public String getRecomendaciones() {
        return recomendaciones;
    }

    public void setRecomendaciones(String recomendaciones) {
        this.recomendaciones = recomendaciones;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getLugarSeccion() {
        return lugarSeccion;
    }

    public void setLugarSeccion(String lugarSeccion) {
        this.lugarSeccion = lugarSeccion;
    }

    public Taller getIdTaller() {
        return idTaller;
    }

    public void setIdTaller(Taller idTaller) {
        this.idTaller = idTaller;
    }

    
}

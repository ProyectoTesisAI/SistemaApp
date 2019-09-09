package ec.edu.epn.snai.Modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Taller implements Serializable {
    private Integer idTaller;
    private String tema;
    private Integer numeroTaller;
    private Date fecha;
    private Date horaInicio;
    private String objetivo;
    private Integer numeroTotalParticipantes;
    private String recomendaciones;
    private String tipo;
    private CAI idCai;
    private UDI idUdi;
    private Usuario idUsuario;

    public Taller() {
    }

    public Integer getIdTaller() {
        return idTaller;
    }

    public void setIdTaller(Integer idTaller) {
        this.idTaller = idTaller;
    }

    public String getTema() {
        return tema;
    }

    public void setTema(String tema) {
        this.tema = tema;
    }

    public Integer getNumeroTaller() {
        return numeroTaller;
    }

    public void setNumeroTaller(Integer numeroTaller) {
        this.numeroTaller = numeroTaller;
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

    public String getObjetivo() {
        return objetivo;
    }

    public void setObjetivo(String objetivo) {
        this.objetivo = objetivo;
    }

    public Integer getNumeroTotalParticipantes() {
        return numeroTotalParticipantes;
    }

    public void setNumeroTotalParticipantes(Integer numeroTotalParticipantes) {
        this.numeroTotalParticipantes = numeroTotalParticipantes;
    }

    public String getRecomendaciones() {
        return recomendaciones;
    }

    public void setRecomendaciones(String recomendaciones) {
        this.recomendaciones = recomendaciones;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public CAI getIdCai() { return idCai; }

    public void setIdCai(CAI idCai) { this.idCai = idCai; }

    public UDI getIdUdi() { return idUdi; }

    public void setIdUdi(UDI idUdi) { this.idUdi = idUdi;}

    public Usuario getIdUsuario() { return idUsuario; }

    public void setIdUsuario(Usuario idUsuario) { this.idUsuario = idUsuario; }
}

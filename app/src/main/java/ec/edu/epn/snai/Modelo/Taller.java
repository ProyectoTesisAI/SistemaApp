package ec.edu.epn.snai.Modelo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

public class Taller implements Serializable {

    @SerializedName("idTaller")
    @Expose
    private Integer idTaller;

    @SerializedName("tema")
    @Expose
    private String tema;

    @SerializedName("numeroTaller")
    @Expose
    private Integer numeroTaller;

    @SerializedName("fecha")
    @Expose
    private Date fecha;

    @SerializedName("horaInicio")
    @Expose
    private Date horaInicio;

    @SerializedName("objetivo")
    @Expose
    private String objetivo;

    @SerializedName("numeroTotalParticipantes")
    @Expose
    private Integer numeroTotalParticipantes;

    @SerializedName("recomendaciones")
    @Expose
    private String recomendaciones;

    @SerializedName("tipo")
    @Expose
    private String tipo;

    @SerializedName("idCai")
    @Expose
    private CAI idCai;

    @SerializedName("idUdi")
    @Expose
    private UDI idUdi;

    @SerializedName("idUsuario")
    @Expose
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

    public CAI getIdCai() {
        return idCai;
    }

    public void setIdCai(CAI idCai) {
        this.idCai = idCai;
    }

    public UDI getIdUdi() {
        return idUdi;
    }

    public void setIdUdi(UDI idUdi) {
        this.idUdi = idUdi;
    }

    public Usuario getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Usuario idUsuario) {
        this.idUsuario = idUsuario;
    }

    @Override
    public String toString() {
        return "Taller{" +
                "idTaller=" + idTaller +
                ", tema='" + tema + '\'' +
                ", numeroTaller=" + numeroTaller +
                ", fecha=" + fecha +
                ", horaInicio=" + horaInicio +
                ", objetivo='" + objetivo + '\'' +
                ", numeroTotalParticipantes=" + numeroTotalParticipantes +
                ", recomendaciones='" + recomendaciones + '\'' +
                ", tipo='" + tipo + '\'' +
                ", idCai=" + idCai +
                ", idUdi=" + idUdi +
                ", idUsuario=" + idUsuario +
                '}';
    }
}

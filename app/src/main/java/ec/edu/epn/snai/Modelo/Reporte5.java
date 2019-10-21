package ec.edu.epn.snai.Modelo;

import java.util.Date;

public class Reporte5 {
    private Integer numero;
    private String cai_uzdi;
    private String nombres;
    private String apellidos;
    private Date fechaAprehension;
    private Integer tiempoSetenciaMedida;
    private Date fechaCumplimiento60;
    private Date fechaCumplimiento80;
    private Double porcentajeCumplimiento;

    public Reporte5() {
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public String getCai_uzdi() {
        return cai_uzdi;
    }

    public void setCai_uzdi(String cai_uzdi) {
        this.cai_uzdi = cai_uzdi;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public Date getFechaAprehension() {
        return fechaAprehension;
    }

    public void setFechaAprehension(Date fechaAprehension) {
        this.fechaAprehension = fechaAprehension;
    }

    public Integer getTiempoSetenciaMedida() {
        return tiempoSetenciaMedida;
    }

    public void setTiempoSetenciaMedida(Integer tiempoSetenciaMedida) {
        this.tiempoSetenciaMedida = tiempoSetenciaMedida;
    }

    public Date getFechaCumplimiento60() {
        return fechaCumplimiento60;
    }

    public void setFechaCumplimiento60(Date fechaCumplimiento60) {
        this.fechaCumplimiento60 = fechaCumplimiento60;
    }

    public Date getFechaCumplimiento80() {
        return fechaCumplimiento80;
    }

    public void setFechaCumplimiento80(Date fechaCumplimiento80) {
        this.fechaCumplimiento80 = fechaCumplimiento80;
    }

    public Double getPorcentajeCumplimiento() {
        return porcentajeCumplimiento;
    }

    public void setPorcentajeCumplimiento(Double porcentajeCumplimiento) {
        this.porcentajeCumplimiento = porcentajeCumplimiento;
    }

    @Override
    public String toString() {
        return "Reporte5{" + "numero=" + numero + ", cai_uzdi=" + cai_uzdi + ", nombres=" + nombres + ", apellidos=" + apellidos + ", fechaAprehension=" + fechaAprehension + ", tiempoSetenciaMedida=" + tiempoSetenciaMedida + ", fechaCumplimiento60=" + fechaCumplimiento60 + ", fechaCumplimiento80=" + fechaCumplimiento80 + ", porcentajeCumplimiento=" + porcentajeCumplimiento + '}';
    }    
    
}

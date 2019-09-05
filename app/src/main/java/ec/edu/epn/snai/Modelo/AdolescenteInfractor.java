package ec.edu.epn.snai.Modelo;

import java.io.Serializable;
import java.util.Date;

public class AdolescenteInfractor implements Serializable {

    private Integer idAdolescenteInfractor;
    private String nombres;
    private String apellidos;
    private String nacionalidad;
    private String cedula;
    private String documento;
    private String genero;
    private String etnia;
    private Boolean registroSocial;
    private String estadoCivil;
    private Date fechaNacimiento;
    private Integer numeroHijos;
    private String tipo;

    public Integer getIdAdolescenteInfractor() {
        return idAdolescenteInfractor;
    }

    public void setIdAdolescenteInfractor(Integer idAdolescenteInfractor) {
        this.idAdolescenteInfractor = idAdolescenteInfractor;
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

    public String getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getEtnia() {
        return etnia;
    }

    public void setEtnia(String etnia) {
        this.etnia = etnia;
    }

    public Boolean getRegistroSocial() {
        return registroSocial;
    }

    public void setRegistroSocial(Boolean registroSocial) {
        this.registroSocial = registroSocial;
    }

    public String getEstadoCivil() {
        return estadoCivil;
    }

    public void setEstadoCivil(String estadoCivil) {
        this.estadoCivil = estadoCivil;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public Integer getNumeroHijos() {
        return numeroHijos;
    }

    public void setNumeroHijos(Integer numeroHijos) {
        this.numeroHijos = numeroHijos;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    @Override
    public String toString() {
        return "AdolescenteInfractor{" +
                "idAdolescenteInfractor=" + idAdolescenteInfractor +
                ", nombres='" + nombres + '\'' +
                ", apellidos='" + apellidos + '\'' +
                ", nacionalidad='" + nacionalidad + '\'' +
                ", cedula='" + cedula + '\'' +
                ", documento='" + documento + '\'' +
                ", genero='" + genero + '\'' +
                ", etnia='" + etnia + '\'' +
                ", registroSocial=" + registroSocial +
                ", estadoCivil='" + estadoCivil + '\'' +
                ", fechaNacimiento=" + fechaNacimiento +
                ", numeroHijos=" + numeroHijos +
                ", tipo='" + tipo + '\'' +
                '}';
    }
}

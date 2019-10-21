package ec.edu.epn.snai.Modelo;

public class Reporte4 {
    private Integer numero;
    private String cai_uzdi;
    private String nombres;
    private String apellidos;
    private String tipoDelito;
    private String medidaSocioeducativa;

    public Reporte4() {
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

    public String getTipoDelito() {
        return tipoDelito;
    }

    public void setTipoDelito(String tipoDelito) {
        this.tipoDelito = tipoDelito;
    }

    public String getMedidaSocioeducativa() {
        return medidaSocioeducativa;
    }

    public void setMedidaSocioeducativa(String medidaSocioeducativa) {
        this.medidaSocioeducativa = medidaSocioeducativa;
    }

    @Override
    public String toString() {
        return "Reporte4{" + "numero=" + numero + ", cai_uzdi=" + cai_uzdi + ", nombres=" + nombres + ", apellidos=" + apellidos + ", tipoDelito=" + tipoDelito + ", medidaSocioeducativa=" + medidaSocioeducativa + '}';
    }
    
    
}

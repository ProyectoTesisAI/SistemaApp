package ec.edu.epn.snai.Modelo;

public class Reporte1 {
    private Integer numero;
    private String cai_uzdi;
    private String nombres;
    private String apellidos;
    private String tipoDelto;

    public Reporte1() {
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

    public String getTipoDelto() {
        return tipoDelto;
    }

    public void setTipoDelto(String tipoDelto) {
        this.tipoDelto = tipoDelto;
    }

    @Override
    public String toString() {
        return "Reporte1{" + "numero=" + numero + ", cai_uzdi=" + cai_uzdi + ", nombres=" + nombres + ", apellidos=" + apellidos + ", tipoDelto=" + tipoDelto + '}';
    }
    
    
}

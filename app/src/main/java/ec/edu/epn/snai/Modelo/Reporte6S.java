package ec.edu.epn.snai.Modelo;

public class Reporte6S {
    private Integer numero;
    private String cai_uzdi;
    private String nombres;
    private String apellidos;
    private Integer edad;
    private Boolean estudia;
    private String nivelEducativo;

    public Reporte6S() {
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

    public Integer getEdad() {
        return edad;
    }

    public void setEdad(Integer edad) {
        this.edad = edad;
    }

    public Boolean getEstudia() {
        return estudia;
    }

    public void setEstudia(Boolean estudia) {
        this.estudia = estudia;
    }

    public String getNivelEducativo() {
        return nivelEducativo;
    }

    public void setNivelEducativo(String nivelEducativo) {
        this.nivelEducativo = nivelEducativo;
    }

    @Override
    public String toString() {
        return "Reporte6{" + "numero=" + numero + ", cai_uzdi=" + cai_uzdi + ", nombres=" + nombres + ", apellidos=" + apellidos + ", estudia=" + estudia + ", nivelEducativo=" + nivelEducativo + '}';
    }
    
}

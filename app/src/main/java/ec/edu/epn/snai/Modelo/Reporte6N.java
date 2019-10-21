package ec.edu.epn.snai.Modelo;

public class Reporte6N {
    private Integer numero;
    private String cai_uzdi;
    private String nombres;
    private String apellidos;
    private Integer edad;
    private Boolean estudia;
    private String razonNoEstudia;

    public Reporte6N() {
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

    public String getRazonNoEstudia() {
        return razonNoEstudia;
    }

    public void setRazonNoEstudia(String razonNoEstudia) {
        this.razonNoEstudia = razonNoEstudia;
    }

    @Override
    public String toString() {
        return "Reporte7{" + "numero=" + numero + ", cai_uzdi=" + cai_uzdi + ", nombres=" + nombres + ", apellidos=" + apellidos + ", estudia=" + estudia + ", razonNoEstudia=" + razonNoEstudia + '}';
    }

    
}

package ec.edu.epn.snai.Modelo;

public class Reporte2 {
    private Integer numero;
    private String cai_uzdi;
    private String nombres;
    private String apellidos;
    private String genero;
    private Integer edad;

    public Reporte2() {
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

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public Integer getEdad() {
        return edad;
    }

    public void setEdad(Integer edad) {
        this.edad = edad;
    }

    @Override
    public String toString() {
        return "Reporte2{" + "numero=" + numero + ", cai_uzdi=" + cai_uzdi + ", nombres=" + nombres + ", apellidos=" + apellidos + ", genero=" + genero + ", edad=" + edad + '}';
    }
    
    
    
}

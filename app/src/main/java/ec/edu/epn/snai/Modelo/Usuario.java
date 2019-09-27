package ec.edu.epn.snai.Modelo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Usuario implements Serializable {

    @SerializedName("idUsuario")
    @Expose
    private Integer idUsuario;

    @SerializedName("nombres")
    @Expose
    private String nombres;

    @SerializedName("apellidos")
    @Expose
    private String apellidos;

    @SerializedName("cedula")
    @Expose
    private String cedula;

    @SerializedName("telefono")
    @Expose
    private String telefono;

    @SerializedName("usuario")
    @Expose
    private String usuario;

    @SerializedName("contraseña")
    @Expose
    private String contraseña;

    @SerializedName("activo")
    @Expose
    private Boolean activo;

    @SerializedName("token")
    @Expose
    private String token;

    @SerializedName("idRolUsuarioCentro")
    @Expose
    private RolCentroUsuario idRolUsuarioCentro;

    public Usuario() {
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
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

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public RolCentroUsuario getIdRolUsuarioCentro() {
        return idRolUsuarioCentro;
    }

    public void setIdRolUsuarioCentro(RolCentroUsuario idRolUsuarioCentro) {
        this.idRolUsuarioCentro = idRolUsuarioCentro;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "idUsuario=" + idUsuario +
                ", nombres='" + nombres + '\'' +
                ", apellidos='" + apellidos + '\'' +
                ", cedula='" + cedula + '\'' +
                ", telefono='" + telefono + '\'' +
                ", usuario='" + usuario + '\'' +
                ", contraseña='" + contraseña + '\'' +
                ", activo=" + activo +
                ", token='" + token + '\'' +
                ", idRolUsuarioCentro=" + idRolUsuarioCentro +
                '}';
    }
}

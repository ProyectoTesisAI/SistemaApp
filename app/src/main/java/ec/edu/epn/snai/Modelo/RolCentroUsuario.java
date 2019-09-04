package ec.edu.epn.snai.Modelo;

import java.io.Serializable;

public class RolCentroUsuario implements Serializable {

    private Integer idRolUsuarioCentro;
    private CAI idCai;
    private UDI idUdi;
    private Rol idRol;
    
    

    public RolCentroUsuario() {
    }

    public Integer getIdRolUsuarioCentro() {
        return idRolUsuarioCentro;
    }

    public void setIdRolUsuarioCentro(Integer idRolUsuarioCentro) {
        this.idRolUsuarioCentro = idRolUsuarioCentro;
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

    public Rol getIdRol() {
        return idRol;
    }

    public void setIdRol(Rol idRol) {
        this.idRol = idRol;
    }

    
    
}

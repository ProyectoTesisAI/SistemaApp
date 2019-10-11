package ec.edu.epn.snai.Servicios;

import ec.edu.epn.snai.Modelo.RolCentroUsuario;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface RolCentroUsuarioServicio {

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("RolCentroUsuario/ObtenerRolAdministrativo")
    Call<RolCentroUsuario> obtenerRolAdministrativo(@Body RolCentroUsuario rolCentroUsuario, @Header("Authorization") String token);

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("RolCentroUsuario/ObtenerRolSoloUDI")
    Call<RolCentroUsuario> obtenerRolSoloUDI(@Body RolCentroUsuario rolCentroUsuario, @Header("Authorization") String token);

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("RolCentroUsuario/ObtenerRolSoloCAI")
    Call<RolCentroUsuario> obtenerRolSoloCAI(@Body RolCentroUsuario rolCentroUsuario, @Header("Authorization") String token);

}

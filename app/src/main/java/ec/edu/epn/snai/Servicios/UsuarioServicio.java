package ec.edu.epn.snai.Servicios;

import ec.edu.epn.snai.Modelo.Usuario;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface UsuarioServicio {

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("Usuario/login")
    Call<Usuario> login(@Body Usuario usuario);

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("Usuario")
    Call<Usuario> crearUsuario(@Body Usuario usuario,@Header("Authorization") String token);

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @GET("Usuario/UsuariosActivos")
    Call<List<Usuario>> obtenerUsuariosActivos(@Header("Authorization") String token);

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @GET("Usuario/UsuariosDesactivados")
    Call<List<Usuario>> obtenerUsuariosDesactivados(@Header("Authorization") String token);

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @PUT("Usuario")
    Call<Usuario> activarUsuario(@Body Usuario usuario,@Header("Authorization") String token);

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @PUT("Usuario")
    Call<Usuario> desactivarUsuario(@Body Usuario usuario,@Header("Authorization") String token);
}

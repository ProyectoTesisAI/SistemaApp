package ec.edu.epn.snai.Servicios;

import ec.edu.epn.snai.Modelo.Usuario;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface UsuarioServicio {

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("Usuario/login")
    Call<Usuario> login(@Body Usuario usuario);
}

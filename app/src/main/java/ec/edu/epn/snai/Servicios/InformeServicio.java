package ec.edu.epn.snai.Servicios;

import ec.edu.epn.snai.Modelo.Informe;
import ec.edu.epn.snai.Modelo.Usuario;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface InformeServicio {


    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @PUT("Informe")
    Call<Informe> editarInforme(@Body Informe informe, @Header("Authorization") String token);

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @GET("Informe")
    Call<List<Informe>> obtenerTodosLosInformes(@Header("Authorization") String token);

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("Informe/InformesPorUsuario")
    Call<List<Informe>> obtenerInformesPorUsuario(@Body Usuario usuario, @Header("Authorization") String token);

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @GET("Informe/InformeSoloUZDI")
    Call<List<Informe>> obtenerInformesSoloUzdi(@Header("Authorization") String token);

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @GET("Informe/InformeSoloCAI")
    Call<List<Informe>> obtenerInformesSoloCai(@Header("Authorization") String token);

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @DELETE("Informe/{id}")
    Call<Void> eliminarInforme(@Path("id") String id,@Header("Authorization") String token);


}

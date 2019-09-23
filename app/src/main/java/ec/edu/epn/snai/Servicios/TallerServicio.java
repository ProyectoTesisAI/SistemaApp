package ec.edu.epn.snai.Servicios;

import ec.edu.epn.snai.Modelo.CAI;
import ec.edu.epn.snai.Modelo.ItemTaller;
import ec.edu.epn.snai.Modelo.Taller;
import ec.edu.epn.snai.Modelo.UDI;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface TallerServicio {

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @GET("Taller")
    Call<List<Taller>> obtenerTalleres(@Header("Authorization") String token);

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("Taller/NumeroAdolescentesPorUzdi")
    Call<String> obtenerNumeroParticipantesUZDI(@Body UDI udi, @Header("Authorization") String token);

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("Taller/NumeroAdolescentesPorCai")
    Call<String> obtenerNumeroParticipantesCAI(@Body CAI cai,@Header("Authorization") String token);

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @GET("Taller/ItemsTaller/{id}")
    Call<List<ItemTaller>> listarItemsPorTaller(@Path("id") String id, @Header("Authorization") String token);

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @PUT("Taller")
    Call<Taller> editarTaller(@Body Taller taller,@Header("Authorization") String token);

}

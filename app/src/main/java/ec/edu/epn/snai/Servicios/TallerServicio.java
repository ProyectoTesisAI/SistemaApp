package ec.edu.epn.snai.Servicios;

import ec.edu.epn.snai.Modelo.Taller;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;

import java.util.List;

public interface TallerServicio {

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @GET("Taller")
    Call<List<Taller>> obtenerTalleres(@Header("Authorization") String token);
}

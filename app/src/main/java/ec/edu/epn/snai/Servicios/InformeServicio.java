package ec.edu.epn.snai.Servicios;

import ec.edu.epn.snai.Modelo.Informe;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;

import java.util.List;

public interface InformeServicio {

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @GET("Informe")
    Call<List<Informe>> obtenerInformes(@Header("Authorization") String token);
}

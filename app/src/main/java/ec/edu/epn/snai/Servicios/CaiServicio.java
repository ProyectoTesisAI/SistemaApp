package ec.edu.epn.snai.Servicios;

import ec.edu.epn.snai.Modelo.CAI;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;

import java.util.List;

public interface CaiServicio {

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @GET("Cai")
    Call<List<CAI>> obtenerListaCAI(@Header("Authorization") String token);
}

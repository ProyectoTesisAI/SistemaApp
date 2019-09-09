package ec.edu.epn.snai.Servicios;

import ec.edu.epn.snai.Modelo.UDI;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;

import java.util.List;

public interface UzdiServicio {

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @GET("Udi")
    Call<List<UDI>> obtenerListaUZDI(@Header("Authorization") String token);
}

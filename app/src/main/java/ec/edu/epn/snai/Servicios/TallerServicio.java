package ec.edu.epn.snai.Servicios;

import ec.edu.epn.snai.Modelo.Taller;
import retrofit2.Call;
import retrofit2.http.GET;

import java.util.List;

public interface TallerServicio {

    @GET("Taller")
    Call<List<Taller>> obtenerTalleres();
}

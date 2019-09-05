package ec.edu.epn.snai.Servicios;

import ec.edu.epn.snai.Modelo.Informe;
import retrofit2.Call;
import retrofit2.http.GET;

import java.util.List;

public interface InformeServicio {

    @GET("Informe")
    Call<List<Informe>> obtenerInformes();
}

package ec.edu.epn.snai.Servicios;

import ec.edu.epn.snai.Modelo.*;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface RegistroFotograficoServicio {

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @PUT("Registro_Fotografico")
    Call<RegistroFotografico> guardarRegistroFotografico(@Body RegistroFotografico registro, @Header("Authorization") String token);

    @Headers({ "Content-Type: application/octet-stream;charset=UTF-8"})
    @GET("Registro_Fotografico/Informe/Movil/{id}")
    Call<List<RegistroFotografico>> obtenerRegistroFotograficoPorInforme(@Path("id") String id, @Header("Authorization") String token);
}

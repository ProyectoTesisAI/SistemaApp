package ec.edu.epn.snai.Servicios;

import ec.edu.epn.snai.Modelo.*;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface RegistroAsistenciaServicio {

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("ListaAdolescentesPorUzdi")
    Call<List<AdolescenteInfractor>> listaAdolescentesInfractoresPorUzdi(@Body UDI udi, @Header("Authorization") String token);

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("ListaAdolescentesPorCai")
    Call<List<AdolescenteInfractor>> listaAdolescentesInfractoresPorUzdi(@Body CAI cai, @Header("Authorization") String token);

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("ListaAdolescentesPorTaller")
    Call<List<AdolescenteInfractor>> listaAdolescentesInfractoresPorTaller(@Body Taller taller, @Header("Authorization") String token);

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @GET("Taller/NumeroAsistentes/{id}")
    Call<Integer> obtenerNumeroAdolescentesPorTaller(@Path("id") Integer idTaller, @Header("Authorization") String token);
}

package ec.edu.epn.snai.Servicios;

import ec.edu.epn.snai.Modelo.AsistenciaAdolescente;
import retrofit2.Call;
import retrofit2.http.*;

public interface AsistenciaAdolescenteServicio {

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @PUT("Registro_Asistencia_Adolescente")
    Call<AsistenciaAdolescente> guardarAsistenciaAdolescente(@Body AsistenciaAdolescente asistenciaAdolescente, @Header("Authorization") String token);
}

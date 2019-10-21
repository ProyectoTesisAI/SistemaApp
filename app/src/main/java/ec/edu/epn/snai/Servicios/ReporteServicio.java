package ec.edu.epn.snai.Servicios;

import ec.edu.epn.snai.Modelo.DatosTipoPenalCAI;
import ec.edu.epn.snai.Modelo.Reporte1;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface ReporteServicio {

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @GET("Datos_Tipo_Penal_Cai")
    Call<List<DatosTipoPenalCAI>> obtenerDatosTipoPenal(@Header("Authorization") String token);

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("Adolescente_Infractor/reporteTipoDelitoUDI")
    Call<List<Reporte1>> obtenerReporteTipoDelitoUZDI(@Body Reporte1 reporte1, @Header("Authorization") String token);
}

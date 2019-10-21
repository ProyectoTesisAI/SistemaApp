package ec.edu.epn.snai.Servicios;

import ec.edu.epn.snai.Modelo.DatosTipoPenalCAI;
import ec.edu.epn.snai.Modelo.Reporte1;
import ec.edu.epn.snai.Modelo.Reporte2;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.Date;
import java.util.List;

public interface ReporteServicio {

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @GET("Datos_Tipo_Penal_Cai")
    Call<List<DatosTipoPenalCAI>> obtenerDatosTipoPenal(@Header("Authorization") String token);

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("Adolescente_Infractor/reporteTipoDelitoUDI")
    Call<List<Reporte1>> obtenerReporteTipoDelitoUZDI(@Body Reporte1 reporte1, @Header("Authorization") String token);

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("Adolescente_Infractor/reporteTipoDelitoCAI")
    Call<List<Reporte1>> obtenerReporteTipoDelitoCAI(@Body Reporte1 reporte1, @Header("Authorization") String token);

    @POST("Adolescente_Infractor/reporteEdadCAI")
    Call<List<Reporte2>> obtenerReporteEdadCAI(@Body Reporte2 reporte2, @Header("Authorization") String token);

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("Adolescente_Infractor/reporteEdadUDI")
    Call<List<Reporte2>> obtenerReporteEdadUDI(@Body Reporte2 reporte2, @Header("Authorization") String token);

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("Adolescente_Infractor//reporteEdadFechaUDI")
    Call<List<Reporte2>> obtenerReporteEdadFechaUZDI(@Body Date fechaFutura, @Header("Authorization") String token);

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("Adolescente_Infractor//reporteEdadFechaCAI")
    Call<List<Reporte2>> obtenerReporteEdadFechaCAI(@Body Date fechaFutura, @Header("Authorization") String token);
}
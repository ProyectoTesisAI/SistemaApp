package ec.edu.epn.snai.Servicios;

import ec.edu.epn.snai.Modelo.ItemTaller;
import retrofit2.Call;
import retrofit2.http.*;

public interface ItemTallerServicio {

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @PUT("Item_Taller")
    Call<ItemTaller> editarItemTaller(@Body ItemTaller itemTaller, @Header("Authorization") String token);

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @DELETE("Item_Taller/{id}")
    Call<Void> eliminarItemTaller(@Path("id") Integer itemTaller, @Header("Authorization") String token);

}

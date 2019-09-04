package ec.edu.epn.snai.Servicios;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ClienteApiRest {

    private static Retrofit retrofit;
    private static final String BASE_URL = "https://programacion.epn.edu.ec/SistemaSNAI_Servidor/webresources/"; //"https://172.29.177.245:8181/SistemaSNAI_Servidor/webresources/"; /

    public static Retrofit getRetrofitInstance() {

        OkHttpClient okHttpClient = HttpCliente.permitirCertificadoAutofirmado();

        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}

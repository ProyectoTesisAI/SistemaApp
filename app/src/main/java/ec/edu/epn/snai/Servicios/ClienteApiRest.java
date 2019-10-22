package ec.edu.epn.snai.Servicios;

import com.google.gson.*;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ClienteApiRest {

    private static Retrofit retrofit;
    private static final String BASE_URL = "https://172.29.189.222:8181/SistemaSNAI_Servidor/webresources/"; //172.29.189.222:8181/SistemaSNAI_Servidor/webresources/"; /

    public static Retrofit getRetrofitInstance() {

        OkHttpClient okHttpClient = HttpCliente.permitirCertificadoAutofirmado();


        Gson gson = new  GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss-05:00")
                .setLenient().create();

        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().serializeNulls().create()))
                    .build();
        }
        return retrofit;
    }

}

package ec.edu.epn.snai.Servicios;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import okhttp3.*;

import java.security.cert.CertificateException;
import javax.net.ssl.*;

public class Conexion<T> {

    private OkHttpClient cliente;

    String url = "https://172.29.188.103:8181/SistemaSNAI_Servidor/webresources/Taller";

    public Conexion(){

        try {
            // Create a trust manager that does not validate certificate chains
            final TrustManager[] trustAllCerts = new TrustManager[]{
                new X509TrustManager() {
                    @Override
                    public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                    }

                    @Override
                    public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                    }

                    @Override
                    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                        return new java.security.cert.X509Certificate[]{};
                    }
                }
            };

            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());

            // Create an ssl socket factory with our all-trusting manager
            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.sslSocketFactory(sslSocketFactory, (X509TrustManager) trustAllCerts[0]);
            builder.hostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });

            cliente = builder.build();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Response conexion(String URL, String tipoPeticion, String token, T informacionAEnviar) {

        //Creo un objeto que contondrá las características de salida en formato json
        Gson json = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss-05:00").create();
        String informacionJson = json.toJson(informacionAEnviar);

        Request request= null;

        if(tipoPeticion == "POST"){

            //objeto que co
            request = new Request.Builder()
                    .url(URL)
                    .addHeader("Authorization", "Bearer " + token)
                    .post(RequestBody.create(MediaType.parse("application/json"), informacionJson))
                    .build();
        }
        else if(tipoPeticion == "PUT"){

            //añado headers en la petición
            request = new Request.Builder()
                    .url(URL)
                    .addHeader("Authorization", "Bearer " + token)
                    .put(RequestBody.create(MediaType.parse("application/json"), informacionJson))
                    .build();
        }

        try {

            //ejecuta el método de forma sincrónica, es decir espera a que finaliza la ejecución del método
            Response response = cliente.newCall(request).execute();
            return response;

        } catch (Exception e) {
            return null;
        }
    }
}

package ec.edu.epn.snai.Controlador.Fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import ec.edu.epn.snai.Controlador.Adaptador.RegistroAsistenciaAdaptador
import ec.edu.epn.snai.Modelo.AsistenciaAdolescente
import ec.edu.epn.snai.Modelo.Informe
import ec.edu.epn.snai.R
import ec.edu.epn.snai.Servicios.ClienteApiRest
import ec.edu.epn.snai.Servicios.RegistroAsistenciaServicio
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class VerEditarRegistroAsistenciaFragment: Fragment(){

    private var listaAdolescentesInfractores: List<AsistenciaAdolescente>?=null

    private lateinit var informeSeleccionado: Informe
    private lateinit var token:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(arguments!=null){
            informeSeleccionado = arguments?.getSerializable("informeSeleccionado") as Informe
            token=arguments?.getSerializable("token") as String
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView=inflater.inflate(R.layout.fragment_ver_registro_asistencia,container,false)
        /*CONSUMO DEL SERVICIO WEB Y ASIGNARLO EN EL RECYCLERVIEW*/
        val servicio = ClienteApiRest.getRetrofitInstance().create(RegistroAsistenciaServicio::class.java)

        if(informeSeleccionado.idTaller!=null ){
            val call = servicio.listaAdolescentesInfractoresPorTaller(informeSeleccionado.idTaller,"Bearer "+ token)
            call.enqueue(object : Callback<List<AsistenciaAdolescente>> {
                override fun onResponse(call: Call<List<AsistenciaAdolescente>>, response: Response<List<AsistenciaAdolescente>>) {
                    if (response.isSuccessful) {
                        listaAdolescentesInfractores = response.body()

                        if(listaAdolescentesInfractores!=null){
                            var recyclerViewRegistroAsistencia=rootView.findViewById(R.id.rv_registro_asistencia) as RecyclerView
                            var adaptador = RegistroAsistenciaAdaptador(listaAdolescentesInfractores)
                            recyclerViewRegistroAsistencia.adapter=adaptador
                            recyclerViewRegistroAsistencia.layoutManager = LinearLayoutManager(context,LinearLayout.VERTICAL,false)
                        }
                    }
                }

                override fun onFailure(call: Call<List<AsistenciaAdolescente>>, t: Throwable) {
                    call.cancel()
                }
            })
        }
        return rootView
    }

}
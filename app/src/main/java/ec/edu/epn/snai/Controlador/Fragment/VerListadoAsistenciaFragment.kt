package ec.edu.epn.snai.Controlador.Fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ec.edu.epn.snai.Controlador.Adaptador.ListaAsistenciaAdaptador
import ec.edu.epn.snai.Modelo.AsistenciaAdolescente
import ec.edu.epn.snai.Modelo.Taller
import ec.edu.epn.snai.R
import ec.edu.epn.snai.Servicios.ClienteApiRest
import ec.edu.epn.snai.Servicios.RegistroAsistenciaServicio
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class VerListadoAsistenciaFragment: Fragment() {

    private var listaAsistenciaAdolescentesInfractores: List<AsistenciaAdolescente>?=null

    private lateinit var tallerActual: Taller
    private lateinit var token:String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if(arguments!=null){
            token=arguments?.getSerializable("token") as String
            tallerActual=arguments?.getSerializable("taller_seleccionado") as Taller
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_ver_listado_asistencia, container, false)

        /*CONSUMO DEL SERVICIO WEB Y ASIGNARLO EN EL RECYCLERVIEW*/
        val servicio = ClienteApiRest.getRetrofitInstance().create(RegistroAsistenciaServicio::class.java)

        if(tallerActual != null ){
            val call = servicio.listaAdolescentesInfractoresPorTaller(tallerActual,"Bearer "+ token)
            call.enqueue(object : Callback<List<AsistenciaAdolescente>> {
                override fun onResponse(call: Call<List<AsistenciaAdolescente>>, response: Response<List<AsistenciaAdolescente>>) {
                    if (response.isSuccessful) {

                        listaAsistenciaAdolescentesInfractores = response.body()

                        if(listaAsistenciaAdolescentesInfractores!=null){
                            mostrarListadoAsistencia(rootView!!)
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

    fun mostrarListadoAsistencia(view: View){

        if(listaAsistenciaAdolescentesInfractores?.size!! > 0){
            val recyclerViewRegistroAsistencia=view.findViewById(R.id.rv_listado_asistencia) as RecyclerView
            val adaptador = ListaAsistenciaAdaptador(listaAsistenciaAdolescentesInfractores)
            recyclerViewRegistroAsistencia.adapter=adaptador
            recyclerViewRegistroAsistencia.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
        }

    }
}
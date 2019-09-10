package ec.edu.epn.snai.Controlador.Fragment

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ec.edu.epn.snai.Controlador.Adaptador.RegistroAsistenciaAdaptador
import ec.edu.epn.snai.Controlador.Activity.VerTallerActivity
import ec.edu.epn.snai.Controlador.Activity.TallerAgregarActivity
import ec.edu.epn.snai.Modelo.*
import ec.edu.epn.snai.R
import ec.edu.epn.snai.Servicios.ClienteApiRest
import ec.edu.epn.snai.Servicios.RegistroAsistenciaServicio
import ec.edu.epn.snai.Servicios.TallerServicio
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegistroAsistenciaFragment: Fragment(){
//class RegistroAsistenciaFragment: Fragment(), RegistroAsistenciaAdaptador.RegistroAsistenciaOnItemClickListener{

//    private var adaptador: RegistroAsistenciaAdaptador? = null
//    private var listaAdolescentesInfractores: List<AdolescenteInfractor>?=null
//    private lateinit var recyclerViewRegistroAsistencia: RecyclerView
//    private lateinit var token:String
//
//    private var taller : Taller?=null
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//        if(arguments!=null){
//            token=arguments?.getSerializable("token") as String
//            println("Fragment de RA"+token)
//        }
//    }
//
//    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
//        val rootView= inflater.inflate(R.layout.fragment_registro_asistencia,container,false)
//
//        /*CONSUMO DEL SERVICIO WEB Y ASIGNARLO EN EL RECYCLERVIEW*/
//        val servicio = ClienteApiRest.getRetrofitInstance().create(RegistroAsistenciaServicio::class.java)
//
//        val call = servicio.listaAdolescentesInfractoresPorTaller(taller,"Bearer "+ token)
//        println("se ejecuto el servicio")
//        call.enqueue(object : Callback<List<AdolescenteInfractor>> {
//
//            override fun onResponse(call: Call<List<AdolescenteInfractor>>, response: Response<List<AdolescenteInfractor>>) {
//
//                if (response.isSuccessful) {
//
//                    listaAdolescentesInfractores = response.body()
//
//                    adaptador = RegistroAsistenciaAdaptador(listaAdolescentesInfractores, this@RegistroAsistenciaFragment)
//                    recyclerViewRegistroAsistencia =rootView.findViewById (R.id.rv_registro_asistencia) as RecyclerView
//                    recyclerViewRegistroAsistencia.adapter=adaptador
//                    recyclerViewRegistroAsistencia.layoutManager=LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
//
//                }
//
//            }
//
//            override fun onFailure(call: Call<List<AdolescenteInfractor>>, t: Throwable) {
//                call.cancel()
//
//            }
//        })
//
//        return rootView
//    }
//
//    override fun OnItemClick(posicion: Int) {
//
//        val intent = Intent(context, VerTallerActivity::class.java)
//        intent.putExtra("taller_seleccionado", listaAdolescentesInfractores?.get(posicion))
//        startActivity(intent)
//    }

}
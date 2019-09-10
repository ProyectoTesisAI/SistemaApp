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
import ec.edu.epn.snai.Controlador.Adaptador.TallerAdaptador
import ec.edu.epn.snai.Controlador.Activity.VerTallerActivity
import ec.edu.epn.snai.Controlador.Activity.TallerAgregarActivity
import ec.edu.epn.snai.Modelo.Taller
import ec.edu.epn.snai.Modelo.Usuario
import ec.edu.epn.snai.R
import ec.edu.epn.snai.Servicios.ClienteApiRest
import ec.edu.epn.snai.Servicios.TallerServicio
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TalleresFragment: Fragment(), TallerAdaptador.TallerOnItemClickListener{

    private var adaptador: TallerAdaptador? = null
    private var listaTalleres: List<Taller>?=null
    private lateinit var recyclerViewTaller: RecyclerView
    private lateinit var token:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if(arguments!=null){
            token=arguments?.getSerializable("token") as String
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView= inflater.inflate(R.layout.fragment_talleres,container,false)

        /*CONSUMO DEL SERVICIO WEB Y ASIGNARLO EN EL RECYCLERVIEW*/
        val servicio = ClienteApiRest.getRetrofitInstance().create(TallerServicio::class.java)

        val call = servicio.obtenerTalleres("Bearer "+ token)

        call.enqueue(object : Callback<List<Taller>> {

            override fun onResponse(call: Call<List<Taller>>, response: Response<List<Taller>>) {

                if (response.isSuccessful) {

                    listaTalleres = response.body()

                    adaptador = TallerAdaptador(listaTalleres, this@TalleresFragment)
                    recyclerViewTaller =rootView.findViewById (R.id.rv_taller) as RecyclerView
                    recyclerViewTaller.adapter=adaptador
                    recyclerViewTaller.layoutManager=LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)

                }

            }

            override fun onFailure(call: Call<List<Taller>>, t: Throwable) {
                call.cancel()

            }
        })


        val fabTaller:FloatingActionButton= rootView.findViewById(R.id.fab_agregar_taller)
        fabTaller.setOnClickListener {

            val intent = Intent(context, TallerAgregarActivity::class.java)
            intent.putExtra("token",token)
            startActivity(intent)
        }

        return rootView
    }

    override fun OnItemClick(posicion: Int) {

        val intent = Intent(context, VerTallerActivity::class.java)
        intent.putExtra("taller_seleccionado", listaTalleres?.get(posicion))
        intent.putExtra("token", token)
        startActivity(intent)
    }

}
package ec.edu.epn.snai.Controlador.Fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ec.edu.epn.snai.Controlador.Adaptador.RegistroFotograficoAdaptador
import ec.edu.epn.snai.Modelo.Informe
import ec.edu.epn.snai.Modelo.RegistroFotografico
import ec.edu.epn.snai.R
import ec.edu.epn.snai.Servicios.ClienteApiRest
import ec.edu.epn.snai.Servicios.RegistroFotograficoServicio
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class VerEditarFotografiasFragment : Fragment(){

    private lateinit var informeSeleccionado: Informe
    private lateinit var token:String

    private var adaptadorItemRegistroFotografico: RegistroFotograficoAdaptador?=null
    var itemsFotografias: List<RegistroFotografico>?=null
    private lateinit var recyclerViewItemsFotografias: RecyclerView

    private lateinit var rootView: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(arguments!=null){
            token =arguments?.getSerializable("token") as String
            informeSeleccionado = arguments?.getSerializable("informeSeleccionado") as Informe
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootView=inflater.inflate(R.layout.fragment_ver_editar_fotografias,container,false)
        itemsFotografias=ArrayList<RegistroFotografico>()
        val servicio = ClienteApiRest.getRetrofitInstance().create(RegistroFotograficoServicio::class.java)

        if(informeSeleccionado!=null ){
            val call = servicio.obtenerRegistroFotograficoPorInforme(informeSeleccionado.idInforme.toString(),"Bearer "+ token)
            call.enqueue(object : Callback<List<RegistroFotografico>> {
                override fun onFailure(call: Call<List<RegistroFotografico>>, t: Throwable) {
                    call.cancel()
                }

                override fun onResponse(call: Call<List<RegistroFotografico>>, response: Response<List<RegistroFotografico>>) {
                    if (response.isSuccessful) {
                        itemsFotografias = response.body()
                        println("fotografias en on reponsoe: ")
                        adaptadorItemRegistroFotografico= RegistroFotograficoAdaptador(itemsFotografias)
                        recyclerViewItemsFotografias= rootView.findViewById<RecyclerView>(R.id.rv_ver_imagenes)
                        recyclerViewItemsFotografias.adapter=adaptadorItemRegistroFotografico
                        recyclerViewItemsFotografias.layoutManager=
                            LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
                    }
                }
            })
        }
        return rootView
    }
}


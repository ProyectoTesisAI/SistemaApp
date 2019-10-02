package ec.edu.epn.snai.Controlador.Fragment

import android.os.AsyncTask
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ec.edu.epn.snai.Controlador.Adaptador.RegistroFotograficoAdaptador
import ec.edu.epn.snai.Modelo.Informe
import ec.edu.epn.snai.Modelo.RegistroFotografico
import ec.edu.epn.snai.R
import ec.edu.epn.snai.Servicios.ClienteApiRest
import ec.edu.epn.snai.Servicios.RegistroFotograficoServicio
import kotlinx.android.synthetic.main.fragment_ver_editar_fotografias.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class VerRegistroFotograficoFragment : Fragment(){

    private lateinit var informeSeleccionado: Informe
    private lateinit var token: String

    private lateinit var rootView: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if(arguments!=null){
            informeSeleccionado = arguments?.getSerializable("informeSeleccionado") as Informe
            token=arguments?.getSerializable("token") as String

        }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootView=inflater.inflate(R.layout.fragment_ver_editar_fotografias,container,false)
        rootView.pbCargando.visibility=View.VISIBLE
        obtenerRegistroFotografico()

        return rootView
    }

    private fun obtenerRegistroFotografico(){
        val servicio = ClienteApiRest.getRetrofitInstance().create(RegistroFotograficoServicio::class.java)
        val call = servicio.obtenerRegistroFotograficoPorInforme(informeSeleccionado.idInforme.toString(), "Bearer " + token)

        call.enqueue(object : Callback<List<RegistroFotografico>>{

            override fun onFailure(call: Call<List<RegistroFotografico>>, t: Throwable) {

                rootView.pbCargando.visibility=View.INVISIBLE

            }

            override fun onResponse(call: Call<List<RegistroFotografico>>, response: Response<List<RegistroFotografico>>) {

                if(response != null){

                    if(response.code()==200){
                        val listaRegistroFotografico=response.body()

                        if(listaRegistroFotografico != null){
                            asignarRegistroFotograficoRecyclerView(listaRegistroFotografico)

                        }


                    }
                }
                rootView.pbCargando.visibility=View.INVISIBLE
            }
        })
    }

    private fun asignarRegistroFotograficoRecyclerView(listaFotografias: List<RegistroFotografico> ){

        if(listaFotografias.size >0){
            val adaptadorItemRegistroFotografico= RegistroFotograficoAdaptador(listaFotografias)
            val recyclerViewItemsFotografias= rootView.findViewById<RecyclerView>(R.id.rv_ver_imagenes)
            recyclerViewItemsFotografias.adapter=adaptadorItemRegistroFotografico
            recyclerViewItemsFotografias.layoutManager= LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)

        }
    }
}


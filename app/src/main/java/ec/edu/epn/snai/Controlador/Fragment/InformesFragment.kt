package ec.edu.epn.snai.Controlador.Fragment

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ec.edu.epn.snai.Controlador.Activity.VerInformeActivity
import ec.edu.epn.snai.Controlador.Adaptador.InformeAdaptador
import ec.edu.epn.snai.Controlador.Adaptador.TallerAdaptador
import ec.edu.epn.snai.Modelo.Informe
import ec.edu.epn.snai.R
import ec.edu.epn.snai.Servicios.ClienteApiRest
import ec.edu.epn.snai.Servicios.InformeServicio
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class InformesFragment: Fragment(), InformeAdaptador.InformeOnItemClickListener{

    private var adaptador : InformeAdaptador?=null
    private var listaInformes : List<Informe>?=null
    private lateinit var recyclerViewInforme: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView=inflater.inflate(R.layout.fragment_informes,container,false)

        val servicio = ClienteApiRest.getRetrofitInstance().create(InformeServicio::class.java)

        val call = servicio.obtenerInformes()
        call.enqueue(object : Callback<List<Informe>>{
            override fun onFailure(call: Call<List<Informe>>, t: Throwable) {
                call.cancel()
            }

            override fun onResponse(call: Call<List<Informe>>, response: Response<List<Informe>>) {
                if(response.isSuccessful){
                    if (response.code()==200){
                        listaInformes=response.body()

                        adaptador=InformeAdaptador(listaInformes,this@InformesFragment)
                        recyclerViewInforme=rootView.findViewById(R.id.rv_informe) as RecyclerView
                        recyclerViewInforme.adapter=adaptador
                        recyclerViewInforme.layoutManager=LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
                    }else{
                        call.cancel()
                    }
                }
            }

        })
        return rootView
    }

    override fun OnItemClick(posicion: Int) {
        val intent = Intent(context,VerInformeActivity::class.java)
        intent.putExtra("informeSeleccionado",listaInformes?.get(posicion))
        startActivity(intent)
    }
}
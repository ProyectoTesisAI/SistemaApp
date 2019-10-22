package ec.edu.epn.snai.Controlador.Fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import ec.edu.epn.snai.Controlador.Adaptador.Reporte9Adaptador
import ec.edu.epn.snai.Modelo.Reporte7
import ec.edu.epn.snai.R
import ec.edu.epn.snai.Servicios.ClienteApiRest
import ec.edu.epn.snai.Servicios.ReporteServicio
import kotlinx.android.synthetic.main.fragment_resultados_reporte_9.view.*

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Reporte9CAIFragment:Fragment() {

    private lateinit var token: String
    private lateinit var rootView: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if(arguments!=null){
            token=arguments?.getSerializable("token") as String

        }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view= inflater.inflate(R.layout.fragment_resultados_reporte_9, container, false)

        rootView=view

        rootView.btnReporte9.setOnClickListener {

            obtenerListaReporte9()
        }
        return rootView
    }


    /****************************LISTA REPORTE 1 ***********************************************/
    private fun obtenerListaReporte9( ){

        try{
            val servicio = ClienteApiRest.getRetrofitInstance().create(ReporteServicio::class.java)
            val call = servicio.obtenerLugarResidenciaCAI( "Bearer " + token)

            call.enqueue(object : Callback<List<Reporte7>>{

                override fun onFailure(call: Call<List<Reporte7>>, t: Throwable) {

                }

                override fun onResponse(call: Call<List<Reporte7>>, response: Response<List<Reporte7>>) {

                    if(response.code()==200){
                        val listaResultadosReporte9=response.body()

                        if(listaResultadosReporte9 != null){

                            rootView.txtSinReportes.visibility=View.GONE
                            rootView.rv_reporte_9.visibility= View.VISIBLE
                            mostrarListadoReportes9(listaResultadosReporte9)
                        }


                    }
                    else{
                        rootView.txtSinReportes.visibility=View.VISIBLE
                        rootView.rv_reporte_9.visibility= View.GONE
                    }
                }
            })

        }catch (e:Exception){
            Toast.makeText(context, "Ha ocurrido un error al obtener los registro del reporte", Toast.LENGTH_SHORT).show()
        }

    }

    fun mostrarListadoReportes9(listaReportesReporte9: List<Reporte7>){

        if(listaReportesReporte9.size > 0){
            val recyclerViewReportes9=rootView.findViewById(R.id.rv_reporte_9) as RecyclerView
            val adaptador = Reporte9Adaptador(listaReportesReporte9)
            recyclerViewReportes9.adapter=adaptador
            recyclerViewReportes9.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
        }

    }

}
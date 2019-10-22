package ec.edu.epn.snai.Controlador.Fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import ec.edu.epn.snai.Controlador.Adaptador.Reporte10Adaptador
import ec.edu.epn.snai.Controlador.Adaptador.Reporte9Adaptador
import ec.edu.epn.snai.Modelo.Reporte8
import ec.edu.epn.snai.R
import ec.edu.epn.snai.Servicios.ClienteApiRest
import ec.edu.epn.snai.Servicios.ReporteServicio
import kotlinx.android.synthetic.main.fragment_resultados_reporte_10.view.*

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Reporte10Fragment:Fragment() {

    private lateinit var token: String
    private lateinit var rootView: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if(arguments!=null){
            token=arguments?.getSerializable("token") as String

        }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view= inflater.inflate(R.layout.fragment_resultados_reporte_10, container, false)

        rootView=view

        rootView.btnReporte10.setOnClickListener {

            obtenerListaReporte10()
        }
        return rootView
    }


    /****************************LISTA REPORTE 1 ***********************************************/
    private fun obtenerListaReporte10( ){

        try{
            val servicio = ClienteApiRest.getRetrofitInstance().create(ReporteServicio::class.java)
            val call = servicio.obtenerReporteInformesCompletos( "Bearer " + token)

            call.enqueue(object : Callback<List<Reporte8>>{

                override fun onFailure(call: Call<List<Reporte8>>, t: Throwable) {

                }

                override fun onResponse(call: Call<List<Reporte8>>, response: Response<List<Reporte8>>) {

                    if(response.code()==200){
                        val listaResultadosReporte10=response.body()

                        if(listaResultadosReporte10 != null){

                            rootView.txtSinReportes.visibility=View.GONE
                            rootView.rv_reporte_10.visibility= View.VISIBLE
                            rootView.layoutTitulos.visibility= View.VISIBLE
                            mostrarListadoReportes10(listaResultadosReporte10)
                        }


                    }
                    else{
                        rootView.txtSinReportes.visibility=View.VISIBLE
                        rootView.rv_reporte_10.visibility= View.GONE
                        rootView.layoutTitulos.visibility= View.GONE
                    }
                }
            })

        }catch (e:Exception){
            Toast.makeText(context, "Ha ocurrido un error al obtener los registro del reporte", Toast.LENGTH_SHORT).show()
        }

    }

    fun mostrarListadoReportes10(listaReportesReporte10: List<Reporte8>){

        if(listaReportesReporte10.size > 0){
            val recyclerViewReportes10=rootView.findViewById(R.id.rv_reporte_10) as RecyclerView
            val adaptador = Reporte10Adaptador(listaReportesReporte10)
            recyclerViewReportes10.adapter=adaptador
            recyclerViewReportes10.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
        }

    }

}
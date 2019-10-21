package ec.edu.epn.snai.Controlador.Fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import ec.edu.epn.snai.Controlador.Adaptador.Reporte2Adaptador
import ec.edu.epn.snai.Modelo.Reporte2
import ec.edu.epn.snai.R
import ec.edu.epn.snai.Servicios.ClienteApiRest
import ec.edu.epn.snai.Servicios.ReporteServicio
import kotlinx.android.synthetic.main.fragment_resultados_reporte_1_.view.*
import kotlinx.android.synthetic.main.fragment_resultados_reporte_2.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Reporte2UZDIFragment:Fragment() {

    private lateinit var token: String
    private lateinit var rootView: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if(arguments!=null){
            token=arguments?.getSerializable("token") as String

        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view= inflater.inflate(R.layout.fragment_resultados_reporte_2, container, false)

        rootView=view

        rootView.btnReporte2.setOnClickListener {
            val reporte2Aux= Reporte2()
            val edad = rootView.txtEdadBusqueda?.text.toString()
            reporte2Aux.edad = Integer.parseInt(edad)

            obtenerListaReporte2(reporte2Aux)
        }
        return rootView
    }

    /****************************LISTA REPORTE 2 ***********************************************/
    private fun obtenerListaReporte2(reporte2: Reporte2){

        try{
            val servicio = ClienteApiRest.getRetrofitInstance().create(ReporteServicio::class.java)
            val call = servicio.obtenerReporteEdadUDI( reporte2,"Bearer " + token)

            call.enqueue(object : Callback<List<Reporte2>>{

                override fun onFailure(call: Call<List<Reporte2>>, t: Throwable) {

                }

                override fun onResponse(call: Call<List<Reporte2>>, response: Response<List<Reporte2>>) {

                    if(response.code()==200){
                        val listaResultadosReporte2=response.body()

                        if(listaResultadosReporte2 != null){

                            rootView.txtSinReportes2.visibility=View.GONE
                            rootView.rv_reporte_2.visibility= View.VISIBLE
                            mostrarListadoReportes2(listaResultadosReporte2)
                        }


                    }
                    else{
                        rootView.txtSinReportes2.visibility=View.VISIBLE
                        rootView.rv_reporte_2.visibility= View.GONE
                    }
                }
            })

        }catch (e:Exception){
            Toast.makeText(context, "Ha ocurrido un error al obtener los datos tipo penal", Toast.LENGTH_SHORT).show()
        }

    }

    fun mostrarListadoReportes2(listaReportesReporte2: List<Reporte2>){

        if(listaReportesReporte2.size > 0){
            val recyclerViewReportes2=rootView.findViewById(R.id.rv_reporte_2) as RecyclerView
            val adaptador = Reporte2Adaptador(listaReportesReporte2)
            recyclerViewReportes2.adapter=adaptador
            recyclerViewReportes2.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
        }

    }

}
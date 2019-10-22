package ec.edu.epn.snai.Controlador.Fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import ec.edu.epn.snai.Controlador.Adaptador.Reporte4Adaptador
import ec.edu.epn.snai.Modelo.Reporte3
import ec.edu.epn.snai.R
import ec.edu.epn.snai.Servicios.ClienteApiRest
import ec.edu.epn.snai.Servicios.ReporteServicio
import kotlinx.android.synthetic.main.fragment_resultados_reporte_4.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Reporte4CAIFragment:Fragment() {

    private lateinit var token: String
    private lateinit var rootView: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if(arguments!=null){
            token=arguments?.getSerializable("token") as String

        }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view= inflater.inflate(R.layout.fragment_resultados_reporte_4, container, false)

        rootView=view

        asignarListaNacionalidadSpinner()

        rootView.btnReporte4.setOnClickListener {

            val nacionalidadAux=rootView.spNacionalidad.selectedItem.toString()
            val nacionalidad=Reporte3()
            nacionalidad.nacionalidad=nacionalidadAux
            obtenerListaReporte4(nacionalidad)

        }
        return rootView
    }

    private fun asignarListaNacionalidadSpinner(){

        val spNacionalidad= rootView.spNacionalidad

        //Adaptador de Nacionalidad para el Spinner
        val adapterTipoCentro= ArrayAdapter<String>(context,android.R.layout.simple_expandable_list_item_1,resources.getStringArray(R.array.nacionalidad))
        adapterTipoCentro.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spNacionalidad.adapter=adapterTipoCentro

    }

        /****************************LISTA REPORTE 4 ***********************************************/
    private fun obtenerListaReporte4(nacionalidad: Reporte3){

        try{
            val servicio = ClienteApiRest.getRetrofitInstance().create(ReporteServicio::class.java)
            val call = servicio.obtenerReporteNacionalidadCAI( nacionalidad,"Bearer " + token)

            call.enqueue(object : Callback<List<Reporte3>>{

                override fun onFailure(call: Call<List<Reporte3>>, t: Throwable) {

                }

                override fun onResponse(call: Call<List<Reporte3>>, response: Response<List<Reporte3>>) {

                    if(response.code()==200){
                        val listaResultadosReporte1=response.body()

                        if(listaResultadosReporte1 != null){

                            rootView.txtSinReportes.visibility=View.GONE
                            rootView.rv_reporte_4.visibility= View.VISIBLE
                            mostrarListadoReportes4(listaResultadosReporte1)
                        }


                    }
                    else{
                        rootView.txtSinReportes.visibility=View.VISIBLE
                        rootView.rv_reporte_4.visibility= View.GONE
                    }
                }
            })

        }catch (e:Exception){
            Toast.makeText(context, "Ha ocurrido un error al obtener los registro del reporte", Toast.LENGTH_SHORT).show()
        }

    }

    fun mostrarListadoReportes4(listaReportesReporte4: List<Reporte3>){

        if(listaReportesReporte4.size > 0){
            val recyclerViewReportes1=rootView.findViewById(R.id.rv_reporte_4) as RecyclerView
            val adaptador = Reporte4Adaptador(listaReportesReporte4)
            recyclerViewReportes1.adapter=adaptador
            recyclerViewReportes1.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
        }

    }

}
package ec.edu.epn.snai.Controlador.Fragment

import android.annotation.SuppressLint
import android.os.AsyncTask
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import ec.edu.epn.snai.Controlador.Adaptador.Reporte1Adaptador
import ec.edu.epn.snai.Controlador.Adaptador.Reporte2Adaptador
import ec.edu.epn.snai.Modelo.DatosTipoPenalCAI
import ec.edu.epn.snai.Modelo.Reporte1
import ec.edu.epn.snai.Modelo.Reporte2
import ec.edu.epn.snai.R
import ec.edu.epn.snai.Servicios.ClienteApiRest
import ec.edu.epn.snai.Servicios.ReporteServicio
import kotlinx.android.synthetic.main.fragment_resultados_reporte_1_.view.*
import kotlinx.android.synthetic.main.fragment_resultados_reporte_1_.view.txtSinReportes
import kotlinx.android.synthetic.main.fragment_resultados_reporte_2.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Reporte2CAIFragment:Fragment() {

    private lateinit var token: String
    private var listaDatosEdadCAI: List<DatosTipoPenalCAI> =ArrayList()
    private lateinit var rootView: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if(arguments!=null){
            token=arguments?.getSerializable("token") as String

        }
        asynTaskObtenerListadoCai()

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view= inflater.inflate(R.layout.fragment_resultados_reporte_2, container, false)

        rootView=view



        rootView.btnReporte1.setOnClickListener {
            val reporte1Aux=Reporte1()

            if(listaDatosEdadCAI.size > 0){

                val edad=listaDatosEdadCAI.get(rootView.spTipoDelito.selectedItemPosition)
                reporte1Aux.tipoDelto=edad.tipoPenal

                obtenerListaReporte1(reporte1Aux)
            }

        }
        return rootView
    }

    /************************* LISTA DATOS TIPO PENAL ***********************************/
    private fun asynTaskObtenerListadoCai(){

        try{

            val task = @SuppressLint("StaticFieldLeak")
            object : AsyncTask<Unit, Unit, List<DatosTipoPenalCAI>>(){


                override fun doInBackground(vararg p0: Unit?): List<DatosTipoPenalCAI> {
                    val listadoTipoPenal=obtenerDatosTipoPenal()
                    return listadoTipoPenal!!
                }

            }
            listaDatosEdadCAI= task.execute().get()
        }catch (e:Exception){
            Toast.makeText(context, "Ha ocurrido un error al obtener los datos de tipo penal", Toast.LENGTH_SHORT).show()
        }

    }

    private fun obtenerDatosTipoPenal(): List<DatosTipoPenalCAI>?{

        try{
            val servicio = ClienteApiRest.getRetrofitInstance().create(ReporteServicio::class.java)
            val call = servicio.obtenerDatosTipoPenal( "Bearer " + token)

            val resultado= call.execute()
            if(resultado.code() == 200){
                return resultado.body()!!
            }
            else{
                return null
            }

        }catch (e:Exception){
            Toast.makeText(context, "Ha ocurrido un error al obtener el Registro Fotográfico", Toast.LENGTH_SHORT).show()
            return null
        }

    }

    /****************************LISTA REPORTE 1 ***********************************************/
    private fun obtenerListaReporte1(reporte2: Reporte2){

        try{
            val servicio = ClienteApiRest.getRetrofitInstance().create(ReporteServicio::class.java)
            val call = servicio.obtenerReporteEdadCAI( reporte2,"Bearer " + token)

            call.enqueue(object : Callback<List<Reporte2>>{

                override fun onFailure(call: Call<List<Reporte2>>, t: Throwable) {

                }

                override fun onResponse(call: Call<List<Reporte2>>, response: Response<List<Reporte2>>) {

                    if(response.code()==200){
                        val listaResultadosReporte2=response.body()

                        if(listaResultadosReporte2 != null){

                            rootView.txtSinReportes.visibility=View.GONE
                            rootView.rv_reporte_2.visibility= View.VISIBLE
                            mostrarListadoReportes2(listaResultadosReporte2)
                        }


                    }
                    else{
                        rootView.txtSinReportes.visibility=View.VISIBLE
                        rootView.rv_reporte_1.visibility= View.GONE
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
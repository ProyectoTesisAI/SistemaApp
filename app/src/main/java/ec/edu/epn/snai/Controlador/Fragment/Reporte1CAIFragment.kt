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
import android.widget.ArrayAdapter
import android.widget.Toast
import ec.edu.epn.snai.Controlador.Adaptador.ListaAsistenciaAdaptador
import ec.edu.epn.snai.Controlador.Adaptador.Reporte1Adaptador
import ec.edu.epn.snai.Modelo.DatosTipoPenalCAI
import ec.edu.epn.snai.Modelo.Reporte1
import ec.edu.epn.snai.Modelo.Usuario
import ec.edu.epn.snai.R
import ec.edu.epn.snai.Servicios.ClienteApiRest
import ec.edu.epn.snai.Servicios.ReporteServicio
import kotlinx.android.synthetic.main.fragment_resultados_reporte_1.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Reporte1CAIFragment:Fragment() {

    private lateinit var token: String
    private var listaDatosTipoPenalCAI: List<DatosTipoPenalCAI> =ArrayList()
    private lateinit var rootView: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if(arguments!=null){
            token=arguments?.getSerializable("token") as String

        }
        asynTaskObtenerListadoCai()

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view= inflater.inflate(R.layout.fragment_resultados_reporte_1, container, false)

        rootView=view

        asignarListaDatosTipoPenalSpinner()

        rootView.btnReporte1.setOnClickListener {
            val reporte1Aux=Reporte1()

            if(listaDatosTipoPenalCAI.size > 0){

                val delito=listaDatosTipoPenalCAI.get(rootView.spTipoDelito.selectedItemPosition)
                reporte1Aux.tipoDelto=delito.tipoPenal

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
            listaDatosTipoPenalCAI= task.execute().get()
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

    private fun asignarListaDatosTipoPenalSpinner(){

        if(listaDatosTipoPenalCAI.size > 0){

            val listaTipoPenalCAIAux:MutableList<String> = ArrayList<String>()

            for (p in listaDatosTipoPenalCAI){
                listaTipoPenalCAIAux.add(p.tipoPenal)
            }

            val adapterTipoDelito= ArrayAdapter<String>(context ,android.R.layout.simple_expandable_list_item_1,listaTipoPenalCAIAux)
            adapterTipoDelito.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            rootView.spTipoDelito.adapter=adapterTipoDelito
        }


    }


    /****************************LISTA REPORTE 1 ***********************************************/
    private fun obtenerListaReporte1(reporte1: Reporte1){

        try{
            val servicio = ClienteApiRest.getRetrofitInstance().create(ReporteServicio::class.java)
            val call = servicio.obtenerReporteTipoDelitoCAI( reporte1,"Bearer " + token)

            call.enqueue(object : Callback<List<Reporte1>>{

                override fun onFailure(call: Call<List<Reporte1>>, t: Throwable) {

                }

                override fun onResponse(call: Call<List<Reporte1>>, response: Response<List<Reporte1>>) {

                    if(response.code()==200){
                        val listaResultadosReporte1=response.body()

                        if(listaResultadosReporte1 != null){

                            rootView.txtSinReportes.visibility=View.GONE
                            rootView.rv_reporte_1.visibility= View.VISIBLE
                            mostrarListadoReportes1(listaResultadosReporte1)
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

    fun mostrarListadoReportes1(listaReportesReporte1: List<Reporte1>){

        if(listaReportesReporte1.size > 0){
            val recyclerViewReportes1=rootView.findViewById(R.id.rv_reporte_1) as RecyclerView
            val adaptador = Reporte1Adaptador(listaReportesReporte1)
            recyclerViewReportes1.adapter=adaptador
            recyclerViewReportes1.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
        }

    }

}
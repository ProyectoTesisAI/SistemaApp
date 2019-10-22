package ec.edu.epn.snai.Controlador.Fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import ec.edu.epn.snai.Controlador.Adaptador.Reporte5Adaptador
import ec.edu.epn.snai.Modelo.Reporte4
import ec.edu.epn.snai.R
import ec.edu.epn.snai.Servicios.ClienteApiRest
import ec.edu.epn.snai.Servicios.ReporteServicio
import kotlinx.android.synthetic.main.fragment_resultados_reporte_5cai.view.*
import kotlinx.android.synthetic.main.fragment_resultados_reporte_5udi.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Reporte5UZDIFragment:Fragment() {

    private lateinit var token: String
    private var listaMedidaSocioeducativa: ArrayList<String> =ArrayList()
    private var medidaBusqueda : Reporte4?=null
    private lateinit var rootView: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if(arguments!=null){
            token=arguments?.getSerializable("token") as String

        }
        medidaBusqueda= Reporte4()

        listaMedidaSocioeducativa.add("AMONESTACIÓN VERBAL")
        listaMedidaSocioeducativa.add("IMPOSICIÓN DE REGLAS DE CONDUCTA")
        listaMedidaSocioeducativa.add("ORIENTACIÓN Y APOYO PSICO SOCIO FAMILIAR")
        listaMedidaSocioeducativa.add("SERVICIO A LA COMUNIDAD")
        listaMedidaSocioeducativa.add("LIBERTAD ASISTIDA")


    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view= inflater.inflate(R.layout.fragment_resultados_reporte_5udi, container, false)

        rootView=view

        asignarListaDatosMedidaSocioeducativa()

        val spTipoMedida: Spinner =rootView.findViewById<Spinner>(R.id.spTipoMedida5u)

        spTipoMedida.onItemSelectedListener=object :AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, posicion: Int, p3: Long) {
                medidaBusqueda?.medidaSocioeducativa=listaMedidaSocioeducativa.get(posicion)
            }

        }

        rootView.btnReporte5u.setOnClickListener {
            if(medidaBusqueda!=null){
                obtenerListaReporte5()
            }
        }
        return rootView
    }

    /************************* LISTA DATOS ***********************************/

    private fun asignarListaDatosMedidaSocioeducativa(){
        if(listaMedidaSocioeducativa.size > 0){
            val adapterMedidaSocioeducativa= ArrayAdapter<String>(context ,android.R.layout.simple_expandable_list_item_1,listaMedidaSocioeducativa)
            adapterMedidaSocioeducativa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            rootView.spTipoMedida5u.adapter=adapterMedidaSocioeducativa
        }
    }

    /****************************LISTA REPORTE 6 ***********************************************/
    private fun obtenerListaReporte5(){
        try{
            val servicio = ClienteApiRest.getRetrofitInstance().create(ReporteServicio::class.java)
            val call = servicio.obtenerReporteMedidaSocioEducativaUDI(medidaBusqueda,"Bearer " + token)
            call.enqueue(object : Callback<List<Reporte4>>{
                override fun onFailure(call: Call<List<Reporte4>>, t: Throwable) {
                }
                override fun onResponse(call: Call<List<Reporte4>>, response: Response<List<Reporte4>>) {
                    if(response.code()==200){
                        val listaResultadosReporte5=response.body()
                        if(listaResultadosReporte5 != null){
                            rootView.txtSinReportes5u.visibility=View.GONE
                            rootView.rv_reporte_5u.visibility= View.VISIBLE
                            mostrarListadoReportes5(listaResultadosReporte5)
                        }
                    }
                    else{
                        rootView.txtSinReportes5u.visibility=View.VISIBLE
                        rootView.rv_reporte_5u.visibility= View.GONE
                    }
                }
            })
        }catch (e:Exception){
            Toast.makeText(context, "Ha ocurrido un error al obtener los datos tipo penal", Toast.LENGTH_SHORT).show()
        }

    }

    fun mostrarListadoReportes5(listaReportesReporte5: List<Reporte4>){
        if(listaReportesReporte5.size > 0){
            val recyclerViewReportes5=rootView.findViewById(R.id.rv_reporte_5u) as RecyclerView
            val adaptador = Reporte5Adaptador(listaReportesReporte5)
            recyclerViewReportes5.adapter=adaptador
            recyclerViewReportes5.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
        }

    }
}
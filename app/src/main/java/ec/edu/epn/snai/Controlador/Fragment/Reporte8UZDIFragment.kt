package ec.edu.epn.snai.Controlador.Fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import ec.edu.epn.snai.Controlador.Adaptador.Reporte7NAdaptador
import ec.edu.epn.snai.Controlador.Adaptador.Reporte8NAdaptador
import ec.edu.epn.snai.Controlador.Adaptador.Reporte8SAdaptador
import ec.edu.epn.snai.Modelo.Reporte6N
import ec.edu.epn.snai.Modelo.Reporte6S
import ec.edu.epn.snai.R
import ec.edu.epn.snai.Servicios.ClienteApiRest
import ec.edu.epn.snai.Servicios.ReporteServicio
import kotlinx.android.synthetic.main.fragment_resultados_reporte_7.view.*
import kotlinx.android.synthetic.main.fragment_resultados_reporte_8.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Reporte8UZDIFragment:Fragment() {

    private lateinit var token: String
    private var listaEstudia: ArrayList<String> =ArrayList()
    private var edadSBusqueda : Reporte6S?=null
    private var edadNBusqueda : Reporte6N?=null
    private lateinit var rootView: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if(arguments!=null){
            token=arguments?.getSerializable("token") as String

        }
        edadSBusqueda= Reporte6S()
        edadNBusqueda= Reporte6N()

        listaEstudia.add("NO")
        listaEstudia.add("SI")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view= inflater.inflate(R.layout.fragment_resultados_reporte_8, container, false)

        rootView=view

        asignarListaDatosEstudia()

        val spEstudia: Spinner =rootView.findViewById<Spinner>(R.id.spEstudia8)

        spEstudia.onItemSelectedListener=object :AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, posicion: Int, p3: Long) {
                if(posicion==0){
                    rootView.findViewById<Button>(R.id.btnReporte8N).visibility=View.VISIBLE
                    rootView.findViewById<Button>(R.id.btnReporte8S).visibility=View.GONE
                }
                if(posicion==1){
                    rootView.findViewById<Button>(R.id.btnReporte8N).visibility=View.GONE
                    rootView.findViewById<Button>(R.id.btnReporte8S).visibility=View.VISIBLE
                }
            }
        }

        rootView.btnReporte8S.setOnClickListener {
            val edad = rootView.txtEdadBusqueda8?.text.toString()
            edadSBusqueda?.edad = Integer.parseInt(edad)
            obtenerListaReporte8S()
        }
        rootView.btnReporte8N.setOnClickListener {
            val edad = rootView.txtEdadBusqueda8?.text.toString()
            edadNBusqueda?.edad = Integer.parseInt(edad)
            obtenerListaReporte8N()
        }
        return rootView
    }

    /************************* LISTA DATOS ***********************************/
    private fun asignarListaDatosEstudia(){
        if(listaEstudia.size > 0){
            val adapterEstudia= ArrayAdapter<String>(context ,android.R.layout.simple_expandable_list_item_1,listaEstudia)
            adapterEstudia.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            rootView.spEstudia8.adapter=adapterEstudia
        }
    }


    /****************************LISTA REPORTE 8 ***********************************************/
    private fun obtenerListaReporte8S(){
        try{
            val servicio = ClienteApiRest.getRetrofitInstance().create(ReporteServicio::class.java)
            val call = servicio.obtenerEdadReporteNivelEducativoSiUDI(edadSBusqueda,"Bearer " + token)
            call.enqueue(object : Callback<List<Reporte6S>>{
                override fun onFailure(call: Call<List<Reporte6S>>, t: Throwable) {
                }
                override fun onResponse(call: Call<List<Reporte6S>>, response: Response<List<Reporte6S>>) {
                    if(response.code()==200){
                        val listaResultadosReporte8S=response.body()
                        if(listaResultadosReporte8S != null){
                            rootView.txtSinReportes8.visibility=View.GONE
                            rootView.rv_reporte_8.visibility= View.VISIBLE
                            mostrarListadoReportes8S(listaResultadosReporte8S)
                        }
                    }
                    else{
                        rootView.txtSinReportes8.visibility=View.VISIBLE
                        rootView.rv_reporte_8.visibility= View.GONE
                    }
                }
            })
        }catch (e:Exception){
            Toast.makeText(context, "Ha ocurrido un error al obtener los datos", Toast.LENGTH_SHORT).show()
        }

    }

    fun mostrarListadoReportes8S(listaReportesReporte8S: List<Reporte6S>){
        if(listaReportesReporte8S.size > 0){
            val recyclerViewReportes6S=rootView.findViewById(R.id.rv_reporte_8) as RecyclerView
            val adaptador = Reporte8SAdaptador(listaReportesReporte8S)
            recyclerViewReportes6S.adapter=adaptador
            recyclerViewReportes6S.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
        }

    }

    /**Listado reportes para no estudia**/
    private fun obtenerListaReporte8N(){
        try{
            val servicio = ClienteApiRest.getRetrofitInstance().create(ReporteServicio::class.java)
            val call = servicio.obtenerEdadReporteNivelEducativoNoUDI(edadNBusqueda,"Bearer " + token)
            call.enqueue(object : Callback<List<Reporte6N>>{
                override fun onFailure(call: Call<List<Reporte6N>>, t: Throwable) {
                }
                override fun onResponse(call: Call<List<Reporte6N>>, response: Response<List<Reporte6N>>) {
                    if(response.code()==200){
                        val listaResultadosReporte8N=response.body()
                        if(listaResultadosReporte8N != null){
                            rootView.txtSinReportes8.visibility=View.GONE
                            rootView.rv_reporte_8.visibility= View.VISIBLE
                            mostrarListadoReportes8N(listaResultadosReporte8N)
                        }
                    }
                    else{
                        rootView.txtSinReportes8.visibility=View.VISIBLE
                        rootView.rv_reporte_8.visibility= View.GONE
                    }
                }
            })

        }catch (e:Exception){
            Toast.makeText(context, "Ha ocurrido un error al obtener los datos", Toast.LENGTH_SHORT).show()
        }

    }

    fun mostrarListadoReportes8N(listaReportesReporte8N: List<Reporte6N>){
        if(listaReportesReporte8N.size > 0){
            val recyclerViewReportes8N=rootView.findViewById(R.id.rv_reporte_8) as RecyclerView
            val adaptador = Reporte8NAdaptador(listaReportesReporte8N)
            recyclerViewReportes8N.adapter=adaptador
            recyclerViewReportes8N.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
        }

    }
}
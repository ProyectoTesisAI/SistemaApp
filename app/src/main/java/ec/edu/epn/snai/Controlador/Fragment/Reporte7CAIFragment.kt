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
import ec.edu.epn.snai.Controlador.Adaptador.Reporte7SAdaptador
import ec.edu.epn.snai.Modelo.Reporte6N
import ec.edu.epn.snai.Modelo.Reporte6S
import ec.edu.epn.snai.R
import ec.edu.epn.snai.Servicios.ClienteApiRest
import ec.edu.epn.snai.Servicios.ReporteServicio
import kotlinx.android.synthetic.main.fragment_resultados_reporte_7.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Reporte7CAIFragment:Fragment() {

    private lateinit var token: String
    private var listaNivelEducativo: ArrayList<String> =ArrayList()
    private var listaEstudia: ArrayList<String> =ArrayList()
    private var nivelEducacionBuscar : String?=null
    private var nivelBusqueda : Reporte6S?=null
    private lateinit var rootView: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if(arguments!=null){
            token=arguments?.getSerializable("token") as String

        }
        nivelBusqueda= Reporte6S()

        listaEstudia.add("NO")
        listaEstudia.add("SI")

        listaNivelEducativo.add("1er año de basica")
        listaNivelEducativo.add("2do año de basica")
        listaNivelEducativo.add("3er año de basica")
        listaNivelEducativo.add("4to año de basica")
        listaNivelEducativo.add("5to año de basica")
        listaNivelEducativo.add("6to año de basica")
        listaNivelEducativo.add("7mo año de basica")
        listaNivelEducativo.add("8vo año de basica")
        listaNivelEducativo.add("9no año de basica")
        listaNivelEducativo.add("10mo año de basica")
        listaNivelEducativo.add("1er año bachillerato general unificado")
        listaNivelEducativo.add("2do año bachillerato general unificado")
        listaNivelEducativo.add("3er año bachillerato general unificado")
        listaNivelEducativo.add("1er año bachillerato técnico")
        listaNivelEducativo.add("2do año bachillerato técnico")
        listaNivelEducativo.add("3er año bachillerato técnico")
        listaNivelEducativo.add("1er año bachillerato internacional")
        listaNivelEducativo.add("2do año bachillerato internacional")
        listaNivelEducativo.add("3er año bachillerato internacional")
        listaNivelEducativo.add("Alfabetizacion/Postalfabetizacion")
        listaNivelEducativo.add("Basica Intensiva")
        listaNivelEducativo.add("Bachillerato Intensivo")
        listaNivelEducativo.add("Tercer Nivel o Superior")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view= inflater.inflate(R.layout.fragment_resultados_reporte_7, container, false)

        rootView=view

        asignarListaDatosNivelEducativo()
        asignarListaDatosEstudia()

        val spEstudia: Spinner =rootView.findViewById<Spinner>(R.id.spEstudia6)
        val spNivel: Spinner =rootView.findViewById<Spinner>(R.id.spNivelEducativo6)

        spEstudia.onItemSelectedListener=object :AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, posicion: Int, p3: Long) {
                if(posicion==0){
                    rootView.findViewById<Button>(R.id.btnReporte6N).visibility=View.VISIBLE
                    rootView.findViewById<Spinner>(R.id.spNivelEducativo6).visibility=View.GONE
                    rootView.findViewById<TextView>(R.id.txtNivel6).visibility=View.GONE
                    rootView.findViewById<Button>(R.id.btnReporte6S).visibility=View.GONE
                }
                if(posicion==1){
                    rootView.findViewById<Button>(R.id.btnReporte6N).visibility=View.GONE
                    rootView.findViewById<Spinner>(R.id.spNivelEducativo6).visibility=View.VISIBLE
                    rootView.findViewById<TextView>(R.id.txtNivel6).visibility=View.VISIBLE
                    rootView.findViewById<Button>(R.id.btnReporte6S).visibility=View.VISIBLE
                }
            }
        }

        spNivel.onItemSelectedListener=object :AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, posicion: Int, p3: Long) {
                nivelEducacionBuscar=listaNivelEducativo.get(posicion)
                nivelBusqueda?.nivelEducativo=nivelEducacionBuscar
            }

        }

        rootView.btnReporte6S.setOnClickListener {
            val reporte6SAux= String()

            if(listaNivelEducativo.size > 0){

                val nivel=listaNivelEducativo.get(rootView.spNivelEducativo6.selectedItemPosition)
                println(nivel)
                obtenerListaReporte6S(nivel)
            }

        }
        rootView.btnReporte6N.setOnClickListener {
            val reporte6NAux= String()
            obtenerListaReporte6N("")

        }
        return rootView
    }

    /************************* LISTA DATOS ***********************************/
    private fun asignarListaDatosNivelEducativo(){
        if(listaNivelEducativo.size > 0){
            val adapterNivelEducativo= ArrayAdapter<String>(context ,android.R.layout.simple_expandable_list_item_1,listaNivelEducativo)
            adapterNivelEducativo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            rootView.spNivelEducativo6.adapter=adapterNivelEducativo
        }
    }

    private fun asignarListaDatosEstudia(){
        if(listaEstudia.size > 0){
            val adapterEstudia= ArrayAdapter<String>(context ,android.R.layout.simple_expandable_list_item_1,listaEstudia)
            adapterEstudia.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            rootView.spEstudia6.adapter=adapterEstudia
        }
    }


    /****************************LISTA REPORTE 6 ***********************************************/
    private fun obtenerListaReporte6S(nivel: String){

        try{
            val servicio = ClienteApiRest.getRetrofitInstance().create(ReporteServicio::class.java)
            val call = servicio.obtenerReporteNivelEducativoSCAI( nivelBusqueda,"Bearer " + token)

            call.enqueue(object : Callback<List<Reporte6S>>{

                override fun onFailure(call: Call<List<Reporte6S>>, t: Throwable) {

                }

                override fun onResponse(call: Call<List<Reporte6S>>, response: Response<List<Reporte6S>>) {

                    if(response.code()==200){
                        val listaResultadosReporte6S=response.body()

                        if(listaResultadosReporte6S != null){

                            rootView.txtSinReportes6.visibility=View.GONE
                            rootView.rv_reporte_6.visibility= View.VISIBLE
                            mostrarListadoReportes6S(listaResultadosReporte6S)
                        }


                    }
                    else{
                        rootView.txtSinReportes6.visibility=View.VISIBLE
                        rootView.rv_reporte_6.visibility= View.GONE
                    }
                }
            })

        }catch (e:Exception){
            Toast.makeText(context, "Ha ocurrido un error al obtener los datos tipo penal", Toast.LENGTH_SHORT).show()
        }

    }

    fun mostrarListadoReportes6S(listaReportesReporte6S: List<Reporte6S>){

        if(listaReportesReporte6S.size > 0){
            val recyclerViewReportes6S=rootView.findViewById(R.id.rv_reporte_6) as RecyclerView
            val adaptador = Reporte7SAdaptador(listaReportesReporte6S)
            recyclerViewReportes6S.adapter=adaptador
            recyclerViewReportes6S.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
        }

    }

    /**Listado reportes para no estudia**/
    private fun obtenerListaReporte6N(nivel: String){

        try{
            val servicio = ClienteApiRest.getRetrofitInstance().create(ReporteServicio::class.java)
            val call = servicio.obtenerReporteNivelEducativoNCAI( nivel,"Bearer " + token)

            call.enqueue(object : Callback<List<Reporte6N>>{

                override fun onFailure(call: Call<List<Reporte6N>>, t: Throwable) {

                }

                override fun onResponse(call: Call<List<Reporte6N>>, response: Response<List<Reporte6N>>) {

                    if(response.code()==200){
                        val listaResultadosReporte6N=response.body()

                        if(listaResultadosReporte6N != null){

                            rootView.txtSinReportes6.visibility=View.GONE
                            rootView.rv_reporte_6.visibility= View.VISIBLE
                            mostrarListadoReportes6N(listaResultadosReporte6N)
                        }


                    }
                    else{
                        rootView.txtSinReportes6.visibility=View.VISIBLE
                        rootView.rv_reporte_6.visibility= View.GONE
                    }
                }
            })

        }catch (e:Exception){
            Toast.makeText(context, "Ha ocurrido un error al obtener los datos tipo penal", Toast.LENGTH_SHORT).show()
        }

    }

    fun mostrarListadoReportes6N(listaReportesReporte6N: List<Reporte6N>){

        if(listaReportesReporte6N.size > 0){
            val recyclerViewReportes6N=rootView.findViewById(R.id.rv_reporte_6) as RecyclerView
            val adaptador = Reporte7NAdaptador(listaReportesReporte6N)
            recyclerViewReportes6N.adapter=adaptador
            recyclerViewReportes6N.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
        }

    }
}
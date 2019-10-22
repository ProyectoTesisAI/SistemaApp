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
import ec.edu.epn.snai.Controlador.Adaptador.Reporte7NAdaptador
import ec.edu.epn.snai.Controlador.Adaptador.Reporte7SAdaptador
import ec.edu.epn.snai.Modelo.Reporte4
import ec.edu.epn.snai.Modelo.Reporte5
import ec.edu.epn.snai.Modelo.Reporte6N
import ec.edu.epn.snai.R
import ec.edu.epn.snai.Servicios.ClienteApiRest
import ec.edu.epn.snai.Servicios.ReporteServicio
import kotlinx.android.synthetic.main.fragment_resultados_reporte_5cai.view.*
import kotlinx.android.synthetic.main.fragment_resultados_reporte_7.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Reporte5CAIFragment:Fragment() {

    private lateinit var token: String
    private var listaTipoMedida: ArrayList<String> =ArrayList()
    private var listaMedidaSocioeducativa: ArrayList<String> =ArrayList()
    private var listaMedidaCautelar: ArrayList<String> =ArrayList()
    private var medidaBusqueda : Reporte4?=null
    private lateinit var rootView: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if(arguments!=null){
            token=arguments?.getSerializable("token") as String

        }
        medidaBusqueda= Reporte4()

        listaTipoMedida.add("MEDIDA SOCIOEDUCATIVA PRIVATIVA DE LIBERTAD")
        listaTipoMedida.add("MEDIDA CAUTELAR")

        listaMedidaSocioeducativa.add("Internamiento domiciliario (Art.379.1 CNA)")
        listaMedidaSocioeducativa.add("Internamiento fin de semana (Art.379.2 CNA)")
        listaMedidaSocioeducativa.add("Internamiento institucional (Art.379.4 CNA)")
        listaMedidaSocioeducativa.add("Régimen cerrado (Art.381 CNA)")
        listaMedidaSocioeducativa.add("Régimen semiabierto (Art.382 CNA)")
        listaMedidaSocioeducativa.add("Régimen  abierto (Art.383 CNA)")
        listaMedidaSocioeducativa.add("Otros")

        listaMedidaCautelar.add("1. La permanencia del adolescente en su propio domicilio, con la vigilancia que el Juez disponga")
        listaMedidaCautelar.add("2. La obligación de someterse al cuidado de una persona o entidad de atención, que informarán regularmente al Juez sobre la conducta del adolescente")
        listaMedidaCautelar.add("3. La obligación de presentarse ante el Juez con la periodicidad que éste ordene")
        listaMedidaCautelar.add("4. La prohibición de ausentarse del país o de la localidad que señale el Juez")
        listaMedidaCautelar.add("5. La prohibición de concurrir a los lugares o reuniones que determine el Juez")
        listaMedidaCautelar.add("6. La prohibición de comunicarse con determinadas personas que el Juez señale, siempre que ello no afecte su derecho al medio familiar y a una adecuada defensa")
        listaMedidaCautelar.add("7. La privación de libertad, en los casos excepcionales que se señala la ley")
        listaMedidaCautelar.add("Art. 326 (a) Infracción Flagrante")
        listaMedidaCautelar.add("Art. 326 (b) Cuando se ha fugado de un CAI")
        listaMedidaCautelar.add("Art. 328.- Detención para investigación")
        listaMedidaCautelar.add("Art. 329.- Detención para asegurar la comparecencia")
        listaMedidaCautelar.add("Art. 330.- Internamiento Preventivo")
        listaMedidaCautelar.add("8. Internamiento Institucional")

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view= inflater.inflate(R.layout.fragment_resultados_reporte_5cai, container, false)

        rootView=view

        asignarListaDatosTipoMedida()
        asignarListaDatosMedidaSocioeducativa()

        val spTipo: Spinner =rootView.findViewById<Spinner>(R.id.spTipoMedida5c)
        val spMedida: Spinner =rootView.findViewById<Spinner>(R.id.spMedida5c)

        spTipo.onItemSelectedListener=object :AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, posicion: Int, p3: Long) {
                if(posicion==0){//MEDIDA SOCIOEDUCATIVA PRIVATIVA DE LIBERTAD
                    medidaBusqueda?.tipoDelito=listaTipoMedida.get(posicion)
                    asignarListaDatosMedidaSocioeducativa()
                    spMedida.onItemSelectedListener=object :AdapterView.OnItemSelectedListener{
                        override fun onNothingSelected(parent: AdapterView<*>?) {

                        }

                        override fun onItemSelected(p0: AdapterView<*>?, p1: View?, posicion: Int, p3: Long) {
                            medidaBusqueda?.medidaSocioeducativa=listaMedidaSocioeducativa.get(posicion)
                        }

                    }
                }
                if(posicion==1){//MEDIDA CAUTELAR
                    medidaBusqueda?.tipoDelito=listaTipoMedida.get(posicion)
                    asignarListaDatosMedidaCautelar()
                    spMedida.onItemSelectedListener=object :AdapterView.OnItemSelectedListener{
                        override fun onNothingSelected(parent: AdapterView<*>?) {

                        }

                        override fun onItemSelected(p0: AdapterView<*>?, p1: View?, posicion: Int, p3: Long) {
                            medidaBusqueda?.medidaSocioeducativa=listaMedidaCautelar.get(posicion)
                        }

                    }
                }
            }
        }



        rootView.btnReporte5c.setOnClickListener {
            if(medidaBusqueda!=null){
                obtenerListaReporte5()
            }
        }
        return rootView
    }

    /************************* LISTA DATOS ***********************************/
    private fun asignarListaDatosTipoMedida(){
        if(listaTipoMedida.size > 0){
            val adapterTipoMedida= ArrayAdapter<String>(context ,android.R.layout.simple_expandable_list_item_1,listaTipoMedida)
            adapterTipoMedida.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            rootView.spTipoMedida5c.adapter=adapterTipoMedida
        }
    }

    private fun asignarListaDatosMedidaSocioeducativa(){
        if(listaMedidaSocioeducativa.size > 0){
            val adapterMedidaSocioeducativa= ArrayAdapter<String>(context ,android.R.layout.simple_expandable_list_item_1,listaMedidaSocioeducativa)
            adapterMedidaSocioeducativa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            rootView.spMedida5c.adapter=adapterMedidaSocioeducativa
        }
    }

    private fun asignarListaDatosMedidaCautelar(){
        if(listaMedidaCautelar.size > 0){
            val adapterMedidaCautelar= ArrayAdapter<String>(context ,android.R.layout.simple_expandable_list_item_1,listaMedidaCautelar)
            adapterMedidaCautelar.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            rootView.spMedida5c.adapter=adapterMedidaCautelar
        }
    }

    /****************************LISTA REPORTE 6 ***********************************************/
    private fun obtenerListaReporte5(){
        try{
            val servicio = ClienteApiRest.getRetrofitInstance().create(ReporteServicio::class.java)
            val call = servicio.obtenerReporteMedidaSocioEducativaCAI(medidaBusqueda,"Bearer " + token)
            call.enqueue(object : Callback<List<Reporte4>>{
                override fun onFailure(call: Call<List<Reporte4>>, t: Throwable) {
                }
                override fun onResponse(call: Call<List<Reporte4>>, response: Response<List<Reporte4>>) {
                    if(response.code()==200){
                        val listaResultadosReporte5=response.body()
                        if(listaResultadosReporte5 != null){
                            rootView.txtSinReportes5c.visibility=View.GONE
                            rootView.rv_reporte_5c.visibility= View.VISIBLE
                            mostrarListadoReportes5(listaResultadosReporte5)
                        }
                    }
                    else{
                        rootView.txtSinReportes5c.visibility=View.VISIBLE
                        rootView.rv_reporte_5c.visibility= View.GONE
                    }
                }
            })
        }catch (e:Exception){
            Toast.makeText(context, "Ha ocurrido un error al obtener los datos tipo penal", Toast.LENGTH_SHORT).show()
        }

    }

    fun mostrarListadoReportes5(listaReportesReporte5: List<Reporte4>){
        if(listaReportesReporte5.size > 0){
            val recyclerViewReportes5=rootView.findViewById(R.id.rv_reporte_5c) as RecyclerView
            val adaptador = Reporte5Adaptador(listaReportesReporte5)
            recyclerViewReportes5.adapter=adaptador
            recyclerViewReportes5.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
        }

    }
}
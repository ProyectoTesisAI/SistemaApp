package ec.edu.epn.snai.Controlador.Fragment

import android.app.DatePickerDialog
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import ec.edu.epn.snai.Controlador.Adaptador.Reporte1Adaptador
import ec.edu.epn.snai.Controlador.Adaptador.Reporte2Adaptador
import ec.edu.epn.snai.Modelo.Reporte1
import ec.edu.epn.snai.Modelo.Reporte2
import ec.edu.epn.snai.Modelo.Reporte5
import ec.edu.epn.snai.R
import ec.edu.epn.snai.Servicios.ClienteApiRest
import ec.edu.epn.snai.Servicios.ReporteServicio
import kotlinx.android.synthetic.main.fragment_resultados_reporte_3.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class Reporte3CAIFragment:Fragment() {

    private lateinit var token: String
    private lateinit var rootView: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if(arguments!=null){
            token=arguments?.getSerializable("token") as String

        }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view= inflater.inflate(R.layout.fragment_resultados_reporte_3, container, false)

        rootView=view

        val etFechaFutura=rootView.etFechaFutura
        etFechaFutura.setOnClickListener {
            dialogoFechaFutura(etFechaFutura)
        }

        rootView.btnReporte3.setOnClickListener {


            if (etFechaFutura.text.toString().isNotBlank()) {

                val sdf = SimpleDateFormat("dd/MM/yyyy")
                var fechaFutura: Date? = null
                fechaFutura = sdf.parse(etFechaFutura.text.toString())

                val fecha=Reporte5()

                fecha.fechaAprehension=fechaFutura
                obtenerListaReporte3(fecha)

            }

        }
        return rootView
    }

    private fun dialogoFechaFutura(etFechaFutura: EditText){
        val cldr = Calendar.getInstance()
        val diaSeleccionado = cldr.get(Calendar.DAY_OF_MONTH)
        val mesSeleccionado = cldr.get(Calendar.MONTH)
        val anioSeleccionado = cldr.get(Calendar.YEAR)
        // date picker dialog
        val picker = DatePickerDialog(context,

            DatePickerDialog.OnDateSetListener { datePicker, anio, mes, dia ->

                val mesAux=mes+1
                val mesFormateado: String
                //Formateo el año obtenido: antepone el 0 si son menores de 10
                if (mesAux < 10) {

                    mesFormateado = String.format("0$mesAux")
                }
                else{
                    mesFormateado = String.format("$mesAux")
                }

                var diaFormateado: String = dia.toString()
                //Formateo el año obtenido: antepone el 0 si son menores de 10
                if (dia < 10) {
                    diaFormateado = String.format("0$dia")
                }


                etFechaFutura.setText("$diaFormateado/$mesFormateado/$anio")

            }, anioSeleccionado, mesSeleccionado, diaSeleccionado
        )
        picker.show()

    }

    /****************************LISTA REPORTE 1 ***********************************************/
    private fun obtenerListaReporte3(fecha:Reporte5 ){

        try{
            val servicio = ClienteApiRest.getRetrofitInstance().create(ReporteServicio::class.java)
            val call = servicio.obtenerReporteEdadFechaCAI( fecha,"Bearer " + token)

            call.enqueue(object : Callback<List<Reporte2>>{

                override fun onFailure(call: Call<List<Reporte2>>, t: Throwable) {

                }

                override fun onResponse(call: Call<List<Reporte2>>, response: Response<List<Reporte2>>) {

                    if(response.code()==200){
                        val listaResultadosReporte3=response.body()

                        if(listaResultadosReporte3 != null){

                            rootView.txtSinReportes.visibility=View.GONE
                            rootView.rv_reporte_3.visibility= View.VISIBLE
                            mostrarListadoReportes3(listaResultadosReporte3)
                        }


                    }
                    else{
                        rootView.txtSinReportes.visibility=View.VISIBLE
                        rootView.rv_reporte_3.visibility= View.GONE
                    }
                }
            })

        }catch (e:Exception){
            Toast.makeText(context, "Ha ocurrido un error al obtener los registro del reporte", Toast.LENGTH_SHORT).show()
        }

    }

    fun mostrarListadoReportes3(listaReportesReporte2: List<Reporte2>){

        if(listaReportesReporte2.size > 0){
            val recyclerViewReportes1=rootView.findViewById(R.id.rv_reporte_3) as RecyclerView
            val adaptador = Reporte2Adaptador(listaReportesReporte2)
            recyclerViewReportes1.adapter=adaptador
            recyclerViewReportes1.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
        }

    }

}
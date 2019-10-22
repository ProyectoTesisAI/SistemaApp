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
import ec.edu.epn.snai.Controlador.Adaptador.Reporte6Adaptador
import ec.edu.epn.snai.Modelo.Reporte5
import ec.edu.epn.snai.R
import ec.edu.epn.snai.Servicios.ClienteApiRest
import ec.edu.epn.snai.Servicios.ReporteServicio
import kotlinx.android.synthetic.main.fragment_resultados_reporte_6.view.*

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class Reporte6CAIFragment:Fragment() {

    private lateinit var token: String
    private lateinit var rootView: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if(arguments!=null){
            token=arguments?.getSerializable("token") as String

        }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view= inflater.inflate(R.layout.fragment_resultados_reporte_6, container, false)

        rootView=view

        val etFechaIngreso=rootView.etFechaIngreso
        etFechaIngreso.setOnClickListener {
            dialogoFechaIngreso(etFechaIngreso)
        }

        rootView.btnReporte6.setOnClickListener {


            if (etFechaIngreso.text.toString().isNotBlank()) {

                val sdf = SimpleDateFormat("dd/MM/yyyy")
                var fechaFutura: Date? = null
                fechaFutura = sdf.parse(etFechaIngreso.text.toString())

                val fecha=Reporte5()
                fecha.fechaAprehension=fechaFutura
                obtenerListaReporte6(fecha)

            }

        }
        return rootView
    }

    private fun dialogoFechaIngreso(etFechaFutura: EditText){
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
    private fun obtenerListaReporte6(fecha:Reporte5 ){

        try{
            val servicio = ClienteApiRest.getRetrofitInstance().create(ReporteServicio::class.java)
            val call = servicio.obtenerReporteFechaIngresoCAI( fecha,"Bearer " + token)

            call.enqueue(object : Callback<List<Reporte5>>{

                override fun onFailure(call: Call<List<Reporte5>>, t: Throwable) {

                }

                override fun onResponse(call: Call<List<Reporte5>>, response: Response<List<Reporte5>>) {

                    if(response.code()==200){
                        val listaResultadosReporte6=response.body()

                        if(listaResultadosReporte6 != null){

                            rootView.txtSinReportes.visibility=View.GONE
                            rootView.rv_reporte_6.visibility= View.VISIBLE
                            mostrarListadoReportes6(listaResultadosReporte6)
                        }


                    }
                    else{
                        rootView.txtSinReportes.visibility=View.VISIBLE
                        rootView.rv_reporte_6.visibility= View.GONE
                    }
                }
            })

        }catch (e:Exception){
            Toast.makeText(context, "Ha ocurrido un error al obtener los registro del reporte", Toast.LENGTH_SHORT).show()
        }

    }

    fun mostrarListadoReportes6(listaReportesReporte6: List<Reporte5>){

        if(listaReportesReporte6.size > 0){
            val recyclerViewReportes6=rootView.findViewById(R.id.rv_reporte_6) as RecyclerView
            val adaptador = Reporte6Adaptador(listaReportesReporte6)
            recyclerViewReportes6.adapter=adaptador
            recyclerViewReportes6.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
        }

    }

}
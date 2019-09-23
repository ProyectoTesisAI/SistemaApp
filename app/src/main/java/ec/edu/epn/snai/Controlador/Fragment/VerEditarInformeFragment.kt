package ec.edu.epn.snai.Controlador.Fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ec.edu.epn.snai.Controlador.Adaptador.ItemInformeAdaptador
import ec.edu.epn.snai.Modelo.Informe
import ec.edu.epn.snai.Modelo.ItemTaller
import ec.edu.epn.snai.R
import ec.edu.epn.snai.Servicios.ClienteApiRest
import ec.edu.epn.snai.Servicios.TallerServicio
import kotlinx.android.synthetic.main.fragment_ver_informe.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class VerEditarInformeFragment : Fragment(){

    private lateinit var informeSeleccionado: Informe
    private lateinit var token:String

    private var adaptadorItemInforme: ItemInformeAdaptador?=null
    var itemsTaller: List<ItemTaller>?=null
    private lateinit var recyclerViewItemsInforme: RecyclerView

    val pattern = "dd/MM/yyyy"
    val simpleDateFormat = SimpleDateFormat(pattern)

    val patternHour = "HH:mm"
    val simpleHourFormat = SimpleDateFormat(patternHour)

    private lateinit var rootView: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if(arguments!=null){
            token =arguments?.getSerializable("token") as String
            informeSeleccionado = arguments?.getSerializable("informeSeleccionado") as Informe
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootView=inflater.inflate(R.layout.fragment_ver_informe,container,false)


        obtenerItems()
        return rootView
    }

    fun asignarVariablesTaller(){

        rootView.tvTemaTallerFr?.text = informeSeleccionado.idTaller.tema
        rootView.tvNumeroInformeFr?.text= informeSeleccionado.idTaller.numeroTaller.toString()
        val fecha = simpleDateFormat.format(informeSeleccionado.idTaller.fecha)
        rootView.tvFechaTallerFr?.text=fecha
        val horaInicio = simpleHourFormat.format(informeSeleccionado.idTaller.horaInicio)
        rootView.tvHoraInicioTallerFr?.text= horaInicio
        val horaFinObtener = obtenerHoraFin(itemsTaller as ArrayList<ItemTaller>)
        val horaFin=simpleHourFormat.format(horaFinObtener)
        rootView.tvHoraFinTallerFr?.text=horaFin
        rootView.tvNumeroParticipantesTallerFr?.text=informeSeleccionado.numeroAdolescentes.toString()
        rootView.etAntecendentesInformeFr?.setText(informeSeleccionado.adolescentesJustificacion)
        rootView.tvObjetivoTallerFr?.setText(informeSeleccionado.idTaller.objetivo)
        rootView.etDesarrolloInformeFr?.setText(informeSeleccionado.socializacionDesarrollo)
        rootView.etObjetivosEspecificosInformeFr?.setText(informeSeleccionado.socializacionObjetivos)
        rootView.etCierreInformeFr?.setText(informeSeleccionado.cierreEvaluacion)
        rootView.etConclusionesInformeFr?.setText(informeSeleccionado.conclusiones)
        rootView.etRecomendacionesInformeFr?.setText(informeSeleccionado.recomendaciones)
        rootView.etObservacionesInformeFr?.setText(informeSeleccionado.observaciones)

    }

    fun obtenerHoraFin(items:List<ItemTaller>): Date? {
        var horaFin: Date?=null
        if(informeSeleccionado.idTaller.horaInicio!=null){

            val duracionFinal=obtenerDuracionTaller(items)
            val horaAux = Calendar.getInstance()
            horaAux.time = informeSeleccionado.idTaller.horaInicio
            horaAux.add(Calendar.MINUTE, duracionFinal)
            horaFin=horaAux.time
            println(horaFin)
        }
        return horaFin
    }

    fun obtenerItems(){
        val servicio = ClienteApiRest.getRetrofitInstance().create(TallerServicio::class.java)

        if(informeSeleccionado.idTaller!=null ){
            val call = servicio.listarItemsPorTaller(informeSeleccionado.idTaller.idTaller.toString(),"Bearer "+ token)
            call.enqueue(object : Callback<List<ItemTaller>> {
                override fun onFailure(call: Call<List<ItemTaller>>, t: Throwable) {
                    call.cancel()
                }

                override fun onResponse(call: Call<List<ItemTaller>>, response: Response<List<ItemTaller>>) {
                    if (response.isSuccessful) {
                        itemsTaller = response.body()
                        asignarVariablesTaller()
                        adaptadorItemInforme= ItemInformeAdaptador(itemsTaller)
                        recyclerViewItemsInforme= rootView.findViewById<RecyclerView>(R.id.rv_items_informe_fr)
                        recyclerViewItemsInforme.adapter=adaptadorItemInforme
                        recyclerViewItemsInforme.layoutManager=
                            LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
                    }
                }
            })
        }
    }

    fun obtenerDuracionTaller(items:List<ItemTaller>):Int{
        var duracion=0
        if(!items.isNullOrEmpty()){
            items.forEach {
                duracion=duracion+it.duracion
            }
        }
        return duracion
    }
}
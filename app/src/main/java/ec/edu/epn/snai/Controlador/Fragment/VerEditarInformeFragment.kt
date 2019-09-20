package ec.edu.epn.snai.Controlador.Fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import ec.edu.epn.snai.Controlador.Adaptador.ItemInformeAdaptador
import ec.edu.epn.snai.Modelo.Informe
import ec.edu.epn.snai.Modelo.ItemTaller
import ec.edu.epn.snai.R
import ec.edu.epn.snai.Servicios.ClienteApiRest
import ec.edu.epn.snai.Servicios.TallerServicio
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class VerEditarInformeFragment : Fragment(){

    private var txtTemaTaller: TextView?=null
    private var txtNumeroTaller: TextView?=null
    private var txtFecha: TextView?=null
    private var txtHoraInicioTaller: TextView?=null
    private var txtHoraFinTaller: TextView?=null
    private var txtNumeroAdolescentesParticipantesInforme: TextView?=null
    private var txtObjetivoGeneralTaller: TextView?=null
    private var txtAntecedentesInforme: TextView?=null
    private var txtDesarrolloInforme: TextView?=null
    private var txtObjetivosEspecificos: TextView? = null
    private var txtCierreEvaluacion: TextView? = null
    private var txtConclusiones: TextView? = null
    private var txtRecomendaciones: TextView? = null
    private var txtObservaciones: TextView? = null

    private var duracion:Int = 0

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
        rootView=inflater.inflate(R.layout.fragment_ver_editar_informe,container,false)
        itemsTaller = ArrayList<ItemTaller>()
        obtenerItems()
        return rootView
    }

    fun graficarValores(){
        txtTemaTaller= rootView.findViewById(R.id.tvTemaTallerFr)
        txtNumeroTaller= rootView.findViewById(R.id.tvNumeroInformeFr)
        txtFecha= rootView.findViewById(R.id.tvFechaTallerFr)
        txtHoraInicioTaller= rootView.findViewById(R.id.tvHoraInicioTallerFr)
        txtHoraFinTaller= rootView.findViewById(R.id.tvHoraFinTallerFr)
        txtNumeroAdolescentesParticipantesInforme= rootView.findViewById(R.id.tvNumeroParticipantesTallerFr)
        txtObjetivoGeneralTaller= rootView.findViewById(R.id.tvObjetivoTallerFr)
        txtAntecedentesInforme= rootView.findViewById(R.id.etAntecendentesInformeFr)
        txtDesarrolloInforme= rootView.findViewById(R.id.etDesarrolloInformeFr)
        txtObjetivosEspecificos= rootView.findViewById(R.id.etObjetivosEspecificosInformeFr)
        txtCierreEvaluacion= rootView.findViewById(R.id.etCierreInformeFr)
        txtConclusiones= rootView.findViewById(R.id.etConclusionesInformeFr)
        txtRecomendaciones= rootView.findViewById(R.id.etRecomendacionesInformeFr)
        txtObservaciones= rootView.findViewById(R.id.etObservacionesInformeFr)

        txtTemaTaller?.text= informeSeleccionado.idTaller.tema
        txtNumeroTaller?.text= informeSeleccionado.idTaller.numeroTaller.toString()
        val fecha = simpleDateFormat.format(informeSeleccionado.idTaller.fecha)
        txtFecha?.text=fecha
        val horaInicio = simpleHourFormat.format(informeSeleccionado.idTaller.horaInicio)
        txtHoraInicioTaller?.text= horaInicio
        val horaFinObtener = obtenerHoraFin(itemsTaller as ArrayList<ItemTaller>)
        val horaFin=simpleHourFormat.format(horaFinObtener)
        txtHoraFinTaller?.text=horaFin
        txtNumeroAdolescentesParticipantesInforme?.text=informeSeleccionado?.numeroAdolescentes.toString()
        txtAntecedentesInforme?.text=informeSeleccionado.adolescentesJustificacion
        txtObjetivoGeneralTaller?.text=informeSeleccionado.idTaller.objetivo
        txtDesarrolloInforme?.text=informeSeleccionado?.socializacionDesarrollo
        txtObjetivosEspecificos?.text=informeSeleccionado?.socializacionObjetivos
        txtCierreEvaluacion?.text=informeSeleccionado?.cierreEvaluacion
        txtConclusiones?.text=informeSeleccionado?.conclusiones
        txtRecomendaciones?.text=informeSeleccionado?.recomendaciones
        txtObservaciones?.text=informeSeleccionado?.observaciones
    }

    fun obtenerHoraFin(items:List<ItemTaller>): Date? {
        var horaFin: Date?=null
        var duracionFinal:Int?=0
        duracionFinal=obtenerDuracionTaller(items) as Int
        if(informeSeleccionado.idTaller.horaInicio!=null){
            duracionFinal=obtenerDuracionTaller(items)
            val horaAux = Calendar.getInstance()
            horaAux.time = informeSeleccionado.idTaller.horaInicio
            horaAux.add(Calendar.MINUTE, duracionFinal as Int)
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
                        graficarValores()
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
        var dura=0
        if(!items.isNullOrEmpty()){
            items.forEach {
                dura=dura+it.duracion
            }
        }
        return dura
    }
}
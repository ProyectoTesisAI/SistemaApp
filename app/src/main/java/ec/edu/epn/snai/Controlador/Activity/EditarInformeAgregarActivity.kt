package ec.edu.epn.snai.Controlador.Activity

import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import ec.edu.epn.snai.R
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.content.Intent
import android.widget.*
import ec.edu.epn.snai.Controlador.Adaptador.ItemInformeAdaptador
import ec.edu.epn.snai.Modelo.*
import ec.edu.epn.snai.Servicios.ClienteApiRest
import ec.edu.epn.snai.Servicios.TallerServicio
import kotlinx.android.synthetic.main.activity_agregar_informe.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class EditarInformeAgregarActivity : AppCompatActivity(){

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

    private var numeroParticipantes=0

    private var duracion:Int = 0

    private lateinit var fabAgregarFotograficas:FloatingActionButton

    private var listaAdolescentesInfractores: List<AsistenciaAdolescente>?=null
    //private var listaFotos: List<RegistroFotografico>?=null
    private var listaActividadesTaller: List<ItemTaller>?=null
    private lateinit var informeSeleccionado: Informe
    private lateinit var token:String

    private var adaptadorItemInforme: ItemInformeAdaptador?=null
    var itemsTaller: List<ItemTaller>?=null
    private lateinit var recyclerViewItemsInforme: RecyclerView

    val pattern = "dd/MM/yyyy"
    val simpleDateFormat = SimpleDateFormat(pattern)

    val patternHour = "HH:mm"
    val simpleHourFormat = SimpleDateFormat(patternHour)

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agregar_informe)
        supportActionBar?.setDisplayHomeAsUpEnabled(true) //activo el botón Atrás}

        val i=intent
        this.informeSeleccionado = i.getSerializableExtra("informeSeleccionado") as Informe
        this.token = i.getSerializableExtra("token") as String
        //this.listaFotos=i.getSerializableExtra("listaFotos") as ArrayList<RegistroFotografico>
        this.listaActividadesTaller = i.getSerializableExtra("listaActividades") as ArrayList<ItemTaller>
        this.listaAdolescentesInfractores = i.getSerializableExtra("listaAsistencia") as ArrayList<AsistenciaAdolescente>
        this.numeroParticipantes=obtenerCantidadParticipantes()

        itemsTaller = ArrayList<ItemTaller>()
        itemsTaller=listaActividadesTaller
        graficarValores()
        adaptadorItemInforme= ItemInformeAdaptador(itemsTaller)
        recyclerViewItemsInforme= findViewById<RecyclerView>(R.id.rv_items_informe)
        recyclerViewItemsInforme.adapter=adaptadorItemInforme
        recyclerViewItemsInforme.layoutManager=LinearLayoutManager(applicationContext,LinearLayoutManager.VERTICAL,false)

        fabAgregarFotograficas=findViewById(R.id.fab_agregar_fotograficas_informe)
        fabAgregarFotograficas.setOnClickListener {
            abrirRegistroFotografico()
        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_gestion, menu)

        menu.findItem(R.id.menu_editar).isVisible = false
        menu.findItem(R.id.menu_eliminar).isVisible=false
        menu.findItem(R.id.menu_guardar).isVisible=false
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        when(item?.itemId){
            R.id.menu_guardar->{

                finish()
            }
            else->{
                finish()
            }
        }
        return true
    }

    fun graficarValores(){
        txtTemaTaller= findViewById<TextView>(R.id.tvTemaTaller)
        txtNumeroTaller= findViewById<TextView>(R.id.tvNumeroInforme)
        txtFecha= findViewById<TextView>(R.id.tvFechaTaller)
        txtHoraInicioTaller= findViewById<TextView>(R.id.tvHoraInicioTaller)
        txtHoraFinTaller= findViewById<TextView>(R.id.tvHoraFinTaller)
        txtNumeroAdolescentesParticipantesInforme= findViewById<TextView>(R.id.tvNumeroParticipantesTaller)
        txtObjetivoGeneralTaller= findViewById<TextView>(R.id.tvObjetivoTaller)
        txtAntecedentesInforme= findViewById<EditText>(R.id.etAntecendentesInforme)
        txtDesarrolloInforme= findViewById<EditText>(R.id.etDesarrolloInforme)
        txtObjetivosEspecificos= findViewById<EditText>(R.id.etObjetivosEspecificosInforme)
        txtCierreEvaluacion= findViewById<EditText>(R.id.etCierreInforme)
        txtConclusiones= findViewById<EditText>(R.id.etConclusionesInforme)
        txtRecomendaciones= findViewById<EditText>(R.id.etRecomendacionesInforme)
        txtObservaciones= findViewById<EditText>(R.id.etObservacionesInforme)

        txtTemaTaller?.text= informeSeleccionado.idTaller.tema
        txtNumeroTaller?.text= informeSeleccionado.idTaller.numeroTaller.toString()
        val fecha = simpleDateFormat.format(informeSeleccionado.fecha)
        txtFecha?.text=fecha
        val horaInicio = simpleHourFormat.format(informeSeleccionado.horaInicio)
        txtHoraInicioTaller?.text= horaInicio
        val horaFinObtener = obtenerHoraFin(itemsTaller as ArrayList<ItemTaller>)
        val horaFin=simpleHourFormat.format(horaFinObtener)
        txtHoraFinTaller?.text=horaFin
        //txtNumeroAdolescentesParticipantesInforme?.text=informeSeleccionado?.numeroAdolescentes.toString()
        txtNumeroAdolescentesParticipantesInforme?.text=numeroParticipantes.toString()
        txtObjetivoGeneralTaller?.text=informeSeleccionado?.idTaller.objetivo
        txtAntecedentesInforme?.text=informeSeleccionado?.adolescentesJustificacion
        txtDesarrolloInforme?.text=informeSeleccionado?.socializacionDesarrollo
        txtObjetivosEspecificos?.text=informeSeleccionado?.socializacionObjetivos
        txtCierreEvaluacion?.text=informeSeleccionado?.cierreEvaluacion
        txtConclusiones?.text=informeSeleccionado?.conclusiones
        txtRecomendaciones?.text=informeSeleccionado?.recomendaciones
        txtObservaciones?.text=informeSeleccionado?.observaciones
    }

    fun obtenerHoraFin(items:List<ItemTaller>):Date? {
        var horaFin:Date?=null
        var duracionFinal:Int?=0
        duracionFinal=obtenerDuracionTaller(items) as Int
        if(informeSeleccionado.horaInicio!=null){
            duracionFinal=obtenerDuracionTaller(items)
            val horaAux = Calendar.getInstance()
            horaAux.time = informeSeleccionado.horaInicio
            horaAux.add(Calendar.MINUTE, duracionFinal as Int)
            horaFin=horaAux.time
            println(horaFin)
        }
        return horaFin
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

    fun obtenerCantidadParticipantes():Int{
        var cantidad=0
        if(!listaAdolescentesInfractores.isNullOrEmpty()){
            listaAdolescentesInfractores!!.forEach{
                if(it.asistio==true){
                    cantidad++
                }
            }
        }
        return cantidad
    }

    fun obtenerVariablesInforme():Informe?{
        if(informeSeleccionado!=null){
            val informeAux = informeSeleccionado

            informeAux.numeroAdolescentes=numeroParticipantes
            informeAux.objetivoGeneral=tvObjetivoTaller?.text.toString()
            informeAux.adolescentesJustificacion= etAntecendentesInforme?.text.toString()
            informeAux.socializacionDesarrollo= etDesarrolloInforme?.text.toString()
            informeAux.socializacionObjetivos= etObjetivosEspecificosInforme?.text.toString()
            informeAux.cierreEvaluacion= etCierreInforme?.text.toString()
            informeAux.conclusiones= etConclusionesInforme?.text.toString()
            informeAux.recomendaciones= etRecomendacionesInforme?.text.toString()
            informeAux.observaciones= etObservacionesInforme?.text.toString()

            return informeAux
        }else{
            return null
        }
    }

    private fun abrirRegistroFotografico(){

        val informeNuevo=obtenerVariablesInforme()

        if(informeNuevo != null){

            if(!informeNuevo.adolescentesJustificacion.isNullOrBlank() && !informeNuevo.socializacionDesarrollo.isNullOrBlank() && !informeNuevo.socializacionObjetivos.isNullOrBlank() && !informeNuevo.cierreEvaluacion.isNullOrBlank() && !informeNuevo.conclusiones.isNullOrBlank() ){
                val intent = Intent(this@EditarInformeAgregarActivity, EditarRegistroFotograficoActivity::class.java)
                intent.putExtra("token",token)
                intent.putExtra("informeSeleccionado", obtenerVariablesInforme())
                intent.putExtra("listaActividades", java.util.ArrayList(listaActividadesTaller))
                intent.putExtra("listaAsistencia", java.util.ArrayList(listaAdolescentesInfractores))
                startActivity(intent)
            }
            else{
                Toast.makeText(applicationContext, "Antecedenes  y justificación, Desarrollo, Objetivos Específicos, Cierre y evaluación y Conclusiones son campos obligatotios, ingrese un valor", Toast.LENGTH_SHORT).show()
            }
        }
    }
}


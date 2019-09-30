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
import kotlinx.android.synthetic.main.activity_agregar_informe.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class AgregarInformeActivity : AppCompatActivity(){

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
    private lateinit var tallerSeleccionado: Taller
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
        this.tallerSeleccionado = i.getSerializableExtra("tallerSeleccionado") as Taller
        this.token = i.getSerializableExtra("token") as String
        //this.listaFotos=i.getSerializableExtra("listaFotos") as ArrayList<RegistroFotografico>
        this.listaActividadesTaller = i.getSerializableExtra("listaActividades") as ArrayList<ItemTaller>
        this.listaAdolescentesInfractores = i.getSerializableExtra("listaAsistencia") as ArrayList<AsistenciaAdolescente>
        println("Lista de adolescentes que fueron enviados desde el Registro Asistencia"+listaAdolescentesInfractores)
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
            var informeNuevo:Informe?=null
            informeNuevo=obtenerVariablesInforme()
            println("informe que se va a mandar: "+informeNuevo)
            val intent = Intent(this@AgregarInformeActivity, AgregarRegistroFotograficoActivity::class.java)
            intent.putExtra("token",token)
            intent.putExtra("tallerSeleccionado",tallerSeleccionado)
            intent.putExtra("informeNuevo", informeNuevo)
            intent.putExtra("listaActividades", java.util.ArrayList(listaActividadesTaller))
            intent.putExtra("listaAsistencia", java.util.ArrayList(listaAdolescentesInfractores))
            //intent.putExtra("listaFotos", java.util.ArrayList(listaFotos))
            startActivity(intent)
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

        txtTemaTaller?.text= tallerSeleccionado.tema
        txtNumeroTaller?.text= tallerSeleccionado.numeroTaller.toString()
        val fecha = simpleDateFormat.format(tallerSeleccionado.fecha)
        txtFecha?.text=fecha
        val horaInicio = simpleHourFormat.format(tallerSeleccionado.horaInicio)
        txtHoraInicioTaller?.text= horaInicio
        val horaFinObtener = obtenerHoraFin(itemsTaller as ArrayList<ItemTaller>)
        val horaFin=simpleHourFormat.format(horaFinObtener)
        txtHoraFinTaller?.text=horaFin
        txtNumeroAdolescentesParticipantesInforme?.text=numeroParticipantes.toString()
        txtObjetivoGeneralTaller?.text=tallerSeleccionado?.objetivo
        /*txtAntecedentesInforme?.text=tallerSeleccionado?.adolescentesJustificacion
        txtDesarrolloInforme?.text=tallerSeleccionado?.socializacionDesarrollo
        txtObjetivosEspecificos?.text=tallerSeleccionado?.socializacionObjetivos
        txtCierreEvaluacion?.text=tallerSeleccionado?.cierreEvaluacion
        txtConclusiones?.text=tallerSeleccionado?.conclusiones
        txtRecomendaciones?.text=tallerSeleccionado?.recomendaciones
        txtObservaciones?.text=tallerSeleccionado?.observaciones*/
    }

    fun obtenerHoraFin(items:List<ItemTaller>):Date? {
        var horaFin:Date?=null
        var duracionFinal:Int?=0
        duracionFinal=obtenerDuracionTaller(items) as Int
        if(tallerSeleccionado.horaInicio!=null){
            duracionFinal=obtenerDuracionTaller(items)
            val horaAux = Calendar.getInstance()
            horaAux.time = tallerSeleccionado.horaInicio
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
        if(tallerSeleccionado!=null){
            var informeAux : Informe
            informeAux=Informe()

            informeAux.idTaller=tallerSeleccionado
            informeAux.fecha=tallerSeleccionado.fecha
            informeAux.horaInicio=tallerSeleccionado.horaInicio
            val horaFinObtener = obtenerHoraFin(itemsTaller as ArrayList<ItemTaller>)
            informeAux.horaFin=horaFinObtener

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
}


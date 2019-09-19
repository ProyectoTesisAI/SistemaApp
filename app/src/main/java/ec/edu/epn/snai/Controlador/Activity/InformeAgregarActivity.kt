package ec.edu.epn.snai.Controlador.Activity

import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import ec.edu.epn.snai.R
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.widget.*
import ec.edu.epn.snai.Controlador.Adaptador.InformeAdaptador
import ec.edu.epn.snai.Controlador.Adaptador.ItemInformeAdaptador
import ec.edu.epn.snai.Modelo.*
import ec.edu.epn.snai.Servicios.ClienteApiRest
import ec.edu.epn.snai.Servicios.TallerServicio
import kotlinx.android.synthetic.main.activity_agregar_taller.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class InformeAgregarActivity : AppCompatActivity(){

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

    private lateinit var fabAgregarFotograficas:FloatingActionButton

    private lateinit var tallerActual: Taller
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

        token = intent.getSerializableExtra("token") as String
        tallerActual = intent.getSerializableExtra("tallerActual") as Taller

        itemsTaller = ArrayList<ItemTaller>()
        obtenerItems()
        fabAgregarFotograficas=findViewById(R.id.fab_agregar_fotograficas_informe)
        fabAgregarFotograficas.setOnClickListener {
            val intent = Intent(this@InformeAgregarActivity, VerRegistroAsistenciaActivity::class.java)
            intent.putExtra("tallerActual", tallerActual)
            intent.putExtra("token", token)
            startActivity(intent)
        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_gestion, menu)

        menu.findItem(R.id.menu_editar).isVisible = false
        menu.findItem(R.id.menu_eliminar).isVisible=false
        menu.findItem(R.id.menu_guardar).isVisible=true
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

        txtTemaTaller?.text= tallerActual.tema
        txtNumeroTaller?.text= tallerActual.numeroTaller.toString()
        val fecha = simpleDateFormat.format(tallerActual.fecha)
        txtFecha?.text=fecha
        val horaInicio = simpleHourFormat.format(tallerActual.horaInicio)
        txtHoraInicioTaller?.text= horaInicio
        val horaFinObtener = obtenerHoraFin(itemsTaller as ArrayList<ItemTaller>)
        val horaFin=simpleHourFormat.format(horaFinObtener)
        txtHoraFinTaller?.text=horaFin
        //txtNumeroAdolescentesParticipantesInforme?.text=informe?.numeroAdolescentes.toString()
        txtObjetivoGeneralTaller?.text=tallerActual?.objetivo
        /*txtDesarrolloInforme?.text=informe?.socializacionDesarrollo
        txtObjetivosEspecificos?.text=informe?.socializacionObjetivos
        txtCierreEvaluacion?.text=informe?.cierreEvaluacion
        txtConclusiones?.text=informe?.conclusiones
        txtRecomendaciones?.text=informe?.recomendaciones
        txtObservaciones?.text=informe?.observaciones*/
    }

    fun mostrarListaItemsTaller(listaItemsTaller: List<ItemTaller>){
        var adaptadorItemInforme = ItemInformeAdaptador(listaItemsTaller)
        var recyclerViewItemTaller = findViewById<RecyclerView>(R.id.rv_items_informe)
        recyclerViewItemTaller.adapter=adaptadorItemInforme
        recyclerViewItemTaller.layoutManager=LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL,false)

    }

    fun obtenerHoraFin(items:List<ItemTaller>):Date? {
        var horaFin:Date?=null
        var duracionFinal:Int?=0
        duracionFinal=obtenerDuracionTaller(items) as Int
        if(tallerActual.horaInicio!=null){
            duracionFinal=obtenerDuracionTaller(items)
            val horaAux = Calendar.getInstance()
            horaAux.time = tallerActual.horaInicio
            horaAux.add(Calendar.MINUTE, duracionFinal as Int)
            horaFin=horaAux.time
            println(horaFin)
        }
        return horaFin
    }

    fun obtenerItems(){
        val servicio = ClienteApiRest.getRetrofitInstance().create(TallerServicio::class.java)

        if(tallerActual!=null ){
            val call = servicio.listarItemsPorTaller(tallerActual.idTaller.toString(),"Bearer "+ token)
            call.enqueue(object : Callback<List<ItemTaller>> {
                override fun onFailure(call: Call<List<ItemTaller>>, t: Throwable) {
                    call.cancel()
                }

                override fun onResponse(call: Call<List<ItemTaller>>, response: Response<List<ItemTaller>>) {
                    if (response.isSuccessful) {
                        itemsTaller = response.body()
                        graficarValores()
                        adaptadorItemInforme= ItemInformeAdaptador(itemsTaller)
                        recyclerViewItemsInforme= findViewById<RecyclerView>(R.id.rv_items_informe)
                        recyclerViewItemsInforme.adapter=adaptadorItemInforme
                        recyclerViewItemsInforme.layoutManager=LinearLayoutManager(applicationContext,LinearLayoutManager.VERTICAL,false)
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


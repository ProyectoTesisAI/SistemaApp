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


class EditarInformeAgregarActivity : AppCompatActivity(){

    private var numeroParticipantes=0

    private var listaAsistenciaAdolescentes: List<AsistenciaAdolescente>?=null
    private var itemsTaller: List<ItemTaller>?=null
    private lateinit var informeSeleccionado: Informe
    private lateinit var token:String


    val patternHour = "HH:mm"
    val simpleHourFormat = SimpleDateFormat(patternHour)

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agregar_informe)
        supportActionBar?.setDisplayHomeAsUpEnabled(true) //activo el botón Atrás}

        val i=intent
        this.informeSeleccionado = i.getSerializableExtra("informeSeleccionado") as Informe
        this.token = i.getSerializableExtra("token") as String
        this.itemsTaller = i.getSerializableExtra("listaActividades") as ArrayList<ItemTaller>
        this.listaAsistenciaAdolescentes = i.getSerializableExtra("listaAsistencia") as ArrayList<AsistenciaAdolescente>
        this.numeroParticipantes=obtenerCantidadParticipantes()

        asignarValoresInforme()

        fab_agregar_fotograficas_informe.setOnClickListener {
            abrirRegistroFotografico()
        }

    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        finish()
        return true
    }

    fun asignarValoresInforme(){

        tvTemaTaller?.text= informeSeleccionado.idTaller.tema
        tvNumeroInforme?.text= informeSeleccionado.idTaller.numeroTaller.toString()

        val pattern = "dd/MM/yyyy"
        val simpleDateFormat = SimpleDateFormat(pattern)
        val fecha = simpleDateFormat.format(informeSeleccionado.fecha)
        tvFechaTaller?.text=fecha

        val horaInicio = simpleHourFormat.format(informeSeleccionado.horaInicio)
        tvHoraInicioTaller?.text= horaInicio

        val horaFinObtener = obtenerHoraFin(itemsTaller as ArrayList<ItemTaller>)
        val horaFin=simpleHourFormat.format(horaFinObtener)
        tvHoraFinTaller?.text=horaFin

        tvNumeroParticipantesTaller?.text=numeroParticipantes.toString()
        tvObjetivoTaller?.text=informeSeleccionado.idTaller.objetivo

        etAntecendentesInforme.setText(informeSeleccionado.adolescentesJustificacion)
        etDesarrolloInforme?.setText(informeSeleccionado.socializacionDesarrollo)
        etObjetivosEspecificosInforme?.setText(informeSeleccionado.socializacionObjetivos)
        etCierreInforme?.setText(informeSeleccionado.cierreEvaluacion)
        etConclusionesInforme?.setText(informeSeleccionado.conclusiones)
        etRecomendacionesInforme?.setText(informeSeleccionado.recomendaciones)
        etObservacionesInforme?.setText(informeSeleccionado.observaciones)

        asignarItemsTallerRecyclerView()
    }

    private fun asignarItemsTallerRecyclerView(){

        if(itemsTaller != null){

            if(itemsTaller?.size!! >0){
                val adaptadorItemInforme= ItemInformeAdaptador(itemsTaller)
                val recyclerViewItemsInforme= findViewById<RecyclerView>(R.id.rv_items_informe)
                recyclerViewItemsInforme.adapter=adaptadorItemInforme
                recyclerViewItemsInforme.layoutManager= LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL,false)
            }

        }
    }


    private fun obtenerHoraFin(items:List<ItemTaller>):Date? {
        var horaFin:Date?=null

        if(informeSeleccionado.horaInicio!=null){

            val duracionFinal=obtenerDuracionTaller(items)
            val horaAux = Calendar.getInstance()
            horaAux.time = informeSeleccionado.horaInicio
            horaAux.add(Calendar.MINUTE, duracionFinal as Int)
            horaFin=horaAux.time
            println(horaFin)
        }
        return horaFin
    }


    private fun obtenerDuracionTaller(items:List<ItemTaller>):Int{
        var duracion=0
        if(!items.isNullOrEmpty()){
            items.forEach {
                duracion=duracion+it.duracion
            }
        }
        return duracion
    }

    private fun obtenerCantidadParticipantes():Int{
        var cantidad=0
        if(!listaAsistenciaAdolescentes.isNullOrEmpty()){
            listaAsistenciaAdolescentes!!.forEach{
                if(it.asistio==true){
                    cantidad++
                }
            }
        }
        return cantidad
    }

    private fun obtenerVariablesInforme():Informe?{

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
                intent.putExtra("listaAsistencia", java.util.ArrayList(listaAsistenciaAdolescentes))
                startActivity(intent)
            }
            else{
                Toast.makeText(applicationContext, "Antecedenes  y justificación, Desarrollo, Objetivos Específicos, Cierre y evaluación y Conclusiones son campos obligatotios, ingrese un valor", Toast.LENGTH_SHORT).show()
            }
        }
    }
}


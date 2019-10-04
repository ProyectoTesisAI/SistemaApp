package ec.edu.epn.snai.Controlador.Activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import ec.edu.epn.snai.R
import android.support.v7.widget.RecyclerView
import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import ec.edu.epn.snai.Controlador.Adaptador.ItemInformeAdaptador
import ec.edu.epn.snai.Modelo.*
import kotlinx.android.synthetic.main.activity_agregar_informe.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class CrearInformeActivity : AppCompatActivity(){

    private var numeroParticipantes=0

    private var listaAsistenciaAdolescentes: List<AsistenciaAdolescente>?=null
    private var itemsTaller: List<ItemTaller>?=null
    private lateinit var tallerSeleccionado: Taller
    private lateinit var token:String

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agregar_informe)
        supportActionBar?.setDisplayHomeAsUpEnabled(true) //activo el botón Atrás}

        val i=intent
        this.tallerSeleccionado = i.getSerializableExtra("tallerSeleccionado") as Taller
        this.token = i.getSerializableExtra("token") as String
        this.itemsTaller = i.getSerializableExtra("listaActividades") as ArrayList<ItemTaller>
        this.listaAsistenciaAdolescentes = i.getSerializableExtra("listaAsistencia") as ArrayList<AsistenciaAdolescente>
        this.numeroParticipantes=obtenerCantidadParticipantes()

        asignarValoresTaller()

        fab_agregar_fotograficas_informe.setOnClickListener {
            abrirRegistroFotografico()
        }

    }


    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        finish()
        return true
    }

    private fun asignarValoresTaller(){

        tvTemaTaller?.text= tallerSeleccionado.tema
        tvNumeroInforme?.text= tallerSeleccionado.numeroTaller.toString()

        val pattern = "dd/MM/yyyy"
        val simpleDateFormat = SimpleDateFormat(pattern)
        val fecha = simpleDateFormat.format(tallerSeleccionado.fecha)
        tvFechaTaller?.text=fecha

        val patternHour = "HH:mm"
        val simpleHourFormat = SimpleDateFormat(patternHour)
        val horaInicio = simpleHourFormat.format(tallerSeleccionado.horaInicio)
        tvHoraInicioTaller?.text= horaInicio

        val horaFinObtener = obtenerHoraFin(itemsTaller as ArrayList<ItemTaller>)
        val horaFin=simpleHourFormat.format(horaFinObtener)
        tvHoraFinTaller.text=horaFin

        tvNumeroParticipantesTaller?.text=numeroParticipantes.toString()
        tvObjetivoTaller?.text=tallerSeleccionado.objetivo

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

        if(tallerSeleccionado.horaInicio!=null){

            val duracionFinal=obtenerDuracionTaller(items)
            val horaAux = Calendar.getInstance()
            horaAux.time = tallerSeleccionado.horaInicio
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

        if(tallerSeleccionado!=null){

            val informeAux =Informe()

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

    private fun abrirRegistroFotografico(){

        val informeNuevo=obtenerVariablesInforme()

        if(informeNuevo != null){

            if(!informeNuevo.adolescentesJustificacion.isNullOrBlank() && !informeNuevo.socializacionDesarrollo.isNullOrBlank() && !informeNuevo.socializacionObjetivos.isNullOrBlank() && !informeNuevo.cierreEvaluacion.isNullOrBlank() && !informeNuevo.conclusiones.isNullOrBlank() ){

                val intent = Intent(this@CrearInformeActivity, CrearRegistroFotograficoActivity::class.java)
                intent.putExtra("token",token)
                intent.putExtra("informeNuevo", informeNuevo)
                intent.putExtra("listaAsistencia", ArrayList(listaAsistenciaAdolescentes))
                startActivity(intent)
            }
            else{
                Toast.makeText(applicationContext, "Antecedenes  y justificación, Desarrollo, Objetivos Específicos, Cierre y evaluación y Conclusiones son campos obligatotios, ingrese un valor", Toast.LENGTH_SHORT).show()
            }
        }
    }
}


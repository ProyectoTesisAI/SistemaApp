package ec.edu.epn.snai.Controlador.Activity

import android.app.Activity
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import ec.edu.epn.snai.Controlador.Adaptador.ItemTallerAdaptador
import ec.edu.epn.snai.Modelo.*
import ec.edu.epn.snai.R
import ec.edu.epn.snai.Servicios.CaiServicio
import ec.edu.epn.snai.Servicios.ClienteApiRest
import ec.edu.epn.snai.Servicios.TallerServicio
import ec.edu.epn.snai.Servicios.UzdiServicio
import kotlinx.android.synthetic.main.activity_agregar_taller.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList

class EditarTallerActivity : AppCompatActivity(),ItemTallerAdaptador.ItemTallerOnItemClickListener{


    private lateinit var fabItemsTallers: FloatingActionButton
    var spCentro:Spinner?=null

    private var itemsTaller: ArrayList<ItemTaller> =ArrayList<ItemTaller>()
    private var listaUZDI: List<UDI>?=null
    private var listaCAI: List<CAI>?= null
    private lateinit var token:String
    private lateinit var taller:Taller
    private var posicionActividadSeleccionada: Int = 0
    private lateinit var menuAux:Menu

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_agregar_taller)
        supportActionBar?.setDisplayHomeAsUpEnabled(true) //activo el botón Atrás

        val i = intent
        this.taller = i.getSerializableExtra("taller_seleccionado") as Taller
        this.token = i.getSerializableExtra("token") as String
        this.itemsTaller=i.getSerializableExtra("items_taller_seleccionado") as ArrayList<ItemTaller>

        asynTaskObtenerListadoCai()
        asynTaskObtenerListadoUzdi()

        fabItemsTallers=findViewById(R.id.fab_agregar_item_taller)
        fabItemsTallers.setOnClickListener {
            dialogoAgregarActividadTaller()
        }

        val etFechaTaller=findViewById<EditText>(R.id.etFechaTallerCrear)
        etFechaTaller.setOnClickListener {
            dialogoFechaTaller(etFechaTaller)
        }

        val etHoraTaller=findViewById<EditText>(R.id.etHoraTallerCrear)
        etHoraTaller.setOnClickListener {
            dialogoObtenerHora(etHoraTaller)
        }


        val spTipoCentro: Spinner =findViewById<Spinner>(R.id.spTipoCentro)

        //Adaptador del Tipo de Centro para el Spinner
        val adapterTipoCentro=
            ArrayAdapter<String>(this@EditarTallerActivity,android.R.layout.simple_expandable_list_item_1,resources.getStringArray(R.array.tipoCentro))
        adapterTipoCentro.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spTipoCentro.adapter=adapterTipoCentro

        spCentro=findViewById(R.id.spUdiCai)

        spTipoCentro.setSelection(obtenerItemTipoCentro())

        //Evento itemSelected del Spinner Tipo de Centro
        spTipoCentro.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, posicion: Int, p3: Long) {

                if(posicion==0){
                    asignarListaUzdiSpinner()
                    spCentro?.setSelection( obtenerItemUzdiCai())
                    itemSelectedPorCentroUzdiCai(0)

                }
                if(posicion==1){
                    asignarListaCaiSpinner()
                    spCentro?.setSelection( obtenerItemUzdiCai())
                    itemSelectedPorCentroUzdiCai(1)
                }
            }

        }

        asignarVariablesTaller()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {

        menuAux=menu
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


    private fun asignarListaUzdiSpinner(){

        val listaUZDIAux : MutableList<String> = ArrayList<String>()
        for (u in listaUZDI!!){
            listaUZDIAux.add(u.udi)
        }


        val adapterTipoCentro=ArrayAdapter<String>(this@EditarTallerActivity,android.R.layout.simple_expandable_list_item_1,listaUZDIAux)
        adapterTipoCentro.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spCentro?.adapter=adapterTipoCentro

    }

    private fun  asignarListaCaiSpinner(){
        val listaCAIAux:MutableList<String> = ArrayList<String>()
        for (c in listaCAI!!){
            listaCAIAux.add(c.cai)
        }

        val adapterTipoCentro=ArrayAdapter<String>(this@EditarTallerActivity,android.R.layout.simple_expandable_list_item_1,listaCAIAux)
        adapterTipoCentro.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spCentro?.adapter=adapterTipoCentro

    }

    private fun obtenerListadoUZDI(): List<UDI>{

        val servicioUzdi= ClienteApiRest.getRetrofitInstance().create(UzdiServicio::class.java)
        val call =servicioUzdi.obtenerListaUZDI("Bearer $token")
        val listadoUzdi = call.execute().body()
        return  listadoUzdi!!

    }

    private fun obtenerListadoCAI(): List<CAI>{

        val servicioCai= ClienteApiRest.getRetrofitInstance().create(CaiServicio::class.java)
        val call =servicioCai.obtenerListaCAI("Bearer $token")
        val listadoCai = call.execute().body()
        return  listadoCai!!
    }

    private fun asynTaskObtenerListadoUzdi(){

        val miclase = object : AsyncTask<Unit, Unit, List<UDI>>(){

            override fun doInBackground(vararg p0: Unit?): List<UDI> {
                val listadoUzdi=obtenerListadoUZDI()
                return listadoUzdi
            }

        }
        listaUZDI= miclase.execute().get()
    }

    private fun asynTaskObtenerListadoCai(){

        val task = object : AsyncTask<Unit, Unit, List<CAI>>(){


            override fun doInBackground(vararg p0: Unit?): List<CAI> {
                val listadoCai=obtenerListadoCAI()
                return listadoCai
            }

        }
        listaCAI= task.execute().get()
    }

    private fun itemSelectedPorCentroUzdiCai(posicionTipoCentro: Int){

        //Evento itemSelected del Spinner Tipo de Centro
        spCentro?.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, posicion: Int, p3: Long) {

                if(posicionTipoCentro==0){
                    obtenerNumeroParticipanteUZDI(listaUZDI?.get(posicion))

                }
                if(posicionTipoCentro==1){
                    obtenerNumeroParticipanteCAI(listaCAI?.get(posicion))

                }

            }

        }
    }

    fun obtenerNumeroParticipanteUZDI(uzdi: UDI?){

        val servicioTaller= ClienteApiRest.getRetrofitInstance().create(TallerServicio::class.java)
        val call =servicioTaller.obtenerNumeroParticipantesUZDI(uzdi, "Bearer $token")

        call.enqueue(object : Callback<String> {


            override fun onResponse(call: Call<String>, response: Response<String>) {

                if (response.isSuccessful) {

                    var numeroParticipantes=response.body()
                    if(numeroParticipantes==null){
                        numeroParticipantes="0"
                    }
                    txtNumeroParticipantesTallerCrear.text= "Número de Adolescentes: $numeroParticipantes"

                }

            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                call.cancel()

            }
        })

    }

    private fun obtenerNumeroParticipanteCAI(cai: CAI?){

        val servicioTaller= ClienteApiRest.getRetrofitInstance().create(TallerServicio::class.java)
        val call =servicioTaller.obtenerNumeroParticipantesCAI(cai,"Bearer $token")

        call.enqueue(object : Callback<String> {

            override fun onResponse(call: Call<String>, response: Response<String>) {

                if (response.isSuccessful) {

                    var numeroParticipantes=response.body()

                    if(numeroParticipantes==null){
                        numeroParticipantes="0"
                    }
                    txtNumeroParticipantesTallerCrear.text= "Número de Adolescentes: $numeroParticipantes"

                }

            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                call.cancel()

            }
        })

    }

    private fun dialogoAgregarActividadTaller(){

        //Creo el builder perteneciente al cuadro de dialogo
        val builder = AlertDialog.Builder(this)

        //obtengo la vista o layout del dialogo
        val view = layoutInflater.inflate(R.layout.dialog_activity_taller, null)

        //añado la vista al builder
        builder.setView(view)

        //creo que el cuadro de dialogo
        val dialogo : AlertDialog = builder.create()
        dialogo.show()

        val etActividad= view.findViewById<EditText?>(R.id.etActividad)
        val etObjetivo=view.findViewById<EditText?>(R.id.etObjetivo)
        val etMateriales=view.findViewById<EditText?>(R.id.etMateriales)
        val etResponsable=view.findViewById<EditText?>(R.id.etResponsable)
        val etDuracion=view.findViewById<EditText?>(R.id.etDuracion)


        val btnAgregar=view.findViewById<Button>(R.id.btnAgregar)
        btnAgregar.setOnClickListener {

            if(etActividad?.text.isNullOrBlank()|| etResponsable?.text.isNullOrBlank() || etDuracion?.text.isNullOrEmpty() ){
                Toast.makeText(getApplicationContext(),"Actividad, Responsable y Duración es requerido, ingrese un valor",Toast.LENGTH_SHORT).show();
                dialogo.dismiss()

            }
            else{

                var actividadTaller= ItemTaller()
                actividadTaller.actividad=etActividad?.text.toString()
                actividadTaller.objetivoEspecifico=etObjetivo?.text.toString()
                actividadTaller.materiales=etMateriales?.text.toString()
                actividadTaller .responsable=etResponsable?.text.toString()
                actividadTaller.duracion= etDuracion?.text.toString().toInt()

                dialogo.dismiss()

                itemsTaller.add(actividadTaller)

                mostrarListaItemsTaller(itemsTaller)

            }

        }

        val btnCancelar=view.findViewById<Button>(R.id.btnCancelar)
        btnCancelar.setOnClickListener {
            dialogo.dismiss();
        }

    }

    private fun dialogoObtenerHora(etHoraTaller: EditText){

        //Calendario para obtener fecha & hora
        val c = Calendar.getInstance()

        //Variables para obtener la hora hora
        val horaActual = c.get(Calendar.HOUR_OF_DAY)
        val minutoActual = c.get(Calendar.MINUTE)

        val obtenerHora = TimePickerDialog(this,  TimePickerDialog.OnTimeSetListener() {

                view, hora, minuto ->

            var horaFormateada:String=hora.toString()
            //Formateo la hora obtenido: antepone el 0 si son menores de 10
            if(hora<10){
                horaFormateada= String.format("0"+hora)
            }

            //Formateo el minuto obtenido: antepone el 0 si son menores de 10
            var minutoFormateado = minuto.toString()
            if(minuto<10){
                minutoFormateado= String.format("0"+minuto)
            }

            //Muestro la hora con el formato deseado
            etHoraTaller.setText("$horaFormateada:$minutoFormateado")

            //Estos valores deben ir en ese orden
            //Al colocar en false se muestra en formato 12 horas y true en formato 24 horas
            //Pero el sistema devuelve la hora en formato 24 horas
        }, horaActual, minutoActual, false)

        obtenerHora.show()
    }

    fun dialogoFechaTaller(etFechaTaller: EditText){
        val cldr = Calendar.getInstance()
        val diaSeleccionado = cldr.get(Calendar.DAY_OF_MONTH)
        val mesSeleccionado = cldr.get(Calendar.MONTH)
        val anioSeleccionado = cldr.get(Calendar.YEAR)
        // date picker dialog
        val picker = DatePickerDialog(this@EditarTallerActivity,

            DatePickerDialog.OnDateSetListener { datePicker, anio, mes, dia ->

                var mesFormateado: String = mes.toString()
                //Formateo el año obtenido: antepone el 0 si son menores de 10
                if ((mes + 1) < 10) {
                    mesFormateado = String.format("0$mes")
                }

                var diaFormateado: String = dia.toString()
                //Formateo el año obtenido: antepone el 0 si son menores de 10
                if (dia < 10) {
                    diaFormateado = String.format("0$dia")
                }


                etFechaTaller.setText("$diaFormateado/$mesFormateado/$anio")

            }, anioSeleccionado, mesSeleccionado, diaSeleccionado
        )
        picker.show()

    }

    override fun OnItemClick(posicion: Int) {

        posicionActividadSeleccionada=posicion

        val intent = Intent(applicationContext, EditarActividadTallerActivity::class.java)
        intent.putExtra("actividad_seleccionada", itemsTaller.get(posicion))
        startActivityForResult(intent,1) //lanzo o ejecuto un nuevo activity, pero además espero una respuesta

    }

    //Método que se ejecuta una vez que obtengo una respuesta del activity EditarActividadTallerActivity, es decir, se ejecuta cuando
    // finaliza el activity  EditarActividadTallerActivity y manda una respuesta a través de un intent
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode==1){
            if(resultCode== Activity.RESULT_OK){
                val actividadRescatada: ItemTaller = data?.getSerializableExtra("actividad_rescatada") as ItemTaller

                itemsTaller.removeAt(posicionActividadSeleccionada)
                itemsTaller.add(actividadRescatada)

                mostrarListaItemsTaller(itemsTaller)

            }
            else if(resultCode== Activity.RESULT_FIRST_USER){

                itemsTaller.removeAt(posicionActividadSeleccionada)

                mostrarListaItemsTaller(itemsTaller)
            }
        }
    }

    fun mostrarListaItemsTaller(listaItemsTaller: List<ItemTaller>){
        val adaptadorItemTaller = ItemTallerAdaptador(listaItemsTaller,this@EditarTallerActivity)
        val recyclerViewItemTaller =findViewById (R.id.rv_items_taller) as RecyclerView
        recyclerViewItemTaller.adapter=adaptadorItemTaller
        recyclerViewItemTaller.layoutManager= LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL,false)

    }

    private fun asignarVariablesTaller(){

        etTemaTallerCrear?.setText(this.taller.tema)
        etNumeroTallerCrear?.setText(this.taller.numeroTaller.toString())
        etFechaTallerCrear?.setText( formatearFecha(this.taller.fecha))
        etHoraTallerCrear?.setText( formatearHora(this.taller.horaInicio))

        etObjetivoTallerCrear?.setText(this.taller.objetivo.toString())
        etRecomendacionesTallerCrear?.setText(this.taller.recomendaciones.toString())

        mostrarListaItemsTaller(itemsTaller)
    }

    private fun formatearFecha(fecha: Date): String {
        val dia = fecha.day
        val mes = fecha.month
        var anio = fecha.year

        var diaString = dia.toString()
        if (dia < 10) {
            diaString = String.format("0$dia")
        }

        var mesString = dia.toString()
        if (mes < 10) {
            mesString = String.format("0$mes")
        }
        anio = anio + 1900
        val anioString = anio.toString()

        return "$diaString/$mesString/$anioString"
    }

    private fun formatearHora(horaInicio: Date): String {
        val hora = horaInicio.hours
        val minuto = horaInicio.minutes

        var hora_string = hora.toString()
        if (hora < 10) {
            hora_string = String.format("0$hora")
        }

        var minuto_string = minuto.toString()
        if (minuto < 10) {
            minuto_string = String.format("0$minuto")
        }
        return "$hora_string:$minuto_string"
    }

    private fun obtenerItemTipoCentro():Int {

        var posicionItem =0

        if(taller.idUdi!=null){

            posicionItem=0
        }
        else if(taller.idCai!= null){
            posicionItem=1

        }
        return posicionItem
    }


    private fun obtenerItemUzdiCai(): Int{

        var posicionItem =0

        if(taller.idUdi!=null){

            val tamanio:String= listaUZDI?.size.toString()

            for(i in 0 until tamanio.toInt()){
                if(listaUZDI?.get(i)?.udi ==taller.idUdi.udi){
                    posicionItem=i
                }
            }

        }
        else if(taller.idCai!= null){

            val tamanio:String= listaUZDI?.size.toString()

            for(i in 0 until tamanio.toInt()){
                if(listaCAI?.get(i)?.cai ==taller.idCai.cai){
                    posicionItem=i
                }
            }
        }

        return posicionItem
    }


}
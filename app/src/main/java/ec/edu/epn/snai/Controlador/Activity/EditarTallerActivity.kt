package ec.edu.epn.snai.Controlador.Activity

import android.annotation.SuppressLint
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
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import ec.edu.epn.snai.Controlador.Adaptador.ItemTallerAdaptador
import ec.edu.epn.snai.Modelo.*
import ec.edu.epn.snai.R
import ec.edu.epn.snai.Servicios.*
import ec.edu.epn.snai.Utilidades.Constantes
import kotlinx.android.synthetic.main.activity_agregar_taller.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class EditarTallerActivity : AppCompatActivity(),ItemTallerAdaptador.ItemTallerOnItemClickListener{


    private lateinit var fabItemsTallers: FloatingActionButton
    var spCentro:Spinner?=null

    private var itemsTaller: ArrayList<ItemTaller> =ArrayList<ItemTaller>()
    private var itemsTallerEliminados: ArrayList<ItemTaller> =ArrayList<ItemTaller>()
    private var listaUZDI: List<UDI>?=null
    private var listaCAI: List<CAI>?= null
    private lateinit var token:String
    private lateinit var taller:Taller
    private var posicionActividadSeleccionada: Int = 0
    private lateinit var usuario: Usuario

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_agregar_taller)
        supportActionBar?.setDisplayHomeAsUpEnabled(true) //activo el botón Atrás

        val i = intent
        this.taller = i.getSerializableExtra("taller_seleccionado") as Taller
        this.token = i.getSerializableExtra("token") as String
        this.usuario=i.getSerializableExtra("usuario") as Usuario
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
        val adapterTipoCentro= ArrayAdapter<String>(this@EditarTallerActivity,android.R.layout.simple_expandable_list_item_1,resources.getStringArray(R.array.tipoCentro))
        adapterTipoCentro.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spTipoCentro.adapter=adapterTipoCentro

        if(this.taller.tipo.equals(Constantes.ROL_INSPECTOR_EDUCADOR)){

            spTipoCentro.setSelection(1) //el "1" corresponde a CAI
            spTipoCentro.isEnabled=false
        }
        else{

            val itemTipoCentro=obtenerItemTipoCentro()

            if(itemTipoCentro==0 || itemTipoCentro==1){
                spTipoCentro.setSelection(itemTipoCentro)
            }

        }


        spCentro=findViewById(R.id.spUdiCai)

        //Evento itemSelected del Spinner Tipo de Centro
        spTipoCentro.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, posicion: Int, p3: Long) {

                if(posicion==0){
                    asignarListaUzdiSpinner()

                    val itemCentroUzdiCAI=obtenerItemUzdiCai()
                    if(itemCentroUzdiCAI != null){
                        spCentro?.setSelection(itemCentroUzdiCAI)
                    }

                    itemSelectedPorCentroUzdiCai(0)

                }
                if(posicion==1){
                    asignarListaCaiSpinner()

                    val itemCentroUzdiCAI=obtenerItemUzdiCai()
                    if(itemCentroUzdiCAI != null){
                        spCentro?.setSelection(itemCentroUzdiCAI)
                    }

                    itemSelectedPorCentroUzdiCai(1)
                }
            }

        }

        asignarVariablesTaller()
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

                val tallerAux=guardarTaller()

                if(tallerAux != null){
                    editarItemsTaller(tallerAux)
                    val registroAsistencia= guardarRegistroAsistencia(tallerAux)

                    if(registroAsistencia != null){
                        val listaAsistenciaAdolescente= generarRegistroAsistencia(tallerAux)

                        if(listaAsistenciaAdolescente != null){

                            guardarListadoRegistroAsistencia(registroAsistencia,listaAsistenciaAdolescente)

                            val toast=Toast.makeText(applicationContext,"Se ha editado correctamente el Taller",Toast.LENGTH_LONG)
                            toast.setGravity(Gravity.CENTER, 0, 0)
                            toast.show()

                            val intent = Intent(this@EditarTallerActivity, MainActivity::class.java)
                            //seteo la bandera FLAG_ACTIVITY_CLEAR_TOP para indicar que el activity actuar lo voy a eliminar del stack
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                            intent.putExtra("usuario", usuario)
                            intent.putExtra("tipoTaller", tallerAux.tipo)
                            startActivity(intent)
                        }


                    }
                }
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

    private fun obtenerListadoUZDI(): List<UDI>?{

        try{
            val servicioUzdi= ClienteApiRest.getRetrofitInstance().create(UzdiServicio::class.java)
            val call =servicioUzdi.obtenerListaUZDI("Bearer $token")
            val listadoUzdi = call.execute().body()
            return  listadoUzdi!!
        }catch (e:Exception){
            return null
        }


    }

    private fun obtenerListadoCAI(): List<CAI>?{

        try{
            val servicioCai= ClienteApiRest.getRetrofitInstance().create(CaiServicio::class.java)
            val call =servicioCai.obtenerListaCAI("Bearer $token")
            val listadoCai = call.execute().body()
            return  listadoCai!!

        }catch (e:Exception){
            return null
        }

    }

    private fun asynTaskObtenerListadoUzdi(){

        try{
            val miclase = @SuppressLint("StaticFieldLeak")
            object : AsyncTask<Unit, Unit, List<UDI>>(){

                override fun doInBackground(vararg p0: Unit?): List<UDI> {
                    val listadoUzdi=obtenerListadoUZDI()!!
                    return listadoUzdi
                }

            }
            listaUZDI= miclase.execute().get()

        }catch (e:Exception){
            Toast.makeText(applicationContext, "Ha ocurrido un error al obtener la lista de UZDI", Toast.LENGTH_SHORT).show()
        }

    }

    private fun asynTaskObtenerListadoCai(){

        try{

            val task = @SuppressLint("StaticFieldLeak")
            object : AsyncTask<Unit, Unit, List<CAI>>(){


                override fun doInBackground(vararg p0: Unit?): List<CAI> {
                    val listadoCai=obtenerListadoCAI()!!
                    return listadoCai
                }

            }
            listaCAI= task.execute().get()
        }catch (e:Exception){
            Toast.makeText(applicationContext, "Ha ocurrido un error al obtener la Lista de CAI", Toast.LENGTH_SHORT).show()
        }

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

        try{
            val servicioTaller= ClienteApiRest.getRetrofitInstance().create(TallerServicio::class.java)
            val call =servicioTaller.obtenerNumeroParticipantesUZDI(uzdi, "Bearer $token")

            call.enqueue(object : Callback<String> {


                override fun onResponse(call: Call<String>, response: Response<String>) {

                    if (response.isSuccessful) {

                        var numeroParticipantes=response.body()
                        if(numeroParticipantes==null){
                            numeroParticipantes="0"
                        }
                        txtNumeroParticipantesTallerCrear.text= "$numeroParticipantes"

                    }

                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    call.cancel()

                }
            })


        }catch (e:Exception){
            Toast.makeText(applicationContext, "Ha ocurrido un error al obtener el número de participantes", Toast.LENGTH_SHORT).show()
        }

    }

    private fun obtenerNumeroParticipanteCAI(cai: CAI?){

        try {
            val servicioTaller = ClienteApiRest.getRetrofitInstance().create(TallerServicio::class.java)
            val call = servicioTaller.obtenerNumeroParticipantesCAI(cai, "Bearer $token")

            call.enqueue(object : Callback<String> {

                override fun onResponse(call: Call<String>, response: Response<String>) {

                    if (response.isSuccessful) {

                        var numeroParticipantes = response.body()

                        if (numeroParticipantes == null) {
                            numeroParticipantes = "0"
                        }
                        txtNumeroParticipantesTallerCrear.text = "$numeroParticipantes"

                    }

                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    call.cancel()

                }
            })
        }catch (e:Exception){
            Toast.makeText(applicationContext, "Ha ocurrido un error al obtener el número de participantes", Toast.LENGTH_SHORT).show()
        }
    }

    private fun dialogoAgregarActividadTaller(){

        //Creo el builder perteneciente al cuadro de dialogo
        val builder = AlertDialog.Builder(this)

        //obtengo la vista o layout del dialogo
        val view = layoutInflater.inflate(R.layout.dialogo_crear_activity_taller, null)

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

                val actividadTaller= ItemTaller()
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
            dialogo.dismiss()
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

                itemsTallerEliminados.add(itemsTaller.get(posicionActividadSeleccionada))
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

        if (this.taller.fecha != null) {
            etFechaTallerCrear?.setText( formatearFecha(this.taller.fecha))
        }
        if (this.taller.horaInicio != null) {
            etHoraTallerCrear?.setText( formatearHora(this.taller.horaInicio))
        }

        etObjetivoTallerCrear?.setText(this.taller.objetivo.toString())
        etRecomendacionesTallerCrear?.setText(this.taller.recomendaciones.toString())

        mostrarListaItemsTaller(itemsTaller)
    }

    private fun formatearFecha(fecha: Date): String {
        val pattern = "dd/MM/yyyy"
        val simpleDateFormat = SimpleDateFormat(pattern)
        return simpleDateFormat.format(fecha)
    }

    private fun formatearHora(horaInicio: Date): String {
        val patternHour = "HH:mm"
        val simpleHourFormat = SimpleDateFormat(patternHour)
        return simpleHourFormat.format(horaInicio)
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

    private fun obtenerItemUzdiCai(): Int?{

        var posicionItem =0

        if(taller.idUdi != null){

            if(listaUZDI != null){

                val longitud: String = listaUZDI?.size.toString()

                for(i in 0 until longitud.toInt()){

                    if( listaUZDI?.get(i)?.udi ==  this.taller.idUdi.udi){
                        posicionItem=i
                    }
                }
            }
        }
        else if(taller.idCai != null){

            if(listaCAI != null ){

                val longitud: String = listaCAI?.size.toString()

                for(i in 0 until longitud.toInt()){
                    if(listaCAI?.get(i)?.cai ==this.taller.idCai.cai){
                        posicionItem=i
                    }
                }
            }
        }
        return posicionItem
    }

    private fun obtenerVariablesTaller(): Taller?{

        if(taller!= null){
            val tallerAux= taller

            tallerAux.tema=etTemaTallerCrear?.text.toString()

            if(!etNumeroTallerCrear.text.toString().isBlank()){
                tallerAux.numeroTaller=etNumeroTallerCrear.text.toString().toInt()
            }
            else{
                tallerAux.numeroTaller=null
            }

            if(etFechaTallerCrear.text.toString().isNotBlank()){

                val sdf = SimpleDateFormat("dd/MM/yyyy")
                var convertedDate: Date? = null
                convertedDate = sdf.parse(etFechaTallerCrear.text.toString())
                tallerAux.fecha=convertedDate
            }

            if(etHoraTallerCrear.text.toString().isNotBlank()){
                val sdfH = SimpleDateFormat("HH:mm")
                var horaConvertida: Date?= null
                horaConvertida=sdfH.parse(etHoraTallerCrear.text.toString())
                tallerAux.horaInicio=horaConvertida
            }

            tallerAux.numeroTotalParticipantes=txtNumeroParticipantesTallerCrear.text.toString().toInt()

            tallerAux.objetivo=etObjetivoTallerCrear.text.toString()
            tallerAux.recomendaciones=etRecomendacionesTallerCrear.text.toString()

            val indiceCentro: Int?=spCentro?.selectedItemPosition

            if(spTipoCentro.selectedItem.toString()=="UZDI"){

                tallerAux.idUdi= listaUZDI?.get(indiceCentro!!)
                tallerAux.idCai=null
            }
            else if(spTipoCentro.selectedItem.toString()=="CAI"){
                tallerAux.idCai= listaCAI?.get(indiceCentro!!)
                tallerAux.idUdi=null

            }
            return tallerAux
        }
        else{
            return null
        }
    }


    /**************** Editar Taller¨***********************/
    private fun guardarTaller(): Taller?{

        val tallerEditar= obtenerVariablesTaller()
        if(tallerEditar!= null){

            if(tallerEditar.horaInicio != null && tallerEditar.fecha != null && tallerEditar.tema.isNotBlank() && tallerEditar.numeroTaller != null){

                if(tallerEditar.numeroTotalParticipantes > 0  ){

                    if(itemsTaller.size > 0){

                        val tallerEditado = asynTaskEditarTaller(tallerEditar)
                        return tallerEditado
                    }
                    else{
                        Toast.makeText(applicationContext, "Debe de ingresar al menos una Actividad", Toast.LENGTH_LONG).show()
                        return null
                    }
                }
                else{
                    Toast.makeText(applicationContext, "Debe seleccionar una Unidad Zonal o CAI con adolescentes infractores", Toast.LENGTH_LONG).show()
                    return null
                }
            }
            else{
                Toast.makeText(applicationContext, "Debe ingresar un Tema, Número Taller, Fecha y Hora", Toast.LENGTH_LONG).show()
                return null
            }

        }
        else{
            return null
        }

    }

    private fun servicioEditarTaller(tallerAux: Taller): Taller?{

        try{
            val servicioTaller= ClienteApiRest.getRetrofitInstance().create(TallerServicio::class.java)
            val call =servicioTaller.editarTaller( tallerAux,"Bearer $token")
            val response = call.execute()

            if(response != null){

                if(response.code() == 200){
                    val tallerGuardado=response.body()
                    return tallerGuardado!!
                }
                else{
                    return null
                }
            }
            else{
                return null
            }

        }catch (e:Exception){
            return null
        }

    }

    private fun asynTaskEditarTaller(tallerAux: Taller): Taller? {

        try{
            val miclase = @SuppressLint("StaticFieldLeak")
            object : AsyncTask<Unit, Unit, Taller>() {

                override fun doInBackground(vararg p0: Unit?): Taller {
                    val tallerEditado = servicioEditarTaller(tallerAux)
                    return tallerEditado!!
                }

            }
            val tallerRescatado = miclase.execute().get()
            return tallerRescatado

        }catch (e:Exception){
            return null
        }

    }


    /**************** Editar Items Taller¨***********************/
    private fun editarItemsTaller(taller: Taller) {

        if(itemsTaller.size > 0){
            for(i in itemsTallerEliminados){

                if(i.idItemTaller != null){
                    asynTaskEliminarItemsTaller(i.idItemTaller)
                }
            }
        }

        for (i in 0 until itemsTaller.size) {

            itemsTaller.get(i).idTaller = taller
            asynTaskEditarItemTaller(itemsTaller.get(i))

        }
    }

    private fun servicioEditarItemTaller(itemTallerAux: ItemTaller){

        try{
            val servicioTaller= ClienteApiRest.getRetrofitInstance().create(ItemTallerServicio::class.java)
            val call =servicioTaller.editarItemTaller( itemTallerAux,"Bearer $token")
            val response = call.execute()

        }catch (e:Exception){
            Toast.makeText(applicationContext, "Ha ocurrido un error al editar la actividad del Taller", Toast.LENGTH_SHORT).show()
        }

    }

    private fun asynTaskEditarItemTaller(itemTallerAux: ItemTaller) {

        try {
            val miclase = @SuppressLint("StaticFieldLeak")
            object : AsyncTask<Unit, Unit, Unit>() {

                override fun doInBackground(vararg p0: Unit?) {
                    val tallerEditado = servicioEditarItemTaller(itemTallerAux)
                }

            }
            val tallerRescatado = miclase.execute().get()
            return tallerRescatado
        }catch (e:Exception){
            Toast.makeText(applicationContext, "Ha ocurrido un error al editar la actividad del Taller", Toast.LENGTH_SHORT).show()
        }
    }


    /**************** Eliminar Items Taller¨***********************/

    private fun servicioEliminarItemsTaller(idItemTaller: Int){

        try {
            val servicioItemTaller = ClienteApiRest.getRetrofitInstance().create(ItemTallerServicio::class.java)
            val call = servicioItemTaller.eliminarItemTaller(idItemTaller, "Bearer $token")
            val response = call.execute()
        }catch (e:Exception){
            Toast.makeText(applicationContext, "Ha ocurrido un error al editar la actividad del Taller", Toast.LENGTH_SHORT).show()
        }
    }

    private fun asynTaskEliminarItemsTaller(idItemTaller: Int){

        try {
            val miclase = @SuppressLint("StaticFieldLeak")
            object : AsyncTask<Unit, Unit, Unit>() {

                override fun doInBackground(vararg p0: Unit?) {

                    servicioEliminarItemsTaller(idItemTaller)
                }

            }
            miclase.execute().get()
        }catch (e:Exception){
            Toast.makeText(applicationContext, "Ha ocurrido un error al editar la actividad del Taller", Toast.LENGTH_SHORT).show()
        }
    }



    /**************** Generar Listado de Registro de Asistencia¨***********************/
    private fun generarRegistroAsistencia(taller: Taller): List<AdolescenteInfractor>? {

        val listadoAsistenciaAdolescenteInfractor= asynTaskGenerarRegistroAsistencia(taller)
        return listadoAsistenciaAdolescenteInfractor
    }

    private fun servicioGenerarRegistroAsistenciaUzdi(uzdi: UDI): List<AdolescenteInfractor>?{

        try{

            val servicioRegistroAsistencia= ClienteApiRest.getRetrofitInstance().create(RegistroAsistenciaServicio::class.java)
            val call =servicioRegistroAsistencia.listaAdolescentesInfractoresPorUzdi( uzdi,"Bearer $token")
            val response = call.execute()

            if(response != null){

                if(response.code() == 200){
                    val listaAsistencia= response.body()
                    return listaAsistencia!!
                }
                else{
                    return null
                }
            }
            else{
                return null
            }
        }catch (e:Exception){
            return null
        }

    }

    private fun servicioGenerarRegistroAsistenciaCai(cai: CAI): List<AdolescenteInfractor>?{

        try{
            val servicioRegistroAsistencia= ClienteApiRest.getRetrofitInstance().create(RegistroAsistenciaServicio::class.java)
            val call =servicioRegistroAsistencia.listaAdolescentesInfractoresPorCai( cai,"Bearer $token")
            val response = call.execute()

            if(response != null){

                if(response.code() == 200){
                    val listaAsistencia= response.body()
                    return listaAsistencia!!
                }
                else{
                    return null
                }
            }
            else{
                return null
            }

        }catch (e:Exception){
            return null
        }

    }

    private fun asynTaskGenerarRegistroAsistencia(taller: Taller): List<AdolescenteInfractor>? {

        try{
            val miclase = @SuppressLint("StaticFieldLeak")
            object : AsyncTask<Unit, Unit, List<AdolescenteInfractor>>() {

                override fun doInBackground(vararg p0: Unit?): List<AdolescenteInfractor>? {

                    if(taller.idUdi != null &&  taller.idCai == null){
                        val listaAsistenciaUzdi = servicioGenerarRegistroAsistenciaUzdi(taller.idUdi)
                        return listaAsistenciaUzdi!!
                    }
                    else if (taller.idUdi == null && taller.idCai != null){
                        val listaAsistenciaCai = servicioGenerarRegistroAsistenciaCai(taller.idCai)
                        return listaAsistenciaCai!!
                    }
                    else{
                        return null
                    }

                }

            }
            val listaAsistencia = miclase.execute().get()
            return listaAsistencia!!

        }catch (e:Exception){
            return null
        }

    }


    /**************** Eliminar Registro de Asistencia¨***********************/
    private fun eliminarRegistroAsistencia(idTaller: Int) {

        asynTaskEliminarRegistroAsistencia(idTaller)
    }

    private fun servicioEliminarRegistroAsistencia(idTaller: Int){

        try{

            val servicioRegistroAsistencia= ClienteApiRest.getRetrofitInstance().create(RegistroAsistenciaServicio::class.java)
            val call =servicioRegistroAsistencia.eliminarRegistroAsistencia( idTaller,"Bearer $token")
            val response = call.execute()
        }catch (e:Exception){
            Toast.makeText(applicationContext, "Ha ocurrido un error al editar el Registro de Asitencia", Toast.LENGTH_SHORT).show()
        }

    }

    private fun asynTaskEliminarRegistroAsistencia(idTaller: Int){

        try{

            val miclase = @SuppressLint("StaticFieldLeak")
            object : AsyncTask<Unit, Unit, Unit>() {

                override fun doInBackground(vararg p0: Unit?) {

                    servicioEliminarRegistroAsistencia(idTaller)
                }

            }
            miclase.execute().get()
        }catch (e:Exception){
            Toast.makeText(applicationContext, "Ha ocurrido un error al editar el Registro de Asitencia", Toast.LENGTH_SHORT).show()
        }

    }



    /**************** Guardar Registro Asistencia¨***********************/
    private fun guardarRegistroAsistencia(taller: Taller): RegistroAsistencia{

        eliminarRegistroAsistencia(taller.idTaller)

        val registroAsistencia= RegistroAsistencia()
        registroAsistencia.idTaller=taller

        return asynTaskGuardarRegistroAsistencia(registroAsistencia)!!

    }

    private fun servicioGuardarRegistroAsistencia(registroAsistencia: RegistroAsistencia): RegistroAsistencia?{

        try{

            val servicioRegistroAsistencia= ClienteApiRest.getRetrofitInstance().create(RegistroAsistenciaServicio::class.java)
            val call =servicioRegistroAsistencia.guardarRegistroAsistencia( registroAsistencia,"Bearer $token")
            val response = call.execute()

            if(response != null){

                if(response.code() == 200){
                    val registroAsistenciaAux=response.body()
                    return registroAsistenciaAux!!
                }
                else{
                    return null
                }
            }
            else{
                return null
            }
        }catch (e:Exception){
            return null
        }

    }

    private fun asynTaskGuardarRegistroAsistencia(registroAsistencia: RegistroAsistencia): RegistroAsistencia?{

        try{
            val miclase = @SuppressLint("StaticFieldLeak")
            object : AsyncTask<Unit, Unit, RegistroAsistencia>() {

                override fun doInBackground(vararg p0: Unit?): RegistroAsistencia {
                    val registroAsistenciaAux = servicioGuardarRegistroAsistencia(registroAsistencia)
                    return registroAsistenciaAux!!
                }

            }
            val registroAsistenciaGuardado = miclase.execute().get()
            return registroAsistenciaGuardado

        }catch (e:Exception){
            return null
        }

    }


    /**************** Guardar Listado de Registro de Asistencia¨***********************/
    private fun guardarListadoRegistroAsistencia(registroAsistencia: RegistroAsistencia, listaAsistenciaAdolescentes: List<AdolescenteInfractor>) {

        for(adolescente in listaAsistenciaAdolescentes){
            val asistenciaAdolescente= AsistenciaAdolescente()
            asistenciaAdolescente.asistio=false
            asistenciaAdolescente.idAdolescenteInfractor=adolescente
            asistenciaAdolescente.idRegistroAsistencia=registroAsistencia

            asynTaskGuardarListadoRegistroAsistencia(asistenciaAdolescente)

        }

    }

    private fun servicioGuardarListadoRegistroAsistenciaUzdi(asistenciaAdolescente: AsistenciaAdolescente){


        try{

            val servicioAsistenciaAdolescente= ClienteApiRest.getRetrofitInstance().create(AsistenciaAdolescenteServicio::class.java)
            val call =servicioAsistenciaAdolescente.guardarAsistenciaAdolescente( asistenciaAdolescente,"Bearer $token")
            call.execute()
        }catch (e:Exception){
            Toast.makeText(applicationContext, "Ha ocurrido un error al guardar el Registro de Asitencia", Toast.LENGTH_SHORT).show()
        }

    }

    private fun asynTaskGuardarListadoRegistroAsistencia(asistenciaAdolescente: AsistenciaAdolescente){

        try{

            val miclase = @SuppressLint("StaticFieldLeak")
            object : AsyncTask<Unit, Unit, Unit>() {

                override fun doInBackground(vararg p0: Unit?) {
                    servicioGuardarListadoRegistroAsistenciaUzdi(asistenciaAdolescente)
                }

            }
            miclase.execute().get()
        }catch (e:Exception){
            Toast.makeText(applicationContext, "Ha ocurrido un error al guardar el Registro de Asitencia", Toast.LENGTH_SHORT).show()
        }

    }



}
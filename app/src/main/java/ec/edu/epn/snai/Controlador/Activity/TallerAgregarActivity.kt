package ec.edu.epn.snai.Controlador.Activity

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import ec.edu.epn.snai.R
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import ec.edu.epn.snai.Controlador.Adaptador.ItemTallerAdaptador
import ec.edu.epn.snai.Modelo.ItemTaller
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.support.v4.app.SupportActivity
import android.support.v4.app.SupportActivity.ExtraData
import android.support.v4.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.service.autofill.Dataset
import android.view.View
import android.widget.*
import ec.edu.epn.snai.Modelo.CAI
import ec.edu.epn.snai.Modelo.UDI
import ec.edu.epn.snai.R.id.fab_agregar_item_taller
import ec.edu.epn.snai.Servicios.CaiServicio
import ec.edu.epn.snai.Servicios.ClienteApiRest
import ec.edu.epn.snai.Servicios.TallerServicio
import ec.edu.epn.snai.Servicios.UzdiServicio
import kotlinx.android.synthetic.main.activity_agregar_taller.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*


class TallerAgregarActivity : AppCompatActivity(),ItemTallerAdaptador.ItemTallerOnItemClickListener {

    private lateinit var fabItemsTallers:FloatingActionButton
    private var spCentro:Spinner?=null

    private var itemsTaller: MutableList<ItemTaller> =ArrayList<ItemTaller>()
    private var listaUZDI: List<UDI>? =null
    private var listaCAI: List<CAI>? =null
    private lateinit var token:String
    private var posicionActividadSeleccionada: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agregar_taller)
        supportActionBar?.setDisplayHomeAsUpEnabled(true) //activo el botón Atrás}

        token = intent.getSerializableExtra("token") as String

        fabItemsTallers=findViewById(R.id.fab_agregar_item_taller)
        fabItemsTallers.setOnClickListener {
            dialogoAgregarActividadTaller()
        }

        var etFechaTaller=findViewById<EditText>(R.id.etFechaTallerCrear)
        etFechaTaller.setOnClickListener {
            dialogoFechaTaller(etFechaTaller)
        }

        var etHoraTaller=findViewById<EditText>(R.id.etHoraTallerCrear)
        etHoraTaller.setOnClickListener {
            dialogoObtenerHora(etHoraTaller)
        }


        var spTipoCentro:Spinner=findViewById(R.id.spTipoCentro)

        //Adaptador del Tipo de Centro para el Spinner
        var adapterTipoCentro=ArrayAdapter<String>(this@TallerAgregarActivity,android.R.layout.simple_expandable_list_item_1,resources.getStringArray(R.array.tipoCentro))
        adapterTipoCentro.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spTipoCentro.adapter=adapterTipoCentro

        spCentro=findViewById(R.id.spUdiCai)

        //Evento itemSelected del Spinner Tipo de Centro
        spTipoCentro.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, posicion: Int, p3: Long) {

                if(posicion==0){
                    obtenerListadoUZDI()
                    itemSelectedPorCentroUzdiCai(0)
                }
                if(posicion==1){
                    obtenerListadoCAI()
                    itemSelectedPorCentroUzdiCai(1)
                }
            }

        }


    }

    fun obtenerListadoUZDI(){

        val servicioUzdi=ClienteApiRest.getRetrofitInstance().create(UzdiServicio::class.java)
        val call =servicioUzdi.obtenerListaUZDI("Bearer "+token)

        call.enqueue(object : Callback<List<UDI>> {

            override fun onResponse(call: Call<List<UDI>>, response: Response<List<UDI>>) {

                if (response.isSuccessful) {

                    listaUZDI=response.body()

                    var listaUZDIAux:MutableList<String> = ArrayList<String>()
                    for (u in listaUZDI!!){
                        listaUZDIAux.add(u.udi)
                    }


                    var adapterTipoCentro=ArrayAdapter<String>(this@TallerAgregarActivity,android.R.layout.simple_expandable_list_item_1,listaUZDIAux)
                    adapterTipoCentro.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    spCentro?.adapter=adapterTipoCentro

                }

            }

            override fun onFailure(call: Call<List<UDI>>, t: Throwable) {
                call.cancel()

            }
        })
    }

    fun obtenerListadoCAI(){

        val servicioCai=ClienteApiRest.getRetrofitInstance().create(CaiServicio::class.java)
        val call =servicioCai.obtenerListaCAI("Bearer "+token)

        call.enqueue(object : Callback<List<CAI>> {

            override fun onResponse(call: Call<List<CAI>>, response: Response<List<CAI>>) {

                if (response.isSuccessful) {

                    listaCAI=response.body()

                    var listaCAIAux:MutableList<String> = ArrayList<String>()
                    for (c in listaCAI!!){
                        listaCAIAux.add(c.cai)
                    }

                    var adapterTipoCentro=ArrayAdapter<String>(this@TallerAgregarActivity,android.R.layout.simple_expandable_list_item_1,listaCAIAux)
                    adapterTipoCentro.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    spCentro?.adapter=adapterTipoCentro

                }

            }

            override fun onFailure(call: Call<List<CAI>>, t: Throwable) {
                call.cancel()

            }
        })
    }

    fun itemSelectedPorCentroUzdiCai(posicionTipoCentro: Int){

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

        val servicioTaller=ClienteApiRest.getRetrofitInstance().create(TallerServicio::class.java)
        val call =servicioTaller.obtenerNumeroParticipantesUZDI(uzdi,"Bearer "+token)

        call.enqueue(object : Callback<String> {

            override fun onResponse(call: Call<String>, response: Response<String>) {

                if (response.isSuccessful) {

                    var numeroParticipantes=response.body()
                    if(numeroParticipantes==null){
                        numeroParticipantes="0"
                    }
                    txtNumeroParticipantesTallerCrear.text="Número de Adolescentes: " +numeroParticipantes

                }

            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                call.cancel()

            }
        })

    }

    fun obtenerNumeroParticipanteCAI(cai: CAI?){

        val servicioTaller=ClienteApiRest.getRetrofitInstance().create(TallerServicio::class.java)
        val call =servicioTaller.obtenerNumeroParticipantesCAI(cai,"Bearer "+token)

        call.enqueue(object : Callback<String> {

            override fun onResponse(call: Call<String>, response: Response<String>) {

                if (response.isSuccessful) {

                    var numeroParticipantes=response.body()

                    if(numeroParticipantes==null){
                        numeroParticipantes="0"
                    }
                    txtNumeroParticipantesTallerCrear.text="Número de Adolescentes: " +numeroParticipantes

                }

            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                call.cancel()

            }
        })

    }

    fun dialogoAgregarActividadTaller(){

        //Creo el builder perteneciente al cuadro de dialogo
        val builder = AlertDialog.Builder(this)

        val inflater =layoutInflater
        //obtengo la vista o layout del dialogo
        val view = inflater.inflate(R.layout.dialog_activity_taller, null)

        //añado la vista al builder
        builder.setView(view)

        //creo que el cuadro de dialogo
        val dialogo :AlertDialog= builder.create()
        dialogo.show()

        var etActividad= view.findViewById<EditText?>(R.id.etActividad)
        var etObjetivo=view.findViewById<EditText?>(R.id.etObjetivo)
        var etMateriales=view.findViewById<EditText?>(R.id.etMateriales)
        var etResponsable=view.findViewById<EditText?>(R.id.etResponsable)
        var etDuracion=view.findViewById<EditText?>(R.id.etDuracion)


        val btnAgregar=view.findViewById<Button>(R.id.btnAgregar)
        btnAgregar.setOnClickListener {

            if(etActividad?.text.isNullOrBlank()|| etResponsable?.text.isNullOrBlank() || etDuracion?.text.isNullOrEmpty() ){
                Toast.makeText(getApplicationContext(),"Actividad, Responsable y Duración es requerido, ingrese un valor",Toast.LENGTH_SHORT).show();
                dialogo.dismiss();

            }
            else{

                var actividadTaller=ItemTaller()
                actividadTaller?.actividad=etActividad?.text.toString()
                actividadTaller?.objetivoEspecifico=etObjetivo?.text.toString()
                actividadTaller?.materiales=etMateriales?.text.toString()
                actividadTaller ?.responsable=etResponsable?.text.toString()
                actividadTaller?.duracion= etDuracion?.text.toString().toInt()

                dialogo.dismiss();

                itemsTaller.add(actividadTaller)

                mostrarListaItemsTaller(itemsTaller)

            }

        }

        val btnCancelar=view.findViewById<Button>(R.id.btnCancelar)
        btnCancelar.setOnClickListener {
            dialogo.dismiss();
        }

    }

    fun dialogoObtenerHora(etHoraTaller: EditText){

        //Calendario para obtener fecha & hora
        val c = Calendar.getInstance();

        //Variables para obtener la hora hora
        val horaActual = c.get(Calendar.HOUR_OF_DAY);
        val minutoActual = c.get(Calendar.MINUTE);

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
                etHoraTaller.setText(horaFormateada + ":" + minutoFormateado);

        //Estos valores deben ir en ese orden
        //Al colocar en false se muestra en formato 12 horas y true en formato 24 horas
        //Pero el sistema devuelve la hora en formato 24 horas
        }, horaActual, minutoActual, false)

        obtenerHora.show();
    }

    fun dialogoFechaTaller(etFechaTaller: EditText){
        val cldr = Calendar.getInstance()
        val diaSeleccionado = cldr.get(Calendar.DAY_OF_MONTH)
        val mesSeleccionado = cldr.get(Calendar.MONTH)
        val añoSeleccionado = cldr.get(Calendar.YEAR)
        // date picker dialog
        val picker = DatePickerDialog(this@TallerAgregarActivity,

            DatePickerDialog.OnDateSetListener { datePicker, año, mes, dia ->

                var mesFormateado: String = mes.toString()
                //Formateo el año obtenido: antepone el 0 si son menores de 10
                if ((mes + 1) < 10) {
                    mesFormateado = String.format("0" + mes)
                }

                var diaFormateado: String = dia.toString()
                //Formateo el año obtenido: antepone el 0 si son menores de 10
                if (dia < 10) {
                    diaFormateado = String.format("0" + dia)
                }


                etFechaTaller.setText(diaFormateado + "/" + mesFormateado + "/" + año)

            }, añoSeleccionado, mesSeleccionado, diaSeleccionado
        )
        picker.show()

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

    override fun OnItemClick(posicion: Int) {

        posicionActividadSeleccionada=posicion

        val intent = Intent(applicationContext, EditarActividadTallerActivity::class.java)
        intent.putExtra("actividad_seleccionada", itemsTaller?.get(posicion))
        startActivityForResult(intent,1) //lanzo o ejecuto un nuevo activity, pero además espero una respuesta

    }

    //Método que se ejecuta una vez que obtengo una respuesta del activity EditarActividadTallerActivity, es decir, se ejecuta cuando
    // finaliza el activity  EditarActividadTallerActivity y manda una respuesta a través de un intent
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode==1){
            if(resultCode== Activity.RESULT_OK){
                val actividadRescatada:ItemTaller = data?.getSerializableExtra("actividad_rescatada") as ItemTaller

                itemsTaller.removeAt(posicionActividadSeleccionada)
                itemsTaller.add(actividadRescatada)

                mostrarListaItemsTaller(itemsTaller)

            }
            else if(resultCode==Activity.RESULT_FIRST_USER){

                itemsTaller.removeAt(posicionActividadSeleccionada)

                mostrarListaItemsTaller(itemsTaller)
            }
        }
    }

    fun mostrarListaItemsTaller(listaItemsTaller: List<ItemTaller>){
        var adaptadorItemTaller = ItemTallerAdaptador(listaItemsTaller,this@TallerAgregarActivity)
        var recyclerViewItemTaller =findViewById (R.id.rv_items_taller) as RecyclerView
        recyclerViewItemTaller.adapter=adaptadorItemTaller
        recyclerViewItemTaller.layoutManager=LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL,false)

    }
}


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
import ec.edu.epn.snai.Modelo.Taller
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


class InformeAgregarActivity : AppCompatActivity(),ItemTallerAdaptador.ItemTallerOnItemClickListener {

    override fun OnItemClick(posicion: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private lateinit var fabAgregarFotograficas:FloatingActionButton

    private var itemsTaller: MutableList<ItemTaller> =ArrayList<ItemTaller>()

    private lateinit var tallerActual: Taller
    private lateinit var token:String

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agregar_informe)
        supportActionBar?.setDisplayHomeAsUpEnabled(true) //activo el botón Atrás}

        token = intent.getSerializableExtra("token") as String
        val taller = intent.getSerializableExtra("tallerActual") as Taller?

        fabAgregarFotograficas=findViewById(R.id.fab_agregar_fotograficas_informe)
        fabAgregarFotograficas.setOnClickListener {
            val intent = Intent(this@InformeAgregarActivity, VerRegistroAsistenciaActivity::class.java)
            intent.putExtra("tallerActual", tallerActual)
            intent.putExtra("token", token)
            startActivity(intent)
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
        val picker = DatePickerDialog(this@InformeAgregarActivity,

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

    //Método que se ejecuta una vez que obtengo una respuesta del activity EditarActividadTallerActivity, es decir, se ejecuta cuando
    // finaliza el activity  EditarActividadTallerActivity y manda una respuesta a través de un intent
    /*override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
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
    }*/

    fun mostrarListaItemsTaller(listaItemsTaller: List<ItemTaller>){
        var adaptadorItemTaller = ItemTallerAdaptador(listaItemsTaller,this@InformeAgregarActivity)
        var recyclerViewItemTaller =findViewById (R.id.rv_items_taller) as RecyclerView
        recyclerViewItemTaller.adapter=adaptadorItemTaller
        recyclerViewItemTaller.layoutManager=LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL,false)

    }
}


package ec.edu.epn.snai.Controlador.Activity

import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import ec.edu.epn.snai.R
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.Button
import android.widget.Toast
import ec.edu.epn.snai.Controlador.Adaptador.ItemTallerAdaptador
import ec.edu.epn.snai.Modelo.ItemTaller
import java.util.ArrayList


class TallerAgregarActivity : AppCompatActivity(),ItemTallerAdaptador.ItemTallerOnItemClickListener {

    private var txtTema: EditText? = null
    private var txtNumeroTaller: EditText? = null
    private var txtFecha: EditText? = null
    private var txtHora: EditText? = null
    private lateinit var fabItemsTallers:FloatingActionButton

    private var itemsTaller: MutableList<ItemTaller> =ArrayList<ItemTaller>()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agregar_taller)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true) //activo el botón Atrás


        txtTema = findViewById<EditText>(R.id.etTemaTallerCrear)
        txtNumeroTaller = findViewById<EditText>(R.id.etNumeroTallerCrear)
        txtFecha = findViewById<EditText>(R.id.etFechaTallerCrear)
        txtHora = findViewById<EditText>(R.id.etHoraTallerCrear)


        fabItemsTallers=findViewById(R.id.fab_agregar_item_taller)
        fabItemsTallers.setOnClickListener {
            dialogoAgregarActividadTaller()
        }

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
                actividadTaller?.responsable=etResponsable?.text.toString()
                actividadTaller?.duracion= etDuracion?.text.toString().toInt()

                Toast.makeText(getApplicationContext(),actividadTaller.toString(),Toast.LENGTH_SHORT).show();
                dialogo.dismiss();

                itemsTaller.add(actividadTaller)

                var adaptadorItemTaller = ItemTallerAdaptador(itemsTaller,this@TallerAgregarActivity)
                var recyclerViewItemTaller =findViewById (R.id.rv_items_taller) as RecyclerView
                recyclerViewItemTaller.adapter=adaptadorItemTaller
                recyclerViewItemTaller.layoutManager=LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL,false)


            }

        }

        val btnCancelar=view.findViewById<Button>(R.id.btnCancelar)
        btnCancelar.setOnClickListener {
            dialogo.dismiss();
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

    override fun OnItemClick(posicion: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }



}
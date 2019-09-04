package ec.edu.epn.snai.Controlador.Activity

import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import ec.edu.epn.snai.R
import android.support.v7.app.AlertDialog
import android.widget.Button
import android.widget.Toast


class TallerAgregarActivity : AppCompatActivity() {

    private var txtTema: EditText? = null
    private var txtNumeroTaller: EditText? = null
    private var txtFecha: EditText? = null
    private var txtHora: EditText? = null

    private lateinit var fabItemsTallers:FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agregar_taller)
        //supportActionBar!!.setDisplayHomeAsUpEnabled(true) //activo el botón Atrás
        if(supportActionBar!=null){
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        }

        //var taller : Taller?=null

        txtTema = findViewById<EditText>(R.id.etTemaTallerCrear)
        txtNumeroTaller = findViewById<EditText>(R.id.etNumeroTallerCrear)
        txtFecha = findViewById<EditText>(R.id.etFechaTallerCrear)
        txtHora = findViewById<EditText>(R.id.etHoraTallerCrear)

//        taller?.tema=txtTema?.text.toString()
//        taller?.numeroTaller= txtNumeroTaller?.text.toString().toIntOrNull()

        fabItemsTallers=findViewById(R.id.fab_agregar_item_taller)
        fabItemsTallers.setOnClickListener {
            dialogoAgregarItemTaller()
        }

    }

    fun dialogoAgregarItemTaller(){

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

        val etActividad= view.findViewById<EditText>(R.id.etActividad)
        val etResponsable=view.findViewById<EditText>(R.id.etResponsable)

        val btnAgregar=view.findViewById<Button>(R.id.btnAgregar)
        btnAgregar.setOnClickListener {
            Toast.makeText(getApplicationContext(),"Conectando...",Toast.LENGTH_SHORT).show();
            dialogo.dismiss();
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


}
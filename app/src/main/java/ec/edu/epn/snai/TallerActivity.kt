package ec.edu.epn.snai

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.widget.TextView
import ec.edu.epn.snai.Modelo.Taller

class TallerActivity : AppCompatActivity(){

    private var txtTema: TextView?=null
    private var txtNumeroTaller: TextView?=null
    private var txtFecha: TextView?=null
    private var txtHora: TextView?=null

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_taller)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true) //activo el botón Atrás

        val i = intent
        val taller = i.getSerializableExtra("taller_seleccionado") as Taller?

        txtTema=findViewById(R.id.txtTemaTallerSeleccionado) as TextView
        txtNumeroTaller= findViewById(R.id.txtNumeroTallerSeleccionado) as TextView
        txtFecha=findViewById(R.id.txtFechaTallerSeleccionado) as TextView
        txtHora=findViewById(R.id.txtHoraTallerSeleccionado) as TextView

        txtTema?.text =taller?.tema
        txtNumeroTaller?.text=taller?.numeroTaller.toString()
        txtHora?.text=taller?.horaInicio.toString()
        txtFecha?.text=taller?.fecha.toString()

    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        /*when(item?.itemId){
            R.id.menu_guardar->{
                finish()
            }
            else->{
                finish()
            }
        }*/
        finish()
        return true
    }
}
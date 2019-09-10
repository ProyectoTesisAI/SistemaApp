package ec.edu.epn.snai.Controlador.Activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import ec.edu.epn.snai.Modelo.Taller
import ec.edu.epn.snai.Modelo.Usuario
import ec.edu.epn.snai.R

class VerTallerActivity : AppCompatActivity(){

    private var txtTema: TextView?=null
    private var txtNumeroTaller: TextView?=null
    private var txtFecha: TextView?=null
    private var txtHora: TextView?=null

    private lateinit var btnListarAdolescentePorTaller: Button

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ver_taller)
        supportActionBar?.setDisplayHomeAsUpEnabled(true) //activo el botón Atrás

        val i = intent
        val taller = i.getSerializableExtra("taller_seleccionado") as Taller?
        val token = i.getSerializableExtra("token") as String?

        txtTema=findViewById(R.id.txtTemaTallerSeleccionado) as TextView
        txtNumeroTaller= findViewById(R.id.txtNumeroTallerSeleccionado) as TextView
        txtFecha=findViewById(R.id.txtFechaTallerSeleccionado) as TextView
        txtHora=findViewById(R.id.txtHoraTallerSeleccionado) as TextView

        txtTema?.text =taller?.tema
        txtNumeroTaller?.text=taller?.numeroTaller.toString()
        txtHora?.text=taller?.horaInicio.toString()
        txtFecha?.text=taller?.fecha.toString()

        btnListarAdolescentePorTaller=findViewById(R.id.btnListarAdolescentes)
        btnListarAdolescentePorTaller.setOnClickListener{
            var tallerActual: Taller?=null
            tallerActual=taller
            val intent = Intent(this@VerTallerActivity, VerListadoAsistenciaActivity::class.java)
            intent.putExtra("tallerActual", tallerActual)
            intent.putExtra("token", token)
            startActivity(intent)
        }
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
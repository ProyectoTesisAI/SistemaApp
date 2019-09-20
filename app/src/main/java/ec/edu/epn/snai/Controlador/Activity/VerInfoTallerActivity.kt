package ec.edu.epn.snai.Controlador.Activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import ec.edu.epn.snai.Controlador.AdaptadorTabs.TallerAdaptadorTabs
import ec.edu.epn.snai.Modelo.Taller
import ec.edu.epn.snai.R
import kotlinx.android.synthetic.main.activity_ver_info_taller.*

class VerInfoTallerActivity : AppCompatActivity() {

    private lateinit var token:String
    private lateinit var taller:Taller
    private lateinit var menuAux:Menu

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ver_info_taller)
        supportActionBar?.setDisplayHomeAsUpEnabled(true) //activo el botón Atrás
        supportActionBar?.elevation=0F


        val i = intent
        this.taller = i.getSerializableExtra("taller_seleccionado") as Taller
        this.token = i.getSerializableExtra("token") as String

        val adaptador=TallerAdaptadorTabs(supportFragmentManager,token,taller)
        view_pager_taller.adapter=adaptador

        tabs_taller.setupWithViewPager(view_pager_taller)

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuAux=menu
        menuInflater.inflate(R.menu.menu_gestion, menu)

        menu.findItem(R.id.menu_editar).isVisible = true
        menu.findItem(R.id.menu_eliminar).isVisible=false
        menu.findItem(R.id.menu_guardar).isVisible=false
        return true

    }


    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        when(item?.itemId){

            R.id.menu_editar->{

                val intent = Intent(applicationContext, EditarTallerActivity::class.java)
                intent.putExtra("token",token)
                intent.putExtra("taller_seleccionado", taller)
                startActivity(intent)

            }
            else->{
                finish()
            }
        }
        return true
    }

}
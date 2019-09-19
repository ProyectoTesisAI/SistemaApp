package ec.edu.epn.snai

import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import ec.edu.epn.snai.Controlador.AdaptadorTabs.TallerAdaptadorTabs
import ec.edu.epn.snai.Modelo.Taller
import kotlinx.android.synthetic.main.activity_ver_info_taller.*

class VerInfoTallerActivity : AppCompatActivity() {

    private lateinit var token:String
    private lateinit var taller:Taller

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
}
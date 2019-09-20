package ec.edu.epn.snai.Controlador.Activity

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v7.app.AppCompatActivity
import ec.edu.epn.snai.Controlador.AdaptadorTabs.InformePagerAdaptador
import ec.edu.epn.snai.Modelo.Informe
import ec.edu.epn.snai.R
import kotlinx.android.synthetic.main.activity_ver_editar_informe.*

class VerEditarInformeActivity : AppCompatActivity() {

    private lateinit var informeSeleccionado: Informe
    private lateinit var token: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ver_editar_informe)
        supportActionBar?.setDisplayHomeAsUpEnabled(true) //activo el botón Atrás
        supportActionBar?.elevation=0F

        val i = intent
        informeSeleccionado= i.getSerializableExtra("informeSeleccionado") as Informe
        token = i.getSerializableExtra("token") as String

        var adaptadorInforme=InformePagerAdaptador(supportFragmentManager,token,informeSeleccionado)
        viewpager.adapter=adaptadorInforme

        tabs.setupWithViewPager(viewpager)

    }

}
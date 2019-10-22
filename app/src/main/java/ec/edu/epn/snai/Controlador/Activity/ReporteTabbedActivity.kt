package ec.edu.epn.snai.Controlador.Activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import ec.edu.epn.snai.Controlador.AdaptadorTabs.*
import ec.edu.epn.snai.Modelo.Usuario
import ec.edu.epn.snai.R
import kotlinx.android.synthetic.main.activity_tabbed_reporte.*

class ReporteTabbedActivity : AppCompatActivity() {

    private lateinit var token:String
    private lateinit var reporte:String
    private lateinit var usuario: Usuario


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tabbed_reporte)
        supportActionBar?.setDisplayHomeAsUpEnabled(true) //activo el botón Atrás
        supportActionBar?.elevation=0F


        val i = intent
        this.reporte = i.getSerializableExtra("nombreReporte") as String
        this.usuario=i.getSerializableExtra("usuario") as Usuario
        this.token = usuario.token

        when(reporte){
            "Reporte 1"->{
                getSupportActionBar()?.setTitle("REPORTE 1 - TIPO DELITO")
                val adaptador=Reporte1AdaptadorTabs(supportFragmentManager,token)
                view_pager_reporte.adapter=adaptador
                tabs_reporte.setupWithViewPager(view_pager_reporte)
            }
            "Reporte 2"->{
                getSupportActionBar()?.setTitle("REPORTE 2 - EDAD")
                val adaptador= Reporte2AdaptadorTabs(supportFragmentManager,token)
                view_pager_reporte.adapter=adaptador
                tabs_reporte.setupWithViewPager(view_pager_reporte)
            }
            "Reporte 3"->{

                getSupportActionBar()?.setTitle("REPORTE 3 - EDAD RESPECTO A UN FECHA FUTURA")
                val adaptador=Reporte3AdaptadorTabs(supportFragmentManager,token)
                view_pager_reporte.adapter=adaptador
                tabs_reporte.setupWithViewPager(view_pager_reporte)
            }
            "Reporte 4"->{

            }
            "Reporte 5"->{

            }
            "Reporte 6"->{

            }
            "Reporte 7"->{
                getSupportActionBar()?.setTitle("REPORTE 7 - NIVEL DE EDUCACION")
                val adaptador= Reporte7AdaptadorTabs(supportFragmentManager,token)
                view_pager_reporte.adapter=adaptador
                tabs_reporte.setupWithViewPager(view_pager_reporte)
            }
            "Reporte 8"->{
                getSupportActionBar()?.setTitle("REPORTE 8 - EDAD Y EDUCACION")
                val adaptador= Reporte8AdaptadorTabs(supportFragmentManager,token)
                view_pager_reporte.adapter=adaptador
                tabs_reporte.setupWithViewPager(view_pager_reporte)
            }
            "Reporte 9"->{

            }
            "Reporte 10"->{

            }
        }

    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        finish()
        return true
    }
}
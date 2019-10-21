package ec.edu.epn.snai.Controlador.Activity

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import ec.edu.epn.snai.Controlador.AdaptadorTabs.Reporte1AdaptadorTabs
import ec.edu.epn.snai.Controlador.AdaptadorTabs.Reporte3AdaptadorTabs
import ec.edu.epn.snai.Controlador.AdaptadorTabs.TallerAdaptadorTabs
import ec.edu.epn.snai.Modelo.ItemTaller
import ec.edu.epn.snai.Modelo.Taller
import ec.edu.epn.snai.Modelo.Usuario
import ec.edu.epn.snai.R
import ec.edu.epn.snai.Servicios.ClienteApiRest
import ec.edu.epn.snai.Servicios.TallerServicio
import ec.edu.epn.snai.Utilidades.Constantes
import kotlinx.android.synthetic.main.activity_tabbed_reporte.*
import kotlinx.android.synthetic.main.activity_tabbed_taller.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.ArrayList

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

            }
            "Reporte 8"->{

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
package ec.edu.epn.snai.Controlador.Activity

import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import ec.edu.epn.snai.Controlador.Adaptador.ItemInformeAdaptador
import ec.edu.epn.snai.Controlador.AdaptadorTabs.TallerAdaptadorTabs
import ec.edu.epn.snai.Modelo.CAI
import ec.edu.epn.snai.Modelo.ItemTaller
import ec.edu.epn.snai.Modelo.Taller
import ec.edu.epn.snai.R
import ec.edu.epn.snai.Servicios.ClienteApiRest
import ec.edu.epn.snai.Servicios.TallerServicio
import kotlinx.android.synthetic.main.activity_ver_info_taller.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.ArrayList

class VerInfoTallerActivity : AppCompatActivity() {

    private lateinit var token:String
    private lateinit var taller:Taller
    private lateinit var menuAux:Menu
    private var itemsTaller: List<ItemTaller>?=null

    private lateinit var btftCrearInforme: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ver_info_taller)
        supportActionBar?.setDisplayHomeAsUpEnabled(true) //activo el botón Atrás
        supportActionBar?.elevation=0F


        val i = intent
        this.taller = i.getSerializableExtra("taller_seleccionado") as Taller
        this.token = i.getSerializableExtra("token") as String
        asynTaskObtenerListadoItemsTaller()

        if(itemsTaller != null){
            val adaptador=TallerAdaptadorTabs(supportFragmentManager,token,taller,ArrayList(itemsTaller))
            view_pager_taller.adapter=adaptador
            tabs_taller.setupWithViewPager(view_pager_taller)
        }
        else{
            Toast.makeText(applicationContext,"El Taller no posee actividades, Por favor edite el taller desde la aplicación web",Toast.LENGTH_SHORT).show()
            finish()
        }

        btftCrearInforme=findViewById(R.id.fab_crear_informe)
        btftCrearInforme.setOnClickListener {
            val intent = Intent(applicationContext, AgregarRegistroAsistenciaActivity::class.java)
            intent.putExtra("token",token)
            intent.putExtra("taller_seleccionado", taller)
            if(itemsTaller != null){
                intent.putExtra("items_taller_seleccionado", ArrayList(itemsTaller))
            }

            startActivity(intent)
        }

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
                if(itemsTaller != null){
                    intent.putExtra("items_taller_seleccionado", ArrayList(itemsTaller))
                }

                startActivity(intent)

            }
            else->{
                finish()
            }
        }
        return true
    }


    private fun asynTaskObtenerListadoItemsTaller(){

        val task = object : AsyncTask<Unit, Unit, List<ItemTaller>>(){


            override fun doInBackground(vararg p0: Unit?): List<ItemTaller>? {
                val listadoItemsTaller=obtenerItemsTaller()
                if(listadoItemsTaller != null){
                    return listadoItemsTaller
                }
                else{
                    return null
                }
            }

        }
        itemsTaller= task.execute().get()

    }

    private fun obtenerItemsTaller(): List<ItemTaller>?{
        val servicio = ClienteApiRest.getRetrofitInstance().create(TallerServicio::class.java)
        val call = servicio.listarItemsPorTaller(taller.idTaller.toString(),"Bearer "+ token)
        val response= call.execute()

        if(response != null){

            if(response.code() == 200){
                val itemsTallerAux =response.body()
                Log.i("item", itemsTallerAux?.size.toString())
                return itemsTallerAux
            }
            else{
                return null
            }
        }
        else{
            return null
        }


    }

}
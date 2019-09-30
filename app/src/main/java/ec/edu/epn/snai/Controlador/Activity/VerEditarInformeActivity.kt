package ec.edu.epn.snai.Controlador.Activity

import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import ec.edu.epn.snai.Controlador.AdaptadorTabs.InformePagerAdaptador
import ec.edu.epn.snai.Modelo.*
import ec.edu.epn.snai.R
import ec.edu.epn.snai.Servicios.ClienteApiRest
import ec.edu.epn.snai.Servicios.RegistroAsistenciaServicio
import ec.edu.epn.snai.Servicios.RegistroFotograficoServicio
import ec.edu.epn.snai.Servicios.TallerServicio
import kotlinx.android.synthetic.main.activity_ver_editar_informe.*
import java.util.ArrayList

class VerEditarInformeActivity : AppCompatActivity() {

    private lateinit var informeSeleccionado: Informe
    private lateinit var token: String
    private var listaFotos: List<RegistroFotografico>?=null
    private var listaActividadesTaller: List<ItemTaller>?=null
    private var listaRegistroAsistencia: List<AsistenciaAdolescente>?=null

    private lateinit var menuAux: Menu

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ver_editar_informe)
        supportActionBar?.setDisplayHomeAsUpEnabled(true) //activo el botón Atrás
        supportActionBar?.elevation=0F

        val i = intent
        this.informeSeleccionado= i.getSerializableExtra("informeSeleccionado") as Informe
        this.token = i.getSerializableExtra("token") as String

        asynTaskObtenerListadoFotografico()
        asynTaskObtenerListadoRegistroAsistencia()
        asynTaskObtenerListadoActividadesTaller()

        if(listaFotos != null && listaActividadesTaller != null && listaRegistroAsistencia != null){
            var adaptadorInforme=InformePagerAdaptador(supportFragmentManager,token,informeSeleccionado, listaFotos!!, listaActividadesTaller!!, listaRegistroAsistencia!!)
            viewpager.adapter=adaptadorInforme
            tabs.setupWithViewPager(viewpager)
        }
        else{
            Toast.makeText(applicationContext,"El Informe no posee actividades o fotos, Por favor edite el taller desde la aplicación web",Toast.LENGTH_SHORT).show()
            finish()
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

                val intent = Intent(applicationContext, EditarRegistroAsistenciaActivity::class.java)
                intent.putExtra("token",token)
                intent.putExtra("informeSeleccionado", informeSeleccionado)
                intent.putExtra("listaActividades", ArrayList(listaActividadesTaller))
                intent.putExtra("listaAsistencia", ArrayList(listaRegistroAsistencia))
                intent.putExtra("listaFotos",ArrayList(listaFotos))
                startActivity(intent)

            }
            else->{
                finish()
            }
        }
        return true
    }

    private fun asynTaskObtenerListadoFotografico(){

        val task = object : AsyncTask<Unit, Unit, List<RegistroFotografico>>(){

            override fun doInBackground(vararg p0: Unit?): List<RegistroFotografico>? {
                val listadoFotografias=obtenerRegistroFotografico()
                if(listadoFotografias!=null){
                    return listadoFotografias
                }else{
                    return null
                }
            }

        }
        listaFotos= task.execute().get()
    }

    private fun asynTaskObtenerListadoActividadesTaller(){

        val task = object : AsyncTask<Unit, Unit, List<ItemTaller>>(){


            override fun doInBackground(vararg p0: Unit?): List<ItemTaller>? {
                val listadoItemsTaller=obtenerActividadesTaller()
                if(listadoItemsTaller!=null){
                    return listadoItemsTaller
                }else{
                    return null
                }
            }

        }
        listaActividadesTaller= task.execute().get()
    }

    private fun asynTaskObtenerListadoRegistroAsistencia(){

        val task = object : AsyncTask<Unit, Unit, List<AsistenciaAdolescente>>(){


            override fun doInBackground(vararg p0: Unit?): List<AsistenciaAdolescente>? {
                val listadoAsistencia=obtenerRegistroAsistencia()
                if(listadoAsistencia!=null){
                    return listadoAsistencia
                }else{
                    return null
                }
            }

        }
        listaRegistroAsistencia= task.execute().get()
    }

    private fun obtenerRegistroFotografico(): List<RegistroFotografico>?{
        val servicio = ClienteApiRest.getRetrofitInstance().create(RegistroFotograficoServicio::class.java)
        val call = servicio.obtenerRegistroFotograficoPorInforme(informeSeleccionado.idInforme.toString(),"Bearer "+ token)

        try{
            val response=call.execute()

            if(response != null){

                val codigoRespuesta: Int= response.code()

                if(codigoRespuesta==200){
                    return response.body()!!
                }
                else{
                    return null
                }
            }
            else{
                return null
            }
        }catch (e: Exception){
            Log.i("ERROR",e.message)
            return  null
        }

    }

    private fun obtenerActividadesTaller(): List<ItemTaller>?{

        val servicio = ClienteApiRest.getRetrofitInstance().create(TallerServicio::class.java)
        if(informeSeleccionado.idTaller!=null){
            val call = servicio.listarItemsPorTaller(informeSeleccionado.idTaller.idTaller.toString(),"Bearer "+ token)

            try{
                val response=call.execute()

                if(response != null){

                    val codigoRespuesta: Int= response.code()

                    if(codigoRespuesta==200){
                        return response.body()!!
                    }
                    else{
                        return null
                    }
                }
                else{
                    return null
                }
            }catch (e: Exception){
                Log.i("ERROR",e.message)
                return  null
            }
        }else{
            return null
        }
    }

    private fun obtenerRegistroAsistencia(): List<AsistenciaAdolescente>?{
        val servicio = ClienteApiRest.getRetrofitInstance().create(RegistroAsistenciaServicio::class.java)
        if(informeSeleccionado.idTaller!=null){
            val call = servicio.listaAdolescentesInfractoresPorTaller(informeSeleccionado.idTaller,"Bearer "+ token)
            try{
                val response=call.execute()

                if(response != null){

                    val codigoRespuesta: Int= response.code()

                    if(codigoRespuesta==200){
                        return response.body()!!
                    }
                    else{
                        return null
                    }
                }
                else{
                    return null
                }
            }catch (e: Exception){
                Log.i("ERROR",e.message)
                return  null
            }
        }else{
            return null
        }
    }
}
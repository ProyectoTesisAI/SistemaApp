package ec.edu.epn.snai.Controlador.Activity

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

class EditarInformeActivity : AppCompatActivity() {

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
        this.listaFotos=i.getSerializableExtra("listaFotos") as ArrayList<RegistroFotografico>
        this.listaActividadesTaller = i.getSerializableExtra("listaActividades") as ArrayList<ItemTaller>
        this.listaRegistroAsistencia = i.getSerializableExtra("listaAsistencia") as ArrayList<AsistenciaAdolescente>

        asynTaskObtenerListadoFotografico()
        asynTaskObtenerListadoRegistroAsistencia()
        asynTaskObtenerListadoActividadesTaller()

        var adaptadorInforme=InformePagerAdaptador(supportFragmentManager,token,informeSeleccionado, listaFotos!!, listaActividadesTaller!!, listaRegistroAsistencia!!)
        viewpager.adapter=adaptadorInforme

        tabs.setupWithViewPager(viewpager)

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuAux=menu
        menuInflater.inflate(R.menu.menu_gestion, menu)

        menu.findItem(R.id.menu_editar).isVisible = false
        menu.findItem(R.id.menu_eliminar).isVisible=false
        menu.findItem(R.id.menu_guardar).isVisible=true
        return true

    }

    private fun asynTaskObtenerListadoFotografico(){

        val task = object : AsyncTask<Unit, Unit, List<RegistroFotografico>>(){


            override fun doInBackground(vararg p0: Unit?): List<RegistroFotografico> {
                val listadoFotografias=obtenerRegistroFotografico()
                return listadoFotografias!!
            }

        }
        listaFotos= task.execute().get()
    }

    private fun asynTaskObtenerListadoActividadesTaller(){

        val task = object : AsyncTask<Unit, Unit, List<ItemTaller>>(){


            override fun doInBackground(vararg p0: Unit?): List<ItemTaller> {
                val listadoItemsTaller=obtenerActividadesTaller()
                return listadoItemsTaller!!
            }

        }
        listaActividadesTaller= task.execute().get()
    }

    private fun asynTaskObtenerListadoRegistroAsistencia(){

        val task = object : AsyncTask<Unit, Unit, List<AsistenciaAdolescente>>(){


            override fun doInBackground(vararg p0: Unit?): List<AsistenciaAdolescente> {
                val listadoAsistencia=obtenerRegistroAsistencia()
                return listadoAsistencia!!
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
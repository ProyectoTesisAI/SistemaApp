package ec.edu.epn.snai.Controlador.Activity

import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import ec.edu.epn.snai.Controlador.AdaptadorTabs.InformePagerAdaptador
import ec.edu.epn.snai.Modelo.Informe
import ec.edu.epn.snai.Modelo.RegistroFotografico
import ec.edu.epn.snai.R
import ec.edu.epn.snai.Servicios.ClienteApiRest
import ec.edu.epn.snai.Servicios.RegistroFotograficoServicio
import kotlinx.android.synthetic.main.activity_ver_editar_informe.*

class VerEditarInformeActivity : AppCompatActivity() {

    private lateinit var informeSeleccionado: Informe
    private lateinit var token: String
    private var listaFotos: List<RegistroFotografico>?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ver_editar_informe)
        supportActionBar?.setDisplayHomeAsUpEnabled(true) //activo el botón Atrás
        supportActionBar?.elevation=0F

        val i = intent
        informeSeleccionado= i.getSerializableExtra("informeSeleccionado") as Informe
        token = i.getSerializableExtra("token") as String

        asynTaskObtenerListadoFotografico()

        var adaptadorInforme=InformePagerAdaptador(supportFragmentManager,token,informeSeleccionado, listaFotos!! )
        viewpager.adapter=adaptadorInforme

        tabs.setupWithViewPager(viewpager)

    }


    private fun asynTaskObtenerListadoFotografico(){

        val task = object : AsyncTask<Unit, Unit, List<RegistroFotografico>>(){


            override fun doInBackground(vararg p0: Unit?): List<RegistroFotografico> {
                val listadoItemsTaller=obtenerRegistroFotografico()
                return listadoItemsTaller!!
            }

        }
        listaFotos= task.execute().get()
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


}
package ec.edu.epn.snai.Controlador.Activity

import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import ec.edu.epn.snai.Controlador.Adaptador.RegistroFotograficoAdaptador
import ec.edu.epn.snai.Modelo.*
import ec.edu.epn.snai.R
import ec.edu.epn.snai.Servicios.ClienteApiRest
import ec.edu.epn.snai.Servicios.RegistroFotograficoServicio

class EditarRegistroFotograficoActivity : AppCompatActivity() {

    private var listaFotografias: List<RegistroFotografico>? = null
    private lateinit var informeSeleccionado: Informe
    private lateinit var token: String
    private var listaAdolescentesInfractores: List<AsistenciaAdolescente>?=null
    private var listaActividadesTaller: List<ItemTaller>?=null

    private lateinit var menuAux: Menu

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_fotografias)
        supportActionBar?.setDisplayHomeAsUpEnabled(true) //activo el botón Atrás

        val i = intent
        this.informeSeleccionado = i.getSerializableExtra("informeSeleccionado") as Informe
        this.token = i.getSerializableExtra("token") as String
        //this.listaFotografias = i.getSerializableExtra("listaFotos") as ArrayList<RegistroFotografico>
        this.listaAdolescentesInfractores = i.getSerializableExtra("listaAsistencia") as ArrayList<AsistenciaAdolescente>
        this.listaActividadesTaller = i.getSerializableExtra("listaActividades") as ArrayList<ItemTaller>

        asynTaskObtenerListadoFotografico()

        if (listaFotografias != null) {
            mostrarListadoAsistencia()
        }

    }

    fun mostrarListadoAsistencia() {

        var recyclerViewRegistroFotografico = findViewById(R.id.rv_editar_imagenes) as RecyclerView
        var adaptador = RegistroFotograficoAdaptador(listaFotografias)
        recyclerViewRegistroFotografico.adapter = adaptador
        recyclerViewRegistroFotografico.layoutManager = LinearLayoutManager(this@EditarRegistroFotograficoActivity)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuAux = menu
        menuInflater.inflate(R.menu.menu_gestion, menu)

        menu.findItem(R.id.menu_editar).isVisible = false
        menu.findItem(R.id.menu_eliminar).isVisible = false
        menu.findItem(R.id.menu_guardar).isVisible = true
        return true

    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        when(item?.itemId){

            R.id.menu_guardar->{

                val intent = Intent(applicationContext, VerEditarInformeActivity::class.java)
                intent.putExtra("token",token)
                intent.putExtra("informeSeleccionado", informeSeleccionado)
                intent.putExtra("listaActividades", ArrayList(listaActividadesTaller))
                intent.putExtra("listaAsistencia", ArrayList(listaAdolescentesInfractores))
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


            override fun doInBackground(vararg p0: Unit?): List<RegistroFotografico> {
                val listadoFotografias=obtenerRegistroFotografico()
                return listadoFotografias!!
            }

        }
        listaFotografias= task.execute().get()
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
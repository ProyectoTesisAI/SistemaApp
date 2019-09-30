package ec.edu.epn.snai.Controlador.Activity

import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import ec.edu.epn.snai.Controlador.Adaptador.IngresarRegistroAsistenciaAdaptador
import ec.edu.epn.snai.Modelo.*
import ec.edu.epn.snai.R
import ec.edu.epn.snai.Servicios.ClienteApiRest
import ec.edu.epn.snai.Servicios.RegistroAsistenciaServicio

class AgregarRegistroAsistenciaActivity : AppCompatActivity(){

    private var listaRegistroAsistencia: List<AsistenciaAdolescente>?=null
    private var listaActividadesTaller: List<ItemTaller>?=null
    private lateinit var tallerSeleccionado: Taller
    private lateinit var token:String

    private lateinit var btnAgregarInforme : FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ver_registro_asistencia)
        supportActionBar?.setDisplayHomeAsUpEnabled(true) //activo el botón Atrás

        val i=intent
        this.tallerSeleccionado = i.getSerializableExtra("taller_seleccionado") as Taller
        this.token = i.getSerializableExtra("token") as String
        this.listaActividadesTaller = i.getSerializableExtra("items_taller_seleccionado") as ArrayList<ItemTaller>
        //this.listaRegistroAsistencia = i.getSerializableExtra("listaAsistencia") as ArrayList<AsistenciaAdolescente>
        asynTaskObtenerListadoRegistroAsistencia()
        if(listaRegistroAsistencia!=null){
            mostrarListadoAsistencia()
        }

        btnAgregarInforme = findViewById(R.id.fab_agregar_informe_nuevo)
        btnAgregarInforme.setOnClickListener {
            val intent = Intent(this@AgregarRegistroAsistenciaActivity, AgregarInformeActivity::class.java)
            intent.putExtra("token",token)
            intent.putExtra("tallerSeleccionado", tallerSeleccionado)
            intent.putExtra("listaActividades", java.util.ArrayList(listaActividadesTaller))
            intent.putExtra("listaAsistencia", java.util.ArrayList(listaRegistroAsistencia))
            startActivity(intent)
        }

    }

    fun mostrarListadoAsistencia(){

        var recyclerViewRegistroAsistencia=findViewById(R.id.rv_registro_asistencia) as RecyclerView
        var adaptador = IngresarRegistroAsistenciaAdaptador(listaRegistroAsistencia)
        recyclerViewRegistroAsistencia.adapter=adaptador
        recyclerViewRegistroAsistencia.layoutManager = LinearLayoutManager(this@AgregarRegistroAsistenciaActivity)
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

    private fun obtenerRegistroAsistencia(): List<AsistenciaAdolescente>?{
        val servicio = ClienteApiRest.getRetrofitInstance().create(RegistroAsistenciaServicio::class.java)
        if(tallerSeleccionado!=null){
            val call = servicio.listaAdolescentesInfractoresPorTaller(tallerSeleccionado,"Bearer "+ token)
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
package ec.edu.epn.snai.Controlador.Activity

import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.MenuItem
import android.widget.Toast
import ec.edu.epn.snai.Controlador.Adaptador.ListaEditarRegistroAsistenciaAdaptador
import ec.edu.epn.snai.Modelo.*
import ec.edu.epn.snai.R
import ec.edu.epn.snai.Servicios.ClienteApiRest
import ec.edu.epn.snai.Servicios.RegistroAsistenciaServicio
import kotlinx.android.synthetic.main.activity_agregar_registro_asistencia.*

class CrearRegistroAsistenciaActivity : AppCompatActivity(){

    private var listaRegistroAsistencia: List<AsistenciaAdolescente>?=null
    private var listaActividadesTaller: List<ItemTaller>?=null
    private lateinit var tallerSeleccionado: Taller
    private lateinit var token:String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agregar_registro_asistencia)
        supportActionBar?.setDisplayHomeAsUpEnabled(true) //activo el botón Atrás

        val i=intent
        this.tallerSeleccionado = i.getSerializableExtra("taller_seleccionado") as Taller
        this.token = i.getSerializableExtra("token") as String
        this.listaActividadesTaller = i.getSerializableExtra("items_taller_seleccionado") as ArrayList<ItemTaller>


        asynTaskObtenerListadoRegistroAsistencia()
        mostrarListadoAsistencia()

        fab_agregar_informe_nuevo.setOnClickListener {
            abrirInforme()
        }

    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        finish()
        return true
    }

    private fun mostrarListadoAsistencia(){

        if(listaRegistroAsistencia!=null){

            val recyclerViewRegistroAsistencia=findViewById(R.id.rv_registro_asistencia) as RecyclerView
            val adaptador =
                ListaEditarRegistroAsistenciaAdaptador(listaRegistroAsistencia)
            recyclerViewRegistroAsistencia.adapter=adaptador
            recyclerViewRegistroAsistencia.layoutManager = LinearLayoutManager(this@CrearRegistroAsistenciaActivity)
        }

    }

    private fun asynTaskObtenerListadoRegistroAsistencia(){

        val task = object : AsyncTask<Unit, Unit, List<AsistenciaAdolescente>>(){


            override fun doInBackground(vararg p0: Unit?): List<AsistenciaAdolescente>? {
                val listadoAsistencia=servicioOtenerRegistroAsistencia()
                if(listadoAsistencia!=null){
                    return listadoAsistencia
                }else{
                    return null
                }
            }

        }
        listaRegistroAsistencia= task.execute().get()
    }

    private fun servicioOtenerRegistroAsistencia(): List<AsistenciaAdolescente>?{
        val servicio = ClienteApiRest.getRetrofitInstance().create(RegistroAsistenciaServicio::class.java)

        if(tallerSeleccionado!=null){

            val call = servicio.listaAdolescentesInfractoresPorTaller(tallerSeleccionado,"Bearer "+ token)
            try{
                val response=call.execute()

                if(response != null){

                    if(response.code()==200){
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
                return  null
            }
        }else{
            return null
        }
    }

    private fun abrirInforme(){

        val cantidadAsistentes=obtenerCantidadParticipantes()

        if(cantidadAsistentes > 0 ){

            val intent = Intent(this@CrearRegistroAsistenciaActivity, CrearInformeActivity::class.java)
            intent.putExtra("token",token)
            intent.putExtra("tallerSeleccionado", tallerSeleccionado)
            intent.putExtra("listaActividades", ArrayList(listaActividadesTaller))
            intent.putExtra("listaAsistencia", ArrayList(listaRegistroAsistencia))
            startActivity(intent)
        }
        else{
            Toast.makeText(applicationContext, "Debe de seleccionar la asistencia de los Adolescentes Infractores", Toast.LENGTH_SHORT).show()
        }


    }

    private fun obtenerCantidadParticipantes():Int{
        var cantidad=0
        if(!listaRegistroAsistencia.isNullOrEmpty()){
            listaRegistroAsistencia!!.forEach{
                if(it.asistio==true){
                    cantidad++
                }
            }
        }
        return cantidad
    }
}
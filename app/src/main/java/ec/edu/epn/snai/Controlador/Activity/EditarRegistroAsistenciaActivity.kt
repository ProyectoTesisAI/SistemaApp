package ec.edu.epn.snai.Controlador.Activity

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.MenuItem
import android.widget.Toast
import ec.edu.epn.snai.Controlador.Adaptador.IngresarRegistroAsistenciaAdaptador
import ec.edu.epn.snai.Controlador.Adaptador.RegistroAsistenciaAdaptador
import ec.edu.epn.snai.Modelo.*
import ec.edu.epn.snai.R

class EditarRegistroAsistenciaActivity : AppCompatActivity(){

    private var listaAdolescentesInfractores: ArrayList<AsistenciaAdolescente>?=null
    private var listaActividadesTaller: ArrayList<ItemTaller>?=null
    private lateinit var informeSeleccionado: Informe
    private lateinit var token:String

    private lateinit var btnAgregarInforme : FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ver_registro_asistencia)
        supportActionBar?.setDisplayHomeAsUpEnabled(true) //activo el botón Atrás

        val i=intent
        this.listaAdolescentesInfractores = i.getSerializableExtra("listaAsistencia") as ArrayList<AsistenciaAdolescente>

        if(listaAdolescentesInfractores!=null){
            mostrarListadoAsistencia()
        }

        btnAgregarInforme = findViewById(R.id.fab_agregar_informe_nuevo)
        btnAgregarInforme.setOnClickListener {
            abrirInforme()
        }

    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        finish()
        return true
    }

    fun mostrarListadoAsistencia(){

        var recyclerViewRegistroAsistencia=findViewById(R.id.rv_registro_asistencia) as RecyclerView
        var adaptador = IngresarRegistroAsistenciaAdaptador(listaAdolescentesInfractores)
        recyclerViewRegistroAsistencia.adapter=adaptador
        recyclerViewRegistroAsistencia.layoutManager = LinearLayoutManager(this@EditarRegistroAsistenciaActivity)
    }

    private fun abrirInforme(){

        val cantidadAsistentes=obtenerCantidadParticipantes()

        if(cantidadAsistentes > 0 ){

            val i=intent
            this.informeSeleccionado = i.getSerializableExtra("informeSeleccionado") as Informe
            this.token = i.getSerializableExtra("token") as String
            this.listaActividadesTaller = i.getSerializableExtra("listaActividades") as ArrayList<ItemTaller>

            val intent = Intent(this@EditarRegistroAsistenciaActivity, EditarInformeAgregarActivity::class.java)
            intent.putExtra("token",token)
            intent.putExtra("informeSeleccionado", informeSeleccionado)
            intent.putExtra("listaActividades", ArrayList(listaActividadesTaller))
            intent.putExtra("listaAsistencia", ArrayList(listaAdolescentesInfractores))
            startActivity(intent)
        }
        else{
            Toast.makeText(applicationContext, "Debe de seleccionar la asistencia de los Adolescentes Infractores", Toast.LENGTH_SHORT).show()
        }


    }

    private fun obtenerCantidadParticipantes():Int{
        var cantidad=0
        if(!listaAdolescentesInfractores.isNullOrEmpty()){
            listaAdolescentesInfractores!!.forEach{
                if(it.asistio==true){
                    cantidad++
                }
            }
        }
        return cantidad
    }
}
package ec.edu.epn.snai.Controlador.Activity

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import ec.edu.epn.snai.Controlador.Adaptador.IngresarRegistroAsistenciaAdaptador
import ec.edu.epn.snai.Controlador.Adaptador.RegistroAsistenciaAdaptador
import ec.edu.epn.snai.Modelo.*
import ec.edu.epn.snai.R

class EditarRegistroAsistenciaActivity : AppCompatActivity(){

    private var listaAdolescentesInfractores: List<AsistenciaAdolescente>?=null
    private var listaActividadesTaller: List<ItemTaller>?=null
    private lateinit var informeSeleccionado: Informe
    private lateinit var token:String

    private lateinit var btnAgregarInforme : FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ver_registro_asistencia)
        supportActionBar?.setDisplayHomeAsUpEnabled(true) //activo el botón Atrás

        val i=intent
        this.informeSeleccionado = i.getSerializableExtra("informeSeleccionado") as Informe
        this.token = i.getSerializableExtra("token") as String
        this.listaActividadesTaller = i.getSerializableExtra("listaActividades") as ArrayList<ItemTaller>
        this.listaAdolescentesInfractores = i.getSerializableExtra("listaAsistencia") as ArrayList<AsistenciaAdolescente>

        if(listaAdolescentesInfractores!=null){
            mostrarListadoAsistencia()
        }

        btnAgregarInforme = findViewById(R.id.fab_agregar_informe_nuevo)
        btnAgregarInforme.setOnClickListener {
            val intent = Intent(this@EditarRegistroAsistenciaActivity, EditarInformeAgregarActivity::class.java)
            intent.putExtra("token",token)
            intent.putExtra("informeSeleccionado", informeSeleccionado)
            intent.putExtra("listaActividades", java.util.ArrayList(listaActividadesTaller))
            intent.putExtra("listaAsistencia", java.util.ArrayList(listaAdolescentesInfractores))
            startActivity(intent)
        }

    }

    fun mostrarListadoAsistencia(){

        var recyclerViewRegistroAsistencia=findViewById(R.id.rv_registro_asistencia) as RecyclerView
        var adaptador = IngresarRegistroAsistenciaAdaptador(listaAdolescentesInfractores)
        recyclerViewRegistroAsistencia.adapter=adaptador
        recyclerViewRegistroAsistencia.layoutManager = LinearLayoutManager(this@EditarRegistroAsistenciaActivity)
    }
}
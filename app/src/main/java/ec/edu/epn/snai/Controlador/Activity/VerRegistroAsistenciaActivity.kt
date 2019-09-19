package ec.edu.epn.snai.Controlador.Activity

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.CheckBox
import ec.edu.epn.snai.Controlador.Adaptador.RegistroAsistenciaAdaptador
import ec.edu.epn.snai.Modelo.AdolescenteInfractor
import ec.edu.epn.snai.Modelo.AsistenciaAdolescente
import ec.edu.epn.snai.Modelo.Taller
import ec.edu.epn.snai.R
import ec.edu.epn.snai.Servicios.ClienteApiRest
import ec.edu.epn.snai.Servicios.RegistroAsistenciaServicio
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class VerRegistroAsistenciaActivity : AppCompatActivity(){

    private var listaAdolescentesInfractores: List<AsistenciaAdolescente>?=null

    private lateinit var tallerActual: Taller
    private lateinit var token:String

    private lateinit var btnAgregarInforme : FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ver_registro_asistencia)
        supportActionBar?.setDisplayHomeAsUpEnabled(true) //activo el botón Atrás

        tallerActual = intent.getSerializableExtra("tallerActual") as Taller
        token = intent.getSerializableExtra("token") as String

        /*CONSUMO DEL SERVICIO WEB Y ASIGNARLO EN EL RECYCLERVIEW*/
        val servicio = ClienteApiRest.getRetrofitInstance().create(RegistroAsistenciaServicio::class.java)

        if(tallerActual!=null ){
            val call = servicio.listaAdolescentesInfractoresPorTaller(tallerActual,"Bearer "+ token)
            call.enqueue(object : Callback<List<AsistenciaAdolescente>> {
                override fun onResponse(call: Call<List<AsistenciaAdolescente>>, response: Response<List<AsistenciaAdolescente>>) {
                    if (response.isSuccessful) {
                        listaAdolescentesInfractores = response.body()

                        if(listaAdolescentesInfractores!=null){
                            mostrarListadoAsistencia()
                        }
                    }
                }

                override fun onFailure(call: Call<List<AsistenciaAdolescente>>, t: Throwable) {
                    call.cancel()
                }
            })
        }

        btnAgregarInforme = findViewById(R.id.fab_agregar_informe_nuevo)
        btnAgregarInforme.setOnClickListener {
            val intent = Intent(this@VerRegistroAsistenciaActivity, InformeAgregarActivity::class.java)
            intent.putExtra("tallerActual", tallerActual)
            intent.putExtra("token", token)
            println(listaAdolescentesInfractores)
            startActivity(intent)
        }

    }

    fun mostrarListadoAsistencia(){

        var recyclerViewRegistroAsistencia=findViewById(R.id.rv_registro_asistencia) as RecyclerView
        var adaptador = RegistroAsistenciaAdaptador(listaAdolescentesInfractores)
        recyclerViewRegistroAsistencia.adapter=adaptador
        recyclerViewRegistroAsistencia.layoutManager = LinearLayoutManager(this@VerRegistroAsistenciaActivity)
    }
}
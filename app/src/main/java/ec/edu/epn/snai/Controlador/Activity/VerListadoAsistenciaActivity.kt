package ec.edu.epn.snai.Controlador.Activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import ec.edu.epn.snai.Controlador.Adaptador.ListadoAsistenciaAdaptador
import ec.edu.epn.snai.Modelo.AdolescenteInfractor
import ec.edu.epn.snai.Modelo.AsistenciaAdolescente
import ec.edu.epn.snai.Modelo.Taller
import ec.edu.epn.snai.R
import ec.edu.epn.snai.Servicios.ClienteApiRest
import ec.edu.epn.snai.Servicios.RegistroAsistenciaServicio
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class VerListadoAsistenciaActivity : AppCompatActivity(){

    private var listaAdolescentesInfractores: List<AsistenciaAdolescente>?=null

    private lateinit var tallerActual: Taller
    private lateinit var token:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ver_listado_asistencia)
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
        }/*else if(tallerActual.idUdi!=null){
            val call = servicio.listaAdolescentesInfractoresPorUzdi(tallerActual.idUdi,"Bearer "+ token)
            call.enqueue(object : Callback<List<AdolescenteInfractor>> {
                override fun onResponse(call: Call<List<AdolescenteInfractor>>, response: Response<List<AdolescenteInfractor>>) {
                    if (response.isSuccessful) {
                        listaAdolescentesInfractores = response.body()

                        if(listaAdolescentesInfractores!=null){
                            mostrarListadoAsistencia()
                        }

                    }
                }

                override fun onFailure(call: Call<List<AdolescenteInfractor>>, t: Throwable) {
                    call.cancel()
                }
            })
        }*/
    }

    fun mostrarListadoAsistencia(){

        var recyclerViewRegistroAsistencia=findViewById(R.id.rv_listado_asistencia) as RecyclerView
        var adaptador = ListadoAsistenciaAdaptador(listaAdolescentesInfractores)
        recyclerViewRegistroAsistencia.adapter=adaptador
        recyclerViewRegistroAsistencia.layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL,false)

    }
}
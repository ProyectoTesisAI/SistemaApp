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
import ec.edu.epn.snai.Modelo.Taller
import ec.edu.epn.snai.R
import ec.edu.epn.snai.Servicios.ClienteApiRest
import ec.edu.epn.snai.Servicios.RegistroAsistenciaServicio
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class VerRegistroAsistenciaActivity : AppCompatActivity(){

    private var adaptador: RegistroAsistenciaAdaptador? = null
    private var listaAdolescentesInfractores: List<AdolescenteInfractor>?=null
    private lateinit var recyclerViewRegistroAsistencia: RecyclerView

    private lateinit var tallerActual: Taller
    private lateinit var token:String

    private lateinit var btnAgregarInforme : FloatingActionButton

    private lateinit var ckcAdolescenteSeleccionado:CheckBox

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ver_registro_asistencia)
        supportActionBar?.setDisplayHomeAsUpEnabled(true) //activo el botón Atrás

        tallerActual = intent.getSerializableExtra("tallerActual") as Taller
        token = intent.getSerializableExtra("token") as String

        /*CONSUMO DEL SERVICIO WEB Y ASIGNARLO EN EL RECYCLERVIEW*/
        val servicio = ClienteApiRest.getRetrofitInstance().create(RegistroAsistenciaServicio::class.java)

        if(tallerActual.idCai!=null ){
            val call = servicio.listaAdolescentesInfractoresPorCai(tallerActual.idCai,"Bearer "+ token)
            call.enqueue(object : Callback<List<AdolescenteInfractor>> {
                override fun onResponse(call: Call<List<AdolescenteInfractor>>, response: Response<List<AdolescenteInfractor>>) {
                    if (response.isSuccessful) {
                        listaAdolescentesInfractores = response.body()
                        recyclerViewRegistroAsistencia=findViewById(R.id.rv_registro_asistencia)
                        recyclerViewRegistroAsistencia.layoutManager = LinearLayoutManager(this@VerRegistroAsistenciaActivity)
                        adaptador = RegistroAsistenciaAdaptador(listaAdolescentesInfractores,this@VerRegistroAsistenciaActivity)
                        recyclerViewRegistroAsistencia.adapter=adaptador
                    }
                }

                override fun onFailure(call: Call<List<AdolescenteInfractor>>, t: Throwable) {
                    call.cancel()
                }
            })
        }else if(tallerActual.idUdi!=null){
            val call = servicio.listaAdolescentesInfractoresPorUzdi(tallerActual.idUdi,"Bearer "+ token)
            call.enqueue(object : Callback<List<AdolescenteInfractor>> {
                override fun onResponse(call: Call<List<AdolescenteInfractor>>, response: Response<List<AdolescenteInfractor>>) {
                    if (response.isSuccessful) {
                        listaAdolescentesInfractores = response.body()
                        recyclerViewRegistroAsistencia=findViewById (R.id.rv_registro_asistencia)
                        recyclerViewRegistroAsistencia.layoutManager=LinearLayoutManager(this@VerRegistroAsistenciaActivity)
                        adaptador = RegistroAsistenciaAdaptador(listaAdolescentesInfractores,this@VerRegistroAsistenciaActivity)
                        recyclerViewRegistroAsistencia.adapter=adaptador
                    }
                }

                override fun onFailure(call: Call<List<AdolescenteInfractor>>, t: Throwable) {
                    call.cancel()
                }
            })
        }

        btnAgregarInforme = findViewById(R.id.fab_agregar_informe_nuevo)
        btnAgregarInforme.setOnClickListener {
            val intent = Intent(this@VerRegistroAsistenciaActivity, InformeAgregarActivity::class.java)
            intent.putExtra("tallerActual", tallerActual)
            intent.putExtra("token", token)
            startActivity(intent)
        }
    }
}
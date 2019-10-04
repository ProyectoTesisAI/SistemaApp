package ec.edu.epn.snai.Controlador.Activity

import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
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
import ec.edu.epn.snai.Servicios.*
import ec.edu.epn.snai.Utilidades.Constantes
import kotlinx.android.synthetic.main.activity_ver_editar_informe.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.ArrayList

class VerEditarInformeActivity : AppCompatActivity() {

    private lateinit var informeSeleccionado: Informe
    private lateinit var token: String
    private var listaActividadesTaller: List<ItemTaller>?=null
    private var listaRegistroAsistencia: List<AsistenciaAdolescente>?=null
    private lateinit var usuario: Usuario

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ver_editar_informe)
        supportActionBar?.setDisplayHomeAsUpEnabled(true) //activo el botón Atrás
        supportActionBar?.elevation=0F

        val i = intent
        this.informeSeleccionado= i.getSerializableExtra("informeSeleccionado") as Informe
        this.token = i.getSerializableExtra("token") as String
        this.usuario=i.getSerializableExtra("usuario") as Usuario


        asynTaskObtenerListadoRegistroAsistencia()
        asynTaskObtenerListadoActividadesTaller()

        if(listaActividadesTaller != null && listaRegistroAsistencia != null){
            val adaptadorInforme=InformePagerAdaptador(supportFragmentManager,token,informeSeleccionado, listaActividadesTaller!!, listaRegistroAsistencia!!)
            viewpager.adapter=adaptadorInforme
            tabs.setupWithViewPager(viewpager)
        }
        else{
            Toast.makeText(applicationContext,"El Informe no posee actividades o fotos, Por favor edite el taller desde la aplicación web",Toast.LENGTH_SHORT).show()
            finish()
        }



    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {

        val rol=this.usuario.idRolUsuarioCentro.idRol.rol

        if(rol != null){
            if(rol.equals(Constantes.ROL_ADMINISTRADOR)){

                menuInflater.inflate(R.menu.menu_gestion, menu)

                menu.findItem(R.id.menu_editar).isVisible = true
                menu.findItem(R.id.menu_eliminar).isVisible=true
                menu.findItem(R.id.menu_guardar).isVisible=false
            }
        }
        return true

    }


    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        when(item?.itemId){

            R.id.menu_editar->{

                val intent = Intent(applicationContext, EditarRegistroAsistenciaActivity::class.java)
                intent.putExtra("token",token)
                intent.putExtra("informeSeleccionado", informeSeleccionado)
                intent.putExtra("listaActividades", ArrayList(listaActividadesTaller))
                intent.putExtra("listaAsistencia", ArrayList(listaRegistroAsistencia))
                startActivity(intent)

            }
            R.id.menu_eliminar->{

                val builder = AlertDialog.Builder(this)

                builder.setTitle("Eliminar")
                builder.setMessage("¿Está seguro de eliminar?")
                builder.setPositiveButton("OK",
                    DialogInterface.OnClickListener { dialog, which ->

                        eliminarInforme(informeSeleccionado.idInforme)
                    })
                builder.setNegativeButton("CANCELAR",
                    DialogInterface.OnClickListener { dialog, which ->
                        //finish()
                    })
                builder.show()


            }
            else->{
                finish()
            }
        }
        return true
    }



    private fun asynTaskObtenerListadoActividadesTaller(){

        val task = object : AsyncTask<Unit, Unit, List<ItemTaller>>(){


            override fun doInBackground(vararg p0: Unit?): List<ItemTaller>? {
                val listadoItemsTaller=obtenerActividadesTaller()
                if(listadoItemsTaller!=null){
                    return listadoItemsTaller
                }else{
                    return null
                }
            }

        }
        listaActividadesTaller= task.execute().get()
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

    private fun eliminarInforme(idInforme: Int){
        val servicio = ClienteApiRest.getRetrofitInstance().create(InformeServicio::class.java)
        val call = servicio.eliminarInforme(idInforme.toString(), "Bearer " + token)

        call.enqueue(object : Callback<Void> {

            override fun onFailure(call: Call<Void>, t: Throwable) {

                Toast.makeText(applicationContext, "Ha ocurrido un error al eliminar el Informe", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<Void>, response: Response<Void>) {

                if(response != null){

                    if(response.code()==204){
                        Toast.makeText(applicationContext, "Se ha eliminado correctamente el Informe", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                }
            }
        })
    }
}
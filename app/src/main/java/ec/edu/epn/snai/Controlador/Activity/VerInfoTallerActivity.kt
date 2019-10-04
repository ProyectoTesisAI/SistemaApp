package ec.edu.epn.snai.Controlador.Activity

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import ec.edu.epn.snai.Controlador.Adaptador.ItemInformeAdaptador
import ec.edu.epn.snai.Controlador.AdaptadorTabs.TallerAdaptadorTabs
import ec.edu.epn.snai.Modelo.CAI
import ec.edu.epn.snai.Modelo.ItemTaller
import ec.edu.epn.snai.Modelo.Taller
import ec.edu.epn.snai.Modelo.Usuario
import ec.edu.epn.snai.R
import ec.edu.epn.snai.Servicios.ClienteApiRest
import ec.edu.epn.snai.Servicios.TallerServicio
import ec.edu.epn.snai.Utilidades.Constantes
import kotlinx.android.synthetic.main.activity_ver_info_taller.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.ArrayList

class VerInfoTallerActivity : AppCompatActivity() {

    private lateinit var token:String
    private lateinit var taller:Taller
    private var itemsTaller: List<ItemTaller>?=null
    private lateinit var usuario: Usuario

    private lateinit var btftCrearInforme: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ver_info_taller)
        supportActionBar?.setDisplayHomeAsUpEnabled(true) //activo el botón Atrás
        supportActionBar?.elevation=0F


        val i = intent
        this.taller = i.getSerializableExtra("taller_seleccionado") as Taller
        this.token = i.getSerializableExtra("token") as String
        this.usuario=i.getSerializableExtra("usuario") as Usuario

        asynTaskObtenerListadoItemsTaller()

        if(itemsTaller != null){

            val adaptador=TallerAdaptadorTabs(supportFragmentManager,token,taller,ArrayList(itemsTaller))
            view_pager_taller.adapter=adaptador
            tabs_taller.setupWithViewPager(view_pager_taller)
        }
        else{
            Toast.makeText(applicationContext,"El Taller no posee actividades, Por favor edite el taller desde la aplicación web",Toast.LENGTH_SHORT).show()
            finish()
        }

        btftCrearInforme=findViewById(R.id.fab_crear_informe)
        btftCrearInforme.setOnClickListener {
            val intent = Intent(applicationContext, AgregarRegistroAsistenciaActivity::class.java)
            intent.putExtra("token",token)
            intent.putExtra("taller_seleccionado", taller)
            if(itemsTaller != null){
                intent.putExtra("items_taller_seleccionado", ArrayList(itemsTaller))
            }

            startActivity(intent)
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

                val intent = Intent(applicationContext, EditarTallerActivity::class.java)
                intent.putExtra("token",token)
                intent.putExtra("taller_seleccionado", taller)
                intent.putExtra("usuario", usuario)
                if(itemsTaller != null){
                    intent.putExtra("items_taller_seleccionado", ArrayList(itemsTaller))
                }

                startActivity(intent)

            }
            R.id.menu_eliminar->{

                val builder = AlertDialog.Builder(this)

                builder.setTitle("Eliminar")
                builder.setMessage("¿Está seguro de eliminar?")
                builder.setPositiveButton("OK",
                    DialogInterface.OnClickListener { dialog, which ->

                        eliminarTaller(taller.idTaller)
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


    private fun asynTaskObtenerListadoItemsTaller(){

        val task = object : AsyncTask<Unit, Unit, List<ItemTaller>>(){


            override fun doInBackground(vararg p0: Unit?): List<ItemTaller>? {
                val listadoItemsTaller=obtenerItemsTaller()
                if(listadoItemsTaller != null){
                    return listadoItemsTaller
                }
                else{
                    return null
                }
            }

        }
        itemsTaller= task.execute().get()

    }

    private fun obtenerItemsTaller(): List<ItemTaller>?{
        val servicio = ClienteApiRest.getRetrofitInstance().create(TallerServicio::class.java)
        val call = servicio.listarItemsPorTaller(taller.idTaller.toString(),"Bearer "+ token)
        val response= call.execute()

        if(response != null){

            if(response.code() == 200){
                val itemsTallerAux =response.body()
                Log.i("item", itemsTallerAux?.size.toString())
                return itemsTallerAux
            }
            else{
                return null
            }
        }
        else{
            return null
        }


    }

    private fun eliminarTaller(idTaller: Int){
        val servicio = ClienteApiRest.getRetrofitInstance().create(TallerServicio::class.java)
        val call = servicio.eliminarTaller(idTaller.toString(), "Bearer " + token)

        call.enqueue(object : Callback<Void>{

            override fun onFailure(call: Call<Void>, t: Throwable) {

                Toast.makeText(applicationContext, "Ha ocurrido un error al eliminar el Taller", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<Void>, response: Response<Void>) {

                if(response.code()==204){
                    Toast.makeText(applicationContext, "Se ha eliminado correctamente el Taller", Toast.LENGTH_SHORT).show()

                    val intent = Intent(this@VerInfoTallerActivity, MainActivity::class.java)
                    //seteo la bandera FLAG_ACTIVITY_CLEAR_TOP ya que si la actividad que se lanza con el intent ya está en la pila de actividades,
                    // en lugar de lanzar una nueva instancia de dicha actividad, el resto de activities en la pila serán cerradas
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    intent.putExtra("usuario",usuario)
                    startActivity(intent)
                    finish()
                }

            }
        })
    }


}
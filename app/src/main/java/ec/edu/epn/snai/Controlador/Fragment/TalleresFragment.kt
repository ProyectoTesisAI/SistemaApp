package ec.edu.epn.snai.Controlador.Fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import ec.edu.epn.snai.Controlador.Adaptador.ListaTalleresAdaptador
import ec.edu.epn.snai.R
import ec.edu.epn.snai.Servicios.ClienteApiRest
import ec.edu.epn.snai.Servicios.TallerServicio
import ec.edu.epn.snai.Controlador.Activity.TallerTabbedActivity
import ec.edu.epn.snai.Modelo.*
import ec.edu.epn.snai.Utilidades.Constantes
import kotlinx.android.synthetic.main.fragment_talleres.view.*

class TalleresFragment: Fragment(), ListaTalleresAdaptador.TallerOnItemClickListener{

    private var listaTalleres: List<Taller>?=null
    private lateinit var token:String
    private var tipoTaller:String?=null
    private lateinit var usuario: Usuario

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if(arguments!=null){
            usuario=arguments?.getSerializable("usuario") as Usuario
            tipoTaller=arguments?.getSerializable("tipoTaller") as String?
            this.token=usuario.token


        }


    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView= inflater.inflate(R.layout.fragment_talleres,container,false)


        if(tipoTaller != null){
            asignarObtenerListaTalleres(usuario, rootView, tipoTaller!!)
        }

        return rootView
    }

    override fun OnItemClick(posicion: Int) {

        val intent = Intent(context, TallerTabbedActivity::class.java)
        intent.putExtra("tallerSeleccionado", listaTalleres?.get(posicion))
        intent.putExtra("usuario", usuario)
        startActivity(intent)
    }


    /**************** Obtener Lista de Talleres¨***********************/
    private fun asignarObtenerListaTalleres(usuario: Usuario, view: View, tipoTaller: String){

        asynTaskObtenerListadoTalleres(usuario, view,tipoTaller)
    }

    private fun asynTaskObtenerListadoTalleres(usuario: Usuario,rootView: View, tipoTaller: String){


        try{

            val task = @SuppressLint("StaticFieldLeak")
            object : AsyncTask<Unit, Unit, List<Taller>>() {

                override fun doInBackground(vararg p0: Unit?): List<Taller>? {

                    val rol=usuario.getIdRolUsuarioCentro().getIdRol().getRol()

                    if(rol.equals(Constantes.ROL_ADMINISTRADOR)|| rol.equals(Constantes.ROL_SUBDIRECTOR)){

                        return servicioObtenerTalleresSinInforme()

                    }else if( rol.equals(Constantes.ROL_LIDER_UZDI) || rol.equals(Constantes.ROL_DIRECTOR_UZDI)){

                        return servicioObtenerTalleresSinInformeSoloUZDI()

                    }else if(rol.equals(Constantes.ROL_COORDINADOR_CAI) || rol.equals(Constantes.ROL_DIRECTOR_CAI)){

                        return servicioObtenerTalleresSinInformeSoloCAI()

                    }else {
                        return servicioObtenerTalleresSinInformePorUsuario(usuario)
                    }
                }

                override fun onPostExecute(result: List<Taller>?) {
                    super.onPostExecute(result)
                    if(result != null){

                        val listaTallerAux=obtenerTalleresPorTipo(result,tipoTaller)
                        if(listaTallerAux != null){

                            if(listaTallerAux.size >0 ){
                                rootView.txtSinTalleres.visibility=View.INVISIBLE
                                asignarListaTalleresRecyclerView(listaTallerAux, rootView)
                            }
                            else{
                                rootView.txtSinTalleres.visibility=View.VISIBLE
                            }
                        }
                        else{
                            rootView.txtSinTalleres.visibility=View.VISIBLE
                        }

                    }
                    else{
                        rootView.txtSinTalleres.visibility=View.VISIBLE
                    }

                }
            }
            task.execute()

        }catch (e:Exception){
            Toast.makeText(context, "Ha ocurrido un error al obtener la Lista de Talleres", Toast.LENGTH_SHORT).show()
        }


    }

    private fun servicioObtenerTalleresSinInforme(): List<Taller>?{

        try{
            val servicioRegistroAsistencia= ClienteApiRest.getRetrofitInstance().create(TallerServicio::class.java)
            val call =servicioRegistroAsistencia.obtenerTalleresSinInforme( "Bearer $token")
            val response = call.execute()

            if(response != null){

                if(response.code() == 200){
                    val listaTalleres= response.body()
                    return listaTalleres
                }
                else{
                    return null
                }
            }
            else{
                return null
            }

        }catch (e:Exception){
            return null
        }
    }

    private fun servicioObtenerTalleresSinInformePorUsuario(usuario: Usuario): List<Taller>?{

        try{

            val servicioRegistroAsistencia= ClienteApiRest.getRetrofitInstance().create(TallerServicio::class.java)
            val call =servicioRegistroAsistencia.obtenerTalleresSinInformePorUsuario( usuario,"Bearer $token")
            val response = call.execute()

            if(response != null){

                if(response.code() == 200){
                    val listaTalleresPorUsuario= response.body()
                    return listaTalleresPorUsuario
                }
                else{
                    return null
                }
            }
            else{
                return null
            }
        }catch (e:Exception){
            return null
        }


    }

    private fun servicioObtenerTalleresSinInformeSoloUZDI(): List<Taller>?{

        try{

            val servicioRegistroAsistencia= ClienteApiRest.getRetrofitInstance().create(TallerServicio::class.java)
            val call =servicioRegistroAsistencia.obtenerTalleresSinInformeSoloUZDI( "Bearer $token")
            val response = call.execute()

            if(response != null){

                if(response.code() == 200){
                    val listaTalleresUzdi= response.body()
                    return listaTalleresUzdi
                }
                else{
                    return null
                }
            }
            else{
                return null
            }
        }catch (e:Exception){
            return null
        }

    }

    private fun servicioObtenerTalleresSinInformeSoloCAI(): List<Taller>?{

        try{

            val servicioRegistroAsistencia= ClienteApiRest.getRetrofitInstance().create(TallerServicio::class.java)
            val call =servicioRegistroAsistencia.obtenerTalleresSinInformeSoloCAI( "Bearer $token")
            val response = call.execute()

            if(response != null){

                if(response.code() == 200){
                    val listaTalleresCai= response.body()
                    return listaTalleresCai
                }
                else{
                    return null
                }
            }
            else{
                return null
            }
        }catch (e:Exception){
            return null
        }

    }

    private fun obtenerTalleresPorTipo(listaTaller: List<Taller>, tipoTaller: String): List<Taller>?{

        val listaTallerPorTipo =ArrayList<Taller>()
        for(t in listaTaller){

             if(t.tipo.equals(tipoTaller)){
                 listaTallerPorTipo.add(t)
            }

        }
        return listaTallerPorTipo
    }

    private fun asignarListaTalleresRecyclerView(listaTaller: List<Taller>, rootView: View){

        listaTalleres=listaTaller
        val adaptador = ListaTalleresAdaptador(listaTaller, this@TalleresFragment)
        val recyclerViewTaller =rootView.findViewById (R.id.rv_taller) as RecyclerView
        recyclerViewTaller.adapter=adaptador
        recyclerViewTaller.layoutManager=LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)

    }
}
package ec.edu.epn.snai.Controlador.Fragment

import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ec.edu.epn.snai.Controlador.Adaptador.TallerAdaptador
import ec.edu.epn.snai.R
import ec.edu.epn.snai.Servicios.ClienteApiRest
import ec.edu.epn.snai.Servicios.TallerServicio
import ec.edu.epn.snai.Controlador.Activity.VerInfoTallerActivity
import ec.edu.epn.snai.Modelo.*
import ec.edu.epn.snai.Utilidades.Constantes
import kotlinx.android.synthetic.main.fragment_talleres.view.*

class TalleresFragment: Fragment(), TallerAdaptador.TallerOnItemClickListener{

    private var listaTalleres: List<Taller>?=null
    private lateinit var token:String
    private var tipoTaller:String?=null
    private lateinit var usuario: Usuario

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if(arguments!=null){
            token=arguments?.getSerializable("token") as String
            usuario=arguments?.getSerializable("usuario") as Usuario
            tipoTaller=arguments?.getSerializable("tipoTaller") as String?

            if(tipoTaller == null){

                val rol=usuario.idRolUsuarioCentro.idRol.rol

                if( rol.equals(Constantes.ROL_ADMINISTRADOR) || rol.equals(Constantes.ROL_SUBDIRECTOR) || rol.equals(Constantes.ROL_LIDER_UZDI) || rol.equals(
                        Constantes.ROL_COORDINADOR_CAI)  || rol.contains("DIRECTOR") || rol.contains("PSICOLOGO") ){
                    tipoTaller=Constantes.TIPO_TALLER_PSICOLOGIA
                }
                else if(rol.contains("JURIDICO")){
                    tipoTaller=Constantes.TIPO_TALLER_JURIDICO
                }
                else if(rol.contains("INSPECTOR EDUCADOR")){
                    tipoTaller=Constantes.TIPO_TALLER_INSPECTOR_EDUCADOR
                }

            }
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

        val intent = Intent(context, VerInfoTallerActivity::class.java)
        intent.putExtra("taller_seleccionado", listaTalleres?.get(posicion))
        intent.putExtra("token", token)
        intent.putExtra("usuario", usuario)
        startActivity(intent)
    }


    /**************** Obtener Lista de Talleres¨***********************/
    private fun asignarObtenerListaTalleres(usuario: Usuario, view: View, tipoTaller: String){

        asynTaskObtenerListadoTalleres(usuario, view,tipoTaller)
    }

    private fun asynTaskObtenerListadoTalleres(usuario: Usuario,rootView: View, tipoTaller: String){


        val task = object : AsyncTask<Unit, Unit, List<Taller>>() {

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
    }

    private fun servicioObtenerTalleresSinInforme(): List<Taller>?{

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
    }

    private fun servicioObtenerTalleresSinInformePorUsuario(usuario: Usuario): List<Taller>?{

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
    }

    private fun servicioObtenerTalleresSinInformeSoloUZDI(): List<Taller>?{

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
    }

    private fun servicioObtenerTalleresSinInformeSoloCAI(): List<Taller>?{

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
        val adaptador = TallerAdaptador(listaTaller, this@TalleresFragment)
        val recyclerViewTaller =rootView.findViewById (R.id.rv_taller) as RecyclerView
        recyclerViewTaller.adapter=adaptador
        recyclerViewTaller.layoutManager=LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)

    }
}
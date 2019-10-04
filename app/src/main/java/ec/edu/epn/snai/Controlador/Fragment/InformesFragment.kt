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
import ec.edu.epn.snai.Controlador.Activity.InformeTabbedActivity
import ec.edu.epn.snai.Controlador.Adaptador.ListaInformesAdaptador
import ec.edu.epn.snai.Modelo.Informe
import ec.edu.epn.snai.Modelo.Usuario
import ec.edu.epn.snai.R
import ec.edu.epn.snai.Servicios.ClienteApiRest
import ec.edu.epn.snai.Servicios.InformeServicio
import ec.edu.epn.snai.Utilidades.Constantes
import kotlinx.android.synthetic.main.fragment_informes.view.*

class InformesFragment: Fragment(), ListaInformesAdaptador.InformeOnItemClickListener{

    private var listaInformes : List<Informe>?=null
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

                if( rol.equals(Constantes.ROL_ADMINISTRADOR) || rol.equals(Constantes.ROL_SUBDIRECTOR) || rol.equals(Constantes.ROL_LIDER_UZDI) || rol.equals(Constantes.ROL_COORDINADOR_CAI)  || rol.contains("DIRECTOR") || rol.contains("PSICOLOGO") ){
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
        val rootView=inflater.inflate(R.layout.fragment_informes,container,false)

        if(tipoTaller != null){
            asignarObtenerListaInformes(this.usuario, rootView, this.tipoTaller!!)
        }
        return rootView
    }

    override fun OnItemClick(posicion: Int) {
        val intent = Intent(context,InformeTabbedActivity::class.java)
        intent.putExtra("informeSeleccionado",listaInformes?.get(posicion))
        intent.putExtra("token",token)
        intent.putExtra("usuario", usuario)
        startActivity(intent)
    }

    /**************** Obtener Lista de Talleres¨***********************/
    private fun asignarObtenerListaInformes(usuario: Usuario, view: View, tipoTaller: String){
        asynTaskObtenerListadoInformes(usuario, view,tipoTaller)
    }

    private fun asynTaskObtenerListadoInformes(usuario: Usuario,rootView: View, tipoTaller: String){


        val task = object : AsyncTask<Unit, Unit, List<Informe>>() {

            override fun doInBackground(vararg p0: Unit?): List<Informe>? {

                val rol=usuario.getIdRolUsuarioCentro().getIdRol().getRol()

                if(rol.equals(Constantes.ROL_ADMINISTRADOR)|| rol.equals(Constantes.ROL_SUBDIRECTOR)){

                    return servicioObtenerTodosLosInformes()

                }else if( rol.equals(Constantes.ROL_LIDER_UZDI) || rol.equals(Constantes.ROL_DIRECTOR_UZDI)){

                    return servicioObtenerInformesSoloUzdi()

                }else if(rol.equals(Constantes.ROL_COORDINADOR_CAI) || rol.equals(Constantes.ROL_DIRECTOR_CAI)){

                    return servicioObtenerInformesSoloCai()

                }else {
                    return servicioObtenerInformesPorUsuario(usuario)
                }
            }

            override fun onPostExecute(result: List<Informe>?) {
                super.onPostExecute(result)
                if(result != null){

                    val listaInformesAux=obtenerInformesPorTipo(result,tipoTaller)
                    if(listaInformesAux != null){

                        if(listaInformesAux.size >0 ){

                            rootView.txtSinInformes.visibility=View.INVISIBLE
                            asignarListaInformesRecyclerView(listaInformesAux, rootView)
                        }
                        else{
                            rootView.txtSinInformes.visibility=View.VISIBLE
                        }
                    }
                    else{
                        rootView.txtSinInformes.visibility=View.VISIBLE
                    }

                }
                else{
                    rootView.txtSinInformes.visibility=View.VISIBLE
                }

            }
        }
        task.execute()
    }

    private fun servicioObtenerTodosLosInformes(): List<Informe>?{

        val servicioRegistroAsistencia= ClienteApiRest.getRetrofitInstance().create(InformeServicio::class.java)
        val call =servicioRegistroAsistencia.obtenerTodosLosInformes( "Bearer $token")
        val response = call.execute()

        if(response != null){

            if(response.code() == 200){
                val listaInformes= response.body()
                return listaInformes
            }
            else{
                return null
            }
        }
        else{
            return null
        }
    }

    private fun servicioObtenerInformesPorUsuario(usuario: Usuario): List<Informe>?{

        val servicioRegistroAsistencia= ClienteApiRest.getRetrofitInstance().create(InformeServicio::class.java)
        val call =servicioRegistroAsistencia.obtenerInformesPorUsuario( usuario,"Bearer $token")
        val response = call.execute()

        if(response != null){

            if(response.code() == 200){
                val listaInformesUsuario= response.body()

                if(listaInformesUsuario != null){

                    return listaInformesUsuario
                }
                else{
                    return null
                }

            }
            else{
                return null
            }
        }
        else{
            return null
        }
    }

    private fun servicioObtenerInformesSoloUzdi(): List<Informe>?{

        val servicioRegistroAsistencia= ClienteApiRest.getRetrofitInstance().create(InformeServicio::class.java)
        val call =servicioRegistroAsistencia.obtenerInformesSoloUzdi("Bearer $token")
        val response = call.execute()

        if(response != null){

            if(response.code() == 200){
                val listaInformesUzdi= response.body()

                if(listaInformesUzdi != null){

                    return listaInformesUzdi
                }
                else{
                    return null
                }

            }
            else{
                return null
            }
        }
        else{
            return null
        }
    }

    private fun servicioObtenerInformesSoloCai(): List<Informe>?{

        val servicioRegistroAsistencia= ClienteApiRest.getRetrofitInstance().create(InformeServicio::class.java)
        val call =servicioRegistroAsistencia.obtenerInformesSoloCai( "Bearer $token")
        val response = call.execute()

        if(response != null){

            if(response.code() == 200){
                val listaInformesCai= response.body()

                if(listaInformesCai != null){

                    return listaInformesCai
                }
                else{
                    return null
                }

            }
            else{
                return null
            }
        }
        else{
            return null
        }
    }

    private fun obtenerInformesPorTipo(listaInformes: List<Informe>, tipoTaller: String): List<Informe>?{

        val listaInformesPorTipo =ArrayList<Informe>()
        for(i in listaInformes){

            if(i.idTaller.tipo.equals(tipoTaller)){
                listaInformesPorTipo.add(i)
            }

        }
        return listaInformesPorTipo
    }

    private fun asignarListaInformesRecyclerView(listaInformesPorTipo: List<Informe>, rootView: View){

        listaInformes=listaInformesPorTipo
        val adaptador=
            ListaInformesAdaptador(listaInformesPorTipo, this@InformesFragment)
        val recyclerViewInforme=rootView.findViewById(R.id.rv_informe) as RecyclerView
        recyclerViewInforme.adapter=adaptador
        recyclerViewInforme.layoutManager=LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
    }

}
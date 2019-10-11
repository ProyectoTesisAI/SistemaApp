package ec.edu.epn.snai.Controlador.Fragment

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
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
import ec.edu.epn.snai.Controlador.Adaptador.UsuariosAdaptador
import ec.edu.epn.snai.Modelo.Usuario
import ec.edu.epn.snai.R
import ec.edu.epn.snai.Servicios.ClienteApiRest
import ec.edu.epn.snai.Servicios.UsuarioServicio
import kotlinx.android.synthetic.main.fragment_usuarios.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UsuariosActivosFragment : Fragment(), UsuariosAdaptador.UsuarioOnItemClickListener{

    private lateinit var token:String
    private lateinit var usuario: Usuario
    private var listaUsuariosActivos: ArrayList<Usuario> =ArrayList<Usuario>()
    private lateinit var rootView:View
    private lateinit var adaptador : UsuariosAdaptador

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if(arguments!=null){
            usuario=arguments?.getSerializable("usuario") as Usuario
            this.token=usuario.token

        }


    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view= inflater.inflate(R.layout.fragment_usuarios,container,false)

        rootView=view

        asignarObtenerListaUsuarios()
        return view
    }

    override fun OnItemClick(posicion: Int) {

        val builder = AlertDialog.Builder(context)

        builder.setTitle("Desactivar Usuario")
        builder.setMessage("¿Está seguro de desactivar el Usuario?")
        builder.setPositiveButton("OK",
            DialogInterface.OnClickListener { dialog, which ->
                val usuario=listaUsuariosActivos.get(posicion)
                usuario.activo=false
                desactivarUsuario( usuario)

            })
        builder.setNegativeButton("CANCELAR",
            DialogInterface.OnClickListener { dialog, which ->
            })
        builder.show()
    }


    /**************** Obtener Lista Usuarios Activos¨***********************/
    private fun asignarObtenerListaUsuarios(){

        asynTaskObtenerListadoTalleres()
    }

    private fun asynTaskObtenerListadoTalleres(){


        try{

            val task = @SuppressLint("StaticFieldLeak")
            object : AsyncTask<Unit, Unit, List<Usuario>>() {

                override fun doInBackground(vararg p0: Unit?): List<Usuario>? {

                        return servicioObtenerUsuariosActivos()

                }

                override fun onPostExecute(result: List<Usuario>?) {

                    super.onPostExecute(result)
                    if(result != null){
                        rootView.txtUsuarios.visibility=View.INVISIBLE
                        asignarListaUsuariosRecyclerView(result)
                    }
                    else{
                        rootView.txtUsuarios.visibility=View.VISIBLE
                    }

                }
            }
            task.execute()

        }catch (e:Exception){
            Toast.makeText(context, "Ha ocurrido un error al obtener la Lista de Usuarios Activos", Toast.LENGTH_SHORT).show()
        }


    }

    private fun servicioObtenerUsuariosActivos(): List<Usuario>?{

        try{
            val servicioRegistroAsistencia= ClienteApiRest.getRetrofitInstance().create(UsuarioServicio::class.java)
            val call =servicioRegistroAsistencia.obtenerUsuariosActivos( "Bearer $token")
            val response = call.execute()

            if(response != null){

                if(response.code() == 200){
                    val listaUsuarios= response.body()
                    return listaUsuarios
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


    /*****************************************************/
    private fun desactivarUsuario(usuario: Usuario){

        try{
            val servicio = ClienteApiRest.getRetrofitInstance().create(UsuarioServicio::class.java)
            val call = servicio.desactivarUsuario(usuario, "Bearer " + token)

            call.enqueue(object : Callback<Usuario>{

                override fun onFailure(call: Call<Usuario>, t: Throwable) {
                    Toast.makeText(context, "Ha ocurrido un error al descativar el usuario", Toast.LENGTH_SHORT).show()
                }

                override fun onResponse(call: Call<Usuario>, response: Response<Usuario>) {

                    if(response != null){

                        if(response.code()==200){
                            val usuarioDesactivado=response.body()

                            if(usuarioDesactivado != null){

                                listaUsuariosActivos.remove(usuario)

                                adaptador.notifyDataSetChanged()

                                Toast.makeText(context, "Se ha descativado el usuario correctamente", Toast.LENGTH_SHORT).show()

                            }


                        }
                        else{
                            Toast.makeText(context, "Ha ocurrido un error al descativar el usuario", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            })

        }catch (e:Exception){
            Toast.makeText(context, "Ha ocurrido un error al obtener el desactivar el usuario", Toast.LENGTH_SHORT).show()
        }

    }


    private fun asignarListaUsuariosRecyclerView(listaUsuariosActivosAux: List<Usuario>){

        if(listaUsuariosActivosAux.size > 0){

            listaUsuariosActivos= listaUsuariosActivosAux as ArrayList<Usuario>
            adaptador = UsuariosAdaptador(listaUsuariosActivosAux, this@UsuariosActivosFragment)
            val recyclerViewTaller =rootView.findViewById (R.id.rv_usuarios) as RecyclerView
            recyclerViewTaller.adapter=adaptador
            recyclerViewTaller.layoutManager=LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
        }


    }
}
package ec.edu.epn.snai.Controlador.Activity

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import cc.duduhuo.util.digest.Digest
import ec.edu.epn.snai.Modelo.*
import java.util.ArrayList
import ec.edu.epn.snai.R
import ec.edu.epn.snai.Utilidades.Constantes
import ec.edu.epn.snai.Utilidades.GeneradorContraseña
import kotlinx.android.synthetic.main.activity_agregar_usuario.*
import android.widget.TextView
import android.content.ClipboardManager
import android.content.Context
import android.graphics.Color
import android.util.TypedValue
import ec.edu.epn.snai.Servicios.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class CrearUsuarioActivity: AppCompatActivity() {

    private var listaUZDI: List<UDI>?=null
    private var listaCAI: List<CAI>?= null
    private lateinit var token:String
    private lateinit var usuario: Usuario
    private lateinit var listaRoles: Array<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agregar_usuario)

        usuario = intent.getSerializableExtra("usuario") as Usuario
        token=usuario.token

        listaRoles=resources.getStringArray(R.array.rol)

        generarContrasena()

        spCentroUsuario.visibility=View.INVISIBLE
        txtTipoCentroUsuario.visibility=View.INVISIBLE

        val spRolAux = spRolUsuario

        asignarRolesSpinner(spRolAux)

        spRolAux.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, posicion: Int, p3: Long) {

                val tipoCentro=asignarCentroEquipoTecnico( listaRoles.get(posicion))

                if(tipoCentro != null){


                    spCentroUsuario.visibility=View.VISIBLE
                    txtTipoCentroUsuario.visibility=View.VISIBLE

                    if(tipoCentro.equals("UZDI")){

                        asynTaskObtenerListadoUzdi()
                        asignarListaUzdiSpinner()

                        txtTipoCentroUsuario.text="UZDI"

                    }else if(tipoCentro.equals("CAI")){
                        asynTaskObtenerListadoCai()
                        asignarListaCaiSpinner()

                        txtTipoCentroUsuario.text="CAI"
                    }
                }
                else{

                    spCentroUsuario.visibility=View.INVISIBLE
                    txtTipoCentroUsuario.visibility=View.INVISIBLE
                }
            }
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.menu_gestion, menu)

        menu?.findItem(R.id.menu_editar)?.isVisible = false
        menu?.findItem(R.id.menu_eliminar)?.isVisible=false
        menu?.findItem(R.id.menu_guardar)?.isVisible=true

        return true
    }

    @SuppressLint("SetTextI18n")
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        when(item?.itemId){
            R.id.menu_guardar->{

                val usuarioAux=obtenerVariablesUsuario()

                if(usuarioAux != null){

                    if(usuarioAux.nombres.isNullOrBlank() || usuarioAux.apellidos.isNullOrBlank() || usuarioAux.cedula.isNullOrBlank() || usuarioAux.usuario.isNullOrBlank()){
                        Toast.makeText(this@CrearUsuarioActivity, "Nombres, Apellidos, Cédula/Documento y Usuario son campos obligatorios, ingrese un valor", Toast.LENGTH_SHORT).show()
                    }
                    else{

                        val txtMensajeCrearUsuario=mensajeDialogoCrearUsuario(usuarioAux)

                        val builder = AlertDialog.Builder(this@CrearUsuarioActivity)

                        builder.setTitle("Crear Usuario")
                        builder.setView(txtMensajeCrearUsuario)
                        builder.setPositiveButton("OK",
                            DialogInterface.OnClickListener { dialog, which ->
                                guardarUsuario(usuarioAux)

                            })
                        builder.setNegativeButton("CANCELAR",
                            DialogInterface.OnClickListener { dialog, which ->
                            })
                        builder.show()

                    }
                }


            }
            else->{

            }
        }
        return true
    }

    private fun mensajeDialogoCrearUsuario(usuarioAux: Usuario): TextView{

        val txtCrearUsuario = TextView(this@CrearUsuarioActivity)
        txtCrearUsuario.text = "Este es su usuario y contraseña creada. No se podrá ver luego asi que copiela y guardela!!!!!\n\n" +
                "Usuario: "+usuarioAux.usuario+"\n" + "Contraseña: "+txtContrasenaUsuario.text.toString()
        txtCrearUsuario.setPadding(30, 25, 30,10)
        txtCrearUsuario.setTextSize(TypedValue.COMPLEX_UNIT_SP,16f)
        txtCrearUsuario.setTextColor(Color.BLACK)

        txtCrearUsuario.setOnLongClickListener(object : View.OnLongClickListener {

            override fun onLongClick(v: View): Boolean {

                // Copiar al portapapeles
                val manager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                manager.text = "Usuario: "+usuarioAux.usuario+"\n" + "Contraseña: "+txtContrasenaUsuario.text.toString()
                Toast.makeText(v.getContext(), "Se ha copiado el usuario y contraseña", Toast.LENGTH_SHORT).show()
                return true
            }
        })
        return txtCrearUsuario
    }

    private fun generarContrasena(){

        val generador=GeneradorContraseña(10)

        txtContrasenaUsuario.text=generador.generarPassword()

    }

    private fun asignarRolesSpinner(spinnerRol: Spinner){
        //Adaptador del Tipo de Centro para el Spinner
        val adapterRol= ArrayAdapter<String>(this@CrearUsuarioActivity ,android.R.layout.simple_expandable_list_item_1,resources.getStringArray(R.array.rol))
        adapterRol.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerRol.adapter=adapterRol
    }

    private fun asignarCentroEquipoTecnico(rol: String): String?{

        var tipoCentro: String?=null
        when(rol){
            Constantes.ROL_PSICOLOGO_UZDI->{
                tipoCentro="UZDI"
            }
            Constantes.ROL_TRABAJADOR_SOCIAL_UZDI->{
                tipoCentro="UZDI"
            }
            Constantes.ROL_JURIDICO_UZDI->{
                tipoCentro="UZDI"
            }
            Constantes.ROL_PSICOLOGO_CAI->{
                tipoCentro="CAI"
            }
            Constantes.ROL_TRABAJADOR_SOCIAL_CAI->{
                tipoCentro="CAI"
            }
            Constantes.ROL_JURIDICO_CAI->{
                tipoCentro="CAI"
            }
            Constantes.ROL_INSPECTOR_EDUCADOR->{
                tipoCentro="CAI"
            }
        }
        return tipoCentro
    }

    private fun asignarListaUzdiSpinner(){

        if(listaUZDI?.size!! > 0){

            val listaUZDIAux : MutableList<String> = ArrayList<String>()

            for (u in listaUZDI!!){
                listaUZDIAux.add(u.udi)
            }

            val adapterTipoCentro= ArrayAdapter<String>(this@CrearUsuarioActivity, android.R.layout.simple_expandable_list_item_1,listaUZDIAux)
            adapterTipoCentro.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spCentroUsuario.adapter=adapterTipoCentro
        }

    }

    private fun asignarListaCaiSpinner(){

        if(listaCAI?.size!! > 0){

            val listaCAIAux:MutableList<String> = ArrayList<String>()

            for (c in listaCAI!!){
                listaCAIAux.add(c.cai)
            }

            val adapterTipoCentro= ArrayAdapter<String>(this@CrearUsuarioActivity, android.R.layout.simple_expandable_list_item_1,listaCAIAux)
            adapterTipoCentro.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spCentroUsuario.adapter=adapterTipoCentro
        }


    }


    /**********************Obtener Variables Usuario****************************/
    private fun obtenerVariablesUsuario(): Usuario?{

        val usuario=Usuario()

        usuario.nombres=etNombresUsuario.text.toString()
        usuario.apellidos=etApellidosUsuario.text.toString()
        usuario.cedula=etCedulaUsuario.text.toString()
        usuario.telefono=etTelefonoUsuario.text.toString()
        usuario.usuario=etUsuario.text.toString()
        usuario.activo=true

        val contraseña=txtContrasenaUsuario.text.toString()
        usuario.contraseña=cifrarPassword(contraseña)


        val rolCentroUsuarioAux=rescatarRolCentroUsuario()

        if(rolCentroUsuarioAux != null){
            usuario.idRolUsuarioCentro=rolCentroUsuarioAux
            return usuario
        }
        else{
            return null
        }

    }

    private fun cifrarPassword(password : String): String{
        return Digest.sha256Hex(password,false)
    }

    private fun obtenerVarialesRolCentroUsuario(): RolCentroUsuario{

        val rolCentroUsuario=RolCentroUsuario()
        val rol=Rol()

        val rolAux=spRolUsuario.selectedItem.toString()
        rol.rol= rolAux

        rolCentroUsuario.idRol=rol

        if(rolAux.equals(Constantes.ROL_PSICOLOGO_UZDI) || rolAux.equals(Constantes.ROL_TRABAJADOR_SOCIAL_UZDI) || rolAux.equals(Constantes.ROL_JURIDICO_UZDI)){
            val posicionItemCentro= spCentroUsuario.selectedItemPosition

            if(listaUZDI != null){
                val uzdi=listaUZDI?.get(posicionItemCentro)
                rolCentroUsuario.idUdi=uzdi
            }

        }
        else if(rolAux.equals(Constantes.ROL_PSICOLOGO_CAI) || rolAux.equals(Constantes.ROL_TRABAJADOR_SOCIAL_CAI) || rolAux.equals(Constantes.ROL_JURIDICO_CAI) || rolAux.equals(Constantes.ROL_INSPECTOR_EDUCADOR)){
            val posicionItemCentro= spCentroUsuario.selectedItemPosition

            if(listaCAI != null){
                val cai=listaCAI?.get(posicionItemCentro)
                rolCentroUsuario.idCai=cai
            }
        }

        return rolCentroUsuario
    }

    private fun rescatarRolCentroUsuario(): RolCentroUsuario?{

        val rolCentroUsuarioAux=obtenerVarialesRolCentroUsuario()

        return asynTaskObtenerRolCentroUsuario(rolCentroUsuarioAux)
    }


    /*********************Obtener Lista UZDI************************/
    private fun asynTaskObtenerListadoUzdi(){

        try{

            val miclase = @SuppressLint("StaticFieldLeak")
            object : AsyncTask<Unit, Unit, List<UDI>?>(){

                override fun doInBackground(vararg p0: Unit?): List<UDI>? {
                    val listadoUzdi=obtenerListadoUZDI()
                    if(listadoUzdi != null){
                        return listadoUzdi
                    }else{
                        return null
                    }
                }

            }
            listaUZDI= miclase.execute().get()
        }catch (e:Exception){
            Toast.makeText(this@CrearUsuarioActivity, "Ha ocurrido un error al obtener la Lista de UZDI", Toast.LENGTH_SHORT).show()
        }

    }

    private fun obtenerListadoUZDI(): List<UDI>?{

        try{
            val servicioUzdi= ClienteApiRest.getRetrofitInstance().create(UzdiServicio::class.java)
            val call =servicioUzdi.obtenerListaUZDI("Bearer $token")
            val listadoUzdi = call.execute().body()
            return  listadoUzdi!!

        }catch (e:Exception){
            return null
        }


    }


    /*********************Obtener Lista CAI*********************************/
    private fun asynTaskObtenerListadoCai(){

        try{
            val task = @SuppressLint("StaticFieldLeak")
            object : AsyncTask<Unit, Unit, List<CAI>?>(){


                override fun doInBackground(vararg p0: Unit?): List<CAI>? {
                    val listadoCai=servicioObtenerListadoCAI()

                    if(listadoCai != null){
                        return listadoCai
                    }else{
                        return null
                    }
                }
            }
            listaCAI= task.execute().get()

        }catch (e:Exception){
            Toast.makeText(this@CrearUsuarioActivity, "Ha ocurrido un error al obtener la lista de CAI", Toast.LENGTH_SHORT).show()
        }

    }

    private fun servicioObtenerListadoCAI(): List<CAI>?{

        try{

            val servicioCai= ClienteApiRest.getRetrofitInstance().create(CaiServicio::class.java)
            val call =servicioCai.obtenerListaCAI("Bearer $token")
            val listadoCai = call.execute().body()
            return  listadoCai!!
        }catch (e:Exception){
            return null
        }


    }


    /*********************Obtener Rol Centro Usuario*************************/
    private fun asynTaskObtenerRolCentroUsuario(rolCentroUsuario: RolCentroUsuario): RolCentroUsuario?{

        try{

            val miclase = @SuppressLint("StaticFieldLeak")
            object : AsyncTask<Unit, Unit, RolCentroUsuario>(){

                override fun doInBackground(vararg p0: Unit?): RolCentroUsuario? {

                    val rolAux=rolCentroUsuario.idRol.rol

                    if(rolAux.equals(Constantes.ROL_PSICOLOGO_UZDI) || rolAux.equals(Constantes.ROL_TRABAJADOR_SOCIAL_UZDI) || rolAux.equals(Constantes.ROL_JURIDICO_UZDI)){
                       return servicioObtenerRolUzdi(rolCentroUsuario)!!

                    }
                    else if(rolAux.equals(Constantes.ROL_PSICOLOGO_CAI) || rolAux.equals(Constantes.ROL_TRABAJADOR_SOCIAL_CAI) || rolAux.equals(Constantes.ROL_JURIDICO_CAI) || rolAux.equals(Constantes.ROL_INSPECTOR_EDUCADOR)){
                        return servicioObtenerRolACai(rolCentroUsuario)!!
                    }
                    else{
                        return servicioObtenerRolAdministrativo(rolCentroUsuario)!!
                    }
                }

            }
            val rolRescatado=miclase.execute().get()
            if(rolRescatado != null){
                return  rolRescatado
            }
            else{
                return null
            }
        }catch (e:Exception){
            Toast.makeText(this@CrearUsuarioActivity, "Ha ocurrido un error al guardar el Usuario", Toast.LENGTH_SHORT).show()
            return null
        }

    }

    private fun servicioObtenerRolAdministrativo(rolCentroUsuario: RolCentroUsuario): RolCentroUsuario?{

        try{

            val servicioCai= ClienteApiRest.getRetrofitInstance().create(RolCentroUsuarioServicio::class.java)
            val call =servicioCai.obtenerRolAdministrativo(rolCentroUsuario,"Bearer $token")
            val rolCentroUsuarioAux = call.execute().body()
            return  rolCentroUsuarioAux
        }catch (e:Exception){
            return null
        }
    }

    private fun servicioObtenerRolUzdi(rolCentroUsuario: RolCentroUsuario): RolCentroUsuario?{

        try{

            val servicioCai= ClienteApiRest.getRetrofitInstance().create(RolCentroUsuarioServicio::class.java)
            val call =servicioCai.obtenerRolSoloUDI(rolCentroUsuario,"Bearer $token")
            val rolCentroUsuarioAux = call.execute().body()
            return  rolCentroUsuarioAux
        }catch (e:Exception){
            return null
        }
    }

    private fun servicioObtenerRolACai(rolCentroUsuario: RolCentroUsuario): RolCentroUsuario?{

        try{

            val servicioCai= ClienteApiRest.getRetrofitInstance().create(RolCentroUsuarioServicio::class.java)
            val call =servicioCai.obtenerRolSoloCAI(rolCentroUsuario,"Bearer $token")
            val rolCentroUsuarioAux = call.execute().body()
            return  rolCentroUsuarioAux
        }catch (e:Exception){
            return null
        }
    }


    /*********************Guardar Usuario*************************/
    private fun guardarUsuario(usuarioAux: Usuario){

        try{
            val servicio = ClienteApiRest.getRetrofitInstance().create(UsuarioServicio::class.java)
            val call = servicio.crearUsuario(usuarioAux, "Bearer " + token)

            call.enqueue(object : Callback<Usuario>{

                override fun onFailure(call: Call<Usuario>, t: Throwable) {
                    Toast.makeText(this@CrearUsuarioActivity,"Ha ocurrido un error al crear el usuario", Toast.LENGTH_SHORT).show()
                }

                override fun onResponse(call: Call<Usuario>, response: Response<Usuario>) {

                    if(response != null){

                        if(response.code()==200){
                            val usuarioRescatado=response.body()

                            if(usuarioRescatado != null){
                                Toast.makeText(this@CrearUsuarioActivity,"Se ha creado correctamente el usuario", Toast.LENGTH_SHORT).show()
                                finish()
                            }


                        }
                        else{
                            Toast.makeText(this@CrearUsuarioActivity,"Ha ocurrido un error al crear el usuario", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            })

        }catch (e:Exception){
            Toast.makeText(this@CrearUsuarioActivity,"Ha ocurrido un error al crear el usuario", Toast.LENGTH_SHORT).show()
        }

    }


}
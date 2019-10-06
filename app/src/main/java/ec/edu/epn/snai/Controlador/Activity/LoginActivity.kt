package ec.edu.epn.snai.Controlador.Activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import android.widget.Toast
import cc.duduhuo.util.digest.Digest
import ec.edu.epn.snai.Modelo.Usuario
import ec.edu.epn.snai.R
import ec.edu.epn.snai.Servicios.ClienteApiRest
import ec.edu.epn.snai.Servicios.UsuarioServicio
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : AppCompatActivity(){

    private var user = Usuario()
    private lateinit var btnAcceder : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        btnAcceder=findViewById(R.id.btn_acceder_login)

        btnAcceder.setOnClickListener {

            val txtUsuario = user_login.text.toString().trim()
            val txtPassword = password_login.text.toString().trim()

            if(txtUsuario.isEmpty()){
                user_login.error = "Usuario requerido"
                user_login.requestFocus()
                return@setOnClickListener
            }

            if(txtPassword.isEmpty()){
                password_login.error = "Constraseña requerida"
                password_login.requestFocus()
                return@setOnClickListener
            }

            user.usuario = txtUsuario//"oscar_espana"//txtUsuario //tatiana.mayorga02 /// sandra.correa02
            user.contraseña = cifrarPassword(txtPassword) //oscar_snai_2019//txtPassword //SuJ[w[9Xac /// cKUwN7m\$vT

            asynTaskIniciarSesion()
        }
    }

    private fun cifrarPassword(password : String): String{
        return Digest.sha256Hex(password,false)
    }

    private fun asynTaskIniciarSesion(){


        val miclase = @SuppressLint("StaticFieldLeak")
        object : AsyncTask<Unit, Unit, Usuario>() {

            override fun onPreExecute() {
                pbLogin.visibility=View.VISIBLE
            }

            override fun doInBackground(vararg p0: Unit?): Usuario?{
                return servicioIniciarSesion()
            }

            override fun onPostExecute(usuario: Usuario?) {
                super.onPostExecute(usuario)

                if(usuario!= null){

                    val bienvenidaUsuario: String="Bienvenido ${usuario.nombres} ${usuario.apellidos}"
                    val intent = Intent(this@LoginActivity, MainActivity::class.java)
                    intent.putExtra("usuario", usuario)
                    startActivity(intent)
                    //finalizo el activity LoginActivity, para evitar volver a la pantalla de Login
                    finish()

                    Toast.makeText(applicationContext, bienvenidaUsuario,Toast.LENGTH_LONG).show()
                }
                else{
                    Toast.makeText(applicationContext, "Ha courrido un error al iniciar Sesión ", Toast.LENGTH_LONG).show()
                }
                pbLogin.visibility=View.INVISIBLE
            }

        }
        miclase.execute()
    }

    private fun servicioIniciarSesion(): Usuario?{

        try{
            val servicio_login = ClienteApiRest.getRetrofitInstance().create(UsuarioServicio::class.java)
            val call = servicio_login.login(user)
            val response =call.execute()
            if(response.code() == 200){

                val usuario=response.body()
                return usuario
            }
            else{
                return null
            }
        }catch (e: Exception){
            return null
        }

    }
}
package ec.edu.epn.snai.Controlador.Activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.Toast
import cc.duduhuo.util.digest.Digest
import ec.edu.epn.snai.Modelo.Usuario
import ec.edu.epn.snai.R
import ec.edu.epn.snai.Servicios.ClienteApiRest
import ec.edu.epn.snai.Servicios.UsuarioServicio
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoginActivity : AppCompatActivity(){
    private var user = Usuario()
    private lateinit var btnAcceder : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        btnAcceder=findViewById(R.id.btn_acceder_login)
        btnAcceder.setOnClickListener {
            val txtUsuario = user_login.text.toString().trim()
            System.out.println(txtUsuario)
            val txtPassword = password_login.text.toString().trim()
            System.out.println(txtPassword)

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

            user?.usuario = txtUsuario
            user?.contraseña = cifrarPassword(txtPassword)
            System.out.println("usuario"+user?.usuario)
            System.out.println("pass"+user?.contraseña)

            val servicio_login = ClienteApiRest.getRetrofitInstance().create(UsuarioServicio::class.java)
            val call = servicio_login.login(user)

            call.enqueue(object: Callback<Usuario> {
                override fun onFailure(call: Call<Usuario>, t: Throwable) {
                    Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
                    call.cancel()
                }

                override fun onResponse(call: Call<Usuario>, response: Response<Usuario>) {
                    if(response.isSuccessful){
                        Toast.makeText(applicationContext, response.body()?.toString(), Toast.LENGTH_LONG).show()
                        val intent = Intent(this@LoginActivity, MainActivity::class.java)
                        startActivity(intent)
                    }
                }

            })
        }
    }

    fun cifrarPassword(password : String): String{
        return Digest.sha256Hex(password,false)
    }
}
package ec.edu.epn.snai.Controlador.Activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.Toast
import cc.duduhuo.util.digest.Digest
import ec.edu.epn.snai.Controlador.Fragment.TalleresFragment
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

            user?.usuario = "oscar_espana"//txtUsuario
            user?.contraseña = cifrarPassword("oscar_snai_2019") //txtPassword

            val servicio_login = ClienteApiRest.getRetrofitInstance().create(UsuarioServicio::class.java)
            val call = servicio_login.login(user)

            call.enqueue(object: Callback<Usuario> {
                override fun onFailure(call: Call<Usuario>, t: Throwable) {
                    Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
                    call.cancel()
                }

                override fun onResponse(call: Call<Usuario>, response: Response<Usuario>) {
                    if(response.isSuccessful){


                        var usuario: Usuario?=response.body()

                        if(usuario!= null){
                            val intent = Intent(this@LoginActivity, MainActivity::class.java)
                            intent.putExtra("usuario", usuario)
                            startActivity(intent)
                        }
                        else{
                            Toast.makeText(applicationContext, "Ha ingresado un Usuario o Contraseña Incorrectas", Toast.LENGTH_LONG).show()
                        }


                    }
                }

            })
        }
    }

    fun cifrarPassword(password : String): String{
        return Digest.sha256Hex(password,false)
    }
}
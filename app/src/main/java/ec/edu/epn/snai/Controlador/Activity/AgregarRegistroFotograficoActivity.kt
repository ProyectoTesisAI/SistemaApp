package ec.edu.epn.snai.Controlador.Activity

import android.app.Activity
import android.content.ContentResolver
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.AsyncTask
import android.os.Bundle
import android.provider.MediaStore
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Base64
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import ec.edu.epn.snai.Controlador.Adaptador.IngresarRegistroFotograficoAdaptador
import ec.edu.epn.snai.Modelo.*
import java.io.ByteArrayOutputStream
import ec.edu.epn.snai.Modelo.AsistenciaAdolescente
import ec.edu.epn.snai.R
import ec.edu.epn.snai.Servicios.*


class AgregarRegistroFotograficoActivity : AppCompatActivity() {

    private var listaFotografias: MutableList<RegistroFotografico>?=ArrayList<RegistroFotografico>()
    private var listaFotografiasRescatado: MutableList<RegistroFotografico>? = null
    private lateinit var tallerSeleccionado: Taller
    private lateinit var informeNuevo: Informe
    private lateinit var token: String
    private var listaAdolescentesInfractores: List<AsistenciaAdolescente>? = null
    private var listaActividadesTaller: List<ItemTaller>? = null

    private lateinit var btnAgregarImagen: Button

    private lateinit var menuAux: Menu

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_fotografias)
        //setContentView(R.layout.activity_item_editar_registro_fotografico)
        supportActionBar?.setDisplayHomeAsUpEnabled(true) //activo el botón Atrás

        val i = intent
        this.tallerSeleccionado = i.getSerializableExtra("tallerSeleccionado") as Taller
        this.informeNuevo = i.getSerializableExtra("informeNuevo") as Informe
        this.token = i.getSerializableExtra("token") as String
        this.listaAdolescentesInfractores =
            i.getSerializableExtra("listaAsistencia") as ArrayList<AsistenciaAdolescente>
        this.listaActividadesTaller = i.getSerializableExtra("listaActividades") as ArrayList<ItemTaller>

        /*asynTaskObtenerListadoFotografico()

        if (listaFotografias != null) {
            mostrarFotografias()
        }*/

        btnAgregarImagen = findViewById(R.id.btn_agregar_imagenes) as Button
        btnAgregarImagen.setOnClickListener {
            cargarImagen()
        }
    }

    fun mostrarFotografias() {

        var recyclerViewRegistroFotografico = findViewById(R.id.rv_editar_imagenes) as RecyclerView
        var adaptador = IngresarRegistroFotograficoAdaptador(listaFotografias)
        recyclerViewRegistroFotografico.adapter = adaptador
        recyclerViewRegistroFotografico.layoutManager = LinearLayoutManager(this@AgregarRegistroFotograficoActivity)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuAux = menu
        menuInflater.inflate(R.menu.menu_gestion, menu)

        menu.findItem(R.id.menu_editar).isVisible = false
        menu.findItem(R.id.menu_eliminar).isVisible = false
        menu.findItem(R.id.menu_guardar).isVisible = true
        return true

    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        when (item?.itemId) {

            R.id.menu_guardar -> {
                //guardarRegistroFotografico(informeNuevo)
                asynTaskCrearInforme()
                val intent = Intent(applicationContext, VerEditarInformeActivity::class.java)
                intent.putExtra("token", token)
                intent.putExtra("tallerSeleccionado", tallerSeleccionado)
                intent.putExtra("listaActividades", ArrayList(listaActividadesTaller))
                intent.putExtra("listaAsistencia", ArrayList(listaAdolescentesInfractores))
                startActivity(intent)
            }
            else -> {
                finish()
            }
        }
        return true
    }

    fun cargarImagen() {
        var intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        intent.setType("image/")
        startActivityForResult(Intent.createChooser(intent, "Selecciona una imagen"), 10)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            var output: Uri? = data?.data
            var cr: ContentResolver
            cr = this.contentResolver
            var bitmap: Bitmap? = null
            bitmap = android.provider.MediaStore.Images.Media.getBitmap(cr, output)
            val stream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, stream)
            val byteArray = stream.toByteArray()
            var encode: String? = null
            encode = Base64.encodeToString(byteArray, Base64.DEFAULT)
            var fotoAux: RegistroFotografico
            fotoAux = RegistroFotografico()
            fotoAux?.imagenAux = encode
            fotoAux?.foto = bitmap
            if (fotoAux != null) {
                listaFotografias!!.add(fotoAux)
            }
            mostrarFotografias()
        }
    }

    private fun guardarEdicionInforme() : Informe {
        var informeRespuesta : Informe
        informeRespuesta = Informe()
        try {
            var asis = guardarRegistroAsistencia()
            if (asis > 0) {
                var info = guardarInforme()
                if (info != null) {
                    guardarRegistroFotografico(info)
                    informeRespuesta=info
                }
            }

        } catch (e: Exception) {
            Log.e(e.message, "No se ha guardado")
        }
        return informeRespuesta
    }

    private fun guardarRegistroAsistencia(): Int {
        if (listaAdolescentesInfractores != null) {
            var cantidadRegistro = 0
            listaAdolescentesInfractores?.forEach {
                if (it.asistio == true) {
                    val asistenciaAux = servicioGuardarRegistroAsistencia(it)
                    if (asistenciaAux != null) {
                        cantidadRegistro++
                    }
                }else{
                    val asistenciaAux = servicioGuardarRegistroAsistencia(it)
                    if (asistenciaAux != null) {
                        cantidadRegistro--
                    }
                }
            }
            return if (cantidadRegistro > 0) {
                cantidadRegistro
            } else {
                0
            }
        } else {
            return 0
        }
    }

    private fun guardarInforme(): Informe? {
        var respuestaServicio: Informe
        respuestaServicio = Informe()
        if (informeNuevo != null) {
            var infoAux = servicioGuardarInforme(informeNuevo)
            respuestaServicio = infoAux!!
        } else {
            return null
        }
        return respuestaServicio
    }

    private fun guardarRegistroFotografico(informe: Informe){

        if(listaFotografias?.size!! >0){
            listaFotografias?.forEach {
                if(it.imagenAux!=null){
                    it.idInforme=informe
                    asynTaskGuardarFoto(it)
                }
            }
        }
    }

    private fun asynTaskCrearInforme(){

        val miclase = object : AsyncTask<Unit, Unit, Informe>(){

            override fun doInBackground(vararg p0: Unit?): Informe {
                val informeEditado=guardarEdicionInforme()
                return informeEditado
            }

        }
        val informeRescatado=miclase.execute().get()
        Log.i("taller",informeRescatado.toString())
    }

    private fun asynTaskGuardarFoto(rf: RegistroFotografico){

        val miclase = object : AsyncTask<Unit, Unit, Unit>(){

            override fun doInBackground(vararg p0: Unit?) {
                servicioGuardarRegistroFotografico(rf)
            }

        }
        miclase.execute().get()
    }


    private fun servicioGuardarRegistroAsistencia(asistencia: AsistenciaAdolescente): AsistenciaAdolescente? {
        val servicioAsistencia = ClienteApiRest.getRetrofitInstance().create(AsistenciaAdolescenteServicio::class.java)
        val call = servicioAsistencia.guardarAsistenciaAdolescente(asistencia, "Bearer $token")
        val asistenciaGuardado = call.execute().body()
        return asistenciaGuardado!!
    }

    private fun servicioGuardarInforme(informe: Informe): Informe? {
        val servicioInforme = ClienteApiRest.getRetrofitInstance().create(InformeServicio::class.java)
        val call = servicioInforme.editarInforme(informe, "Bearer $token")
        val informeGuardado = call.execute().body()
        return informeGuardado!!
    }

    private fun servicioGuardarRegistroFotografico(registroFoto: RegistroFotografico): RegistroFotografico? {
        val servicioFotografias = ClienteApiRest.getRetrofitInstance().create(RegistroFotograficoServicio::class.java)
        val call = servicioFotografias.guardarRegistroFotograficoMovil(registroFoto, "Bearer $token")
        val fotografiasGuardado = call.execute().body()
        return fotografiasGuardado!!
    }
}
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


class EditarRegistroFotograficoActivity : AppCompatActivity() {

    private var listaFotografias: MutableList<RegistroFotografico>? = ArrayList<RegistroFotografico>()
    private var listaFotografiasRescatado: MutableList<RegistroFotografico>? = null
    private lateinit var informeSeleccionado: Informe
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
        this.informeSeleccionado = i.getSerializableExtra("informeSeleccionado") as Informe
        this.token = i.getSerializableExtra("token") as String
        //this.listaFotografias = i.getSerializableExtra("listaFotos") as ArrayList<RegistroFotografico>
        this.listaAdolescentesInfractores =
            i.getSerializableExtra("listaAsistencia") as ArrayList<AsistenciaAdolescente>
        this.listaActividadesTaller = i.getSerializableExtra("listaActividades") as ArrayList<ItemTaller>

        asynTaskObtenerListadoFotografico()
        asynTaskObtenerListadoFotograficoBase()

        if (listaFotografias != null) {
            mostrarListadoAsistencia()
        }

        btnAgregarImagen = findViewById<Button>(R.id.btn_agregar_imagenes) as Button
        btnAgregarImagen.setOnClickListener {
            cargarImagen()
        }
    }

    fun mostrarListadoAsistencia() {

        var recyclerViewRegistroFotografico = findViewById(R.id.rv_editar_imagenes) as RecyclerView
        var adaptador = IngresarRegistroFotograficoAdaptador(listaFotografias)
        recyclerViewRegistroFotografico.adapter = adaptador
        recyclerViewRegistroFotografico.layoutManager = LinearLayoutManager(this@EditarRegistroFotograficoActivity)
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
                guardarRegistroFotografico(informeSeleccionado)
                //asynTaskEditarInforme()
                val intent = Intent(applicationContext, VerEditarInformeActivity::class.java)
                intent.putExtra("token", token)
                intent.putExtra("informeSeleccionado", informeSeleccionado)
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

    private fun asynTaskObtenerListadoFotografico() {

        val task = object : AsyncTask<Unit, Unit, MutableList<RegistroFotografico>>() {


            override fun doInBackground(vararg p0: Unit?): MutableList<RegistroFotografico>? {
                val listadoFotografias = obtenerRegistroFotografico()
                return listadoFotografias
            }

        }
        listaFotografias = task.execute().get()
    }

    private fun asynTaskObtenerListadoFotograficoBase() {

        val task = object : AsyncTask<Unit, Unit, MutableList<RegistroFotografico>>() {


            override fun doInBackground(vararg p0: Unit?): MutableList<RegistroFotografico>? {
                val listadoFotografias = obtenerRegistroFotografico()
                return listadoFotografias
            }

        }
        listaFotografiasRescatado = task.execute().get()
    }

    private fun obtenerRegistroFotografico(): MutableList<RegistroFotografico>? {
        val servicio = ClienteApiRest.getRetrofitInstance().create(RegistroFotograficoServicio::class.java)
        val call =
            servicio.obtenerRegistroFotograficoPorInforme(informeSeleccionado.idInforme.toString(), "Bearer " + token)

        try {
            val response = call.execute()

            if (response != null) {

                val codigoRespuesta: Int = response.code()

                if (codigoRespuesta == 200) {
                    return response.body()!!
                } else {
                    return null
                }
            } else {
                return null
            }
        } catch (e: Exception) {
            Log.i("ERROR", e.message)
            return null
        }

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
                listaFotografias?.add(fotoAux)
            }
            mostrarListadoAsistencia()
        }
    }

    private fun guardarEdicionInforme(): Informe {
        var informeRespuesta: Informe
        informeRespuesta = Informe()
        try {
            var asis = guardarRegistroAsistencia()
            if (asis > 0) {
                var info = guardarInforme()
                if (info != null) {
                    guardarRegistroFotografico(info)
                    informeRespuesta = info
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
                } else {
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
        if (informeSeleccionado != null) {
            var infoAux = servicioGuardarInforme(informeSeleccionado)
            respuestaServicio = infoAux!!
        } else {
            return null
        }
        return respuestaServicio
    }

    private fun guardarRegistroFotografico(informe: Informe) {
        var listadoFotosAGuardar: MutableList<RegistroFotografico>
        var listadoFotosABorrar: MutableList<RegistroFotografico>
        var respuestaServicioFoto: RegistroFotografico
        respuestaServicioFoto = RegistroFotografico()
        listadoFotosAGuardar = ArrayList<RegistroFotografico>()
        listadoFotosABorrar = ArrayList<RegistroFotografico>()
        for (rf1: RegistroFotografico in listaFotografias!!) {
            if (rf1.idRegistroFotografico == null) {
                listadoFotosAGuardar.add(rf1)
            } else {
                for (rf2: RegistroFotografico in listaFotografiasRescatado!!) {
                    // si las fotos no se han cambiado
                    if (rf1 == rf2) {
                        listadoFotosAGuardar.add(rf2)
                        //break
                    }
                    //si he borrado una foto
                    else if (rf2.idRegistroFotografico != null) {
                        //si agrego una nuevo imagen
                        listadoFotosABorrar.add(rf2)
                        //break
                    }
                }
            }
        }
        if (listadoFotosABorrar.size > 0) {
            listadoFotosABorrar.forEach {
                if (it.imagenAux != null) {
                    asynTaskEliminarFoto(it)
                }
            }
        }
        if (listadoFotosAGuardar.size > 0) {
            listadoFotosAGuardar.forEach {
                if (it.imagenAux != null) {
                    it.idInforme = informe
                    //servicioGuardarRegistroFotografico(it)
                    asynTaskEditarFoto(it)
                }
            }
        }
        /*if(listaFotografias?.size!! >0){
            listaFotografias?.forEach {
                if(it.imagenAux!=null){
                    it.idInforme=informe
                    //servicioGuardarRegistroFotografico(it)
                    asynTaskEditarFoto(it)
                }
            }
        }*/
    }

    private fun asynTaskEditarInforme() {

        val miclase = object : AsyncTask<Unit, Unit, Informe>() {

            override fun doInBackground(vararg p0: Unit?): Informe {
                val informeEditado = guardarEdicionInforme()
                return informeEditado
            }

        }
        val informeRescatado = miclase.execute().get()
        Log.i("taller", informeRescatado.toString())
    }

    private fun asynTaskEditarFoto(rf: RegistroFotografico) {

        val miclase = object : AsyncTask<Unit, Unit, Unit>() {

            override fun doInBackground(vararg p0: Unit?) {
                servicioGuardarRegistroFotografico(rf)
            }

        }
        miclase.execute().get()
    }

    private fun asynTaskEliminarFoto(rf: RegistroFotografico) {

        val miclase = object : AsyncTask<Unit, Unit, Unit>() {

            override fun doInBackground(vararg p0: Unit?) {
                servicioEliminarRegistroFotografico(rf)
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

    private fun servicioEliminarRegistroFotografico(registroFoto: RegistroFotografico): Int? {
        val servicioEliminarFotografias =
            ClienteApiRest.getRetrofitInstance().create(RegistroFotograficoServicio::class.java)
        val call = servicioEliminarFotografias.eliminarRegistroFotografico(
            registroFoto.idRegistroFotografico.toString(),
            "Bearer $token"
        )
        val fotografiasEliminar = call.execute().body()
        println(fotografiasEliminar)
        return fotografiasEliminar!!
    }
}
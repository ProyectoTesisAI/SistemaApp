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
import android.widget.Toast
import ec.edu.epn.snai.Controlador.Adaptador.IngresarRegistroFotograficoAdaptador
import ec.edu.epn.snai.Modelo.*
import java.io.ByteArrayOutputStream
import ec.edu.epn.snai.Modelo.AsistenciaAdolescente
import ec.edu.epn.snai.R
import ec.edu.epn.snai.Servicios.*
import kotlinx.android.synthetic.main.activity_editar_fotografias.*


class EditarRegistroFotograficoActivity : AppCompatActivity() {

    //Me permite tratar a todos los atributos y métodos dentro del object como estáticos
    companion object {
        var listaFotografiasEliminar: MutableList<RegistroFotografico>? = ArrayList<RegistroFotografico>()
    }

    private var listaFotografias: MutableList<RegistroFotografico>? = ArrayList<RegistroFotografico>()
    private lateinit var informeSeleccionado: Informe
    private lateinit var token: String
    private var listaAsistenciaAdolescentes: List<AsistenciaAdolescente>? = null
    private lateinit var btnAgregarImagen: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_fotografias)
        supportActionBar?.setDisplayHomeAsUpEnabled(true) //activo el botón Atrás

        val i = intent
        this.informeSeleccionado = i.getSerializableExtra("informeSeleccionado") as Informe
        this.token = i.getSerializableExtra("token") as String
        this.listaAsistenciaAdolescentes = i.getSerializableExtra("listaAsistencia") as ArrayList<AsistenciaAdolescente>

        asynTaskObtenerRegistroFotografico()

        if (listaFotografias != null) {
            mostrarFotografias()
        }

        btn_agregar_imagenes.setOnClickListener {
            cargarImagen()
        }
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {

        menuInflater.inflate(R.menu.menu_gestion, menu)

        menu.findItem(R.id.menu_editar).isVisible = false
        menu.findItem(R.id.menu_eliminar).isVisible = false
        menu.findItem(R.id.menu_guardar).isVisible = true
        return true

    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        when (item?.itemId) {

            R.id.menu_guardar -> {
                if (!informeSeleccionado.adolescentesJustificacion.isNullOrBlank() && !informeSeleccionado.socializacionDesarrollo.isNullOrBlank() && !informeSeleccionado.socializacionObjetivos.isNullOrBlank() && !informeSeleccionado.cierreEvaluacion.isNullOrBlank() && !informeSeleccionado.conclusiones.isNullOrBlank()) {

                    if (informeSeleccionado.numeroAdolescentes > 0) {

                        if (listaFotografias?.size!! > 0) {

                            val informeAux = editarInforme()
                            if (informeAux != null) {

                                editarRegistroAsistencia()
                                guardarRegistroFotografico(informeAux)


                                Toast.makeText(
                                    applicationContext,
                                    "Se ha guardado correctamente el Informe",
                                    Toast.LENGTH_SHORT
                                ).show()

                                val intent = Intent(applicationContext, MainActivity::class.java)
                                //seteo la bandera FLAG_ACTIVITY_CLEAR_TOP para indicar que el activity actuar lo voy a eliminar del stack
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                                val usuarioAux = informeAux.idTaller.idUsuario
                                usuarioAux.token = this.token
                                intent.putExtra("usuario", usuarioAux)
                                startActivity(intent)
                            }

                        } else {
                            Toast.makeText(applicationContext, "Debe de ingresar al menos una foto", Toast.LENGTH_SHORT)
                                .show()

                        }
                    } else {
                        Toast.makeText(
                            applicationContext,
                            "Debe de seleccionar la asistencia de los Adolescentes Infractores",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                } else {
                    Toast.makeText(
                        applicationContext,
                        "Antecedenes  y justificación, Desarrollo, Objetivos Específicos, Cierre y evaluación y Conclusiones son campos obligatotios, ingrese un valor",
                        Toast.LENGTH_SHORT
                    ).show()
                }

            }
            else -> {
                finish()
            }
        }
        return true
    }

    private fun cargarImagen() {

        if (listaFotografias?.size!! < 5) {

            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            intent.setType("image/")
            startActivityForResult(Intent.createChooser(intent, "Seleccione una imagen"), 10)

        } else {
            Toast.makeText(applicationContext, "Ha alcanzado el número máximo de fotos", Toast.LENGTH_SHORT).show()
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {

            val output: Uri? = data?.data
            val cr: ContentResolver= this.contentResolver

            val bitmap: Bitmap?  = MediaStore.Images.Media.getBitmap(cr, output)
            val imagenaUX = Bitmap.createScaledBitmap( bitmap, 800, 600, false);

            val stream = ByteArrayOutputStream()
            imagenaUX?.compress(Bitmap.CompressFormat.JPEG, 25, stream)

            val byteArray = stream.toByteArray()
            val encode: String? = Base64.encodeToString(byteArray, Base64.DEFAULT)

            val fotoAux = RegistroFotografico()
            fotoAux.imagenAux = encode
            fotoAux.foto = imagenaUX

            if (fotoAux != null) {
                listaFotografias!!.add(fotoAux)
            }

            mostrarFotografias()
        }
    }

    private fun mostrarFotografias() {

        if (listaFotografias != null) {

            val recyclerViewRegistroFotografico = findViewById(R.id.rv_editar_imagenes) as RecyclerView
            val adaptador = IngresarRegistroFotograficoAdaptador(listaFotografias)
            recyclerViewRegistroFotografico.adapter = adaptador
            recyclerViewRegistroFotografico.layoutManager = LinearLayoutManager(this@EditarRegistroFotograficoActivity)
        }

    }


    /*********************Obtener Registro Fotográfico***************************/
    private fun asynTaskObtenerRegistroFotografico() {

        val task = object : AsyncTask<Unit, Unit, MutableList<RegistroFotografico>>() {


            override fun doInBackground(vararg p0: Unit?): MutableList<RegistroFotografico>? {
                val listadoFotografias = servicioObtenerRegistroFotografico()
                return listadoFotografias
            }

        }
        listaFotografias = task.execute().get()
    }

    private fun servicioObtenerRegistroFotografico(): MutableList<RegistroFotografico>? {
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
            return null
        }

    }


    /*********************Editar Informe***************************/
    private fun editarInforme(): Informe? {

        if (this.informeSeleccionado != null) {

            val informeAux = asynTaskEditarInforme(informeSeleccionado)
            if (informeAux != null) {
                return informeAux
            } else {
                return null
            }
        } else {
            return null
        }
    }

    private fun asynTaskEditarInforme(informe: Informe): Informe? {

        val miclase = object : AsyncTask<Unit, Unit, Informe>() {

            override fun doInBackground(vararg p0: Unit?): Informe? {
                val informeEditado = servicioEditarInforme(informe)
                return informeEditado
            }

        }
        val informeRescatado = miclase.execute().get()
        if (informeRescatado != null) {
            return informeRescatado
        } else {
            return null
        }
    }

    private fun servicioEditarInforme(informe: Informe): Informe? {
        val servicioInforme = ClienteApiRest.getRetrofitInstance().create(InformeServicio::class.java)
        val call = servicioInforme.editarInforme(informe, "Bearer $token")
        val response = call.execute()

        if (response != null) {

            if (response.code() == 200) {
                val informeGuardado = response.body()
                return informeGuardado
            } else {
                return null
            }
        } else {
            return null
        }
    }


    /*********************Editar Registro Asistencia***************************/
    private fun editarRegistroAsistencia() {

        if (listaAsistenciaAdolescentes != null) {

            for (asistenciaAdolescente in listaAsistenciaAdolescentes!!) {

                asynTaskEditarRegistroAsistencia(asistenciaAdolescente)

            }
        }
    }

    private fun asynTaskEditarRegistroAsistencia(asistenciaAdolescente: AsistenciaAdolescente) {

        val miclase = object : AsyncTask<Unit, Unit, Unit>() {

            override fun doInBackground(vararg p0: Unit?) {
                servicioEditarRegistroAsistencia(asistenciaAdolescente)

            }

        }
        miclase.execute().get()
    }

    private fun servicioEditarRegistroAsistencia(asistencia: AsistenciaAdolescente) {

        val servicioAsistencia = ClienteApiRest.getRetrofitInstance().create(AsistenciaAdolescenteServicio::class.java)
        val call = servicioAsistencia.guardarAsistenciaAdolescente(asistencia, "Bearer $token")
        val asistenciaGuardado = call.execute()
    }


    /*********************Guardar Registro Fotográfico***************************/
    private fun guardarRegistroFotografico(informe: Informe) {

        if (listaFotografiasEliminar?.size!! > 0) {

            for (rf in listaFotografiasEliminar!!) {

                if (rf.idRegistroFotografico != null) {
                    asynTaskEliminarRegistroFotografico(rf)
                }
            }
        }

        if (informe != null) {

            if (listaFotografias?.size!! > 0) {

                for (f in listaFotografias!!) {

                    if (f.idRegistroFotografico == null) {

                        f.idInforme = informe
                        asynTaskGuardarRegistroFotografico(f)
                    }
                }
            }
        }

    }

    private fun asynTaskGuardarRegistroFotografico(rf: RegistroFotografico) {

        val miclase = object : AsyncTask<Unit, Unit, Unit>() {

            override fun doInBackground(vararg p0: Unit?) {
                servicioGuardarRegistroFotografico(rf)
            }

        }
        miclase.execute().get()
    }

    private fun servicioGuardarRegistroFotografico(registroFoto: RegistroFotografico): RegistroFotografico? {
        val servicioFotografias = ClienteApiRest.getRetrofitInstance().create(RegistroFotograficoServicio::class.java)
        val call = servicioFotografias.guardarRegistroFotograficoMovil(registroFoto, "Bearer $token")
        val fotografiasGuardado = call.execute().body()
        return fotografiasGuardado!!
    }


    /*********************Eliminar Registro Fotográfico***************************/

    private fun asynTaskEliminarRegistroFotografico(registroFotografico: RegistroFotografico) {

        val miclase = object : AsyncTask<Unit, Unit, Unit>() {

            override fun doInBackground(vararg p0: Unit?) {
                servicioEliminarRegistroFotografico(registroFotografico)
            }

        }
        miclase.execute().get()
    }

    private fun servicioEliminarRegistroFotografico(registroFoto: RegistroFotografico) {

        val servicioEliminarFotografias = ClienteApiRest.getRetrofitInstance().create(RegistroFotograficoServicio::class.java)
        val call = servicioEliminarFotografias.eliminarRegistroFotografico(registroFoto.idRegistroFotografico.toString(), "Bearer $token")
        val response =call.execute()

        if(response != null){

            val code=response.code()
            Log.i("code", code.toString())
        }
    }


}
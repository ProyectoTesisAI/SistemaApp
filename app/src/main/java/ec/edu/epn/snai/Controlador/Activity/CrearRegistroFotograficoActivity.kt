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
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import ec.edu.epn.snai.Controlador.Adaptador.ListaEditarRegistroFotograficoAdaptador
import ec.edu.epn.snai.Modelo.*
import java.io.ByteArrayOutputStream
import ec.edu.epn.snai.Modelo.AsistenciaAdolescente
import ec.edu.epn.snai.R
import ec.edu.epn.snai.Servicios.*
import kotlinx.android.synthetic.main.activity_editar_fotografias.*


class CrearRegistroFotograficoActivity : AppCompatActivity() {

    private var listaFotografias: ArrayList<RegistroFotografico> = ArrayList()
    private lateinit var informeNuevo: Informe
    private lateinit var token: String
    private var listaAsistenciaAdolescentes: List<AsistenciaAdolescente>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_fotografias)
        supportActionBar?.setDisplayHomeAsUpEnabled(true) //activo el botón Atrás

        val i = intent
        this.informeNuevo = i.getSerializableExtra("informeNuevo") as Informe
        this.token = i.getSerializableExtra("token") as String
        this.listaAsistenciaAdolescentes = i.getSerializableExtra("listaAsistencia") as ArrayList<AsistenciaAdolescente>


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

                if(!informeNuevo.adolescentesJustificacion.isNullOrBlank() && !informeNuevo.socializacionDesarrollo.isNullOrBlank() && !informeNuevo.socializacionObjetivos.isNullOrBlank() && !informeNuevo.cierreEvaluacion.isNullOrBlank() && !informeNuevo.conclusiones.isNullOrBlank() ){

                    if(informeNuevo.numeroAdolescentes >0 ){

                        if(listaFotografias?.size!! >0){

                            val informeAux=guardarInforme()
                            if(informeAux != null){

                                guardarListadoRegistroAsistencia()
                                guardarRegistroFotografico(informeAux)

                                Toast.makeText(applicationContext, "Se ha guardado correctamente el Informe", Toast.LENGTH_SHORT).show()

                                val intent = Intent(this@CrearRegistroFotograficoActivity, MainActivity::class.java)
                                //seteo la bandera FLAG_ACTIVITY_CLEAR_TOP ya que si la actividad que se lanza con el intent ya está en la pila de actividades,
                                // en lugar de lanzar una nueva instancia de dicha actividad, el resto de activities en la pila serán cerradas
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                                val usuarioAux=MainActivity.Companion.usuario
                                intent.putExtra("usuario",usuarioAux)
                                intent.putExtra("tipoInforme", informeAux.idTaller.tipo)
                                startActivity(intent)
                            }
                        }
                        else{
                            Toast.makeText(applicationContext, "Debe de ingresar al menos una foto", Toast.LENGTH_SHORT).show()

                        }
                    }
                    else{
                        Toast.makeText(applicationContext, "Debe de seleccionar la asistencia de los Adolescentes Infractores", Toast.LENGTH_SHORT).show()
                    }

                }
                else{
                    Toast.makeText(applicationContext, "Antecedenes  y justificación, Desarrollo, Objetivos Específicos, Cierre y evaluación y Conclusiones son campos obligatotios, ingrese un valor", Toast.LENGTH_SHORT).show()
                }
            }
            else -> {
                finish()
            }
        }
        return true
    }

    private fun cargarImagen() {


        if(listaFotografias.size < 5 ){

            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            intent.setType("image/")
            startActivityForResult(Intent.createChooser(intent, "Seleccione una imagen"), 10)
        }
        else{
            Toast.makeText(applicationContext, "Ha alcanzado el número máximo de fotos", Toast.LENGTH_SHORT).show()
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {

            val output: Uri? = data?.data
            val cr: ContentResolver= this.contentResolver

            val bitmap: Bitmap?  = MediaStore.Images.Media.getBitmap(cr, output)

            var ancho=bitmap?.width!!
            var alto=bitmap.height

            while (alto > 875 || ancho > 875){

                ancho= (ancho/1.25F).toInt()
                alto=(alto/1.25F).toInt()
            }

            val imagenaUX = Bitmap.createScaledBitmap( bitmap, ancho, alto, false);

            val stream = ByteArrayOutputStream()
            imagenaUX?.compress(Bitmap.CompressFormat.JPEG, 95, stream)

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

        if(listaFotografias != null){

            val recyclerViewRegistroFotografico = findViewById(R.id.rv_editar_imagenes) as RecyclerView
            val adaptador =
                ListaEditarRegistroFotograficoAdaptador(listaFotografias)
            recyclerViewRegistroFotografico.adapter = adaptador
            recyclerViewRegistroFotografico.layoutManager = LinearLayoutManager(this@CrearRegistroFotograficoActivity)
        }

    }




    /*******************Guardar Informr*******************************************/
    private fun guardarInforme():Informe?{

        if(this.informeNuevo != null){

            val informeAux= asynTaskGuardarInforme(informeNuevo)
            if(informeAux != null){
                return informeAux
            }
            else{
                return null
            }
        }
        else {
            return null
        }
    }

    private fun asynTaskGuardarInforme(informe: Informe): Informe?{

        val miclase = object : AsyncTask<Unit, Unit, Informe>(){

            override fun doInBackground(vararg p0: Unit?): Informe? {
                val informeEditado=servicioGuardarInforme(informe)
                return informeEditado
            }

        }
        val informeRescatado=miclase.execute().get()
        if(informeRescatado != null){
            return informeRescatado
        }
        else{
            return null
        }
    }

    private fun servicioGuardarInforme(informe: Informe): Informe? {
        val servicioInforme = ClienteApiRest.getRetrofitInstance().create(InformeServicio::class.java)
        val call = servicioInforme.editarInforme(informe, "Bearer $token")
        val response = call.execute()

        if(response != null){

            if(response.code() == 200){
                val informeGuardado= response.body()
                return informeGuardado
            }
            else{
                return null
            }
        }
        else{
            return null
        }
    }


    /**************** Guardar Listado de Registro de Asistencia¨***********************/
    private fun guardarListadoRegistroAsistencia() {

        if(listaAsistenciaAdolescentes != null){

            for(asistenciaAdolescente in listaAsistenciaAdolescentes!!){

                asynTaskGuardarListadoRegistroAsistencia(asistenciaAdolescente)

            }
        }


    }

    private fun asynTaskGuardarListadoRegistroAsistencia(asistenciaAdolescente: AsistenciaAdolescente){

        val miclase = object : AsyncTask<Unit, Unit, Unit>() {

            override fun doInBackground(vararg p0: Unit?) {
                servicioGuardarListadoRegistroAsistenciaUzdi(asistenciaAdolescente)
            }

        }
        miclase.execute().get()
    }

    private fun servicioGuardarListadoRegistroAsistenciaUzdi(asistenciaAdolescente: AsistenciaAdolescente){

        val servicioAsistenciaAdolescente= ClienteApiRest.getRetrofitInstance().create(AsistenciaAdolescenteServicio::class.java)
        val call =servicioAsistenciaAdolescente.guardarAsistenciaAdolescente( asistenciaAdolescente,"Bearer $token")
        call.execute()
    }


    /*******************Guardar Registro Fotográfico******************************/
    private fun guardarRegistroFotografico(informe: Informe){

        if(informe != null){

            if(listaFotografias?.size!! > 0 ){

                for (f in listaFotografias!!){

                    if(f.imagenAux!=null){

                        f.idInforme=informe

                        asynTaskGuardarFoto(f)
                    }
                }
            }
        }

    }

    private fun asynTaskGuardarFoto(rf: RegistroFotografico){

        val miclase = object : AsyncTask<Unit, Unit, Unit>(){

            override fun doInBackground(vararg p0: Unit?) {
                servicioGuardarRegistroFotografico(rf)
            }

        }
        miclase.execute().get()
    }

    private fun servicioGuardarRegistroFotografico(registroFoto: RegistroFotografico): RegistroFotografico? {
        val servicioFotografias = ClienteApiRest.getRetrofitInstance().create(RegistroFotograficoServicio::class.java)
        val call = servicioFotografias.guardarRegistroFotograficoMovil(registroFoto, "Bearer $token")

        val response = call.execute()

        if(response != null){

            if(response.code() == 200){
                val fotografiasGuardado= response.body()
                return fotografiasGuardado
            }
            else{
                return null
            }
        }
        else{
            return null
        }
    }






}
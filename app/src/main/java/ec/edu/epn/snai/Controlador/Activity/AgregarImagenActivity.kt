package ec.edu.epn.snai.Controlador.Activity

import android.app.Activity
import android.content.ContentResolver
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.support.v7.app.AppCompatActivity
import android.util.Base64
import android.widget.Button
import android.widget.ImageView
import ec.edu.epn.snai.R
import java.io.ByteArrayOutputStream




class AgregarImagenActivity : AppCompatActivity(){
    private lateinit var btnAgregarImagen:Button
    private lateinit var imgImagenPrevia:ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_fotografias)
        imgImagenPrevia=findViewById(R.id.imagenPrevia) as ImageView
        btnAgregarImagen=findViewById(R.id.btn_agregar_imagenes) as Button
        btnAgregarImagen.setOnClickListener {
            cargarImagen()
        }
    }

    fun cargarImagen(){
        var intent=Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        intent.setType("image/")
        startActivityForResult(Intent.createChooser(intent,"Selecciona una imagen"),10)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode== Activity.RESULT_OK){
            var output : Uri?= data?.data
            /*var path : Uri?= data?.data
            println(path?.path)
            decodificarBitmap(path?.path)
            imgImagenPrevia.setImageURI(path)*/
            var cr : ContentResolver
            cr=this.contentResolver
            var bitmap : Bitmap?=null
            bitmap = android.provider.MediaStore.Images.Media.getBitmap(cr,output)
            val stream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG,0,stream)
            val byteArray = stream.toByteArray()
            var encode : String?=null
            encode=Base64.encodeToString(byteArray,Base64.DEFAULT)
            imgImagenPrevia.setImageBitmap(bitmap)
        }
    }

    fun decodificarBitmap(dir:String?){
        var bitmap : Bitmap?=null
        bitmap=BitmapFactory.decodeFile(dir)
        println("foto decodificada: "+bitmap)
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG,0,stream)
        println("foto en: "+bitmap)
    }
}

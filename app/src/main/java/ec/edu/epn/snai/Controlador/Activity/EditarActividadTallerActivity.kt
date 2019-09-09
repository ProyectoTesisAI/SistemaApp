package ec.edu.epn.snai.Controlador.Activity

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import ec.edu.epn.snai.Modelo.ItemTaller
import ec.edu.epn.snai.R
import kotlinx.android.synthetic.main.activity_editar_actividad_taller.*

class EditarActividadTallerActivity : AppCompatActivity() {

    var actividadAux=ItemTaller()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_actividad_taller)
        supportActionBar?.setDisplayHomeAsUpEnabled(true) //activo el botón Atrás

        actividadAux = intent.getSerializableExtra("actividad_seleccionada") as ItemTaller

        etActividadEditar?.setText(actividadAux?.actividad)
        etObjetivoEditar?.setText(actividadAux?.objetivoEspecifico)
        etMaterialesEditar?.setText(actividadAux?.materiales)
        etResponsableEditar?.setText(actividadAux?.responsable)
        etDuracionEditar?.setText(actividadAux?.duracion.toString())

        habilitarDeshabilitarAtributos(false)
        habilitarDeshabilitarFocus(false)

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_gestion, menu)

        menu.findItem(R.id.menu_editar).isVisible = true
        menu.findItem(R.id.menu_eliminar).isVisible=true
        menu.findItem(R.id.menu_guardar).isVisible=false
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        when(item?.itemId){
            R.id.menu_guardar->{

                actividadAux?.actividad=etActividadEditar?.getText().toString()
                actividadAux?.objetivoEspecifico=etObjetivoEditar?.getText().toString()
                actividadAux?.materiales=etMaterialesEditar?.getText().toString()
                actividadAux?.responsable=etResponsableEditar?.getText().toString()
                actividadAux?.duracion= etDuracionEditar?.getText().toString().toInt()

                val intent = Intent()
                intent.putExtra("actividad_rescatada",actividadAux)
                setResult(Activity.RESULT_OK,intent)

                finish()
            }
            R.id.menu_editar->{
                habilitarDeshabilitarAtributos(true)
                habilitarDeshabilitarFocus(true)
            }
            R.id.menu_eliminar->{

            }
            else->{
                finish()
            }
        }
        return true
    }

    fun habilitarDeshabilitarAtributos(habilitado: Boolean){
        etActividadEditar?.isEnabled =habilitado
        etObjetivoEditar?.isEnabled =habilitado
        etMaterialesEditar?.isEnabled =habilitado
        etResponsableEditar?.isEnabled =habilitado
        etDuracionEditar?.isEnabled =habilitado

    }

    fun habilitarDeshabilitarFocus(habilitado: Boolean){
        etActividadEditar?.isFocusableInTouchMode =habilitado
        etObjetivoEditar?.isFocusableInTouchMode =habilitado
        etMaterialesEditar?.isFocusableInTouchMode =habilitado
        etResponsableEditar?.isFocusableInTouchMode =habilitado
        etDuracionEditar?.isFocusableInTouchMode =habilitado

        etActividadEditar?.isFocusable =habilitado
        etObjetivoEditar?.isFocusable =habilitado
        etMaterialesEditar?.isFocusable =habilitado
        etResponsableEditar?.isFocusable =habilitado
        etDuracionEditar?.isFocusable =habilitado

    }

}

package ec.edu.epn.snai.Controlador.Activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import ec.edu.epn.snai.Modelo.Informe
import ec.edu.epn.snai.R
import org.w3c.dom.Text
import java.text.SimpleDateFormat

class VerInformeActivity : AppCompatActivity() {

    private var txtTemaInforme: TextView?=null
    private var txtNumeroTallerInforme: TextView?=null
    private var txtFechaInforme: TextView?=null
    private var txtHoraInicioInforme: TextView?=null
    private var txtHoraFinInforme: TextView?=null
    private var txtNumeroAdolescentesInforme: TextView?=null
    private var txtObjetivoGeneralInforme: TextView?=null

    private var sdff : SimpleDateFormat?=null
    private var sdfh : SimpleDateFormat?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ver_informe)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        sdff = SimpleDateFormat("dd/MM/yyyy")
        sdfh = SimpleDateFormat("HH:mm")

        val i = intent
        val informe = i.getSerializableExtra("informe_seleccionado") as Informe?

        txtTemaInforme=findViewById(R.id.txtTemaInformeSeleccionado) as TextView
        txtNumeroTallerInforme=findViewById(R.id.txtNumeroTallerSeleccionado) as TextView
        txtFechaInforme=findViewById(R.id.txtFechaTallerSeleccionado) as TextView
        txtHoraInicioInforme=findViewById(R.id.txtHoraInicioTallerSeleccionado) as TextView
        txtHoraFinInforme=findViewById(R.id.txtHoraFinInformeSeleccionado) as TextView
        txtNumeroAdolescentesInforme=findViewById(R.id.txtNumeroAdolescenteInformeSeleccionado) as TextView
        txtObjetivoGeneralInforme=findViewById(R.id.txtObjetivoGeneralInformeSeleccionado) as TextView

        txtTemaInforme?.text=informe?.idTaller?.tema
        txtNumeroTallerInforme?.text=informe?.idTaller?.idTaller.toString()
        txtFechaInforme?.text=sdff?.parse(informe?.idTaller?.fecha.toString()).toString()
        txtHoraInicioInforme?.text= sdfh?.parse(informe?.idTaller?.horaInicio.toString()).toString()
        txtHoraFinInforme?.text=sdfh?.parse(informe?.horaFin.toString()).toString()
        txtNumeroAdolescentesInforme?.text=informe?.numeroAdolescentes.toString()
        txtObjetivoGeneralInforme?.text=informe?.objetivoGeneral
    }
}
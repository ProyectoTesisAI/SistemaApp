package ec.edu.epn.snai.Controlador.Activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import ec.edu.epn.snai.Modelo.Informe
import ec.edu.epn.snai.R
import java.text.SimpleDateFormat

class VerInformeActivity : AppCompatActivity() {

    private var txtTemaInforme: TextView?=null
    private var txtNumeroTallerInforme: TextView?=null
    private var txtFechaInforme: TextView?=null
    private var txtHoraInicioInforme: TextView?=null
    private var txtHoraFinInforme: TextView?=null
    private var txtNumeroAdolescentesInforme: TextView?=null
    private var txtObjetivoGeneralInforme: TextView?=null
    private var txtSocializacionDesarrollo: TextView?=null
    private var txtSocializacionObjetivos: TextView? = null
    private var txtCierreEvaluacion: TextView? = null
    private var txtConclusiones: TextView? = null
    private var txtRecomendaciones: TextView? = null
    private var txtObservaciones: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ver_informe)
        supportActionBar?.setDisplayHomeAsUpEnabled(true) //activo el botón Atrás

        val pattern = "dd/MM/yyyy"
        val simpleDateFormat = SimpleDateFormat(pattern)

        val patternHour = "HH:mm"
        val simpleHourFormat = SimpleDateFormat(patternHour)

        val i = intent
        val informe = i.getSerializableExtra("informeSeleccionado") as Informe?

        txtTemaInforme=findViewById(R.id.txtTemaInformeSeleccionado) as TextView
        txtNumeroTallerInforme=findViewById(R.id.txtNumeroTallerSeleccionado) as TextView
        txtFechaInforme=findViewById(R.id.txtFechaTallerSeleccionado) as TextView
        txtHoraInicioInforme=findViewById(R.id.txtHoraInicioTallerSeleccionado) as TextView
        txtHoraFinInforme=findViewById(R.id.txtHoraFinInformeSeleccionado) as TextView
        txtNumeroAdolescentesInforme=findViewById(R.id.txtNumeroAdolescenteInformeSeleccionado) as TextView
        txtObjetivoGeneralInforme=findViewById(R.id.txtObjetivoGeneralInformeSeleccionado) as TextView
        txtSocializacionDesarrollo=findViewById(R.id.txtSocializacionDesarrolloInformeSeleccionado) as TextView
        txtSocializacionObjetivos=findViewById(R.id.txtSocializacionObjetivosInformeSeleccionado) as TextView
        txtCierreEvaluacion=findViewById(R.id.txtCierreEvaluacionInformeSeleccionado) as TextView
        txtConclusiones=findViewById(R.id.txtConclusionesInformeSeleccionado) as TextView
        txtRecomendaciones=findViewById(R.id.txtRecomendacionesInformeSeleccionado) as TextView
        txtObservaciones=findViewById(R.id.txtObservacionesInformeSeleccionado) as TextView

        txtTemaInforme?.text=informe?.idTaller?.tema
        txtNumeroTallerInforme?.text=informe?.idTaller?.idTaller.toString()
        val fecha = simpleDateFormat.format(informe?.idTaller?.fecha)
        txtFechaInforme?.text=fecha
        val horaInicio = simpleHourFormat.format(informe?.idTaller?.horaInicio)
        txtHoraInicioInforme?.text= horaInicio
        val horaFin = simpleHourFormat.format(informe?.horaFin)
        txtHoraFinInforme?.text=horaFin
        txtNumeroAdolescentesInforme?.text=informe?.numeroAdolescentes.toString()
        txtObjetivoGeneralInforme?.text=informe?.objetivoGeneral
        txtSocializacionDesarrollo?.text=informe?.socializacionDesarrollo
        txtSocializacionObjetivos?.text=informe?.socializacionObjetivos
        txtCierreEvaluacion?.text=informe?.cierreEvaluacion
        txtConclusiones?.text=informe?.conclusiones
        txtRecomendaciones?.text=informe?.recomendaciones
        txtObservaciones?.text=informe?.observaciones
    }
}
package ec.edu.epn.snai.Controlador.Fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.*
import ec.edu.epn.snai.Controlador.Adaptador.ItemInformeAdaptador
import ec.edu.epn.snai.Modelo.*
import ec.edu.epn.snai.R
import ec.edu.epn.snai.Servicios.ClienteApiRest
import ec.edu.epn.snai.Servicios.TallerServicio
import kotlinx.android.synthetic.main.fragment_ver_taller.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList

class VerTallerFragment : Fragment(){

    private lateinit var token:String
    private lateinit var taller:Taller
    private lateinit var menuAux: Menu
    private var itemsTaller: ArrayList<ItemTaller>?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if(arguments!=null){
            token=arguments?.getSerializable("token") as String
            taller=arguments?.getSerializable("taller_seleccionado") as Taller
            itemsTaller=arguments?.getSerializable("items_taller_seleccionado") as ArrayList<ItemTaller>
        }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_ver_taller, container, false)

        asignarVariablesTaller(rootView)
        return rootView
    }

    private fun asignarVariablesTaller(view: View){

        view.txtTemaTallerVer?.text=this.taller.tema
        view.txtNumeroTallerVer?.text= this.taller.numeroTaller.toString()
        if (this.taller.fecha != null) {
            view.txtFechaTallerVer?.text= this.formatearFecha(this.taller.fecha)
        }
        if(this.taller.horaInicio != null){
            view.txtHoraTallerVer?.text= this.formatearHora(this.taller.horaInicio)
        }

        view.txtTipoCentroVer?.text=this.obtenerTipoCentro()
        view.txtUdiCaiVer?.text=this.obtenerItemUzdiCai()
        view.txtNumeroParticipantesTallerVer?.text=this.taller.numeroTotalParticipantes.toString()
        view.txtObjetivoTallerVer?.text=this.taller.objetivo.toString()
        view.txtRecomendacionesTallerVer?.text= this.taller.recomendaciones.toString()
        asignarItemsTaller(view)
    }

    private fun asignarItemsTaller(rootView: View){

        val adaptadorItemInforme= ItemInformeAdaptador(itemsTaller)
        val recyclerViewItemsInforme= rootView.findViewById<RecyclerView>(R.id.rv_items_taller)
        recyclerViewItemsInforme.adapter=adaptadorItemInforme
        recyclerViewItemsInforme.layoutManager= LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
    }

    private fun formatearFecha(fecha: Date): String {
        val dia = fecha.day
        val mes = fecha.month
        var anio = fecha.year

        var diaString = dia.toString()
        if (dia < 10) {
            diaString = String.format("0$dia")
        }

        var mesString = dia.toString()
        if (mes < 10) {
            mesString = String.format("0$mes")
        }
        anio = anio + 1900
        val anioString = anio.toString()

        return "$diaString/$mesString/$anioString"
    }

    private fun formatearHora(horaInicio: Date): String {
        val hora = horaInicio.hours
        val minuto = horaInicio.minutes

        var hora_string = hora.toString()
        if (hora < 10) {
            hora_string = String.format("0$hora")
        }

        var minuto_string = minuto.toString()
        if (minuto < 10) {
            minuto_string = String.format("0$minuto")
        }
        return "$hora_string:$minuto_string"
    }

    private fun obtenerTipoCentro():CharSequence {

        var tipoCentro= ""

        if(taller.idUdi!=null){

            tipoCentro="UZDI"
        }
        else if(taller.idCai!= null){
            tipoCentro="CAI"

        }
        return tipoCentro
    }

    private fun obtenerItemUzdiCai(): CharSequence{

        var itemCentro =""

        if(taller.idUdi!=null){
            itemCentro=taller.idUdi.udi

        }
        else if(taller.idCai!= null){
            itemCentro=taller.idCai.cai
        }

        return itemCentro
    }

}
package ec.edu.epn.snai.Controlador.Fragment

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
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
import java.text.SimpleDateFormat
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
            itemsTaller=arguments?.getSerializable("items_taller_seleccionado") as ArrayList<ItemTaller>?
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

        if(itemsTaller != null){
            val adaptadorItemInforme= ItemInformeAdaptador(itemsTaller)
            val recyclerViewItemsInforme= rootView.findViewById<RecyclerView>(R.id.rv_items_taller)
            recyclerViewItemsInforme.adapter=adaptadorItemInforme
            recyclerViewItemsInforme.layoutManager= LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
        }

    }

    private fun formatearFecha(fecha: Date): String {

        val pattern = "dd/MM/yyyy"
        val simpleDateFormat = SimpleDateFormat(pattern)
        return simpleDateFormat.format(fecha)
    }

    private fun formatearHora(horaInicio: Date): String {

        val patternHour = "HH:mm"
        val simpleHourFormat = SimpleDateFormat(patternHour)
        return simpleHourFormat.format(horaInicio)
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
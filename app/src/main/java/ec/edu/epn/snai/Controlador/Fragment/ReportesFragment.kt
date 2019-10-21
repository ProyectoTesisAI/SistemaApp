package ec.edu.epn.snai.Controlador.Fragment

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ec.edu.epn.snai.Controlador.Activity.ReporteTabbedActivity
import ec.edu.epn.snai.R
import ec.edu.epn.snai.Controlador.Activity.TallerTabbedActivity
import ec.edu.epn.snai.Controlador.Adaptador.ListaReportesAdaptador
import ec.edu.epn.snai.Modelo.*

class ReportesFragment: Fragment(), ListaReportesAdaptador.ReporteOnItemClickListener{

    private var listaReportes: ArrayList<Reporte> = ArrayList()
    private lateinit var token:String
    private lateinit var usuario: Usuario

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if(arguments!=null){
            usuario=arguments?.getSerializable("usuario") as Usuario
            this.token=usuario.token

        }
        agregarListaReportes()

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView= inflater.inflate(R.layout.fragment_reportes,container,false)

        asignarListaReportesRecyclerView(listaReportes,rootView)
        return rootView
    }

    override fun OnItemClick(posicion: Int) {

        val intent = Intent(context, ReporteTabbedActivity::class.java)
        intent.putExtra("nombreReporte", listaReportes.get(posicion).nombre)
        intent.putExtra("usuario", usuario)
        startActivity(intent)
    }


    /**************** Obtener Lista de Reportes¨***********************/
    private fun agregarListaReportes(){
        listaReportes.add( Reporte("Reporte 1", "Reporte para obtener los adolescetes por Tipo de delito"));
        listaReportes.add(Reporte("Reporte 2", "Reporte para obtener los adolescetes por la edad del adolescente"));
        listaReportes.add( Reporte("Reporte 3", "Reporte para obtener la edad del adolescente en una determinada fecha"));
        listaReportes.add(Reporte("Reporte 4", "Reporte para obtener los adolescetes por la nacionalidad del adolescente"));
        listaReportes.add(Reporte("Reporte 5", "Reporte para obtener los adolescetes por el tipo de medida"));
        listaReportes.add(Reporte("Reporte 6", "Reporte para obtener los adolescetes por fecha de ingreso al CAI"));
        listaReportes.add(Reporte("Reporte 7", "Reporte para obtener los adolescetes por su nivel de educación"));
        listaReportes.add( Reporte("Reporte 8", "Reporte para obtener los adolescetes por su edad y nivel de educación"));
        listaReportes.add(Reporte("Reporte 9", "Reporte para obtener los adolescetes por su lugar de residencia"));
        listaReportes.add(Reporte("Reporte 10", "Reporte para obtener los informes completos por cada usuario del sistema SNAI"));

    }

    private fun asignarListaReportesRecyclerView(listaReportes: List<Reporte>, rootView: View){

        val adaptador = ListaReportesAdaptador(listaReportes, this@ReportesFragment)
        val recyclerViewTaller =rootView.findViewById (R.id.rv_listado_reportes) as RecyclerView
        recyclerViewTaller.adapter=adaptador
        recyclerViewTaller.layoutManager=LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)

    }
}
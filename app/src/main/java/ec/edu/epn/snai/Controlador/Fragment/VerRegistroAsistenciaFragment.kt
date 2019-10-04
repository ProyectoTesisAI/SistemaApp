package ec.edu.epn.snai.Controlador.Fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import ec.edu.epn.snai.Controlador.Adaptador.ListaRegistroAsistenciaAdaptador
import ec.edu.epn.snai.Modelo.AsistenciaAdolescente
import ec.edu.epn.snai.R

class VerRegistroAsistenciaFragment: Fragment(){

    private var listaAdolescentesInfractores: List<AsistenciaAdolescente>?=null
    private lateinit var rootView: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(arguments!=null){

            listaAdolescentesInfractores=arguments?.getSerializable("listaAsistencia") as ArrayList<AsistenciaAdolescente>
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootView=inflater.inflate(R.layout.fragment_ver_registro_asistencia,container,false)

        if(listaAdolescentesInfractores != null){

            val adaptadorRegistroAsistencia =
                ListaRegistroAsistenciaAdaptador(listaAdolescentesInfractores)
            val recyclerViewItemsRegistroAsistencia=rootView.findViewById(R.id.rv_registro_asistencia) as RecyclerView
            recyclerViewItemsRegistroAsistencia.adapter=adaptadorRegistroAsistencia
            recyclerViewItemsRegistroAsistencia.layoutManager = LinearLayoutManager(context,LinearLayout.VERTICAL,false)

        }

        return rootView
    }

}
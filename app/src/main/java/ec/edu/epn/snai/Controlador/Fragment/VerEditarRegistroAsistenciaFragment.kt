package ec.edu.epn.snai.Controlador.Fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import ec.edu.epn.snai.Controlador.Adaptador.RegistroAsistenciaAdaptador
import ec.edu.epn.snai.Modelo.AsistenciaAdolescente
import ec.edu.epn.snai.Modelo.Informe
import ec.edu.epn.snai.R
import ec.edu.epn.snai.Servicios.ClienteApiRest
import ec.edu.epn.snai.Servicios.RegistroAsistenciaServicio
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class VerEditarRegistroAsistenciaFragment: Fragment(){

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

            val adaptadorRegistroAsistencia = RegistroAsistenciaAdaptador(listaAdolescentesInfractores)
            val recyclerViewItemsRegistroAsistencia=rootView.findViewById(R.id.rv_registro_asistencia) as RecyclerView
            recyclerViewItemsRegistroAsistencia.adapter=adaptadorRegistroAsistencia
            recyclerViewItemsRegistroAsistencia.layoutManager = LinearLayoutManager(context,LinearLayout.VERTICAL,false)

        }

        return rootView
    }

}
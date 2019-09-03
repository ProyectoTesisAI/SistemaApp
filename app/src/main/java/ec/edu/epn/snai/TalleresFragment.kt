package ec.edu.epn.snai

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ec.edu.epn.snai.Adaptador.TallerRecyclerViewAdaptador
import ec.edu.epn.snai.Modelo.Taller
import java.util.ArrayList

class TalleresFragment: Fragment(),TallerRecyclerViewAdaptador.TallerOnItemClickListener{

    private var adaptador: TallerRecyclerViewAdaptador? = null
    var listaTalleres= ArrayList<Taller>()
    private lateinit var taller: Taller

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView= inflater.inflate(R.layout.talleres_fragment,container,false)

        val t1=Taller("Psicologia",1)
        val t2=Taller("Fisica",2)

        listaTalleres.add (t1)
        listaTalleres.add(t2)

        adaptador = TallerRecyclerViewAdaptador(listaTalleres, this)
        val recyclerViewTaller =rootView.findViewById (R.id.rv_taller) as RecyclerView
        recyclerViewTaller.adapter=adaptador
        recyclerViewTaller.layoutManager=LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)

        val fabTaller:FloatingActionButton= rootView.findViewById(R.id.fab_agregar_taller)
        fabTaller.setOnClickListener {

            val intent = Intent(context,TallerAgegarActivity::class.java)
            startActivity(intent)
        }

        return rootView
    }

    override fun OnItemClick(posicion: Int) {

        val intent = Intent(context,TallerActivity::class.java)
        intent.putExtra("taller_seleccionado", listaTalleres[posicion])
        startActivity(intent)
    }

}
package ec.edu.epn.snai.Controlador.Fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ec.edu.epn.snai.Controlador.Adaptador.RegistroFotograficoAdaptador
import ec.edu.epn.snai.Modelo.Informe
import ec.edu.epn.snai.Modelo.RegistroFotografico
import ec.edu.epn.snai.R

class VerEditarFotografiasFragment : Fragment(){

    private var listaFotografias: ArrayList<RegistroFotografico>?=null

    private var adaptadorItemRegistroFotografico: RegistroFotograficoAdaptador?=null
    private lateinit var recyclerViewItemsFotografias: RecyclerView

    private lateinit var rootView: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if(arguments!=null){
            listaFotografias = arguments?.getSerializable("listaFotos") as ArrayList<RegistroFotografico>
        }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootView=inflater.inflate(R.layout.fragment_ver_editar_fotografias,container,false)

        if(listaFotografias?.size!! >0){
            adaptadorItemRegistroFotografico= RegistroFotograficoAdaptador(listaFotografias)
            recyclerViewItemsFotografias= rootView.findViewById<RecyclerView>(R.id.rv_ver_imagenes)
            recyclerViewItemsFotografias.adapter=adaptadorItemRegistroFotografico
            recyclerViewItemsFotografias.layoutManager= LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)

        }
        return rootView
    }


}


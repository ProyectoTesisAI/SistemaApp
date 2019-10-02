package ec.edu.epn.snai.Controlador.AdaptadorTabs

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import ec.edu.epn.snai.Controlador.Fragment.VerRegistroFotograficoFragment
import ec.edu.epn.snai.Controlador.Fragment.VerEditarInformeFragment
import ec.edu.epn.snai.Controlador.Fragment.VerEditarRegistroAsistenciaFragment
import ec.edu.epn.snai.Controlador.Fragment.VerRegistroAsistenciaFragment
import ec.edu.epn.snai.Modelo.AsistenciaAdolescente
import ec.edu.epn.snai.Modelo.Informe
import ec.edu.epn.snai.Modelo.ItemTaller
import java.util.ArrayList

class InformePagerAdaptador(fm: FragmentManager, tokenAux : String, informeAux: Informe,listaItemsActividadesAux: List<ItemTaller>, listaAsistenciaAux: List<AsistenciaAdolescente>) : FragmentPagerAdapter(fm) {

    val token=tokenAux
    val informe= informeAux
    val listaActividades=listaItemsActividadesAux
    val listaAsistencia=listaAsistenciaAux

    private fun obtenerBundle() : Bundle{
        val bundle = Bundle()

        bundle.putSerializable("token", token)
        bundle.putSerializable("informeSeleccionado", informe)
        bundle.putSerializable("listaActividades", ArrayList(listaActividades))
        bundle.putSerializable("listaAsistencia", ArrayList(listaAsistencia))
        return  bundle
    }

    override fun getItem(posicion: Int): Fragment {

        var fragmentAux=Fragment()
        when(posicion){

            0->{
                fragmentAux= VerEditarInformeFragment()
                fragmentAux.arguments=obtenerBundle()
            }
            1->{
                fragmentAux= VerRegistroFotograficoFragment()
                fragmentAux.arguments=obtenerBundle()
            }
            2->{

                fragmentAux= VerEditarRegistroAsistenciaFragment()
                fragmentAux.arguments=obtenerBundle()
            }

        }
        return fragmentAux
    }

    override fun getCount(): Int {
        return 3
    }

    override fun getPageTitle(posicion: Int): CharSequence? {
        when(posicion){

            0->{
                return "Informe"
            }
            1->{
                return "Evidencia"
            }
            2->{
                return "Asistencia"
            }
            else->{
                return null
            }
        }
    }
}
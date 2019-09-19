package ec.edu.epn.snai.Controlador.AdaptadorTabs

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import ec.edu.epn.snai.Controlador.Fragment.VerEditarFotografiasFragment
import ec.edu.epn.snai.Controlador.Fragment.VerEditarInformeFragment
import ec.edu.epn.snai.Controlador.Fragment.VerEditarRegistroAsistenciaFragment
import ec.edu.epn.snai.Controlador.Fragment.VerTallerFragment
import ec.edu.epn.snai.Modelo.Informe

class InformePagerAdaptador(fm: FragmentManager, tokenAux : String, informeAux: Informe) : FragmentPagerAdapter(fm) {

    val token=tokenAux
    val informe= informeAux

    private fun obtenerBundle() : Bundle{
        var bundle = Bundle()
        bundle.putSerializable("token", token)
        bundle.putSerializable("informeSeleccionado", informe)
        return  bundle
    }

    override fun getItem(posicion: Int): Fragment {

        var fragmentAux=Fragment()
        when(posicion){

            0->{
                fragmentAux= VerEditarRegistroAsistenciaFragment()
                fragmentAux.arguments=obtenerBundle()
            }
            1->{
                fragmentAux= VerEditarInformeFragment()
                fragmentAux.arguments=obtenerBundle()
            }
            2->{
                fragmentAux= VerEditarFotografiasFragment()
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
                return "Asistencia"
            }
            1->{
                return "Informe"
            }
            2->{
                return "Evidencia"
            }
            else->{
                return null
            }
        }
    }
}
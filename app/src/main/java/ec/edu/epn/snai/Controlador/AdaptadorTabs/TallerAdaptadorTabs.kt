package ec.edu.epn.snai.Controlador.AdaptadorTabs

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import ec.edu.epn.snai.Controlador.Fragment.VerTallerFragment
import ec.edu.epn.snai.Modelo.Taller

class TallerAdaptadorTabs(fm: FragmentManager, tokenAux : String, tallerAux: Taller) : FragmentPagerAdapter(fm) {

    val token=tokenAux
    val taller= tallerAux

    private fun obtenerBundle() : Bundle{
        var bundle = Bundle()
        bundle.putSerializable("token", token)
        bundle.putSerializable("taller_seleccionado", taller)
        return  bundle
    }

    override fun getItem(posicion: Int): Fragment {

        var fragmentAux=Fragment()
        when(posicion){

            0->{
                fragmentAux= VerTallerFragment()
                fragmentAux.arguments=obtenerBundle()

            }

        }
        return fragmentAux
    }

    override fun getCount(): Int {
        return 2
    }

    override fun getPageTitle(posicion: Int): CharSequence? {
        when(posicion){

            0->{
                return "Taller"
            }
            1->{
                return "Asistencia"
            }
            else->{
                return null
            }
        }
    }
}
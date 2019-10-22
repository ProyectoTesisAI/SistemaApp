package ec.edu.epn.snai.Controlador.AdaptadorTabs

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import ec.edu.epn.snai.Controlador.Fragment.*
import ec.edu.epn.snai.Modelo.ItemTaller
import ec.edu.epn.snai.Modelo.Taller

class Reporte6AdaptadorTabs(fm: FragmentManager, tokenAux : String) : FragmentPagerAdapter(fm) {

    val token=tokenAux

    private fun obtenerBundle() : Bundle{
        val bundle = Bundle()
        bundle.putSerializable("token", token)
        return  bundle
    }

    override fun getItem(posicion: Int): Fragment {

        var fragmentAux=Fragment()
        when(posicion){

            0->{
                fragmentAux= Reporte6CAIFragment()
                fragmentAux.arguments=obtenerBundle()

            }

        }
        return fragmentAux
    }

    override fun getCount(): Int {
        return 1
    }

    override fun getPageTitle(posicion: Int): CharSequence? {
        when(posicion){

            0->{
                return "CAI"
            }
            else->{
                return null
            }
        }
    }
}
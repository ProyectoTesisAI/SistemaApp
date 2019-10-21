package ec.edu.epn.snai.Controlador.AdaptadorTabs

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import ec.edu.epn.snai.Controlador.Fragment.Reporte1CAIFragment
import ec.edu.epn.snai.Controlador.Fragment.Reporte1UZDIFragment
import ec.edu.epn.snai.Controlador.Fragment.VerListadoAsistenciaFragment
import ec.edu.epn.snai.Controlador.Fragment.VerTallerFragment
import ec.edu.epn.snai.Modelo.ItemTaller
import ec.edu.epn.snai.Modelo.Taller

class Reporte1AdaptadorTabs(fm: FragmentManager, tokenAux : String) : FragmentPagerAdapter(fm) {

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
                fragmentAux= Reporte1UZDIFragment()
                fragmentAux.arguments=obtenerBundle()

            }
            1->{
                fragmentAux= Reporte1CAIFragment()
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
                return "UZDI"
            }
            1->{
                return "CAI"
            }
            else->{
                return null
            }
        }
    }
}
package ec.edu.epn.snai.Controlador.AdaptadorTabs

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import ec.edu.epn.snai.Controlador.Fragment.VerRegistroAsistenciaFragment
import ec.edu.epn.snai.Controlador.Fragment.VerTallerFragment
import ec.edu.epn.snai.Modelo.ItemTaller
import ec.edu.epn.snai.Modelo.Taller

class TallerAdaptadorTabs(fm: FragmentManager, tokenAux : String, tallerAux: Taller, itemsTallerAux: List<ItemTaller>?) : FragmentPagerAdapter(fm) {

    val token=tokenAux
    val taller= tallerAux
    val itemsTaller=itemsTallerAux

    private fun obtenerBundle() : Bundle{
        var bundle = Bundle()
        bundle.putSerializable("token", token)
        bundle.putSerializable("taller_seleccionado", taller)

        if(itemsTaller != null){
            bundle.putSerializable("items_taller_seleccionado",ArrayList(itemsTaller))
        }
        else{
            bundle.putSerializable("items_taller_seleccionado",null)
        }

        return  bundle
    }

    override fun getItem(posicion: Int): Fragment {

        var fragmentAux=Fragment()
        when(posicion){

            0->{
                fragmentAux= VerTallerFragment()
                fragmentAux.arguments=obtenerBundle()

            }
            1->{
                fragmentAux=VerRegistroAsistenciaFragment()
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
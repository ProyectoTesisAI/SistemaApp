package ec.edu.epn.snai.Controlador.AdaptadorTabs

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import ec.edu.epn.snai.Controlador.Fragment.VerEditarFotografiasFragment
import ec.edu.epn.snai.Controlador.Fragment.VerEditarInformeFragment
import ec.edu.epn.snai.Controlador.Fragment.VerEditarRegistroAsistenciaFragment
import ec.edu.epn.snai.Modelo.AsistenciaAdolescente
import ec.edu.epn.snai.Modelo.Informe
import ec.edu.epn.snai.Modelo.ItemTaller
import ec.edu.epn.snai.Modelo.RegistroFotografico
import java.util.ArrayList

class InformePagerAdaptador(fm: FragmentManager, tokenAux : String, informeAux: Informe, listaFotografias:  List<RegistroFotografico>, listaItemsActividades: List<ItemTaller>, listaAsistencia: List<AsistenciaAdolescente>) : FragmentPagerAdapter(fm) {

    val token=tokenAux
    val informe= informeAux
    val listaFotografias=listaFotografias
    val listaActividades=listaItemsActividades
    val listaAsistencia=listaAsistencia

    private fun obtenerInformeBundle() : Bundle{
        var bundle = Bundle()
        bundle.putSerializable("token", token)
        bundle.putSerializable("informeSeleccionado", informe)
        bundle.putSerializable("listaActividades", ArrayList(listaActividades))
        return  bundle
    }

    private fun obtenerRegistroAsistenciaBundle() : Bundle{
        var bundle = Bundle()
        bundle.putSerializable("token", token)
        bundle.putSerializable("informeSeleccionado", informe)
        bundle.putSerializable("listaAsistencia", ArrayList(listaAsistencia))
        return  bundle
    }

    private fun obtenerListaFotograficasBundle() : Bundle{
        var bundle = Bundle()
        bundle.putSerializable("token", token)
        bundle.putSerializable("informeSeleccionado", informe)
        bundle.putSerializable("listaFotos", ArrayList(listaFotografias))
        return  bundle
    }

    override fun getItem(posicion: Int): Fragment {

        var fragmentAux=Fragment()
        when(posicion){

            0->{
                fragmentAux= VerEditarRegistroAsistenciaFragment()
                fragmentAux.arguments=obtenerRegistroAsistenciaBundle()
            }
            1->{
                fragmentAux= VerEditarInformeFragment()
                fragmentAux.arguments=obtenerInformeBundle()
            }
            2->{
                fragmentAux= VerEditarFotografiasFragment()
                fragmentAux.arguments=obtenerListaFotograficasBundle()
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
package ec.edu.epn.snai.Controlador.Activity

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import ec.edu.epn.snai.Controlador.Fragment.VerEditarFotografiasFragment
import ec.edu.epn.snai.Controlador.Fragment.VerEditarInformeFragment
import ec.edu.epn.snai.Controlador.Fragment.VerEditarRegistroAsistenciaFragment
import ec.edu.epn.snai.Modelo.Informe
import ec.edu.epn.snai.R

class VerEditarInformeActivity : AppCompatActivity() {
    private var tabLayout: TabLayout? = null
    var viewPager: ViewPager? = null

    private lateinit var informeSeleccionado: Informe
    private lateinit var token: String

    var VerEditarInformeFragment:VerEditarRegistroAsistenciaFragment?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ver_editar_informe)

        val i = intent
        informeSeleccionado= i.getSerializableExtra("informeSeleccionado") as Informe
        token = i.getSerializableExtra("token") as String
        println("vereditarinforme: "+informeSeleccionado)
        println("vereditartoken"+token)

        viewPager = findViewById(R.id.viewpager) as ViewPager
        setPestana(viewPager!!)

        tabLayout = findViewById(R.id.tabs) as TabLayout
        tabLayout!!.setupWithViewPager(viewPager)

        //Agrego en el bundle la variable token
        var bundle = Bundle()
        bundle.putSerializable("token", token)

        //Seteo el bundle en el argumento de TalleresFragment, el cual contiene el token del usuario
        VerEditarInformeFragment=VerEditarRegistroAsistenciaFragment()
        VerEditarInformeFragment!!.arguments=bundle

        supportFragmentManager.beginTransaction().
            replace(R.id.frameLayout, VerEditarInformeFragment!!).commit()

    }

    private fun setPestana(viewPager: ViewPager) {
        val adaptador = ViewPagerAdaptador(supportFragmentManager,informeSeleccionado,token)
        adaptador.addFragment(VerEditarRegistroAsistenciaFragment(), "Registro de asistencia")
        adaptador.addFragment(VerEditarInformeFragment(), "Informe")
         adaptador.addFragment(VerEditarFotografiasFragment(), "Fotografías")
        viewPager.adapter = adaptador
    }

    internal inner class ViewPagerAdaptador(adaptador: FragmentManager,private val informe:Informe, private val token:String) : FragmentPagerAdapter(adaptador) {
        private val mFragmentList = ArrayList<Fragment>()
        private val mFragmentTitleList = ArrayList<String>()

        override fun getItem(position: Int): Fragment {
            return mFragmentList[position]
        }

        override fun getCount(): Int {
            return mFragmentList.size
        }

        fun addFragment(fragment: Fragment, title: String) {
            mFragmentList.add(fragment)
            mFragmentTitleList.add(title)
        }

        override fun getPageTitle(position: Int): CharSequence {
            return mFragmentTitleList[position]
        }
    }
}
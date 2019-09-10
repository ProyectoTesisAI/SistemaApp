package ec.edu.epn.snai.Controlador.Activity

import android.os.Bundle
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.view.MenuItem
import android.support.v4.widget.DrawerLayout
import android.support.design.widget.NavigationView
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.widget.Toast
import ec.edu.epn.snai.Controlador.Fragment.InformesFragment
import ec.edu.epn.snai.Controlador.Fragment.TalleresFragment
import ec.edu.epn.snai.Modelo.Usuario
import ec.edu.epn.snai.R

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var usuario:Usuario

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.drawer_navegacion)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        navView.setNavigationItemSelectedListener(this) //permite la navegación entre los items del menú

        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        usuario = intent.getSerializableExtra("usuario") as Usuario
        println("TOKEN EN MAIN"+usuario?.token)

        if(usuario!= null){

            itemsMenuRol(usuario,navView)

            if (savedInstanceState == null) {

                //Agrego en el bundle la variable token
                var bundle = Bundle()
                bundle.putSerializable("token", usuario?.token)

                //Seteo el bundle en el argumento de TalleresFragment, el cual contiene el token del usuario
                val talleresFragment=TalleresFragment()
                talleresFragment.arguments=bundle

                supportFragmentManager.beginTransaction().
                    replace(R.id.frameLayout, talleresFragment).commit()
                navView.setCheckedItem(R.id.nav_talleres_fragment)
            }
        }
        else{
            Toast.makeText(applicationContext, "Ha caducado la sesión del Usuario", Toast.LENGTH_LONG).show()
        }

    }

    override fun onBackPressed() {
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_talleres_fragment -> {

                //Agrego en el bundle la variable token
                var bundle = Bundle()
                bundle.putSerializable("token", usuario?.token)

                //Seteo el bundle en el argumento de TalleresFragment, el cual contiene el token del usuario
                val talleresFragment=TalleresFragment()
                talleresFragment.arguments=bundle

                cargarTalleresFragment(talleresFragment)
            }
            R.id.nav_informes_layout -> {
                //Agrego en el bundle la variable token
                var bundle = Bundle()
                bundle.putSerializable("token", usuario?.token)

                //Seteo el bundle en el argumento de InformesFragment, el cual contiene el token del usuario
                val informesFragment=InformesFragment()
                informesFragment.arguments=bundle
                cargarInformesFragment(informesFragment)
            }
        }
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun cargarTalleresFragment(fragment: TalleresFragment){
        val fm=supportFragmentManager.beginTransaction()
        fm.replace(R.id.frameLayout,fragment)
        fm.commit()
    }

    private fun cargarInformesFragment(fragment: InformesFragment){
        val fm=supportFragmentManager.beginTransaction()
        fm.replace(R.id.frameLayout,fragment)
        fm.commit()
    }


    private fun itemsMenuRol(usuario: Usuario,navView: NavigationView){

        val rolUsuario:String=usuario.idRolUsuarioCentro.idRol.rol
        val menu=navView.menu

        menu.findItem(R.id.menu_juridico).isVisible = false
        menu.findItem(R.id.menu_psicologo).isVisible=false
        menu.findItem(R.id.menu_inspector_educador).isVisible=false

        if (rolUsuario.contains("JURIDICO") || rolUsuario.contains("DIRECTOR") || rolUsuario.contains("LIDER") || rolUsuario.contains("COORDINADOR") || "ADMINISTRADOR".equals(rolUsuario) || "SUBDIRECTOR".equals(rolUsuario)) {
            menu.findItem(R.id.menu_juridico).isVisible = true
        }

        if (rolUsuario.contains("PSICOLOGO") || rolUsuario.contains("DIRECTOR") || rolUsuario.contains("LIDER") || rolUsuario.contains("COORDINADOR") || "ADMINISTRADOR".equals(rolUsuario) || "SUBDIRECTOR".equals(rolUsuario)) {
            menu.findItem(R.id.menu_psicologo).isVisible = true
        }

        if ("INSPECTOR EDUCADOR".equals(rolUsuario) || "COORDINADOR CAI".equals(rolUsuario) || "DIRECTOR TECNICO DE MEDIDAS PRIVATIVAS Y ATENCIÓN".equals(rolUsuario) || "ADMINISTRADOR".equals(rolUsuario) || "SUBDIRECTOR".equals(rolUsuario)) {
            menu.findItem(R.id.menu_inspector_educador).isVisible=true
        }


    }
}

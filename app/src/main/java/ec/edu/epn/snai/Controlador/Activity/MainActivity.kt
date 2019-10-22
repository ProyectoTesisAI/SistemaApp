package ec.edu.epn.snai.Controlador.Activity

import android.content.Intent
import android.os.Bundle
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.view.MenuItem
import android.support.v4.widget.DrawerLayout
import android.support.design.widget.NavigationView
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.widget.TextView
import ec.edu.epn.snai.Controlador.Fragment.*
import ec.edu.epn.snai.Modelo.Usuario
import ec.edu.epn.snai.R
import ec.edu.epn.snai.Utilidades.Constantes

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    //Me permite tratar a todos los atributos y métodos dentro del object como estáticos
    companion object {
        lateinit var usuario:Usuario
    }
    private var tipoTaller:String?=null
    private var tipoInforme:String?=null

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
        this.tipoTaller = intent.getSerializableExtra("tipoTaller") as String?
        this.tipoInforme = intent.getSerializableExtra("tipoInforme") as String?

        asignarDatosUsuarioHeader(navView)
        itemsMenuRol(usuario,navView)

        if (savedInstanceState == null) {

            if(tipoInforme != null){
                asignarTipoInformeSeleccionado()
            }
            else{
                asignarTipoTallerSeleccionado()
            }


        }


    }

    private fun asignarDatosUsuarioHeader(navView: NavigationView){
        val header=navView.getHeaderView(0)

        val txtNombre=header.findViewById<TextView>(R.id.txtUsuario)
        val txtRol=header.findViewById<TextView>(R.id.txtRol)

        val usuarioAux="${usuario.nombres.toUpperCase()} ${usuario.apellidos.toUpperCase()}"
        val rol=usuario.idRolUsuarioCentro.idRol.rol

        txtNombre.setText(usuarioAux)
        txtRol.setText(rol)
    }

    private fun asignarTipoTallerSeleccionado(){

        if(tipoTaller == null){

            val rol=usuario.idRolUsuarioCentro.idRol.rol

            if( rol.equals(Constantes.ROL_ADMINISTRADOR) || rol.equals(Constantes.ROL_SUBDIRECTOR) || rol.equals(Constantes.ROL_LIDER_UZDI) || rol.equals(
                    Constantes.ROL_COORDINADOR_CAI)  || rol.contains("DIRECTOR") || rol.contains("PSICOLOGO") ){
                tipoTaller=Constantes.TIPO_TALLER_PSICOLOGIA
            }
            else if(rol.contains("JURIDICO")){
                tipoTaller=Constantes.TIPO_TALLER_JURIDICO
            }
            else if(rol.contains("INSPECTOR EDUCADOR")){
                tipoTaller=Constantes.TIPO_TALLER_INSPECTOR_EDUCADOR
            }

        }

        when(tipoTaller){
            Constantes.TIPO_TALLER_PSICOLOGIA -> {
                abrirTalleresFragment(Constantes.TIPO_TALLER_PSICOLOGIA)
            }
            Constantes.TIPO_TALLER_JURIDICO -> {
                abrirTalleresFragment(Constantes.TIPO_TALLER_JURIDICO)
            }
            Constantes.TIPO_TALLER_INSPECTOR_EDUCADOR -> {
                abrirTalleresFragment(Constantes.TIPO_TALLER_INSPECTOR_EDUCADOR)
            }
        }
    }

    private fun asignarTipoInformeSeleccionado(){

        when(tipoInforme) {

            Constantes.TIPO_TALLER_PSICOLOGIA -> {
                abrirInformesFragment(Constantes.TIPO_TALLER_PSICOLOGIA)
            }
            Constantes.TIPO_TALLER_JURIDICO -> {
                abrirInformesFragment(Constantes.TIPO_TALLER_JURIDICO)
            }
            Constantes.TIPO_TALLER_INSPECTOR_EDUCADOR -> {
                abrirInformesFragment(Constantes.TIPO_TALLER_INSPECTOR_EDUCADOR)
            }
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
        when (item.itemId) {
            R.id.nav_cerrar_sesion ->{

                val intent = Intent(this@MainActivity, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
        return true
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        drawerLayout.closeDrawer(GravityCompat.START)


        when(item.itemId) {

            /********** CREAR TALLERES ***********/
            R.id.nav_crear_taller_psicologia->{
                abrirCrearTallerActivity(Constantes.TIPO_TALLER_PSICOLOGIA)
            }
            R.id.nav_crear_taller_juridico->{
                abrirCrearTallerActivity(Constantes.TIPO_TALLER_JURIDICO)
            }
            R.id.nav_crear_taller_inspector_educador->{
                abrirCrearTallerActivity(Constantes.TIPO_TALLER_INSPECTOR_EDUCADOR)

            }

            /********** GESTIONAR  TALLERES ***********/
            R.id.nav_gestion_taller_psicologia -> {
                abrirTalleresFragment(Constantes.TIPO_TALLER_PSICOLOGIA)
            }
            R.id.nav_gestion_taller_juridico -> {
                abrirTalleresFragment(Constantes.TIPO_TALLER_JURIDICO)
            }
            R.id.nav_gestion_taller_inspector_educador -> {
                abrirTalleresFragment(Constantes.TIPO_TALLER_INSPECTOR_EDUCADOR)
            }


            /********** GESTIONAR  INFORMES ***********/
            R.id.nav_gestion_informe_psicologia -> {
                abrirInformesFragment(Constantes.TIPO_TALLER_PSICOLOGIA)
            }
            R.id.nav_gestion_informe_juridico->{
                abrirInformesFragment(Constantes.TIPO_TALLER_JURIDICO)
            }
            R.id.nav_gestion_informe_inspector_educador->{
                abrirInformesFragment(Constantes.TIPO_TALLER_INSPECTOR_EDUCADOR)
            }

            /********** GESTION USUARIOS ***********/
            R.id.nav_crear_usuario -> {
                abrirCrearUsuario()
            }

            R.id.nav_activar_usuario -> {
                abrirGestionUsuariosActivados()
            }

            R.id.nav_desactivar_usuario -> {
                abrirGestionUsuariosDesactivados()
            }

            /********** GESTION REPORTS ***********/
            R.id.nav_gestion_reportes -> {
                abrirReportesFragment()
            }
        }
        return true


    }

    private fun abrirCrearTallerActivity(tipoTallerSeleccionado:String){

        val tituloToolbar="CREAR TALLER $tipoTallerSeleccionado"

        //Agrego en el bundle la variable token
        val intent= Intent(this@MainActivity, CrearTallerActivity::class.java)
        intent.putExtra("token", usuario.token)
        intent.putExtra("usuario",usuario)
        intent.putExtra("tipoTaller", tipoTallerSeleccionado)
        intent.putExtra("tituloToolbar", tituloToolbar)

        startActivity(intent)
    }

    private fun abrirTalleresFragment(tipoTallerSeleccionado:String){

        val tituloToolbar="TALLERES SIN INFORME - $tipoTallerSeleccionado"
        getSupportActionBar()?.setTitle(tituloToolbar)

        //Agrego en el bundle la variable token
        val bundle = Bundle()
        bundle.putSerializable("usuario",usuario)
        bundle.putSerializable("tipoTaller", tipoTallerSeleccionado)

        //Seteo el bundle en el argumento de TalleresFragment, el cual contiene el token del usuario
        val talleresFragment=TalleresFragment()
        talleresFragment.arguments=bundle

        cargarTalleresFragment(talleresFragment)
    }

    private fun abrirInformesFragment(tipoTallerSeleccionado:String){

       val tituloToolbar= "INFORMES - $tipoTallerSeleccionado"
        getSupportActionBar()?.setTitle(tituloToolbar)
        //Agrego en el bundle la variable token
        val bundle = Bundle()
        bundle.putSerializable("usuario",usuario)
        bundle.putSerializable("tipoTaller", tipoTallerSeleccionado)

        //Seteo el bundle en el argumento de InformesFragment, el cual contiene el token del usuario
        val informesFragment=InformesFragment()
        informesFragment.arguments=bundle
        cargarInformesFragment(informesFragment)
    }

    private fun abrirGestionUsuariosActivados(){

        val tituloToolbar="USUARIOS ACTIVOS"
        getSupportActionBar()?.setTitle(tituloToolbar)

        //Agrego en el bundle la variable token
        val bundle = Bundle()
        bundle.putSerializable("usuario",usuario)

        val usuariosFragment=UsuariosActivosFragment()
        usuariosFragment.arguments=bundle

        cargarUsuariosActivosFragment(usuariosFragment)
    }

    private fun abrirCrearUsuario(){

        val intent = Intent(applicationContext, CrearUsuarioActivity::class.java)
        intent.putExtra("usuario", usuario)
        startActivity(intent)
    }

    private fun abrirGestionUsuariosDesactivados(){

        val tituloToolbar="USUARIOS DESACTIVADOS"
        getSupportActionBar()?.setTitle(tituloToolbar)

        //Agrego en el bundle la variable token
        val bundle = Bundle()
        bundle.putSerializable("usuario",usuario)

        val usuariosFragment=UsuariosDesactivadosFragment()
        usuariosFragment.arguments=bundle

        cargarUsuariosDesactivadosFragment(usuariosFragment)
    }

    private fun abrirReportesFragment(){

        val tituloToolbar="REPORTES"
        getSupportActionBar()?.setTitle(tituloToolbar)

        //Agrego en el bundle la variable token
        val bundle = Bundle()
        bundle.putSerializable("usuario",usuario)

        //Seteo el bundle en el argumento de TalleresFragment, el cual contiene el token del usuario
        val reportesFragment=ReportesFragment()
        reportesFragment.arguments=bundle

        cargarReportesFragment(reportesFragment)
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

    private fun cargarUsuariosActivosFragment(fragment: UsuariosActivosFragment){
        val fm=supportFragmentManager.beginTransaction()
        fm.replace(R.id.frameLayout,fragment)
        fm.commit()
    }

    private fun cargarUsuariosDesactivadosFragment(fragment: UsuariosDesactivadosFragment){
        val fm=supportFragmentManager.beginTransaction()
        fm.replace(R.id.frameLayout,fragment)
        fm.commit()
    }

    private fun cargarReportesFragment(fragment: ReportesFragment){
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
        menu.findItem(R.id.menu_administracion_usuarios).isVisible=false
        menu.findItem(R.id.menu_reportes).isVisible=false

        if (rolUsuario.contains("JURIDICO") || rolUsuario.contains("DIRECTOR") || rolUsuario.contains("LIDER") || rolUsuario.contains("COORDINADOR") || Constantes.ROL_ADMINISTRADOR.equals(rolUsuario) || Constantes.ROL_SUBDIRECTOR.equals(rolUsuario)) {
            menu.findItem(R.id.menu_juridico).isVisible = true
        }

        if (rolUsuario.contains("PSICOLOGO") || rolUsuario.contains("DIRECTOR") || rolUsuario.contains("LIDER") || rolUsuario.contains("COORDINADOR") || Constantes.ROL_ADMINISTRADOR.equals(rolUsuario) || Constantes.ROL_SUBDIRECTOR.equals(rolUsuario)) {
            menu.findItem(R.id.menu_psicologo).isVisible = true
        }

        if (Constantes.ROL_INSPECTOR_EDUCADOR.equals(rolUsuario) || Constantes.ROL_COORDINADOR_CAI.equals(rolUsuario) || Constantes.ROL_DIRECTOR_CAI.equals(rolUsuario) || Constantes.ROL_ADMINISTRADOR.equals(rolUsuario) || Constantes.ROL_SUBDIRECTOR.equals(rolUsuario)) {
            menu.findItem(R.id.menu_inspector_educador).isVisible=true
        }


    }
}

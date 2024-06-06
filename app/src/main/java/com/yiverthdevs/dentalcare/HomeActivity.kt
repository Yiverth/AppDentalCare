package com.yiverthdevs.dentalcare
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import org.koin.androidx.fragment.android.replace

class HomeActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

     // Se definen las variables que se van a usar
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var fragmentManager: FragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        /* configurar el color de la barra de estado */

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.decorView.systemUiVisibility=View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            window.statusBarColor=ContextCompat.getColor(this,R.color.white)
        }

        /* configurar el color de la barra de navegacion */

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            window.decorView.systemUiVisibility=window.decorView.systemUiVisibility or View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
            window.navigationBarColor=ContextCompat.getColor(this,R.color.white)
        }

        /* configuracíon del toolbar*/

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        // Ocultar el título dentalcare que trae por defecto la barra toolbar
        supportActionBar?.setDisplayShowTitleEnabled(false)

        // Se implementa la navegacion del icono usar dentro del toolbar
        val toolbarUser = findViewById<ImageView>(R.id.toolbar_user)
        toolbarUser.setOnClickListener {
            val userFragment = UserFragment()
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmnet_container, userFragment)
                .addToBackStack(null) // Esto permite volver al fragment anterior con boton de retroceso
                .commit()
        }
        // Se instancia el boton navigationView (Se inicia)
        bottomNavigationView = findViewById(R.id.bottom_navigation)

        // Se inicia drawerLayout en el menu lateral
        drawerLayout = findViewById(R.id.drawer_layout)
        val navigationView: NavigationView = findViewById(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)

        // Se inicia el drawerLayout y se cierra respectivamente
        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar, R.string.open_nav, R.string.close_nav)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        // Cambiar el color del ícono del menú a negro
        toggle.drawerArrowDrawable.color = ContextCompat.getColor(this, R.color.green)

        // Se devuelve el drawerLayout al fragmentHome con el boton de retroceso
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmnet_container, HomeFragment())
                .commit()
            navigationView.setCheckedItem(R.id.nav_home)
        }
            // Se configura la navegancion del boton navigationView en la barra inferior
            bottomNavigationView.setOnItemSelectedListener { MenuItem ->
                when (MenuItem.itemId) {
                    R.id.button_nav_home -> {
                        fragmentManager = supportFragmentManager
                        openFragment(HomeFragment())
                    }

                    R.id.button_nav_location -> {
                        fragmentManager = supportFragmentManager
                        openFragment(UbicationFragment())
                    }

                    R.id.button_nav_assistan -> {
                        fragmentManager = supportFragmentManager
                        openFragment(Asistente_virtual_fragment())

                    }
                    R.id.button_nav_phone -> {
                        fragmentManager = supportFragmentManager
                        openFragment(ContactoFragment())
                    }
                }
                true // Devuelve true para indicar que el evento se ha consumido
            }
        }


// Se configura la navegacion del drawerLayout y toolbar (La barra superior)
override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_home -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragmnet_container, HomeFragment())
                    .commit()
            }
            R.id.nav_usuario -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragmnet_container, UserFragment())
                    .commit()
            }
            R.id.nav_cita -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragmnet_container, CitaFragment())
                    .commit()
            }
            R.id.nav_historialClinico -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragmnet_container, HistorialClinicoFragment())
                    .commit()
            }
            R.id.nav_pagos -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragmnet_container, PagosFragment())
                    .commit()
            }
            R.id.nav_especialistas -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragmnet_container, EspecialistasFragment())
                    .commit()
            }
            R.id.nav_reportes -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragmnet_container, ReportesFragment())
                    .commit()
            }
            R.id.nav_cancelarCitas -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragmnet_container, CancelarCitaFragment())
                    .commit()
            }
            R.id.nav_logout -> {
                Toast.makeText(this, "Logout", Toast.LENGTH_SHORT).show()
            }
        }

        drawerLayout.closeDrawer(GravityCompat.START)
        return true // Devuelve true para indicar que el evento se ha consumido
    }

    override fun onBackPressed() { // Se instancia el evento de abrir y cerrar el drawerLayout
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)

        }else {
            super.onBackPressed()
        }
    }
    // Remplaza el contenido del contenedor de fragmentos con un nuevo fragmento
    private fun openFragment(fragment: androidx.fragment.app.Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragmnet_container, fragment)
        transaction.commit()
    }
 }

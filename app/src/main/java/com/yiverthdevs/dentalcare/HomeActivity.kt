package com.yiverthdevs.dentalcare
import PagosFragment
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.os.Vibrator
import android.os.VibrationEffect
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.FragmentManager
import com.bumptech.glide.Glide
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject


class HomeActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

     // Se definen las variables que se van a usar
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var fragmentManager: FragmentManager
    private lateinit var vibrator: Vibrator
    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        // Se llama a la funcion onBottomNavigation
        onBottomNavigation()

        // Se instancia la propiedad de vibrator
        vibrator = getSystemService(android.content.Context.VIBRATOR_SERVICE) as Vibrator

        /* configurar el color de la barra de estado */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            window.statusBarColor = ContextCompat.getColor(this, R.color.white)
        }

        /* configurar el color de la barra de navegacion */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            window.decorView.systemUiVisibility =
                window.decorView.systemUiVisibility or View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
            window.navigationBarColor = ContextCompat.getColor(this, R.color.white)
        }

        /* configuracíon del toolbar*/
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        // Ocultar el título dentalcare que trae por defecto la barra toolbar
        supportActionBar?.setDisplayShowTitleEnabled(false)

        // Se implementa la navegacion del icono usar dentro del toolbar
        val toolbarUser = findViewById<ImageView>(R.id.toolbar_user)
        toolbarUser.setOnClickListener {

            // Vibrar al precionar el boton
            vibrate()
            openFragment(UserFragment())
        }

        // Se instancia el boton navigationView (Se inicia)
        bottomNavigationView = findViewById(R.id.bottom_navigation)

        // Se inicia drawerLayout en el menu lateral
        drawerLayout = findViewById(R.id.drawer_layout)
        val navigationView: NavigationView = findViewById(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)

        // Se inicia el drawerLayout y se cierra respectivamente
        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar, R.string.open_nav, R.string.close_nav
        )
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
        // Recuperar la informacion del usuario
        auth.currentUser?.let { user ->
            getUserData(user.uid)
        }
    }

        // Se configura la navegancion del boton navigationView en la barra inferior
        private fun onBottomNavigation() {
            bottomNavigationView = findViewById(R.id.bottom_navigation)
            bottomNavigationView.setOnItemSelectedListener { menuIten->

                // Vibrar al precionar el boton
                vibrate()

                when(menuIten.itemId){
                    R.id.button_nav_home->openFragment(HomeFragment())
                    R.id.button_nav_location->openFragment(UbicationFragment())
                    R.id.button_nav_assistan->openFragment(Asistente_virtual_fragment())
                    R.id.button_nav_phone->openFragment(ContactoFragment())
                }
                true // Devuelve true para indicar que el evento se ha consumido
            }
        }

// Se configura la navegacion del drawerLayout y toolbar (La barra superior)
override fun onNavigationItemSelected(item: MenuItem): Boolean {

    vibrate()
    when (item.itemId){
        R.id.nav_home->openFragment(HomeFragment())
        R.id.nav_usuario->openFragment(UserFragment())
        R.id.nav_cita->openFragment(CitaFragment())
        R.id.nav_historialClinico->openFragment(HistorialClinicoFragment())
        R.id.nav_pagos->openFragment(PagosFragment())
        R.id.nav_especialistas->openFragment(EspecialistasFragment())
        R.id.nav_reportes->openFragment(ReportesFragment())
        R.id.nav_cancelarCitas->openFragment(CancelarCitaFragment())
        R.id.nav_logout -> {

            // Eliminar el estado de la sesión
            val sharedPref = getSharedPreferences("DentalCare", Context.MODE_PRIVATE)
            with(sharedPref.edit()) {
                putBoolean("Sesion iniciada", false)
                apply()
            }

            // Redirigir al LoginActivity
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish() // Para que no se pueda volver a la pantalla de home con el botón "atrás"
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
        transaction.replace(R.id.fragmnet_container, fragment).addToBackStack(null)
        transaction.commit()
    }
        private fun vibrate () {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                vibrator.vibrate(VibrationEffect.createOneShot(50,VibrationEffect.DEFAULT_AMPLITUDE))
            }else {
                vibrator.vibrate(50)
            }
        }
    //Funfion que permite obtener la informacion del usuario
    private fun getUserData (userId: String){
        db.collection("users").document(userId).get()
            .addOnSuccessListener { document ->
                if (document != null){
                    val user = document.toObject(UserData::class.java)
                    if (user != null){
                        Log.d("HomeActivity","UserData retriebe:: $user")

                        // Actualizar la UI con la informacion del usuario
                        updateHeader(user)
                        updateImageTooblar(user)
                    }
                }else {
                    Log.d("HomeActivity","No succes document")
                }
            }
            .addOnFailureListener { e ->
                Log.d("HomeActivity","Error geting user data", e)
            }

    }
    private fun updateHeader(user: UserData){
        val navigationView: NavigationView = findViewById(R.id.nav_view)
        val headerView = navigationView.getHeaderView(0)
        val headerName = headerView.findViewById<TextView>(R.id.header_name)
        val headerEmail = headerView.findViewById<TextView>(R.id.header_email)
        val headerImage = headerView.findViewById<ImageView>(R.id.header_image)
        // Obtener el correo electronico desde firebaseAuth
        val userEmail = FirebaseAuth.getInstance().currentUser?.email

        headerName.text = user.name
        headerEmail.text = userEmail ?: "Email no disponible"
        Log.d("updateHeader", "Url: ${user.photoUrl}")

        //Cargar la imagen con navhider usando glide
        if (!user.photoUrl.isNullOrEmpty()){
            Glide.with(this)
                .load(user.photoUrl)
                .placeholder(R.drawable.nav_user) // Imagen por defecto mietras se carga
                .error(R.drawable.nav_user) // Imagen por defecto en caso de error
                .circleCrop() // Recorta en forma circular
                .into(headerImage)
        }else { // Si no hay url usar imagen por defecto
            headerImage.setImageResource(R.drawable.nav_user)
        }
    }
    // Cargar la imagen en el tooblar_user usando glide
    private fun updateImageTooblar (user: UserData){
        val toolbarImage: ImageView = findViewById(R.id.toolbar_user)

        if (!user.photoUrl.isNullOrEmpty()){
            Glide.with(this)
                .load(user.photoUrl)
                .placeholder(R.drawable.nav_user)
                .error(R.drawable.nav_user)
                .circleCrop()
                .into(toolbarImage)
        }else {
            toolbarImage.setImageResource(R.drawable.nav_user)
        }
    }

}
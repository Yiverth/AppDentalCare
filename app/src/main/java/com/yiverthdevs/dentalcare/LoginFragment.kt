package com.yiverthdevs.dentalcare

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.yiverthdevs.dentalcare.databinding.FragmentLoginBinding
import com.google.firebase.auth.FirebaseAuth
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.firestore.toObject

class LoginFragment : Fragment(R.layout.fragment_login) {
    // Instancia de firebase fireStore para acceder a la base de datos
    private val db = FirebaseFirestore.getInstance()
    private val TAG = "LoginFragment"
    private lateinit var binding: FragmentLoginBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        val view = binding.root

        auth = FirebaseAuth.getInstance()

        binding.loginFragmentSignupButton.setOnClickListener { loginUser() }

        //configurar el text view para ir al fragment singUp
        val textsingUp = view.findViewById<TextView>(R.id.login_fragment_text_signup)
        textsingUp.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_singUpFragment)
        }
        //configurar el text view para ir al fragment forget
        val textForget = view.findViewById<TextView>(R.id.login_fragment_label_text_password)
        textForget.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_forgetFragment)
        }
        return view
    }
    // Funcion que permite al usuario iniciar sesíon
    private fun loginUser() {
        val email = binding.loginFragmentEmail.text.toString()
        val password = binding.loginFragmentPassword.text.toString()

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(
                requireContext(),
                "Por favor completa todos los campos",
                Toast.LENGTH_SHORT
            ).show()
            Log.e(TAG,"Por favor completa todos los campos")
            return
        }

        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {

                    val user = auth.currentUser
                    if (user != null) {
                        // Guardar el estado de la sesion
                        val sharePref = requireActivity().getSharedPreferences("DentalCare", Context.MODE_PRIVATE)
                        with(sharePref.edit()){
                            Toast.makeText(requireContext(),"Bienvenido a DentalCare",Toast.LENGTH_SHORT).show()
                            Log.i(TAG, "Usuario ingresa correctamente")
                            putBoolean("Sesion iniciada", true)
                            apply()
                        }
                        // Guardar o actualizar los datos del usuario
                        saveUserData(user)

                        //Inicio de sesión exitoso, navegar al home activiti
                        findNavController().navigate(R.id.action_loginFragment_to_homeActivity)
                    }
                } else {

                    //Error al iniciar sesión
                    val exception = task.exception
                    if (exception is FirebaseAuthInvalidCredentialsException) {
                        //Correo electronico no valido.
                        Toast.makeText(requireContext(), "Correo electrónico o contraseña incorrecta", Toast.LENGTH_SHORT).show()
                        Log.e(TAG,"Correo electrónico o contraseña incorrecta")
                    }
                }
            }

    }
    // Funcion que permite guardar los datos del usuario desde firebase
    private fun saveUserData (user:FirebaseUser) {
        val userId = user.uid // Variable del id
        val userDocRef = db.collection("users").document(userId)// Variable de referencia

        // Obtener los datos del usuario si ya existen

        userDocRef.get().addOnSuccessListener { document ->
            if (document.exists()) {
                // Datos existentes del usuario
                val existinData = document.toObject(UserData::class.java)
                val updateData = UserData(
                    userId = userId,
                    name = existinData?.name ?: "",
                    number = existinData?.number?: "",
                    gender = existinData?.gender?: "",
                    photoUrl = existinData?.photoUrl?: ""
                )
                // Guardar los datos actualizados
                userDocRef.set(updateData)
                    .addOnSuccessListener {
                        Log.d(TAG,"Datos de usuario guardado correctamente")
                    }
                    .addOnFailureListener { e ->
                        Log.d(TAG, "Error al guardar datos", e)
                    }
            } else {
                // Si no existen datos, crear uno nuevo
                val newData = UserData(
                    userId = userId,
                    name = "",
                    number = "",
                    gender = "",
                    photoUrl = ""
                )
                userDocRef.set(newData)
                    .addOnSuccessListener {
                        Log.d(TAG,"Datos de usuario guardado correctamente")
                    }
                    .addOnFailureListener { e ->
                        Log.d(TAG, "Error al guardar datos", e)
                    }
            }
        }.addOnFailureListener { e ->
            Log.d(TAG, "Error al obtener datos del usuario", e)
        }

    }
}
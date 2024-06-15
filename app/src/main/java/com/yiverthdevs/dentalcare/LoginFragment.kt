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

class LoginFragment : Fragment(R.layout.fragment_login) {
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

    private fun loginUser() {
        val email = binding.loginFragmentEmail.text.toString()
        val password = binding.loginFragmentPassword.text.toString()

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(
                requireContext(),
                "Por favor completa todos los campos",
                Toast.LENGTH_SHORT
            ).show()
            return
        }

        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {

                    // Guardar el estado de la sesion
                    val sharePref = requireActivity().getSharedPreferences("DentalCare", Context.MODE_PRIVATE)
                    with(sharePref.edit()){
                        putBoolean("Sesion iniciada", true)
                        apply()
                    }

                    //Inicio de sesión exitoso, navegar al home activiti
                    findNavController().navigate(R.id.action_loginFragment_to_homeActivity)
                } else {

                    //Error al iniciar sesión
                    val email = task.exception
                    if (email is FirebaseAuthInvalidCredentialsException) {
                        //Correo electronico no valido.
                        Toast.makeText(requireContext(), "Correo electrónico o contraseña incorrecta", Toast.LENGTH_SHORT).show()
                    }
                }
            }

    }
}
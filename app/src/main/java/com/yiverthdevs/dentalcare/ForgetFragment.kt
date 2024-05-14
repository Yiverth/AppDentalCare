package com.yiverthdevs.dentalcare

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import android.util.Log
import android.widget.Toast
import com.yiverthdevs.dentalcare.databinding.FragmentForgetBinding
import com.yiverthdevs.dentalcare.databinding.FragmentSignupBinding

class ForgetFragment : Fragment(R.layout.fragment_forget) {

    private val TAG = "ForgetFragment"
    private lateinit var binding: FragmentForgetBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentForgetBinding.inflate(inflater, container, false)
        val view = binding.root

        auth = FirebaseAuth.getInstance()

        binding.forgetFragmentSignupButton.setOnClickListener { (sendPasswordResetEmail()) }

        //configurar el text view para ir al fragment singUp
        val textSingUp = view.findViewById<TextView>(R.id.forget_fragment_text_signup)
        textSingUp.setOnClickListener {
            findNavController().navigate(R.id.action_forgetFragment_to_singUpFragment)
        }
        return view
    }

    private fun sendPasswordResetEmail (){

        val email = binding.forgetFragmentEmail.text.toString()

        if (email.isEmpty()) {
            Toast.makeText(requireContext(), "Por favor ingresa tu correo electronico", Toast.LENGTH_SHORT).show()
            return
        }

        FirebaseAuth.getInstance().sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(requireContext(), "Se ha enviado un correo electrónico para restablecer tu contraseña",Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.action_forgetFragment_to_loginFragment)
                } else {
                    Toast.makeText(requireContext(), "Error al enviar el correo electrónico de restablecimiento de contraseña. Por favor intenta de nuevo",Toast.LENGTH_SHORT).show()
                    Log.e(TAG, "Error al enviar el correo electrónico de restablecimiento de contraseña", task.exception)

                }
            }

    }

}
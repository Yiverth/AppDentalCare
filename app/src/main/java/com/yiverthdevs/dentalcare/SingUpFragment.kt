package com.yiverthdevs.dentalcare

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.addCallback
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.firestore.FirebaseFirestore
import com.yiverthdevs.dentalcare.databinding.FragmentSignupBinding

class SingUpFragment : Fragment(R.layout.fragment_signup) {
    private val TAG = "Registro de usuario"
    private lateinit var binding: FragmentSignupBinding
    private lateinit var auth: FirebaseAuth

    private var gender: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignupBinding.inflate(inflater, container, false)
        val view = binding.root

        auth = FirebaseAuth.getInstance()

        binding.apply {
            // Se inicializan las vistas del layout y asignar listeners si es necesario
            signupFragmentRadioGroup.setOnCheckedChangeListener { _, checkedId ->
                gender = view.findViewById<RadioButton>(checkedId).text.toString()
                Log.i("Genero", gender)
            }
            binding.signupFragmentSignupButton.setOnClickListener { createUser() }
        }
        // configurar el text view para ir al fragment login
        val textLogin = view.findViewById<TextView>(R.id.signup_fragment_text_login)
        textLogin.setOnClickListener {
            findNavController().navigate(R.id.action_singUpFragment_to_loginFragment)
        }

        // configurar el botón de retroceso para salir de la app
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            requireActivity().finish()
        }

        // Validación en tiempo real del formato del email
        binding.signupFragmentEmail.addTextChangedListener {
            val email = binding.signupFragmentEmail.text.toString().trim()

            if (email.isNotEmpty()) {
                if (!isValidEmail(email)) {
                    binding.signupFragmentEmailLayout.setBoxStrokeColor(
                        ContextCompat.getColor(requireContext(), android.R.color.holo_red_light)
                    )
                    binding.signupFragmentEmail.error = "Correo inválido. Ingrese un correo válido."
                    Log.e(TAG,"Correo inválido. Ingrese un correo válido.")
                } else {
                    binding.signupFragmentEmailLayout.setBoxStrokeColor(
                        ContextCompat.getColor(requireContext(), R.color.green)
                    )
                    binding.signupFragmentEmail.error = null
                }
            } else {
                binding.signupFragmentEmail.error = null
                binding.signupFragmentEmailLayout.setBoxStrokeColor(
                    ContextCompat.getColor(requireContext(), R.color.green)
                )
            }
        }
        return view
    }

    // Función para validar el formato del email
    private fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    // Función para validar la seguridad de la contraseña
    private fun isValidPassword(password: String): Boolean {
        return password.length >= 8 && password.any { it.isDigit() } && password.any { it.isLetter() }
    }

    // Función para validar el número de teléfono
    private fun isValidPhoneNumber(number: String): Boolean {
        return number.length == 10 && number.all { it.isDigit() }
    }

    private fun createUser() {
        val name = binding.signupFragmentName.text.toString().trim()
        val number = binding.signupFragmentNumber.text.toString().trim()
        val email = binding.signupFragmentEmail.text.toString().trim()
        val password = binding.signupFragmentPassword.text.toString().trim()

        // Validar campos de nombre, número, email, contraseña y género
        if (name.isEmpty()) {
            binding.signupFragmentName.error = "El nombre es obligatorio."
            Log.e(TAG,"El nombre es obligatorio.")
            return
        }

        if (number.isEmpty()) {
            binding.signupFragmentNumber.error = "El número de teléfono es obligatorio."
            Log.e(TAG,"El número de teléfono es obligatorio.")
            return
        }

        // Validar que el número de teléfono tenga 10 dígitos
        if (!isValidPhoneNumber(number)) {
            binding.signupFragmentNumber.error = "El número de teléfono debe tener 10 dígitos."
            Log.e(TAG,"El número de teléfono debe tener 10 dígitos.")
            return
        }

        if (gender.isEmpty()) {
            Toast.makeText(requireContext(), "Por favor selecciona un género.", Toast.LENGTH_SHORT).show()
            Log.e(TAG,"Por favor selecciona un género.")
            return
        }

        if (email.isEmpty()) {
            binding.signupFragmentEmail.error = "El correo es obligatorio."
            Log.e(TAG,"El correo es obligatorio.")
            return
        }

        if (!isValidEmail(email)) {
            binding.signupFragmentEmail.error = "Correo inválido. Ingrese un correo válido."
            Log.e(TAG,"Correo inválido. Ingrese un correo válido.")
            return
        }

        if (password.isEmpty()) {
            binding.signupFragmentPassword.error = "La contraseña es obligatoria."
            Log.e(TAG,"La contraseña es obligatoria.")
            return
        }

        if (!isValidPassword(password)) {
            binding.signupFragmentPassword.error = "La contraseña debe tener al menos 8 caracteres y contener letras y números."
            Log.e(TAG,"La contraseña debe tener al menos 8 caracteres y contener letras y números.")
            return
        }

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = FirebaseAuth.getInstance().currentUser
                    val uid = user?.uid

                    val userData = hashMapOf(
                        "name" to name,
                        "number" to number,
                        "gender" to gender
                    )

                    val db = FirebaseFirestore.getInstance()
                    if (uid != null) {
                        db.collection("users").document(uid)
                            .set(userData)
                            .addOnSuccessListener {
                                Log.i(TAG, "Usuario registrado exitosamente")
                                Toast.makeText(
                                    requireContext(),
                                    "Usuario registrado exitosamente",
                                    Toast.LENGTH_SHORT
                                ).show()
                                findNavController().navigate(R.id.action_singUpFragment_to_loginFragment)
                            }
                            .addOnFailureListener { e ->
                                Log.e(
                                    TAG,
                                    "Error al guardar datos adicionales del usuario en Firestore",
                                    e
                                )
                                Toast.makeText(
                                    requireContext(),
                                    "Error al registrar usuario. Por favor intenta de nuevo más tarde",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                    }
                } else {
                    // Manejar errores específicos de Firebase
                    val exception = task.exception
                    if (exception is FirebaseAuthException) {
                        handleFirebaseEmailErrors(exception)
                    } else {
                        Log.e(TAG, "Error al registrar usuario", task.exception)
                        Toast.makeText(
                            requireContext(),
                            "Error al registrar usuario. Por favor intenta de nuevo más tarde",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
    }

    // Función para manejar los errores específicos de Firebase
    private fun handleFirebaseEmailErrors(exception: FirebaseAuthException) {
        when (exception.errorCode) {
            "ERROR_INVALID_EMAIL" -> {
                Toast.makeText(requireContext(), "El formato del correo no es válido.", Toast.LENGTH_SHORT).show()
            }
            "ERROR_EMAIL_ALREADY_IN_USE" -> {
                Toast.makeText(requireContext(), "Este correo ya está registrado. Intente con otro correo.", Toast.LENGTH_SHORT).show()
            }
            "ERROR_USER_NOT_FOUND" -> {
                Toast.makeText(requireContext(), "Correo no encontrado. Verifica los detalles y vuelve a intentar.", Toast.LENGTH_SHORT).show()
            }
            else -> {
                Toast.makeText(requireContext(), "Error: ${exception.localizedMessage}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}

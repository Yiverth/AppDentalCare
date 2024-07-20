package com.yiverthdevs.dentalcare
import CitaViewModel
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import java.util.Calendar

class CitaFragment : Fragment() {

    private val citaViewModel: CitaViewModel by viewModels()
    // ViewModel para manejar la logica y datos de la cita

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return ComposeView(requireContext()).apply {
            setContent {
                // Configuracion de la ui utilizando jectpack compose
                app(citaViewModel, ::navigateToHistorialClinico)// se le instancia la clase cita viewModel
            }
        }
    }

    private fun navigateToHistorialClinico() {
        // Metodo para navegar al historial clinico despues de agendar una cita
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragmnet_container, HistorialClinicoFragment())
            .addToBackStack(null)
            .commit()
    }

    // Método que se llama cuando la vista del fragmento ha sido creada.
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Observador pára el resultado de guardar cita.
        citaViewModel.saveCitaResult.observe(viewLifecycleOwner, Observer { result ->
            result.fold(
                onSuccess = {
                    Log.d("Firestore", it)
                    Toast.makeText(requireContext(), "Cita agendada exitosamente", Toast.LENGTH_LONG).show()
                    navigateToHistorialClinico()
                },
                onFailure = { e ->
                    Log.w("Firestore", "Error al agendar cita", e)
                    Toast.makeText(requireContext(), "Error al agendar cita: ${e.message}", Toast.LENGTH_LONG).show()
                }
            )
        })
    }
}
// Colores personalizados para la ui
val CustomGreen = Color(0xFF009E0F)
val CustomBlack = Color(0xFF000000)
val CustomWhite = Color(0xFFFFFFFF)

// Funcion que define la ui utilizando jeckpact compose
@Composable
fun app(citaViewModel: CitaViewModel, onCitaAgendada: () -> Unit) {
    // Variables de estado para almacenar los datos ingresados por el usuario
    var nombre by remember {
        mutableStateOf("")
    }
    var correo by remember {
        mutableStateOf("")
    }
    var motivo by remember {
        mutableStateOf("")
    }

    var selectedDate by remember { mutableStateOf("") }
    var selectedTime by remember { mutableStateOf("") }
    val context = LocalContext.current

    // Variables para obtener las fechas y horas actuales
    val calendar = remember {Calendar.getInstance()}
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)
    val hour = calendar.get(Calendar.HOUR_OF_DAY)
    val minute = calendar.get(Calendar.MINUTE)

    // Dialogo para seleccionar la fecha.
    val datePickerDialog = remember {
        DatePickerDialog(
            context,
            R.style.CustomDatePickerDialog,
            { _, year, month, dayOfMonth ->
                selectedDate = "$dayOfMonth/${month + 1}/$year"
            },
            year, month, day
        ).apply {
            setOnDateSetListener { _, year, month, dayOfMonth ->
                selectedDate = "$dayOfMonth/${month + 1}/$year"
            }
        }
    }
    // Dialogo para seleccionar la hora.
    val timePickerDialog = remember {
        TimePickerDialog(
            context,
            R.style.CustomTimePickerDialog,
            { _, hourOfDay, minuteOfHour ->
                val amPm = if (hourOfDay < 12) "AM" else "PM"
                val hour12 = if (hourOfDay % 12 == 0) 12 else hourOfDay % 12
                selectedTime = String.format("%02d:%02d %s", hour12, minuteOfHour, amPm)
            },
            hour, minute, false
        )
    }

    // Colores personalizados para los campos de texto
    val textFieldColors = TextFieldDefaults.outlinedTextFieldColors(
        focusedBorderColor = CustomGreen, // Cambia el color del borde cuando está enfocado
        focusedLabelColor = CustomGreen, // Cambia el color de la etiqueta cuando está enfocado
        cursorColor = CustomGreen // Cambia el color del cursor
    )

    // Colores personalizados para los campos de texto de la fecha y hora
    val textFieldColorsTwo = TextFieldDefaults.outlinedTextFieldColors(
        // Cambia el color del borde cuando no esta seleccionado
        unfocusedBorderColor = CustomBlack,
        // Cambia el color de la etiqueta cuando no esta seleccionada
        unfocusedLabelColor = CustomBlack
    )

    // Colores personalizados para los botones
    val buttonColors = ButtonDefaults.buttonColors(
        // Cambia el color del fondo del boton
        backgroundColor = CustomGreen,
        // Cambia el color del contenido dentro del boton
        contentColor = CustomWhite
    )

    // Estructura de la interfaz de usuario
    Column(modifier = Modifier
        .fillMaxSize()
        .background(Color.White)) {
        Spacer(modifier = Modifier.height(60.dp))
        OutlinedTextField(
            value = nombre,
            onValueChange = {nombre=it},
            colors = textFieldColors,
            label = {Text("Ingrese Su Nombre")},
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp)
        )
        OutlinedTextField(
            value = correo,
            onValueChange = {correo=it},
            colors = textFieldColors,
            label = {Text("Ingrese Su Correo Electrónico")},
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp)
        )
        OutlinedTextField(
            value = motivo,
            onValueChange = {motivo=it},
            maxLines = Int.MAX_VALUE,
            colors = textFieldColors,
            label = {Text("Indique El Motivo De Cita")},
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp)
            )
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            elevation = 4.dp
        ) {
            Column(
                modifier = Modifier
                    .padding(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 32.dp) // Ajusta el padding horizontal según tus necesidades
                ) {
                    Button(
                        onClick = { datePickerDialog.show() },
                        colors = buttonColors,// Pinta el boton y el texto
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Text(text = "Seleccionar Fecha")
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
                Divider(
                    color = Color.Gray,
                    thickness = 1.dp,
                    modifier = Modifier
                        .fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                if ( selectedDate.isNotEmpty()) {// Condicion que valida si la fecha se ha seleccionado
                    OutlinedTextField(
                        value = selectedDate,
                        onValueChange = {},
                        colors = textFieldColorsTwo,
                        label = { Text("Fecha")},
                        enabled = false,
                        modifier = Modifier
                            .padding(vertical = 8.dp)
                    )
                }
            }
        }
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            elevation = 4.dp
        ){
            Column(
                modifier = Modifier
                    .padding(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 32.dp) // Ajusta el padding horizontal según tus necesidades
                ){
                    Button(onClick = { timePickerDialog.show()},
                        colors = buttonColors,
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                    {
                        Text(text = "Seleccionar Hora")
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
                Divider(
                    color = Color.Gray,
                    thickness = 1.dp,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                if ( selectedTime.isNotEmpty()) {// Condicion que valida si la hora se ha seleccionado
                    OutlinedTextField(
                        value = selectedTime,
                        onValueChange = {},
                        colors = textFieldColorsTwo,
                        label = { Text("Tiempo")},
                        enabled = false,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(32.dp))
        Button(onClick = {
            if (nombre.isBlank() || correo.isBlank() || motivo.isBlank() || selectedDate.isBlank() || selectedTime.isBlank()) {
                Toast.makeText(context, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show()
            } else {
                val cita = citasData(
                    nombre = nombre,
                    correo = correo,
                    motivo = motivo,
                    fecha = selectedDate,
                    hora = selectedTime,
                    estado = ""
                )
                citaViewModel.saveCita(cita)
            }
        },
            colors = buttonColors,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 35.dp)
        )
        {
            Text(
                text = "Agendar Cita" )
        }
    }
}


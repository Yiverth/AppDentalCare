package com.yiverthdevs.dentalcare

import CitaViewModel
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
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
import androidx.compose.ui.unit.sp
import androidx.fragment.app.viewModels
import java.util.Calendar

class HistorialClinicoFragment : Fragment() {

    private val citaViewModel: CitaViewModel by viewModels()
    // Variable privada citaViewModel para manejar el viewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return ComposeView(requireContext()).apply {
            setContent {
                // configuracion de ui utilizando jeckpack compose
                HistorialClinicoScreen(citaViewModel)// se le instancia la clase citaViewModel
            }
        }
    }

    //Metodo que se llama cuando la vista del fragmento ha sido creada
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        citaViewModel.fetchCitas() // Se visualiza el estado de la cita
        // Manejo del boton del retroceso
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                //  Navega la homeFragment
                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragmnet_container, HomeFragment())
                    .commit()
            }
        })
    }
}

val CustomGreentwo = Color(0xFF009E0F) // Para realizada
val CustomOrange = Color(0xFFFF9800) // Para pendiente

@Composable
fun HistorialClinicoScreen(citaViewModel: CitaViewModel) {
    // Observa el estado de las citas desde el view model
    val citas by citaViewModel.citas.observeAsState (initial = emptyList())

    // Lista donde se visualizan las Card creadas para el historial de las citas
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 60.dp)
    )
    {
        items(citas) { cita ->
            CitaCard(cita) // Se llama la funcion CitaCard, la cual tiene la ui el diseño de la card
        }
    }
}
@Composable
fun CitaCard(cita:citasData){

    // condicion que segun el estado de la cita se visualiza un determinado color
    val estadoColor = when (cita.estado){
        "Realizada" -> CustomGreentwo // Color verde
        "Pendiente" -> CustomOrange // Color naranja
        else -> Color.Black // Color negro
    }
    // Tarjeta (Card) que muestra los detalles de la cita
    Card(
        elevation = 4.dp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
    ){
        Column (
            modifier = Modifier
                .padding(16.dp)
        ){

            Row (
                modifier = Modifier
                    .padding(bottom = 4.dp)
                    .fillMaxWidth()
            ) {
                Text (
                    text = "Su cita se agendo el:",
                    color = CustomBlack,
                    fontSize = 17.sp,
                    style = MaterialTheme.typography.body2,
                    modifier = Modifier
                        .weight(1f)
                )
                Text(
                    text = "${cita.fechaRegistro}",
                    color = CustomBlack,
                    style = MaterialTheme.typography.body2,
                    fontSize = 17.sp
                )
            }
            // Linea divisora
            Divider(
                color = Color.Gray,
                thickness = 1.dp, // Para dar grosor a la linea
                modifier = Modifier
                    .padding(bottom = 4.dp)
            )
            Text(
                text = "Nombre: ${cita.nombre}",// Nombre del paciente
                modifier = Modifier
                    .padding(bottom = 4.dp)
            )
            Text(
                text = "Correo: ${cita.correo}",// Correo del paciente
                modifier = Modifier
                    .padding(bottom = 4.dp)
            )
            Text(
                text = "Motivo: ${cita.motivo}",// Motivo que ingreso el paciente
                modifier = Modifier
                    .padding(bottom = 4.dp)
            )
            Text(
                text = "Fecha: ${cita.fecha}",// Fecha que selecciono el paciente
                modifier = Modifier
                    .padding(bottom = 4.dp)
            )
            Row (
                modifier = Modifier
                    .padding(bottom = 4.dp)
                    .fillMaxWidth()
            ){
                Text(
                    text = "Hora: ${cita.hora}",// Hora que selecciono el paciente
                    modifier = Modifier
                        .weight(1f)
                )
                Text(
                    text = "${cita.estado}",// Se viasualiza el estado de la cita
                    color = estadoColor,// Color del estado, segun logica aplicada
                    style = MaterialTheme.typography.body2,
                    fontSize = 15.sp
                )
            }
        }
    }
}
@Preview (showBackground = true)
@Composable
fun HistorialClinicoScreen() {
    val citasPrueba = listOf(
        citasData(nombre = "Carlos", correo = "carlos@example.com", motivo = "Consulta", fecha = "10/07/2023", hora = "10:00 AM", estado = "Pendiente"),
        citasData(nombre = "Carlos", correo = "carlos@example.com", motivo = "Consulta", fecha = "10/07/2023", hora = "10:00 AM", estado = "Pendiente")

    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    )
    {
        items(citasPrueba) { cita ->
            CitaCard(cita)
        }
    }
}
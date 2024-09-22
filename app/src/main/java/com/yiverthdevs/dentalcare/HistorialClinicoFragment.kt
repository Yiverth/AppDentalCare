package com.yiverthdevs.dentalcare

import CitaViewModel
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.viewModels

class HistorialClinicoFragment : Fragment(), NavigationListener {

    private val citaViewModel: CitaViewModel by viewModels()

    // Implementación de la interfaz para navegar a la pantalla de cancelar cita
    override fun navigateToCancelarCita(
        motivo: String,
        paciente: String,
        fecha: String,
        hora: String,
        idCita: String
    ) {
        val bundle = Bundle().apply {
            putString("motivo", motivo)
            putString("paciente", paciente)
            putString("fecha", fecha)
            putString("hora", hora)
            putString("idCita", idCita)
        }

        val cancelarCitaFragment = CancelarCitaFragment().apply {
            arguments = bundle
        }

        parentFragmentManager.beginTransaction()
            .replace(R.id.fragmnet_container, cancelarCitaFragment)
            .addToBackStack(null)
            .commit()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return ComposeView(requireContext()).apply {
            setContent {
                // Pasar la instancia de la interfaz a la función composable
                HistorialClinicoScreen(citaViewModel, this@HistorialClinicoFragment)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        citaViewModel.fetchCitas() // Se visualiza el estado de la cita

        // Manejo del botón de retroceso
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                // Navega al home
                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragmnet_container, HomeFragment())
                    .commit()
            }
        })
    }
}

interface NavigationListener {
    fun navigateToCancelarCita(
        motivo: String,
        paciente: String,
        fecha: String,
        hora: String,
        hora1: String,
    )
}

val CustomGreentwo = Color(0xFF009E0F) // Para realizada
val CustomOrange = Color(0xFFFF9800) // Para pendiente

@Composable
fun HistorialClinicoScreen(citaViewModel: CitaViewModel, navigationListener: NavigationListener) {

    // Observa el estado de las citas desde el viewModel
    val citas by citaViewModel.citas.observeAsState(initial = emptyList())

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 60.dp)
    ) {
        items(citas) { cita ->
            CitaCard(cita, navigationListener)
        }
    }
}

@Composable
fun CitaCard(cita: citasData, navigationListener: NavigationListener) {
    val estadoActual = cita.estado
    val estadoColor = when (cita.estado) {
        "Realizada" -> CustomGreentwo   // Color Verde
        "Pendiente" -> CustomOrange     // Color Naranja
        else -> Color.Black             // Color Negro
    }
    // Tarjeta (Card) que muestre los detalles de la cita
    Card(
        elevation = 4.dp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier
                    .padding(bottom = 4.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = "Su cita se agendo el:",
                    color = Color.Black,
                    fontSize = 17.sp,
                    style = MaterialTheme.typography.body2,
                    modifier = Modifier
                        .weight(1f)
                )
                Text(
                    text = "${cita.fechaRegistro}",
                    color = Color.Black,
                    style = MaterialTheme.typography.body2,
                    fontSize = 17.sp
                )
            }
            // Línea divisora
            Divider(color = Color.Gray, thickness = 1.dp)
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                text = "Nombre: ${cita.nombre}",
                modifier = Modifier
                    .padding(bottom = 4.dp)
            )
            Text(
                text = "Correo: ${cita.correo}",
                modifier = Modifier
                    .padding(bottom = 4.dp)
            )
            Text(
                text = "Motivo: ${cita.motivo}",
                modifier = Modifier
                    .padding(bottom = 4.dp)
            )
            Text(
                text = "Fecha: ${cita.fecha}",
                modifier = Modifier
                    .padding(bottom = 4.dp)
            )
            Row(
                modifier = Modifier
                    .padding(bottom = 4.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = "Hora: ${cita.hora}",
                    modifier = Modifier
                        .weight(1f)
                )
                Text(
                    text = "${cita.estado}",
                    color = estadoColor,
                    style = MaterialTheme.typography.body2,
                    fontSize = 15.sp
                )
                if (estadoActual == "Pendiente"){
                    IconButton(
                        onClick = {
                            navigationListener.navigateToCancelarCita(
                                cita.motivo,
                                cita.nombre,
                                cita.fecha,
                                cita.hora,
                                cita.idCita, // Pasa el ID del documento aquí
                            )
                        },
                        modifier = Modifier.size(20.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.delete),
                            contentDescription = "Cancelar Cita",
                            tint = Color.Red
                        )

                    }

                }else { null

                }
            }
        }
    }
}

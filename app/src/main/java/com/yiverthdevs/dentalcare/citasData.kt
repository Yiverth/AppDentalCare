package com.yiverthdevs.dentalcare

// Definicion de la clase de datos "citasData"
data class citasData(
    val idCita: String = "", // Campo para almacenar el ID de la cita
    val nombre: String = "",
    val correo: String = "",
    val motivo: String = "",
    val fecha: String = "",
    val hora: String = "",
    val estado: String = "",
    val fechaRegistro: String = "",
    val userId: String = "",
    val precio: Int = 0, // campo para almacenar el precio de la cita
    var isPaid: Boolean = false // Nuevo campo
)

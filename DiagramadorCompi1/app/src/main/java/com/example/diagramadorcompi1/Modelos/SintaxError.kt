package com.example.diagramadorcompi1.Modelos

data class SintaxError(
    val tipo: String,
    val mensaje: String,
    val linea: Int,
    val columna: Int
)

package com.example.diagramadorcompi1.Modelos

data class Token(
    val lexema: String,
    val tipo: String,
    val linea: Int,
    val columna: Int
)

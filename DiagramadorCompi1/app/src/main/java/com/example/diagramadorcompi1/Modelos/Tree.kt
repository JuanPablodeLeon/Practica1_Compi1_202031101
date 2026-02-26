package com.example.diagramadorcompi1.Modelos

import com.example.diagramadorcompi1.Instruccion.Reportes.Aritmeticos
import com.example.diagramadorcompi1.Instruccion.Reportes.Control
import com.example.diagramadorcompi1.Patron.Instruccion
import java.util.LinkedList

class Tree(
    var instrucciones : LinkedList<Instruccion>
) {
    var console: String = ""
    var errors: LinkedList<SintaxError> = LinkedList()

    val reporteAritmeticos : LinkedList<Aritmeticos> = LinkedList()
    val reporteControl : LinkedList<Control> = LinkedList()

    fun print(value: String){
        console += "$value\n"
    }

    fun agregarAritmeticos(reporte: Aritmeticos){
        reporteAritmeticos.add(reporte)
    }
    fun agregarControl(reporte: Control){
        reporteControl.add(reporte)
    }
}
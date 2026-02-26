package com.example.diagramadorcompi1.Modelos

import com.example.diagramadorcompi1.Instruccion.Reportes.Aritmeticos
import com.example.diagramadorcompi1.Instruccion.Reportes.Control
import com.example.diagramadorcompi1.Patron.Instruccion
import java.util.LinkedList

class Tree(
    var instrucciones : LinkedList<Instruccion>
) {
    //variable para poder mostrar los elementos de print en la consola de reportes
    var console: String = ""
    var errors: LinkedList<SintaxError> = LinkedList()

    //Listas para guardar las operaciones aritmeticas y estructuras de control del codigo ingresado
    val reporteAritmeticos : LinkedList<Aritmeticos> = LinkedList()
    val reporteControl : LinkedList<Control> = LinkedList()

    //funcion para debugear visualmente si las operaciones se encuentran de forma correcta
    fun print(value: String){
        console += "$value\n"
    }

    //funcion para poder ir agregando las operaciones aritmeticas a su respectiva lista
    fun agregarAritmeticos(reporte: Aritmeticos){
        reporteAritmeticos.add(reporte)
    }

    //funcion para poder agregar las estructuras de control a su respectiva lista
    //Los operadores logicos no se toman en cuenta -> && || !
    fun agregarControl(reporte: Control){
        reporteControl.add(reporte)
    }
}
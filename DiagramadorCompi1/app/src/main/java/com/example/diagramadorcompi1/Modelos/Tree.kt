package com.example.diagramadorcompi1.Modelos

import com.example.diagramadorcompi1.Patron.Instruccion
import java.util.LinkedList

class Tree(
    var instrucciones : LinkedList<Instruccion>
) {
    var console: String = ""
    var errors: LinkedList<SintaxError> = LinkedList()

    fun print(value: String){
        console += "$value\n"
    }
}
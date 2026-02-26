package com.example.diagramadorcompi1.Expression

import com.example.diagramadorcompi1.Modelos.TableSymbol
import com.example.diagramadorcompi1.Modelos.Tree
import com.example.diagramadorcompi1.Modelos.Type
import com.example.diagramadorcompi1.Patron.Instruccion

//Para poder almacenar los nombres de los identificadores y valores numericos
class Native (
    val value: Any?,
    type: Type,
    linea: Int,
    columna: Int
) : Instruccion(
    type,
    linea,
    columna
){
    override fun interprete(tree: Tree, table: TableSymbol): Any? {
        return value//unicamente devuelve el valor
    }
}
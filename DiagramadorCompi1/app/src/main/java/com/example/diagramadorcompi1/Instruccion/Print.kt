package com.example.diagramadorcompi1.Instruccion

import com.example.diagramadorcompi1.Modelos.TableSymbol
import com.example.diagramadorcompi1.Modelos.Tree
import com.example.diagramadorcompi1.Modelos.TypeData
import com.example.diagramadorcompi1.Modelos.Type
import com.example.diagramadorcompi1.Patron.Instruccion

class Print(
    private  val expression: Instruccion,
    linea: Int,
    columna: Int
) : Instruccion(
    Type(TypeData.VOID),
    linea,
    columna
){
    override fun interprete(tree: Tree, table: TableSymbol): Any? {
        val value = expression.interprete(tree, table)
        if (value is Exception){
            return value
        }
        tree.print(value.toString()) //agrega como String el objeto ingresado
        return null
    }
}
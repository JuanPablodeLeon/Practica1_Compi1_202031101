package com.example.diagramadorcompi1.Expression.Aritmetica

import com.example.diagramadorcompi1.Modelos.TableSymbol
import com.example.diagramadorcompi1.Modelos.Tree
import com.example.diagramadorcompi1.Modelos.TypeData
import com.example.diagramadorcompi1.Modelos.Type
import com.example.diagramadorcompi1.Patron.Instruccion

class OperacionMathNoRetornable (
    private val left: Instruccion,
    private val right: Instruccion,
    linea: Int,
    columna: Int
) : Instruccion(
    Type(TypeData.BOOLEAN),
    linea,
    columna
){
    override fun interprete(tree: Tree, table: TableSymbol): Any? {
        val leftValue = left.interprete(tree, table)
        if (leftValue is Error)
            return leftValue

        val rightValue = right.interprete(tree, table)
        if(rightValue is Error)
            return rightValue

        return true
    }
}
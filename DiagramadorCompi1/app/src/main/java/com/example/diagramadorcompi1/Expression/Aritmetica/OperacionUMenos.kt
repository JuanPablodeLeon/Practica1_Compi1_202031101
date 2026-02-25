package com.example.diagramadorcompi1.Expression.Aritmetica

import com.example.diagramadorcompi1.Modelos.TableSymbol
import com.example.diagramadorcompi1.Modelos.Tree
import com.example.diagramadorcompi1.Modelos.Type
import com.example.diagramadorcompi1.Modelos.TypeData
import com.example.diagramadorcompi1.Patron.Instruccion

class OperacionUMenos(
    private val unique: Instruccion,
    linea: Int,
    columna: Int
) : Instruccion(
    Type(TypeData.BOOLEAN),
    linea,
    columna
){
    override fun interprete(tree: Tree, table: TableSymbol): Any? {
        val valueUnique = interprete(tree, table)
        if (valueUnique is Error)
            return valueUnique
        return true
    }
}
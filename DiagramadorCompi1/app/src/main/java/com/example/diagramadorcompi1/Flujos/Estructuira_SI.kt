package com.example.diagramadorcompi1.Flujos

import com.example.diagramadorcompi1.Modelos.TableSymbol
import com.example.diagramadorcompi1.Modelos.Tree
import com.example.diagramadorcompi1.Modelos.Type
import com.example.diagramadorcompi1.Modelos.TypeData
import com.example.diagramadorcompi1.Patron.Instruccion
import java.util.LinkedList

class Estructuira_SI (
     private val unique: Instruccion,
     private val instrucciones :LinkedList<Instruccion>?,
    linea : Int,
    columna : Int
) : Instruccion(
    Type(TypeData.VOID),
    linea,
    columna
){
    override fun interprete(tree: Tree, table: TableSymbol): Any? {
        val uniqueVal = unique.interprete(tree, table)

        if (uniqueVal == true)
            if (instrucciones != null)
                for(instruc in instrucciones)
                    instruc.interprete(tree, table)

        return null
    }
}
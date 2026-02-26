package com.example.diagramadorcompi1.Patron

import com.example.diagramadorcompi1.Modelos.TableSymbol
import com.example.diagramadorcompi1.Modelos.Tree
import com.example.diagramadorcompi1.Modelos.Type

abstract class Instruccion(
    var typeValue: Type,
    val linea: Int,
    val columna: Int
) {
    /**
     * @param tree
     * @param table
     */
    abstract fun interprete(tree: Tree, table: TableSymbol): Any?
}
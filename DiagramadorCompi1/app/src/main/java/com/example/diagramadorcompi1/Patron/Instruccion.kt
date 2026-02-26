package com.example.diagramadorcompi1.Patron

import com.example.diagramadorcompi1.Modelos.TableSymbol
import com.example.diagramadorcompi1.Modelos.Tree
import com.example.diagramadorcompi1.Modelos.Type

//clase abstracta para poder ingresar los elementos al arbol ast
abstract class Instruccion(
    var typeValue: Type,
    val linea: Int,
    val columna: Int
) {

    abstract fun interprete(tree: Tree, table: TableSymbol): Any?
}
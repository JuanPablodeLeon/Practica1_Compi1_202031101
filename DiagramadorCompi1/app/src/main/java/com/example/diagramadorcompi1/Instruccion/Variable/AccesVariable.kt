package com.example.diagramadorcompi1.Instruccion.Variable

import com.example.diagramadorcompi1.Modelos.SintaxError
import com.example.diagramadorcompi1.Modelos.TableSymbol
import com.example.diagramadorcompi1.Modelos.Tree
import com.example.diagramadorcompi1.Modelos.Type
import com.example.diagramadorcompi1.Modelos.TypeData
import com.example.diagramadorcompi1.Patron.Instruccion

class AccesVariable(
    val name: String,
    linea: Int,
    columna: Int
): Instruccion(
    Type(TypeData.VOID),
    linea,
    columna
) {
    override fun interprete(tree: Tree, table: TableSymbol): Any? {
        val value = table.gertVariable(name)// obtiene el nombre del identificador
        if (value == null){//si es null significa que aun no fue declarada
            return SintaxError("Semantico", "Variable no encontrada", linea, columna)
        }
        this.typeValue = value.typeData!!//le remueve el puntero null
        return value.value
    }
}
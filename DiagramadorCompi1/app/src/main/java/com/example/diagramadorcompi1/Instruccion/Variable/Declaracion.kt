package com.example.diagramadorcompi1.Instruccion.Variable

import com.example.diagramadorcompi1.Modelos.SintaxError
import com.example.diagramadorcompi1.Modelos.Symbol
import com.example.diagramadorcompi1.Modelos.TableSymbol
import com.example.diagramadorcompi1.Modelos.Tree
import com.example.diagramadorcompi1.Modelos.TypeData
import com.example.diagramadorcompi1.Modelos.Type
import com.example.diagramadorcompi1.Patron.Instruccion

class Declaracion(
    val name: String,
    val value: Instruccion?,
    linea : Int,
    columna: Int
) : Instruccion(
    Type(TypeData.VOID),
    linea,
    columna
){
    override fun interprete(tree: Tree, table: TableSymbol): Any? {
        if (value == null){
            val type = Type(TypeData.VOID)
            val newVal : Any? = null //Le asigna el valor null de inicio
            //Prueba establecer la variable en la tabla de simbolos
            if (table.setVariable(Symbol(this.name, newVal, type))){
                return null //lo regresa a null para que cuando llegue a el esto mas bajo pueda ser modificado
            }
        }

        val newValue = value?.interprete(tree, table)
        if(newValue is Error){
            return newValue
        }

        val data = value?.typeValue
        if (table.setVariable(Symbol(this.name, newValue, data))){
            return null
        }

        return SintaxError("Semantico", "Variable ya existente", linea, columna)
    }
}
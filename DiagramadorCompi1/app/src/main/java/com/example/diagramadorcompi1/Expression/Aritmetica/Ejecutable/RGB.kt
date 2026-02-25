package com.example.diagramadorcompi1.Expression.Aritmetica.Ejecutable

import com.example.diagramadorcompi1.Modelos.SintaxError
import com.example.diagramadorcompi1.Modelos.TableSymbol
import com.example.diagramadorcompi1.Modelos.Tree
import com.example.diagramadorcompi1.Modelos.TypeData
import com.example.diagramadorcompi1.Modelos.Type
import com.example.diagramadorcompi1.Patron.Instruccion

class RGB(
    private val unique : Instruccion,
    linea: Int,
    columna : Int
) : Instruccion(
    Type(TypeData.VOID),
    linea,
    columna
){
    override fun interprete(tree: Tree, table: TableSymbol): Any? {
        val uniqueValue = unique.interprete(tree, table)
        if (uniqueValue is Error)
            return uniqueValue

        var uniqueType = unique.typeValue.typeData

        when (uniqueType){
            TypeData.ENTERO -> {
                this.typeValue = Type(TypeData.ENTERO)
                return (uniqueValue as Int).coerceIn(0,255)
            }
            TypeData.DECIMAL ->{
                this.typeValue = Type(TypeData.ENTERO)
                val conversion : Int = (uniqueType as Double).toInt()
                return conversion.coerceIn(0,255)
            }
            else -> {
                val error : SintaxError =
                    SintaxError("SINTACTICO", "Suma Invalida", this.linea, this.columna)
                return  error
            }
        }
    }
}
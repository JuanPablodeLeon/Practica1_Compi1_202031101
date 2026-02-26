package com.example.diagramadorcompi1.Expression.Aritmetica.Ejecutable

import com.example.diagramadorcompi1.Modelos.SintaxError
import com.example.diagramadorcompi1.Modelos.TableSymbol
import com.example.diagramadorcompi1.Modelos.Tree
import com.example.diagramadorcompi1.Modelos.Type
import com.example.diagramadorcompi1.Modelos.TypeData
import com.example.diagramadorcompi1.Patron.Instruccion

//en caso de tener un valor/expresion con estructura negativa se devuelve negativa
class Negacion (
    private val unique : Instruccion,
    linea : Int,
    columna : Int
) : Instruccion(
    Type(TypeData.VOID),
    linea,
    columna
){
    override fun interprete(tree: Tree, table: TableSymbol): Any? {

        val uniqueValue = this.interprete(tree, table)
        if (uniqueValue is Error)
            return uniqueValue

        val uniqueType = unique.typeValue.typeData

        //devuelve el valor negativo
        when (uniqueType){
            TypeData.ENTERO ->{
                this.typeValue = Type(TypeData.ENTERO)
                return (uniqueType as Int) * -1
            }
            TypeData.DECIMAL ->{
                this.typeValue = Type(TypeData.ENTERO)
                return (uniqueType as Double) * -1
            }
            else -> {
                val error : SintaxError =
                    SintaxError("SINTACTICO", "Tipo de Valor no Invalido", this.linea, this.columna)
                return  error
            }
        }

    }
}
package com.example.diagramadorcompi1.Expression.Aritmetica.Ejecutable

import com.example.diagramadorcompi1.Modelos.SintaxError
import com.example.diagramadorcompi1.Modelos.TableSymbol
import com.example.diagramadorcompi1.Modelos.Tree
import com.example.diagramadorcompi1.Modelos.Type
import com.example.diagramadorcompi1.Modelos.TypeData
import com.example.diagramadorcompi1.Patron.Instruccion

class Suma (
    private val left : Instruccion,
    private val right : Instruccion,
    linea : Int,
    columna : Int
) : Instruccion(
    Type(TypeData.VOID),
    linea,
    columna
){
    override fun interprete(tree: Tree, table: TableSymbol): Any? {
        val leftValue = left.interprete(tree, table)
        if (leftValue is Error)
            return leftValue

        val rightValue = right.interprete(tree, table)
        if (rightValue is Error)
            return rightValue

        var leftType = left.typeValue.typeData
        var rightType = right.typeValue.typeData

        when (leftType){
            TypeData.ENTERO -> {
                when (rightType){
                    TypeData.ENTERO -> {
                        this.typeValue = Type(TypeData.ENTERO)
                        return leftValue as Int + rightValue as Int
                    }

                    TypeData.DECIMAL ->{
                        this.typeValue = Type(TypeData.DECIMAL)
                        if (leftValue is Number && rightValue is Number){
                            return  leftValue.toDouble() + rightValue.toDouble()
                        } else {
                            throw Exception("No se pueden sumar valores no numericos")
                        }
                    }
                    else ->{
                        val error : SintaxError =
                            SintaxError("SINTACTICO", "Suma Invalida", this.linea, this.columna)
                        return  error
                    }
                }
            }

            TypeData.DECIMAL ->{
                when (rightType){
                    TypeData.ENTERO ->{
                        this.typeValue = Type(TypeData.DECIMAL)
                        return (leftValue as Double) + (rightValue as Double)
                    }
                    TypeData.DECIMAL ->{
                        this.typeValue = Type(TypeData.DECIMAL)
                        return  leftValue as Double + rightValue as Double
                    }
                    else -> {
                        val error : SintaxError =
                            SintaxError("SINTACTICO", "Suma Invalida", this.linea, this.columna)
                        return  error
                    }
                }
            }
            else -> {
                val error : SintaxError =
                    SintaxError("SINTACTICO", "Suma Invalida", this.linea, this.columna)
                return  error
            }
        }
    }
}
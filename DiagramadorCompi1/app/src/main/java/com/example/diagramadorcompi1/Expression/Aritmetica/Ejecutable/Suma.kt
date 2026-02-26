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

        //se obtiene el tipo de las instrucciones para poder verificar si se castea o unicamente realiza la accion
        var leftType = left.typeValue.typeData
        var rightType = right.typeValue.typeData

        when (leftType){
            TypeData.ENTERO -> {
                when (rightType){
                    TypeData.ENTERO -> {//en caso de ser el valor izquierdo y derecho enteros unicamente se suman y se devuelve
                        this.typeValue = Type(TypeData.ENTERO)
                        return leftValue as Int + rightValue as Int
                    }

                    TypeData.DECIMAL ->{//si el segundo elemento es decimal se castea el primero a decimal y se devuelve
                        this.typeValue = Type(TypeData.DECIMAL)
                        if (leftValue is Number && rightValue is Number){
                            return  (leftValue as Number).toDouble() + (rightValue as Number).toDouble()
                        } else {
                            return SintaxError("SINTACTICO","No se pueden sumar valores no numericos", this.linea, this.columna)
                        }
                    }
                    else ->{
                        val error : SintaxError =
                            SintaxError("SINTACTICO", "Suma Invalida", this.linea, this.columna)
                        return  error
                    }
                }
            }

            TypeData.DECIMAL ->{//si el primero es decimal
                when (rightType){
                    TypeData.ENTERO ->{//si el segundo es decimal se casean para manejar un mismo valor
                        this.typeValue = Type(TypeData.DECIMAL)//cambia el valor a tipo decimal
                        return (leftValue as Double) + (rightValue as Double)
                    }
                    TypeData.DECIMAL ->{ //ambos decimales unicamente suma
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
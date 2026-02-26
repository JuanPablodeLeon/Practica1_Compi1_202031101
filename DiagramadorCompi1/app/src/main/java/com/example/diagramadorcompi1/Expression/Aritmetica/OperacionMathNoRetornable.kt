package com.example.diagramadorcompi1.Expression.Aritmetica

import com.example.diagramadorcompi1.Expression.Aritmetica.Ejecutable.Division
import com.example.diagramadorcompi1.Expression.Aritmetica.Ejecutable.Multiplicacion
import com.example.diagramadorcompi1.Expression.Aritmetica.Ejecutable.OperacionesReportes
import com.example.diagramadorcompi1.Expression.Aritmetica.Ejecutable.Resta
import com.example.diagramadorcompi1.Expression.Aritmetica.Ejecutable.Suma
import com.example.diagramadorcompi1.Expression.Native
import com.example.diagramadorcompi1.Instruccion.Reportes.Aritmeticos
import com.example.diagramadorcompi1.Instruccion.Variable.AccesVariable
import com.example.diagramadorcompi1.Modelos.SintaxError
import com.example.diagramadorcompi1.Modelos.TableSymbol
import com.example.diagramadorcompi1.Modelos.Tree
import com.example.diagramadorcompi1.Modelos.TypeData
import com.example.diagramadorcompi1.Modelos.Type
import com.example.diagramadorcompi1.Patron.Instruccion

class OperacionMathNoRetornable (
    private val left: Instruccion,
    private val right: Instruccion,
    private val operador: String,
    linea: Int,
    columna: Int
) : Instruccion(
    Type(TypeData.BOOLEAN),
    linea,
    columna
){
    override fun interprete(tree: Tree, table: TableSymbol): Any? {
        val leftValue = left.interprete(tree, table)
        if (leftValue is Error)
            return leftValue

        val rightValue = right.interprete(tree, table)
        if(rightValue is Error)
            return rightValue

        val tipoOperador = when (operador){
            "+" -> "Suma"
            "-" -> "Resta"
            "*" -> "Multiplicacion"
            "/" -> "Division"
            ">=" -> "Mayor Igual Que"
            "<=" -> "Menor Igual Que"
            ">" -> "Mayor Que"
            "<" -> "Menor Que"
            "==" -> "Igual"
            "!=" -> "Diferente"
            else -> null
        }

        if (tipoOperador != null && soloAritmeticos(tipoOperador) == true){
            val ocurrencia = " "+valorNumero(leftValue)+" "+operador+" "+valorNumero(rightValue)+" "
            tree.agregarAritmeticos(Aritmeticos(tipoOperador, linea, columna, ocurrencia))
        }

        return when (operador) {
            "+" , "-", "*", "/" -> OperacionesReportes.calcular(leftValue, left.typeValue.typeData, rightValue, right.typeValue.typeData, operador,linea, columna)
            else -> true
        }
    }
    fun toTexto():String{
        return "${crearTexto(left)} $operador ${crearTexto(right)}"
    }

    private fun crearTexto(texto : Instruccion):String{
       return when(texto){
            is AccesVariable -> texto.name
            is Native -> texto.value.toString()
            is OperacionMathNoRetornable -> texto.toTexto()
            else -> " "
        }
    }

    private fun soloAritmeticos(operadorV: String): Boolean{
        if (operadorV.equals("Suma") || operadorV.equals("Resta") || operadorV.equals("Multiplicacion") || operadorV.equals("Division")){
            return true
        }
        return false
    }
    private fun valorNumero(valor: Any?): String = valor?.toString() ?: "¿?"
}
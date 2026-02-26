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

        val tipoOperador = when (operador){ //cambia los operadores de simbolos a su nombre
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

        //Si el operador es valido y es una operacion aritmetia lo agrega a la lista de aritmeticos
        if (tipoOperador != null && soloAritmeticos(tipoOperador) == true){
            //convierte a un solo string la operacion aritmetica completa
            val ocurrencia = " "+valorNumero(leftValue)+" "+operador+" "+valorNumero(rightValue)+" "
            tree.agregarAritmeticos(Aritmeticos(tipoOperador, linea, columna, ocurrencia))
        }
        //devuelve el valor de la operacion aritmetica
        return when (operador) {
            "+" , "-", "*", "/" -> OperacionesReportes.calcular(leftValue, left.typeValue.typeData, rightValue, right.typeValue.typeData, operador,linea, columna)
            else -> true
        }
    }

    //devuelve en formato para la estructura de cotrol
    fun toTexto():String{
        return "${crearTexto(left)} $operador ${crearTexto(right)}"
    }

    //
    private fun crearTexto(texto : Instruccion):String{
       return when(texto){
            is AccesVariable -> texto.name //obtiene el nombre del id
            is Native -> texto.value.toString() //si es numero lo pasa a string
            is OperacionMathNoRetornable -> texto.toTexto() //si ya es una estructura de control la devuelve
            else -> " " //no ser valido manda una cadena vacia
        }
    }

    //funcion para verificar que solo operadores aritmeticos sean validos en la lista de aritmeticos
    private fun soloAritmeticos(operadorV: String): Boolean{
        if (operadorV.equals("Suma") || operadorV.equals("Resta") || operadorV.equals("Multiplicacion") || operadorV.equals("Division")){
            return true
        }
        return false
    }

    //pasa el valor del numero a String
    private fun valorNumero(valor: Any?): String = valor?.toString() ?: "¿?"
}
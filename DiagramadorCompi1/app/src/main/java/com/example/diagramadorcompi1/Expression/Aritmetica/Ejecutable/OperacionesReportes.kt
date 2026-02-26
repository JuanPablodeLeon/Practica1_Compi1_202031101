package com.example.diagramadorcompi1.Expression.Aritmetica.Ejecutable

import com.example.diagramadorcompi1.Modelos.SintaxError
import com.example.diagramadorcompi1.Modelos.TypeData

//clase tipo objeto para poder realizar las operaciones aritmeticas de forma mas sencilla
object OperacionesReportes {
    //unica clase a la que se puede acceder
    fun calcular(
        leftValue: Any?, //el valor izquierdo
        leftType: TypeData?, //el tipo del valor izquierdo
        rightValue: Any?,// el valor derecho
        rightType: TypeData?,// el tipo del valor derecho
        operador: String, //el tipo de operador
        linea: Int,
        columna: Int
    ): Any? {
        return when (operador) { //dependiendo del operador admitido realiza la operacion
            "+" -> suma(leftValue, leftType, rightValue, rightType, linea, columna)
            "-" -> resta(leftValue, leftType, rightValue, rightType, linea, columna)
            "*" -> multiplicacion(leftValue, leftType, rightValue, rightType, linea, columna)
            "/" -> division(leftValue, leftType, rightValue, rightType, linea, columna)
            else -> SintaxError("SEMANTICO", "Operador No Definido: $operador", linea, columna)
        }
    }

    private fun suma(leftV: Any?, leftT: TypeData?, rightV: Any?, rightT: TypeData?, linea: Int, columna: Int): Any? {
        //castea todo hacia Double para mayor practisidad
        val a = conversionDouble(leftV) ?: return SintaxError("SEMANTICO", "Suma invalida", linea, columna)
        val b = conversionDouble(rightV) ?: return SintaxError("SEMANTICO", "Suma invalida", linea, columna)
        val result = a + b //realiza la operacion
        //en caso de ser los valores oriinalmente ambos enteros se devuelven como enteros sino como Double
        return if (leftT == TypeData.ENTERO && rightT == TypeData.ENTERO) result.toInt() else result
    }

    private fun resta(leftV: Any?, leftT: TypeData?, rightV: Any?, rightT: TypeData?, linea: Int, columna: Int): Any? {
        val a = conversionDouble(leftV) ?: return SintaxError("SEMANTICO", "Resta inválida", linea, columna)
        val b = conversionDouble(rightV) ?: return SintaxError("SEMANTICO", "Resta inválida", linea, columna)
        val result = a - b
        return if (leftT == TypeData.ENTERO && rightT == TypeData.ENTERO) result.toInt() else result
    }

    private fun multiplicacion(leftV: Any?, leftT: TypeData?, rightV: Any?, rightT: TypeData?, linea: Int, columna: Int): Any? {
        val a = conversionDouble(leftV) ?: return SintaxError("SEMANTICO", "Multiplicación inválida", linea, columna)
        val b = conversionDouble(rightV) ?: return SintaxError("SEMANTICO", "Multiplicación inválida", linea, columna)
        val result = a * b
        return if (leftT == TypeData.ENTERO && rightT == TypeData.ENTERO) result.toInt() else result
    }

    private fun division(leftV: Any?, leftT: TypeData?, rightV: Any?, rigthT: TypeData?, linea: Int, columna: Int): Any? {
        val a = conversionDouble(leftV) ?: return SintaxError("SEMANTICO", "División inválida", linea, columna)
        val b = conversionDouble(rightV) ?: return SintaxError("SEMANTICO", "División inválida", linea, columna)
        if (b == 0.0) return SintaxError("SEMANTICO", "División por cero", linea, columna)
        val result = a / b
        return if (leftT == TypeData.ENTERO && rigthT == TypeData.ENTERO) result.toInt() else result
    }

    //funcion para poder hacer el casteo hacia double
    private fun conversionDouble(unique: Any?): Double? {
        return when (unique) {
            is Int -> unique.toDouble()
            is Double -> unique
            else -> null //si devuelve null es porque el valor admitiodo no es un numero
        }
    }
}
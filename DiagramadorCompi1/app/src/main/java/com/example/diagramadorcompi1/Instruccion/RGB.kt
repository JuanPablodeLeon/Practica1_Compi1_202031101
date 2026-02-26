package com.example.diagramadorcompi1.Instruccion

import com.example.diagramadorcompi1.Modelos.SintaxError
import com.example.diagramadorcompi1.Modelos.TableSymbol
import com.example.diagramadorcompi1.Modelos.Tree
import com.example.diagramadorcompi1.Modelos.Type
import com.example.diagramadorcompi1.Modelos.TypeData
import com.example.diagramadorcompi1.Patron.Instruccion

class RGB(
    private val red : Instruccion,
    private val green : Instruccion,
    private val blue : Instruccion,
    linea: Int,
    columna : Int
) : Instruccion(
    Type(TypeData.VOID),
    linea,
    columna
){
    override fun interprete(tree: Tree, table: TableSymbol): Any? {

        val redValue = red.interprete(tree, table)
        if (redValue is Error)
            return redValue

        val greenValue = green.interprete(tree, table)
        if (greenValue is Error)
            return greenValue

        val blueValue = blue.interprete(tree, table)
        if (blueValue is Error)
            return blueValue

        var redType = when (red.typeValue.typeData){
            TypeData.ENTERO -> {
                (redValue as Int).coerceIn(0,255)
            }
            TypeData.DECIMAL ->{
                this.typeValue = Type(TypeData.ENTERO)
                val conversion : Int = (redValue as Double).toInt()
                return conversion.coerceIn(0,255)
            }
            else -> {
                val error : SintaxError =
                    SintaxError("SINTACTICO", "Suma Invalida", this.linea, this.columna)
                return  error
            }
        }

        var greenType = when (green.typeValue.typeData){
            TypeData.ENTERO -> {
                (greenValue as Int).coerceIn(0,255)
            }
            TypeData.DECIMAL ->{
                this.typeValue = Type(TypeData.ENTERO)
                val conversion : Int = (greenValue as Double).toInt()
                return conversion.coerceIn(0,255)
            }
            else -> {
                val error : SintaxError =
                    SintaxError("SINTACTICO", "Suma Invalida", this.linea, this.columna)
                return  error
            }
        }
        var blueType = when (blue.typeValue.typeData){
            TypeData.ENTERO -> {
                (blueValue as Int).coerceIn(0,255)
            }
            TypeData.DECIMAL ->{
                this.typeValue = Type(TypeData.ENTERO)
                val conversion : Int = (blueValue as Double).toInt()
                return conversion.coerceIn(0,255)
            }
            else -> {
                val error : SintaxError =
                    SintaxError("SINTACTICO", "Suma Invalida", this.linea, this.columna)
                return  error
            }
        }
        this.typeValue = Type(TypeData.RGB)
        return Triple(red, green, blue)
    }
}
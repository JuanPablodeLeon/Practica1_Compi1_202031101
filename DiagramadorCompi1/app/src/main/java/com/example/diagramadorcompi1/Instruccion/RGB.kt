package com.example.diagramadorcompi1.Instruccion

import com.example.diagramadorcompi1.Modelos.SintaxError
import com.example.diagramadorcompi1.Modelos.TableSymbol
import com.example.diagramadorcompi1.Modelos.Tree
import com.example.diagramadorcompi1.Modelos.Type
import com.example.diagramadorcompi1.Modelos.TypeData
import com.example.diagramadorcompi1.Patron.Instruccion
/*
* Clase para poder verificar si los valores corresponden a la estructura RGB
*/
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

        ///verifica que los valores sean validos
        val redValue = red.interprete(tree, table)
        if (redValue is Error)
            return redValue

        //ingresa el valor al arbol ast
        val greenValue = green.interprete(tree, table)
        if (greenValue is Error)
            return greenValue

        val blueValue = blue.interprete(tree, table)
        if (blueValue is Error)
            return blueValue

        var redType = when (red.typeValue.typeData){
            TypeData.ENTERO -> {
                /*Si el numero es menor a 0 devuelve 0
                Si el numero es mayor a 255 devuelve 255
                Si esta dentro del rango da el numero ingresado
                * */
                (redValue as Int).coerceIn(0,255)
            }
            TypeData.DECIMAL ->{
                this.typeValue = Type(TypeData.ENTERO)
                val conversion : Int = (redValue as Double).toInt() //castea el decimal a entero
                return conversion.coerceIn(0,255)
            }
            else -> {
                val error : SintaxError =
                    SintaxError("SINTACTICO", "Color Rojo Invalido", this.linea, this.columna)
                return  error
            }
        }

        //eliminar var greenType = para prueba
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
                    SintaxError("SINTACTICO", "Color Verde Invalido", this.linea, this.columna)
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
                    SintaxError("SINTACTICO", "Color Azul Invalido", this.linea, this.columna)
                return  error
            }
        }
        this.typeValue = Type(TypeData.RGB) //redefine el tipo de datos de los objetos ingresados
        return Triple(red, green, blue) //devuele los 3 objetos recibidos
    }
}
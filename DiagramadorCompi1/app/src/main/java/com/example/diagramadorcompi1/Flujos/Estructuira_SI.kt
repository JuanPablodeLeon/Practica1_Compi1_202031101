package com.example.diagramadorcompi1.Flujos

import com.example.diagramadorcompi1.Expression.Aritmetica.OperacionMathNoRetornable
import com.example.diagramadorcompi1.Instruccion.Reportes.Control
import com.example.diagramadorcompi1.Modelos.TableSymbol
import com.example.diagramadorcompi1.Modelos.Tree
import com.example.diagramadorcompi1.Modelos.Type
import com.example.diagramadorcompi1.Modelos.TypeData
import com.example.diagramadorcompi1.Patron.Instruccion
import java.util.LinkedList

class Estructuira_SI (
     private val unique: Instruccion, //recibe una instrucion para condicional
     private val instrucciones :LinkedList<Instruccion>?, //enlista mas instrucciones para poder almacenarlas dentro de SI
    linea : Int,
    columna : Int
) : Instruccion(
    Type(TypeData.VOID),
    linea,
    columna
){
    override fun interprete(tree: Tree, table: TableSymbol): Any? {
        //Si unique llega a ser una estructura de condicion lo almacena para el uso de reporte
        //en caso de no ser una condicion se agrega ¿?
        val condicion = if(unique is OperacionMathNoRetornable) unique.toTexto() else "¿?"
        tree.agregarControl(Control("SI",linea,condicion))

        val uniqueVal = unique.interprete(tree, table)

        if (uniqueVal == true)
            if (instrucciones != null)
                for(instruc in instrucciones) //itera todas las instrucciones almacenadas para guardaras en el ast y ser usadas posteriormente
                    instruc.interprete(tree, table)

        return null //devuelve null para no tener confusion con las condicionales
    }
}
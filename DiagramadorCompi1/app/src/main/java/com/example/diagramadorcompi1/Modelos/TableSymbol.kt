package com.example.diagramadorcompi1.Modelos

class TableSymbol (
    var tablaPrevia: TableSymbol?= null
){
    private val tablaActual: MutableMap<String, Symbol> = HashMap()
    var nombre: String = ""

    fun setVariable(symbol: Symbol): Boolean{
        val id = symbol.id.lowercase()

        if (!tablaActual.containsKey(id)){
            tablaActual[id] = symbol
            return true
        }
        return false
    }

    fun gertVariable(id: String): Symbol?{
        return tablaActual[id.lowercase()]
    }
}
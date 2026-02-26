package com.example.diagramadorcompi1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.traceEventEnd
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.diagramadorcompi1.Analizadores.Lexer
import com.example.diagramadorcompi1.Analizadores.Parser
import com.example.diagramadorcompi1.Modelos.TableSymbol
import com.example.diagramadorcompi1.Modelos.Tree
import com.example.diagramadorcompi1.Patron.Instruccion
import com.example.diagramadorcompi1.ui.theme.DiagramadorCompi1Theme
import java_cup.runtime.Symbol
import java.io.StringReader
import java.util.LinkedList

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DiagramadorCompi1Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    InterfazApp(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun InterfazApp(modifier: Modifier = Modifier){

    var inputText by remember { mutableStateOf(
       /* "INICIO\n" +
                "VAR a = 5 + 8 * (12 - 1) / 3\n" +
                "FIN\n" +
                "%%%%\n" +
                "%DEFAULT = 1"*/
        """
            INICIO
            VAR a = 10
            VAR b = 20
            SI (a < b) ENTONCES
            MOSTRAR "a es menor que b"
            FINSI
            MIENTRAS (a < 15) HACER
            a = a + 1
            MOSTRAR a
            FINMIENTRAS
            MOSTRAR "Fin del programa"
            FIN
            %%%%
            %DEFAULT=1
            %COLOR_TEXTO_SI=12,45-5,1|1
            %FIGURA_MIENTRAS=CIRCULO|1
            %DEFAULT=3
        """.trimIndent()
    ) }

    var consoleText by remember { mutableStateOf("Consola....\n") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp)
    ) {
        //Titulo
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp),
            shape = RoundedCornerShape(24.dp),
            color = Color.DarkGray
        ) {
            Box(contentAlignment = Alignment.Center){
                Text(
                    text = "Diagramador Compi 1",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            shape = RoundedCornerShape(20.dp),
            color = Color(0xFF121212),
            shadowElevation = 8.dp
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                //Area de Texto del psudocodigo
                OutlinedTextField(
                    value = inputText,
                    onValueChange = { inputText = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    textStyle = TextStyle(color = Color.White),
                    shape = RoundedCornerShape(16.dp)
                )

                Spacer(modifier = Modifier.height(12.dp))

                //Lugar donde muestra los reportes del lenguaje ingresado
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp),
                    shape = RoundedCornerShape(16.dp),
                    color = Color.Black
                ) {
                    Column(
                        modifier = Modifier
                            .padding(12.dp)
                            .verticalScroll(rememberScrollState())
                    ) {
                        Text(
                            text = consoleText,
                            color = Color.Green,
                            fontSize = 14.sp
                        )
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {

            // Boton para mostrar los reportes con y sin errores
            Button(
                onClick = {
                    consoleText = "++++++++++++++++++++++++++++\n===== LISTADO REPORTES =====\n++++++++++++++++++++++++++++\n\n"
                    try {
                        val lexer = Lexer(StringReader(inputText))
                        val parser = Parser(lexer)
                        var result: Symbol? = null
                        try {
                            result = parser.parse()
                        } catch (e : Exception){

                        }

                        //probabilidad de que el reporte sea de esta forma
                        if (result != null && lexer.listaErrorLexico.isEmpty() && parser.listErrorSintactico.isEmpty()){
                            val ast = Tree(result.value as LinkedList<Instruccion>)
                            val table = TableSymbol()

                            for (instruction in ast.instrucciones) {
                                instruction.interprete(ast, table)
                            }

                            consoleText += "-- REPORTE OPERADORES MATEMATICOS --\n"

                            if (ast.reporteAritmeticos.isEmpty()){
                                consoleText += "Sin operaciones aritmeticas"
                            } else{
                                for(report in ast.reporteAritmeticos){
                                    consoleText += "\n| Operador : ${report.operador} \n| Linea : ${report.linea}\n| Columna : ${report.columna}\n| Ocurrencia : ${report.ocurrencia}\n=================================\n"
                                }
                            }

                            consoleText += "-- REPORTE ESTRUCTURAS DE CONTROL --\n"

                            if (ast.reporteControl.isEmpty()){
                                consoleText += "Sin estructuras de Control"
                            } else{
                                for(report in ast.reporteControl){
                                    consoleText += "\n| Objeto : ${report.objeto} \n| Linea : ${report.linea}\n| Condicion : ${report.condicion}\n=================================\n"
                                }
                            }
                        }

                        if(lexer.listaErrorLexico.isNotEmpty()){
                            consoleText += "\n---- ERROR LEXICO ----\n"
                            for(error in lexer.listaErrorLexico){
                                consoleText += "${error.mensaje} en :  línea ${error.linea} | columna ${error.columna} \n"
                            }
                        }

                        if(parser.listErrorSintactico.isNotEmpty()){
                            consoleText += "\n---- ERROR SINTACTICO ----\n"
                            for(error in parser.listErrorSintactico){
                                consoleText += "${error.mensaje} en :  línea ${error.linea} | columna ${error.columna} \n"
                            }
                        }

                    } catch (e: Exception) {
                        if (e.message != null && !e.message!!.contains("Couldn't repair")){
                            consoleText += "\n ---- ERROR ---- \n" + e.message
                        }
                    }
                },
                modifier = Modifier
                    .weight(1f)
                    .height(55.dp)
                    .padding(end = 8.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text("Reportes", fontSize = 14.sp)
            }
            //boton para mostrar unicamente los tokens ingresados
            Button(
                onClick = {
                    consoleText = "++++++++++++++++++++++++++\n===== LISTADO TOKENS =====\n++++++++++++++++++++++++++\n\n"
                    try {
                        val lexer = Lexer(StringReader(inputText))
                        val parser = Parser(lexer)
                        var result: Symbol? = null
                        try {
                            result = parser.parse()
                        } catch (e : Exception){

                        }

                        if (result != null && lexer.listaErrorLexico.isEmpty() && parser.listErrorSintactico.isEmpty()){
                            val ast = Tree(result.value as LinkedList<Instruccion>)
                            val table = TableSymbol()

                            for (instruction in ast.instrucciones) {
                                instruction.interprete(ast, table)
                            }

                            consoleText += "---- TOKENS ----\n" +
                                    "============================="

                            for (token in Lexer.listaTokens){
                                consoleText += "\n| Lexema: ${token.lexema}\n" +
                                        "| Tipo: ${token.tipo}\n" +
                                        "| Linea: ${token.linea}\n" +
                                        "=============================="
                            }
                        }
                        if(lexer.listaErrorLexico.isNotEmpty()){
                            consoleText += "\n---- ERROR LEXICO ----\n"
                            for(error in lexer.listaErrorLexico){
                                consoleText += "${error.mensaje} en :  línea ${error.linea} | columna ${error.columna} \n"
                            }
                        }

                        if(parser.listErrorSintactico.isNotEmpty()){
                            consoleText += "\n---- ERROR SINTACTICO ----\n"
                            for(error in parser.listErrorSintactico){
                                consoleText += "${error.mensaje} en :  línea ${error.linea} | columna ${error.columna} \n"
                            }
                        }

                    } catch (e: Exception) {
                        if (e.message != null && !e.message!!.contains("Couldn't repair")){
                            consoleText += "\n ---- ERROR ---- \n" + e.message
                        }
                    }
                },
                modifier = Modifier
                    .weight(1f)
                    .height(55.dp)
                    .padding(start = 8.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text("Tokens", fontSize = 14.sp)
            }
            // Boton para Eliminar el texto de la Consola y de donde Lee el pseudocodigo
            Button(
                onClick = {
                    inputText = " "
                },
                modifier = Modifier
                    .weight(1f)
                    .height(55.dp)
                    .padding(start = 8.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text("Limpiar", fontSize = 14.sp)
            }
        }
    }
}

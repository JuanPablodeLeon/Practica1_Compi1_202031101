package com.example.diagramadorcompi1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
//import androidx.benchmark.traceprocessor.Row
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.diagramadorcompi1.ui.theme.DiagramadorCompi1Theme

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
        "MOSTRAR 12 < 5"
    ) }

    var consoleText by remember { mutableStateOf("Consola....\n") }

    Column(
        modifier = modifier.fillMaxSize().background(Color.Black).padding(16.dp)
    ) {
        //Titulo
        Surface(
            modifier = Modifier.fillMaxWidth().height(40.dp),
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
            modifier = Modifier.fillMaxWidth().weight(1f),
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

                //Consola para debug
                //Cambiarla por reportes al finalizar lo semantico
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp),
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

            // Boton Verificar Estructura
            Button(
                onClick = {
                    consoleText = "===== EJECUCIÓN =====\n"
                    //Parte donde se agrega la info del analizador en la "Consola"
                    /*
                                        try {
                                            val lexer = Lexer(StringReader(inputText))
                                            val parser = Parser(lexer)
                                            val result = parser.parse()
                                            val ast = Tree(result.value as LinkedList<Instruction>)
                                            val table = TableSymbol()

                                            for (instruction in ast.instructions) {
                                                instruction.interpret(ast, table)
                                            }

                                            consoleText += ast.console

                                        } catch (e: Exception) {
                                            consoleText += "\n--- ERROR ---\n"
                                            consoleText += "${e.message}\n"
                                        }*/
                },
                modifier = Modifier
                    .weight(1f)
                    .height(55.dp)
                    .padding(end = 8.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text("1", fontSize = 18.sp)
            }

            // 🟣 BOTÓN 2 (puede ser limpiar consola)
            Button(
                onClick = {
                    consoleText = "Consola limpia...\n"
                },
                modifier = Modifier
                    .weight(1f)
                    .height(55.dp)
                    .padding(start = 8.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text("2", fontSize = 18.sp)
            }
        }
    }
}

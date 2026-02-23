package com.example.diagramadorcompi1.Analizadores;

import java_cup.runtime.Symbol;
import java.util.LinkedList;
import com.example.diagramadorcompi1.Modelos.SintaxError;
import com.example.diagramadorcompi1.Modelos.Token;

%%
%cup
%class Lexer
%public
%char
%line
%line
%column
%full

%{
    public LinkedList<SintaxError> listaErrorLexico = new LinkedList<>();
    public static LinkedList<Token> listaTokens = new LinkedList<>();

    private void agregarToken(String lexema, String tipo){
        listaTokens.add(new Token(lexema, tipo, yyline, yycolumn));
    }
%}

%init{
    yyline = 1;
    yycolumn = 1;
    listaErrorLexico = new LinkedList<>();
    listaTokens = new LinkedList<>();
%init}

/***** DEFINICIONES *******/
NUMEROS = [0-9]+
LETRAS = [a-zA-Z]+
DECIMAL = {NUMEROS}"."{NUMEROS}
IDENTIFICADOR = ({LETRAS}| "_")+({LETRAS}|{NUMEROS}|"_")*
HEXADECIMAL = "H"[0-9A-F]{6}
TEXTO = [\"](^\")*[\"]

espacios = [ \t\r\n\f]+

/***** TOKENS *****/

ASIGNACION = "="
SEPARADOR = "%%%%"

// Operadores Relacionales
IGUALDAD = "=="
DIFERENTE = "!="
MAYOR_QUE = ">"
MENOR_QUE = "<"
MAYOR_IGUAL_QUE = ">="
MENOR_IGUAL_QUE = "<="

// Operadores Logicos
AND = "&&"
OR = "||"
NOT = "!"

// Expresiones Numericas
LPAREN = "("
RPAREN = ")"
SUMA = "+"
RESTA = "-"
MULTIPLICACION = "*"
DIVISION = "/"

// Configuraciones Dibujo
COMA = ","
SEPARADOR_CONFIG = "|"

%%

/**** IGNORAR ESPACIOS Y COMENTARIOS DE UNA SOLA LINEA *****/
<YYINITIAL> {espacios} { }
<YYINITIAL> "#".* { }

/******PALABRAS RESERVADAS********/

//Pseudocodigo
<YYINITIAL> INICIO { agregarToken(yytext(), "INICIO"); return new Symbol(sym.INICIO, yyline, yycolumn, yytext());}
<YYINITIAL> FIN { addToken(yytext(), "FIN"); return new Symbol(sym.FIN, yyline, yycolumn, yytext());}

<YYINITIAL> SI { addToken(yytext(), "SI"); return new Symbol(sym.SI, yyline, yycolumn, yytext());}
<YYINITIAL> ENTONCES { addToken(yytext(), "ENTONCES"); return new Symbol(sym.ENTONCES, yyline, yycolumn, yytext());}
<YYINITIAL> FINSI { addToken(yytext(), "FINSI"); return new Symbol(sym.FINSI, yyline, yycolumn, yytext());}

<YYINITIAL> MIENTRAS { addToken(yytext(), "MIENTRAS"); return new Symbol(sym.MIENTRAS, yyline, yycolumn, yytext());}
<YYINITIAL> HACER { addToken(yytext(), "HACER"); return new Symbol(sym.HACER, yyline, yycolumn, yytext());}
<YYINITIAL> FINMIENTRAS { addToken(yytext(), "FINMIENTRAS"); return new Symbol(sym.FINMIENTRAS, yyline, yycolumn, yytext());}

<YYINITIAL> MOSTRAR { addToken(yytext(), "MOSTRAR"); return new Symbol(sym.MOSTRAR, yyline, yycolumn, yytext());}

<YYINITIAL> LEER { addToken(yytext(), "LEER"); return new Symbol(sym.LEER, yyline, yycolumn, yytext());}

<YYINITIAL> VAR { addToken(yytext(), "VAR"); return new Symbol(sym.VAR, yyline, yycolumn, yytext());}

//Configuraciones
<YYINITIAL> %DEFAULT { addToken(yytext(), "DEFAULT"); return new Symbol(sym.DEFAULT, yyline, yycolumn, yytext());}

<YYINITIAL> %COLOR_TEXTO_SI { addToken(yytext(), "COLOR_TEXTO_SI"); return new Symbol(sym.COLOR_TEXTO_SI, yyline, yycolumn, yytext());}
<YYINITIAL> %COLOR_SI { addToken(yytext(), "COLOR_SI"); return new Symbol(sym.COLOR_SI, yyline, yycolumn, yytext());}
<YYINITIAL> %FIGURA_SI { addToken(yytext(), "FIGURA_SI"); return new Symbol(sym.FIGURA_SI, yyline, yycolumn, yytext());}
<YYINITIAL> %LETRAS_SI { addToken(yytext(), "LETRA_SI"); return new Symbol(sym.LETRA_SI, yyline, yycolumn, yytext());}
<YYINITIAL> %LETRA_SIZE_SI { addToken(yytext(), "LETRA_SIZE_SI"); return new Symbol(sym.LETRA_SIZE_SI, yyline, yycolumn, yytext());}

<YYINITIAL> %COLOR_TEXTO_MIENTRAS { addToken(yytext(), "COLOR_TEXTO_MIENTRAS"); return new Symbol(sym.COLOR_TEXTO_MIENTRAS, yyline, yycolumn, yytext());}
<YYINITIAL> %COLOR_MIENTRAS { addToken(yytext(), "COLOR_MIENTRAS"); return new Symbol(sym.COLOR_MIENTRAS, yyline, yycolumn, yytext());}
<YYINITIAL> %FIGURA_MIENTRAS { addToken(yytext(), "FIGURA_MIENTRAS"); return new Symbol(sym.FIGURA_MIENTRAS, yyline, yycolumn, yytext());}
<YYINITIAL> %LETRAS_MIENTRAS { addToken(yytext(), "LETRA_MIENTRAS"); return new Symbol(sym.LETRA_MIENTRAS, yyline, yycolumn, yytext());}
<YYINITIAL> %LETRA_SIZE_MIENTRAS { addToken(yytext(), "LETRA_SIZE_MIENTRAS"); return new Symbol(sym.LETRA_SIZE_MIENTRAS, yyline, yycolumn, yytext());}

<YYINITIAL> %COLOR_TEXTO_BLOQUE { addToken(yytext(), "COLOR_TEXTO_BLOQUE"); return new Symbol(sym.COLOR_TEXTO_BLOQUE, yyline, yycolumn, yytext());}
<YYINITIAL> %COLOR_BLOQUE { addToken(yytext(), "COLOR_BLOQUE"); return new Symbol(sym.COLOR_BLOQUE, yyline, yycolumn, yytext());}
<YYINITIAL> %FIGURA_BLOQUE { addToken(yytext(), "FIGURA_BLOQUE"); return new Symbol(sym.FIGURA_BLOQUE, yyline, yycolumn, yytext());}
<YYINITIAL> %LETRAS_BLOQUE{ addToken(yytext(), "LETRA_BLOQUE"); return new Symbol(sym.LETRA_BLOQUE, yyline, yycolumn, yytext());}
<YYINITIAL> %LETRA_SIZE_BLOQUE { addToken(yytext(), "LETRA_SIZE_BLOQUE"); return new Symbol(sym.LETRA_SIZE_BLOQUE, yyline, yycolumn, yytext());}

//Tipos de Forma
<YYINITIAL> ECLIPSE { addToken(yytext(), "ECLIPSE"); return new Symbol(sym.ECLIPSE, yyline, yycolumn, yytext());}

<YYINITIAL> CIRCULO { addToken(yytext(), "CIRCULO"); return new Symbol(sym.CIRCULO, yyline, yycolumn, yytext());}

<YYINITIAL> PARALELOGRAMO { addToken(yytext(), "PARALELOGRAMO"); return new Symbol(sym.PARALELOGRAMO, yyline, yycolumn, yytext());}

<YYINITIAL> RECTANGULO { addToken(yytext(), "RECTANGULO"); return new Symbol(sym.RECTANGULO, yyline, yycolumn, yytext());}

<YYINITIAL> ROMBO { addToken(yytext(), "ROMBO"); return new Symbol(sym.ROMBO, yyline, yycolumn, yytext());}

<YYINITIAL> RECTANGULO_REDONDEADO { addToken(yytext(), "RECTANGULO_REDONDEADO"); return new Symbol(sym.RECTANGULO_REDONDEADO, yyline, yycolumn, yytext());}

//Tipos de Letra
<YYINITIAL> ARIAL { addToken(yytext(), "ARIAL"); return new Symbol(sym.ARIAL, yyline, yycolumn, yytext());}

<YYINITIAL> TIMES_NEW_ROMAN { addToken(yytext(), "TIMES_NEW_ROMAN"); return new Symbol(sym.TIMES_NEW_ROMAN, yyline, yycolumn, yytext());}

<YYINITIAL> COMIC_SANS { addToken(yytext(), "COMIC_SANS"); return new Symbol(sym.COMIC_SANS, yyline, yycolumn, yytext());}

<YYINITIAL> VERDANA { addToken(yytext(), "VERDANA"); return new Symbol(sym.VERDANA, yyline, yycolumn, yytext());}

//Separador para dar Inicio de configuraciones
<YYINITIAL> {SEPARADOR} { addToken(yytext(), "SEPARADOR"); return new Symbol(sym.SEPARADOR, yyline, yycolumn, yytext());}

/***** Texto para la estructura "MOSTRAR" ******/
<YYINITIAL> {TEXTO} { addToken(yytext(), "TEXTO"); return new Symbol(sym.TEXTO, yyline, yycolumn, yytext());}

/***** Operadores Logicos y Relacionales ******/
<YYINITIAL> {IGUALDAD} { addToken(yytext(), "IGUALDAD"); return new Symbol(sym.IGUALDAD, yyline, yycolumn, yytext());}

<YYINITIAL> {DIFERENTE} { addToken(yytext(), "DIFERENTE"); return new Symbol(sym.DIFERENTE, yyline, yycolumn, yytext());}

<YYINITIAL> {MENOR_IGUAL_QUE} { addToken(yytext(), "MENOR_IGUAL_QUE"); return new Symbol(sym.MENOR_IGUAL_QUE, yyline, yycolumn, yytext());}

<YYINITIAL> {MAYOR_IGUAL_QUE} { addToken(yytext(), "MAYOR_IGUAL_QUE"); return new Symbol(sym.MAYOR_IGUAL_QUE, yyline, yycolumn, yytext());}

<YYINITIAL> {OR} { addToken(yytext(), "OR"); return new Symbol(sym.OR, yyline, yycolumn, yytext());}

<YYINITIAL> {AND} { addToken(yytext(), "AND"); return new Symbol(sym.AND, yyline, yycolumn, yytext());}

<YYINITIAL> {MENOR_QUE} { addToken(yytext(), "MENOR_QUE"); return new Symbol(sym.MENOR_QUE, yyline, yycolumn, yytext());}

<YYINITIAL> {MAYOR_QUE} { addToken(yytext(), "MAYOR_QUE"); return new Symbol(sym.,MAYOR_QUE yyline, yycolumn, yytext());}

<YYINITIAL> {ASIGNACION} { addToken(yytext(), "ASIGNACION"); return new Symbol(sym.ASIGNACION, yyline, yycolumn, yytext());}

<YYINITIAL> {NOT} { addToken(yytext(), "NOT"); return new Symbol(sym.NOT, yyline, yycolumn, yytext());}

/**** Simbolos ****/
<YYINITIAL> {LAPREN} { addToken(yytext(), "LPAREN"); return new Symbol(sym.LPAREN, yyline, yycolumn, yytext());}

<YYINITIAL> {RPAREN} { addToken(yytext(), "RPAREN"); return new Symbol(sym.RPAREN, yyline, yycolumn, yytext());}

<YYINITIAL> {COMA} { addToken(yytext(), "COMA"); return new Symbol(sym.COMA, yyline, yycolumn, yytext());}

<YYINITIAL> {SEPARADOR_CONFIG} { addToken(yytext(), "SEPARADOR_CONFIG"); return new Symbol(sym.SEPARADOR_CONFIG, yyline, yycolumn, yytext());}

//Aridmeticos
<YYINITIAL> {SUMA} { addToken(yytext(), "SUMA"); return new Symbol(sym.SUMA, yyline, yycolumn, yytext());}

<YYINITIAL> {RESTA} { addToken(yytext(), "RESTA"); return new Symbol(sym.RESTA, yyline, yycolumn, yytext());}

<YYINITIAL> {MULTIPLICACION} { addToken(yytext(), "MULTIPLICACION"); return new Symbol(sym.MULTIPLICACION, yyline, yycolumn, yytext());}

<YYINITIAL> {DIVISION} { addToken(yytext(), "DIVISION"); return new Symbol(sym.DIVISION, yyline, yycolumn, yytext());}

/***** Numeros ******/
<YYINITIAL> {NUMEROS} { addToken(yytext(), "ENTEROS"); return new Symbol(sym.ENTEROS, yyline, yycolumn, yytext());}

<YYINITIAL> {DECIMAL} { addToken(yytext(), "DECIMAL"); return new Symbol(sym.DECIMAL, yyline, yycolumn, yytext());}

/**** Forma Hexadecimal ****/
<YYINITIAL> {HEXADECIMAL} { addToken(yytext(), "HEXADECIMAL"); return new Symbol(sym.HEXADECIMAL, yyline, yycolumn, yytext());}

/***** Identificador *****/
<YYINITIAL> {IDENTIFICADOR} { addToken(yytext(), "IDENTIFICADOR"); return new Symbol(sym.IDENTIFICADOR, yyline, yycolumn, yytext());}

/**** Error Lexico ****/
<YYINITIAL> . { addToken(yytext(), "ERROR"); listaErrorLexico.add(new SintaxError("LEXICO", "Simbolo: "+ yytext()+" no existente en el lenguaje", yyline, yycolumn));}

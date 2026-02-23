package com.example.diagramadorcompi1.Analizadores;

import java_cup.runtime.Symbol;
import java.util.LinkedList;
/* Modificar
import com.example.test.model.SintaxError;
import com.example.test.model.Token;*/

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
IDENTIFICADOR = ({LETRAS}| "_")+({LETRAS}|{NUMEROS})*




%%
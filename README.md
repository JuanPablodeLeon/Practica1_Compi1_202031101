# Diagramador Compi 1 — Manual de Usuario

Aplicación Android para interpretar pseudocódigo estructurado y generar reportes de operadores matemáticos, estructuras de control y errores.

---
## APK
Acceso al APK

- [Descarga Apk](https://github.com/JuanPablodeLeon/Practica1_Compi1_202031101/releases)

## 📱 Interfaz de la Aplicación

Al abrir la app verás tres secciones:

<p align="center">
  <img src="/Documentacio/Preview.jpg" alt="Preview App" width="400"/>
</p>


### Botones


 **Reportes** -> Analiza el código y muestra el reporte de operadores matemáticos y estructuras de control (o errores si los hay) 
 
 **Tokens**  -> Muestra todos los tokens que el analizador léxico reconoció
 
 **Limpiar**  -> Borra el texto ingresado, pero deja el ultimo texto analizado en consola

---

## ¿Como podes escribir el codigo?

El archivo tiene **dos secciones separadas por `%%%%`**:

```
INICIO
    ... codigo ...
FIN
%%%%
... configuracion de diagramas ...
```

**El lenguaje es case sensitive.**
Significa que solo reconocera tokens a base de como estan definidos en el analizador lexico
un ejemplo es:  `INICIO` es válido, `inicio` no lo es.

---

## Estructura del Algoritmo

### Inicio y Fin

Todo algoritmo debe comenzar con `INICIO` y terminar con `FIN`:

```
INICIO
    VAR a = 10
    MOSTRAR "Hola"
FIN
```

### Variables

Se inicializan con `VAR`, seguido de un `IDENTIFICADOR`,
tambien puede ser seguido de un `=` seguido de una operacion aritmetica, numero u otro identificador.
Cabe aclarar que se puede iniciar con un `IDENTIFICADOR`, pero este si debe de ser seguido de `=` y los demas valores validos para esta parte

```
VAR a           # declara sin valor
VAR b = 25      # declara con entero
VAR c = 3.14    # declara con decimal
VAR d = 45 + 9 -(12) # declara con operacion aritmetica
a = b + 10      # reasigna valor
```

### MOSTRAR

Solo armite un texto que se encuentre dentro de `"` o el nombre de un `IDENTIFICADOR`

```
MOSTRAR "Hola mundo"   # muestra texto literal
MOSTRAR a              # muestra el valor de la variable a
```

### LEER

Unicamente admite `IDENTIFICADORES`

```
LEER a
```

### Comentarios

Los comentarios comienzan con `#` y pueden ir en cualquier parte.
Estos no seran tomados en cuenta en la parte de reportes

```
# Esto es un comentario
VAR x = 5  # también al final de una línea
```

---

## Operadores Aritméticos

| Símbolo | Descripción    | Precedencia |

| `+`     | Suma           | 1 (menor)   |

| `-`     | Resta          | 1           |

| `*`     | Multiplicación | 2           |

| `/`     | División       | 2           |

| `()`    | Paréntesis     | 3 (mayor)   |

**Ejemplos:**
```
5 + 8 * 2        # resultado: 21
 (5 + 8) * 2      # resultado: 26
 25 - 2 * 5 / 4   # resultado: 22.5
```

---

## Estructuras de Control


### SI (condicional)

Dentro del condicional `SI` puede haber cualquier estructura del bloque de codigo
```
SI (condicion) ENTONCES
    ... instrucciones ...
FINSI
```

**Ejemplo:**
```
SI (a < b) ENTONCES
    MOSTRAR "a es menor que b"
FINSI
```

### MIENTRAS (ciclo)

Dentro del condicional `MIENTRAS` puede haber cualquier estructura del bloque de codigo

```
MIENTRAS (condicion) HACER
    ... instrucciones ...
FINMIENTRAS
```

**Ejemplo:**
```
MIENTRAS (a <= 5) HACER
    MOSTRAR a
    a = a + 1
FINMIENTRAS
```

---

## Operadores Relacionales y Lógicos

### Relacionales

| Símbolo | Descripción       |

| `==`    | Igual a           |

| `!=`    | Diferente de      |

| `>`     | Mayor que         |

| `<`     | Menor que         |

| `>=`    | Mayor o igual que |

| `<=`    | Menor o igual que |

### Lógicos

| Símbolo | Descripción |

| `&&`    | AND         |

| `||`  | OR          |

| `!`     | NOT         |

**Ejemplos de condiciones:**
```
(a > 5)
(a >= 0 && b != 10)
(x < 100)
```

---

## Sección de Configuración

Después del `%%%%` puedes configurar la apariencia del diagrama. 
En esta parte **debe tener al menos una instrucción**.

```
%%%%
%DEFAULT=1
%COLOR_TEXTO_SI=12,45,200|1
%FIGURA_MIENTRAS=CIRCULO|1
```

El número al final de cada instrucción (después de `|`) es el **índice** del elemento que se modificará.

### Instrucciones disponibles

| Instrucción | Descripción |

| `%DEFAULT=<indice>` | Aplica estilo por defecto al elemento indicado |

| `%COLOR_TEXTO_SI=<color>\|<indice>` | Color del texto del nodo SI |

| `%COLOR_SI=<color>\|<indice>` | Color de fondo del nodo SI |

| `%FIGURA_SI=<figura>\|<indice>` | Forma del nodo SI |

| `%LETRAS_SI=<tipografia>\|<indice>` | Tipografía del nodo SI |

| `%LETRAS_SIZE_SI=<decimal>\|<indice>` | Tamaño de letra del nodo SI |

| `%COLOR_TEXTO_MIENTRAS=<color>\|<indice>` | Color del texto del nodo MIENTRAS |

| `%COLOR_MIENTRAS=<color>\|<indice>` | Color de fondo del nodo MIENTRAS |

| `%FIGURA_MIENTRAS=<figura>\|<indice>` | Forma del nodo MIENTRAS |

| `%LETRAS_MIENTRAS=<tipografia>\|<indice>` | Tipografía del nodo MIENTRAS |

| `%COLOR_TEXTO_BLOQUE=<color>\|<indice>` | Color del texto de un bloque |

| `%COLOR_BLOQUE=<color>\|<indice>` | Color de fondo de un bloque |

| `%FIGURA_BLOQUE=<figura>\|<indice>` | Forma de los bloques |

### Colores

**RGB**
Seran valores entre 0 y 255, pero pueden haber operaciones aritmeticas para el calculo del valor
```
12,74,25
251,12,85/2
12,45-5,1
```

**Hexadecimal** 
Empieza por la letra `H` y despues seguido por 6 digitos siendo combinacion de 0 a 9 con A al F
```
HFF0012A
H124AA44
```

### Figuras disponibles

`ELIPSE` `CIRCULO` `PARALELOGRAMO` `RECTANGULO` `ROMBO` `RECTANGULO_REDONDEADO`

### Tipografías disponibles

`ARIAL` `TIMES_NEW_ROMAN` `COMIC_SANS` `VERDANA`

---

## Reportes

### Reporte de Operadores Matemáticos

Muestra cada operación aritmética y relacional encontrada durante la ejecución:

```
--- REPORTE OPERADORES MATEMATICOS ---
| Operador  : Suma
| Linea     : 3
| Columna   : 9
| Ocurrencia: 10 + 1
=================================
```

### Reporte de Estructuras de Control

Muestra cada estructura `SI` o `MIENTRAS` encontrada con su condición:

```
--- REPORTE ESTRUCTURAS DE CONTROL ---
| Objeto   : SI
| Linea    : 4
| Condicion: a < b
=================================
```

### Reporte de Errores

Si el código tiene errores, **solo se muestra este reporte**:

```
---- ERROR LEXICO ----
Simbolo: $ no existente en el lenguaje en: línea 2 | columna 5

---- ERROR SINTACTICO ----
No se esperaba: * en: línea 3 | columna 1
```

### Lista de Tokens

Al presionar **Tokens** verás cada token reconocido por el analizador:

```
---- TOKENS ----
| Lexema: INICIO
| Tipo  : INICIO
| Linea : 1
=================================
| Lexema: a
| Tipo  : IDENTIFICADOR
| Linea : 2
=================================
```

---
title: Recordant
layout: default
parent: Descripció del Mòdul
nav_order: 20
has_children: true
has_toc: true
---


# Excepcions en Java — Repàs

Les excepcions no són un mecanisme exclusiu de l'accés a dades. Poden aparéixer en qualsevol programa quan es produeix una situació que impedeix continuar l'execució normal: una divisió entre zero, una dada amb un format incorrecte, l'accés a una posició que no existeix, etc.

L'objectiu d'este repàs és entendre **què ocorre quan es produeix una excepció, com podem controlar-la i quines estructures proporciona Java per gestionar-la**. 

---

## 1. Què és una excepció?

Una **excepció** és un objecte que representa una situació **anormal** produïda durant l'execució d'un programa.

Per exemple:

```java
public class ExempleDivisio {

    public static void main(String[] args) {

        int a = 10;
        int b = 0;

        int resultat = a / b;

        System.out.println("Resultat: " + resultat);
        System.out.println("Fi del programa");
    }
}
```

Java no pot realitzar:

```java
10 / 0
```

i genera una excepció:

```text
ArithmeticException
```

Quan es produeix l'excepció, el programa interromp el seu flux normal. Per tant:

```java
System.out.println("Resultat: " + resultat);
System.out.println("Fi del programa");
```

no arriben a executar-se.

Un altre exemple:

```java
public class ExempleArray {

    public static void main(String[] args) {

        int[] numeros = {10, 20, 30};

        System.out.println(numeros[8]);

        System.out.println("Fi del programa");
    }
}
```

L'array només té les posicions `0`, `1` i `2`. Intentar accedir a:

```java
numeros[8]
```

genera:

```text
ArrayIndexOutOfBoundsException
```

Per tant, una excepció permet representar un problema produït durant l'execució. Java ens proporciona mecanismes per **detectar-lo i decidir què fer**, en lloc de deixar que el programa finalitze de manera descontrolada. 

---

## 2. `try` i `catch`

L'estructura bàsica per controlar excepcions és:

```java
try {

    // codi que pot produir una excepció

} catch (TipusExcepcio e) {

    // què fem si es produeix

}
```

El bloc `try` conté el codi que volem executar.

Si no es produeix cap excepció, totes les instruccions s'executen normalment i el `catch` s'ignora.

Si es produeix una excepció, Java **interromp immediatament l'execució del `try`** i busca un `catch` compatible. 

### Exemple

```java
public class ExempleDivisio {

    public static void main(String[] args) {

        int a = 10;
        int b = 0;

        try {

            int resultat = a / b;
            System.out.println("Resultat: " + resultat);

        } catch (ArithmeticException e) {

            System.out.println("No es pot dividir entre zero");
        }

        System.out.println("Fi del programa");
    }
}
```

L'eixida és:

```text
No es pot dividir entre zero
Fi del programa
```

L'excepció s'ha produït igualment, però ara **l'hem capturada i controlada**.

És important observar que esta instrucció:

```java
System.out.println("Resultat: " + resultat);
```

no s'executa.

Quan apareix l'excepció, Java abandona la resta del `try`.

Podem comprovar-ho fàcilment:

```java
public class ExempleFlux {

    public static void main(String[] args) {

        try {

            System.out.println("1. Inici del try");

            int resultat = 10 / 0;

            System.out.println("2. Resultat: " + resultat);
            System.out.println("3. Final del try");

        } catch (ArithmeticException e) {

            System.out.println("4. Excepció capturada");
        }

        System.out.println("5. Fi del programa");
    }
}
```

El resultat és:

```text
1. Inici del try
4. Excepció capturada
5. Fi del programa
```

---

## 3. Diversos `catch`

Un mateix bloc de codi pot provocar **diferents tipus d'excepcions**.

En eixe cas podem utilitzar diversos `catch`.

```java
import java.util.InputMismatchException;
import java.util.Scanner;

public class Exemple {

    public static void main(String[] args) {

        Scanner teclat = new Scanner(System.in);

        try {

            System.out.print("Numerador: ");
            int a = teclat.nextInt();

            System.out.print("Denominador: ");
            int b = teclat.nextInt();

            int resultat = a / b;

            System.out.println("Resultat: " + resultat);

        } catch (InputMismatchException e) {

            System.out.println("Has d'introduir números enters");

        } catch (ArithmeticException e) {

            System.out.println("No es pot dividir entre zero");
        }
    }
}
```

En este exemple poden produir-se dos problemes diferents:

* Si introduïm text en lloc d'un enter, `nextInt()` genera una `InputMismatchException`.
* Si el denominador és `0`, es genera una `ArithmeticException`.

Cada `catch` resol **un problema diferent**. 

### Ordre dels `catch`

Les excepcions més específiques han d'anar abans que les més generals.

Correcte:

```java
try {

    int resultat = 10 / 0;

} catch (ArithmeticException e) {

    System.out.println("Error aritmètic");

} catch (Exception e) {

    System.out.println("S'ha produït una excepció");
}
```

`ArithmeticException` és més específica que `Exception`.

Sempre que siga possible és preferible capturar **el tipus concret d'excepció**, perquè així podem saber què ha fallat i donar un tractament adequat. 

### Multicatch

Si diverses excepcions han de tindre **exactament el mateix tractament**, podem agrupar-les utilitzant `|`.

```java
import java.util.InputMismatchException;
import java.util.Scanner;

public class ExempleMulticatch {

    public static void main(String[] args) {

        Scanner teclat = new Scanner(System.in);
        int[] numeros = {10, 20, 30, 40, 50};

        try {

            System.out.print("Introdueix una posició: ");
            int posicio = teclat.nextInt();

            System.out.println("Valor: " + numeros[posicio]);

        } catch (InputMismatchException |
                 ArrayIndexOutOfBoundsException e) {

            System.out.println(
                    "No s'ha pogut realitzar la consulta"
            );
        }
    }
}
```

Utilitzarem multicatch quan **realment vulguem donar el mateix tractament** a les diferents excepcions. Si necessitem diferenciar els problemes, és millor utilitzar diversos `catch`.

---

## 4. Checked i unchecked exceptions

No totes les excepcions es comporten igual.

Una distinció important en Java és entre excepcions **checked** i **unchecked**. 

| Tipus         | Java obliga a tractar-la? | Exemples                                                                                                  |
| ------------- | ------------------------- | --------------------------------------------------------------------------------------------------------- |
| **Checked**   | Sí                        | `FileNotFoundException`, `IOException`, `ClassNotFoundException`                                          |
| **Unchecked** | No                        | `ArithmeticException`, `InputMismatchException`, `ArrayIndexOutOfBoundsException`, `NullPointerException` |

### Unchecked

Són excepcions que el compilador **no ens obliga a capturar o declarar**.

Per exemple:

```java
public class Exemple {

    public static void main(String[] args) {

        int a = 10;
        int b = 0;

        int resultat = a / b;

        System.out.println(resultat);
    }
}
```

El programa compila.

Java no ens obliga a escriure:

```java
try {

    ...

} catch (ArithmeticException e) {

    ...
}
```

El problema apareixerà durant l'execució.

El mateix ocorre amb:

```java
int[] numeros = {10, 20, 30};

System.out.println(numeros[10]);
```

Java permet compilar-lo, encara que durant l'execució es produirà una:

```text
ArrayIndexOutOfBoundsException
```

### Checked

En altres casos Java obliga a **gestionar l'excepció**.

Podem fer-ho principalment de dues maneres:

```text
capturar-la              propagar-la
     ↓                         ↓
 try-catch                  throws
```

Per exemple, quan intentem obrir un fitxer:

```java
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Exemple {

    public static void main(String[] args) {

        try {

            FileInputStream fitxer =
                    new FileInputStream("dades.txt");

        } catch (FileNotFoundException e) {

            System.out.println("No s'ha trobat el fitxer");
        }
    }
}
```

El constructor de `FileInputStream` pot produir una `FileNotFoundException`. Com és una excepció **checked**, Java no ens permet ignorar-la: hem de gestionar-la. 

Una altra possibilitat és propagar-la:

```java
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Exemple {

    public static void obrirFitxer()
            throws FileNotFoundException {

        FileInputStream fitxer =
                new FileInputStream("dades.txt");
    }
}
```

En este cas `obrirFitxer()` no tracta l'excepció. Indica que **pot propagar-la al mètode que l'ha cridat**.

A continuació veurem què significa exactament `throws`.

---

## 5. `throw` i `throws`: no són el mateix

És una de les confusions més habituals quan comencem a treballar amb excepcions.

|            | `throw`                    | `throws`                              |
| ---------- | -------------------------- | ------------------------------------- |
| Significat | Llança una excepció        | Declara que un mètode la pot propagar |
| On apareix | Dins del codi              | En la signatura del mètode            |
| Exemple    | `throw new Exception(...)` | `void metode() throws Exception`      |

### `throw`

Fins ara les excepcions apareixien automàticament.

Per exemple:

```java
int resultat = 10 / 0;
```

provoca una `ArithmeticException`.

Però nosaltres també podem decidir que una determinada situació és incorrecta i **llançar una excepció explícitament**.

Per a això utilitzem `throw`.

```java
public class ExempleNota {

    public static void main(String[] args) {

        double nota = 15;

        if (nota < 0 || nota > 10) {

            throw new IllegalArgumentException(
                    "La nota ha d'estar entre 0 i 10"
            );
        }

        System.out.println("Nota correcta: " + nota);
    }
}
```

La instrucció:

```java
throw new IllegalArgumentException(
        "La nota ha d'estar entre 0 i 10"
);
```

crea una excepció i la llança.

En este cas no ha sigut Java qui ha detectat automàticament el problema. **Nosaltres hem decidit que una nota fora del rang 0–10 és una situació incorrecta.**

### `throws`

`throws` té una funció diferent.

Indica que un mètode **pot produir una excepció i no la gestionarà en el seu interior**.

```java
public class Exemple {

    public static void comprovarEdat(int edat)
            throws Exception {

        if (edat < 18) {

            throw new Exception(
                    "La persona ha de ser major d'edat"
            );
        }

        System.out.println("Edat correcta");
    }

    public static void main(String[] args) {

        try {

            comprovarEdat(16);

        } catch (Exception e) {

            System.out.println(
                    "Error: " + e.getMessage()
            );
        }
    }
}
```

El mètode declara:

```java
throws Exception
```

per indicar que pot propagar una excepció.

Dins del mètode:

```java
throw new Exception(...)
```

és la instrucció que realment la llança.

I qui crida el mètode:

```java
comprovarEdat(16);
```

pot capturar-la amb:

```java
catch (Exception e)
```

Per tant:

```text
throw                       throws
  ↓                            ↓
llança                    declara que
l'excepció                pot propagar-la
```

---

## 6. `finally`

`finally` conté codi que s'executa **tant si es produeix una excepció com si no**.

L'estructura és:

```java
try {

    // operació

} catch (Exception e) {

    // tractament de l'excepció

} finally {

    // s'executa al final
}
```

### Exemple

```java
public class ExempleFinally {

    public static void main(String[] args) {

        try {

            System.out.println("Inici de l'operació");

            int resultat = 10 / 0;

            System.out.println("Resultat: " + resultat);

        } catch (ArithmeticException e) {

            System.out.println("No es pot dividir entre zero");

        } finally {

            System.out.println("Final de l'operació");
        }

        System.out.println("Fi del programa");
    }
}
```

El resultat és:

```text
Inici de l'operació
No es pot dividir entre zero
Final de l'operació
Fi del programa
```

Si no es produeix l'excepció, `finally` també s'executa.

| Situació                 | `try`                       | `catch` | `finally` |
| ------------------------ | --------------------------- | ------- | --------- |
| No hi ha excepció        | S'executa complet           | No      | Sí        |
| Hi ha excepció capturada | Fins que apareix l'excepció | Sí      | Sí        |

`finally` és útil quan tenim codi que **volem executar independentment del resultat de l'operació**. 

---

## 7. `try-with-resources`

Quan treballem amb alguns recursos, com ara els **fitxers**, és important tancar-los després d'utilitzar-los.

Per exemple, podem obrir un fitxer amb `FileInputStream`:

```java
FileInputStream fitxer =
        new FileInputStream("dades.txt");
```

Una vegada hem acabat de treballar amb el fitxer, hem de tancar-lo:

```java
fitxer.close();
```

El problema és que una excepció pot produir-se **abans d'arribar al `close()`**. Per això, tradicionalment el tancament del recurs es realitzava dins d'un bloc `finally`.

Java proporciona una forma més senzilla i segura de fer-ho: **`try-with-resources`**.

### Comparació

Suposem que volem obrir un fitxer i llegir el primer byte.

| `try-catch-finally`                                                                                                                                                                                                                                                                                                                                                                                                                                                                        | `try-with-resources`                                                                                                                                                                                                                                     |
| ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------ | -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| `java<br>FileInputStream fitxer = null;<br><br>try {<br>    fitxer = new FileInputStream("dades.txt");<br><br>    int dada = fitxer.read();<br>    System.out.println(dada);<br><br>} catch (IOException e) {<br>    System.out.println("Error amb el fitxer");<br><br>} finally {<br>    if (fitxer != null) {<br>        try {<br>            fitxer.close();<br>        } catch (IOException e) {<br>            System.out.println("Error en tancar");<br>        }<br>    }<br>}<br>` | `java<br>try (FileInputStream fitxer =<br>        new FileInputStream("dades.txt")) {<br><br>    int dada = fitxer.read();<br>    System.out.println(dada);<br><br>} catch (IOException e) {<br>    System.out.println("Error amb el fitxer");<br>}<br>` |

En els dos casos fem el mateix:

```text
obrir el fitxer
      ↓
utilitzar-lo
      ↓
tancar el fitxer
```

La diferència està en **qui s'encarrega de tancar-lo**:

| Forma                | Tancament del fitxer             |
| -------------------- | -------------------------------- |
| `try-catch-finally`  | El programador executa `close()` |
| `try-with-resources` | Java el tanca automàticament     |

### Com funciona?

En `try-with-resources`, el recurs es declara entre els parèntesis del `try`:

```java
try (FileInputStream fitxer =
        new FileInputStream("dades.txt")) {

    // utilitzar el fitxer
}
```

Quan finalitza el bloc `try`, Java **tanca automàticament el recurs**.

És equivalent, de manera simplificada, a executar:

```java
fitxer.close();
```

però sense haver d'escriure nosaltres el codi de tancament.

El més important és que el recurs també es tanca **si es produeix una excepció mentre l'estem utilitzant**.

```text
s'obri el recurs
       ↓
s'utilitza
       ↓
   hi ha error?
    ↙       ↘
   no        sí
    ↘       ↙
  es tanca sempre
       ↓
continua l'execució
```

### Quins recursos podem utilitzar?

Per poder declarar un objecte dins de `try (...)`, la seua classe ha de ser compatible amb:

```java
AutoCloseable
```

Moltes classes que utilitzarem per treballar amb fitxers compleixen este requisit.

Per tant, la idea que cal recordar és:

```text
try-catch-finally
        ↓
hem de gestionar manualment
el tancament del recurs


try-with-resources
        ↓
Java tanca automàticament
el recurs
```

Sempre que treballem amb un recurs que necessita tancar-se i siga compatible amb `AutoCloseable`, **`try-with-resources` és la forma preferible**, perquè simplifica el codi i assegura el tancament del recurs fins i tot si es produeix una excepció.


---

## 8. Excepcions pròpies

Java proporciona moltes classes d'excepcions, però també podem crear **les nostres pròpies excepcions**.

Açò és útil quan volem representar una situació incorrecta específica del nostre programa.

Suposem que una aplicació treballa amb notes entre `0` i `10`.

Podem crear:

```java
public class NotaIncorrectaException extends Exception {

    public NotaIncorrectaException(String missatge) {

        super(missatge);
    }
}
```

La nostra classe:

```java
NotaIncorrectaException
```

hereta de:

```java
Exception
```

Ara podem utilitzar-la en un mètode:

```java
public static void comprovarNota(double nota)
        throws NotaIncorrectaException {

    if (nota < 0 || nota > 10) {

        throw new NotaIncorrectaException(
                "La nota ha d'estar entre 0 i 10"
        );
    }
}
```

### Programa complet

Classe de l'excepció:

```java
public class NotaIncorrectaException extends Exception {

    public NotaIncorrectaException(String missatge) {

        super(missatge);
    }
}
```

Programa:

```java
public class ExempleNota {

    public static void main(String[] args) {

        try {

            comprovarNota(12.5);

            System.out.println("Nota correcta");

        } catch (NotaIncorrectaException e) {

            System.out.println(
                    "Error: " + e.getMessage()
            );
        }
    }

    public static void comprovarNota(double nota)
            throws NotaIncorrectaException {

        if (nota < 0 || nota > 10) {

            throw new NotaIncorrectaException(
                    "La nota ha d'estar entre 0 i 10"
            );
        }
    }
}
```

En este exemple apareixen les tres peces que hem de diferenciar:

```java
class NotaIncorrectaException extends Exception
```

crea **el nostre tipus d'excepció**.

```java
throws NotaIncorrectaException
```

declara que el mètode **pot propagar-la**.

```java
throw new NotaIncorrectaException(...)
```

**crea i llança** l'excepció.

---

# Per acabar

## `getMessage()` i `printStackTrace()`

Quan capturem una excepció:

```java
catch (ArithmeticException e)
```

`e` és l'objecte que representa l'excepció produïda.

Podem utilitzar:

```java
e.getMessage();
```

per obtindre el missatge associat a l'excepció.

Per exemple:

```java
try {

    int resultat = 10 / 0;

} catch (ArithmeticException e) {

    System.out.println(
            "Error: " + e.getMessage()
    );
}
```

També podem utilitzar:

```java
e.printStackTrace();
```

per mostrar informació detallada sobre l'excepció i el recorregut de mètodes que ha portat fins al problema.

```java
public class Exemple {

    public static void dividir(int a, int b) {

        int resultat = a / b;
        System.out.println(resultat);
    }

    public static void main(String[] args) {

        try {

            dividir(10, 0);

        } catch (ArithmeticException e) {

            System.out.println("S'ha produït un error");

            e.printStackTrace();
        }
    }
}
```

`printStackTrace()` és especialment útil **durant el desenvolupament i la depuració**, perquè ajuda a localitzar on s'ha produït el problema. En una aplicació acabada normalment no mostrarem tot el *stack trace* a l'usuari. 

## Quina estructura utilitzem?

| Situació                                   | Solució habitual           |
| ------------------------------------------ | -------------------------- |
| Una operació pot fallar                    | `try-catch`                |
| Hi ha diversos errors diferents            | diversos `catch`           |
| Diversos errors tenen el mateix tractament | multicatch `A \| B`        |
| Un mètode no vol gestionar l'excepció      | `throws`                   |
| Volem provocar una excepció                | `throw`                    |
| Volem crear una excepció pròpia            | classe `extends Exception` |
| Cal executar codi passe el que passe       | `finally`                  |
| Utilitzem un recurs que s'ha de tancar     | `try-with-resources`       |
| Volem consultar el missatge                | `e.getMessage()`           |
| Estem depurant el programa                 | `e.printStackTrace()`      |

### `finally` o `try-with-resources`?

No són el mateix.

|                      | Utilitat                          |
| -------------------- | --------------------------------- |
| `finally`            | Executar determinat codi al final |
| `try-with-resources` | Tancar automàticament un recurs   |

Per tant:

```text
finally
   ↓
executar codi al final


try-with-resources
   ↓
gestionar automàticament
el tancament d'un recurs
```

## Idea que cal recordar

Quan treballem amb excepcions podem fer-nos tres preguntes:

```text
1. QUÈ POT FALLAR?
        ↓
   identificar l'excepció


2. QUI GESTIONA L'EXCEPCIÓ?
        ↓
      catch
        o
      throws


3. UTILITZEM UN RECURS QUE CAL TANCAR?
        ↓
   try-with-resources
```

Amb estes estructures tenim la base necessària per començar a aplicar la gestió d'excepcions als **fitxers i als diferents mecanismes d'accés a dades**.

---
title: Excepcions
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

### Exemple complet

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

Per exemple, en este programa poden produir-se dos problemes diferents:

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

            System.out.println(
                    "Has d'introduir números enters"
            );

        } catch (ArithmeticException e) {

            System.out.println(
                    "No es pot dividir entre zero"
            );

        }
    }
}
```

Si introduïm:

```text
hola
```

`nextInt()` genera:

```text
InputMismatchException
```

En canvi, si introduïm:

```text
10
0
```

la divisió genera:

```text
ArithmeticException
```

Cada `catch` resol **un problema diferent**.

### Regla important

Les excepcions més específiques han d'anar abans que les més generals.

Per exemple:

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

Per això primer intentem capturar:

```java
ArithmeticException
```

i deixem:

```java
Exception
```

com una captura més general.

Sempre que siga possible és preferible capturar **el tipus concret d'excepció**, perquè així podem saber què ha fallat i donar un tractament adequat.

---

## 4. Checked i unchecked exceptions

No totes les excepcions es comporten igual.

Una distinció important en Java és entre excepcions **checked** i **unchecked**.

| Tipus         | Java obliga a tractar-la? | Exemples                                                                                                  |
| ------------- | ------------------------- | --------------------------------------------------------------------------------------------------------- |
| **Checked**   | Sí                        | `Exception` i moltes de les seues subclasses                                                              |
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

Per exemple, quan intentem obrir un fitxer que no existeix:

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

En este exemple hem decidit **capturar-la** amb:

```java
catch (FileNotFoundException e)
```

Una altra possibilitat seria **propagar-la** amb `throws`:

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

En este cas, `obrirFitxer()` no tracta l'excepció. Indica amb `throws` que **pot propagar-la al mètode que l'ha cridat**.

A continuació veurem amb més detall com funciona `throws`.

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

Per a això utilitzem:

```java
throw
```

Per exemple:

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

Per exemple:

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
throw
   ↓
llança l'excepció

throws
   ↓
avisa que el mètode pot propagar-la
```

---

## 6. `finally`

`finally` conté codi que s'executa **tant si es produeix una excepció com si no**.

L'estructura completa és:

```java
try {

    // operació

} catch (Exception e) {

    // tractament de l'excepció

} finally {

    // s'executa al final

}
```

### Exemple complet

```java
public class ExempleFinally {

    public static void main(String[] args) {

        try {

            System.out.println("Inici de l'operació");

            int resultat = 10 / 0;

            System.out.println(
                    "Resultat: " + resultat
            );

        } catch (ArithmeticException e) {

            System.out.println(
                    "No es pot dividir entre zero"
            );

        } finally {

            System.out.println(
                    "Final de l'operació"
            );
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

Si canviem:

```java
int resultat = 10 / 0;
```

per:

```java
int resultat = 10 / 2;
```

també s'executarà:

```java
finally
```

Per tant:

| Situació                 | `try`                       | `catch` | `finally` |
| ------------------------ | --------------------------- | ------- | --------- |
| No hi ha excepció        | S'executa complet           | No      | Sí        |
| Hi ha excepció capturada | Fins que apareix l'excepció | Sí      | Sí        |

`finally` és útil quan tenim codi que **volem executar independentment del resultat de l'operació**.

---

## 7. `try-with-resources`

Java disposa d'una variant especial del `try` denominada **try-with-resources**.

Està pensada per treballar amb **recursos que necessiten tancar-se després d'utilitzar-los**.

La sintaxi és:

```java
try (recurs) {

    // utilitzar el recurs

} catch (Excepcio e) {

    // tractament de l'error

}
```

La característica més important és que el recurs es tanca **automàticament**, fins i tot si es produeix una excepció.

Per poder utilitzar un objecte dins de:

```java
try (...)
```

ha de ser un recurs compatible amb `AutoCloseable`.

No necessitem conéixer encara les classes que utilitzarem més avant. Podem veure el funcionament amb un recurs genèric.

### Exemple complet

Primer creem una classe molt senzilla:

```java
public class Recurs implements AutoCloseable {

    public Recurs() {

        System.out.println("Recurs obert");
    }

    public void utilitzar() {

        System.out.println("Utilitzant el recurs");
    }

    @Override
    public void close() {

        System.out.println("Recurs tancat");
    }
}
```

La classe implementa:

```java
AutoCloseable
```

i, per tant, ha de disposar del mètode:

```java
close()
```

Ara podem utilitzar-la:

```java
public class ExempleRecurs {

    public static void main(String[] args) {

        try (Recurs recurs = new Recurs()) {

            recurs.utilitzar();

        }

        System.out.println("Fi del programa");
    }
}
```

El resultat és:

```text
Recurs obert
Utilitzant el recurs
Recurs tancat
Fi del programa
```

Observa que nosaltres **no hem escrit**:

```java
recurs.close();
```

Java l'ha executat automàticament quan ha finalitzat el `try`.

### Què ocorre si hi ha una excepció?

```java
public class ExempleRecurs {

    public static void main(String[] args) {

        try (Recurs recurs = new Recurs()) {

            recurs.utilitzar();

            int resultat = 10 / 0;

            System.out.println(
                    "Resultat: " + resultat
            );

        } catch (ArithmeticException e) {

            System.out.println(
                    "No es pot dividir entre zero"
            );
        }

        System.out.println("Fi del programa");
    }
}
```

El resultat serà:

```text
Recurs obert
Utilitzant el recurs
Recurs tancat
No es pot dividir entre zero
Fi del programa
```

El punt important és que:

```text
es produeix l'excepció
        ↓
es tanca el recurs
        ↓
s'executa el catch
```

Per tant, `try-with-resources` evita haver de preocupar-nos manualment del tancament dels recursos.

Esta és la idea que interessa dominar ara; més avant veurem per què és especialment útil quan treballem amb recursos d'accés a dades. El document original ja presenta `try-with-resources` com la forma recomanada de gestionar recursos que necessiten tancar-se. 

---

## 8. Multicatch: diverses excepcions amb el mateix tractament

Normalment utilitzem diversos `catch` quan volem actuar de manera diferent davant de cada problema:

```java
try {

    // operacions

} catch (InputMismatchException e) {

    System.out.println("Entrada incorrecta");

} catch (ArithmeticException e) {

    System.out.println("Divisió incorrecta");

}
```

Però pot haver-hi situacions en què volem fer **exactament el mateix** davant de diverses excepcions.

Java permet agrupar-les utilitzant `|`.

### Exemple complet

```java
import java.util.InputMismatchException;
import java.util.Scanner;

public class ExempleMulticatch {

    public static void main(String[] args) {

        Scanner teclat = new Scanner(System.in);

        int[] numeros = {10, 20, 30, 40, 50};

        try {

            System.out.print(
                    "Introdueix una posició: "
            );

            int posicio = teclat.nextInt();

            System.out.println(
                    "Valor: " + numeros[posicio]
            );

        } catch (InputMismatchException |
                 ArrayIndexOutOfBoundsException e) {

            System.out.println(
                    "No s'ha pogut realitzar la consulta"
            );
        }
    }
}
```

El mateix `catch` controla:

```text
InputMismatchException
```

i:

```text
ArrayIndexOutOfBoundsException
```

És equivalent conceptualment a:

```java
catch (InputMismatchException e) {

    System.out.println(
            "No s'ha pogut realitzar la consulta"
    );
}

catch (ArrayIndexOutOfBoundsException e) {

    System.out.println(
            "No s'ha pogut realitzar la consulta"
    );
}
```

### Quan utilitzar multicatch?

Quan **realment volem donar el mateix tractament** a diferents excepcions.

Si volem distingir els problemes, és millor mantindre diversos `catch`:

```java
catch (InputMismatchException e) {

    System.out.println(
            "Has d'introduir un número enter"
    );

} catch (ArrayIndexOutOfBoundsException e) {

    System.out.println(
            "La posició no existeix"
    );
}
```

---

## 9. Excepcions pròpies

Java proporciona moltes classes d'excepcions, però també podem crear **les nostres pròpies excepcions**.

Açò és útil quan volem representar una situació incorrecta específica del nostre programa.

Suposem que una aplicació treballa amb notes entre 0 i 10.

Podem crear:

```java
public class NotaIncorrectaException
        extends Exception {

    public NotaIncorrectaException(
            String missatge) {

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
public class NotaIncorrectaException
        extends Exception {

    public NotaIncorrectaException(
            String missatge) {

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

En este exemple apareixen tres peces importants:

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

## 10. `getMessage()` i `printStackTrace()`

Quan capturem una excepció:

```java
catch (ArithmeticException e)
```

`e` és l'objecte que representa l'excepció produïda.

Podem utilitzar-lo per obtindre informació sobre el problema.

### `getMessage()`

Retorna el missatge associat a l'excepció.

```java
public class Exemple {

    public static void main(String[] args) {

        try {

            int resultat = 10 / 0;

            System.out.println(resultat);

        } catch (ArithmeticException e) {

            System.out.println(
                    "Error: " + e.getMessage()
            );
        }
    }
}
```

Podem utilitzar-lo per mostrar informació més concreta:

```java
System.out.println(
        "S'ha produït un error: "
        + e.getMessage()
);
```

### `printStackTrace()`

Mostra informació detallada sobre l'excepció i el recorregut de mètodes que ha portat fins al problema.

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

            System.out.println(
                    "S'ha produït un error"
            );

            e.printStackTrace();
        }
    }
}
```

`printStackTrace()` és especialment útil **durant el desenvolupament i la depuració**, perquè ajuda a localitzar on s'ha produït el problema.

En canvi, en una aplicació acabada normalment no mostrarem tot el *stack trace* a l'usuari.

---

## 11. Quina estructura utilitzem en cada situació?

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

---

## 12. Comparativa: `finally` i `try-with-resources`

`finally` és una estructura general.

Podem utilitzar-la sempre que necessitem executar determinades instruccions independentment que es produïsca o no una excepció:

```java
try {

    // operació

} catch (Exception e) {

    // tractament

} finally {

    // s'executa al final

}
```

En canvi, si estem treballant amb un **recurs que necessita tancar-se**, Java proporciona una solució específica:

```java
try (Recurs recurs = new Recurs()) {

    // utilització del recurs

} catch (Exception e) {

    // tractament

}
```

En este cas Java gestiona automàticament el tancament.

Per tant, no hem de confondre les dues idees:

```text
finally
   ↓
executar codi al final

try-with-resources
   ↓
gestionar automàticament
el tancament d'un recurs
```

---

## 13. Idea que cal recordar

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

Per exemple:

```java
import java.util.InputMismatchException;
import java.util.Scanner;

public class ExempleFinal {

    public static void main(String[] args) {

        try (Scanner teclat =
                new Scanner(System.in)) {

            System.out.print(
                    "Introdueix el numerador: "
            );
            int a = teclat.nextInt();

            System.out.print(
                    "Introdueix el denominador: "
            );
            int b = teclat.nextInt();

            int resultat = a / b;

            System.out.println(
                    "Resultat: " + resultat
            );

        } catch (InputMismatchException e) {

            System.out.println(
                    "Has d'introduir números enters"
            );

        } catch (ArithmeticException e) {

            System.out.println(
                    "No es pot dividir entre zero"
            );
        }
    }
}
```

Este exemple resumeix bona part del repàs: **el `try` delimita les operacions que poden fallar, cada `catch` tracta un problema concret i `try-with-resources` permet gestionar automàticament un recurs quan correspon**. L'objectiu del repàs original és precisament entendre què fer quan una operació pot fallar. 

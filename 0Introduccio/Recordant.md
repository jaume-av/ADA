---
title: Recordant
layout: default
parent: Descripció del Mòdul
nav_order: 20
has_children: true
has_toc: true
---


# Excepcions en Java — Repàs

Una **excepció** representa un problema que es produeix durant l'execució d'un programa.

```java
int resultat = 10 / 0;
```

En este cas Java genera:

```text
ArithmeticException
```

Si no la controlem, el flux normal del programa s'interromp.

---

## 1. `try-catch`: controlar una excepció

Utilitzem `try` per delimitar el codi que pot fallar i `catch` per indicar què fer si es produeix l'excepció.

```java
try {
    int resultat = 10 / 0;
    System.out.println(resultat);

} catch (ArithmeticException e) {
    System.out.println("No es pot dividir entre zero");
}

System.out.println("Fi");
```

Quan apareix una excepció, Java **abandona la resta del `try` i busca un `catch` compatible**.

```text
        try
         │
    es produeix
    una excepció?
      /       \
    NO         SÍ
    │           │
continua      abandona el try
el try          │
                ▼
              catch
                │
                ▼
        continua el programa
```

### Diversos `catch`

Un `try` pot tindre diferents `catch` per tractar diferents tipus d'excepcions:

```java
try {
    // operacions

} catch (InputMismatchException e) {
    System.out.println("Entrada incorrecta");

} catch (ArithmeticException e) {
    System.out.println("Error aritmètic");
}
```

Si utilitzem excepcions específiques i generals, les **més específiques han d'anar primer**:

```java
catch (ArithmeticException e) {
    ...
} catch (Exception e) {
    ...
}
```

---

## 2. Checked i unchecked

No totes les excepcions obliguen el programador a actuar de la mateixa manera.

|                             | **Unchecked**          | **Checked**                      |
| --------------------------- | ---------------------- | -------------------------------- |
| Java obliga a gestionar-la  | No                     | Sí                               |
| Quan es detecta el problema | Normalment en execució | El compilador exigeix tractament |
| Exemple                     | `ArithmeticException`  | `FileNotFoundException`          |

Una excepció **unchecked** pot quedar sense capturar:

```java
int resultat = 10 / 0;
```

El programa compila, encara que després falle.

Altres exemples habituals són `NullPointerException`, `InputMismatchException` o `ArrayIndexOutOfBoundsException`.

En canvi, davant d'una excepció **checked**, Java ens obliga a fer una de les dues coses:

```text
              EXCEPCIÓ CHECKED
                     │
             ┌───────┴───────┐
             ▼               ▼
         CAPTURAR         PROPAGAR
             │               │
         try-catch          throws
```

Per exemple:

```java
FileInputStream fitxer =
        new FileInputStream("dades.txt");
```

Obrir el fitxer pot produir una `FileNotFoundException`, que Java obliga a gestionar.

---

## 3. Capturar o propagar una excepció

Davant d'una excepció checked hem de decidir **qui s'encarrega de gestionar-la**.

### Capturar

**Capturar** significa gestionar l'excepció en el mateix mètode mitjançant `try-catch`.

```java
public static void obrirFitxer() {

    try {
        FileInputStream fitxer =
                new FileInputStream("dades.txt");

    } catch (FileNotFoundException e) {
        System.out.println("No s'ha trobat el fitxer");
    }
}
```

L'excepció es gestiona ací i no continua cap al mètode que ha fet la crida.

### Propagar

**Propagar** significa no gestionar l'excepció en este mètode i deixar que ho faça el mètode que l'ha cridat.

Utilitzem `throws`:

```java
public static void obrirFitxer()
        throws FileNotFoundException {

    FileInputStream fitxer =
            new FileInputStream("dades.txt");
}
```

El mètode que el crida pot capturar-la:

```java
try {
    obrirFitxer();

} catch (FileNotFoundException e) {
    System.out.println("No s'ha trobat el fitxer");
}
```

Per tant:

```text
CAPTURAR                         PROPAGAR

try-catch                        throws
    │                               │
    ▼                               ▼
la gestione ací          la gestionarà el mètode
                              que m'ha cridat
```

### L'IDE ens ajuda

Quan utilitzem una operació que pot produir una excepció checked, els IDE com **IntelliJ IDEA o Eclipse** detecten que falta gestionar-la.

Normalment ofereixen accions ràpides semblants a:

```text
Surround with try/catch
```

o:

```text
Add exception to method signature
```

És a dir, l'IDE pot generar automàticament el `try-catch` o afegir el `throws`.

Però **no s'ha d'acceptar l'opció automàticament**. Primer cal decidir:

```text
Puc i he de gestionar l'error ací?

        SÍ              NO
        │                │
        ▼                ▼
      catch            throws
```

L'IDE escriu el codi, però **la decisió de capturar o propagar és del programador**.

---

## 4. `throw` i `throws`

Són pareguts de nom, però fan coses diferents.

| `throw`                    | `throws`                                  |
| -------------------------- | ----------------------------------------- |
| **Llança** una excepció    | **Declara** que un mètode pot propagar-la |
| Apareix dins del codi      | Apareix en la signatura                   |
| `throw new Exception(...)` | `metode() throws Exception`               |

Exemple:

```java
public static void comprovarNota(double nota)
        throws Exception {

    if (nota < 0 || nota > 10) {
        throw new Exception("Nota incorrecta");
    }
}
```

Ací:

```java
throws Exception
```

declara que `comprovarNota()` pot **propagar una excepció**.

En canvi:

```java
throw new Exception("Nota incorrecta");
```

és la instrucció que **crea i llança l'excepció**.

Per recordar-ho:

```text
throw  → LLANÇA
throws → DECLARA / PROPAGA
catch  → CAPTURA
```

---

## 5. `finally`

`finally` permet executar un bloc de codi **encara que durant el `try` es produïsca una excepció**.

No és necessari utilitzar-lo simplement per executar una instrucció després d'un `try-catch`. El seu ús tradicional més important és garantir **tasques de neteja o alliberament de recursos**.

Per exemple:

```java
FileInputStream fitxer = null;

try {
    fitxer = new FileInputStream("dades.txt");

    int dada = fitxer.read();

    fitxer.close();

} catch (IOException e) {
    System.out.println("Error");
}
```

El problema és que si `read()` produeix una excepció, Java abandona el `try` i **no arriba a executar `close()`**:

```text
obrir fitxer
     │
     ▼
   read()
     │
   ERROR
     │
     ▼
   catch

close() NO s'executa
```

Podem assegurar el tancament utilitzant `finally`:

```java
FileInputStream fitxer = null;

try {
    fitxer = new FileInputStream("dades.txt");
    int dada = fitxer.read();

} catch (IOException e) {
    System.out.println("Error");

} finally {

    if (fitxer != null) {
        try {
            fitxer.close();

        } catch (IOException e) {
            System.out.println("Error en tancar");
        }
    }
}
```

Ara s'intenta executar `close()` **tant si tot ha funcionat com si s'ha produït una excepció**.

Però apareix un problema: `close()` també pot produir una `IOException`. Per això necessitem un altre `try-catch` dins del `finally`.

El resultat és bastant farragós. Precisament per solucionar este problema tenim `try-with-resources`.

---

## 6. `try-with-resources`

Quan treballem amb recursos que s'han de tancar, Java proporciona una forma més senzilla: **`try-with-resources`**.

L'exemple anterior queda així:

```java
try (FileInputStream fitxer =
        new FileInputStream("dades.txt")) {

    int dada = fitxer.read();

} catch (IOException e) {
    System.out.println("Error");
}
```

El recurs es declara entre els parèntesis del `try`:

```java
try (FileInputStream fitxer =
        new FileInputStream("dades.txt"))
```

Java s'encarrega de **tancar-lo automàticament quan acaba el `try`**, també si es produeix una excepció.

```text
FORMA TRADICIONAL              TRY-WITH-RESOURCES

finally                              try (recurs)
   │                                      │
   ▼                                      ▼
comprovar recurs                       utilitzar
   │                                      │
   ▼                                      ▼
close() manual                    tancament automàtic
   │
   ▼
controlar possible
error del close()
```

Per poder utilitzar un recurs amb `try-with-resources`, ha de ser compatible amb `AutoCloseable`.

**Idea clau:** per als recursos que necessiten tancament, `try-with-resources` evita haver de gestionar manualment el `close()`.

---

## 7. Excepcions pròpies

Java proporciona moltes excepcions, però també podem crear excepcions pròpies per representar problemes específics de la nostra aplicació.

Per exemple:

```java
public class NotaIncorrectaException extends Exception {

    public NotaIncorrectaException(String missatge) {
        super(missatge);
    }
}
```

Ara tenim un nou tipus d'excepció: `NotaIncorrectaException`.

Podem llançar-la:

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

I capturar-la:

```java
try {
    comprovarNota(15);

} catch (NotaIncorrectaException e) {
    System.out.println(e.getMessage());
}
```

`getMessage()` permet obtindre el missatge associat a l'excepció.

Ací podem veure relacionats els conceptes principals:

```text
extends Exception → CREA un tipus d'excepció

throw             → LLANÇA l'excepció

throws            → DECLARA que pot propagar-la

catch             → CAPTURA l'excepció

getMessage()      → OBTÉ el missatge
```

---

# Resum final

| Concepte             | Què cal recordar                                 |
| -------------------- | ------------------------------------------------ |
| **Excepció**         | Problema produït durant l'execució               |
| `try`                | Delimita el codi que pot fallar                  |
| `catch`              | Captura i gestiona una excepció                  |
| Diversos `catch`     | Permeten tractar errors diferents                |
| **Checked**          | Java obliga a capturar-la o propagar-la          |
| **Unchecked**        | Java no obliga a gestionar-la                    |
| **Capturar**         | Gestionar l'excepció ací amb `try-catch`         |
| **Propagar**         | Deixar que la gestione qui ens ha cridat         |
| `throw`              | Llança una excepció                              |
| `throws`             | Declara que un mètode pot propagar-la            |
| `finally`            | Garanteix tasques finals, especialment de neteja |
| `try-with-resources` | Tanca automàticament els recursos                |
| `extends Exception`  | Permet crear una excepció pròpia                 |
| `getMessage()`       | Retorna el missatge de l'excepció                |

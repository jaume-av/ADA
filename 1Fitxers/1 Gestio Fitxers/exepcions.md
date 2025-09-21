

# EXEPCIONS (I)

**Error Sintàctic** → El codi del programa no es correcte, el compilador mostra error i no compila.   

**Error Semàntic** → El codi és vàlid, el compilador no mostra error, però la instrucció és incorrecta en temps d’execució (ex: divisió per 0, posició incorrecta en un array, etc.).   

Els errors semàntics, o errors que es produeixen durant l'execució d’un programa en JAVA s’anomenen **Excepcions**, per tant: 

>**Una excepció és un ERROR SEMÀNTIC que es produeix en temps d’execució.**   

Entre els més comuns trobem:

* Dividir per 0.
* Accedir a una posició d’un array fora dels seus límits.
* Errors en la classe SCANNER (demanar un enter (`nextInt()`) i escriure una cadena de text).
* Un objecte és `NULL` i no ho pot ser.
* Accedir a un fitxer que no existeix o està en un disc dur corrupte.
* Etc.    

Quan el compilador troba una excepció o error d’execució **mostra un missatge d’error** i **finalitza l’execució** del programa, i **llança una excepció** ("Throwing Exception").

Quan es produeix una excepció en Java es crea un **OBJECTE** de la classe `Exception` (les excepcions en Java són objectes) que ens proporcionarà informació sobre l’error i mètodes per a obtindre-la.     
---

## EXEMPLES D’EXEPCIONS   



### 1) Forcem la divisió per zero.    

**Eixida per pantalla:**

> La màquina virtual de JAVA detecta un error en temps d’execució i crea un objecte de la classe `java.lang.ArithmeticException`, i com el mètode on es produeix l’excepció no pot tractar-la acaba el programa en la fila 12    

**Codi (reconstrucció aproximada):**

```java
public class DivZero {
    public static void main(String[] args) {
        int a = 10;
        int b = 0;
        int c = a / b; // <- ArithmeticException: / by zero
        System.out.println("Resultat: " + c);
    }
}
```

### 2) Forcem error en conversió de tipus. (imatge al PDF) 

**Eixida per pantalla:**

> Com `cadena2` no té un format adequat ("Hola" no representa un número vàlid), el mètode `Integer.parseInt(...)` no pot convertir-la a `int` i llança l’excepció **`NumberFormatException`**. La Màquina Virtual Java finalitza el programa a la línia 13 i mostra per pantalla la informació sobre l’excepció.&#x20;

**Codi:**

```java
public class Exemple2 {
    public static void main(String[] args) {
        String cadena1 = "123";
        String cadena2 = "Hola";
        int x = Integer.parseInt(cadena1);
        int y = Integer.parseInt(cadena2); // <- NumberFormatException
        System.out.println(x + y);
    }
}
```

### 3) Forcem els límits d’un vector. (imatge al PDF)   

**Eixida per pantalla:**

> Com intentem accedir a la posició 5 del vector i sols té 4, es produeix **`ArrayIndexOutOfBoundsException`**. La Màquina Virtual de Java finalitza el programa a la línia 11 i mostra el missatge d’error.&#x20;

**Codi:**

```java
public class Exemple3 {
    public static void main(String[] args) {
        int[] v = {1,2,3,4};
        System.out.println(v[5]); // <- ArrayIndexOutOfBoundsException
    }
}
```

---

## GESTIONAR EXEPCIONS (TRY–CATCH–FINALLY)

En Java es poden gestionar excepcions utilitzant tres mecanismes anomenats **gestors d'excepcions**, que funcionen conjuntament:

* **Bloc `try` (intentar):** codi que podria llançar una excepció.
* **Bloc `catch` (capturar):** codi que manejarà l'excepció si és llançada.
* **Bloc `finally` (finalment):** codi que s'executa tant si hi ha excepció com si no.&#x20;

Un gestor d'excepcions és un bloc de codi encarregat de tractar les excepcions per intentar recuperar-se i evitar que siga llançada descontroladament fins a `main()` i acabe l’execució.&#x20;

**Sintaxi:**    

```java
try {
    // Instruccions que podrien llançar una exepció
} catch (TipusExcepció nomVariable) {
    // Instruccions que s'executen quant ‘try’ llança una excepció.
} finally {
    // Instruccions que s'executen tant si hi ha exepció com no (SEMPRE)
}
```

* El bloc `try` intentarà executar el codi. Si es produeix una excepció, s’abandona aquest bloc i es salta a `catch`. Si en el `try` no es produeix cap excepció, el `catch` s'ignora.
* El bloc `catch` **capturarà** excepcions del tipus indicat, i es poden especificar **diversos `catch`** per a diferents tipus.
* El bloc `finally` és **opcional** i s’executarà **tant si s’ha llançat una excepció com si no**.    

**Exemple Divisió per Zero (amb try–catch):**     

```java
try {
    int a = 10, b = 0;
    int c = a / b; // ArithmeticException
} catch (ArithmeticException e) {
    System.out.println("Has intentat dividir per 0");
} finally {
    System.out.println("Fi del programa");
}
```

---

## Clàusula `catch` (múltiples `catch`)    

* **Objectiu:** resoldre la condició excepcional perquè el programa puga continuar.
* **Múltiples `catch`:** es poden especificar diversos `catch`, cadascun per a un tipus diferent d’excepció.
* Quan es llança una excepció dins del `try`, es comprova cada `catch` **en ordre** i s'executa el **primer que coincideix**. Els altres s'ignoren. Després s'executarà el `finally` (si existeix) i el programa continua.
* Si el tipus d’excepció **no coincideix** amb cap `catch`, l’excepció serà **llençada al mètode cridador**; si tampoc es resol, error i finalització.&#x20;

**Exemple (múltiples `catch`)** – al PDF hi ha codi i diverses eixides; transcrivim idees clau:

* Cas 1: el `try` s'executa sense excepcions → s’ignoren els `catch` i s’imprimeix "Fi del programa".
* Cas 2: es produeix divisió per 0 → salta al primer `catch` (`ArithmeticException`) i després "Fi del programa".
* Cas 3: es produeix accés fora de límits → salta al segon `catch` (`ArrayIndexOutOfBoundsException`) i després "Fi del programa".&#x20;

> **Nota del PDF:**
>
> * Si només captures `ArrayIndexOutOfBoundsException` i es produeix `ArithmeticException`, **no** es gestiona i el programa finalitza amb error.
> * El cas més general és `catch (Exception e)` que **captura TOTES** les excepcions (totes hereten d’`Exception`). **No és bona idea** perquè no sabrem quin tipus d’error ha passat. Encara que el programa no es pare, continuarà amb possibles errors en les dades o en l’execució.
> * En dues eixides distintes (divisió per 0 o fora de límits) si captures només `Exception`, veus **el mateix missatge**: senyal que **no** s’estan gestionant bé.&#x20;

> També: un `catch (ArithmeticException e)` **capturarà** excepcions **heretades** del tipus indicat. (Exemple de jerarquia).&#x20;

---

## Jerarquia d’Excepcions en JAVA (diagrama amb fletxes al PDF)&#x20;

> Al PDF hi ha un **diagrama amb fletxes**. Representació textual aproximada:

```
Throwable
└─ Exception
   ├─ (Excepcions comprovades: p.ex. ClassNotFoundException, IOException, etc.)
   └─ RuntimeException (no comprovades)
      ├─ ArithmeticException
      ├─ ArrayIndexOutOfBoundsException
      ├─ NullPointerException
      ├─ NumberFormatException
      └─ ...
```

**Excepcions comprovades**: Java les comprova **en compilació**.
**Excepcions no comprovades**: Java no les pot comprovar en compilació; apareixen en **execució**.&#x20;

**Exemples *comprovades* (de `java.lang` al PDF):**

* `ClassNotFoundException` – No s’ha trobat la classe.
* `CloneNotSupportedException` – Duplicat d’un objecte que no implementa `Cloneable`.
* `IllegalAccessException` – Accés denegat a una classe.
* `InstantiationException` – Intent de crear un objecte d’una classe abstracta o interfície.
* `InterruptedException` – Fil interromput per un altre fil.
* `NoSuchFieldException` – El camp sol·licitat no existeix.
* `NoSuchMethodException` – El mètode sol·licitat no existeix.&#x20;

**Subclasses de `RuntimeException` (no comprovades) al PDF:**

* `AritmeticException` (sic al PDF) – Error aritmètic com divisió entre zero.
* `ArrayIndexOutOfBoundsException` – Índex de la matriu fora de límit.
* `ArrayStoreException` – Assignació a una matriu de tipus incompatible.
* `ClassCastException` – Conversió invàlida.
* `IllegalArgumentException` – Ús invàlid d’un argument en un mètode.
* `IllegalMonitorStateException` – Operació de monitor invàlida.
* `IllegalStateException` – Estat incorrecte.
* `IllegalThreadStateException` – Operació incompatible amb l’estat del fil.
* `IndexOutOfBoundException` – Índex fora de rang.
* `NegativeArraySizeException` – Mida negativa.
* `NullPointerException` – Ús de referència `NULL`.
* `NumberFormatException` – Conversió de cadena a número incorrecta.
* `SecurityException` – Violació de seguretat.
* `StringIndexOutBounds` – Límits d’una cadena sobrepassats.
* `TypeNotPresentException` – Tipus no trobat.
* `UnsupportedOperationException` – Operació no admesa.&#x20;

---

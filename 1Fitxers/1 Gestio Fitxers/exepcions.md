

# Excepcions en Java

## 0) Per què necessitem excepcions?

**Què és una excepció?**
Un **problema que ocorre durant l’execució** i **trenca el flux normal** si no es tracta (dividir per 0, índex fóra de rang, lectura invàlida…).

**Com funciona internament?**
Java crea i *llança* un **objecte d’excepció** (p. ex. `new ArithmeticException("/ by zero")`). Si ningú el captura, **el programa acaba** i mostra una traça d’error.

**Per a què valen?**
Per **detectar i manejar** errors reals (inputs, fitxers, xarxes…) sense que el programa isca de colp; podem **explicar** què ha passat i **continuar** amb seguretat.

**Diferència amb errors de compilació**

* *Compilació:* el codi ni s’executa (falta `;`, variable no declarada).
* *Execució (excepcions):* el codi compila, però peta en marxa (usuari escriu “hola” on tocava un enter, etc.).

---

## 1) Jerarquia i tipus d’excepcions (idea essencial)

```
Throwable
├─ Error                 (greus de la JVM → no solem tractar-los)
└─ Exception
   ├─ (Checked)   COMPROVADES → el compilador obliga a gestionar o propagar
   └─ (Unchecked) NO COMPROVADES (RuntimeException i filles) → no obliga
```

**No comprovades (unchecked)** més comunes a classe:

* `ArithmeticException` (dividir per 0)
* `ArrayIndexOutOfBoundsException` (índex fóra de 0..len-1)
* `InputMismatchException` (Scanner: esperes enter i posen text)
* `NumberFormatException` (`Integer.parseInt("hola")`)
* `NullPointerException` (usar una referència `null`)

**Comprovades (checked)** (E/S, xarxa…):

* `IOException`, `FileNotFoundException`, etc. → el compilador **exigeix** `try/catch` o `throws`.

---

## 2) Estructura bàsica: `try` – `catch` – `finally`

```java
try {
    // codi “riscós”
} catch (TipusExcepcio e) {
    // reacció a un error concret
} finally { // opcional
    // s'executa sempre (tanque recursos, missatge final…)
}
```

* **Múltiples `catch`**: d’específic → general.
* **`finally`**: ideal per a *tancar* o *netejar* encara que hi haja error.

---

## 3) Scanner i el buffer

**Problema típic:** `nextInt()` i l’usuari escriu “hola” → **`InputMismatchException`** i **queden caràcters en el buffer**.
**Solució:** al `catch`, crida **`in.nextLine()`** per **netejar**.

**Exemple 1 — Llegir un enter amb reacció a error**

```java
import java.util.*;

public class Ex1_EntradaEnter {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int a;

        try {
            System.out.print("Introdueix un enter: ");
            a = in.nextInt(); // pot llançar InputMismatchException
            System.out.println("Valor introduït: " + a);
        } catch (InputMismatchException e) {
            System.out.println("Valor introduït incorrecte.");
            in.nextLine(); // ← Neteja del token invàlid
        }

        System.out.println("Fi del programa.");
    }
}
```

**Eixida (si escric “hola”):**

```
Introdueix un enter: hola
Valor introduït incorrecte.
Fi del programa.
```

---

## 4) Tractar dos problemes independents (lectura i divisió per 0)

**Exemple 2 — Demanar A i B, calcular A/B**

```java
import java.util.*;

public class Ex2_DivisioAB {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        try {
            System.out.print("Numerador (A): ");
            int A = in.nextInt();  // pot fallar si no és enter

            System.out.print("Denominador (B): ");
            int B = in.nextInt();  // pot fallar si no és enter

            int R = A / B;         // ArithmeticException si B == 0
            System.out.println(A + " / " + B + " = " + R);

        } catch (InputMismatchException e) {
            System.out.println("Valor introduït incorrecte (cal un enter).");
            in.nextLine(); // neteja
        } catch (ArithmeticException e) {
            System.out.println("Divisió entre zero (B no pot ser 0).");
        }

        System.out.println("Fi del programa.");
    }
}
```

* **Cas B=0**: salta al `catch(ArithmeticException)` i no peta el programa.
* **Cas “hola”**: salta a `catch(InputMismatchException)` i neteja el buffer.

---

## 5) Reintents i arrays: omplir un `double[5]` encara que hi haja errors

**Exemple 3 — Patró `i--` per a repetir la mateixa posició**

```java
import java.util.*;

public class Ex3_VectorDouble {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        double[] v = new double[5];

        for (int i = 0; i < v.length; i++) {
            try {
                System.out.print("Valor posició " + i + ": ");
                v[i] = in.nextDouble(); // pot fallar
            } catch (InputMismatchException e) {
                System.out.println("Valor introduït incorrecte. Torna-ho a provar.");
                in.nextLine(); // neteja buffer
                i--;           // ← REPETIR la mateixa posició
            }
        }

        mostrar(v);
    }

    static void mostrar(double[] v) {
        System.out.print("Dades del vector [ ");
        for (double x : v) System.out.print(x + ", ");
        System.out.println("\b\b ]"); // lleva la coma i l’espai final
    }
}
```

---

## 6) Índexs i “sentinelles” d’eixida (negatiu per a acabar)

**Exemple 4 — Vector `int[N]` + consulta fins que pos siga negatiu (amb comprovació prèvia)**

```java
import java.util.*;

public class Ex4_ConsultaVector {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        int N = (int)(Math.random() * 100 + 1);
        int[] v = new int[N];
        for (int i = 0; i < N; i++) v[i] = (int)(Math.random() * 10 + 1);

        int pos = 0;

        do {
            try {
                System.out.print("Posició a mostrar (0.." + (N-1) + ", negatiu per eixir): ");
                pos = in.nextInt(); // pot fallar (no enter)

                // Comprovació PRÈVIA del negatiu ABANS d'accedir
                if (pos >= 0) {
                    System.out.println("v[" + pos + "] = " + v[pos]); // pot llançar AIOOBE
                    in.nextLine(); // neteja
                }

            } catch (InputMismatchException e) {
                System.out.println("Valor introduït incorrecte (cal un enter).");
                in.nextLine(); // neteja
            } catch (ArrayIndexOutOfBoundsException e) {
                if (pos >= 0) {
                    System.out.println("Índex fora de límits. Rang: 0.." + (N - 1));
                }
            }
        } while (pos >= 0);

        System.out.println("Eixida del programa.");
    }
}
```

* **Sentinella**: negatiu = **acabar** (no és error).
* **Comprovació prèvia**: si és negatiu, **no** accedisc al vector → evite excepcions.

---

## 7) `finally`: codi que s’executa sempre

```java
Scanner in = new Scanner(System.in);
try {
    // … lectures i càlculs
} catch (InputMismatchException e) {
    System.out.println("Entrada invàlida.");
    in.nextLine();
} finally {
    System.out.println("Finalitzant…");
    // en exercicis de consola, sovint NO tanquem System.in
}
```

---

## 8) Ordre dels `catch`: específic → general

```java
try {
    // ...
} catch (NumberFormatException e) { // específic
    // ...
} catch (Exception e) {             // general
    // ...
}
```

Si poses `catch (Exception e)` primer, els específics **no s’executaran mai** (codi inassolible).

---

## 9) **`throw` i `throws`** 

### 9.1 Què és **`throw`**?

* **Acció immediata:** *llança* una excepció **ara mateix** quan es dona una condició que **no** vull permetre.
* **Per a què?** Per **validar** paràmetres/estat i forçar que el codi que crida **tracte o no permeta** eixa situació.

**Sintaxi bàsica**

```java
if (condicioErronia) {
    throw new TipusExcepcio("Missatge explicatiu");
}
```

**Exemple A — Validació d’arguments**

```java
public static int dividir(int a, int b) {
    if (b == 0) {
        // Excepció NO comprovada (unchecked): el compilador no obliga a catch
        throw new IllegalArgumentException("b no pot ser zero");
    }
    return a / b;
}

public static void main(String[] args) {
    System.out.println(dividir(8, 2));  // 4
    System.out.println(dividir(8, 0));  // ← llança IllegalArgumentException
}
```

**Fil d’execució (cas b=0):** en `dividir` es detecta la condició i es *llança* → si `main` no la captura, el programa **acaba** amb traça d’error.
**Quan capturar ací?** Depén: si vols mostrar un missatge amable i continuar, captura a `main`:

```java
try {
    System.out.println(dividir(8, 0));
} catch (IllegalArgumentException e) {
    System.out.println("Error: " + e.getMessage());
}
```

**Exemple B — Sanititzar entrada abans de continuar**

```java
public static int parseEdat(String s) {
    int edat;
    try {
        edat = Integer.parseInt(s);
    } catch (NumberFormatException e) {
        throw new IllegalArgumentException("L'edat ha de ser un enter", e);
    }
    if (edat < 0) {
        throw new IllegalArgumentException("L'edat no pot ser negativa");
    }
    return edat;
}
```

* Ací convertim qualsevol error de format o valor en **una sola excepció coherent** (`IllegalArgumentException`) amb **missatge clar**.

---

### 9.2 Què és **`throws`**?

* **Promesa/Anunci** a la **signatura** d’un mètode: “**aquest mètode pot llançar** aquesta/es excepció/ns i **NO** les gestione ací”.
* **Per a què?**

  * En **excepcions comprovades (checked)**, el compilador **t’obliga** a *o bé* capturar *o bé* **propagar** amb `throws`.
  * En **unchecked**, també pots anunciar-les amb `throws` (per documentar), però **no és obligatori**.

**Sintaxi**

```java
// Exemple amb excepció COMPROVADA (checked): IOException
static void lligFitxer(String ruta) throws IOException {
    try (BufferedReader br = new BufferedReader(new FileReader(ruta))) {
        String linia;
        while ((linia = br.readLine()) != null) {
            System.out.println(linia);
        }
    } // try-with-resources tanca automàticament
}
```

**On la tracte?** En qui **crida**:

```java
public static void main(String[] args) {
    try {
        lligFitxer("dades.txt"); // pot llançar IOException → l’he de capturar
    } catch (IOException e) {
        System.out.println("No s'ha pogut llegir el fitxer: " + e.getMessage());
    }
}
```

**Resum `throw` vs `throws`**

* **`throw`** → *executa ara* una excepció (dins del cos del mètode).
* **`throws`** → *anuncia* a la signatura que el mètode **pot** llançar una excepció i **no la gestiona** ací (qui crida, que la gestione).

---

### 9.3 Propagació: qui s’encarrega?

1. Passa una condició errònia → `throw` (o un mètode intern llança una checked).
2. Si el mètode **no la captura**, i és **checked**, ha de posar-la en **`throws`**.
3. Qui crida **ha de** capturar-la (o tornar a propagar).
4. Si ningú la captura fins a `main`, el programa **acaba** amb traça.

---

### 9.4 Exemples `throw/throws`

**Exemple C — `throws` amb fitxers (checked)**

```java
import java.io.*;

public class ExThrowsFitxer {
    static int comptaLinies(String ruta) throws IOException {
        int count = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(ruta))) {
            while (br.readLine() != null) count++;
        }
        return count;
    }

    public static void main(String[] args) {
        try {
            int n = comptaLinies("dades.txt");
            System.out.println("Línies: " + n);
        } catch (IOException e) {
            System.out.println("Error d'entrada/ixida: " + e.getMessage());
        }
    }
}
```

**Si el fitxer no existeix:**
`FileNotFoundException` (subclasse d’`IOException`) → missatge d’error controlat al `catch` del `main`.

**Exemple D — `throw` per validar paràmetres (unchecked)**

```java
public static int percentatge(int total, int part) {
    if (total <= 0) throw new IllegalArgumentException("total ha de ser > 0");
    if (part < 0)   throw new IllegalArgumentException("part no pot ser negativa");
    return part * 100 / total; // pot llançar ArithmeticException si total==0 (ja evitat)
}

public static void main(String[] args) {
    try {
        System.out.println(percentatge(50, 20)); // 40
        System.out.println(percentatge(0, 10));  // ← llança IllegalArgumentException (missatge clar)
    } catch (IllegalArgumentException e) {
        System.out.println("Paràmetres invàlids: " + e.getMessage());
    }
}
```

---

## 10) “Què passa exactament?” (mini traça de flux)

```java
try {
    int x = Integer.parseInt("hola"); // NumberFormatException
    System.out.println(x);            // ← no s'arriba a executar
} catch (NumberFormatException e) {
    System.out.println("No és número");
}
System.out.println("Continue");
```

**Flux:** entra en `try` → excepció → salta al `catch` corresponent → “No és número” → continua → “Continue”.

---

## 11) Bones pràctiques (resum curt i útil)

* **Missatges concrets** per a cada `catch`.
* **Neteja el buffer** de `Scanner` després d’un `InputMismatchException` (`nextLine()`).
* **Comprova** abans d’accedir a l’array o dividir.
* **Ordre dels `catch`**: específic → general.
* **`finally` o try-with-resources** per a tancar **recursos**.
* **`throw`** per validar; **`throws`** per propagar (sobretot checked).
* Evita `catch(Exception e)` si pots ser específic (didàcticament és molt millor).

---

## 12) Exemple COMPLET (integra tot) — **comentat línia a línia**

*(Aquest exemple ja el tenies; el mantinc intacte i comentat perquè siga guia de referència. No lleve res.)*

```java
import java.util.*;

public class ExempleIntegratComentat {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        // (1) LLEGIR A i B amb reintents (evitem InputMismatchException persistent)
        Integer A = null, B = null;

        while (A == null) {
            try {
                System.out.print("Introdueix A (enter): ");
                A = in.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("A no és un enter. Torna-ho a provar.");
                in.nextLine();
            }
        }

        while (B == null) {
            try {
                System.out.print("Introdueix B (enter): ");
                B = in.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("B no és un enter. Torna-ho a provar.");
                in.nextLine();
            }
        }

        // (2) DIVISIÓ: provem i capturem divisió per zero
        try {
            int R = A / B; // ArithmeticException si B == 0
            System.out.println(A + " / " + B + " = " + R);
        } catch (ArithmeticException e) {
            System.out.println("No es pot dividir per zero (B=0).");
        }

        // (3) VECTOR aleatori 1..100, valors 1..10
        int N = (int) (Math.random() * 100 + 1);
        int[] v = new int[N];
        for (int i = 0; i < N; i++) v[i] = (int) (Math.random() * 10 + 1);

        // (4) CONSULTA: fins negatiu; comprovació prèvia del negatiu
        int pos = 0;
        do {
            try {
                System.out.print("Posició a mostrar (0.." + (N - 1) + ", negatiu per eixir): ");
                pos = in.nextInt();

                if (pos >= 0) {
                    System.out.println("v[" + pos + "] = " + v[pos]); // AIOOBE si fóra de rang
                    in.nextLine();
                }

            } catch (InputMismatchException e) {
                System.out.println("Cal un enter (has escrit un valor no numèric).");
                in.nextLine();
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("Índex fora de límits. Rang vàlid: 0.." + (N - 1));
            }
        } while (pos >= 0);

        System.out.println("Eixida del programa.");
    }
}
```

---

## 13) Exercicis finals (amb `throw/throws`)

1. **Validador d’edats amb `throw`**
   Escriu `int edatValida(String s)` que convertisca `s` a `int` i **llance** `IllegalArgumentException` si no és enter o si és negativa. Prova-ho amb diversos valors i **captura** al `main` per a mostrar missatges clars.

2. **Lectura de fitxer amb `throws`**
   Escriu `int comptaParaules(String ruta) throws IOException` que llija línies i compte paraules (separades per espais). Captura al `main` i mostra el recompte o l’error.

3. **Percentatge segur**
   `int percentatge(int total, int part)` que **llance** `IllegalArgumentException` si `total<=0` o `part<0`. Des del `main`, demana total/part amb `Scanner`; si hi ha error, **torna a demanar** només el camp erroni.

4. **Consulta robusta d’array**
   Reusa l’Ex.4: afig al principi missatge amb **rang \[0..N-1]** i **N**. Gestiona `InputMismatchException` i `ArrayIndexOutOfBoundsException` com abans; negatiu per eixir.

---

### Resum (idees que has d’haver fixat ara)

* Què és una **excepció**, quan passa i com **canvia el flux**.
* Ús de **`try`/`catch`/`finally`**, **ordre** dels `catch`.
* Tractament d’**InputMismatch**, **Arithmetic**, **ArrayIndexOutOfBounds**, **NumberFormat**, **NullPointer**.
* **Buffer** de `Scanner` i `nextLine()` al `catch`.
* **Comprovacions prèvies** (índexs, divisor).
* **`try-with-resources`** per a tancar recursos automàticament.
* **`throw`** (validació immediata) i **`throws`** (propagació, sobretot per *checked*).
* Exemples **comentats** i **eixides** per a seguir el fil real d’execució.

Si vols, et genere també la **versió HTML** amb ressaltat tipus IntelliJ i botons “Copiar”, 1:1 amb estos apunts.

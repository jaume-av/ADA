

# Exercicis amb FLUXOS DE BYTES (InputStream / OutputStream)

## 1) Comptador de bytes i blocs (mitjà)

**Objectiu:** llegir `Documentos/numeros.txt` en cru i contar quants bytes té, i quants blocs de 4 KB s’han llegit.
**Streams i capes:**

* `FileInputStream` → **base** de bytes (accés a fitxer).
* `BufferedInputStream` → **decorador** per reduir E/S física i llegir en blocs (millor rendiment).
  **Per què:** no ens importa l’encoding; volem mesurar bytes reals.

**Resultat en consola:** “Bytes totals: X ; Blocs (4 KB): Y”.

---

## 2) Copiadora binària amb *checksum* (mitjà)

**Objectiu:** copiar `Documentos/pi-million.txt` a `Documentos/pi-million.copy` calculant un **checksum** simple (p. ex. suma modular d’1 byte) durant la còpia.
**Streams i capes:**

* `FileInputStream` → `BufferedInputStream` (lectura eficient).
* `FileOutputStream` → `BufferedOutputStream` (escriptura eficient).
  **Per què:** és una còpia **binària**; no hi ha text ni codificacions.

**Resultat en consola:** “Checksum (mod 256): Z”.

---

## 3) Troba un patró de bytes (alt)

**Objectiu:** cercar l’aparició d’un **subarray de bytes** (p. ex. la seqüència corresponent a “12345”) dins de `Documentos/pi-million.txt` i mostrar la **primera posició** (índex byte) on apareix.
**Streams i capes:**

* `FileInputStream` → `BufferedInputStream`.
  **Per què:** la cerca és sobre bytes (no text). Implementa un **algorisme de cerca** eficient (p. ex. KMP sobre bytes o una finestra lliscant).

**Resultat:** “Patró trobat a l’offset N” o “No trobat”.

---

## 4) Paquets binaris amb primitives (mitjà)

**Objectiu:** a partir de `Documentos/numeros.txt` (una seqüència de nombres enters en línies), crea un fitxer **binari** `Documentos/numeros.dat` que escriga: primer un `int` amb el recompte, i després cada valor com a `int`.
Després, en un segon programa, llig `Documentos/numeros.dat` i mostra la mitjana.
**Streams i capes:**

* Escriptura: `FileOutputStream` → `BufferedOutputStream` → `DataOutputStream`.
* Lectura: `FileInputStream` → `BufferedInputStream` → `DataInputStream`.
  **Per què:** guardem i recuperem **primitives** en **binari** de manera segura i eficient.

---

## 5) “Split” binari per mida (mitjà-alt)

**Objectiu:** partir `Documentos/pi-million.txt` en trossos de, p. ex., **1 MB**: `pi-1.bin`, `pi-2.bin`, … en `Documentos/parts/`.
**Streams i capes:**

* Lectura: `FileInputStream` → `BufferedInputStream`.
* Escriptura: `FileOutputStream` → `BufferedOutputStream`.
  **Per què:** operem amb **mida fixa en bytes**. No és text.

**Detall:** crea la carpeta `Documentos/parts/` si no existix.

---

## 6) Filtre binari de “línies” (alt)

**Objectiu:** sense interpretar encodings, escriu un programa que **conte salts de línia** (bytes `\n`) en `Documentos/usa_personas.txt` i genere `Documentos/line-index.dat` amb offsets (longs) on comença cada línia.
Després, un segon programa que, donat un **número de línia**, use `line-index.dat` per a **posicionar-se** i extraure la línia en cru.
**Streams i capes:**

* Lectura i indexat: `FileInputStream` → `BufferedInputStream`.
* Escriptura índex: `FileOutputStream` → `BufferedOutputStream` → `DataOutputStream` (guarda `long` offsets).
* Lectura ràpida per línia: `FileInputStream` amb `skip()` + buffer propi.
  **Per què:** exercita **aleatorietat** simple i **primitives** binàries.

---

# Exercicis amb FLUXOS DE CARÀCTERS (Reader / Writer)

## 7) Mínim, màxim i mitjana (mitjà)

**Objectiu:** llegir **text** de `Documentos/numeros.txt` i mostrar mínim, màxim i mitjana.
**Streams i capes (ordre recomanat):**

* `FileInputStream` → `InputStreamReader(UTF-8)` → `BufferedReader`.
  **Per què:** és **text**; controlem **encoding** i llegim línies amb `readLine()`.

---

## 8) Notes d’alumnes ordenades (mitjà)

**Objectiu:** llegir `Documentos/alumnos_notas.txt`, calcular la **nota mitjana** per alumne i mostrar-les **ordenades** de major a menor.
**Streams i capes:**

* `FileInputStream` → `InputStreamReader(UTF-8)` → `BufferedReader` (línies).
* (Opcional d’eixida) `FileOutputStream` → `OutputStreamWriter(UTF-8)` → `BufferedWriter` → `PrintWriter` per a exportar `Documentos/alumnos_medias.txt` amb `printf`.
  **Per què:** processem **text** i volem `readLine()` + format agradable.

---

## 9) Ordenar fitxer alfabèticament (mitjà)

**Objectiu:** demanar a l’usuari un **fitxer d’entrada** (p. ex. `Documentos/usa_personas.txt`) i un **fitxer d’eixida**, llegir totes les línies, **ordenar-les** alfabèticament i escriure el resultat.
**Streams i capes:**

* In: `FileInputStream` → `InputStreamReader(UTF-8)` → `BufferedReader`.
* Out: `FileOutputStream` → `OutputStreamWriter(UTF-8)` → `BufferedWriter`.
  **Per què:** ordenar **text**; important mantindre `UTF-8`.

---

## 10) Generador de noms (mitjà)

**Objectiu:** demanar **quants** noms generar i a quin fitxer escriure (p. ex. `Documentos/usa_personas.txt`). Barreja aleatòria d’un nom de `Documentos/usa_nombres.txt` i un cognom de `Documentos/usa_apellidos.txt`.
**Streams i capes:**

* Lectura llistes: `FileInputStream` → `InputStreamReader(UTF-8)` → `BufferedReader`.
* Escriptura: `FileOutputStream` → `OutputStreamWriter(UTF-8)` → `BufferedWriter`.
  **Per què:** tot és **text**; `newLine()` per línies portables.

---

## 11) Diccionari per lletres (mitjà-alt)

**Objectiu:** crear la carpeta `Documentos/Diccionario/` i, per a cada lletra **A..Z**, un fitxer (A.txt, …, Z.txt) amb les paraules de `Documentos/diccionario.txt` que **comencen** per eixa lletra (ignorant majúscules/minúscules).
**Streams i capes:**

* In: `FileInputStream` → `InputStreamReader(UTF-8)` → `BufferedReader`.
* Out: **per a cada lletra** `FileOutputStream` → `OutputStreamWriter(UTF-8)` → `BufferedWriter`.
  **Per què:** dades textuals, `UTF-8`, i `newLine()`.

---

## 12) Cerca de subcadena en Pi (alt)

**Objectiu:** demanar un número (com a **cadena** de longitud qualsevol) i dir si apareix al **primer milió de decimals** en `Documentos/pi-million.txt`. **Sense** emprar biblioteques de cerca de subcadenes: implementa tu el KMP / Boyer–Moore.
**Streams i capes:**

* `FileInputStream` → `InputStreamReader(UTF-8)` → `BufferedReader`.
  **Per què:** el patró que cerquem és **text** (digits com a caràcters), i volem tractar-lo com a tal amb encoding controlat.

---

## 13) Freqüència de paraules i *stopwords* (alt)

**Objectiu:** en `Documentos/frases.txt`, calcular la **freqüència** de paraules, ignorant **majúscules** i puntuació, i filtrant **stopwords** (donades en una llista simple dins del codi). Exporta un `Documentos/frases_freq.csv` amb “paraula;compte”.
**Streams i capes:**

* In: `FileInputStream` → `InputStreamReader(UTF-8)` → `BufferedReader`.
* Out: `FileOutputStream` → `OutputStreamWriter(UTF-8)` → `BufferedWriter` → (opcional) `PrintWriter` per a format CSV.
  **Per què:** processament **textual** + export **text**.

---

## 14) Índex invertit simple (alt)

**Objectiu:** construir un **índex invertit** de `Documentos/usa_personas.txt` (paraula → llistat de línies on apareix). Desa’l a `Documentos/invertido.txt` amb el format:
`Paraula -> l1,l2,l3` (línies ordenades sense duplicats).
**Streams i capes:**

* Lectura: `FileInputStream` → `InputStreamReader(UTF-8)` → `BufferedReader`.
* Escriptura: `FileOutputStream` → `OutputStreamWriter(UTF-8)` → `BufferedWriter`.
  **Per què:** és processament **de text** amb línies i paraules.

---

# Notes generals (per a tots)

* **Patró Decorator (ordre recomanat):**
  **Base** (`FileInputStream`/`FileOutputStream`) → **Buffer/Primitives** (`Buffered*`, `Data*`) → **Pont a text** (`*StreamReader/Writer(UTF-8)`) → **Decoradors de text** (`BufferedReader/Writer`) → **Format** (`PrintWriter` si cal).
* **Un sol buffer per direcció** sol ser suficient.
* En **text**, sempre **encoding explícit** (`UTF-8`).
* Usa **try-with-resources** i captura/propaga **IOException** segons convinga.
* Treballa assumint que tots els fitxers estan a la carpeta **`Documentos/`**.




## Plantilles

perfecte — fem-ho en dos passos com demanes: primer et deixe els **esquelets** (amb imports, try-with-resources i TODOs), i tot seguit t’he creat els **.java amb solucions** llestos per descarregar.

---

# Esquelets (plantilles) per als 14 exercicis

> Tots assumeixen l’estructura **`Documentos/…`** i els fitxers que m’has passat.

## BYTES (nivell mitjà i algun alt)

### 1) Comptar bytes i blocs (4 KB) — *`FileInputStream` → `BufferedInputStream`*

```java
import java.io.*;

public class BytesCount {
    private static final int BLOCK = 4096;
    public static void main(String[] args) {
        File src = new File("Documentos/numeros.txt"); // TODO: canvia si cal
        long totalBytes = 0, blocks = 0;
        try (BufferedInputStream in = new BufferedInputStream(new FileInputStream(src))) {
            byte[] buf = new byte[BLOCK];
            int n;
            while ((n = in.read(buf)) != -1) {
                // TODO: sumar n a totalBytes i incrementar blocks
            }
            // TODO: imprimir totals
        } catch (IOException e) { /* TODO: gestió d’errors */ }
    }
}
```

### 2) Còpia binària + checksum — *`BufferedInputStream` + `BufferedOutputStream`*

```java
import java.io.*;

public class BinaryCopyChecksum {
    public static void main(String[] args) {
        File inFile  = new File("Documentos/pi-million.txt");
        File outFile = new File("Documentos/pi-million.copy");
        int checksum = 0;
        try (BufferedInputStream in = new BufferedInputStream(new FileInputStream(inFile));
             BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(outFile))) {
            byte[] buf = new byte[8192];
            int n;
            while ((n = in.read(buf)) != -1) {
                // TODO: actualitzar checksum i escriure buf[0..n)
            }
            // TODO: imprimir checksum
        } catch (IOException e) { /* ... */ }
    }
}
```

### 3) Cercar patró de bytes (KMP) — *`BufferedInputStream`*

```java
import java.io.*;
import java.util.*;

public class BytePatternSearch {
    // TODO: funció lps(byte[]) per a KMP
    public static void main(String[] args) {
        // TODO: llegir patró ASCII per consola
        // TODO: recórrer el fitxer amb BufferedInputStream i aplicar KMP byte a byte
        // TODO: imprimir offset o “no trobat”
    }
}
```

### 4) Escriure/lligir `.dat` amb enters — *`DataOutputStream` / `DataInputStream`*

**(a) Escriure des de `numeros.txt` → `numeros.dat`)**

```java
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class NumbersToDatWrite {
    public static void main(String[] args) {
        // TODO: llegir enters de Documentos/numeros.txt (Reader UTF-8)
        // TODO: escriure n i els n enters amb DataOutputStream(BufferedOutputStream(...))
    }
}
```

**(b) Llegir `numeros.dat` i calcular min/max/mitjana)**

```java
import java.io.*;

public class NumbersFromDatRead {
    public static void main(String[] args) {
        // TODO: DataInputStream -> llegir n i n enters; calcular estadístics i mostrar
    }
}
```

### 5) Partir un arxiu binari en trossos — *`BufferedInputStream` + `BufferedOutputStream`*

```java
import java.io.*;

public class BinarySplit {
    public static void main(String[] args) {
        File src = new File("Documentos/pi-million.txt");
        File dir = new File("Documentos/parts"); dir.mkdirs();
        long partSize = 1L * 1024 * 1024; // 1 MB
        // TODO: llegir i anar creant pi-1.bin, pi-2.bin, ... fins acabar
    }
}
```

### 6) Índex de línies per offset (binari) — *build: `BufferedInputStream`+`DataOutputStream`; read: `DataInputStream` + skip*

```java
// build
import java.io.*;
public class LineIndexBuild {
    public static void main(String[] args) {
        // TODO: recórrer bytes d'usa_personas.txt i escriure offsets (long) a line-index.dat
    }
}

// read
import java.io.*;
import java.nio.charset.StandardCharsets;
public class LineIndexRead {
    public static void main(String[] args) {
        // TODO: llegir el long (n-1) i posicionar-se a usa_personas.txt per mostrar la línia n
    }
}
```

---

## CARÀCTERS (nivell mitjà i algun alt)

### 7) Stats de `numeros.txt` — *`InputStreamReader(UTF-8)` → `BufferedReader`*

```java
import java.io.*;
import java.nio.charset.StandardCharsets;

public class StatsNumeros {
    public static void main(String[] args) {
        // TODO: min, max, mitjana amb lectura línia a línia
    }
}
```

### 8) Mitjana d’alumnes i ordenació — *`BufferedReader` + col·leccions*

```java
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class AlumnesNotes {
    // TODO: classe Alumne { nom, cognom, mitjana }
    public static void main(String[] args) {
        // TODO: llegir, calcular mitjana, ordenar descendent i imprimir taula
    }
}
```

### 9) Ordenar un fitxer de text (entrada/sortida) — *`BufferedReader` / `BufferedWriter`*

```java
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class OrdenarArchivo {
    public static void main(String[] args) {
        // TODO: demanar camins, llegir línies, Collections.sort, i escriure
    }
}
```

### 10) Generar noms combinant llistes — *`BufferedReader`/`BufferedWriter`*

```java
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class GeneradorNombres {
    // TODO: llegir usa_nombres.txt i usa_apellidos.txt, demanar n i destí, escriure
}
```

### 11) Diccionari A..Z — *`BufferedReader` + múltiples `BufferedWriter`*

```java
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class DiccionarioPorLetra {
    public static void main(String[] args) {
        // TODO: crear carpeta Documentos/Diccionario i omplir A.txt..Z.txt
    }
}
```

### 12) Buscar cadena en PI (KMP text) — *`BufferedReader` + KMP de caràcters*

```java
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class BuscarEnPi {
    // TODO: lps(char[]) i recorregut char a char amb BufferedReader
}
```

### 13) Freqüència de paraules → CSV — *`BufferedReader` + `PrintWriter`*

```java
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class FrecuenciaPalabras {
    public static void main(String[] args) {
        // TODO: map paraula->compte, filtrar stopwords, ordenar i exportar a CSV
    }
}
```

### 14) Índex invertit (paraula → línies) — *`BufferedReader` + `BufferedWriter`*

```java
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class IndiceInvertido {
    public static void main(String[] args) {
        // TODO: Map<String, Set<Integer>>; escriure ordenat
    }
}
```

---



Genial! Ací tens **3 exercicis de bytes** i **3 de caràcters**, tots amb **estructures simples** (com a molt `ArrayList`) i usant **decorator** per muntar els streams. Cada enunciat indica la **combinació recomanada** i després tens la **solució**. S’assumeix la carpeta **`Documentos/`**.

---

# BLOC A — FLUXOS DE BYTES

## A1) Còpia binària en blocs i recompte de bytes

**Usa:** `BufferedInputStream(FileInputStream)` → `read(byte[])` i
`BufferedOutputStream(FileOutputStream)` → `write(byte[],off,len)`.
**Per què:** bytes purs (cap conversió d’encoding) i buffer per reduir crides al disc.

**Solució (`A1_CopyBinary.java`):**

```java
import java.io.*;

public class A1_CopyBinary {
    public static void main(String[] args) {
        File in  = new File("Documentos/pi-million.txt");
        File out = new File("Documentos/pi-million.copy");
        long total = 0;
        try (BufferedInputStream  bis = new BufferedInputStream(new FileInputStream(in));
             BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(out))) {
            byte[] buf = new byte[8192];
            int n;
            while ((n = bis.read(buf)) != -1) {
                bos.write(buf, 0, n);
                total += n;
            }
            bos.flush();
            System.out.println("Copiats " + total + " bytes a: " + out.getPath());
        } catch (IOException e) {
            System.err.println("Error I/O: " + e.getMessage());
        }
    }
}
```

---

## A2) Estadístiques bàsiques de bytes (mínim, màxim, mitjana)

**Usa:** `BufferedInputStream(FileInputStream)` → `read(byte[])`.
**Per què:** llegir ràpid en blocs i jugar amb els valors `0..255` dels bytes.

**Solució (`A2_ByteStats.java`):**

```java
import java.io.*;

public class A2_ByteStats {
    public static void main(String[] args) {
        File in = new File("Documentos/pi-million.txt"); // el tractem com binari
        long count = 0, sum = 0;
        int min = 255, max = 0;
        try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(in))) {
            byte[] buf = new byte[4096];
            int n;
            while ((n = bis.read(buf)) != -1) {
                for (int i = 0; i < n; i++) {
                    int v = buf[i] & 0xFF;
                    if (v < min) min = v;
                    if (v > max) max = v;
                    sum += v; count++;
                }
            }
            double avg = count == 0 ? 0.0 : (double) sum / count;
            System.out.printf("Bytes: %d  Min: %d  Max: %d  Mitjana: %.2f%n", count, min, max, avg);
        } catch (IOException e) {
            System.err.println("Error I/O: " + e.getMessage());
        }
    }
}
```

---

## A3) Concatenar fitxers binaris

**Usa:** dos `BufferedInputStream(FileInputStream)` i un `BufferedOutputStream(FileOutputStream)`.
**Per què:** practicar llegir→escriure en blocs i tancar en cascada.

**Solució (`A3_ConcatFiles.java`):**

```java
import java.io.*;

public class A3_ConcatFiles {
    private static void dump(InputStream in, OutputStream out) throws IOException {
        byte[] buf = new byte[8192];
        int n;
        while ((n = in.read(buf)) != -1) out.write(buf, 0, n);
    }
    public static void main(String[] args) {
        File f1 = new File("Documentos/pi-million.txt");
        File f2 = new File("Documentos/diccionario.txt");
        File out = new File("Documentos/concat.bin");
        try (BufferedInputStream  i1 = new BufferedInputStream(new FileInputStream(f1));
             BufferedInputStream  i2 = new BufferedInputStream(new FileInputStream(f2));
             BufferedOutputStream o  = new BufferedOutputStream(new FileOutputStream(out))) {
            dump(i1, o);
            dump(i2, o);
            o.flush();
            System.out.println("Concatenat a: " + out.getPath());
        } catch (IOException e) {
            System.err.println("Error I/O: " + e.getMessage());
        }
    }
}
```

---

# BLOC B — FLUXOS DE CARÀCTERS

## B1) Numerar línies d’un fitxer de text

**Usa:** `BufferedReader(InputStreamReader(FileInputStream, UTF-8))`
i `BufferedWriter(OutputStreamWriter(FileOutputStream, UTF-8))`.
**Per què:** llegir per línies (`readLine()`), escriure amb `newLine()` i charset explícit.

**Solució (`B1_NumberLines.java`):**

```java
import java.io.*;
import java.nio.charset.StandardCharsets;

public class B1_NumberLines {
    public static void main(String[] args) {
        File in  = new File("Documentos/frases.txt");
        File out = new File("Documentos/frases_numeradas.txt");
        try (BufferedReader br = new BufferedReader(
                 new InputStreamReader(new FileInputStream(in), StandardCharsets.UTF_8));
             BufferedWriter bw = new BufferedWriter(
                 new OutputStreamWriter(new FileOutputStream(out), StandardCharsets.UTF_8))) {
            String line; int num = 1;
            while ((line = br.readLine()) != null) {
                bw.write(String.format("%4d | %s", num++, line));
                bw.newLine();
            }
            System.out.println("Creat: " + out.getPath());
        } catch (IOException e) {
            System.err.println("Error I/O: " + e.getMessage());
        }
    }
}
```

---

## B2) Ordenar alfabèticament un fitxer de noms

**Usa:** `BufferedReader(InputStreamReader(..., UTF-8))` per llegir,
`ArrayList<String>` per guardar, `Collections.sort`, i
`BufferedWriter(OutputStreamWriter(..., UTF-8))` per escriure.
**Per què:** practicar lectura/escriptura de text i combinació típica de decoradors.

**Solució (`B2_SortLines.java`):**

```java
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class B2_SortLines {
    public static void main(String[] args) {
        File in  = new File("Documentos/usa_personas.txt");
        File out = new File("Documentos/usa_personas_sorted.txt");
        ArrayList<String> lines = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(
                 new InputStreamReader(new FileInputStream(in), StandardCharsets.UTF_8))) {
            String s; while ((s = br.readLine()) != null) lines.add(s);
        } catch (IOException e) { System.err.println("Error llegint: " + e.getMessage()); return; }
        Collections.sort(lines, String::compareToIgnoreCase);
        try (BufferedWriter bw = new BufferedWriter(
                 new OutputStreamWriter(new FileOutputStream(out), StandardCharsets.UTF_8))) {
            for (String s : lines) { bw.write(s); bw.newLine(); }
        } catch (IOException e) { System.err.println("Error escrivint: " + e.getMessage()); return; }
        System.out.println("Ordenat creat a: " + out.getPath());
    }
}
```

---

## B3) Filtrar diccionari per lletra inicial

**Usa:** `BufferedReader(InputStreamReader(..., UTF-8))` per llegir,
i per a cada lletra, `BufferedWriter(OutputStreamWriter(..., UTF-8))` per escriure.
**Per què:** practicar `startsWith`, `toUpperCase`, `newLine()` i múltiples writers (tots tancats en cascada).

**Solució (`B3_SplitDictionary.java`):**

```java
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class B3_SplitDictionary {
    public static void main(String[] args) {
        File in = new File("Documentos/diccionario.txt");
        File dir = new File("Documentos/Diccionario");
        if (!dir.exists()) dir.mkdir();

        Map<Character, BufferedWriter> writers = new HashMap<>();
        try (BufferedReader br = new BufferedReader(
                 new InputStreamReader(new FileInputStream(in), StandardCharsets.UTF_8))) {

            // Obrim un writer per a cada lletra A..Z
            for (char c = 'A'; c <= 'Z'; c++) {
                File out = new File(dir, c + ".txt");
                BufferedWriter bw = new BufferedWriter(
                        new OutputStreamWriter(new FileOutputStream(out), StandardCharsets.UTF_8));
                writers.put(c, bw);
            }

            String line;
            while ((line = br.readLine()) != null) {
                String w = line.trim();
                if (w.isEmpty()) continue;
                char first = Character.toUpperCase(w.charAt(0));
                BufferedWriter bw = writers.get(first);
                if (bw != null) { bw.write(w); bw.newLine(); }
            }

        } catch (IOException e) {
            System.err.println("Error I/O: " + e.getMessage());
        } finally {
            // Tancar tots els writers
            for (BufferedWriter bw : writers.values()) {
                try { if (bw != null) bw.close(); } catch (IOException ignored) {}
            }
        }
        System.out.println("Arxius per lletres en: " + dir.getPath());
    }
}
```

---

Vols que te’ls empaquete amb noms exactes de fitxer o que adapte algun dels camins? També puc canviar els fitxers d’origen si prefereixes practicar amb uns altres dels que tens en `Documentos/`.

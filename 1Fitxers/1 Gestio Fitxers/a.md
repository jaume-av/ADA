## Patró de disseny: **Decorator** (aplicat als Streams)

**Ideia clau:** en Java I/O, molts “*streams decoradors*” **emboliquen** un altre stream per **afegir funcionalitats** sense duplicar codi. Pots **compondre capes**: comences amb un **stream base** (accés a origen/destinació) i hi afegixes decoradors (buffer, primitives, text, línies, format…).

### Per què usar-lo ací?

* **Flexibilitat:** combina només el que necessites (buffer? text? format?).
* **Reutilització:** capes independents i intercanviables.
* **Simplicitat:** tanques **només** la capa externa; la resta es tanquen en cascada.

---

### Peces (amb les classes de streams)

* **Components base (bytes):** `FileInputStream`, `FileOutputStream`
* **Decoradors de bytes:** `BufferedInputStream`, `BufferedOutputStream` (buffer), `DataInputStream`, `DataOutputStream` (primitives binàries)
* **“Pont” bytes→caràcters (també decoradors):** `InputStreamReader` (llegir text amb charset), `OutputStreamWriter` (escriure text amb charset)
* **Decoradors de caràcters (text):** `BufferedReader` (línies i buffer), `BufferedWriter` (buffer), `PrintWriter` (format)

---

### ORDE recomanat de capes

1. **Base (bytes)** →
2. **Buffer/Primitives (bytes)** →
3. **Pont a text (si cal)** →
4. **Decoradors de text** →
5. **Format (si cal)**

> Si vas a **text**, el **pont** (`InputStreamReader`/`OutputStreamWriter`) ha d’anar **abans** dels decoradors de text.

---

## Combinacions habituals (centralitzat)

### Llistat de combinacions coherents

**Lectura (bytes / text):**

* `FileInputStream`
* `FileInputStream` → `BufferedInputStream`
* `FileInputStream` → `DataInputStream`
* `FileInputStream` → `BufferedInputStream` → `DataInputStream`
* `FileInputStream` → `InputStreamReader(UTF-8)`
* `FileInputStream` → `InputStreamReader(UTF-8)` → `BufferedReader`

**Escriptura (bytes / text):**

* `FileOutputStream`
* `FileOutputStream` → `BufferedOutputStream`
* `FileOutputStream` → `DataOutputStream`
* `FileOutputStream` → `BufferedOutputStream` → `DataOutputStream`
* `FileOutputStream` → `OutputStreamWriter(UTF-8)`
* `FileOutputStream` → `OutputStreamWriter(UTF-8)` → `BufferedWriter`
* `FileOutputStream` → `OutputStreamWriter(UTF-8)` → `BufferedWriter` → `PrintWriter`

> Evita **dos buffers seguits** en la mateixa direcció (p. ex. `BufferedInputStream` + `BufferedReader` a la vegada): amb **un** n’hi ha prou.

---

### Taula resum de combinacions típiques

| Propòsit                               | Combinació (de fora cap a dins)                                              | Notes ràpides                     |
| -------------------------------------- | ---------------------------------------------------------------------------- | --------------------------------- |
| Lectura **binària** amb primitives     | `DataInputStream(BufferedInputStream(FileInputStream))`                      | Ràpid + `readInt()/readUTF()`     |
| Lectura **text** amb línies (UTF-8)    | `BufferedReader(InputStreamReader(FileInputStream, "UTF-8"))`                | `readLine()` i encoding controlat |
| Escriptura **binària** amb primitives  | `DataOutputStream(BufferedOutputStream(FileOutputStream))`                   | `writeInt()/writeUTF()`           |
| Escriptura **text** (UTF-8) amb format | `PrintWriter(BufferedWriter(OutputStreamWriter(FileOutputStream, "UTF-8")))` | `printf/println` + buffer         |

> Referències internes: per a detalls de cada classe, vegeu les seccions de `File*Stream`, `Buffered*`, `Data*Stream`, `*StreamReader/Writer`, `BufferedReader/Writer` i `PrintWriter`.

---

## Exemples detallats i comentats

### A) Lectura BINÀRIA amb buffer + primitives

**Combinació:** `FileInputStream` → `BufferedInputStream` → `DataInputStream`

```java
try (FileInputStream fis = new FileInputStream("notes.dat");      // base: bytes des del fitxer
     BufferedInputStream bis = new BufferedInputStream(fis);      // decorador: buffer de lectura
     DataInputStream dis = new DataInputStream(bis)) {            // decorador: llegir tipus primitius

    int n = dis.readInt();                                        // primer camp: quants registres hi ha
    for (int i = 0; i < n; i++) {
        long id = dis.readLong();                                 // llegim un long
        String nom = dis.readUTF();                               // llegim un String (Modified UTF-8)
        double nota = dis.readDouble();                           // llegim un double
        System.out.printf("%d %s %.2f%n", id, nom, nota);         // eixida per consola
    }

} catch (EOFException e) {
    System.out.println("Final de fitxer inesperat.");
} catch (FileNotFoundException e) {
    System.out.println("Fitxer inexistent o sense permisos.");
} catch (IOException e) {
    System.out.println("Error d'entrada/eixida: " + e.getMessage());
}
```

**Al codi anterior:** 1) Base de bytes; 2) buffer per rendiment; 3) primitives binàries. L’ordre de lectura **ha de coincidir** amb l’ordre d’escriptura.

---

### B) Lectura de TEXT amb charset + línies

**Combinació recomanada:** `FileInputStream` → `InputStreamReader("UTF-8")` → `BufferedReader`

```java
try (BufferedReader br = new BufferedReader(
         new InputStreamReader(new FileInputStream("entrada.txt"), "UTF-8"))) {
    String linia;
    while ((linia = br.readLine()) != null) {                     // llegim línia a línia
        System.out.println(linia);                                // mostrem el text
    }
} catch (IOException e) {
    System.out.println("Error d'entrada/eixida: " + e.getMessage());
}
```

**Variant sense charset explícit (dependix del sistema):**

```java
try (BufferedReader br = new BufferedReader(new FileReader("entrada.txt"))) {
    String linia;
    while ((linia = br.readLine()) != null) {
        System.out.println(linia);
    }
}
catch (IOException e) {
    System.out.println("Error d'entrada/eixida: " + e.getMessage());
}
```

**Al codi anterior:** 1) Base de bytes; 2) **pont** UTF-8 (bytes→caràcters); 3) `BufferedReader` per `readLine()`.
*Nota:* la variant amb `FileReader` depén del **charset del sistema** (pot fallar amb accents).

---

### C) Escriptura BINÀRIA amb buffer + primitives

**Combinació:** `FileOutputStream` → `BufferedOutputStream` → `DataOutputStream`

```java
try (DataOutputStream dos = new DataOutputStream(
         new BufferedOutputStream(new FileOutputStream("notes.dat")))) {

    dos.writeInt(2);                                              // escriurem 2 registres
    dos.writeLong(1L); dos.writeUTF("Anna"); dos.writeDouble(9.5);
    dos.writeLong(2L); dos.writeUTF("Marc"); dos.writeDouble(7.8);
    // tancant la capa externa, es fa flush i es tanquen totes les capes
} catch (IOException e) {
    System.out.println("Error d'entrada/eixida: " + e.getMessage());
}
```

**Al codi anterior:** 1) Base de bytes; 2) buffer d’escriptura; 3) primitives en binari. Respecta l’**ordre** si després ho llegiràs amb `DataInputStream`.

---

### D) Escriptura de TEXT amb charset + línies + format

**Combinació:** `FileOutputStream` → `OutputStreamWriter("UTF-8")` → `BufferedWriter` → `PrintWriter`

```java
try (PrintWriter pw = new PrintWriter(
         new BufferedWriter(
             new OutputStreamWriter(new FileOutputStream("informe.txt"), "UTF-8")))) {

    pw.println("ID\tNom\tNota");                                  // capçalera amb tabuladors
    pw.printf("%d\t%s\t%.2f%n", 1, "Anna", 9.25);                 // línia amb format
    pw.printf("%d\t%s\t%.2f%n", 2, "Marc", 7.80);
    // PrintWriter pot fer buffer (via BufferedWriter) i format (printf)
} 
// PrintWriter/BufferedWriter/OutputStreamWriter/FileOutputStream es tanquen en cascada
```

**Al codi anterior:** 1) Base de bytes; 2) **pont** UTF-8 (caràcters→bytes); 3) buffer de text; 4) `PrintWriter` per `print/println/printf`.

---

### Trucs pràctics (Decorator amb Streams)

* **Tanca només la capa externa**; la resta es tanquen **en cascada**.
* **Un sol buffer** per direcció sol ser suficient (`BufferedInputStream` **o** `BufferedReader`; `BufferedOutputStream` **o** `BufferedWriter`).
* **Encoding sempre explícit** quan treballes amb text (`"UTF-8"`), per a evitar sorpreses.
* **Capturar o propagar?** El `catch` és **opcional**: si no gestiones ací, declara `throws IOException` al mètode.
* **Coherència binari/text:** després del **pont** (`InputStreamReader`/`OutputStreamWriter`) ja estàs en **caràcters**; continua amb decoradors de **text** (no barreges `Data*Stream` després d’un *reader/writer*).

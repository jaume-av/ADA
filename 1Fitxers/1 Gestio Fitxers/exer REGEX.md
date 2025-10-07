



## Exercici 1: Extracció de dominis de correu electrònic

Crea un programa que extraga **tots els dominis** de correu electrònic (la part després de l’`@`) d’un text i els mostre per consola.

Per fer-ho, pots utilitzar esta expressió regular:

```java
String regex = "(?<=@)[A-Za-z0-9.-]+\\.[A-Za-z]{2,}";
```

Aquesta expressió busca **només la part del domini** després del `@`:

* `(?<=@)` indica que el patró ha d’anar **precedit d’un `@`**, però sense incloure’l en el resultat.
* `[A-Za-z0-9.-]+` permet **lletres, números, punts i guions** dins del domini.
* `\\.[A-Za-z]{2,}` assegura que després hi haja un **punt** seguit d’almenys dues lletres (com `.com`, `.es`, `.edu`...).

Utilitza els mètodes `find()` i `group()` per a buscar i mostrar totes les coincidències trobades.

**Exemple d’entrada:**

```java
String text = "Els meus correus són joan@gmail.com, maria@universitat.es i pere@empresa.net";
```

**Exemple de sortida:**

```
Dominis trobats:
gmail.com
universitat.es
empresa.net
```

---

## Exercici 2: Validació de números de targeta de crèdit

Escriu un programa que valide **números de targeta de crèdit** amb el format típic:
quatre grups de quatre dígits separats per un **guió (`-`)** o per un **espai (` `)**.
El programa ha de permetre un sol tipus de separador, però no barrejats.

Utilitza aquesta expressió regular:
abobat
```java
String regex = "^(\\d{4}[- ]){3}\\d{4}$";
```

Aquesta expressió significa:

* `^` i `$` marquen **l’inici i el final** de la cadena, exigint una coincidència exacta.
* `(\\d{4}[- ]){3}` busca **tres grups** de quatre dígits (`\\d{4}`), cadascun seguit per un **guió o espai**.
* `\\d{4}` assegura que al final hi haja un **últim grup** de quatre dígits sense separador.

Utilitza el mètode `matches()` per comprovar si el número és vàlid.

**Exemple d’entrada:**

```java
String numero = "1234-5678-9123-4567";
```

**Exemple de sortida:**

```
El número de targeta és vàlid.
```

---

## Exercici 3: Comptar les paraules d’una frase

Fes un programa que **comte quantes paraules** hi ha en una frase utilitzant una expressió regular.

Una **paraula** la considerarem qualsevol seqüència de caràcters alfabètics, amb o sense accents.
Per identificar-les, pots usar aquest patró:

```java
String regex = "[A-Za-zÀ-ÿ]+";
```

On:

* `[A-Za-zÀ-ÿ]` inclou **totes les lletres de l’alfabet**, també amb accents.
* El símbol `+` indica que busquem **una o més lletres consecutives**, definint així una paraula.

Utilitza `find()` per comptar totes les coincidències trobades.

**Exemple d’entrada:**

```java
String frase = "Això és un exercici senzill de Java.";
```

**Exemple de sortida:**

```
La frase té 6 paraules.
```

---


# Exercici Combinat (versió amb fitxer d’eixida)

## Fitxer d’entrada: `cosesquepasen.txt`

Copia el text indicat en l’enunciat original dins d’un fitxer **`cosesquepasen.txt`** al mateix directori del programa.

Aula d’informàtica, dimarts de matí.

Pep entra correntment a classe i crida: “Profe! El meu ordinador fa un soroll estrany!”
El profe respon tranquil·lament: “Això no és soroll, és el ventilador intentant sobreviure al teu codi, Pep.”
Oh! Tota la classe riu estrepitosament.

Maria comenta rient: “Ahir vaig trencar el teclat. I saps què vaig fer? Control + Alt + Plorar.”
Ai! Pere es tapa la cara desesperadament i diu: “Jo vaig intentar menjar mentre programava… ara tinc pa dins del teclat!”
Uf! Toni esclata: “Això sí que és menjar ràpid!”

El profe diu seriosament: “Sou uns tontos, però almenys divertits.”
Eh! Anna alça la mà educadament: “Profe, si pose un ‘;’ després de cada frase, també compila millor la meua redacció?”
El profe sospira llargament i diu: “Només si declares bé les idees.”

Mentrestant, Jordi murmura discretament: “Jo vaig trobar una nova dieta.”
Pep pregunta curiosament: “Quina?”
Jordi respon orgullosament: “Només menjar quan el programa compila bé.”
Maria esclata rient: “Això vol dir que ja no tornaràs a menjar mai!”

Després d’uns minuts de caos, el profe intenta restaurar l’ordre:
“Silenci, si no acabeu l’exercici, vos pose un examen sorpresa!”
Tots callen immediatament... menys Toni, que diu tímidament: “Profe, què és més trist que un error 404?”
El profe contesta: “Un alumne que no sap on està el seu arxiu .java.”
Tota la classe riu fortament.

Ai! Maria exclama: “El meu ordinador diu que té massa cookies i poca memòria!”
Pep replica: “Doncs el meu està tan roí que ja no vol iniciar-se!”
El profe es posa les mans al cap i diu: “Este grup és pitjor que un bucle infinit!”
Eh! Tots esclaten a riure sorollosament.

Quan acaba la classe, Anna diu dolçament: “Malgrat tot, profe... ens agrada programar.”
El profe somriu sincerament i respon: “Jo també... però demà vos explique com es declara una variable sense declarar la guerra!”

Fi del dia, i el compilador encara tremola.






## Tasques

1. **Lectura del fitxer (línia a línia)**

   * **Pista**: fes servir `BufferedReader` amb `InputStreamReader(new FileInputStream(...), StandardCharsets.UTF_8)`.

2. **Aplica estos patrons regex** i mostra coincidències per a cada línia:

   * **Noms propis**: `\\b[A-ZÀ-ÿ][a-zà-ÿ]+\\b`

     * **Pista**: compila un `Pattern` i recorre les coincidències amb `Matcher.find()`. Guarda-les temporalment en una llista.
   * **Adverbis en -ment**: `\\b[a-zA-ZÀ-ÿ]+ment\\b`

     * **Pista**: reutilitza el mateix esquema `Pattern` + `Matcher`.
   * **Interjeccions**: `\\b(oh|ai|uf|eh)!\\b` (usa `CASE_INSENSITIVE`)

     * **Pista**: passa el flag `Pattern.CASE_INSENSITIVE` quan faces `Pattern.compile(...)`.
   * **Censura (paraules prohibides)**: `\\b(tonto|roí|lleig|idiota)\\b` (també `CASE_INSENSITIVE`)

     * **Pista**: per a la *línia censurada*, usa `matcher.replaceAll("****")`.

3. **Imprimeix també la línia censurada** substituint `tonto|roí|lleig|idiota` per `****`.

4. **Guarda TOTS els resultats en un fitxer de text** anomenat **`resultats_exercici.txt`**:

   * **Pista**: usa `BufferedWriter` o `PrintWriter` (sense *append*, sobreescriu cada vegada).
   * **Pista**: fes un *format* tipus:

     ```
     Línia N:
       Noms propis: [...]
       Adverbis en -ment: [...]
       Interjeccions: [...]
       Paraules prohibides trobades: [...]
       Censurada: <text censurat>

     ```
   * **Opcional (recomanat)**: al final, escriu un **resum** amb el total de coincidències de cada categoria.

     * **Pista**: porta quatre **comptadors** sencills (`int`) i ves sumant el tamany de cada llista de coincidències per línia.

---

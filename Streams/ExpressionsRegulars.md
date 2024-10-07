---
title: 1.- Expressions Regulars
parent: Streams
layout: default
nav_order: 2
---

Les classes `Pattern` i `Matcher` en Java ens permeten treballar amb expressions regulars, que són seqüències de caràcters que defineixen patrons per a cercar, validar o manipular textos. 

## Classe `Pattern`

La classe `Pattern` representa una expressió regular compilada i es pot utilitzar per a crear objectes `Matcher` que cerquen coincidències en seqüències de caràcters.

### **Mètodes principals de `Pattern`:**

- **`Pattern.compile(String regex)`:** Aquest mètode compila una expressió regular en un objecte `Pattern`. Això és necessari abans de poder buscar coincidències amb `Matcher`.

- **`matcher(CharSequence input)`:** Crea un objecte `Matcher` per buscar coincidències en una seqüència de caràcters específica, com ara una `String`.

- **`Pattern.matches(String regex, CharSequence input)`:** Un mètode estàtic que comprova si la seqüència de caràcters coincideix completament amb el patró donat.

- **`split(CharSequence input)`:** Divideix la seqüència de caràcters en funció del patró i retorna un array de `String` amb les parts resultants.

## Classe `Matcher`

La classe `Matcher` es crea a partir d’un objecte `Pattern` i permet cercar coincidències d’una expressió regular en una seqüència de caràcters. També proporciona mètodes per a analitzar i manipular aquestes coincidències.

### **Mètodes principals de `Matcher`:**

- **`matches()`:** Comprova si tota la seqüència de caràcters coincideix amb l'expressió regular.

- **`find()`:** Cerca la següent coincidència de l'expressió regular en la seqüència de caràcters. Retorna `true` si es troba una coincidència i `false` en cas contrari.

- **`group()`:** Retorna la subcadena que ha coincidit amb l'expressió regular en la cerca actual.

- **`start()`:** Retorna la posició inicial de la coincidència actual.

- **`end()`:** Retorna la posició final de la coincidència actual.

- **`replaceAll(String replacement)`:** Reemplaça totes les coincidències de l'expressió regular amb la cadena proporcionada.

## Exemples pràctics

### **Exemple 1: Extracció de números de telèfon**

```java

import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class ExtractorDeTelefons {
    public static void main(String[] args) {
        String text = "El meu número de telèfon és 123-456-789. El de casa és 964-123-456";
        String regex = "\\d{3}-\\d{3}-\\d{3}";

        Pattern objectePattern = Pattern.compile(regex);
        Matcher objecteMatcher = objectePattern.matcher(text);

        while (objecteMatcher.find()) {
            String phoneNumber = objecteMatcher.group();
            System.out.println("El número de telèfon trobat és: " + phoneNumber);
            System.out.println("Inici: " + objecteMatcher.start() + ", Fi: " + objecteMatcher.end());
        }
    }
}
```
A l'exemple, l'expressió regular `\\d{3}-\\d{3}-\\d{3}` busca números de telèfon amb el format `123-456-789`. `find()` es fa servir per cercar totes les coincidències, i `group()` retorna la subcadena trobada.

### **Exemple 2: Validació de correu electrònic**

```java

import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class ValidarCorreu {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Introdueix un correu electrònic:");
        String email = sc.nextLine();
        String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,3}$";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);

        if (matcher.matches()) {
            System.out.println("L'adreça de correu és vàlida.");
        } else {
            System.out.println("L'adreça de correu NO és vàlida.");
        }
        sc.close();
    }
}
```
`matches()` comprova si la cadena `email` coincideix completament amb el patró d'un correu electrònic vàlid.

### **Exemple 3: Reemplaçament de paraules ofensives**

```java

import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class Censura {
    public static void main(String[] args) {
        String frase = "Eres un datil i un abobat.";
        String regex = "(datil|abobat)";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(frase);

        String fraseCensurada = matcher.replaceAll("****");
        System.out.println("CENSURAT!!!!: " + fraseCensurada);
    }
}
```
L'exemple anterior utilitza `replaceAll()` per substituir totes les coincidències de les paraules "datil" i "abobat" per "****".

### Altres Mètodes

- **`lookingAt()`:** Verifica si la seqüència de caràcters coincideix amb el patró des del començament, però no necessita que tota la cadena coincideixi.

- **`quote()` (de `Pattern`):** Permet tractar un text literal com una expressió regular. Això és útil quan es vol buscar una cadena específica sense que els seus caràcters especials s’interpreten com a part d’un patró.

- **`pattern()` (de `Matcher`):** Retorna l'objecte `Pattern` associat a un `Matcher`.

Les anteriors classes, combinades amb els mètodes que proporcionen, fan que el treball amb expressions regulars a Java siga flexible i potent, permetent des de validacions bàsiques fins a manipulacions avançades de text.


## Exercicis


### Exercici 1: Extracció de dominis de correu electrònic

**Objectiu:** Crear un programa que extraga tots els dominis de correu electrònic (la part després del `@`) d'un text i mostrar-los per consola.

**Detalls:**
- Utilitza una expressió regular per identificar les adreces de correu electrònic dins d'un text.
- Utilitza els mètodes `find()` i `group()` per a extreure i mostrar només la part del domini.

**Exemple d'entrada:**
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

### Exercici 2: Validació de números de targeta de crèdit

**Objectiu:** Escriure un programa que valide números de targeta de crèdit seguint un patró bàsic: quatre grups de quatre dígits separats per un guió (`-`) o un espai (` `).

**Detalls:**
- La expressió regular ha de permetre tant números amb guions com amb espais, però no barrejats.
- Utilitza `matches()` per verificar si el número és vàlid.

**Exemple d'entrada:**
```java
String numero = "1234-5678-9123-4567";
```

**Exemple de sortida:**
```
El número de targeta és vàlid.
```

---

### Exercici 3: Compta les paraules d'una frase

**Objectiu:** Crear un programa que conte quantes paraules té una frase utilitzant expressions regulars.

**Detalls:**
- Una paraula es defineix com qualsevol seqüència de caràcters alfabètics (`[A-Za-z]+`).
- Utilitza `find()` per comptar el nombre de paraules que compleixen amb aquest patró.

**Exemple d'entrada:**
```java
String frase = "Això és un exercici senzill de Java.";
```

**Exemple de sortida:**
```
La frase té 6 paraules.
```

---
Aquí tens exemples de cadenes regex per a cadascun dels exercicis:

### Extracció de dominis de correu electrònic: 

**Regex:** `(?<=@)[A-Za-z0-9.-]+\\.[A-Za-z]{2,}`

- **Explicació:**
  - `(?<=@)`: Assegura que la coincidència apareix després d'un `@` (això és un lookbehind, que busca però no captura el `@`).
  - `[A-Za-z0-9.-]+`: Coincideix amb una seqüència de caràcters alfanumèrics, punts (`.`) i guions (`-`).
  - `\\.[A-Za-z]{2,}`: Assegura que després del domini hi haja un punt (`.`) seguit de dues o més lletres (com `.com` o `.es`).


### Validació de números de targeta de crèdit

**Regex:** `^(\\d{4}[- ]){3}\\d{4}$`

- **Explicació:**
  - `^` i `$`: Marquen l'inici i el final de la cadena, assegurant que tota la cadena ha de coincidir amb el patró.
  - `(\\d{4}[- ]){3}`: Coincideix amb tres grups de quatre dígits (`\\d{4}`), cadascun seguit per un guió (`-`) o un espai (` `).
  - `\\d{4}`: Assegura que hi haja un últim grup de quatre dígits sense cap separador al final.


---

### Exercici 3: Compta les paraules d'una frase

**Regex:** `[A-Za-zÀ-ÿ]+`

- **Explicació:**
  - `[A-Za-zÀ-ÿ]+`: Coincideix amb qualsevol seqüència de caràcters alfabètics, incloent lletres amb accents (`À-ÿ`).
  - El `+` assegura que trobem una o més lletres consecutives (definint una paraula).


---
title: 1.- Expressions Regulars
parent: 2.- Gestió del Contingut
has_children: true
layout: default
nav_order: 10
---


# Expressions Regulars en Java

Les expressions regulars (regex) són seqüències de caràcters que defineixen patrons per a cercar, validar i manipular cadenes de text. A Java, són molt útils per a identificar patrons complexos en les cadenes, com ara números de telèfon, adreces de correu electrònic o valors específics.

## Usos Comuns de les Expressions Regulars

Alguns exemples d'ús de les expressions regulars són:

- **Validació de formats**: Comprovar que una data siga en format `dd/mm/yyyy` o que un NIF (Número d'Identificació Fiscal) seguisca el format correcte.
- **Validació de correu electrònic**: Verificar que una adreça de correu complisca amb un format estàndard.
- **Validació de contrasenyes**: Assegurar-se que una contrasenya continga majúscules, minúscules, números i símbols.
- **Extracció de dades**: Buscar i extraure números de telèfon, codis postals o qualsevol altra informació que complisca un patró determinat.
- **Cerca de patrons**: Trobar i comptar quantes vegades es repeteix un fragment dins d’una cadena.

## Funcionament Bàsic de les Expressions Regulars

Les expressions regulars recorren una cadena d’esquerra a dreta, buscant coincidències amb el patró definit. Quan un caràcter de la cadena compleix amb el patró, aquest caràcter ja no torna a considerar-se per a futures comprovacions en la mateixa cerca.

Hi ha dos enfocaments principals en la comparació de patrons amb cadenes:

- **Trobar coincidències parcials**: Determinar si una part de la cadena compleix amb el patró. Per exemple, trobar una data en format `dd/mm/yyyy` dins d'una cadena com "Hui és 12/02/2017".
- **Coincidència exacta**: Verificar que tota la cadena complisca exactament amb el patró donat. Per exemple, comprovar si "12/02/2017" és exactament una data sense text addicional.

### Símbols i Metacaràcters en Expressions Regulars

Els metacaràcters són símbols especials que defineixen el comportament de les expressions regulars. A continuació, es descriuen alguns dels més comuns:

- **.** : Qualsevol caràcter (excepte salts de línia).
- **^** : Indica que el patró ha de començar al principi de la cadena.
- **$** : Indica que el patró ha de finalitzar al final de la cadena.
- **[abc]** : Coincideix amb qualsevol dels caràcters `a`, `b` o `c`.
- **[^abc]** : Coincideix amb qualsevol caràcter excepte `a`, `b` o `c`.
- **[a-z]** : Coincideix amb qualsevol lletra minúscula de la `a` a la `z`.
- `A|B` : Coincideix amb `A` o `B` (operador OR).
- **\\d** : Coincideix amb qualsevol dígit (equivalent a `[0-9]`).
- **\\D** : Coincideix amb qualsevol caràcter que no siga un dígit (equivalent a `[^0-9]`).
- **\\s** : Coincideix amb qualsevol espai en blanc (inclou espais, tabuladors i salts de línia).
- **\\S** : Coincideix amb qualsevol caràcter que no siga un espai en blanc.
- **\\w** : Coincideix amb qualsevol lletra, dígit o subratllat (equivalent a `[A-Za-z0-9_]`).
- **\\W** : Coincideix amb qualsevol caràcter que no siga una lletra, dígit o subratllat.

### Quantificadors

Els quantificadors determinen quantes vegades un element de l'expressió regular pot aparèixer en una coincidència:

- **`{X}`** : Exactament X repeticions.
- **`{X,Y}`** : Entre X i Y repeticions.
- **`{X,}`** : Almenys X repeticions.
- **`*`** : Zero o més repeticions (equivalent a `{0,}`).
- **`+`** : Una o més repeticions (equivalent a `{1,}`).
- **`?`** : Zero o una repetició (equivalent a `{0,1}`).

## Exemples d'Expressions Regulars

### 1. Validar un dígit

```java

String regex = "^\\d$";
```

- **`^`**: Indica l'inici de la cadena.
- **`\\d`**: Representa qualsevol dígit del 0 al 9.
- **`$`**: Indica el final de la cadena.

**Exemple:**
- `"5"`: Vàlid, ja que és un únic dígit.
- `"a"`: No vàlid, ja que no és un dígit.
- `"12"`: No vàlid, ja que conté més d'un dígit.

---

### 2. Validar una paraula de lletres majúscules

```java

String regex = "^[A-Z]+$";
```

- **`^`**: Inici de la cadena.
- **`[A-Z]`**: Qualsevol lletra majúscula de la `A` a la `Z`.
- **`+`**: Almenys una o més repeticions de lletres majúscules.
- **`$`**: Final de la cadena.

**Exemple:**
- `"HELLO"`: Vàlid, només conté lletres majúscules.
- `"Hello"`: No vàlid, conté lletres minúscules.
- `"123"`: No vàlid, no conté cap lletra.

---

### 3. Validar un número enter positiu de fins a 3 dígits

```java

String regex = "^[1-9]\\d{0,2}$";
```

- **`^`**: Inici de la cadena.
- **`[1-9]`**: El primer dígit ha de ser entre 1 i 9 (per evitar zeros al davant).
- **`\\d{0,2}`**: Pot tenir de 0 a 2 dígits addicionals.
- **`$`**: Final de la cadena.

**Exemple:**
- `"5"`: Vàlid, ja que és un número de 1 dígit.
- `"123"`: Vàlid, ja que és un número de 3 dígits.
- `"012"`: No vàlid, comença amb un zero.
- `"1000"`: No vàlid, ja que conté més de 3 dígits.

---

### 4. Validar una adreça de correu electrònic senzilla

```java

String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
```

- **`^`**: Inici de la cadena.
- **`[A-Za-z0-9+_.-]+`**: Permet lletres, dígits i alguns caràcters especials abans de l'`@`.
- **`@`**: Simbolitza la separació entre el nom d'usuari i el domini.
- **`[A-Za-z0-9.-]+`**: Permet caràcters alfanumèrics i alguns símbols en el domini.
- **`$`**: Final de la cadena.

**Exemple:**
- `"joan123@gmail.com"`: Vàlid.
- `"maria@universitat"`: No vàlid, falta el domini després del punt.
- `"pere@empresa.net"`: Vàlid.
- `"@gmail.com"`: No vàlid, falta la part de l'usuari.

---

### 5. Validar una data en format `dd/mm/yyyy`

```java

String regex = "^(0[1-9]|[12]\\d|3[01])/(0[1-9]|1[0-2])/\\d{4}$";
```

- **`^`**: Inici de la cadena.
- **`(0[1-9]|[12]\\d|3[01])`**: Dies de l'1 al 31.
- **`/`**: Separador de la data.
- **`(0[1-9]|1[0-2])`**: Mesos de l'1 al 12.
- **`/`**: Separador de la data.
- **`\\d{4}`**: Any de quatre dígits.
- **`$`**: Final de la cadena.

**Exemple:**
- `"12/02/2024"`: Vàlid.
- `"31/04/2024"`: Vàlid, tot i que no és una data real (no comprova els dies del mes).
- `"01/13/2024"`: No vàlid, no existeix el mes 13.
- `"1/2/2024"`: No vàlid, falta un zero per mantenir el format `dd/mm/yyyy`.

---

### 6. Validar un número de telèfon en format internacional

```java

String regex = "^\\+[0-9]{1,3} ?[0-9]{6,14}$";
```

- **`^\\+`**: Comença amb el símbol `+`.
- **`[0-9]{1,3}`**: Codi de país, d'1 a 3 dígits.
- **` ?`**: Espai opcional.
- **`[0-9]{6,14}`**: De 6 a 14 dígits per al número de telèfon.
- **`$`**: Final de la cadena.

**Exemple:**
- `"+34 123456789"`: Vàlid.
- `"+123456789"`: Vàlid.
- `"123456789"`: No vàlid, falta el `+` del codi de país.
- `"+1 12345"`: No vàlid, massa pocs dígits.

---

### 7. Validar una contrasenya amb requisits de seguretat

```java

String regex = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@#$%!]).{8,}$";
```

- **`^`**: Inici de la cadena.
- **`(?=.*[A-Z])`**: Almenys una lletra majúscula.
- **`(?=.*[a-z])`**: Almenys una lletra minúscula.
- **`(?=.*\\d)`**: Almenys un dígit.
- **`(?=.*[@#$%!])`**: Almenys un dels caràcters especials: `@`, `#`, `$`, `%`, `!`.
- **`.{8,}`**: La contrasenya ha de tenir almenys 8 caràcters.
- **`$`**: Final de la cadena.

**Exemple:**
- `"Password123!"`: Vàlid.
- `"password"`: No vàlid, falta una majúscula, un dígit i un caràcter especial.
- `"PASSWORD123"`: No vàlid, falta un caràcter especial i una minúscula.
- `"Pass1!"`: No vàlid, massa curta.

---

# Pattern i Matcher 

Les classes `Pattern` i `Matcher` en Java ens permeten treballar amb expressions regulars, que són seqüències de caràcters que defineixen patrons per a cercar, validar o manipular textos. 

## Classe Pattern

La classe `Pattern` representa una expressió regular compilada i es pot utilitzar per a crear objectes `Matcher` que cerquen coincidències en seqüències de caràcters.

### **Mètodes principals de Pattern:**

- **`Pattern.compile(String regex)`:** Aquest mètode compila una expressió regular en un objecte `Pattern`. Això és necessari abans de poder buscar coincidències amb `Matcher`.

- **`matcher(CharSequence input)`:** Crea un objecte `Matcher` per buscar coincidències en una seqüència de caràcters específica, com ara una `String`.

- **`Pattern.matches(String regex, CharSequence input)`:** Un mètode estàtic que comprova si la seqüència de caràcters coincideix completament amb el patró donat.

- **`split(CharSequence input)`:** Divideix la seqüència de caràcters en funció del patró i retorna un array de `String` amb les parts resultants.

## Classe Matcher

La classe `Matcher` es crea a partir d’un objecte `Pattern` i permet cercar coincidències d’una expressió regular en una seqüència de caràcters. També proporciona mètodes per a analitzar i manipular aquestes coincidències.

### **Mètodes principals de Matcher:**

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


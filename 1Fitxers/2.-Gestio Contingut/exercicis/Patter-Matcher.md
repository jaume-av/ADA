
# **Expressions Regulars en Java**

## **Definició:**
Una **expressió regular** defineix un patró de cerca per a cadenes de caràcters. Es pot utilitzar per comprovar si una cadena conté o coincideix amb el patró definit. Un mateix patró pot aparéixer 0, 1 o més vegades en la cadena.

### **Ús d'Expressions Regulars:**


- Comprovar que una data compleix el patró `dd/mm/aaaa`.
- Validar que un NIF està format per 8 xifres, un guió i una lletra.
- Validar una adreça de correu electrònic vàlida.
- Comprovar que una contrasenya compleix certs requisits de seguretat.
- Validar si una URL és vàlida.
- Comptar quantes vegades es repeteix una seqüència dins d'una cadena.

### **Comportament:**
El patró es busca de **l’esquerra a la dreta** dins de l’String. Una volta un caràcter compleix el patró, ja no torna a ser considerat en futures comprovacions.

---

## **Patrons Bàsics:**

- **`.`**: Qualsevol caràcter.
- **`^expressió`**: L'expressió ha de ser al principi de l’String.
- **`expressió$`**: L'expressió ha de ser al final de l’String.
- **`[abc]`**: Qualsevol dels caràcters entre claudàtors (`a`, `b`, o `c`).
- **`[^abc]`**: Qualsevol caràcter **excepte** els indicats.
- **`[a-z]`**: Un rang, com per exemple, qualsevol lletra minúscula entre `a` i `z`.
- **`A|B`**: O bé A o bé B.
- **`AB`**: El patró ha de contindre A seguit de B.

---

## **Meta Caràcters:**

- **`\d`**: Qualsevol dígit (`[0-9]`).
- **`\D`**: Qualsevol caràcter que **no** siga un dígit.
- **`\s`**: Un espai en blanc (`\t`, `\n`, `\x0b`, `\r`, `\f`).
- **`\S`**: Qualsevol caràcter que **no** siga un espai en blanc.
- **`\w`**: Qualsevol lletra, dígit o el caràcter `_`.
- **`\W`**: Qualsevol caràcter que no siga alfanumèric.

---

## **Quantificadors:**

- **`{X}`**: El patró es repeteix exactament **X** vegades.
- **`{X,Y}`**: El patró es repeteix un mínim de **X** vegades i un màxim de **Y**.
- **`*`**: El patró es repeteix 0 o més vegades.
- **`+`**: El patró es repeteix 1 o més vegades.
- **`?`**: El patró es repeteix 0 o 1 vegada.

---

## **Exemples**

### 1. **Validar una direcció de correu electrònic:**

```java

String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
// Explicació:
// ^       - Inici de la cadena.
// [A-Za-z0-9+_.-] - Caràcters permesos abans de l'@ (lletres, números i certs símbols).
// @       - Ha de contindre el símbol arrova.
// [A-Za-z0-9.-]   - Caràcters permesos després de l'@ (lletres, números, punts i guions).
// $       - Final de la cadena.
```

### 2. **Validar un número enter positiu de fins a 3 dígits:**

```java

String regex = "^[1-9]\\d{0,2}$";
// Explicació:
// ^       - Inici de la cadena.
// [1-9]   - Ha de començar amb un número de l'1 al 9.
// \\d{0,2} - Es permeten fins a 2 dígits addicionals (0 a 99).
// $       - Final de la cadena.
```

### 3. **Validar una data en format `dd/mm/aaaa`:**

```java

String regex = "^(0[1-9]|[12]\\d|3[01])/(0[1-9]|1[0-2])/\\d{4}$";
// Explicació:
// ^               - Inici de la cadena.
// (0[1-9]|[12]\\d|3[01]) - El dia (01-09, 10-29, o 30-31).
// /               - Separador de la part del dia i el mes.
// (0[1-9]|1[0-2]) - El mes (01-09, o 10-12).
// /               - Separador de la part del mes i l'any.
// \\d{4}          - Any de 4 dígits.
// $               - Final de la cadena.
```

### 4. **Validar un número de telèfon internacional:**

```java

String regex = "^\\+[0-9]{1,3} ?[0-9]{6,14}$";
// Explicació:
// ^               - Inici de la cadena.
// \\+             - Ha de començar amb el símbol '+' literalment.
// [0-9]{1,3}      - Fins a 3 dígits per al codi de país.
// ?               - Un espai opcional.
// [0-9]{6,14}     - El número de telèfon (6-14 dígits).
// $               - Final de la cadena.
```

---

## **Classes `Pattern` i `Matcher`**

- **Pattern**: S’utilitza per a compilar expressions regulars.
- **Matcher**: Utilitzat per a comparar patrons amb cadenes.

### Exemple:

```java

import java.util.regex.*;

public class ValidarCorreu {
    public static void main(String[] args) {
        String email = "exemple@domini.com";
        String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);

        if (matcher.matches()) {
            System.out.println("L'adreça de correu és vàlida.");
        } else {
            System.out.println("L'adreça de correu NO és vàlida.");
        }
    }
}
```

### **Mètodes de l'exemple:**
1. **Pattern.compile()**: Compila l'expressió regular.
2. **matcher()**: Crea un objecte `Matcher` per a comprovar l'email.
3. **matcher.matches()**: Verifica si l'email coincideix totalment amb el patró.


# Exercicis Resolts

---

### 1. **Validar si una cadena conté només números (dígit):**

Crea un programa que verifique si una cadena conté només números.

```java

import java.util.regex.*;

public class ValidarNumeros {
    public static void main(String[] args) {
        String text = "123456";
        String regex = "\\d+";  // Un o més dígits.

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(text);

        if (matcher.matches()) {
            System.out.println("La cadena només conté números.");
        } else {
            System.out.println("La cadena NO conté només números.");
        }
    }
}
```



- El patró `\\d+` indica que la cadena ha de contindre un o més dígits. El caràcter especial `\\d` representa qualsevol dígit del 0 al 9, i el quantificador `+` permet que es repetisca 1 o més vegades.
- **Pattern.compile()** compila l'expressió regular en un objecte `Pattern`.
- **matcher()** crea un objecte `Matcher` per a aplicar el patró a la cadena.
- **matcher.matches()** comprova si tota la cadena coincideix amb el patró.

**Sortida espeda:**
```
La cadena només conté números.
```

---

### 2. **Validar si una contrasenya és segura:**

Crea un programa que verifique si una contrasenya conté almenys 8 caràcters, incloent majúscules, minúscules, dígits i símbols especials.

```java

import java.util.regex.*;

public class ValidarContrasenya {
    public static void main(String[] args) {
        String contrasenya = "Strong@123";
        String regex = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@#$%!]).{8,}$";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(contrasenya);

        if (matcher.matches()) {
            System.out.println("La contrasenya és segura.");
        } else {
            System.out.println("La contrasenya NO és segura.");
        }
    }
}
```


- **Patró**: `^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@#$%!]).{8,}$`
  - `^`: Inici de la cadena.
  - `(?=.*[A-Z])`: Almenys una majúscula.
  - `(?=.*[a-z])`: Almenys una minúscula.
  - `(?=.*\\d)`: Almenys un dígit.
  - `(?=.*[@#$%!])`: Almenys un símbol especial (@, #, $, %, !).
  - `.{8,}`: La contrasenya ha de tindre almenys 8 caràcters.
  - `$`: Final de la cadena.
- **matcher.matches()** comprova si la contrasenya compleix tots els requisits de seguretat.

**Sortida:**
```
La contrasenya és segura.
```

---

### 3. **Validar una adreça de correu electrònic:**

**Enunciat:** Escriu un programa que valide una adreça de correu electrònic.

```java

import java.util.regex.*;

public class ValidarCorreu {
    public static void main(String[] args) {
        String email = "prova@domini.com";
        String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);

        if (matcher.matches()) {
            System.out.println("L'adreça de correu és vàlida.");
        } else {
            System.out.println("L'adreça de correu NO és vàlida.");
        }
    }
}
```


- **Patró**: `^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$`
  - `^`: Inici de la cadena.
  - `[A-Za-z0-9+_.-]+`: Caràcters permesos abans del símbol `@`.
  - `@`: Ha de contindre el símbol arrova.
  - `[A-Za-z0-9.-]+`: Caràcters permesos després del `@` (domini).
  - `$`: Final de la cadena.
- **matcher.matches()** comprova si la cadena compleix el patró d'un correu electrònic vàlid.

**Sortida:**
```
L'adreça de correu és vàlida.
```

---

### 4. **Validar un número de telèfon internacional:**

**Enunciat:** Crea un programa que valide un número de telèfon internacional.

```java

import java.util.regex.*;

public class ValidarTelefon {
    public static void main(String[] args) {
        String telefon = "+34 123456789";
        String regex = "^\\+[0-9]{1,3} ?[0-9]{6,14}$";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(telefon);

        if (matcher.matches()) {
            System.out.println("El número de telèfon és vàlid.");
        } else {
            System.out.println("El número de telèfon NO és vàlid.");
        }
    }
}
```


- **Patró**: `^\\+[0-9]{1,3} ?[0-9]{6,14}$`
  - `^`: Inici de la cadena.
  - `\\+`: El símbol `+` literal.
  - `[0-9]{1,3}`: Fins a 3 dígits per al codi de país.
  - `?`: Espai opcional.
  - `[0-9]{6,14}`: El número de telèfon (6-14 dígits).
  - `$`: Final de la cadena.
- **matcher.matches()** comprova si el número compleix el patró internacional.

**Sortida:**
```
El número de telèfon és vàlid.
```

---

### 5. **Validar un codi postal espanyol:**

Crea un programa que valide un codi postal espanyol (5 dígits).

```java

import java.util.regex.*;

public class ValidarCodiPostal {
    public static void main(String[] args) {
        String codiPostal = "46001";
        String regex = "^(0[1-9]|[1-4]\\d|5[0-2])\\d{3}$";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(codiPostal);

        if (matcher.matches()) {
            System.out.println("El codi postal és vàlid.");
        } else {
            System.out.println("El codi postal NO és vàlid.");
        }
    }
}
```


- **Patró**: `^(0[1-9]|[1-4]\\d|5[0-2])\\d{3}$`
  - `^`: Inici de la cadena.
  - `(0[1-9]|[1-4]\\d|5[0-2])`: El codi postal ha de començar amb els dígits permesos (de l'01 al 52).
  - `\\d{3}`: 3 dígits addicionals.
  - `$`: Final de la cadena.
- **matcher.matches()** comprova si el codi postal compleix el patró espanyol.

**Sortida:**
```
El codi postal és vàlid.
```

---

### 6. **Validar un número enter positiu:**

**Enunciat:** Crea un programa que verifique si una cadena és un número enter positiu de fins a 3 dígits.

```java

import java.util.regex.*;

public class ValidarEnterPositiu {
    public static void main(String[] args) {
        String numero = "123";
        String regex = "^[1-9]\\d{0,2}$";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(numero);

        if (matcher.matches()) {
            System.out.println("El número és un enter positiu vàlid.");
        } else {
            System.out.println("El número NO és vàlid.");
        }
    }
}
```


- **Patró**: `^[1-9]\\d{0,2}$`
  - `^`: Inici de la cadena.
  - `[1-9]`: El primer dígit ha de ser de l'1 al 9.
  - `\\d{0,2}`: Es permeten fins a 2 dígits addicionals (0 a 99).
  - `$`: Final de la cadena.
- **matcher.matches()** comprova si el número compleix el patró d'un enter positiu de fins a 3 dígits

.

**Sortida:**
```
El número és un enter positiu vàlid.
```

---

### 7. **Buscar una data dins d'una cadena:**

**Enunciat:** Crea un programa que busque una data en format `dd/mm/aaaa` dins d'una cadena.

```java

import java.util.regex.*;

public class BuscarData {
    public static void main(String[] args) {
        String text = "Avui és 15/09/2024";
        String regex = "\\b(0[1-9]|[12]\\d|3[01])/(0[1-9]|1[0-2])/\\d{4}\\b";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(text);

        if (matcher.find()) {
            System.out.println("S'ha trobat una data: " + matcher.group());
        } else {
            System.out.println("No s'ha trobat cap data.");
        }
    }
}
```


- **Patró**: `\\b(0[1-9]|[12]\\d|3[01])/(0[1-9]|1[0-2])/\\d{4}\\b`
  - `\\b`: Limita la cerca a una paraula completa (no part d'una altra paraula).
  - `(0[1-9]|[12]\\d|3[01])`: Dia vàlid (01-31).
  - `/`: Separador de dia i mes.
  - `(0[1-9]|1[0-2])`: Mes vàlid (01-12).
  - `/`: Separador de mes i any.
  - `\\d{4}`: Any de 4 dígits.
  - `\\b`: Final de la paraula.
- **matcher.find()** busca la primera coincidència del patró dins del text.

**Sortida:**
```
S'ha trobat una data: 15/09/2024
```

---

### 8. **Validar una adreça IP:**

**Enunciat:** Crea un programa que valide una adreça IP.

```java

import java.util.regex.*;

public class ValidarIP {
    public static void main(String[] args) {
        String ip = "192.168.1.1";
        String regex = "^(?:(?:25[0-5]|2[0-4]\\d|[01]?\\d\\d?)\\.){3}(?:25[0-5]|2[0-4]\\d|[01]?\\d\\d?)$";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(ip);

        if (matcher.matches()) {
            System.out.println("L'adreça IP és vàlida.");
        } else {
            System.out.println("L'adreça IP NO és vàlida.");
        }
    }
}
```


- **Patró**: `^(?:(?:25[0-5]|2[0-4]\\d|[01]?\\d\\d?)\\.){3}(?:25[0-5]|2[0-4]\\d|[01]?\\d\\d?)$`
  - `25[0-5]`: Números de 250 a 255.
  - `2[0-4]\\d`: Números de 200 a 249.
  - `[01]?\\d\\d?`: Números de 0 a 199.
  - `\\.`: Punt com a separador.
  - `{3}`: Repetit 3 vegades per als primers 3 blocs de l'IP.
  - `$`: Final de la cadena.
- **matcher.matches()** comprova si l'IP compleix el patró.

**Sortida:**
```
L'adreça IP és vàlida.
```

---

### 9. **Reemplaçar paraules ofensives en un text:**

Crea un programa que reemplaçe paraules ofensives en un text per "*".

```java

public class ReemplacarParaules {
    public static void main(String[] args) {
        String frase = "Eres un datil i un abobat.";
        String regex = "(datil|abobat)";
        String resultat = frase.replaceAll(regex, "***");

        System.out.println("Frase modificada: " + resultat);
    }
}
```


- **Patró**: `(datil|abobat)`
  - `(datil|abobat)`: Coincideix amb la paraula `datil` o `abobat`.
- **replaceAll()** reemplaça totes les coincidències amb `***`.

**Sortida:**
```
Frase modificada: Eres un *** i un ***.
```

---

### 10. **Comptar quantes vegades es repeteix una paraula en un text:**

**Enunciat:** Escriu un programa que compte quantes vegades es repeteix una paraula en un text.

```java

import java.util.regex.*;

public class ComptarParaula {
    public static void main(String[] args) {
        String text = "hola món, hola Java, hola a tots.";
        String regex = "\\bhola\\b";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(text);

        int contador = 0;
        while (matcher.find()) {
            contador++;
        }

        System.out.println("La paraula 'hola' es repeteix " + contador + " vegades.");
    }
}
```

- **Patró**: `\\bhola\\b`
  - `\\b`: Delimita la paraula completa `hola`.
- **matcher.find()** busca totes les coincidències dins del text.
- Un **bucle `while`** compta quantes vegades es troba la paraula.

**Sortida:**
```
La paraula 'hola' es repeteix 3 vegades.
```

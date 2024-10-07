

Les classes `Pattern` i `Matcher` en Java són fonamentals per treballar amb expressions regulars, que són seqüències de caràcters que defineixen patrons per a cercar, validar o manipular textos. A continuació, explorarem cadascuna d’aquestes classes en detall, els seus mètodes més importants i alguns exemples pràctics.

### Classe `Pattern`

La classe `Pattern` representa una expressió regular compilada i es pot utilitzar per a crear objectes `Matcher` que cerquin coincidències en seqüències de caràcters.

**Mètodes principals de `Pattern`:**

- **`Pattern.compile(String regex)`:** Aquest mètode compila una expressió regular en un objecte `Pattern`. Això és necessari abans de poder buscar coincidències amb `Matcher`.

- **`matcher(CharSequence input)`:** Crea un objecte `Matcher` per buscar coincidències en una seqüència de caràcters específica, com ara una `String`.

- **`Pattern.matches(String regex, CharSequence input)`:** Un mètode estàtic que comprova si la seqüència de caràcters coincideix completament amb el patró donat.

- **`split(CharSequence input)`:** Divideix la seqüència de caràcters en funció del patró i retorna un array de `String` amb les parts resultants.

### Classe `Matcher`

La classe `Matcher` es crea a partir d’un objecte `Pattern` i permet cercar coincidències d’una expressió regular en una seqüència de caràcters. També proporciona mètodes per a analitzar i manipular aquestes coincidències.

**Mètodes principals de `Matcher`:**

- **`matches()`:** Comprova si tota la seqüència de caràcters coincideix amb l'expressió regular.

- **`find()`:** Cerca la següent coincidència de l'expressió regular en la seqüència de caràcters. Retorna `true` si es troba una coincidència i `false` en cas contrari.

- **`group()`:** Retorna la subcadena que ha coincidit amb l'expressió regular en la cerca actual.

- **`start()`:** Retorna la posició inicial de la coincidència actual.

- **`end()`:** Retorna la posició final de la coincidència actual.

- **`replaceAll(String replacement)`:** Reemplaça totes les coincidències de l'expressió regular amb la cadena proporcionada.

### Exemples pràctics

**Exemple 1: Extracció de números de telèfon**

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
En aquest exemple, l'expressió regular `\\d{3}-\\d{3}-\\d{3}` busca números de telèfon amb el format `123-456-789`. `find()` es fa servir per cercar totes les coincidències, i `group()` retorna la subcadena trobada.

**Exemple 2: Validació de correu electrònic**

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
En aquest exemple, `matches()` comprova si la cadena `email` coincideix completament amb el patró d'un correu electrònic vàlid.

**Exemple 3: Reemplaçament de paraules ofensives**

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
Aquest exemple utilitza `replaceAll()` per substituir totes les coincidències de les paraules "datil" i "abobat" per "****".

### Altres Mètodes i Conceptes

- **`lookingAt()`:** Verifica si la seqüència de caràcters coincideix amb el patró des del començament, però no necessita que tota la cadena coincideixi.

- **`quote()` (de `Pattern`):** Permet tractar un text literal com una expressió regular. Això és útil quan es vol buscar una cadena específica sense que els seus caràcters especials s’interpreten com a part d’un patró.

- **`pattern()` (de `Matcher`):** Retorna l'objecte `Pattern` associat a un `Matcher`.

Aquestes classes, combinades amb els mètodes que proporcionen, fan que el treball amb expressions regulars a Java siga flexible i potent, permetent des de validacions bàsiques fins a manipulacions avançades de text. Els exemples anteriors ofereixen una visió pràctica de com usar `Pattern` i `Matcher` per a cobrir diferents necessitats.

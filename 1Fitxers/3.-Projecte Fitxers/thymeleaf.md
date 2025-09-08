



---

# Thymeleaf

**Thymeleaf** és un motor de plantilles HTML per a aplicacions Java. Encara que s'integra fàcilment amb frameworks com Spring Boot, també es pot utilitzar en projectes Java independents. 

Thymeleaf permet generar HTML dinàmic i treballar amb dades de manera senzilla i segura.

## Configuració

Per començar amb Thymeleaf, afegeix la següent dependència en el fitxer `pom.xml` del teu projecte (en Maven):

```xml
<dependency>
    <groupId>org.thymeleaf</groupId>
    <artifactId>thymeleaf</artifactId>
    <version>3.0.12.RELEASE</version>
</dependency>
```

### 1. Configuració del `TemplateEngine` i el Resolutor de Plantilles

El següent codi configura el motor de plantilles **Thymeleaf** per carregar **plantilles HTML** des d'una carpeta específica (`carpetaPlantilles`) i generar un fitxer HTML dinàmic (`fitxerHTML.html`) a `carpetaFitxersHTML`.

#### Codi Java complet de configuració i ús

```java
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.FileTemplateResolver;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ThymeleafExample {

    public static void main(String[] args) {

        // 1. Configuració del resolutor (Template Resolver) de plantilles per a la carpeta especificada
        // 
        
        FileTemplateResolver resolver = new FileTemplateResolver();
        resolver.setPrefix("carpetaPlantilles/"); // Carpeta on es troba la plantilla
        resolver.setSuffix(".html"); // Les plantilles acabaran en ".html"
        resolver.setTemplateMode("HTML"); // Indiquem que són fitxers HTML

        // 2. Configuració del motor de plantilles
        TemplateEngine engine = new TemplateEngine();
        engine.setTemplateResolver(resolver);

        // 3. Creació del context amb les dades que es volen injectar a la plantilla
        Context context = new Context();
        context.setVariable("nomUsuari", "Jaume"); // Afegim una variable "nomUsuari" amb el valor "Jaume"

        // 4. Processament de la plantilla
        String resultatHTML = engine.process("platillaThymeleaf", context);

        // 5. Crear la carpeta de sortida si no existeix
        File carpetaSortida = new File("carpetaFitxersHTML");
        if (!carpetaSortida.exists()) {
            if (carpetaSortida.mkdirs()) {
                System.out.println("Carpeta de sortida creada correctament.");
            } else {
                System.err.println("Error al crear la carpeta de sortida.");
                return;
            }
        }

        // 6. Escriure el contingut generat en el fitxer HTML a la carpeta especificada
        try (FileWriter writer = new FileWriter("carpetaFitxersHTML/fitxerHTML.html")) {
            writer.write(resultatHTML); // Escrivim el contingut HTML al fitxer
            System.out.println("Fitxer HTML generat correctament com fitxerHTML.html dins carpetaFitxersHTML.");
        } catch (IOException e) {
            System.err.println("Error al generar el fitxer HTML: " + e.getMessage());
        }
    }
}
```

---

### El codi anterior segueix els següents passos:

1. **Configuració del resolutor de plantilles**:  El **template resolver** és el component que s'encarrega de buscar i carregar les plantilles HTML o altres tipus de fitxers de plantilla per tal que el motor de **Thymeleaf** puga processar-les.

   - `FileTemplateResolver resolver = new FileTemplateResolver();` crea un resolutor que permet carregar plantilles des d'una carpeta específica del sistema.
   - `resolver.setPrefix("carpetaPlantilles/");` indica la carpeta de les plantilles (per exemple, `carpetaPlantilles/`).
   - `resolver.setSuffix(".html");` defineix que les plantilles tenen l'extensió `.html`.
   - `resolver.setTemplateMode("HTML");` indica a Thymeleaf que tracte els fitxers com HTML.

2. **Configuració del motor de plantilles**: Component principal que **Thymeleaf** que s'utilitza per processar i generar HTML dinàmics a partir de les plantilles i les dades que li passem.
   - `TemplateEngine engine = new TemplateEngine();` crea el motor de plantilles Thymeleaf.
   - `engine.setTemplateResolver(resolver);` configura el motor perquè utilitzi el resolutor definit anteriorment.

3. **Creació del context**: El **context** en **Thymeleaf** és una estructura de dades (com un “contenidor” o “caixa”) que guarda tota la **informació o dades** que volem injectar en una plantilla perquè el motor de plantilles les puga utilitzar durant el processament.

   - `Context context = new Context();` crea el context, un contenidor per a les dades que es volen passar a la plantilla.
   - `context.setVariable("nomUsuari", "Jaume");` afegeix una variable `nomUsuari` al context amb el valor `"Jaume"`, que es substituirà dins la plantilla `platillaThymeleaf.html`.

4. **Processament de la plantilla**: Procés pel qual **Thymeleaf** agafa una plantilla (un fitxer HTML amb **expressions o variable**s) i la combina amb les dades proporcionades (**context**) per generar un document final amb el contingut substituït i complet.
   
   - `String resultatHTML = engine.process("platillaThymeleaf", context);` diu a Thymeleaf que busqui el fitxer `platillaThymeleaf.html` a `carpetaPlantilles` i genere el contingut HTML substituint les variables.

5. **Creació de la carpeta de sortida**:
   - `File carpetaSortida = new File("carpetaFitxersHTML");` defineix la carpeta de sortida.
   - `if (!carpetaSortida.exists()) { carpetaSortida.mkdirs(); }` assegura que la carpeta `carpetaFitxersHTML` existeix, creant-la si cal.

6. **Escriure el contingut en un fitxer HTML**:
   - `try (FileWriter writer = new FileWriter("carpetaFitxersHTML/fitxerHTML.html"))` escriu el contingut processat a `fitxerHTML.html` dins de `carpetaFitxersHTML`.

---

### 2. Creació de Plantilles Thymeleaf

Crearem un fitxer anomenat `platillaThymeleaf.html` a la carpeta `carpetaPlantilles/` amb el següent contingut:

```html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <title>Pàgina de Benvinguda</title>
</head>
<body>
    <h1>Hola, <span th:text="${nomUsuari}">Nom d'usuari</span>!</h1>
    <p>Aquesta és una pàgina HTML generada dinàmicament amb Thymeleaf.</p>
</body>
</html>
```

---

### 3. Expressions Thymeleaf

Les **Expressions Thymeleaf** són formes d'injectar dades o variables dins d’una plantilla. S’escriuen amb la sintaxi **`${...}`** i permeten accedir a les variables que s'han definit en el **context** de la plantilla. 
Quan **Thymeleaf processa la plantilla, substitueix aquestes expressions amb els valors que es troben en el context**.

Per exemple, si tenim una variable **`nomUsuari`** en el context amb el valor `"Maria"`, podem usar **`${nomUsuari}`** a la plantilla per mostrar eixe nom.

#### Exemple

```html
<p>Benvingut, <span th:text="${nomUsuari}">Nom per defecte</span>!</p>
```

Si `nomUsuari` és `"Maria"`, el resultat final serà:
```html
<p>Benvingut, <span>Maria</span>!</p>
```

---

### 4. Atributs Bàsics de Thymeleaf

Thymeleaf ofereix atributs específics per modificar el contingut i l’aparença dels elements HTML segons les dades del context.

#### **`th:text`**
- `th:text` substitueix el contingut de l'element HTML amb el valor d'una variable.
- Aquest atribut s'utilitza per injectar text dins d'elements com `<p>`, `<span>`, etc., i és especialment útil per mostrar text dinàmicament.

```html
<p th:text="${usuari.nom}">Nom per defecte</p>
```

En aquest exemple, si `usuari.nom` és `"Jaume"`, el contingut final serà:
```html
<p>Jaume</p>
```

#### **`th:utext`**
- `th:utext` és similar a `th:text`, però permet mostrar HTML sense escapament, és a dir, que el contingut es mostra tal com està, sense ser tractat com a text pla.
- Això és útil si el contingut conté etiquetes HTML que volem mostrar sense modificar-les.

```html
<p th:utext="${usuari.htmlContent}">Contingut HTML</p>
```

Si `usuari.htmlContent` conté `<strong>Important</strong>`, el resultat final serà:
```html
<p><strong>Important</strong></p>
```

#### **`th:if` i `th:unless`**
- `th:if` i `th:unless` són atributs per condicionar la visualització d'elements HTML **segons una condició**.
  - `th:if` mostra l'element si la condició és certa.
  - `th:unless` mostra l'element si la condició és falsa.

```html
<p th:if="${usuari.edat > 18}">Ets major d'edat</p>
<p th:unless="${usuari.edat > 18}">Ets menor d'edat</p>
```

Si `usuari.edat` és 20, només es mostrarà:
```html
<p>Ets major d'edat</p>
```

Si `usuari.edat` és 15, només es mostrarà:
```html
<p>Ets menor d'edat</p>
```

---

### 5. Iteracions amb `th:each`

L’atribut `th:each` s'utilitza per fer **bucles** o **iteracions** sobre una col·lecció de dades, com una llista d'objectes. Aquest atribut és útil per **generar una llista d'elements repetitius**, com llistats de noms, productes, o qualsevol tipus d'informació que necessitem mostrar en forma de col·lecció.

- `th:each` pren el format `th:each="element : ${col·leccio}"`, on `element` és el nom temporal que representa cada element de la col·lecció dins del bucle, i `${col·leccio}` és la col·lecció definida en el context.

#### Exemple

Suposem que tenim una llista d'usuaris en el context (`usuaris`) i volem mostrar el nom, edat i email de cadascun.

```html
<ul>
    <li th:each="usuari : ${usuaris}">
        <p>Nom: <span th:text="${usuari.nom}">Nom per defecte</span></p>
        <p>Edat: <span th:text="${usuari.edat}">Edat per defecte</span></p>
        <p>Email: <span th:text="${usuari.email}">Email per defecte</span></p>
    </li>
</ul>
```

Si `usuaris` és una llista de dos objectes Usuari, amb dades com `"Jaume, 25, jaume@example.com"` i `"Maria, 30, maria@example.com"`, el resultat final seria:

```html
<ul>
    <li>
        <p>Nom: Jaume</p>
        <p>Edat: 25</p>
        <p>Email: jaume@example.com</p>
    </li>
    <li>
        <p>Nom: Maria</p>
        <p>Edat: 30</p>
        <p>Email: maria@example.com</p>
    </li>
</ul>
```


---

## 6. Link Expressions `@{...}` i Fragments

### Expressions de Link `@{...}`

Les **Expressions de Link** en Thymeleaf (`@{...}`) s’utilitzen per generar **URL dinàmiques** dins d’una pàgina HTML. Aquestes expressions permeten crear enllaços (links) que s’adapten automàticament en funció de les dades del context, cosa que és especialment útil per construir URL que incloguin paràmetres variables o dinàmics.

#### Com funcionen les Expressions de Link?

Quan Thymeleaf troba una expressió de link `@{...}`, processa l'URL dins de les claus `{...}` i substitueix qualsevol variable amb els valors definits al context. Aquesta capacitat permet generar URL que poden canviar dinàmicament.

##### Exemple d’URL Dinàmica

Suposem que volem crear un enllaç que apunti al perfil d’un usuari i que inclogui el seu ID en l’URL, com `/usuari/123`. Aquí és on les expressions de link resulten útils.

```html
<a th:href="@{/usuari/{id}(id=${usuari.id})}">Perfil de l'usuari</a>
```

**Explicació:**

- `@{/usuari/{id}(id=${usuari.id})}` indica a Thymeleaf que substitueixi `{id}` pel valor de `${usuari.id}`.
- Si `${usuari.id}` és `123`, el resultat de l'enllaç serà `/usuari/123`.
- Aquest tipus d’enllaç és útil quan necessitem generar URL per a diferents elements, com ara perfils d’usuaris, productes, etc.

**Resultat Final** (si `usuari.id` és `123`):
```html
<a href="/usuari/123">Perfil de l'usuari</a>
```

### Fragments en Thymeleaf

Els **Fragments** en Thymeleaf permeten crear seccions de plantilla **reutilitzables**. Això vol dir que pots definir parts de codi HTML (com capçaleres, peus de pàgina, menús de navegació, etc.) en un sol lloc i incloure-les en diverses pàgines sense haver de duplicar el codi. Aquesta funcionalitat ajuda a mantenir el codi organitzat i coherent en totes les pàgines de l'aplicació.

#### Com es defineixen els Fragments?

Els fragments es defineixen amb l’atribut `th:fragment`. Aquest atribut s’utilitza per donar un nom al fragment perquè pugui ser referenciat des d’altres plantilles.

##### Exemple de Fragment

Suposem que vols crear una capçalera de lloc web amb el títol i alguns enllaços de navegació. Pots definir aquesta capçalera com un fragment, per exemple, en un fitxer anomenat `fragments.html`.

```html
<!-- fitxer fragments.html -->
<div th:fragment="capçalera">
    <h1>Capçalera del lloc web</h1>
    <nav>
        <a th:href="@{/home}">Inici</a>
        <a th:href="@{/contacte}">Contacte</a>
    </nav>
</div>
```

En aquest exemple:
- El fragment es defineix amb `th:fragment="capçalera"`, la qual cosa li assigna el nom `capçalera`.
- Aquest fragment conté una capçalera amb un títol i dos enllaços de navegació.

#### Com s'inclou un Fragment en altres plantilles?

Per incloure un fragment en una altra plantilla, Thymeleaf ofereix els atributs `th:insert` o `th:replace`.

- **`th:insert`**: Inclou el fragment dins de l’element especificat, mantenint l'element original.
- **`th:replace`**: Substitueix l'element actual pel fragment complet.

##### Exemple d’Inclusió d’un Fragment en una altra Plantilla

Suposem que tens una altra plantilla anomenada `paginaprincipal.html` i vols incloure-hi la capçalera definida al fragment anterior.

```html
<!-- fitxer paginaprincipal.html -->
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <title>Pàgina Principal</title>
</head>
<body>
    <!-- Inclou el fragment de la capçalera -->
    <div th:insert="fragments :: capçalera"></div>
    
    <p>Contingut principal de la pàgina</p>
</body>
</html>
```

**Explicació:**
- `th:insert="fragments :: capçalera"` indica a Thymeleaf que busqui el fragment `capçalera` al fitxer `fragments.html` i l’inclogui aquí.
- Això farà que la capçalera definida al fragment aparegui a la pàgina principal sense necessitat de duplicar el codi HTML.

**Resultat Final:**

Quan Thymeleaf processa aquesta pàgina, el codi del fragment es mostrarà dins de `paginaprincipal.html` com si hagués estat escrit allà directament.

---

### Resum

- **Expressions de Link** `@{...}`: Permeten generar URL dinàmiques que s'adapten segons les dades del context, fent que els enllaços siguin flexibles i reutilitzables.
- **Fragments**: Són blocs de plantilla reutilitzables (com capçaleres o peus de pàgina) que poden ser inclosos en diverses pàgines, millorant la modularitat i la coherència del disseny.

Aquestes funcionalitats fan que Thymeleaf sigui molt potent per construir aplicacions web amb plantilles dinàmiques i fàcils de mantenir.

---

### 7. Càrrega d'Imatges amb Thymeleaf

Pots carregar imatges en Thymeleaf de dues maneres: des d'un fitxer local o des d'un enllaç en línia.

#### Càrrega d'Imatge des d'un Fitxer Local

Per carregar una imatge local, assegura't que la imatge es trobi en el directori de recursos del teu projecte (per exemple, a la carpeta `static/images/`). Aleshores, pots utilitzar `th:src` per a la ruta de la im

atge:

```html
<img th:src="@{/images/imatgeLocal.jpg}" alt="Imatge local" />
```

#### Càrrega d'Imatge des d'un Enllaç

Si vols carregar una imatge directament des d'un enllaç (URL en línia), pots assignar l'URL com a valor de `th:src`:

```html
<img th:src="'https://example.com/imatgeEnLinia.jpg'" alt="Imatge en línia" />
```

En aquest cas, assegura't d'incloure l'URL entre cometes simples dins del valor `th:src` perquè Thymeleaf l’interpreti correctament.

---

Aquest codi et proporciona una base sòlida per treballar amb Thymeleaf en projectes Java, generant HTML dinàmic a partir de plantilles i dades.











# Exercicis Pràctics: Thymeleaf


## Exercici 1: Visualització bàsica d'un objecte

Crea una plantilla Thymeleaf anomenada `usuari.html` que mostre el nom i l’edat d’un objecte `Usuari`.

#### Classe Usuari

```java
public class Usuari {
    private String nom;
    private int edat;

    public Usuari(String nom, int edat) {
        this.nom = nom;
        this.edat = edat;
    }

    public String getNom() {
        return nom;
    }

    public int getEdat() {
        return edat;
    }
}
```

#### `main` d'exemple

```java
public class Main {
    public static void main(String[] args) {
        // 1. Crea un objecte Usuari amb un nom i una edat.
        Usuari usuari = new Usuari("Maria", 28);

        // 2. Configura Thymeleaf: crea un context i carrega la plantilla `usuari.html`.

        // 3. Passa l'objecte Usuari al context per a mostrar les propietats `nom` i `edat` en la plantilla.

        // 4. Processa la plantilla i mostra el resultat per consola o en un fitxer HTML.
    }
}
```

---

###Exercici 2: Mostrar una llista d'objectes en una taula


Crea una plantilla Thymeleaf anomenada `productes.html` que mostre una taula amb la llista de productes, incloent les seues propietats `nom` i `preu`.

#### Classe Producte

```java
public class Producte {
    private String nom;
    private double preu;

    public Producte(String nom, double preu) {
        this.nom = nom;
        this.preu = preu;
    }

    public String getNom() {
        return nom;
    }

    public double getPreu() {
        return preu;
    }
}
```

#### `main` d'exemple

```java
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // 1. Crea una llista de Producte amb diversos productes.
        List<Producte> productes = new ArrayList<>();
        productes.add(new Producte("Ordinador", 900.0));
        productes.add(new Producte("Teclat", 30.0));
        productes.add(new Producte("Ratolí", 15.0));

        // 2. Configura Thymeleaf: crea un context i carrega la plantilla `productes.html`.

        // 3. Passa la llista de productes al context per mostrar cada producte en la taula.

        // 4. Processa la plantilla i mostra el resultat per consola o en un fitxer HTML.
    }
}
```

---

## Exercici 3: Objecte complex amb una llista de subobjectes

#### Enunciat de la plantilla
Crea una plantilla anomenada `categories.html` que mostre les categories i la llista de productes dins de cada categoria. Cada categoria ha de tindre un títol amb el nom i una llista amb els productes (`nom` i `preu`).

#### Classe Categoria i Producte

```java
import java.util.List;

public class Categoria {
    private String nom;
    private List<Producte> productes;

    public Categoria(String nom, List<Producte> productes) {
        this.nom = nom;
        this.productes = productes;
    }

    public String getNom() {
        return nom;
    }

    public List<Producte> getProductes() {
        return productes;
    }
}
```

#### `main` d'exemple

```java
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // 1. Crea llistes de productes per a cada categoria.
        List<Producte> electrònica = new ArrayList<>();
        electrònica.add(new Producte("Portàtil", 1200.0));
        electrònica.add(new Producte("Mòbil", 800.0));

        List<Producte> moda = new ArrayList<>();
        moda.add(new Producte("Samarreta", 25.0));
        moda.add(new Producte("Jaqueta", 60.0));

        // 2. Crea objectes Categoria amb els productes associats.
        Categoria categoria1 = new Categoria("Electrònica", electrònica);
        Categoria categoria2 = new Categoria("Moda", moda);

        // 3. Configura Thymeleaf: crea un context i carrega la plantilla `categories.html`.

        // 4. Passa les categories al context per mostrar-les amb la seua llista de productes.

        // 5. Processa la plantilla i mostra el resultat per consola o en un fitxer HTML.
    }
}
```

---


# Solucions


---

## Exercici 1: Visualització bàsica d'un objecte

#### Classe Usuari

```java
public class Usuari {
    private String nom;
    private int edat;

    public Usuari(String nom, int edat) {
        this.nom = nom;
        this.edat = edat;
    }

    public String getNom() {
        return nom;
    }

    public int getEdat() {
        return edat;
    }
}
```

#### `main` complet

```java
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

public class Main {
    public static void main(String[] args) {
        Usuari usuari = new Usuari("Maria", 28);

        // Configura Thymeleaf
        ClassLoaderTemplateResolver resolver = new ClassLoaderTemplateResolver();
        resolver.setSuffix(".html");
        resolver.setTemplateMode("HTML");

        TemplateEngine templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(resolver);

        // Crea el context i passa l'objecte Usuari
        Context context = new Context();
        context.setVariable("usuari", usuari);

        // Processa la plantilla
        String result = templateEngine.process("usuari", context);

        // Mostra el resultat
        System.out.println(result);
    }
}
```

#### Plantilla `usuari.html`

```html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <title>Usuari</title>
</head>
<body>
    <h1>Informació de l'usuari</h1>
    <p>Nom: <span th:text="${usuari.nom}">Nom de l'usuari</span></p>
    <p>Edat: <span th:text="${usuari.edat}">Edat de l'usuari</span></p>
</body>
</html>
```

---

## Exercici 2: Mostrar una llista d'objectes en una taula

#### Classe Producte

```java
public class Producte {
    private String nom;
    private double preu;

    public Producte(String nom, double preu) {
        this.nom = nom;
        this.preu = preu;
    }

    public String getNom() {
        return nom;
    }

    public double getPreu() {
        return preu;
    }
}
```

#### `main` complet

```java
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Producte> productes = new ArrayList<>();
        productes.add(new Producte("Ordinador", 900.0));
        productes.add(new Producte("Teclat", 30.0));
        productes.add(new Producte("Ratolí", 15.0));

        ClassLoaderTemplateResolver resolver = new ClassLoaderTemplateResolver();
        resolver.setSuffix(".html");
        resolver.setTemplateMode("HTML");

        TemplateEngine templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(resolver);

        Context context = new Context();
        context.setVariable("productes", productes);

        String result = templateEngine.process("productes", context);
        System.out.println(result);
    }
}
```

#### Plantilla `productes.html`

```html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <title>Productes</title>
</head>
<body>
    <h1>Llista de productes</h1>
    <table>
        <tr>
            <th>Nom</th>
            <th>Preu</th>
        </tr>
        <tr th:each="producte : ${productes}">
            <td th:text="${producte.nom}">Nom del producte</td>
            <td th:text="${producte.preu}">Preu del producte</td>
        </tr>
    </table>
</body>
</html>
```

---

## Exercici 3: Objecte complex amb una llista de subobjectes

#### Classe Categoria i Producte

```java
import java.util.List;

public class Categoria {
    private String nom;
    private List<Producte> productes;

    public Categoria(String nom, List<Producte> productes) {
        this.nom = nom;
        this.productes = productes;
    }

    public String getNom() {
        return nom;
    }

    public List<Producte> getProductes() {
        return productes;
    }
}
```

#### `main` complet

```java
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Producte> electrònica = new ArrayList<>();
        electrònica.add(new Producte("Portàtil", 1200.0));
        electrònica.add(new Producte("Mòbil", 800.0));

        List<Producte> moda = new ArrayList<>();
        moda.add(new Producte("Samarreta", 25.0));
        moda.add(new Producte("Jaqueta", 60.0));

        Categoria categoria1 = new Categoria("Electrònica", electrònica);
        Categoria categoria2 = new Categoria("Moda", moda);

        List<Categoria> categories = new ArrayList<>();
        categories.add(categoria1);
        categories.add(categoria2);

        ClassLoaderTemplateResolver resolver = new ClassLoaderTemplateResolver();
        resolver.setSuffix(".html");
        resolver.setTemplateMode("HTML");

        TemplateEngine templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(resolver);

        Context context = new Context();
        context.setVariable("categories", categories);

        String result = templateEngine.process("categories", context);
        System.out.println(result);
    }
}
```

#### Plantilla `categories.html`

```html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <title>Categories</title>
</head>
<body>
    <h1>Llista de Categories</h1>
    <div th:each="categoria : ${categories}">
        <h2 th:text="${categoria.nom}">Nom de la categoria</h2>
        <ul>
            <li th:each="producte : ${categoria.productes}" th:text="${producte.nom} + ' - ' + ${producte.preu}">Producte</li>
        </ul>
    </div>
</body>
</html>
```

---

# Exercicis de reforç

## Exercici 1: Mostrar una llista de productes amb imatges

Modifica l'exercici 2 per a mostrar una llista de productes amb imatges. Cada producte ha de tindre una imatge associada que es mostre en la taula.

#### Classe Producte

```java
public class Producte {
    private String nom;
    private double preu;
    private String imatge;

    public Producte(String nom, double preu, String imatge) {
        this.nom = nom;
        this.preu = preu;
        this.imatge = imatge;
    }

    public String getNom() {
        return nom;
    }

    public double getPreu() {
        return preu;
    }

    public String getImatge() {
        return imatge;
    }
}
```

#### `main` d'exemple

```java
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Producte> productes = new ArrayList<>();
        productes.add(new Producte("Ordinador", 900.0, "ordinador.jpg"));
        productes.add(new Producte("Teclat", 30.0, "teclat.jpg"));
        productes.add(new Producte("Ratolí", 15.0, "ratoli.jpg"));

        // Configura Thymeleaf: crea un context i carrega la plantilla `productes.html`.

        // Passa la llista de productes al context per mostrar cada producte en la taula.

        // Processa la plantilla i mostra el resultat per consola o en un fitxer HTML.
    }
}
```


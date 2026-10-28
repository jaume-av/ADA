---
title: Jackson 3. Mapeig JSON i XML
layout: default
nav_exclude: true
search_exclude: true
---

# Mapejant JSON i XML amb Jackson 3

Ja sabem observar un document JSON o XML i crear les classes Java que el representen. Ara farem el pas següent: **llegir el document per a obtindre objectes Java i escriure objectes Java en un document**.

Treballarem amb **Java 21 i Jackson 3**. Els exemples utilitzen Jackson **3.2.4** i mantenen les versions de JSON i XML iguals.

## 1. Què és mapejar amb Jackson?

Jackson relaciona les dades del document amb les **propietats Java**. En els nostres POJOs, cada propietat està representada per un camp i els seus mètodes d’accés. Els getters, setters i les anotacions intervenen en el mapeig; Jackson no es limita a copiar tots els camps privats.

| Procés | Direcció | Exemple |
| --- | --- | --- |
| **Deserialització** | JSON/XML → objecte Java | Llegir `producte.json` i obtindre un `Producte` |
| **Serialització** | Objecte Java → JSON/XML | Guardar un `Producte` en `producte.json` |

Per a fer-ho utilitzarem dos classes:

| Format | Classe de Jackson |
| --- | --- |
| JSON | `ObjectMapper` |
| XML | `XmlMapper` |

> **Recorda:** aquesta serialització genera **text JSON o XML**. No és la serialització binària de Java amb `ObjectOutputStream`, i els nostres POJOs no necessiten implementar `Serializable`.

---

## 2. Preparar el projecte Maven

Creem un projecte Maven amb Java 21. En el fitxer `pom.xml` posem les dependències necessàries:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0
                             https://maven.apache.org/xsd/maven-4.0.0.xsd">

    <modelVersion>4.0.0</modelVersion>

    <groupId>com.jaume</groupId>
    <artifactId>exemples-jackson</artifactId>
    <version>1.0-SNAPSHOT</version>

    <properties>
        <maven.compiler.release>21</maven.compiler.release>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <jackson.version>3.2.4</jackson.version>
    </properties>

    <dependencies>
        <!-- Llegir i escriure JSON -->
        <dependency>
            <groupId>tools.jackson.core</groupId>
            <artifactId>jackson-databind</artifactId>
            <version>${jackson.version}</version>
        </dependency>

        <!-- Llegir i escriure XML -->
        <dependency>
            <groupId>tools.jackson.dataformat</groupId>
            <artifactId>jackson-dataformat-xml</artifactId>
            <version>${jackson.version}</version>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <version>3.13.0</version>
            </plugin>
        </plugins>
    </build>

</project>
```

La propietat `maven.compiler.release` indica que compilarem per a Java 21. Fixem també la versió del plugin de compilació perquè el projecte no depenga de la versió que Maven trie per defecte.

Cada classe pública dels exemples es guarda en un fitxer `.java` amb el mateix nom, dins de `src/main/java`. Els fragments que no declaren una classe s’incorporen al lloc indicat; no són programes independents.

Després de modificar el `pom.xml`, actualitzem el projecte Maven des d’IntelliJ perquè descarregue les dependències.

### On posarem els fitxers?

Utilitzarem aquesta distribució:

```text
exemples-jackson/
├── pom.xml
├── dades/
│   ├── producte.json
│   ├── cataleg.json
│   └── producte.xml
└── src/
    └── main/
        └── java/
            ├── Producte.java
            └── ...
```

Crearem manualment la carpeta `dades` **a l’arrel del projecte**. Tant els documents d’entrada com els fitxers que genere el programa estaran ací.

> En aquests exemples, `new File("dades/producte.json")` busca el fitxer respecte del **directori de treball**. Si IntelliJ executa el programa des d’un altre directori, cal revisar la configuració d’execució.

### Imports de Jackson 3

Cal distingir els paquets:

| Què necessitem? | Import |
| --- | --- |
| Mapper JSON | `tools.jackson.databind.ObjectMapper` |
| Mapper XML | `tools.jackson.dataformat.xml.XmlMapper` |
| Anotacions comunes | `com.fasterxml.jackson.annotation.*` |
| Anotacions específiques XML | `tools.jackson.dataformat.xml.annotation.*` |

**Atenció:** en Jackson 3, els mappers passen a `tools.jackson`, però les anotacions comunes, com `@JsonProperty` i `@JsonFormat`, continuen en `com.fasterxml.jackson.annotation`.

---

## 3. Primer exemple: llegir i escriure una `String` JSON

Partim d’aquest JSON:

```json
{
  "nom": "Ratolí Gamer",
  "preu": 24.99,
  "actiu": true
}
```

Ja sabem crear el POJO corresponent.

### `Producte.java`

```java
public class Producte {

    private String nom;
    private double preu;
    private boolean actiu;

    public Producte() {
    }

    public Producte(String nom, double preu, boolean actiu) {
        this.nom = nom;
        this.preu = preu;
        this.actiu = actiu;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public double getPreu() {
        return preu;
    }

    public void setPreu(double preu) {
        this.preu = preu;
    }

    public boolean isActiu() {
        return actiu;
    }

    public void setActiu(boolean actiu) {
        this.actiu = actiu;
    }
}
```

En aquest model senzill, Jackson pot crear un `Producte` amb el **constructor buit** i assignar-li després els valors mitjançant els *setters*. Els *getters* li permeten obtindre els valors quan escriu el JSON.

El constructor buit és el mecanisme que utilitzarem en aquests exemples, però Jackson també admet altres formes de crear objectes. Per a serialitzar un objecte que ja existeix no necessita cridar aquest constructor.

Utilitzem `double` per a centrar-nos en el mapeig. En aplicacions amb càlculs monetaris que requerisquen precisió decimal, convé utilitzar `BigDecimal`.

### `PrimerJackson.java`

```java
import tools.jackson.databind.ObjectMapper;

public class PrimerJackson {

    public static void main(String[] args) {

        String json = """
                {
                  "nom": "Ratolí Gamer",
                  "preu": 24.99,
                  "actiu": true
                }
                """;

        ObjectMapper mapper = new ObjectMapper();

        // JSON → objecte Java
        Producte producte = mapper.readValue(json, Producte.class);

        System.out.println("Nom: " + producte.getNom());
        System.out.println("Preu: " + producte.getPreu());
        System.out.println("Actiu: " + producte.isActiu());

        // Objecte Java → JSON
        String jsonGenerat = mapper.writeValueAsString(producte);

        System.out.println("JSON generat:");
        System.out.println(jsonGenerat);
    }
}
```

La línia més important de la lectura és:

```java
Producte producte = mapper.readValue(json, Producte.class);
```

Li indiquem a Jackson **quin text ha de llegir** i **de quin tipus ha de ser el resultat**. `Producte.class` representa el tipus `Producte`: no crea un objecte ni crida el constructor. Jackson utilitza eixa informació per a construir un objecte nou i assignar-li les dades.

La deserialització recupera dades, no la identitat d’un objecte anterior. Dos objectes poden contindre els mateixos valors i ser instàncies diferents.

Per a fer el camí contrari:

```java
String jsonGenerat = mapper.writeValueAsString(producte);
```

El resultat queda en una `String`. **Encara no hem creat cap fitxer.**

L’inici de l’eixida és:

```text
Nom: Ratolí Gamer
Preu: 24.99
Actiu: true
JSON generat:
```

A continuació apareix el JSON en una línia. L’ordre de les propietats pot ser diferent del document d’entrada.

### Donar format llegible a una escriptura

Dins del `main`, podem substituir l’escriptura anterior per:

```java
String jsonGenerat = mapper.writerWithDefaultPrettyPrinter()
        .writeValueAsString(producte);
```

`writerWithDefaultPrettyPrinter()` retorna un **`ObjectWriter`**, un objecte preparat per a escriure amb indentació. Després, `writeValueAsString` fa la conversió. Aquesta configuració afecta eixa escriptura i no modifica el mapper.

La indentació facilita llegir el resultat; no canvia les dades. També podem encadenar `.writeValue(fitxer, producte)` per a guardar un fitxer llegible.

---

## 4. Llegir i escriure fitxers JSON

Ara el JSON estarà en un fitxer real.

Creem `dades/producte.json`:

```json
{
  "nom": "Ratolí Gamer",
  "preu": 24.99,
  "actiu": true
}
```

### `LligIEscriuJSON.java`

```java
import java.io.File;
import tools.jackson.databind.ObjectMapper;

public class LligIEscriuJSON {

    public static void main(String[] args) {

        ObjectMapper mapper = new ObjectMapper();

        File entrada = new File("dades/producte.json");

        // Llegim el fitxer i obtenim un objecte Java.
        Producte producte = mapper.readValue(entrada, Producte.class);

        System.out.println("Producte llegit: " + producte.getNom());
        System.out.println("Preu anterior: " + producte.getPreu());

        // Modifiquem l'objecte en memòria.
        producte.setPreu(29.99);

        // Escrivim l'objecte en un altre fitxer JSON.
        File eixida = new File("dades/producte_modificat.json");
        mapper.writeValue(eixida, producte);

        System.out.println("Preu nou: " + producte.getPreu());
        System.out.println("Fitxer guardat en: " + eixida.getAbsolutePath());
    }
}
```

La diferència respecte de l’apartat anterior és el **primer argument**:

| Crida | Què llig o escriu? |
| --- | --- |
| `readValue(json, Producte.class)` | El contingut d’una `String` |
| `readValue(entrada, Producte.class)` | El contingut d’un fitxer |
| `writeValueAsString(producte)` | Retorna una `String` |
| `writeValue(eixida, producte)` | Escriu en un fitxer |

`writeValue` crea el fitxer d’eixida si no existeix i en substitueix el contingut si ja existeix. En aquest exemple, `producte.json` continua amb el preu original; el canvi es guarda en `producte_modificat.json`.

> **Important:** `readValue("dades/producte.json", Producte.class)` intentaria interpretar eixe text com a **JSON**, no com una ruta. Per a llegir el fitxer hem passat un `File`.

---

## 5. Objectes dins d’objectes i llistes

Suposem que tenim `dades/cataleg.json`:

```json
{
  "nom": "Perifèrics",
  "productes": [
    {
      "nom": "Ratolí Gamer",
      "preu": 24.99,
      "actiu": true
    },
    {
      "nom": "Teclat Mecànic",
      "preu": 59.90,
      "actiu": true
    }
  ]
}
```

L’arrel és un objecte: `Cataleg`. La propietat `productes` és una llista d’objectes: `List<Producte>`.

### `Cataleg.java`

```java
import java.util.List;

public class Cataleg {

    private String nom;
    private List<Producte> productes;

    public Cataleg() {
    }

    public Cataleg(String nom, List<Producte> productes) {
        this.nom = nom;
        this.productes = productes;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public List<Producte> getProductes() {
        return productes;
    }

    public void setProductes(List<Producte> productes) {
        this.productes = productes;
    }
}
```

Reutilitzem `Producte.java`; no cal tornar a definir els seus camps dins de `Cataleg`.

### `LligCataleg.java`

```java
import java.io.File;
import tools.jackson.databind.ObjectMapper;

public class LligCataleg {

    public static void main(String[] args) {

        ObjectMapper mapper = new ObjectMapper();

        File entrada = new File("dades/cataleg.json");
        Cataleg cataleg = mapper.readValue(entrada, Cataleg.class);

        System.out.println("Catàleg: " + cataleg.getNom());
        System.out.println("Nombre de productes: "
                + cataleg.getProductes().size());

        for (Producte producte : cataleg.getProductes()) {
            System.out.println(producte.getNom()
                    + " - " + producte.getPreu() + " €");
        }

        File eixida = new File("dades/cataleg_copia.json");
        mapper.writeValue(eixida, cataleg);
    }
}
```

Jackson observa `List<Producte>` i crea un objecte `Producte` per cada element de l’array JSON.

> **Compte amb els decimals:** `59.90` i `59.9` representen el mateix valor `double`. En tornar a escriure el document, Jackson pot mostrar `59.9`.

### Un objecte dins d’un altre

En el cas de la fira, `organitzador` és un objecte únic. La classe que representa la fira tindrà una propietat del tipus `Organitzador`, amb el seu getter i setter:

```java
// Fragment dins de la classe Fira.
private Organitzador organitzador;

public Organitzador getOrganitzador() {
    return organitzador;
}

public void setOrganitzador(Organitzador organitzador) {
    this.organitzador = organitzador;
}
```

`Organitzador` és el POJO que ja hem dissenyat per a les dades internes. Jackson el crea en llegir la fira, igual que crea cada `Producte` dins de `Cataleg`.

| Estructura del document | Tipus de propietat Java |
| --- | --- |
| Un objecte `organitzador` | `Organitzador` |
| Diversos objectes `productes` | `List<Producte>` |
| Diverses cadenes `categories` | `List<String>` |

**Per a comprovar que ho heu entés:** afegiu un tercer producte al fitxer, executeu el programa i observeu com canvia el resultat.

---

## 6. Anotacions comunes: quan els noms o els valors requereixen ajustos

Fins ara, els noms Java i els noms JSON coincidien. En documents reals pot aparéixer `nom_usuari` en el JSON, mentre que en Java preferim un camp `nom`.

Les **anotacions** indiquen a Jackson com ha de tractar aquestes diferències.

| Anotació | Funció principal |
| --- | --- |
| `@JsonProperty("nom")` | Indica el nom de la propietat en el document |
| `@JsonIgnore` | Exclou una propietat de la lectura i l’escriptura |
| `@JsonInclude(NON_NULL)` | Omet propietats amb valor `null` en escriure |
| `@JsonFormat(...)` | Indica un format, per exemple per a una data |

Aquestes són anotacions **comunes de Jackson**; no són exclusives de JSON.

Una anotació sobre la classe, com `@JsonInclude`, estableix un criteri general per a les seues propietats. Sobre un camp, afecta eixa propietat. També hi ha anotacions que es poden aplicar als getters o setters.

En l’exemple següent, observeu els canvis en aquest ordre: primer el nom amb `@JsonProperty`, després el camp exclòs amb `@JsonIgnore` i finalment els nuls amb `@JsonInclude`.

### Exemple: `Usuari.java`

```java
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Usuari {

    @JsonProperty("nom_usuari")
    private String nom;

    private String correu;

    @JsonIgnore
    private String contrasenya;

    public Usuari() {
    }

    public Usuari(String nom, String correu, String contrasenya) {
        this.nom = nom;
        this.correu = correu;
        this.contrasenya = contrasenya;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getCorreu() {
        return correu;
    }

    public void setCorreu(String correu) {
        this.correu = correu;
    }

    public String getContrasenya() {
        return contrasenya;
    }

    public void setContrasenya(String contrasenya) {
        this.contrasenya = contrasenya;
    }
}
```

### `ProvaAnotacions.java`

```java
import tools.jackson.databind.ObjectMapper;

public class ProvaAnotacions {

    public static void main(String[] args) {

        ObjectMapper mapper = new ObjectMapper();

        Usuari usuari = new Usuari("Anna", null, "secret");

        String json = mapper.writerWithDefaultPrettyPrinter()
                .writeValueAsString(usuari);

        System.out.println(json);

        String jsonRebut = """
                {
                  "nom_usuari": "Pau",
                  "correu": "pau@example.org",
                  "contrasenya": "1234"
                }
                """;

        Usuari llegit = mapper.readValue(jsonRebut, Usuari.class);

        System.out.println("Usuari llegit: " + llegit.getNom());
        System.out.println("Correu: " + llegit.getCorreu());
        System.out.println("Contrasenya: " + llegit.getContrasenya());
    }
}
```

El primer JSON contindrà `nom_usuari`. No contindrà `correu`, perquè és `null`, ni `contrasenya`, perquè porta `@JsonIgnore`:

```json
{
  "nom_usuari" : "Anna"
}
```

En la lectura de `jsonRebut`, Jackson associa `nom_usuari` amb el camp Java `nom`. La contrasenya del JSON s’ignora i `getContrasenya()` retorna `null`. `@JsonIgnore` no xifra dades: simplement exclou la propietat del mapeig.

**Diferència important entre les anotacions:**

- `NON_NULL` omet només els valors `null`.
- `NON_EMPTY` també permet ometre valors buits, com una cadena buida o una col·lecció buida.
- `@JsonIgnore` exclou la propietat encara que tinga un valor.

En Jackson 3, els camps desconeguts del document s’ignoren per defecte. Més endavant podem estudiar `@JsonIgnoreProperties`, però no el necessitem per a aquests primers exemples. Tampoc convé donar per garantit l’ordre de les propietats del JSON: per a llegir-lo importen els **noms**, no l’ordre.

---

## 7. Llegir i escriure XML amb el mateix POJO

Tornem a `Producte.java`. Ara creem `dades/producte.xml`:

```xml
<Producte>
    <nom>Ratolí Gamer</nom>
    <preu>24.99</preu>
    <actiu>true</actiu>
</Producte>
```

La classe `Producte` continua servint perquè les etiquetes internes coincideixen amb les seues propietats.

### `LligIEscriuXML.java`

```java
import java.io.File;
import tools.jackson.dataformat.xml.XmlMapper;

public class LligIEscriuXML {

    public static void main(String[] args) {

        XmlMapper mapper = new XmlMapper();

        File entrada = new File("dades/producte.xml");
        Producte producte = mapper.readValue(entrada, Producte.class);

        System.out.println("Producte llegit: " + producte.getNom());

        producte.setPreu(27.50);

        File eixida = new File("dades/producte_modificat.xml");
        mapper.writeValue(eixida, producte);

        System.out.println("XML guardat en: " + eixida.getAbsolutePath());
    }
}
```

Observeu el paral·lelisme:

| JSON | XML |
| --- | --- |
| `ObjectMapper` | `XmlMapper` |
| `readValue(...)` | `readValue(...)` |
| `writeValue(...)` | `writeValue(...)` |

Amb `XmlMapper` també podem llegir una `String` XML amb `readValue(textXML, Producte.class)` i generar text amb `writeValueAsString(producte)`. Les operacions són les mateixes; el mapper determina el format.

> En aquest exemple senzill podem reutilitzar el POJO directament. Quan l’XML tinga **atributs** o **llistes amb contenidors**, haurem d’indicar a Jackson com és l’estructura exacta.

---

## 8. XML: arrel, atributs i elements repetits

Fixem-nos en aquest document `dades/zoologic.xml`:

```xml
<zoologic codi="Z-1">
    <animals>
        <animal tipus="mamífer">
            <nom>Lleó</nom>
        </animal>
        <animal tipus="ocell">
            <nom>Lloro</nom>
        </animal>
    </animals>
    <avis>Obert dissabte</avis>
    <avis>Última entrada a les 18 h</avis>
</zoologic>
```

Abans de programar, analitzem-ne l’estructura:

| Part de l’XML | Representació Java |
| --- | --- |
| `<zoologic>` | Classe `Zoologic` |
| `codi="Z-1"` | Camp `String codi`, com a atribut XML |
| `<animals>...</animals>` | Contenidor de la llista |
| Diversos `<animal>` | `List<Animal>` |
| `tipus="mamífer"` | Camp `String tipus`, com a atribut XML |
| Diversos `<avis>` directament dins de l’arrel | `List<String>` sense contenidor |

### `Animal.java`

```java
import tools.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class Animal {

    @JacksonXmlProperty(isAttribute = true)
    private String tipus;

    private String nom;

    public Animal() {
    }

    public Animal(String tipus, String nom) {
        this.tipus = tipus;
        this.nom = nom;
    }

    public String getTipus() {
        return tipus;
    }

    public void setTipus(String tipus) {
        this.tipus = tipus;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
}
```

Sense `isAttribute = true`, Jackson representaria `tipus` com un element `<tipus>`, en lloc de l’atribut `tipus="..."`.

### `Zoologic.java`

```java
import java.util.List;
import com.fasterxml.jackson.annotation.JsonRootName;
import tools.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import tools.jackson.dataformat.xml.annotation.JacksonXmlProperty;

@JsonRootName("zoologic")
public class Zoologic {

    @JacksonXmlProperty(isAttribute = true)
    private String codi;

    @JacksonXmlElementWrapper(localName = "animals")
    @JacksonXmlProperty(localName = "animal")
    private List<Animal> animals;

    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "avis")
    private List<String> avisos;

    public Zoologic() {
    }

    public Zoologic(String codi, List<Animal> animals,
                     List<String> avisos) {
        this.codi = codi;
        this.animals = animals;
        this.avisos = avisos;
    }

    public String getCodi() {
        return codi;
    }

    public void setCodi(String codi) {
        this.codi = codi;
    }

    public List<Animal> getAnimals() {
        return animals;
    }

    public void setAnimals(List<Animal> animals) {
        this.animals = animals;
    }

    public List<String> getAvisos() {
        return avisos;
    }

    public void setAvisos(List<String> avisos) {
        this.avisos = avisos;
    }
}
```

Hi ha **dos decisions de disseny diferents** per a les llistes:

```java
@JacksonXmlElementWrapper(localName = "animals")
@JacksonXmlProperty(localName = "animal")
private List<Animal> animals;
```

Açò correspon a:

```xml
<animals>
    <animal>...</animal>
    <animal>...</animal>
</animals>
```

En canvi:

```java
@JacksonXmlElementWrapper(useWrapping = false)
@JacksonXmlProperty(localName = "avis")
private List<String> avisos;
```

Correspon a:

```xml
<avis>Obert dissabte</avis>
<avis>Última entrada a les 18 h</avis>
```

**No hi ha cap etiqueta `<avisos>`**. Per això indiquem `useWrapping = false`. En Jackson XML, les col·leccions tenen contenidor per defecte; no hem de confiar que llevar l’anotació elimine el contenidor.

`@JsonRootName("zoologic")` indica el nom de l’arrel quan escrivim l’XML. En Jackson 3 és la forma recomanada per a aquest cas; `@JacksonXmlRootElement` està obsoleta. Aquesta anotació no valida el nom de l’arrel quan llegim i no activa, per si mateixa, un embolcall equivalent en JSON.

### `LligZoologicXML.java`

```java
import java.io.File;
import tools.jackson.dataformat.xml.XmlMapper;

public class LligZoologicXML {

    public static void main(String[] args) {

        XmlMapper mapper = new XmlMapper();

        Zoologic zoologic = mapper.readValue(
                new File("dades/zoologic.xml"),
                Zoologic.class
        );

        System.out.println("Codi: " + zoologic.getCodi());

        for (Animal animal : zoologic.getAnimals()) {
            System.out.println(animal.getNom()
                    + " (" + animal.getTipus() + ")");
        }

        for (String avis : zoologic.getAvisos()) {
            System.out.println("Avís: " + avis);
        }

        mapper.writeValue(
                new File("dades/zoologic_copia.xml"),
                zoologic
        );
    }
}
```

L’eixida per consola és:

```text
Codi: Z-1
Lleó (mamífer)
Lloro (ocell)
Avís: Obert dissabte
Avís: Última entrada a les 18 h
```

**Idea principal:** primer decidim quines classes i llistes representen el document. Després afegim només les anotacions necessàries per a expressar els detalls propis de l’XML.

---

## 9. Dates amb `LocalDate`

Suposem que volem representar una data:

```json
{
  "nom": "Fira de videojocs",
  "data": "27-10-2026"
}
```

En Java utilitzarem `LocalDate`. Per a indicar el format `dia-mes-any`, aplicarem `@JsonFormat`.

### `Esdeveniment.java`

```java
import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonFormat;

public class Esdeveniment {

    private String nom;

    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate data;

    public Esdeveniment() {
    }

    public Esdeveniment(String nom, LocalDate data) {
        this.nom = nom;
        this.data = data;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }
}
```

### `ProvaDates.java`

```java
import java.time.LocalDate;
import tools.jackson.databind.ObjectMapper;

public class ProvaDates {

    public static void main(String[] args) {

        ObjectMapper mapper = new ObjectMapper();

        Esdeveniment esdeveniment = new Esdeveniment(
                "Fira de videojocs",
                LocalDate.of(2026, 10, 27)
        );

        String json = mapper.writerWithDefaultPrettyPrinter()
                .writeValueAsString(esdeveniment);

        System.out.println(json);

        Esdeveniment llegit =
                mapper.readValue(json, Esdeveniment.class);

        System.out.println("Data recuperada: "
                + llegit.getData());
    }
}
```

El JSON generat mostra el format definit per l’anotació:

```json
{
  "data": "27-10-2026",
  "nom": "Fira de videojocs"
}
```

Quan imprimim directament el `LocalDate` amb `getData()`, Java el mostra habitualment com `2026-10-27`. **Són dos representacions del mateix valor.**

En Jackson 3, el suport de `java.time`, inclòs `LocalDate`, forma part de `jackson-databind`: no hem d’afegir `jackson-datatype-jsr310` ni registrar `JavaTimeModule`. Sense `@JsonFormat`, el format de cadena habitual per a `LocalDate` és l’ISO, com `2026-10-27`.

### Configurar la indentació de totes les escriptures

En l’exemple hem utilitzat:

```java
mapper.writerWithDefaultPrettyPrinter()
      .writeValueAsString(esdeveniment);
```

Això formata **aquesta escriptura concreta**. Si volem configurar el mapper perquè indente totes les seues eixides, en Jackson 3 fem servir un *builder*:

```java
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.SerializationFeature;
import tools.jackson.databind.json.JsonMapper;

ObjectMapper mapper = JsonMapper.builder()
        .enable(SerializationFeature.INDENT_OUTPUT)
        .build();
```

Amb `XmlMapper` es fa de la mateixa manera:

```java
import tools.jackson.databind.SerializationFeature;
import tools.jackson.dataformat.xml.XmlMapper;

XmlMapper mapper = XmlMapper.builder()
        .enable(SerializationFeature.INDENT_OUTPUT)
        .build();
```

**Per què apareix `builder()`?** En Jackson 3 configurem el mapper **abans de construir-lo**. Podem continuar utilitzant `new ObjectMapper()` o `new XmlMapper()` quan no necessitem canviar-ne la configuració.

---

## 10. Classes de mapeig i mètodes de consulta

`ObjectMapper` és la classe de mapeig que hem utilitzat per a JSON. `JsonMapper` especialitza el mapeig JSON i ens proporciona el builder. `XmlMapper` adapta el mapeig a XML. `ObjectWriter` prepara una escriptura amb opcions concretes.

### Mètodes de lectura de `ObjectMapper`

| Mètode | Retorn | Què rep i què fa? |
| --- | --- | --- |
| `readValue(String contingut, Class<T> tipus)` | `T` | Interpreta el text JSON i crea un objecte del tipus indicat. |
| `readValue(File fitxer, Class<T> tipus)` | `T` | Llig JSON d’un fitxer. |
| `readValue(InputStream flux, Class<T> tipus)` | `T` | Llig JSON d’un flux de bytes. |
| `readValue(Reader lector, Class<T> tipus)` | `T` | Llig JSON d’un lector de caràcters. |
| `readValue(String contingut, TypeReference<T> tipus)` | `T` | Conserva informació de tipus genèrics, com `List<Producte>`. |
| `readTree(String contingut)` | `JsonNode` | Crea un arbre JSON sense definir un POJO. També hi ha versions per a fitxer i flux. |

`T` representa el tipus del resultat: amb `Producte.class`, el retorn és un `Producte`. `Class<T>` i `TypeReference<T>` indiquen el tipus; no contenen les dades que llegim.

### Mètodes d’escriptura de `ObjectMapper`

| Mètode | Retorn | Què fa? |
| --- | --- | --- |
| `writeValueAsString(Object valor)` | `String` | Genera text JSON en memòria. |
| `writeValueAsBytes(Object valor)` | `byte[]` | Genera els bytes del JSON en memòria. |
| `writeValue(File fitxer, Object valor)` | `void` | Escriu JSON en un fitxer i substitueix el contingut anterior. |
| `writeValue(OutputStream flux, Object valor)` | `void` | Escriu JSON en un flux de bytes. |
| `writeValue(Writer escriptor, Object valor)` | `void` | Escriu JSON en un escriptor de caràcters. |
| `writerWithDefaultPrettyPrinter()` | `ObjectWriter` | Prepara una escriptura amb indentació; encara no escriu dades. |

`void` significa que el mètode no retorna un valor. El resultat està en la destinació indicada. `File`, `InputStream`, `OutputStream`, `Reader` i `Writer` pertanyen a `java.io`.

### Mètodes de `XmlMapper`

| Mètode | Retorn | Particularitat XML |
| --- | --- | --- |
| `readValue(String contingut, Class<T> tipus)` | `T` | Interpreta una cadena XML i crea el POJO. |
| `readValue(File fitxer, Class<T> tipus)` | `T` | Llig un fitxer XML segons el model i les anotacions. |
| `readValue(InputStream flux, Class<T> tipus)` | `T` | Llig XML d’un flux de bytes. |
| `writeValueAsString(Object valor)` | `String` | Genera XML amb un element arrel. |
| `writeValue(File fitxer, Object valor)` | `void` | Guarda XML en el fitxer. |
| `writeValue(OutputStream flux, Object valor)` | `void` | Escriu XML en un flux. |
| `writerWithDefaultPrettyPrinter()` | `ObjectWriter` | Prepara l’escriptura XML amb indentació. |

També admet les variants amb `Reader` i `Writer`. Encara que `XmlMapper` disposa de lectura en arbre, **`JsonNode` no conserva tots els detalls d’un document XML**, com la distinció general entre atributs i elements. En aquests apunts utilitzem POJOs per a XML.

### Configuració i `ObjectWriter`

| Expressió | Resultat | Ús |
| --- | --- | --- |
| `JsonMapper.builder()` | Builder JSON | Preparar la configuració del mapper JSON. |
| `XmlMapper.builder()` | Builder XML | Preparar la configuració del mapper XML. |
| `.enable(SerializationFeature.INDENT_OUTPUT)` | El builder | Activar la indentació abans de construir el mapper. |
| `.build()` | Mapper configurat | Finalitzar la construcció i començar a llegir o escriure. |
| `writer.writeValueAsString(objecte)` | `String` | Escriure amb les opcions de l’`ObjectWriter`. |
| `writer.writeValue(fitxer, objecte)` | `void` | Aplicar les mateixes opcions a un fitxer. |

Configurem el mapper una vegada i el reutilitzem per a diverses operacions. No cal crear-ne un de nou per a cada producte.

Per a saber si treballarem amb JSON o XML, observem **quin mapper hem creat**:

```java
ObjectMapper json = new ObjectMapper();
XmlMapper xml = new XmlMapper();
```

El nom del fitxer ens ajuda a organitzar-nos, però és el mapper qui determina el format que llegim o escrivim.

### Anotacions que sí hem necessitat

| Anotació | Quan la posem? |
| --- | --- |
| `@JsonProperty` | El nom de la propietat del document és diferent del camp Java |
| `@JsonIgnore` | Volem excloure una propietat |
| `@JsonInclude` | Volem ometre determinats valors en escriure |
| `@JsonFormat` | Necessitem un format concret, com una data |
| `@JsonRootName` | Necessitem indicar el nom de l’arrel XML |
| `@JacksonXmlProperty` | Necessitem un atribut XML o canviar el nom d’un element |
| `@JacksonXmlElementWrapper` | Necessitem definir si una llista té contenidor XML |

> **Regla de treball:** comenceu amb POJOs senzills. Afegiu una anotació quan identifiqueu una diferència concreta entre la classe Java i el document.

---

## 11. Pràctica final: mapar els casos que ja coneixem

Ara aplicarem Jackson als dos documents complexos per als quals ja havíeu creat POJOs: **la fira de videojocs en JSON** i **la reserva d’hotel en XML**.

### Part A. Fira de videojocs

Partiu del JSON i de les classes de [la fira de videojocs](1Fitxers/2.-Gestio%20Contingut/2.3.1-Jackson%20-%20Conceptes%20bàsics%20Exercicis.md#cas-pràctic-1-fira-de-videojocs--json).

1. Guardeu el document com a `dades/fira.json`.
2. Llegiu-lo amb `ObjectMapper` i la classe que representa la fira.
3. Mostreu el nom de la fira, el nom de l’organitzador i el nombre de tornejos.
4. Recorreu els tornejos i mostreu el títol del joc i el nombre de participants.
5. Canvieu la quota del primer torneig a `7.50` en l’objecte Java.
6. Guardeu el resultat en `dades/fira_modificada.json`.

**Pista:** `tornejos` és una llista d’objectes. Cada torneig conté un objecte `joc` i una altra llista, `participants`. Si els camps i els noms JSON coincideixen, Jackson pot recórrer tota aquesta estructura amb els POJOs que ja teniu.

### Part B. Reserva d’hotel

Partiu de l’XML i de les classes de [la reserva d’hotel](1Fitxers/2.-Gestio%20Contingut/2.3.1-Jackson%20-%20Conceptes%20bàsics%20Exercicis.md#cas-pràctic-2-reserva-dhotel--xml). Indiqueu `@JsonRootName("reserva")` en la classe arrel.

1. Guardeu el document com a `dades/reserva.xml`.
2. Reviseu els camps Java que corresponen als atributs XML `codi`, `confirmada` i `numero`.
3. Indiqueu amb anotacions la llista d’habitacions, que està dins de `<habitacions>`.
4. Indiqueu la llista d’ocupants, que està dins de `<ocupants>`.
5. Indiqueu els elements `<servei>` repetits directament dins de `<reserva>`, **sense** un contenidor `<serveis>`.
6. Llegiu el document amb `XmlMapper` i mostreu el codi de la reserva, el nom del client, el nombre d’habitacions i els serveis.
7. Guardeu una còpia en `dades/reserva_copia.xml`.

**Comprovació final:** torneu a llegir els dos fitxers generats amb Jackson. Comproveu que la quota és `7.50`, que es conserven els tornejos i participants, i que la reserva conserva el codi, les habitacions, els ocupants i els serveis. Després obriu els fitxers. Compareu-ne els valors i l’estructura amb els documents originals. En l’XML, fixeu-vos especialment que els atributs continuen sent atributs i que els `<servei>` no han acabat dins d’un contenidor nou.

---

## 12. Errors habituals i validació de les dades

Que Jackson puga llegir un document no significa que totes les dades siguen adequades per al nostre programa. Per exemple, `-5.0` és un nombre vàlid en JSON, però pot ser un preu invàlid per a la nostra aplicació.

| Situació | Què hem de revisar? |
| --- | --- |
| No es troba el fitxer | La ruta, el directori de treball i els permisos de lectura. |
| No es pot escriure el resultat | Que existisca la carpeta de destinació i tinguem permisos. `writeValue` no crea les carpetes intermèdies. |
| JSON o XML mal format | Les comes, cometes, claus o etiquetes; consulteu el missatge i la posició de l’error. |
| Un valor no es pot convertir al tipus Java | El valor d’entrada i el tipus de la propietat; per exemple, un text no numèric per a `double`. |
| Falta una propietat en el document | En aquests POJOs queda el valor inicial del camp: `null`, `0.0` o `false`, segons el tipus. |
| Falta una llista | Si no l’hem inicialitzada, pot quedar a `null`; cridar `size()` o recórrer-la provocaria un error. |
| Un nom està mal escrit | Pot interpretar-se com una propietat desconeguda i ignorar-se. Reviseu els noms i les anotacions. |
| El document es llig però les dades són inadequades | Apliqueu les regles del programa: preus no negatius, noms obligatoris, etc. |

Una propietat absent i una propietat amb `null` explícit no són sempre equivalents. El tractament de `null` també depén del tipus i de la configuració. En les pràctiques, distingiu una llista buida `[]` d’una llista absent.

### Detectar noms desconeguts

En Jackson 3, els camps desconeguts s’ignoren per defecte. Durant l’aprenentatge pot interessar-nos detectar-los. Aquest fragment configura un mapper estricte:

```java
import tools.jackson.databind.DeserializationFeature;
import tools.jackson.databind.json.JsonMapper;

JsonMapper mapper = JsonMapper.builder()
        .enable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
        .build();
```

Així, una propietat escrita com `preuu` no passarà desapercebuda. Aquesta opció no obliga que apareguen tots els camps del POJO: detectar propietats desconegudes i comprovar propietats obligatòries són tasques diferents.

### Capturar un error de lectura

Les excepcions pròpies de Jackson 3 deriven de `JacksonException`, que és una excepció no comprovada. No cal posar `throws Exception` per a aquestes crides, però sí que convé gestionar els errors quan volem informar l’usuari.

```java
import java.io.File;
import tools.jackson.core.JacksonException;
import tools.jackson.databind.ObjectMapper;

public class LecturaControlada {
    public static void main(String[] args) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            Producte producte = mapper.readValue(
                    new File("dades/producte.json"), Producte.class);

            if (producte.getNom() == null || producte.getNom().isBlank()) {
                System.err.println("El producte ha de tindre nom.");
                return;
            }
            if (producte.getPreu() < 0) {
                System.err.println("El preu no pot ser negatiu.");
                return;
            }
            System.out.println("Producte vàlid: " + producte.getNom());
        } catch (JacksonException e) {
            System.err.println("No s'ha pogut llegir el producte: "
                    + e.getMessage());
        }
    }
}
```

Aquestes comprovacions validen el nom i el signe del preu; no detecten si el preu faltava i ha quedat a `0.0`. Si necessitem distingir absència i zero, podem representar-lo amb `Double` i comprovar si és `null`.

### `@JsonIgnoreProperties`

Podem ignorar propietats concretes amb `@JsonIgnoreProperties({"id", "admin"})`. Per defecte, aquesta llista afecta la lectura i l’escriptura. Amb `ignoreUnknown = true` indiquem que s’ignoren els noms que no tenen correspondència en el POJO durant la lectura.

```java
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Perfil {
    private String nom;

    public Perfil() {}
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
}
```

En un mapper amb la configuració predeterminada de Jackson 3, aquesta opció és redundant. Té utilitat si hem activat la lectura estricta i volem permetre camps addicionals en aquesta classe concreta.

## 13. Ampliacions del mapeig

Aquests casos completen les possibilitats anteriors. Treballeu-los quan ja domineu la lectura i escriptura amb POJOs.

### 13.1. Una llista en l’arrel del JSON

En `Cataleg`, Jackson coneixia el tipus dels elements gràcies al camp `List<Producte>`. Si el document comença directament amb `[...]`, no hi ha un objecte contenidor.

`List.class` no indica quin tipus tenen els elements. Utilitzem `TypeReference<List<Producte>>` per a conservar aquesta informació:

```java
import java.util.List;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

public class LlistaArrel {
    public static void main(String[] args) {
        String json = """
                [
                  {"nom":"Ratolí", "preu":19.95, "actiu":true},
                  {"nom":"Teclat", "preu":39.90, "actiu":true}
                ]
                """;
        ObjectMapper mapper = new ObjectMapper();
        List<Producte> productes = mapper.readValue(
                json, new TypeReference<List<Producte>>() {});

        for (Producte producte : productes) {
            System.out.println(producte.getNom());
        }
        System.out.println(mapper.writeValueAsString(productes));
    }
}
```

La construcció `new TypeReference<...>() {}` crea una classe anònima que conserva el tipus genèric. Per a l’escriptura no necessitem repetir-la: Jackson rep els objectes que ha de convertir.

### 13.2. Llegir JSON sense POJOs: `JsonNode`

Si només necessitem consultar algunes dades i no tenim un model estable, podem llegir el document com un arbre. En aquest cas som nosaltres qui naveguem pels noms i interpretem els valors.

```java
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

public class LlegirArbre {
    public static void main(String[] args) {
        String json = """
                {"ciutat":"València", "temperatures":[19,21,18]}
                """;
        ObjectMapper mapper = new ObjectMapper();
        JsonNode arrel = mapper.readTree(json);
        JsonNode ciutat = arrel.get("ciutat");
        JsonNode temperatures = arrel.get("temperatures");

        if (ciutat == null || !ciutat.isString()
                || temperatures == null || !temperatures.isArray()) {
            System.err.println("El document no té l'estructura esperada.");
            return;
        }
        System.out.println(ciutat.stringValue());
        for (JsonNode temperatura : temperatures) {
            if (temperatura.isIntegralNumber()) {
                System.out.println(temperatura.intValue());
            }
        }
    }
}
```

`get` consulta una propietat; si no existeix, retorna `null`. Comprovem el tipus abans de recuperar el valor. Aquest estil requereix més comprovacions manuals que els POJOs.

### 13.3. Anotar getters i setters

`@JsonGetter` identifica el mètode que proporciona el valor en escriure; `@JsonSetter`, el que rep el valor en llegir. Podem indicar explícitament el nom de la propietat del document:

```java
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;

public class Llibre {
    private String titol;

    public Llibre() {}

    @JsonGetter("nom_llibre")
    public String getTitol() { return titol; }

    @JsonSetter("nom_llibre")
    public void setTitol(String titol) { this.titol = titol; }
}
```

Aquest model llig i escriu `nom_llibre`, mentre que els mètodes Java continuen anomenant-se `getTitol` i `setTitol`. Per a un canvi senzill de nom, `@JsonProperty` sol ser suficient.

### 13.4. Text directe i CDATA en XML

Un XML pot tindre text directament dins d’un element, a més d’atributs:

```xml
<missatge idioma="va">Bon dia</missatge>
```

`@JacksonXmlText` associa una propietat al text directe; no crea una etiqueta interior. En una classe només hi ha d’haver una propietat amb aquest paper.

```java
import com.fasterxml.jackson.annotation.JsonRootName;
import tools.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import tools.jackson.dataformat.xml.annotation.JacksonXmlText;

@JsonRootName("missatge")
public class Missatge {
    @JacksonXmlProperty(isAttribute = true)
    private String idioma;
    @JacksonXmlText
    private String text;

    public Missatge() {}
    public String getIdioma() { return idioma; }
    public void setIdioma(String idioma) { this.idioma = idioma; }
    public String getText() { return text; }
    public void setText(String text) { this.text = text; }
}
```

`@JacksonXmlCData` indica que el valor s’escriga en una secció CDATA. És una decisió sobre la representació XML; el valor Java continua sent una cadena.

```java
import tools.jackson.dataformat.xml.annotation.JacksonXmlCData;

public class Noticia {
    @JacksonXmlCData
    private String contingut;

    public Noticia() {}
    public Noticia(String contingut) { this.contingut = contingut; }
    public String getContingut() { return contingut; }
    public void setContingut(String contingut) { this.contingut = contingut; }
}
```

Provem les dos classes:

```java
import tools.jackson.dataformat.xml.XmlMapper;

public class ProvaTextXML {
    public static void main(String[] args) {
        XmlMapper mapper = new XmlMapper();
        Missatge missatge = mapper.readValue(
                "<missatge idioma=\"va\">Bon dia</missatge>", Missatge.class);
        System.out.println(missatge.getIdioma() + ": " + missatge.getText());
        System.out.println(mapper.writeValueAsString(missatge));

        Noticia noticia = new Noticia("Preu < 20 & disponible");
        String xml = mapper.writeValueAsString(noticia);
        System.out.println(xml);
        Noticia recuperada = mapper.readValue(xml, Noticia.class);
        System.out.println(recuperada.getContingut());
    }
}
```

El XML de la notícia és:

```xml
<Noticia><contingut><![CDATA[Preu < 20 & disponible]]></contingut></Noticia>
```

Sense CDATA, Jackson escaparia els caràcters especials, per exemple `<` com `&lt;`. En tornar a llegir-los recuperaríem el mateix text. `@JacksonXmlCData` té l’argument opcional `value`, que és `true` per defecte. No fa falta aquesta anotació per a guardar text normal correctament.

### 13.5. Afegir la declaració XML

La declaració `<?xml ...?>` és opcional en els exemples anteriors. Per a generar-la, configurem el mapper amb l’opció de Jackson 3:

```java
import tools.jackson.dataformat.xml.XmlMapper;
import tools.jackson.dataformat.xml.XmlWriteFeature;

XmlMapper mapper = XmlMapper.builder()
        .enable(XmlWriteFeature.WRITE_XML_DECLARATION)
        .build();
```

La declaració no s’activa simplement per utilitzar un fitxer amb extensió `.xml`.

### Altres anotacions de consulta

Aquestes opcions no són requisits per a les pràctiques del tema:

| Anotació o opció | Què aporta? |
| --- | --- |
| `@JsonInclude(Include.ALWAYS)` | Inclou també els valors nuls. |
| `@JsonInclude(Include.NON_EMPTY)` | Omet nuls i valors considerats buits, com cadenes o col·leccions buides. |
| `@JsonInclude(Include.NON_DEFAULT)` | Omet valors considerats predeterminats; el criteri depén de si s’aplica a la classe o a la propietat. No equival a `NON_NULL`. |
| `@JsonAnySetter` / `@JsonAnyGetter` | Permeten recollir propietats addicionals, habitualment en un `Map`, i escriure-les com a propietats del document. |
| `@JsonAutoDetect` | Ajusta la visibilitat dels camps i mètodes que Jackson utilitza. |
| `@JsonSerialize` / `@JsonDeserialize` | Permeten indicar serialitzadors o deserialitzadors propis; en Jackson 3 estan en `tools.jackson.databind.annotation`. |

`@JsonFormat` també ofereix `shape` per a indicar una representació, i `timezone` per als tipus on té sentit una zona horària. `LocalDate` només representa una data, sense hora ni zona: no necessita `timezone`. Un patró amb hores requereix un tipus que també represente hores.

## 14. Comprovar el que hem aprés

Abans de donar un exercici per acabat, comproveu que podeu:

1. Identificar la classe arrel, els objectes interns i les llistes.
2. Explicar per què cada anotació és necessària.
3. Llegir les dades i consultar-les des dels objectes Java.
4. Modificar un valor i guardar-lo en un altre fitxer.
5. Rellegir el resultat i comprovar els valors i les quantitats d’elements.
6. Distingir un problema de ruta, de sintaxi, de mapeig i de validació.

La comparació ha de centrar-se en les dades i en l’estructura que necessitem. Jackson no és un editor que conserve literalment el document original: poden canviar la indentació, l’ordre de propietats JSON o la representació dels decimals. Els comentaris XML tampoc formen part dels POJOs que hem definit.

## Referències

- [Guia oficial de migració a Jackson 3](https://github.com/FasterXML/jackson/blob/main/jackson3/MIGRATING_TO_JACKSON_3.md): paquets, configuració i suport temporal.
- [Jackson Databind](https://github.com/FasterXML/jackson-databind): mapeig entre documents i objectes Java.
- [Jackson XML](https://github.com/FasterXML/jackson-dataformat-xml/tree/3.x): ús de `XmlMapper` i anotacions XML.
- [Anotacions comunes de Jackson](https://github.com/FasterXML/jackson-annotations): noms, inclusió i visibilitat.
- [Maven Compiler Plugin: compilació amb `release`](https://maven.apache.org/plugins/maven-compiler-plugin/examples/set-compiler-release.html).

### Comprovació dels exemples

Les classes completes i els onze programes d’exemple s’han compilat i executat amb Java 21 i Jackson 3.2.4. Els fragments parcials s’han de situar en el context indicat. La construcció completa amb Maven queda pendent de comprovació: l’entorn de revisió no disposa de l’ordre `mvn`.

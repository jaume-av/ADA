---
title: 2.4.- Jackson. Serialitzant - Deserialitzat.
parent: 2.- Gestió del Contingut
grand_parent: Persistència de Fitxers
has_children: true
layout: default
nav_order: 70
---




# 2.x – Jackson (JSON i XML): Model híbrid de mapeig

## Introducció

Per a **mapejar** dades estructurades (JSON o XML) a **classes Java (POJOs/DTOs)**. Jackson ens ofereix un **nucli comú** d’anotacions i configuració que serveix tant per a JSON com per a XML, i un **conjunt reduït d’anotacions específiques d’XML** per a casos concrets (atributs, wrapper de llistes, etc.).

Per tant, es poden **reutilitzar les mateixes classes** per als dos formats i només afegir ajustos d’XML quan calga.

---

## Dependències Maven (comú + XML)

> Correcció: on posava “jackson-databin”, ha de ser **`jackson-databind`**.

```xml
<dependencies>
  <!-- Nucli JSON -->
  <dependency>
    <groupId>com.fasterxml.jackson.core</groupId>
    <artifactId>jackson-databind</artifactId>
    <version>2.17.2</version>
  </dependency>

  <!-- Dates Java 8+ (LocalDate, Instant...) -->
  <dependency>
    <groupId>com.fasterxml.jackson.datatype</groupId>
    <artifactId>jackson-datatype-jsr310</artifactId>
    <version>2.17.2</version>
  </dependency>

  <!-- Suport XML -->
  <dependency>
    <groupId>com.fasterxml.jackson.dataformat</groupId>
    <artifactId>jackson-dataformat-xml</artifactId>
    <version>2.17.2</version>
  </dependency>
</dependencies>
```

---

## Configuració bàsica dels mappers (comú)

Esta configuració funciona per a la majoria de projectes:

```java
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

ObjectMapper json = new ObjectMapper()
    .registerModule(new JavaTimeModule())
    .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
    .enable(SerializationFeature.INDENT_OUTPUT);

XmlMapper xml = new XmlMapper()
    .registerModule(new JavaTimeModule())
    .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
    .enable(SerializationFeature.INDENT_OUTPUT);
```

---

## Anotacions comunes (valen per a JSON i, en part, per a XML)

* `@JsonProperty("...")`: canviar el nom d’un camp en el document.
* `@JsonInclude(Include.NON_NULL | NON_EMPTY | NON_DEFAULT)`: controlar inclusions.
* `@JsonIgnore`, `@JsonIgnoreProperties({...})`: excloure camps o conjunts de camps.
* `@JsonFormat(pattern="yyyy-MM-dd")`: formatar dates/nombres.
* `@JsonAlias({"nom","name"})`: admetre claus d’entrada alternatives.
* `@JsonCreator` (amb `@JsonProperty` en el constructor): construcció controlada.

> Idea clau: amb estes anotacions podem **governar JSON** i gran part de l’XML. Només afegirem 2–3 anotacions **específiques d’XML** quan calga.

---

## Anotacions específiques d’XML (bloc curt)

* `@JacksonXmlRootElement(localName="...")`: nom de l’arrel XML.
* `@JacksonXmlProperty(localName="...", isAttribute=true|false)`: mapar camps a **atributs** o **elements**.
* `@JacksonXmlElementWrapper(useWrapping=false|true, localName="...")`: controlar si una llista va **envoltada** per un contenidor (p. ex. `<Monuments>`) o **no**.
* `@JacksonXmlCData`: escriure el contingut com a **CDATA**.

> Nota important sobre `@JacksonXmlText`: només s’usa quan l’element **només conté text** (sense subelements). En els teus exemples, `persona` té subelements (`<nom>`, `<edat>`, `<adreces>`); per tant **no** és aplicable ací. Per a `<notes><![CDATA[...]]></notes>` emprar **`@JacksonXmlCData`** en un camp mapejat com a element normal.

---

## Exemple dual A — Mateixa classe per a JSON i XML (llista sense wrapper)

**JSON desitjat**

```json
{
  "ciutat": "València",
  "monuments": [
    {"nom": "La Llotja"},
    {"nom": "El Micalet"}
  ]
}
```

**XML desitjat (sense contenidor per a la llista)**

```xml
<Ciutat>
  <Nom>València</Nom>
  <Monument><Nom>La Llotja</Nom></Monument>
  <Monument><Nom>El Micalet</Nom></Monument>
</Ciutat>
```

**Classes**

```java
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import java.util.List;

@JacksonXmlRootElement(localName = "Ciutat")
public class Ciutat {
    @JsonProperty("ciutat")                 // JSON: "ciutat"
    @JacksonXmlProperty(localName = "Nom")  // XML: <Nom>
    private String nom;

    @JsonProperty("monuments")              // JSON: array
    @JacksonXmlElementWrapper(useWrapping = false) // XML: sense <Monuments>
    @JacksonXmlProperty(localName = "Monument")
    private List<Monument> monuments;

    public Ciutat() {}
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    public List<Monument> getMonuments() { return monuments; }
    public void setMonuments(List<Monument> monuments) { this.monuments = monuments; }
}

public class Monument {
    @JsonProperty("nom")
    @JacksonXmlProperty(localName = "Nom")
    private String nom;

    public Monument() {}
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
}
```

---

## Exemple dual B — Atributs en XML, elements en JSON

**JSON**

```json
{
  "marca": "Toyota",
  "model": "Corolla",
  "any": 2020,
  "motor": { "cilindrada": 1800, "combustible": "gasolina" }
}
```

**XML**

```xml
<Cotxe marca="Toyota" model="Corolla" any="2020">
  <Motor cilindrada="1800" combustible="gasolina"/>
</Cotxe>
```

**Classes**

```java
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

@JacksonXmlRootElement(localName = "Cotxe")
public class Cotxe {
    @JsonProperty("marca")
    @JacksonXmlProperty(isAttribute = true)
    private String marca;

    @JsonProperty("model")
    @JacksonXmlProperty(isAttribute = true)
    private String model;

    @JsonProperty("any")
    @JacksonXmlProperty(isAttribute = true)
    private int any;

    private Motor motor;

    public Cotxe() {}
    public String getMarca() { return marca; }
    public void setMarca(String marca) { this.marca = marca; }
    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }
    public int getAny() { return any; }
    public void setAny(int any) { this.any = any; }
    public Motor getMotor() { return motor; }
    public void setMotor(Motor motor) { this.motor = motor; }
}

public class Motor {
    @JsonProperty("cilindrada")
    @JacksonXmlProperty(isAttribute = true)
    private int cilindrada;

    @JsonProperty("combustible")
    @JacksonXmlProperty(isAttribute = true)
    private String combustible;

    public Motor() {}
    public int getCilindrada() { return cilindrada; }
    public void setCilindrada(int cilindrada) { this.cilindrada = cilindrada; }
    public String getCombustible() { return combustible; }
    public void setCombustible(String combustible) { this.combustible = combustible; }
}
```

---

## Revisió i correcció del teu exemple “Persona” (XML)

**XML de partida**

```xml
<persona>
    <nom>Jaume Aragó</nom>
    <edat>68</edat>
    <adreces>
        <adreca tipus="casa">
            <carrer>Avinguda Datileres</carrer>
            <ciutat>Benizahat</ciutat>
        </adreca>
        <adreca tipus="oficina">
            <carrer>Canto del Bobet</carrer>
            <ciutat>Benigaslo</ciutat>
        </adreca>
    </adreces>
    <notes><![CDATA[Esta és una persona molt important.]]></notes>
</persona>
```

**POJOs corregits (substituïm `@JacksonXmlText` per `@JacksonXmlCData` en `notes`)**

```java
import com.fasterxml.jackson.dataformat.xml.annotation.*;
import java.util.List;

@JacksonXmlRootElement(localName = "persona")
public class Persona {
    @JacksonXmlProperty(localName = "nom")
    private String nom;

    @JacksonXmlProperty(localName = "edat")
    private int edat;

    @JacksonXmlElementWrapper(localName = "adreces")
    @JacksonXmlProperty(localName = "adreca")
    private List<Adreca> adreces;

    @JacksonXmlProperty(localName = "notes")
    @JacksonXmlCData
    private String notes;

    public Persona() {}
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    public int getEdat() { return edat; }
    public void setEdat(int edat) { this.edat = edat; }
    public List<Adreca> getAdreces() { return adreces; }
    public void setAdreces(List<Adreca> adreces) { this.adreces = adreces; }
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
}

public class Adreca {
    @JacksonXmlProperty(isAttribute = true, localName = "tipus")
    private String tipus;

    @JacksonXmlProperty(localName = "carrer")
    private String carrer;

    @JacksonXmlProperty(localName = "ciutat")
    private String ciutat;

    public Adreca() {}
    public String getTipus() { return tipus; }
    public void setTipus(String tipus) { this.tipus = tipus; }
    public String getCarrer() { return carrer; }
    public void setCarrer(String carrer) { this.carrer = carrer; }
    public String getCiutat() { return ciutat; }
    public void setCiutat(String ciutat) { this.ciutat = ciutat; }
}
```

**Serialitzar amb capçalera i indentació (opcional):**

```java
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.ser.ToXmlGenerator;
import com.fasterxml.jackson.databind.SerializationFeature;

XmlMapper xml = new XmlMapper();
xml.configure(ToXmlGenerator.Feature.WRITE_XML_DECLARATION, true);
xml.enable(SerializationFeature.INDENT_OUTPUT);

String xmlOut = xml.writeValueAsString(persona);
```

---

## ObjectMapper vs XmlMapper: operacions habituals (recordatori)

* `readValue(String/File/InputStream, Class<T>)` → deserialitzar.
* `writeValueAsString(obj)` / `writeValue(File, obj)` → serialitzar.
* `readTree(...)` / `writeTree(...)` → model d’arbre (`JsonNode`) per a inspecció i transformacions.

---

## Checklist de comprovació (per a l’alumnat)

* Les **classes** i **camps** reflecteixen fidelment el document?
* Les **llistes** són `List<T>` i, en XML, has decidit si vols **wrapper**?
* Les **dates** tenen `@JsonFormat` si el format ha de ser específic?
* Has registrat `JavaTimeModule` i desactivat `WRITE_DATES_AS_TIMESTAMPS`?
* En XML, el que va com **atribut** té `isAttribute=true`?
* Has comprovat **JSON i XML** amb les **mateixes classes**?

---


---
title: 2.4.-
parent: 2.- Gestió del Contingut
has_children: true
nav_order: 40
layout: default
---

### Introducció a la Serialització i Deserialització d'XML amb Jackson

En el desenvolupament d'aplicacions Java, és comú treballar amb dades en format **XML**. A través de la llibreria **Jackson**, que és àmpliament coneguda pel seu suport per a **JSON**, també es poden gestionar documents XML de manera senzilla utilitzant la classe **`XmlMapper`**. Aquesta classe permet convertir objectes Java a XML (serialització) i convertir XML a objectes Java (deserialització).

### Context i Importància de `XmlMapper`

**`XmlMapper`** és una classe proporcionada pel mòdul **`jackson-dataformat-xml`** que permet treballar amb XML de manera similar a com es fa amb JSON a través de `ObjectMapper`. Aquesta classe és útil per gestionar dades que es reben o s'envien en format XML, un format de dades estructurat que és utilitzat sovint en aplicacions empresarials, configuracions i serveis web.

### Principals Mètodes de `XmlMapper`

La classe **`XmlMapper`** ofereix diversos mètodes per treballar amb XML i mapejar-lo a objectes Java i viceversa. A continuació es descriuen els mètodes més importants:

| Mètode                      | Descripció                                                                                      |
|-----------------------------|-------------------------------------------------------------------------------------------------|
| **`readValue()`**           | Deserialitza un contingut XML en un objecte Java. Pot llegir des d'una cadena (String), un fitxer, o un `InputStream`. |
| **`writeValue()`**          | Serialitza un objecte Java en un contingut XML. Pot escriure a una cadena (String), un fitxer o un `OutputStream`. |
| **`readTree()`**            | Llegeix un contingut XML com a `JsonNode`, una representació abstracta de les dades, útil per explorar dades XML sense mapar-les a classes concretes. |
| **`writeTree()`**           | Escriu un `JsonNode` com a contingut XML.                                                        |
| **`readValues()`**          | Llegeix una seqüència de documents XML separats.                                                  |
| **`readerForUpdating()`**   | Permet l'actualització incremental d'un objecte Java existent amb dades XML.                      |
| **`getFactory()`**          | Obté l'objecte `JsonFactory` associat amb l'`XmlMapper`. Es pot configurar per gestionar aspectes específics com l'ús de DTDs, espais de noms, etc. |
| **`getSerializationConfig()`** | Obté la configuració de serialització per personalitzar aspectes com la indentació o la inclusió d'espais de noms XML. |
| **`getDeserializationConfig()`** | Obté la configuració de deserialització per gestionar opcions específiques de l'anàlisi XML, com reconèixer espais de noms o CDATA. |
| **`setConfig()`**           | Configura l'objecte `XmlMapper` amb una nova configuració personalitzada per a gestionar opcions específiques de l'anàlisi o la generació d'XML. |

### Exemples d'Ús de `XmlMapper`

1. **Deserialització d'XML en un objecte Java**:
   ```java
   XmlMapper xmlMapper = new XmlMapper();
   String xmlString = "<persona><nom>John</nom><edat>30</edat></persona>";
   Persona persona = xmlMapper.readValue(xmlString, Persona.class);
   ```
   Utilitzem `readValue()` per convertir un fragment d'XML en un objecte Java de la classe `Persona`.

2. **Serialització d'un objecte Java a XML**:
   ```java
   XmlMapper xmlMapper = new XmlMapper();
   Persona persona = new Persona("John", 30);
   String xmlString = xmlMapper.writeValueAsString(persona);
   ```
   `writeValueAsString()` converteix un objecte `Persona` a una cadena XML.

3. **Lectura d'arxius XML**:
   ```java
   XmlMapper xmlMapper = new XmlMapper();
   File xmlFile = new File("persona.xml");
   Persona persona = xmlMapper.readValue(xmlFile, Persona.class);
   ```
   `readValue()` llegeix les dades d'un fitxer XML i les deserialitza en un objecte `Persona`.

4. **Serialització d'objectes Java a un fitxer XML**:
   ```java
   XmlMapper xmlMapper = new XmlMapper();
   Persona persona = new Persona("John", 30);
   xmlMapper.writeValue(new File("persona.xml"), persona);
   ```
   `writeValue()` serialitza un objecte `Persona` i l'emmagatzema com un fitxer XML.

### Anotacions XML amb Jackson

Jackson ofereix diverses anotacions per personalitzar el mapeig entre objectes Java i XML, similar a les anotacions que es fan servir amb JSON. A continuació es detallen les anotacions més importants per treballar amb XML:

- **`@JacksonXmlProperty`**: Mapeja un camp de la classe amb un element XML. Es pot especificar el nom de l'element XML amb el paràmetre `localName`.
  ```java
  @JacksonXmlProperty(localName = "Nom")
  private String nom;
  ```

- **`@JacksonXmlElementWrapper`**: Envolta una llista d'objectes amb un element XML específic. És útil per gestionar col·leccions com `List`, `Set` o `Map`.
  ```java
  @JacksonXmlElementWrapper(localName = "alumnes")
  @JacksonXmlProperty(localName = "alumne")
  private List<Alumne> alumnes;
  ```

- **`@JacksonXmlText`**: Mapeja el contingut d'un element XML directament a un camp de la classe. Útil quan l'element XML no té atributs o subelements i tota la informació es troba en el seu contingut.
  ```java
  @JacksonXmlText
  private String text;
  ```

- **`@JacksonXmlRootElement`**: Defineix l'element XML arrel que representarà la classe Java.
  ```java
  @JacksonXmlRootElement(localName = "llibre")
  public class Llibre {
      // Camps i mètodes de la classe
  }
  ```

- **`@JacksonXmlProperty(isAttribute = true)`**: Mapeja un camp com un atribut d'un element XML, representant-lo com un atribut en lloc d'un element.
  ```java
  @JacksonXmlProperty(isAttribute = true)
  private String id;
  ```

- **`@JacksonXmlCData`**: Indica que el contingut del camp s'ha d'emmagatzemar com a **CDATA** en el document XML. Això és útil quan el camp pot contenir caràcters especials.
  ```java
  @JacksonXmlCData
  private String contingut;
  ```

- **`@JacksonXmlProperty(namespace = "http://example.com/namespace")`**: Mapeja un camp a un element XML d'un espai de noms específic, útil quan es treballa amb documents XML que utilitzen espais de noms.
  ```java
  @JacksonXmlProperty(localName = "Nom", namespace = "http://example.com/persona")
  private String nom;
  ```

### Conclusió

Jackson facilita el treball amb XML a través de la classe **`XmlMapper`**, permetent la conversió fàcil entre objectes Java i representacions XML. Amb les anotacions adequades, és possible ajustar com es mapegen les propietats de les classes Java als elements i atributs XML, proporcionant un alt grau de personalització. Això és especialment útil en entorns empresarials on XML és un format de dades comú per a la configuració, integració i intercanvi de dades entre sistemes.

Amb aquests coneixements, es pot gestionar la serialització i deserialització d'XML de manera més eficient, aprofitant les potents funcionalitats de Jackson per treballar amb dades estructurades.
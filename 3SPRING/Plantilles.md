---
title:  Plantilles Thymeleaf
parent: SPRING
has_children: true
layout: default  
nav_order: 110  
---

# Vista “Llistat de Ciutats” amb Spring MVC + Thymeleaf (Gradle)

Anem a crear muntar **una vista Thymeleaf** que mostra **un llistat de ciutats** (taula HTML) a partir d’un controlador MVC com el que hem de crear, amb els **estils CSS** carregats des de `/css/llistatGeneral.css`.


---

### Requisits i idea general

Hem de tindre estes peces:

* **Entitat JPA** `Ciutat` (i relació amb `Provincia`).
* **Repositori** `CiutatRepository` per a fer `findAll()`.
* **Controlador MVC** que posa `ciutats` dins del `Model`.
* **Plantilla Thymeleaf** `LlistatCiutats.html` dins de `src/main/resources/templates`.
* **CSS** dins de `src/main/resources/static/css/llistatGeneral.css`.

Quan entrem a:

* `GET /llistats/ciutats`

Spring executa el mètode del controlador, carrega la llista, la posa al model, i Thymeleaf renderitza la plantilla.

---

### Dependències Gradle (build.gradle)

Guarda un `build.gradle` amb dependències típiques per a MVC + Thymeleaf + JPA. (Si ja tens projecte creat, revisa que estiguen.)

```gradle

dependencies {
    implementation 'org.springframework.boot:spring-boot-starter-web'
    implementation 'org.springframework.boot:spring-boot-starter-thymeleaf'
    implementation 'org.springframework.boot:spring-boot-starter-data-jpa'


}


```


---

### Estructura de carpetes recomanada

Comprova que el projecte tinga esta estructura (com a mínim):

```
src/main/java/...           (controladors, entitats, repositoris)
src/main/resources/
  templates/                (HTML Thymeleaf)
  static/
    css/                    (CSS)
```

Accions:

* Crea `src/main/resources/templates` si no existeix.
* Crea `src/main/resources/static/css` si no existeix.

---

### Controlador MVC, a partir del repositori

La part important per a la vista de ciutats és:

* Fer `ciutatRepository.findAll()`
* Afegir-ho al `Model` amb una clau **exacta**: `"ciutats"`
* Retornar el nom de la plantilla: `"LlistatCiutats"`

```java
@Controller
@RequestMapping("/llistats")
public class PlantillesMVCController {

    @Autowired
    private CiutatRepository ciutatRepository;

    @GetMapping("/ciutats")
    public String llistatCiutats(Model model) {

        // Recuperem totes les ciutats de la base de dades
        List<Ciutat> ciutatList = ciutatRepository.findAll();

        // Afegim la llista al model amb el nom "ciutats"
        model.addAttribute("ciutats", ciutatList);

        // Retornem el nom de la plantilla Thymeleaf (LlistatCiutats.html)
        return "LlistatCiutats";
    }
}
```

Hem de tindre en compte:

* El fitxer ha de dir-se **LlistatCiutats.html**.
* Ha d’estar dins de **templates**.
* La ruta completa queda: `/llistats/ciutats`.


Este controlador s’encarrega de:

* Atendre una petició HTTP `GET`
* Recuperar totes les ciutats de la base de dades
* Passar-les a la vista mitjançant el `Model`
* Retornar la plantilla Thymeleaf corresponent

---

### Plantilla Thymeleaf: LlistatCiutats.html

Guarda este fitxer en:

`src/main/resources/templates/LlistatCiutats.html`

```html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8"/>
    <title>Llistat de Ciutats</title>
    <link rel="stylesheet" type="text/css" href="/css/llistatGeneral.css">
</head>
<body>

<header>
    <h1>Llistat de Ciutats</h1>
</header>

<table>
    <thead>
    <tr>
        <th>ID</th>
        <th>Nom</th>
        <th>Població</th>
        <th>Descripció</th>
        <th>Imatge</th>
        <th>Província</th>
    </tr>
    </thead>
    <tbody>
    <tr th:each="ciutat : ${ciutats}">
        <td th:text="${ciutat.id}"></td>
        <td th:text="${ciutat.nom}"></td>
        <td th:text="${ciutat.poblacio}"></td>
        <td th:text="${ciutat.descripcio}"></td>
        <td>
            <img th:if="${ciutat.imatge}" th:src="${ciutat.imatge}" alt="Imatge de la ciutat"/>
            <span th:if="${ciutat.imatge == null}">Sense imatge</span>
        </td>
        <td th:text="${ciutat.provincia.nom}"></td>
    </tr>
    </tbody>
</table>

</body>
</html>
```

Hem de tindre en compte:

* `xmlns:th="http://www.thymeleaf.org"` activa els atributs `th:*`.
* `th:each` recorre la llista que el controlador ha posat al `Model`.
* `th:text` escriu text dins de la cel·la.
* `th:if` mostra o amaga elements segons condició.
* `th:src` assigna el `src` de la imatge amb una expressió.

---

### CSS: llistatGeneral.css

Guarda este fitxer en:

`src/main/resources/static/css/llistatGeneral.css`

```css
body {
    font-family: Arial, sans-serif;
    margin: 0;
    padding: 0;
    background: #f6f6f6;
}

header {
    background: #222;
    color: white;
    padding: 16px;
}

header h1 {
    margin: 0;
    font-size: 20px;
}

table {
    width: 95%;
    margin: 16px auto;
    border-collapse: collapse;
    background: white;
}

thead th {
    background: #e9e9e9;
    text-align: left;
    padding: 10px;
    border-bottom: 2px solid #ccc;
}

tbody td {
    padding: 10px;
    border-bottom: 1px solid #ddd;
    vertical-align: top;
}

tbody tr:hover {
    background: #f2f2f2;
}

img {
    max-width: 120px;
    height: auto;
    display: block;
}
```

Hem de tindre en compte:

* `href="/css/llistatGeneral.css"` funciona perquè Spring servix `static/` com a arrel de recursos.
* El navegador demana `/css/llistatGeneral.css` i Spring el troba en `static/css/llistatGeneral.css`.

---

## Com funciona Thymeleaf 

### Namespace Thymeleaf

Hem de declarar:

```html
<html xmlns:th="http://www.thymeleaf.org">
```

Açò permet usar atributs com `th:text`, `th:each`, `th:if`, etc.

### Expressions de variable

Quan escrivim:

```html
<td th:text="${ciutat.nom}"></td>
```

Thymeleaf:

* Agafa `ciutat.nom`
* Converteix el valor a text
* El posa dins del `<td>...</td>`

Important:

* `th:text` sempre escriu **text escapant HTML** (és el comportament segur per defecte).

### Iteració sobre llistes (th:each)

Esta línia:

```html
<tr th:each="ciutat : ${ciutats}">
```

vol dir:

* Per a cada element de la llista `ciutats`
* Crea una fila `<tr>`
* I en cada iteració tindrem una variable `ciutat`

Per això després podem fer:

* `${ciutat.id}`
* `${ciutat.nom}`
* `${ciutat.poblacio}`

### Estructures de control (th:if)

En la columna “Imatge” estem fent:

```html
<img th:if="${ciutat.imatge}" th:src="${ciutat.imatge}" .../>
<span th:if="${ciutat.imatge == null}">Sense imatge</span>
```

Açò vol dir:

* Si `ciutat.imatge` té valor, mostra `<img ...>`
* Si és `null`, mostra el text “Sense imatge”

Així evitem:

* Imatges trencades
* Files buides sense explicació

### Recursos estàtics

Als apunts apareix:

* `th:href="@{...}"`
* `th:src="@{...}"`

En el teu exemple, el CSS està en un `href` normal:

```html
<link rel="stylesheet" href="/css/llistatGeneral.css">
```

També ho podríem fer amb Thymeleaf (quan volem que Thymeleaf construïsca la ruta):

```html
<link rel="stylesheet" th:href="@{/css/llistatGeneral.css}">
```

I per a imatges també és molt habitual:

```html
<img th:src="@{${ciutat.imatge}}">
```

En el teu cas `ciutat.imatge` pareix una URL o ruta guardada en BD, i per això `th:src="${ciutat.imatge}"` ja és coherent.

---

### Detall important: camps i relacions (provincia.nom)

En la plantilla tenim:

```html
<td th:text="${ciutat.provincia.nom}"></td>
```

Açò implica que:

* `Ciutat` té una propietat `provincia`
* `provincia` no és `null`
* `Provincia` té una propietat `nom`

Si alguna ciutat poguera tindre `provincia == null`, caldria protegir-ho amb un `th:if`, com hem fet amb la imatge.

---


### Resum del flux complet

Hem de tindre clar este recorregut:

* El navegador demana `/llistats/ciutats`
* El controlador:

  * consulta `ciutatRepository.findAll()`
  * posa la llista en `model.addAttribute("ciutats", ...)`
  * retorna `"LlistatCiutats"`
* Thymeleaf:

  * carrega `templates/LlistatCiutats.html`
  * executa `th:each` i `th:text`
  * genera HTML final
* El navegador carrega també `/css/llistatGeneral.css` des de `static/`

---

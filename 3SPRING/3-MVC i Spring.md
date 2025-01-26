---
title: 3. Arquitectura MVC. 
parent: SPRING

has_children: true
layout: default
nav_order: 30

---


# **Arquitectura MVC i  Spring Boot**

El patró **Model-View-Controller (MVC)** és una arquitectura que separa les responsabilitats d’una aplicació en tres components principals:


---
![alt text](imatges/mvc.png)

---

1. **Model**:
  - Gestiona les dades, la lògica de negoci i les regles d'aplicació.
   - Conté les classes que representen les dades i interactua amb la base de dades.
    
2. **Vista**:
   - És la interfície d'usuari.
   - Mostra les dades al client i permet la interacció.
    
3. **Controlador**:
    - Gestiona les peticions de l’usuari.
    - Actua com a intermediari entre el **Model** i la Vista (**View**).

## **1.- Patró Model-View-Controller (MVC)**


MVC és un patró de disseny que separa les responsabilitats d'una aplicació en tres components principals:
- **Model**: Gestiona les dades, la lògica de negoci i les regles d'aplicació.
- **View**: És la interfície d'usuari. Mostra les dades al client i permet la interacció.
- **Controller**: Gestiona les peticions de l'usuari, interacciona amb el Model i actualitza la View.

**Objectius del patró MVC**
  - **Separació de responsabilitats**:
      - Cada component té un paper clar i independent, cosa que facilita la comprensió i el manteniment del codi.
  - **Reutilització de codi**:
        - És possible reutilitzar el Model i la lògica de negoci en altres aplicacions o components.
  - **Escalabilitat**:
        - És fàcil afegir noves funcionalitats sense afectar altres components.
  - **Facilitat de manteniment**:
        - Com que el codi està ben separat, és més senzill localitzar i corregir errors.
  
**Implementació de MVC amb Spring Boot**
    
     Spring Boot ofereix una implementació eficient del patró MVC, ja que proporciona:    

     - Gestió automàtica de components:
    
       - Amb anotacions com `@Controller`, `@Service` i `@Repository`, podem estructurar el codi fàcilment.
    
     - Integració amb bases de dades
    
       - Utilitza Spring Data JPA per interactuar amb bases de dades de manera senzilla.
    
     - Motor de plantilles:
    
       - Thymeleaf permet generar vistes dinàmiques basades en HTML.
    
     - API REST integrada:
     -     
       - Amb `@RestController`, podem crear APIs que retornen JSON o XML.
    
     ------
    
**Exemple: Funcionament de MVC en Spring Boot**
    
  Petició simple en un projecte MVC:
    
  1. **Usuari fa una petició HTTP (GET)** a `http://localhost:8080/ciutats`.
  2. El **Controlador** (anotat amb `@Controller`) gestiona la petició.
  3. El **Servei** interactua amb el **Model** per obtenir dades de la base de dades.
  4. El Controlador envia les dades a la **Vista** (Thymeleaf) per ser renderitzades.
    


---


## **2. Components principals del patró MVC en Spring Boot**

En l’arquitectura MVC de Spring Boot, cada component té un paper clar. Això ajuda a organitzar l’aplicació i facilita el desenvolupament i manteniment. Els components principals són:

1. **Model**: Gestiona les dades i la lògica de negoci.
2. **Vista**: Mostra les dades a l’usuari.
3. **Controlador**: Gestiona les peticions HTTP i connecta el Model amb la Vista.

---

## **1. Model: Entitats, Repositoris i Serveis**

El **Model** representa la informació de l’aplicació i com es gestiona. A Spring Boot, el Model es compon de:
- **Entitats**: Representen taules en la base de dades.
- **Repositoris**: Gestionen les operacions amb la base de dades.
- **Serveis**: Encapsulen la lògica de negoci i interactuen amb els repositoris.

###  Entitats

Una **entitat** és una classe que representa una taula en la base de dades. Utilitza anotacions de **JPA (Java Persistence API)** per definir les columnes, claus primàries i relacions.

**Exemple d’una entitat `Ciutat`:**

```java
package com.example.model;

import jakarta.persistence.*;

@Entity // Marca aquesta classe com una entitat JPA
@Table(name = "ciutats") // Opcional: especifica el nom de la taula
public class Ciutat {

    @Id // Defineix la clau primària
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Genera automàticament l'ID
    private Long id;

    @Column(nullable = false) // Marca el camp "nom" com a obligatori
    private String nom;

    private int poblacio;

    // Constructor per defecte (requerit per JPA)
    public Ciutat() {}

    // Constructor personalitzat
    public Ciutat(String nom, int poblacio) {
        this.nom = nom;
        this.poblacio = poblacio;
    }

    // Getters i setters (necessaris per accedir als camps)
    
}
```

---

### Repositoris

Els **repositoris** són interfícies que gestionen les operacions CRUD (Create, Read, Update, Delete) i consultes personalitzades. A Spring Boot, s’utilitzen `JpaRepository` o `CrudRepository`.

**Exemple d’un repositori `CiutatRepository`:**

```java
package com.example.repository;

import com.example.model.Ciutat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository // Marca la interfície com un repositori gestionat per Spring
public interface CiutatRepository extends JpaRepository<Ciutat, Long> {

    // Consulta personalitzada: Cerca ciutats pel nom
    Iterable<Ciutat> findByNomContaining(String partNom);
}
```

- **Mètodes inclosos a `JpaRepository`**:
  - `findAll()`: Obté totes les entitats.
  - `save(entity)`: Desa o actualitza una entitat.
  - `deleteById(id)`: Elimina una entitat per ID.

---

###  Serveis

Els **serveis** encapsulen la lògica de negoci. Gestionen la comunicació entre el controlador i el repositori.

**Exemple d’un servei `CiutatService`:**

```java
package com.example.service;

import com.example.model.Ciutat;
import com.example.repository.CiutatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service // Marca la classe com un servei gestionat per Spring
public class CiutatService {

    @Autowired // Injecció de dependència del repositori
    private CiutatRepository ciutatRepository;

    // Retorna totes les ciutats de la base de dades
    public Iterable<Ciutat> getAllCiutats() {
        return ciutatRepository.findAll();
    }

    // Guarda una ciutat nova o actualitza una existent
    public Ciutat saveCiutat(Ciutat ciutat) {
        return ciutatRepository.save(ciutat);
    }
}
```

---

## **2 Vista: Thymeleaf**

La **Vista** és responsable de presentar les dades a l’usuari. En Spring Boot, es poden utilitzar motors de plantilles com **Thymeleaf**.

**Exemple de plantilla Thymeleaf (`ciutats.html`):**

```html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <title>Llista de Ciutats</title>
</head>
<body>
    <h1>Llista de Ciutats</h1>
    <table>
        <thead>
            <tr>
                <th>ID</th>
                <th>Nom</th>
                <th>Població</th>
            </tr>
        </thead>
        <tbody>
            <!-- Itera sobre la llista de ciutats passada pel controlador -->
            <tr th:each="ciutat : ${ciutats}">
                <td th:text="${ciutat.id}"></td>
                <td th:text="${ciutat.nom}"></td>
                <td th:text="${ciutat.poblacio}"></td>
            </tr>
        </tbody>
    </table>
</body>
</html>
```

---

## **3. Controladors: `@Controller` vs `@RestController`**

Els **controladors** gestionen les peticions HTTP. Spring Boot ofereix dos tipus principals:
1. **`@Controller`**: Gestiona vistes HTML.
2. **`@RestController`**: Retorna respostes en format JSON o XML (ideal per a APIs REST).

### **Controlador per a vistes (HTML)**

```java
package com.example.controller;

import com.example.service.CiutatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller // Gestiona vistes HTML
public class CiutatController {

    @Autowired
    private CiutatService ciutatService;

    @GetMapping("/ciutats") // Ruta per obtenir la llista de ciutats
    public String getCiutats(Model model) {
        model.addAttribute("ciutats", ciutatService.getAllCiutats());
        return "ciutats"; // Retorna la plantilla HTML "ciutats.html"
    }
}
```

### **Controlador REST (JSON)**

```java
package com.example.controller;

import com.example.model.Ciutat;
import com.example.service.CiutatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController // Gestiona respostes JSON
@RequestMapping("/api/ciutats") // Ruta base de l'API
public class CiutatRestController {

    @Autowired
    private CiutatService ciutatService;

    @GetMapping // Retorna totes les ciutats en format JSON
    public Iterable<Ciutat> getAllCiutats() {
        return ciutatService.getAllCiutats();
    }

    @PostMapping // Desa una ciutat enviada en format JSON
    public Ciutat saveCiutat(@RequestBody Ciutat ciutat) {
        return ciutatService.saveCiutat(ciutat);
    }
}
```

---

### **Diferències entre `@Controller` i `@RestController`**

| **`@Controller`**                | **`@RestController`**                |
|----------------------------------|--------------------------------------|
| Gestiona vistes HTML.            | Retorna dades en format JSON/XML.    |
| Necessita un motor de plantilles (Thymeleaf). | No necessita motor de plantilles.   |
| Retorna el nom de la vista (`String`). | Retorna dades (`Object` o col·leccions). |

---

## **3. Flux de treball dins de Spring MVC**

En Spring MVC, cada petició HTTP realitzada per un usuari segueix un flux de treball clar i estructurat que passa per diferents components: el **Controlador**, el **Servei**, el **Repositori** i, finalment, la **Vista** (o la resposta en format JSON en cas d'una API REST).

Aquest flux garanteix que cada capa del patró MVC compleix una responsabilitat específica i permet que l'aplicació siga escalable, mantenible i fàcil d'entendre.

---

- **1. Petició de l'usuari**
    - L'usuari accedeix a una URL, com ara:
      ```
      http://localhost:8080/ciutats
      ```
    - Aquesta petició HTTP (per exemple, GET, POST, PUT, DELETE) és rebuda pel servidor de Spring Boot.

---

- **2. El controlador processa la petició**
    - El controlador és el primer component que gestiona la petició.
    - En funció del tipus de controlador:
      - **`@Controller`**: Processa peticions que han de retornar vistes HTML.
      - **`@RestController`**: Retorna respostes en format JSON o XML.
    - El mètode anotat amb **`@GetMapping`** o **`@PostMapping`**  gestiona la petició.
    - Si cal, el controlador interactua amb el model (base de dades, serveis, etc.).

**Exemple de Controlador (HTML)**:
```java
@Controller
public class CiutatController {

    @Autowired
    private CiutatService ciutatService;

    @GetMapping("/ciutats") // Ruta que gestiona la petició GET
    public String getCiutats(Model model) {
        // Obtenim les dades del servei i les afegim al model
        model.addAttribute("ciutats", ciutatService.getAllCiutats());
        // Retornem la vista "ciutats.html"
        return "ciutats";
    }
}
```

**Exemple de Controlador REST (JSON)**:
```java
@RestController
@RequestMapping("/api/ciutats")
public class CiutatRestController {

    @Autowired
    private CiutatService ciutatService;

    @GetMapping // Gestiona peticions GET per obtenir totes les ciutats
    public Iterable<Ciutat> getAllCiutats() {
        // Retorna les dades en format JSON
        return ciutatService.getAllCiutats();
    }
}
```

---

- **3. El controlador interactua amb el servei**
    - Si el controlador necessita dades de la base de dades, delega aquesta tasca al **Servei**.
    - El servei encapsula la lògica de negoci i interactua amb els **Repositoris** per accedir a les dades.

**Exemple de Servei:**
```java
@Service
public class CiutatService {

    @Autowired
    private CiutatRepository ciutatRepository;

    public Iterable<Ciutat> getAllCiutats() {
        // Obtenim totes les ciutats de la base de dades
        return ciutatRepository.findAll();
    }

    public Ciutat saveCiutat(Ciutat ciutat) {
        // Guardem una nova ciutat o actualitzem una existent
        return ciutatRepository.save(ciutat);
    }
}
```

---

- **4. Obtenció de dades del model**
    - El servei crida al **Repositori** per interactuar amb la base de dades.
    - El repositori s'encarrega d'executar operacions** CRUD** (Create, Read, Update, Delete) utilitzant ** JPA**.

**Exemple de Repositori:**
```java
@Repository
public interface CiutatRepository extends JpaRepository<Ciutat, Long> {

    // Cerca ciutats pel nom que continga una paraula específica
    Iterable<Ciutat> findByNomContaining(String partNom);
}
```

---

- **5. Generació de la vista o resposta**
    - Una vegada el servei retorna les dades al controlador:
      - Si és un **`@Controller`**, el controlador passa les dades a la vista (Thymeleaf).
      - Si és un **`@RestController`**, el controlador retorna directament les dades en format JSON.

**Exemple de Vista Thymeleaf (`ciutats.html`):**
```html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <title>Llista de Ciutats</title>
</head>
<body>
    <h1>Llista de Ciutats</h1>
    <table>
        <thead>
            <tr>
                <th>ID</th>
                <th>Nom</th>
                <th>Població</th>
            </tr>
        </thead>
        <tbody>
            <tr th:each="ciutat : ${ciutats}">
                <td th:text="${ciutat.id}"></td>
                <td th:text="${ciutat.nom}"></td>
                <td th:text="${ciutat.poblacio}"></td>
            </tr>
        </tbody>
    </table>
</body>
</html>
```

---

- **6. Resposta al client**
    - Una vegada la vista està generada o les dades en JSON estan preparades:
      - Spring retorna una resposta HTTP al navegador o client que ha fet la petició.
      - Si és HTML, es renderitza la pàgina al navegador.
      - Si és JSON, es pot consumir des d’un frontend o una altra aplicació.

**Exemple de Resposta JSON:**
```json
[
    {
        "id": 1,
        "nom": "València",
        "poblacio": 800000
    },
    {
        "id": 2,
        "nom": "Barcelona",
        "poblacio": 1600000
    }
]
```

---

### **Diagrama del flux MVC**

Com es processa una petició en Spring MVC:

```plaintext
Usuari (client)
     |
     V
Petició HTTP (URL)
     |
     V
Controller (@Controller o @RestController)
     |
     V
Interacció amb Servei
     |
     V
Interacció amb Repositori (Base de dades)
     |
     V
Recuperació de dades
     |
     V
Vista HTML (Thymeleaf) o JSON/XML
     |
     V
Resposta HTTP al client
```

---

1. L'usuari fa una **petició HTTP**.
2. El **Controlador** processa la petició i, si cal, crida al **Servei**.
3. El **Servei** encapsula la lògica de negoci i interactua amb el **Repositori**.
4. El **Repositori** accedeix a la base de dades per obtenir o modificar dades.
5. El controlador retorna la resposta al client:
   - En forma de **Vista HTML** (amb Thymeleaf).
   - O com a **JSON/XML** en una API REST.


---

![alt text](imatges/mvc2.png)

---

### **Conclusió**

El patró MVC en Spring Boot és clar i estructurat:

- **Model**: Entitats i repositoris per gestionar les dades.
- **Vista**: HTML o JSON per mostrar les dades al client.
- **Controlador**: Gestiona peticions i crida als serveis.
- **Servei**: Conté la lògica de negoci i interactua amb els repositoris.

Aquesta arquitectura facilita el desenvolupament, la comprensió i el manteniment de l'aplicació.




---

## **Backend i Frontend amb Spring Boot**

Per integrar un backend desenvolupat amb Spring Boot amb diferents tipus d'aplicacions frontend, es fa utilitzant **APIs REST** o **WebSockets**. Aquestes eines permeten que el backend expose dades i funcionalitats que el frontend pot consumir de manera fàcil i escalable.

---

**Categories d'aplicacions frontend i eines més populars**

- **1. Aplicacions web (Frontend tradicional i SPAs)**
Aquestes aplicacions es renderitzen en el navegador i poden consumir dades del backend a través de peticions HTTP (APIs REST) o WebSockets.

- **Frameworks/Motors de plantilles (Frontend renderitzat al servidor)**:
  - **Thymeleaf** (integració nativa amb Spring Boot).
  - **JSP** (Java Server Pages).
  - **Freemarker**.

- **Single Page Applications (SPAs)**:
  - **React** (JavaScript/TypeScript).
  - **Vue.js** (JavaScript/TypeScript).
  - **Angular** (JavaScript/TypeScript).

- **Eina de comunicació amb el backend**:
  - **Axios** (biblioteca JavaScript per a peticions HTTP).
  - **fetch** (API nativa dels navegadors).
  - **SWR** (capa de gestió de dades per React).

---

- **2. Aplicacions mòbils (Android, iOS i multiplataforma)**

Aquestes aplicacions consumeixen dades del backend per proporcionar funcionalitats a dispositius mòbils. Normalment utilitzen **APIs REST** o, en casos d'actualitzacions en temps real, **WebSockets**.

- **Natiu**:
  - **Android (Java/Kotlin)**: Utilitza biblioteques com Retrofit per consumir APIs REST.
  - **iOS (Swift)**: Utilitza URLSession per consumir APIs REST.

- **Multiplataforma**:
  - **Flutter**: Basat en Dart, utilitza biblioteques com `http` per a peticions REST.
  - **React Native**: Comparteix moltes eines amb React, com Axios o fetch.

---

- **3. Aplicacions d'escriptori**
Aquestes aplicacions es desenvolupen per funcionar en sistemes operatius com Windows, macOS o Linux i consumeixen dades del backend.

- **Eines per al desenvolupament d'aplicacions d'escriptori**:
  - **JavaFX** (Java).
  - **Electron** (JavaScript/TypeScript, ideal per a aplicacions basades en web).
  - **Qt** (C++, amb bindings per a Python).
  - **Swing/AWT** (Java, menys modern però encara en ús).

---

- **4. Aplicacions per a IoT (Internet of Things)**
Aquestes aplicacions gestionen dispositius connectats i solen utilitzar el backend per recollir dades o enviar ordres.

- **Protocols comuns**:
  - **HTTP/REST**: Per interaccions bàsiques.
  - **MQTT**: Per comunicació lleugera i en temps real.
  - **CoAP**: Protocol optimitzat per IoT.

- **Eines populars**:
  - **Node.js**: Combinat amb frameworks com Express.js.
  - **Python**: Amb llibreries com Flask o FastAPI per gestionar el frontend.

---

- **5. Aplicacions híbrides (Web + Mòbil + Escriptori)**
Aquestes aplicacions utilitzen tecnologies compartides per funcionar en múltiples plataformes.

- **Eines per al desenvolupament híbrid**:
  - **Ionic** (HTML5/CSS/JavaScript, ideal per a web i mòbil).
  - **Flutter** (Dart, per a mòbil i escriptori).
  - **React Native + Expo** (per a mòbil amb possibilitat d’integració web).

---

**Taula Resum**

| **Categoria**               | **Eines populars**                                                                         |
|------------------------------|-------------------------------------------------------------------------------------------|
| Aplicacions web             | Thymeleaf, React, Vue.js, Angular                                                         |
| Aplicacions mòbils          | Android (Retrofit), iOS (URLSession), Flutter (http), React Native (Axios)                |
| Aplicacions d'escriptori    | JavaFX, Electron, Qt, Swing                                                               |
| Aplicacions IoT             | MQTT, HTTP/REST, CoAP, Node.js, Python                                                    |
| Aplicacions híbrides        | Ionic, Flutter, React Native + Expo                                                       |

---

## **Backend i Frontend a Spring Boot - Integració**

El backend desenvolupat amb Spring Boot s'integra fàcilment amb aplicacions frontend o altres sistemes utilitzant **APIs REST** o **WebSockets**.

---

### **Creació d'una API REST en Spring Boot**

Un **API REST** permet exposar dades i funcionalitats perquè altres aplicacions les consumisquen.

**Exemple de Controlador REST:**
```java
@RestController
@RequestMapping("/api/ciutats")
public class CiutatRestController {

    @Autowired
    private CiutatService ciutatService;

    // Retorna totes les ciutats en format JSON
    @GetMapping
    public Iterable<Ciutat> getAllCiutats() {
        return ciutatService.getAllCiutats();
    }

    // Desa una nova ciutat enviada en format JSON
    @PostMapping
    public Ciutat saveCiutat(@RequestBody Ciutat ciutat) {
        return ciutatService.saveCiutat(ciutat);
    }
}
```

---

### **Integració amb un Frontend SPA (React)**

**Exemple: Petició des de React utilitzant Axios**
```javascript
import axios from 'axios';

// Obtenir totes les ciutats des de l'API REST
axios.get('http://localhost:8080/api/ciutats')
    .then(response => {
        console.log(response.data); // Mostra les ciutats al navegador
    })
    .catch(error => {
        console.error("Error obtenint ciutats:", error);
    });

// Afegir una nova ciutat
axios.post('http://localhost:8080/api/ciutats', {
    nom: 'València',
    poblacio: 800000
})
    .then(response => {
        console.log("Ciutat afegida:", response.data);
    })
    .catch(error => {
        console.error("Error afegint ciutat:", error);
    });
```

---

### **CORS (Cross-Origin Resource Sharing)**

Quan el frontend i el backend estan en dominis diferents, cal configurar el **CORS** perquè el navegador permeta les peticions.

**Configuració de CORS a Spring Boot:**
```java
@RestController
@RequestMapping("/api/ciutats")
@CrossOrigin(origins = "http://localhost:3000") // Permet peticions des de React (port 3000)
public class CiutatRestController {
    // Controlador REST
}
```

---

### **Comunicació en temps real amb WebSockets**

Si es necessita una actualització en temps real entre el servidor i el client (per exemple, en aplicacions IoT), podem utilitzar **WebSockets**.

**Configuració bàsica de WebSocket a Spring Boot:**
```java
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/websocket").withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic");
        config.setApplicationDestinationPrefixes("/app");
    }
}
```

---
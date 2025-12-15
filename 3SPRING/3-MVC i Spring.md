---
title: 3. Arquitectura MVC. 
parent: SPRING

has_children: true
layout: default
nav_order: 30
---

# **MVC i Spring Boot**

---

## **1. Arquitectura Model–Vista–Controlador (MVC)**

El patró **Model-View-Controller (MVC)** és una arquitectura que separa les responsabilitats d’una aplicació en tres components principals:

---

![alt text](imatges/mvc.png)

---

### **Components del patró MVC**

1. **Model**
   - Gestiona les dades, la lògica de negoci i les regles d'aplicació.
   - Conté les classes que representen les dades i interactua amb la base de dades.

2. **Vista**
   - És la interfície d'usuari.
   - Mostra les dades al client i permet la interacció.

3. **Controlador**
   - Gestiona les peticions de l’usuari.
   - Actua com a intermediari entre el **Model** i la **Vista**.

---

### **Objectius del patró MVC**

- **Separació de responsabilitats**
  - Cada component té un paper clar i independent, cosa que facilita la comprensió i el manteniment del codi.
- **Reutilització de codi**
  - És possible reutilitzar el Model i la lògica de negoci en altres aplicacions o components.
- **Escalabilitat**
  - És fàcil afegir noves funcionalitats sense afectar altres components.
- **Facilitat de manteniment**
  - Com que el codi està ben separat, és més senzill localitzar i corregir errors.

---

## **2. Implementació del patró MVC amb Spring Boot**

Spring Boot ofereix una implementació eficient del patró MVC, ja que proporciona:

- Gestió automàtica de components:
  - Amb anotacions com `@Controller`, `@Service` i `@Repository`, podem estructurar el codi fàcilment.
- Integració amb bases de dades:
  - Utilitza Spring Data JPA per interactuar amb bases de dades de manera senzilla.
- Motor de plantilles:
  - Thymeleaf permet generar vistes dinàmiques basades en HTML.
- API REST integrada:
  - Amb `@RestController`, podem crear APIs que retornen JSON o XML.

---

### **Exemple: Funcionament de MVC en Spring Boot**

Petició simple en un projecte MVC:

1. L’usuari fa una petició HTTP (GET) a `http://localhost:8080/ciutats`.
2. El **Controlador** (anotat amb `@Controller`) gestiona la petició.
3. El **Servei** interactua amb el **Model** (entitats, serveis, etc.) per obtenir dades de la base de dades.
4. El Controlador envia les dades a la **Vista** (Thymeleaf) per ser renderitzades, o genera una resposta REST amb `@RestController`.

---

## **3. Arquitectura en capes en Spring Boot**

Spring Boot combina el patró MVC amb una **arquitectura en capes**, encara que no són exactament el mateix concepte.

Les capes més habituals són:

- **Controlador**
- **Servei**
- **Repositori**
- **Model (entitats)**

Aquesta arquitectura permet:
- organitzar millor el codi
- separar responsabilitats
- facilitar el manteniment i les proves

---

## **4. El Model en Spring Boot**

En Spring Boot, el **Model** està format principalment per:

- **Entitats**
- **Repositoris**
- **Serveis**

---

### **Entitats**

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

### **Repositoris**

Els **repositoris** gestionen les operacions CRUD **(Create, Read, Update, Delete)** i l’accés a la base de dades mitjançant Spring Data JPA.
Són **interficies** que hereten de `JpaRepository` o `CrudRepository`.


**Exemple de repositori `CiutatRepository`:**

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

* `JpaRepository` o `CrudRepository` contenen una serie de mètodes que faciliten l'accés a la base de dades (`findAll()`, `save(entity)`, `deleteById(id)`)

---

### **Serveis**

Els **serveis** encapsulen la lògica de negoci i actuen com a intermediari entre el controlador i el repositori.

**Exemple de servei `CiutatService`:**

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

>Nota: El **servei** retorna dades del model (entitats o col·leccions), i el **controlador** decideix com enviar-les al client.

---

## **5. La Vista en Spring Boot (Thymeleaf)**

La **Vista** és responsable de mostrar les dades a l’usuari. En Spring Boot, Thymeleaf és un motor de plantilles molt utilitzat.

La vista **no obté dades directament de la base de dades** ni conté lògica de negoci; **rep les dades del controlador**, que les posa en el **Model**, i només s’encarrega de representar-les en HTML.

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

## **6. Controladors en Spring Boot**

Els **controladors** són els encarregats de **rebre les peticions HTTP** que fa l’usuari o un client (navegador, aplicació frontend, etc.) i de **coordinar la comunicació entre el Model i la Vista** o, en el cas d’una API REST, entre el Model i la resposta JSON.
El controlador **no conté lògica de negoci** ni accedeix directament a la base de dades; **delegua aquestes tasques als serveis** i decideix **com es retornarà la informació al client**.

---

### **Controlador per a vistes HTML (`@Controller`)**


Un controlador anotat amb **`@Controller`** s’utilitza quan l’aplicació ha de **retornar vistes HTML**.

Aquest tipus de controlador:

* rep una petició HTTP
* crida al servei per obtenir les dades
* afegeix les dades al `Model`
* retorna el **nom de la vista** que s’ha de renderitzar amb Thymeleaf


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

En aquest cas, el controlador:

* rep la petició `GET /ciutats`
* obté les dades mitjançant el servei
* passa la llista de ciutats a la vista
* retorna el nom de la plantilla HTML que es mostrarà a l’usuari



---

### **Controlador REST (`@RestController`)**



Un controlador anotat amb **`@RestController`** s’utilitza quan l’aplicació ha de **retornar dades**, normalment en format **JSON**, en lloc de vistes HTML.

Aquest tipus de controlador:

* rep peticions HTTP
* crida als serveis per obtenir o guardar dades
* retorna directament objectes Java
* Spring s’encarrega de convertir-los automàticament a JSON




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
```



En aquest cas, el controlador:

* rep peticions a `/api/ciutats`
* retorna dades en format JSON
* no utilitza cap motor de plantilles
* sol ser consumit per aplicacions frontend o altres serveis

---

> **El controlador decideix com es retorna la informació, però no com s’obté ni com es guarda.*



---

### **Diferències entre `@Controller` i `@RestController`**

| `@Controller`                 | `@RestController`        |
| ----------------------------- | ------------------------ |
| Retorna vistes HTML           | Retorna dades (JSON/XML) |
| Usa Thymeleaf o altres motors | No necessita vista       |
| Retorna el nom de la vista    | Retorna objectes         |

---

## **7. Flux de treball dins de Spring MVC**

El flux d’una petició en Spring MVC és el següent:

1. L’usuari fa una petició HTTP.
2. El controlador rep la petició.
3. El controlador delega la lògica en el servei.
4. El servei interactua amb el repositori.
5. El controlador retorna una vista HTML o una resposta JSON.

---

### **Diagrama del flux MVC**

```plaintext
Usuari (client)
     |
     V
Petició HTTP
     |
     V
Controller
     |
     V
Service
     |
     V
Repository
     |
     V
Vista HTML o JSON
```

---

![alt text](imatges/mvc2.png)

---

## **8. MVC en APIs REST (sense vista)**

En una API REST:

* **Model**: entitats i DTOs.
* **Vista**: no existeix dins del backend.
* **Controlador**: retorna dades en format JSON o XML.

La vista sol estar en aplicacions frontend com React, Vue o aplicacions mòbils.

---

## **9. Backend i Frontend amb Spring Boot**

El backend desenvolupat amb Spring Boot pot integrar-se amb diferents tipus d’aplicacions frontend mitjançant **APIs REST** o **WebSockets**.

---

### **Tipus d’aplicacions frontend**

* Aplicacions web: Thymeleaf, React, Vue, Angular
* Aplicacions mòbils: Android, iOS, Flutter, React Native
* Aplicacions d’escriptori: JavaFX, Electron
* Aplicacions IoT: HTTP/REST, MQTT
* Aplicacions híbrides: Ionic, Flutter, React Native + Expo

---

## **10. Integració amb APIs REST**

**Exemple de controlador REST:**

```java
@RestController
@RequestMapping("/api/ciutats")
public class CiutatRestController {

    @Autowired
    private CiutatService ciutatService;

    @GetMapping
    public Iterable<Ciutat> getAllCiutats() {
        return ciutatService.getAllCiutats();
    }

    @PostMapping
    public Ciutat saveCiutat(@RequestBody Ciutat ciutat) {
        return ciutatService.saveCiutat(ciutat);
    }
}
```

---

## **11. Integració amb un frontend SPA (React)**

```javascript
import axios from 'axios';

axios.get('http://localhost:8080/api/ciutats')
    .then(response => {
        console.log(response.data);
    });

axios.post('http://localhost:8080/api/ciutats', {
    nom: 'València',
    poblacio: 800000
});
```

---

## **12. CORS (Cross-Origin Resource Sharing)**

```java
@CrossOrigin(origins = "http://localhost:3000")
```

Permet que un frontend en un domini diferent puga consumir l’API.

---

## **13. Comunicació en temps real amb WebSockets**

Els **WebSockets** permeten comunicació bidireccional en temps real. No formen part del patró MVC, però poden conviure amb Spring Boot.

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

## **Conclusió**

* El patró MVC separa Model, Vista i Controlador.
* Spring Boot combina MVC amb una arquitectura en capes.
* Les APIs REST permeten integrar qualsevol tipus de frontend.
* Aquesta arquitectura facilita el desenvolupament, el manteniment i l’escalabilitat de l’aplicació.



---

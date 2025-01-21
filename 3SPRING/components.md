### **Tutorial sobre components en Spring: MVC, entitats, repositoris, serveis i controladors**

---

## **Introducció**

Spring Boot facilita el desenvolupament d'aplicacions web utilitzant el patró **Model-Vista-Controlador (MVC)**, que organitza el codi en capes per millorar la separació de responsabilitats.

### **Què són els components en Spring?**
Un **component en Spring** és una classe gestionada pel contenedor de Spring. Això significa que Spring s'encarrega de:
- Crear instàncies de la classe (bean).
- Gestionar el seu cicle de vida.
- Proveir-lo a altres classes quan siga necessari (injecció de dependències).

### **Principals components en un projecte Spring MVC**
1. **Entitats (`@Entity`)**: Representen les taules de la base de dades.
2. **Repositoris (`@Repository`)**: Gestionen l'accés a les dades de la base de dades.
3. **Serveis (`@Service`)**: Contenen la lògica de negoci.
4. **Controladors (`@Controller` o `@RestController`)**: Gestionen les peticions del client i retornen respostes.

---

## **Relació entre components i comunicació**

1. **Controlador** → Gestiona peticions del client.
2. **Servei** → Executa la lògica de negoci.
3. **Repositori** → Interactua amb la base de dades.
4. **Entitats** → Representen les dades.

### Flux d'una petició:
1. El client fa una **petició HTTP** al **controlador**.
2. El **controlador** crida al **servei** per gestionar la lògica.
3. El **servei** utilitza el **repositori** per accedir a les dades.
4. El **repositori** recupera o manipula dades en la base de dades i les retorna.
5. El **servei** processa les dades i les passa al **controlador**.
6. El **controlador** retorna una resposta al client (HTML, JSON, etc.).

---

## **Detall i exemples de cada component**

### **1. Entitats (`@Entity`)**

Les **entitats** són classes Java que representen taules en una base de dades. Spring Boot utilitza **JPA (Java Persistence API)** per mapar aquestes classes amb les taules.

#### **Exemple**
Suposem que tenim una taula `ciutats` a la base de dades.

```java
package com.example.model;

import jakarta.persistence.*;

@Entity // Marca aquesta classe com una entitat JPA
@Table(name = "ciutats") // Opcional: especifica el nom de la taula
public class Ciutat {

    @Id // Clau primària
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Generació automàtica de l'ID
    private Long id;

    @Column(nullable = false) // El camp és obligatori
    private String nom;

    private int poblacio;

    // Getters i setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getPoblacio() {
        return poblacio;
    }

    public void setPoblacio(int poblacio) {
        this.poblacio = poblacio;
    }
}
```

#### **Què fa aquesta classe?**
- Defineix els camps que corresponen a les columnes de la taula.
- Marca els atributs especials (`@Id`, `@GeneratedValue`, etc.).
- És gestionada automàticament per Hibernate (la implementació de JPA més comuna en Spring Boot).

---

### **2. Repositoris (`@Repository`)**

Els **repositoris** són interfícies que gestionen l'accés a les dades. Spring Data JPA ofereix implementacions automàtiques de mètodes CRUD (Create, Read, Update, Delete).

#### **Exemple**

```java
package com.example.repository;

import com.example.model.Ciutat;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CiutatRepository extends CrudRepository<Ciutat, Long> {
    // Declaració de mètodes personalitzats
    Iterable<Ciutat> findByNomContaining(String nomPart);
}
```

#### **Què fa aquesta interfície?**
- Extén `CrudRepository`, que ja ofereix mètodes com `findAll`, `save`, `deleteById`, etc.
- Declara mètodes personalitzats que Spring implementa automàticament (com `findByNomContaining`).

---

### **3. Serveis (`@Service`)**

Els **serveis** contenen la lògica de negoci. Gestionen el flux de dades entre el controlador i el repositori.

#### **Exemple**

```java
package com.example.service;

import com.example.model.Ciutat;
import com.example.repository.CiutatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CiutatService {

    @Autowired
    private CiutatRepository ciutatRepository;

    public Iterable<Ciutat> getAllCiutats() {
        return ciutatRepository.findAll(); // Recupera totes les ciutats
    }

    public Ciutat getCiutatById(Long id) {
        return ciutatRepository.findById(id).orElse(null); // Busca una ciutat per ID
    }

    public Ciutat saveCiutat(Ciutat ciutat) {
        return ciutatRepository.save(ciutat); // Desa una nova ciutat
    }
}
```

#### **Què fa aquesta classe?**
- Centralitza la lògica de negoci, separant-la de la capa de controladors.
- Utilitza el repositori per interactuar amb les dades.

---

### **4. Controladors (`@Controller` o `@RestController`)**

Els **controladors** gestionen les peticions HTTP. Són el punt d'entrada per als usuaris (o clients).

#### **Exemple**

```java
package com.example.controller;

import com.example.model.Ciutat;
import com.example.service.CiutatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ciutats") // Ruta base per a totes les peticions
public class CiutatController {

    @Autowired
    private CiutatService ciutatService;

    @GetMapping // Gestiona peticions GET a "/ciutats"
    public Iterable<Ciutat> getAllCiutats() {
        return ciutatService.getAllCiutats();
    }

    @GetMapping("/{id}") // Gestiona peticions GET a "/ciutats/{id}"
    public Ciutat getCiutatById(@PathVariable Long id) {
        return ciutatService.getCiutatById(id);
    }

    @PostMapping // Gestiona peticions POST a "/ciutats"
    public Ciutat saveCiutat(@RequestBody Ciutat ciutat) {
        return ciutatService.saveCiutat(ciutat);
    }
}
```

#### **Què fa aquesta classe?**
- Defineix les rutes i mètodes HTTP (`GET`, `POST`, etc.).
- Crida als serveis per accedir a la lògica de negoci.
- Retorna respostes en format JSON o HTML, segons el tipus de controlador.

---

## **Relació entre components**

1. **Entitats**: Representen les dades (model).
2. **Repositoris**: Gestionen les operacions amb la base de dades.
3. **Serveis**: Encapsulen la lògica de negoci.
4. **Controladors**: Gestionen les peticions i respostes.

---

### **Exemple complet del flux**

1. Petició HTTP: **`GET /ciutats`**
2. El **controlador** crida al servei `getAllCiutats()`.
3. El **servei** utilitza el repositori per cridar `findAll`.
4. El **repositori** recupera les dades de la base de dades.
5. Les dades són retornades al servei, que les passa al controlador.
6. El **controlador** retorna la resposta en JSON.

---

### **Conclusió**

El patró MVC organitza el codi de manera que cada component té una responsabilitat clara. Això millora la modularitat, mantenibilitat i escalabilitat de l'aplicació. Aquest tutorial et dóna una base sòlida per començar amb Spring Boot i entendre com els components interactuen entre ells. 😊

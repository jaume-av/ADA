### **EXAMEN PRÀCTIC: Implementació d’una API REST i una Vista MVC en Spring Boot**  

⏳ **Duració estimada:** 1 hora 30 minuts  
📜 **Examen escrit a mà en paper** (sense ordinador)  

---

## **📌 Enunciat**  

Eres un/a desenvolupador/a que ha de dissenyar l’arquitectura d'una aplicació basada en **Spring Boot**, amb una base de dades **H2** en memòria. L’aplicació ha de permetre gestionar **ciutats i monuments**, on **cada ciutat pot tindre diversos monuments (1:N)**.  

L'objectiu de l'examen és que redactes a mà el **codi Java necessari** per a implementar:  

✅ **Entitats JPA** (amb la relació 1:N).  
✅ **Repositoris** per a accedir a la base de dades.  
✅ **Un únic Controlador REST** (GET, POST i DELETE).  
✅ **Un Controlador MVC** per a mostrar una vista amb **Thymeleaf**.  

📌 **Atenció:** **No has d'executar el codi**. Es tracta d’un examen teòric-pràctic per demostrar la teua comprensió de **Spring Boot, JPA i MVC**.  

---

## **📍 1️⃣ Model de Dades: Entitats JPA**  

En la base de dades **H2**, les taules es defineixen així:  

1. **Ciutat**  
   - `id` (**Long**, clau primària, generada automàticament)  
   - `nom` (**String**, obligatori)  
   - `poblacio` (**int**, obligatori)  

2. **Monument**  
   - `id` (**Long**, clau primària, generada automàticament)  
   - `nom` (**String**, obligatori)  
   - `ciutat_id` (**Long**, clau forana cap a `Ciutat`)  

📌 **Tasca:** Escriu el codi de les **entitats JPA** `Ciutat` i `Monument`, implementant la relació **1:N** amb les anotacions correctes.  

---

## **📍 2️⃣ Repositoris**  

Per a accedir a la base de dades, has de crear dos **repositoris JPA** que permeten gestionar `Ciutat` i `Monument`.  

📌 **Tasca:**  
- Defineix les interfícies `CiutatRepository` i `MonumentRepository`, heretant de `JpaRepository`.  
- Els repositoris han d'incloure els mètodes per a:  
  - **Obtenir totes les entitats**.  
  - **Buscar una entitat per ID**.  
  - **Guardar una entitat**.  

---

## **📍 3️⃣ API REST: Controlador REST**  

L’API REST ha de permetre **consultar, afegir i eliminar** ciutats i monuments.  

📌 **Tasca:**  
- Escriu el codi complet d’un controlador REST `CiutatMonumentRestController`.  
- El controlador ha de:  
  - Estar anotat correctament com a `@RestController`.  
  - Utilitzar `@RequestMapping("/api")` per definir el camí base dels endpoints.  
  - Injectar els repositoris amb `@Autowired` directament en els camps.  
  - Definir els següents **endpoints**:  
    - **`GET /api/ciutats`** → Retorna totes les ciutats.  
    - **`POST /api/ciutats`** → Afig una nova ciutat.  
    - **`DELETE /api/monuments/{id}`** → Elimina un monument per ID (sense comprovar si existeix).  

📌 **Pautes per a la Correcció:**  
✅ El controlador ha d’estar anotat correctament com a `@RestController`.  
✅ Ha d’incloure `@RequestMapping("/api")` a nivell de classe.  
✅ Els repositoris han d’injectar-se amb `@Autowired` **directament als camps**.  
✅ Els mètodes han de tindre les anotacions correctes per a cada operació:  
  - `@GetMapping("/ciutats")`  
  - `@PostMapping("/ciutats")`  
  - `@DeleteMapping("/monuments/{id}")` _(Sense comprovació prèvia d'existència)_.  

---

## **📍 4️⃣ Controlador MVC i Vista amb Thymeleaf**  

L'aplicació també ha de mostrar una **llista de ciutats i monuments** en una vista HTML bàsica.  

📌 **Tasca:**  
- Escriu el codi d’un **controlador MVC** (`CiutatController`) que tinga un endpoint:  
  - **`GET /ciutats`** → Retorna una vista amb les ciutats i els seus monuments.  
- Defineix una **plantilla Thymeleaf bàsica** (`ciutats.html`) que mostre:  
  - Un títol `"Llista de Ciutats"`.  
  - Totes les ciutats i els seus monuments en una llista.  

📌 **Nota:** No cal afegir estils ni funcionalitats avançades, només mostrar la informació.


### **SOLUCIÓ: Implementació de l'API REST i Vista MVC en Spring Boot**  

Aquesta és la solució escrita en codi Java segons l'enunciat de l'examen.  

---

## **📍 1️⃣ Model de Dades: Entitats JPA**  

```java
import jakarta.persistence.*;
import java.util.List;

@Entity
public class Ciutat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;
    private int poblacio;

    @OneToMany(mappedBy = "ciutat", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Monument> monuments;

    // Constructors, getters i setters
    public Ciutat() {}

    public Ciutat(String nom, int poblacio) {
        this.nom = nom;
        this.poblacio = poblacio;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public int getPoblacio() { return poblacio; }
    public void setPoblacio(int poblacio) { this.poblacio = poblacio; }

    public List<Monument> getMonuments() { return monuments; }
    public void setMonuments(List<Monument> monuments) { this.monuments = monuments; }
}
```

```java
import jakarta.persistence.*;

@Entity
public class Monument {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;

    @ManyToOne
    @JoinColumn(name = "ciutat_id", nullable = false)
    private Ciutat ciutat;

    // Constructors, getters i setters
    public Monument() {}

    public Monument(String nom, Ciutat ciutat) {
        this.nom = nom;
        this.ciutat = ciutat;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public Ciutat getCiutat() { return ciutat; }
    public void setCiutat(Ciutat ciutat) { this.ciutat = ciutat; }
}
```

---

## **📍 2️⃣ Repositoris**  

```java
import org.springframework.data.jpa.repository.JpaRepository;

public interface CiutatRepository extends JpaRepository<Ciutat, Long> {
}
```

```java
import org.springframework.data.jpa.repository.JpaRepository;

public interface MonumentRepository extends JpaRepository<Monument, Long> {
}
```

---

## **📍 3️⃣ API REST: Controlador REST**  

```java
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CiutatMonumentRestController {

    @Autowired
    private CiutatRepository ciutatRepository;

    @Autowired
    private MonumentRepository monumentRepository;

    // GET - Retorna totes les ciutats
    @GetMapping("/ciutats")
    public List<Ciutat> obtindreCiutats() {
        return ciutatRepository.findAll();
    }

    // POST - Afegir una nova ciutat
    @PostMapping("/ciutats")
    public Ciutat afegirCiutat(@RequestBody Ciutat ciutat) {
        return ciutatRepository.save(ciutat);
    }

    // DELETE - Elimina un monument per ID (sense comprovació prèvia)
    @DeleteMapping("/monuments/{id}")
    public void eliminarMonument(@PathVariable Long id) {
        monumentRepository.deleteById(id);
    }
}
```

---

## **📍 4️⃣ Controlador MVC i Vista amb Thymeleaf**  

```java
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/ciutats")
public class CiutatController {

    @Autowired
    private CiutatRepository ciutatRepository;

    @GetMapping
    public String veureCiutats(Model model) {
        List<Ciutat> ciutats = ciutatRepository.findAll();
        model.addAttribute("ciutats", ciutats);
        return "ciutats";
    }
}
```

---

## **📍 5️⃣ Plantilla Thymeleaf (`ciutats.html`)**  

```html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <title>Llista de Ciutats</title>
</head>
<body>
    <h1>Llista de Ciutats</h1>
    <ul>
        <li th:each="ciutat : ${ciutats}">
            <strong th:text="${ciutat.nom}"></strong> - 
            Població: <span th:text="${ciutat.poblacio}"></span>
            <ul>
                <li th:each="monument : ${ciutat.monuments}" th:text="${monument.nom}"></li>
            </ul>
        </li>
    </ul>
</body>
</html>
```

---

## **📌 Resum de la Solució**
✔ **Entitats JPA** definides amb la relació **1:N**.  
✔ **Repositoris JPA** per a la gestió de les dades.  
✔ **API REST** amb operacions GET, POST i DELETE segons l'enunciat.  
✔ **Controlador MVC** per mostrar les ciutats amb Thymeleaf.  
✔ **Plantilla HTML Thymeleaf** per visualitzar les ciutats i monuments.  

✅ **Aquesta solució compleix tots els requeriments de l'examen!**













AMPLIACIO POSSIBLE:

Perfecte! Així doncs, et deixo la versió millorada i simplificada de l'ampliació per a l'examen, sense afegir informació innecessària i mantenint el focus en la tasca dels alumnes.  

---

### **📌 Millora del Controlador REST: Ús de `CiutatService` i `CiutatDTO`**  

A l'examen, els alumnes han de modificar el controlador perquè utilitze `CiutatService` en lloc de cridar directament als repositoris.  

---

### **📌 Modificacions necessàries en `CiutatMonumentRestController`**  

#### **1️⃣ Eliminar la injecció directa dels repositoris**  
🔴 **Eliminar:**  
```java
@Autowired
private CiutatRepository ciutatRepository;

@Autowired
private MonumentRepository monumentRepository;
```
✅ **Substituir per:**  
```java
@Autowired
private CiutatService ciutatService;
```

---

#### **2️⃣ Modificar el mètode `GET` per utilitzar DTOs**  
🔴 **Eliminar:**  
```java
@GetMapping("/ciutats")
public List<Ciutat> obtindreCiutats() {
    return ciutatRepository.findAll();
}
```
✅ **Substituir per:**  
```java
@GetMapping("/ciutats")
public List<CiutatDTO> obtindreCiutats() {
    return ciutatService.obtindreTotesLesCiutats();
}
```

---

#### **3️⃣ Modificar el mètode `POST` per utilitzar DTOs**  
🔴 **Eliminar:**  
```java
@PostMapping("/ciutats")
public Ciutat afegirCiutat(@RequestBody Ciutat ciutat) {
    return ciutatRepository.save(ciutat);
}
```
✅ **Substituir per:**  
```java
@PostMapping("/ciutats")
public CiutatDTO afegirCiutat(@RequestBody CiutatDTO ciutatDTO) {
    return ciutatService.afegirCiutat(ciutatDTO);
}
```

---

#### **4️⃣ Modificar el `DELETE` per delegar-lo al servei**  
🔴 **Eliminar:**  
```java
@DeleteMapping("/monuments/{id}")
public void eliminarMonument(@PathVariable Long id) {
    monumentRepository.deleteById(id);
}
```
✅ **Substituir per:**  
```java
@DeleteMapping("/monuments/{id}")
public void eliminarMonument(@PathVariable Long id) {
    ciutatService.eliminarMonument(id);
}
```

---

## **📌 Servei i DTO proporcionats**
Els alumnes han d'utilitzar les classes següents per completar la tasca:

### **`CiutatService`**
```java
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CiutatService {

    @Autowired
    private CiutatRepository ciutatRepository;

    @Autowired
    private MonumentRepository monumentRepository;

    public List<CiutatDTO> obtindreTotesLesCiutats() {
        return ciutatRepository.findAll()
                .stream()
                .map(CiutatDTO::new)
                .collect(Collectors.toList());
    }

    public CiutatDTO afegirCiutat(CiutatDTO ciutatDTO) {
        Ciutat ciutat = new Ciutat(ciutatDTO.getNom(), ciutatDTO.getPoblacio());
        ciutat = ciutatRepository.save(ciutat);
        return new CiutatDTO(ciutat);
    }

    public void eliminarMonument(Long id) {
        monumentRepository.deleteById(id);
    }
}
```

### **`CiutatDTO`**
```java
public class CiutatDTO {
    private String nom;
    private int poblacio;

    public CiutatDTO() {}

    public CiutatDTO(Ciutat ciutat) {
        this.nom = ciutat.getNom();
        this.poblacio = ciutat.getPoblacio();
    }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public int getPoblacio() { return poblacio; }
    public void setPoblacio(int poblacio) { this.poblacio = poblacio; }
}
```

---

## **📌 Tasques de l'examen**
1️⃣ **Identifica i subratlla les línies que has de modificar en el controlador original.**  
2️⃣ **Modifica el controlador perquè utilitze `CiutatService` i `CiutatDTO` en lloc d'accedir directament als repositoris.**  
3️⃣ **Explica en poques paraules per què és millor aquesta nova estructura.**  

---


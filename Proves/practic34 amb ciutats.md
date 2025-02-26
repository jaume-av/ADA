### **EXAMEN PRÀCTIC: Disseny d'una API REST amb Base de Dades H2 i una Vista MVC**  

⏳ **Duració estimada:** 1 hora 30 minuts  
📜 **Examen escrit a mà en paper** (sense ordinador)  

---

## **📌 Enunciat**  

Eres un/a desenvolupador/a que ha de dissenyar l’arquitectura d'una aplicació basada en **Spring Boot**, amb una base de dades **H2** en memòria. L’aplicació ha de permetre gestionar **ciutats i monuments**, on **cada ciutat pot tindre diversos monuments (1:N)**.  

L'objectiu de l'examen és que redactes a mà el **codi Java necessari** per a implementar:  

✅ **Entitats JPA** (amb la relació 1:N).  
✅ **Repositoris** per a accedir a la base de dades.  
✅ **Un únic Controlador REST** (només GET i PUT).  
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

L’API REST ha de permetre **consultar i actualitzar** ciutats i monuments.  

📌 **Tasca:**  
- Escriu el codi per a **un únic controlador REST** (`CiutatRestController`).  
- El controlador ha de tindre **només** aquests endpoints:  
  - **`GET /api/ciutats`** → Retorna totes les ciutats amb els seus monuments.  
  - **`PUT /api/ciutats/{id}`** → Actualitza una ciutat existent (nom i població).  

📌 **Condicions**:  
- Les dependències han d’injectar-se **directament en els camps** amb `@Autowired`.  
- No cal gestionar errors ni afegir validacions avançades.  

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


# Solució:

### **SOLUCIÓ EXAMEN PRÀCTIC**  
📜 **Escrit a mà en paper** (sense ordinador)  

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

    @Column(nullable = false)
    private String nom;

    @Column(nullable = false)
    private int poblacio;

    @OneToMany(mappedBy = "ciutat", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Monument> monuments;

    // Constructors, getters i setters
}
```

```java
import jakarta.persistence.*;

@Entity
public class Monument {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nom;

    @ManyToOne
    @JoinColumn(name = "ciutat_id", nullable = false)
    private Ciutat ciutat;

    // Constructors, getters i setters
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
import java.util.Optional;

@RestController
@RequestMapping("/api/ciutats")
public class CiutatRestController {

    @Autowired
    private CiutatRepository ciutatRepository;

    @GetMapping
    public List<Ciutat> obtenirTotesLesCiutats() {
        return ciutatRepository.findAll();
    }

    @PutMapping("/{id}")
    public String actualitzarCiutat(@PathVariable Long id, @RequestBody Ciutat ciutatActualitzada) {
        Optional<Ciutat> ciutatOpt = ciutatRepository.findById(id);
        if (ciutatOpt.isPresent()) {
            Ciutat ciutat = ciutatOpt.get();
            ciutat.setNom(ciutatActualitzada.getNom());
            ciutat.setPoblacio(ciutatActualitzada.getPoblacio());
            ciutatRepository.save(ciutat);
            return "Ciutat actualitzada correctament";
        }
        return "ERROR: Ciutat no trobada";
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

import java.util.List;

@Controller
public class CiutatController {

    @Autowired
    private CiutatRepository ciutatRepository;

    @GetMapping("/ciutats")
    public String veureCiutats(Model model) {
        List<Ciutat> ciutats = ciutatRepository.findAll();
        model.addAttribute("ciutats", ciutats);
        return "ciutats";
    }
}
```

---

## **📍 5️⃣ Plantilla Thymeleaf: `ciutats.html`**  

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
            <strong th:text="${ciutat.nom}"></strong> - Població: <span th:text="${ciutat.poblacio}"></span>
            <ul>
                <li th:each="monument : ${ciutat.monuments}" th:text="${monument.nom}"></li>
            </ul>
        </li>
    </ul>
</body>
</html>
```

---

### ✅ **Explicació de la Solució**
1. **Entitats JPA**
   - `Ciutat` té una relació **1:N** amb `Monument`.
   - `Monument` conté una clau forana `ciutat_id` que apunta a `Ciutat`.

2. **Repositoris**
   - `CiutatRepository` i `MonumentRepository` permeten accedir a la base de dades.

3. **Controlador REST**
   - `GET /api/ciutats` retorna totes les ciutats amb els seus monuments.
   - `PUT /api/ciutats/{id}` actualitza el nom i la població d’una ciutat existent.

4. **Controlador MVC**
   - `GET /ciutats` retorna una vista **Thymeleaf** amb les ciutats i els seus monuments.

5. **Vista Thymeleaf**
   - Es mostra una llista de ciutats i, dins de cada ciutat, els seus monuments.

📌 **Aquest examen permet comprovar la comprensió de Spring Boot, JPA, Repositoris, Controladors REST i MVC amb Thymeleaf.**
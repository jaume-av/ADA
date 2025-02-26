### **EXAMEN PRÀCTIC: Disseny d'una API REST amb Base de Dades H2 i una Vista MVC**  

⏳ **Duració estimada:** 1 hora 30 minuts  
📜 **Examen escrit a mà en paper** (sense ordinador)  

---

## **Enunciat**  

Eres un/a desenvolupador/a que ha de dissenyar l’arquitectura d'una aplicació basada en **Spring Boot**, amb una base de dades **H2** en memòria. L’aplicació ha de permetre gestionar **categories i productes**, on **cada categoria pot tindre diversos productes (1:N)**.  

L'objectiu de l'examen és que redactes a mà el **codi Java necessari** per a implementar:  

✅ **Entitats JPA** (amb la relació 1:N).  
✅ **Repositoris** per a accedir a la base de dades.  
✅ **Controladors REST** (només GET i PUT).  
✅ **Un Controlador MVC** per a mostrar una vista amb **Thymeleaf**.  

📌 **Atenció:** **No has d'executar el codi**. Es tracta d’un examen teòric-pràctic per demostrar la teua comprensió de **Spring Boot, JPA i MVC**.  

---

## **1️⃣ Model de Dades: Entitats JPA**  

En la base de dades **H2**, les taules es defineixen així:  

1. **Categoria**  
   - `id` (Long, clau primària, generada automàticament)  
   - `nom` (String, obligatori)  

2. **Producte**  
   - `id` (Long, clau primària, generada automàticament)  
   - `nom` (String, obligatori)  
   - `preu` (BigDecimal, obligatori)  
   - `categoria_id` (Long, clau forana cap a `Categoria`)  

📌 **Tasca:** Escriu el codi de les **entitats JPA** `Categoria` i `Producte`, implementant la relació **1:N** amb les anotacions correctes.  

---

## **2️⃣ Repositoris**  

Per a accedir a la base de dades, has de crear dos **repositoris JPA** que permeten gestionar `Categoria` i `Producte`.  

📌 **Tasca:**  
- Defineix les interfícies `CategoriaRepository` i `ProducteRepository`, heretant de `JpaRepository`.  
- Els repositoris han d'incloure els mètodes per a:  
  - **Obtenir totes les entitats**.  
  - **Buscar una entitat per ID**.  
  - **Guardar una entitat**.  

---

## **3️⃣ API REST: Controladors REST**  

L’API REST ha de permetre consultar i actualitzar categories i productes.  

📌 **Tasca:**  
- Escriu el codi per a **dos controladors REST** (`CategoriaRestController` i `ProducteRestController`).  
- Cada controlador ha de tindre **només** aquests endpoints:  
  - `GET /api/categories` → Retorna totes les categories amb els seus productes.  
  - `PUT /api/categories/{id}` → Actualitza una categoria existent.  
  - `GET /api/productes` → Retorna tots els productes.  
  - `PUT /api/productes/{id}` → Actualitza un producte existent.  
- **No inclogues DELETE ni POST**.  

📌 **Condicions**:  
- Les dependències han d’injectar-se **directament en els camps** amb `@Autowired`.  
- No cal gestionar errors ni afegir validacions avançades.  

---

## **4️⃣ Controlador MVC i Vista amb Thymeleaf**  

L'aplicació també ha de mostrar una **llista de categories i productes** en una vista HTML bàsica.  

📌 **Tasca:**  
- Escriu el codi d’un **controlador MVC** (`CategoriaController`) que tinga un endpoint:  
  - `GET /categories` → Retorna una vista amb les categories i els seus productes.  
- Defineix una **plantilla Thymeleaf bàsica** (`categories.html`) que mostre:  
  - Un títol `"Llista de Categories"`.  
  - Totes les categories i els seus productes en una llista.  

📌 **Nota:** No cal afegir estils ni funcionalitats avançades, només mostrar la informació.  

---

## **📌 Criteris d'Avaluació (100 punts)**  

1. **Entitats JPA (25 punts)**  
   - `@Entity`, `@OneToMany`, `@ManyToOne` correctes.  
   - Identificadors generats automàticament (`@GeneratedValue`).  

2. **Repositoris (15 punts)**  
   - `JpaRepository` ben definit per a `Categoria` i `Producte`.  

3. **API REST (30 punts)**  
   - Controladors REST funcionals amb GET i PUT.  
   - Injecció de dependències correcta (`@Autowired`).  

4. **Controlador MVC i Thymeleaf (20 punts)**  
   - Controlador `CategoriaController` correcte.  
   - Vista `categories.html` que mostra correctament les dades.  

5. **Estructura i claredat del codi (10 punts)**  
   - Codi ben estructurat i fàcil de llegir.  
   - Ús correcte d’anotacions i convencions de Spring Boot.  

---

## **📢 Recomanacions per Escriure el Codi**  

✏️ **Estructura recomanada per a escriure el codi a mà:**  

1️⃣ **Escriu els encapçalaments correctes** per a cada secció (`// Entitat Categoria`, `// Repositori Producte`, etc.).  
2️⃣ **Utilitza indentació clara** per a millorar la llegibilitat.  
3️⃣ **Repassa les anotacions** abans d’entregar l’examen.  
4️⃣ **No oblidis la relació 1:N entre Categoria i Producte** en les entitats.  
5️⃣ **Si tens dubtes sobre Thymeleaf**, simplement mostra una llista bàsica de categories i productes.  

---

**📌 NOTES FINALS**  
- **No es permet l’ús d’ordinadors ni apunts.**  
- **Només pots utilitzar paper i bolígraf.**  
- **Si no recordes exactament alguna anotació, escriu el codi de la manera més aproximada possible.**  

📢 **Molta sort!** 🚀




# SOLUCIÓ

Aquí tens la **solució completa** de l'examen pràctic, escrita de manera clara perquè es puga redactar a mà fàcilment.

---

# **📌 SOLUCIÓ EXAMEN PRÀCTIC**

## **1️⃣ Entitats JPA (Relació 1:N)**  

```java
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "categories")
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nom;

    @OneToMany(mappedBy = "categoria", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Producte> productes;

    // Constructors, Getters i Setters
}
```

```java
import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "productes")
public class Producte {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nom;

    @Column(nullable = false)
    private BigDecimal preu;

    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;

    // Constructors, Getters i Setters
}
```

---

## **2️⃣ Repositoris**  

```java
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
}
```

```java
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProducteRepository extends JpaRepository<Producte, Long> {
}
```

---

## **3️⃣ Controladors REST (SOLS GET i PUT)**  

```java
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/categories")
public class CategoriaRestController {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @GetMapping
    public List<Categoria> obtenirTotes() {
        return categoriaRepository.findAll();
    }

    @PutMapping("/{id}")
    public Categoria actualitzarCategoria(@PathVariable Long id, @RequestBody Categoria novaCategoria) {
        Optional<Categoria> opcional = categoriaRepository.findById(id);
        if (opcional.isPresent()) {
            Categoria categoria = opcional.get();
            categoria.setNom(novaCategoria.getNom());
            return categoriaRepository.save(categoria);
        }
        return null; // En un cas real, millor gestionar errors
    }
}
```

```java
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import java.util.Optional;
import java.math.BigDecimal;

@RestController
@RequestMapping("/api/productes")
public class ProducteRestController {

    @Autowired
    private ProducteRepository producteRepository;

    @GetMapping
    public List<Producte> obtenirTots() {
        return producteRepository.findAll();
    }

    @PutMapping("/{id}")
    public Producte actualitzarProducte(@PathVariable Long id, @RequestBody Producte nouProducte) {
        Optional<Producte> opcional = producteRepository.findById(id);
        if (opcional.isPresent()) {
            Producte producte = opcional.get();
            producte.setNom(nouProducte.getNom());
            producte.setPreu(nouProducte.getPreu());
            return producteRepository.save(producte);
        }
        return null;
    }
}
```

---

## **4️⃣ Controlador MVC amb Thymeleaf**  

```java
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

@Controller
public class CategoriaController {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @GetMapping("/categories")
    public String veureCategories(Model model) {
        List<Categoria> categories = categoriaRepository.findAll();
        model.addAttribute("categories", categories);
        return "categories"; // Retorna la vista categories.html
    }
}
```

---

## **5️⃣ Plantilla Thymeleaf (`categories.html`)**  

📌 **Plantilla HTML bàsica per mostrar categories i productes.**  
📄 **Este codi s’ha de redactar a mà en paper durant l’examen.**

```html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <title>Llista de Categories</title>
</head>
<body>
    <h1>Llista de Categories</h1>
    <ul>
        <li th:each="categoria : ${categories}">
            <strong th:text="${categoria.nom}"></strong>
            <ul>
                <li th:each="producte : ${categoria.productes}" th:text="${producte.nom} + ' - ' + ${producte.preu} + '€'"></li>
            </ul>
        </li>
    </ul>
</body>
</html>
```

---

## **📢 NOTES FINALS**
- **El codi s’ha de redactar a mà en paper, seguint l’estructura recomanada.**  
- **No cal incloure gestió avançada d’errors.**  
- **L’objectiu és demostrar comprensió de JPA, REST i MVC en Spring Boot.**  

📌 **Aquesta solució cobreix completament els requisits de l'examen.** 🚀


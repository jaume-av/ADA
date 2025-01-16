---
title: 1. Introducció a Spring Boot. 
parent: SPRING

has_children: true
layout: default
nav_order: 10
---





# Introducció a Spring Boot


**Spring Boot** és un framework obert basat en Java que simplifica el desenvolupament d'aplicacions modernes. Està construït sobre **Spring Framework**, però introdueix un nivell més alt d'automatització per reduir la configuració manual i agilitzar el treball.

En poques paraules, **Spring Boot** ens permet crear aplicacions **ràpides i senzilles** amb una estructura robusta i escalable.

Spring Boot és molt versàtil. Algunes de les aplicacions més comunes són:

- **APIs REST**: Per a la comunicació entre aplicacions.
- **Aplicacions web**: Amb interfícies d'usuari modernes.
- **Microserveis**: Per gestionar aplicacions distribuïdes.
- **Aplicacions empresarials**: Amb necessitats complexes de persistència i seguretat.

---

### **Característiques principals de Spring Boot**

- **1. Simplificació del desenvolupament**
Amb Spring Boot, no cal que configurem manualment fitxers XML llargs o gestionem dependències complexament. Tot es gestiona automàticament o amb fitxers senzills com `application.properties`.

- **2. Servidor integrat**
Pots iniciar la teua aplicació amb el servidor ja inclòs (per defecte, **Tomcat**). Això significa que no necessites instal·lar un servidor extern.

Exemple pràctic (codi d'arrencada):
```java
@SpringBootApplication
public class AplicacioSpringBoot {
    public static void main(String[] args) {
        SpringApplication.run(AplicacioSpringBoot.class, args);
    }
}
```

Quan executem este codi, Spring Boot aixeca automàticament un servidor web en el port 8080.

- **3. Dependències gestionades amb Maven o Gradle**
Spring Boot utilitza Maven o Gradle per gestionar biblioteques i versions compatibles. Només has d'afegir les dependències necessàries al fitxer `pom.xml` o `build.gradle`.

Exemple de dependències Maven:
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
```

- **4. Compatibilitat amb entorns moderns**
Spring Boot es pot integrar fàcilment amb contenidors com Docker, entorns de núvol com **AWS** o **Google Cloud** i eines modernes com **Kubernetes**.

---

- **5 Avantatges de Spring Boot**

  - **Configuració automàtica**: Detecta automàticament les dependències del projecte i les configura.
  - **Ecosistema complet**: És compatible amb **Spring Security**, **Spring Data**, **Spring Cloud**, entre d'altres.
  - **Rendiment alt**: Gràcies a l'optimització de recursos.
  - **Comunitat activa**: Hi ha moltíssima documentació i suport en línia.

---

## **L'ecosistema Spring**

Entenem  **ecosistema Spring** com el conjunt de mòduls, eines i frameworks dissenyats per ajudar-nos a construir aplicacions modernes, escalables i robustes. Aquest ecosistema cobreix totes les necessitats del desenvolupament d'aplicacions, des de la persistència de dades fins a la seguretat o la comunicació entre microserveis.

---



### **1. Spring Framework**
El **cor de l'ecosistema**. Proporciona eines bàsiques per gestionar la lògica d'aplicacions empresarials:
- **Injecció de dependències (IoC)**: Gestiona la creació i l'ús d'objectes amb anotacions com `@Component` i `@Autowired`.
- **Programació orientada a aspectes (AOP)**: Permet afegir funcionalitats com la gestió de transaccions o logs sense modificar el codi principal.


>**Nota**: La Programació Orientada a Aspectes (AOP) és un paradigma de programació que permet modularitzar funcionalitats transversals a través de l'ús de >"aspectes". Un aspecte és un mòdul que encapsula comportaments que poden ser reutilitzats en diferents parts de l'aplicació, com per exemple la gestió de >transaccions, la seguretat o el logging.

---

### **2. Spring Boot**
Facilita la creació d'aplicacions ràpides i sense configuració manual.
- Inclou servidors integrats com Tomcat.
- Automatitza la configuració mitjançant anotacions com `@SpringBootApplication`.

---

### **3. Spring Data**
Simplifica l'accés i la persistència de dades:
- Suporta diversos tipus de bases de dades (SQL com MySQL, i NoSQL com MongoDB).
- Repositoris predefinits amb **Spring Data JPA**.
- Anotacions com `@Entity` per definir models relacionals.

---

### **4. Spring Security**
Proporciona **autenticació i autorització** per protegir aplicacions:
- Configura usuaris i rols.
- Suporta protocols moderns com OAuth2 i JWT.
- Permet limitar l'accés amb anotacions com `@PreAuthorize`.

---

### **5. Spring MVC (Model-View-Controller)**
Gestiona peticions web i la separació de responsabilitats:
- **Model**: Gestiona dades i lògica de negoci.
- **View**: Mostra la interfície d'usuari (normalment amb Thymeleaf o JSP).
- **Controller**: Processa les peticions HTTP.

---

### **6. Spring Cloud**
És perfecte per gestionar **microserveis i arquitectures distribuïdes**:
- **Eureka**: Registre i descobriment de serveis.
- **Config Server**: Gestiona configuracions centralitzades.
- **Spring Cloud Gateway**: Gestió de rutes i seguretat.

---

### **7. Spring Batch**
Dissenyat per **processar grans volums de dades** en lots:
- Ideal per tasques com ETL (Extract, Transform, Load) o processaments per lots.

---

### **8. Spring Integration i Spring Messaging**
Facilita la **comunicació asíncrona** entre components:
- Integra missatgeria amb sistemes com RabbitMQ o Apache Kafka.

---

### **Exemple pràctic: Una aplicació completa de l'ecosistema Spring**

Imagina que volem crear un sistema per gestionar **comandes en línia** amb les següents funcionalitats:

1. Autenticació d'usuaris (Spring Security).
2. Gestió de comandes (Spring Data).
3. API REST per a les operacions CRUD (Spring Boot + Spring MVC).
4. Monitorització del sistema (Spring Actuator).
5. Comunicació amb altres serveis (Spring Cloud).

---

**1. Configuració del projecte**
- Generem un projecte Spring Boot amb dependències:
  - `Spring Web`, `Spring Data JPA`, `Spring Security`, `Spring Actuator`, `Spring Cloud`.

---

**2. Entitats i repositoris**
```java
@Entity
public class Comanda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String producte;

    private int quantitat;

    private String estat; // Pendent, Enviat, etc.

    // Getters i Setters
}
```

```java
@Repository
public interface ComandaRepository extends JpaRepository<Comanda, Long> {
    List<Comanda> findByEstat(String estat);
}
```

---

**3. Lògica de negoci**
```java
@Service
public class ComandaService {

    @Autowired
    private ComandaRepository repository;

    public List<Comanda> obtenirTotes() {
        return repository.findAll();
    }

    public Comanda afegirComanda(Comanda comanda) {
        return repository.save(comanda);
    }

    public void eliminarComanda(Long id) {
        repository.deleteById(id);
    }
}
```

---

**4. Controlador REST**
```java
@RestController
@RequestMapping("/api/comandes")
public class ComandaController {

    @Autowired
    private ComandaService service;

    @GetMapping
    public List<Comanda> llistarComandes() {
        return service.obtenirTotes();
    }

    @PostMapping
    public Comanda crearComanda(@RequestBody Comanda comanda) {
        return service.afegirComanda(comanda);
    }

    @DeleteMapping("/{id}")
    public void eliminarComanda(@PathVariable Long id) {
        service.eliminarComanda(id);
    }
}
```

---

**5. Seguretat**
- Afegim configuració bàsica per protegir l'API REST.
```java
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
            .antMatchers("/api/comandes/**").authenticated()
            .and()
            .httpBasic();
    }
}
```

---

**6. Monitorització**
- Habilitem **Spring Actuator** per supervisar l'estat del sistema.
- Exemple d'endpoint:
  - `/actuator/health`: Verifica que l'aplicació estiga en funcionament.
  - `/actuator/metrics`: Mostra mètriques del sistema.

---

**7. Comunicació entre serveis**
- Utilitzem **Spring Cloud** per connectar amb altres serveis que gestionen els enviaments.
```java
@RestController
@RequestMapping("/api/enviaments")
public class EnviamentController {

    @GetMapping("/seguiment/{id}")
    public String seguiment(@PathVariable Long id) {
        return "Comanda " + id + " en trànsit";
    }
}
```

---

L'ecosistema **Spring** ens ofereix totes les eines necessàries per construir aplicacions modernes i complexes, des de la seguretat fins a la persistència i la comunicació entre serveis. 
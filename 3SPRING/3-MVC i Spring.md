---
title: 4. IoC i DI. 
parent: SPRING

has_children: true
layout: default
nav_order: 40
---

# **Inversió de Control (IoC) i Injecció de Dependències (DI) a Spring Boot**

Spring Boot es basa en dos conceptes essencials que simplifiquen el desenvolupament d’aplicacions: **Inversió de Control (IoC)** i **Injecció de Dependències (DI)**.
     
Estos conceptes, que poden pareixer abstractes i difícils de comprendre al principi, són **claus per entendre com Spring Boot gestiona els components** d’una aplicació i com es relacionen entre ells.

---

## **1. Inversió de Control (IoC)**

La **Inversió de Control (IoC)** és un **principi de disseny** que canvia la responsabilitat de **crear i gestionar els objectes** d’una aplicació.

En una aplicació tradicional, som nosaltres qui:
- Creem els objectes amb `new`
- Decidim quan es creen
- Gestionem les seues dependències

Amb IoC, aquesta responsabilitat es trasllada a un component extern.

En el cas de Spring Boot, és el **contenidor IoC de Spring** qui:

- **Crea els objectes** que necessita l’aplicació (anomenats **beans**)
- **Gestiona el cicle de vida** d’aquests objectes
- **Injecta les dependències** quan una classe les necessita

En resum, amb IoC:
> **Demanem objectes, però no els creem nosaltres.**

> **IoC no és exclusiu de Spring**: és un principi de disseny general.  
> **Spring Boot és una implementació concreta** d’aquest principi.

---

### **Què és un bean?**

Un **bean** és un **objecte creat, gestionat i injectat pel contenidor IoC de Spring**.

Només els objectes que Spring coneix (beans) poden ser injectats com a dependències.

---

### **Què és el contenidor IoC de Spring?**

El **contenidor IoC de Spring** és el nucli del framework. La seua funció principal és **gestionar els beans i les seues dependències** dins d’una aplicació.

El contenidor:

- Decideix **quan i com** es creen els objectes
- Manté una única instància (per defecte) de cada bean
- Proporciona aquests objectes quan una altra classe els necessita

---

### **Com funciona el contenidor IoC?**

1. **Inicialització del contenidor**
   - El contenidor IoC s’inicia amb:
     ```java
     SpringApplication.run(Aplicacio.class, args);
     ```
   - Spring escaneja els paquets del projecte a partir de la classe anotada amb `@SpringBootApplication`.

2. **Detecció de components**
   - Spring detecta classes anotades amb:
     - `@Component`
     - `@Service`
     - `@Repository`
     - `@Controller`
     - `@RestController`

3. **Creació de beans**
   - Per cada classe detectada, Spring crea una instància i la registra com a **bean**.

4. **Gestió de dependències**
   - Quan una classe necessita una altra, Spring injecta automàticament la dependència.

5. **Gestió del cicle de vida**
   - Spring controla la inicialització i destrucció dels beans.
   - Pot executar mètodes especials com:
     - `@PostConstruct`
     - `@PreDestroy`

---

### **Per què existeixen @Component, @Service, @Repository i @Controller?**

Totes aquestes anotacions són, en realitat, especialitzacions de `@Component`.

- **@Component**: anotació genèrica
- **@Service**: indica lògica de negoci
- **@Repository**: indica accés a dades  
  - A més, tradueix excepcions de persistència
- **@Controller / @RestController**: indiquen controladors web

Açò no canvia el funcionament tècnic, però **aporta significat i claredat arquitectònica**.

---

### **Exemple d’IoC**

#### **1. Sense IoC (creació manual d’objectes)**

```java
public class Servei {
    private Repositori repositori;

    public Servei() {
        // El servei crea la dependència manualment
        this.repositori = new Repositori();
    }

    public void executar() {
        repositori.guardar();
    }
}
```

**Problemes d’aquest enfocament:**

* **Acoblament** (Coupling) fort: El Servei està lligat a la implementació concreta de Repositori. Si vols canviar de repositori, has de modificar el codi.
* **Dificultat** per fer proves (mocking)
* C**odi poc flexible** i difícil de mantenir


---

#### **2. Amb IoC (gestió del contenidor Spring)**

**Repositori:**

```java
@Repository
public class Repositori {
    public void guardar() {
        System.out.println("Dades guardades");
    }
}
```

**Servei:**

```java
@Service
public class Servei {
    private final Repositori repositori;

    @Autowired
    public Servei(Repositori repositori) {
        this.repositori = repositori;
    }

    public void executar() {
        repositori.guardar();
    }
}
```

Ara:

* El servei **no crea** el repositori
* Spring s’encarrega de crear-lo i injectar-lo
* El codi és més flexible i testejable

---

## **2. Injecció de Dependències (DI)**

La **Injecció de Dependències (DI)** és la **tècnica que Spring utilitza per aplicar IoC**.

Les dependències:

* No es creen amb `new`
* Es **passen des de fora** de la classe

> **IoC és el principi.
> DI és la tècnica concreta.**

---

### **Formes d’injecció de dependències a Spring**

#### **1. Injecció per constructor (recomanada)**

* Garanteix que la classe estiga completament inicialitzada
* Facilita proves unitàries
* Permet declarar dependències com a `final`

```java
@Service
public class Servei {
    private final Repositori repositori;

    @Autowired
    public Servei(Repositori repositori) {
        this.repositori = repositori;
    }
}
```

> Si una classe té **un únic constructor**, `@Autowired` **no és obligatori**.

---

#### **2. Injecció per setter**

* Útil per a dependències opcionals
* Permet canviar la dependència després de crear l’objecte

```java
@Service
public class Servei {
    private Repositori repositori;

    @Autowired
    public void setRepositori(Repositori repositori) {
        this.repositori = repositori;
    }
}
```

---

#### **3. Injecció directa en camps**

```java
@Service
public class Servei {
    @Autowired
    private Repositori repositori;
}
```

És la més senzilla, però **no és recomanada** perquè:

* Amaga dependències
* Dificulta proves unitàries
* No garanteix una inicialització completa

---

### **Què NO fa Spring**

* Spring **només injecta beans**
* No pot injectar classes creades amb `new`
* Crear objectes manualment trenca IoC

---

## **Exemple complet d’IoC i DI**

**Repositori:**

```java
@Repository
public class PaisRepository {
    public String[] obtenirPaisos() {
        return new String[]{"Espanya", "França", "Itàlia"};
    }
}

// Este repositori no hereta de JpaRepository ni implementa cap interfície,
// però Spring el detecta com a bean per l’anotació @Repository
```

**Servei:**

```java
@Service
public class PaisService {
    private final PaisRepository paisRepository;

    @Autowired
    public PaisService(PaisRepository paisRepository) {
        this.paisRepository = paisRepository;
    }

    public void mostrarPaisos() {
        String[] paisos = paisRepository.obtenirPaisos();
        for (int i = 0; i < paisos.length; i++) {
            System.out.println(paisos[i]);
        }
    }
}
```

**Controlador:**

```java
@RestController
@RequestMapping("/paisos")
public class PaisController {
    private final PaisService paisService;

    @Autowired
    public PaisController(PaisService paisService) {
        this.paisService = paisService;
    }

    @GetMapping
    public void llistarPaisos() {
        paisService.mostrarPaisos();
    }
}
```

> En una API REST real, el controlador normalment retornaria dades (JSON),
> no faria `System.out.println`.
> Ací s’utilitza per simplificar l’exemple.

---

### **En resum: IoC i DI a Spring Boot**

| Concepte                 | Funcionament                                |
| ------------------------ | ------------------------------------------- |
| Inversió de Control      | Spring crea i gestiona els objectes         |
| Injecció de Dependències | Spring injecta les dependències entre beans |

Amb **IoC** i **DI**, **Spring Boot** automatitza la gestió d’objectes, redueix la dependència entre classes i fa que el codi siga més modular, mantenible i fàcil de provar.

```

---

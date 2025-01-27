---
title: 4. IoC i DI. 
parent: SPRING

has_children: true
layout: default
nav_order: 40

---


# **Inversió de Control (IoC) i Injecció de Dependències (DI) a Spring Boot**

Spring Boot es basa en dos conceptes essencials que simplifiquen el desenvolupament d’aplicacions: **Inversió de Control (IoC)** i **Injecció de Dependències (DI)**. Estos conceptes (que poden pareixer abstractes i deificils al principi), són claus per entendre com Spring Boot gestiona automàticament els components del projecte.

---

## **1. Inversió de Control (IoC)**

La **Inversió de Control (IoC)** és un principi de disseny que canvia la responsabilitat de **crear i gestionar els objectes** d'una aplicació. 

No som nosaltres qui creem els objectes, és el **contenidor IoC de Spring** qui:

- **Crea els objectes** que necessita l’aplicació (anomenats **beans**).
- **Gestiona el cicle de vida** d’aquests objectes.
- **Proporciona aquests objectes** quan una classe els necessita.

En resum, IoC permet que  nosaltres sigam **els que demanen els objectes**, però no els que els creen.

---


### **Què és el contenidor IoC de Spring?**

El **contenidor IoC de Spring** és el nucli del framework Spring. La seua funció principal és **gestionar els objectes (beans)** i les seues **dependències** dins d'una aplicació.

#### **Com funciona el contenidor IoC?**
1. **Inicialització del contenidor**:
   - El contenidor IoC s'inicia amb el mètode `SpringApplication.run()`:
     ```java
     @SpringBootApplication
     public class Aplicacio {
         public static void main(String[] args) {
             SpringApplication.run(Aplicacio.class, args); // Arranca el contenidor IoC
         }
     }
     ```
   - Durant l’arrencada, Spring escaneja les classes del projecte per trobar anotacions com `@Component`, `@Service`, `@Repository` o `@Controller`.

2. **Creació de beans**:
   - Per cada classe detectada amb aquestes anotacions, Spring crea una instància (bean) i la registra al contenidor IoC.

3. **Gestió de dependències**:
   - Si una classe (per exemple, un servei) necessita un altre objecte (per exemple, un repositori), Spring detecta aquesta necessitat (a través de `@Autowired`) i injecta automàticament la dependència.

4. **Gestió del cicle de vida dels beans**:
   - Spring controla quan els beans són inicialitzats, utilitzats i destruïts. També pot executar mètodes inicials (`@PostConstruct`) o finals (`@PreDestroy`).

**Els avantatges que ens proporciona el contenidor IoC són, entre d'altres**:

- **Redueix el coupling**: Les classes no depenen d'implementacions concretes.
- **Facilita la reutilització**: Els beans es poden compartir entre diferents components.
- **Simplifica la configuració**: Les dependències es gestionen automàticament.

---

### **Exemple d'IoC**

-  **1.- Sense IoC (creació manual d’objectes):**
En aquest cas, cada classe crea les seues dependències utilitzant el mètode `new`.

```java
public class Servei {
    private Repositori repositori;

    public Servei() {
        // El servei crea la dependència manualment
        this.repositori = new Repositori(); 
    }

    public void executar() {
        // Acció utilitzant el repositori
        System.out.println("Executant servei...");
        repositori.guardar(); // Crida al mètode guardar del repositori
    }
}
```

**Problemes amb aquest codi:**
1. **Acoblament (Coupling) fort**: El `Servei` està lligat a la implementació concreta de `Repositori`. Si vols canviar de repositori, has de modificar el codi.
2. **Dificultat per fer proves**: No pots substituir fàcilment el `Repositori` per una versió falsa (**mock**) en les proves.
3. **Codi menys flexible i difícil de mantenir**.

---

- **2.- Amb IoC (gestió automàtica de Spring):**
Ara és el **contenidor IoC de Spring** qui crea i injecta automàticament el repositori al servei.

**Repositori:**
```java
@Repository
public class Repositori {
    public void guardar() {
        // Simulació de guardar dades
        System.out.println("Dades guardades al repositori!");
    }
}
```

**Servei:**
```java
@Service
public class Servei {
    private Repositori repositori;

    // La dependència es proporciona automàticament amb IoC
    @Autowired
    public Servei(Repositori repositori) {
        this.repositori = repositori; // La dependència és injectada per Spring
    }

    public void executar() {
        // Acció utilitzant el repositori
        System.out.println("Executant servei amb IoC...");
        repositori.guardar(); // Crida al mètode guardar del repositori
    }
}
```

**Classe principal:**
```java
@SpringBootApplication
public class Aplicacio {
    public static void main(String[] args) {
        // Inicialitza l'aplicació i el contenidor IoC de Spring
        SpringApplication.run(Aplicacio.class, args);
    }
}
```

---

### **Funcionament del contenidor IoC?**

1. **Detecta les classes anotades amb**:
   - `@Repository`, `@Service`, `@Controller`, `@RestController`, etc.
2. **Crea automàticament instàncies** d’aquestes classes (anomenades **beans**).
3. **Gestiona el cicle de vida dels beans** i injecta les dependències quan són necessàries.

---

## **2. Injecció de Dependències (DI)**


La **Injecció de Dependències (DI)** és una tècnica que implementa **IoC**. Permet que el **contenidor IoC** de Spring **proporcione automàticament** els objectes que una classe necessita. Així, les dependències es passen a la classe **des de fora**.

- En lloc de crear un objecte amb `new`, Spring l’injecta automàticament.

---

 **Formes d’injectar dependències a Spring**

Spring Boot ofereix tres formes principals d’injecció de dependències:

1. **Injecció per constructor (recomanada):**
   Spring passa les dependències necessàries a través del constructor. És la millor opció perquè assegura que la classe estiga correctament inicialitzada.

   **Exemple:**
   ```java
   @Service
   public class Servei {
       private final Repositori repositori; // Dependència declarada com a final

       @Autowired
       public Servei(Repositori repositori) {
           this.repositori = repositori; // La dependència es passa pel constructor
       }

       public void executar() {
           repositori.guardar(); // Crida al mètode guardar del repositori
       }
   }
   ```

2. **Injecció per mètode setter:**
   Spring utilitza un mètode setter per passar la dependència. Aquesta opció és útil si la dependència és opcional.

   **Exemple:**
   ```java
   @Service
   public class Servei {
       private Repositori repositori; // Dependència no final perquè es pot modificar

       @Autowired
       public void setRepositori(Repositori repositori) {
           this.repositori = repositori; // La dependència es passa pel setter
       }

       public void executar() {
           repositori.guardar(); // Crida al mètode guardar del repositori
       }
   }
   ```

3. **Injecció directa en camps:**
   Spring injecta la dependència directament al camp. És l’opció més senzilla, però no és la més recomanada perquè dificulta proves i no assegura que l’objecte estiga completament inicialitzat.

   **Exemple:**
   ```java
   @Service
   public class Servei {
       @Autowired
       private Repositori repositori; // Dependència injectada directament

       public void executar() {
           repositori.guardar(); // Crida al mètode guardar del repositori
       }
   }
   ```

---

### **Exemple de IoC i DI**

- **Repositori:**
```java
@Repository
public class PaisRepository {
    public String[] obtenirPaisos() {
        // Retorna una llista de països
        return new String[]{"Espanya", "França", "Itàlia"};
    }
}
```

- **Servei:**
```java
@Service
public class PaisService {
    private final PaisRepository paisRepository; // Dependència injectada per constructor

    @Autowired
    public PaisService(PaisRepository paisRepository) {
        this.paisRepository = paisRepository; // Injecció de la dependència
    }

    public void mostrarPaisos() {
        // Obté els països del repositori i els mostra per pantalla
        String[] paisos = paisRepository.obtenirPaisos();
        for (int i = 0; i < paisos.length; i++) {
            System.out.println(paisos[i]); // Mostra cada país
        }
    }
}
```

- **Controlador:**
```java
@RestController
@RequestMapping("/paisos")
public class PaisController {
    private final PaisService paisService; // Dependència injectada per constructor

    @Autowired
    public PaisController(PaisService paisService) {
        this.paisService = paisService; // Injecció de la dependència
    }

    @GetMapping
    public void llistarPaisos() {
        // Crida al servei per mostrar els països
        paisService.mostrarPaisos();
    }
}
```

---

**Com funciona IoC i DI a l'exemple:**

1. **IoC:**
   - El contenidor Spring detecta `PaisRepository`, `PaisService` i `PaisController` gràcies a les anotacions `@Repository`, `@Service` i `@RestController`.
   - Crea instàncies de cada classe i les registra com a **beans**.

2. **DI:**
   - Spring injecta automàticament:
     - `PaisRepository` dins de `PaisService`.
     - `PaisService` dins de `PaisController`.

3. Quan accedeixes a l’URL `http://localhost:8080/paisos`, el controlador:
   - Utilitza el servei per obtenir els països.
   - El servei crida al repositori per recuperar les dades.

---

### **En Resum: IoC i DI a Spring Boot**

| **Concepte**            | **Com funciona a Spring Boot**                                                                 |
|-------------------------|------------------------------------------------------------------------------------------------|
| **Inversió de Control** | El contenidor IoC de Spring crea i gestiona automàticament els objectes necessaris.             |
| **Injecció de Dependències** | Les dependències entre classes són proporcionades automàticament pel contenidor mitjançant anotacions com `@Autowired`. |

Amb IoC i DI, Spring Boot automatitza la gestió d’objectes, redueix la dependència entre classes i fa que el codi siga més modular, mantenible i fàcil de provar.


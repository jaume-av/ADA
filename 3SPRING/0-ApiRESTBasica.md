---
title: 0. Creació API Rest Bàsica 
parent: SPRING

has_children: true
layout: default
nav_order: 4
---


# Creació API Rest Bàsica

Com a punt de partida, crearem una API Bàsica d'una sola taula.
Utilitzarem:

- Spring Boot
- Spring Data JPA
- Base de Dades H2 en memòria.
- Postman, per a proves dels **endPoints** de la API que gererem.

Mapejarem una taula amb anotacions JPA, crearem un controlador i un servei, i una connexió a H2

### Creació del Projecte Spring Boot

Accedim a spring initializer: [Initializr](https://start.spring.io/)


![alt text](imatges/initializr.png)


Automàticament es generaran les dependències **gradle** necessàries i el projecte es descarregarà en un zip.

### Configuració del Projecte.

- **Estructura**

La nostra API bàsica tindrà la següent estructura:

  
![alt text](imatges/estructuraAPIBasica.png)




- **1.- application.properies**

L'arxiu de configuració `application.properties` és un arxiu de propietats que conté valors clau-valor i és utilitzat per configurar l'aplicació Spring Boot. 
Es pot utilitzar per configurar la connexió a la base de dades, el port del servidor, etc.

```bash

# Configurem el port del servidor (per defecte és el 8080)
server.port=8080

# Activem la consola de la base de dades H2
spring.h2.console.enabled=true

# Configuració de la base de dades
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=sa

# Indicacions per a Hibernate sobre la gestió dels canvis a la base de dades
spring.jpa.hibernate.ddl-auto=update
```


- **2.- Connexió amb la Base de Dades H2**

Configurem des d'IntelliJ la connexió a la base de dades H2:

- Crearem un datasource amb el nom que hem posat a l'arxiu **application.properties (testdb)**
- La URL de connexió és la que hem posat a l'arxiu de configuració.
- L'usuari i la contrasenya són els que s'indiquen a l'arxiu de configuració.

Realizem el test de connexió per comprovar que tot està correcte.


![alt text](imatges/DataSourceH2.png)



### Classe Ciutat - JPA

- **Classe Inicial**

```java

public class Ciutat {

    private Long id;
    private String nom;
    private int poblacio;
    private String descripcio;
    private String imatge;

    // Constructors (els 2) , Getters i Setters

}
```


- **3.- Entitat Ciutat amb Anotacions JPA**

```java

@Entity
@Table (name = "Ciutat")
public class Ciutat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String nom;
    private int poblacio;
    private String descripcio;
    private String imatge;

    // Constructors, getters i setters
}
```
---

**Les anotacions JPA més importants són:**



| **Anotació**       | **Propòsit**                                                                                 | **Exemples bàsics**                                                                         |
|---------------------|-----------------------------------------------------------------------------------------|-------------------------------------------------------------------------------------------------|
| `@Entity`          | Marca una classe com una entitat JPA, representant una taula en la base de dades.        | `@Entity public class Ciutat { ... }` <br> *Defineix la classe `Ciutat` com a entitat JPA, fent que cada instància corresponga a una fila de la taula de la base de dades.* <br> `@Entity public class Usuari { ... }` <br> *La classe `Usuari` serà una altra entitat JPA mapejada a la taula de base de dades per a gestionar informació d'usuaris.* |
| `@Table`           | Defineix el nom de la taula i altres configuracions (com índexs o restriccions).         | `@Table(name = "ciutats")` <br> *Especifica que aquesta entitat s'emmagatzema en la taula `ciutats` de la base de dades.* <br> `@Table(name = "usuaris", schema = "administracio")` <br> *Mapeja l'entitat `Usuari` a la taula `usuaris` dins de l'esquema `administracio` de la base de dades.* |
| `@Id`              | Indica quin atribut és la clau primària de l’entitat.                                   | `@Id private Long id;` <br> *Marca l'atribut `id` com a clau primària de l'entitat.* <br> `@Id private String codi;` <br> *Marca l'atribut `codi` com a clau primària si la taula utilitza una clau alfanumèrica en lloc d'un ID numèric.* |
| `@GeneratedValue`  | Especifica com es generarà el valor de la clau primària (ex.: autoincremental).          | `@GeneratedValue(strategy = GenerationType.IDENTITY)` <br> *Utilitza la configuració d'autoincrement pròpia de la base de dades (com MySQL o PostgreSQL).* <br> `@GeneratedValue(strategy = GenerationType.AUTO)` <br> *Spring selecciona automàticament l'estratègia adequada segons la base de dades.* |
| `@Column`          | Configura propietats específiques d’una columna (nom, si és nullable, longitud, etc.).   | `@Column(nullable = false, length = 50)` <br> *Indica que aquesta columna no pot ser nul·la i limita la longitud del text a 50 caràcters.* <br> `@Column(name = "nom_ciutat", unique = true)` <br> *Canvia el nom de la columna a `nom_ciutat` i assegura que el seu valor siga únic.* |
| `@Transient`       | Marca un atribut que no es guardarà en la base de dades.                                | `@Transient private String cache;` <br> *Evita que el camp `cache` siga persistit a la base de dades perquè és temporal.* <br> `@Transient private boolean modificat;` <br> *L'atribut `modificat` s'utilitza per processar dades en memòria, però no es guarda en la base de dades.* |
| `@Lob`             | Indica que un atribut és un objecte gran, com text llarg o binari.                      | `@Lob private String descripcioDetallada;` <br> *Permet guardar un text molt llarg, com una descripció extensa, en una columna de tipus CLOB.* <br> `@Lob private byte[] imatge;` <br> *Permet guardar dades binàries, com imatges o fitxers, en una columna de tipus BLOB.* |



---



### Repositori - CiutatRepository

```java

@Repository
public interface CiutatRepository  extends JpaRepository<Ciutat,Long> {
}
```



### Controlador- CiutatController


```java


@RestController
@RequestMapping("/ciutats")
public class CiutatController {

    @Autowired
    CiutatRepository ciutatRepository;

    @GetMapping
    public List<Ciutat> obtenirCiutats() {

        return (List<Ciutat>) ciutatRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ciutat> obtenirCiutatPerId(@PathVariable(value = "id") Long id) {
        Optional<Ciutat> ciutat = ciutatRepository.findById(id);

        if (ciutat.isPresent()) {
            return ResponseEntity.ok().body(ciutat.get());

        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @PostMapping
    public Ciutat crearCiutat(@RequestBody Ciutat ciutat) {
        return ciutatRepository.save(ciutat);

    }

        @DeleteMapping("/{id}")
    public void eliminarCiutat(@PathVariable Long id) {
        ciutatRepository.deleteById(id);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Ciutat> actualitzarCiutat(@PathVariable Long id, @RequestBody Ciutat ciutatActualitzada) {
        Optional<Ciutat> optionalCiutat = ciutatRepository.findById(id);

        if (optionalCiutat.isPresent()) {
            Ciutat ciutat = optionalCiutat.get();
            ciutat.setNom(ciutatActualitzada.getNom());
            ciutat.setPoblacio(ciutatActualitzada.getPoblacio());
            ciutat.setDescripcio(ciutatActualitzada.getDescripcio());
            ciutat.setImatge(ciutatActualitzada.getImatge());

            return ResponseEntity.ok().body(ciutatRepository.save(ciutat));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
```

### Prova de l'API


Una volta creat el projecte, l'executem i provem en el navegador:

- http://localhost:8080

Després connectem el projecte amb **Postman** i inserim dades amb **POST**

![alt text](imatges/postmqan.png)












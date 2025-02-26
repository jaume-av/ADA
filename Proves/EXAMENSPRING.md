
# Introduccion a Spring





### **Preguntes fàcils**  

1. Què és Spring Boot?  
   a) Un llenguatge de programació nou.  
   b) Un framework basat en Java que simplifica el desenvolupament d'aplicacions. ✅  
   c) Un gestor de bases de dades.  
   d) Un sistema operatiu.  

2. Quin d'estos usos és **comú** per a Spring Boot?  
   a) Creació de videojocs en 3D.  
   b) Desenvolupament d'APIs REST. ✅  
   c) Disseny de maquinari.  
   d) Edició de vídeos professionals.  

3. Quin servidor està **integrat per defecte** en Spring Boot?  
   a) Apache HTTP Server.  
   b) Jetty.  
   c) Tomcat. ✅  
   d) Nginx.  

4. Quina d'estes opcions **NO** és una característica principal de Spring Boot?  
   a) Configuració automàtica.  
   b) Servidors integrats.  
   c) Suport a bases de dades SQL i NoSQL.  
   d) Ús exclusiu de JavaScript. ✅  

5. Quin fitxer s'utilitza per a gestionar dependències en un projecte **Maven** amb Spring Boot?  
   a) `settings.gradle`  
   b) `application.properties`  
   c) `pom.xml` ✅  
   d) `server.conf`  

---

### **Preguntes intermèdies**  

6. Quina anotació s'utilitza per definir l'entrada principal d'una aplicació Spring Boot?  
   a) `@SpringApplication`  
   b) `@SpringBootApplication` ✅  
   c) `@SpringService`  
   d) `@SpringMain`  

7. Quin mòdul de Spring permet gestionar **autenticació i autorització**?  
   a) Spring Security ✅  
   b) Spring MVC  
   c) Spring Batch  
   d) Spring Cloud  

8. Quin és l'objectiu principal de **Spring Cloud**?  
   a) Gestionar aplicacions monolítiques.  
   b) Simplificar la persistència de dades.  
   c) Gestionar microserveis i arquitectures distribuïdes. ✅  
   d) Crear interfícies gràfiques d'usuari.  

9. Quina d'estes anotacions s'utilitza per definir una **entitat de base de dades** en Spring Data JPA?  
   a) `@Service`  
   b) `@Entity` ✅  
   c) `@Controller`  
   d) `@Autowired`  

10. Quin d'estos serveis proporciona **Spring Actuator**?  
   a) Generació d'interfícies d'usuari.  
   b) Configuració automàtica d'autenticació.  
   c) Monitorització i mètriques de l'aplicació. ✅  
   d) Creació de bases de dades automàtiques.  

---

### **Preguntes difícils**  

11. Quina funcionalitat proporciona **Spring AOP (Aspect-Oriented Programming)?**  
   a) Creació de controladors REST.  
   b) Modularització de funcionalitats transversals com logs i transaccions. ✅  
   c) Gestió d'autenticació i permisos d'usuari.  
   d) Implementació de serveis de microserveis.  

12. Com es defineix un repositori JPA personalitzat en Spring Data?  
   a) Implementant la interfície `JpaRepository` ✅  
   b) Afegint l'anotació `@RepositoryRest`  
   c) Utilitzant `@EntityManager` sense cap interfície  
   d) Definint una classe `@SpringDataRepository`  

13. Quin mòdul de Spring Boot s'utilitza per a gestionar **processaments per lots**?  
   a) Spring Cloud  
   b) Spring Batch ✅  
   c) Spring MVC  
   d) Spring Data  

14. En una aplicació Spring Boot, quin fitxer s’utilitza normalment per **configurar propietats d’execució**?  
   a) `config.yaml`  
   b) `application.properties` ✅  
   c) `server.xml`  
   d) `settings.gradle`  

15. Quin component de **Spring Cloud** s'utilitza per al registre i descobriment de serveis en una arquitectura de microserveis?  
   a) Spring Config Server  
   b) Eureka ✅  
   c) Spring Gateway  
   d) RabbitMQ  

---





# 2.- Estructura de l'aplicació Spring Boot

Ací tens l'**Examen Test** basat en el text que m'has proporcionat. He seguit els criteris establerts, amb 15 preguntes dividides en 5 fàcils, 5 intermèdies i 5 difícils.

---

### **Preguntes fàcils**  

1. Quina és l'eina recomanada per crear projectes Spring Boot de manera senzilla?  
   a) Spring Initializr ✅  
   b) Spring Launcher  
   c) Spring Generator  
   d) Spring Installer  

2. Quin dels següents sistemes de construcció és **més popular** per gestionar dependències en Spring Boot?  
   a) Makefile  
   b) Ant  
   c) Maven ✅  
   d) NPM  

3. Quina és la funció principal del directori `src/main/java` en un projecte Spring Boot?  
   a) Emmagatzemar la documentació del projecte.  
   b) Contindre el codi font de l'aplicació. ✅  
   c) Definir les dependències del projecte.  
   d) Almacenar configuracions del servidor.  

4. Com es diu el fitxer principal de configuració en un projecte Spring Boot?  
   a) `application.yml`  
   b) `server.config`  
   c) `appsettings.json`  
   d) `application.properties` ✅  

5. Quin mòdul s'utilitza per gestionar **peticions HTTP** en Spring Boot?  
   a) `controller` ✅  
   b) `repository`  
   c) `model`  
   d) `service`  

---

### **Preguntes intermèdies**  

6. Quin llenguatge de programació **NO** és compatible amb Spring Boot?  
   a) Java  
   b) Kotlin  
   c) Groovy  
   d) TypeScript ✅  

7. Quin fitxer de configuració Spring Boot utilitza un format jeràrquic amb indentació?  
   a) `application.json`  
   b) `application.properties`  
   c) `application.yml` ✅  
   d) `config.ini`  

8. Per a què serveix el directori `repository` en l'estructura d'un projecte Spring Boot?  
   a) Per a gestionar l'accés a la base de dades. ✅  
   b) Per a definir la lògica de negoci.  
   c) Per a gestionar les respostes HTTP.  
   d) Per a carregar arxius d'estil CSS.  

9. Quin fitxer s'utilitza per definir **l'esquema d'una base de dades** en un projecte Spring Boot?  
   a) `schema.sql` ✅  
   b) `database.xml`  
   c) `data.json`  
   d) `queries.sql`  

10. Quina de les següents opcions és una dependència comuna en un projecte Spring Boot?  
   a) `Spring Web` ✅  
   b) `Spring CSS`  
   c) `Spring Docker`  
   d) `Spring API`  

---

### **Preguntes difícils**  

11. Quina és la funció del fitxer `pom.xml` en un projecte Spring Boot?  
   a) Configurar el comportament de la base de dades.  
   b) Definir les dependències i la configuració de Maven. ✅  
   c) Gestionar les peticions HTTP.  
   d) Establir les variables d'entorn del servidor.  

12. Quin dels següents **NO** és un directori estàndard en un projecte Spring Boot?  
   a) `src/main/java`  
   b) `src/main/kotlin`  
   c) `src/test/java`  
   d) `src/controllers` ✅  

13. Com es configura **un port personalitzat** en un projecte Spring Boot?  
   a) `server.port=8081` en `application.properties` ✅  
   b) `server_port: 8081` en `server.config`  
   c) `port=8081` en `settings.yml`  
   d) `spring.port=8081` en `config.xml`  

14. Quin fitxer s'utilitza per **carregar dades inicials** en una base de dades Spring Boot?  
   a) `data.sql` ✅  
   b) `load_data.json`  
   c) `init.sql`  
   d) `database.yml`  

15. Quin dels següents elements **no forma part** de l'estructura d'un projecte Spring Boot?  
   a) `controller`  
   b) `service`  
   c) `models` ✅ (El nom correcte és `model`)  
   d) `repository`  

---

# 3.- MVC i Spring Boot



### **Preguntes fàcils**  

1. Quina és la funció del patró Model-Vista-Controlador (MVC)?  
   a) Permetre la connexió amb altres aplicacions externes.  
   b) Separar la lògica de negoci, la vista i la gestió de peticions. ✅  
   c) Accelerar el rendiment d'una base de dades.  
   d) Gestionar la memòria en temps d'execució.  

2. Quin component del patró MVC s'encarrega de **gestionar la interfície d'usuari**?  
   a) Model  
   b) Vista ✅  
   c) Controlador  
   d) Servei  

3. Quin component del patró MVC gestiona les peticions de l'usuari?  
   a) Model  
   b) Vista  
   c) Controlador ✅  
   d) Repositori  

4. Quina anotació s'utilitza per definir un controlador en Spring Boot que **gestiona vistes HTML**?  
   a) `@Service`  
   b) `@Repository`  
   c) `@Controller` ✅  
   d) `@RestController`  

5. Quin d'estos mòduls s'encarrega de gestionar la persistència de dades en Spring Boot?  
   a) `repository` ✅  
   b) `controller`  
   c) `service`  
   d) `view`  

---

### **Preguntes intermèdies**  

6. Quin d'estos avantatges ofereix el patró MVC en Spring Boot?  
   a) Separació de responsabilitats ✅  
   b) Reducció del codi necessari per a configurar Maven  
   c) Eliminació de la necessitat de serveis de base de dades  
   d) Generació automàtica de pàgines HTML  

7. Quin component de Spring Boot facilita la creació de **vistes dinàmiques** en aplicacions MVC?  
   a) JSP  
   b) Thymeleaf ✅  
   c) JPA  
   d) JDBC  

8. Quina és la funció principal d'un **repositori** en el Model de MVC?  
   a) Gestionar l'accés a la base de dades ✅  
   b) Mostrar informació a l'usuari  
   c) Processar la lògica de negoci  
   d) Validar peticions HTTP  

9. Com es defineix una entitat JPA en Spring Boot?  
   a) Amb l'anotació `@Repository`  
   b) Amb l'anotació `@Entity` ✅  
   c) Amb la classe `JdbcTemplate`  
   d) Amb `@Controller`  

10. Quina diferència principal hi ha entre `@Controller` i `@RestController` en Spring Boot?  
   a) `@RestController` s'utilitza per gestionar bases de dades.  
   b) `@Controller` s'utilitza per gestionar APIs REST.  
   c) `@RestController` retorna dades en JSON o XML, mentre que `@Controller` gestiona vistes HTML. ✅  
   d) `@Controller` i `@RestController` són exactament iguals.  

---

### **Preguntes difícils**  

11. Quin és l'ordre correcte del **flux de treball** en una aplicació Spring Boot basada en MVC?  
   a) Vista → Model → Controlador → Servei  
   b) Controlador → Servei → Repositori → Model → Vista ✅  
   c) Servei → Controlador → Vista → Model  
   d) Repositori → Servei → Vista → Controlador  

12. Quin dels següents mètodes **NO** pertany a `JpaRepository` en Spring Boot?  
   a) `findAll()`  
   b) `save()`  
   c) `deleteById()`  
   d) `executeQuery()` ✅  

13. En Spring Boot, quin tipus de petició HTTP s'utilitza per **crear un nou recurs** en una API REST?  
   a) GET  
   b) POST ✅  
   c) DELETE  
   d) PUT  

14. Quin mètode de `JpaRepository` permet fer una cerca filtrant per un camp específic d'una entitat?  
   a) `getByField()`  
   b) `queryByExample()`  
   c) `findByNomContaining()` ✅  
   d) `searchData()`  

15. Quina és la principal funció d'un servei (`@Service`) en una aplicació Spring Boot basada en MVC?  
   a) Mostrar informació a l'usuari.  
   b) Gestionar la lògica de negoci i interactuar amb els repositoris. ✅  
   c) Retornar dades en format JSON.  
   d) Executar consultes directament a la base de dades sense intermediació.  

---

# 4 IOC i DI


---

### **Preguntes fàcils**  

1. Què significa IoC en Spring Boot?  
   a) Internet of Computers  
   b) Inversió de Control ✅  
   c) Implementació orientada a Clases  
   d) Injecció de Components  

2. Quin element principal gestiona els objectes (beans) en Spring Boot?  
   a) El contenidor IoC ✅  
   b) La base de dades  
   c) L'usuari final  
   d) Un servidor extern  

3. Quina anotació indica que una classe és un **Servei** en Spring Boot?  
   a) `@Component`  
   b) `@Repository`  
   c) `@Service` ✅  
   d) `@RestController`  

4. Quina és la funció principal d'un **bean** en Spring Boot?  
   a) Actuar com una entitat de base de dades.  
   b) Ser creat i gestionat pel contenidor IoC. ✅  
   c) Proporcionar una API REST.  
   d) Substituir controladors en Spring MVC.  

5. Quina anotació permet que una classe siga **injectada automàticament** en un component de Spring Boot?  
   a) `@Inject`  
   b) `@Bean`  
   c) `@Autowired` ✅  
   d) `@Persistence`  

---

### **Preguntes intermèdies**  

6. Quin és un dels **principals avantatges** de la Inversió de Control?  
   a) Redueix el coupling entre components ✅  
   b) Augmenta la complexitat del codi  
   c) Permet evitar l’ús d’anotacions en Spring Boot  
   d) Elimina la necessitat de bases de dades  

7. Quina diferència hi ha entre **Inversió de Control (IoC)** i **Injecció de Dependències (DI)**?  
   a) IoC és un principi general, mentre que DI és la seua implementació en Spring Boot. ✅  
   b) DI elimina la necessitat d'IoC.  
   c) IoC i DI són exactament el mateix.  
   d) IoC només s'utilitza en bases de dades.  

8. Quin és l’ordre correcte en la creació i gestió de **beans** en Spring Boot?  
   a) Detecta classes anotades → Crea instàncies de beans → Gestiona dependències ✅  
   b) Crea instàncies de beans → Gestiona dependències → Detecta classes anotades  
   c) Gestiona dependències → Crea instàncies de beans → Detecta classes anotades  
   d) No hi ha un ordre establert  

9. En **IoC**, qui és responsable de gestionar els objectes d’una aplicació?  
   a) El desenvolupador, creant-los manualment amb `new`  
   b) El contenidor IoC de Spring Boot ✅  
   c) La base de dades, que els proporciona automàticament  
   d) L’usuari final a través de peticions HTTP  

10. Quin dels següents components **NO** forma part de la gestió d’IoC en Spring Boot?  
   a) `@Service`  
   b) `@Controller`  
   c) `@Autowired`  
   d) `@Entity` ✅  

---

### **Preguntes difícils**  

11. Quina forma d’**injecció de dependències** és la més recomanada en Spring Boot?  
   a) Injecció per constructor ✅  
   b) Injecció per setter  
   c) Injecció directa en camps  
   d) Injecció mitjançant una classe estàtica  

12. Per què la injecció de dependències per **setter** pot no ser recomanable?  
   a) Perquè fa que les dependències siguen opcionals, i poden no estar inicialitzades correctament. ✅  
   b) Perquè no permet l’ús de `@Autowired`.  
   c) Perquè no es pot utilitzar en serveis.  
   d) Perquè és incompatible amb repositoris JPA.  

13. Quan Spring Boot detecta una classe amb `@Repository`, què fa automàticament?  
   a) La registra com un **bean** i permet que siga injectada ✅  
   b) La converteix en una entitat de base de dades  
   c) La fa accessible a través d’un API REST  
   d) La descarta si no té un constructor definit  

14. Quin dels següents **NO** és un avantatge de la Injecció de Dependències?  
   a) Facilita la reutilització del codi  
   b) Redueix el coupling entre classes  
   c) Permet modificar dependències sense canviar el codi  
   d) Fa que el codi siga més difícil de provar ✅  

15. Què fa exactament el mètode `SpringApplication.run(Aplicacio.class, args);` en Spring Boot?  
   a) Executa el servidor i la base de dades  
   b) Inicialitza el contenidor IoC i gestiona els beans ✅  
   c) Defineix automàticament les rutes de l’API REST  
   d) Configura la connexió a la base de dades  

---

# 5 JPA i Spring Data

---


### **Preguntes fàcils**  

1. Què significa JPA?  
   a) Java Programming Architecture  
   b) Java Persistence API ✅  
   c) Java Process Automation  
   d) Java Parallel Application  

2. Quin és l’objectiu principal de JPA?  
   a) Gestionar la persistència de dades en aplicacions Java ✅  
   b) Crear interfícies gràfiques d'usuari  
   c) Administrar la memòria de l'aplicació  
   d) Millorar el rendiment dels servidors web  

3. Quina de les següents **NO** és una implementació de JPA?  
   a) Hibernate  
   b) EclipseLink  
   c) Spring Data JPA  
   d) Apache Tomcat ✅  

4. Quina anotació s'utilitza per marcar una classe com una entitat JPA?  
   a) `@Table`  
   b) `@Column`  
   c) `@Entity` ✅  
   d) `@JoinTable`  

5. Si una classe està anotada amb `@Entity`, quin requisit ha de complir obligatòriament?  
   a) Ha de tindre un camp amb `@Id` ✅  
   b) Ha de tindre almenys una relació `@OneToMany`  
   c) Ha de definir un constructor sense paràmetres  
   d) Ha de tindre una anotació `@Table`  

6. Quina anotació defineix la clau primària d'una entitat JPA?  
   a) `@PrimaryKey`  
   b) `@Column`  
   c) `@Id` ✅  
   d) `@Table`  

7. Si no es defineix `@Table(name = "nom_taula")`, quin nom agafa per defecte la taula en la base de dades?  
   a) El nom del paquet de la classe  
   b) El nom de la base de dades  
   c) El nom de la classe ✅  
   d) JPA genera un nom aleatori  

8. Quin paràmetre de `@Column` evita que un camp permeta valors nuls?  
   a) `nullable = false` ✅  
   b) `notNull = true`  
   c) `unique = false`  
   d) `required = true`  

9. Quina anotació es fa servir per a definir una relació **un a un** en JPA?  
   a) `@OneToOne` ✅  
   b) `@OneToMany`  
   c) `@ManyToOne`  
   d) `@ManyToMany`  

10. Quina anotació es fa servir per a definir una **clau forana** en una entitat JPA?  
   a) `@JoinColumn` ✅  
   b) `@ForeignKey`  
   c) `@PrimaryKey`  
   d) `@TableJoin`  

---

### **Preguntes intermèdies**  

11. Quina anotació permet definir el nom d’una taula diferent al de la classe en JPA?  
   a) `@Entity(name = "nom_taula")`  
   b) `@Table(name = "nom_taula")` ✅  
   c) `@Schema(name = "nom_taula")`  
   d) `@DbTable(name = "nom_taula")`  

12. Quin paràmetre de `@Column` s’utilitza per establir un valor únic en una columna?  
   a) `nullable = false`  
   b) `unique = true` ✅  
   c) `primary = true`  
   d) `defaultValue = true`  

13. Com es poden definir restriccions d’unicitat sobre múltiples columnes?  
   a) `@Table(uniqueConstraints = { @UniqueConstraint(columnNames = {"col1", "col2"}) })` ✅  
   b) `@Column(unique = {"col1", "col2"})`  
   c) `@Entity(unique = {"col1", "col2"})`  
   d) `@Unique(columnNames = {"col1", "col2"})`  

14. Quina anotació s'utilitza per definir una estratègia de generació automàtica d'IDs en JPA?  
   a) `@Id`  
   b) `@GeneratedValue` ✅  
   c) `@AutoIncrement`  
   d) `@KeyGeneration`  

15. Quin valor de `GenerationType` utilitza una **seqüència SQL** per generar IDs?  
   a) `AUTO`  
   b) `IDENTITY`  
   c) `SEQUENCE` ✅  
   d) `TABLE`  

16. Quina anotació evita que un atribut d'una entitat siga guardat en la base de dades?  
   a) `@Ignore`  
   b) `@Transient` ✅  
   c) `@NotPersisted`  
   d) `@HiddenColumn`  

17. En una relació **un a molts**, quina entitat conté la clau forana?  
   a) L'entitat amb `@OneToMany`  
   b) L'entitat amb `@ManyToOne` ✅  
   c) La taula intermèdia  
   d) La base de dades decideix automàticament  

18. Com es defineix una relació **molts a molts** en JPA?  
   a) `@OneToOne`  
   b) `@OneToMany`  
   c) `@ManyToMany` ✅  
   d) `@JoinColumn`  

19. Quin dels següents **NO** és un tipus de `CascadeType` en JPA?  
   a) `ALL`  
   b) `REMOVE`  
   c) `MERGE`  
   d) `AUTOLOAD` ✅  

20. Què fa el paràmetre `fetch = FetchType.LAZY` en una relació?  
   a) Carrega les dades immediatament  
   b) Retrassa la càrrega de les dades fins que siga necessària ✅  
   c) Elimina les dades quan no es fan servir  
   d) Evita la creació de clau forana  

---

### **Preguntes difícils**  

21. Quin dels següents valors de `GenerationType` és el **menys eficient**?  
   a) `AUTO`  
   b) `IDENTITY`  
   c) `SEQUENCE`  
   d) `TABLE` ✅  

22. Quin dels següents valors **NO** és vàlid per `CascadeType` en JPA?  
   a) `CascadeType.REFRESH`  
   b) `CascadeType.PERSIST`  
   c) `CascadeType.FETCH` ✅  
   d) `CascadeType.DETACH`  

23. Quina és la diferència principal entre `FetchType.LAZY` i `FetchType.EAGER`?  
   a) `LAZY` carrega dades de manera immediata i `EAGER` ho fa en segon pla.  
   b) `EAGER` carrega les dades immediatament i `LAZY` només quan són necessàries. ✅  
   c) `LAZY` evita la creació de clau forana.  
   d) `EAGER` només funciona amb relacions `@ManyToMany`.  

24. En una relació **bidireccional** `@OneToMany`, com s’especifica la part inversa?  
   a) `@MappedBy` ✅  
   b) `@InverseJoin`  
   c) `@ForeignKey`  
   d) `@Bidirectional`  

25. Com es defineix una **clau composta** en JPA?  
   a) `@PrimaryKey`  
   b) `@CompositeId`  
   c) `@EmbeddedId` ✅  
   d) `@UniqueKey`  

26. Quina diferència hi ha entre `@JoinColumn` i `@JoinTable`?  
   a) `@JoinColumn` defineix una clau forana, mentre que `@JoinTable` es fa servir per relacions `@ManyToMany`. ✅  
   b) `@JoinColumn` només funciona en relacions `@OneToOne`.  
   c) `@JoinTable` crea una vista a la base de dades.  
   d) No hi ha cap diferència.  

27. Quin d'estos **NO** és un tipus de relació en JPA?  
   a) `@OneToOne`  
   b) `@OneToMany`  
   c) `@ForeignKey` ✅  
   d) `@ManyToMany`  

28. Com es pot definir un camp de tipus **BLOB** en JPA?  
   a) `@Lob` ✅  
   b) `@BlobColumn`  
   c) `@BinaryData`  
   d) `@FileColumn`  

29. Quin tipus de `CascadeType` elimina automàticament els fills quan s’elimina el pare?  
   a) `PERSIST`  
   b) `REMOVE` ✅  
   c) `DETACH`  
   d) `MERGE`  

30. Quin tipus de relació requereix una taula intermèdia?  
   a) `@ManyToMany` ✅  
   b) `@OneToOne`  
   c) `@OneToMany`  
   d) `@ManyToOne`  

---

# 6 REPOSITORIS



### **Preguntes fàcils**  

1. Quin és l'objectiu principal d'un repositori en Spring Boot?  
   a) Gestionar la persistència de dades ✅  
   b) Gestionar les vistes de l'aplicació  
   c) Generar codi HTML automàticament  
   d) Gestionar la memòria de l'aplicació  

2. Quina interfície de Spring Data JPA proporciona funcionalitats avançades de persistència?  
   a) `JpaRepository` ✅  
   b) `RepositoryManager`  
   c) `SpringDataRepository`  
   d) `DatabaseManager`  

3. Quin d'aquests **NO** és un benefici de Spring Data JPA?  
   a) Simplificació de consultes SQL  
   b) Generació automàtica d'implementacions  
   c) Eliminació de la necessitat d'una base de dades ✅  
   d) Separació entre lògica de negoci i accés a dades  

4. Quina anotació marca una classe com una **entitat JPA**?  
   a) `@Table`  
   b) `@Entity` ✅  
   c) `@Repository`  
   d) `@Service`  

5. Quina operació **NO** és proporcionada per defecte per `JpaRepository`?  
   a) `findAll()`  
   b) `save()`  
   c) `deleteById()`  
   d) `runQuery()` ✅  

6. Com es declara un repositori per a una entitat **Ciutat**?  
   a) `public interface CiutatRepository extends JpaRepository<Ciutat, Long> {}` ✅  
   b) `public class CiutatRepository implements JpaRepository<Ciutat, Long> {}`  
   c) `public interface CiutatRepository extends CrudManager<Ciutat, Long> {}`  
   d) `public class CiutatRepository implements Repository<Ciutat, Long> {}`  

7. Quin tipus de dades ha de coincidir amb el segon paràmetre de `JpaRepository<T, ID>`?  
   a) El tipus de la clau primària de l'entitat ✅  
   b) Un objecte Java qualsevol  
   c) Un array de tipus String  
   d) Una llista de registres de la base de dades  

8. Quin mètode s'utilitza per **eliminar una entitat** pel seu identificador?  
   a) `removeById(Long id)`  
   b) `deleteById(Long id)` ✅  
   c) `eraseById(Long id)`  
   d) `dropEntityById(Long id)`  

9. Quin mètode comprova si existeix una entitat amb un ID determinat?  
   a) `existsById(Long id)` ✅  
   b) `checkIfExists(Long id)`  
   c) `findIfExists(Long id)`  
   d) `entityExists(Long id)`  

10. Quina interfície és la **més bàsica** per gestionar operacions CRUD en Spring Boot?  
   a) `JpaRepository`  
   b) `CrudRepository` ✅  
   c) `PagingAndSortingRepository`  
   d) `SpringDataManager`  

---

### **Preguntes intermèdies**  

11. Quin mètode s'utilitza per **guardar o actualitzar una entitat** en Spring Boot?  
   a) `store(T entity)`  
   b) `save(T entity)` ✅  
   c) `merge(T entity)`  
   d) `persist(T entity)`  

12. Quina diferència hi ha entre `CrudRepository` i `JpaRepository`?  
   a) `JpaRepository` afegeix funcionalitats avançades com paginació i ordenació ✅  
   b) `CrudRepository` només es pot utilitzar amb MySQL  
   c) `JpaRepository` és una versió antiga de `CrudRepository`  
   d) No hi ha cap diferència entre ambdues  

13. Com es poden definir consultes personalitzades en un repositori?  
   a) Utilitzant noms de mètodes específics  
   b) Amb anotacions `@Query`  
   c) Amb SQL natiu  
   d) Totes les anteriors ✅  

14. Quin d'aquests **NO** és un prefix reconegut per definir mètodes de cerca automàtica?  
   a) `findBy`  
   b) `getBy`  
   c) `searchBy` ✅  
   d) `readBy`  

15. Quina anotació permet escriure consultes personalitzades en JPQL dins d'un repositori?  
   a) `@JPQL`  
   b) `@Query` ✅  
   c) `@SQLQuery`  
   d) `@NativeQuery`  

16. Quina consulta genera el mètode `findByNomContaining(String fragment)`?  
   a) `SELECT * FROM ciutat WHERE nom LIKE %?%` ✅  
   b) `SELECT * FROM ciutat WHERE nom = ?`  
   c) `SELECT * FROM ciutat WHERE nom CONTAINS ?`  
   d) `SELECT * FROM ciutat WHERE fragment LIKE nom`  

17. Quin mètode s'utilitza per **comptar** el nombre d'entitats en una taula?  
   a) `countByNom(String nom)`  
   b) `count()` ✅  
   c) `size()`  
   d) `totalCount()`  

18. Quina consulta genera `findByNomAndPais(String nom, String pais)`?  
   a) `SELECT * FROM ciutat WHERE nom = ? AND pais = ?` ✅  
   b) `SELECT * FROM ciutat WHERE nom OR pais = ?`  
   c) `SELECT * FROM ciutat WHERE nom LIKE pais`  
   d) `SELECT * FROM ciutat ORDER BY pais`  

19. Quin mètode retorna una entitat **opcional** en cas de no existir?  
   a) `getById(Long id)`  
   b) `optionalFindById(Long id)`  
   c) `findById(Long id)` ✅  
   d) `searchById(Long id)`  

20. Quina interfície permet gestionar paginació i ordenació de resultats?  
   a) `PagingAndSortingRepository` ✅  
   b) `CrudRepository`  
   c) `JpaRepository`  
   d) `SpringDataPaginator`  

---

### **Preguntes difícils**  

21. Quin dels següents **NO** és un mètode de `JpaRepository`?  
   a) `findAll(Sort sort)`  
   b) `deleteInBatch(Iterable<T> entities)`  
   c) `flush()`  
   d) `runTransaction()` ✅  

22. Quin dels següents mètodes **NO** existeix en Spring Data JPA?  
   a) `saveAndFlush(T entity)`  
   b) `deleteAllInBatch()`  
   c) `removeAllEntities()` ✅  
   d) `existsById(ID id)`  

23. Quina diferència hi ha entre `findAll()` i `findAll(Sort sort)`?  
   a) `findAll(Sort sort)` permet ordenar els resultats ✅  
   b) `findAll()` filtra els resultats per condició  
   c) `findAll(Sort sort)` és més ràpid  
   d) No hi ha cap diferència  

24. Per què es fan servir **anotacions `@Param` en `@Query`**?  
   a) Per evitar SQL injectat ✅  
   b) Per millorar la velocitat de les consultes  
   c) Per permetre l'ús de JSON  
   d) Per eliminar la necessitat d'un repositori  

25. Quin dels següents **NO** és un tipus de repositori a Spring Data?  
   a) `JpaRepository`  
   b) `CrudRepository`  
   c) `SortingRepository` ✅  
   d) `PagingAndSortingRepository`  

26. Quina interfície permet paginació **i** ordenació?  
   a) `CrudRepository`  
   b) `JpaRepository`  
   c) `PagingAndSortingRepository` ✅  
   d) `SpringPaginator`  

27. Què retorna `findAll(Pageable pageable)`?  
   a) `List<T>`  
   b) `Page<T>` ✅  
   c) `Optional<T>`  
   d) `Set<T>`  

28. Quin dels següents **NO** és un avantatge dels repositoris en Spring Boot?  
   a) Separació de lògica de negoci  
   b) Generació de consultes automàtiques  
   c) Accés directe a SQL sense ORM ✅  
   d) Integració amb JPQL  

29. Quina anotació s’utilitza per definir consultes **SQL natives** en un repositori?  
   a) `@Query(nativeQuery = true)` ✅  
   b) `@NativeSQL`  
   c) `@JPQLQuery`  
   d) `@EntityManagerQuery`  

30. Quin tipus de dades retorna `count()` en un repositori?  
   a) `long` ✅  
   b) `int`  
   c) `boolean`  
   d) `float`  

---

# 7 SERVEIS



### **Preguntes fàcils**  

1. Quin és l'objectiu principal d'un repositori en Spring Boot?  
   a) Gestionar la persistència de dades ✅  
   b) Gestionar les vistes de l'aplicació  
   c) Generar codi HTML automàticament  
   d) Gestionar la memòria de l'aplicació  

2. Quina interfície de Spring Data JPA proporciona funcionalitats avançades de persistència?  
   a) `JpaRepository` ✅  
   b) `RepositoryManager`  
   c) `SpringDataRepository`  
   d) `DatabaseManager`  

3. Quin d'aquests **NO** és un benefici de Spring Data JPA?  
   a) Simplificació de consultes SQL  
   b) Generació automàtica d'implementacions  
   c) Eliminació de la necessitat d'una base de dades ✅  
   d) Separació entre lògica de negoci i accés a dades  

4. Quina anotació marca una classe com una **entitat JPA**?  
   a) `@Table`  
   b) `@Entity` ✅  
   c) `@Repository`  
   d) `@Service`  

5. Quina operació **NO** és proporcionada per defecte per `JpaRepository`?  
   a) `findAll()`  
   b) `save()`  
   c) `deleteById()`  
   d) `runQuery()` ✅  

6. Com es declara un repositori per a una entitat **Ciutat**?  
   a) `public interface CiutatRepository extends JpaRepository<Ciutat, Long> {}` ✅  
   b) `public class CiutatRepository implements JpaRepository<Ciutat, Long> {}`  
   c) `public interface CiutatRepository extends CrudManager<Ciutat, Long> {}`  
   d) `public class CiutatRepository implements Repository<Ciutat, Long> {}`  

7. Quin tipus de dades ha de coincidir amb el segon paràmetre de `JpaRepository<T, ID>`?  
   a) El tipus de la clau primària de l'entitat ✅  
   b) Un objecte Java qualsevol  
   c) Un array de tipus String  
   d) Una llista de registres de la base de dades  

8. Quin mètode s'utilitza per **eliminar una entitat** pel seu identificador?  
   a) `removeById(Long id)`  
   b) `deleteById(Long id)` ✅  
   c) `eraseById(Long id)`  
   d) `dropEntityById(Long id)`  

9. Quin mètode comprova si existeix una entitat amb un ID determinat?  
   a) `existsById(Long id)` ✅  
   b) `checkIfExists(Long id)`  
   c) `findIfExists(Long id)`  
   d) `entityExists(Long id)`  

10. Quina interfície és la **més bàsica** per gestionar operacions CRUD en Spring Boot?  
   a) `JpaRepository`  
   b) `CrudRepository` ✅  
   c) `PagingAndSortingRepository`  
   d) `SpringDataManager`  

---

### **Preguntes intermèdies**  

11. Quin mètode s'utilitza per **guardar o actualitzar una entitat** en Spring Boot?  
   a) `store(T entity)`  
   b) `save(T entity)` ✅  
   c) `merge(T entity)`  
   d) `persist(T entity)`  

12. Quina diferència hi ha entre `CrudRepository` i `JpaRepository`?  
   a) `JpaRepository` afegeix funcionalitats avançades com paginació i ordenació ✅  
   b) `CrudRepository` només es pot utilitzar amb MySQL  
   c) `JpaRepository` és una versió antiga de `CrudRepository`  
   d) No hi ha cap diferència entre ambdues  

13. Com es poden definir consultes personalitzades en un repositori?  
   a) Utilitzant noms de mètodes específics  
   b) Amb anotacions `@Query`  
   c) Amb SQL natiu  
   d) Totes les anteriors ✅  

14. Quin d'aquests **NO** és un prefix reconegut per definir mètodes de cerca automàtica?  
   a) `findBy`  
   b) `getBy`  
   c) `searchBy` ✅  
   d) `readBy`  

15. Quina anotació permet escriure consultes personalitzades en JPQL dins d'un repositori?  
   a) `@JPQL`  
   b) `@Query` ✅  
   c) `@SQLQuery`  
   d) `@NativeQuery`  

16. Quina consulta genera el mètode `findByNomContaining(String fragment)`?  
   a) `SELECT * FROM ciutat WHERE nom LIKE %?%` ✅  
   b) `SELECT * FROM ciutat WHERE nom = ?`  
   c) `SELECT * FROM ciutat WHERE nom CONTAINS ?`  
   d) `SELECT * FROM ciutat WHERE fragment LIKE nom`  

17. Quin mètode s'utilitza per **comptar** el nombre d'entitats en una taula?  
   a) `countByNom(String nom)`  
   b) `count()` ✅  
   c) `size()`  
   d) `totalCount()`  

18. Quina consulta genera `findByNomAndPais(String nom, String pais)`?  
   a) `SELECT * FROM ciutat WHERE nom = ? AND pais = ?` ✅  
   b) `SELECT * FROM ciutat WHERE nom OR pais = ?`  
   c) `SELECT * FROM ciutat WHERE nom LIKE pais`  
   d) `SELECT * FROM ciutat ORDER BY pais`  

19. Quin mètode retorna una entitat **opcional** en cas de no existir?  
   a) `getById(Long id)`  
   b) `optionalFindById(Long id)`  
   c) `findById(Long id)` ✅  
   d) `searchById(Long id)`  

20. Quina interfície permet gestionar paginació i ordenació de resultats?  
   a) `PagingAndSortingRepository` ✅  
   b) `CrudRepository`  
   c) `JpaRepository`  
   d) `SpringDataPaginator`  

---

### **Preguntes difícils**  

21. Quin dels següents **NO** és un mètode de `JpaRepository`?  
   a) `findAll(Sort sort)`  
   b) `deleteInBatch(Iterable<T> entities)`  
   c) `flush()`  
   d) `runTransaction()` ✅  

22. Quin dels següents mètodes **NO** existeix en Spring Data JPA?  
   a) `saveAndFlush(T entity)`  
   b) `deleteAllInBatch()`  
   c) `removeAllEntities()` ✅  
   d) `existsById(ID id)`  

23. Quina diferència hi ha entre `findAll()` i `findAll(Sort sort)`?  
   a) `findAll(Sort sort)` permet ordenar els resultats ✅  
   b) `findAll()` filtra els resultats per condició  
   c) `findAll(Sort sort)` és més ràpid  
   d) No hi ha cap diferència  

24. Per què es fan servir **anotacions `@Param` en `@Query`**?  
   a) Per evitar SQL injectat ✅  
   b) Per millorar la velocitat de les consultes  
   c) Per permetre l'ús de JSON  
   d) Per eliminar la necessitat d'un repositori  

25. Quin dels següents **NO** és un tipus de repositori a Spring Data?  
   a) `JpaRepository`  
   b) `CrudRepository`  
   c) `SortingRepository` ✅  
   d) `PagingAndSortingRepository`  

26. Quina interfície permet paginació **i** ordenació?  
   a) `CrudRepository`  
   b) `JpaRepository`  
   c) `PagingAndSortingRepository` ✅  
   d) `SpringPaginator`  

27. Què retorna `findAll(Pageable pageable)`?  
   a) `List<T>`  
   b) `Page<T>` ✅  
   c) `Optional<T>`  
   d) `Set<T>`  

28. Quin dels següents **NO** és un avantatge dels repositoris en Spring Boot?  
   a) Separació de lògica de negoci  
   b) Generació de consultes automàtiques  
   c) Accés directe a SQL sense ORM ✅  
   d) Integració amb JPQL  

29. Quina anotació s’utilitza per definir consultes **SQL natives** en un repositori?  
   a) `@Query(nativeQuery = true)` ✅  
   b) `@NativeSQL`  
   c) `@JPQLQuery`  
   d) `@EntityManagerQuery`  

30. Quin tipus de dades retorna `count()` en un repositori?  
   a) `long` ✅  
   b) `int`  
   c) `boolean`  
   d) `float`  

---

# 8 CONTROLADORS

*.

---

### **Preguntes fàcils**  

1. Quina és la funció principal d'un **controlador** en Spring Boot?  
   a) Gestionar les peticions HTTP ✅  
   b) Accedir directament a la base de dades  
   c) Generar entitats JPA  
   d) Controlar la memòria de l'aplicació  

2. Quina anotació s'utilitza per definir un **controlador MVC**?  
   a) `@RestController`  
   b) `@Controller` ✅  
   c) `@Service`  
   d) `@Repository`  

3. Quina diferència hi ha entre `@RestController` i `@Controller`?  
   a) `@RestController` retorna JSON i `@Controller` retorna HTML ✅  
   b) `@RestController` només gestiona peticions GET  
   c) `@Controller` no permet fer peticions HTTP  
   d) No hi ha cap diferència entre ambdues  

4. Quina anotació s'utilitza per definir una **ruta HTTP GET** en un controlador?  
   a) `@PostMapping`  
   b) `@PutMapping`  
   c) `@GetMapping` ✅  
   d) `@DeleteMapping`  

5. Quin dels següents **NO** és un tipus de controlador en Spring Boot?  
   a) Controlador REST  
   b) Controlador MVC  
   c) Controlador SOAP ✅  
   d) Controlador GraphQL  

6. Quina anotació permet definir una **ruta base** per a totes les peticions d'un controlador?  
   a) `@PathVariable`  
   b) `@RequestParam`  
   c) `@RequestMapping` ✅  
   d) `@RestController`  

7. Quina anotació permet obtenir **paràmetres de consulta** des d'una URL com `/ciutats?poblacioMinima=50000`?  
   a) `@RequestParam` ✅  
   b) `@PathVariable`  
   c) `@Autowired`  
   d) `@JsonProperty`  

8. Quin mètode HTTP s’utilitza per **actualitzar** un recurs en una API REST?  
   a) GET  
   b) POST  
   c) PUT ✅  
   d) DELETE  

9. Quin tipus de resposta retorna per defecte un `@RestController`?  
   a) Un fitxer XML  
   b) Un objecte serialitzat en JSON ✅  
   c) Una pàgina HTML  
   d) Un arxiu PDF  

10. Quin framework de Spring Boot s'utilitza per renderitzar vistes HTML en controladors MVC?  
   a) Thymeleaf ✅  
   b) JPA  
   c) React  
   d) GraphQL  

---

### **Preguntes intermèdies**  

11. Com es defineix un **endpoint POST** en un `@RestController`?  
   a) `@GetMapping("/afegir")`  
   b) `@PostMapping("/afegir")` ✅  
   c) `@RequestMapping("/afegir")`  
   d) `@DeleteMapping("/afegir")`  

12. Quin objecte s’utilitza per passar dades des del controlador a la plantilla HTML en un controlador MVC?  
   a) `@RequestParam`  
   b) `Model` ✅  
   c) `@Autowired`  
   d) `@ResponseBody`  

13. Quin dels següents **NO** és un avantatge dels controladors en Spring Boot?  
   a) Separació de responsabilitats  
   b) Gestió de peticions HTTP  
   c) Accés directe a la base de dades sense repositoris ✅  
   d) Suport per a JSON i XML  

14. Quin és el format més comú per a les respostes d'un controlador REST?  
   a) CSV  
   b) YAML  
   c) JSON ✅  
   d) HTML  

15. Quina anotació permet **injectar dependències** en un controlador?  
   a) `@InjectBean`  
   b) `@Autowired` ✅  
   c) `@Component`  
   d) `@RestMapping`  

16. Com s’indica que un paràmetre ha de vindre **com a part de la URL** en una API REST?  
   a) `@RequestParam`  
   b) `@PathVariable` ✅  
   c) `@QueryString`  
   d) `@GetMappingVariable`  

17. Com es retorna una resposta **HTML** en un controlador MVC?  
   a) Retornant una cadena amb el nom de la plantilla ✅  
   b) Retornant JSON directament  
   c) Utilitzant `@ResponseBody`  
   d) Cridant un servei que generi HTML dinàmic  

18. Quina és la manera recomanada per estructurar controladors en una aplicació escalable?  
   a) Cridar directament als repositoris  
   b) Separar en capes **Controller -> Service -> Repository** ✅  
   c) Crear un únic controlador amb tota la lògica  
   d) Evitar l’ús de serveis  

19. Com s’anomena la pràctica de **separar dades i lògica** en capes en una API REST?  
   a) MVC ✅  
   b) JSON Binding  
   c) Serialització  
   d) WebSockets  

20. Per a què serveix `@ResponseBody` en un controlador Spring Boot?  
   a) Per indicar que una classe és un controlador  
   b) Per injectar un servei dins del controlador  
   c) Per retornar directament JSON sense usar un motor de plantilles ✅  
   d) Per especificar la URL base d’un controlador  

---

### **Preguntes difícils**  

21. Quin dels següents **NO** és un benefici de separar controladors, serveis i repositoris?  
   a) Millora la modularitat  
   b) Facilita la reutilització del codi  
   c) Evita l’ús de bases de dades ✅  
   d) Facilita les proves unitàries  

22. Quina és la manera més segura d'exposar dades a través d'una API REST?  
   a) Retornar directament les entitats JPA  
   b) Utilitzar DTOs per encapsular les dades ✅  
   c) Exposar els repositoris directament  
   d) Utilitzar XML en lloc de JSON  

23. Quina anotació s'utilitza per **definir una API REST en un controlador**?  
   a) `@Controller`  
   b) `@RestController` ✅  
   c) `@Service`  
   d) `@RestService`  

24. Quina diferència hi ha entre `@PathVariable` i `@RequestParam`?  
   a) `@PathVariable` es troba dins de la URL i `@RequestParam` en la query string ✅  
   b) No hi ha cap diferència  
   c) `@RequestParam` només es pot utilitzar en peticions POST  
   d) `@PathVariable` només es pot utilitzar en peticions GET  

25. Quin patró de disseny segueixen els controladors MVC en Spring Boot?  
   a) Repository Pattern  
   b) Singleton  
   c) Model-View-Controller (MVC) ✅  
   d) Facade  

26. Quin tipus de dades retorna un `@RestController` si no s’especifica res?  
   a) HTML  
   b) JSON ✅  
   c) XML  
   d) CSV  

27. Com es pot restringir l’accés a un endpoint en un controlador REST?  
   a) Utilitzant `@RestMapping`  
   b) Configurant **Spring Security** ✅  
   c) Afegint `@ProtectedEndpoint`  
   d) Utilitzant `@RequestSecure`  

28. Quina és la millor pràctica per gestionar errors en un controlador REST?  
   a) Retornar missatges d’error genèrics  
   b) Usar `@ExceptionHandler` per gestionar excepcions ✅  
   c) Retornar una cadena de text amb "Error"  
   d) No capturar excepcions, que el client gestione els errors  

29. Què fa `@CrossOrigin` en un controlador REST?  
   a) Habilita el suport per WebSockets  
   b) Permet peticions des d'altres dominis ✅  
   c) Defineix una API REST segura  
   d) Activa la compressió de les respostes  

30. Com s'estructura un controlador REST recomanat en una aplicació Spring Boot?  
   a) Controlador -> Service -> Repository ✅  
   b) Controlador -> Repository -> Service  
   c) Service -> Controller -> Repository  
   d) Repository -> Controller -> Service  

---



# 9 Builder i DTO


### **Preguntes**  

1. Quin és l’objectiu principal del **patró Builder**?  
   a) Crear objectes complexos pas a pas ✅  
   b) Evitar l’ús d’objectes Java  
   c) Reduir la quantitat de classes en una aplicació  
   d) Substituir les entitats JPA  

2. Quin dels següents components **no** forma part del patró Builder?  
   a) Builder  
   b) Director  
   c) Factory ✅  
   d) Producte  

3. En el patró Builder, quin component **controla el procés de construcció** de l’objecte?  
   a) Producte  
   b) Builder  
   c) Director ✅  
   d) Factory  

4. Quin benefici aporta el patró Builder en comparació amb constructors tradicionals?  
   a) Evita la creació d’objectes  
   b) Permet crear objectes amb molts atributs de manera clara ✅  
   c) Elimina la necessitat de getters i setters  
   d) No permet utilitzar mètodes encadenats  

5. Quin és el propòsit de la classe interna `Builder` dins d’una entitat com `Persona`?  
   a) Definir atributs constants  
   b) Facilitar la construcció d’objectes de manera flexible ✅  
   c) Eliminar la necessitat d’instanciar objectes  
   d) Reduir la memòria usada per la JVM  

6. Com es crea una instància d’un objecte amb el patró Builder?  
   a) `new Persona("Joan", "Garcia", 30)`  
   b) `Persona persona = new Persona.Builder("Joan", "Garcia", 30).build();` ✅  
   c) `Persona persona = Persona.builder().build();`  
   d) `Persona persona = BuilderPersona.create();`  

7. En el patró Builder, què significa que els mètodes del Builder **retornen `this`**?  
   a) Evita errors de compilació  
   b) Redueix la mida de l’objecte creat  
   c) Permet encadenar mètodes en la creació de l’objecte ✅  
   d) Fa que l’objecte siga immutable  

8. Quin d’aquests conceptes descriu millor el **patró DTO**?  
   a) Optimitza l’accés a bases de dades  
   b) Separa la lògica de negoci del model de dades  
   c) Facilita la transferència de dades entre capes d’una aplicació ✅  
   d) Redueix la necessitat de controladors  

9. Quin és un **avantatge principal** d’utilitzar DTOs en una API REST?  
   a) Evita exposar directament les entitats JPA ✅  
   b) Redueix la seguretat de l’aplicació  
   c) Elimina la necessitat d’usuaris autenticats  
   d) Permet modificar directament les taules de la base de dades  

10. Quina de les següents classes s’utilitza per convertir una entitat JPA en un DTO?  
   a) `EntityMapper`  
   b) `DTOConverter`  
   c) `Assembler` ✅  
   d) `EntityDTO`  

11. Com es passa un objecte DTO a un controlador en una API REST de Spring Boot?  
   a) Com un atribut d’una sessió  
   b) Com a paràmetre en un mètode amb `@RequestBody` ✅  
   c) Mitjançant una variable global  
   d) Utilitzant `@EntityDTO`  

12. Quin d’aquests és un **desavantatge** d’utilitzar DTOs?  
   a) Augmenta la modularitat  
   b) Requereix crear classes addicionals ✅  
   c) Redueix la seguretat de l’aplicació  
   d) Impedeix la reutilització de dades  

13. En una API REST, com s’evita que un DTO continga dades no vàlides?  
   a) Implementant el patró Singleton  
   b) Afegint validacions amb anotacions com `@NotBlank` i `@Min` ✅  
   c) Creant entitats sense atributs  
   d) Evitant l’ús de classes DTO  

14. Quin seria el millor escenari per utilitzar DTOs en Spring Boot?  
   a) Quan volem mostrar directament els objectes JPA en la vista  
   b) Quan necessitem separar les entitats de la base de dades del que es retorna en una API ✅  
   c) Quan volem eliminar el model de dades de l’aplicació  
   d) Quan treballem amb una base de dades sense relacions  

15. En el patró DTO, què representa un **Assembler**?  
   a) Una classe que gestiona la persistència d’entitats  
   b) Una interfície que defineix una entitat JPA  
   c) Una classe que converteix entre entitats i DTOs ✅  
   d) Un objecte immutable que encapsula dades  

---









# 10 Spring security


---

### **Preguntes**  

1. Quina funcionalitat principal ofereix **Spring Security** en una aplicació Spring Boot?  
   a) Accés a bases de dades  
   b) Protecció contra atacs i gestió d'autenticació ✅  
   c) Optimització del rendiment  
   d) Renderització de vistes  

2. Quina dependència és necessària per habilitar Spring Security en un projecte Spring Boot?  
   a) `spring-boot-starter-data-jpa`  
   b) `spring-boot-starter-web`  
   c) `spring-boot-starter-security` ✅  
   d) `spring-boot-starter-thymeleaf`  

3. Quin usuari s’utilitza per defecte quan s’instal·la Spring Security sense configuració addicional?  
   a) admin  
   b) user ✅  
   c) root  
   d) security  

4. On es pot trobar la contrasenya generada per defecte per Spring Security?  
   a) Al fitxer `application.properties`  
   b) En la base de dades H2  
   c) A la consola durant l'execució ✅  
   d) A un fitxer `security.log`  

5. Quina és la manera més senzilla de definir un usuari personalitzat a Spring Security?  
   a) A `application.properties` ✅  
   b) Creant un `UserService`  
   c) Definint-lo manualment a `security.xml`  
   d) Afegint-lo a `spring-users.yml`  

6. Quines són les dues taules bàsiques que utilitza Spring Security en **JDBC Authentication**?  
   a) users i roles  
   b) users i authorities ✅  
   c) credentials i permissions  
   d) accounts i grants  

7. Com es defineix un camp com a **clau primària** en una entitat d’usuari a Spring Security?  
   a) `@PrimaryKey`  
   b) `@GeneratedKey`  
   c) `@Id` ✅  
   d) `@PrimaryField`  

8. Quin algorisme s’utilitza en Spring Security per encriptar contrasenyes?  
   a) MD5  
   b) SHA-256  
   c) BCrypt ✅  
   d) Base64  

9. Quina anotació s’utilitza per configurar un **bean** que gestiona l’autenticació d’usuaris amb JDBC?  
   a) `@JdbcUserDetailsManager`  
   b) `@UserDetailsService`  
   c) `@Bean` ✅  
   d) `@SecurityConfig`  

10. Quina anotació permet **desactivar la protecció CSRF** en una configuració de seguretat?  
   a) `http.csrf(csrf -> csrf.enabled(false))`  
   b) `http.csrf(csrf -> csrf.disable())` ✅  
   c) `http.disableCSRF()`  
   d) `http.allowAllRequests()`  

11. Per defecte, quina política d’accés s’aplica a una aplicació protegida per Spring Security?  
   a) Totes les peticions són permeses  
   b) Només es permeten peticions GET  
   c) Totes les peticions requereixen autenticació ✅  
   d) Només els administradors poden accedir  

12. Com es poden protegir determinades rutes a Spring Security?  
   a) Utilitzant `@Secure`  
   b) Configurant les rutes a `SecurityConfig` ✅  
   c) Afegint `@ProtectedRoute` al controlador  
   d) Editant `application.properties`  

13. Quin mètode HTTP s’utilitza normalment per **registrar un usuari** en una API REST segura?  
   a) GET  
   b) POST ✅  
   c) DELETE  
   d) PATCH  

14. Quina és una **bona pràctica** quan es crea un sistema d’autenticació en una API REST?  
   a) Guardar les contrasenyes en text pla  
   b) Usar DTOs per encapsular les dades d’usuari ✅  
   c) Permetre que tots els usuaris siguen administradors  
   d) Utilitzar una contrasenya estàndard per a tots els usuaris  

15. Quina classe Spring Boot s’utilitza per gestionar usuaris amb JDBC?  
   a) `JdbcUserDetailsManager` ✅  
   b) `JpaUserRepository`  
   c) `SpringUserManager`  
   d) `SecurityDetailsProvider`  

---

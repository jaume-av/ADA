

# **1. Identifica les anotacions**

## **Enunciat**

A partir del següent fragment, explica quina funció té cadascuna de les anotacions JPA utilitzades:

```java
@Entity
@Table(name = "productes")
public class Producte {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String nom;

    @Column(unique = true)
    private String codi;
}
```

Explica: `@Entity`, `@Table`, `@Id`, `@GeneratedValue`, `@Column`.

## **Solució comentada**

* **@Entity**
  Indica que la classe és una entitat JPA i que existirà com a taula SQL.
* **@Table(name="productes")**
  Defineix explícitament el nom de la taula en la base de dades.
* **@Id**
  Marca el camp com a clau primària.
* **@GeneratedValue(IDENTITY)**
  Hibernate delega al motor SQL la generació de l’ID (AUTO_INCREMENT).
* **@Column(nullable=false, length=150)**
  Genera una columna NOT NULL i limita la longitud a 150 caràcters.
* **@Column(unique=true)**
  Crea un índex UNIQUE en el camp `codi`.

---

# **2. Explica la diferència**

## **Enunciat**

Explica les diferències següents:

* @OneToMany vs @ManyToOne
* FetchType.LAZY vs FetchType.EAGER
* CascadeType.ALL vs CascadeType.MERGE
* @Transient vs columna SQL

## **Solució comentada**

* **@OneToMany**: costat pare; una entitat té moltes dependents.

* **@ManyToOne**: costat fill; moltes entitats pertanyen a una mateixa.

* **LAZY**: carrega la relació quan es necessita. Optimitza rendiment.

* **EAGER**: carrega la relació immediatament; pot provocar recursions o sobrecàrrega.

* **CascadeType.ALL**: propaga *totes* les operacions JPA.

* **CascadeType.MERGE**: només propaga actualitzacions.

* **@Transient**: camp que no es guarda a la base de dades.

* **Columna SQL normal**: es crea automàticament i es persisteix.

---

# **3. Crea l’entitat ALUMNE**

## **Enunciat**

Model:

```
ALUMNE(id PK, nom, cognoms, edat, foto BLOB)
```

Requisits: id autogenerat, nom obligatori, foto BLOB.

## **Solució comentada**

```java
@Entity
public class Alumne {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Clau primària autogenerada
    private Long id;

    @Column(nullable = false) // nom NO pot ser null
    private String nom;

    private String cognoms;

    private int edat;

    @Lob // Emmagatzema dades binàries grans (BLOB)
    private byte[] foto;

    public Alumne() {}
}
```

---

# **4. Relació 1:N Departament – Empleat**

## **Enunciat**

Modela:

* Un Departament té molts Empleats
* Un Empleat pertany a un Departament
* LAZY en la col·lecció
* En esborrar un Departament s’esborren els Empleats

## **Solució comentada**

### Departament

```java
@Entity
public class Departament {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;

    @OneToMany(
        mappedBy = "departament",  // Indica quin camp en Empleat és la FK
        fetch = FetchType.LAZY,    // Carrega només quan es necessite
        cascade = CascadeType.ALL  // Es propaguen les operacions
    )
    private List<Empleat> empleats;

    public Departament() {}
}
```

### Empleat

```java
@Entity
public class Empleat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;

    @ManyToOne(fetch = FetchType.LAZY) // Molts empleats → 1 departament
    @JoinColumn(name = "departament_id", nullable = false) // FK obligatòria
    private Departament departament;

    public Empleat() {}
}
```

---

# **5. Many-to-Many amb atribut extra**

## **Enunciat**

Taules:

* ALUMNE
* CURS
* ALUMNE_CURS amb `data_matricula`

Fes:

1. Per què no podem usar @ManyToMany simple?
2. Escriu la clau composta.
3. Dibuixa la relació.

## **Solució comentada**

1. **No podem usar @ManyToMany** perquè hi ha un camp extra.
   → Cal una entitat pròpia (`AlumneCurs`).

2. **Clau composta:**

```java
@Embeddable
public class AlumneCursId implements Serializable {
    private Long alumneId;
    private Long cursId;
}
```

3. **Relació ASCII:**

```
ALUMNE 1 --- N  ALUMNE_CURS  N --- 1 CURS
               (data_matricula)
```

---

# **6. Evitar recursions JSON**

## **Enunciat**

Model:

```
Pais 1 → N Provincia
```

Indica tres maneres d’evitar recursions infinites en JSON.

## **Solució comentada**

* **@JsonIgnore** en una de les bandes → ignora la propietat durant la serialització.
* **@JsonManagedReference / @JsonBackReference** → controlen serialització bidireccional.
* **DTOs** → no exposes directament les entitats, sinó versions reduïdes o personalitzades.

---

# **7. SQL generat**

## **Enunciat**

`@Column(name="preu", precision=10, scale=2, nullable=false)`

## **Solució comentada**

```sql
preu DECIMAL(10, 2) NOT NULL
```

* Precision 10 → 10 dígits totals
* Scale 2 → 2 decimals
* NOT NULL → valor obligatori

---

# **8. Relació 1:1 Usuari – Perfil**

## **Enunciat**

Model:

```
USUARI(id PK, nom, correu UNIQUE)
PERFIL(id PK, biografia, foto BLOB, usuari_id FK UNIQUE → USUARI.id)
```

Requisits: 1:1, foto BLOB, usuari_id únic.

## **Solució comentada**

### Usuari

```java
@Entity
public class Usuari {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nom;

    @Column(nullable = false, unique = true) // correu únic
    private String correu;

    @OneToOne(mappedBy = "usuari") // El Perfil és el propietari de la relació
    private Perfil perfil;

    public Usuari() {}
}
```

### Perfil

```java
@Entity
public class Perfil {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String biografia;

    @Lob
    private byte[] foto; // BLOB

    @OneToOne
    @JoinColumn(name = "usuari_id", unique = true, nullable = false) 
    private Usuari usuari;

    public Perfil() {}
}
```

---

# **9. Camps no persistits i BLOB**

## **Enunciat**

Model:

```
DOCUMENT(id PK, nom, contingut BLOB, tipus)
```

Crea una entitat amb @Lob i un camp @Transient.

## **Solució comentada**

```java
@Entity
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false) // nom obligatori
    private String nom;

    @Lob // contingut BLOB
    private byte[] contingut;

    private String tipus;

    @Transient // no es guarda a la BDD
    private String resum;

    public Document() {}
}
```

---

# **10. Cadena de relacions Client – Comanda – Línia**

## **Enunciat**

Model:

```
CLIENT 1 → N COMANDA 1 → N LINIA
```

## **Solució comentada**

### Client

```java
@Entity
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;

    @OneToMany(mappedBy = "client") // Un client → moltes comandes
    private List<Comanda> comandes;

    public Client() {}
}
```

### Comanda

```java
@Entity
public class Comanda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate data; // tipus de data modern

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @OneToMany(mappedBy = "comanda") // Una comanda → moltes línies
    private List<Linia> linies;

    public Comanda() {}
}
```

### Línia

```java
@Entity
public class Linia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String producte;
    private int quantitat;

    @ManyToOne
    @JoinColumn(name = "comanda_id", nullable = false)
    private Comanda comanda;

    public Linia() {}
}
```

---

# **11. Many-to-Many amb entitat pròpia (Usuari – Rol)**

## **Enunciat**

Model:

```
USUARI 1 → N USUARI_ROL N → 1 ROL
```

## **Solució comentada**

### Clau composta

```java
@Embeddable
public class UsuariRolId implements Serializable {
    private Long usuariId;
    private Long rolId;
}
```

→ Agrupa les dues claus primàries en un sol objecte.

### Entitat intermèdia

```java
@Entity
public class UsuariRol {

    @EmbeddedId // la clau composta és l'ID de l'entitat
    private UsuariRolId id;

    @ManyToOne
    @MapsId("usuariId") // sincronitza l’ID amb la FK usuari_id
    @JoinColumn(name = "usuari_id")
    private Usuari usuari;

    @ManyToOne
    @MapsId("rolId") // sincronitza amb rol_id
    @JoinColumn(name = "rol_id")
    private Rol rol;

    public UsuariRol() {}
}
```

### Usuari

```java
@Entity
public class Usuari {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;

    @Column(nullable = false, unique = true)
    private String email;

    private String contrasenya;

    @OneToMany(mappedBy = "usuari")
    private List<UsuariRol> rols;

    public Usuari() {}
}
```

### Rol

```java
@Entity
public class Rol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nom;

    @OneToMany(mappedBy = "rol")
    private List<UsuariRol> usuaris;

    public Rol() {}
}
```

---

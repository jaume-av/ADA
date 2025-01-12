---
title: 1. JPA. 
parent: SPRING

has_children: true
layout: default
nav_order: 10
---





# JPA (Java Persistence API)


### **Introducció a JPA i el Context del Text**

**JPA (Java Persistence API)** és una especificació estàndard que facilita la gestió de la persistència de dades en aplicacions Java. La seua funció principal és permetre el mapatge d'objectes Java a taules de bases de dades relacionals, simplificant les operacions de lectura, escriptura i actualització de dades a través de l'ús d'**anotacions**.

JPA no és una implementació en si mateixa, sinó una **interfície** que pot ser utilitzada amb diferents frameworks que implementen aquest estàndard. 
Les opcions més populars són:

- **Hibernate**: Una implementació molt estesa que amplia les capacitats de JPA, oferint funcionalitats avançades.
- **Spring Data JPA**: Una capa d'abstracció que integra JPA dins de l'ecosistema Spring, simplificant encara més el treball amb bases de dades.

Segons el framework que utilitzem, les anotacions i la configuració poden variar lleugerament, però els conceptes bàsics de JPA són aplicables a tots els casos.

Amb **JPA**, cada taula de la base de dades es pot mapejar com una classe Java, on:
- **Columnes** es corresponen amb **atributs**.
- **Relacions** es representen com **associacions entre classes**.


# Anotacions Per a Entitats (Taules)

Les anotacions de JPA són **metadades** que indiquen a Java com una classe, els seus atributs i les seues relacions es mapegen amb una base de dades relacional. 

JPA ens permet;
- Declarar una classe com una **entitat** que representa una taula.
- Especificar com es gestionen les columnes, claus primàries, i claus foranes.
- Mapar **relacions entre taules** (one-to-one, one-to-many, many-to-many).
- Configurar herència i altres aspectes avançats.

**Funcionament bidireccional de JPA**
Les anotacions permeten que JPA treballe de forma bidireccional:
1. **Objectes Java → Base de dades**: Persistir objectes a la base de dades.
2. **Base de dades → Objectes Java**: Convertir registres en objectes Java (hydration).


### @Entity

- Marca una classe com una **entitat JPA**.
- Aquesta classe es mapeja a una taula de la base de dades.

- Una classe amb `@Entity` ha de tindre un camp amb `@Id` per identificar la clau primària.
- Si no s'especifica `@Table`, el nom de la taula serà el mateix que el de la classe.


**Per exemple:**

**Base de dades:**
```sql
CREATE TABLE usuari (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(100) NOT NULL
);
```

**Codi Java:**
```java
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity // Representa la taula "usuari"
public class Usuari {
    @Id
    private Long id;
    private String nom;
}
```

---

### @Table

L'anotació `@Table` s'utilitza per especificar el nom de la taula en la base de dades que es mapeja amb una entitat JPA. Si no s'indica explícitament, JPA utilitza el nom de la classe com a nom de la taula per defecte.

#### **Paràmetres principals**
- `name`: Especifica el nom de la taula.
- `schema`: Defineix l'esquema de la base de dades on es troba la taula.
- `catalog`: Defineix el catàleg de la base de dades (opcional).
- `uniqueConstraints`: Estableix restriccions d'uniqueness per a una o més columnes.

#### **Exemple bàsic**
```java
@Entity
@Table(name = "usuaris", schema = "public")
public class Usuari {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nom;

    @Column(unique = true)
    private String correu;
}
```

Aquest exemple mapeja la classe `Usuari` a la taula `usuaris` de l'esquema `public`.

**Restriccions amb `uniqueConstraints`**
El paràmetre `uniqueConstraints` permet definir restriccions úniques en una o més columnes de la taula. Els valors que pot prendre són:

- `@UniqueConstraint(columnNames = "nom_columna")`: Defineix una restricció única sobre una sola columna.
- `@UniqueConstraint(columnNames = {"columna1", "columna2"})`: Defineix una restricció única composta sobre diverses columnes.
- `@UniqueConstraint(name = "nom_restriccio", columnNames = "columna")`: Assigna un nom específic a la restricció única d'una columna.
- `@UniqueConstraint(columnNames = "columna", name = "nom_restriccio")`: Defineix el mateix que l'anterior amb l'ordre invers.
- `@UniqueConstraint(columnNames = {"columna1", "columna2"}, name = "nom_restriccio")`: Defineix una restricció única composta i li assigna un nom.

**Exemple amb `uniqueConstraints`**
```java
@Entity
@Table(
    name = "productes",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = "codi"),
        @UniqueConstraint(name = "uk_nom_categoria", columnNames = {"nom", "categoria_id"})
    }
)
public class Producte {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nom;

    @Column(nullable = false, unique = true)
    private String codi;

    @Column(name = "categoria_id")
    private Long categoriaId;
}
```

**Base de dades generada**
```sql
CREATE TABLE productes (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(255) NOT NULL,
    codi VARCHAR(255) NOT NULL UNIQUE,
    categoria_id BIGINT,
    CONSTRAINT uk_nom_categoria UNIQUE (nom, categoria_id)
);
```



---

### @Id
- Marca un camp com la **clau primària** de l'entitat.

```java
import jakarta.persistence.Id;

@Entity
public class Usuari {
    @Id
    private Long id; // Clau primària
}
```


- Normalment es combina amb `@GeneratedValue` per especificar com es genera el valor de la clau primària.

---

### @GeneratedValue

L'anotació `@GeneratedValue` defineix com es genera el valor de la clau primària d'una entitat. 
Aquesta estratègia pot ser gestionada per JPA o delegada a la base de dades.

#### **Paràmetres principals**
- `strategy`: Estratègia de generació de la clau primària.
- `generator`: Nom d'un generador personalitzat (opcional).

---

**Estratègies disponibles (`GenerationType`)**

1. **`AUTO`**: JPA selecciona l'estratègia adequada segons la base de dades.
2. **`IDENTITY`**: Utilitza **AUTO_INCREMENT** en SQL. És compatible amb MySQL i similars.
3. **`SEQUENCE`**: Usa una **seqüència SQL**. Compatible amb bases de dades com PostgreSQL o Oracle.
4. **`TABLE`**: Emula una seqüència mitjançant una taula específica. Compatible amb totes les bases de dades però menys eficient.

---

**Exemples**

**1. Estratègia `IDENTITY` (AUTO_INCREMENT)**
**Base de dades:**
```sql
CREATE TABLE usuari (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(100) NOT NULL
);
```

**Java:**
```java
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;
```

---

**2. Estratègia `SEQUENCE`**

**Base de dades:**
```sql
CREATE SEQUENCE seq_usuari START WITH 1 INCREMENT BY 1;

CREATE TABLE usuari (
    id BIGINT PRIMARY KEY DEFAULT nextval('seq_usuari'),
    nom VARCHAR(100) NOT NULL
);
```

**Java:**
```java
@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_gen")
@SequenceGenerator(name = "seq_gen", sequenceName = "seq_usuari", allocationSize = 1)
private Long id;
```

---

**3. Estratègia `TABLE`**
**Base de dades:**
```sql
CREATE TABLE seq_table (
    seq_name VARCHAR(50) PRIMARY KEY,
    seq_value BIGINT
);

INSERT INTO seq_table (seq_name, seq_value) VALUES ('usuari_seq', 1);
```

**Codi Java:**
```java
@GeneratedValue(strategy = GenerationType.TABLE, generator = "table_gen")
@TableGenerator(
    name = "table_gen",
    table = "seq_table",
    pkColumnName = "seq_name",
    valueColumnName = "seq_value",
    pkColumnValue = "usuari_seq",
    allocationSize = 1
)
private Long id;
```

---

#### **Resum**


| Estratègia           | Bases de dades compatibles    | Comentari                     |
|----------------------|-------------------------------|-------------------------------|
| `AUTO`              | Totes                        | Selecció automàtica per JPA. |
| `IDENTITY`          | MySQL, SQL Server            | Basada en AUTO_INCREMENT.    |
| `SEQUENCE`          | PostgreSQL, Oracle           | Requereix suport de seqüències. |
| `TABLE`             | Totes                        | Compatible, però lenta.       |


---

### @Column

L'anotació `@Column` s'utilitza per mapar un atribut Java a una columna SQL, permetent personalitzar el nom i les propietats de la columna dins de la base de dades.

#### **Paràmetres**
- **`name`**: Especifica el nom de la columna a la base de dades. Si no s'indica, s'utilitza el nom de l'atribut Java.
- **`nullable`**: Determina si la columna pot tindre valors `NULL`. Per defecte, és `true`.
- **`unique`**: Indica si els valors de la columna han de ser únics. Per defecte, és `false`.
- **`length`**: Especifica la longitud màxima de la columna (només aplicable a tipus `String`). El valor per defecte és 255.
- **`precision` i `scale`**: S'utilitzen per definir la precisió i el nombre de decimals en tipus numèrics com `BigDecimal`.
  - **`precision`**: Nombre total de dígits.
  - **`scale`**: Nombre de dígits després del punt decimal.

---

**Exemple**

**Base de dades:**
```sql
CREATE TABLE usuari (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(100) NOT NULL,
    correu VARCHAR(100) UNIQUE
);
```

**Java:**
```java
@Column(name = "nom", nullable = false, length = 100)
private String nom;

@Column(name = "correu", unique = true, length = 100)
private String correu;
```

---

**Exemple avançat: `precision` i `scale`**
**Base de dades:**
```sql
CREATE TABLE producte (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    preu DECIMAL(10, 2) NOT NULL
);
```

**Java:**
```java
@Column(name = "preu", precision = 10, scale = 2, nullable = false)
private BigDecimal preu;
```

En aquest cas:
- **`precision = 10`**: El valor pot tindre fins a 10 dígits totals.
- **`scale = 2`**: Dels 10 dígits, 2 són decimals.

---

- Si una columna no es defineix amb `@Column`, JPA utilitza valors per defecte segons el nom i el tipus de l'atribut.
- Les opcions `nullable` i `unique` només afecten el comportament a la base de dades, no al codi Java. 

endre completament l'ús de `@Column`. 😊

---

### @Transient
- Marca un camp que **no es persistirà** en la base de dades.
- Útil per a atributs calculats o temporals.

**Exemple:**
```java
@Transient
private String cacheLocal; // No es guardarà en la base de dades
```

---

### @Lob
- Marca un camp com un **Large Object (LOB)**, utilitzat per:
  - **CLOB**: Texts grans (`String`).
  - **BLOB**: Fitxers binaris (`byte[]`).

#### **Exemple:**
**Base de dades:**
```sql
CREATE TABLE document (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    contingut LONGBLOB
);
```

**Java:**

```java
@Lob
private byte[] contingut;
```

---



## TAULA RESUM ANOTACIONS BÀSIQUES 



| **Anotació**       | **Propòsit**                                                                             | **Exemple**                                                                                     |
|---------------------|-----------------------------------------------------------------------------------------|-------------------------------------------------------------------------------------------------|
| `@Entity`          | Marca una classe com una entitat JPA.                                                   | `@Entity public class Usuari { ... }`                                                          |
| `@Table`           | Configura el nom de la taula i restriccions.                                             | `@Table(name = \"usuaris\", uniqueConstraints = {...})`                                        |
| `@Id`              | Marca la clau primària d’una entitat.                                                   | `@Id private Long id;`                                                                         |
| `@GeneratedValue`  | Defineix com es genera la clau primària.                                                 | `@GeneratedValue(strategy = GenerationType.IDENTITY)`                                          |
| `@Column`          | Configura propietats específiques d’una columna.                                        | `@Column(name = \"nom\", nullable = false, length = 100)`                                      |
| `@Transient`       | Indica que un atribut no es persistirà a la base de dades.                              | `@Transient private String cache;`                                                            |
| `@Lob`             | Defineix una columna per a objectes grans (CLOB/BLOB).                                  | `@Lob private byte[] contingut;`                                                              |





# Relacions entre Entitats

JPA proporciona anotacions per definir relacions entre entitats i mapar-les amb bases de dades relacionals. Aquestes anotacions permeten representar associacions com **One-To-One**, **One-To-Many**, **Many-To-One**, i **Many-To-Many**, gestionant les claus foranes i taules intermèdies de manera senzilla. 

A més, les estructures de dades en Java, com **`List`**, **`Set`** i **`Map`**, tenen un paper clau en la representació d'aquestes relacions, ja que afecten directament el comportament de les entitats i el rendiment de les operacions.

---

## **Anotacions per a Relacions**

Les anotacions de JPA per relacions són essencials per establir associacions entre entitats (classes) que reflectisquen les relacions entre taules a la base de dades.

Amb aquestes anotacions, podem:
- Definir relacions **un a un**, **un a molts** i **molts a molts**.
- Gestionar la càrrega de dades (`LAZY` o `EAGER`).
- Configurar claus foranes i taules intermèdies.
- Especificar el comportament en cascada (`CascadeType`).
- Triar les estructures de dades més adequades per a cada tipus de relació.

---

### **@OneToOne**

- Representa una relació **un a un** entre dues entitats.
- Generalment s'utilitza juntament amb `@JoinColumn` per indicar la clau forana.
- **Paràmetres principals**:
  - `mappedBy`: Defineix el costat invers de la relació.
  - `cascade`: Gestiona les operacions en cascada (persistència, eliminació, etc.).
  - `fetch`: Determina si la relació es carrega de manera **LAZY** (per demanda) o **EAGER** (immediatament).

**Base de dades:**
```sql
CREATE TABLE usuari (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    perfil_id BIGINT UNIQUE,
    FOREIGN KEY (perfil_id) REFERENCES perfil(id)
);

CREATE TABLE perfil (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    descripcio VARCHAR(255)
);
```

**Codi Java:**
```java
@Entity
public class Usuari {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "perfil_id") // Clau forana
    private Perfil perfil;
}

@Entity
public class Perfil {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String descripcio;
}
```

**Estructura recomanada**:
- **Objecte simple**: Aquesta relació no requereix estructures de col·lecció.

**Quan utilitzar @OneToOne**:
- Per relacions exclusives entre dues entitats, com usuari-perfil o cotxe-motor.

---

### **@OneToMany i @ManyToOne**

- Representen una relació **un a molts** i la seua inversa **molts a un**.
- **Paràmetres principals**:
  - `mappedBy`: Defineix el costat invers en una relació bidireccional.
  - `cascade` i `fetch`: Comportament en cascada i estratègia de càrrega.

**Base de dades:**
```sql
CREATE TABLE categoria (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(100) NOT NULL
);

CREATE TABLE producte (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(100) NOT NULL,
    categoria_id BIGINT,
    FOREIGN KEY (categoria_id) REFERENCES categoria(id)
);
```

**Codi Java:**
```java
@Entity
public class Categoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;

    @OneToMany(mappedBy = "categoria", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Producte> productes; // O Set<Producte>
}

@Entity
public class Producte {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;

    @ManyToOne
    @JoinColumn(name = "categoria_id") // Clau forana
    private Categoria categoria;
}
```

**Estructura recomanada**:
- **`List`**: Manté l'ordre d'inserció dels elements.
- **`Set`**: Evita duplicats a la col·lecció.

**Quan utilitzar `List` o `Set`**:
- **`List`**: Si és important mantenir un ordre específic (ex. productes ordenats per data d'entrada).
- **`Set`**: Si es requereixen elements únics (ex. productes sense duplicats dins d'una categoria).

---

### **@ManyToMany**

- Representa una relació **molts a molts** entre dues entitats.
- Necessita una **taula intermèdia** per gestionar les relacions.
- **Paràmetres principals**:
  - `mappedBy`: Defineix el costat invers.
  - `cascade` i `fetch`: Comportament en cascada i estratègia de càrrega.

**Base de dades:**
```sql
CREATE TABLE alumne (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(100) NOT NULL
);

CREATE TABLE curs (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(100) NOT NULL
);

CREATE TABLE alumnes_cursos (
    alumne_id BIGINT,
    curs_id BIGINT,
    PRIMARY KEY (alumne_id, curs_id),
    FOREIGN KEY (alumne_id) REFERENCES alumne(id),
    FOREIGN KEY (curs_id) REFERENCES curs(id)
);
```

**Codi Java amb `List`:**
```java
@Entity
public class Alumne {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;

    @ManyToMany
    @JoinTable(
        name = "alumnes_cursos",
        joinColumns = @JoinColumn(name = "alumne_id"),
        inverseJoinColumns = @JoinColumn(name = "curs_id")
    )
    private List<Curs> cursos; // També es pot utilitzar Set
}

@Entity
public class Curs {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;

    @ManyToMany(mappedBy = "cursos")
    private List<Alumne> alumnes;
}
```

**Estructura recomanada**:
- **`List`**: Manté l'ordre dels elements (si és necessari).
- **`Set`**: Evita duplicats.

**Quan utilitzar `List` o `Set`**:
- **`List`**: Quan l'ordre dels elements té importància (ex. cursos ordenats cronològicament).
- **`Set`**: Per garantir que no hi hagen duplicats en les relacions.

**Nota**: JPA permet usar tant `List` com `Set` per a relacions **molts a molts**. **`Set`** és recomanat si es volen evitar duplicats a nivell de col·lecció.

---

### **Taula Resum de les Anotacions per a Relacions**

| **Anotació**       | **Relació**     | **Descripció**                                                                                  | **Estructura Recomanada** | **Exemple Base de Dades**                              |
|---------------------|-----------------|------------------------------------------------------------------------------------------------|---------------------------|-------------------------------------------------------|
| `@OneToOne`        | 1:1             | Relació entre dues entitats amb una clau forana única.                                          | Objecte simple            | Una taula amb clau forana única cap a una altra.      |
| `@OneToMany`       | 1:N             | Una entitat té múltiples registres relacionats.                                                | `List` o `Set`            | Una taula amb una clau forana cap a la taula pare.    |
| `@ManyToOne`       | N:1             | Relació inversa: múltiples registres apunten a una sola entitat pare.                          | Objecte simple            | Diversos registres apunten a un únic registre pare.   |
| `@ManyToMany`      | N:M             | Relació amb una taula intermèdia per a múltiples registres relacionats.                        | `List` o `Set`            | Taula intermèdia amb claus foranes a dues taules.     |
| `@JoinColumn`      | -               | Especifica la clau forana i el seu nom.                                                        | -                         | Defineix la columna de relació en la taula SQL.       |
| `@JoinTable`       | -               | Configura una taula intermèdia per a relacions molts a molts.                                  | -                         | Taula amb claus foranes a dues entitats relacionades. |

--- 

# ANNEX - Claus compostes en relacions @many to many


En algunes situacions, les relacions **@ManyToMany** requereixen la gestió de **claus compostes** a la taula intermèdia. Això pot ocórrer quan es necessiten més dades a la taula intermèdia o quan la taula intermèdia té claus que referencien múltiples atributs de cada entitat relacionada.

---

**Escenari**

Suposem que volem representar una relació entre **Estudiants** i **Assignatures** amb una taula intermèdia que inclou informació addicional, com la **data d'inscripció**. En aquest cas, la taula intermèdia no només contindrà les claus foranes de les entitats principals, sinó també un altre camp que no forma part de les claus primàries.

---

### **Base de dades**

**Esquema SQL:**
```sql
CREATE TABLE estudiant (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(100) NOT NULL
);

CREATE TABLE assignatura (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(100) NOT NULL
);

CREATE TABLE estudiant_assignatura (
    estudiant_id BIGINT NOT NULL,
    assignatura_id BIGINT NOT NULL,
    data_inscripcio DATE NOT NULL,
    PRIMARY KEY (estudiant_id, assignatura_id),
    FOREIGN KEY (estudiant_id) REFERENCES estudiant(id),
    FOREIGN KEY (assignatura_id) REFERENCES assignatura(id)
);
```

---

### **Codi Java: Implementació amb @ManyToMany i Claus Compostes**

**1. Crear una Classe Embeddable per a la Clau Composta**

Utilitzem l'anotació `@Embeddable` per definir una classe que representarà la clau composta de la taula intermèdia.

```java
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class EstudiantAssignaturaId implements Serializable {

    private Long estudiantId;
    private Long assignaturaId;

    // Constructors, equals i hashCode

    public EstudiantAssignaturaId() {}

    public EstudiantAssignaturaId(Long estudiantId, Long assignaturaId) {
        this.estudiantId = estudiantId;
        this.assignaturaId = assignaturaId;
    }

    // Getters i Setters
    public Long getEstudiantId() {
        return estudiantId;
    }

    public void setEstudiantId(Long estudiantId) {
        this.estudiantId = estudiantId;
    }

    public Long getAssignaturaId() {
        return assignaturaId;
    }

    public void setAssignaturaId(Long assignaturaId) {
        this.assignaturaId = assignaturaId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EstudiantAssignaturaId that = (EstudiantAssignaturaId) o;
        return Objects.equals(estudiantId, that.estudiantId) &&
               Objects.equals(assignaturaId, that.assignaturaId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(estudiantId, assignaturaId);
    }
}
```

---

**2. Definir l'Entitat per a la Taula Intermèdia**

La taula intermèdia es representa com una entitat amb la clau composta definida anteriorment.

```java
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class EstudiantAssignatura {

    @EmbeddedId
    private EstudiantAssignaturaId id;

    @ManyToOne
    @MapsId("estudiantId") // Mapar la clau composta
    @JoinColumn(name = "estudiant_id")
    private Estudiant estudiant;

    @ManyToOne
    @MapsId("assignaturaId") // Mapar la clau composta
    @JoinColumn(name = "assignatura_id")
    private Assignatura assignatura;

    private LocalDate dataInscripcio;

    // Constructors, getters i setters

    public EstudiantAssignatura() {}

    public EstudiantAssignatura(Estudiant estudiant, Assignatura assignatura, LocalDate dataInscripcio) {
        this.id = new EstudiantAssignaturaId(estudiant.getId(), assignatura.getId());
        this.estudiant = estudiant;
        this.assignatura = assignatura;
        this.dataInscripcio = dataInscripcio;
    }

    public EstudiantAssignaturaId getId() {
        return id;
    }

    public void setId(EstudiantAssignaturaId id) {
        this.id = id;
    }

    public Estudiant getEstudiant() {
        return estudiant;
    }

    public void setEstudiant(Estudiant estudiant) {
        this.estudiant = estudiant;
    }

    public Assignatura getAssignatura() {
        return assignatura;
    }

    public void setAssignatura(Assignatura assignatura) {
        this.assignatura = assignatura;
    }

    public LocalDate getDataInscripcio() {
        return dataInscripcio;
    }

    public void setDataInscripcio(LocalDate dataInscripcio) {
        this.dataInscripcio = dataInscripcio;
    }
}
```

---

**3. Modificar les Entitats Principals**

Actualitzem les entitats **Estudiant** i **Assignatura** per incloure la relació amb la taula intermèdia.

```java
import jakarta.persistence.*;
import java.util.List;

@Entity
public class Estudiant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;

    @OneToMany(mappedBy = "estudiant", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EstudiantAssignatura> assignatures;

    // Constructors, getters i setters
}

@Entity
public class Assignatura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;

    @OneToMany(mappedBy = "assignatura", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EstudiantAssignatura> estudiants;

    // Constructors, getters i setters
}
```

---

### **Quan utilitzar Claus Compostes amb @ManyToMany**

- Quan necessites informació addicional a la taula intermèdia.
- Quan les claus de la taula intermèdia són compostes.
- Quan vols tindre més control sobre la taula intermèdia, incloent-hi la personalització de les claus o camps addicionals.


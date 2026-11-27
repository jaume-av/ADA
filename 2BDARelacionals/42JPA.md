---
title: "4.2. JPA i mapatge de relacions"
parent: "4. Persistència amb JPA i Hibernate"
grand_parent: Persistència en Base de Dades
layout: default
nav_order: 20
---

# 4.2. JPA i mapatge de relacions

## Entitats i atributs

Una **entitat** és una classe les instàncies de la qual es poden guardar i recuperar. En la pràctica, cada instància d'`Empleat` representa una fila d'`Empleats`. Les anotacions del mapatge es posen sobre els atributs perquè l'`@Id` està declarat sobre un camp. JPA accedix als camps i els *getters* els utilitza el nostre programa.

| Anotació | Per a què servix |
| --- | --- |
| `@Entity` | Declara una entitat. Per defecte, JPQL usa el nom de la classe. |
| `@Table(name = "...")` | Indica la taula. |
| `@Id` | Identifica la clau primària. |
| `@GeneratedValue` | Delega la generació d'un identificador. Amb `IDENTITY` l'identificador es genera en inserir la fila. Cal que la base de dades i el dialecte ho admeten. |
| `@Column` | Configura el mapatge d'un camp a una columna, per exemple, el nom, la longitud o si admet nuls. |
| `@Transient` | Exclou un atribut Java del mapatge. |

Una entitat ha de tindre un constructor públic o protegit sense paràmetres. Pot tindre, a més, constructors amb paràmetres que use el nostre codi. Amb una columna que admet `NULL`, utilitza un tipus com `Double` o `Integer` si necessites representar eixe valor en Java. Les restriccions de les anotacions descriuen el mapatge. En una base ja creada, cal comprovar també les restriccions reals de l'esquema.

## Context de persistència i transaccions

L'`EntityManager` gestiona un conjunt d'entitats dins d'un **context de persistència**. Dins del mateix context, una identitat persistent correspon a una sola instància gestionada.

| Estat | Exemple |
| --- | --- |
| Nova | Acabem de crear l'entitat amb `new`. |
| Gestionada | L'hem recuperada amb `find()` o l'hem incorporada amb `persist()`. |
| Desvinculada | Era gestionada i hem tancat l'`EntityManager` o l'hem desvinculada. |
| Eliminada | Hem cridat `remove()` sobre una entitat gestionada. L'eliminació es trasllada a la base quan es sincronitzen els canvis. |

| Operació | Ús |
| --- | --- |
| `persist(entitat)` | Incorpora una entitat nova al context perquè s'inserisca. |
| `find(Empleat.class, nif)` | Recupera per clau primària. Retorna `null` si no existix. |
| Modificar un atribut d'una entitat gestionada | Hibernate detecta el canvi i pot generar un `UPDATE` quan sincronitza. |
| `remove(entitat)` | Marca per a eliminar una entitat gestionada. |
| `merge(entitat)` | Copia l'estat a una instància gestionada i la retorna. Per continuar treballant amb una entitat gestionada, usa la instància retornada. |

En Java SE, inicia una transacció amb `em.getTransaction().begin()` abans d'escriure. Confirma-la amb `commit()` i revertix-la amb `rollback()` si falla i continua activa. La **sincronització** (*flush*) envia els canvis pendents a la base de dades. Pot produir-se abans d'algunes consultes o demanar-se amb `em.flush()`. El `commit()` confirma la transacció.

### Consultes amb JPQL

Quan el criteri és un atribut diferent de la clau primària, pots utilitzar **JPQL**. La consulta usa entitats i atributs Java. Hibernate la traduïx a SQL.

```java
List<Empleat> empleats = em.createQuery("""
        SELECT e
        FROM Empleat e
        WHERE e.salari > :minim
        ORDER BY e.nom
        """, Empleat.class)
    .setParameter("minim", 2500.0)
    .getResultList();
```

`Empleat` és el nom de l'entitat i `salari` i `nom` són atributs. El paràmetre `:minim` es vincula amb `setParameter()`, com quan donàvem valor a un paràmetre d'un `PreparedStatement`.

## Relacions entre entitats

La **cardinalitat** diu amb quantes instàncies es pot relacionar una entitat. La **direcció** indica des de quins objectes podem navegar per la relació. Per exemple, l'esquema permet localitzar els empleats d'un departament des de SQL encara que, en Java, només hàgem declarat `Empleat.departament`.

En un mapatge bidireccional, el **costat propietari** és el que JPA té en compte per guardar els canvis de la relació. En el costat invers, `mappedBy` conté el **nom de l'atribut Java** del costat propietari. Quan canvies una relació bidireccional, mantín les dues referències Java coherents.

Els fragments següents mostren els camps que has d'afegir en cada variant. Cada variant alternativa s'aplica per separat. Les taules d'acreditacions, projectes i assignacions requerixen ampliar l'esquema de la pràctica.

### N:1 i 1:N. Empleats i departaments

Molts empleats poden pertànyer al mateix departament. La clau forana està en la taula `Empleats`. L'atribut següent és suficient si només necessitem anar de l'empleat al seu departament.

```java
// En Empleat
@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "IdDepartament")
private Departament departament;
```

Com que `IdDepartament` admet `NULL`, l'empleat pot estar sense departament. Si la relació fora obligatòria, declararíem `optional = false` en `@ManyToOne`, `nullable = false` en `@JoinColumn` i una restricció `NOT NULL` en SQLite.

Per poder anar també del departament als seus empleats, mantenim el camp anterior i afegim la col·lecció següent. `mappedBy = "departament"` apunta a l'atribut d'`Empleat`.

```java
// En Departament
@OneToMany(mappedBy = "departament")
private List<Empleat> empleats = new ArrayList<>();
```

Quan assignes un empleat a un departament, actualitza `empleat.departament` i la col·lecció `empleats`. Si l'empleat canvia de departament, trau-lo també de la col·lecció de l'anterior. Afegir-lo només a la col·lecció inversa deixa la clau forana sense el canvi.

Si el programa només necessitara navegar de departament a empleats, també podríem declarar `@OneToMany` unidireccional. Per defecte usa una taula d'unió. Amb `@JoinColumn(name = "IdDepartament")` sobre la col·lecció, reutilitza la clau forana d'`Empleats`. Esta variant substituïx els dos camps anteriors.

### 1:1. Empleat i acreditació

Suposem que cada empleat pot tindre una acreditació i que cada acreditació correspon a un empleat. En `Acreditacions`, la columna `EmpleatNIF` és una clau forana amb restriccions `UNIQUE` i `NOT NULL`. La restricció `UNIQUE` impedix que dues acreditacions apunten al mateix empleat.

```java
// En Acreditacio, que conté la clau forana
@OneToOne(optional = false)
@JoinColumn(name = "EmpleatNIF", unique = true, nullable = false)
private Empleat empleat;
```

Si també necessitem consultar l'acreditació des de l'empleat, afegim el costat invers.

```java
// En Empleat
@OneToOne(mappedBy = "empleat")
private Acreditacio acreditacio;
```

Una altra variant és que l'acreditació compartisca la clau primària de l'empleat. En eixe cas, `Acreditacions.EmpleatNIF` és **clau primària i clau forana**. En `Acreditacio`, els camps d'identitat i de la relació queden així:

```java
@Id
@Column(name = "EmpleatNIF")
private String nif;

@MapsId
@OneToOne(optional = false)
@JoinColumn(name = "EmpleatNIF")
private Empleat empleat;
```

`@MapsId` fa que JPA prenga l'identificador de l'empleat associat.

### N:M amb taula d'unió. Empleats i projectes

Un empleat pot participar en diversos projectes i un projecte pot tindre diversos empleats. La taula `EmpleatsProjectes` té dues claus foranes, `EmpleatNIF` i `IdProjecte`. Una clau primària composta per les dues columnes impedix duplicar una participació.

**Unidireccional.** Des de l'empleat volem veure els seus projectes. Declarem la col·lecció en `Empleat`.

```java
@ManyToMany
@JoinTable(
    name = "EmpleatsProjectes",
    joinColumns = @JoinColumn(name = "EmpleatNIF"),
    inverseJoinColumns = @JoinColumn(name = "IdProjecte")
)
private Set<Projecte> projectes = new HashSet<>();
```

`joinColumns` identifica la clau forana cap a l'entitat propietària, `Empleat`. `inverseJoinColumns` identifica la clau forana cap a `Projecte`. Si l'única navegació que ens interessa és la inversa, podem declarar el mapatge només en `Projecte` i intercanviar el paper de les dues columnes.

**Bidireccional.** Mantenim el mapatge anterior i afegim una col·lecció en `Projecte` per veure els seus empleats.

```java
@ManyToMany(mappedBy = "projectes")
private Set<Empleat> empleats = new HashSet<>();
```

En afegir una participació, actualitza la col·lecció propietària `Empleat.projectes` i mantín també `Projecte.empleats` al dia. En retirar-la de la col·lecció propietària, Hibernate elimina el vincle de la taula d'unió.

```sql
CREATE TABLE EmpleatsProjectes (
    EmpleatNIF VARCHAR(9) NOT NULL REFERENCES Empleats(NIF),
    IdProjecte INTEGER NOT NULL REFERENCES Projectes(IdProjecte),
    PRIMARY KEY (EmpleatNIF, IdProjecte)
);
```

Les dues variants usen la mateixa taula d'unió. Per aplicar l'exemple, prepara també `Projectes` i una entitat `Projecte` amb `@Id` sobre el camp que correspon a `IdProjecte`. Registra l'entitat en `persistence.xml`.

### N:M amb entitat intermèdia. Assignacions

Si també volem guardar les **hores** de cada empleat en cada projecte, la fila d'unió té informació pròpia. La representem amb una entitat `Assignacio` i dues relacions N:1.

```java
@Entity
@Table(name = "Assignacions", uniqueConstraints =
    @UniqueConstraint(columnNames = {"EmpleatNIF", "IdProjecte"}))
public class Assignacio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdAssignacio")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "EmpleatNIF", nullable = false)
    private Empleat empleat;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "IdProjecte", nullable = false)
    private Projecte projecte;

    @Column(name = "Hores", nullable = false)
    private int hores;

    protected Assignacio() {
    }

    public Assignacio(Empleat empleat, Projecte projecte, int hores) {
        this.empleat = empleat;
        this.projecte = projecte;
        this.hores = hores;
    }
}
```

En SQLite, crea `IdAssignacio` com a `INTEGER PRIMARY KEY` i afig `UNIQUE (EmpleatNIF, IdProjecte)` si només vols una assignació per parella. Si necessites navegar des de les entitats originals, afig `@OneToMany(mappedBy = "empleat")` en `Empleat` i `@OneToMany(mappedBy = "projecte")` en `Projecte`, cadascuna sobre la seua col·lecció d'assignacions.

Si la taula ja té la parella com a clau primària, pots usar una **clau composta**. En la classe següent, els noms `empleatNif` i `projecteId` corresponen als atributs de la clau que agafarà cada relació. Suposem que l'identificador de `Projecte` és de tipus `Integer`.

```java
@Embeddable
public class AssignacioId implements Serializable {
    private String empleatNif;
    private Integer projecteId;

    public AssignacioId() {
    }

    @Override
    public boolean equals(Object altre) {
        if (!(altre instanceof AssignacioId id)) {
            return false;
        }
        return Objects.equals(empleatNif, id.empleatNif)
            && Objects.equals(projecteId, id.projecteId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(empleatNif, projecteId);
    }
}
```

En `Assignacio`, substituïx l'identificador generat per `@EmbeddedId` i indica amb `@MapsId` quina part de la clau aporta cada relació. La resta d'atributs de l'entitat, com `hores`, es mantenen.

```java
@EmbeddedId
private AssignacioId id = new AssignacioId();

@MapsId("empleatNif")
@ManyToOne(fetch = FetchType.LAZY, optional = false)
@JoinColumn(name = "EmpleatNIF", nullable = false)
private Empleat empleat;

@MapsId("projecteId")
@ManyToOne(fetch = FetchType.LAZY, optional = false)
@JoinColumn(name = "IdProjecte", nullable = false)
private Projecte projecte;
```

### Càrrega i operacions en cascada

- **Càrrega.** JPA usa `LAZY` per defecte en les col·leccions i `EAGER` en `@ManyToOne` i `@OneToOne`. Amb `LAZY`, el proveïdor pot ajornar la càrrega fins que accedim a l'atribut. En la pràctica accedim al departament amb l'`EntityManager` obert. Si fas una consulta sobre una llista i després accedixes a un departament per cada empleat, pots provocar consultes addicionals. Un `LEFT JOIN FETCH e.departament` en JPQL permet demanar els departaments en la mateixa consulta i conservar els empleats sense departament.
- **Cascada.** Per defecte, JPA fa cada operació sobre l'entitat indicada. Amb `cascade = CascadeType.PERSIST` es pot propagar el desament a una entitat nova associada. En la pràctica, el departament ja existix i el recuperem amb `find()`.
- **Eliminació.** `orphanRemoval = true` en una relació 1:1 o 1:N elimina l'entitat dependent quan es desvincula. Usa-ho quan la dependent només té sentit dins del seu propietari. En una N:M els projectes són compartits, així que evita `CascadeType.REMOVE` i `CascadeType.ALL`. Retirar un projecte d'un empleat ha d'eliminar la participació.

[Especificació de Jakarta Persistence 3.1](https://jakarta.ee/specifications/persistence/3.1/jakarta-persistence-spec-3.1.html)

### Raona

1. Si afegixes un empleat a `Departament.empleats` sense assignar-li `Empleat.departament`, quin valor quedarà en `Empleats.IdDepartament`?
2. Si vols guardar la data d'incorporació d'un empleat a un projecte, quina variant N:M faries servir i en quin atribut guardaries la data?
3. Si elimines una participació d'un empleat, quina fila s'ha d'eliminar i quines han de continuar existint?

---
title: "4.1. Introducció i pràctica guiada"
parent: "4. Persistència amb JPA i Hibernate"
grand_parent: Persistència en Base de Dades
layout: default
nav_order: 10
---

# 4.1. Introducció i pràctica guiada

## 1. El desfasament objecte-relacional

En SQLite, un empleat pertany a un departament perquè la columna `Empleats.IdDepartament` conté l'identificador d'una fila de `Departaments`. Per a obtindre el nom del departament amb SQL podem fer un `JOIN`. En Java ens interessa representar eixa relació amb un atribut de tipus `Departament` i poder escriure `empleat.getDepartament().getNom()` si l'empleat té departament.

Esta diferència entre la manera com es representen les dades en Java i en les taules s'anomena **desfasament objecte-relacional**. Apareix també amb la identitat. Dos objectes Java diferents poden representar la mateixa fila si tenen el mateix NIF.

També apareix quan naveguem en l'altre sentit. En Java, un departament pot tindre una col·lecció d'empleats. En SQLite, la taula `Departaments` no té cap columna que continga eixa llista. Per a obtindre-la, busquem les files d'`Empleats` que tenen el mateix `IdDepartament`.

## 2. Eines de mapatge objecte-relacional

El **mapatge objecte-relacional** (*object-relational mapping*, ORM) descriu com es corresponen les classes i les taules. Una eina ORM llig el mapatge i el fa servir per convertir files en objectes i traslladar canvis dels objectes a la base de dades. En el nostre cas, indiquem que `Empleat` correspon a `Empleats`, que `nif` és la seua clau primària i que `departament` es guarda com a clau forana.

| Operació | Amb JDBC | Amb JPA i Hibernate |
| --- | --- | --- |
| Llegir un empleat i el seu departament | Escrivim un `SELECT` amb un `JOIN`, llegim el `ResultSet` i construïm els objectes. | Demanem l'empleat amb `find(Empleat.class, nif)`. Hibernate crea l'objecte i usa el mapatge per carregar el departament associat. |
| Canviar un empleat de departament | Escrivim un `UPDATE` sobre `Empleats.IdDepartament` i li passem els identificadors. | Canviem la referència `departament` d'un empleat gestionat. Hibernate detecta el canvi i genera l'`UPDATE` quan sincronitza la transacció. |

En els dos casos, el programa decidix quin empleat modifica, a quin departament el trasllada i quan confirma la transacció. Amb Hibernate, el SQL continua executant-se a través de JDBC. Mirar-lo ens ajuda a entendre quan es llig una relació o s'escriu un canvi.

### JPA i Hibernate

**Jakarta Persistence (JPA)** és l'especificació que definix les anotacions del mapatge i l'API de persistència. En els exemples, les anotacions i classes de l'API s'importen de `jakarta.persistence`.

**Hibernate ORM** i **EclipseLink** són proveïdors que implementen JPA. En esta pràctica usem Hibernate. Llig les anotacions, gestiona les entitats i genera SQL. El **controlador JDBC de SQLite** obri la connexió amb el fitxer de base de dades. El **dialecte** de SQLite indica a Hibernate quines formes de SQL pot utilitzar amb eixa base de dades. [Guia de Hibernate ORM](https://docs.hibernate.org/orm/6.6/introduction/html_single/)

En esta pràctica executarem JPA en una aplicació Java amb un `main`. Més avant, en crear l'API REST amb Spring Boot, podrem reutilitzar el mapatge de les entitats i accedir a les dades amb Spring Data JPA.

## 3. Pràctica guiada amb la base de dades d’empresa

**Objectiu.** Guardar un empleat, recuperar-lo amb un altre `EntityManager` i ampliar el projecte per canviar-lo de departament. Observa quan apareixen els `SELECT`, l’`INSERT` i l’`UPDATE`.

### Prepara el projecte

1. Obri la [carpeta del projecte](hibernate-empresa/) com a projecte Maven, amb JDK 17 o posterior.
2. Executa des de la carpeta que conté el `pom.xml`.

   ```bash
   mvn compile exec:java
   ```

   També pots executar la classe `ada.empresa.Practica` des de l'IDE si el directori de treball és el del `pom.xml`.
3. En la primera execució, el programa copia les dades inicials dels exercicis a `empresa.sqlite`. Per usar les dades que vas preparar amb JDBC, posa **una còpia** del teu fitxer ampliat amb `Departaments` i `Empleats.IdDepartament` en la carpeta del projecte amb el nom `empresa.sqlite`, abans de la primera execució. Comprova que hi haja un departament amb identificador `2`. El programa llig el nom que tinga eixe departament en la teua còpia.

### Llig el mapatge

Obri `Empleat.java`. `@Entity` identifica la classe que gestionarà JPA, i `@Table(name = "Empleats")` indica quina taula li correspon. `@Id` marca el NIF com a identificador. El constructor protegit sense paràmetres permet a JPA crear la instància quan llig una fila.

En la relació, `@ManyToOne` indica que molts empleats poden tindre el mateix departament i `@JoinColumn` indica la columna que guarda la clau forana.

```java
@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "IdDepartament")
private Departament departament;
```

En `Departament.java`, `@GeneratedValue(strategy = GenerationType.IDENTITY)` indica que l'identificador d'un departament nou es genera en inserir la fila. En canvi, el NIF el proporciona el programa en construir l'empleat.

### Guarda l'empleat

En `Practica.java`, el `main` crea una sola fàbrica amb `Persistence.createEntityManagerFactory("empresa")`. El nom `empresa` identifica la unitat definida en `persistence.xml`. El `main` passa la mateixa fàbrica als dos mètodes. Cada mètode obri el seu propi `EntityManager`.

```java
private static void guardaEmpleat(EntityManagerFactory emf) {
    try (EntityManager em = emf.createEntityManager()) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {
            if (em.find(Empleat.class, NIF) == null) {
                Departament departament = em.find(Departament.class, 2);
                if (departament == null) {
                    throw new IllegalStateException("Falta el departament amb identificador 2");
                }

                Empleat empleat = new Empleat(NIF, "Aina", "Ferrandis Roig", 2200.0, departament);
                em.persist(empleat);
            }
            tx.commit();
        } catch (RuntimeException error) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw error;
        }
    }
}
```

`NIF` és la constant de `Practica` amb el valor `"555555555"`.

1. `em.getTransaction()` obté la transacció de la unitat configurada com a `RESOURCE_LOCAL`. `begin()` la inicia abans de fer l'escriptura.
2. El primer `find()` comprova si l'empleat ja existix. Si encara no està guardat, el segon recupera el departament amb identificador `2`. En crear l'empleat, li passem l'objecte `Departament`. El mapatge indica a Hibernate que ha de guardar el seu identificador en `Empleats.IdDepartament`.
3. `persist(empleat)` fa que l'objecte nou passe a estar gestionat. En esta pràctica, quan cridem `commit()`, Hibernate envia l'`INSERT` pendent i es confirma la transacció.
4. Si hi ha una excepció i la transacció continua activa, `rollback()` revertix els canvis de la transacció. El mètode torna a llançar l'error perquè es veja què ha fallat. El bloc `try` tanca l'`EntityManager` quan acaba.

### Recupera l'empleat

El `main` crida `mostraEmpleat()` després de `guardaEmpleat()`. Ara obrim un altre `EntityManager` per comprovar que la fila es pot tornar a llegir després de confirmar la inserció.

```java
private static void mostraEmpleat(EntityManagerFactory emf) {
    try (EntityManager em = emf.createEntityManager()) {
        Empleat empleat = em.find(Empleat.class, NIF);
        Departament departament = empleat.getDepartament();
        String nomDepartament = departament == null ? "Sense departament" : departament.getNom();
        System.out.printf("%s treballa a %s%n", empleat.getNom(), nomDepartament);
    }
}
```

`find()` recupera l'empleat per NIF. La relació amb el departament usa `LAZY`. Hibernate pot esperar fins a `getNom()` per carregar les dades del departament. Per això consultem el nom abans de tancar l'`EntityManager`. Si l'empleat no té departament, el mètode mostra `Sense departament`.

### Comprova el resultat

Amb les dades inicials, el missatge de l'aplicació és:

```text
Aina treballa a Desenvolupament
```

- Localitza l'`INSERT` de la primera execució i comprova amb SQLite que la fila té el NIF `555555555` i la clau forana `2`.
- Torna a executar el programa i comprova que es mostra el mateix missatge. La comprovació amb `find()` evita que la pràctica inserisca una segona fila amb el mateix NIF.
- Canvia el NIF de prova per un que encara no existisca i usa l'identificador `1`. Prediu quin departament apareixerà amb les dades inicials i comprova-ho.

### Canvia l'empleat de departament

Completa este exercici amb la còpia de treball de SQLite. Si has canviat el NIF en la prova anterior, torna a usar el NIF `555555555` o adapta totes les comprovacions al NIF que has triat.

1. Afig en `Empleat` un mètode `setDepartament(Departament departament)` que assigne el nou valor a l'atribut.
2. Escriu un mètode `canviaDepartament` en `Practica`. Usa l'estructura de `guardaEmpleat` per obrir l'`EntityManager`, iniciar la transacció i gestionar el `rollback()` si falla.
3. Dins de la transacció, recupera l'empleat per NIF i el departament amb identificador `1`. Si alguna de les dues files falta, comunica l'error i revertix la transacció. Si les trobes, assigna el departament a l'empleat gestionat i confirma els canvis.
4. Crida el mètode entre `guardaEmpleat` i `mostraEmpleat`. Amb les dades inicials, el programa ha de mostrar `Aina treballa a Recursos Humans`. Busca l'`UPDATE` en la consola quan el valor passe de `2` a `1`.

Comprova el resultat també en SQLite.

```sql
SELECT NIF, Nom, IdDepartament
FROM Empleats
WHERE NIF = '555555555';
```

La consulta ha de retornar una sola fila i `IdDepartament` ha de valdre `1`. Compara el resultat amb l'`UPDATE` de la consola.

- Quina instrucció Java ha canviat el departament? Quina columna de SQLite guarda el vincle?
- Per què Hibernate genera l'`UPDATE` en confirmar la transacció si l'exercici no torna a cridar `persist()`?

### El mateix canvi amb Spring Boot

Quan fem l'API REST, recuperarem este mateix exercici. El controlador rebrà la petició HTTP i el servei s'encarregarà de canviar el departament. Podrem reutilitzar les anotacions d'`Empleat` i `Departament`.

| Pas | En la pràctica amb JPA | En l'API amb Spring Boot i Spring Data JPA |
| --- | --- | --- |
| Preparar la persistència | El programa crea la fàbrica a partir de `persistence.xml` i obri l'`EntityManager`. | Spring Boot configura la infraestructura de JPA a partir de la configuració de l'aplicació. |
| Recuperar les entitats | El programa crida `em.find()` per a cada entitat. | El servei crida `findById()` en els repositoris de Spring Data JPA. |
| Delimitar la transacció | El programa crida `begin()`, `commit()` o `rollback()`. | El servei delimita la transacció amb `@Transactional` i Spring la gestiona. |
| Guardar el canvi | El programa assigna el departament a l'empleat gestionat. Hibernate detecta el canvi i genera l'`UPDATE`. | El servei fa la mateixa assignació dins de la transacció. Hibernate continua detectant el canvi i generant l'`UPDATE`. |

Fer primer l'operació amb `EntityManager` ens permet reconéixer què passa en la base de dades quan el servei treballe amb un `JpaRepository`.

Si vols saber què hi ha darrere de la configuració preparada, consulta l'[annex de configuració]({% link 2BDARelacionals/4AnnexHibernate.md %}). En el [punt 4.2]({% link 2BDARelacionals/42JPA.md %}) veurem com convertir el mapatge anterior en una relació bidireccional i com representar altres cardinalitats.

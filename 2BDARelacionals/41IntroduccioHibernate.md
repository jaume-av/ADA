---
title: "4.1. Introducció i pràctica guiada"
parent: "4. Persistència amb JPA i Hibernate"
grand_parent: Persistència en Base de Dades
layout: default
nav_order: 10
---

# 4.1. Introducció i pràctica guiada

## Del model relacional als objectes

En SQLite, un empleat pertany a un departament perquè la columna `Empleats.IdDepartament` conté l'identificador d'una fila de `Departaments`. Per a obtindre el nom del departament amb SQL podem fer un `JOIN`. En Java ens interessa representar eixa relació amb un atribut de tipus `Departament` i poder escriure `empleat.getDepartament().getNom()` si l'empleat té departament.

Esta diferència entre la manera com es representen les dades en Java i en les taules s'anomena **desfasament objecte-relacional**. Apareix també amb la identitat. Dos objectes Java diferents poden representar la mateixa fila si tenen el mateix NIF.

Un **ORM** (*object-relational mapping*, o mapatge objecte-relacional) utilitza un mapatge per a convertir files en objectes i traslladar canvis dels objectes a la base de dades. Per exemple, li indiquem que `Empleat` correspon a la taula `Empleats`, que `nif` correspon a la seua clau primària i que `departament` es guarda mitjançant una clau forana. L'ORM genera i executa SQL a través de JDBC.

## JPA i Hibernate

**Jakarta Persistence (JPA)** és l'especificació que definix les anotacions del mapatge i l'API de persistència. En els exemples, les anotacions i classes de l'API s'importen de `jakarta.persistence`.

**Hibernate ORM** és un proveïdor que implementa JPA. Llig les anotacions, gestiona les entitats i genera SQL. El **controlador JDBC de SQLite** obri la connexió amb el fitxer de base de dades. El **dialecte** de SQLite indica a Hibernate quines formes de SQL pot utilitzar amb eixa base de dades. [Guia de Hibernate ORM](https://docs.hibernate.org/orm/6.6/introduction/html_single/)

En esta pràctica executarem JPA en una aplicació Java amb un `main`. Més avant, en crear l'API REST amb Spring Boot, podrem reutilitzar el mapatge de les entitats i accedir a les dades amb Spring Data JPA.

## Pràctica guiada. Guarda i recupera un empleat

**Objectiu.** Guardar un empleat en la mateixa estructura de dades dels exercicis de JDBC i recuperar-lo en un context de persistència nou. Observa quan apareixen l'`INSERT` i els `SELECT`.

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

### Seguix l'execució

1. `Persistence.createEntityManagerFactory("empresa")` carrega la unitat de persistència del fitxer `persistence.xml`. La fàbrica es crea una vegada i s'utilitza per a obrir els `EntityManager` necessaris.
2. En `guardaEmpleat`, el programa obri un `EntityManager` i inicia una transacció. Si el NIF de prova encara no existix, recupera el departament `2` amb `find()` i construïx l'empleat amb una referència a eixe objecte.
3. `persist()` incorpora l'empleat al context de persistència. En confirmar la transacció amb `commit()`, Hibernate sincronitza els canvis pendents amb SQLite. Si hi ha una excepció i la transacció continua activa, el programa fa `rollback()`.
4. En `mostraEmpleat`, el programa obri un altre `EntityManager` i llig l'empleat per NIF. Obri el departament associat mentre el context encara està actiu, perquè Hibernate pot ajornar la càrrega fins que es consulta el nom.

### Comprova el resultat

Amb les dades inicials, el missatge de l'aplicació és:

```text
Aina treballa a Desenvolupament
```

- Localitza l'`INSERT` de la primera execució i comprova amb SQLite que la fila té el NIF `555555555` i la clau forana `2`.
- Torna a executar el programa i comprova que es mostra el mateix missatge. La comprovació amb `find()` evita que la pràctica inserisca una segona fila amb el mateix NIF.
- Canvia el NIF de prova per un que encara no existisca i usa l'identificador `1`. Prediu quin departament apareixerà amb les dades inicials i comprova-ho.

Si vols saber què hi ha darrere de la configuració preparada, consulta l'[annex de configuració]({% link 2BDARelacionals/4AnnexHibernate.md %}). En el [punt 4.2]({% link 2BDARelacionals/42JPA.md %}) veurem com convertir el mapatge anterior en una relació bidireccional i com representar altres cardinalitats.

---
title: "Annex. Configuració de Hibernate amb SQLite"
parent: "4. Persistència amb JPA i Hibernate"
grand_parent: Persistència en Base de Dades
layout: default
nav_order: 30
---

# Annex. Configuració de Hibernate amb SQLite

El projecte de la pràctica ja està configurat. Pots seguir estos passos per a reproduir la configuració en un projecte Maven Java SE.

## 1. Crea el projecte i afig les dependències

Crea un projecte Maven amb JDK 17 o posterior. Configura `maven.compiler.release` a `17` en el `pom.xml` i indica UTF-8 com a codificació. Afig les dependències següents dins de `<dependencies>`.

| Dependència | Versió en la pràctica | Funció |
| --- | --- | --- |
| `org.hibernate.orm:hibernate-core` | `6.6.59.Final` | Aporta el proveïdor Hibernate i incorpora l'API de Jakarta Persistence com a dependència transitiva. |
| `org.hibernate.orm:hibernate-community-dialects` | `6.6.59.Final` | Conté `SQLiteDialect`. Usa la mateixa versió que `hibernate-core`. |
| `org.xerial:sqlite-jdbc` | `3.50.3.0` | Proporciona el controlador JDBC per a obrir el fitxer SQLite. |

Pots copiar les dependències i els connectors de compilació i execució del [pom.xml del projecte](hibernate-empresa/pom.xml). El dialecte de SQLite es distribuïx en el mòdul de dialectes comunitaris de Hibernate. [Dialectes de Hibernate ORM](https://docs.hibernate.org/orm/6.6/dialect/)

## 2. Prepara la base de dades

El fitxer de treball es diu `empresa.sqlite` i va al directori del `pom.xml`. Pots copiar-hi la base de dades dels exercicis JDBC una vegada feta l'ampliació de departaments, o executar [empresa.sql](hibernate-empresa/empresa.sql) sobre una base de dades buida. Amb el client de línia d'ordes de SQLite, la segona opció es pot fer així des de la carpeta que conté l'script.

```bash
sqlite3 empresa.sqlite < empresa.sql
```

L'script crea les dues taules i inserix les files dels exercicis. En el projecte preparat, `src/main/resources/empresa-inicial.sqlite` conté una base creada amb eixe script. La classe `Practica` la copia al directori de treball si allí encara no hi ha cap `empresa.sqlite`.

## 3. Declara la unitat de persistència

Crea `src/main/resources/META-INF/persistence.xml`. En el [fitxer de la pràctica](hibernate-empresa/src/main/resources/META-INF/persistence.xml) trobaràs esta configuració:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<persistence xmlns="https://jakarta.ee/xml/ns/persistence" version="3.0">
    <persistence-unit name="empresa" transaction-type="RESOURCE_LOCAL">
        <provider>org.hibernate.jpa.HibernatePersistenceProvider</provider>
        <class>ada.empresa.Empleat</class>
        <class>ada.empresa.Departament</class>
        <exclude-unlisted-classes>true</exclude-unlisted-classes>
        <properties>
            <property name="jakarta.persistence.jdbc.driver" value="org.sqlite.JDBC"/>
            <property name="jakarta.persistence.jdbc.url" value="jdbc:sqlite:empresa.sqlite?foreign_keys=on"/>
            <property name="jakarta.persistence.schema-generation.database.action" value="none"/>
            <property name="hibernate.dialect" value="org.hibernate.community.dialect.SQLiteDialect"/>
            <property name="hibernate.show_sql" value="true"/>
            <property name="hibernate.format_sql" value="true"/>
        </properties>
    </persistence-unit>
</persistence>
```

- **Unitat de persistència.** `empresa` és el nom que passarem a `Persistence.createEntityManagerFactory()`. `RESOURCE_LOCAL` permet a l'aplicació gestionar les transaccions amb `EntityTransaction`.
- **Entitats.** Escriu el nom complet de cada classe. Amb `exclude-unlisted-classes`, registrem explícitament les classes de la pràctica.
- **Connexió.** La URL usa una ruta relativa al directori des d'on s'executa Java. `foreign_keys=on` demana al controlador que active la comprovació de claus foranes en les connexions de la pràctica.
- **Esquema.** Amb `database.action=none`, les taules es preparen amb la còpia de JDBC o amb l'script SQL.
- **SQL visible.** `hibernate.show_sql` mostra les sentències en la consola i `hibernate.format_sql` en facilita la lectura.

## 4. Crea les entitats i comprova la connexió

1. Afig les classes `Empleat` i `Departament` a `src/main/java/ada/empresa`. Els noms de la taula i les columnes de les anotacions han de coincidir amb els de SQLite.
2. Des del `main`, crea la fàbrica de la unitat `empresa`, obri un `EntityManager` i recupera una fila coneguda amb `em.find(Empleat.class, "123456789")`. Amb les dades inicials ha de retornar l'empleat Pep. Tanca el gestor i la fàbrica després de la prova.
3. Executa la [pràctica guiada]({% link 2BDARelacionals/41IntroduccioHibernate.md %}) per comprovar també l'escriptura dins d'una transacció.

Si apareix `no such table: Empleats`, comprova el directori de treball i obri el fitxer que està usant el programa. SQLite pot crear un fitxer buit quan la ruta és incorrecta. Si no es troba la unitat `empresa`, comprova el nom del fitxer, la carpeta `META-INF` i que l'IDE haja importat el projecte Maven.

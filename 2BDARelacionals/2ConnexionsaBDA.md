---
title: 2.- Tipus de Connexions a Bases de Dades
parent: Persistència en Base de Dades

has_children: true
layout: default
nav_order: 10
---



# Tipus de Connexions a Bases de Dades

---


Les bases de dades són bàsiques en el desenvolupament d'aplicacions, ja que permeten **emmagatzemar, gestionar i recuperar dades** de manera eficient.    
La connexió entre una aplicació i una base de dades es realitza a través de diversos mètodes i tecnologies.      

A continuació es mostren els t**ipus generals de connexió** a una base de dades, **independentment del llenguatge de programació** utilitzat.

---

## **Tipus de Connexions a Bases de Dades**

### **1.- Connexió Directa** (Connectors)

És el tipus més bàsic i tradicional de connexió. L'aplicació interactua directament amb la base de dades utilitzant un controlador (**driver**).

- **Context**: Desenvolupament d'aplicacions petites o monousuari.
- **Característiques**:
  - Requereix un driver compatible amb el sistema de base de dades.
  - Utilitza credencials i URLs de connexió específiques (per exemple, `jdbc:mysql://localhost:3306/mi_base` en Java).
- **Avantatges**:
  - Simple d'implementar.
  - Baix nivell d'abstracció, ideal per a aprenentatge.
- **Desavantatges**:
  - Difícil d'escalar.
  - **No gestiona la concurrència** de manera eficient. (multiples usuaris i processos)

---

### **2.- Pool de Connexions**

Un pool de connexions és un mecanisme que permet **reutilitzar connexions prèviament establertes** en lloc de **crear i tancar** noves connexions per a cada sol·licitud.

- **Context**: Aplicacions web d'alt rendiment.
- **Llibreries populars**:
  - **HikariCP** (Java): Alta velocitat i eficiència.
  - **Apache DBCP**: Molt usada en projectes **legacy** (software que ha quedat obsolet però que encara s'utilitza).
  - **C3P0**: Senzill i àmpliament conegut.
- **Avantatges**:
  - Millora el rendiment al reduir la càrrega de crear i destruir connexions.
  - Escalable per a aplicacions amb molt de tràfic.
- **Desavantatges**:
  - Més complexitat en la configuració inicial.
  - Depèn de llibreries externes.

---

### **3.- Connexió Basada en ORM (Object-Relational Mapping)**

L'**ORM** és una tècnica que **mapeja classes i objectes** del llenguatge de programació **amb taules i registres** d'una base de dades.

- **Context**: Aplicacions de mitjana o gran escala.
- **Eines més populars**:
  - **Hibernate** (Java).
  - **Entity Framework** (.NET).
  - **SQLAlchemy** (Python).
- **Avantatges**:
  - Redueix la necessitat d'escriure SQL explícit.
  - Simplifica la programació utilitzant **objectes en lloc de taules**.
- **Desavantatges**:
  - Corba d'aprenentatge alta.
  - Pot introduir una càrrega addicional de rendiment,per la traducció d'objectes a consultes SQL.

---

### **4.- Connexió a través d'APIs REST**

Moltes bases de dades modernes proporcionen APIs per a interactuar amb els seus serveis. Aquestes APIs solen ser RESTful o basades en gRPC.

- **Context**: Bases de dades al núvol o distribuïdes.
- **Plataformes populars**:
  - **Firebase Realtime Database** i **Firestore** (Google).
  - **AWS DynamoDB**.
- **Avantatges**:
  - Ofereixen** alta disponibilitat i escalabilitat automàtica**.
  - Fàcil d'**integrar** en aplicacions **web** i **mòbils**.
- **Desavantatges**:
  - Latència més alta a causa de la comunicació per xarxa. (entenem per **latencia el temps que tarda una acció o sol·licitud a obtenir una resposta o completar-se**, es a dir, el temps de retard entre una entrada i la seua resposta)
  - Requereix un **maneig addicional d'autenticació i permisos**.

---

### **5.- Model Client-Servidor**

Model que implica una comunicació directa entre un client (l'aplicació) i un servidor de base de dades.

- **Context**: Aplicacions empresarials o software legacy.
- **Bases de dades comunes**:
  - **Oracle Database**.
  - **Microsoft SQL Server**.
- **Avantatges**:
  - Permet una administració centralitzada de la base de dades.
  - Adequat per a entorns multiusuari.
- **Desavantatges**:
  - Depèn d'una xarxa estable.
  - Pot ser lent en xarxes de baixa qualitat.

---

## **6.- Bases de Dades Distribuïdes**

Les bases de dades distribuïdes permeten connexions a través de múltiples nodes per aconseguir alta disponibilitat i escalabilitat.

- **Context**: Sistemes globals i aplicacions modernes amb gran volum de dades.
- **Bases de dades comunes**:
  - **Apache Cassandra**.
  - **MongoDB**.
  - **Google Spanner**.
- **Avantatges**:
  - Escalabilitat horitzontal i tolerància a fallades.
  - Compatibilitat amb llenguatges moderns com Python, JavaScript, i Java.
- **Desavantatges**:
  - Major complexitat en la configuració i manteniment.

---

## **Taula Comparativa**

| **Tipus de Connexió**    | **Avantatges**                                  | **Desavantatges**                           | **Casos d'Ús**                             |
|--------------------------|------------------------------------------------|--------------------------------------------|--------------------------------------------|
| **Connexió Directa**      | Fàcil d'implementar                            | Difícil d'escalar                           | Aplicacions petites o prototips            |
| **Pool de Connexions**    | Rendiment optimitzat                           | Configuració inicial complexa              | Aplicacions web amb alt tràfic             |
| **ORM**                  | Reducció del codi SQL, més productivitat       | Sobrecàrrega de rendiment                  | Aplicacions empresarials                   |
| **APIs REST**            | Ideal per a bases de dades al núvol            | Latència més alta                          | Aplicacions mòbils/web al núvol            |
| **Client-Servidor**      | Administració centralitzada                    | Depèn d'una xarxa estable                  | Sistemes empresarials                      |
| **Bases Distribuïdes**   | Alta disponibilitat i escalabilitat            | Complexitat elevada                        | Bases de dades globals o distribuïdes      |


## Taula comparativa llenguatges de programació

---

### **Tipus de Connexió i Eines Més Usades per Llenguatge de Programació**

| **Llenguatge**    | **Connexió Directa**                  | **Pool de Connexions**             | **ORM**                              | **APIs REST**                | **Bases Distribuïdes**               | **Middleware**                     |
|-------------------|---------------------------------------|-------------------------------------|--------------------------------------|--------------------------------|--------------------------------------|-------------------------------------|
| **Java**          | JDBC                                 | HikariCP, Apache DBCP, C3P0        | Hibernate, JPA                      | HttpClient, OkHttp             | Cassandra Driver, MongoDB Driver     | Spring Framework, JBoss Middleware |
| **Python**        | sqlite3, psycopg2, MySQL Connector   | SQLAlchemy, aiomysql, asyncpg      | SQLAlchemy, Django ORM              | Requests, httpx                | pymongo, cassandra-driver            | FastAPI, Django Middleware          |
| **C#**            | ADO.NET                              | Entity Framework                   | Entity Framework                    | HttpClient                     | MongoDB.Driver, CassandraSharp       | ASP.NET Middleware                  |
| **PHP**           | PDO, MySQLi                          | Laravel Database                   | Eloquent (Laravel)                  | Guzzle                         | MongoDB Driver, Cassandra PHP Driver | Laravel Middleware                  |
| **Node.js**       | `mysql2`, `pg`                       | `pg-pool`, `generic-pool`          | Sequelize, TypeORM                  | Axios, Fetch, `node-fetch`     | Mongoose, Cassandra Driver           | Express Middleware                  |
| **Ruby**          | `pg`, `mysql2`                       | Active Record                      | Active Record                       | Faraday, HTTParty              | `mongo`, `cassandra-driver`          | Rack Middleware                     |
| **Go**            | `database/sql`                      | `pgx`                              | GORM                                | `net/http`, `resty`            | `mongo-go-driver`, `gocql`           | Middleware personalitzat            |
| **C/C++**         | ODBC, MySQL C API                   | Gestió manual                      | No és habitual                      | Llibreries HTTP natives         | MongoDB C Driver                     | Middleware específic                |

---


- **Connexió Directa**: Llibreries i eines per establir connexions directes amb la base de dades.
- **Pool de Connexions**: Llibreries que milloren el rendiment reutilitzant connexions.
- **ORM**: Llibreries per al mapatge objecte-relacional.
- **APIs REST**: Eines per interactuar amb bases de dades a través de serveis web.
- **Bases Distribuïdes**: Llibreries per treballar amb bases de dades escalables i distribuïdes.
- **Middleware**: Frameworks o eines per a la gestió d'intermediaris en aplicacions.

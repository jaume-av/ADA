---
title: 1. introducció a les Bases de Dades. 
parent: Persistència en Base de Dades

has_children: true
layout: default
nav_order: 10
---




# **Introducció a les Bases de Dades**

---

Les **bases de dades** són sistemes que permeten **emmagatzemar, gestionar i recuperar dades** de **manera organitzada** i eficient. Són una peça fonamental en el desenvolupament de programari, ja que permeten treballar amb grans volums d'informació de manera estructurada, segura i accessible.

---
Una base de dades és una **col·lecció d'informació organitzada** de manera que **pot ser fàcilment accedida, gestionada i actualitzada**. Pot contenir dades de molts tipus, com ara números, textos, imatges o qualsevol altra informació que necessite ser emmagatzemada.

- **Exemple bàsic**: Un registre d'una escola podria contindre dades d'estudiants (noms, edats, qualificacions).
- **Objectiu**: Facilitar l'accés i la manipulació de dades de manera eficient i coherent.

---

## **2. Tipus de Bases de Dades**

### **2.1. Bases de Dades Relacionals**
- Emmagatzemen dades en taules formades per files i columnes (estructures tabulars). Les dades es poden relacionar entre si mitjançant claus primàries i estrangeres.  
- **Per exemple**: MySQL, PostgreSQL, Oracle Database, Microsoft SQL Server.  
 -**S'utilitza** en aplicacions empresarials, comerç electrònic o gestió d'usuaris.  
-**Característiques**: Utilitzen SQL (Structured Query Language) per accedir i gestionar dades.

#### **2.2. Bases de Dades NoSQL**
- Dissenyades per gestionar dades no estructurades o semi-estructurades. Són més flexibles que les relacionals.  

**Tipus principals:** 
- **Documents**: Emmagatzemen dades en formats com **JSON** o **BSON**. Per exemple: **MongoDB**.  
- **Clau-valor**: Cada dada es guarda com una **parella clau-valo**r. Per exemple: **Redis**.  
- **Columnes àmplies**: Optimitzades per a **grans volums de dades** distribuïdes. Per exemple: **Cassandra**.  
- **Gràfics**: Emmagatzemen relacions complexes entre dades. Per exemple: **Neo4j**.  
**S'utilitza** en aplicacions en temps real, sistemes de recomanació, **Big Data**.

#### **2.3. Bases de Dades Jeràrquiques**
- Organitzen les dades en una estructura d'arbre, amb nodes i relacions de pare-fill.  
- **Per exemple**: IBM Information Management System (**IMS**).  
- **S'utilitza** en **sistemes antics o legacy**, com aplicacions financeres o logístiques.

#### **2.4. Bases de Dades en Xarxa**
- Són una evolució de les jeràrquiques, on un node pot tindre múltiples relacions amb altres nodes.  
- **Per exemple**: Base de dades IDMS.  
- **Utilizades** en sistemes de gestió d'inventaris o cadenes de subministrament.

#### **2.5. Bases de Dades Orientades a Objectes**
- Integren els principis de la programació orientada a objectes (POO) amb les bases de dades. Les dades es guarden com a objectes.  
- **Per exemple**: ObjectDB, db4o.  
- **S'utilitzen** en aplicacions complexes amb dades relacionals i no relacionals.

#### **2.6. Bases de Dades Distribuïdes**
-Les dades es reparteixen entre diversos servidors o nodes, però es gestionen com una única base de dades.  
-**Per exemple**: Cassandra, Google Spanner.  
-**S'utilitza** en sistemes globals, alta disponibilitat, Big Data.

#### **2.7. Bases de Dades en Memòria**
- Emmagatzemen dades directament a la memòria RAM per a un accés ultra ràpid.  
- **Per exemple**: Redis, Memcached.  
- **S'utilitza** en caché de dades, aplicacions en temps real.

#### **2.8. Bases de Dades en el Núvol**
- Emmagatzemen dades en servidors al núvol, accessibles via internet.  
- **Per exemple**: Firebase, Amazon RDS.  
- **Utilitzades** en aplicacions modernes i escalables.

---

### **3. Components d'una Base de Dades**

1. **Dades**: Informació emmagatzemada (per exemple, noms d'usuaris, productes).
2. **Sistema de Gestió de Bases de Dades (SGBD)**: Programari que gestiona les bases de dades.
   - Per exemple: **MySQL**, **PostgreSQL**, **MongoDB**.
3. **Usuari**: Persona o aplicació que accedeix a les dades.
4. **Interfície d'Usuari**: Mitjà per accedir a les bases de dades, com aplicacions o consultes SQL.

---

### **4. Beneficis de les Bases de Dades**

- **Organització**: Permeten estructurar dades de manera lògica.
- **Accessibilitat**: Faciliten l'accés ràpid i eficient a grans volums de dades.
- **Integritat**: Mantenen la coherència i precisió de les dades.
- **Seguretat**: Protegeixen les dades mitjançant permisos, encriptació i còpies de seguretat.
- **Escalabilitat**: Les bases modernes poden créixer segons les necessitats del sistema.

---

### **5. Aplicacions de les Bases de Dades**

- Sistemes de comerç electrònic (Amazon, eBay).
- Xarxes socials (Facebook, Instagram).
- Gestió empresarial (ERP, CRM).
- Emmagatzematge en el núvol (Google Drive, Dropbox).
- Sistemes d'anàlisi de dades i Big Data.

---




---

## 3. Bases de Dades Entitat-Relació (ER)

Les bases de dades basades en el model **Entitat-Relació (ER)** són una metodologia per representar l'estructura lògica de les dades mitjançant conceptes com **entitats, relacions i atributs.** Aquest model permet dissenyar bases de dades relacionalment, reflectint clarament les relacions entre les dades abans d'implementar-les en un sistema de gestió de bases de dades (SGBD).

#### **Conceptes Principals del Model ER**
1. **Entitat**:
   - Representa un objecte del món real amb característiques identificables.
   - **Exemple**: En una base de dades d'estudiants, una entitat podria ser "Estudiant".

2. **Atribut**:
   - És una propietat o característica de l'entitat.
   - **Exemple**: Per a l'entitat "Estudiant", els atributs podrien ser "Nom", "Cognoms", "Edat".

3. **Relació**:
   - Defineix com interactuen o es connecten les entitats entre si.
   - **Exemple**: Una relació "Estudia" pot connectar l'entitat "Estudiant" amb l'entitat "Assignatura".

4. **Clau Primària**:
   - És un atribut que identifica de manera única cada instància d'una entitat.
   - **Exemple**: L'atribut "ID Estudiant" pot ser la clau primària de l'entitat "Estudiant".

5. **Cardinalitat**:
   - Indica la quantitat de connexions possibles entre entitats.
   - **Exemple**: Un estudiant pot estar matriculat en moltes assignatures (relació 1:N).

#### **Avantatges del Model ER**
- **Claredat**: Permet visualitzar fàcilment les dades i les seves relacions.
- **Organització**: Facilita la planificació abans de crear una base de dades.
- **Flexibilitat**: Es pot adaptar a bases de dades més complexes.

---

## 4. SQL (Structured Query Language)

**SQL** és un llenguatge estàndard utilitzat per interactuar amb bases de dades relacionals. Amb SQL, els usuaris poden crear, modificar, consultar i gestionar dades de manera eficient. És compatible amb la majoria dels sistemes de gestió de bases de dades, com MySQL, PostgreSQL, Oracle Database i SQL Server.

#### **Categories Principals d'Ordres SQL**
1. **DML (Data Manipulation Language)**:
   - Per gestionar dades dins de les taules.
   - **Ordres principals**:
     - `SELECT`: Recuperar dades.
     - `INSERT`: Afegir dades.
     - `UPDATE`: Actualitzar dades.
     - `DELETE`: Eliminar dades.
   - **Exemple**:
     ```sql
     SELECT Nom, Cognoms FROM Estudiants WHERE Edat > 18;
     ```

2. **DDL (Data Definition Language)**:
   - Per definir i modificar l'estructura de la base de dades.
   - **Ordres principals**:
     - `CREATE`: Crear taules, esquemes o bases de dades.
     - `ALTER`: Modificar taules existents.
     - `DROP`: Eliminar taules o bases de dades.
   - **Exemple**:
     ```sql
     CREATE TABLE Estudiants (
         ID INT PRIMARY KEY,
         Nom VARCHAR(50),
         Cognoms VARCHAR(50),
         Edat INT
     );
     ```

3. **DCL (Data Control Language)**:
   - Per gestionar permisos i control d'accés.
   - **Ordres principals**:
     - `GRANT`: Atorgar permisos.
     - `REVOKE`: Revocar permisos.

4. **TCL (Transaction Control Language)**:
   - Per gestionar transaccions.
   - **Ordres principals**:
     - `COMMIT`: Confirma els canvis.
     - `ROLLBACK`: Reverteix els canvis.
   - **Exemple**:
     ```sql
     BEGIN TRANSACTION;
     UPDATE Estudiants SET Edat = 20 WHERE ID = 1;
     COMMIT;
     ```

#### **Característiques Principals de SQL**
- **Declaratiu**: Permet als usuaris descriure què volen fer amb les dades, sense haver de definir com fer-ho.
- **Estàndard**: És reconegut i utilitzat àmpliament en bases de dades relacionals.
- **Potent**: Pot gestionar grans volums de dades i relacions complexes.

---

### **Relació entre el Model ER i SQL**

- El **Model ER** es fa servir per dissenyar l'estructura conceptual de les dades abans de crear-les.
- **SQL** és l'eina que implementa aquest disseny en una base de dades relacional.

#### **Procés típic**:
1. **Disseny amb el Model ER**: Es defineixen entitats, atributs i relacions.
2. **Creació amb SQL**: S'utilitzen ordres com `CREATE TABLE` per construir les taules basades en el model ER.
3. **Manipulació i Consulta amb SQL**: Es fan servir ordres com `INSERT`, `SELECT`, `UPDATE` per gestionar les dades.

---

[Resum sintaxi SQL](2BDARelacionals/Repas SQL.pdf)
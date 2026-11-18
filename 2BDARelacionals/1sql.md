---
title: 1. introducció a les Bases de Dades. 
parent: Persistència en Base de Dades

has_children: true
layout: default
nav_order: 10
---

# Introducció a les bases de dades i a JDBC

Fins ara hem treballat amb **fitxers** per a emmagatzemar i recuperar informació. Hem utilitzat la classe **`File`**, els fluxos d'entrada i eixida (*streams*) i Jackson per a llegir i escriure documents JSON i XML.

Ara farem un pas més: **accedir a les dades emmagatzemades en una base de dades des d'un programa Java**.

## 1. De fitxers a bases de dades

Imaginem una aplicació que gestiona els productes d'una botiga d'informàtica.

Podríem guardar-los en un document JSON:

```json
{
  "productes": [
    {"id": 1, "nom": "Ratolí", "preu": 19.95},
    {"id": 2, "nom": "Teclat", "preu": 49.90},
    {"id": 3, "nom": "Monitor", "preu": 129.99}
  ]
}
```

Amb Jackson podem llegir aquest document, convertir-lo en objectes Java, modificar-los i tornar a guardar el fitxer.

Aquest procediment és perfectament vàlid, però pot resultar poc pràctic quan necessitem consultar, actualitzar i relacionar moltes dades, especialment si diversos usuaris treballen amb la mateixa informació.

Les **bases de dades** proporcionen mecanismes per a realitzar aquestes operacions de manera controlada.

---

## 2. Bases de dades relacionals i model entitat-relació

Les **bases de dades relacionals** organitzen la informació en taules formades per files i columnes. Les taules es relacionen mitjançant claus primàries i alienes.

El **model entitat-relació (ER)** permet representar les dades i les seues relacions abans d'implementar-les en una base de dades relacional.

### Conceptes principals

- **Entitat:** objecte o concepte del qual volem emmagatzemar informació.
- **Atribut:** característica d'una entitat.
- **Relació:** associació entre dues o més entitats.
- **Clau primària (PK):** atribut o conjunt d'atributs que identifica de manera única cada registre.
- **Clau aliena (FK):** atribut o conjunt d'atributs que permet relacionar registres de diferents taules.
- **Cardinalitat:** indica com es relacionen les entitats segons el nombre d'instàncies: **1:1, 1:N i N:M**.

### Avantatges del model relacional

- **Organització:** les dades s'estructuren en taules relacionades.
- **Integritat:** les claus i les restriccions ajuden a mantindre la coherència de les dades.
- **Consultes:** SQL permet recuperar, relacionar i modificar informació.
- **Concurrència:** els SGBD proporcionen mecanismes per a gestionar l'accés de diversos usuaris.

---

## 3. SQL: consulta i manipulació de dades (DML)

**SQL (Structured Query Language)** és el llenguatge utilitzat per a definir, consultar i modificar dades en bases de dades relacionals.

Les instruccions que permeten consultar i manipular els registres són:

- **`SELECT`**: consultar registres.
- **`INSERT INTO`**: afegir registres.
- **`UPDATE`**: modificar registres.
- **`DELETE FROM`**: eliminar registres.

Recordem les instruccions principals amb exemples sobre una taula anomenada `estudiants`, que conté els camps `id`, `nom`, `cognoms` i `edat`.

### 3.1. `SELECT`: consultar registres

La instrucció `SELECT` permet recuperar informació d'una o diverses taules.

**Consultar tots els estudiants:**

```sql
SELECT * FROM estudiants;
```

El símbol `*` indica que volem recuperar totes les columnes.

**Consultar només el nom i els cognoms:**

```sql
SELECT nom, cognoms
FROM estudiants;
```

**Consultar estudiants majors d'edat:**

```sql
SELECT nom, cognoms
FROM estudiants
WHERE edat >= 18;
```

La clàusula `WHERE` permet establir una condició per a seleccionar els registres.

En aquest últim exemple obtindríem únicament el nom i els cognoms dels estudiants que tenen 18 anys o més.

### 3.2. `INSERT`: afegir registres

La instrucció `INSERT INTO` permet incorporar nous registres a una taula.

**Afegir un estudiant:**

```sql
INSERT INTO estudiants (id, nom, cognoms, edat)
VALUES (5, 'Anna', 'Soler', 20);
```

Aquesta instrucció incorpora un nou registre amb identificador `5`, nom `Anna`, cognoms `Soler` i edat `20`, sempre que es complisquen les restriccions de la taula.

### 3.3. `UPDATE`: modificar registres

La instrucció `UPDATE` permet modificar dades que ja existixen.

**Modificar l'edat d'un estudiant:**

```sql
UPDATE estudiants
SET edat = 21
WHERE id = 5;
```

Si existix el registre amb identificador `5`, el valor del camp `edat` passa a ser `21`.

També podem modificar diversos camps:

```sql
UPDATE estudiants
SET nom = 'Anna Maria',
    edat = 22
WHERE id = 5;
```

**Important:** si no indiquem `WHERE`, la modificació pot afectar tots els registres de la taula.

Per exemple:

```sql
UPDATE estudiants
SET edat = 18;
```

Aquesta instrucció assignaria l'edat `18` a tots els estudiants.

### 3.4. `DELETE`: eliminar registres

La instrucció `DELETE` elimina registres d'una taula.

**Eliminar l'estudiant incorporat anteriorment:**

```sql
DELETE FROM estudiants
WHERE id = 5;
```

Després d'executar aquesta instrucció, el registre amb identificador `5` deixarà d'existir en la taula, si hi era.

Igual que amb `UPDATE`, hem de prestar atenció a `WHERE`.

```sql
DELETE FROM estudiants;
```

Aquesta instrucció eliminaria tots els registres de la taula, però no eliminaria la taula mateixa.

## 4. SQL: definició de l'estructura (DDL)

Les instruccions DDL permeten crear, modificar o eliminar l'estructura de les taules:

- **`CREATE TABLE`**: crear una taula.
- **`ALTER TABLE`**: modificar l'estructura d'una taula.
- **`DROP TABLE`**: eliminar una taula.

### 4.1. `CREATE TABLE`: definir l'estructura

Les instruccions anteriors treballen amb taules que ja existixen.

Per a crear una taula utilitzem `CREATE TABLE`.

**Exemple SQL:**

```sql
CREATE TABLE categories (
    id INTEGER PRIMARY KEY,
    nom VARCHAR(100) NOT NULL
);

CREATE TABLE productes (
    id INTEGER PRIMARY KEY,
    nom VARCHAR(100) NOT NULL,
    preu DECIMAL(10, 2) NOT NULL,
    estoc INTEGER NOT NULL,
    categoria_id INTEGER,
    FOREIGN KEY (categoria_id) REFERENCES categories(id)
);
```

Hem definit els camps, els tipus de dades i les claus necessàries per a relacionar les dues taules.

En SQL, `INTEGER` representa nombres enters, `VARCHAR(100)` permet emmagatzemar text amb una longitud màxima de 100 caràcters i `DECIMAL(10, 2)` representa valors decimals amb dos xifres després de la coma. La sintaxi exacta i els tipus disponibles poden variar segons el SGBD.

`PRIMARY KEY` identifica els registres, `NOT NULL` impedix emmagatzemar un valor nul en el camp corresponent i `FOREIGN KEY` definix la relació amb una altra taula.

### 4.2. `ALTER TABLE`: modificar l'estructura

Per a afegir una columna a una taula existent podem utilitzar `ALTER TABLE`:

```sql
ALTER TABLE productes
ADD COLUMN descripcio VARCHAR(200);
```

Aquesta instrucció afig la columna `descripcio` a la taula `productes`, sense eliminar-ne els registres existents.

### 4.3. `DROP TABLE`: eliminar una taula

Per a eliminar la taula i la seua estructura utilitzem `DROP TABLE`:

```sql
DROP TABLE productes;
```

A diferència de `DELETE FROM productes`, que elimina els registres, `DROP TABLE` elimina la taula mateixa. Aquesta operació pot comportar la pèrdua de totes les dades que conté.

### 4.4. Altres instruccions SQL

A més de consultar i modificar registres, SQL permet treballar amb altres operacions:

- **`COMMIT`:** confirmar els canvis d'una transacció.
- **`ROLLBACK`:** desfer els canvis d'una transacció que encara no s'ha confirmat.

Una **transacció** agrupa diverses operacions que han de tractar-se com una unitat. És especialment important quan necessitem que diversos canvis es completen conjuntament o que cap d'ells quede aplicat si es produïx un error.


# Tipus de Bases de Dades

![Bases de Dades](<imatges/Infografia_ tipus de bases de dades.png>)
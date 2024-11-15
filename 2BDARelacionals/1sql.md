

# **Resum SQL**

SQL, o Structured Query Language (llenguatge de consulta estructurada), és un llenguatge per comunicar-se amb bases de dades. S'utilitza per seleccionar dades específiques i crear informes complexos.     

SQL és un llenguatge de dades universal i s'utilitza en pràcticament totes les tecnologies que processen dades.

### **Exemple de Dades**

**PAÍS**

| id | nom      | població | superfície |
|----|----------|----------|------------|
| 1  | França   | 66600000 | 640680     |
| 2  | Alemanya | 80700000 | 357000     |

...

**CIUTAT**

| id | nom   | id_pais | població | classificació |
|----|-------|---------|----------|---------------|
| 1  | París | 1       | 2243000  | 5             |
| 2  | Berlín| 2       | 3460000  | 3             |

...

---

## **Consultar una sola taula**

- **Recuperar totes les columnes de la taula `pais`:**
    ```sql
    SELECT *
    FROM pais;
    ```

- **Recuperar les columnes `id` i `nom` de la taula `ciutat`:**
    ```sql
    SELECT id, nom
    FROM ciutat;
    ```

- **Recuperar els noms de les ciutats ordenats per la columna `classificacio` en ordre ascendent (ASC):**
    ```sql
    SELECT nom
    FROM ciutat
    ORDER BY classificacio ASC;
    ```

- **Recuperar els noms de les ciutats ordenats per la columna `classificacio` en ordre descendent (DESC):**
    ```sql
    SELECT nom
    FROM ciutat
    ORDER BY classificacio DESC;
    ```

---

## **Àlies**

- **Columnes**
    ```sql
    SELECT nom AS nom_ciutat
    FROM ciutat;
    ```

- **Taules**
    ```sql
    SELECT pa.nom, ci.nom
    FROM ciutat AS ci
    JOIN pais AS pa
    ON ci.id_pais = pa.id;
    ```

---

## **Filtrar Resultats**

### **Operadors de Comparació**

- **Recuperar els noms de les ciutats la classificació de les quals siga superior a 3:**
    ```sql
    SELECT nom
    FROM ciutat
    WHERE classificacio > 3;
    ```

- **Recuperar els noms de les ciutats que no siguen ni Berlín ni Madrid:**
    ```sql
    SELECT nom
    FROM ciutat
    WHERE nom != 'Berlín'
      AND nom != 'Madrid';
    ```

---

### **Operadors de Text**

- **Recuperar els noms de les ciutats que comencen per "P" o acaben en "s":**
    ```sql
    SELECT nom
    FROM ciutat
    WHERE nom LIKE 'P%'
      OR nom LIKE '%s';
    ```

- **Recuperar els noms de les ciutats que comencen per qualsevol lletra seguida de "adrid" (com Madrid, d'Espanya):**
    ```sql
    SELECT nom
    FROM ciutat
    WHERE nom LIKE '_adrid';
    ```

---

### **Altres Operadors**

- **Recuperar els noms de les ciutats amb poblacions compreses entre 500.000 i 5 milions d'habitants:**
    ```sql
    SELECT nom
    FROM ciutat
    WHERE poblacio BETWEEN 500000 AND 5000000;
    ```

- **Recuperar els noms de les ciutats que tenen un valor en la classificació:**
    ```sql
    SELECT nom
    FROM ciutat
    WHERE classificacio IS NOT NULL;
    ```

- **Recuperar els noms de les ciutats situades en països amb ID 1, 4, 7 o 8:**
    ```sql
    SELECT nom
    FROM ciutat
    WHERE id_pais IN (1, 4, 7, 8);
    ```

---

## **Consultar a diverses taules**

### **INNER JOIN**

`JOIN` (o explícitament `INNER JOIN`) retorna les files en les quals coincideixen els valors en ambdues taules.
```sql
SELECT ciutat.nom, pais.nom
FROM ciutat
[INNER] JOIN pais
ON ciutat.id_pais = pais.id;


```

**CIUTAT  -   PAÍS**
| id   | nom       | id_pais |      | id   | nom       |
|------|-----------|---------|------|------|-----------|
| 1    | París     | 1       |      | 1    | França    |
| 2    | Berlín    | 2       |      | 2    | Alemanya  |

---

#### **LEFT JOIN**

`LEFT JOIN` retorna totes les files de la taula de l'esquerra amb les files corresponents de la taula de la dreta. Si no coincideix cap fila de la taula de la dreta, retorna `NULL` com a valors de la taula dreta.
```sql
SELECT ciutat.nom, pais.nom
FROM ciutat
LEFT JOIN pais
ON ciutat.id_pais = pais.id;
```
**CIUTAT  -   PAÍS**
| id   | nom       | id_pais |      | id   | nom       |
|------|-----------|---------|------|------|-----------|
| 1    | París     | 1       |      | 1    | França    |
| 2    | Berlín    | 2       |      | 2    | Alemanya  |
| 3    | Varsòvia  | 4       |      | NULL | NULL      |

---

#### **RIGHT JOIN**

`RIGHT JOIN` retorna totes les files de la taula de la dreta amb les files corresponents de la taula de l'esquerra. Si no coincideix cap fila de la taula esquerra, retorna `NULL` com a valors de la taula esquerra.
```sql
SELECT ciutat.nom, pais.nom
FROM ciutat
RIGHT JOIN pais
ON ciutat.id_pais = pais.id;
```
**CIUTAT  -   PAÍS**
| id   | nom       | id_pais |      | id   | nom       |
|------|-----------|---------|------|------|-----------|
| 1    | París     | 1       |      | 1    | França    |
| 2    | Berlín    | 2       |      | 2    | Alemanya  |
| NULL | NULL      | NULL    |      | 3    | Islàndia  |

---

#### **FULL JOIN**

`FULL JOIN` (o explícitament `FULL OUTER JOIN`) retorna totes les files de les dues taules. Si no coincideix cap fila de l'altra taula, retorna valors `NULL`.
```sql
SELECT ciutat.nom, pais.nom
FROM ciutat
FULL [OUTER] JOIN pais
ON ciutat.id_pais = pais.id;
```
**CIUTAT  -   PAÍS**
| id   | nom       | id_pais |      | id   | nom       |
|------|-----------|---------|------|------|-----------|
| 1    | París     | 1       |      | 1    | França    |
| 2    | Berlín    | 2       |      | 2    | Alemanya  |
| 3    | Varsòvia  | 4       |      | NULL | NULL      |
| NULL | NULL      | NULL    |      | 3    | Islàndia  |

---

### **Operacions de Conjunts**

#### **UNION**

`UNION` combina els resultats de dos conjunts de resultats i exclou els duplicats.
```sql
SELECT nom
FROM ciclisme
WHERE pais = 'DE'
UNION
SELECT nom
FROM patinatge
WHERE pais = 'DE';
```

#### **INTERSECT**

`INTERSECT` retorna només les files que apareixen en ambdós conjunts de resultats.
```sql
SELECT nom
FROM ciclisme
WHERE pais = 'DE'
INTERSECT
SELECT nom
FROM patinatge
WHERE pais = 'DE';
```

#### **EXCEPT**

`EXCEPT` retorna només les files que apareixen en el primer conjunt de resultats, però no en el segon.
```sql
SELECT nom
FROM ciclisme
WHERE pais = 'DE'
EXCEPT
SELECT nom
FROM patinatge
WHERE pais = 'DE';
```

---

Aquesta és la traducció completa en valencià, amb totes les taules i el format indicat per mantenir la claredat i distinció entre les taules `CIUTAT` i `PAÍS` quan apareixen juntes en les operacions de `JOIN`.
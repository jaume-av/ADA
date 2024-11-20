---
title: 2.- Exercicis
parent: 3. JDBC - Connectors a Base de Dades Relacionals.
grand_parent: Persistència en Base de Dades
has_children: true
layout: default
nav_order: 20
---


## Ampliació de la Base de Dades d'Empresa

L’empresa vol gestionar els seus departaments i assignar cada empleat a un departament. Completa les següents tasques utilitzant operacions DDL i DML:

1. **Crea una nova taula `Departaments`** amb les següents columnes:
   - `IdDepartament`: Identificador únic, clau primària (INTEGER, AUTOINCREMENT).
   - `NomDepartament`: Nom del departament (obligatori, VARCHAR(100)).
   - `Responsable`: Responsable del departament (opcional, VARCHAR(100)).

2. **Modifica la taula `Empleats`** per afegir una columna `IdDepartament` (INTEGER) com a clau forana que relacioni cada empleat amb un departament.

3. **Insereix dades a la taula `Departaments`**:
   - Exemples de departaments: Recursos Humans, Desenvolupament, Comptabilitat.

4. **Assigna un departament a cada empleat** actual de la taula `Empleats`.

5. **Realitza una consulta** que mostri els empleats juntament amb el nom del departament al qual pertanyen.

**Sortida esperada:**
```

NIF: 123456789, Nom: Pep, Cognoms: Martínez González, Departament: Recursos Humans
NIF: 987654321, Nom: Marta, Cognoms: Soler Sánchez, Departament: Desenvolupament
NIF: 111111111, Nom: Fina, Cognoms: Valls Beltran, Departament: Comptabilitat
...
```


### Pistes - Sentències SQL:


### **Sentències SQL per a Completar l'Exercici**

---

### **1. Crear la taula `Departaments`**
```sql

CREATE TABLE IF NOT EXISTS Departaments (
    IdDepartament INTEGER PRIMARY KEY AUTOINCREMENT,
    NomDepartament VARCHAR(100) NOT NULL,
    Responsable VARCHAR(100)
);
```

---

### **2. Modificar la taula `Empleats`**
Afegim una columna `IdDepartament` a la taula `Empleats` que actuarà com a clau forana.

```sql

ALTER TABLE Empleats ADD COLUMN IdDepartament INTEGER REFERENCES Departaments(IdDepartament);
```

---

### **3. Inserir dades a la taula `Departaments`**
Afegim informació de departaments a la taula `Departaments`.

```sql

INSERT INTO Departaments (NomDepartament, Responsable) VALUES
('Recursos Humans', 'Marta Pérez'),
('Desenvolupament', 'Jaume Martí'),
('Comptabilitat', 'Fina Soler');
```

---

### **4. Assignar un departament a cada empleat**
Actualitzem la taula `Empleats` per assignar un departament a cada empleat. 

```sql

UPDATE Empleats SET IdDepartament = CASE
    WHEN NIF = '123456789' THEN 1 -- Recursos Humans
    WHEN NIF = '987654321' THEN 2 -- Desenvolupament
    WHEN NIF = '111111111' THEN 3 -- Comptabilitat
    WHEN NIF = '222222222' THEN 1 -- Recursos Humans
    WHEN NIF = '333333333' THEN 2 -- Desenvolupament
    WHEN NIF = '444444444' THEN 3 -- Comptabilitat
END;
```

---

### **5. Consultar empleats i departaments**
Unim la informació de les taules `Empleats` i `Departaments` per mostrar el nom, cognoms i departament de cada empleat.

```sql

SELECT e.NIF, e.Nom, e.Cognoms, d.NomDepartament
FROM Empleats e
LEFT JOIN Departaments d ON e.IdDepartament = d.IdDepartament;
```

---


## Consultes  amb `PreparedStatement` i Placeholders

Utilitzant la base de dades `Empresa` amb les taules `Empleats` i `Departaments`, resol els següents exercicis.

Hem d'utilitzar **placeholders (`?`)** en **consultes SQL** amb `PreparedStatement`, incloent-hi sentències amb **subconsultes** i condicions complexes.

---

### **1. Consulta tots els empleats.**
Escriu una consulta per obtenir el `NIF`, `Nom` i `Cognoms` de tots els empleats. Aquesta consulta no té placeholders, però és la base per començar.

---

### **2. Consulta els empleats d’un departament específic.**
Utilitza un placeholder (`?`) per demanar el nom del departament (`NomDepartament`) i mostrar els empleats que hi pertanyen.

---

### **3. Consulta els empleats amb un salari superior a un valor donat.**
Demana per pantalla un valor mínim de salari i mostra els empleats que el superen. Utilitza un placeholder per al valor del salari.

---

### **4. Consulta empleats que treballen en un departament amb més de 2 empleats.**
Utilitza una **subconsulta** per seleccionar els `IdDepartament` que tenen més de 2 empleats i mostra el `NIF`, `Nom`, i `Cognoms` dels empleats assignats a aquests departaments.

---

### **5. Mostra la informació completa d’un empleat concret.**
Demana per pantalla el `NIF` d’un empleat i mostra tota la seva informació, incloent-hi el nom del departament al qual pertany. Utilitza una consulta amb **`JOIN`** i placeholders.

---

### **6. Consulta empleats amb un salari superior al salari mitjà del seu departament.**
Utilitza una **subconsulta** per calcular el salari mitjà de cada departament i selecciona els empleats amb un salari superior al del seu departament.

---

### **7. Mostra els departaments amb la suma total dels salaris superior a un valor donat.**
Demana un valor per pantalla i utilitza una **subconsulta** per sumar els salaris de cada departament. Mostra només els departaments que superen aquesta suma.

---

### **8. Consulta empleats que no tenen departament assignat.**
Mostra els empleats amb la columna `IdDepartament` amb un valor nul. Aquesta consulta és útil per comprovar la consistència de les dades.

---

### **9. Consulta el salari màxim i mínim per a un departament específic.**
Demana el nom d’un departament per pantalla i mostra el salari màxim i mínim dels empleats que hi treballen. Utilitza placeholders per al departament.

---

### **10. Consulta empleats que treballen en un departament amb la paraula 'Desenvolupament' al nom.**
Utilitza l’operador `LIKE` amb placeholders per trobar els empleats dels departaments que contenen la paraula "Desenvolupament".

---

### **Pistes**

1. **Practica Placeholders (`?`)**:
   - Assegura’t que totes les consultes (excepte la primera) utilitzen placeholders per als valors dinàmics.
   - Exemples d’ús:
     - Placeholder per un valor concret (`Salari > ?`).
     - Placeholder amb operadors com `LIKE` (`NomDepartament LIKE ?`).

2. **Inclou Subconsultes**:
   - Exercicis com el 4, 6 i 7 requereixen subconsultes per practicar sentències complexes.

3. **Utilitza `JOIN` quan sigui necessari**:
   - Per obtenir informació combinada de les taules `Empleats` i `Departaments`.

4. **Comprova els resultats amb dades existents**:
   - Executa cada consulta i verifica la seva sortida per assegurar-te que funciona correctament.

---

### **Exemple de Sortida per l'Exercici 6**
```

NIF: 987654321, Nom: Marta, Cognoms: Soler Sánchez, Salari: 3200.00
NIF: 111111111, Nom: Fina, Cognoms: Valls Beltran, Salari: 3100.00
...
```

Aquest conjunt d’exercicis et permetrà aprofundir en l'ús de `PreparedStatement` amb placeholders i millorar la comprensió de les sentències SQL complexes amb subconsultes.


Eixides esperades:

```

1. Tots els empleats:
NIF: 123456789, Nom: Pep, Cognoms: Martínez González
NIF: 987654321, Nom: Marta, Cognoms: Soler Sánchez
NIF: 111111111, Nom: Fina, Cognoms: Valls Beltran
NIF: 222222222, Nom: Josefa, Cognoms: Beltran Benedito
NIF: 333333333, Nom: Jaime, Cognoms: Valls Abad
NIF: 444444444, Nom: Sergi, Cognoms: Navarro Pérez

2. Empleats del departament 'Desenvolupament':
NIF: 987654321, Nom: Marta, Cognoms: Soler Sánchez
NIF: 333333333, Nom: Jaime, Cognoms: Valls Abad

3. Empleats amb salari superior a 2500:
NIF: 123456789, Nom: Pep, Cognoms: Martínez González, Salari: 2500.5
NIF: 987654321, Nom: Marta, Cognoms: Soler Sánchez, Salari: 3000.75
NIF: 111111111, Nom: Fina, Cognoms: Valls Beltran, Salari: 3000.75
NIF: 222222222, Nom: Josefa, Cognoms: Beltran Benedito, Salari: 3000.75
NIF: 333333333, Nom: Jaime, Cognoms: Valls Abad, Salari: 3000.75

4. Empleats en departaments amb més de 2 empleats:

5. Informació completa de l'empleat amb NIF '123456789':
NIF: 123456789, Nom: Pep, Cognoms: Martínez González, Salari: 2500.5, Departament: Recursos Humans

6. Empleats amb salari superior al salari mitjà del seu departament:
NIF: 111111111, Nom: Fina, Cognoms: Valls Beltran, Salari: 3000.75
NIF: 222222222, Nom: Josefa, Cognoms: Beltran Benedito, Salari: 3000.75

7. Departaments amb suma total de salaris superior a 5000:
Departament: Comptabilitat, Total Salaris: 5000.75
Departament: Desenvolupament, Total Salaris: 6001.5
Departament: Recursos Humans, Total Salaris: 5501.25

8. Empleats sense departament assignat:

9. Salari màxim i mínim del departament 'Comptabilitat':
Salari Màxim: 3000.75, Salari Mínim: 2000.0

10. Empleats en departaments que contenen 'Desenvolupament':
NIF: 987654321, Nom: Marta, Cognoms: Soler Sánchez
NIF: 333333333, Nom: Jaime, Cognoms: Valls Abad

´´´

---
title: 1.- Exercici guiat(I)
parent: 3. JDBC - Connectors a Base de Dades Relacionals.
grand_parent: Persistència en Base de Dades
has_children: true
layout: default
nav_order: 15
---









# **1.- Exercici guiat**

Crea una base de dades SQLite amb la taula **Empleats**, que continga informació sobre els empleats d'una empresa, incloent el seu NIF, nom, cognoms i salari. 


---

## 1.- Creació i Inserció de Dades en una Base de Dades SQLite

SQLite és una base de dades relacional lleugera i fàcil d'utilitzar que es pot integrar en aplicacions mòbils i altres programes. En aquest exercici, aprendrem a crear una base de dades SQLite i a inserir dades en ella.

Podem usar SQLite Online per a realitzar aquest exercici. Pots accedir a SQLite Online a través del següent enllaç: 

- [SQLite Online](https://sqliteonline.com/).

### **Creació de la Base de Dades i Taula**

1. Entra a l'intèrpret de línia de comandes de SQLite o a l'eina en línia.
2. Executa el següent script SQL, que:
   - **Esborra** la taula `Empleats` si ja existeix.
   - **Crea** la taula `Empleats` amb els següents camps:
     - **NIF**: Identificador únic de l'empleat (clau primària).
     - **Nom**: Nom de l'empleat.
     - **Cognoms**: Cognoms de l'empleat.
     - **Salari**: Salari de l'empleat.

   ```sql
   -- Esborra la taula si ja existeix
   DROP TABLE IF EXISTS Empleats;

   -- Crea la taula Empleats
   CREATE TABLE IF NOT EXISTS Empleats
   (
       NIF       VARCHAR(9) PRIMARY KEY,
       Nom       VARCHAR(100),
       Cognoms   VARCHAR(100),
       Salari    REAL
   );
   ```

---

### **Inserció de dades**
Després de crear la taula, insereix les dades següents d'empleats utilitzant una única instrucció `INSERT` amb múltiples valors:

   ```sql
   -- Inserta dades en la taula Empleats
   INSERT INTO Empleats (NIF, Nom, Cognoms, Salari) VALUES
   ('123456789', 'Pep', 'Martínez González', 2500.50),
   ('987654321', 'Marta', 'Soler Sánchez', 3000.75),
   ('111111111', 'Fina', 'Valls Beltran', 3000.75),
   ('222222222', 'Josefa', 'Beltran Benedito', 3000.75),
   ('333333333', 'Jaime', 'Valls Abad', 3000.75),
   ('444444444', 'Sergi', 'Navarro Pérez', 2000.00);
   ```

---

### **Validació de la taula i dades**
Verifica que la taula ha sigut creada correctament i que les dades han sigut inserides:

1. Executa la següent consulta per veure tot el contingut de la taula:
   ```sql
   SELECT * FROM Empleats;
   ```
2. Revisa que els registres siguen correctes.

---



### **Resultat**

La taula `Empleats` contindrà els següents registres:

| **NIF**       | **Nom**  | **Cognoms**           | **Salari**  |
|---------------|----------|-----------------------|-------------|
| 123456789     | Pep      | Martínez González     | 2500.50     |
| 987654321     | Marta    | Soler Sánchez         | 3000.75     |
| 111111111     | Fina     | Valls Beltran         | 3000.75     |
| 222222222     | Josefa   | Beltran Benedito      | 3000.75     |
| 333333333     | Jaime    | Valls Abad            | 3000.75     |
| 444444444     | Sergi    | Navarro Pérez         | 2000.00     |


### **Exportar a fitxer .db**

Des del menú File -> saveDB, una volta descarregat, canviem el nom al fitxer.

## 2.- Connectar a la Base de Dades SQLite amb Java.

Per connectar a la base de dades SQLite des de Java, necessitem la llibreria JDBC SQLite. Aquesta llibreria ens permetrà interactuar amb la base de dades i realitzar operacions com la inserció, actualització, eliminació i consulta de dades.

Crearem un projecte `maven` amb `IntelliJ` i afegim la dependència de **JDBC SQLite** al  projecte. 
Per tant, afegim al fitxer `pom.xml`:

```xml
<!-- Source: https://mvnrepository.com/artifact/org.xerial/sqlite-jdbc -->
<dependency>
    <groupId>org.xerial</groupId>
    <artifactId>sqlite-jdbc</artifactId>
    <version>3.54.1.0</version>
    <scope>compile</scope>
</dependency>

```
o la versió més actualitzada.

[Dependència maven Sqlite JDBC](https://mvnrepository.com/artifact/org.xerial/sqlite-jdbc)


### **Incorprar la base de dades al projecte**

Copiem el fitxer `Empleats.db` a la carpeta `resources` del projecte.

### **Connectar a la Base de Dades**

Per connectar a la base de dades SQLite des de Java, necessitem la interfície `Connection` de la llibreria JDBC. A continuació, es mostra un exemple de com connectar a la base de dades `Empleats.db`:

```java
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLiteJDBC {
    public static void main(String[] args) {
        // Connectar a la base de dades
        String url = "jdbc:sqlite:src/main/resources/Empleats.db";
        try (Connection connection = DriverManager.getConnection(url)) {
            System.out.println("Connexió a SQLite establerta.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
```

En aquest exemple, establim una connexió a la base de dades `Empleats.db` utilitzant la URL `jdbc:sqlite:src/main/resources/Empleats.db`. Si la connexió s'estableix correctament, es mostrarà el missatge `Connexió a SQLite establerta.`.

---

### **crear consultes SQL**

Neceistarem la interfície `Statement` per a executar consultes SQL a la base de dades. A continuació, es mostra un exemple de com crear una consulta SQL per a seleccionar totes les columnes de la taula `Empleats` i mostrar el NIF, el nom i els cognoms:

```java

package com.jaume;

import javax.xml.transform.Source;
import java.sql.*;

public class ExerciciGuiat {

    public static void main(String[] args)  {
        // Connectar amb la base de dades
        String url = "jdbc:sqlite:src/main/resources/Empleats.db";
        // Creem una consulta i obeim el resultat
        String sql = "SELECT * FROM Empleats";

        try (Connection connexio = DriverManager.getConnection(url);
             Statement sentenciaSQL = connexio.createStatement();
             ResultSet resultat = sentenciaSQL.executeQuery(sql)) {
            System.out.println("Connexió Establerta");
            System.out.println("*******************");

            // Recorrem el resultat de la consultai mostrem les dades

            while (resultat.next()) {

                String nif = resultat.getString("nif");
                String nom = resultat.getString("nom");
                String cognoms = resultat.getString("cognoms");

                System.out.println(nif + "\t" + nom + "\t" + cognoms);

            }


        }

        catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }
}
```

**NOTA:** `try-with-resources` tanca automàticament els recursos en ordre invers al de declaració: primer el `ResultSet`, després l’`Statement` i finalment la `Connection`. El tancament també es fa si es produïx una excepció.

## 3.- Crear consultes SQL



Crea les següents consultes SQL i mostra els resultats des de Java:


### **Requisits**

- **Usa `Statement` i `executeQuery()`:** Per a totes les consultes, utilitza l'objecte `Statement` ja creat.

- **Gestiona els recursos:**
  - Tanca el `ResultSet` després de cada consulta.

- **Dóna format a la sortida:**
  - Usa `printf()` per mostrar els resultats alineats i ben organitzats.
  

### **Consultes SQL**

1. **Consulta 1:** Mostra tots els empleats amb un salari superior a 2000 i totes les seves columnes (`nif`, `nom`, `cognoms`, `salari`) en format alineat.

<details>
<summary>Consulta 1</summary>

```sql
SELECT * FROM Empleats WHERE Salari > 2000;
```
</details>


2. **Consulta 2:** Mostra només el `nif` i el `nom` dels empleats amb el cognom exactament igual a "Soler Sánchez".

<details>
<summary>Consulta 2</summary>

```sql
SELECT nif, nom FROM Empleats WHERE cognoms = 'Soler Sánchez';

```
</details>



3. **Consulta 3:** Ordena tots els empleats pel camp `salari` de major a menor i mostra totes les columnes (`nif`, `nom`, `cognoms`, `salari`) amb format alineat.

<details>
<summary>Consulta 3</summary>

```sql
SELECT * FROM Empleats ORDER BY salari DESC;

```
</details>


4. **Consulta 4:** Calcula el salari mitjà de tots els empleats utilitzant la funció `AVG()` i mostra el resultat amb un missatge clar.

<details>
<summary>Consulta 4</summary>

```sql
SELECT AVG(salari) AS salari_mitja FROM Empleats;

```
</details>



    


### L'eixida del programa ha de quedar com la següent:

```
Connexió Establerta
*******************

Consulta 1: Empleats amb salari superior a 2000
************************************************
NIF        Nom             Cognoms              Salari     
123456789  Pep             Martínez González    2500.50
987654321  Marta           Soler Sánchez        3000.75
111111111  Fina            Valls Beltran        3000.75
222222222  Josefa          Beltran Benedito     3000.75
333333333  Jaime           Valls Abad           3000.75

Consulta 2: Empleats amb cognoms 'Soler Sánchez'
*************************************************
NIF        Nom             
987654321  Marta           

Consulta 3: Ordenar empleats per salari de major a menor
*********************************************************
NIF        Nom             Cognoms              Salari     
987654321  Marta           Soler Sánchez        3000.75
111111111  Fina            Valls Beltran        3000.75
222222222  Josefa          Beltran Benedito     3000.75
333333333  Jaime           Valls Abad           3000.75
123456789  Pep             Martínez González    2500.50
444444444  Sergi           Navarro Pérez        2000.00

Consulta 4: Salari mitjà de tots els empleats
**********************************************
Salari mitjà: 2750.58

```


## Recordatori: avís d’accés natiu amb Java

En executar el programa amb Java 24 o posterior, el driver SQLite pot mostrar este avís:

```text
WARNING: A restricted method in java.lang.System has been called
WARNING: java.lang.System::load has been called by org.sqlite.SQLiteJDBCLoader in an unnamed module
WARNING: Use --enable-native-access=ALL-UNNAMED to avoid a warning for callers in this module
WARNING: Restricted methods will be blocked in a future release unless native access is enabled
```

El driver SQLite necessita carregar una biblioteca nativa. Java mostra l’avís perquè no hem autoritzat explícitament este accés. **L’avís, per si mateix, no indica que la connexió haja fallat.**

### Configurar l’execució en IntelliJ

1. Executa el programa una vegada.
2. Obri **Run → Edit Configurations…**.
3. Selecciona **Application → ExerciciGuiat** i comprova estos camps:

   | Camp | Valor |
   |---|---|
   | **Name** | `ExerciciGuiat` |
   | **Main class** | `com.jaume.ExerciciGuiat` |
   | **Working directory** | Carpeta del projecte on està `pom.xml` |

4. Prem **Modify options → Add VM options**.
5. En **VM options**, escriu:

   ```text
   --enable-native-access=ALL-UNNAMED
   ```

6. Prem **Apply → Run**.

**L’opció va en `VM options` de l’aplicació Java**, no en `Program arguments` ni en Maven.
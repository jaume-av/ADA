### Exercicis per Practicar JDBC

Els exercicis estan ordenats de menys a més dificultat i cobreixen els conceptes bàsics i detalls del tema de **JDBC**.

---

#### **Nivell 1: Conceptes Bàsics**
1. **Preguntes teòriques**:
   - Què és el **desfasament objecte-relacional**? Explica-ho amb exemples simples.
   - Quin és el propòsit de l'API **JDBC**?
   - Diferència entre **Statement** i **PreparedStatement**.
   - Quina és la diferència entre els mètodes `executeQuery`, `executeUpdate` i `execute`?

2. **Escriu el codi**:
   - Escriu una línia de codi per establir una connexió amb una base de dades SQLite que es troba a `src/main/resources/nomdb.sqlite`.
   - Escriu el codi per carregar el driver **SQLite JDBC**.

---

#### **Nivell 2: Connexió i Consultes Simples**
3. **Exercici pràctic: Connexió**  
   Escriu un programa que connecti a una base de dades SQLite i mostri un missatge per pantalla indicant si la connexió s'ha establert correctament o hi ha hagut algun error.

   **Exemple de sortida esperada**:
   ```
   Connexió establerta amb èxit!
   ```

4. **Exercici pràctic: SELECT amb Statement**  
   Utilitza **Statement** per mostrar totes les files d'una taula anomenada `productes` amb les següents columnes:
   - `id`: Enter.
   - `nom`: Text.
   - `preu`: Decimal.

   **Sortida esperada** (exemple):
   ```
   ID: 1, Nom: Portàtil, Preu: 799.99
   ID: 2, Nom: Ratolí, Preu: 19.99
   ```

---

#### **Nivell 3: Operacions DML amb Statement**
5. **Exercici: INSERT amb Statement**  
   Escriu un programa que inserisca una fila nova en la taula `productes`.  
   Sentència SQL:
   ```sql
   INSERT INTO productes (nom, preu) VALUES ('Auriculars', 49.99);
   ```
   **Sortida esperada**:
   ```
   S'han insertat 1 fila/es.
   ```

6. **Exercici: DELETE amb Statement**  
   Escriu un programa que elimine els productes amb preu superior a 1000.  
   **Sortida esperada**:
   ```
   S'han eliminat 2 fila/es.
   ```

---

#### **Nivell 4: PreparedStatement amb Paràmetres**
7. **Exercici: SELECT amb PreparedStatement**  
   Escriu un programa que mostri els productes que tenen un preu inferior a un valor introduït per l'usuari.  
   **Entrada**: 500  
   **Sortida esperada**:
   ```
   ID: 1, Nom: Ratolí, Preu: 19.99
   ```

8. **Exercici: INSERT amb PreparedStatement**  
   Utilitza un **PreparedStatement** per inserir un nou producte en la taula `productes`. Els valors del producte han de ser introduïts per l'usuari.  
   **Exemple d'entrada**:
   ```
   Nom del producte: Teclat
   Preu del producte: 29.99
   ```
   **Sortida esperada**:
   ```
   S'han insertat 1 fila/es.
   ```

---

#### **Nivell 5: Consultes Complexes i DDL**
9. **Exercici: Crear una Taula**  
   Escriu un programa que cree una taula anomenada `clients` amb les següents columnes:
   - `id`: Enter (Clau Primària).
   - `nom`: Text.
   - `correu`: Text (únic).

   **Sortida esperada**:
   ```
   Taula 'clients' creada amb èxit.
   ```

10. **Exercici: UPDATE amb PreparedStatement**  
    Escriu un programa que actualitze el preu dels productes amb un **descompte del 10%** si el preu és superior a 100.  

    **Sortida esperada**:
    ```
    S'han actualitzat 3 fila/es.
    ```

---

#### **Nivell 6: Gestió d'Excepcions i Recursos**
11. **Exercici: Gestió d'Errors**  
    Modifica el codi d'un programa de connexió perquè gestione les excepcions **`SQLException`** i mostri un missatge personalitzat si ocorre algun error.

    **Sortida esperada (si hi ha error)**:
    ```
    Error: No s'ha pogut connectar a la base de dades.
    ```

12. **Exercici: Alliberament de Recursos**  
    Escriu un programa complet que connecti a una base de dades, execute una consulta i tanqui els recursos utilitzant un bloc `finally`.

---

#### **Nivell 7: Metadades**
13. **Exercici: Llistar Taules**  
    Escriu un programa que mostri totes les taules existents en la base de dades utilitzant **`DatabaseMetaData`**.  
    **Sortida esperada**:
    ```
    Taula: productes
    Taula: clients
    ```

14. **Exercici: Llistar Columnes d'una Taula**  
    Escriu un programa que mostri les columnes d'una taula (`clients`) amb els seus tipus.  
    **Sortida esperada**:
    ```
    Columna: id, Tipus: INTEGER
    Columna: nom, Tipus: VARCHAR
    Columna: correu, Tipus: VARCHAR
    ```

---

### Preguntes de Reflexió:
1. Per què és recomanable utilitzar **PreparedStatement** en lloc de **Statement**?
2. Què ocorre si no tanquem els objectes `ResultSet`, `Statement` o `Connection`?
3. Quin avantatge té utilitzar **DatabaseMetaData**?
4. Què indica el resultat retornat per `executeUpdate`?
5. Com evitaríeu una injecció SQL en una aplicació?







### **Respostes a Exercici 1: Preguntes teòriques**

1. **Què és el desfasament objecte-relacional?**
   - El desfasament objecte-relacional es refereix a les dificultats tècniques que sorgeixen a causa de la diferència entre el model de dades utilitzat en la programació orientada a objectes (POO) i el model relacional utilitzat a les bases de dades.  
     **Exemple**:
     - En POO, tenim objectes amb atributs i mètodes (per exemple, una classe `Producte` amb atributs com `id`, `nom` i `preu`).
     - En bases de dades relacionals, les dades es representen en taules amb files i columnes, sense comportament ni mètodes.

---

2. **Quin és el propòsit de l'API JDBC?**
   - JDBC (Java Database Connectivity) és una API que permet a les aplicacions Java comunicar-se amb bases de dades relacionals mitjançant sentències SQL. El seu objectiu és oferir una interfície comuna per interactuar amb bases de dades independentment del SGBD utilitzat.

---

3. **Diferència entre `Statement` i `PreparedStatement`:**
   - **`Statement`**:
     - S'utilitza per executar sentències SQL simples sense paràmetres.
     - No protegeix contra **SQL Injection**.
   - **`PreparedStatement`**:
     - S'utilitza per executar sentències SQL amb placeholders (`?`) que accepten valors dinàmics.
     - Proporciona més seguretat (evita **SQL Injection**) i millor rendiment, ja que les consultes es precompilen.

---

4. **Quina és la diferència entre els mètodes `executeQuery`, `executeUpdate` i `execute`?**

| **Mètode**         | **Tipus de SQL**          | **Retorn**                                  |
|---------------------|---------------------------|---------------------------------------------|
| **`executeQuery`**  | Només per `SELECT`.       | Retorna un `ResultSet` amb els resultats.   |
| **`executeUpdate`** | Per operacions DML (`INSERT`, `UPDATE`, `DELETE`) i DDL (`CREATE`, `ALTER`). | Retorna el nombre de files afectades.       |
| **`execute`**       | Per qualsevol sentència SQL. | Retorna `true` si hi ha un `ResultSet` i `false` altrament. |

---

### **Respostes a Exercici 2: Escriure Codi**

1. **Codi per establir una connexió amb SQLite:**
   ```java
   Connection conn = DriverManager.getConnection("jdbc:sqlite:src/main/resources/nomdb.sqlite");
   ```
   - Aquesta línia estableix una connexió amb una base de dades SQLite ubicada a `src/main/resources/nomdb.sqlite`.

---

2. **Codi per carregar el driver SQLite:**
   ```java
   Class.forName("org.sqlite.JDBC");
   ```
   - Encara que no és obligatori si utilitzeu Maven/Gradle, aquesta línia assegura que el driver JDBC per a SQLite estigui disponible.

---



### **Exercici 3: Connexió a una base de dades SQLite**
**Codi**:
```java
import java.sql.Connection;
import java.sql.DriverManager;

public class ConnexioSQLite {
    public static void main(String[] args) {
        try {
            // Establir la connexió amb SQLite
            Connection conn = DriverManager.getConnection("jdbc:sqlite:src/main/resources/nomdb.sqlite");

            // Mostrar un missatge si la connexió és correcta
            if (conn != null) {
                System.out.println("Connexió establerta amb èxit!");
                conn.close(); // Tanquem la connexió després d'usar-la
            }
        } catch (Exception e) {
            // Gestió d'errors si ocorre algun problema
            System.out.println("Error: No s'ha pogut connectar a la base de dades.");
            e.printStackTrace();
        }
    }
}
```

---

### **Exercici 4: SELECT amb Statement**
**Codi**:
```java
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class SelectStatement {
    public static void main(String[] args) {
        try {
            // Establir connexió amb SQLite
            Connection conn = DriverManager.getConnection("jdbc:sqlite:src/main/resources/nomdb.sqlite");

            // Crear l'objecte Statement
            Statement stmt = conn.createStatement();

            // Executar una consulta SELECT
            ResultSet rs = stmt.executeQuery("SELECT id, nom, preu FROM productes");

            // Processar el resultat
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id") + ", Nom: " + rs.getString("nom") + ", Preu: " + rs.getDouble("preu"));
            }

            // Alliberar recursos
            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
```
**Comentaris**:
1. **`Statement stmt = conn.createStatement();`**: Creem un Statement per enviar la consulta SQL.
2. **`stmt.executeQuery("SELECT...");`**: Executem la consulta i recuperem un ResultSet.
3. **`rs.next()`**: Iterem les files del resultat per processar-les.

---

### **Exercici 5: INSERT amb Statement**
**Codi**:
```java
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class InsertStatement {
    public static void main(String[] args) {
        try {
            // Establir connexió amb SQLite
            Connection conn = DriverManager.getConnection("jdbc:sqlite:src/main/resources/nomdb.sqlite");

            // Crear Statement
            Statement stmt = conn.createStatement();

            // Executar un INSERT
            int filesInsertades = stmt.executeUpdate("INSERT INTO productes (nom, preu) VALUES ('Auriculars', 49.99)");

            // Mostrar quantes files han estat afectades
            System.out.println("Files insertades: " + filesInsertades);

            // Alliberar recursos
            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
```
**Comentaris**:
1. **`stmt.executeUpdate("INSERT INTO...");`**: Executem una sentència SQL que modifica la base de dades.
2. El valor retornat indica quantes files s'han afectat.

---

### **Exercici 7: SELECT amb PreparedStatement**
**Codi**:
```java
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class SelectPreparedStatement {
    public static void main(String[] args) {
        try {
            // Establir connexió amb SQLite
            Connection conn = DriverManager.getConnection("jdbc:sqlite:src/main/resources/nomdb.sqlite");

            // Crear PreparedStatement amb un placeholder per al preu
            PreparedStatement pstmt = conn.prepareStatement("SELECT id, nom, preu FROM productes WHERE preu < ?");
            pstmt.setDouble(1, 500); // Assignar el valor al placeholder

            // Executar la consulta
            ResultSet rs = pstmt.executeQuery();

            // Processar els resultats
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id") + ", Nom: " + rs.getString("nom") + ", Preu: " + rs.getDouble("preu"));
            }

            // Alliberar recursos
            rs.close();
            pstmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
```
**Comentaris**:
1. **`PreparedStatement pstmt = conn.prepareStatement("SELECT...");`**: Especifica un placeholder (`?`) per al valor.
2. **`pstmt.setDouble(1, 500);`**: Assigna un valor al placeholder.

---

### **Exercici 8: INSERT amb PreparedStatement**
**Codi**:
```java
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Scanner;

public class InsertPreparedStatement {
    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            // Establir connexió amb SQLite
            Connection conn = DriverManager.getConnection("jdbc:sqlite:src/main/resources/nomdb.sqlite");

            // Recollir dades de l'usuari
            System.out.print("Nom del producte: ");
            String nom = sc.nextLine();
            System.out.print("Preu del producte: ");
            double preu = sc.nextDouble();

            // Crear PreparedStatement
            PreparedStatement pstmt = conn.prepareStatement("INSERT INTO productes (nom, preu) VALUES (?, ?)");
            pstmt.setString(1, nom);  // Assignar valor al primer placeholder
            pstmt.setDouble(2, preu); // Assignar valor al segon placeholder

            // Executar l'INSERT
            int filesInsertades = pstmt.executeUpdate();
            System.out.println("Files insertades: " + filesInsertades);

            // Alliberar recursos
            pstmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
```
**Comentaris**:
1. Recollim dades de l'usuari amb un **`Scanner`**.
2. Assignem els valors als placeholders amb **`pstmt.setString`** i **`pstmt.setDouble`**.

---

### **Exercici 10: UPDATE amb PreparedStatement**
**Codi**:
```java
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class UpdatePreparedStatement {
    public static void main(String[] args) {
        try {
            // Establir connexió amb SQLite
            Connection conn = DriverManager.getConnection("jdbc:sqlite:src/main/resources/nomdb.sqlite");

            // Crear PreparedStatement per aplicar un descompte
            PreparedStatement pstmt = conn.prepareStatement("UPDATE productes SET preu = preu * 0.9 WHERE preu > ?");
            pstmt.setDouble(1, 100); // Assignar el valor per al filtre

            // Executar l'UPDATE
            int filesActualitzades = pstmt.executeUpdate();
            System.out.println("Files actualitzades: " + filesActualitzades);

            // Alliberar recursos
            pstmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
```
**Comentaris**:
1. **`UPDATE productes SET preu = preu * 0.9 WHERE preu > ?`**: Redueix el preu en un 10%.
2. **`pstmt.setDouble(1, 100);`**: Aplica la condició per a productes amb preu superior a 100.

---


### Solució a les Preguntes de Reflexió

---

#### **1. Per què és recomanable utilitzar `PreparedStatement` en lloc de `Statement`?**

- **Seguretat**:
  - `PreparedStatement` ajuda a prevenir atacs d'**SQL Injection** gràcies a l'ús de placeholders (`?`) i l'assignació de valors amb els mètodes `setX()`. Això assegura que les dades de l'usuari no s'executen com a codi SQL.

- **Rendiment**:
  - Les consultes de `PreparedStatement` es **precompilen** al SGBD, cosa que redueix el temps d'execució si es reutilitzen diverses vegades amb valors diferents.

- **Llegibilitat i modularitat**:
  - Amb placeholders, el codi SQL és més llegible i modular, especialment en consultes complexes amb múltiples valors dinàmics.

---

#### **2. Què ocorre si no tanquem els objectes `ResultSet`, `Statement` o `Connection`?**

- **Fuites de memòria**:
  - Si els recursos no es tanquen, poden romandre ocupats a la memòria, fins i tot després que el programa hagi acabat la seva execució.

- **Bloquejos de connexió**:
  - En alguns SGBD, les connexions no tancades poden mantenir les taules o registres bloquejats, impedint que altres aplicacions o processos accedeixin a les dades.

- **Excepcions o errors inesperats**:
  - Deixar recursos oberts pot provocar errors de connexió o d'execució si es reutilitzen més tard.

- **Solució**:
  - Sempre utilitzar blocs `try-with-resources` o `finally` per garantir que es tanquen els recursos:
    ```java
    try (Connection conn = DriverManager.getConnection("jdbc:sqlite:src/main/resources/nomdb.sqlite");
         Statement stmt = conn.createStatement();
         ResultSet rs = stmt.executeQuery("SELECT * FROM productes")) {

        while (rs.next()) {
            System.out.println(rs.getString("nom"));
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }
    ```

---

#### **3. Quin avantatge té utilitzar `DatabaseMetaData`?**

- **Exploració de la base de dades**:
  - Proporciona informació detallada sobre l'estructura de la base de dades (taules, columnes, claus primàries i estrangeres, etc.) sense haver de consultar directament el SGBD.

- **Portabilitat**:
  - Permet crear aplicacions més generiques i adaptables, ja que es poden explorar diferents bases de dades sense conèixer la seva estructura prèviament.

- **Aplicacions pràctiques**:
  - Mostrar dinàmicament les taules disponibles a l'usuari.
  - Generar documentació de l'estructura de la base de dades.
  - Preparar consultes dinàmiques basades en la informació del SGBD.

**Exemple**:
```java
DatabaseMetaData metaData = conn.getMetaData();
ResultSet rs = metaData.getTables(null, null, "%", new String[]{"TABLE"});
while (rs.next()) {
    System.out.println("Taula: " + rs.getString("TABLE_NAME"));
}
```

---

#### **4. Què indica el resultat retornat per `executeUpdate`?**

- **Significat del valor retornat**:
  - **Per operacions DML (`INSERT`, `UPDATE`, `DELETE`)**:
    - Retorna el nombre de files afectades.
    - Si no hi ha cap fila afectada, retorna **0**.
  - **Per operacions DDL (`CREATE`, `ALTER`, `DROP`)**:
    - Retorna sempre **0** (indica que l'operació s'ha completat amb èxit, però no afecta files directament).

**Exemple**:
```java
int filesAfectades = stmt.executeUpdate("DELETE FROM productes WHERE preu > 1000");
System.out.println("Files afectades: " + filesAfectades);
```

---

#### **5. Com evitaríeu una injecció SQL en una aplicació?**

- **Ús de `PreparedStatement`**:
  - Sempre utilitzar `PreparedStatement` en lloc de `Statement` per assegurar que les dades d'entrada s'escapen correctament.

**Exemple incorrecte** (risc d'**SQL Injection**):
```java
String usuari = "admin' OR '1'='1";
String sql = "SELECT * FROM usuaris WHERE nom = '" + usuari + "'";
Statement stmt = conn.createStatement();
ResultSet rs = stmt.executeQuery(sql);
```

**Exemple correcte**:
```java
PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM usuaris WHERE nom = ?");
pstmt.setString(1, "admin");
ResultSet rs = pstmt.executeQuery();
```

- **Validació d'entrada**:
  - Assegurar que les dades introduïdes per l'usuari compleixin un format esperat abans d'enviar-les a la base de dades.

- **Mínim privilegi**:
  - Configurar les credencials de l'aplicació amb només els permisos necessaris per a la seva funcionalitat (evitar privilegis d'administrador).

- **Ús de procediments emmagatzemats** (en alguns casos):
  - Encapsular les operacions SQL dins del SGBD per limitar l'exposició a codi SQL directe.

---
title: 3. JDBC - Connectors a Base de Dades Relacionals. 
parent: Persistència en Base de Dades

has_children: true
layout: default
nav_order: 30
---


# Maneig de Connectors

**L'accés a dades** és el procés de recuperació o manipulació de dades obtingudes des d'un origen local o remot.

**Els orígens de dades** poden ser de diversa índole:
- Una base de dades relacional.
- Un full de càlcul.
- Un fitxer de text.
- Un servei web remot.
- Etc.

En aquesta unitat ens centrarem en els orígens de dades relacionals i aprendrem a realitzar **aplicacions Java per accedir a bases de dades** relacionals mitjançant l'ús de **connectors**.

---

## 1. Desfasament Objecte-Relacional

Encara que existeixen bases de dades orientades a objectes (**OODB**), el mercat està dominat per les **bases de dades relacionals**, ja que ofereixen un millor rendiment i més flexibilitat en diversos àmbits.

No obstant això, el paradigma de programació dominant avui dia és la **Programació Orientada a Objectes (POO)**:
- Les **bases de dades relacionals no estan dissenyades per emmagatzemar objectes**; les dades estan estructurades en **taules**, i són les **relacions** les que permeten vincular les diferents taules.
- Això crea un desfasament entre les estructures del model relacional i els models de dades utilitzats en POO. Per tant, és necessari un esforç addicion a l'hora de programar per conjuntar o fer conpatibles els dos paradigmes.
- A aquest concepte s'anomena **desfasament objecte-relacional** i es refereix a les dificultats tècniques que sorgeixen per les diferències entre aquests dos models.  Es a dir, totes les dificultats tècniques que trobem quan mesclem els dos paradigmes.

## 2. Connectors

Els sistemes gestors de bases de dades (**SGBD**) utilitzen **llenguatges especialitzats** per operar amb les dades que emmagatzemen. Mentrestant, les aplicacions s'escriuen en llenguatges de programació de propòsit general, com Java.

Per permetre que aquestes aplicacions es comuniquen amb els SGBD, es **necessiten mecanismes específics**. Aquests mecanismes es desenvolupen com a **APIs** i es denominen **connectors**.

---

{: .text-center}
![alt text](imatges/connectors1.png)

---


#### Característiques dels connectors:

- Per treballar amb bases de dades relacionals (**RDBMS**), s'utilitza el llenguatge **SQL**.
- Cada RDBMS té la seva pròpia **versió de SQL** (dialectes SQL) amb peculiaritats específiques, 
requerint així estructures de baix nivell personalitzades.

L'ús de **drivers** permet **desenvolupar una arquitectura genèrica**. Els connectors defineixen una interfície 
comú entre les aplicacions i les bases de dades, 
mentre que els drivers s'encarreguen de gestionar les particularitats de cada base de dades.

---

{: .text-center}
![alt text](imatges/connectors2.png)

---



Així, el connector no és només una API, sinó una **arquitectura** que especifica **les interfícies que han d'implementar els diferents drivers** per accedir a les bases de dades.

---

#### Arquitectures de connectors:

- **ODBC (Open Database Connectivity)**: Defineix una API per obrir connexions amb bases de dades, enviar consultes, actualitzacions, etc. Es pot utilitzar amb qualsevol servidor de bases de dades compatible. Està escrit en llenguatge C.
- **JDBC (Java Database Connectivity)**: És l'API per excel·lència per connectar bases de dades amb aplicacions Java. Ens centrarem en aquesta API en aquesta unitat.
- **OLE-DB**: Orientat a aplicacions Windows.
- **ADO**: Variant d'OLE-DB.

Avui dia, les arquitectures més utilitzades són **ODBC** i **JDBC**, ja que són compatibles amb la majoria dels SGBD. Tot i això, ofereixen beneficis a canvi d'una major complexitat de programació.

---

## 3. JDBC – Accés a Bases de Dades Relacionals

JDBC és una API que permet accedir a fonts de dades relacionals SQL des d'aplicacions Java. 
Esta arquitectura ofereix una interfície comú perquè els fabricants de SGBD pugen crear drivers que connecten les **aplicacions Java amb les bases de dades**.

### Característiques de JDBC:
- JDBC proporciona una **interfície específica per a cada SGBD**, coneguda com a **driver**.
- Les **crides als mètodes Java** es corresponen amb **operacions SQL**, facilitant la interacció entre l'aplicació i la base de dades.

---

{: .text-center}
![alt text](imatges/connectors3.png)

---



### Tasques principals amb JDBC:

JDBC inclou un **conjunt d'interfícies i classes** que permeten desenvolupar aplicacions en Java per:

1. **Connectar-se** a una base de dades.
2. **Enviar consultes** i instruccions d'actualització.
3. **Recuperar i processar resultats** d'una base de dades en resposta a aquestes consultes.


### Totes estes classes i interfícies les podem trobar al paquet `java.sql`. Les més importants són:


| Classe/Interfície      | Descripció                                                                                  |
|-------------------------|--------------------------------------------------------------------------------------------|
| **Driver**             | Permet connectar-se a una base de dades. Cada SGBD necessita un driver específic.          |
| **DriverManager**      | Gestiona els drivers instal·lats en el sistema.                                            |
| **DriverPropertyInfo** | Proporciona informació sobre el driver.                                                    |
| **Connection**         | Representa una connexió amb una base de dades. És possible tenir més d'una connexió alhora. |
| **DatabaseMetadata**   | Proporciona informació sobre la base de dades.                                             |
| **Statement**          | Executa sentències SQL sense paràmetres.                                                   |
| **PreparedStatement**  | Executa sentències SQL amb paràmetres d'entrada.                                           |
| **CallableStatement**  | Executa sentències SQL amb paràmetres d'entrada i d'eixida, com crides a procediments emmagatzemats. |
| **ResultSet**          | Conté les files resultants d'una consulta SELECT.                                          |
| **ResultSetMetadata**  | Proporciona informació sobre un ResultSet, com el nombre de columnes i els seus noms.      |

---

## Passos per a utilitzar JDBC en una aplicació Java

Per connectar una aplicació Java a una base de dades amb JDBC, seguirem els següents passos:

1. Importar les classes necessàries:
2. Carregar el driver JDBC.
3. Identificalr l'origen de dades (la BDA).
4. Crear un objecte **`Connection`**.
5. Crear un objecte **`Statement`**.
6. Executar una consulta SQL amb l'objecte **`Statement`**.
7. Recuperar el reultat de la consulta amb l'objecte **`ResultSet`**.
8. Alliberar recursos. (Per ordre invers al que s'han creat, **ResultSet, Statement, Connection**).

{: .text-center }
![alt text](imatges/connectors6.png)

Resumint, per connectar una base de dates a una aplicació java amb JDBC seguirem 4 passos:


---
1. **Carregar el driver.**
2. **Establir la connexió.**
3. **Executar sentències SQL.**
4. **Alliberar recursos.**

---

---

### 1. Carregar els Drivers

El primer pas és carregar el driver utilitzant el mètode **`forName`** de la classe **`Class`**. Es passa com a argument un **String** amb el nom de la classe del driver.

**Exemple:**
```java

Class.forName("com.mysql.cj.jdbc.Driver");
```

Aquest mètode assegura que el driver es carregue a memòria, permetent establir connexions a la base de dades.


En este exemple connectem a una base de dades relacional **mySql**, en cas de utilitzar un altre Sistema de Gestió de Base de Dades, caldria carregar els drivers corresponents.

---
**Nota:** Si carreguem les dependències amb **maven o gradle**, no cladrà posar esta línia.

---

### 2. Establir la Connexió

A continuació, establim una connexió a la base de dades usant el mètode **`getConnection()`** de la classe **`DriverManager`**. Aquest mètode requereix tres paràmetres:

- **URL**: Localització de la base de dades.
- **USER**: Nom d'usuari.
- **PASS**: Contrasenya.

**Exemple:**
```java
Connection conn = DriverManager.getConnection(
    "jdbc:mysql://localhost:3306/nom.db", "usuari", "contrasenya");
```
On:
- `jdbc:mysql`: indica que es tracta d'una base de dades MySQL.
- `localhost:3306`: és la URL de la base de dades.
- `nom.db`: és el nom de la base de dades.
- `usuari`: és el nom d'usuari de la base de dades.
- `contrasenya`: és la contrasenya d'usuari.

**Per a altres SGBD, la URL pot variar.**

- **Per a  SQLite:**

```java

Connection conn = DriverManager.getConnection("jdbc:sqlite:src/main/resources/nom.db");
```

on 
- `jdbc:sqlite`: indica que es tracta d'una base de dades SQLite.
- `src/main/resources/nom.db` és la ruta on es troba la base de dades.

Com es local, no requereix usuari ni contrasenya.

---

 - **PostgreSQL**:
  
```java

 Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/nom.db", "usuari", "contrasenya");
```

-  **Oracle**:
  
```java

 Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:nom.db", "usuari", "contrasenya");
```
etc...


### 3. Executar Sentències SQL

Per realitzar una consulta SQL, fem servir la **interfície `Statement`**. Els passos són:

1. Crear un objecte `Statement` a partir de la connexió vàlida. 
   - Per a obtindre un objecte `Statement` cridem al mètode `createStatement()` d'un objecte `Connection vàlid.
2. Utilitzar el mètode `executeQuery()` per executar una consulta SQL.
3. Recollir el resultat de la consulta en un objecte `ResultSet`.

**Exemple:**
```java

Statement stmt = conn.createStatement();
String sql = "SELECT * FROM taula";
ResultSet rs = stmt.executeQuery(sql);

while (rs.next()) {
    System.out.println(rs.getString("nom_columna"));
}
```
o directament

```java
...
ResultSet rs = stmt.executeQuery("SELECT * FROM taula");
...
```

#### Notes sobre `ResultSet`:
- `ResultSet` conté totes les dades retornades pel SGBD en resposta a la consulta.
- Té un punter inicial que apunta al primer element de la primera fila.
- El mètode `next()`:
  - Avança el punter a la següent fila.
  - Retorna `true` si existeix una altra fila, o `false` si s'ha arribat al final.
  - Utilizant els mètodes **`getString()`** i **`getDouble()`** anem obtenint els valors de les diferents columnes. Aquests mètodes reben el nom de la columna com a paràmetre.
  - 

#### Exemple d'ús de `ResultSet`:

```java

while (rs.next()) {
    String valor1 = rs.getString("columna1");
    double valor2 = rs.getDouble("columna2");
    System.out.println("Valor 1: " + valor1 + ", Valor 2: " + valor2);
}
```

---

### 4. Alliberar Recursos

És important alliberar els recursos utilitzats per evitar fuites de memòria o bloquejos de connexió. Això inclou tancar el `ResultSet`, el `Statement` i la `Connection`.

**Exemple:**

```java

rs.close();
stmt.close();
conn.close();
```

---

## IMPORTANT
1. **Imports necessaris:**
   Tots els imports per manejar bases de dades estan inclosos en el paquet `java.sql`.

   ```java
   import java.sql.*;
   ```

2. **Gestió d'Excepcions:**
   La majoria dels mètodes relatius a bases de dades poden llançar l'excepció `SQLException`. Per tant, és essencial utilitzar blocs `try/catch` per capturar i gestionar aquestes excepcions.

   **Exemple:**
   ```java
   try {
       Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/nombredb", "usuari", "contrasenya");
       Statement stmt = conn.createStatement();
       ResultSet rs = stmt.executeQuery("SELECT * FROM taula");

       while (rs.next()) {
           System.out.println(rs.getString("columna1"));
       }

       rs.close();
       stmt.close();
       conn.close();
   } catch (SQLException e) {
       e.printStackTrace();
   }
   ```


## Exemple de Connexió i Consulta a Bases de Dades en Java

A continuació es mostra un exemple complet a partir de l'estructura d'uda BDA Relacional, de com realitzar una connexió a una base de dades **MySQL**, executar una consulta i processar els resultats.

Usem JDBC (Java Database Connectivity), recorda que JDBC és una API de Java que permet connectar aplicacions Java a bases de dades relacionals, enviar consultes SQL i processar els resultats.

En aquest exemple, treballarem amb una base de dades anomenada **Empresa** i una taula anomenada **Empleats**. L'estructura de la taula és la següent:

### Detalls de la Base de Dades i Taula
---

- **DBName**: Empresa
- **TableName**: Empleats
- **Camps**:
  - `NIF` - `Varchar(9)` (Clau primària)
  - `Nom` - `Varchar(100)`
  - `Cognoms` - `Varchar(100)`
  - `Salari` - `Float (6,2)`

---

### Exemple de Connexió JDBC

A continuació, es presenta un codi en Java que es connecta a la base de dades "Empresa", realitza una consulta sobre la taula "Empleats" i mostra els resultats.

#### Codi Java

```java
public static void main(String[] args) {
    // Carregar el Driver
    try {
        Class.forName("com.mysql.jdbc.Driver");

        // Establim la connexió amb la BBDD
        Connection connexio= DriverManager.getConnection("jdbc:mysql://localhost/empresa", "root", "");

        // Preparem la consulta
        Statement sentència= connexio.createStatement();
        String sql= "Select * from empleados";
        ResultSet resultat= sentència.executeQuery(sql);

        // Recorrem el resultSet obtenint el seu contingut
        while(resultat.next()) {
            String nif= resultat.getString("nif");
            String nom= resultat.getString("nombre");
            String cognoms= resultat.getString("Apellidos");
            Double salari= resultat.getDouble("salario");
            System.out.println(nif+ " " + nom + " " +cognoms+ " " +salari);
        }
        
        // Alliberem els recursos
        resultat.close();
        sentència.close();
        connexio.close();

    } catch (ClassNotFoundException e) {
        e.printStackTrace();
    } catch (SQLException e) {
        e.printStackTrace();
    }
}
```

### Explicació del Codi

1. **Càrrega del Driver**:  
   ```java

   Class.forName("com.mysql.jdbc.Driver");
   ```
   Aquest codi carrega el controlador JDBC de MySQL per establir la connexió. És important assegurar-se de tindre el controlador JDBC en el classpath del projecte.

2. **Establiment de la Connexió**:  
   ```java

   Connection connexio= DriverManager.getConnection("jdbc:mysql://localhost/empresa", "root", "");
   ```
   S'utilitza `DriverManager.getConnection` per establir la connexió amb la base de dades. En aquest cas, la URL de connexió és "jdbc:mysql://localhost/empresa" i es connecta amb l'usuari "root" sense contrasenya.

3. **Preparació i Execució de la Consulta**:  
   ```java

   Statement sentència= connexio.createStatement();
   String sql= "Select * from empleados";
   ResultSet resultat= sentència.executeQuery(sql);
   ```
   Creem un objecte `Statement` per enviar la consulta SQL a la base de dades i després executem la consulta amb `executeQuery`, que retorna un `ResultSet` amb els resultats.

4. **Recorregut del ResultSet**:  
   ```java

   while(resultat.next()) {
       String nif= resultat.getString("nif");
       String nom= resultat.getString("nombre");
       String cognoms= resultat.getString("Apellidos");
       Double salari= resultat.getDouble("salario");
       System.out.println(nif+ " " + nom + " " +cognoms+ " " +salari);
   }
   ```
   S'utilitza un bucle `while` per recórrer cada fila del `ResultSet`. `getString()` i `getDouble()` recuperen els valors de les columnes "nif", "nombre", "Apellidos" i "salario".

5. **Alliberament de Recursos**:  
   ```java

   resultat.close();
   sentència.close();
   connexio.close();
   ```
   **És important tancar el `ResultSet`, el `Statement` i la `Connection` per alliberar recursos i evitar fugues de memòria.**

6. **Gestió d'Excepcions**:  

   El codi gestiona dues excepcions possibles:
   - `ClassNotFoundException` per capturar errors de càrrega del controlador.
   - `SQLException` per capturar errors de connexió, consulta o manipulació de la base de dades.



## 4. Execució de Sentències DML

En SQL, les sentències **DML (Data Manipulation Language)** són aquelles que ens permeten treballar amb les dades d'una base de dades. Els tipus principals són:

- **SELECT**: Per obtenir dades d'una base de dades.
- **INSERT**: Per inserir dades a una taula.
- **UPDATE**: Modifica dades existents dins d'una taula.
- **DELETE**: Elimina registres d'una taula (els espais assignats als registres no esborren).

Per executar aquestes sentències SQL en Java, hem d'utilitzar objectes de tipus:

1. **`Statement`**: Permet executar sentències SQL sense paràmetres.
2. **`PreparedStatement`**: Permet executar sentències SQL amb paràmetres.

---

## La Interfície Statement

La interfície **Statement** s'utilitza per crear crear objectes que permenten executar sentències SQL. 
    
Com és una interfície, no es pot instanciar directament.

 En canvi, s'ha d'obtenir mitjançant el mètode `createStatement()` de la classe `Connection` (com hem vist en exemples anteriors).

### Mètodes de `Statement`

| Mètode                                | Descripció                                                                                   |
|---------------------------------------|---------------------------------------------------------------------------------------------|
| **`ResultSet executeQuery(String query)`** | Executa sentències SQL que recuperen dades. Retorna un objecte `ResultSet` amb les dades recuperades. |
| **`int executeUpdate(String query)`** | Executa sentències com `INSERT`, `UPDATE` o `DELETE`. Retorna un enter amb el número de registres afectats. |
| **`boolean execute(String query)`**   | Executa qualsevol consulta SQL. Si retorna `true` si hi ha un `ResultSet`, en cas contrari, `false`. |

En cas que el mètode `execute()` retorne `false` (es a dir, si no retorna un **ResultSet**), podem utilitzar:
- **`getUpdateCount()`**: Per obtenir el nombre de registres afectats si la consulta no retorna un `resultset`.

```java

if (!stmt.execute("UPDATE productes SET preu = preu * 1.1 WHERE preu < 50")) {
    int filesAfectades = stmt.getUpdateCount();
    System.out.println("Files afectades: " + filesAfectades);
}

```

---


## La Interfície PreparedStatement

És molt habitual l’ús de **variables** dins d’una **sentència SQL**, com ara **valors a inserir, actualitzar o filtrar**. Per a aquest tipus de consultes, hem de fer ús de les anomenades **Sentències Preparades** (`Prepared Statements`).


El **`PreparedStatement`** és una versió més avançada i flexible de `Statement`. Es fa servir principalment quan les consultes SQL inclouen **paràmetres variables**.


Els **placeholders** (o marcadors de posició) dins de les consultes SQL es representen amb signes d'interrogació (`?`). Cada placeholder té un índex que comença des de **1** i s'ha d'assignar un valor abans d'executar la consulta.

```java


String insert="insert into empleats values(?,?,?,?)";

```

### Avantatges de `PreparedStatement`:
- Les consultes es **precompilen** una vegada i es poden executar múltiples vegades amb valors d'entrada diferents, millorant el rendiment.
- Major seguretat, ja que ajuda a prevenir atacs **SQL Injection**.

---



### Mètodes de `PreparedStatement`

Els mètodes més comuns de `PreparedStatement` són **`executeQuery`**, **`executeUpdate`** i **`execute`**, que funcionen de manera similar als de `Statement`, però amb l'addició de placeholders (`?`) per valors dinàmics.


**Mètodes de PreparedStatement**


| **Mètode**         | **Descripció**                                                                 |
|---------------------|-------------------------------------------------------------------------------|
| `executeQuery()`    | Retorna un `ResultSet` amb els resultats d'una consulta `SELECT`.            |
| `executeUpdate()`   | Executa sentències SQL com `INSERT`, `UPDATE` o `DELETE` i retorna les files afectades. |
| `execute()`         | Executa qualsevol consulta SQL. Retorna `true` si genera un `ResultSet` o `false` altrament. |

---

### **Exemples dels mètodes amb múltiples placeholders**

- **`executeQuery()`**
- 
  ```java
  PreparedStatement pstmt = conn.prepareStatement(
      "SELECT * FROM productes WHERE preu < ? AND categoria = ? AND disponible = ?"
  );
  pstmt.setDouble(1, 50.0);            // Placeholder 1: Double
  pstmt.setString(2, "Electrònica");   // Placeholder 2: String
  pstmt.setBoolean(3, true);           // Placeholder 3: Boolean

  ResultSet rs = pstmt.executeQuery(); // Executar la consulta

  while (rs.next()) {
      System.out.println("ID: " + rs.getInt("id") + ", Nom: " + rs.getString("nom"));
  }

  rs.close();
  pstmt.close();
  ```

  - La consulta selecciona productes amb un preu menor de 50, de la categoria "Electrònica" i disponibles (`true`).
  - S'utilitzen placeholders per a tipus `Double`, `String` i `Boolean`.

---

- **`executeUpdate()`**:
  ```java
  PreparedStatement pstmt = conn.prepareStatement(
      "UPDATE productes SET preu = ?, categoria = ? WHERE id = ?"
  );
  pstmt.setDouble(1, 59.99);           // Placeholder 1: Double
  pstmt.setString(2, "Accessoris");    // Placeholder 2: String
  pstmt.setInt(3, 5);                  // Placeholder 3: Integer

  int filesActualitzades = pstmt.executeUpdate(); // Executar l'actualització
  System.out.println("Files afectades: " + filesActualitzades);

  pstmt.close();
  ```
  - La consulta actualitza el preu i la categoria d'un producte identificat pel seu `id`.
  - S'utilitzen placeholders per a tipus `Double`, `String` i `Integer`.

---

- **`execute()`**:
  ```java
  PreparedStatement pstmt = conn.prepareStatement(
      "INSERT INTO productes (nom, preu, categoria, disponible) VALUES (?, ?, ?, ?)"
  );
  pstmt.setString(1, "Càmera");        // Placeholder 1: String
  pstmt.setDouble(2, 349.99);          // Placeholder 2: Double
  pstmt.setString(3, "Fotografia");    // Placeholder 3: String
  pstmt.setBoolean(4, true);           // Placeholder 4: Boolean

  boolean resultat = pstmt.execute(); // Executar la consulta
  if (!resultat) {
      System.out.println("Files afectades: " + pstmt.getUpdateCount());
  }

  pstmt.close();
  ```
  - La consulta insereix un nou producte amb diversos atributs (`nom`, `preu`, `categoria` i `disponible`).
  - S'utilitzen placeholders per a tipus `String`, `Double` i `Boolean`.

---



**Nota important**: Quan s'utilitza `PreparedStatement` per executar una consulta `SELECT`, és imprescindible obtenir els resultats mitjançant un objecte `ResultSet`. 


---

**Exemple**:
```java

ResultSet rs = pstmt.executeQuery(); // Obtenim els resultats de la consulta
while (rs.next()) {
    System.out.println("Nom: " + rs.getString("nom")); // Accés a les dades
}
```

---

### **La Classe ResultSet**

El `ResultSet` és una estructura proporcionada per JDBC que conté els **resultats d'una consulta SQL**. Representa un conjunt de files retornades pel SGBD i permet accedir als valors de les columnes de cada fila a través del **nom** o **l'índex** de la columna.

Quan utilitzem un objecte `PreparedStatement` amb el mètode `executeQuery()`, el resultat de la consulta es retorna dins d'un `ResultSet`. 
    
Per accedir a les dades d'aquest resultat, és necessari recórrer les files una a una.

---

#### **Mètodes de ResultSet**
| **Mètode**               | **Tipus Java** | **Descripció**                                                           |
|--------------------------|----------------|---------------------------------------------------------------------------|
| `getString(int columna)` | `String`       | Retorna el valor de la columna indicada (per índex) com a cadena.         |
| `getString(String nom)`  | `String`       | Retorna el valor de la columna indicada (per nom) com a cadena.           |
| `getInt(int columna)`    | `int`          | Retorna el valor de la columna com un enter.                              |
| `getDouble(int columna)` | `double`       | Retorna el valor de la columna com un número decimal.                     |
| `getDate(int columna)`   | `Date`         | Retorna el valor de la columna com una data (`java.sql.Date`).            |

---

#### **Recorregut d'un ResultSet**

El `ResultSet` utilitza un punter que inicialment es troba **abans de la primera fila**. Per accedir a les dades, és necessari avançar el punter fila per fila utilitzant el mètode `next()`. Cada vegada que el punter es mou, es pot accedir als valors de la fila actual utilitzant els mètodes `getX()`.

- **El mètode `next()`**:
  - Mou el punter a la següent fila.
  - Retorna `true` si existeix una fila vàlida.
  - Retorna `false` si no queden més files.

- **Mètodes `getX()`**:
  - Accedeixen al valor d'una columna de la fila actual.
  - Es poden utilitzar amb l'índex de la columna (1-based) o amb el nom de la columna.

---

### **Exemples**

### **Recorregut bàsic del ResultSet**
Aquest exemple recorre les files retornades d'una taula `productes` per mostrar el `id`, el `nom` i el `preu` de cada producte.

```java

ResultSet rs = pstmt.executeQuery(); // Executa una consulta SELECT
while (rs.next()) {
    int id = rs.getInt("id");          // Accés per nom de columna
    String nom = rs.getString("nom");
    double preu = rs.getDouble("preu");
    System.out.println("ID: " + id + ", Nom: " + nom + ", Preu: " + preu);
}
rs.close(); // Tanca el ResultSet
```

### **Accés utilitzant índexs de columnes**
És possible accedir als valors de les columnes utilitzant els seus índexs, que comencen en **1**:

```java
ResultSet rs = pstmt.executeQuery();
while (rs.next()) {
    int id = rs.getInt(1);             // Accés per índex
    String nom = rs.getString(2);
    double preu = rs.getDouble(3);
    System.out.println("ID: " + id + ", Nom: " + nom + ", Preu: " + preu);
}
rs.close();
```

### **Control de ResultSet buit**
Abans de processar les dades, podem verificar si el `ResultSet` conté alguna fila:

```java
ResultSet rs = pstmt.executeQuery();
if (!rs.next()) {
    System.out.println("No s'han trobat resultats.");
} else {
    do {
        System.out.println("ID: " + rs.getInt("id") + ", Nom: " + rs.getString("nom"));
    } while (rs.next());
}
rs.close();
```

### **Gestió de tipus diferents**
Aquest exemple mostra com accedir a valors de columnes amb diferents tipus (`String`, `Double`, `Boolean`, i `Date`):

```java
ResultSet rs = pstmt.executeQuery();
while (rs.next()) {
    String nom = rs.getString("nom");
    double preu = rs.getDouble("preu");
    boolean disponible = rs.getBoolean("disponible");
    java.sql.Date dataAlta = rs.getDate("data_alta");

    System.out.println("Nom: " + nom + ", Preu: " + preu +
                       ", Disponible: " + disponible +
                       ", Data d'Alta: " + dataAlta);
}
rs.close();
```

---

**Recorda:**
- Tancar el `ResultSet` després de processar-lo per evitar fuites de recursos:
```java
rs.close();
```

- Si el `ResultSet` s'utilitza dins d'un bloc `try-with-resources`, es tancarà automàticament:
```java
try (ResultSet rs = pstmt.executeQuery()) {
    while (rs.next()) {
        System.out.println("Nom: " + rs.getString("nom"));
    }
}
```

**Curiositat:**
```
El bloc try-with-resources assegura que tots els recursos declarats dins del seu parèntesi seran automàticament tancats al final del bloc try, fins i tot si es produeix una excepció

han de ser instàncies d’una classe que implementi la interfície AutoCloseable o Closeable com:

 - Gestió d'arxius (BufferedReader, FileInputStream, FileOutputStream, etc.)
 - Connexions a bases de dades (Connection, Statement, ResultSet de JDBC).
 - Connexions de xarxa (sockets, streams).

```


### 5. Execució de Sentències DDL


Encara que la majoria d'operacions que es realitzen des d'una aplicació client són de manipulació de dades (DML), hi ha situacions en què cal dur a terme operacions de **definició de dades (DDL)**. Aquestes operacions permeten definir o modificar l'estructura de la base de dades.

Les principals sentències DDL són:


- **CREATE**: Per crear taules, vistes, índexs, etc.
- **ALTER**: Per modificar l'estructura d'objectes existents.
- **DROP**: Per eliminar objectes com taules o índexs.

Aquestes sentències es poden executar utilitzant les interfícies `Statement` o `PreparedStatement`, però no hi ha cap diferència significativa entre elles, ja que no hi ha paràmetres variables (**placeholders**) en les sentències DDL.



---

### **Exemples d'Operacions DDL amb `Statement`**

---

**1. Crear una Taula (`CREATE`)**
Aquest exemple crea la taula `usuaris` només si no existeix.

```java
Statement stmt = conn.createStatement();
String sql = "CREATE TABLE IF NOT EXISTS usuaris (" +
             "id INT AUTO_INCREMENT PRIMARY KEY, " +
             "nom VARCHAR(100) NOT NULL, " +
             "correu VARCHAR(100) UNIQUE NOT NULL)";
stmt.executeUpdate(sql); // Executa la sentència CREATE
stmt.close();
```

---

**2. Modificar una Taula (`ALTER`)**
Aquest exemple afegeix una columna `edat` a la taula `usuaris`.

```java
Statement stmt = conn.createStatement();
String sql = "ALTER TABLE usuaris ADD edat INT";
stmt.executeUpdate(sql); // Executa la sentència ALTER
stmt.close();
```

---

**3. Eliminar una Taula (`DROP`)**
Aquest exemple elimina la taula `usuaris` si existeix.

```java
Statement stmt = conn.createStatement();
String sql = "DROP TABLE IF EXISTS usuaris";
stmt.executeUpdate(sql); // Executa la sentència DROP
stmt.close();
```

---

En les anteriors operacions:
- El modificador **`IF NOT EXISTS`** evita errors si la taula ja existeix (en `CREATE`).
- El modificador **`IF EXISTS`** evita errors si la taula no existeix (en `DROP`).
- Recorda tancar el `Statement` per alliberar recursos després de cada operació.

---



# En Resum 



1. **Carregar el Driver JDBC**:

     ```java
     Class.forName("com.mysql.cj.jdbc.Driver");
     ```

     - Carrega el driver necessari per establir la connexió amb la base de dades.

     - **Nota**: Si utilitzem **Maven o Gradle**, no és necessari incloure aquesta línia, ja que els drivers es carreguen automàticament.

2. **Establir la connexió amb la base de dades**:
  
     ```java

     Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/nomdb", "usuari", "contrasenya");
     ```
     - **URL**: Localització de la base de dades (e.g., `jdbc:mysql://localhost:3306/nomdb`).
     - **Usuari** i **Contrasenya**: Credencials d'accés a la base de dades.
   - **Nota**: Per altres sistemes, la URL pot variar (e.g., SQLite: `jdbc:sqlite:ruta`).

3. **Crear l'objecte per executar sentències SQL**:
   - Per executar SQL, primer creeu un objecte que us permeti enviar sentències a la base de dades:
     - **Amb un `Statement`**:
       ```java
       Statement stmt = conn.createStatement();
       ```
     - **Amb un `PreparedStatement`** (si hi ha paràmetres variables):
       ```java
       PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM taula WHERE columna = ?");
       ```

4. **Executar una sentència SQL**:
   - Per executar SQL utilitzeu els mètodes següents:
     - **Amb `Statement`**:
       - **Consulta `SELECT`**:
         ```java
         ResultSet rs = stmt.executeQuery("SELECT * FROM taula");
         ```
       - **Operacions DML (`INSERT`, `UPDATE`, `DELETE`)**:
         ```java
         int filesAfectades = stmt.executeUpdate("INSERT INTO taula (columna) VALUES ('valor')");
         ```
     - **Amb `PreparedStatement`**:
       - **Assignació de valors**:
         ```java
         pstmt.setString(1, "valor");
         ```
       - **Execució de la consulta**:
         ```java
         ResultSet rs = pstmt.executeQuery();  // Per a SELECT
         ```
       - **Execució d'operacions DML**:
         ```java
         int filesAfectades = pstmt.executeUpdate();
         ```

5. **Recuperar i processar resultats**:
   - **Amb un `ResultSet`** (per consultes `SELECT`):

     ```java

     while (rs.next()) {
         System.out.println(rs.getString("columna"));
     }
     ```

6. **Alliberar recursos**:
   - Tanqueu els objectes per evitar fugues de memòria:
     ```java
     rs.close();
     stmt.close();
     conn.close();
     ```

---



## Exemple amb `Statement`


```java
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class ExempleStatement {
    public static void main(String[] args) {
        try {
            // Carregar el driver (opcional amb Maven/Gradle)
            Class.forName("org.sqlite.JDBC");

            // Establir la connexió
            Connection conn = DriverManager.getConnection("jdbc:sqlite:src/main/resources/nomdb.sqlite");


            // Crear Statement
            Statement stmt = conn.createStatement();

            // Executar un SELECT
            ResultSet rs = stmt.executeQuery("SELECT * FROM taula");
            while (rs.next()) {
                System.out.println("Columna1: " + rs.getString("columna1"));
            }

            // Executar un INSERT
            int filesInsertades = stmt.executeUpdate("INSERT INTO taula (columna1) VALUES ('valorNou')");
            System.out.println("Files insertades: " + filesInsertades);

            // Executar un DELETE
            int filesEsborrades = stmt.executeUpdate("DELETE FROM taula WHERE columna1 = 'valorNou'");
            System.out.println("Files esborrades: " + filesEsborrades);

            // Tancar recursos
            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
```

---

### Exemple amb `PreparedStatement`


```java
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ExemplePreparedStatement {
    public static void main(String[] args) {
        try {
            // Carregar el driver (opcional amb Maven/Gradle)
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establir la connexió
            Connection conn = DriverManager.getConnection("jdbc:sqlite:src/main/resources/nomdb.sqlite");


            // SELECT amb PreparedStatement
            PreparedStatement pstmtSelect = conn.prepareStatement("SELECT * FROM taula WHERE columna1 = ?");
            pstmtSelect.setString(1, "valor");
            ResultSet rs = pstmtSelect.executeQuery();
            while (rs.next()) {
                System.out.println("Columna1: " + rs.getString("columna1"));
            }

            // INSERT amb PreparedStatement
            PreparedStatement pstmtInsert = conn.prepareStatement("INSERT INTO taula (columna1) VALUES (?)");
            pstmtInsert.setString(1, "valorNou");
            int filesInsertades = pstmtInsert.executeUpdate();
            System.out.println("Files insertades: " + filesInsertades);

            // DELETE amb PreparedStatement
            PreparedStatement pstmtDelete = conn.prepareStatement("DELETE FROM taula WHERE columna1 = ?");
            pstmtDelete.setString(1, "valorNou");
            int filesEsborrades = pstmtDelete.executeUpdate();
            System.out.println("Files esborrades: " + filesEsborrades);

            // Tancar recursos
            rs.close();
            pstmtSelect.close();
            pstmtInsert.close();
            pstmtDelete.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
```


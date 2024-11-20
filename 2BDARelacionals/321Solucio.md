### **Solució**



```java

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class EmpresaDB {
    public static void main(String[] args) {
        // Ruta de la base de dades SQLite
        String url = "jdbc:sqlite:src/main/resources/empresa.sqlite";

        try (Connection conn = DriverManager.getConnection(url)) {
            // Assegurar-nos que la connexió està activa
            if (conn != null) {
                Statement stmt = conn.createStatement();

                // 1. Crear la taula `Departaments`
                String sqlCreateDepartaments = "CREATE TABLE IF NOT EXISTS Departaments (" +
                                               "IdDepartament INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                               "NomDepartament VARCHAR(100) NOT NULL, " +
                                               "Responsable VARCHAR(100))";
                stmt.executeUpdate(sqlCreateDepartaments);
                System.out.println("Taula 'Departaments' creada.");

                // 2. Modificar la taula `Empleats`
                String sqlAlterEmpleats = "ALTER TABLE Empleats ADD COLUMN IdDepartament INTEGER REFERENCES Departaments(IdDepartament)";
                stmt.executeUpdate(sqlAlterEmpleats);
                System.out.println("Columna 'IdDepartament' afegida a la taula 'Empleats'.");

                // 3. Inserir dades a la taula `Departaments`
                String sqlInsertDepartaments = "INSERT INTO Departaments (NomDepartament, Responsable) VALUES " +
                                               "('Recursos Humans', 'Marta Pérez'), " +
                                               "('Desenvolupament', 'Jaume Martí'), " +
                                               "('Comptabilitat', 'Fina Soler')";
                stmt.executeUpdate(sqlInsertDepartaments);
                System.out.println("Departaments inserits.");

                // 4. Assignar un departament a cada empleat
                String sqlUpdateEmpleats = "UPDATE Empleats SET IdDepartament = CASE " +
                                           "WHEN NIF = '123456789' THEN 1 " + // Recursos Humans
                                           "WHEN NIF = '987654321' THEN 2 " + // Desenvolupament
                                           "WHEN NIF = '111111111' THEN 3 " + // Comptabilitat
                                           "WHEN NIF = '222222222' THEN 1 " +
                                           "WHEN NIF = '333333333' THEN 2 " +
                                           "WHEN NIF = '444444444' THEN 3 END";
                stmt.executeUpdate(sqlUpdateEmpleats);
                System.out.println("Departaments assignats als empleats.");

                // 5. Consultar empleats i departaments
                String sqlSelect = "SELECT e.NIF, e.Nom, e.Cognoms, d.NomDepartament " +
                                   "FROM Empleats e " +
                                   "LEFT JOIN Departaments d ON e.IdDepartament = d.IdDepartament";
                ResultSet rs = stmt.executeQuery(sqlSelect);

                System.out.println("\n--- Empleats i Departaments ---");
                while (rs.next()) {
                    System.out.println("NIF: " + rs.getString("NIF") +
                                       ", Nom: " + rs.getString("Nom") +
                                       ", Cognoms: " + rs.getString("Cognoms") +
                                       ", Departament: " + rs.getString("NomDepartament"));
                }

                // Tancar ResultSet i Statement
                rs.close();
                stmt.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
```

---

### **Explicació del Codi**

1. **Connexió a la Base de Dades**:
   - S'utilitza `DriverManager.getConnection(url)` per connectar-se a la base de dades SQLite.

2. **Crear la Taula `Departaments`**:
   - Es defineix amb les columnes `IdDepartament`, `NomDepartament`, i `Responsable`.

3. **Modificar la Taula `Empleats`**:
   - Es crea una nova columna `IdDepartament` com a clau forana.

4. **Inserir Dades a `Departaments`**:
   - Es defineixen tres departaments inicials.

5. **Actualitzar la Taula `Empleats`**:
   - Assignem un departament a cada empleat utilitzant una instrucció `CASE`.

6. **Consulta de les Taules**:
   - Utilitzem un `LEFT JOIN` per combinar les taules `Empleats` i `Departaments` i mostrar la informació conjunta.

---

### **Sortida Esperada**
```plaintext
Taula 'Departaments' creada.
Columna 'IdDepartament' afegida a la taula 'Empleats'.
Departaments inserits.
Departaments assignats als empleats.

--- Empleats i Departaments ---
NIF: 123456789, Nom: Pep, Cognoms: Martínez González, Departament: Recursos Humans
NIF: 987654321, Nom: Marta, Cognoms: Soler Sánchez, Departament: Desenvolupament
NIF: 111111111, Nom: Fina, Cognoms: Valls Beltran, Departament: Comptabilitat
NIF: 222222222, Nom: Josefa, Cognoms: Beltran Benedito, Departament: Recursos Humans
NIF: 333333333, Nom: Jaime, Cognoms: Valls Abad, Departament: Desenvolupament
NIF: 444444444, Nom: Sergi, Cognoms: Navarro Pérez, Departament: Comptabilitat
``` 

.




Aquí tens la solució completa amb totes les consultes implementades dins d’un únic `main`. Per tal de mantenir l’organització i evitar la redundància, les consultes estan encapsulades en blocs lògics però dins del mateix mètode.

```java
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class EmpresaConsultes {
    public static void main(String[] args) {
        String dbUrl = "jdbc:sqlite:src/main/resources/empresa.sqlite";

        try (Connection conn = DriverManager.getConnection(dbUrl)) {
            if (conn != null) {
                System.out.println("--- CONSULTES SQL AMB PREPAREDSTATEMENT ---");

                // 1. Consulta tots els empleats
                System.out.println("\n1. Tots els empleats:");
                String sql1 = "SELECT NIF, Nom, Cognoms FROM Empleats";
                try (PreparedStatement pstmt = conn.prepareStatement(sql1);
                     ResultSet rs = pstmt.executeQuery()) {
                    while (rs.next()) {
                        System.out.println("NIF: " + rs.getString("NIF") +
                                           ", Nom: " + rs.getString("Nom") +
                                           ", Cognoms: " + rs.getString("Cognoms"));
                    }
                }

                // 2. Consulta els empleats d’un departament específic
                System.out.println("\n2. Empleats del departament 'Desenvolupament':");
                String sql2 = "SELECT e.NIF, e.Nom, e.Cognoms " +
                              "FROM Empleats e " +
                              "JOIN Departaments d ON e.IdDepartament = d.IdDepartament " +
                              "WHERE d.NomDepartament = ?";
                try (PreparedStatement pstmt = conn.prepareStatement(sql2)) {
                    pstmt.setString(1, "Desenvolupament");
                    try (ResultSet rs = pstmt.executeQuery()) {
                        while (rs.next()) {
                            System.out.println("NIF: " + rs.getString("NIF") +
                                               ", Nom: " + rs.getString("Nom") +
                                               ", Cognoms: " + rs.getString("Cognoms"));
                        }
                    }
                }

                // 3. Consulta els empleats amb un salari superior a un valor donat
                System.out.println("\n3. Empleats amb salari superior a 2500:");
                String sql3 = "SELECT NIF, Nom, Cognoms, Salari FROM Empleats WHERE Salari > ?";
                try (PreparedStatement pstmt = conn.prepareStatement(sql3)) {
                    pstmt.setDouble(1, 2500.00);
                    try (ResultSet rs = pstmt.executeQuery()) {
                        while (rs.next()) {
                            System.out.println("NIF: " + rs.getString("NIF") +
                                               ", Nom: " + rs.getString("Nom") +
                                               ", Cognoms: " + rs.getString("Cognoms") +
                                               ", Salari: " + rs.getDouble("Salari"));
                        }
                    }
                }

                // 4. Consulta empleats que treballen en un departament amb més de 2 empleats
                System.out.println("\n4. Empleats en departaments amb més de 2 empleats:");
                String sql4 = "SELECT e.NIF, e.Nom, e.Cognoms " +
                              "FROM Empleats e " +
                              "WHERE e.IdDepartament IN (" +
                              "    SELECT IdDepartament " +
                              "    FROM Empleats " +
                              "    GROUP BY IdDepartament " +
                              "    HAVING COUNT(*) > 2)";
                try (PreparedStatement pstmt = conn.prepareStatement(sql4);
                     ResultSet rs = pstmt.executeQuery()) {
                    while (rs.next()) {
                        System.out.println("NIF: " + rs.getString("NIF") +
                                           ", Nom: " + rs.getString("Nom") +
                                           ", Cognoms: " + rs.getString("Cognoms"));
                    }
                }

                // 5. Mostra la informació completa d’un empleat concret
                System.out.println("\n5. Informació completa de l'empleat amb NIF '123456789':");
                String sql5 = "SELECT e.NIF, e.Nom, e.Cognoms, e.Salari, d.NomDepartament " +
                              "FROM Empleats e " +
                              "LEFT JOIN Departaments d ON e.IdDepartament = d.IdDepartament " +
                              "WHERE e.NIF = ?";
                try (PreparedStatement pstmt = conn.prepareStatement(sql5)) {
                    pstmt.setString(1, "123456789");
                    try (ResultSet rs = pstmt.executeQuery()) {
                        if (rs.next()) {
                            System.out.println("NIF: " + rs.getString("NIF") +
                                               ", Nom: " + rs.getString("Nom") +
                                               ", Cognoms: " + rs.getString("Cognoms") +
                                               ", Salari: " + rs.getDouble("Salari") +
                                               ", Departament: " + rs.getString("NomDepartament"));
                        }
                    }
                }

                // 6. Consulta empleats amb un salari superior al salari mitjà del seu departament
                System.out.println("\n6. Empleats amb salari superior al salari mitjà del seu departament:");
                String sql6 = "SELECT e.NIF, e.Nom, e.Cognoms, e.Salari " +
                              "FROM Empleats e " +
                              "WHERE e.Salari > (SELECT AVG(Salari) FROM Empleats WHERE IdDepartament = e.IdDepartament)";
                try (PreparedStatement pstmt = conn.prepareStatement(sql6);
                     ResultSet rs = pstmt.executeQuery()) {
                    while (rs.next()) {
                        System.out.println("NIF: " + rs.getString("NIF") +
                                           ", Nom: " + rs.getString("Nom") +
                                           ", Cognoms: " + rs.getString("Cognoms") +
                                           ", Salari: " + rs.getDouble("Salari"));
                    }
                }

                // 7. Mostra els departaments amb la suma total dels salaris superior a un valor donat
                System.out.println("\n7. Departaments amb suma total de salaris superior a 5000:");
                String sql7 = "SELECT d.NomDepartament, SUM(e.Salari) AS TotalSalari " +
                              "FROM Departaments d " +
                              "JOIN Empleats e ON d.IdDepartament = e.IdDepartament " +
                              "GROUP BY d.NomDepartament " +
                              "HAVING SUM(e.Salari) > ?";
                try (PreparedStatement pstmt = conn.prepareStatement(sql7)) {
                    pstmt.setDouble(1, 5000.00);
                    try (ResultSet rs = pstmt.executeQuery()) {
                        while (rs.next()) {
                            System.out.println("Departament: " + rs.getString("NomDepartament") +
                                               ", Total Salaris: " + rs.getDouble("TotalSalari"));
                        }
                    }
                }

                // 8. Consulta empleats que no tenen departament assignat
                System.out.println("\n8. Empleats sense departament assignat:");
                String sql8 = "SELECT NIF, Nom, Cognoms FROM Empleats WHERE IdDepartament IS NULL";
                try (PreparedStatement pstmt = conn.prepareStatement(sql8);
                     ResultSet rs = pstmt.executeQuery()) {
                    while (rs.next()) {
                        System.out.println("NIF: " + rs.getString("NIF") +
                                           ", Nom: " + rs.getString("Nom") +
                                           ", Cognoms: " + rs.getString("Cognoms"));
                    }
                }

                // 9. Consulta el salari màxim i mínim per a un departament específic
                System.out.println("\n9. Salari màxim i mínim del departament 'Comptabilitat':");
                String sql9 = "SELECT MAX(e.Salari) AS SalariMaxim, MIN(e.Salari) AS SalariMinim " +
                              "FROM Empleats e " +
                              "JOIN Departaments d ON e.IdDepartament = d.IdDepartament " +
                              "WHERE d.NomDepartament = ?";
                try (PreparedStatement pstmt = conn.prepareStatement(sql9)) {
                    pstmt.setString(1, "Comptabilitat");
                    try (ResultSet rs = pstmt.executeQuery()) {
                        if (rs.next()) {
                            System.out.println("Salari Màxim: " + rs.getDouble("SalariMaxim") +
                                               ", Salari Mínim: " + rs.getDouble("SalariMinim"));
                        }
                    }
                }

                // 10. Consulta empleats que treballen en un departament amb la paraula 'Desenvolupament'
                System.out.println("\n10. Empleats en departaments que contenen 'Desenvolupament':");
                String sql10 = "SELECT e.NIF, e.Nom, e.Cognoms " +
                               "FROM Empleats e " +
                               "JOIN Departaments d ON e.IdDepartament = d.IdDepartament " +
                               "WHERE d.NomDepartament LIKE ?";
                try (PreparedStatement pstmt = conn.prepareStatement(sql10)) {
                    pstmt.setString(1, "%Desenvolupament%");
                    try (ResultSet rs = pstmt.executeQuery()) {
                        while (rs.next()) {
                            System.out.println("NIF: " + rs.getString("NIF") +
                                               ", Nom: " + rs.getString("Nom") +
                                               ", Cognoms: " + rs.getString("Cognoms"));
                        }
                    }
                }
            }
        } catch (

Exception e) {
            e.printStackTrace();
        }
    }
}
```


### Per separat:



### **1. Consulta tots els empleats**

```java
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ConsultaTotsElsEmpleats {
    public static void main(String[] args) {
        String dbUrl = "jdbc:sqlite:src/main/resources/empresa.sqlite";

        try (Connection conn = DriverManager.getConnection(dbUrl)) {
            String sql = "SELECT NIF, Nom, Cognoms FROM Empleats";
            try (PreparedStatement pstmt = conn.prepareStatement(sql);
                 ResultSet rs = pstmt.executeQuery()) {
                System.out.println("Tots els empleats:");
                while (rs.next()) {
                    System.out.println("NIF: " + rs.getString("NIF") +
                                       ", Nom: " + rs.getString("Nom") +
                                       ", Cognoms: " + rs.getString("Cognoms"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
```

---

### **2. Consulta els empleats d’un departament específic**

```java
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ConsultaEmpleatsPerDepartament {
    public static void main(String[] args) {
        String dbUrl = "jdbc:sqlite:src/main/resources/empresa.sqlite";

        try (Connection conn = DriverManager.getConnection(dbUrl)) {
            String sql = "SELECT e.NIF, e.Nom, e.Cognoms " +
                         "FROM Empleats e " +
                         "JOIN Departaments d ON e.IdDepartament = d.IdDepartament " +
                         "WHERE d.NomDepartament = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, "Desenvolupament");
                try (ResultSet rs = pstmt.executeQuery()) {
                    System.out.println("Empleats del departament 'Desenvolupament':");
                    while (rs.next()) {
                        System.out.println("NIF: " + rs.getString("NIF") +
                                           ", Nom: " + rs.getString("Nom") +
                                           ", Cognoms: " + rs.getString("Cognoms"));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
```

---

### **3. Consulta els empleats amb un salari superior a un valor donat**

```java
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ConsultaEmpleatsPerSalari {
    public static void main(String[] args) {
        String dbUrl = "jdbc:sqlite:src/main/resources/empresa.sqlite";

        try (Connection conn = DriverManager.getConnection(dbUrl)) {
            String sql = "SELECT NIF, Nom, Cognoms, Salari FROM Empleats WHERE Salari > ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setDouble(1, 2500.00);
                try (ResultSet rs = pstmt.executeQuery()) {
                    System.out.println("Empleats amb salari superior a 2500:");
                    while (rs.next()) {
                        System.out.println("NIF: " + rs.getString("NIF") +
                                           ", Nom: " + rs.getString("Nom") +
                                           ", Cognoms: " + rs.getString("Cognoms") +
                                           ", Salari: " + rs.getDouble("Salari"));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
```

---

### **4. Consulta empleats que treballen en un departament amb més de 2 empleats**

```java
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ConsultaDepartamentsMesDos {
    public static void main(String[] args) {
        String dbUrl = "jdbc:sqlite:src/main/resources/empresa.sqlite";

        try (Connection conn = DriverManager.getConnection(dbUrl)) {
            String sql = "SELECT e.NIF, e.Nom, e.Cognoms " +
                         "FROM Empleats e " +
                         "WHERE e.IdDepartament IN (" +
                         "    SELECT IdDepartament " +
                         "    FROM Empleats " +
                         "    GROUP BY IdDepartament " +
                         "    HAVING COUNT(*) > 2)";
            try (PreparedStatement pstmt = conn.prepareStatement(sql);
                 ResultSet rs = pstmt.executeQuery()) {
                System.out.println("Empleats en departaments amb més de 2 empleats:");
                while (rs.next()) {
                    System.out.println("NIF: " + rs.getString("NIF") +
                                       ", Nom: " + rs.getString("Nom") +
                                       ", Cognoms: " + rs.getString("Cognoms"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
```

---

### **5. Mostra la informació completa d’un empleat concret**

```java
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ConsultaInformacioEmpleat {
    public static void main(String[] args) {
        String dbUrl = "jdbc:sqlite:src/main/resources/empresa.sqlite";

        try (Connection conn = DriverManager.getConnection(dbUrl)) {
            String sql = "SELECT e.NIF, e.Nom, e.Cognoms, e.Salari, d.NomDepartament " +
                         "FROM Empleats e " +
                         "LEFT JOIN Departaments d ON e.IdDepartament = d.IdDepartament " +
                         "WHERE e.NIF = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, "123456789");
                try (ResultSet rs = pstmt.executeQuery()) {
                    System.out.println("Informació completa de l'empleat amb NIF '123456789':");
                    if (rs.next()) {
                        System.out.println("NIF: " + rs.getString("NIF") +
                                           ", Nom: " + rs.getString("Nom") +
                                           ", Cognoms: " + rs.getString("Cognoms") +
                                           ", Salari: " + rs.getDouble("Salari") +
                                           ", Departament: " + rs.getString("NomDepartament"));
                    } else {
                        System.out.println("No s'ha trobat cap empleat amb aquest NIF.");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
```

---

## solucio sense finally

Aquí tens el mateix patró de codi, però amb els recursos tancats dins del bloc `try` sense utilitzar `finally`. Això és una alternativa per mantenir el codi llegible i gestionar els recursos de forma explícita.

---

### **1. Consulta tots els empleats**

```java
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ConsultaTotsElsEmpleats {
    public static void main(String[] args) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DriverManager.getConnection("jdbc:sqlite:src/main/resources/empresa.sqlite");
            String sql = "SELECT NIF, Nom, Cognoms FROM Empleats";
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            System.out.println("Tots els empleats:");
            while (rs.next()) {
                System.out.println("NIF: " + rs.getString("NIF") +
                                   ", Nom: " + rs.getString("Nom") +
                                   ", Cognoms: " + rs.getString("Cognoms"));
            }
            rs.close();
            pstmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
```

---

### **2. Consulta els empleats d’un departament específic**

```java
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ConsultaEmpleatsPerDepartament {
    public static void main(String[] args) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DriverManager.getConnection("jdbc:sqlite:src/main/resources/empresa.sqlite");
            String sql = "SELECT e.NIF, e.Nom, e.Cognoms " +
                         "FROM Empleats e " +
                         "JOIN Departaments d ON e.IdDepartament = d.IdDepartament " +
                         "WHERE d.NomDepartament = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, "Desenvolupament");
            rs = pstmt.executeQuery();

            System.out.println("Empleats del departament 'Desenvolupament':");
            while (rs.next()) {
                System.out.println("NIF: " + rs.getString("NIF") +
                                   ", Nom: " + rs.getString("Nom") +
                                   ", Cognoms: " + rs.getString("Cognoms"));
            }
            rs.close();
            pstmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
```

---

### **3. Consulta els empleats amb un salari superior a un valor donat**

```java
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ConsultaEmpleatsPerSalari {
    public static void main(String[] args) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DriverManager.getConnection("jdbc:sqlite:src/main/resources/empresa.sqlite");
            String sql = "SELECT NIF, Nom, Cognoms, Salari FROM Empleats WHERE Salari > ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setDouble(1, 2500.00);
            rs = pstmt.executeQuery();

            System.out.println("Empleats amb salari superior a 2500:");
            while (rs.next()) {
                System.out.println("NIF: " + rs.getString("NIF") +
                                   ", Nom: " + rs.getString("Nom") +
                                   ", Cognoms: " + rs.getString("Cognoms") +
                                   ", Salari: " + rs.getDouble("Salari"));
            }
            rs.close();
            pstmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
```

---

### **4. Consulta empleats que treballen en un departament amb més de 2 empleats**

```java
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ConsultaDepartamentsMesDos {
    public static void main(String[] args) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DriverManager.getConnection("jdbc:sqlite:src/main/resources/empresa.sqlite");
            String sql = "SELECT e.NIF, e.Nom, e.Cognoms " +
                         "FROM Empleats e " +
                         "WHERE e.IdDepartament IN (" +
                         "    SELECT IdDepartament " +
                         "    FROM Empleats " +
                         "    GROUP BY IdDepartament " +
                         "    HAVING COUNT(*) > 2)";
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            System.out.println("Empleats en departaments amb més de 2 empleats:");
            while (rs.next()) {
                System.out.println("NIF: " + rs.getString("NIF") +
                                   ", Nom: " + rs.getString("Nom") +
                                   ", Cognoms: " + rs.getString("Cognoms"));
            }
            rs.close();
            pstmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
```

---

### **5. Mostra la informació completa d’un empleat concret**

```java
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ConsultaInformacioEmpleat {
    public static void main(String[] args) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DriverManager.getConnection("jdbc:sqlite:src/main/resources/empresa.sqlite");
            String sql = "SELECT e.NIF, e.Nom, e.Cognoms, e.Salari, d.NomDepartament " +
                         "FROM Empleats e " +
                         "LEFT JOIN Departaments d ON e.IdDepartament = d.IdDepartament " +
                         "WHERE e.NIF = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, "123456789");
            rs = pstmt.executeQuery();

            System.out.println("Informació completa de l'empleat amb NIF '123456789':");
            if (rs.next()) {
                System.out.println("NIF: " + rs.getString("NIF") +
                                   ", Nom: " + rs.getString("Nom") +
                                   ", Cognoms: " + rs.getString("Cognoms") +
                                   ", Salari: " + rs.getDouble("Salari") +
                                   ", Departament: " + rs.getString("NomDepartament"));
            } else {
                System.out.println("No s'ha trobat cap empleat amb aquest NIF.");
            }
            rs.close();
            pstmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
```

---

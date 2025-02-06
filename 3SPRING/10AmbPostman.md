## **Proves de consulta i inserció d'usuaris amb Postman**

Una vegada implementada la gestió d’usuaris en **Spring Security** amb **JDBC Authentication**, hem de provar que el sistema funcione correctament. Farem aquestes proves utilitzant **Postman**.

---

### **1. Inserir un nou usuari**

Per registrar un nou usuari, farem una petició **POST** a l'endpoint de registre:

### **Petició:**
```
POST http://localhost:8080/users/register/
```

### **Mètode:**
`POST`

### **Paràmetres:**
- `username` → El nom d'usuari que volem registrar.
- `password` → La contrasenya de l'usuari.

### **Forma d'enviar les dades:**
- **Opció 1: Paràmetres en la URL** *(més senzill)*
  ```
  http://localhost:8080/users/register/?username=jaume&password=1234
  ```

- **Opció 2: JSON en el cos de la petició** *(més estructurat, recomanat si fem servir DTO)*
  - Canviem el `UserController` per acceptar `@RequestBody` en comptes de `@RequestParam`.
  - Seleccionem **Body -> raw -> JSON** en Postman i enviem aquest JSON:
  ```json
  {
      "username": "jaume",
      "password": "1234"
  }
  ```

### **Resposta esperada:**
- Si l’usuari **ja existeix**:
  ```plaintext
  ERROR: l'usuari ja existeix
  ```
- Si el registre **és correcte**:
  ```plaintext
  OK
  ```

---

## **2. Autenticar un usuari registrat**

Una vegada registrat l’usuari, podem provar d’autenticar-nos amb **Spring Security**.

### **Petició:**
```
GET http://localhost:8080/login
```

### **Mètode:**
`GET`

### **Autenticació en Postman:**
1. **Anar a la pestanya "Authorization"** en Postman.
2. Seleccionar **"Basic Auth"**.
3. Introduir:
   - **Username:** `jaume`
   - **Password:** `1234`
4. Fer clic a **Send**.

### **Resposta esperada:**
- Si les credencials **són correctes**, s’iniciarà sessió i es rebrà un **codi d’estat HTTP 200**.
- Si l'usuari o la contrasenya **no són correctes**, es rebrà un **codi HTTP 401 Unauthorized**.

---

## **3. Consultar la base de dades des de Postman**

Podem comprovar que l’usuari s'ha registrat correctament accedint directament a la base de dades **H2**.

### **Petició:**
```
GET http://localhost:8080/h2-console
```

### **Passos en el navegador:**
1. Obrir `http://localhost:8080/h2-console`.
2. Introduir la configuració de connexió:
   - **JDBC URL:** `jdbc:h2:file:./DBCiutats`
   - **User Name:** `sa`
   - **Password:** `sa`
3. Fer clic a **Connect**.
4. Executar aquesta consulta SQL per veure els usuaris registrats:
   ```sql
   SELECT * FROM users;
   ```
5. Per veure els rols dels usuaris:
   ```sql
   SELECT * FROM authorities;
   ```

---

## **4. Intentar accedir a un endpoint protegit**

Una vegada registrat un usuari, podem provar d'accedir a una pàgina protegida que requereix autenticació.

### **Petició:**
```
GET http://localhost:8080/admin
```

### **Mètode:**
`GET`

### **Autenticació en Postman:**
- Anem a **Authorization -> Basic Auth**.
- Introduïm les credencials d'un usuari registrat (`jaume` i `1234`).

### **Respostes possibles:**
- Si l’usuari **no està autenticat**, es rebrà un error **401 Unauthorized**.
- Si l’usuari **no té permisos suficients**, es rebrà un error **403 Forbidden**.
- Si l’usuari **té accés a l’endpoint**, es mostrarà la resposta esperada.

---

--

## **3. Proves en Postman amb DTO**

Ara, en comptes de passar els paràmetres en la URL, enviarem un **JSON** en el cos de la petició.

📌 **Petició POST per a registrar un usuari**
```
POST http://localhost:8080/users/register/
```

📌 **Cos de la petició en JSON:**
```json
{
    "username": "jaume",
    "password": "1234"
}
```

📌 **Respostes possibles:**
- Si el registre és correcte:
  ```plaintext
  OK
  ```
- Si el nom d’usuari ja existeix:
  ```plaintext
  ERROR: l'usuari ja existeix
  ```
- Si falta algun camp (per exemple, el nom d’usuari està buit):
  ```json
  {
      "username": "El nom d'usuari és obligatori"
  }
  ```

---

## **4. Beneficis de l'ús del DTO en aquest projecte**
1. **Separa la lògica de la petició HTTP de la lògica de negoci**.
2. **Permet validació automàtica de les dades** abans d'arribar al controlador.
3. **Millora la llegibilitat i mantenibilitat del codi**.
4. **És fàcil d'extendre** si en el futur es volen afegir més camps com "email" o "nom complet".

---

Amb aquesta implementació, el projecte està més estructurat i **segueix les bones pràctiques recomanades per Spring Security i Spring Boot** 🚀.

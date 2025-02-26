# **Examen Pràctic - Spring Boot**
## **Duració: 1 hora - 1 hora 30 minuts**

### **Indicacions generals:**
- Aquest examen ha de ser escrit a mà en paper.
- Llegeix atentament cada enunciat abans de respondre.
- Pots fer anotacions i comentaris explicatius si creus necessari.
- La claredat i correcció del codi seran valorades.
- Es permet l'ús de llapis i goma.

---

## **📌 Enunciat**

**Situació:** Treballes com a desenvolupador en una empresa i et demanen crear una API REST per gestionar productes en una botiga online.

**Tasca:** Implementa un sistema complet per a l’entitat `Producte`, seguint una estructura modular amb **DTOs, capa de servei i repositori.**

### **📂 1. Requeriments de l’API**  
La teua API REST ha de permetre:
✔ **Consultar tots els productes.**  
✔ **Consultar un producte per ID.**  
✔ **Crear un nou producte.**  
✔ **Eliminar un producte per ID.**  
✔ **Modificar un producte existent.**  

**L’objecte `Producte` ha de tindre els següents camps:**
- `id`: Identificador únic.  
- `nom`: Nom del producte.  
- `preu`: Preu del producte.  
- `stock`: Quantitat disponible.  

**📜 Escriu el codi corresponent a les següents parts:**

---

### **📝 1. Definir l’Entitat JPA `Producte`** *(5-10 min)*
Crea una entitat JPA per a `Producte` amb anotacions adequades per gestionar la persistència en la base de dades.

---

### **📝 2. Crear el DTO `ProducteDTO`** *(5-10 min)*
Escriu una classe DTO per a `Producte` que només continga els camps necessaris per transferir dades sense incloure l'ID.

---

### **📝 3. Escriure el `ProducteRepository` (DAO)** *(5 min)*
Crea una interfície que gestione l’accés a la base de dades per a l’entitat `Producte`.

---

### **📝 4. Implementar `ProducteService` (Capa de Servei)** *(10-15 min)*
Crea una classe de servei que implemente la lògica de negoci per gestionar els productes. Aquest servei ha de:
- Convertir entre `Producte` i `ProducteDTO`.
- Implementar mètodes per obtenir tots els productes, buscar per ID i eliminar per ID.

---

### **📝 5. Escriure el `ProducteController`** *(10-15 min)*
Implementa un controlador REST amb els endpoints següents:
- **GET `/api/productes`** → Retorna tots els productes.
- **GET `/api/productes/{id}`** → Retorna un producte per ID.
- **DELETE `/api/productes/{id}`** → Elimina un producte per ID.


---

## **📊 Criteris de Correcció**

| Secció | Punts | Observacions |
|--------|-------|-------------|
| Entitat `Producte` | 10 | Ha de tindre `@Entity`, `@Table`, `@Id`, `@GeneratedValue`, `@Column`. |
| DTO `ProducteDTO` | 10 | Ha de contindre només els camps necessaris. |
| `ProducteRepository` | 5 | Ha d'estendre `JpaRepository`. |
| `ProducteService` | 15 | Ús correcte de `@Service`, `@Autowired`, ús de DTOs. |
| `ProducteController` | 15 | Ús correcte de `@RestController`, `@RequestMapping`, mètodes REST. |
| **Total** | **50** | |


---

**Temps recomanat per secció:**
1️⃣ **Definir Entitat JPA i DTO** → 15-20 min  
2️⃣ **Escriure Repositori i Servei** → 20 min  
3️⃣ **Implementar Controlador** → 20-25 min  


### **🔚 Notes finals**
- Pots afegir comentaris explicatius per millorar la claredat del teu codi.
- No cal escriure implementacions detallades, però les estructures han de ser completes i correctes.
- Els errors menors de sintaxi no penalitzen si la lògica està ben desenvolupada.

✍ **Sort i bona feina!**


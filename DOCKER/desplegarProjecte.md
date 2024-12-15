



### **Guia per desplegar el projecte "Generador de Pàgines Web Estàtiques" amb Docker**

Aquesta guia se centra exclusivament en com desplegar el projecte utilitzant **Docker**. Suposa que ja tens un projecte funcional desenvolupat amb **IntelliJ IDEA**, **Maven**, i que utilitza llibreries com **Thymeleaf**, **Jackson**, i altres.

---

### **1. Preparació del projecte**

#### **1.1. Verifica els requisits**
Abans de continuar, assegura't de tenir els següents components:
- **Un JAR executiu** del projecte generat amb Maven.
- Un fitxer `Dockerfile` preparat per contenidoritzar l'aplicació.
- Docker instal·lat i funcionant al teu sistema.

#### **1.2. Genera el JAR del projecte**
1. Obre una terminal al directori del projecte.
2. Executa la següent comanda per empaquetar l'aplicació:
   ```bash
   mvn clean package
   ```
3. Verifica que el fitxer JAR es genera a la carpeta `target` (per exemple, `GeneradorPaginesWeb-1.0-SNAPSHOT.jar`).

---

### **2. Crea el fitxer `Dockerfile`**

El fitxer `Dockerfile` defineix com es construirà el contenidor per executar l'aplicació.

#### **2.1. Exemple de `Dockerfile`**
```dockerfile
# Utilitza una imatge base amb Java 17
FROM openjdk:17-jdk-slim

# Defineix el directori de treball dins del contenidor
WORKDIR /app

# Copia el fitxer JAR generat al contenidor
COPY target/GeneradorPaginesWeb-1.0-SNAPSHOT.jar app.jar

# Exposa un port si l'aplicació ho requereix
EXPOSE 8080

# Defineix la comanda d'entrada per executar l'aplicació
ENTRYPOINT ["java", "-jar", "app.jar"]
```

---

### **3. Construcció de la imatge Docker**

#### **3.1. Genera la imatge Docker**
1. Navega al directori on es troba el teu `Dockerfile`.
2. Executa la següent comanda per crear la imatge Docker:
   ```bash
   docker build -t generador-pagines-web .
   ```
3. Verifica que la imatge es crea correctament:
   ```bash
   docker images
   ```
   La sortida hauria de mostrar una imatge anomenada `generador-pagines-web`.

---

### **4. Executa l'aplicació amb Docker**

#### **4.1. Executa el contenidor**
1. Llança un contenidor a partir de la imatge generada:
   ```bash
   docker run -d --name generador-web -v $(pwd)/output:/app/output generador-pagines-web
   ```
   - **`-d`**: Executa el contenidor en segon pla.
   - **`--name generador-web`**: Dona un nom al contenidor.
   - **`-v $(pwd)/output:/app/output`**: Mapeja el directori `output` del host amb el directori `output` del contenidor.

2. Verifica que el contenidor s'està executant:
   ```bash
   docker ps
   ```

#### **4.2. Inspecciona els resultats**
1. Navega al directori `output` al teu sistema host per veure els fitxers generats pel projecte.
2. Si l'aplicació exposa un port (com ara `8080`), pots accedir-hi mitjançant el navegador:
   ```bash
   http://localhost:8080
   ```

---

### **5. Depuració i gestió del contenidor**

#### **5.1. Accedeix al contenidor**
Si necessites accedir al contenidor per inspeccionar o depurar, utilitza:
```bash
docker exec -it generador-web /bin/bash
```

#### **5.2. Atura i elimina el contenidor**
1. Per aturar el contenidor:
   ```bash
   docker stop generador-web
   ```
2. Per eliminar el contenidor:
   ```bash
   docker rm generador-web
   ```

---

### **6. Desplegament avançat amb Docker Compose**

Si el projecte requereix múltiples serveis (per exemple, un servidor web i un contenidor per al projecte), pots utilitzar **Docker Compose**.

#### **6.1. Exemple de `docker-compose.yml`**
```yaml
version: "3.8"
services:
  generador-web:
    image: generador-pagines-web
    build:
      context: .
    volumes:
      - ./output:/app/output
    ports:
      - "8080:8080"
```

#### **6.2. Executa Docker Compose**
1. Llança els serveis definits al fitxer `docker-compose.yml`:
   ```bash
   docker-compose up -d
   ```
2. Atura els serveis:
   ```bash
   docker-compose down
   ```

---

### **7. Conclusions**

Amb aquesta configuració, pots contenidoritzar i desplegar el projecte de forma senzilla utilitzant Docker. Aquesta guia t'ha mostrat com construir una imatge, executar un contenidor i utilitzar Docker Compose per gestionar serveis.





En el context del teu projecte, **desplegar** fa referència a posar en funcionament l'aplicació en un entorn de producció o desenvolupament perquè altres usuaris o sistemes puguen accedir-hi i utilitzar-la.

En aquest cas, desplegar implica:

1. **Generar les pàgines web estàtiques a partir de les dades JSON i les plantilles Thymeleaf**.
   - L'aplicació Java llegeix les entrades (JSON i fitxers de configuració), processa les dades amb les plantilles i genera els fitxers de sortida (`index.html`, pàgines individuals, RSS, etc.).
   
2. **Proporcionar accés a les pàgines generades**:
   - Les pàgines estàtiques generades han d'estar accessibles des d'un servidor web o des d'un navegador local. Això pot implicar:
     - Servir-les a través d’un servidor web (com **NGINX**, **Apache** o altres).
     - Obrir-les localment al navegador si només necessites visualitzar-les a nivell personal.

3. **Optimitzar el procés d'execució**:
   - Si cal, automatitzar l’execució del projecte mitjançant scripts, contenidors Docker, o altres eines.

4. **Preparar l'aplicació per a l’ús o distribució**:
   - Això inclou empaquetar l'aplicació perquè puga ser fàcilment executada en altres entorns o sistemes. Per exemple:
     - Crear un `.jar` executable per executar directament l’aplicació Java.
     - Crear un contenidor Docker perquè l'aplicació funcione en qualsevol sistema amb Docker instal·lat.

---

### **Què vol dir desplegar en el teu projecte concret?**
Amb el teu enunciat, desplegar significa:

1. **Executar el projecte Java**:
   - Processa el JSON i genera els fitxers HTML, RSS i altres sortides al sistema de fitxers.

2. **Visualitzar les pàgines generades**:
   - Assegurar-te que els fitxers `index.html` i altres pàgines són accessibles.
   - Això pot fer-se:
     - Obrint els fitxers al navegador (ús local).
     - Configurant un servidor web com **NGINX** o **Apache** per servir-los.

3. **Automatitzar l’execució del projecte**:
   - Utilitzar Docker per encapsular tot el projecte i facilitar la seva execució en qualsevol màquina sense preocupar-se de configuracions locals.

---

### **Exemple: Desplegar amb Docker**
1. **Preparar l’entorn de desenvolupament i producció**:
   - Escriu un `Dockerfile` per empaquetar l’aplicació.
   - Inclou les dependències (Java, Maven, Thymeleaf, etc.).
   - Copia el codi font i configura la comanda d’execució.

2. **Executar l’aplicació**:
   - Utilitza `docker run` per iniciar l’aplicació i generar els fitxers HTML i RSS.

3. **Servir les pàgines generades**:
   - Amb Docker, pots utilitzar un contenidor addicional (per exemple, amb **NGINX**) per servir les pàgines estàtiques:
     ```bash
     docker run --name web-server -v $(pwd)/output:/usr/share/nginx/html:ro -d -p 8080:80 nginx
     ```
   - Ara podries accedir a les pàgines a `http://localhost:8080`.

4. **Provar i verificar**:
   - Assegura’t que l’aplicació funciona segons el disseny i genera les pàgines correctament.

---

### **Resum**
Desplegar en aquest context implica:
- **Executar l'aplicació** per generar el contingut.
- **Proporcionar accés a les pàgines generades**, ja siga a nivell local o mitjançant un servidor web.
- **Facilitar la distribució** i reutilització del projecte mitjançant eines com Docker o empaquetats `.jar`.

Aquest procés assegura que l'aplicació pot ser utilitzada en diversos entorns i sistemes amb mínims ajustaments.



Per **executar el programa desplegat** en el teu projecte, pots seguir aquests passos, depenent de com hagis empaquetat i desplegat el projecte:

---

### **1. Si has empaquetat el projecte com un `.jar` executable**
El `.jar` contindrà tot el necessari per executar l’aplicació (el codi, les dependències, etc.). Pots executar-lo així:

#### **Pasos per executar el `.jar`:**
1. **Genera el `.jar` amb Maven:**
   - Executa aquesta comanda des del directori del projecte:
     ```bash
     mvn clean package
     ```
   - Això generarà un fitxer `.jar` a la carpeta `target`, com per exemple: `GeneradorWeb-1.0.jar`.

2. **Executa el `.jar`:**
   - Executa el següent des de la terminal:
     ```bash
     java -jar target/GeneradorWeb-1.0.jar
     ```
   - El programa llegirà els fitxers d’entrada (JSON i plantilles Thymeleaf) i generarà les pàgines web i altres sortides especificades.

3. **Accedeix a les pàgines generades:**
   - Les pàgines HTML estaran en la carpeta de sortida especificada al projecte. Pots obrir el fitxer `index.html` al navegador.

---

### **2. Si has desplegat l’aplicació amb Docker**
Amb Docker, l'aplicació i el seu entorn estan encapsulats en un contenidor.

#### **Pasos per executar l'aplicació amb Docker:**
1. **Construeix la imatge Docker:**
   - Escriu un `Dockerfile` per empaquetar l’aplicació:
     ```dockerfile
     FROM maven:3.8.5-openjdk-17 AS build
     WORKDIR /app
     COPY . .
     RUN mvn clean package

     FROM openjdk:17-jdk-slim
     WORKDIR /app
     COPY --from=build /app/target/GeneradorWeb-1.0.jar app.jar
     ENTRYPOINT ["java", "-jar", "app.jar"]
     ```
   - Construeix la imatge:
     ```bash
     docker build -t generador-web .
     ```

2. **Executa el contenidor:**
   - Executa el següent:
     ```bash
     docker run --rm -v $(pwd)/output:/app/output generador-web
     ```
   - El directori de sortida tindrà els fitxers generats.

3. **Accedeix a les pàgines web:**
   - Servir les pàgines generades amb un servidor web. Per exemple, amb NGINX:
     ```bash
     docker run --name web-server -v $(pwd)/output:/usr/share/nginx/html:ro -d -p 8080:80 nginx
     ```
   - Ara pots obrir `http://localhost:8080` per veure el contingut.

---

### **3. Si només vols executar el programa localment (sense Docker)**
#### **Pasos:**
1. **Executa el programa des d’IntelliJ IDEA:**
   - Obre el projecte a IntelliJ.
   - Fes clic dret al fitxer que conté el `main` i selecciona `Run`.
   - Això executarà el programa en mode local i generarà les sortides.

2. **Accedeix a les pàgines generades:**
   - Obre el fitxer `index.html` generat en la carpeta de sortida.

---

### **Exemples pràctics segons el mètode utilitzat**
#### **A través del `.jar`**
```bash
java -jar target/GeneradorWeb-1.0.jar
```

#### **A través de Docker**
```bash
docker run --rm -v $(pwd)/output:/app/output generador-web
docker run --name web-server -v $(pwd)/output:/usr/share/nginx/html:ro -d -p 8080:80 nginx
```

#### **Des d'IntelliJ**
1. Clica dret al `main`.
2. Selecciona `Run`.
3. Accedeix als fitxers generats.

---

### **Resum**
- **Localment:** Executa el `.jar` o des d’IntelliJ.
- **Amb Docker:** Empaqueta amb Docker, executa el contenidor i, si cal, serveix les pàgines generades amb un altre contenidor (NGINX).
- **Resultat:** Sempre obtindràs pàgines generades (`index.html`, altres HTMLs, i RSS) que pots obrir al navegador o compartir.




Desplegar aquest projecte amb **Docker** pot tenir sentit depenent del **context d’ús** i els **requisits** del projecte. Analitzem si és adequat:

---

### **Situacions en què té sentit utilitzar Docker per al teu projecte**
1. **Uniformitat de l'entorn:**
   - Si el projecte s’ha d’executar en diversos ordinadors amb entorns diferents (Windows, macOS, Linux), Docker garanteix que funcionarà de la mateixa manera.
   - T’assegures que totes les dependències (Java, Maven, etc.) i configuracions estan incloses en el contenidor.

2. **Facilitat per compartir i desplegar:**
   - Si altres desenvolupadors o usuaris necessiten executar el projecte, només necessiten Docker instal·lat. No cal preocupar-se per configurar Maven, Java, etc.
   - Pots empaquetar el projecte en una imatge Docker i distribuir-la.

3. **Automatització de generació i execució:**
   - Pots automatitzar tot el procés de generació (build), execució i generació de sortides dins d’un contenidor.
   - Això és útil en entorns de desenvolupament continu (CI/CD).

4. **Integració amb altres serveis:**
   - Si vols servir les pàgines generades directament des d’un contenidor (per exemple, amb **NGINX** o un altre servidor web).
   - Si necessites executar serveis relacionats (com bases de dades o altres microserveis) en contenidors a més del teu projecte.

5. **Entorn de demostració o producció:**
   - Si has de presentar el projecte a un client o professor, pots executar-lo directament des de Docker sense necessitat d'instal·lació.
   - Pots utilitzar-lo per generar les pàgines i alhora servir-les immediatament en un servidor.

---

### **Situacions en què no té sentit utilitzar Docker**
1. **Projecte senzill i d’ús local:**
   - Si el projecte només s'executa en el teu ordinador per crear pàgines HTML locals, Docker pot ser innecessari i afegir complexitat.

2. **No cal un entorn estandarditzat:**
   - Si totes les persones que treballen en el projecte tenen el mateix entorn configurat (Java, Maven, etc.) i no hi ha problemes d’instal·lació.

3. **Sobrecàrrega inicial:**
   - Per a projectes d'aprenentatge senzills, configurar Docker pot ser massa complex si no aporta beneficis reals.

---

### **Conclusió**
- **Si tens pensat compartir, distribuir o integrar el projecte amb altres serveis**, té molt de sentit utilitzar Docker per:
  1. Crear un contenidor que generi les pàgines.
  2. Utilitzar un altre contenidor per servir les pàgines generades.
- **Si només vols executar-lo localment per generar pàgines HTML senzilles**, Docker no és estrictament necessari i pots executar-lo des d’IntelliJ o com un `.jar`.

---

### **Quan recomanar Docker per aquest projecte?**
Recomanaria Docker si:
1. **Vols servir les pàgines generades automàticament** amb un servidor web.
2. **Vols distribuir el projecte a altres persones** perquè puguin executar-lo fàcilment.
3. **Tens plans d'integrar-lo amb més serveis** en el futur (bases de dades, microserveis, etc.).

Si només vols practicar Docker o aprendre a utilitzar-lo, desplegar aquest projecte és una bona oportunitat d’experimentació!
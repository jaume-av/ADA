**Guia Completa sobre Docker**

---

# **Docker: Introducció i Guia Completa**

Docker és una eina dissenyada per crear, desplegar i executar aplicacions en contenidors. Aquesta guia et proporcionarà una base sòlida per entendre i dominar Docker, des de la instal·lació fins a la gestió avançada de contenidors, imatges i aplicacions multi-container.

---

## **Índex**
1. Introducció
2. Instal·lació i configuració
3. Conceptes bàsics
   - Contenidors
   - Imatges
   - Dockerfile
   - docker-compose
4. Comandes bàsiques
5. Creació i gestió d'imatges personalitzades
6. Desplegament d'aplicacions
7. Arquitectures multi-container amb Docker Compose
8. Volums i persistència de dades
9. Casos pràctics i exemples complets

---

## **1. Introducció**

Docker facilita el desenvolupament, desplegament i execució d'aplicacions utilitzant contenidors. A diferència de les màquines virtuals, que repliquen un sistema operatiu complet, els contenidors comparteixen el mateix nucli del sistema operatiu però aïllen els processos per garantir portabilitat i eficiència.

### **Per què Docker?**
- **Portabilitat**: Els contenidors són independents del sistema host.
- **Eficiència**: Ocupen menys recursos que una màquina virtual.
- **Flexibilitat**: Faciliten la configuració i desplegament d'aplicacions.
- **Velocitat**: Els contenidors s'inicien i aturen més ràpidament que una màquina virtual.

---

## **2. Instal·lació i configuració**

### **Instal·lar Docker**
Per instal·lar Docker a Linux, utilitza la següent comanda:
```bash
curl -fsSL https://get.docker.com -o get-docker.sh && sh get-docker.sh
```

### **Permetre executar Docker com a usuari no-root**
1. Afegir l'usuari al grup `docker`:
   ```bash
   sudo usermod -aG docker $USER
   ```
2. Actualitzar el grup:
   ```bash
   newgrp docker
   ```

### **Verificar la instal·lació**
Executa la següent comanda per assegurar-te que Docker està correctament instal·lat:
```bash
docker --version
```

---

## **3. Conceptes bàsics**

### **Contenidors**
Un contenidor és un entorn aïllat que conté tot el necessari per executar una aplicació: codi, dependències, configuracions, etc.

#### **Característiques dels contenidors**
- Portables: Pots executar-los en qualsevol sistema amb Docker instal·lat.
- Lleugers: Comparats amb les màquines virtuals, consumeixen menys recursos.
- Efímers: Són fàcils de crear, destruir i reemplaçar.

#### **Exemple: Crear un contenidor**
```bash
docker run hello-world
```
Aquesta comanda executa un contenidor amb la imatge `hello-world`.

### **Imatges**
Una imatge és una plantilla immutable que es pot utilitzar per crear contenidors. Conté el sistema de fitxers, biblioteques i dependències necessàries.

#### **Exemple: Descarregar i executar una imatge**
```bash
docker pull nginx
docker run -d -p 8080:80 nginx
```
Aquestes comandes descarreguen la imatge de Nginx i executen un contenidor que mapeja el port 8080 del host al port 80 del contenidor.

### **Dockerfile**
Un Dockerfile és un arxiu que conté instruccions per crear una imatge Docker personalitzada.

#### **Exemple bàsic de Dockerfile**
```dockerfile
# Utilitza una imatge base
FROM node:14

# Estableix el directori de treball
WORKDIR /app

# Copia els fitxers al contenidor
COPY . .

# Instal·la les dependències
RUN npm install

# Comanda per iniciar l'aplicació
CMD ["npm", "start"]
```

### **docker-compose**
Eina per definir i gestionar aplicacions multi-container. Utilitza un arxiu YAML per descriure els serveis, xarxes i volums necessaris.

#### **Exemple de docker-compose.yml**
```yaml
version: "3.8"
services:
  web:
    image: nginx
    ports:
      - "8080:80"
  app:
    build: .
    ports:
      - "3000:3000"
```

---

## **4. Comandes bàsiques**

### **Gestió d'imatges**
- Llistar imatges:
  ```bash
  docker images
  ```
- Eliminar una imatge:
  ```bash
  docker rmi <image_id>
  ```

### **Gestió de contenidors**
- Llistar contenidors actius:
  ```bash
  docker ps
  ```
- Llistar tots els contenidors:
  ```bash
  docker ps -a
  ```
- Iniciar un contenidor:
  ```bash
  docker start <container_id>
  ```
- Aturar un contenidor:
  ```bash
  docker stop <container_id>
  ```
- Eliminar un contenidor:
  ```bash
  docker rm <container_id>
  ```

---

## **5. Creació i gestió d'imatges personalitzades**

### **Construir una imatge personalitzada**
Crea un Dockerfile al directori del teu projecte:
```dockerfile
FROM python:3.9
WORKDIR /app
COPY . .
RUN pip install -r requirements.txt
CMD ["python", "app.py"]
```

Construeix la imatge amb:
```bash
docker build -t my-python-app .
```

### **Executar la imatge**
```bash
docker run -d -p 5000:5000 my-python-app
```

---

## **6. Desplegament d'aplicacions**

### **Executar una aplicació multi-container**
Defineix els serveis al fitxer `docker-compose.yml`:
```yaml
version: "3.8"
services:
  db:
    image: postgres
    environment:
      POSTGRES_USER: user
      POSTGRES_PASSWORD: password
  app:
    build: .
    ports:
      - "8000:8000"
    depends_on:
      - db
```

Inicia els serveis amb:
```bash
docker-compose up -d
```

---

## **7. Volums i persistència de dades**

### **Què són els volums?**
Els volums permeten guardar dades fora dels contenidors per garantir que aquestes no es perdin quan es destrueix un contenidor.

### **Crear un volum**
```bash
docker volume create my-data
```

### **Associar un volum a un contenidor**
```bash
docker run -d -v my-data:/data my-container
```

---

## **8. Casos pràctics i exemples**

### **Aplicació web amb base de dades**
1. Crea un fitxer `docker-compose.yml`:
   ```yaml
   version: "3.8"
   services:
     web:
       image: nginx
       ports:
         - "8080:80"
     db:
       image: mysql
       environment:
         MYSQL_ROOT_PASSWORD: root
   ```
2. Inicia l'aplicació:
   ```bash
   docker-compose up -d
   ```

### **Convertir un Dockerfile en una imatge**
1. Escriu el Dockerfile:
   ```dockerfile
   FROM node:14
   WORKDIR /app
   COPY . .
   RUN npm install
   CMD ["npm", "start"]
   ```
2. Construeix la imatge:
   ```bash
   docker build -t my-app .
   ```
3. Executa el contenidor:
   ```bash
   docker run -p 3000:3000 my-app
   ```

---


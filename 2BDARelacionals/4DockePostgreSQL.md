
---
title: 4. Docker - Postgress. 
parent: Persistència en Base de Dades
has_children: true
layout: default
nav_order: 35
---




# Connexió Base de Dades amb Docker

Instal·lació de **PostgreSQL** i **pgAdmin** utilitzant **Docker** i **Docker Compose**.

---

### **Instal·lació**

Abans de començar, assegura't de tindre instal·lat:

```bash
docker --version
docker compose version
# Si usem una versió antiga de Docker Compose:
 docker-compose --version
```

en cas contrari:

```bash
sudo apt update
sudo apt install docker.io docker-compose
```

o manualment:

1. **Docker:** Pots descarregar-lo des de [Docker](https://www.docker.com/).
2. **Docker Compose:** Generalment, ve integrat amb Docker. Si no, pots seguir les instruccions d'instal·lació [ací](https://docs.docker.com/compose/install/).

---

Una vegada instal·lat Docker i Docker Compose, seguirem ls següents passos per a configurar **PostgreSQL** i **pgAdmin**:


## **1: Creació del fitxer `docker-compose.yml`**

- En este fitxer definirem els serveis necessaris per a **PostgreSQL** i **pgAdmin**. 
- Al nostre directori del projecte crearm un fitxer anomenat `docker-compose.yml` i posarem el següent contingut:

```yaml

version: '3.8'

services:
  postgres:
    container_name: postgres_db
    image: postgres:15
    restart: always
    ports:
      - "5432:5432"
    environment:
      POSTGRES_USER: el_meu_usuari
      POSTGRES_PASSWORD: la_meua_contrasenya
      POSTGRES_DB: la_meua_base_de_dades
    volumes:
      - postgres_data:/var/lib/postgresql/data

  pgadmin:
    container_name: pgadmin_container
    image: dpage/pgadmin4
    restart: always
    ports:
      - "8080:80"
    environment:
      PGADMIN_DEFAULT_EMAIL: admin@example.com
      PGADMIN_DEFAULT_PASSWORD: admin
    depends_on:
      - postgres

volumes:
  postgres_data:
```

### **Es te fitxer configura `docker-compose.yml`:**

1. **Servei `postgres`:**
   - Utilitza la imatge oficial `postgres:15`.
   - Exposa el port `5432` del contenidor **Docker** al sistema host, essent així accessible des de l'exterior.
   - Configura un **volum Docker** (`postgres_data`) per a la persistència de dades, evitant problemes de permisos i assegurant que les dades no es perden en reinicis.
   - Inclou variables d'entorn per a definir usuari, contrasenya i base de dades inicials.

**Nota**: Un volum Docker és un sistema de fitxers independent del contenidor, que es manté encara que el contenidor es detinga o s'elimine.

2. **Servei `pgadmin`:**
   - Utilitza la imatge oficial `dpage/pgadmin4`.
   - Exposa el port `8080` perquè pugues accedir a l'interfície web de pgAdmin.
   - Inclou credencials predeterminades per a accedir (email i contrasenya).
   - Utilitza `depends_on` per a garantir que PostgreSQL s'inicie abans que pgAdmin.

3. **Volum `postgres_data`:**
   - Este volum emmagatzema les dades de PostgreSQL, mantenint-les persistents fins i tot si el contenidor es reinicia o s'elimina.
   - Si vullguerem posar més bases de dades en altres ubicacions dins del contenidor, podríem crear volums addicionals i mapejar-los a altres rutes, assegurant que cada base de dades tinga el seu propi volum per a una millor organització i gestió.
   - Per exemple, si volem posar una base de dades en `/var/lib/postgresql/data2`, afegiríem un volum addicional:
   - 
     ```yaml
     volumes:
       - postgres_data:/var/lib/postgresql/data
       - postgres_data2:/var/lib/postgresql/data2

     volumes:
       postgres_data:
       postgres_data2:
     ```

---

## **2: Executar Docker Compose**

1. Accedim des d'un terminal al directori on es troba el fitxer `docker-compose.yml` 
2. Iniciem els serveis:

   ```bash
   docker-compose up -d
   ```
   - L'opció `-d` inicia els contenidors en segon pla.

3. Verifiquem que els serveis estan en execució:
   
   ```bash
   docker ps
   ```

Hauriem de veure dos contenidors en execució:
- Un amb el nom `postgres_db` (PostgreSQL).
- Un amb el nom `pgadmin_container` (pgAdmin).

---

## **3: Accedim a pgAdmin**

1. Al nostre navegador posem l'adreça `http://localhost:8080`.
   
2. Iniciem sessió amb les credencials indicades a l'arxiu `docker-compose.yml`:
3. 
   - **Email:** `admin@example.com`
   - **Contrasenya:** `admin`

4. En pgAdmin registrem el nostre servidor PostgreSQL:
   - Accedim a  **Add New Server**.
   - En la pestanya **General**, posem nom al servidor, per exemple: `Servidor Local`.
   - En la pestanya **Connection**, introuim les dades de connexió especificades en el fitxer `docker-compose.yml`:
- 
     - **Host:** `postgres_db`
     - **Port:** `5432`
     - **Usuari:** `el_meu_usuari`
     - **Contrasenya:** `la_meua_contrasenya`

---


# Gestió dels serveis i les dades amb Docker

## **1: Gestionar els serveis**

- **Detindre els serveis:**
  ```bash
  docker-compose down
  ```
  - Deté i elimina els contenidors, però els volums persistents (dades) es conserven.

- **Reiniciar els serveis:**
  ```bash
  docker-compose up -d
  ```

  - Inicia els contenidors en segon pla, sense perdre les dades.

- **Verificar l'estat dels contenidors:**
  ```bash
  docker ps
  ```
  - Mostra els contenidors en execució. Si no apareixen, s'han detingut.
  

> **Nota:** Evita utilitzar `docker-compose down --volumes` tret que vulgues eliminar permanentment les dades.

---

### **2: Gestió de les dades**

1. **Respaldar les dades amb `pg_dump`:**
   Si volem exportar una còpia de la base de dades executarem el següent comandament:
   ```bash
   docker exec -it postgres_db pg_dump -U el_meu_usuari -d la_meua_base_de_dades > backup.sql
   ```
   - Amb este comandament, es crea un fitxer SQL anomenat `backup.sql` amb les dades de la base de dades. El contingut del fitxer es pot restaurar en un altre sistema.

2. **Restaurar dades amb `psql`:**
   Per a restaurar un fitxer SQL:
   ```bash
   docker exec -i postgres_db psql -U el_meu_usuari -d la_meua_base_de_dades < backup.sql
   ```
   - Este comandament importa les dades del fitxer `backup.sql` a la base de dades.
   - **Nota:** Aquesta acció sobreescriu les dades existents, si el fitxer és molt gran, millor fer-ho amb `pg_restore`.

   Per exemple: 
   ```bash
    docker exec -i postgres_db pg_restore -U el_meu_usuari -d la_meua_base_de_dades < backup.sql
    ```

    - 
3. **Accedir al volum Docker per a inspeccionar dades:**
   ```bash
   docker volume inspect postgres_data
   ```

   El camp **`Mountpoint`** indica la ubicació on Docker emmagatzema les dades. En Ubuntu, sol ser:
   ```
   /var/lib/docker/volumes/postgres_data/_data
   ```

---

### **Com es conserven les dades?**
Les dades es guarden en el volum Docker `postgres_data`, que és independent dels contenidors. Açò significa que:
- Pots detindre els serveis amb `docker-compose down` i les dades es mantindran.
- Només es perden si elimines el volum manualment amb `docker volume rm` o utilitzes `docker-compose down --volumes`.

**Per a transferir o reutilitzar dades en un altre ordinador:**
- **Exporta les dades amb `pg_dump`** i restaura-les en el nou sistema.
- O copia el volum Docker complet utilitzant els comandaments explicats prèviament.

---


# **Resum  Docker**

#### **Gestió de Contenidors**
- **Llistar els contenidors en execució:** `docker ps`  
- **Llistar tots els contenidors (incloent els detinguts):** `docker ps -a`  
- **Crear i iniciar un contenidor:** `docker run -d --name nom_contenidor imatge`  
- **Accedir a un contenidor en execució:** `docker exec -it nom_contenidor bash`  
- **Detindre un contenidor:** `docker stop nom_contenidor`  
- **Reiniciar un contenidor:** `docker restart nom_contenidor`  
- **Eliminar un contenidor detingut:** `docker rm nom_contenidor`  
- **Eliminar tots els contenidors detinguts:** `docker container prune`  

---

#### **Gestió d’Imatges**
- **Llistar totes les imatges locals:** `docker images`  
- **Descarregar una imatge des de Docker Hub:** `docker pull nom_imatge`  
- **Eliminar una imatge:** `docker rmi nom_imatge`  
- **Eliminar totes les imatges no utilitzades:** `docker image prune -a`  

---

#### **Gestió de Volums**
- **Llistar tots els volums:** `docker volume ls`  
- **Crear un volum nou:** `docker volume create nom_volum`  
- **Inspeccionar un volum (veure la seua ubicació):** `docker volume inspect nom_volum`  
- **Eliminar un volum:** `docker volume rm nom_volum`  
- **Eliminar tots els volums no utilitzats:** `docker volume prune`  

---

#### **Docker Compose**
- **Iniciar serveis definits en `docker-compose.yml`:** `docker-compose up -d`  
- **Detindre i eliminar els serveis:** `docker-compose down`  
- **Detindre i eliminar serveis amb volums:** `docker-compose down --volumes`  
- **Verificar l'estat dels serveis:** `docker-compose ps`  

---

#### **Gestió General**
- **Comprovar la versió de Docker:** `docker --version`  
- **Comprovar la versió de Docker Compose:** `docker compose version`  
- **Eliminar tots els recursos no utilitzats (contenidors, volums, xarxes, imatges):** `docker system prune -a --volumes`  
- **Consultar informació general del sistema Docker:** `docker info`  

---

#### **Còpia de Fitxers**
- **Copiar fitxers des d'un contenidor al sistema host:** `docker cp nom_contenidor:/ruta/en/contenidor ./ruta/local`  
- **Copiar fitxers des del sistema host al contenidor:** `docker cp ./ruta/local nom_contenidor:/ruta/en/contenidor`  

---

#### **Altres**
- **Visualitzar els logs d'un contenidor:** `docker logs nom_contenidor`  
- **Matar (forçar la detenció) d'un contenidor:** `docker kill nom_contenidor`  

---


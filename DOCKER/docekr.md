---

title: Introducció a Docker i Docker Compose
parent: Desplegament

has_children: true
layout: default
nav_order: 10

---

**Enllaços Documentació Oficial:**

- [Docker Docs](https://docs.docker.com/)
- [Comandes Docker](https://docs.docker.com/reference/cli/docker/)
- [Dockerfile Reference](https://docs.docker.com/reference/dockerfile/)
- [Docker Compose](https://docs.docker.com/compose/)
- [Docker Compose CLI](https://docs.docker.com/compose/intro/compose-application-model/)


# Docker

**Docker** és una eina que ens ajuda a executar aplicacions dins de contenidors.  
Però, què és un **contenidor**? Hem de pensar en un contenidor com una "caixa virtual" que té tot el que una aplicació necessita per funcionar: 
- Codi.
- Dependències (com llibreries).
- Configuracions. 

Aquesta **"caixa"** pot funcionar a qualsevol ordinador que tinga **Docker** instal·lat, independentment de les diferències entre els sistemes operatius.

**Avantatges d'usar Docker**

- **Portabilitat**: Pots portar la teua aplicació a qualsevol màquina sense preocupar-te si té instal·lades les mateixes dependències.  
- **Eficiència**: Consumeix molts menys recursos que una màquina virtual.  
- **Velocitat**: Els contenidors s’inicien en segons.

**Per exemple:**

Imaginem que tenim una aplicació que necessita una base de dades específica i una versió concreta de Java per funcionar.  
En lloc d’instal·lar-ho tot al nostre ordinador, podem posar-ho tot en un contenidor i així aïllar-ho de la resta del sistema, assegurant-nos que funcionarà de la mateixa manera en qualsevol lloc.

També evitarem possibles conflictes amb altres aplicacions o versions de llibreries que ja tenim instal·lades.

---

**1. Instal·lació i configuració de Docker**

**Per a Linux**

Per a instal·lar Docker podem usar aquestes comandes des del terminal:

```bash
# 1. Baixa el script d’instal·lació oficial de Docker:
curl -fsSL https://get.docker.com -o get-docker.sh

# 2. Executa el script per instal·lar Docker:
sh get-docker.sh

# 3. Permet que Docker s'execute com un usuari no-root (opcional però recomanat):
sudo usermod -aG docker $USER

# 4. Actualitza el grup per aplicar els canvis:
newgrp docker

# 5. Comprova que Docker s’ha instal·lat correctament:
docker --version
```

**Per a Windows i macOS**

Per aquests sistemes, pots descarregar **Docker Desktop**, que inclou tot el necessari per començar. Els passos són:

1. Ves a la pàgina oficial de Docker: [https://www.docker.com](https://www.docker.com)  
2. Descarrega **Docker Desktop**.  
3. Segueix les instruccions de la instal·lació.  
4. Un cop instal·lat, comprova que Docker funciona obrint una terminal i escrivint:  

```bash
docker --version
```

---

**2. Conceptes bàsics de Docker**

**Contenidors**  
Un **contenidor** és una "instància en execució" d’una imatge. És com un programa que s'està executant a la teua màquina, però de manera aïllada de la resta del sistema. 

Un contenidor conté tot el necessari per fer funcionar una aplicació: codi, llibreries, configuracions, etc.

**Característiques dels contenidors:**

- **Lleugers**: Utilitzen els recursos mínims necessaris, a diferència de les màquines virtuals que simulen un sistema operatiu complet.  
- **Portables**: Pots moure un contenidor d'un ordinador a un altre i s'executarà de la mateixa manera, sempre que tingui Docker instal·lat.  
- **Efímers**: Es poden crear, destruir i recrear fàcilment.

**Exemple**:  
Executem el següent contenidor:

```bash
docker run hello-world
```

**Què fa?**

1. **docker run**: Indica a Docker que vols executar un contenidor.  
2. **hello-world**: És el nom d’una imatge oficial de Docker. Aquesta imatge conté un programa senzill que imprimeix un missatge de benvinguda a la terminal.

Quan executem aquesta ordre:
- Docker descarregarà la imatge **hello-world** (si no la tens al sistema).  
- Crearà un contenidor amb la imatge.  
- Executarà el codi dins del contenidor i mostrarà un missatge com el següent:

```
Hello from Docker!
This message shows that your installation appears to be working correctly.
```

---

**Imatges**  
Una **imatge** és com una "plantilla" que utilitzem per crear contenidors. Conté el sistema de fitxers, biblioteques, dependències i tot el que l'aplicació necessita.

**Què és una imatge?**

- És **immutable**: Una vegada creada, no es pot modificar directament.  
- Es pot pensar com un "moment congelat" del sistema que el contenidor utilitzarà.  

**Exemple: Imatge de Nginx**

**Nginx** és un servidor web. Es fa servir per gestionar peticions d'usuari (com carregar una pàgina web), servir fitxers estàtics (imatges, documents, etc.), i fins i tot com a proxy invers per altres aplicacions.

Per executar un servidor web Nginx amb Docker:

```bash
# 1. Descarreguem la imatge d’Nginx:
docker pull nginx

# Això descarregarà la imatge d’Nginx des de Docker Hub (una mena de repositori d'imatges).

# 2. Executem un contenidor amb aquesta imatge:
docker run -d -p 8080:80 nginx
```

**A la comanda anterior:**

- **-d**: Executa el contenidor en segon pla (mode "detached").  
- **-p 8080:80**: Mapeja el port 8080 del teu ordinador al port 80 del contenidor. Això vol dir que pots accedir al servidor Nginx escrivint http://localhost:8080 al teu navegador.  
- **nginx**: És la imatge que utilitza el contenidor.  

Quan ho fem, Nginx estarà servint una pàgina web predeterminada que pots veure al navegador.

**Per què utilitzar Nginx amb Docker?**

Si volem provar com funciona un servidor web per a la nostra aplicació. En lloc de configurar-lo manualment al nostre sistema, podem utilitzar un contenidor amb Nginx. Així evitem fer canvis al nostre ordinador i podem destruir el contenidor quan ja no el necessitem.

---

**Dockerfile**  
Un **Dockerfile** és un arxiu de text que conté un conjunt d'instruccions per crear una imatge Docker personalitzada. És com una "recepta" que defineix com serà la imatge.

**Exemple bàsic de Dockerfile:**  
Suposem que volem crear una imatge que continga:  
- **Node.js** per executar aplicacions.  
- Una aplicació pròpia dins del contenidor.  

El Dockerfile seria:

```dockerfile
# Utilitza una imatge base amb Node.js
FROM node:14

# Estableix el directori de treball dins del contenidor
WORKDIR /app

# Copia els fitxers de l’aplicació al contenidor
COPY . .

# Instal·la les dependències
RUN npm install

# Comanda que s'executarà quan el contenidor s'iniciï
CMD ["npm", "start"]
```

Per construir la imatge amb aquest arxiu Dockerfile:

```bash
# 1. Situa't al directori on tens el Dockerfile.
# 2. Executa:
docker build -t my-node-app .
```

Això crearà una imatge anomenada **my-node-app**.

---

**3. Docker Compose: Gestió d'Aplicacions Multi-Container**

**Què és Docker Compose?**  
- Docker Compose és una eina que ens permet definir i gestionar aplicacions amb múltiples contenidors.  
- En lloc d'executar diversos contenidors individualment amb `docker run`, utilitzem un únic fitxer anomenat **docker-compose.yml** per descriure tots els serveis, xarxes i volums que necessita l'aplicació.

**Avantatges de Docker Compose**  
- **Centralitza la configuració**: Tot es defineix en un **sol fitxer**.  
- **Facilita l'execució**: Pots iniciar o aturar tota l'aplicació amb una **sola comanda**.  
- És ideal per a entorns de desenvolupament i proves.  

---

**Exemple: Servidor web i base de dades**

Suposem que volem executar una aplicació que consta de:  
1. Un servidor web (per exemple, **Nginx**).  
2. Una base de dades (per exemple, **MySQL**).  

Amb Docker Compose, el fitxer de configuració seria així:

**Fitxer docker-compose.yml**

```yaml
version: "3.8" # Versió de Docker Compose

services:
  web:
    image: nginx # Utilitza la imatge oficial de Nginx
    ports:
      - "8080:80" # Mapeja el port 8080 del teu ordinador al port 80 del contenidor

  db:
    image: mysql # Utilitza la imatge oficial de MySQL
    environment:
      MYSQL_ROOT_PASSWORD: root # Defineix la contrasenya de root
      MYSQL_DATABASE: example_db # Crea una base de dades per defecte
```

---

**Executar l'aplicació amb Docker Compose**

1. Crea un fitxer **docker-compose.yml** amb el contingut anterior.  
2. A la terminal, situa't al directori on has creat el fitxer i executa:  

```bash
docker compose up
```

**Què fa aquesta comanda?**  
- Llegeix el fitxer **docker-compose.yml**.  
- Descarrega les imatges necessàries (Nginx i MySQL).  
- Crea i inicia els contenidors definits.  

3. Accedeix al teu navegador i escriu:  
```
http://localhost:8080
```
Aquí veuràs la pàgina predeterminada del servidor Nginx.

4. Per aturar els contenidors:  
```bash
docker compose down
```

---

**Exemple: Aplicació amb codi propi**

Suposem que tenim una aplicació **Node.js** que vol accedir a una base de dades **PostgreSQL**. Això és com ho configuraries.

**Fitxer docker-compose.yml**

```yaml
version: "3.8"

services:
  app:
    build: . # Indica que utilitzarà un Dockerfile al mateix directori
    ports:
      - "3000:3000" # Mapeja el port 3000 del teu ordinador al port 3000 del contenidor
    environment:
      DB_HOST: db
      DB_USER: user
      DB_PASSWORD: password
      DB_NAME: example_db

  db:
    image: postgres # Utilitza la imatge oficial de PostgreSQL
    environment:
      POSTGRES_USER: user
      POSTGRES_PASSWORD: password
      POSTGRES_DB: example_db
```

**Dockerfile per a l'aplicació (al mateix directori)**

```dockerfile
# Imatge base amb Node.js
FROM node:14

# Estableix el directori de treball
WORKDIR /app

# Copia els fitxers del projecte
COPY package*.json ./

# Instal·la les dependències
RUN npm install

# Copia el codi de l'aplicació
COPY . .

# Exposa el port 3000
EXPOSE 3000

# Comanda d'inici
CMD ["npm", "start"]
```

**Exemple de codi Node.js (fitxer app.js)**

```javascript
const express = require("express");
const app = express();

app.get("/", (req, res) => {
  res.send("Hola des de Docker amb Node.js i PostgreSQL!");
});

app.listen(3000, () => {
  console.log("Aplicació en marxa al port 3000");
});
```

---

**Resum de comandes útils de Docker Compose**

- **Iniciar serveis**:
```bash
docker compose up
```

- **Aturar serveis**:
```bash
docker compose down
```

- **Veure els logs dels contenidors**:
```bash
docker compose logs
```

- **Reiniciar només un servei concret**:
```bash
docker compose restart <nom_del_servei>
```

---

**4. Volums i Persistència de Dades**

**Què són els volums?**  
Un volum és una manera de guardar dades fora del sistema de fitxers intern d’un contenidor. Això significa que, fins i tot si el contenidor s'elimina o reinicia, les dades es mantenen.

**Els volums s'utilitzen per:**  
- **Persistència**: Les dades no es perden quan s’elimina un contenidor.  
- **Compartició**: Pots compartir dades entre múltiples contenidors.  
- **Separació de dades i aplicació**: Mantens el codi i les dades aïllats per facilitar la gestió.  

---

**Crear un volum manualment**  
Pots crear un volum de manera senzilla amb Docker.

1. Crea un volum anomenat **my-data**:
```bash
docker volume create my-data
```

2. Executa un contenidor i associa-li aquest volum:
```bash
docker run -d -v my-data:/data busybox
```

**Explicació de la comanda:**  
- **-v my-data:/data**: Mapeja el volum **my-data** al directori **/data** dins del contenidor.  
- **busybox**: És una imatge minimalista que pots utilitzar per provar coses senzilles.  

3. Comprova que el volum existeix:  
```bash
docker volume ls
```

---

**Volums amb Docker Compose**

Els volums es poden definir fàcilment en un fitxer **docker-compose.yml**.

**Exemple: Base de dades MySQL amb volum**

```yaml
version: "3.8"

services:
  db:
    image: mysql
    environment:
      MYSQL_ROOT_PASSWORD: root
      MYSQL_DATABASE: example_db
    volumes:
      - db-data:/var/lib/mysql # Associa un volum per guardar les dades

volumes:
  db-data: # Defineix el volum
```

---

**5. Xarxes en Docker**

En Docker, els contenidors utilitzen xarxes per comunicar-se entre ells o amb l'exterior. Docker proporciona diferents tipus de xarxes que pots utilitzar segons les necessitats de la teua aplicació.

**Tipus de xarxes en Docker:**

1. **bridge** (predeterminada): Permet que els contenidors del mateix host es comuniquin entre ells.  
2. **host**: Comparteix la mateixa xarxa que el sistema operatiu host (sense aïllament).  
3. **none**: El contenidor no té accés a cap xarxa.  
4. **custom**: Xarxes personalitzades que pots definir per controlar com es comuniquen els contenidors.  

**Xarxa predeterminada (bridge)**

Per defecte, quan crees contenidors sense especificar cap xarxa, Docker utilitza una xarxa de tipus **bridge**.

Exemple:  
1. Executa un contenidor:  
```bash
docker run --name my-container -d nginx
```

2. Comprova la xarxa:  
```bash
docker network inspect bridge
```

---

**Xarxes personalitzades**

Pots crear una xarxa personalitzada per connectar contenidors de manera controlada.

1. **Crea una xarxa anomenada my-network:**  
```bash
docker network create my-network
```

2. **Executa dos contenidors a la mateixa xarxa:**  
```bash
docker run --name container1 --network my-network -d nginx
docker run --name container2 --network my-network -d busybox sleep 3600
```

3. **Prova la comunicació entre contenidors:**  
Accedeix al contenidor **container2**:  
```bash
docker exec -it container2 sh
```

Prova si pots "veure" el container1:  
```bash
ping container1
```

---

**6. Optimització d’Imatges amb Dockerfile**

Els **Dockerfile** són essencials per crear imatges personalitzades, però també poden ser ineficients si no els estructurem correctament. Aquí tens algunes pràctiques recomanades per optimitzar-los:

**Reduir la mida de les imatges**

- **Utilitza imatges base lleugeres**: Per exemple, en lloc d'utilitzar **node:14**, utilitza **node:14-alpine**.  
```dockerfile
FROM node:14-alpine
```

- **Elimina arxius innecessaris**: Si instal·les paquets, elimina les memòries cau després de la instal·lació.  
```dockerfile
RUN apt-get update && apt-get install -y \
    build-essential \
    && rm -rf /var/lib/apt/lists/*
```

---

**Aprofitar la memòria cau de Docker**

Docker utilitza una memòria cau per evitar tornar a executar passos innecessaris. Per aprofitar-la:  
- Col·loca les instruccions que canvien poc (com **RUN apt-get install**) al principi.  
- Col·loca les instruccions que canvien sovint (com **COPY . .**) al final.  

**Exemple de Dockerfile optimitzat:**

```dockerfile
# Imatge base
FROM node:14

# Instal·la les dependències
WORKDIR /app
COPY package.json ./
RUN npm install

# Copia el codi de l'aplicació
COPY . .

# Inicia l'aplicació
CMD ["npm", "start"]
```

---

**Comprimir imatges**

Pots utilitzar eines com [Docker Slim](https://dockerslim.com/) per reduir encara més la mida de les imatges, eliminant fitxers no necessaris.

---

**7. Desplegament d’Aplicacions en Producció**

Quan estàs preparat per portar una aplicació a producció, cal considerar diversos aspectes:

**Imatges optimitzades per a producció**

- Utilitza versions mínimes i segures de les teues dependències.  
- Afegeix només les eines necessàries per executar l’aplicació (no incloguis eines de desenvolupament).  

---

**Gestió de secrets**

Mai incloguis secrets (com contrasenyes o claus d'API) directament en un **Dockerfile** o en un fitxer **docker-compose.yml**. Utilitza eines com **Docker Secrets** o variables d'entorn.

**Exemple senzill amb variables d'entorn:**

```yaml
services:
  app:
    image: my-app
    environment:
      - APP_SECRET=${APP_SECRET}
```

Defineix la variable al teu sistema abans d'executar **docker compose up**:  
```bash
export APP_SECRET=my-super-secret
docker compose up
```

---

**Servei de proxy invers**

Quan tens diverses aplicacions, pots utilitzar un servidor com **Nginx** per gestionar les peticions externes.

**Exemple: Proxy invers amb Nginx**

1. Defineix el servei nginx al **docker-compose.yml**:  
```yaml
services:
  nginx:
    image: nginx
    ports:
      - "80:80"
    volumes:
      - ./nginx.conf:/etc/nginx/nginx.conf
```

2. Crea el fitxer **nginx.conf**:  
```nginx
server {
    listen 80;

    location / {
        proxy_pass http://app:3000;
    }
}
```

3. Ara http://localhost redirigeix automàticament les peticions al servei **app**.

---

**8. Resum Docker i Docker Compose**

A continuació, es mostra una taula amb les ordres principals de **Docker** i **Docker Compose**, classificades per categoria i amb una breu descripció de la seva funcionalitat.

---

**Ordres Principals de Docker**

| **Categoria**        | **Comanda**             | **Descripció**                                              | **Exemple**                  |
|----------------------|------------------------|------------------------------------------------------------|-------------------------------|
| **Gestió d'Imatges**  | docker build           | Crea una imatge Docker a partir d'un Dockerfile.            | docker build -t app:latest .|
|                      | docker images          | Llista totes les imatges locals disponibles.                | docker images               |
|                      | docker rmi             | Elimina una imatge de Docker local.                         | docker rmi app:latest       |
|                      | docker pull            | Descarrega una imatge del registre de Docker Hub.           | docker pull ubuntu:latest   |
|                      | docker push            | Puja una imatge al registre (Docker Hub, etc.).             | docker push user/app:latest |
| **Contenidors**       | docker run             | Executa un contenidor a partir d'una imatge.                | docker run -d -p 80:80 nginx|
|                      | docker ps              | Llista els contenidors en execució.                         | docker ps                   |
|                      | docker ps -a           | Llista tots els contenidors (actius i aturats).              | docker ps -a                |
|                      | docker start           | Inicia un contenidor aturat.                                 | docker start container_id   |
|                      | docker stop            | Atura un contenidor en execució.                             | docker stop container_id    |
|                      | docker restart         | Reinicia un contenidor aturat.                               | docker restart container_id |
|                      | docker kill            | Finalitza de forma forçada un contenidor.                    | docker kill container_id    |
|                      | docker rm              | Elimina un contenidor.                                       | docker rm container_id      |
| **Gestió de Volums**  | docker volume create   | Crea un volum de Docker.                                     | docker volume create myvol  |
|                      | docker volume ls       | Llista els volums de Docker.                                 | docker volume ls            |
|                      | docker volume rm       | Elimina un volum de Docker.                                  | docker volume rm myvol      |
| **Gestió de Xarxes**  | docker network create  | Crea una xarxa personalitzada de Docker.                     | docker network create mynet |
|                      | docker network ls      | Llista les xarxes de Docker.                                 | docker network ls           |
|                      | docker network rm      | Elimina una xarxa de Docker.                                 | docker network rm mynet     |
| **Altres**            | docker exec            | Executa una comanda dins d'un contenidor actiu.              | docker exec -it container_id bash |
|                      | docker logs            | Mostra els registres d'un contenidor.                        | docker logs container_id    |
|                      | docker inspect         | Mostra informació detallada d'un contenidor o imatge.        | docker inspect container_id |
|                      | docker system prune    | Elimina imatges, contenidors i volums no utilitzats.         | docker system prune -f      |

---

**Ordres Principals de Docker Compose**

| **Categoria**        | **Comanda**              | **Descripció**                                                | **Exemple**                  |
|----------------------|-------------------------|--------------------------------------------------------------|-------------------------------|
| **Inici i Parada**    | docker compose up       | Crea i inicia els serveis definits a docker-compose.yml.       | docker compose up -d        |
|                      | docker compose down     | Atura i elimina els serveis definits a docker-compose.yml.     | docker compose down         |
|                      | docker compose start    | Inicia els serveis aturats.                                    | docker compose start        |
|                      | docker compose stop     | Atura els serveis en execució.                                 | docker compose stop         |
|                      | docker compose restart  | Reinicia els serveis en execució.                              | docker compose restart      |
| **Gestió de Serveis** | docker compose ps       | Llista l'estat dels serveis definits.                          | docker compose ps           |
|                      | docker compose build    | Crea les imatges definides a docker-compose.yml.               | docker compose build        |
|                      | docker compose pull     | Descarrega les imatges definides a docker-compose.yml.         | docker compose pull         |
|                      | docker compose push     | Puja les imatges al registre Docker.                           | docker compose push         |
| **Execució de Comandes**| docker compose exec   | Executa comandes dins d'un contenidor actiu.                   | docker compose exec web bash|

---

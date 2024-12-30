---

title: 4.1.- Cas Pràctic - Projecte Fitxers  
parent: 4.- NGINX  
has_children: true
layout: default  
nav_order: 45  

---

## Cas Pràctic - Projecte Fitxers (Generaciò de Web Estàtica)

Volem desplegar el nostre projecte de la 1a Avaluació (**Projecte Fitxers**) que genera fitxers HTML a partir d'un fitxer JSON i volem servir-los amb **NGINX**.

Per poder desplegar amb **NGINX** pagines web estàtiques, hem de mapejar les carpetes del nostre projecte amb les carpetes del contenidor NGINX.

Usarem la comnada `docker run` per a iniciar un contenidor NGINX i mapejar les carpetes del nostre projecte.

```bash
docker run --name some-nginx -v /some/content:/usr/share/nginx/html:ro -p 8080:80 -d nginx
```

---



### **Pas a pas...**


```bash
docker run
```
* **Crea i executa un contenidor Docker**.  
El contenidor es basa en una imatge Docker (en aquest cas, la imatge **nginx**).

Cada vegada que utilitzem `docker run`, Docker farà tres coses:

1. **Descarregar la imatge** de Docker Hub (si no la tens).  
2. **Crear un contenidor** a partir de la imatge.  
3. **Executar** el contenidor amb la configuració que li has passat.  

---


```bash
--name some-nginx
```
Esta opció assigna un **nom personalitzat** al contenidor.
- El contenidor es dirà en este cas `some-nginx`.
- Amb este nom, podràs utilitzar comandes de **Docker** per a interactuar amb el contenidor.

**Exemples de comandes per a usar el nom del contenidor**:

```bash
docker stop some-nginx    # Para el contenidor "some-nginx"
docker start some-nginx   # Torna a iniciar el contenidor "some-nginx"
docker logs some-nginx    # Mostra els registres (logs) del contenidor
docker rm some-nginx      # Elimina el contenidor
```

---


```bash
-v /some/content:/usr/share/nginx/html:ro
```
Aquesta opció crea un **volum de Docker**.
- **`/some/content`**: És la **carpeta al teu sistema host** (Linux, Windows o macOS).  
- **`/usr/share/nginx/html`**: És la **carpeta dins del contenidor NGINX**.  
- **`:ro`**: Indica que el volum és **només lectura** ("read-only").  

#### **Què està passant ací?**
1. Es diu a Docker que **mapege** (connecte) la carpeta `/some/content` del teu sistema host amb la carpeta `/usr/share/nginx/html` dins del contenidor.  
2. NGINX utilitza **/usr/share/nginx/html** com a la seua carpeta arrel, on es troben els arxius **HTML, CSS, JS** que NGINX servirà.  
3. El contenidor no podrà modificar els arxius de la carpeta `/some/content` (per això està en mode **només lectura** `:ro`).  

**Exemple**:  
Si al nostre sistema tenim la carpeta `/jaume/vaigaaprovar/` amb aquesta estructura:
```
/jaume/vaigaaprovar/
   ┣ 📄 index.html
   ┣ 📄 about.html
   ┗ 📁 css/
      ┗ 📄 styles.css
```
Quan accedim a **http://localhost/index.html**, veurem l'arxiu **index.html** de la carpeta `/jaume/vaigaaprovar/`.

---
```bash
-p 8080:80
```

Exposa el port 80 dins del contenidor **docker** al port de la nostra maquina o **host** 8080.

El contenidor NGINX s'executa i escolta el port 80 dins del contenidor, amb `-p` Docker realitza una assignació de ports entre el contenidor i la maquina host, el que ens permetrà accedir al servidor NGINX  des de `http://localhost:8080`

Es a dir, amb `-p 8080:80`, el port 80 del contenidor s'associa amb el port 8080 de la màquina host, permetent accedir a NGINX des de http://localhost:8080.

Si no es fa esta assinació no podrem accedir des del host.


```bash
-d
```
Aquesta opció **"desacobla"** (detach) el contenidor de la terminal.
- Sense esta opció, el contenidor s'executa i mostra els registres (logs) a la terminal.
- Amb esta opció, el contenidor s'executa en segon pla.

**Com pots veure els contenidors en execució?**  
Si executem esta comanda:
```bash
docker ps
```
Veuràs alguna cosa paregunda a:
```
CONTAINER ID   IMAGE    COMMAND                  PORTS       NAMES
123456abcdef   nginx    "/docker-entrypoint.…"  0.0.0.0:80->80/tcp   some-nginx
```

El contenidor `some-nginx` estarà funcionant en segon pla.

---

### **nginx**
```bash
nginx
```
Este és el **nom de la imatge Docker** que es farà servir.
- Si la imatge no està al teu ordinador, **Docker la descarregarà de Docker Hub**.
- La imatge de NGINX inclou un servidor web llest per a servir arxius HTML, CSS, JS.
- Aquesta imatge de **NGINX** serveix els arxius de la carpeta **/usr/share/nginx/html** (dins del contenidor).

---
Per tant, si volem servir els arxius HTML i CSS del nostre projecte, podem utilitzar la comanda:

```bash
docker run --name some-nginx -v /some/content:/usr/share/nginx/html:ro -p 8080:80 -d nginx
```

---

## **Resum**
1. **Creem i executem un contenidor** amb la imatge de NGINX.  
2. **Assignem el nom `some-nginx`** al contenidor.  
3. **Mapejem la carpeta `/some/content/`** (del nostre PC) a **`/usr/share/nginx/html/`** (al contenidor NGINX).  
4. NGINX **serveix els arxius estàtics** que estiguen a la carpeta **/some/content/**.  
5. **Exposa el port 80 del contenidor al port 8080 del host** perquè es puga accedir al servidor NGINX des del navegador amb la URL **http://localhost:8080**.
6. S'executa en **segon pla (-d)**, per la qual cosa no ocupa la terminal.  




## **Consideracions**

### **Què passa si no utilitze `-v /some/content:/usr/share/nginx/html`?**
NGINX utilitzarà la carpeta per defecte **/usr/share/nginx/html** que està dins de la imatge Docker.  
Esta carpeta només conté una pàgina HTML per defecte,  **Benvingut a NGINX**.

---

### **Per a què serveix `:ro` (mode només lectura)?**
Quan utilitzes **`:ro`**, li dius a Docker que NGINX **no pot modificar els arxius** a la carpeta `/some/content/`.  
Això evita que el contenidor sobreescriga els arxius al teu ordinador.  
Si no vols només lectura, pots ometre **`:ro`**, i la carpeta serà accessible amb permisos d'escriptura.

---

### **Com puc actualitzar els arxius HTML i CSS?**
Ja que estem mapejant la carpeta `/some/content/`, podem modificar els arxius al nostre sistema de fitxers local.  
Els canvis es reflectiran automàticament en **http://localhost/index.html**.

**Exemple**:
```bash
echo "<h1>Pàgina actualitzada!</h1>" > /some/content/index.html
```
Accedeix de nou a **http://localhost/index.html** i veuràs els canvis.

---

## Projecte Fitxers amb NGINX


Partim de la següent estructura de carpetes i fitxers:

```markdown
📦 **ADA-P1-CiutatsNginx**
├── 📁 .idea                 # Configuració de l'entorn IntelliJ IDEA
├── 📁 src
│   ├── 📁 main              # Conté el codi principal de l'aplicació
│   │   ├── 📁 java          # Fitxers amb codi font Java
│   │   └── 📁 resources     # Recursos estàtics i dinàmics
│   │       ├── 📁 css       # Arxius CSS per als estils
│   │       ├── 📁 fitxersWeb # HTML generat que es servirà per NGINX
│   │       ├── 📁 json      # Fitxers JSON amb les dades d'entrada
│   │       └── 📁 templates # Plantilles Thymeleaf per generar HTML
├── 📁 target                # Carpeta on Maven generarà els arxius compilats
└── 📄 .gitignore            # Arxius i carpetes ignorats pel sistema de control de versions
```

---

* **Exercici 1 : Servir amb NGINX tota la carpeta `resources` del projecte.**

* **Exercici 2 : Servir amb NGINX la carpeta `fitxersWeb` i `css` del projecte.**

>**També tindràs que mapejar la carpeta d'imatges si existeix.**  

Per a este exercici, usa l'expressió:

```bash
docker run --name nginx-static-server \
    -v $(pwd)/src/main/resources/fitxersWeb:/usr/share/nginx/html:ro \
    -v $(pwd)/src/main/resources/css:/usr/share/nginx/html/css:ro \
    -p 8080:80 -d nginx
```

on:

- **`--name nginx-static-server`**: Assigna un nom al contenidor Docker.
- **`-v $(pwd)/src/main/resources/fitxersWeb:/usr/share/nginx/html:ro`**:  
  Mapeja la carpeta `fitxersWeb` com a la carpeta arrel que NGINX servirà.
- **`-v $(pwd)/src/main/resources/css:/usr/share/nginx/html/css:ro`**:  
  Mapeja la carpeta `css` dins de la carpeta arrel de NGINX per als estils.
- **`-p 8080:80`**: Redirecciona el port 80 del contenidor al port **8080** del teu sistema local.
- **`-d nginx`**: Inicia NGINX en segon pla.

> `$(pwd)` és una ordre de shell que s'utilitza per a obtindre la ruta completa del directori de treball actual. És una forma de capturar i utilitzar dinàmicament la ubicació actual de la terminal.

**Atenciò**

Quan NGINX serveix els arxius:  
* `resources/fitxersWeb` es mapetja a `/usr/share/nginx/html` (arrel del servidor).  
* `resources/css` es mapetja a `/usr/share/nginx/html/css`.  

per tant, cal tindre amb compte les rutes relatives:  

```html
<link rel="stylesheet" href="css/monument.css">
```
canviar a  
```html
<link rel="stylesheet" href="/css/monument.css">
```

### **Comandes que pots utilitzar**

* Llistar contenidors docker actius:

```bash
docker docker ps
```
* Llistar contenidors actius i inactius:

```bash
docker docker ps -a
```

* Parar el contenidor:
```bash
docker stop some-nginx
```
* Eliminar el contenidor:
```bash
docker rm some-nginx
```
* Reinicar el contenidor:

```bash
docker restart some-nginx
```

## Per a entrar en el sistema de fitxers del contenidor NGINX:

```bash
docker exec -it p1-nginx /bin/sh
```

Una volta dins podem accedir als arxius que hem servit amb:

```bash
cd /usr/share/nginx/html
ls -l

```
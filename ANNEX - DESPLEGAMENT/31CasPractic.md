---

title: 3.1.- Cas Pràctic - Projecte Fitxers  
parent: NGINX 
has_children: true
layout: default  
nav_order: 35  

---

## Cas Pràctic - Projecte Fitxers (Generaciò de Web Estàtica)

Volem desplegar el nostre projecte de la 1a Avaluació (Projecte Fitxers) que genera fitxers HTML a partir d'un fitxer JSON i volem servir-los amb NGINX.

Per poder desplegar amb NGINX pagines web estàtiques, hem de mapejar les carpetes del nostre projecte amb les carpetes del contenidor NGINX.

Usarem la comnada `docker run` per a iniciar un contenidor NGINX i mapejar les carpetes del nostre projecte.

```bash
docker run --name some-nginx -v /some/content:/usr/share/nginx/html:ro -d nginx
```

---



### **Explicació de la comanda**



```bash
docker run
```
* **Crea i executa un contenidor Docker**.  
El contenidor es basa en una imatge Docker (en aquest cas, la imatge **nginx**).

Cada vegada que utilitzes `docker run`, Docker:
1. **Descarrega la imatge** de Docker Hub (si no la tens).  
2. **Crea un contenidor** a partir de la imatge.  
3. **Executa** el contenidor amb la configuració que li has passat.  

---

* **--name some-nginx**
```bash
--name some-nginx
```
Esta opció assigna un **nom personalitzat** al contenidor.
- El contenidor es dirà en este cas `some-nginx`.
- Amb este nom, podràs utilitzar comandes de Docker per a interactuar amb el contenidor.

**Exemples de comandes per a usar el nom del contenidor**:
```bash
docker stop some-nginx    # Atura el contenidor "some-nginx"
docker start some-nginx   # Torna a iniciar el contenidor "some-nginx"
docker logs some-nginx    # Mostra els registres (logs) del contenidor
docker rm some-nginx      # Elimina el contenidor
```

---

* **-v /some/content:/usr/share/nginx/html:ro**
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

**Exemple pràctic**:  
Si al teu sistema tens la carpeta `/some/content/` amb aquesta estructura:
```
/some/content/
   ┣ 📄 index.html
   ┣ 📄 about.html
   ┗ 📁 css/
      ┗ 📄 styles.css
```
Quan accedisques a **http://localhost/index.html**, veuràs l'arxiu **index.html** de la carpeta `/some/content/`.

---

### **-d**
```bash
-d
```
Aquesta opció **"desacobla"** (detach) el contenidor de la terminal.
- Sense aquesta opció, el contenidor s'executa i mostra els registres (logs) a la terminal.
- Amb aquesta opció, el contenidor s'executa en segon pla.

**Com pots veure els contenidors en execució?**  
Executa aquesta comanda:
```bash
docker ps
```
Veuràs alguna cosa semblant a:
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
Aquest és el **nom de la imatge Docker** que es farà servir.
- Si la imatge no està al teu ordinador, **Docker la descarregarà de Docker Hub**.
- La imatge de NGINX inclou un servidor web llest per a servir arxius HTML, CSS, JS.
- Aquesta imatge de **NGINX** serveix els arxius de la carpeta **/usr/share/nginx/html** (dins del contenidor).

---

## **Resum complet**
1. **Crea i executa un contenidor** amb la imatge de NGINX.  
2. **Assigna el nom `some-nginx`** al contenidor.  
3. **Mapeja la carpeta `/some/content/`** (del teu PC) a **`/usr/share/nginx/html/`** (al contenidor NGINX).  
4. NGINX **serveix els arxius estàtics** que estiguen a la carpeta **/some/content/**.  
5. S'executa en **segon pla (-d)**, per la qual cosa no ocupa la terminal.  

---



| **Part**             | **Significat**                                      |
|---------------------|-----------------------------------------------------|
| `docker run`         | Executa un contenidor Docker basat en una imatge.    |
| `--name some-nginx`  | Assigna un nom al contenidor per a identificar-lo (en aquest cas, `some-nginx`). |
| `-v /some/content:/usr/share/nginx/html:ro` | Mapeja una carpeta del teu sistema al contenidor d'NGINX (el volum). |
| `-d`                 | Executa el contenidor en segon pla (mode "detach").  |
| `nginx`              | Nom de la imatge base que s'usarà per al contenidor. |

---

#
| **Part**             | **Significat**                                      |
|---------------------|-----------------------------------------------------|
| `docker run`         | Executa un contenidor Docker basat en una imatge.    |
| `--name some-nginx`  | Assigna un nom al contenidor per a identificar-lo (en aquest cas, `some-nginx`). |
| `-v /some/content:/usr/share/nginx/html:ro` | Mapeja una carpeta del teu sistema al contenidor d'NGINX (el volum). |
| `-d`                 | Executa el contenidor en segon pla (mode "detach").  |
| `nginx`              | Nom de la imatge base que s'usarà per al contenidor. |

---

## **Preguntes freqüents**

### **Què passa si no utilitze `-v /some/content:/usr/share/nginx/html`?**
NGINX utilitzarà la carpeta per defecte **/usr/share/nginx/html** que està dins de la imatge Docker.  
Aquesta carpeta només conté una pàgina HTML per defecte, la famosa **Benvingut a NGINX**.

---

### **Per a què serveix `:ro` (mode només lectura)?**
Quan utilitzes **`:ro`**, li dius a Docker que NGINX **no pot modificar els arxius** a la carpeta `/some/content/`.  
Això evita que el contenidor sobreescriga els arxius al teu ordinador.  
Si no vols només lectura, pots ometre **`:ro`**, i la carpeta serà accessible amb permisos d'escriptura.

---

### **Com puc actualitzar els arxius HTML i CSS?**
Ja que estàs mapejant la carpeta `/some/content/`, pots modificar els arxius al teu sistema de fitxers local.  
Els canvis es reflectiran automàticament en **http://localhost/index.html**.

**Exemple**:
```bash
echo "<h1>Pàgina actualitzada!</h1>" > /some/content/index.html
```
Accedeix de nou a **http://localhost/index.html** i veuràs els canvis.

---

### **Com ature o elimine el contenidor?**
Per a aturar el contenidor:
```bash
docker stop some-nginx
```

Per a eliminar el contenidor:
```bash
docker rm some-nginx
```

---

### **Com reinicie el contenidor?**
```bash
docker restart some-nginx
```

---

## **Resum final**
- **Serveix arxius HTML/CSS amb NGINX** directament des del teu ordinador.  
- Usa l'opció `-v /some/content:/usr/share/nginx/html:ro` per a mapar la carpeta **/some/content** del teu ordinador al contenidor.  
- Els canvis a la carpeta **/some/content** es reflecteixen automàticament al navegador.  
- Pots aturar, reiniciar o eliminar el contenidor amb les comandes de Docker.  
```









































---

### **Objectiu**
1. Utilitzar les carpetes **`resources/fitxersWeb`** i **`resources/css`** per a servir els fitxers **HTML i CSS** amb NGINX.
2. Assegurar-se que si es modifica l'arxiu **JSON d'entrada**, els fitxers **HTML es regeneren automàticament**.
3. Configurar **NGINX** per a servir els fitxers sense necessitat de reiniciar-lo o recompilar res.

---

## 🚀 **Passos per implementar la solució**

---

### 🔹 **Pas 1: Estructura del projecte**
Organitza el projecte segons aquesta estructura:

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

### 🔹 **Pas 2: Configuració del sistema per generar els fitxers HTML**
Cada vegada que es modifique l'arxiu **JSON d'entrada**, el sistema ha de:
1. Llegir l'arxiu **JSON**.
2. Utilitzar **Thymeleaf** per generar els fitxers **HTML**.
3. Guardar els HTML generats en la carpeta **`resources/fitxersWeb`**.

---

### 🔹 **Pas 3: Configurar NGINX per servir els fitxers**

Per a **servir directament les carpetes `fitxersWeb` i `css`**, es pot muntar la configuració en un contenidor de **NGINX**.

#### 1️⃣ **Comanda Docker per iniciar NGINX**:
```bash
docker run --name nginx-static-server \
    -v $(pwd)/src/main/resources/fitxersWeb:/usr/share/nginx/html:ro \
    -v $(pwd)/src/main/resources/css:/usr/share/nginx/html/css:ro \
    -p 8080:80 -d nginx
```

---

### 🔍 **Explicació del comando**:
- **`--name nginx-static-server`**: Assigna un nom al contenidor Docker.
- **`-v $(pwd)/src/main/resources/fitxersWeb:/usr/share/nginx/html:ro`**:  
  Mapeja la carpeta `fitxersWeb` com a la carpeta arrel que NGINX servirà.
- **`-v $(pwd)/src/main/resources/css:/usr/share/nginx/html/css:ro`**:  
  Mapeja la carpeta `css` dins de la carpeta arrel de NGINX per als estils.
- **`-p 8080:80`**: Redirecciona el port 80 del contenidor al port **8080** del teu sistema local.
- **`-d nginx`**: Inicia NGINX en segon pla.

---

### 🔹 **Pas 4: Accedir a l'aplicació**
Una vegada el contenidor està funcionant, pots accedir a l'aplicació des del navegador a la següent adreça:

```
http://localhost:8080/index.html
```

---

### 🔹 **Pas 5: Automatitzar el procés**
Per automatitzar el procés de generació dels HTML i la configuració de NGINX, pots crear un **script bash**.

#### **Script `start.sh`**:
```bash
#!/bin/bash

# 1. Compilar i executar l'aplicació Java
echo "Compilant i executant l'aplicació Java..."
mvn clean package
java -cp target/tu-proyecto.jar Main &

# 2. Reiniciar NGINX amb Docker
echo "Iniciant NGINX amb Docker..."
docker stop nginx-static-server
docker rm nginx-static-server
docker run --name nginx-static-server \
    -v $(pwd)/src/main/resources/fitxersWeb:/usr/share/nginx/html:ro \
    -v $(pwd)/src/main/resources/css:/usr/share/nginx/html/css:ro \
    -p 8080:80 -d nginx
```

---

### 🚀 **Com executar tot el procés automàticament**
1. Fes el script **executable** amb la següent comanda:
   ```bash
   chmod +x start.sh
   ```

2. Executa l'script per generar els HTML i iniciar NGINX:
   ```bash
   ./start.sh
   ```

---

## 🎉 **Resultat final**
1. Els fitxers **HTML** generats s'emmagatzemen en la carpeta **`resources/fitxersWeb`**.
2. Els **arxius CSS** s'utilitzen des de la carpeta **`resources/css`**.
3. **NGINX** serveix automàticament els arxius HTML i CSS en:  
   👉 **http://localhost:8080/index.html**.
4. Cada vegada que s'actualitze el fitxer **data.json**, l'script regenerarà els HTML i NGINX servirà els nous arxius.

---

## 🚀 **Resum**
1. **Carpeta `fitxersWeb` i `css`**: Serveix els fitxers estàtics directament.
2. **Automatització**: Regenera automàticament els HTML quan es modifiquen els fitxers JSON.
3. **Configuració NGINX**: Serveix els arxius directament, sense reiniciar.
4. **Accés immediat**: Accedeix a **http://localhost:8080/index.html** per veure la versió més actualitzada.

---



# RUTES

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

Quan NGINX serveix els arxius:
* resources/fitxersWeb es mapeja a /usr/share/nginx/html (arrel del servidor).
* resources/css es mapeja a /usr/share/nginx/html/css.

per tant :

<link rel="stylesheet" href="css/monument.css">
canviar a 
<link rel="stylesheet" href="/css/monument.css">



docker run --name p1-nginx -v /home/jaume/IdeaProjects/ADA-P1-CiutatsNginx/src/main/resources/:/usr/share/nginx/html:ro -p 8080:80 -d nginx

docker exec -it p1-nginx /bin/sh
cd /usr/share/nginx/
ls -l


docker stop p1-nginx

docker rm p1-nginx
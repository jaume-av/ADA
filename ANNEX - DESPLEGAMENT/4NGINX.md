---
title: 4.- NGINX  
parent: ANNEX - DESPLEGAMENT  
has_children: true
layout: default  
nav_order: 40  
---

# NGINX - SERVIDOR WEB  



[NGINX - enllaç a dockerhub](https://hub.docker.com/_/nginx)



Imagineu que teniu una paradeta de menjar ràpid en una festa i arriba molta gent alhora per demanar menjar. Vosaltres, com a persones, no podeu atendre totes les persones al mateix temps, però... i si poseu algú a l'entrada que reba totes les comandes i les distribuïsca a diferents cuiners que preparen cada comanda? Això és exactament el que fa **NGINX**.

---

## **Què és NGINX?**  
**NGINX (es pronuncia "Engine-X")** és com un "cambrer intel·ligent" per a les pàgines web. És un **servidor web** que s’encarrega de gestionar comandes (peticions) d’usuaris d'Internet i enviar-los la informació (com HTML, CSS, imatges, etc.) que necessiten.  

Però no sols això, també fa de **repartidor de trànsit** (reverse proxy) i pot gestionar moltes peticions a la vegada, sense saturar-se.

---

## **Usos d'NGINX**  
**NGINX** té moltes funcions, però les més importants són les seguents:

---

### **1️. Servidor web (la més típica)**  
Quan obriu una pàgina web (com **http://localhost:8080/index.html**), **NGINX** s’encarrega d’enviar-vos l'arxiu **index.html** que es troba en el servidor.  

**Exemple**:  
Si teniu arxius HTML, CSS i imatges, **NGINX** us envia tot això al navegador, com si fora el "cambrer" que us porta el menjar a la taula.  

**I per a què?**  
- Per a mostrar pàgines web estàtiques (HTML, CSS, imatges, etc.).  

**Com es fa?**  
Amb esta comanda de Docker:  
```bash
docker run --name nginx-container \
    -v $(pwd)/resources/fitxersWeb:/usr/share/nginx/html:ro \
    -p 8080:80 -d nginx
```
Això fa que puagam veure la pàgina **http://localhost:8080/index.html** al nostre navegador.  

---

### **2️. Reverse proxy (el "distribuïdor de comandes")**  
Imagineu que teniu moltes "paradetes de menjar" (servidors) i cada una fa una tasca diferent:  
- Una fa pizzes.  
- L'altra fa hamburgueses.  
- L'altra ven gelats.  

Quan arriba un client, no ha de saber quina paradeta fa cada cosa. Ell només li demana menjar al **cambrer principal (NGINX)** i aquest sap on ha d'enviar la comanda. Això és el que fa NGINX com a **reverse proxy**.

**Per exemple**:  
Suponem que tenim 3 aplicacions:  
- **App 1**: Web d'HTML estàtica.  
- **App 2**: API de dades.  
- **App 3**: Aplicació en Node.js.  

**NGINX** pot redirigir les peticions segons la URL:  
- **http://localhost/app1** → Serveix la pàgina HTML.  
- **http://localhost/api** → Redirigeix a l’API.  
- **http://localhost/app2** → Serveix l'aplicació Node.js.  

**Amb quin objectiu?**  
- Per a tindre un únic punt d’entrada per a diverses aplicacions o serveis.  
- Per a no exposar els servidors interns a l'exterior (més seguretat).  

**Com es fa?**  
Creem una configuració per a **NGINX** com aquesta: 

```nginx

server {
    listen 80;

    location /app1/ {
        proxy_pass http://localhost:8081/;
    }

    location /api/ {
        proxy_pass http://localhost:8082/;
    }

    location /app2/ {
        proxy_pass http://localhost:8083/;
    }
}
```
Ara, quan accedim a **http://localhost/app1**, **NGINX** ens enviarà a **http://localhost:8081**.  
Per a **http://localhost/api**, NGINX us enviarà a **http://localhost:8082**.  

---

###  **3️. Balancer de càrrega (el "repartidor d'usuaris")**  
Quan una pàgina web rep **milers d'usuaris al mateix temps**, un sol servidor es pot col·lapsar. Per a evitar això, es posen **molts servidors** a treballar junts, però... **com sap cada usuari a quin servidor ha d'anar?** Ací entra NGINX!  

NGINX distribueix als usuaris entre els diferents servidors.  
Exemple: 
- **Usuari 1** → Servidor A.  
- **Usuari 2** → Servidor B.  
- **Usuari 3** → Servidor C.  
- **Usuari 4** → Servidor A (comencem de nou).  

**Exemple**:  

Si tenim tres servidors d'aplicació, **NGINX** pot repartir el tràfic entre ells amb esta configuració:  

```nginx

upstream backend_servers {
    server servidor1.example.com;
    server servidor2.example.com;
    server servidor3.example.com;
}

server {
    listen 80;

    location / {
        proxy_pass http://backend_servers;
    }
}
```
Ara, cada vegada que una persona entra en la web, **NGINX** l'envia a un servidor diferent.

**Per a què?**  
- Per a evitar la saturació d’un únic servidor.  
- Per a repartir la càrrega entre molts servidors.  

---

### **4️. Caché de contingut (molt més ràpid)**  
Imagina que sempre demaneu la mateixa comanda a la paradeta (pizza de 4 formatges).  
El cambrer pot tindre aquesta pizza "preparada" i només ha de servir-la ràpidament.  
Això és el que fa **NGINX** quan guarda les respostes de les webs al "caché".

Quan la següent persona demana la mateixa pàgina, **NGINX la retorna molt més ràpidament** sense haver de tornar a demanar-la al servidor.

**Exemple**:

Pots activar la caché amb aquesta configuració:  
```nginx
proxy_cache_path /var/cache/nginx levels=1:2 keys_zone=cache_zone:10m;

server {
    location / {
        proxy_cache cache_zone;
        proxy_pass http://backend_servers;
    }
}
```
Ara, cada vegada que demaneu una pàgina, NGINX la guarda en la caché, i la següent persona la rebrà en mil·lisegons.

**Per a què?**  
- Per a fer que la web carregue més ràpidament.  
- Per a no carregar tant de treball al servidor d’origen.  

---

### **En Resum**  
- **NGINX** és com un **cambrer intel·ligent** que sap on enviar cada petició.  
- **Servidor web**: Envia HTML, CSS i imatges al navegador.  
- **Reverse proxy**: Reparteix peticions entre diferents servidors.  
- **Balancer de càrrega**: Reparteix la càrrega entre servidors quan hi ha molts usuaris.  
- **Caché**: Guarda les respostes per a servir-les més ràpidament.  

---

###  Importància d'NGINX**  
- És **ràpid** i consumeix molt **poca memòria** (pot gestionar milers de peticions a la vegada).  
- S’utilitza en **el 60% de les pàgines web més grans del món** (YouTube, Netflix, etc.).  
- Permet servir **HTML, CSS, JS i imatges** sense necessitat de tindre un servidor d'aplicacions complet.  
- És **gratuït i obert (open-source)**.  

---


## **Què pot fer i què no pot fer NGINX (Comparativa servidors web)**

NGINX és un dels servidors més utilitzats al món, però com qualsevol tecnologia, té punts forts i limitacions en comparació amb altres servidors web com **Apache**, **Tomcat** o **Node.js**.

---

## **Què pot fer NGINX**

### **Servidor de fitxers estàtics (HTML, CSS, JS, imatges, vídeos, etc.)**
- **NGINX és més ràpid que Apache i Tomcat** per servir fitxers estàtics, gràcies a la seua arquitectura basada en esdeveniments asíncrons.  
- **Es millor per que:**  
  - Utilitza menys memòria RAM que Apache o Tomcat, ja que no crea un procés nou per a cada connexió.  
  - Pot gestionar **milers de connexions simultànies** sense crear nous fils o processos, cosa que Apache no pot fer de forma tan eficient.  
  - NGINX no carrega mòduls innecessaris, només serveix fitxers estàtics d'una forma simple i ràpida.  

> **Exemple d'ús**: Si el vonostrestre lloc web només serveix HTML, CSS, JS i imatges, **NGINX és la millor opció**. Per contra, Tomcat o Node.js serien excessius, ja que estan pensats per a executar aplicacions dinàmiques.

---

### **Reverse proxy (Proxy invers)**
- **NGINX destaca com a reverse proxy per davant d'Apache** per la seua eficiència i configuració simple.  
- **Es millor per que:**  
  - Pot gestionar milers de connexions simultànies de forma asíncrona.  
  - Pot redirigir el trànsit de manera dinàmica segons la URL, cosa que Apache també fa, però NGINX ho fa amb menys ús de memòria.  
  - **Amaga la IP i el port interns** dels servidors d'aplicació, millorant la seguretat.  

> **Exemple d'ús pràctic**: Imaginem que tenim una API en Node.js a **localhost:3000** i vols accedir a l'API a través de **/api/**. Amb NGINX, pots fer aquesta redirecció de forma ràpida i senzilla, evitant exposar la IP o el port de l'API. Aquesta funcionalitat també es pot aconseguir amb **Apache** o **HAProxy**, però NGINX ho fa de forma més eficient.

---

### **Balancer de càrrega (Load Balancer)**
- **NGINX és més eficient com a balancer de càrrega que Apache**.  
- **Es millor per que:**    
  - Permet repartir el trànsit entre diversos servidors amb estratègies com **round-robin**, **least connections** (menys connexions) i **IP Hash**.  
  - Consumeix menys recursos, ja que no crea un fil o procés per a cada connexió, com fa Apache.  
  - La configuració és molt senzilla en comparació amb un **Load Balancer d'Apache o HAProxy**.  

> **Exemple d'ús**: Si tenim una aplicació gran que necessita diversos servidors de backend (per exemple, diverses instàncies d'un servidor Node.js o Tomcat), NGINX pot distribuir les peticions entre aquests servidors per no saturar-ne cap. Apache també pot fer-ho, però el seu rendiment no és tan bo quan es tracta de moltes connexions simultànies.

---

### **Caché de contingut**
- **NGINX pot guardar respostes d'API o pàgines HTML per a millorar la velocitat de càrrega**.  
- **Es millor per que:**    
  - Pot utilitzar el sistema de fitxers per guardar respostes de peticions de forma local, evitant futures consultes al servidor d'origen.  
  - **Apache també té caché**, però requereix mòduls addicionals com **mod_cache** i la configuració és més complexa.  
  - La configuració de NGINX és més senzilla i consumeix menys recursos de memòria i CPU.  

> **Exemple d'ús pràctic**: Imagina que la teva aplicació carrega les mateixes dades d'una API moltes vegades. Amb NGINX, pots cachejar aquestes respostes, millorant el temps de resposta i reduint la càrrega del servidor d'API. Aquesta mateixa funcionalitat es pot aconseguir amb **Varnish Cache**, però NGINX pot ser suficient en la majoria dels casos.

---

### **HTTPS i certificats SSL/TLS**
- **NGINX pot gestionar directament connexions HTTPS** i inclou suport natiu per a **Let’s Encrypt**.  
- **Es millor per que:**  
  - Pot gestionar certificats SSL/TLS sense necessitat de mòduls addicionals (cosa que Apache necessita).  
  - Suporta **HTTPS/2** per defecte, oferint millor rendiment que Apache.  
  - La configuració per a HTTPS en NGINX és més fàcil que en Tomcat o Apache.  

> **Exemple d'ús pràctic**: Si voleu servir la vostra aplicació de forma segura amb **https://**, NGINX permet utilitzar **Let’s Encrypt** de forma senzilla per obtenir certificats SSL gratuïts. Apache també pot utilitzar Let's Encrypt, però la configuració de NGINX sol ser més directa.

---

## **Què no pot fer NGINX (limitacions) i alternatives?**

### **Executar codi dinàmic (Java, Python, PHP, etc.)**
- **NGINX no pot executar codi dinàmic directament**, a diferència de Tomcat, Node.js o Apache.  
- **Com ho solucionem?**  
  - **NGINX actua com a reverse proxy**, redirigint les peticions a servidors d'aplicacions (com Node.js, Tomcat o Python/Flask).  
  - **Alternativa**: Si voleu executar codi dinàmic, utilitzeu **Tomcat** (per a Java), **Node.js** (per a JavaScript) o **Apache amb mod_php** (per a PHP).  

---

### **Processar codi server-side (JSP, Thymeleaf, EJS, etc.)**
- **NGINX no pot processar codi server-side** com fitxers JSP (Java Server Pages) o plantilles de Thymeleaf.  
- **Com ho solucionem?**  
  - **NGINX només serveix fitxers estàtics**. Per processar JSP o Thymeleaf, necessiteu un servidor d'aplicacions com **Tomcat o Spring Boot**.  
  - **Alternativa**: Utilitzeu **Tomcat o Spring Boot** per a JSP i Thymeleaf, o utilitzeu **Node.js** amb **EJS o Handlebars**.  

---

### **Gestionar bases de dades (MySQL, PostgreSQL, etc.)**
- **NGINX no pot connectar-se directament a bases de dades** com MySQL o PostgreSQL.  
- **Com ho solucionem?**  
  - Podeu utilitzar **NGINX com a reverse proxy** per a connectar les peticions a un servidor d'aplicacions que interactue amb la base de dades (com Node.js, Flask o Django).  
  - **Alternativa**: Utilitzeu **Node.js** o **Java/Tomcat** per a connectar a bases de dades.  

---

## **Comparativaservidors web**

| **Funcionalitat**         | **NGINX** | **Apache** | **Tomcat** | **Node.js** |
|--------------------------|-----------|------------|------------|-------------|
| Servir fitxers estàtics    | ✅ Millor  | ✅          | ⚠️ Regular | ✅          |
| Reverse proxy              | ✅ Millor  | ✅          | ❌ No       | ❌ No       |
| Balancer de càrrega        | ✅ Millor  | ⚠️ Pot     | ❌ No       | ❌ No       |
| Codi dinàmic (Java, PHP)   | ❌ No      | ✅          | ✅ Java     | ✅ JS       |
| Plantilles (JSP, Thymeleaf)| ❌ No      | ✅ JSP     | ✅ JSP     | ✅ EJS, Pug|
| Connectar bases de dades   | ❌ No      | ❌ No      | ✅ Sí      | ✅ Sí       |

---


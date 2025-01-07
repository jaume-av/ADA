---
title: 3.- Problemes amb les Rutes eb Producció (Desplegament)  
parent: ANNEX - DESPLEGAMENT  
has_children: true
layout: default  
nav_order: 30  
---


# Desplegament d'Aplicacions - Problemes amb les Rutes

Quan treballem localment amb una aplicació com el **Projecte Fitxers**, sovint les rutes dels recursos (com arxius CSS, JavaScript o imatges) es configuren assumint que estem treballant dins d'una estructura de carpetes molt específica. Però **quan despleguem l'aplicació en un entorn de producció**, com **Render**, **NGINX** o **GitHub Pages**, eixa estructura pot canviar i causar problemes.

Estos problemes solen aparéixer perquè **cada entorn gestiona les rutes d'una manera diferent**, i per tant, ens pot passar que els fitxers no es troben, la pàgina es veja sense estil o, fins i tot, que parts de l'aplicació no funcionen correctament.

---

## 1. **Local (desenvolupament)**

Quan treballem localment, tot sol estar configurat per funcionar directament amb els fitxers que tenim en el nostre ordinador. Les rutes relatives com `css/estil.css` funcionen perquè el navegador busca els recursos en la mateixa carpeta o subcarpeta.

**Problema**: Esta configuració **no sempre funciona en producció**, ja que els servidors poden esperar que els arxius estiguen en ubicacions diferents.

**Ampliació: Com funciona el sistema de fitxers local?**  
Quan treballem en local, el navegador accedeix directament als fitxers emmagatzemats en el nostre ordinador. Estes són les característiques principals:

- **Base del projecte**: La carpeta del projecte és la base per a totes les rutes relatives.
- **Estructura d’arxius**: Sol ser similar a esta:
   ```
   📂 fitxersWeb
    ┣ 📜 index.html
    ┣ 📂 css
    ┃ ┗ 📜 estil.css
    ┣ 📂 imatges
    ┃ ┗ 📜 logo.png
   ```
- **Accessos**: Quan carreguem `index.html` en el navegador, aquest interpreta totes les rutes relatives respecte a la carpeta base.

---

## 2. **NGINX**

NGINX serveix per a desplegar aplicacions web i controlar el tràfic de xarxa. Quan despleguem una aplicació amb NGINX, la seua principal característica és que **ens dóna control total sobre la configuració del servidor**. Això significa que hem de configurar explícitament on es troben els fitxers i com s'han de servir.

També pot ser una font de problemes amb les rutes si no està configurat correctament.
   - Si no especifiquem correctament on es troben els recursos, NGINX pot no localitzar-los i retornar errors 404 (fitxer no trobat).
   - Si l'aplicació es desplega en un subdirectori, les rutes relatives també poden fallar.

**Com funciona el sistema de fitxers en NGINX?**  
NGINX és molt flexible i permet configurar com es mapeja el sistema de fitxers local al servidor. Estes són les característiques clau:
- **Carpeta arrel (`root`)**: La configuració `root` indica a NGINX on buscar els fitxers del projecte.
- **Per defecte**: Si no s’especifica cap carpeta arrel, NGINX busca els fitxers en la carpeta `/var/www/html`.
- **Subdirectoris**: Pots servir fitxers en la base (`/`) o en subdirectoris (`/projecteciutats`).
- **Rutes relatives**: Les rutes relatives es resolen respecte a la carpeta base.
- **Exemple d’estructura**:
   ```
   📂 /var/www/projecteciutats
    ┣ 📂 fitxersWeb
    ┃ ┣ 📜 index.html
    ┃ ┣ 📂 css
    ┃ ┃ ┗ 📜 estil.css
    ┃ ┣ 📂 imatges
    ┃ ┃ ┗ 📜 logo.png
   ```
   En este cas:
   - Si `fitxersWeb` és la base (`root`), les rutes relatives funcionaran directament.
   - Si s’utilitza `/projecteciutats`, cal ajustar les rutes o la configuració.

**Opcions de configuració**:
- **Base directa**:
   ```nginx
   root /var/www/projecteciutats/fitxersWeb;
   ```
- **Subdirectori**:
   ```nginx
   location /projecteciutats/ {
       root /var/www/projecteciutats/fitxersWeb;
   }
   ```

**Problemàtica en passar de local a NGINX**  
Quan passem d’un entorn local a NGINX, la principal problemàtica és:
- **Errors 404**: Les rutes relatives poden no funcionar si no configurem correctament la carpeta base (`root`) o el subdirectori.
- **Confusió amb subdirectoris**: Les rutes locals com `css/estil.css` poden fallar en producció perquè NGINX interpreta que cal incloure el subdirectori (`/projecteciutats/`).

---

## 3. **GitHub Pages**

GitHub Pages és un servei de GitHub que permet publicar pàgines web directament des dels repositoris. Quan despleguem una aplicació amb GitHub Pages, hem de tenir en compte que **la ruta de publicació és fixa** i pot afectar la configuració de les rutes relatives.

GitHub Pages sempre serveix l'aplicació des d'un **subdirectori** amb el mateix nom que el repositori (excepte si usem un domini personalitzat). Això fa que les rutes relatives que funcionaven localment deixen de fer-ho.

**Com funciona el sistema de fitxers en GitHub Pages?**  
GitHub Pages automatitza completament la publicació, però amb certes regles fixes:
- **Subdirectori predeterminat**: Cada repositori es publica dins d’un subdirectori que coincideix amb el seu nom (`https://usuari.github.io/nom-repositori/`).
- **Domini personalitzat**: Si s’utilitza un domini personalitzat (`https://ciutats.exemple.com`), els fitxers es publiquen en la base del domini, i les rutes relatives funcionaran directament.

**Opcions de configuració**:
1. Usa `<base>` per a indicar la base del projecte:
   ```html
   <base href="/projecteciutats/">
   ```
2. Utilitza rutes absolutes per evitar problemes:
   ```html
   <link rel="stylesheet" href="/projecteciutats/css/estil.css">
   ```

**Problemàtica en passar de local a GitHub Pages**  
Quan passem de local a GitHub Pages, apareixen els següents problemes:
- **Subdirectori automàtic**: El repositori es publica dins d’un subdirectori (`https://usuari.github.io/projecteciutats`), i les rutes relatives com `css/estil.css` poden fallar.
- **Limitacions del servidor**: No és possible ajustar configuracions avançades per solucionar problemes de rutes.
- **Domini personalitzat**: Si es canvia de subdirectori a domini personalitzat, cal reconfigurar totes les rutes.

---

## 4. **Render**

Render és una plataforma que desplega aplicacions web amb una configuració predeterminada. Quan passem la nostra aplicació a Render:
   - Render espera que indiquem una **carpeta de publicació** (per exemple, `fitxersWeb`) on estiguen els arxius estàtics.
   - Les rutes relatives poden no funcionar si la configuració del projecte no redirigeix correctament els recursos.

**Ampliació: Com gestiona Render el sistema de fitxers?**  
Render segueix un sistema de fitxers basat en una **carpeta de publicació** que tu mateix indiques en la configuració. Això implica:
- **Base del servidor**: La carpeta que indiques (com `fitxersWeb`) es converteix en la base de la teua aplicació.
- **Publicació dels fitxers**: Render no fa cap suposició sobre la resta del projecte. Només publica els fitxers dins de la carpeta designada.

**Problemàtica en passar de local a Render**  
En Render, els principals problemes són:
- **Carpeta de publicació limitada**: Només es publiquen els fitxers dins de la carpeta seleccionada. Si deixem fora fitxers necessaris, no estaran disponibles.
- **Subdirectoris opcionals**: Si l’aplicació es publica en un subdirectori, cal ajustar les rutes relatives amb `<base>`.

---

## 5. En resum:

Els problemes de rutes en passar d’un entorn local a producció (Render, NGINX, GitHub Pages, etc.) es deuen principalment a diferències en com cada entorn gestiona el **sistema de fitxers** i les **rutes relatives** o **absolutes**. Afortunadament, podem aplicar una estratègia universal per minimitzar errors i garantir que l’aplicació funcione correctament en qualsevol entorn.

---

### **Problemes comuns**
1. **Subdirectoris**: Molts servidors publiquen l’aplicació dins d’un subdirectori (com `https://usuari.github.io/projecteciutats`), cosa que fa que les rutes relatives locals deixen de funcionar.
2. **Carpetes de publicació específiques**: Plataformes com Render només publiquen fitxers dins d’una carpeta designada, la qual cosa pot deixar recursos fora de l’abast.
3. **Rendiment i configuració manual**: NGINX requereix configuració explícita per mapar rutes i optimitzar la càrrega de recursos.

---

Per evitar problemes en qualsevol entorn, seguim estes pautes:

### 1. **Usar rutes absolutes sempre que siga possible**
Les rutes absolutes són més segures perquè defineixen el camí complet al recurs independentment de l’entorn:
   ```html
   <link rel="stylesheet" href="/projecteciutats/css/estil.css">
   ```
- **Avantatge**: Garantixen que el navegador trobe els recursos des de l’arrel del servidor o del subdirectori.
- **Inconvenient**: Cal ajustar-les si canvia el subdirectori o el domini.

> NOTA: Una bona configuracio amb rutes relatives ens permetrà desplegar l'aplicació sense problemes en qualsevol entorn.


---

### 2. **Configurar `<base>` per a rutes relatives**
Si volem usar rutes relatives, una bona pràctica és afegir l’etiqueta `<base>` al document HTML principal:
   ```html
   <base href="/projecteciutats/">
   ```
- **Avantatge**: Simplifica les rutes relatives (p. ex., `css/estil.css`) perquè es resolen automàticament respecte al subdirectori base.
- **Recomanat per a**: GitHub Pages i Render, especialment quan es publiquen en subdirectoris.

---

### 3. **Organitzar correctament els fitxers**
Mantindre una estructura clara de carpetes ajuda a evitar problemes amb recursos no trobats:
   ```
   📂 fitxersWeb
    ┣ 📜 index.html
    ┣ 📂 css
    ┃ ┗ 📜 estil.css
    ┣ 📂 imatges
    ┃ ┗ 📜 logo.png
   ```
- **Recomanat per a**: Tots els entorns. Assegura que els recursos estan accessibles i dins de la carpeta de publicació.

---

### 4. **Provar localment amb un servidor**
Abans de desplegar, simula un entorn de producció amb eines com:
   - **http-server**:
     ```bash
     npx http-server ./fitxersWeb --port 8080
     ```
   - **Python**:
     ```bash
     python -m http.server
     ```
- **Avantatge**: Permet detectar problemes amb rutes abans del desplegament.

---

### **Aplicació pràctica**
Seguint estos passos, podem desplegar els nostres projectes amb èxit en qualsevol entorn:
1. Usar rutes absolutes o configurar `<base>` segons l’entorn.
2. Assegurar que tots els fitxers necessaris estan dins de la carpeta de publicació.
3. Provar localment amb un servidor per validar les rutes.


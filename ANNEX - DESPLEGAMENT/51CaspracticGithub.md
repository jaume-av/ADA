---
title: 5.1- Cas pràctic - Desplegament amb GitHub Pages
parent: 5.- Github Pages   
has_children: true
layout: default  
nav_order: 55  
---


# Desplegament amb GitHub Pages

Hem de desplegar el nostre projecte de la 1a Avaluació (**Projecte Fitxers**) que genera fitxers HTML a partir d'un fitxer JSON i volem servir-los amb **GitHub Pages**.

Per poder desplegar amb **GitHub Pages** pàgines web estàtiques, hem de pujar els fitxers del nostre projecte a un repositori de **GitHub** i activar **GitHub Pages** des de la configuració del repositori.

## En dos passos

**1. Pujar el projecte a GitHub**

1. Creem un repositori a **GitHub**.
2. Pujem el nostre projecte a **GitHub**.
3. Activem **GitHub Pages** des de la configuració del repositori.
4. La URL de la pàgina serà `https://usuari.github.io/nom-repositori/`.
5. Obre la URL en el teu navegador per verificar que l'aplicació s'ha desplegat correctament.

**Nota**: GitHub Pages pot trigar uns minuts a actualitzar-se.

---

**2. Verificar el desplegament**

1. Un cop finalitzat el desplegament, **GitHub Pages** ens proporcionarà una **URL** per accedir a la nostra aplicació.
2. Obrim la URL al navegador per verificar que l'aplicació s'ha desplegat correctament.
3. Si hi ha problemes, cal comprovar la configuració del repositori i els fitxers del projecte.

---

## Pas a pas...

### **1. Preparem la carpeta `fitxersWeb` (o la carpeta on tingues tots els arxius) per a GitHub Pages**
GitHub Pages només pot servir fitxers que estiguen en una carpeta accessible directament com a **arrel del repositori** o dins de la carpeta especial `/docs`. Per tant, necessitem ajustar l’estructura del projecte.

#### **Opcions per configurar la carpeta**

- **Opció 1: Moure `fitxersWeb` a l’arrel del repositori:**
  - Trasllada el contingut de `fitxersWeb` (pàgines HTML i CSS) directament a l’arrel del repositori.
  - Exemple d’estructura final al repositori:
    ```
    📂 projecte
     ┣ 📜 Barcelona.html
     ┣ 📜 Granada.html
     ┣ 📜 La Vall d'Uixó.html
     ┣ 📜 Madrid.html
     ┣ 📜 Sevilla.html
     ┣ 📜 València.html
     ┣ 📜 index.html
     ┣ 📂 css
     ┃ ┣ 📜 ciutat.css
     ┃ ┗ 📜 monument.css
    ```

- **Opció 2: Moure `fitxersWeb` a la carpeta `/docs`:**
  - Copia o mou la carpeta `fitxersWeb` a `/docs` a l’arrel del repositori. Aquesta és una alternativa si vols mantenir l’organització actual del projecte.
  - Exemple d’estructura:
    ```
    📂 projecte
     ┣ 📂 docs
     ┃ ┣ 📜 Barcelona.html
     ┃ ┣ 📜 Granada.html
     ┃ ┣ 📜 La Vall d'Uixó.html
     ┃ ┣ 📜 Madrid.html
     ┃ ┣ 📜 Sevilla.html
     ┃ ┣ 📜 València.html
     ┃ ┣ 📜 index.html
     ┃ ┣ 📂 css
     ┃ ┃ ┣ 📜 ciutat.css
     ┃ ┃ ┗ 📜 monument.css
    ```

**Nota:** No pots configurar subcarpetes arbitràries (com `src/main/resources/fitxersWeb`) perquè GitHub Pages només permet publicar des de `/root` o `/docs`.

---

### **2. Activar GitHub Pages**

1. Ves al repositori del projecte a GitHub.
2. Accedeix a **Settings** > **Pages**.
3. A la secció "Source":
   - Si has optat per **Opció 1**, selecciona la branca principal (`master` o `main`) i deixa `/root` com a carpeta de publicació.
   - Si has optat per **Opció 2**, selecciona la branca principal i escull `/docs` com a carpeta de publicació.

---

### **3. Comprovar rutes relatives**

Hem de revisar les rutes relatives dins dels fitxers HTML per assegurar que funcionen amb l’estructura publicada, tant dels arxis CSS com de les imatges i enllaços HTML.

**Exemple d’enllaç correcte als CSS**:
Si els fitxers CSS estan dins de `css/`:
```html
<link rel="stylesheet" href="css/ciutat.css">
<link rel="stylesheet" href="css/monument.css">
```

**Prova en local abans de desplegar** per assegurar-nos que les rutes funcionen correctament.

1. Desplaça’t a la carpeta `fitxersWeb` en el teu ordinador.
2. Usa un servidor local com:
   ```bash
   python3 -m http.server
   ```
   Accedeix a `http://localhost:8000` i comprova que les pàgines es veuen correctament amb els estils aplicats.
   Si volem un altre port: `python3 -m http.server 8080`

**Nota**: Si no s'atura el servidor amb `Ctrl+C`, el port 8000 quedarà ocupat

Ho podem comprovar amb:
```bash
lsof -i :8000
```
i matar el procés amb el PID que ens dona:
```bash
kill -9 <PID>
```
o amb:

```bash
killall python3
```
---

### **4. Publicar i comprova el desplegament**
1. Fes `push` de les modificacions al repositori de GitHub.
2. Accedeix a l’URL proporcionada per GitHub Pages (`https://usuari.github.io/nom-repositori/`) i comprova que les pàgines es mostren correctament amb els estils aplicats.



## **I si necessitem mantindre la nostra estructura de carpetes?**

Si és imprescindible mantenir l’estructura actual amb `fitxersWeb` dins de `src/main/resources/`, **GitHub Pages no és la millor opció per desplegar**. Aquesta limitació es deu al fet que GitHub Pages només pot publicar fitxers des de l’arrel del repositori o des d’una carpeta anomenada `/docs`.

En aquest cas, seria millor utilitzar altres plataformes que ofereixen més flexibilitat i suport per a estructures de carpetes més complexes:

- **Render**: Permet configurar qualsevol carpeta com a directori de publicació, fent-lo ideal per a projectes amb estructures personalitzades.
- **Netlify**: Detecta automàticament les subcarpetes configurables i publica el contingut sense necessitat de reestructurar el projecte.
- **Vercel**: Especialment dissenyat per a aplicacions modernes, és una excel·lent opció per a projectes amb estructures arbitràries o dinàmiques.

Aquestes plataformes són alternatives potents que poden adaptar-se millor a les necessitats del projecte sense requerir canvis en la seua organització.
---

# Cas pràctic - Desplegament Projecte Fitxers amb GitHub Pages

Hem de desplegar el nostre **Projecte Fitxers**amb **GitHub Pages**.

Ho farem de 3 maneres:

**1.- Movent els fitxers HTML i CSS** ho podem fer tant a la carpeta arrel del repositori o a la carpeta `/docs`.

* Crearem un repositori a **GitHub** 
* Moure els fitxers HTML i CSS a la carpeta arrel del **repositori** o a la carpeta **`/docs`**.
  * En este cas, mourem els fitxers HTML i CSS a la carpeta `/docs`.
* No es necessari pujar el projecte sencer, només els fitxers HTML i CSS.


**2.- Mantenint l'estructura de carpetes actual**

* Creem un repositori a **GitHub** amb tot el nostre projecte.
* En este cas farem trampa i crearem un arxiu `index.html` a la carpeta arrel del repositori amb un enllaç a la carpeta `fitxersWeb`.

**3.- Utilitzant Render**

* Com no podem configurar subcarpetes arbitràries (com `src/main/resources/fitxersWeb`) perquè GitHub Pages només permet publicar des de `/root` o `/docs`, hem de buscar altres alternatives com **Render**, o també podem utilitzar **Netlify** o **Vercel**, o també 
* Eliminem l'anterior arxiu `index.html` i creem un servei web en Render.


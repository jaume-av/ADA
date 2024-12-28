---
title: 5.- Github Pages 
parent: ANNEX - DESPLEGAMENT  
has_children: true
layout: default  
nav_order: 50  
---

# **GitHub Pages**

**GitHub Pages** és un servei gratuït que permet publicar pàgines web directament des d’un repositori de **GitHub**. Està pensat per a projectes **estàtics**, com pàgines personals, documentació de projectes o aplicacions senzilles.

El procés és senzill:
1. Puges els fitxers del projecte (HTML, CSS, JS) al repositori de GitHub.
2. Actives GitHub Pages des de la configuració del repositori.
3. Els fitxers es publiquen automàticament en una URL com `https://usuari.github.io/nom-repositori/`.

---

### **GitHub Pages és recomanable per a:**

**1. Pàgines estàtiques senzilles:**
   Pàgines on el contingut no canvia de manera dinàmica. Totes les dades es preparen prèviament en fitxers HTML, CSS i JavaScript.

   - **Exemples**:
     - Portafolis personals.
     - Blogs estàtics generats amb eines com **Jekyll** o **Hugo**.
     - Pàgines de documentació tècnica.

**2. Pàgines amb contingut generat prèviament:**.

   - Projectes que usen eines per generar fitxers estàtics a partir de dades, com en el nostre cas**.
   - **Exemples**:
     - Llistats d’informació (com ciutats i monuments).
     - Pàgines d’empresa amb contingut fix (qui som, serveis, contacte).

**3. Single Page Applications (SPA) limitades:**
   - Aplicacions que es carreguen en una sola pàgina (`index.html`) i gestionen la navegació amb JavaScript.
   - **Exemples**:
     - Aplicacions React, Angular o Vue que no necessiten backend dinàmic.
   - **Nota**: Requereixen configuracions especials per suportar navegació amb **rutes dinàmiques**.

---

### **GitHub Pages NO és recomanable per a**

**1. Aplicacions amb backend o bases de dades:**
   - GitHub Pages no pot executar codi del servidor (com PHP, Python o Node.js) ni connectar directament a bases de dades.
    
   - **Exemples**:
     - Sistemes de reserves.
     - Portals amb autenticació o usuaris personalitzats.

**2. Pàgines amb contingut altament dinàmic:**
   - Si el contingut depén d’interaccions en temps real o d’actualitzacions constants, necessitaràs un servidor backend.
   - **Exemples**:
     - Aplicacions de xat en viu.
     - Dashboards amb dades que canvien en temps real.

**3. Aplicacions complexes amb funcionalitats avançades:**
   - Funcionalitats com la gestió de processos al servidor o enviament d’emails no són possibles amb GitHub Pages.
   - **Exemples**:
     - Plataformes de comerç electrònic.
     - Sistemes de gestió empresarial (ERP).

---

### **Eines populars per utilitzar amb GitHub Pages**
1. **Jekyll**: Per generar blogs estàtics i pàgines personals.
2. **Hugo**: Per crear pàgines ràpides amb generació prèvia de contingut.
3. **React o Vue** (en mode estàtic): Per construir aplicacions SPA amb navegació limitada.
4. **Sphinx**: Per generar documentació tècnica estàtica.

---


GitHub Pages és ideal per a projectes senzills o estàtics on no es necessiten funcionalitats avançades del servidor. No és recomanable per a aplicacions amb **backend** o amb contingut **altament dinàmic**. 


---


## GitHub Actions

**GitHub** ofereix una altra eina de desplegament coneguda com **GitHub Actions**, que és molt més flexible que GitHub Pages i permet desplegar aplicacions més complexes.

**GitHub Actions** és una eina integrada dins de GitHub que permet automatitzar processos com compilacions, proves, desplegaments i més. Funciona com un sistema d’integració i lliurament continu (**CI/CD**) que podem usar per desplegar aplicacions en plataformes externes.

### Funcionament 

1. Defininim  un fitxer de configuració YAML (workflow) dins del repositori (`.github/workflows/`).
2. Este fitxer especifica:
   - Quan s'ha d'executar el workflow (p. ex., quan es fa un push al repositori).
   - Quines accions s’han de dur a terme (p. ex., compilar, passar proves, desplegar).
3. GitHub Actions executa el workflow en entorns virtuals preparats (p. ex., Ubuntu, Windows, macOS).

---

### **Us de GitHub Actions en desplegaments**

1. **Desplegar aplicacions a serveis externs**:
   - Pots utilitzar GitHub Actions per desplegar aplicacions en plataformes com:
     - **Heroku** (per aplicacions amb backend dinàmic).
     - **Render** (amb suport per backend i frontend).
     - **AWS** o **Google Cloud** (per a desplegaments avançats).
     - **Netlify** o **Vercel** (per a aplicacions estàtiques o SPA).

2. **Desplegar aplicacions Docker**:
   - Pots utilitzar GitHub Actions per construir i desplegar contenidors Docker en serveis com **Docker Hub** o **Kubernetes**.

3. **Desplegar a GitHub Pages**:
   - Encara que GitHub Pages és una eina senzilla, pots combinar-la amb GitHub Actions per generar i publicar automàticament contingut estàtic (p. ex., un blog generat amb Jekyll).

---

### **Quan utilitzar GitHub Actions en lloc de GitHub Pages?**

1. **Aplicacions dinàmiques**:
   - Si el teu projecte necessita backend o bases de dades, GitHub Pages no és suficient. Amb GitHub Actions pots desplegar el backend a plataformes com Heroku o Render.

2. **Automatització del desplegament**:
   - Si vols desplegar automàticament després de cada canvi al codi, GitHub Actions pot gestionar tot el procés (compilar, passar proves, desplegar).

3. **Necessitats avançades de personalització**:
   - Si el teu projecte requereix configuracions especials (p. ex., desplegar diferents versions en diversos entorns), GitHub Actions és més adequat.

---

**Exemples d’ús combinat**

- **GitHub Pages + GitHub Actions**:
   - Generar automàticament fitxers estàtics amb **Hugo** o **Jekyll** i publicar-los a GitHub Pages sense intervenció manual.

- **GitHub Actions per Render**:
   - Automatitzar el desplegament d’una aplicació backend+frontend a Render després de cada actualització al repositori.

- **GitHub Actions per Docker**:
   - Construir una imatge Docker del teu projecte i enviar-la automàticament a Docker Hub o un clúster Kubernetes.

---

En resum, **GitHub Pages** és perfecte per a projectes estàtics senzills, però si necessitem més flexibilitat o gestionar aplicacions dinàmiques, **GitHub Actions** és l’eina recomanada. Permet desplegar projectes en plataformes externes i automatitzar tot el flux de treball del desenvolupament fins al desplegament.


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

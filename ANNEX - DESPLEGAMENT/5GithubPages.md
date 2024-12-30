---
title: 5.- Github Pages 
parent: ANNEX - DESPLEGAMENT  
has_children: true
layout: default  
nav_order: 50  
---

# **GitHub Pages i GitHub Actions**

## **GitHub Pages**

**GitHub Pages** és un servei gratuït que permet publicar pàgines web directament des d’un repositori de **GitHub**. Està pensat per a projectes **estàtics**, com pàgines personals, documentació de projectes o aplicacions senzilles.

El procés és senzill:
1. Puges els fitxers del projecte (HTML, CSS, JS) al repositori de GitHub.
2. Actives GitHub Pages des de la configuració del repositori.
3. Els fitxers es publiquen automàticament en una URL com `https://usuari.github.io/nom-repositori/`.

---

### **GitHub Pages és recomanable per a:**

**1. Pàgines estàtiques senzilles:**  
Pàgines amb contingut predefinit en fitxers HTML, CSS i JavaScript.  
- **Exemples:** 
  - Portafolis personals.
  - Blogs estàtics generats amb **Jekyll** o **Hugo**.
  - Documentació tècnica de projectes.

**2. Pàgines amb contingut generat prèviament:**  
Projectes que utilitzen eines per generar fitxers estàtics a partir de dades estructurades.  
- **Exemples:**  
  - Llistats d’informació, com guies o directoris (p. ex., ciutats i monuments).  
  - Pàgines d’empresa amb contingut fix (qui som, serveis, contacte).

**3. Single Page Applications (SPA) limitades:**  
Aplicacions que es carreguen en una sola pàgina (`index.html`) i gestionen la navegació amb JavaScript.  
- **Exemples:**  
  - Aplicacions **React**, **Angular** o **Vue** sense backend dinàmic.  
- **Nota:** Requereixen configuracions especials per suportar navegació amb **rutes dinàmiques**.

---

### **GitHub Pages NO és recomanable per a:**

**1. Aplicacions amb backend o bases de dades:**  
GitHub Pages no pot executar codi del servidor ni connectar-se a bases de dades.  
- **Exemples:**  
  - Sistemes de reserves.  
  - Aplicacions amb autenticació o usuaris personalitzats.

**2. Contingut altament dinàmic:**  
Si el contingut es genera en temps real, necessitaràs un servidor backend.  
- **Exemples:**  
  - Aplicacions de xat.  
  - Dashboards amb dades actualitzades constantment.

**3. Funcionalitats avançades:**  
Funcionalitats com processos al servidor o enviament d’emails no són possibles.  
- **Exemples:**  
  - Plataformes de comerç electrònic.  
  - Sistemes de gestió empresarial (ERP).

---

#### **Eines populars per usar amb GitHub Pages**
1. **Jekyll**: Blogs estàtics i pàgines personals.  
2. **Hugo**: Generació ràpida de pàgines estàtiques.  
3. **React o Vue** (en mode estàtic): Per construir aplicacions SPA amb navegació limitada..  
4. **Sphinx**: Documentació tècnica.

---

## **GitHub Actions**

**GitHub Actions** és una eina integrada de **CI/CD** (Integració i Lliurament Continu) que permet automatitzar processos com proves, compilacions i desplegaments. A diferència de GitHub Pages, és molt més flexible i s’adapta a projectes dinàmics o amb necessitats avançades.

---

### **Funcionament de GitHub Actions**

1. **Definició de workflows:**  
   Cada workflow es defineix en un fitxer YAML dins la carpeta `.github/workflows/`.
2. **Execució automàtica:**  
   Pots configurar triggers (p. ex., un push o una PR) que inicien automàticament el procés.
3. **Entorns virtuals:**  
   GitHub Actions ofereix entorns preconfigurats (Ubuntu, Windows, macOS) per executar les tasques definides.

---

### **Usos principals de GitHub Actions**

**1. Desplegar aplicacions a serveis externs:**  
- **Exemples de plataformes:**  
  - **Heroku:** Per a backend dinàmics.  
  - **Render:** Per projectes backend i frontend.  
  - **AWS** o **Google Cloud:** Desplegaments avançats.  
  - **Netlify/Vercel:** Per SPAs o projectes estàtics.

**2. Construcció i desplegament de contenidors Docker:**  
- Automatitza la construcció d’imatges Docker i el seu desplegament a:
  - **Docker Hub**.  
  - Clústers de **Kubernetes**.

**3. Proves i integracions contínues:**  
- Automatitza tests unitàries i proves d’integració per assegurar la qualitat del codi.  

**4. Sincronització i tasques personalitzades:**  
- Còpies de seguretat, sincronització de repositoris, notificacions a Slack, etc.

---

### **GitHub Actions + GitHub Pages: Sinergia**

**1. Automatitzar la generació i publicació de pàgines estàtiques:**  
- Genera automàticament fitxers estàtics (amb **Jekyll**, **Hugo**, etc.) i publica’ls a GitHub Pages.

**2. Publicar blogs o documentació tècnica:**  
- Configura un workflow per compilar fitxers estàtics i desplegar-los sense intervenció manual.

---

### **Quan utilitzar GitHub Actions en lloc de GitHub Pages?**

1. **Aplicacions dinàmiques:**  
   - Necessites un backend? GitHub Actions et permet desplegar-lo en serveis com Heroku o Render.

2. **Automatització:**  
   - Desplega automàticament després de cada canvi al codi.  

3. **Personalització avançada:**  
   - Configura desplegaments complexos, com versions múltiples o entorns paral·lels.

---

### **Exemples pràctics combinats**

- **GitHub Pages + Actions:**  
  - Generar fitxers estàtics amb **Jekyll** i desplegar-los automàticament.

- **Actions per Render:**  
  - Automatitzar el desplegament d'una aplicació backend+frontend a Render.

- **Actions per Docker:**  
  - Construir i desplegar imatges Docker automàticament.

---

**En resum**

**GitHub Pages** és ideal per a projectes senzills i estàtics. **GitHub Actions**, en canvi, ofereix una gran flexibilitat per automatitzar tasques, desplegar aplicacions complexes i integrar serveis externs. Si el projecte requereix tant pàgines estàtiques com funcionalitats avançades, combinar les dues eines és una opció molt potent.




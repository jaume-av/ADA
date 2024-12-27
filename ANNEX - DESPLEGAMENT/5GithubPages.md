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

   - **Descripció**: Projectes que usen eines per generar fitxers estàtics a partir de dades, com en el nostre cas**.
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
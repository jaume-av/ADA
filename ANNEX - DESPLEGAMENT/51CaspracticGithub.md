---
title: 5.1- Cas pràctic - Desplegament amb GitHub Pages
parent: 5.- Github Pages   
has_children: true
layout: default  
nav_order: 55  
---


# Cas pràctic - Desplegament amb GitHub Pages

Hem de desplegar el nostre **Projecte Fitxers**amb **GitHub Pages**.

Ho farem de 3 maneres:

**1.- Movent els fitxers HTML i CSS** ho podem fer tant a la carpeta arrel del repositori o a la carpeta `/docs`.

* Crearem un repositori a **GitHub** 
* Moure els fitxers HTML i CSS a la carpeta arrel del **repositori** o a la carpeta **`/docs`**.
  * En este cas, mourem els fitxers HTML i CSS a la carpeta `/docs`.
* No es necessari pujar el projecte sencer, només els fitxers HTML i CSS.


**2.- Mantenint l'estructura de carpetes actual**

* Creem un repositori a **GitHub** amb tot el nostre projecte.
* Com no podem configurar subcarpetes arbitràries (com `src/main/resources/fitxersWeb`) perquè GitHub Pages només permet publicar des de `/root` o `/docs`, hem de buscar altres alternatives com Render, Netlify o Vercel.
* En este cas farem trampa i crearem un arxiu `index.html` a la carpeta arrel del repositori amb un enllaç a la carpeta `fitxersWeb`.

**3.- Utilitzant Render**

* Eliminem l'anterior arxiu `index.html` i creem un servei web en Render.


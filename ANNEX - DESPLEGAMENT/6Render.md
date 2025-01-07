---
title: 6.- Render 
parent: ANNEX - DESPLEGAMENT  
has_children: true
layout: default  
nav_order: 60  
---

# Introducció a Render

Si volem de desplegar aplicacions web de manera senzilla, eficient i amb un bon rendiment, **Render** és una bona opció. És una plataforma de desplegament moderna que simplifica el procés de portar les nostres aplicacions des del desenvolupament fins a la producció, sense que hagem de preocupar-nos per la infraestructura subjacent.


Render és una **plataforma d'allotjament completament gestionada** que ens permet desplegar:
- **Aplicacions web** (frontend i backend).
- **Serveis estàtics** (per exemple, pàgines generades amb frameworks com React, Vue o Angular).
- **Bases de dades** (PostgreSQL).
- **Serveis de backend** (Node.js, Python, Java, entre d’altres).
- **Contenidors Docker** personalitzats.

### Característiques de Render

Hi ha moltes raons per considerar Render per als vostres projectes:
- **Configuració fàcil**: Tot funciona a partir d'un arxiu de configuració senzill o integració directa amb Git.
- **Escalabilitat automàtica**: Render pot escalar els vostres serveis de manera dinàmica segons la càrrega.
- **Preus competitius**: Ofereix un model de preus assequible, amb un nivell gratuït que és ideal per a projectes petits o en desenvolupament.
- **Certificats SSL automàtics**: La seguretat està integrada, amb certificats SSL generats automàticament.
- **Integració amb CI/CD (integració i desplegament contínu)**: Es sincronitza amb repositoris com GitHub o GitLab per a desplegaments automàtics.

### Característiques principals

1. **Desplegaments automàtics**:
   - Cada vegada que actualitzem el codi al nostre repositori (per exemple, GitHub), Render pot desplegar automàticament la versió més recent.

2. **Gestió de serveis estàtics**:
   - Ideal per a pàgines web sense servidor (serverless), com per exemple les creades amb Jekyll, Hugo o frameworks moderns de JavaScript.

3. **Bases de dades gestionades**:
   - Render ens proporciona bases de dades PostgreSQL gestionades, amb còpies de seguretat i restauració fàcil.
   - Render no té suport natiu per a altres bases de dades com **MySQL**, **MongoDB** o **Redis**, però podem  desplegar-les mitjançant un contenidor Docker personalitzat. El que ens permet qualsevol base de dades que tingam configurada dins del nostre entorn de contenidors.
   -  Render permet connectar-nos a servidors de bases de dades externs, es a dir, podem integrar serveis de tercers com **Amazon RDS** (PostgreSQL, MySQL, etc.), **Google Cloud SQL**, **Azure Database Services**, etc.

4. **Suport per contenidors Docker**:
   - Si utilitzem Docker, Render pot executar imatges personalitzades, cosa que el fa molt flexible.

5. **Observabilitat**:
   - Disposa d'eines de monitoratge integrades que ens permeten fer un seguiment del rendiment de les nostres aplicacions.

### Casos d'ús comuns

- Desplegar una **API backend** amb Node.js o Flask.
- Allotjar una pàgina estàtica de **portafolis personals**.
- Implementar un servei amb un **contenidor Docker personalitzat**.
- Crear una base de dades PostgreSQL per a una aplicació de producció.

### Per on començar?

1. Creem un compte a la pàgina de **[Render](https://render.com)**.
2. Connectem el nostre repositori GitHub o GitLab.
3. Configurem el projecte (Render detectarà automàticament la configuració per defecte, com ara el llenguatge de programació i el framework utilitzat).
4. Fem clic a "Deploy" i Render s'encarregarà de la resta!

---

Amb Render, podem dedicar més temps a desenvolupar i menys temps a gestionar la infraestructura. És una eina ideal tant per a **desenvolupadors novells** com per a **professionals experimentats** que busquen una solució senzilla però potent per desplegar aplicacions.





## Desplegament en Render

### Prerequisits
Abans de començar, assegura't de tenir:
- Un compte a [Render](https://render.com/)
- Una aplicació web preparada per al desplegament (pot ser una aplicació Node.js, Python, Ruby, etc.)
- Un repositori de codi font (GitHub, GitLab, etc.)

### Pas 1: Crear un nou servei web o estàtic
1. Inicia sessió al teu compte de Render.
2. Fes clic a "New" i selecciona "Web Service".
3. Connecta el teu compte de GitHub o GitLab i autoritza Render a accedir als teus repositoris.
4. Selecciona el repositori que conté la teva aplicació web.

### Pas 2: Configurar el servei
1. Assigna un nom al teu servei.
2. Selecciona la branca del repositori que vols desplegar (normalment `main` o `master`).
3. Configura els paràmetres de desplegament, com ara el tipus d'entorn (Node, Python, etc.) i la versió.
4. Defineix les variables d'entorn necessàries per a la teva aplicació.

### Pas 3: Desplegar l'aplicació
1. Fes clic a "Create Web Service".
2. Render començarà a construir i desplegar la teva aplicació automàticament.
3. Pots veure els logs de construcció i desplegament en temps real a la pàgina del servei.

## Pas 4: Verificar el desplegament
1. Un cop finalitzat el desplegament, Render proporcionarà una URL per accedir a la teva aplicació.
2. Obre la URL en el teu navegador per verificar que l'aplicació s'ha desplegat correctament.





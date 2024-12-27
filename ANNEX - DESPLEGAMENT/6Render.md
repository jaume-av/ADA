# Tutorial de Desplegament en Render

## Introducció
Render és una plataforma de desplegament que permet als desenvolupadors desplegar aplicacions web, serveis estàtics, bases de dades i més, de manera fàcil i eficient. En aquest tutorial, aprendrem com desplegar una aplicació web en Render.

## Prerequisits
Abans de començar, assegura't de tenir:
- Un compte a [Render](https://render.com/)
- Una aplicació web preparada per al desplegament (pot ser una aplicació Node.js, Python, Ruby, etc.)
- Un repositori de codi font (GitHub, GitLab, etc.)

## Pas 1: Crear un nou servei web
1. Inicia sessió al teu compte de Render.
2. Fes clic a "New" i selecciona "Web Service".
3. Connecta el teu compte de GitHub o GitLab i autoritza Render a accedir als teus repositoris.
4. Selecciona el repositori que conté la teva aplicació web.

## Pas 2: Configurar el servei
1. Assigna un nom al teu servei.
2. Selecciona la branca del repositori que vols desplegar (normalment `main` o `master`).
3. Configura els paràmetres de desplegament, com ara el tipus d'entorn (Node, Python, etc.) i la versió.
4. Defineix les variables d'entorn necessàries per a la teva aplicació.

## Pas 3: Desplegar l'aplicació
1. Fes clic a "Create Web Service".
2. Render començarà a construir i desplegar la teva aplicació automàticament.
3. Pots veure els logs de construcció i desplegament en temps real a la pàgina del servei.

## Pas 4: Verificar el desplegament
1. Un cop finalitzat el desplegament, Render proporcionarà una URL per accedir a la teva aplicació.
2. Obre la URL en el teu navegador per verificar que l'aplicació s'ha desplegat correctament.



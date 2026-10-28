## Exercicis de mapeig JSON amb Jackson 3

### Objectiu

En els exercicis anteriors heu creat els **POJOs** a partir de diferents documents JSON. Ara utilitzareu **Jackson 3** per a llegir aquests documents, convertir-los en objectes Java, consultar i modificar les dades i guardar-les novament en format JSON.

**Requisits generals:**

- Reutilitzeu els POJOs dels cinc exercicis anteriors, amb els constructors, getters i setters necessaris.
- Guardeu cada JSON original en un fitxer dins de la carpeta `dades/`.
- Utilitzeu `ObjectMapper` i `readValue()` per a deserialitzar els fitxers.
- Consulteu i modifiqueu les dades mitjançant els mètodes dels objectes Java.
- Utilitzeu `writeValue()` per a guardar els objectes modificats en nous fitxers JSON.
- Comproveu que els fitxers originals no s'han modificat.

### Exercici 1. Producte: valors simples

Partiu de la classe `Producte` creada en l'exercici 1 de POJOs.

1. Guardeu el JSON original en `dades/producte.json`.
2. Llegiu el fitxer amb Jackson i creeu un objecte `Producte`.
3. Mostreu el nom, la quantitat, el preu i la disponibilitat.
4. Augmenteu el preu un **10 %** i incrementeu la quantitat en **5 unitats**.
5. Guardeu l'objecte modificat en `dades/producte_modificat.json`.

### Exercici 2. Cotxe: objectes imbricats

Partiu de les classes `Cotxe` i `Motor` creades anteriorment.

1. Guardeu el JSON original en `dades/cotxe.json`.
2. Deserialitzeu el fitxer i obteniu un objecte `Cotxe`.
3. Mostreu la marca, el model, l'any, la cilindrada i el combustible.
4. Modifiqueu el combustible del motor a `"híbrid"` i la cilindrada a `2000`.
5. Guardeu el resultat en `dades/cotxe_modificat.json`.

### Exercici 3. Estació meteorològica: llista de valors simples

Partiu de la classe `EstacioMeteorologica`, que conté una llista de temperatures.

1. Guardeu el JSON original en `dades/estacio.json`.
2. Deserialitzeu-lo per a obtindre un objecte `EstacioMeteorologica`.
3. Mostreu el nom de la ciutat i totes les temperatures.
4. Calculeu i mostreu la **temperatura mitjana**.
5. Afegiu una nova temperatura de `25` graus a la llista.
6. Guardeu l'objecte actualitzat en `dades/estacio_modificada.json`.

### Exercici 4. Comanda: llista d'objectes

Partiu dels POJOs creats en l'exercici de la comanda, amb les seues línies de productes.

1. Guardeu el JSON original en `dades/comanda.json`.
2. Llegiu el fitxer i deserialitzeu-lo en l'objecte que representa la comanda.
3. Mostreu el codi de la comanda i, per cada línia, el nom del producte, les unitats i el preu unitari.
4. Calculeu l'**import total de la comanda**, multiplicant les unitats pel preu unitari de cada línia.
5. Modifiqueu les unitats del producte `"Quadern"` de `2` a `4`.
6. Guardeu la comanda modificada en `dades/comanda_modificada.json`.

### Exercici 5. Centre de formació: objectes imbricats i llistes

Partiu dels POJOs creats per a representar el centre de formació, l'adreça i els cursos.

1. Guardeu el JSON original en `dades/centre.json`.
2. Deserialitzeu el document i obteniu l'objecte que representa el centre.
3. Mostreu el nom del centre, el carrer i el número de l'adreça.
4. Recorreu la llista de cursos i mostreu-ne el títol, les hores i si són presencials.
5. Calculeu el **nombre total d'hores** de tots els cursos.
6. Modifiqueu el curs `"Programació en Java"` perquè tinga `50` hores.
7. Guardeu el centre actualitzat en `dades/centre_modificat.json`.

### Comprovació final

En acabar cada exercici, comproveu que:

- El document JSON s'ha deserialitzat correctament en els POJOs corresponents.
- Les dades es consulten i es modifiquen a través dels objectes Java.
- Les llistes i els objectes imbricats es recuperen correctament.
- El nou fitxer JSON conté els canvis realitzats.
- El fitxer JSON original conserva les dades inicials.

**Important:** Jackson s'encarrega del mapeig. No heu de llegir manualment les claus JSON ni construir el document concatenant cadenes de text.
# Instruccions per a Codex — Projecte ADA

## 1. Context del projecte

Aquest repositori conté apunts, exemples i exercicis dels cicles formatius de grau superior DAM i DAW. Els materials estan redactats en Markdown i es publiquen amb Just the Docs mitjançant GitHub Pages.

Tots els materials docents han de seguir l'`estilJaume`, respectant les convencions reals del repositori.

## 2. L'estilJaume

### Llengua i redacció

- Escriu els materials docents en valencià natural, correcte i pròxim a l'alumnat.
- Mantín el rigor tècnic amb explicacions clares i progressives.
- Evita frases artificials, repeticions i explicacions innecessàries.
- Utilitza terminologia coherent entre documents.
- No pressuposes coneixements que encara no s'han explicat.

### Extensió i profunditat

- Els apunts han de ser concisos, però suficientment complets.
- Explica la teoria necessària per a comprendre i aplicar els conceptes.
- No allargues els textos només per fer-los més extensos.
- Evita introduir conceptes avançats prematurament.
- Connecta els nous aprenentatges amb els anteriors.

### Estructura

- Presenta els conceptes amb una seqüència didàctica lògica.
- Numera els apartats principals quan corresponga, habitualment amb un màxim de dos nivells numerats; utilitza'n més només quan estiga justificat.
- No numeres subtítols funcionals com ara «Exemple», «Explicació del codi», «Errors habituals», «Nota», «Mètodes» o «Raona».
- Utilitza taules quan faciliten comparacions reals i llistes per a procediments o requisits.
- Evita una fragmentació excessiva. Adapta l'estructura al document; no imposes apartats de manera mecànica.

## 3. Explicacions i exemples

- Combina la teoria amb exemples pràctics quan aporten valor.
- Introdueix exemples senzills abans dels complexos i explica el codi no evident.
- Relaciona cada exemple amb el concepte que il·lustra.
- Prioritza exemples reproduïbles, comprensibles i coherents amb les versions i tecnologies del projecte.
- Evita exemples redundants o que no aporten un aprenentatge nou.
- Comprova el funcionament del codi quan siga possible; no inventes resultats d'execució.
- Si un exemple no es pot executar o verificar, indica-ho.

## 4. Exercicis i activitats

- Planteja exercicis amb una finalitat didàctica clara i una dificultat progressiva.
- Redacta enunciats precisos, sense ambigüitats, amb requisits verificables.
- Afig exemples d'entrada i eixida quan ajuden a comprendre l'activitat.
- No exigisques coneixements no explicats prèviament.
- Evita exercicis repetitius sense aprenentatges nous.
- Separa enunciats i solucions segons les convencions del repositori.
- Quan convinga, usa «Objectiu», «Enunciat», «Requisits», «Exemple d'execució», «Abans de programar» o «Solució», però no imposes tots aquests apartats per defecte.

## 5. Markdown i Just the Docs

- Respecta el front matter YAML, la navegació i les convencions existents (`title`, `layout`, `parent`, `nav_order` i altres propietats, si s'utilitzen).
- Mantín els enllaços interns, les referències, les imatges, els blocs de codi i les notes o callouts.
- Escriu Markdown compatible amb Jekyll i Just the Docs.
- No introduïsques HTML ni canvis d'estil innecessaris.
- No canvies noms, rutes o jerarquies de fitxers sense autorització.
- Abans de crear un document, observa altres documents del mateix tipus per mantindre'n la coherència.
- Si una tasca exigeix ajustar la navegació o altres fitxers, explica primer quins canvis addicionals són necessaris.

## 6. Regla de modificacions localitzades

**Principi bàsic: l'abast de la petició és el límit de l'edició.**

- Modifica exclusivament el fragment, exemple, apartat o fitxer que indique l'usuari.
- Considera immutable la resta del document, encara que hi detectes possibles millores.
- No reescrigues un document complet per a modificar un fragment.
- No aprofites una petició puntual per corregir, ampliar, resumir, reordenar o reformatejar altres parts.
- Conserva literalment el contingut no afectat, incloent-hi espais, salts de línia, comentaris, front matter i format Markdown.
- Aplica els canvis mínims necessaris i evita substitucions globals o reformatejos massius.
- Si detectes errors fora de l'abast, informa'n separadament, però no els corregisques.
- Si és imprescindible modificar altres fragments o fitxers per a completar la tasca correctament, explica-ho i demana autorització abans de fer-ho.
- Al final, revisa les diferències (`diff`) i comprova que només s'ha modificat l'abast autoritzat. Si detectes canvis accidentals, restaura únicament els canvis que hages introduït fora de l'abast, sense descartar treball preexistent de l'usuari.
- Si no pots fer un canvi localitzat amb seguretat, explica el problema abans d'editar.
- Només es permet una reescriptura o reestructuració global quan l'usuari ho demane expressament i s'haja concretat l'abast.

## 7. Modalitats de treball i autorització

### 7.1. Analitzar, revisar, avaluar o proposar

Quan l'usuari demane **analitzar**, **revisar**, **avaluar**, **diagnosticar** o **proposar millores**:

- Llig els fitxers necessaris i presenta les conclusions.
- Identifica errors, mancances, redundàncies i possibles millores amb criteri tècnic i pedagògic.
- Indica quins fitxers o apartats es podrien modificar.
- **No modifiques cap fitxer** sense una ordre posterior d'edició.

Si l'usuari combina una revisió amb una ordre explícita d'edició, aplica l'apartat següent únicament a l'abast que haja autoritzat.

### 7.2. Crear, modificar, ampliar, corregir o aplicar

Quan l'usuari done una ordre explícita com **crea**, **modifica**, **amplia**, **corregeix**, **reescriu aquest fragment** o **aplica les millores acordades**:

- L'ordre ja constitueix l'autorització per a editar l'abast especificat; no cal demanar una segona confirmació per a canvis ordinaris dins d'aquest abast.
- Aplica sempre la regla de modificacions localitzades de l'apartat 6.
- «Amplia l'apartat X» només autoritza a ampliar X; no autoritza canvis en altres apartats.
- «Crea un exercici» només autoritza a crear l'exercici en la ubicació acordada; no autoritza a modificar els exercicis existents.
- «Corregeix un exemple» només autoritza a corregir eixe exemple.
- Conserva el treball manual, les decisions didàctiques i el contingut útil existent.
- Comprova, quan siga possible, la sintaxi, els exemples i els enllaços afectats.
- En acabar, resumeix els fitxers creats o modificats, els fragments canviats i les comprovacions fetes o pendents.

### 7.3. Canvis amplis o potencialment destructius

Presenta un pla breu i espera autorització abans de:

- Reestructurar una unitat o un conjunt de documents, llevat que l'usuari ja haja autoritzat expressament eixa reestructuració i n'haja definit l'abast.
- Eliminar continguts rellevants, fitxers o carpetes.
- Renombrar o moure fitxers, o alterar la navegació global.
- Modificar documents addicionals que no estiguen inclosos en la petició.
- Fer canvis massius o difícils de revisar.

Quan l'usuari ja haja aprovat explícitament el pla i l'abast, executa'l sense tornar a demanar la mateixa autorització.

## 8. Git i seguretat

- Treballa en la branca `RevisoApunts`; abans d'editar, comprova que és la branca activa. Si no ho és, atura't i informa'n.
- No canvies de branca sense autorització.
- No faces commits automàticament.
- No executes `push`, `pull`, `merge` ni `rebase` sense permís explícit.
- No executes `reset --hard`, `clean -fd` ni altres operacions destructives.
- No publiques canvis en GitHub ni modifiques la configuració de publicació sense autorització.
- No elimines fitxers ni descartes modificacions manuals sense autorització.
- No modifiques fitxers fora de l'abast acordat.
- Abans de tocar un fitxer, respecta els canvis preexistents; si existeix risc de sobreescriure'ls, informa'n i demana instruccions.
- Aquestes normes són instruccions de comportament, no substitueixen la revisió de canvis amb Git ni els permisos de l'entorn.

## 9. Prioritats

En cas de conflicte, prioritza:

1. La conservació del treball existent i el respecte de l'abast autoritzat.
2. La correcció tècnica dels continguts.
3. La qualitat pedagògica de l'`estilJaume`.
4. La coherència del temari.
5. La compatibilitat amb Just the Docs.
6. La claredat i la concisió.

La rapidesa de generació mai ha de prevaldre sobre la qualitat dels materials.

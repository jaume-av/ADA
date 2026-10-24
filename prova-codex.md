# 1. Introducció a XML

XML és un format de text que permet representar dades de manera estructurada. Utilitza etiquetes per identificar cada dada, com ara el nom d’un videojoc, la plataforma o el preu. Aquests noms els defineix qui crea el document segons la informació que necessita representar.

Un document XML té un únic element arrel que conté la resta d’elements. Els elements poden incloure text o altres elements, formant una estructura jeràrquica. En l’exemple següent, `videojocs` agrupa els videojocs i cada `videojoc` conté les seues dades.

# 2. Exemple XML

```xml
<?xml version="1.0" encoding="UTF-8"?>
<videojocs>
    <videojoc id="V001">
        <nom>Stardew Valley</nom>
        <plataforma>PC</plataforma>
        <preu>13.99</preu>
    </videojoc>
    <videojoc id="V002">
        <nom>Celeste</nom>
        <plataforma>Nintendo Switch</plataforma>
        <preu>19.99</preu>
    </videojoc>
</videojocs>
```

Els preus són dades fictícies per a l’exemple, expressades en euros.

# 3. Validació XML

Un XML està **ben format** quan compleix les regles de sintaxi: té un únic element arrel, les etiquetes es tanquen correctament i els elements estan ben imbricats. A més, XML diferencia majúscules i minúscules: `<nom>` s’ha de tancar amb `</nom>`, no amb `</Nom>`.

La **validació** comprova, a més, que el document compleix unes regles definides en una DTD o un esquema XSD. Per exemple, un XSD pot exigir que cada videojoc tinga nom, plataforma i preu, i que el preu siga un valor decimal. Sense aquestes regles podem comprovar que l’XML està ben format, però no afirmar que s’ha validat contra un esquema.

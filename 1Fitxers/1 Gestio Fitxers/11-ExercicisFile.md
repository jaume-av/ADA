---
title:  Exercicis amb la Classe File
parent: Classe File
grand_parent: 1.- Gestió del Sistema de Fitxers
has_children: true
layout: default
nav_order: 10
---


# Exercicis amb la Classe `File`



## Exercici 1 – Mostrar informació de fitxers

Implementa un programa que demane a l’usuari introduir per teclat una ruta del sistema d’arxius (per exemple, `C:/Windows` o `Documents`) i mostre informació sobre esta ruta. El procés es repetirà una vegada i una altra fins que l’usuari introduïsca una ruta buida (tecla intro). Hauràs de manejar les possibles excepcions.

Crea la funció:

```java
void mostraRuta(File ruta)
```

Esta funció haurà de fer el següent:

* Si la ruta no existix, llançar una excepció `FileNotFoundException`.
* Si és un arxiu, mostrar el seu nom per pantalla.
* Si és un directori, mostrar la llista d’elements que conté. Davant de cada nom, afegeix l’etiqueta `[D]` si és directori i `[A]` si és arxiu.

### Pistes i restriccions

* **Utilitza de la classe `File` els següents mètodes:**

  * `exists()`
  * `isFile()`
  * `isDirectory()`
  * `getName()`
  * `listFiles()`

* **Per a les excepcions has d’utilitzar:**

  * `try-catch` amb `FileNotFoundException`.
  * `throws` per llançar l’excepció pròpia des de la funció.

---


## Exercici 2 – Mostrar informació de fitxers (v2)


Partint d’una còpia del programa de l’Exercici 1, modifica la funció:

```java
void mostraRuta(File f, boolean mesInfo)
```

* Si la ruta **no existeix**, llança una **`FileNotFoundException`**.
* Si és un **arxiu**, mostra el **nom**.
* Si és un **directori**, mostra la **llista de continguts** en **ordre alfabètic**:

  * Primer **els directoris** i després **els arxius**.
  * En els directoris, etiqueta `[*]`; en els arxius, etiqueta `[A]`.
* Si el paràmetre **`mesInfo` és `true`**, mostra al costat de cada element la **mida en bytes** i la **data de l’última modificació** (utilitza `new Date(lastModified())`).
* Si **`mesInfo` és `false`**, mostra només les etiquetes i els noms, com a l’Exercici 1.

### Pistes i restriccions

* **Classe `File`:** `getName()`, `length()`, `lastModified()`, `isDirectory()`, `isFile()`, `listFiles()`.
* **Ordenació alfabètica:** utilitza **`Arrays.sort(llistat)`** sobre l’array de `File`.
* **Data d’última modificació:** utilitza **`new Date(f.lastModified())`**.
* **Excepcions:** utilitza un **`try-catch`** amb `FileNotFoundException` en el `main`, i **`throws FileNotFoundException`** en la signatura de `mostraRuta`.

---





## Exercici 3 – Reanomenant directoris i fitxers

Per als exercicis 3, 4 i 5, descarrega l’estructura d’arxius `Documentos.zip` i descomprím-la en la carpeta on estiguen els teus projectes d’IntelliJ.

[Descarregar Documentos.zip]({{ '/1Fitxers/1%20Gestio%20Fitxers/Fitxers/Documentos.zip' | relative_url }}){: .btn .btn-outline }


---


Implementa un programa que realitze les següents tasques:

1. **Reanomena** les carpetes:

   * `Documentos` → `DOCS`
   * `DOCS/Fotografias` → `DOCS/FOTOS`
   * `DOCS/Libros` → `DOCS/LECTURES`

2. **Abans** de modificar els noms d’arxiu, **imprimeix** per pantalla el **llistat d’arxius** de `DOCS/LECTURES` **ordenat alfabèticament** (només els arxius, un per línia).

3. **Lleva l’extensió** de **tots els arxius** dins de `DOCS/LECTURES` reanomenant-los (per exemple, `astronauta.jpg` → `astronauta`).

4. **Després**, torna a **imprimir** el **llistat d’arxius** de `DOCS/LECTURES` **ordenat alfabèticament** (sense extensions).

### Tasca a realitzar



1.- A partir de la plantilla `Exercici3.java` i l’estructura d’arxius `Documentos.zip`.

2.- Obri’ls i prepara’ls en IntelliJ.

3.- Completa la funció `main()` i crea les funcions `imprimirLlistaArxius()` i `llevarExtensionsArxius()`, tal com s’indica en els comentaris.


### Plantilla inicial

```java
import java.io.File;
import java.util.Arrays;


public class Exercici3 {
	

    /* **************************
     * Reanomenem les carpetes  *
     * **************************/

// Creem dos objectes de tipus File on assignem la ruta d'origen i la ruta de destí (PER A LA CARPETA DOCUMENTOS)       
	
	public static void main (String args[]) {
		
		File docOrigen = new File("Documentos");
		File docDesti =new File("DOCS");

// Comprovem que la carpeta DOCUMENTOS està creada; simplement és per acotar errors
		
		if(!docOrigen.exists())
			System.out.println("COMPROVA QUE LA CARPETA DOCUMENTOS ESTÀ CREADA I LA RUTA ÉS CORRECTA");
		
// Reanomenem la carpeta Documentos



		
// Creem dos objectes de tipus File on assignem la ruta d'origen i la ruta de destí (PER A LA CARPETA FOTOGRAFIAS) i canviem el nom




		
// Creem dos objectes de tipus File on assignem la ruta d'origen i la ruta de destí (PER A LA CARPETA LIBROS) i canviem el nom
		

		

		/* **********************************************************
         *  Llevem les extensions en LECTURES (REANOMENANT ELS ARXIUS)
           ********************************************************** */
        
  // ABANS d'eliminar les extensions, imprimim la llista d'arxius ordenada cridant a la funció imprimirLlistaArxius()


		
		

// Reanomenem els arxius, llevant les extensions cridant a la funció llevarExtensionsArxius()


		
		

// DESPRÉS d'eliminar les extensions, imprimim de nou la llista d'arxius ordenada cridant a la funció imprimirLlistaArxius()


	
		
} // del main()
	

	
/*
 * IMPRIMIR LLISTA D'ARXIUS
 * 
 */
	
	
/*
 * LLEVAR EXTENSIONS D'ARXIUS
 * 	
 */
	
	



} // de la classe
```



### Eixida esperada (exemple)

```
Llistat d'arxius de DOCS\LECTURES abans de llevar les extensions:
coplas_manrique.txt
fuenteovejuna_lopevega.txt
lazarillo.txt
quijote_cervantes.txt
vida_unamuno.txt

Llistat d'arxius de DOCS\LECTURES després de llevar les extensions:
coplas_manrique
fuenteovejuna_lopevega
lazarillo
quijote_cervantes
vida_unamuno
```

---

### Pistes i restriccions

* **Classe `File`**:

  * `renameTo(File dest)` – per reanomenar/moure fitxers o directoris.
  * `listFiles()` – per obtindre el contingut d’un directori.
  * `isFile()` – per discriminar arxius en el llistat.
  * `getName()` i `getParent()` – per treballar amb noms i rutes.

* **Classe `Arrays`**:

  * `Arrays.sort(File[])` – per **ordenar alfabèticament** la llista d’arxius/directoris (ordenació natural per path).

* **Per llevar l’extensió**:

  * Utilitza `String.split("\\.")` sobre `getName()` i pren la **primera part** com a nom base.
  * Crea el destí amb `new File(f.getParent() + "/" + nomSenseExtensio)` i crida `renameTo(...)`.

* **Organització del codi**:

  * Crea i utilitza **dues funcions**:

    * `imprimirLlistaArxius(File ruta)` – llista els **arxius** ordenats en eixa ruta.
    * `llevarExtensionsArxius(File ruta)` – reanomena **tots els arxius** de la ruta llevant l’extensió.
---


## Exercici 4 – Creant i movent carpetes

Implementa un programa que faça les següents tasques:

* Dins de la carpeta `Documentos`, crea dues noves carpetes: `Les Meues Coses` i `Alfabet`.
* Mou les carpetes `Fotografias` i `Libros` dins de `Les Meues Coses`.
* Crea dins de `Alfabet` una carpeta per a cada lletra de l’alfabet en majúscules (`A`, `B`, `C`, ..., `Z`). Per a això pots utilitzar els codis numèrics ASCII.

---

### Tasca a realitzar

1. A partir de la plantilla `Exercici4.java` i l’estructura d’arxius `Documentos.zip`.
2. Obri’ls i prepara’ls en IntelliJ.
3. Completa la funció `main()` tal com s’indica en els comentaris.

---

### Pistes i restriccions

* **Classe `File`:**

  * `mkdir()` → per crear carpetes noves.
  * `renameTo(File dest)` → per moure o reanomenar carpetes.
  * `listFiles()` → per llistar el contingut d’una carpeta.
  * `getName()` → per obtindre el nom de cada carpeta.

* **Per crear les carpetes de l’alfabet:**

  * Utilitza un bucle `for` amb els codis ASCII de la `A` (65) fins a la `Z` (90).
  * Construeix la ruta de cada lletra amb `new File(...)` i crida `mkdir()`.

---

### Eixida esperada (exemple)

```
S'ha creat la carpeta 'Documentos/Les Meues Coses'?? true
S'ha creat la carpeta 'Documentos/Alfabet'?? true
S'ha mogut la carpeta 'Documentos/Fotografias' a 'Documentos/Les Meues Coses/Fotografias'? true
S'ha mogut la carpeta 'Documentos/Libros' a 'Documentos/Les Meues Coses/Libros'? true

El contingut de la carpeta 'Alfabet' és:
A B C D E F G H I J K L M N O P Q R S T U V W X Y Z
```


### Plantilla inicial

```java
package exercici4;

import java.io.File;

public class Exercici4 {

    public static void main(String[] args) {

        // Creem instàncies de la classe File amb les rutes relatives de les carpetes a crear
        File lesMeuesCoses = new File("Documentos/Les Meues Coses");
        File alfabet = new File("Documentos/Alfabet");

        // Crea les carpetes amb mkdir() i mostra si s'han creat correctament
        

        // Anem a moure les carpetes. Primer, crea objectes File amb les rutes origen i destí
        File fotoOrigen = new File("Documentos/Fotografias");
        File fotoDesti = new File("Documentos/Les Meues Coses/Fotografias");

        File llibreOrigen = new File("Documentos/Libros");
        File llibreDesti = new File("Documentos/Les Meues Coses/Libros");

        // Mou la carpeta Fotografias dins de Les Meues Coses i mostra si ha funcionat
        

        // Mou la carpeta Libros dins de Les Meues Coses i mostra si ha funcionat
        

        // Crea dins de 'Alfabet' una carpeta per a cada lletra (A..Z) utilitzant els codis ASCII
        

        // Llista el contingut final de 'Alfabet' amb listFiles() i mostra per pantalla els noms de les carpetes creades
        

    } // Del main()
} // De la classe
```

---


## Exercici 5 – Esborrant arxius i carpetes

Implementa un programa que faça les següents tasques:

* Crea una funció anomenada `esborrarTot(File f)` que elimine tots els arxius i carpetes d’una ruta.
* Si la ruta no existeix, ha de llançar una excepció `FileNotFoundException`.
* Si és un **arxiu**, l’ha d’esborrar directament.
* Si és una **carpeta**, primer ha d’eliminar tots els seus arxius i després esborrar la carpeta.
* Des del `main()`, prova la funció esborrant, en este ordre, les carpetes:

  * `Documentos/Fotografias`
  * `Documentos/Libros`
  * `Documentos`

---

### Tasca a realitzar

1. A partir de la plantilla `Exercici5.java` i l’estructura d’arxius `Documentos.zip`.
2. Obri’ls i prepara’ls en IntelliJ.
3. Completa la funció `main()` i crea la funció `esborrarTot()` seguint les instruccions dels comentaris.

---

### Pistes i restriccions

* **Classe `File`:**

  * `exists()` → comprova si la ruta existix.
  * `isFile()` → comprova si és un arxiu.
  * `isDirectory()` → comprova si és una carpeta.
  * `listFiles()` → retorna els fitxers continguts dins d’una carpeta.
  * `delete()` → elimina un arxiu o una carpeta (només si està buida).

* **Excepcions:**

  * Utilitza `FileNotFoundException` per indicar que la ruta no existix.
  * Gestiona l’excepció amb `try-catch` al `main()`.

* **Recorregut:**

  * Per a les carpetes, recorre primer els arxius interns amb un bucle `for` sobre `listFiles()`.
  * Després crida `delete()` sobre la carpeta mateixa.

---

### Eixida esperada (exemple)

En cas que la ruta no existisca:

```
java.io.FileNotFoundException: La ruta introduïda no existeix
```

Si tot funciona correctament:

```
La carpeta 'Fotografias' ha segut esborrada
La carpeta 'Libros' ha segut esborrada
La carpeta 'Documentos' ha segut esborrada
```

---

### Plantilla inicial

```java
package exercici5;

import java.io.File;
import java.io.FileNotFoundException;

public class Exercici5 {
    public static void main(String[] args) {

        // Instanciem la classe File amb les rutes relatives de les carpetes que volem esborrar
        File fotografies = new File("Documentos/Fotografias");
        File llibres = new File("Documentos/Libros");
        File documents = new File("Documentos");

        boolean resultat = false;

        try {
            // Esborrem la carpeta 'Fotografias' i tot el seu contingut
            resultat = esborrarTot(fotografies);
            if (resultat) {
                System.out.println("La carpeta 'Fotografias' ha segut esborrada");
            }

            // Esborrem la carpeta 'Libros' i tot el seu contingut
            resultat = esborrarTot(llibres);
            if (resultat) {
                System.out.println("La carpeta 'Libros' ha segut esborrada");
            }

            // Esborrem la carpeta 'Documentos' i tot el seu contingut
            resultat = esborrarTot(documents);
            if (resultat) {
                System.out.println("La carpeta 'Documentos' ha segut esborrada");
            }

        } catch (FileNotFoundException e) {
            System.out.println(e);
        }
    } // Del main()


   /* Crea una funció anomenada esborrarTot() que elimine tots els arxius i carpetes d'una ruta,
    *
    * Si no existeix la ruta mostra una excepció.
    * Si és un arxiu l'esborrem.
    * Si és una carpeta, primer eliminem tots els seus arxius, i després, esborrem la carpeta.
    *
    */


} // de la classe
```

## ANNEX -  El mètode `split()` en Java

El mètode **`split()`** de la classe `String` permet **dividir una cadena de text en parts** a partir d’un delimitador.

* Retorna un **array (`String[]`)** amb les parts resultants.
* El **delimitador** es defineix amb una **expressió regular (regex)**.
* És molt útil per separar dades dins d’una cadena, per exemple noms de fitxers, extensions, paraules en una frase, etc.

---

## Exemple bàsic

```java
public class MetodeSplit {

    public static void main(String[] args) {

        String s = "Nom.ext";

        // Dividim la cadena en parts, separant pel punt "."
        String[] cadenaPartida = s.split("\\.");

        System.out.println(cadenaPartida[0]); // "Nom"
        System.out.println(cadenaPartida[1]); // "ext"
    }
}
```

A l'exemple anterior:

* Declarem `s = "Nom.ext"`.
* Apliquem `split("\\.")`.

  * El paràmetre és `"\\."` i **no només `"."`**, perquè en expressions regulars el punt té un significat especial (significa “qualsevol caràcter”).
  * Amb `\\.` li diem a Java: “busca exactament un punt literal”.
* El resultat és un array amb dos elements:

  * `cadenaPartida[0] = "Nom"`
  * `cadenaPartida[1] = "ext"`


>Important!! El separador o delimitador és una **expressió regular (regex)**. I no apareix mai a l'array resultant.

## Exemple tallant per una lletra ("e")

```java
public class MetodeSplit {

    public static void main(String[] args) {

        String s = "Nom.ext";

        // Dividim la cadena en parts, separant per la lletra "e"
        String[] cadenaPartida = s.split("e");

        System.out.println(cadenaPartida[0]); // "Nom."
        System.out.println(cadenaPartida[1]); // "xt"
    }
}
```

A l'exemple anterior:

* Declarem `s = "Nom.ext"`.
* Apliquem `split("e")`.
* La cadena es parteix cada vegada que apareix la lletra **e**.
* El resultat és un array amb dos elements:

  * `cadenaPartida[0] = "Nom."`
  * `cadenaPartida[1] = "xt"`

---

### En resum

* **Sintaxi:**

  ```java
  String[] parts = text.split(regex);
  ```
* **Paràmetre:**

  * `regex` → expressió regular que indica per on es fa la separació.
* **Resultat:**

  * Retorna un `String[]` amb les subcadenes.

---

## Casos habituals

1. **Separar paraules en una frase per espais**

   ```java
   "Hola món Java".split(" ") 
   // → ["Hola", "món", "Java"]
   ```

2. **Separar elements per comes**

   ```java
   "roig,verd,blau".split(",") 
   // → ["roig", "verd", "blau"]
   ```

3. **Separar un fitxer i la seua extensió**

   ```java
   "document.pdf".split("\\.") 
   // → ["document", "pdf"]
   ```


4. **Separar per múltiples delimitadors**

   ```java
   "roig;verd,blau".split("[,;]") 
   // → ["roig", "verd", "blau"]
   ```


---



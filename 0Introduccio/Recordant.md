---
title: Recordant
layout: default
parent: Descripció del Mòdul
nav_order: 20
has_children: true
has_toc: true
---


## Recordant conceptes bàsics de Java
*(Arrays i funcions · Cadenes · Palíndroms · Classes i col·leccions · Excepcions)*

---

### 1) Arrays i funcions

* Vectors, funcions i menús interactius.

Escriu un programa que gestione un **vector d’enters** amb el següent comportament:

1. El programa demana la **dimensió** del vector.

2. El vector es **plena amb números aleatoris** entre **-100 i 100**.

3. Demana un **nombre enter X**.

4. Implementa les funcions:

   * `rellenar(int[] v)`: ompli el vector amb valors aleatoris.
   * `media(int[] v) -> double`: calcula i retorna la mitjana.
   * `existe(int[] v, int x) -> boolean`: diu si X està dins del vector.
   * `mayores(int[] v, int x) -> int`: diu quants valors són majors o iguals a X.

5. Mostra un **menú**:

```
*************** MENÚ D’OPCIONS *****************
1) Omplir el vector
2) Calcular la mitjana
3) Comprovar si existeix el número X
4) Comptar majors o iguals que X
5) Eixir
***********************************************
```

El programa cridarà les funcions quan calga i mostrarà resultats per pantalla.

---

### 2) Cadenes de caràcters (classe String)

* Recorregut de cadenes i recompte de vocals.

Crea un programa que llija una **frase** pel teclat i mostre quantes vocals de cada tipus hi ha. No es diferenciaran majúscules i minúscules.

**Exemple amb la frase “Sempre plou quan no hi ha escola”:**

```
El número de ‘a’ és: 3
El número de ‘e’ és: 3
El número de ‘i’ és: 1
El número de ‘o’ és: 3
El número de ‘u’ és: 2
```

---

### 3) Palíndroms

**Objectiu.** Treballar neteja de text i comparació de cadenes.

**Enunciat.** Escriu un programa que llija una **frase** i indique si és **palíndrom** o no.

* S’ignoraran **espais**.
* No es diferenciaran **majúscules i minúscules**.
* L’usuari només introduirà **lletres i espais** (sense signes).

**Exemples de frases palíndroms:**

* *Yo hago yoga hoy*
* *Ella te da detalle*
* *Lavan esa base naval*
* *Amo la pacifica paloma*

---
### 4) Classes i col·leccions

* Practicar POO, `ArrayList` i diferents formes de recorregut.


Defineix la classe **`Producte`** amb:

* Atributs: `String nom`, `int quantitat`.
* Constructor amb paràmetres.
* Getters i setters.

**Programa principal:**

1. Crea **5 instàncies** de `Producte`.
2. Crea ArrayList de `Producte`.
3. Afig els 5 productes a la llista.
4. Mostra el contingut amb **Iterator**.
5. Elimina **2 elements**.
6. Insereix un **nou producte al mig** de la llista.
7. Mostra el contingut amb **for** clàssic.
8. Mostra el contingut amb **for-each**.
9. Mostra el contingut amb **Iterator**.
10. Elimina tots els valors de l’ArrayList.

---

### 5) – Excepcions

**Enunciat.**

Dona’t el següent codi base:

```java
import java.util.InputMismatchException;
import java.util.Scanner;

public class Ejercicio1 {

    public static void main(String args[]) {
        Scanner teclado = new Scanner(System.in);		
        try {
            System.out.print("Introduce un número positivo\n");
            imprimePositivo(teclado.nextInt());
            System.out.print("Introduce un número negativo\n");
            imprimeNegativo(teclado.nextInt());
        } 
        catch (InputMismatchException e) {
            e.printStackTrace();
        } 
        catch (Exception e) {
            e.printStackTrace();
        }		
    }
    // imprimePositivo()
    // imprimeNegativo()
} // De la clase
```

Es demana:

1. Implementa la funció **`imprimePositivo(int n)`**:

   * Si el número introduït és **positiu**, l’ha de mostrar per pantalla.
   * Si el número és **negatiu**, haurà de **llançar una excepció** i mostrar el missatge:

     ```
     El número debe ser positivo
     ```

2. Implementa la funció **`imprimeNegativo(int n)`**:

   * Si el número introduït és **negatiu**, l’ha de mostrar per pantalla.
   * Si el número és **positiu**, haurà de **llançar una excepció** i mostrar el missatge:

     ```
     El número debe ser negativo
     ```

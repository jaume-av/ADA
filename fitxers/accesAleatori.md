---
title: accés aleatori
parent: Índex
layout: default
has_children: true
nav_order: 2
---

# Accés aleatori en Java

## Descripció

L'**accés aleatori** en Java fa referència a la capacitat de llegir i escriure en qualsevol part d'un fitxer sense haver de seguir un ordre seqüencial. Això és útil quan es treballa amb fitxers grans i només es necessita accedir a determinades posicions de dades dins del fitxer sense llegir-lo completament des del principi.

En Java, l'accés aleatori als fitxers es gestiona mitjançant la classe **`RandomAccessFile`**, que permet accedir a qualsevol posició dins del fitxer i llegir o escriure en aquestes posicions.

## Tipus d'accés a fitxers

- **Accés seqüencial**: Llegeix o escriu dades seqüencialment, començant des del principi del fitxer fins al final.
- **Accés aleatori**: Permet saltar a una posició específica del fitxer per llegir o escriure dades, sense necessitat de llegir seqüencialment fins a aquesta posició.

L'accés aleatori és especialment útil en aplicacions com bases de dades, on sovint és necessari accedir a registres individuals sense processar tot el fitxer.

## Classe principal: `RandomAccessFile`

La classe **`RandomAccessFile`** és la que permet realitzar operacions d'accés aleatori en fitxers en Java. Aquesta classe permet llegir i escriure dades en un fitxer en qualsevol moment i en qualsevol ubicació, com si es tractés d'un array gran de bytes.

### Creació d'un `RandomAccessFile`

Un objecte `RandomAccessFile` es pot obrir en mode de lectura (`"r"`) o en mode de lectura i escriptura (`"rw"`).

- **Lectura**: `RandomAccessFile file = new RandomAccessFile("fitxer.txt", "r");`
- **Lectura i escriptura**: `RandomAccessFile file = new RandomAccessFile("fitxer.txt", "rw");`

### Mètodes principals

#### Mètodes de lectura:
- **`read()`**: Llegeix un byte de dades.
- **`readInt()`**: Llegeix un enter de 4 bytes.
- **`readDouble()`**: Llegeix un valor de tipus `double` de 8 bytes.
- **`readUTF()`**: Llegeix una cadena de text en format UTF-8.

#### Mètodes d'escriptura:
- **`write()`**: Escriu un byte de dades.
- **`writeInt(int v)`**: Escriu un enter de 4 bytes.
- **`writeDouble(double v)`**: Escriu un valor de tipus `double`.
- **`writeUTF(String s)`**: Escriu una cadena de text en format UTF-8.

#### Mètodes per moure's dins del fitxer:
- **`seek(long pos)`**: Mou el punter a una posició específica dins del fitxer (basada en el nombre de bytes des del començament del fitxer).
- **`getFilePointer()`**: Retorna la posició actual del punter dins del fitxer.

#### Altres mètodes útils:
- **`length()`**: Retorna la longitud del fitxer en bytes.
- **`setLength(long newLength)`**: Estableix la longitud del fitxer. Si el nou valor és més gran que l'actual, s'omple amb zeros.
- **`close()`**: Tanca el fitxer.

### Exemples pràctics

#### Exemple 1: Escriure i llegir en un fitxer utilitzant `RandomAccessFile`

```java
import java.io.IOException;
import java.io.RandomAccessFile;

public class ExempleAccesAleatori {
    public static void main(String[] args) {
        try {
            // Crear un fitxer d'accés aleatori amb lectura i escriptura
            RandomAccessFile fitxer = new RandomAccessFile("dades.bin", "rw");

            // Escriure dades en el fitxer
            fitxer.writeUTF("Benvinguts a Java!");
            fitxer.writeInt(2024);
            fitxer.writeDouble(3.1416);

            // Moure's dins del fitxer a la posició inicial
            fitxer.seek(0);

            // Llegir les dades des del fitxer
            String missatge = fitxer.readUTF();
            int any = fitxer.readInt();
            double valor = fitxer.readDouble();

            // Mostrar les dades llegides
            System.out.println("Missatge: " + missatge);
            System.out.println("Any: " + any);
            System.out.println("Valor: " + valor);

            // Tancar el fitxer
            fitxer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
```

#### Sortida:
```
Missatge: Benvinguts a Java!
Any: 2024
Valor: 3.1416
```

#### Exemple 2: Accedir a posicions específiques en un fitxer

```java
import java.io.IOException;
import java.io.RandomAccessFile;

public class AccesPosicioEspecifica {
    public static void main(String[] args) {
        try {
            // Crear o obrir un fitxer d'accés aleatori amb lectura i escriptura
            RandomAccessFile fitxer = new RandomAccessFile("registre.dat", "rw");

            // Escriure alguns valors en el fitxer
            fitxer.writeInt(100);  // Posició 0
            fitxer.writeInt(200);  // Posició 4
            fitxer.writeInt(300);  // Posició 8

            // Moure's a la posició del segon enter (posició 4)
            fitxer.seek(4);
            
            // Llegir el valor en la posició 4
            int valor = fitxer.readInt();
            System.out.println("Valor a la posició 4: " + valor);

            // Moure's a la posició del tercer enter (posició 8)
            fitxer.seek(8);
            
            // Llegir el valor en la posició 8
            int tercerValor = fitxer.readInt();
            System.out.println("Valor a la posició 8: " + tercerValor);

            // Tancar el fitxer
            fitxer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
```

#### Sortida:
```
Valor a la posició 4: 200
Valor a la posició 8: 300
```

### Consideracions importants
- **Sincronització**: Quan s'utilitza accés aleatori en un entorn multi-fil, cal tenir en compte la sincronització per evitar condicions de carrera.
- **Gestió d'excepcions**: S'ha de gestionar adequadament les excepcions com `IOException` i assegurar-se que es tanquen els fitxers després d'usar-los.
- **Actuacions**: Accedir de manera aleatòria a fitxers grans pot ser molt eficient en comparació amb l'accés seqüencial.

### Avantatges de l'accés aleatori
- Permet **modificar parts d'un fitxer** sense reescriure tot el fitxer.
- És **eficient** per a grans fitxers on només cal llegir o escriure a parts específiques.

### Inconvenients de l'accés aleatori
- Requereix una **bona gestió** del punter dins del fitxer, ja que és fàcil perdre's en fitxers grans.
- Pot ser **complicat** si s'utilitzen formats de fitxer personalitzats.


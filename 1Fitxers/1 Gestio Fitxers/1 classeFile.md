---
title:  Classe File
parent: 1.- Gestió del Sistema de Fitxers
has_children: true
layout: default
nav_order: 10
---


# Classe `File` – Gestió de Fitxers a Java

En aquest document aprendràs a utilitzar la classe **`File`** de Java, que permet gestionar rutes i fitxers de manera independent del sistema operatiu.

## Classe `File` en Java
[Classe File](https://docs.oracle.com/javase/8/docs/api/index.html?java/io/File.html)


La classe **`File`** és una de les més importants per operar amb arxius i directoris en Java. És important compendre que la classe **`File`** **no representa el contingut dels arxius**, sinó la **ruta a l'arxiu o directori**.

### Importació de la classe

Abans d'utilitzar la classe **`File`**, cal importar-la de la llibreria `java.io`:

```java
import java.io.File;
```

### Característiques principals

- **`File`** **no** representa el contingut dels arxius, sinó una **ruta a l'arxiu o directori**.
- Pot representar tant **fitxers** com **directoris**.
- Ofereix **independència del sistema operatiu** en la gestió de rutes i fitxers.
- Quan es crea un objecte **`File`**, aquest estarà **vinculat a la ruta de l'arxiu** durant tota la seva existència.

## Constructors de la classe `File`

Hi ha tres constructors principals per crear un objecte **`File`**:

1. **`File(String directori_i_fitxer)`**: Es passa la ruta completa del fitxer o directori.

   ```java
   File fitxer1 = new File("/home/usuari/Exemples/fitxer1.txt");
   File fitxer2 = new File("C:/Exemples/fitxer2.txt");
   ```

2. **`File(String directori, String fitxer)`**: Es passa per separat el directori i el nom del fitxer.

   ```java
   File fitxer = new File("/home/usuari/Exemples", "fitxer3.txt");
   ```

3. **`File(File directori, String fitxer)`**: El directori és un objecte **`File`** previ.

   ```java
   File directori = new File("/home/usuari/Exemples");
   File fitxer = new File(directori, "fitxer4.txt");
   ```

### Rutes relatives i absolutes

- Les **rutes absolutes** comencen des de l'arrel (`/` en sistemes Unix i `C:` en Windows).
- Les **rutes relatives** comencen des del directori actiu en què s'està executant el programa.

## Funcions de la Classe `File`

La classe **`File`** encapsula una gran varietat de funcionalitats per gestionar fitxers i directoris, des de la creació i eliminació d'arxius fins a la manipulació de rutes i permisos.

### Funcions principals

#### Obtenir el nom o la ruta

- `String getName()`: Retorna el nom del fitxer o directori.
- `String getPath()`: Retorna la ruta relativa.
- `String getAbsolutePath()`: Retorna la ruta absoluta.

#### Accedir al directori pare o subdirectoris

- `String[] list()`: Retorna una llista de noms dels elements continguts en el directori.
- `File[] listFiles()`: Retorna una llista d'objectes **`File`** dels elements continguts en el directori.
- `String getParent()`: Retorna el nom del directori pare.
- `File getParentFile()`: Retorna el directori pare com un objecte **`File`**.

#### Verificar existència i tipus

- `boolean exists()`: Retorna `true` si el fitxer o directori existeix.
- `boolean isDirectory()`: Retorna `true` si és un directori.
- `boolean isFile()`: Retorna `true` si és un fitxer.
- `boolean isHidden()`: Retorna `true` si és un arxiu ocult.
- `long length()`: Retorna la mida del fitxer en bytes.
- `long lastModified()`: Retorna la data de l'última modificació.

#### Crear i eliminar fitxers o directoris

- `boolean delete()`: Esborra l'arxiu o directori.
- `boolean mkdir()`: Crea un nou directori.
- `boolean renameTo(File dest)`: Canvia el nom d'un arxiu o directori.

## Exemple d'ús de la classe `File`

### Llistar el contingut d'un directori

Aquest exemple mostra com llistar tots els fitxers i directoris del directori actual:

```java
import java.io.File;

public class LlistaFitxers {
    public static void main(String[] args) {
        File directoriActual = new File(".");
        String[] contingut = directoriActual.list();
        
        if (contingut != null) {
            for (String fitxer : contingut) {
                System.out.println(fitxer);
            }
        } else {
            System.out.println("El directori no existeix o no es pot llegir.");
        }
    }
}
```

### Obtenir la mida d'un fitxer

```java
import java.io.File;

public class MidaFitxer {
    public static void main(String[] args) {
        File fitxer = new File("exemple.txt");
        
        if (fitxer.exists()) {
            System.out.println("Mida del fitxer: " + fitxer.length() + " bytes");
        } else {
            System.out.println("El fitxer no existeix.");
        }
    }
}
```

## Funcions addicionals

### Gestió de permisos

La classe **`File`** ofereix mètodes per gestionar els permisos de lectura, escriptura i execució:

- **`boolean canRead()`**: Retorna `true` si el fitxer és llegible.
- **`boolean canWrite()`**: Retorna `true` si el fitxer és modificable.
- **`boolean canExecute()`**: Retorna `true` si el fitxer es pot executar.
- **`boolean setReadable(boolean)`**: Defineix si el fitxer es pot llegir o no.
- **`boolean setWritable(boolean)`**: Defineix si el fitxer es pot modificar o no.
- **`boolean setExecutable(boolean)`**: Defineix si el fitxer es pot executar o no.

#### Exemple d'ús per gestionar permisos

```java
File fitxer = new File("exemple.txt");

if (fitxer.exists()) {
    System.out.println("Pot ser llegit: " + fitxer.canRead());
    System.out.println("Pot ser modificat: " + fitxer.canWrite());
    System.out.println("Pot ser executat: " + fitxer.canExecute());
    
    // Canviar els permisos
    fitxer.setReadable(false);
    fitxer.setWritable(false);
}
```

### Espai de disc

Mètodes per obtenir informació sobre l'espai de disc disponible:

- **`long getUsableSpace()`**: Retorna l'espai disponible en el disc on es troba el fitxer o directori.
- **`long getTotalSpace()`**: Retorna la capacitat total del disc.
- **`long getFreeSpace()`**: Retorna l'espai lliure en el disc.

#### Exemple per consultar l'espai del disc

```java
File disc = new File("/");
System.out.println("Espai total: " + disc.getTotalSpace() + " bytes");
System.out.println("Espai lliure: " + disc.getFreeSpace() + " bytes");
System.out.println("Espai utilitzable: " + disc.getUsableSpace() + " bytes");
```

### Comparació de fitxers

La classe **`File`** permet comparar rutes de fitxers amb els mètodes **`compareTo()`** i **`equals()`**:

- **`int compareTo(File altreFitxer)`**: Compara lexicogràficament dos camins.
- **`boolean equals(Object)`**: Compara si dos objectes **`File`** fan referència al mateix fitxer o directori.

#### Exemple de comparació de rutes

```java
File fitxer1 = new File("exemple1.txt");
File fitxer2 = new File("exemple2.txt");

if (fitxer1.compareTo(fitxer2) == 0) {
    System.out.println("Els fitxers són iguals.");
} else {
    System.out.println("Els fitxers són diferents.");
}
```

## Compatabilitat amb diferents sistemes operatius

Quan es treballa amb la classe **`File`**, és important tenir

 en compte la **independència del sistema operatiu**. 
 
En diferents sistemes operatius, el separador de directoris pot variar:
- En **Windows**, el separador de directoris és la barra invertida **`\`**.
- En **Unix, Linux i macOS**, el separador de directoris és la barra inclinada **`/`**.


Si codifiquem rutes de fitxer de manera fixa amb un separador concret (per exemple, `C:\fitxers\exemple.txt`), és possible que el teu codi no funcioni correctament en altres sistemes operatius. 

Per tant, hem d'evitar escriure rutes fixes (com `/` o `\`) 

### File.separator

Java proporciona **`File.separator`**, que retorna automàticament el separador correcte segons el sistema operatiu en què s'executa el programa.

El **`File.separator`** és una constant estàtica en la classe **`File`** de Java que representa el separador de directoris utilitzat pel sistema operatiu en què s'executa el programa.

### Exemple File.separator:

Si volem construir una ruta de fitxer de manera segura independentment del sistema operatiu, podem utilitzar **`File.separator`** en comptes d'especificar el separador manualment:

```java
import java.io.File;

public class ExempleSeparator {
    public static void main(String[] args) {
        // Utilitzar File.separator per crear una ruta que funcioni a qualsevol sistema operatiu
        String directori = "usuari" + File.separator + "documents";
        String rutaCompleta = directori + File.separator + "fitxer.txt";
        
        // Mostra la ruta segons el sistema operatiu
        System.out.println("Ruta completa: " + rutaCompleta);
    }
}
```


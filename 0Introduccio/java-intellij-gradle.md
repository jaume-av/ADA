---
title: Gestió Java amb IntelliJ i Gradle/Maven
layout: default
parent: Accés a Dades
nav_order: 30
has_children: true
has_toc: true
---

## Gestió de Java en projectes amb IntelliJ i Gradle

## Idea general

Quan treballem amb Java, IntelliJ i Gradle, **no existeix un únic “Java”**.
Hem de tindre en compte que **hi ha tres nivells diferents**, cadascun amb una funció concreta:

* **Java del sistema (terminal)**: el que usa `java` i `./gradlew` quan ho llancem des del terminal.
* **Java del projecte (IntelliJ)**: el que usa IntelliJ per compilar i executar dins de l’IDE.
* **Java de compilació (toolchain de Gradle)**: el Java amb què Gradle compila el projecte, però no necessàriament amb el qual s’arranca Gradle.

Quan estes tres coses no estan alineades, apareixen errors com:

* “Dependency requires at least JVM runtime version 17. This build uses a Java 8 JVM.”

---

# Instal·lació de Java 21 (pas previ obligatori)

## Per què cal Java 21

Hem de tindre en compte que:

* **Spring Boot 3** i **Spring Framework 6** requerixen **Java 17 o superior**.
* Si el sistema usa Java 8, Gradle falla abans fins i tot d’arribar als tests.

El teu cas ho mostrava clar:

```
openjdk version "1.8.0_472"
```

Això significa: el terminal està en **Java 8**, i per això el build cau.

---

## Instal·lar Java 21 (Ubuntu 24.04)

Executa:

```
sudo apt update
sudo apt install openjdk-21-jdk
```

Açò instal·la el **JDK complet** (no només el runtime).

---

## Comprovar que realment tens instal·lat Java 21

Mira els JDK disponibles:

```
ls /usr/lib/jvm
```

Hauries de veure coses semblants a:

* `java-8-openjdk-amd64`
* `java-21-openjdk-amd64`

Si no apareix cap Java 21, el paquet no s’ha instal·lat o s’ha instal·lat un altre nom.

---

# Canviar entre Java 8 i Java 21 (sense eliminar res)

## Cal eliminar Java 8?

No. Hem de tindre en compte que:

* **No cal eliminar Java 8**.
* Tindre diversos JDK instal·lats és normal.
* El que importa és **quin està actiu** quan treballem amb cada projecte.

---

## Triar el Java actiu amb `update-alternatives`

Per a triar quin Java usa el sistema, executa:

```
sudo update-alternatives --config java
```

Això mostra una taula semblant a esta (l’exemple és el que ens interessa mentalment):

```
There are 2 choices for the alternative java (providing /usr/bin/java).

  Selection    Path                                            Priority   Status
------------------------------------------------------------
* 0            /usr/lib/jvm/java-8-openjdk-amd64/bin/java       1081      auto mode
  1            /usr/lib/jvm/java-8-openjdk-amd64/bin/java       1081      manual mode
  2            /usr/lib/jvm/java-21-openjdk-amd64/bin/java      2121      manual mode
```

Què significa:

* L’asterisc `*` marca el Java actual.
* Les opcions poden variar, però la idea és la mateixa.
* **Tu has d’escriure el número** de Java 21 (en l’exemple seria `2`) i prémer Enter.

---

## Comprovar que el canvi s’ha aplicat

Després, comprova:

```
java -version
```

Ha de mostrar alguna cosa com:

* `openjdk version "21..."`

Si continua mostrant:

* `openjdk version "1.8.0..."`

vol dir que encara estàs en Java 8 i cal revisar la selecció.

---

## Tornar a Java 8 quan ho necessites

Si tens un projecte antic que requereix Java 8, repeteix:

```
sudo update-alternatives --config java
```

i selecciona l’opció de Java 8.

Així pots canviar segons el projecte, sense trencar res.

---

# Java del sistema (terminal)

## Què és

Este és el Java que utilitza el terminal quan executes:

* `java`
* `./gradlew test`
* `mvn test`

Depén de:

* `JAVA_HOME`
* `PATH`
* i/o `update-alternatives`

Per a comprovar-lo:

```
java -version
```

Hem de tindre en compte que:

* Si el terminal està en Java 8, **Gradle arrancarà en Java 8**.
* Açò passa encara que IntelliJ estiga configurat amb Java 21.

---

# Java del projecte (IntelliJ)

## Què és

És el Java que IntelliJ utilitza per:

* compilar
* executar
* fer tests dins de l’IDE

Es configura amb:

* **Project SDK**
* **Gradle JVM**

Hem de tindre en compte que:

* IntelliJ pot estar en Java 21 i el terminal en Java 8 al mateix temps.
* Açò és normal, però pot confondre.

---

# Java de compilació (Gradle toolchain)

## Què és

El toolchain indica amb quin Java es compila el projecte:

```gradle
java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}
```

Però hem de tindre clar que:

* Açò **no canvia** el Java amb què s’arranca Gradle.
* Si Gradle arranca en Java 8, el build falla abans.

---

# Forçar Java 21 per al projecte (gradle.properties)

## Per a què servix

`gradle.properties` pot forçar la JVM amb què Gradle s’executa.

Crea (si no existeix) un fitxer `gradle.properties` en l’arrel del projecte, al costat de `build.gradle` i `settings.gradle`.

Estructura típica:

```
TarjetaCredit/
├── build.gradle
├── settings.gradle
├── gradle.properties
├── gradlew
└── src/
```

Contingut:

```
org.gradle.java.home=/usr/lib/jvm/java-21-openjdk-amd64
```

Important:

* El camí ha d’existir de veritat.
* Ha de ser el directori del JDK, no `.../bin/java`.

---

# Com comprovar amb certesa què usa Gradle

Executa:

```
./gradlew -version
```

Busca la línia “JVM”. Hauria de dir alguna cosa com:

* `JVM: 21...`

Si diu:

* `JVM: 1.8...`

encara tens el problema.

---

# Combinacions recomanades

## Projectes nous (Spring Boot 3)

* Terminal: Java **21**
* IntelliJ Project SDK: **21**
* IntelliJ Gradle JVM: **21**
* Gradle toolchain: **21**
* (Opcional) `gradle.properties` forçant Java 21

Això és el més estable per a la pràctica.

---

## Projectes antics (Java 8)

* Terminal: pots posar Java 8 quan treballes en eixos projectes
* IntelliJ Project SDK: **8**
* Gradle JVM: **8**
* Toolchain: **8**

---

# Errors habituals 

* Executar `./gradlew test` i que Gradle use Java 8 perquè el terminal està en Java 8.
* Pensar que el toolchain arregla això (no ho fa).
* Posar un camí incorrecte en `org.gradle.java.home`.
* No mirar la taula de `update-alternatives` per seleccionar Java 21.

---

Hem de tindre en compte que:

* **No cal eliminar Java 8**.
* El que importa és **triar Java 21** quan fem projectes moderns.
* El moment crític és quan executem `./gradlew test` des del terminal: ahí mana el Java del sistema.

Amb estes passes i comprovacions, podem treballar amb **Java 8 i Java 21** en el mateix ordinador sense conflictes.

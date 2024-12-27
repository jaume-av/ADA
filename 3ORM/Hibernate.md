
# Hibernate

**Introducció**
En quasi qualsevol aplicació que anem a construir, més prompte o més tard ens haurem d’enfrontar amb la **persistència de les seues dades**. És a dir, hem de garantir que les dades que maneja o genera l’aplicació s'emmagatzemen de forma persistent fora d'aquesta per al seu ús posterior.

Això, per regla general, implica l'**ús d'un sistema de base de dades**, que pot ser una base de dades relacional (SQL) o una base de dades documental (NoSQL). 

En aquesta unitat, treballarem amb bases de dades relacionals des d'aplicacions Java. Però, a diferència de l'enfocament clàssic de connexions JDBC, farem front al **desfasament objecte-relacional (O/R mismatch)**, un problema que sorgix quan es combina la programació orientada a objectes (POO) amb l’accés a bases de dades relacionals.

Aquest desfasament sorgeix perquè:
- Les bases de dades tracten la informació com a relacions i conjunts.
- Les aplicacions orientades a objectes tracten amb instàncies de classes i les relacions entre elles.

Per superar aquest desfasament i facilitar la gestió de dades, utilitzarem **eines ORM (Object Relational Mapping)**, com Hibernate, que faran la "traducció" automàtica entre taules i objectes.

### **Què és Hibernate?**
* Hibernate és una eina de mapatge **objecte-relacional (ORM)** que permet gestionar l'**accés a les bases de dades des d'aplicacions Java** de forma automàtica i eficient. 
* Gràcies a Hibernate, **no hem  d'escriure codi SQL manualment per a consultar, inserir, actualitzar o eliminar dades**. Hibernate mapeja les taules de la base de dades a classes Java, convertint les files en objectes i viceversa.

Hibernate també proporciona un llenguatge de consultes **HQL (Hibernate Query Language)**, que és semblant a SQL, però està dissenyat per a treballar amb objectes en lloc de taules.

### **Què és un ORM?**
Un **Object Relational Mapping (ORM)** és una tècnica que converteix dades entre un sistema de tipus utilitzat en una base de dades relacional i el sistema de tipus d’un llenguatge de programació orientat a objectes (com Java). Aquesta conversió es a través d'un **motor de persistència** que s'encarrega de la "traducció" entre les taules de la base de dades i les classes Java.


L’objectiu és evitar haver de transformar manualment les dades de les files de la base de dades a objectes Java i viceversa, facilitant la programació i augmentant la productivitat.

**¿Com funciona Hibernate?**
Hibernate actua com una **capa d'abstracció** entre la vostra aplicació Java i la base de dades. Quan necessiteu llegir o modificar les dades, Hibernate fa el treball "difícil" per vosaltres:
1. Traduïeix les operacions sobre objectes en consultes SQL.
2. Gestiona les transaccions de forma automàtica.
3. Retorna les dades en forma d’objectes Java.

**Avantatges d’usar Hibernate**
1. **Reducció del codi JDBC**: No haureu de gestionar la connexió a la base de dades ni escriure sentències SQL manuals.
2. **Abstracció del codi SQL**: Utilitza **HQL** en lloc de SQL, treballant amb objectes.
3. **Independència de la base de dades**: Podeu canviar de base de dades (MySQL, Oracle, SQL Server) modificant solament la configuració.
4. **Automatització de tasques CRUD**: Les operacions de **Create, Read, Update i Delete (CRUD)** s’automatitzen.
5. **Millor manteniment i reutilització**: Permet una arquitectura més neta i escalable.
6. **Seguretat**: Redueix les possibilitats d’inserció de codi malícit (SQL Injection).

**Inconvenients de Hibernate**
1. **Major consum de recursos**: Les consultes es tradueixen a SQL, cosa que pot requerir més processament.
2. **Temps d’aprenentatge**: Hibernate té una corba d’aprenentatge considerable.
3. **Menor rendiment en sistemes complexes**: Quan hi ha massa complexitat, pot resultar menys eficient que el codi SQL personalitzat.

**Mapeig de Dades amb Hibernate**
1. **Classe Client**: Definiu una classe `Client` amb propietats com `id`, `nom`, `cognom` i `telèfon`.
2. **Conversió a Taula**: Hibernate mapeja aquesta classe a una taula de la base de dades on cada propietat és una columna.
3. **Gestí de Transaccions**: Quan creeu un objecte `Client` i el guardeu amb `save()`, Hibernate genera automàticament la consulta SQL `INSERT INTO client (nom, cognom, telefon) VALUES (...)`.

**Requisits per a usar Hibernate**
1. **Sistema gestor de base de dades**: Necessiteu una base de dades com MySQL, SQL Server o Oracle.
2. **Driver JDBC**: Instal·leu el controlador JDBC corresponent.
3. **Llibreries de Hibernate**: Incloeu les llibreries necessàries (via Maven o manualment).
4. **Java 8 o superior**: Hibernate requereix Java 8 com a mínim.

**HQL (Hibernate Query Language)**
**HQL** és un llenguatge de consultes semblant a SQL, però dissenyat per treballar amb objectes. Per exemple, podeu fer una consulta com:
```java
List<Client> clients = session.createQuery("FROM Client WHERE nom = :nom")
                             .setParameter("nom", "Joan")
                             .list();
```
Aquesta consulta obté tots els clients amb el nom "Joan".

**Conclusió**
Hibernate és una eina imprescindible per al desenvolupament d’aplicacions Java que accedeixen a bases de dades. Automatitza les operacions més repetitives, millora la seguretat i permet treballar de forma més intuïtiva amb objectes Java. Encara que té una corba d’aprenentatge inicial, a la llarga us ajudarà a ser molt més eficients i a crear aplicacions escalables i segures.


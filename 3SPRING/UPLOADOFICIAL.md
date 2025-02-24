### **Pujada d'Arxius en Spring Boot**  

Aquesta guia t'explica com crear una aplicació de servidor capaç de rebre pujades d'arxius mitjançant **HTTP multi-part**.

---

## **Què construiràs?**  
Crearàs una aplicació web amb **Spring Boot** que accepte pujades d'arxius. A més, construiràs una interfície HTML senzilla per a provar la funcionalitat.

---

## **Què necessites?**  
- Uns **15 minuts**  
- Un **editor de text** o **IDE** preferit  
- **Java 17** o una versió posterior  
- **Gradle 7.5+** o **Maven 3.5+**  
- Pots importar el codi directament al teu IDE:  
  - **Spring Tool Suite (STS)**  
  - **IntelliJ IDEA**  
  - **VSCode**  

---

## **Completar la guia**  
Com en la majoria de guies de **Spring**, pots començar des de zero o ometre els passos bàsics si ja estàs familiaritzat amb la configuració.  

### **Si vols començar des de zero:**  
Segueix amb la secció **"Començant amb Spring Initializr"**.  

### **Si vols saltar la configuració inicial:**  
1. Descarrega i descomprimeix el repositori de codi font d’aquesta guia o clona'l amb Git:  
   ```bash
   git clone https://github.com/spring-guides/gs-uploading-files.git
   ```
2. Accedeix a la carpeta inicial del projecte:  
   ```bash
   cd gs-uploading-files/initial
   ```
3. Salta directament a la secció **"Crear una Classe d’Aplicació"**.  

Quan hages acabat, podràs comparar el teu codi amb la versió completa en `gs-uploading-files/complete`.

---

## **Començant amb Spring Initializr**  
Pots utilitzar **Spring Initializr** per a generar el projecte preconfigurat.  

### **Passos per configurar el projecte manualment:**  
1. **Accedeix a** [Spring Initializr](https://start.spring.io).  
2. Tria **Gradle** o **Maven** com a gestor de dependències i selecciona **Java** com a llenguatge.  
3. Fes clic a **Dependencies** i afegeix:  
   - **Spring Web**  
   - **Thymeleaf**  
4. Fes clic a **Generate** per descarregar el fitxer **ZIP** amb la configuració del projecte.  
5. Extreu el fitxer ZIP i obri'l en el teu **IDE**.  

---

## **Crear la Classe d’Aplicació**  
Per a començar amb Spring Boot MVC, necessites una classe d’inici. A més, per a gestionar pujades d'arxius en **contenidors Servlet**, cal registrar un **MultipartConfigElement**. Però gràcies a **Spring Boot**, això es configura automàticament.  

📌 **Codi de la classe principal:**
```java
package com.example.uploadingfiles;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class UploadingFilesApplication {

    public static void main(String[] args) {
        SpringApplication.run(UploadingFilesApplication.class, args);
    }
}
```
Quan **Spring Boot** configura automàticament **Spring MVC**, també crea un **MultipartConfigElement** per a gestionar les pujades d'arxius.

---

## **Crear un Controlador per a Pujar Arxius**  
L'aplicació ja conté classes per a gestionar l’emmagatzematge i la càrrega dels arxius pujats. Utilitzaràs aquestes classes en un **FileUploadController**.

📌 **Codi del controlador de pujada d'arxius:**
```java
package com.example.uploadingfiles;

import java.io.IOException;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.uploadingfiles.storage.StorageFileNotFoundException;
import com.example.uploadingfiles.storage.StorageService;

@Controller
public class FileUploadController {

    private final StorageService storageService;

    @Autowired
    public FileUploadController(StorageService storageService) {
        this.storageService = storageService;
    }

    @GetMapping("/")
    public String listUploadedFiles(Model model) throws IOException {
        model.addAttribute("files", storageService.loadAll().map(
                path -> MvcUriComponentsBuilder.fromMethodName(FileUploadController.class,
                        "serveFile", path.getFileName().toString()).build().toUri().toString())
                .collect(Collectors.toList()));

        return "uploadForm";
    }

    @GetMapping("/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
        Resource file = storageService.loadAsResource(filename);
        if (file == null)
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

    @PostMapping("/")
    public String handleFileUpload(@RequestParam("file") MultipartFile file,
                                   RedirectAttributes redirectAttributes) {
        storageService.store(file);
        redirectAttributes.addFlashAttribute("message",
                "Has pujat correctament " + file.getOriginalFilename() + "!");

        return "redirect:/";
    }

    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }
}
```

📌 **Aquest controlador fa el següent:**  
1. **GET `/`** → Mostra la llista d’arxius pujats en una plantilla Thymeleaf.  
2. **GET `/files/{filename}`** → Serveix l’arxiu perquè es puga descarregar.  
3. **POST `/`** → Gestiona la pujada d’un arxiu i el desa en el sistema d’arxius.  
4. **Excepció** → Si un arxiu no existeix, retorna un **error 404**.  

---

## **Crear una plantilla HTML per a pujar arxius**  
📌 **Codi del fitxer `uploadForm.html`:**
```html
<html xmlns:th="https://www.thymeleaf.org">
<body>

    <div th:if="${message}">
        <h2 th:text="${message}"/>
    </div>

    <div>
        <form method="POST" enctype="multipart/form-data" action="/">
            <table>
                <tr><td>Arxiu a pujar:</td><td><input type="file" name="file" /></td></tr>
                <tr><td></td><td><input type="submit" value="Pujar" /></td></tr>
            </table>
        </form>
    </div>

    <div>
        <ul>
            <li th:each="file : ${files}">
                <a th:href="${file}" th:text="${file}" />
            </li>
        </ul>
    </div>

</body>
</html>
```
📌 **Parts de la plantilla:**  
1. Un **missatge** de confirmació després de la pujada.  
2. Un **formulari HTML** per pujar arxius.  
3. Una **llista d'arxius** pujats.  

---

## **Configuració de límits per a pujada d'arxius**  
Spring Boot permet establir límits per a evitar pujades de fitxers massa grans.  
📌 **Afig aquestes propietats a `application.properties`:**
```properties
spring.servlet.multipart.max-file-size=128KB
spring.servlet.multipart.max-request-size=128KB
```

---

## **Executar l’aplicació**  
📌 **Amb Gradle:**  
```bash
./gradlew bootRun
```
📌 **Amb Maven:**  
```bash
./mvnw spring-boot:run
```
📌 **Després, obri el navegador i ves a:**  
```
http://localhost:8080/
```
Tria un fitxer i prem **Pujar**. Si el fitxer és massa gran, es mostrarà un missatge d'error.

---

## **Conclusió**  
✅ Has creat una aplicació amb **Spring Boot** per pujar arxius.  
✅ Has implementat un **controlador** per gestionar les pujades.  
✅ Has creat una **interfície HTML** amb Thymeleaf.  

🎉 **Ara ja pots gestionar pujades d’arxius en la teua aplicació Spring Boot!** 🚀
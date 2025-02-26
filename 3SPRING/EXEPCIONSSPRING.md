# Gestió d'Excepcions en Spring MVC i Spring REST

La gestió d'excepcions és un aspecte fonamental en qualsevol aplicació, ja que permet oferir respostes adequades als errors que es poden produir durant l'execució. Spring proporciona diferents mecanismes per capturar i gestionar aquestes excepcions, diferenciant entre aplicacions **MVC** (amb Thymeleaf) i **API REST** (amb JSON).

## 1. Tipus de gestió d'excepcions
Spring permet gestionar excepcions de dues maneres principals:

- **Gestió local**: Es defineix dins d'un `@Controller` o `@RestController` i només maneja errors dins d'eixe controlador.
- **Gestió global**: S'utilitza `@ControllerAdvice` o `@RestControllerAdvice` per capturar excepcions de manera centralitzada.

## 2. Gestió local d'excepcions
Si només volem gestionar errors dins d'un controlador específic, podem usar `@ExceptionHandler` dins del mateix `@Controller`.

### Exemple en MVC (Thymeleaf)
```java
@Controller
@RequestMapping("/llistats")
public class PlantillesMVCController {

    @Autowired
    private CiutatRepository ciutatRepository;

    @GetMapping("/ciutats")
    public String llistatCiutats(Model model) {
        List<Ciutat> ciutatList = ciutatRepository.findAll();
        if (ciutatList.isEmpty()) {
            throw new ResourceNotFoundException("No s'han trobat ciutats");
        }
        model.addAttribute("ciutats", ciutatList);
        return "LlistatCiutats";
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public String handleResourceNotFound(ResourceNotFoundException ex, Model model) {
        model.addAttribute("textError", ex.getMessage());
        return "plantillaError";
    }
}
```

### Exemple en API REST (JSON)
```java
@RestController
@RequestMapping("/api")
public class VueController {

    @Autowired
    private CiutatRepository ciutatRepository;

    @GetMapping("/ciutats")
    public List<Ciutat> obtindreCiutats() {
        List<Ciutat> ciutats = ciutatRepository.findAll();
        if (ciutats.isEmpty()) {
            throw new ResourceNotFoundException("No s'han trobat ciutats");
        }
        return ciutats;
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleResourceNotFound(ResourceNotFoundException ex) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", "Recurs no trobat");
        errorResponse.put("message", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }
}
```

## 3. Gestió global d'excepcions
Quan volem gestionar errors per a **tots els controladors**, utilitzem `@ControllerAdvice` per a MVC i `@RestControllerAdvice` per a API REST.

### 3.1 Gestió global en MVC (Thymeleaf)
```java
@ControllerAdvice(assignableTypes = {PlantillesMVCController.class})
public class WebExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleException(Exception ex, Model model) {
        model.addAttribute("textError", "S'ha produït un error inesperat.");
        model.addAttribute("detallError", ex.getMessage());
        return "plantillaError";
    }
}
```

### 3.2 Gestió global en API REST (JSON)
```java
@RestControllerAdvice(assignableTypes = {VueController.class})
public class ApiExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, String> handleException(Exception ex) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", "Error intern del servidor");
        errorResponse.put("message", ex.getMessage());
        return errorResponse;
    }
}
```

## 4. Excepcions personalitzades
Podem definir excepcions personalitzades per millorar la claredat i l'organització del codi.

### Definició d'una excepció personalitzada
```java
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
```

Aquesta excepció pot ser llançada des de qualsevol controlador.

## 5. Resum de comportament esperat
| **Petició** | **Controlador** | **Gestió d’excepcions** | **Resposta** |
|-------------|---------------|-------------------------|--------------|
| `GET /llistats/ciutats` | `@Controller` (MVC) | `WebExceptionHandler` | `plantillaError.html` |
| `GET /api/ciutats` | `@RestController` (API) | `ApiExceptionHandler` | JSON: `{ "error": "Error intern del servidor", "message": "Detall de l'error"}` |

## 6. Conclusió
**Si utilitzem Thymeleaf (`@Controller`), hem de gestionar les excepcions retornant una vista HTML.**  
**Si utilitzem una API REST (`@RestController`), hem de retornar una resposta JSON.**  
**Separar la gestió d'excepcions en `@ControllerAdvice` i `@RestControllerAdvice` millora l'organització del codi i la mantenibilitat.**


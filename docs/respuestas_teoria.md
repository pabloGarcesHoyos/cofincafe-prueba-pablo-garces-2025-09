# Respuestas – Prueba COFINCAFE

## 1) HashMap vs ConcurrentHashMap.
**HashMap**: Un HashMap es un mapa normal, muy rápido, pero no es seguro usarlo si varios hilos (threads) lo modifican al mismo tiempo. Puede pasar cualquier cosa: que falle, que se pierdan datos, un desastre. Permite una clave null y valores null.

**ConcurrentHashMap**: Un ConcurrentHashMap es la versión segura para cuando trabajas con múltiples hilos. Te permite leer y escribir de forma concurrente sin que se corrompan los datos. Es un poco más lento que el HashMap por esta seguridad, pero es esencial en aplicaciones multi-hilo. No permite claves ni valores null.

## List, Set y Map en Java Collections.
**List**: Es como una lista de la compra. Los elementos tienen un orden (índice) y puedes tener duplicados. Si quieres guardar una colección de cosas y el orden importa, usas una List (como ArrayList o LinkedList).

**Set**: Es como un conjunto de matemáticas. No permite elementos duplicados. Si intentas añadir algo que ya está, no pasa nada. El orden a veces no está garantizado (como en un HashSet), pero puedes usar un LinkedHashSet para mantener el orden de inserción o un TreeSet para ordenarlos automáticamente.

**Map**: Es un diccionario. Guardas pares "clave-valor". Cada clave es única (como una palabra en el diccionario), y a cada clave le asignas un valor (la definición). No puedes tener dos claves iguales. HashMap es el más común.

## 2) ¿Qué es un Optional en Java y para qué sirve?
Es una clase contenedora que se usa para evitar los temidos NullPointerException. En lugar de devolver null cuando un método no encuentra un resultado, devuelves un Optional. Esto fuerza a quien use el método a tener que manejar explícitamente el caso de que el valor esté vacío. Es una forma de decir: "Ojo, esto podría no tener resultado, tienes que comprobarlo". Por ejemplo, con .isPresent(), .get() (si estás seguro de que hay algo) o .orElse() (para dar un valor por defecto si está vacío).

## 3) ¿Cómo manejar excepciones en un servicio que accede a base de datos?
Lo manejo por capas:

- Repositorio/DAO: dejo que las excepciones de persistencia se traduzcan a DataAccessException (Spring lo hace automáticamente). No meto lógica de negocio aquí.

- Servicio: aplico transacciones con @Transactional (y readOnly = true en lecturas). Hago log con contexto (IDs, parámetros), y convierto a una excepción de dominio cuando aporta claridad (manteniendo cause). Por defecto, Spring hace rollback de RuntimeException.

- Controlador (REST): un @ControllerAdvice centraliza el manejo de errores y devuelve respuestas HTTP limpias (400/404/409/500, etc.) con mensajes útiles (o RFC-7807 si quiero estandarizar).

## 4) ¿Qué diferencia hay entre @Component, @Service y @Repository en Spring?
Los tres son estereotipos que Spring detecta por component scanning:

- @Component: genérico; “esto es un bean gestionado por Spring”.

- @Service: semántica de lógica de negocio; útil para dejar claro el rol (y para aspectos como métricas/transacciones).

- @Repository: pensado para acceso a datos; además traduce excepciones específicas de JPA/JDBC a DataAccessException, unificando el manejo de errores.

## 5) En Fineract, el backend expone una API REST: ¿cómo mapeamos un endpoint?
```java
@RestController
@RequestMapping("/api/v1/loans")
public class LoanController {

    @GetMapping("/{loanId}")
    public LoanDTO getLoan(@PathVariable Long loanId) {
        // Buscar y devolver el préstamo
    }

    @PostMapping
    public ResponseEntity<LoanDTO> createLoan(@RequestBody @Valid LoanDTO in) {
        // Crear un nuevo préstamo
        return ResponseEntity.status(HttpStatus.CREATED).body(/* dto */);
    }
}


- @RestController expone JSON por defecto.

- @RequestMapping en la clase define la base de la ruta.

- @GetMapping, @PostMapping, etc., para cada verbo.

- @PathVariable para variables de ruta, @RequestParam para query params, @RequestBody para el cuerpo.

- Validación con @Valid y DTOs. Respuestas con ResponseEntity y códigos adecuados.


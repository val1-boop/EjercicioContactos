# Gestión de Contactos

API REST en Spring Boot para administrar una agenda de contactos, con su propio frontend servido desde el mismo proyecto.

Desarrollado para la materia de Programación Web — Universidad Tecnológica Emiliano Zapata (UTEZ).

## Qué hace

- Registrar contactos con nombre y teléfono
- Listar todos los contactos guardados
- Eliminar un contacto por su identificador
- Validación en dos capas: en el navegador antes de enviar, y en el servidor antes de guardar

## API

Todos los endpoints cuelgan de `/api/contactos`.

| Método | Ruta | Qué hace | Respuesta |
|---|---|---|---|
| `GET` | `/api/contactos` | Devuelve todos los contactos | `200` con la lista en JSON |
| `POST` | `/api/contactos` | Crea un contacto | `200` con el contacto creado, o `400` si falta nombre o teléfono |
| `DELETE` | `/api/contactos/{id}` | Elimina un contacto | `200` si se eliminó, o `400` si el id no existe |

Ejemplo de creación:

```json
POST /api/contactos
{
  "nombre": "Ana Torres",
  "telefono": "7771234567"
}
```

## Arquitectura

El proyecto separa responsabilidades en capas:

```
src/main/
├── java/com/utez/EquipoValeriaLuis/Contactos/
│   ├── ContactosApplication.java   # Punto de entrada
│   ├── controller/
│   │   └── ContactoController.java # Endpoints REST y validación
│   ├── model/
│   │   └── Contacto.java           # Entidad JPA
│   └── repository/
│       └── ContactoRepository.java # Acceso a datos (JpaRepository)
└── resources/
    ├── application.properties      # Configuración de H2 y JPA
    └── static/                     # Frontend servido por Spring Boot
        ├── index.html
        └── style.css
```

El repositorio extiende `JpaRepository`, así que las operaciones de base de datos (`findAll`, `save`, `deleteById`, `existsById`) las genera Spring Data automáticamente; no hay SQL escrito a mano.

El frontend vive en `static/` y Spring Boot lo sirve directamente: al levantar el proyecto quedan disponibles la API y la interfaz en el mismo puerto, sin necesidad de un servidor aparte.

## Tecnologías

| Capa | Herramientas |
|---|---|
| Backend | Java, Spring Boot, Spring Web |
| Persistencia | Spring Data JPA, Hibernate, H2 |
| Frontend | HTML, CSS, JavaScript (Fetch API) |
| Build | Maven |

## Cómo ejecutarlo

```bash
./mvnw spring-boot:run
```

En Windows:

```bash
mvnw.cmd spring-boot:run
```

Después abre `http://localhost:8080`.

La consola de la base de datos queda en `http://localhost:8080/h2` (usuario `sa`, sin contraseña).

## Estado actual y siguientes pasos

Cosas que quedaron pendientes y cómo se resolverían:

- **No hay endpoint de actualización.** La API cubre crear, consultar y eliminar; falta un `PUT /api/contactos/{id}` para editar un contacto existente.
- **La base de datos es en memoria.** H2 está configurado con `jdbc:h2:mem`, así que los contactos se pierden al reiniciar. Cambiar a un archivo o a MySQL sería el siguiente paso.
- **`static/app.js` quedó sin uso.** La lógica del frontend está embebida en `index.html`; ese archivo es una versión anterior que ya no se carga. Lo correcto sería mover el script a `app.js` y enlazarlo desde el HTML.
- **La validación de `@NotBlank` no se aplica.** La entidad declara las anotaciones, pero el controlador valida a mano; añadir `@Valid` en el parámetro del `POST` haría que Spring las use.
- **CORS está abierto a todos los orígenes** (`@CrossOrigin(origins = "*")`). Práctico para desarrollo, pero conviene restringirlo antes de exponer la API.

## Equipo

- Valeria Lagunas Carbajal
- Luis Ángel Castelar Hernández

# PRÁCTICA ACADÉMICA - SISTEMA DE GESTIÓN UNIAJC (MVC + DB)

## Objetivo
Replicar el ecosistema académico propuesto en el diagrama Mermaid, siguiendo los estándares de arquitectura en capas (Entidad, DAO, Service, Controller, Vista).

## Diagrama del Ecosistema
```mermaid
classDiagram
    class Estudiante {
        +int id_estudiante
        +string nombre
        +string apellido
        +string email
    }

    class Docente {
        +int id_docente
        +string nombre
        +string especialidad
    }

    class Materia {
        +int id_materia
        +string nombre_materia
        +int creditos
    }

    class Grupo {
        +int id_grupo
        +int id_materia
        +int id_docente
        +string aula
        +string horario
    }

    class Inscripcion_Curso {
        +int id_inscripcion
        +int id_estudiante
        +int id_grupo
        +float nota_final
        +string estado
    }

    Estudiante "1" -- "*" Inscripcion_Curso : se inscribe
    Grupo "1" -- "*" Inscripcion_Curso : contiene alumnos
    Materia "1" -- "*" Grupo : se dicta en
    Docente "1" -- "*" Grupo : imparte
```

## Instrucciones para el Estudiante:
1.  **Crea tu propia rama:** `feature/practica-[TuNombre]`.
2.  **Entidad:** Crea las clases en el paquete `com.uniajc.modelo` para las entidades restantes.
3.  **DAO:** Implementa la persistencia en `com.uniajc.modelo` usando JDBC y `ConexionDatabase`.
4.  **Servicio:** Agrega la lógica de negocio (validaciones de integridad).
5.  **Vistas:** Implementa interfaces tanto para Scanner como para SWING como para JAVA FX.
6.  **Pruebas:** Asegúrate de que tu código pase los tests de validación del docente.

## Configuración de BD:
Usa `config.properties` en la raíz del proyecto. La conexión acepta `db.url`, `db.user` y `db.password`,
o sus alias `URL`, `USERNAME` y `PASSWORD`. El archivo está ignorado por Git; conserva allí las
credenciales y reinicia la aplicación después de cambiarlas. No subas ese archivo al repositorio.

La base configurada debe tener la tabla `public.estudiante` con `id`, `name`, `lastname`, `email`, `nit`
y `numcelular`. Ejecuta [`src/main/resources/db/estudiantes.sql`](src/main/resources/db/estudiantes.sql) para instalar
las rutinas usadas por el CRUD: `sp_insertar_estudiante` (crear), `fn_listar_estudiantes` (listar),
`sp_actualizar_estudiante` (editar) y `sp_eliminar_estudiante` (eliminar). Luego inicia la aplicación desde
tu IDE usando `com.uniajc.Main`.

---
*Recuerda: El Modelo nunca debe hablar con el usuario (sin System.out). El Controlador orquesta y la Vista interactúa.*

Creado para una prueba.

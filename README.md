# Gestor de Tareas API con Spring Boot y OpenApi

## Descripción
Esta API proporciona una solución robusta y flexible para gestionar tareas utilizando el framework Spring Boot. Ofrece una serie de endpoints que permiten la creación, actualización, consulta y eliminación de tareas, así como la capacidad de ordenar, paginar y filtrar las tareas según diversos criterios. La API se documenta automáticamente mediante la especificación OPENAPI y Swagger, lo que facilita su comprensión y uso.
Para el desarrollo de este proyecto se han seguido buenas prácticas y 'Clean Code'.

## Capturas de Pantalla

![image](https://github.com/canaritel/spring-restapi-openapi-swagger/assets/57302177/024be2ce-f354-4e9e-845e-bc60de9104f6)

![image](https://github.com/canaritel/spring-restapi-openapi-swagger/assets/57302177/2b8bee9e-e65b-4037-ba8b-7b38fe97db6e)

## Características Principales
- Creación, actualización y eliminación de tareas.
- Consulta y filtrado de tareas por diversos criterios.
- Ordenación y paginación de tareas para una experiencia de usuario optimizada.
- Cambio de estado y completitud de tareas.
- Documentación interactiva generada automáticamente a través de Swagger.

## Patrones y Tecnologías Utilizadas
- Arquitectura MVC (Modelo-Vista-Controlador): El proyecto sigue el patrón MVC para mantener una estructura organizada y escalable.
- Inyección de Dependencias: Spring Boot permite la inyección de dependencias, promoviendo la modularidad y la facilidad de mantenimiento.
- Swagger (OpenAPI): La documentación de la API se genera automáticamente con Swagger, permitiendo una exploración y comprensión sencilla de los endpoints.
- Patrón de Repositorio: El acceso a la base de datos se gestiona a través de repositorios, garantizando una separación clara entre la lógica de negocio y la persistencia.
- Validación de Datos: Se aplican anotaciones de validación en las clases DTO para asegurar la integridad de los datos.
- Patrón Builder: Utilizado para crear objetos complejos con múltiples parámetros de configuración de manera clara y legible.
- Principios de Clean Code: El código sigue los principios de "Clean Code" para garantizar la legibilidad, la simplicidad y la facilidad de mantenimiento. Se enfatiza en la claridad de los nombres de variables, funciones y clases, así como en la separación de responsabilidades y la minimización de la complejidad.

## Instrucciones de Uso

### Requisitos

- Java 11 o 17
- Maven
- Base de datos H2 o MySQL

### Configuración

1. Clona este repositorio: `git clone https://github.com/tu-usuario/gestor-de-tareas-api.git`
2. Configura las propiedades de la base de datos en `application.properties`.
3. Ejecuta `mvn clean install` para construir el proyecto.
4. Ejecuta la aplicación: `mvn spring-boot:run`

### Endpoints de la API

Consulta la documentación de la API en [Swagger UI](http://localhost:8080/swagger-ui/index.html) para obtener una descripción detallada de los endpoints disponibles.

## Contribución

¡Estamos abiertos a contribuciones! Si deseas contribuir, por favor sigue estos pasos:

1. Haz un fork de este repositorio.
2. Crea una rama para tu contribución: `git checkout -b mi-nueva-funcionalidad`
3. Realiza tus cambios y realiza commits: `git commit -m "Añadir nueva funcionalidad"`
4. Envía un Pull Request a la rama principal de este repositorio.

## Licencia

Este proyecto está bajo la Licencia MIT. Consulta el archivo [LICENSE](/LICENSE) para más detalles.

## Contacto

Si tienes alguna pregunta o sugerencia, no dudes en contactarnos en `antonio@canaritel.es`.

---

**Este proyecto es parte del Proyecto Spring Avanzado | 2023**

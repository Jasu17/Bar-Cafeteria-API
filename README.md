# Bar/Cafetería API

Backend REST desarrollado con Spring Boot para la gestión operativa de un bar/cafetería híbrido.

## Stack
- Java 17
- Spring Boot 3
- Spring Security + JWT
- PostgreSQL
- Lombok

## Configuración local

1. Clonar el repositorio
2. Copiar `.env.example` a `.env` y completar los valores
3. Crear la base de datos en PostgreSQL: `barcafe_db`
4. Ejecutar el proyecto con `export $(cat .env | xargs) && ./mvnw spring-boot:run`

## Variables de entorno requeridas

| Variable         | Descripción                        |
|------------------|------------------------------------|
| DB_URL           | URL de conexión a PostgreSQL       |
| DB_USERNAME      | Usuario de la base de datos        |
| DB_PASSWORD      | Contraseña de la base de datos     |
| JWT_SECRET       | Clave secreta para firmar el JWT   |
| JWT_EXPIRATION   | Expiración del token en ms         |
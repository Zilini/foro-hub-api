# 📘 ForoHub API

Una API RESTful robusta y segura para un foro de discusión, desarrollada con **Spring Boot**. ForoHub permite a los usuarios registrarse, autenticarse, crear y gestionar tópicos de discusión, y controlar permisos a través de un sistema basado en roles.

---

## 🚀 Características Principales

- **🔐 Registro Seguro de Usuarios**  
  Nuevos usuarios pueden registrarse, y sus contraseñas se encriptan con **BCrypt** antes de almacenarse en la base de datos.

- **🔑 Autenticación JWT**  
  El inicio de sesión retorna un **JSON Web Token (JWT)** que debe incluirse en el header `Authorization` para acceder a los endpoints protegidos.

- **🔒 Autorización Basada en Roles**  
  Uso de anotaciones `@PreAuthorize` para controlar el acceso según los roles (`ADMIN`, `MODERADOR`, `USUARIO`, etc.).

- **📝 Gestión de Tópicos**
  - **CRUD completo**: Crear, leer, actualizar y eliminar tópicos.
  - **Seguridad a nivel de objeto**: Solo el autor o usuarios con rol `ADMIN` o `MODERADOR` pueden modificar o eliminar tópicos.

- **⚠️ Manejo de Errores Consistente**  
  Respuestas estructuradas en JSON para errores comunes: validación, lógica de negocio, autenticación (`401 Unauthorized`) y autorización (`403 Forbidden`).

- **📄 Documentación Interactiva**  
  Integración con **Swagger UI** mediante SpringDoc para explorar y probar endpoints desde el navegador.

---

## 🛠️ Tecnologías y Herramientas

**Backend**
- Spring Boot 3.x
- Spring Security (JWT)
- Spring Data JPA + Hibernate
- Lombok
- Maven

**Base de Datos**
- MySQL

**Documentación**
- SpringDoc OpenAPI (Swagger UI)

---

## 🗺️ Flujo Lógico de la Aplicación

### 1. Registro de Usuario
1. POST a `/usuarios` con datos del usuario.
2. Contraseña encriptada con BCrypt.
3. Usuario guardado con rol por defecto `USUARIO`.

### 2. Autenticación
1. POST a `/login` con email y contraseña.
2. Se valida la contraseña.
3. Si es válida, se genera y retorna un JWT.

### 3. Acceso a Recursos Protegidos
1. El cliente envía el JWT en el header `Authorization`.
2. El filtro de seguridad valida el token.
3. Se verifica autorización con `@PreAuthorize`.
4. Si no tiene permisos, se responde con `403 Forbidden`.

---

## ⚙️ Guía de Implementación

### 1. Requisitos Previos
- JDK 17+
- Maven 3.6+
- MySQL (local o en la nube)

### 2. Configuración de la Base de Datos

> ⚠️ **Importante:** Por defecto, el endpoint de registro (`/usuarios`) crea usuarios con el rol `USUARIO`.  
> Para registrar administradores (`ADMIN`) o moderadores (`MODERADOR`) deberás agregarlos manualmente a la base de datos, ya sea:
> - Insertando directamente en la tabla de usuarios y asignando el rol deseado, o
> - Modificando temporalmente el código del backend para permitir el registro con roles personalizados (solo durante desarrollo).

Edita el archivo `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/foro_db
spring.datasource.username=root
spring.datasource.password=tu_contrasena
spring.jpa.show-sql=true
spring.jpa.hibernate.ddl-auto=update
```

> ℹ️ Para producción, considera usar `validate` o `none` en lugar de `update` para evitar modificaciones automáticas.

### 3. Compilar y Ejecutar la Aplicación

```bash
mvn clean package
```

```bash
java -jar ./target/api-0.0.1-SNAPSHOT.jar \
  --spring.datasource.url=jdbc:mysql://localhost/foro_db \
  --spring.datasource.username=root \
  --spring.datasource.password=tu_contrasena
```

### 4. Acceder a Swagger UI

Abre tu navegador en:

```
http://localhost:8080/swagger-ui.html
```

---

## 🧪 Ejemplos de Uso con Insomnia

Puedes usar la herramienta [Insomnia](https://insomnia.rest/) para probar la API REST de ForoHub. A continuación se presentan ejemplos básicos de configuración para algunas de las operaciones principales.

### 🔐 1. Registro de Usuario

**POST** `http://localhost:8080/usuarios`

```json
{
  "nombre": "Juan Pérez",
  "email": "juan@example.com",
  "password": "123456"
}
```

---

### 🔑 2. Inicio de Sesión (Login)

**POST** `http://localhost:8080/login`

```json
{
  "email": "juan@example.com",
  "password": "123456"
}
```

**Respuesta esperada:**

```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5..."
}
```

---

### 📥 3. Crear un Tópico

**POST** `http://localhost:8080/topicos`

Headers:
```
Authorization: Bearer <tu_token_aquí>
Content-Type: application/json
```

```json
{
  "titulo": "¿Cómo aprender Spring Boot?",
  "mensaje": "Quiero recomendaciones de recursos para aprender Spring Boot desde cero.",
  "categoria": "Programación"
}
```

---

### 🛡️ 4. Editar un Tópico

**PUT** `http://localhost:8080/topicos/1`

```json
{
  "titulo": "Actualizado: Recursos para Spring Boot",
  "mensaje": "Agregué algunos links útiles que encontré en YouTube y documentación oficial.",
  "categoria": "Programación"
}
```

---

### 🗑️ 5. Eliminar un Tópico

**DELETE** `http://localhost:8080/topicos/1`

---

### 📘 6. Ver todos los Tópicos

**GET** `http://localhost:8080/topicos`

<div align="center">

<br/>

```
████████╗███████╗ ██████╗██╗  ██╗ ██████╗██╗   ██╗██████╗ 
╚══██╔══╝██╔════╝██╔════╝██║  ██║██╔════╝██║   ██║██╔══██╗
   ██║   █████╗  ██║     ███████║██║     ██║   ██║██████╔╝
   ██║   ██╔══╝  ██║     ██╔══██║██║     ██║   ██║██╔═══╝ 
   ██║   ███████╗╚██████╗██║  ██║╚██████╗╚██████╔╝██║     
   ╚═╝   ╚══════╝ ╚═════╝╚═╝  ╚═╝ ╚═════╝ ╚═════╝ ╚═╝     
```

### ⚽ Plataforma de Gestión del Torneo de Fútbol — ECI

[![Java](https://img.shields.io/badge/Java-17-orange?style=flat-square&logo=openjdk)](https://openjdk.org/)
[![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.2.5-6DB33F?style=flat-square&logo=springboot)](https://spring.io/projects/spring-boot)
[![Maven](https://img.shields.io/badge/Maven-3.8+-C71A36?style=flat-square&logo=apache-maven)](https://maven.apache.org/)
[![JUnit 5](https://img.shields.io/badge/JUnit-5.10.2-25A162?style=flat-square&logo=junit5)](https://junit.org/junit5/)
[![JaCoCo](https://img.shields.io/badge/JaCoCo-0.8.12-brightgreen?style=flat-square)](https://www.jacoco.org/)
[![SonarQube](https://img.shields.io/badge/SonarQube-análisis_estático-4E9BCD?style=flat-square&logo=sonarqube)](https://www.sonarsource.com/)
[![License](https://img.shields.io/badge/Licencia-ECI-blueviolet?style=flat-square)](https://escuelaing.edu.co/)

<br/>

*Escuela Colombiana de Ingeniería Julio Garavito — DOSW · Grupo Beta*

</div>

---

## 🏆 Sobre el proyecto

**TechCup** es una plataforma web para la gestión integral del torneo semestral de fútbol de los programas de Ingeniería de Sistemas, Inteligencia Artificial, Ciberseguridad y Estadística de la ECI.

El sistema centraliza:

- 👤 Registro y autenticación de usuarios por dominio institucional
- ⚽ Perfiles deportivos de jugadores
- 🛡️ Gestión de equipos e inscripciones
- 🏟️ Administración de torneos y partidos
- 📊 Estadísticas, tabla de posiciones y llaves eliminatorias
- 🔍 Log de auditoría de acciones del sistema

---

## 👥 Equipo de desarrollo

<div align="center">

| # | Nombre |
|---|--------|
| 1 | **Kevin Segura** |
| 2 | **Juan Pablo Vélez Muñoz** |
| 3 | **Juan Daniel Bogotá** |
| 4 | **Cristian González** |
| 5 | **Rafael Moreno** |

</div>

---

## 📁 Estructura del proyecto

A partir del **Laboratorio 8** el proyecto migró de Java plano a **Spring Boot**, adoptando una arquitectura en capas. La estructura de paquetes es:

```
src/main/java/edu/dosw/techcup/
│
├── TechcupApplication.java        ← Punto de entrada Spring Boot
│
├── controller/                    ← Recibe peticiones HTTP — @RestController
├── service/                       ← Lógica de negocio — @Service
├── repository/                    ← Acceso a BD — @Repository (Sprint 2)
├── entity/                        ← Clases del modelo de datos
│   ├── Action.java                ← Enum: LOGIN, LOGOUT, etc.
│   ├── AuditAction.java           ← Registro de auditoría
│   ├── IAuthenticable.java        ← Interfaz de autenticación
│   ├── Manager.java               ← Rol administrador
│   ├── MemberState.java           ← Enum: ACTIVE, UNACTIVE, SUSPENDED
│   ├── Organizer.java             ← Rol organizador
│   ├── ParticipantType.java       ← Enum: STUDENT, TEACHER, etc.
│   ├── Player.java                ← Entidad jugador
│   ├── Position.java              ← Enum: GOALKEEPER, STRIKER, etc.
│   ├── Tournament.java            ← Entidad torneo
│   ├── TournamentState.java       ← Enum: BORRADOR, ACTIVO, etc.
│   └── User.java                  ← Clase base de usuario
├── dto/                           ← Objetos de transferencia de datos
├── exception/                     ← Manejo centralizado de errores
└── config/                        ← Configuración Swagger y otros
```

> **Nota:** En el Laboratorio 6 el modelo estaba organizado en subpaquetes `model/user`, `model/audit` y `model/player`. Al migrar a Spring Boot en el Lab 7, todas las clases se consolidaron en el paquete `entity/` siguiendo la arquitectura estándar de Spring.

---

## 🛠️ Tecnologías

| Componente | Tecnología |
|------------|------------|
| Lenguaje | Java 17 |
| Gestor de dependencias | Apache Maven 3.8+ |
| Framework backend | Spring Boot 3.2.5 |
| Base de datos *(Sprint 2)* | PostgreSQL |
| Imágenes *(Sprint 3)* | MongoDB (microservicio) |
| Seguridad *(Sprint 2)* | Spring Security + JWT |
| Pruebas unitarias | JUnit 5 — incluido en `spring-boot-starter-test` |
| Pruebas de comportamiento | Mockito 5.14.2 |
| Cobertura | JaCoCo 0.8.12 |
| Análisis estático | SonarQube |
| Documentación API | Springdoc OpenAPI (Swagger UI) |
| CI/CD | GitHub Actions |
| Contenedores | Docker + Docker Compose |

---

## 📦 Evolución del `pom.xml` — Lab 6 → Lab 7

En el **Laboratorio 6** el `pom.xml` era un proyecto Maven plano con JUnit como dependencia explícita y sin servidor web:

```xml
<!-- Lab 6 — dependencia manual de JUnit -->
<dependency>
    <groupId>org.junit.jupiter</groupId>
    <artifactId>junit-jupiter</artifactId>
    <version>5.10.2</version>
    <scope>test</scope>
</dependency>
```

En el **Laboratorio 7** se adoptó Spring Boot como parent del proyecto, lo que trajo dos cambios importantes:

**1. JUnit ya no se declara explícitamente** — `spring-boot-starter-test` lo incluye automáticamente junto con Mockito y AssertJ:

```xml
<!-- Lab 7 — JUnit incluido en el starter de test -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-test</artifactId>
    <scope>test</scope>
</dependency>
```

**2. Se agregó el `spring-boot-maven-plugin`** — necesario para levantar la aplicación con `mvn spring-boot:run` y para generar el `.jar` ejecutable:

```xml
<plugin>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-maven-plugin</artifactId>
</plugin>
```

Se mantuvieron de Lab 6: **JaCoCo** (cobertura ≥ 85%) y **SonarQube** (análisis estático).

---

## 🧪 Pruebas unitarias

El proyecto aplica **TDD (Test-Driven Development)** siguiendo el ciclo **Rojo → Verde → Refactor**. Cada prueba sigue la estructura **Having / When / Then**.

### Requerimientos cubiertos con pruebas en el Sprint 1

| Requerimiento | Descripción | Servicio |
|---|---|---|
| RF-01 | Registro de usuario con dominio válido | `UserService` |
| RF-04 | Gestión de usuarios — listar, inactivar, cambiar rol | `UserService` |
| RF-05 | Registro de acciones en log de auditoría | `AuditActionService` |
| RF-06 | Consulta del log de auditoría | `AuditActionService` |

### `AuditActionServiceTest` — RF-05 y RF-06

| # | Nombre del test | Descripción |
|---|-----------------|-------------|
| 1 | `shouldRegisterFirstAction` | Registra la primera acción de auditoría correctamente |
| 2 | `shouldRegisterAction` | Registra múltiples acciones y verifica el último registro |
| 3 | `shouldNotRegisterInvalidUser` | No registra una acción `null` |
| 4 | `shouldNotRegisterDuplicatedId` | No sobreescribe un registro con ID duplicado |
| 5 | `shouldReturnEmptyLogs` | Retorna lista vacía cuando no hay registros |
| 6 | `shouldReturnLogs` | Retorna correctamente todos los registros almacenados |

### `UserServiceTest` — RF-01 y RF-04

| # | Nombre del test | Descripción |
|---|-----------------|-------------|
| 1 | `shouldRegisterUserWithEciEmail` | Registra un usuario con correo `@escuelaing.edu.co` |
| 2 | `shouldRegisterUserWithGmailEmail` | Registra un usuario con correo `@gmail.com` |
| 3 | `shouldNotRegisterUserWithInvalidEmailDomain` | Rechaza dominios no permitidos (ej. `@hotmail.com`) |
| 4 | `shouldNotRegisterUserWithNullEmail` | Rechaza correos nulos |
| 5 | `shouldNotRegisterDuplicateEmail` | No permite registrar el mismo correo dos veces |
| 6 | `shouldGetAllRegisteredUsers` | Lista todos los usuarios registrados |
| 7 | `shouldGetUserById` | Recupera un usuario por su ID |
| 8 | `shouldReturnNullForNonExistentUserId` | Retorna `null` si el ID no existe |
| 9 | `shouldGetUserByEmail` | Recupera un usuario por su correo |
| 10 | `shouldInactivateUser` | Cambia el estado del usuario a `UNACTIVE` |
| 11 | `shouldSuspendUser` | Cambia el estado del usuario a `SUSPENDED` |
| 12 | `shouldActivateInactiveUser` | Reactiva un usuario previamente inactivo |
| 13 | `shouldThrowExceptionWhenInactivatingNonExistentUser` | Lanza excepción si el usuario no existe |

### Ejecutar los tests

```bash
mvn test
```

Resultado esperado:

```
[INFO] Tests run: 19, Failures: 0, Errors: 0, Skipped: 0
[INFO] BUILD SUCCESS
```

![PruebaTest](/docs/images/Tests/TestPasan.png)

---

## 📊 Cobertura con JaCoCo

JaCoCo mide el porcentaje de código cubierto por las pruebas. El objetivo del proyecto es **≥ 85% por paquete**.

![Cobertura](/docs/images/Tests/Cobertura.png)

> La cobertura actual es del **70%** correspondiente al Sprint 1. Se incrementará en los siguientes sprints al cubrir los módulos de equipos, torneos y partidos.

---

## 🔍 Análisis estático con SonarQube

```bash
# 1. Levantar SonarQube
docker run -d --name sonarqube -e SONAR_ES_BOOTSTRAP_CHECKS_DISABLE=true -p 9000:9000 sonarqube:latest

# 2. Ejecutar análisis
mvn verify sonar:sonar -Dsonar.token=TU_TOKEN
```

Ver resultados en `http://localhost:9000`

---

<div align="center">

**DOSW Company** · Escuela Colombiana de Ingeniería Julio Garavito · 2025

*Desarrollado con ☕ y mucho fútbol*

</div>
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

```
DOSW-ProyectoTechcup/
│
├── src/
│   ├── main/java/edu/dosw/techcup/
│   │   ├── model/
│   │   │   ├── audit/
│   │   │   │   ├── Action.java            ← Enum: LOGIN, LOGOUT, etc.
│   │   │   │   └── AuditAction.java       ← Registro de auditoría
│   │   │   ├── player/
│   │   │   │   ├── ParticipantType.java   ← Enum: STUDENT, TEACHER, etc.
│   │   │   │   ├── Player.java            ← Entidad jugador
│   │   │   │   └── Position.java          ← Enum: GOALKEEPER, STRIKER, etc.
│   │   │   └── user/
│   │   │       ├── IAuthenticable.java    ← Interfaz de autenticación
│   │   │       ├── Manager.java           ← Rol administrador
│   │   │       ├── MemberState.java       ← Enum: ACTIVE, UNACTIVE, SUSPENDED
│   │   │       ├── Organizer.java         ← Rol organizador
│   │   │       └── User.java              ← Clase abstracta base
│   │   └── service/
│   │       ├── AuditActionService.java    ← RF-05, RF-06: Auditoría
│   │       ├── PlayerService.java         ← RF-07, RF-08: Perfil deportivo
│   │       └── UserService.java           ← RF-01, RF-04: Usuarios
│   │
│   └── test/java/edu/dosw/techcup/
│       └── service/
│           ├── AuditActionServiceTest.java
│           ├── PlayerServiceTest.java
│           └── UserServiceTest.java
│
└── pom.xml
```

---

## 🛠️ Tecnologías

| Componente | Tecnología |
|------------|------------|
| Lenguaje | Java 17 |
| Gestor de dependencias | Apache Maven 3.8+ |
| Framework backend *(sprint 2+)* | Spring Boot |
| Base de datos *(sprint 2+)* | PostgreSQL |
| Imágenes *(sprint 3+)* | MongoDB (microservicio) |
| Seguridad *(sprint 2+)* | Spring Security + JWT |
| Pruebas unitarias | JUnit 5.10.2 |
| Pruebas de comportamiento | Mockito 5.14.2 |
| Cobertura | JaCoCo 0.8.12 |
| Análisis estático | SonarQube |
| CI/CD | GitHub Actions |
| Contenedores | Docker + Docker Compose |

---

## 🧪 Pruebas unitarias

El proyecto aplica **TDD (Test-Driven Development)** para las funcionalidades del Sprint 1. Cada prueba sigue la estructura **Having / When / Then**.

### `AuditActionServiceTest` — RF-05 y RF-06

Valida el registro y consulta del log de auditoría del sistema.

| # | Nombre del test | Descripción |
|---|-----------------|-------------|
| 1 | `shouldRegisterFirstAction` | Registra la primera acción de auditoría correctamente |
| 2 | `shouldRegisterAction` | Registra múltiples acciones y verifica el último registro |
| 3 | `shouldNotRegisterInvalidUser` | No registra una acción `null` |
| 4 | `shouldNotRegisterDuplicatedId` | No sobreescribe un registro con ID duplicado |
| 5 | `shouldReturnEmptyLogs` | Retorna lista vacía cuando no hay registros |
| 6 | `shouldReturnLogs` | Retorna correctamente todos los registros almacenados |

### `UserServiceTest` — RF-01 y RF-04

Valida el registro de usuarios y la gestión administrativa de los mismos.

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

### Ejecutar los tests y ver resultados

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

![Cobertura](/docs/images/Tests/Cobertura.png)

Por ahora se tiene 70% de cobertura porque solo se tiene el sprint 1.

---

<div align="center">

**DOSW Company** · Escuela Colombiana de Ingeniería Julio Garavito · 2025

*Desarrollado con ☕ y mucho fútbol*

</div>
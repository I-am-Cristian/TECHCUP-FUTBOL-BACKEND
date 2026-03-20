# ⚽ TECHCUP FÚTBOL
## Software Requirements Specification (SRS)

Plataforma digital para la gestión del torneo semestral de fútbol del programa de Ingeniería de Sistemas, Inteligencia Artificial, Ciberseguridad y Estadística de la Escuela Colombiana de Ingeniería Julio Garavito.

---

# 📖 1. Introducción

El presente documento describe los requerimientos de software para el desarrollo de TECHCUP, una solución tecnológica que centraliza la gestión de torneos de fútbol dentro de la universidad.

El sistema permitirá:

- Gestionar torneos
- Administrar equipos
- Registrar participantes
- Consultar partidos y resultados
- Simplificar el proceso de inscripción
- Centralizar la información del torneo

La plataforma será una aplicación web que permitirá la interacción organizada entre todos los participantes del torneo.

---

# 🧭 2. Descripción General del Sistema

TECHCUP será una plataforma web diseñada para facilitar la gestión del torneo de fútbol universitario.

El sistema permitirá a los usuarios interactuar mediante una interfaz web según su rol dentro de la plataforma.

La información será almacenada en una base de datos y el sistema interactuará con servicios externos como:

- Servicios de autenticación
- Servicios financieros

---

# 👥 2.1 Roles del Sistema

| Rol | Descripción |
|----|----|
| Jugador | Puede registrarse en equipos y participar en torneos. |
| Capitán | Crea y gestiona equipos y los inscribe en torneos. |
| Árbitro | Consulta información sobre los partidos que debe arbitrar. |
| Organizador | Crea y gestiona los torneos. |
| Administrador | Controla el funcionamiento general del sistema. |

---

# ⚙️ 2.2 Suposiciones y Dependencias

El sistema depende de los siguientes elementos:

- Conexión a internet para su funcionamiento.
- Servicios externos de autenticación.
- Servicios financieros para validar pagos.

**Dependencias**

- D1: Servicios de autenticación  
- D2: Servicios financieros

---

# 📋 3. Requerimientos Funcionales

Los requerimientos funcionales describen las acciones que el sistema debe realizar.

| ID | Requerimiento |
|----|---------------|
| RF-01 | El sistema permite al organizador crear y gestionar un torneo. |
| RF-02 | El sistema permite a un capitán inscribir a su equipo a un torneo. |
| RF-03 | El sistema permite consultar información del torneo y sus partidos. |
| RF-04 | El sistema permite registrar el pago mediante comprobante. |
| RF-06 | El sistema define automáticamente los partidos de fase de grupos. |
| RF-10 | El sistema permite al árbitro consultar información de partidos. |
| RF-11 | El sistema no permite cambiar integrantes de un equipo en torneo activo. |
| RF-12 | El capitán puede organizar su equipo antes de un partido. |
| RF-13 | Solo equipos con pago aprobado participan en el torneo. |
| RF-14 | El capitán puede subir comprobante de pago. |
| RF-15 | El organizador recibe notificación de nuevos comprobantes. |
| RF-16 | Los usuarios pueden consultar alineaciones de equipos. |
| RF-17 | Los capitanes pueden buscar jugadores para su equipo. |
| RF-18 | Un usuario puede registrarse como jugador. |
| RF-19 | Un usuario puede registrarse como capitán. |
| RF-20 | El organizador puede iniciar y finalizar un torneo. |
| RF-21 | El organizador puede definir reglamento, fechas importantes, horarios de partidos, canchas y sanciones del torneo. |
| RF-22 | El organizador puede cambiar el estado de un pago (Pendiente, En revisión, Aprobado, Rechazado). |
| RF-23 | El organizador puede registrar el marcador, goleadores y tarjetas de un partido. |
| RF-24 | El sistema genera automáticamente las llaves eliminatorias (cuartos, semifinal y final). |
| RF-25 | El sistema muestra la tabla de posiciones con partidos jugados, ganados, empatados, perdidos, goles a favor, goles en contra, diferencia de gol y puntos. |
| RF-26 | El capitán puede crear un equipo asignándole nombre, escudo y colores de uniforme. |
| RF-27 | El capitán puede invitar jugadores a su equipo. |
| RF-28 | Un jugador puede aceptar o rechazar una invitación a un equipo. |
| RF-29 | Un jugador puede crear su perfil deportivo indicando posición, número dorsal y foto. |
| RF-30 | Un jugador puede marcarse como disponible para ser contactado por capitanes. |
| RF-31 | El sistema permite consultar estadísticas del torneo: máximos goleadores, historial de partidos y resultados por equipo. |
| RF-32 | El administrador tiene control total sobre el sistema. |

---

# 🧩 4. Requerimientos No Funcionales

Los requerimientos no funcionales describen las características técnicas y de calidad del sistema.

| Código | Nombre | Categoría | Descripción |
|--------|--------|-----------|-------------|
| RNF-001 | Restricción de acceso por dominio | Seguridad | El sistema debe garantizar que solo usuarios con correo verificado (@escuelaing.edu.co o @gmail.com) puedan acceder. Ninguna sesión puede iniciarse sin verificación del dominio del correo. |
| RNF-002 | Tokens de sesión seguros | Seguridad | El sistema debe gestionar sesiones mediante tokens firmados (JWT), con expiración configurable. Las sesiones deben invalidarse al cerrar sesión o al detectar inactividad prolongada. |
| RNF-003 | Control de roles y permisos (RBAC) | Seguridad | El sistema debe implementar control de acceso basado en roles (RBAC) para restringir las funcionalidades según el tipo de usuario: Estudiante, Capitán, Organizador, Árbitro y Administrador. |
| RNF-004 | Registro de acciones (Auditoría) | Seguridad | El sistema debe registrar las acciones relevantes realizadas por los usuarios (quién, qué, cuándo) para fines de auditoría y trazabilidad. Los registros no pueden ser modificados ni eliminados por usuarios regulares. |
| RNF-005 | Backend Spring Boot por capas | Arquitectura | El backend debe desarrollarse con Spring Boot separado por capas: controladores, adaptadores, lógica y datos. |
| RNF-006 | API REST | Arquitectura | El sistema debe exponer sus funcionalidades mediante una API REST desarrollada con Spring Boot, siguiendo convenciones estándar de verbos HTTP y códigos de respuesta. |
| RNF-007 | Frontend React con TypeScript | Arquitectura | El frontend debe desarrollarse como una aplicación web utilizando React con TypeScript. |
| RNF-008 | Base de datos PostgreSQL | Arquitectura | El sistema debe utilizar PostgreSQL como motor de base de datos relacional. |
| RNF-009 | Diseño responsive | Usabilidad | La interfaz web debe ser responsive y adaptarse a dispositivos móviles para facilitar el acceso desde cualquier dispositivo. |
| RNF-010 | Actualización automática de estadísticas | Rendimiento | La tabla de posiciones y las estadísticas deben recalcularse automáticamente al registrar un resultado, sin intervención manual del organizador. |
| RNF-011 | Despliegue en contenedores Docker | Portabilidad | El sistema debe estar completamente dockerizado, con contenedores independientes para el backend, frontend y base de datos, orquestados mediante Docker Compose para facilitar su despliegue en cualquier entorno. |
| RNF-012 | Tiempo de respuesta de la API | Rendimiento | Los endpoints de consulta frecuente (tabla de posiciones, lista de partidos, búsqueda de jugadores) deben responder en menos de 2 segundos bajo condiciones normales de uso. |
| RNF-013 | Validación de integridad de datos | Arquitectura | El sistema debe aplicar validaciones en capa de negocio y en base de datos para garantizar la integridad referencial: un jugador no puede pertenecer a dos equipos simultáneamente, el número de jugadores por equipo debe estar entre 7 y 12, y más de la mitad deben pertenecer a los programas definidos. |
| RNF-014 | Gestión segura de comprobantes | Seguridad | Los archivos de comprobante de pago subidos por los capitanes deben almacenarse de forma segura, accesibles únicamente por el organizador y el Administrador. No deben ser públicamente accesibles mediante URL directa. |
| RNF-015 | Mantenibilidad del código | Mantenibilidad | El código debe seguir los patrones de diseño definidos por el equipo, estar documentado en sus componentes principales y estructurado con Maven, facilitando la incorporación de nuevos integrantes al proyecto. |
| RNF-016 | Disponibilidad durante periodo activo | Disponibilidad | El sistema debe estar disponible durante todo el periodo activo del torneo (estado "En progreso"). Las caídas no planificadas deben ser recuperables sin pérdida de datos. |
| RNF-017 | Generación automática de llaves | Rendimiento | La generación de llaves eliminatorias (cuartos, semifinal, final) debe ejecutarse de forma automática e inmediata al activar la fase eliminatoria, sin tiempos de espera perceptibles por el organizador. |
| RNF-018 | Trazabilidad del estado de inscripción | Seguridad | Cada cambio de estado de un pago (Pendiente → En revisión → Aprobado / Rechazado) debe quedar registrado con marca de tiempo y el usuario que realizó la acción, garantizando trazabilidad completa del proceso. |

---

# 📊 5. Diagramas

El sistema incluirá los siguientes diagramas de diseño:


##  Diagrama de contexto:

![DiagramaContexto](docs/images/DiagramaContexto.png)
- Diagramas UML
- Diagramas de casos de uso

Estos diagramas permitirán comprender las interacciones entre el sistema y sus actores.

---

# 🏗 Tecnologías del Sistema

El sistema se desarrollará con la siguiente arquitectura tecnológica:

- **Backend:** Spring Boot
- **API:** REST
- **Frontend:** React + Typescript
- **Base de datos:** PostgreSQL

---

# 🎯 Objetivo del Sistema

TECHCUP busca transformar la organización del torneo universitario de un proceso manual a una plataforma digital centralizada, mejorando:

- la organización del torneo
- la transparencia en resultados
- la comunicación entre participantes
- la gestión de equipos y partidos
# Mockup
https://enjoy-slush-87404811.figma.site/
---

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

# Sistema de Citas Médicas — Microservices

![Java](https://img.shields.io/badge/Java-24-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.4.4-brightgreen)
![Spring Cloud](https://img.shields.io/badge/Spring%20Cloud-2024.0.1-brightgreen)
![Docker](https://img.shields.io/badge/Docker-Compose-blue)
![Keycloak](https://img.shields.io/badge/Keycloak-26.0-red)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-16-blue)

A medical appointments management system built as a **microservices architecture** using Spring Boot, Spring Cloud, and Docker. The system handles patients, doctors, medical specialties, appointments, and medical history — each as an independent, isolated service.

The project demonstrates key microservices patterns: service discovery via Eureka, centralized OAuth2 authentication via an API Gateway, inter-service communication via OpenFeign, database isolation per service, and business logic triggers across service boundaries.

---

## Architecture

```
                        ┌─────────────────────────────────┐
                        │         Keycloak :8180           │
                        │   OAuth2 Authorization Server    │
                        │   Realm: citas-medicas           │
                        └────────────────┬────────────────┘
                                         │ validates JWT
                                         ▼
Client (Insomnia) ──────────▶  API Gateway :9090
                                         │
                          ┌──────────────▼──────────────┐
                          │       Eureka Server :8761    │
                          │    Service Discovery (lb://) │
                          └──┬──────┬──────┬──────┬─────┘
                             │      │      │      │
              ┌──────────────┘      │      │      └────────────────┐
              │              ┌──────┘      └──────┐                │
              ▼              ▼                     ▼                ▼
       pacientes:8081   medicos:8082      especialidades:8084  historial:8085
       pacientes_db     medicos_db        especialidades_db    historial_db
                              │                                     ▲
                              └──────────────┐                      │
                                             ▼                      │ auto POST
                                       citas:8083  ────────────────┘
                                       citas_db    (on COMPLETADA)

                        ┌─────────────────────────────────┐
                        │        PostgreSQL :5432          │
                        │  5 isolated databases, 1 server  │
                        └─────────────────────────────────┘
```

---

## Services

| Service | Port | Database | Responsibility |
|---|---|---|---|
| `servicio-descubrimiento` | 8761 | — | Eureka Server — service registry and discovery |
| `servicio-gateway` | 9090 | — | API Gateway — JWT validation, routing, OAuth2 |
| `servicio-pacientes` | 8081 | `pacientes_db` | Patient management (CRUD) |
| `servicio-medicos` | 8082 | `medicos_db` | Doctor management, validates specialty existence |
| `servicio-especialidades` | 8084 | `especialidades_db` | Medical specialties, lists doctors per specialty |
| `servicio-citas` | 8083 | `citas_db` | Appointment scheduling and status management |
| `servicio-historial-medico` | 8085 | `historial_db` | Immutable medical history records (read + create only) |

---

## Key Design Decisions

### No foreign keys between services
Each service has its own isolated database. Instead of database-level foreign keys (which would tightly couple services), referential integrity is enforced at the application level via **OpenFeign existence checks** before any write operation.

For example, creating a doctor (`POST /medicos`) calls `servicio-especialidades` via Feign to verify the `especialidadId` exists before persisting. If the call fails or the ID doesn't exist, a `422 Unprocessable Entity` is returned.

### OAuth2 only at the gateway layer
Keycloak tokens are validated **exclusively at the gateway**. The downstream microservices receive requests without any authentication logic of their own — the gateway acts as the single security perimeter. This keeps each service simple and focused on its domain.

### Immutable medical history
`servicio-historial-medico` exposes no `PUT` or `DELETE` endpoints. Medical records cannot be modified or deleted once created — this is enforced both at the API level (no routes defined) and at the entity level (no setters).

### Business logic trigger across services
When a `PATCH /citas/{id}/estado` request sets a cita's status to `COMPLETADA`, `servicio-citas` automatically sends a `POST` to `servicio-historial-medico` via Feign, creating the medical history record. The fields `diagnostico` and `tratamiento` are required for this transition — the gateway returns `400` if they are missing.

### Environment variable fallbacks
All inter-service URLs and database hosts are injectable via environment variables with localhost fallbacks:
```properties
spring.datasource.url=jdbc:postgresql://${DB_HOST:localhost}:5432/pacientes_db
```
This means every service **runs without Docker** exactly as before using the fallback values.

---

## Prerequisites

Only **Docker Desktop** is required to run the full system. Java, Maven, and PostgreSQL do not need to be installed locally — Docker handles everything.

- [Docker Desktop](https://www.docker.com/products/docker-desktop/) — Windows / Mac / Linux
- [Insomnia](https://insomnia.rest/) or [Postman](https://www.postman.com/) — for API testing

---

## Getting Started

### 1. Start the system

```bash
cd wsd_ef
docker compose up --build
```

The first run takes several minutes — Maven downloads all dependencies and compiles 7 services inside Docker. Subsequent runs are significantly faster due to layer caching.

Expected startup order:
1. `postgres` becomes healthy
2. `keycloak` imports the realm and becomes healthy (~30s)
3. `eureka` starts and becomes healthy
4. All microservices start and register with Eureka
5. `gateway` starts once Eureka and Keycloak are healthy

> **Note:** On the first run the gateway may exit before Keycloak is fully healthy. It is configured with `restart: on-failure` and will restart automatically. If it doesn't, start it manually from Docker Desktop.

### 2. Create your Keycloak user (one-time setup)

The realm (`citas-medicas`) and the OAuth2 client (`gateway-client`) are imported automatically. However, Keycloak's partial export does not include users, so one user must be created manually after the first startup.

1. Open `http://localhost:8180` and log in with `admin / admin`
2. Select the **citas-medicas** realm from the top-left dropdown
3. Go to **Users** → **Add user**
4. Fill in: Username, Email, First name, Last name → **Create**
5. Go to the **Credentials** tab → **Set password** → set `Temporary` to **OFF** → **Save**

This is a one-time step. The user persists across restarts as long as you do not run `docker compose down -v`.

### 3. Get a token from Insomnia

```
POST http://localhost:8180/realms/citas-medicas/protocol/openid-connect/token
Body → Form URL Encoded:
  grant_type = password
  client_id  = gateway-client
  username   = <your username>
  password   = <your password>
```

Copy the `access_token` from the response. Tokens expire in **5 minutes** — request a new one when needed.

### 4. Make your first authenticated request

In Insomnia, add the token to the **Auth** tab → **Bearer Token**, then call:

```
GET http://localhost:9090/pacientes/api/pacientes
```

A `200 OK` with an empty array confirms the full system is working.

---

## API Reference

All endpoints below are accessed through the gateway (`localhost:9090`) and require a Bearer token unless marked **public**.

### Especialidades — `/especialidades`

| Method | Path | Auth | Description |
|---|---|---|---|
| GET | `/especialidades/api/especialidades` | Public | List all specialties |
| GET | `/especialidades/api/especialidades/{id}` | Public | Get specialty by ID |
| GET | `/especialidades/api/especialidades/{id}/medicos` | Public | Get specialty with its doctors |
| POST | `/especialidades/api/especialidades` | Required | Create a specialty |
| PUT | `/especialidades/api/especialidades/{id}` | Required | Update a specialty |
| DELETE | `/especialidades/api/especialidades/{id}` | Required | Delete a specialty |

### Médicos — `/medicos`

| Method | Path | Auth | Description |
|---|---|---|---|
| GET | `/medicos/api/medicos` | Public | List all doctors |
| GET | `/medicos/api/medicos/{id}` | Public | Get doctor by ID |
| GET | `/medicos/api/medicos/especialidad/{especialidadId}` | Public | List doctors by specialty |
| POST | `/medicos/api/medicos` | Required | Create a doctor (validates especialidadId) |
| PUT | `/medicos/api/medicos/{id}` | Required | Update a doctor |
| DELETE | `/medicos/api/medicos/{id}` | Required | Delete a doctor |

### Pacientes — `/pacientes`

| Method | Path | Auth | Description |
|---|---|---|---|
| GET | `/pacientes/api/pacientes` | Required | List all patients |
| GET | `/pacientes/api/pacientes/{id}` | Required | Get patient by ID |
| POST | `/pacientes/api/pacientes` | Required | Create a patient |
| PUT | `/pacientes/api/pacientes/{id}` | Required | Update a patient |
| DELETE | `/pacientes/api/pacientes/{id}` | Required | Delete a patient |

### Citas — `/citas`

| Method | Path | Auth | Description |
|---|---|---|---|
| GET | `/citas/api/citas` | Required | List all appointments |
| GET | `/citas/api/citas/{id}` | Required | Get appointment by ID |
| GET | `/citas/api/citas/paciente/{pacienteId}` | Required | List appointments by patient |
| GET | `/citas/api/citas/medico/{medicoId}` | Required | List appointments by doctor |
| POST | `/citas/api/citas` | Required | Schedule an appointment (validates pacienteId and medicoId) |
| PATCH | `/citas/api/citas/{id}/estado` | Required | Change appointment status |
| DELETE | `/citas/api/citas/{id}` | Required | Delete an appointment |

**PATCH body — estado COMPLETADA** (requires clinical fields):
```json
{
  "nuevoEstado": "COMPLETADA",
  "diagnostico": "Diagnosis text",
  "tratamiento": "Treatment text",
  "observaciones": "Optional notes"
}
```

**Available estados:** `PENDIENTE` → `CONFIRMADA` → `COMPLETADA` / `CANCELADA`

### Historial Médico — `/historial`

| Method | Path | Auth | Description |
|---|---|---|---|
| GET | `/historial/api/historial` | Required | List all medical records |
| GET | `/historial/api/historial/{id}` | Required | Get record by ID |
| GET | `/historial/api/historial/paciente/{pacienteId}` | Required | Records by patient |
| GET | `/historial/api/historial/cita/{citaId}` | Required | Record by appointment |

> Records are created automatically when a cita is set to `COMPLETADA`. No manual POST is needed. No PUT or DELETE endpoints exist — records are immutable by design.

---

## Testing Flow

Follow this sequence to exercise the complete business logic from end to end.

**Step 1 — Create a specialty**
```json
POST /especialidades/api/especialidades
{
  "nombre": "Cardiología",
  "descripcion": "Especialidad del corazón",
  "codigoEspecialidad": "CARD-01",
  "areaClinica": "Medicina Interna"
}
```

**Step 2 — Create a doctor** (use `id` from Step 1)
```json
POST /medicos/api/medicos
{
  "nombre": "Carlos",
  "apellido": "Ramírez",
  "especialidadId": 1,
  "numeroLicencia": "LIC-001",
  "correoElectronico": "carlos@hospital.com",
  "telefono": "999-0001"
}
```

**Step 3 — Create a patient**
```json
POST /pacientes/api/pacientes
{
  "nombre": "Ana",
  "apellido": "Torres",
  "fechaNacimiento": "1990-05-15",
  "telefono": "999-1234",
  "correoElectronico": "ana@correo.com",
  "numeroCedula": "12345678",
  "grupoSanguineo": "O+"
}
```

**Step 4 — Schedule an appointment** (use IDs from Steps 2 and 3)
```json
POST /citas/api/citas
{
  "pacienteId": 1,
  "medicoId": 1,
  "fechaHora": "2026-04-20T10:00:00",
  "motivo": "Control general"
}
```

**Step 5 — Complete the appointment** (triggers automatic historial creation)
```json
PATCH /citas/api/citas/1/estado
{
  "nuevoEstado": "COMPLETADA",
  "diagnostico": "Paciente en buen estado",
  "tratamiento": "Reposo y vitaminas",
  "observaciones": "Control en 3 meses"
}
```

**Step 6 — Verify historial was created automatically**
```
GET /historial/api/historial/paciente/1
```

---

## Environment Variables Reference

All services support environment variable injection with local development fallbacks.

| Variable | Used by | Docker value | Local fallback |
|---|---|---|---|
| `DB_HOST` | All microservices | `postgres` | `localhost` |
| `DB_USERNAME` | All microservices | `admin` | `admin` |
| `DB_PASSWORD` | All microservices | `admin123` | `admin123` |
| `EUREKA_URL` | All microservices + gateway | `http://eureka:8761/eureka/` | `http://localhost:8761/eureka/` |
| `KEYCLOAK_CERTS_URI` | Gateway | `http://keycloak:8080/realms/citas-medicas/protocol/openid-connect/certs` | `http://localhost:8180/realms/citas-medicas/protocol/openid-connect/certs` |
| `ESPECIALIDADES_URL` | servicio-medicos | `http://especialidades:8084` | `http://localhost:8084` |
| `MEDICOS_URL` | servicio-especialidades, servicio-citas | `http://medicos:8082` | `http://localhost:8082` |
| `PACIENTES_URL` | servicio-citas | `http://pacientes:8081` | `http://localhost:8081` |
| `HISTORIAL_URL` | servicio-citas | `http://historial:8085` | `http://localhost:8085` |

---

## Useful Commands

```bash
# First run — builds all Docker images and starts everything
docker compose up --build

# Subsequent runs — starts with existing images (much faster)
docker compose up

# Stop all containers — data is preserved in the postgres volume
docker compose down

# Stop and wipe all data — completely fresh start on next run
docker compose down -v

# Rebuild and restart only the gateway (e.g. after a config change)
docker compose up --build gateway

# View logs for a specific service
docker compose logs -f gateway
docker compose logs -f keycloak
docker compose logs -f pacientes
```

---

## Troubleshooting

### 401 Unauthorized despite having a valid token

**Cause A — Token expired.** Keycloak tokens expire in 5 minutes by default. Request a fresh token and try again immediately.

**Cause B — Issuer mismatch (Docker only).** Tokens generated via `localhost:8180` carry `iss: http://localhost:8180/realms/citas-medicas`. The gateway inside Docker uses the JWK Set URI directly (`jwk-set-uri`) to validate token signatures without checking the issuer, which resolves this mismatch. If you see 401s after a fresh token, verify the gateway is using `jwk-set-uri` in its `application.properties`.

### Gateway container exits on startup

**Cause:** Keycloak's health check takes up to 30 seconds to become ready. If the gateway starts before Keycloak's `/health/ready` endpoint responds, the `depends_on: condition: service_healthy` check fails.

**Fix:** The gateway is configured with `restart: on-failure` and will automatically restart. If it does not restart within a minute, start it manually from Docker Desktop or run:
```bash
docker compose start gateway
```

### `database "admin" does not exist` in postgres logs

This was a known issue with the PostgreSQL health check using `pg_isready -U admin` without specifying a target database. It has been fixed in the current `docker-compose.yml` with `pg_isready -U admin -d postgres`. If you see this error, ensure you are using the latest version of the compose file.

### Keycloak realm not imported

If the Keycloak admin console shows no `citas-medicas` realm after startup, the `realm-export.json` file may be missing or malformed. Export it again from a running Keycloak instance:

1. Start your local Keycloak container
2. Go to `http://localhost:8180` → **citas-medicas** realm → **Realm settings** → **Action** → **Partial export**
3. Enable both toggles (groups/roles and clients) → **Export**
4. Save the file as `keycloak/realm-export.json`
5. Run `docker compose down -v && docker compose up --build`

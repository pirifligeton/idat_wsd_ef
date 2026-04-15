-- ─── Creación de bases de datos por microservicio ────────────────────────────
-- Ejecutado automáticamente por PostgreSQL al iniciar el contenedor por primera vez.
-- Cada microservicio tiene su propia base de datos aislada (patrón microservicios).
-- Las tablas las crea Hibernate automáticamente al arrancar cada servicio.

CREATE DATABASE pacientes_db;
CREATE DATABASE medicos_db;
CREATE DATABASE citas_db;
CREATE DATABASE especialidades_db;
CREATE DATABASE historial_db;

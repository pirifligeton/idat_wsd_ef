package com.citas.gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuración de rutas del API Gateway.
 *
 * Cada ruta define:
 *  - Un prefijo de entrada  (ej. /pacientes/**)
 *  - Un filtro StripPrefix  (elimina el prefijo antes de reenviar)
 *  - Una URI de destino     (lb:// usa Eureka para resolver la ubicación)
 *
 * Ejemplo de flujo:
 *  GET http://localhost:9090/pacientes/api/pacientes
 *      → StripPrefix elimina /pacientes
 *      → Eureka resuelve lb://servicio-pacientes → localhost:8081
 *      → GET http://localhost:8081/api/pacientes
 */
@Configuration
public class GatewayRoutesConfig {

    @Bean
    public RouteLocator rutasGateway(RouteLocatorBuilder builder) {
        return builder.routes()

                // ── Servicio de Pacientes ──────────────────────────────────
                .route("ruta-pacientes", r -> r
                        .path("/pacientes/**")
                        .filters(f -> f.stripPrefix(1))
                        .uri("lb://servicio-pacientes"))

                // ── Servicio de Médicos ────────────────────────────────────
                .route("ruta-medicos", r -> r
                        .path("/medicos/**")
                        .filters(f -> f.stripPrefix(1))
                        .uri("lb://servicio-medicos"))

                // ── Servicio de Citas ──────────────────────────────────────
                .route("ruta-citas", r -> r
                        .path("/citas/**")
                        .filters(f -> f.stripPrefix(1))
                        .uri("lb://servicio-citas"))

                // ── Servicio de Especialidades ─────────────────────────────
                .route("ruta-especialidades", r -> r
                        .path("/especialidades/**")
                        .filters(f -> f.stripPrefix(1))
                        .uri("lb://servicio-especialidades"))

                // ── Servicio de Historial Médico ───────────────────────────
                .route("ruta-historial", r -> r
                        .path("/historial/**")
                        .filters(f -> f.stripPrefix(1))
                        .uri("lb://servicio-historial-medico"))

                .build();
    }

}

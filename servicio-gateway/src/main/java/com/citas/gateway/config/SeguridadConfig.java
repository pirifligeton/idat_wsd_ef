package com.citas.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

/**
 * Configuración de seguridad OAuth2 para el API Gateway.
 *
 * El gateway actúa como Resource Server — no emite tokens, solo los valida.
 * Los tokens son emitidos por Keycloak y validados usando su clave pública
 * obtenida automáticamente desde el issuer-uri configurado en application.properties.
 *
 * Reglas de acceso:
 *  - GET /especialidades/**  → público (catálogo de especialidades)
 *  - GET /medicos/**         → público (listado de médicos)
 *  - Todo lo demás           → requiere token JWT válido
 */
@Configuration
@EnableWebFluxSecurity
public class SeguridadConfig {

    @Bean
    public SecurityWebFilterChain filtroSeguridad(ServerHttpSecurity http) {
        return http
                .csrf(csrf -> csrf.disable())
                .authorizeExchange(exchanges -> exchanges

                        // Endpoints públicos — no requieren token
                        .pathMatchers(HttpMethod.GET, "/especialidades/**").permitAll()
                        .pathMatchers(HttpMethod.GET, "/medicos/**").permitAll()

                        // Todo lo demás requiere autenticación con JWT válido
                        .anyExchange().authenticated()
                )
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(Customizer.withDefaults())
                )
                .build();
    }

}

package com.citas.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Punto de entrada del API Gateway del Sistema de Citas Médicas.
 *
 * Responsabilidades:
 *  - Enrutar peticiones entrantes hacia el microservicio correspondiente.
 *  - Validar tokens JWT emitidos por Keycloak (OAuth2 Resource Server).
 *  - Resolver ubicaciones de servicios dinámicamente vía Eureka (lb://).
 *
 * Puerto: 9090
 * Dashboard Eureka: http://localhost:8761
 * Keycloak:        http://localhost:8180
 */
@SpringBootApplication
@EnableDiscoveryClient
public class GatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }

}

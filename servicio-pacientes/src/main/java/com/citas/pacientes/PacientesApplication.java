package com.citas.pacientes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Punto de entrada del servicio de pacientes.
 * Forma parte del Sistema de Citas Médicas.
 */
@SpringBootApplication
@EnableDiscoveryClient
public class PacientesApplication {

    public static void main(String[] args) {
        SpringApplication.run(PacientesApplication.class, args);
    }

}

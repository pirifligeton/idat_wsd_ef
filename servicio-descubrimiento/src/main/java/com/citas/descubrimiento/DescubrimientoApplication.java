package com.citas.descubrimiento;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * Servidor de descubrimiento de servicios para el Sistema de Citas Médicas.
 * Todos los microservicios se registran aquí al iniciar.
 * Dashboard disponible en http://localhost:8761
 */
@SpringBootApplication
@EnableEurekaServer
public class DescubrimientoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DescubrimientoApplication.class, args);
    }

}

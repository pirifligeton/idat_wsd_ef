package com.citas.medicos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * Punto de entrada del servicio de médicos.
 * Forma parte del Sistema de Citas Médicas.
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class MedicosApplication {

    public static void main(String[] args) {
        SpringApplication.run(MedicosApplication.class, args);
    }

}

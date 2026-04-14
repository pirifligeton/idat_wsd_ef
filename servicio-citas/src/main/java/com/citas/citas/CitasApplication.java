package com.citas.citas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * Punto de entrada del servicio de citas médicas.
 * Forma parte del Sistema de Citas Médicas.
 * Se comunica con servicio-pacientes y servicio-medicos vía OpenFeign.
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class CitasApplication {

    public static void main(String[] args) {
        SpringApplication.run(CitasApplication.class, args);
    }

}

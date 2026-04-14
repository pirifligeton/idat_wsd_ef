package com.citas.especialidades;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * Punto de entrada del servicio de especialidades médicas.
 * Forma parte del Sistema de Citas Médicas.
 * Se comunica con servicio-medicos vía OpenFeign.
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class EspecialidadesApplication {

    public static void main(String[] args) {
        SpringApplication.run(EspecialidadesApplication.class, args);
    }

}

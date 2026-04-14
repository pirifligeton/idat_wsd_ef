package com.citas.historial;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Punto de entrada del servicio de historial médico.
 * Forma parte del Sistema de Citas Médicas.
 * Solo recibe escrituras desde servicio-citas (vía Feign al completar una cita).
 * Los registros son inmutables — no se permiten actualizaciones ni eliminaciones.
 */
@SpringBootApplication
@EnableDiscoveryClient
public class HistorialApplication {

    public static void main(String[] args) {
        SpringApplication.run(HistorialApplication.class, args);
    }

}

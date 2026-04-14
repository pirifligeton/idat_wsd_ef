package com.citas.citas.cliente;

import com.citas.citas.dto.PacienteDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Cliente Feign para comunicarse con servicio-pacientes.
 * Utiliza el nombre registrado en Eureka para resolver la URL dinámicamente.
 */
@FeignClient(name = "servicio-pacientes", url = "${servicios.pacientes.url:http://localhost:8081}")
public interface PacienteCliente {

    @GetMapping("/api/pacientes/{id}")
    PacienteDto obtenerPorId(@PathVariable("id") Long id);

}

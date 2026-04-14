package com.citas.citas.cliente;

import com.citas.citas.dto.MedicoDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Cliente Feign para comunicarse con servicio-medicos.
 * Utiliza el nombre registrado en Eureka para resolver la URL dinámicamente.
 */
@FeignClient(name = "servicio-medicos", url = "${servicios.medicos.url:http://localhost:8082}")
public interface MedicoCliente {

    @GetMapping("/api/medicos/{id}")
    MedicoDto obtenerPorId(@PathVariable("id") Long id);

}

package com.citas.medicos.cliente;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Cliente Feign para comunicarse con servicio-especialidades.
 * Se utiliza únicamente para verificar la existencia de una especialidad
 * antes de registrar o actualizar un médico (integridad referencial simulada).
 */
@FeignClient(name = "servicio-especialidades", url = "${servicios.especialidades.url:http://localhost:8084}")
public interface EspecialidadCliente {

    @GetMapping("/api/especialidades/{id}")
    Object verificarExistencia(@PathVariable("id") Long id);

}

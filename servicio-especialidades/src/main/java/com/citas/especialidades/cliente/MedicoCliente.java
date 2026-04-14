package com.citas.especialidades.cliente;

import com.citas.especialidades.dto.MedicoDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * Cliente Feign para comunicarse con servicio-medicos.
 * Obtiene la lista de médicos que pertenecen a una especialidad dada.
 */
@FeignClient(name = "servicio-medicos", url = "${servicios.medicos.url:http://localhost:8082}")
public interface MedicoCliente {

    @GetMapping("/api/medicos/especialidad/{especialidadId}")
    List<MedicoDto> listarPorEspecialidad(@PathVariable("especialidadId") Long especialidadId);

}

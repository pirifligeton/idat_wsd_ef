package com.citas.citas.cliente;

import com.citas.citas.dto.HistorialDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Cliente Feign para comunicarse con servicio-historial-medico.
 * Es invocado automáticamente cuando una cita es marcada como COMPLETADA.
 */
@FeignClient(name = "servicio-historial-medico", url = "${servicios.historial.url:http://localhost:8085}")
public interface HistorialCliente {

    @PostMapping("/api/historial")
    Object registrar(@RequestBody HistorialDto dto);

}

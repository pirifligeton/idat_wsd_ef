package com.citas.medicos.controlador;

import com.citas.medicos.dto.MedicoDto;
import com.citas.medicos.entidad.Medico;
import com.citas.medicos.servicio.MedicoServicio;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para la gestión de médicos.
 * Expone los endpoints del servicio de médicos bajo /api/medicos.
 */
@RestController
@RequestMapping("/api/medicos")
@Tag(name = "Médicos", description = "Operaciones de gestión de médicos")
public class MedicoControlador {

    private final MedicoServicio servicio;

    public MedicoControlador(MedicoServicio servicio) {
        this.servicio = servicio;
    }

    @GetMapping
    @Operation(summary = "Listar todos los médicos registrados")
    public ResponseEntity<List<Medico>> listarTodos() {
        return ResponseEntity.ok(servicio.listarTodos());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener un médico por su ID")
    public ResponseEntity<Medico> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(servicio.obtenerPorId(id));
    }

    @GetMapping("/especialidad/{especialidad}")
    @Operation(summary = "Listar médicos filtrados por especialidad")
    public ResponseEntity<List<Medico>> listarPorEspecialidad(@PathVariable String especialidad) {
        return ResponseEntity.ok(servicio.listarPorEspecialidad(especialidad));
    }

    @PostMapping
    @Operation(summary = "Registrar un nuevo médico")
    public ResponseEntity<Medico> registrar(@RequestBody MedicoDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(servicio.registrar(dto));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar los datos de un médico existente")
    public ResponseEntity<Medico> actualizar(@PathVariable Long id, @RequestBody MedicoDto dto) {
        return ResponseEntity.ok(servicio.actualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un médico por su ID")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        servicio.eliminar(id);
        return ResponseEntity.noContent().build();
    }

}

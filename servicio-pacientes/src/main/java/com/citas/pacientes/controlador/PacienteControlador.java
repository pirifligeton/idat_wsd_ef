package com.citas.pacientes.controlador;

import com.citas.pacientes.dto.PacienteDto;
import com.citas.pacientes.entidad.Paciente;
import com.citas.pacientes.servicio.PacienteServicio;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para la gestión de pacientes.
 * Expone los endpoints del servicio de pacientes bajo /api/pacientes.
 */
@RestController
@RequestMapping("/api/pacientes")
@Tag(name = "Pacientes", description = "Operaciones de gestión de pacientes")
public class PacienteControlador {

    private final PacienteServicio servicio;

    public PacienteControlador(PacienteServicio servicio) {
        this.servicio = servicio;
    }

    @GetMapping
    @Operation(summary = "Listar todos los pacientes registrados")
    public ResponseEntity<List<Paciente>> listarTodos() {
        return ResponseEntity.ok(servicio.listarTodos());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener un paciente por su ID")
    public ResponseEntity<Paciente> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(servicio.obtenerPorId(id));
    }

    @PostMapping
    @Operation(summary = "Registrar un nuevo paciente")
    public ResponseEntity<Paciente> registrar(@RequestBody PacienteDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(servicio.registrar(dto));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar los datos de un paciente existente")
    public ResponseEntity<Paciente> actualizar(@PathVariable Long id, @RequestBody PacienteDto dto) {
        return ResponseEntity.ok(servicio.actualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un paciente por su ID")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        servicio.eliminar(id);
        return ResponseEntity.noContent().build();
    }

}

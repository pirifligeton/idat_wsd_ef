package com.citas.citas.controlador;

import com.citas.citas.dto.CitaDetalleDto;
import com.citas.citas.dto.CitaDto;
import com.citas.citas.entidad.Cita;
import com.citas.citas.entidad.EstadoCita;
import com.citas.citas.servicio.CitaServicio;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para la gestión de citas médicas.
 * Expone los endpoints del servicio de citas bajo /api/citas.
 */
@RestController
@RequestMapping("/api/citas")
@Tag(name = "Citas Médicas", description = "Operaciones de gestión de citas médicas")
public class CitaControlador {

    private final CitaServicio servicio;

    public CitaControlador(CitaServicio servicio) {
        this.servicio = servicio;
    }

    @GetMapping
    @Operation(summary = "Listar todas las citas registradas")
    public ResponseEntity<List<Cita>> listarTodas() {
        return ResponseEntity.ok(servicio.listarTodas());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener detalle completo de una cita (incluye datos del paciente y médico)")
    public ResponseEntity<CitaDetalleDto> obtenerDetalle(@PathVariable Long id) {
        return ResponseEntity.ok(servicio.obtenerDetallePorId(id));
    }

    @GetMapping("/paciente/{pacienteId}")
    @Operation(summary = "Listar todas las citas de un paciente")
    public ResponseEntity<List<Cita>> listarPorPaciente(@PathVariable Long pacienteId) {
        return ResponseEntity.ok(servicio.listarPorPaciente(pacienteId));
    }

    @GetMapping("/medico/{medicoId}")
    @Operation(summary = "Listar todas las citas de un médico")
    public ResponseEntity<List<Cita>> listarPorMedico(@PathVariable Long medicoId) {
        return ResponseEntity.ok(servicio.listarPorMedico(medicoId));
    }

    @PostMapping
    @Operation(summary = "Registrar una nueva cita médica")
    public ResponseEntity<Cita> registrar(@RequestBody CitaDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(servicio.registrar(dto));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar los datos de una cita existente")
    public ResponseEntity<Cita> actualizar(@PathVariable Long id, @RequestBody CitaDto dto) {
        return ResponseEntity.ok(servicio.actualizar(id, dto));
    }

    @PatchMapping("/{id}/estado")
    @Operation(summary = "Cambiar únicamente el estado de una cita (PENDIENTE, CONFIRMADA, CANCELADA, COMPLETADA)")
    public ResponseEntity<Cita> cambiarEstado(@PathVariable Long id,
                                              @RequestParam EstadoCita nuevoEstado) {
        return ResponseEntity.ok(servicio.cambiarEstado(id, nuevoEstado));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar una cita por su ID")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        servicio.eliminar(id);
        return ResponseEntity.noContent().build();
    }

}

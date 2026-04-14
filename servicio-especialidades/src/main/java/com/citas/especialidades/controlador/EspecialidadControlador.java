package com.citas.especialidades.controlador;

import com.citas.especialidades.dto.EspecialidadDetalleDto;
import com.citas.especialidades.dto.EspecialidadDto;
import com.citas.especialidades.entidad.Especialidad;
import com.citas.especialidades.servicio.EspecialidadServicio;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para la gestión de especialidades médicas.
 * Expone los endpoints del servicio de especialidades bajo /api/especialidades.
 */
@RestController
@RequestMapping("/api/especialidades")
@Tag(name = "Especialidades", description = "Operaciones de gestión de especialidades médicas")
public class EspecialidadControlador {

    private final EspecialidadServicio servicio;

    public EspecialidadControlador(EspecialidadServicio servicio) {
        this.servicio = servicio;
    }

    @GetMapping
    @Operation(summary = "Listar todas las especialidades registradas")
    public ResponseEntity<List<Especialidad>> listarTodas() {
        return ResponseEntity.ok(servicio.listarTodas());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener una especialidad por su ID")
    public ResponseEntity<Especialidad> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(servicio.obtenerPorId(id));
    }

    @GetMapping("/{id}/medicos")
    @Operation(summary = "Obtener una especialidad junto con la lista de médicos que la ejercen")
    public ResponseEntity<EspecialidadDetalleDto> obtenerConMedicos(@PathVariable Long id) {
        return ResponseEntity.ok(servicio.obtenerConMedicos(id));
    }

    @GetMapping("/area/{areaClinica}")
    @Operation(summary = "Listar especialidades filtradas por área clínica")
    public ResponseEntity<List<Especialidad>> listarPorAreaClinica(@PathVariable String areaClinica) {
        return ResponseEntity.ok(servicio.listarPorAreaClinica(areaClinica));
    }

    @PostMapping
    @Operation(summary = "Registrar una nueva especialidad médica")
    public ResponseEntity<Especialidad> registrar(@RequestBody EspecialidadDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(servicio.registrar(dto));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar los datos de una especialidad existente")
    public ResponseEntity<Especialidad> actualizar(@PathVariable Long id, @RequestBody EspecialidadDto dto) {
        return ResponseEntity.ok(servicio.actualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar una especialidad por su ID")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        servicio.eliminar(id);
        return ResponseEntity.noContent().build();
    }

}

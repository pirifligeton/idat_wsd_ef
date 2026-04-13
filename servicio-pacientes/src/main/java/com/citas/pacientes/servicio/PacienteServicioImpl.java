package com.citas.pacientes.servicio;

import com.citas.pacientes.dto.PacienteDto;
import com.citas.pacientes.entidad.Paciente;
import com.citas.pacientes.repositorio.PacienteRepositorio;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementación del servicio de gestión de pacientes.
 */
@Service
public class PacienteServicioImpl implements PacienteServicio {

    private final PacienteRepositorio repositorio;

    public PacienteServicioImpl(PacienteRepositorio repositorio) {
        this.repositorio = repositorio;
    }

    @Override
    public List<Paciente> listarTodos() {
        return repositorio.findAll();
    }

    @Override
    public Paciente obtenerPorId(Long id) {
        return repositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("Paciente no encontrado con id: " + id));
    }

    @Override
    public Paciente registrar(PacienteDto dto) {
        if (repositorio.existsByNumeroCedula(dto.getNumeroCedula())) {
            throw new RuntimeException("Ya existe un paciente con la cédula: " + dto.getNumeroCedula());
        }
        if (repositorio.existsByCorreoElectronico(dto.getCorreoElectronico())) {
            throw new RuntimeException("Ya existe un paciente con el correo: " + dto.getCorreoElectronico());
        }

        Paciente nuevoPaciente = new Paciente(
                dto.getNombre(),
                dto.getApellido(),
                dto.getFechaNacimiento(),
                dto.getTelefono(),
                dto.getCorreoElectronico(),
                dto.getNumeroCedula(),
                dto.getGrupoSanguineo()
        );

        return repositorio.save(nuevoPaciente);
    }

    @Override
    public Paciente actualizar(Long id, PacienteDto dto) {
        Paciente pacienteExistente = obtenerPorId(id);

        pacienteExistente.setNombre(dto.getNombre());
        pacienteExistente.setApellido(dto.getApellido());
        pacienteExistente.setFechaNacimiento(dto.getFechaNacimiento());
        pacienteExistente.setTelefono(dto.getTelefono());
        pacienteExistente.setCorreoElectronico(dto.getCorreoElectronico());
        pacienteExistente.setNumeroCedula(dto.getNumeroCedula());
        pacienteExistente.setGrupoSanguineo(dto.getGrupoSanguineo());

        return repositorio.save(pacienteExistente);
    }

    @Override
    public void eliminar(Long id) {
        Paciente paciente = obtenerPorId(id);
        repositorio.delete(paciente);
    }

}

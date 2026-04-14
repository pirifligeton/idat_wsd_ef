package com.citas.medicos.servicio;

import com.citas.medicos.dto.MedicoDto;
import com.citas.medicos.entidad.Medico;
import com.citas.medicos.excepcion.RecursoNoEncontradoException;
import com.citas.medicos.excepcion.RecursoYaExisteException;
import com.citas.medicos.repositorio.MedicoRepositorio;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementación del servicio de gestión de médicos.
 */
@Service
public class MedicoServicioImpl implements MedicoServicio {

    private final MedicoRepositorio repositorio;

    public MedicoServicioImpl(MedicoRepositorio repositorio) {
        this.repositorio = repositorio;
    }

    @Override
    public List<Medico> listarTodos() {
        return repositorio.findAll();
    }

    @Override
    public Medico obtenerPorId(Long id) {
        return repositorio.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Médico no encontrado con id: " + id));
    }

    @Override
    public List<Medico> listarPorEspecialidad(String especialidad) {
        return repositorio.findByEspecialidad(especialidad);
    }

    @Override
    public Medico registrar(MedicoDto dto) {
        if (repositorio.existsByNumeroColegiado(dto.getNumeroColegiado())) {
            throw new RecursoYaExisteException("Ya existe un médico con el número colegiado: " + dto.getNumeroColegiado());
        }
        if (repositorio.existsByCorreoElectronico(dto.getCorreoElectronico())) {
            throw new RecursoYaExisteException("Ya existe un médico con el correo: " + dto.getCorreoElectronico());
        }

        Medico nuevoMedico = new Medico(
                dto.getNombre(),
                dto.getApellido(),
                dto.getEspecialidad(),
                dto.getNumeroColegiado(),
                dto.getTelefono(),
                dto.getCorreoElectronico()
        );

        return repositorio.save(nuevoMedico);
    }

    @Override
    public Medico actualizar(Long id, MedicoDto dto) {
        Medico medicoExistente = repositorio.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Médico no encontrado con id: " + id));

        medicoExistente.setNombre(dto.getNombre());
        medicoExistente.setApellido(dto.getApellido());
        medicoExistente.setEspecialidad(dto.getEspecialidad());
        medicoExistente.setNumeroColegiado(dto.getNumeroColegiado());
        medicoExistente.setTelefono(dto.getTelefono());
        medicoExistente.setCorreoElectronico(dto.getCorreoElectronico());

        return repositorio.save(medicoExistente);
    }

    @Override
    public void eliminar(Long id) {
        Medico medico = repositorio.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Médico no encontrado con id: " + id));
        repositorio.delete(medico);
    }

}

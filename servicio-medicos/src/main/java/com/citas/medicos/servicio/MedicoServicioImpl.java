package com.citas.medicos.servicio;

import com.citas.medicos.cliente.EspecialidadCliente;
import com.citas.medicos.dto.MedicoDto;
import com.citas.medicos.entidad.Medico;
import com.citas.medicos.excepcion.RecursoNoEncontradoException;
import com.citas.medicos.excepcion.RecursoYaExisteException;
import com.citas.medicos.excepcion.ServicioNoDisponibleException;
import com.citas.medicos.repositorio.MedicoRepositorio;
import feign.FeignException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementación del servicio de gestión de médicos.
 * Verifica la existencia del especialidadId en servicio-especialidades
 * antes de persistir, simulando integridad referencial sin FK entre bases de datos.
 */
@Service
public class MedicoServicioImpl implements MedicoServicio {

    private final MedicoRepositorio repositorio;
    private final EspecialidadCliente especialidadCliente;

    public MedicoServicioImpl(MedicoRepositorio repositorio,
                              EspecialidadCliente especialidadCliente) {
        this.repositorio = repositorio;
        this.especialidadCliente = especialidadCliente;
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
    public List<Medico> listarPorEspecialidad(Long especialidadId) {
        return repositorio.findByEspecialidadId(especialidadId);
    }

    @Override
    public Medico registrar(MedicoDto dto) {
        // Verificar existencia de la especialidad antes de guardar
        verificarEspecialidad(dto.getEspecialidadId());

        if (repositorio.existsByNumeroColegiado(dto.getNumeroColegiado())) {
            throw new RecursoYaExisteException("Ya existe un médico con el número colegiado: " + dto.getNumeroColegiado());
        }
        if (repositorio.existsByCorreoElectronico(dto.getCorreoElectronico())) {
            throw new RecursoYaExisteException("Ya existe un médico con el correo: " + dto.getCorreoElectronico());
        }

        Medico nuevoMedico = new Medico(
                dto.getNombre(),
                dto.getApellido(),
                dto.getEspecialidadId(),
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

        // Verificar existencia de la especialidad antes de actualizar
        verificarEspecialidad(dto.getEspecialidadId());

        medicoExistente.setNombre(dto.getNombre());
        medicoExistente.setApellido(dto.getApellido());
        medicoExistente.setEspecialidadId(dto.getEspecialidadId());
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

    /**
     * Verifica que la especialidad exista en servicio-especialidades.
     * Si el ID no existe → 422. Si el servicio no responde → 422.
     */
    private void verificarEspecialidad(Long especialidadId) {
        try {
            especialidadCliente.verificarExistencia(especialidadId);
        } catch (FeignException.NotFound ex) {
            throw new ServicioNoDisponibleException(
                    "No existe una especialidad con id: " + especialidadId);
        } catch (FeignException ex) {
            throw new ServicioNoDisponibleException(
                    "El servicio de especialidades no está disponible en este momento.");
        }
    }

}

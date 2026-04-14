package com.citas.especialidades.servicio;

import com.citas.especialidades.cliente.MedicoCliente;
import com.citas.especialidades.dto.EspecialidadDetalleDto;
import com.citas.especialidades.dto.EspecialidadDto;
import com.citas.especialidades.dto.MedicoDto;
import com.citas.especialidades.entidad.Especialidad;
import com.citas.especialidades.excepcion.RecursoNoEncontradoException;
import com.citas.especialidades.excepcion.RecursoYaExisteException;
import com.citas.especialidades.excepcion.ServicioNoDisponibleException;
import com.citas.especialidades.repositorio.EspecialidadRepositorio;
import feign.FeignException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementación del servicio de gestión de especialidades médicas.
 * Se comunica con servicio-medicos para enriquecer la respuesta de detalle.
 */
@Service
public class EspecialidadServicioImpl implements EspecialidadServicio {

    private final EspecialidadRepositorio repositorio;
    private final MedicoCliente medicoCliente;

    public EspecialidadServicioImpl(EspecialidadRepositorio repositorio,
                                    MedicoCliente medicoCliente) {
        this.repositorio = repositorio;
        this.medicoCliente = medicoCliente;
    }

    @Override
    public List<Especialidad> listarTodas() {
        return repositorio.findAll();
    }

    @Override
    public Especialidad obtenerPorId(Long id) {
        return repositorio.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Especialidad no encontrada con id: " + id));
    }

    @Override
    public EspecialidadDetalleDto obtenerConMedicos(Long id) {
        Especialidad especialidad = obtenerPorId(id);

        // Llamada a servicio-medicos para obtener los médicos de esta especialidad
        List<MedicoDto> medicos;
        try {
            medicos = medicoCliente.listarPorEspecialidad(id);
        } catch (FeignException ex) {
            throw new ServicioNoDisponibleException("El servicio de médicos no está disponible en este momento.");
        }

        EspecialidadDetalleDto detalle = new EspecialidadDetalleDto();
        detalle.setId(especialidad.getId());
        detalle.setNombre(especialidad.getNombre());
        detalle.setDescripcion(especialidad.getDescripcion());
        detalle.setCodigoEspecialidad(especialidad.getCodigoEspecialidad());
        detalle.setAreaClinica(especialidad.getAreaClinica());
        detalle.setMedicos(medicos);

        return detalle;
    }

    @Override
    public List<Especialidad> listarPorAreaClinica(String areaClinica) {
        return repositorio.findByAreaClinica(areaClinica);
    }

    @Override
    public Especialidad registrar(EspecialidadDto dto) {
        if (repositorio.existsByNombre(dto.getNombre())) {
            throw new RecursoYaExisteException("Ya existe una especialidad con el nombre: " + dto.getNombre());
        }
        if (repositorio.existsByCodigoEspecialidad(dto.getCodigoEspecialidad())) {
            throw new RecursoYaExisteException("Ya existe una especialidad con el código: " + dto.getCodigoEspecialidad());
        }

        Especialidad nueva = new Especialidad(
                dto.getNombre(),
                dto.getDescripcion(),
                dto.getCodigoEspecialidad(),
                dto.getAreaClinica()
        );

        return repositorio.save(nueva);
    }

    @Override
    public Especialidad actualizar(Long id, EspecialidadDto dto) {
        Especialidad existente = obtenerPorId(id);

        existente.setNombre(dto.getNombre());
        existente.setDescripcion(dto.getDescripcion());
        existente.setCodigoEspecialidad(dto.getCodigoEspecialidad());
        existente.setAreaClinica(dto.getAreaClinica());

        return repositorio.save(existente);
    }

    @Override
    public void eliminar(Long id) {
        Especialidad especialidad = obtenerPorId(id);
        repositorio.delete(especialidad);
    }

}

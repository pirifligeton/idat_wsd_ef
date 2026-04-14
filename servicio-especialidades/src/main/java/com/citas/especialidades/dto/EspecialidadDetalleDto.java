package com.citas.especialidades.dto;

import java.util.List;

/**
 * DTO enriquecido que combina los datos de la especialidad con la lista
 * de médicos que la ejercen, obtenidos desde servicio-medicos vía Feign.
 * Es retornado en el endpoint GET /api/especialidades/{id}/medicos.
 */
public class EspecialidadDetalleDto {

    private Long id;
    private String nombre;
    private String descripcion;
    private String codigoEspecialidad;
    private String areaClinica;

    // Lista de médicos obtenida desde servicio-medicos
    private List<MedicoDto> medicos;

    public EspecialidadDetalleDto() {}

    // Getters y Setters

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getCodigoEspecialidad() { return codigoEspecialidad; }
    public void setCodigoEspecialidad(String codigoEspecialidad) { this.codigoEspecialidad = codigoEspecialidad; }

    public String getAreaClinica() { return areaClinica; }
    public void setAreaClinica(String areaClinica) { this.areaClinica = areaClinica; }

    public List<MedicoDto> getMedicos() { return medicos; }
    public void setMedicos(List<MedicoDto> medicos) { this.medicos = medicos; }

}

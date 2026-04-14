package com.citas.especialidades.dto;

/**
 * DTO utilizado para recibir datos al crear o actualizar una especialidad médica.
 */
public class EspecialidadDto {

    private String nombre;
    private String descripcion;
    private String codigoEspecialidad;
    private String areaClinica;

    public EspecialidadDto() {}

    // Getters y Setters

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getCodigoEspecialidad() { return codigoEspecialidad; }
    public void setCodigoEspecialidad(String codigoEspecialidad) { this.codigoEspecialidad = codigoEspecialidad; }

    public String getAreaClinica() { return areaClinica; }
    public void setAreaClinica(String areaClinica) { this.areaClinica = areaClinica; }

}

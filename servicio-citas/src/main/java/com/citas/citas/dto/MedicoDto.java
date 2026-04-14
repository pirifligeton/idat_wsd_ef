package com.citas.citas.dto;

/**
 * DTO que mapea la respuesta recibida desde servicio-medicos.
 * Refleja los campos expuestos por el endpoint GET /api/medicos/{id}.
 * El campo especialidadId es la referencia lógica hacia servicio-especialidades.
 */
public class MedicoDto {

    private Long id;
    private String nombre;
    private String apellido;
    private Long especialidadId;
    private String numeroColegiado;
    private String telefono;
    private String correoElectronico;

    public MedicoDto() {}

    // Getters y Setters

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }

    public Long getEspecialidadId() { return especialidadId; }
    public void setEspecialidadId(Long especialidadId) { this.especialidadId = especialidadId; }

    public String getNumeroColegiado() { return numeroColegiado; }
    public void setNumeroColegiado(String numeroColegiado) { this.numeroColegiado = numeroColegiado; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getCorreoElectronico() { return correoElectronico; }
    public void setCorreoElectronico(String correoElectronico) { this.correoElectronico = correoElectronico; }

}

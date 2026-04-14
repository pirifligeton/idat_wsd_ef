package com.citas.especialidades.entidad;

import jakarta.persistence.*;

/**
 * Entidad que representa una especialidad médica en el sistema.
 * Es referenciada lógicamente por servicio-medicos mediante especialidadId.
 */
@Entity
@Table(name = "especialidades")
public class Especialidad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nombre;

    @Column(nullable = false)
    private String descripcion;

    @Column(name = "codigo_especialidad", nullable = false, unique = true)
    private String codigoEspecialidad;

    @Column(name = "area_clinica", nullable = false)
    private String areaClinica;

    // Constructor vacío requerido por JPA
    public Especialidad() {}

    public Especialidad(String nombre, String descripcion,
                        String codigoEspecialidad, String areaClinica) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.codigoEspecialidad = codigoEspecialidad;
        this.areaClinica = areaClinica;
    }

    // Getters y Setters

    public Long getId() { return id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getCodigoEspecialidad() { return codigoEspecialidad; }
    public void setCodigoEspecialidad(String codigoEspecialidad) { this.codigoEspecialidad = codigoEspecialidad; }

    public String getAreaClinica() { return areaClinica; }
    public void setAreaClinica(String areaClinica) { this.areaClinica = areaClinica; }

}

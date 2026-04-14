package com.citas.medicos.entidad;

import jakarta.persistence.*;

/**
 * Entidad que representa a un médico registrado en el sistema.
 * El campo especialidadId es una referencia lógica hacia servicio-especialidades
 * (sin FK directa entre bases de datos — patrón de microservicios).
 */
@Entity
@Table(name = "medicos")
public class Medico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String apellido;

    @Column(name = "especialidad_id", nullable = false)
    private Long especialidadId;

    @Column(name = "numero_colegiado", unique = true, nullable = false)
    private String numeroColegiado;

    @Column(nullable = false)
    private String telefono;

    @Column(name = "correo_electronico", unique = true, nullable = false)
    private String correoElectronico;

    // Constructor vacío requerido por JPA
    public Medico() {}

    public Medico(String nombre, String apellido, Long especialidadId,
                  String numeroColegiado, String telefono, String correoElectronico) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.especialidadId = especialidadId;
        this.numeroColegiado = numeroColegiado;
        this.telefono = telefono;
        this.correoElectronico = correoElectronico;
    }

    // Getters y Setters

    public Long getId() { return id; }

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

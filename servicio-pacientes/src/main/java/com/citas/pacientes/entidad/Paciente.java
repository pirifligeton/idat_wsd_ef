package com.citas.pacientes.entidad;

import jakarta.persistence.*;
import java.time.LocalDate;

/**
 * Entidad que representa a un paciente registrado en el sistema.
 */
@Entity
@Table(name = "pacientes")
public class Paciente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String apellido;

    @Column(name = "fecha_nacimiento")
    private LocalDate fechaNacimiento;

    @Column(nullable = false)
    private String telefono;

    @Column(name = "correo_electronico", unique = true)
    private String correoElectronico;

    @Column(name = "numero_cedula", unique = true, nullable = false)
    private String numeroCedula;

    @Column(name = "grupo_sanguineo")
    private String grupoSanguineo;

    // Constructor vacío requerido por JPA
    public Paciente() {}

    public Paciente(String nombre, String apellido, LocalDate fechaNacimiento,
                    String telefono, String correoElectronico,
                    String numeroCedula, String grupoSanguineo) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.fechaNacimiento = fechaNacimiento;
        this.telefono = telefono;
        this.correoElectronico = correoElectronico;
        this.numeroCedula = numeroCedula;
        this.grupoSanguineo = grupoSanguineo;
    }

    // Getters y Setters

    public Long getId() { return id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }

    public LocalDate getFechaNacimiento() { return fechaNacimiento; }
    public void setFechaNacimiento(LocalDate fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getCorreoElectronico() { return correoElectronico; }
    public void setCorreoElectronico(String correoElectronico) { this.correoElectronico = correoElectronico; }

    public String getNumeroCedula() { return numeroCedula; }
    public void setNumeroCedula(String numeroCedula) { this.numeroCedula = numeroCedula; }

    public String getGrupoSanguineo() { return grupoSanguineo; }
    public void setGrupoSanguineo(String grupoSanguineo) { this.grupoSanguineo = grupoSanguineo; }

}

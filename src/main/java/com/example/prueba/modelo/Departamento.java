package com.example.prueba.modelo;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "DEPARTAMENTO")

public class Departamento  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DEPAR_ID")
    private long idDepartamento;

    @Column(length = 50 ,name = "nombre_Departamento", nullable = false)
    private String nombreDepartamento;


    /**
     * TODO para este caso es mejor una lista porque es más liviana
     * cambio el Set no permite repetidos
     */
    @OneToMany(mappedBy="departamento")
    @JsonIgnore
    private Set<Empleado> empleados = new HashSet<>();



    public Departamento() {
        super();
    }

    public long getIdDepartamento() {
        return idDepartamento;
    }

    public void setIdDepartamento(long idDepartamento) {
        this.idDepartamento = idDepartamento;
    }

    public String getNombreDepartamento() {
        return nombreDepartamento;
    }

    public void setNombreDepartamento(String nombreDepartamento) {
        this.nombreDepartamento = nombreDepartamento;
    }

    public Set<Empleado> getEmpleados() {
        return empleados;
    }

    public void setEmpleados(Set<Empleado> empleados) {
        this.empleados = empleados;
    }
}

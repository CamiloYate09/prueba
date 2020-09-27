package com.example.prueba.modelo;


import javax.persistence.*;

@Entity
@Table(name = "EMPLEADO")
public class Empleado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(length = 50, name = "nombre", nullable = false)
    private String nombre;

    @Column(length = 50, name = "cargo", nullable = false)
    private String cargo;

    @Column(length = 18, name = "salario", nullable = false)
    private Double salario;

    @Column(name = "tiempo_completo", nullable = false)
    private Boolean timepoCompleto;

    @ManyToOne
    @JoinColumn(name="departamento", referencedColumnName="DEPAR_ID")
    private Departamento departamento ;



    public Empleado() {
        super();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public Double getSalario() {
        return salario;
    }

    public void setSalario(Double salario) {
        this.salario = salario;
    }

    public Boolean getTimepoCompleto() {
        return timepoCompleto;
    }

    public void setTimepoCompleto(Boolean timepoCompleto) {
        this.timepoCompleto = timepoCompleto;
    }

    public Departamento getDepartamento() {
        return departamento;
    }

    public void setDepartamento(Departamento departamento) {
        this.departamento = departamento;
    }

}

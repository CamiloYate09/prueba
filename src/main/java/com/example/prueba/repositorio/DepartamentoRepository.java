package com.example.prueba.repositorio;

import com.example.prueba.modelo.Departamento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DepartamentoRepository extends JpaRepository<Departamento, Long> {


    @Query("SELECT u FROM Departamento u WHERE u.nombreDepartamento = ?1")
    Departamento findAllActiveDepartamenteIndex(String nombre);

    @Query("SELECT e.nombre, e.salario, e.cargo, e.timepoCompleto, d.idDepartamento, d.nombreDepartamento FROM Departamento d, Empleado  e  WHERE  d.idDepartamento = e.departamento.idDepartamento AND  d.idDepartamento = ?1")
    Page<Departamento> empleadosDepartamentoID(Long departamentoId, Pageable pageable);
}

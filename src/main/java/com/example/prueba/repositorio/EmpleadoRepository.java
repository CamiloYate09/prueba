package com.example.prueba.repositorio;

//import com.example.prueba.modelo.Departamento;

import com.example.prueba.modelo.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EmpleadoRepository extends JpaRepository<Empleado, Long> {

    /**
     * SQL nativo de PostrgreSQL
     * Query obtiene los 5 empleados con los salarios mas altos
     * @return
     */
    @Query(value = "SELECT e.nombre,e.cargo,  max(e.salario) AS SALARIO  FROM EMPLEADO e GROUP BY e.nombre, e.cargo, e.salario limit 5"
            , nativeQuery = true)
    List empleadosSalarioTop();



    @Query(value = "select d.nombre_departamento, sum(e.salario)  from departamento d , empleado e where e.departamento = d.depar_id GROUP BY d.nombre_departamento"
            , nativeQuery = true)
    List empleadosSalarioDepartamento();


}

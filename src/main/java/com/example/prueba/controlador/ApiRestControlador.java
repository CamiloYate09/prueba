package com.example.prueba.controlador;


import com.example.prueba.Negocio.EmpleadoNegocio;
import com.example.prueba.exception.ResourceNotFoundException;
import com.example.prueba.modelo.Departamento;
import com.example.prueba.modelo.Empleado;
import com.example.prueba.repositorio.DepartamentoRepository;
import com.example.prueba.repositorio.EmpleadoRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

/**
 * Rest encargado de servir las solicitudes
 */
@RestController
@RequestMapping("api1")
public class ApiRestControlador {
    Logger logger = LoggerFactory.getLogger(ApiRestControlador.class);

    @Autowired
    private EmpleadoNegocio empleadoNegocio;

    @Autowired
    private EmpleadoRepository empleadoRepository;


    @Autowired
    private DepartamentoRepository departamentoRepository;

    /*
Crear Empleados con sus departamentos
* */
    @PostMapping(value = "/crearEmpleado", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}, produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity crearEmpleado(@RequestParam(value = "files") MultipartFile[] files) throws Exception, JsonProcessingException, ResourceNotFoundException {
        logger.info("Ingreso al método crearEmpleado");

        for (MultipartFile file : files) {
            empleadoNegocio.crearEmpleados(file);
        }

        return ResponseEntity.status(HttpStatus.CREATED).build();

    }

    /**
     * Consulta todos los empleados
     * @return
     * @throws JsonProcessingException
     * @throws ResourceNotFoundException
     */
    @GetMapping(value = "/todos", produces = "application/json")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public CompletableFuture<ResponseEntity<List<Empleado>>> findAllEmpleados() throws JsonProcessingException, ResourceNotFoundException {
        return empleadoNegocio.findAllEmpleados().thenApply(ResponseEntity::ok);
    }


    /**
     * Consulta los 5 salarios mas altos
     * @return
     * @throws JsonProcessingException
     * @throws ResourceNotFoundException
     */
    @GetMapping(value = "/salario", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List> empeladosSalario()throws JsonProcessingException, ResourceNotFoundException  {
        logger.info("Ingreso al método empeladosSalario");
        ResponseEntity<List> respuesta = null;
        List empleado = empleadoRepository.empleadosSalarioTop();
        logger.info("Ingreso al método empeladosSalario {}", empleado);
        return respuesta.ok().body(empleado);


    }

    /**
     * Consulta los salarios por deparamento agrupados
     * @return
     * @throws JsonProcessingException
     * @throws ResourceNotFoundException
     */
    @GetMapping(value = "/salarioDeparta", produces = "application/json")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<List> empleadosSalarioDepartamento() throws JsonProcessingException, ResourceNotFoundException  {
        logger.info("Ingreso al método empleadosSalarioDepartamento");
        ResponseEntity<List> respuesta = null;
        List empleado = empleadoRepository.empleadosSalarioDepartamento();
        logger.info("Ingreso al método empleadosSalarioDepartamento {}", empleado);
        return respuesta.ok().body(empleado);


    }

    /**
     * Consulta por identificados del Departamento y sus empleados que hacen parte
     * @param departamentoId
     * @param pageable
     * @return
     * @throws JsonProcessingException
     * @throws ResourceNotFoundException
     */

    @GetMapping("/departamento/{departamentoId}")
    public Page<Departamento> empleadosDepartamentoID(@PathVariable (value = "departamentoId") Long departamentoId,
                                                      @RequestParam Optional<Integer> page , @RequestParam Optional<String> orderBY)throws ResourceNotFoundException  {
        logger.info("Ingreso al método empleadosDepartamentoID");
        logger.info("departamentoId {} ",departamentoId);
        Departamento departamentoID = departamentoRepository.findById(departamentoId).orElseThrow(() -> new ResourceNotFoundException("No se encontró Información con el Identificador :" + departamentoId));
        logger.info("departamentoId {} ",departamentoID.getNombreDepartamento());
        return departamentoRepository.empleadosDepartamentoID(departamentoID.getIdDepartamento(),  PageRequest.of( page.orElse(0),5));
    }

}

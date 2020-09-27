package com.example.prueba.Negocio;

//import com.example.prueba.modelo.Departamento;

import com.example.prueba.modelo.Departamento;
import com.example.prueba.modelo.Empleado;
import com.example.prueba.repositorio.DepartamentoRepository;
import com.example.prueba.repositorio.EmpleadoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;


/**
 * Servicio encargado de leer las lineas de un documento CSV y crear los objectos
 * para ser guardados.
 */
@Service
public class EmpleadoNegocio {


    Logger logger = LoggerFactory.getLogger(EmpleadoNegocio.class);
    @Autowired
    private EmpleadoRepository empleadoRepository;

    @Autowired
    private DepartamentoRepository departamentoRepository;


    /**
     * Clase encargada de el leer el archivo plano y guardar cada empleados con su departamento
     * @param archivo
     * @return
     * @throws Exception
     */
    @Async
    public CompletableFuture<Empleado> crearEmpleados(MultipartFile archivo) throws Exception {

        long start = System.currentTimeMillis();
        Departamento departamento;
        Empleado empleadoRespuesta = null;
        List<Empleado> empleadoLeido = parseCSVArchivo(archivo);
        logger.info("Guardar Lista de Usuario con size {}", empleadoLeido.size(), "" + Thread.currentThread().getName());
        for (Empleado empleado : empleadoLeido) {
             departamento = departamentoRepository.findAllActiveDepartamenteIndex(empleado.getDepartamento().getNombreDepartamento());
            if (Objects.isNull(departamento)) {
                logger.info("*** Inicio: Departamento Núlo ***");
                Departamento valor = new Departamento();
                valor.setNombreDepartamento(empleado.getDepartamento().getNombreDepartamento());
                departamento = departamentoRepository.save(valor);
                logger.info("*** Inicio: Departamento Guardado ***");

            }
//            Departamento idDepartamentoBusqueda = departamentoRepository.findAllActiveDepartamenteIndex(empleado.getDepartamento().getNombreDepartamento());
            if (departamento != null) {
                logger.info("*** Inicio: Departamento Existente ***");
                empleado.setDepartamento(departamento);

                empleadoRespuesta = empleadoRepository.save(empleado);
                logger.info("*** Inicio: Empleado Guardado ***");
            }
        }

        long end = System.currentTimeMillis();
        logger.info("Tiempo Total {}", (end - start));

        return CompletableFuture.completedFuture(empleadoRespuesta);


    }


    /**
     * Método encargado de leer el archivo CSV,
     * @param archivo
     * @return
     * @throws Exception
     */
    private List<Empleado> parseCSVArchivo(final MultipartFile archivo) throws Exception {
        final List<Empleado> empleados = new ArrayList<>();
        try {

            try (final BufferedReader br = new BufferedReader(new InputStreamReader(archivo.getInputStream()))) {
                String linea;

                while ((linea = br.readLine()) != null) {
                    logger.info("*** Inicio: parseCSVArchivo ***");
                    final String[] data = linea.split(",");
                    final Empleado empleado1 = new Empleado();
                    Departamento departamento = new Departamento();
                    empleado1.setNombre(data[0]);
                    empleado1.setCargo(data[1]);
                    empleado1.setSalario(Double.valueOf(data[2]));
                    empleado1.setTimepoCompleto(Boolean.valueOf(data[3]));
                    departamento.setNombreDepartamento(data[4]);
                    empleado1.setDepartamento(departamento);
                    empleados.add(empleado1);
                    logger.info("*** Fin: parseCSVArchivo ***");
                }
                return empleados;
            }


        } catch (final IOException e) {
            logger.error("Fallo la conversion del archivo CSV {}", e);
            throw new Exception("Fallo la conversion del archivo CSV {}", e);
        }

    }

    @Async
    public CompletableFuture<List<Empleado>> findAllEmpleados() {
        logger.info("Obteniedo lista de todos los empleados" + Thread.currentThread().getName());
        List<Empleado> empleado = empleadoRepository.findAll();
        return CompletableFuture.completedFuture(empleado);
    }


}

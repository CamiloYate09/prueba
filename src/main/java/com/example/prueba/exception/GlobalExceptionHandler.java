package com.example.prueba.exception;



import com.example.prueba.modelo.NotificacionErrores;
import com.example.prueba.repositorio.MensajeErrorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

/**
 * Clase encargada de controlar los errores presentados en las peticiones
 */

@ControllerAdvice
public class GlobalExceptionHandler {

    @Autowired
    private MensajeErrorRepository mensajeErrorRepository;


    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> resourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(), request.getDescription(false));
        NotificacionErrores error = new NotificacionErrores();
        error.setRegistroError(new Date());
        error.setTipo(String.valueOf(HttpStatus.NOT_FOUND));
        error.setMensajeError(ex.getMessage());
        error.setDescripcion(request.getDescription(Boolean.TRUE));
        mensajeErrorRepository.save(error);
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> globleExcpetionHandler(Exception ex, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(), request.getDescription(false));
        NotificacionErrores error = new NotificacionErrores();
        error.setRegistroError(new Date());
        error.setTipo(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR));
        error.setMensajeError(ex.getMessage());
        error.setDescripcion(request.getDescription(Boolean.TRUE));
        mensajeErrorRepository.save(error);
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

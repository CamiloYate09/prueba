package com.example.prueba.modelo;


import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "NOTIFICACION_ERRORES")
public class NotificacionErrores {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(length = 50 ,name = "TIPO", nullable = false)
    private String tipo;

    @Column(length = 50 ,name = "MENSAJE_ERROR", nullable = false)
    private String mensajeError;

    @Column(length = 50 ,name = "DESCRIPCION", nullable = false)
    private String descripcion;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "REGISTRO_ERROR", nullable = false)
    private Date registroError;


    public NotificacionErrores() {
        super();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getMensajeError() {
        return mensajeError;
    }

    public void setMensajeError(String mensajeError) {
        this.mensajeError = mensajeError;
    }

    public Date getRegistroError() {
        return registroError;
    }

    public void setRegistroError(Date registroError) {
        this.registroError = registroError;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}

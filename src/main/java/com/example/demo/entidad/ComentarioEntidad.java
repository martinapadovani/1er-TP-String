package com.example.demo.entidad;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Comentario")
public class ComentarioEntidad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (unique = true, nullable = false)
    private Integer id;

    private String comentario;
    private double puntuacion;

    @ManyToOne
    @JoinColumn(name = "producto_id")
    @JsonBackReference
    private ProductoEntidad producto;

    public ComentarioEntidad(){}

    public ComentarioEntidad(Integer id, String comentario, double puntuacion, ProductoEntidad producto) {
        this.id = id;
        this.comentario = comentario;
        this.puntuacion = puntuacion;
        this.producto = producto;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public double getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(double puntuacion) {
        this.puntuacion = puntuacion;
    }

    public ProductoEntidad getProducto() {
        return producto;
    }

    public void setProducto(ProductoEntidad producto) {
        this.producto = producto;
    }

    
    
}

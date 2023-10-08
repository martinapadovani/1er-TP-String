package com.example.demo.entidad;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "Producto")
public class ProductoEntidad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (unique = true, nullable = false)
    private Integer id;
    
    private String title;
    private double price;
    private String category;
    
    @OneToMany(mappedBy = "producto")
    @JsonManagedReference
    private List<ComentarioEntidad> comentarios;

    private double calificacion;

    public ProductoEntidad(){};

    public ProductoEntidad(Integer id, String title, double price, String category,
            List<ComentarioEntidad> comentarios, double calificacion) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.category = category;
        this.comentarios = new ArrayList();
        this.calificacion = calificacion;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<ComentarioEntidad> getComentarios() {
        return comentarios;
    }

    public void setComentarios(List<ComentarioEntidad> comentarios) {
        this.comentarios = comentarios;
    }

    public double getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(double calificacion) {
        this.calificacion = calificacion;
    }  
    
}

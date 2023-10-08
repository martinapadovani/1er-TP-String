package com.example.demo.repositorio;

import java.util.ArrayList;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.core.CrudMethods;
import org.springframework.stereotype.Repository;

import com.example.demo.entidad.ComentarioEntidad;
import com.example.demo.entidad.ProductoEntidad;

@Repository
public interface ComentarioRepositorio extends CrudRepository<ComentarioEntidad, Integer>{

    public abstract ArrayList<ComentarioEntidad> findByProducto(ProductoEntidad productoEntidad);
    
}

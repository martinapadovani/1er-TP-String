package com.example.demo.repositorio;

import java.util.ArrayList;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entidad.ProductoEntidad;

@Repository
public interface ProductoRepositorio extends CrudRepository<ProductoEntidad, Integer> {

    public abstract ArrayList<ProductoEntidad> findByCategory(String categoria);
    
}

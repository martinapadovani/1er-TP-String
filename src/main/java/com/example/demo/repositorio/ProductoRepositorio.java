package com.example.demo.repositorio;

import org.springframework.data.jpa.repository.support.CrudMethodMetadata;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entidad.ProductoEntidad;

@Repository
public interface ProductoRepositorio extends CrudRepository<ProductoEntidad, Integer> {
    
}

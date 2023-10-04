package com.example.demo.servicios;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entidad.ProductoEntidad;
import com.example.demo.repositorio.ProductoRepositorio;

@Service
public class ProductoServicio {

    @Autowired
    ProductoRepositorio productoRepositorio;

    public void agregarProducto(ProductoEntidad  producto){

        productoRepositorio.save(producto);
    }

    public ArrayList<ProductoEntidad> verProductos (){

        return (ArrayList<ProductoEntidad>) productoRepositorio.findAll();
    }
    
}

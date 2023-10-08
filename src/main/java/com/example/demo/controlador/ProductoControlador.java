package com.example.demo.controlador;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entidad.ProductoEntidad;
import com.example.demo.servicios.ProductoServicio;

@RestController
@RequestMapping("/admin")
public class ProductoControlador {

    @Autowired
    ProductoServicio productoServicio;

    //API

    @PostMapping("/api")
    public ProductoEntidad[] guardarProductosApi(){

        return productoServicio.guardarProductosApi();
    }

    //OPERACIONES DESDE LA ADMIN

    @GetMapping("/productos")
    public ArrayList<ProductoEntidad> verProductos(){
        return productoServicio.verProductos();
    }

    @GetMapping("/id/{id}")
    public ProductoEntidad verProductoPorId(@PathVariable("id") Integer id){
        return productoServicio.obtenerProductoPorId(id);
    }

    @GetMapping("/categoria/{categoria}")
    public ArrayList<ProductoEntidad> verProductosCategoria(@PathVariable("categoria") String categoria){
        return productoServicio.buscarPorCategoria(categoria);
    }

    @PostMapping()
    public void agregarProducto(@RequestBody ProductoEntidad producto){
        productoServicio.agregarProducto(producto);
    }

    @PutMapping("/actualizar/{id}")
    public ProductoEntidad actualizarProducto(@PathVariable("id") Integer id, @RequestBody ProductoEntidad producto){
        return productoServicio.actualizarProducto(producto, id);
    }

    @DeleteMapping("/eliminar/{id}")
    public String eliminarProducto(@PathVariable("id") Integer id){

        boolean okEliminacion = productoServicio.eliminarProducto(id);
        if (okEliminacion) {
            return "Se elimino el producto con el id " + id;
        } else {
            return "No se pudo eliminar el producto con el id " + id;
        }
    }
    
}
    
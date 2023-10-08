package com.example.demo.controlador;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entidad.ComentarioEntidad;
import com.example.demo.entidad.ProductoEntidad;
import com.example.demo.servicios.ProductoServicio;

@RestController
@RequestMapping("cliente")
public class ClienteControlador {

    @Autowired
    ProductoServicio productoServicio;

    //OPERACIONES DESDE EL CLIENTE

    @GetMapping("/productos")
    public ArrayList<ProductoEntidad> verProductosCliente(){
        return productoServicio.verProductosCliente();
    }

    @GetMapping("/id/{id}")
    public ProductoEntidad verProductoPorIdCliente(@PathVariable("id") Integer id){
        return productoServicio.obtenerProductoPorIdCliente(id);
    }

    @GetMapping("/categoria/{categoria}")
    public ArrayList<ProductoEntidad> verProductosCategoriaCliente(@PathVariable("categoria") String categoria){
        return productoServicio.buscarPorCategoriaCliente(categoria);
    }

    @PostMapping("/calificar/{id}")
    public void calificar(@PathVariable("id") Integer id, @RequestBody ComentarioEntidad comentario){
        productoServicio.crearComentario(comentario, id);
    }
}

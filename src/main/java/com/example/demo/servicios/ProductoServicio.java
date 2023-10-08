package com.example.demo.servicios;

import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entidad.ComentarioEntidad;
import com.example.demo.entidad.ProductoEntidad;
import com.example.demo.repositorio.ComentarioRepositorio;
import com.example.demo.repositorio.ProductoRepositorio;
import com.google.gson.Gson;

@Service
public class ProductoServicio {

    @Autowired
    ProductoRepositorio productoRepositorio;

    @Autowired
    ComentarioRepositorio comentarioRepositorio;

    //Herramientas

    public ProductoEntidad aumentarPrecioDeCosto(ProductoEntidad producto){
        
        double precio = producto.getPrice();  

        double precioActualizado = precio + precio*0.5; 

        producto.setPrice(precioActualizado);

        return producto;
    }


    //API

    ProductoEntidad[] productosApi; //Inicio un arreglo de productos
    public ProductoEntidad[] guardarProductosApi(){

        try {
            // Realizo una solicitud HTTP GET a la URL, para obtener los productos de la API.
            URL url = new URL("https://fakestoreapi.com/products");
            // Utilizo la clase HttpURLConnection para establecer la conexión y realizar la solicitud GET. 
            HttpURLConnection conexion = (HttpURLConnection) url.openConnection();
            conexion.setRequestMethod("GET");
            conexion.connect();

            int respuestaCodigo = conexion.getResponseCode();

            if (respuestaCodigo != 200) {
                throw new RuntimeException("Ocurrió un error " + respuestaCodigo);
            } else {

                /*Después de obtener la respuesta de la API, 
                instancio InputStreamReader para leer los datos JSON de la respuesta*/
                InputStreamReader inputStreamReader = new InputStreamReader(conexion.getInputStream());
                //Recibe por parametro nuestra conexion

                // Instancio Gson para convertir el JSON a una lista de objetos JAVA
                Gson gson = new Gson();

                /*Llamo al arreglo para guardar los datos de la api,
                 ya que la estructura JSON de los datos que me devuelve la api es: un arreglo de objetos producto */
                productosApi = gson.fromJson(inputStreamReader, ProductoEntidad[].class); 

                for (ProductoEntidad producto : productosApi) {
                    //Recorro el arreglo

                  productoRepositorio.save(producto);
                  //Por cada producto, lo guardo en mi DB
                }

                // Cierro la conexion
                inputStreamReader.close();
                conexion.disconnect();

                return productosApi; 
                //Devuelvo el arreglo para confirmar el procedimiento.
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return productosApi;

    }


    //COMENTARIOS Y CALIFICACION

    public void crearComentario(ComentarioEntidad comentario, Integer id){

        ProductoEntidad productoDelComentario = productoRepositorio.findById(id).get();
        //Obtengo el Producto al que se le quiere agregar un comentario

        comentario.setProducto(productoDelComentario);
        //Vinculo el producto al comentario

        comentarioRepositorio.save(comentario);
        //Guardo el comentario en la DB

        productoDelComentario.getComentarios().add(comentario);
        //Obtengo los comentarios del Producto, y le agrego el nuevo.

        calcularCalificacion(productoDelComentario);
        //Calculo la nueva calificacion del Producyo

        productoRepositorio.save(productoDelComentario);
        //Guardo el Producto en al DB
    }

    double promedio = 0;
    public void calcularCalificacion(ProductoEntidad producto){

        List<ComentarioEntidad> comentarios =  new ArrayList<>(producto.getComentarios());
        //Obtengo los comentarios del producto a calcular

        if(comentarios.size() > 0){

            for (ComentarioEntidad comentario : comentarios) {
              promedio += comentario.getPuntuacion(); 
              //Sumo la puntuacion total e todos los comentarios
            }

            promedio = promedio / comentarios.size();
            /*Divido la puntuacion total de los comentarios por su cantidad,
             para obtener el promedio*/ 

            producto.setCalificacion(promedio);
            //Le agrego la calificacion al producto
        }
    }

    public ArrayList<ComentarioEntidad> verComentariosDeProducto(ProductoEntidad producto){

        return comentarioRepositorio.findByProducto(producto);
    }

    //SERVICIOS CLIENTE

    ArrayList<ProductoEntidad> productos = new ArrayList();

    public ArrayList<ProductoEntidad> verProductosCliente(){

        productos = (ArrayList<ProductoEntidad>) productoRepositorio.findAll();

        for (ProductoEntidad producto : productos) {

            aumentarPrecioDeCosto(producto);
            calcularCalificacion(producto);
        }

        return productos;
    }

    public ProductoEntidad obtenerProductoPorIdCliente(Integer id){

        ProductoEntidad producto = productoRepositorio.findById(id).get();

        aumentarPrecioDeCosto(producto);
        calcularCalificacion(producto);

        return producto;
    }

    public ArrayList<ProductoEntidad> buscarPorCategoriaCliente(String categoria){

        productos = productoRepositorio.findByCategory(categoria);

        for (ProductoEntidad producto : productos) {

            aumentarPrecioDeCosto(producto);
            calcularCalificacion(producto);
        }

        return productos;
    }

    //SERVICIOS ADMIN

    public ArrayList<ProductoEntidad> verProductos(){

        ArrayList<ProductoEntidad> productos =  (ArrayList<ProductoEntidad>) productoRepositorio.findAll();

        for (ProductoEntidad producto : productos) {
            calcularCalificacion(producto);
        }

        return(productos);
    }

    public ProductoEntidad obtenerProductoPorId(Integer id){

        ProductoEntidad producto = productoRepositorio.findById(id).get();

        calcularCalificacion(producto);
        return producto;
    }

    // public ArrayList<ProductoEntidad> buscarPorCategoria(String categoria){

    //     ArrayList<ProductoEntidad> productos =  productoRepositorio.findByCategory(categoria);

    //     for (ProductoEntidad producto : productos) {
    //         calcularCalificacion(producto);
    //     }

    //     return(productos);
    // }

    public ArrayList<ProductoEntidad> buscarPorCategoria(String categoria) {
    try {
        String categoriaCodificada = URLEncoder.encode(categoria, StandardCharsets.UTF_8.toString());
        /*URLEncoder.encode codifica la cadena de "categoría" en el formato adecuado para una URL. 
        Esto asegurará que los caracteres especiales, como el apóstrofe (men's clothing), sean manejados correctamente en la URL. */

        //Usar la categoría codificada para buscar en el repositorio
        ArrayList<ProductoEntidad> productos = productoRepositorio.findByCategory(categoriaCodificada);

        for (ProductoEntidad producto : productos) {
            calcularCalificacion(producto);
        }
        return productos;
    } catch (UnsupportedEncodingException e) {
        // Manejar la excepción en caso de que ocurra un error de codificación
        e.printStackTrace();
        return new ArrayList<>(); // O manejar el error de otra manera según tus necesidades
    }
}

    public void agregarProducto(ProductoEntidad producto){
        productoRepositorio.save(producto);
    }

    public ProductoEntidad actualizarProducto(ProductoEntidad productoActualizado, Integer id) {
        //Obtengo el usuario que ya tengo actualizado
        ProductoEntidad productoAux = productoRepositorio.findById(id).get();
        //Obtengo, desde la DB, el usuario que quiero actualizar 

        productoAux.setTitle(productoActualizado.getTitle());
        productoAux.setPrice(productoActualizado.getPrice());
        productoAux.setCategory(productoActualizado.getCategory());

        return productoRepositorio.save(productoAux);
        //Una vez modificado, guardo el usuario actualizado en al DB
    }

    public boolean eliminarProducto(Integer id) {
        try {
            productoRepositorio.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }




    
}

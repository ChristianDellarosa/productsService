package com.springboot.app.productos.controllers;

import com.springboot.app.productos.models.entity.Product;
import com.springboot.app.productos.models.service.interfaces.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
//@RequestMapping("/products")
public class ProductController {

    private Environment environment;

    private IProductService productService;

    @Value("${server.port}")
    private Integer port;

    @Autowired
    public ProductController(IProductService productService, Environment environment) {
        this.productService = productService;
        this.environment = environment;
    }

    @GetMapping("/list")
    public List<Product> list() {
        return productService.findAll().stream().map(product -> {
            // product.setPort(Integer.parseInt(environment.getProperty("local.server.port")));
            product.setPort(port);
            return product;
        }).collect(Collectors.toList());
    }

    @GetMapping("/list/{id}")
    public Product list(@PathVariable Long id) {
        Product product = productService.findById(id);
        //product.setPort(Integer.parseInt(environment.getProperty("local.server.port")));
        product.setPort(port);
        return product;
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public Product create(@RequestBody Product product) {
        return productService.save(product);
    }

    ///TODO Modificar put, pasando id por requestBody
    @PutMapping("/update/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Product update(@RequestBody Product newProduct, @PathVariable Long id) {
        Product product = productService.findById(id);
        product.setNombre(newProduct.getNombre());
        product.setPrecio(newProduct.getPrecio());
        return productService.save(product);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable Long id) {
        productService.deleteById(id);
    }
}

package com.springboot.app.productos.models.service.interfaces;

import com.springboot.app.productos.models.entity.Product;

import java.util.List;

public interface IProductService {
    List<Product> findAll();
    Product findById(Long id);
}

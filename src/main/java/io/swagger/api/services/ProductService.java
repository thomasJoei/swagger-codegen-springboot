package io.swagger.api.services;

import io.swagger.model.NewProductDto;
import io.swagger.model.ProductDto;
import io.swagger.model.ProductPage;

import java.util.List;


public interface ProductService {

    ProductDto addProduct(NewProductDto newProduct);

    ProductPage findProducts(String category, Integer limit, Integer page);
}

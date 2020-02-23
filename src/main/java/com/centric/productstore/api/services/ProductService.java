package com.centric.productstore.api.services;

import com.centric.productstore.api.dto.NewProductDto;
import com.centric.productstore.api.dto.ProductDto;
import com.centric.productstore.api.dto.ProductPage;


public interface ProductService {

    ProductDto addProduct(NewProductDto newProduct);

    ProductPage findProducts(String category, Integer limit, Integer page);
}

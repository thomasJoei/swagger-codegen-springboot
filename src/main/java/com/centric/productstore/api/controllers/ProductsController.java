package com.centric.productstore.api.controllers;

import com.centric.productstore.api.ProductsApi;
import com.centric.productstore.api.dto.NewProductDto;
import com.centric.productstore.api.dto.ProductDto;
import com.centric.productstore.api.dto.ProductPage;
import com.centric.productstore.api.services.ProductService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

import static org.springframework.http.ResponseEntity.ok;

@Controller
@Api(tags = {"products"})
public class ProductsController implements ProductsApi {

    private ProductService productService;

    public ProductsController(@Autowired ProductService productService) {
        this.productService = productService;
    }

    @Override
    public ResponseEntity<ProductDto> addProduct(@Valid @RequestBody NewProductDto newProductDto) {
        return ok(productService.addProduct(newProductDto));
    }


    @Override
    public ResponseEntity<ProductPage> findProducts(@RequestParam(value = "category", required = false) String category,
                                                    @RequestParam(value = "limit", required = false) Integer limit,
                                                    @RequestParam(value = "page", required = false) Integer page) {
        return ok(productService.findProducts(category, limit, page));
    }
}

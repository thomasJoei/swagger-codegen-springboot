package com.centric.productstore.api.controllers;

import com.centric.productstore.api.ProductsApi;
import com.centric.productstore.api.dto.ErrorModel;
import com.centric.productstore.api.dto.NewProductDto;
import com.centric.productstore.api.dto.ProductDto;
import com.centric.productstore.api.dto.ProductPage;
import com.centric.productstore.api.services.ProductService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
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
        limit = limit == null ? 10 : limit;
        page = page == null ? 0 : page;

        return ok(productService.findProducts(category, limit, page));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorModel> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) throws MethodArgumentNotValidException {

//        Spring exception wrapping provide more detailed information on the error
//        throw e;
        return ResponseEntity.badRequest()
            .body(new ErrorModel()
                .code(400)
                .message("Bad request")
            );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorModel> handleException(Exception e) {

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(
                new ErrorModel()
                    .code(500)
                    .message("Internal server error")
            );
    }
}

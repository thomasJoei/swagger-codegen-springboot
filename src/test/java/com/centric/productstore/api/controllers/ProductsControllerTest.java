package com.centric.productstore.api.controllers;


import com.centric.productstore.api.dto.NewProductDto;
import com.centric.productstore.api.dto.ProductDto;
import com.centric.productstore.api.services.ProductService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ProductsControllerTest {


    @Mock
    protected ProductService productService;


    protected ProductsController productsController;

    private MockMvc mockMvc;

    private static final String NAME = "Pilote";
    private static final String DESCRIPTION = "Rayban Pilote";
    private static final String BRAND = "Rayban";
    private static final String CATEGORY = "glasses";
//    private static final String CATEGORY = "glasses";

    @Before
    public void before() throws Exception {
        MockitoAnnotations.initMocks(this);

        productsController = spy(
            new ProductsController(productService)
        );

        this.mockMvc = MockMvcBuilders.standaloneSetup(productsController).build();
    }


    @Test
    public void addProduct() throws Exception {

        NewProductDto newProductDto = new NewProductDto()
            .name(NAME)
            .description(DESCRIPTION)
            .brand(BRAND)
            .category(CATEGORY);

        when(productService.addProduct(eq(newProductDto)))
            .thenReturn(new ProductDto());

        mockMvc
            .perform(
                post("/products")
                    .content(ProductsControllerTest.serialize(newProductDto, true))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isOk())
        ;
    }

    @Test
    public void addProductNullName() throws Exception {
        NewProductDto newProductDto = new NewProductDto()
            .name(null)
            .description(DESCRIPTION)
            .brand(BRAND)
            .category(CATEGORY);

        mockMvc
            .perform(
                post("/products")
                    .content(ProductsControllerTest.serialize(newProductDto, true))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isBadRequest())
        ;
    }

    @Test
    public void addProductNullBrand() throws Exception {
        NewProductDto newProductDto = new NewProductDto()
            .name(NAME)
            .description(DESCRIPTION)
            .brand(null)
            .category(CATEGORY);

        mockMvc
            .perform(
                post("/products")
                    .content(ProductsControllerTest.serialize(newProductDto, true))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isBadRequest())
        ;
    }

    @Test
    public void addProductNullCategory() throws Exception {
        NewProductDto newProductDto = new NewProductDto()
            .name(NAME)
            .description(DESCRIPTION)
            .brand(BRAND)
            .category(null);

        mockMvc
            .perform(
                post("/products")
                    .content(ProductsControllerTest.serialize(newProductDto, true))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isBadRequest())
        ;
    }


//    @Test
//    public void findAllProduct() throws Exception {
//
//        NewProductDto newProductDto = new NewProductDto()
//            .name(NAME)
//            .description(DESCRIPTION)
//            .brand(BRAND)
//            .category(CATEGORY);
//
//        when(productService.addProduct(eq(newProductDto)))
//            .thenReturn(new ProductDto());
//
//        mockMvc
//            .perform(
//                post("/products")
//                    .content(ProductsControllerTest.serialize(newProductDto, true))
//                    .contentType(MediaType.APPLICATION_JSON)
//            )
//            .andExpect(status().isOk())
//        ;
//    }


    public static String serialize(Object obj, boolean pretty) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();


        if (pretty) {
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
        }

        return mapper.writeValueAsString(obj);
    }
}
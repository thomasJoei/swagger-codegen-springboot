package com.centric.productstore.api.controllers;


import com.centric.productstore.api.dto.NewProductDto;
import com.centric.productstore.api.dto.ProductDto;
import com.centric.productstore.api.dto.ProductPage;
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

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ProductsControllerTest {

    @Mock
    private ProductService productService;

    private MockMvc mockMvc;

    private static final String NAME = "Pilote";
    private static final String DESCRIPTION = "Rayban Pilote";
    private static final String BRAND = "Rayban";
    private static final String CATEGORY = "glasses";

    @Before
    public void before() throws Exception {
        MockitoAnnotations.initMocks(this);

        ProductsController productsController = spy(
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


    @Test
    public void findAllByCategory() throws Exception {
        ProductDto product = (ProductDto) new ProductDto()
            .name(NAME)
            .description(DESCRIPTION)
            .brand(BRAND)
            .category(CATEGORY);

        List<ProductDto> products = new ArrayList<>();
        products.add(product);


        when(productService.findAllByCategory(eq("pants"), eq(3), eq(4)))
            .thenReturn(new ProductPage().currentPage(4).totalPages(5).products(products));

        mockMvc
            .perform(
                get("/products")
                    .param("category", "pants")
                    .param("limit", "3")
                    .param("page", "4")
            )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.products[0].name").value(NAME));
    }


    private static String serialize(Object obj, boolean pretty) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();


        if (pretty) {
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
        }

        return mapper.writeValueAsString(obj);
    }
}
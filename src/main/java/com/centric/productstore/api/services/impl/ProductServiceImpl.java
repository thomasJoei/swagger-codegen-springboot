package com.centric.productstore.api.services.impl;

import com.centric.productstore.api.db.dao.ProductRepository;
import com.centric.productstore.api.db.models.Product;
import com.centric.productstore.api.services.ProductService;
import com.centric.productstore.api.dto.NewProductDto;
import com.centric.productstore.api.dto.ProductDto;
import com.centric.productstore.api.dto.ProductPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private ProductRepository productRepository;

    public ProductServiceImpl(@Autowired ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public ProductDto addProduct(NewProductDto newProduct) {
        Product persistedProduct = productRepository.save(newProductDtoToProduct(newProduct));
        return productToProductDto(persistedProduct);
    }

    @Override
    public ProductPage findProducts(String category, Integer limit, Integer pageNumber) {
        Pageable sortedByCreationDate =
            PageRequest.of(pageNumber, limit, Sort.by("createdAt").descending());
        Page<Product> page = productRepository.findAllByCategory(category, sortedByCreationDate);

        return pageToProductPage(pageNumber, page);
    }

    private ProductPage pageToProductPage(Integer pageNumber, Page<Product> page) {
        return new ProductPage()
            .totalElements(page.getTotalElements())
            .totalPages(page.getTotalPages())
            .currentPage(pageNumber)
            .products(page.stream()
                .map(this::productToProductDto).collect(Collectors.toList())
            );
    }

    private Product newProductDtoToProduct(NewProductDto newProductDto) {
        return new Product()
            .name(newProductDto.getName())
            .description(newProductDto.getDescription())
            .brand(newProductDto.getBrand())
            .category(newProductDto.getCategory())
            .tags(new HashSet<>(newProductDto.getTags()));
    }

    private ProductDto productToProductDto(Product product) {

        return (ProductDto) new ProductDto()
            .id(product.getId().toString())
            .createdAt(product.getCreatedAt().atOffset(ZoneOffset.UTC))
            .name(product.getName())
            .description(product.getDescription())
            .brand(product.getBrand())
            .category(product.getCategory())
            .tags(new ArrayList<>(product.getTags()));
    }
}

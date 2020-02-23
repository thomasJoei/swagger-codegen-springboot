package com.centric.productstore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@ComponentScan(basePackages = {"io.swagger", "com.centric.productstore.api"})
public class ProductStoreApplication {

    public static void main(String[] args) {
        new SpringApplication(ProductStoreApplication.class).run(args);
    }

}

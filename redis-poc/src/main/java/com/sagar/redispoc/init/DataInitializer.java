package com.sagar.redispoc.init;

import com.sagar.redispoc.entity.Product;
import com.sagar.redispoc.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final ProductRepository repository;

    @Override
    public void run(String... args) {
        if (repository.count() == 0) {
            repository.saveAll(List.of(
                    Product.builder().name("Laptop").category("Electronics").price(1200.0).stock(10).build(),
                    Product.builder().name("Phone").category("Electronics").price(800.0).stock(25).build(),
                    Product.builder().name("Desk").category("Furniture").price(300.0).stock(5).build()
            ));
        }
    }
}

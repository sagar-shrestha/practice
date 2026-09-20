package com.sagar.redispoc.service;

import com.sagar.redispoc.entity.Product;
import com.sagar.redispoc.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    @Cacheable(value = "products", key = "#id")
    public Product getProductById(Long id) {
        log.info(">>> Fetching product {} from H2 DB (cache miss)", id);
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found: " + id));
    }

    // Cache full list under key 'all'
    @Cacheable(value = "productList", key = "'all'")
    public List<Product> getAllProducts() {
        log.info(">>> Fetching ALL products from H2 DB (cache miss)");
        return productRepository.findAll();
    }

    @CachePut(value = "products", key = "#result.id")
    public Product save(Product product) {
        log.info(">>> Saving product to H2 DB");
        return productRepository.save(product);
    }

    @CacheEvict(value = {"products", "productList"}, allEntries = true)
    public void clearCache() {
        log.info(">>> Cleared product caches");
    }

    @CacheEvict(value = "products", key = "#id")
    public void delete(Long id) {
        productRepository.deleteById(id);
    }
}
package com.sagar.redispoc.repository;

import com.sagar.redispoc.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}

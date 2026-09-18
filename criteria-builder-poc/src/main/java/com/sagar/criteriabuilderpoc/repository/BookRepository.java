package com.sagar.criteriabuilderpoc.repository;

import com.sagar.criteriabuilderpoc.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long>, BookRepositoryCustom {
}

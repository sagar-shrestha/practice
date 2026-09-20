package com.sagar.aoppoc.repository;

import com.sagar.aoppoc.entity.Book;

import java.util.List;

public interface BookRepositoryCustom {

    List<Book> findBooksByAuthorNameAndTitle(String authorName, String title);
}

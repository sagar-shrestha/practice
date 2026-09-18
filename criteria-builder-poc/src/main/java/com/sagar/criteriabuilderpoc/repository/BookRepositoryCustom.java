package com.sagar.criteriabuilderpoc.repository;

import com.sagar.criteriabuilderpoc.entity.Book;

import java.util.List;

public interface BookRepositoryCustom {

    List<Book> findBooksByAuthorNameAndTitle(String authorName, String title);
}

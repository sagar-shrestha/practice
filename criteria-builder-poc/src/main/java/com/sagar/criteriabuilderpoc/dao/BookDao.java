package com.sagar.criteriabuilderpoc.dao;

import com.sagar.criteriabuilderpoc.entity.Book;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BookDao {

    EntityManager em;

    List<Book> findBooksByAuthorNameAndTitle(String author, String title) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Book> cq = cb.createQuery(Book.class);

        Root<Book> root = cq.from(Book.class);
        Predicate authorPredicate = cb.equal(root.get("author"), author);
        Predicate titlePredicate = cb.equal(root.get("title"), title);
        cq.where(authorPredicate, titlePredicate);

        TypedQuery<Book> query = em.createQuery(cq);
        return query.getResultList();
    }
}

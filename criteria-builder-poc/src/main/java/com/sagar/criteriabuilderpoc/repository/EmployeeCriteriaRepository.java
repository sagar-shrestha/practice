package com.sagar.criteriabuilderpoc.repository;

import com.sagar.criteriabuilderpoc.pojo.EmployeeSearchRequest;
import com.sagar.criteriabuilderpoc.entity.Department;
import com.sagar.criteriabuilderpoc.entity.Employee;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Repository
public class EmployeeCriteriaRepository {

    @PersistenceContext
    private EntityManager em;

    public Page<Employee> search(EmployeeSearchRequest req, Pageable pageable) {
        CriteriaBuilder cb = em.getCriteriaBuilder();

        // main query
        CriteriaQuery<Employee> cq = cb.createQuery(Employee.class);
        Root<Employee> root = cq.from(Employee.class);
        root.fetch("department", JoinType.LEFT);

        List<Predicate> predicates = buildPredicates(cb, root, req);
        cq.where(cb.and(predicates.toArray(new Predicate[0])));

        // dynamic sorting
        cq.orderBy(buildOrder(cb, root, pageable.getSort()));

        TypedQuery<Employee> query = em.createQuery(cq);
        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());

        // count query
        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        Root<Employee> countRoot = countQuery.from(Employee.class);
        countQuery.select(cb.countDistinct(countRoot));
        countQuery.where(cb.and(buildPredicates(cb, countRoot, req).toArray(new Predicate[0])));
        Long total = em.createQuery(countQuery).getSingleResult();

        return new PageImpl<>(query.getResultList(), pageable, total);
    }

    public List<Object[]> groupByDepartment(BigDecimal minSalary) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Object[]> cq = cb.createQuery(Object[].class);
        Root<Employee> root = cq.from(Employee.class);
        Join<Employee, Department> dept = root.join("department", JoinType.LEFT);

        cq.multiselect(
                dept.get("name"),
                cb.count(root),
                cb.avg(root.get("salary")),
                cb.max(root.get("salary"))
        );

        cq.where(cb.greaterThanOrEqualTo(root.get("salary"), minSalary));
        cq.groupBy(dept.get("name"));
        cq.having(cb.greaterThan(cb.count(root), 1L));
        cq.orderBy(cb.desc(cb.avg(root.get("salary"))));

        return em.createQuery(cq).getResultList();
    }

    private List<Predicate> buildPredicates(CriteriaBuilder cb, Root<Employee> root, EmployeeSearchRequest req) {
        List<Predicate> predicates = new ArrayList<>();

        if (req.getName() != null && !req.getName().isBlank()) {
            String like = "%" + req.getName().toLowerCase() + "%";
            predicates.add(cb.or(
                    cb.like(cb.lower(root.get("firstName")), like),
                    cb.like(cb.lower(root.get("lastName")), like)
            ));
        }

        if (req.getEmail() != null && !req.getEmail().isBlank()) {
            predicates.add(cb.equal(cb.lower(root.get("email")), req.getEmail().toLowerCase()));
        }

        if (req.getStatus() != null) {
            predicates.add(cb.equal(root.get("status"), req.getStatus()));
        }

        if (req.getDepartmentId() != null) {
            predicates.add(cb.equal(root.get("department").get("id"), req.getDepartmentId()));
        }

        if (req.getMinSalary() != null) {
            predicates.add(cb.greaterThanOrEqualTo(root.get("salary"), req.getMinSalary()));
        }
        if (req.getMaxSalary() != null) {
            predicates.add(cb.lessThanOrEqualTo(root.get("salary"), req.getMaxSalary()));
        }

        if (req.getHiredAfter() != null) {
            predicates.add(cb.greaterThanOrEqualTo(root.get("hireDate"), req.getHiredAfter()));
        }
        if (req.getHiredBefore() != null) {
            predicates.add(cb.lessThanOrEqualTo(root.get("hireDate"), req.getHiredBefore()));
        }

        return predicates;
    }

    private List<Order> buildOrder(CriteriaBuilder cb, Root<Employee> root, Sort sort) {
        List<Order> orders = new ArrayList<>();
        if (sort == null || sort.isUnsorted()) {
            orders.add(cb.asc(root.get("id")));
            return orders;
        }
        for (Sort.Order o : sort) {
            Path<Object> path = root.get(o.getProperty());
            orders.add(o.isAscending() ? cb.asc(path) : cb.desc(path));
        }
        return orders;
    }
}

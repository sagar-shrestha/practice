package com.sagar.aoppoc.init;

import com.sagar.aoppoc.entity.Department;
import com.sagar.aoppoc.entity.Employee;
import com.sagar.aoppoc.entity.EmployeeStatus;
import com.sagar.aoppoc.repository.EmployeeRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final EntityManager em;
    private final EmployeeRepository repository;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        if (repository.count() > 0) return;

        Department eng = Department.builder().name("Engineering").location("Kathmandu").build();
        Department hr = Department.builder().name("HR").location("Pokhara").build();
        Department fin = Department.builder().name("Finance").location("Lalitpur").build();
        em.persist(eng);
        em.persist(hr);
        em.persist(fin);

        List<Employee> employees = List.of(
                emp("Sagar", "Sharma", "sagar@x.com", "95000", EmployeeStatus.ACTIVE, "2021-03-15", eng),
                emp("Anita", "Rai", "anita@x.com", "88000", EmployeeStatus.ACTIVE, "2020-07-01", eng),
                emp("Bibek", "Thapa", "bibek@x.com", "72000", EmployeeStatus.ON_LEAVE, "2022-01-10", eng),
                emp("Chetan", "Gurung", "chetan@x.com", "65000", EmployeeStatus.ACTIVE, "2023-05-20", eng),
                emp("Dipesh", "Karki", "dipesh@x.com", "120000", EmployeeStatus.ACTIVE, "2018-09-01", fin),
                emp("Elina", "Shrestha", "elina@x.com", "105000", EmployeeStatus.ACTIVE, "2019-11-11", fin),
                emp("Farhan", "Khan", "farhan@x.com", "58000", EmployeeStatus.TERMINATED, "2022-04-04", hr),
                emp("Gita", "Adhikari", "gita@x.com", "61000", EmployeeStatus.ACTIVE, "2021-08-30", hr)
        );
        employees.forEach(em::persist);
    }

    private Employee emp(String fn, String ln, String email, String salary,
                         EmployeeStatus status, String hireDate, Department dept) {
        return Employee.builder()
                .firstName(fn).lastName(ln).email(email)
                .salary(new BigDecimal(salary))
                .status(status)
                .hireDate(LocalDate.parse(hireDate))
                .department(dept)
                .build();
    }
}

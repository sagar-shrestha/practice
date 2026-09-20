package com.sagar.aoppoc.repository;

import com.sagar.aoppoc.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {


}

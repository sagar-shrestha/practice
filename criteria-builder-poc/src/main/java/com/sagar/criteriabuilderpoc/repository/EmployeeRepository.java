package com.sagar.criteriabuilderpoc.repository;

import com.sagar.criteriabuilderpoc.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {


}

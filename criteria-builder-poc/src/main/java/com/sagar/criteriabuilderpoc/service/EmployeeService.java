package com.sagar.criteriabuilderpoc.service;

import com.sagar.criteriabuilderpoc.entity.Employee;
import com.sagar.criteriabuilderpoc.pojo.EmployeeResponse;
import com.sagar.criteriabuilderpoc.pojo.EmployeeSearchRequest;
import com.sagar.criteriabuilderpoc.pojo.PageResponse;
import com.sagar.criteriabuilderpoc.repository.EmployeeCriteriaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeCriteriaRepository criteriaRepository;

    @Transactional(readOnly = true)
    public PageResponse<EmployeeResponse> search(EmployeeSearchRequest req, Pageable pageable) {
        Page<Employee> page = criteriaRepository.search(req, pageable);
        return PageResponse.from(page, this::toResponse);
    }

    @Transactional(readOnly = true)
    public List<Object[]> groupByDepartment(BigDecimal minSalary) {
        return criteriaRepository.groupByDepartment(minSalary);
    }

    private EmployeeResponse toResponse(Employee e) {
        return EmployeeResponse.builder()
                .id(e.getId())
                .firstName(e.getFirstName())
                .lastName(e.getLastName())
                .email(e.getEmail())
                .salary(e.getSalary())
                .status(e.getStatus())
                .hireDate(e.getHireDate())
                .departmentId(e.getDepartment() != null ? e.getDepartment().getId() : null)
                .departmentName(e.getDepartment() != null ? e.getDepartment().getName() : null)
                .build();
    }
}

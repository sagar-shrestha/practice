package com.sagar.criteriabuilderpoc.controller;

import com.sagar.criteriabuilderpoc.pojo.EmployeeResponse;
import com.sagar.criteriabuilderpoc.pojo.EmployeeSearchRequest;
import com.sagar.criteriabuilderpoc.pojo.PageResponse;
import com.sagar.criteriabuilderpoc.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService service;

    @PostMapping("/search")
    public PageResponse<EmployeeResponse> search(
            @RequestBody EmployeeSearchRequest req,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "ASC") String direction) {

        Sort sort = Sort.by(Sort.Direction.fromString(direction), sortBy);
        Pageable pageable = PageRequest.of(page, Math.min(size, 100), sort);
        return service.search(req, pageable);
    }

    @GetMapping("/stats/by-department")
    public List<Object[]> stats(@RequestParam BigDecimal minSalary) {
        return service.groupByDepartment(minSalary);
    }
}

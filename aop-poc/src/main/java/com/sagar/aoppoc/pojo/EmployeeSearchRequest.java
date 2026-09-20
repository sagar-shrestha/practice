package com.sagar.aoppoc.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.sagar.aoppoc.entity.EmployeeStatus;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class EmployeeSearchRequest {

    private String name;
    private String email;
    private EmployeeStatus status;
    private Long departmentId;
    private BigDecimal minSalary;
    private BigDecimal maxSalary;
    private LocalDate hiredAfter;
    private LocalDate hiredBefore;
}

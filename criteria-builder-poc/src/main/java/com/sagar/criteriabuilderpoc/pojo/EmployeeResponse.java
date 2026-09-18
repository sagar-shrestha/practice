package com.sagar.criteriabuilderpoc.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.sagar.criteriabuilderpoc.entity.EmployeeStatus;
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
public class EmployeeResponse {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private BigDecimal salary;
    private EmployeeStatus status;
    private Long departmentId;
    private String departmentName;
    private LocalDate hireDate;
}

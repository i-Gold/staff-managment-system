package com.company.departments.converter;

import com.company.departments.dao.DAOFactory;
import com.company.departments.model.Department;
import com.company.departments.model.Employee;
import com.company.departments.model.dto.DepartmentDTO;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class DepartmentConverter {

    public static Department fromDTO(DepartmentDTO departmentDTO) throws SQLException {
        return new Department.Builder()
                .id(departmentDTO.getId())
                .name(departmentDTO.getName())
                .amountOfEmployees(departmentDTO.getAmountOfEmployees())
                .employees(Objects.nonNull(departmentDTO.getEmployeeIds()) ?
                        getListOfEmployeesByIds(departmentDTO.getEmployeeIds())
                        : null)
                .build();
    }

    public static DepartmentDTO toDTO(Department department) {
        return new DepartmentDTO.Builder()
                .id(department.getId())
                .name(department.getName())
                .amountOfEmployees(department.getAmountOfEmployees())
                .employees(Objects.nonNull(department.getEmployees()) ?
                        new HashSet<>(department.getEmployees().stream()
                                .filter(Objects::nonNull)
                                .map(Employee::getId)
                                .collect(Collectors.toSet()))
                        : null)
                .build();
    }

    private static HashSet<Employee> getListOfEmployeesByIds(Set<Long> employeeIds) throws SQLException {
        HashSet<Employee> result = new HashSet<>();
        for (Long employeeId : employeeIds) {
            result.add(DAOFactory.getInstance()
                    .getEmployeeDAO().findById(employeeId)
                    .orElse(null));
        }
        return result;
    }

}

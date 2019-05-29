package com.company.departments.converter;

import com.company.departments.dao.DAOFactory;
import com.company.departments.model.Employee;
import com.company.departments.model.dto.EmployeeDTO;

import java.sql.SQLException;
import java.util.Objects;

public class EmployeeConverter {

    public static Employee fromDTO(EmployeeDTO employeeDTO) throws SQLException {
        return new Employee.Builder()
                .id(employeeDTO.getId())
                .fullName(employeeDTO.getFullName())
                .email(employeeDTO.getEmail())
                .startedWorkAt(employeeDTO.getStartedWorkAt())
                .department(employeeDTO.getDepartmentId() != null ?
                        DAOFactory.getInstance()
                                .getDepartmentDAO().findById(employeeDTO.getDepartmentId())
                                .orElse(null)
                        : null)
                .build();
    }

    public static EmployeeDTO toDTO(Employee employee) {
        return new EmployeeDTO.Builder()
                .id(employee.getId())
                .fullName(employee.getFullName())
                .email(employee.getEmail())
                .startedWorkAt(employee.getStartedWorkAt())
                .departmentId(Objects.nonNull(employee.getDepartment()) ?
                        employee.getDepartment().getId()
                        : null)
                .build();
    }

}

package com.company.departments.dao;

import com.company.departments.model.Employee;
import com.company.departments.model.dto.EmployeeDTO;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface EmployeeDAO {

    Employee add(EmployeeDTO employee) throws SQLException;

    Employee update(EmployeeDTO employee) throws SQLException;

    boolean deleteById(Long id) throws SQLException;

    Optional<Employee> findById(Long id) throws SQLException;

    Set<Employee> findAllByDepartmentId(Long departmentId) throws SQLException;

    List<EmployeeDTO> getAll() throws SQLException;

}

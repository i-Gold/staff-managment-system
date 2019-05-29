package com.company.departments.dao;

import com.company.departments.model.Department;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface DepartmentDAO {

    Department add(Department department) throws SQLException;

    Department update(Department department) throws SQLException;

    boolean deleteById(Long id) throws SQLException;

    Optional<Department> findById(Long id) throws SQLException;

    List<Department> getAll() throws SQLException;

}

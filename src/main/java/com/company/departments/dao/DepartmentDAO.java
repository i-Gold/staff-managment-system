package com.company.departments.dao;

import com.company.departments.model.Department;
import com.company.departments.model.dto.DepartmentDTO;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface DepartmentDAO {

    Department add(DepartmentDTO department) throws SQLException;

    Department update(DepartmentDTO department) throws SQLException;

    boolean deleteById(Long id) throws SQLException;

    Optional<Department> findById(Long id) throws SQLException;

    List<DepartmentDTO> getAll() throws SQLException;

}

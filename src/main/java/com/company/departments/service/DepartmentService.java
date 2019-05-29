package com.company.departments.service;

import com.company.departments.dao.DAOFactory;
import com.company.departments.model.Department;
import com.company.departments.model.dto.DepartmentDTO;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static com.company.departments.converter.DepartmentConverter.fromDTO;

public class DepartmentService {

    private final DAOFactory daoFactory = DAOFactory.getInstance();

    public Department addNewDepartment(DepartmentDTO departmentDTO) throws SQLException {
        return daoFactory.getDepartmentDAO()
                .add(fromDTO(departmentDTO));
    }

    public Department updateDepartment(DepartmentDTO departmentDTO) throws SQLException {
        return daoFactory.getDepartmentDAO()
                .update(fromDTO(departmentDTO));
    }

    public boolean deleteDepartment(Long id) throws SQLException {
        return daoFactory.getDepartmentDAO()
                .deleteById(id);
    }

    public List<DepartmentDTO> getAllDepartments() throws SQLException {
        return daoFactory.getDepartmentDAO()
                .getAll();
    }

    public Optional<Department> findDepartmentById(Long id) throws SQLException {
        return daoFactory.getDepartmentDAO()
                .findById(id);
    }

}

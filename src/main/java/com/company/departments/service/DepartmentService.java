package com.company.departments.service;

import com.company.departments.converter.DepartmentConverter;
import com.company.departments.dao.DAOFactory;
import com.company.departments.dao.DepartmentDAO;
import com.company.departments.model.Department;
import com.company.departments.model.dto.DepartmentDTO;

import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.company.departments.converter.DepartmentConverter.fromDTO;
import static com.company.departments.converter.DepartmentConverter.toDTO;

public class DepartmentService {

    private final DepartmentDAO departmentDAO = DAOFactory.getInstance().getDepartmentDAO();

    public DepartmentDTO addNewDepartment(DepartmentDTO departmentDTO) throws SQLException {
        return toDTO(departmentDAO.add(fromDTO(departmentDTO)));
    }

    public DepartmentDTO updateDepartment(DepartmentDTO departmentDTO) throws SQLException {
        return toDTO(departmentDAO.update(fromDTO(departmentDTO)));
    }

    public boolean deleteDepartment(Long id) throws SQLException {
        return departmentDAO.deleteById(id);
    }

    public Optional<DepartmentDTO> findDepartmentById(Long id) throws SQLException {
        Optional<Department> department = departmentDAO.findById(id);
        return department.map(DepartmentConverter::toDTO);
    }

    public List<DepartmentDTO> getAllDepartments() throws SQLException {
        return departmentDAO.getAll().stream()
                .filter(Objects::nonNull)
                .map(DepartmentConverter::toDTO)
                .collect(Collectors.toList());
    }

}

package com.company.departments.service;

import com.company.departments.converter.EmployeeConverter;
import com.company.departments.dao.DAOFactory;
import com.company.departments.dao.EmployeeDAO;
import com.company.departments.model.Employee;
import com.company.departments.model.dto.EmployeeDTO;

import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static com.company.departments.converter.EmployeeConverter.fromDTO;
import static com.company.departments.converter.EmployeeConverter.toDTO;

public class EmployeeService {

    private final EmployeeDAO employeeDAO = DAOFactory.getInstance().getEmployeeDAO();

    public EmployeeDTO addNewEmployee(EmployeeDTO employeeDTO) throws SQLException {
        return toDTO(employeeDAO.add(fromDTO(employeeDTO)));
    }

    public EmployeeDTO updateEmployee(EmployeeDTO employeeDTO) throws SQLException {
        return toDTO(employeeDAO.update(fromDTO(employeeDTO)));
    }

    public boolean deleteEmployeeById(Long id) throws SQLException {
        return employeeDAO.deleteById(id);
    }

    public EmployeeDTO findEmployeeById(Long id) throws SQLException {
        Optional<Employee> employee = employeeDAO.findById(id);
        return employee.map(EmployeeConverter::toDTO)
                .get();
    }

    public Set<EmployeeDTO> findAllByDepartmentId(Long departmentId) throws SQLException {
        return employeeDAO.findAllByDepartmentId(departmentId).stream()
                .filter(Objects::nonNull)
                .map(EmployeeConverter::toDTO)
                .collect(Collectors.toSet());
    }

    public List<EmployeeDTO> getAllEmployees() throws SQLException {
        return employeeDAO.getAll().stream()
                .filter(Objects::nonNull)
                .map(EmployeeConverter::toDTO)
                .collect(Collectors.toList());
    }

}

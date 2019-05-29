package com.company.departments.service;

import com.company.departments.dao.DAOFactory;
import com.company.departments.dao.EmployeeDAO;
import com.company.departments.model.Employee;
import com.company.departments.model.dto.EmployeeDTO;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static com.company.departments.converter.EmployeeConverter.fromDTO;

public class EmployeeService {

    private final EmployeeDAO employeeDAO = DAOFactory.getInstance().getEmployeeDAO();

    public Employee addNewEmployee(EmployeeDTO employeeDTO) throws SQLException {
        return employeeDAO.add(fromDTO(employeeDTO));
    }

    public Employee updateEmployee(EmployeeDTO employeeDTO) throws SQLException {
        return employeeDAO.update(fromDTO(employeeDTO));
    }

    public boolean deleteEmployeeById(Long id) throws SQLException {
        return employeeDAO.deleteById(id);
    }

    public Optional<Employee> findEmployeeById(Long id) throws SQLException {
        return employeeDAO.findById(id);
    }

    public Set<Employee> findAllByDepartmentId(Long departmentId) throws SQLException {
        return employeeDAO.findAllByDepartmentId(departmentId);
    }

    public List<EmployeeDTO> getAllEmployees() throws SQLException {
        return employeeDAO.getAll();
    }

}

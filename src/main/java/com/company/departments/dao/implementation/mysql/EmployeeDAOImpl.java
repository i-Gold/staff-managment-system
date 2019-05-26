package com.company.departments.dao.implementation.mysql;

import com.company.departments.dao.DAOFactory;
import com.company.departments.dao.EmployeeDAO;
import com.company.departments.exception.BadRequest;
import com.company.departments.model.Employee;
import com.company.departments.model.dto.EmployeeDTO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.*;

import static com.company.departments.dao.implementation.mysql.DataInitializer.getConnection;
import static com.company.departments.dao.implementation.mysql.queries.Queries.*;

public class EmployeeDAOImpl implements EmployeeDAO {

    private Employee fromDTO(EmployeeDTO employeeDTO) throws SQLException {
        return new Employee.Builder()
                .id(employeeDTO.getId())
                .fullName(employeeDTO.getFullName())
                .email(employeeDTO.getEmail())
                .startedWorkAt(employeeDTO.getStartedWorkAt())
                .department(employeeDTO.getDepartmentId() != null ?
                        DAOFactory.getInstance()
                                .getDepartmentDAO().findById(employeeDTO.getDepartmentId()).get()
                        : null)
                .build();
    }

    private EmployeeDTO toDTO(Employee employee) {
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

    @Override
    public Employee add(EmployeeDTO employeeDTO) throws SQLException {
        if (employeeDTO == null) {
            throw new BadRequest();
        }
        try (PreparedStatement query = getConnection().prepareStatement(CREATE_EMPLOYEE)) {
            setParametersAndExecuteQuery(query, employeeDTO);
            try (PreparedStatement updateDepartment = getConnection().prepareStatement(CHANGE_AMOUNT_OF_EMPLOYEES_UP)) {
                updateDepartment.setLong(1, employeeDTO.getDepartmentId());
                updateDepartment.executeUpdate();
            }
            return fromDTO(employeeDTO);
        }
    }

    private void setParametersAndExecuteQuery(PreparedStatement query, EmployeeDTO employeeDTO) throws SQLException {
        query.setString(1, employeeDTO.getFullName());
        query.setString(2, employeeDTO.getEmail());
        if (employeeDTO.getId() == null) {
            query.setLong(3, employeeDTO.getDepartmentId());
        } else {
            query.setLong(3, employeeDTO.getId());
        }
        query.executeUpdate();
    }

    private Employee getFromResultSet(ResultSet resultSet) throws SQLException {
        return new Employee.Builder()
                .id(resultSet.getLong("id"))
                .fullName(resultSet.getString("full_name"))
                .email(resultSet.getString("email"))
                .startedWorkAt(resultSet.getObject("started_work_at", LocalDateTime.class))
                .department(DAOFactory.getInstance()
                        .getDepartmentDAO().findById(resultSet.getLong("department_id")).get())
                .build();
    }

    @Override
    public Employee update(EmployeeDTO employeeDTO) throws SQLException {
        if (employeeDTO == null) {
            throw new BadRequest();
        }
        try (PreparedStatement query = getConnection().prepareStatement(UPDATE_EMPLOYEE)) {
            setParametersAndExecuteQuery(query, employeeDTO);
            return fromDTO(employeeDTO);
        }
    }

    @Override
    public boolean deleteById(Long id) throws SQLException {
        Employee employee = new DAOFactoryImpl().getEmployeeDAO()
                .findById(id).get();
        try (PreparedStatement query = getConnection().prepareStatement(DELETE_EMPLOYEE)) {
            query.setLong(1, id);
            boolean result = query.execute();
            try (PreparedStatement updateDepartment = getConnection().prepareStatement(CHANGE_AMOUNT_OF_EMPLOYEES_DOWN)) {
                updateDepartment.setLong(1, employee.getDepartment().getId());
                updateDepartment.executeUpdate();
            }
            return result;
        }
    }

    @Override
    public Optional<Employee> findById(Long id) throws SQLException {
        try (ResultSet resultSet = SearchEntity.find(FIND_EMPLOYEE_BY_ID, id)) {
            if (resultSet.next()) {
                return Optional.of(getFromResultSet(resultSet));
            }
            return Optional.empty();
        }
    }

    @Override
    public Set<Employee> findAllByDepartmentId(Long departmentId) throws SQLException {
        Set<Employee> result = new HashSet<>();
        try (PreparedStatement query = getConnection().prepareStatement(GET_ALL_EMPLOYEES_OF_DEPARTMENT)) {
            query.setLong(1, departmentId);
            boolean founded = query.execute();
            if (!founded) {
                return new HashSet<>();
            }
            try (ResultSet resultSet = query.getResultSet()) {
                while (resultSet.next()) {
                    result.add(getFromResultSet(resultSet));
                }
            }
        }
        return result;
    }

    @Override
    public List<EmployeeDTO> getAll() throws SQLException {
        List<EmployeeDTO> result = new ArrayList<>();
        try (PreparedStatement query = getConnection().prepareStatement(GET_ALL_EMPLOYEES_FROM_DATABASE)) {
            try (ResultSet resultSet = query.executeQuery()) {
                while (resultSet.next()) {
                    result.add(
                            toDTO(getFromResultSet(resultSet))
                    );
                }
            }
        }
        return result;
    }

}

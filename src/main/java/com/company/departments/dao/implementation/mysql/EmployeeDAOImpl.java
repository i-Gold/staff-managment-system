package com.company.departments.dao.implementation.mysql;

import com.company.departments.dao.DAOFactory;
import com.company.departments.dao.EmployeeDAO;
import com.company.departments.exception.BadRequest;
import com.company.departments.model.Employee;
import com.company.departments.model.dto.EmployeeDTO;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.*;

import static com.company.departments.converter.EmployeeConverter.toDTO;
import static com.company.departments.dao.implementation.mysql.DataInitializer.getConnection;
import static com.company.departments.dao.implementation.mysql.queries.Queries.*;

public class EmployeeDAOImpl implements EmployeeDAO {

    private static final Logger logger = Logger.getLogger(EmployeeDAOImpl.class);

    @Override
    public Employee add(Employee employee) throws SQLException {
        if (employee == null) {
            throw new BadRequest();
        }
        try (Connection connection = getConnection()) {
            try (PreparedStatement query = connection.prepareStatement(CREATE_EMPLOYEE)) {
                connection.setAutoCommit(false);
                setParametersAndExecuteQuery(query, employee);
                try (PreparedStatement updateDepartment = connection.prepareStatement(CHANGE_AMOUNT_OF_EMPLOYEES_UP)) {
                    updateDepartment.setLong(1, employee.getDepartment().getId());
                    updateDepartment.executeUpdate();
                }
                connection.commit();
                return employee;
            } catch (SQLException e) {
                logger.error(e);
                connection.rollback();
            }
        }
        throw new SQLException();
    }

    private void setParametersAndExecuteQuery(PreparedStatement query, Employee employee) throws SQLException {
        query.setString(1, employee.getFullName());
        query.setString(2, employee.getEmail());
        if (employee.getId() == null) {
            query.setLong(3, employee.getDepartment().getId());
        } else {
            query.setLong(3, employee.getId());
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
                        .getDepartmentDAO().findById(resultSet.getLong("department_id"))
                        .orElse(null))
                .build();
    }

    @Override
    public Employee update(Employee employee) throws SQLException {
        if (employee == null) {
            throw new BadRequest();
        }
        try (Connection connection = getConnection()) {
            try (PreparedStatement query = connection.prepareStatement(UPDATE_EMPLOYEE)) {
                connection.setAutoCommit(false);
                setParametersAndExecuteQuery(query, employee);
                connection.commit();
                return employee;
            } catch (SQLException e) {
                logger.error(e);
                connection.rollback();
            }
        }
        throw new SQLException();
    }

    @Override
    public boolean deleteById(Long id) throws SQLException {
        Employee employee = findById(id)
                .orElse(null);
        if (employee != null) {
            try (Connection connection = getConnection()) {
                try (PreparedStatement query = getConnection().prepareStatement(DELETE_EMPLOYEE)) {
                    connection.setAutoCommit(false);
                    query.setLong(1, id);
                    try (PreparedStatement updateDepartment = getConnection().prepareStatement(CHANGE_AMOUNT_OF_EMPLOYEES_DOWN)) {
                        updateDepartment.setLong(1, employee.getDepartment().getId());
                        updateDepartment.executeUpdate();
                    }
                    boolean result = query.execute();
                    connection.commit();
                    return result;
                } catch (SQLException e) {
                    logger.error(e);
                    connection.rollback();
                }
            }
        }
        return false;
    }

    @Override
    public Optional<Employee> findById(Long id) throws SQLException {
        try (Connection connection = getConnection()) {
            try (ResultSet resultSet = SearchEntity.find(connection, FIND_EMPLOYEE_BY_ID, id)) {
                if (resultSet.next()) {
                    return Optional.of(getFromResultSet(resultSet));
                }
                return Optional.empty();
            }
        }
    }

    @Override
    public Set<Employee> findAllByDepartmentId(Long departmentId) throws SQLException {
        Set<Employee> result = new HashSet<>();
        try (Connection connection = getConnection()) {
            try (PreparedStatement query = connection.prepareStatement(GET_ALL_EMPLOYEES_OF_DEPARTMENT)) {
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
        }
        return result;
    }

    @Override
    public List<EmployeeDTO> getAll() throws SQLException {
        List<EmployeeDTO> result = new ArrayList<>();
        try (Connection connection = getConnection()) {
            try (PreparedStatement query = connection.prepareStatement(GET_ALL_EMPLOYEES_FROM_DATABASE)) {
                try (ResultSet resultSet = query.executeQuery()) {
                    while (resultSet.next()) {
                        result.add(
                                toDTO(getFromResultSet(resultSet))
                        );
                    }
                }
            }
        }
        return result;
    }

}

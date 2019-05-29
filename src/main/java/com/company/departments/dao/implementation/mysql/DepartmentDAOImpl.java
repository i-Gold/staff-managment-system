package com.company.departments.dao.implementation.mysql;

import com.company.departments.dao.DepartmentDAO;
import com.company.departments.exception.BadRequest;
import com.company.departments.model.Department;
import com.company.departments.model.dto.DepartmentDTO;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.company.departments.converter.DepartmentConverter.toDTO;
import static com.company.departments.dao.implementation.mysql.DataInitializer.getConnection;
import static com.company.departments.dao.implementation.mysql.queries.Queries.*;

public class DepartmentDAOImpl implements DepartmentDAO {

    private static final Logger logger = Logger.getLogger(DepartmentDAOImpl.class);

    @Override
    public Department add(Department department) throws SQLException {
        return executeRequest(department, CREATE_DEPARTMENT);
    }

    private Department executeRequest(Department department, String request) throws SQLException {
        if (department == null) {
            throw new BadRequest();
        }
        try (Connection connection = getConnection()) {
            try (PreparedStatement query = connection.prepareStatement(request)) {
                connection.setAutoCommit(false);
                setParamsAndExecuteQuery(query, department);
                connection.commit();
                return department;
            } catch (SQLException e) {
                logger.error(e);
                connection.rollback();
            }
        }
        throw new SQLException();
    }

    private void setParamsAndExecuteQuery(PreparedStatement query, Department department) throws SQLException {
        query.setString(1, department.getName());
        if (department.getAmountOfEmployees() != null) {
            query.setInt(2, department.getAmountOfEmployees());
        }
        if (department.getId() != null) {
            query.setLong(3, department.getId());
        }
        query.executeUpdate();
    }

    private Department getFromResultSet(ResultSet resultSet) throws SQLException {
        return new Department.Builder()
                .id(resultSet.getLong("id"))
                .name(resultSet.getString("name"))
                .amountOfEmployees(resultSet.getInt("amount_of_employees"))
//                .employees(new HashSet<>(DAOFactory.getInstance()
//                        .getEmployeeDAO().findAllByDepartmentId(resultSet.getLong("id"))))
                .build();
    }

    @Override
    public Department update(Department department) throws SQLException {
        return executeRequest(department, UPDATE_DEPARTMENT);
    }

    @Override
    public boolean deleteById(Long id) throws SQLException {
        try (Connection connection = getConnection()) {
            try (PreparedStatement query = connection.prepareStatement(DELETE_EMPLOYEES_FROM_DEPARTMENT)) {
                connection.setAutoCommit(false);
                query.setLong(1, id);
            }
            try (PreparedStatement query = connection.prepareStatement(DELETE_DEPARTMENT)) {
                query.setLong(1, id);
                boolean result = query.execute();
                connection.commit();
                return result;
            } catch (SQLException e) {
                logger.error(e);
                connection.rollback();
            }
        }
        return false;
    }

    @Override
    public Optional<Department> findById(Long id) throws SQLException {
        try (Connection connection = getConnection()) {
            try (ResultSet resultSet = SearchEntity.find(connection, FIND_DEPARTMENT_BY_ID, id)) {
                if (resultSet.next()) {
                    return Optional.of(getFromResultSet(resultSet));
                }
                return Optional.empty();
            }
        }
    }

    @Override
    public List<DepartmentDTO> getAll() throws SQLException {
        List<DepartmentDTO> result = new ArrayList<>();
        try (Connection connection = getConnection()) {
            try (PreparedStatement query = connection.prepareStatement(GET_ALL_DEPARTMENTS_FROM_DATABASE)) {
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

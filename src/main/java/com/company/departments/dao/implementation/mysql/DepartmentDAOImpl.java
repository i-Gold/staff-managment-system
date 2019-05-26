package com.company.departments.dao.implementation.mysql;

import com.company.departments.dao.DAOFactory;
import com.company.departments.dao.DepartmentDAO;
import com.company.departments.exception.BadRequest;
import com.company.departments.model.Department;
import com.company.departments.model.Employee;
import com.company.departments.model.dto.DepartmentDTO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

import static com.company.departments.dao.implementation.mysql.DataInitializer.getConnection;
import static com.company.departments.dao.implementation.mysql.queries.Queries.*;

public class DepartmentDAOImpl implements DepartmentDAO {

    private Department fromDTO(DepartmentDTO departmentDTO) throws SQLException {
        return new Department.Builder()
                .id(departmentDTO.getId())
                .name(departmentDTO.getName())
                .amountOfEmployees(departmentDTO.getAmountOfEmployees())
                .employees(Objects.nonNull(departmentDTO.getEmployeeIds()) ?
                        getListOfEmployeesByIds(departmentDTO.getEmployeeIds())
                        : null)
                .build();
    }

    private DepartmentDTO toDTO(Department department) {
        return new DepartmentDTO.Builder()
                .id(department.getId())
                .name(department.getName())
                .amountOfEmployees(department.getAmountOfEmployees())
                .employees(Objects.nonNull(department.getEmployees()) ?
                        new HashSet<>(department.getEmployees().stream()
                                .filter(Objects::nonNull)
                                .map(Employee::getId)
                                .collect(Collectors.toSet()))
                        : null)
                .build();
    }

    @Override
    public Department add(DepartmentDTO departmentDTO) throws SQLException {
        if (departmentDTO == null) {
            throw new BadRequest();
        }
        try (PreparedStatement query = getConnection().prepareStatement(CREATE_DEPARTMENT)) {
            setParamsAndExecuteQuery(query, departmentDTO);
            return fromDTO(departmentDTO);
        }
    }

    private void setParamsAndExecuteQuery(PreparedStatement query, DepartmentDTO departmentDTO) throws SQLException {
        query.setString(1, departmentDTO.getName());
        if (departmentDTO.getAmountOfEmployees() != null) {
            query.setInt(2, departmentDTO.getAmountOfEmployees());
        }
        if (departmentDTO.getId() != null) {
            query.setLong(3, departmentDTO.getId());
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
    public Department update(DepartmentDTO departmentDTO) throws SQLException {
        if (departmentDTO == null) {
            throw new BadRequest();
        }
        try (PreparedStatement query = getConnection().prepareStatement(UPDATE_DEPARTMENT)) {
            setParamsAndExecuteQuery(query, departmentDTO);
            return fromDTO(departmentDTO);
        }
    }

    @Override
    public boolean deleteById(Long id) throws SQLException {
        try (PreparedStatement query = getConnection().prepareStatement(DELETE_EMPLOYEES_FROM_DEPARTMENT)) {
            query.setLong(1, id);
        }
        try (PreparedStatement query = getConnection().prepareStatement(DELETE_DEPARTMENT)) {
            query.setLong(1, id);
            return query.execute();
        }
    }

    @Override
    public Optional<Department> findById(Long id) throws SQLException {
        try (ResultSet resultSet = SearchEntity.find(FIND_DEPARTMENT_BY_ID, id)) {
            if (resultSet.next()) {
                return Optional.of(getFromResultSet(resultSet));
            }
            return Optional.empty();
        }
    }

    @Override
    public List<DepartmentDTO> getAll() throws SQLException {
        List<DepartmentDTO> result = new ArrayList<>();
        try (PreparedStatement query = getConnection().prepareStatement(GET_ALL_DEPARTMENTS_FROM_DATABASE)) {
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

    private HashSet<Employee> getListOfEmployeesByIds(Set<Long> employeeIds) throws SQLException {
        HashSet<Employee> result = new HashSet<>();
        for (Long employeeId : employeeIds) {
            result.add(DAOFactory.getInstance()
                    .getEmployeeDAO().findById(employeeId).get());
        }
        return result;
    }

}

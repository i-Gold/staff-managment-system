package com.company.departments.dao.implementation.mysql.queries;

public class Queries {

    public static final String CREATE_DEPARTMENT = "INSERT INTO departments(name, amount_of_employees) VALUES (?,?)";
    public static final String CREATE_EMPLOYEE = "INSERT INTO employees(full_name, email, department_id) VALUES (?,?,?)";

    public static final String UPDATE_EMPLOYEE = "UPDATE employees SET full_name = ?, email = ? WHERE id = ?";
    public static final String UPDATE_DEPARTMENT = "UPDATE departments SET name = ?, amount_of_employees = ? WHERE id = ?";

    public static final String DELETE_EMPLOYEE = "DELETE FROM employees WHERE id = ?";
    public static final String DELETE_DEPARTMENT = "DELETE FROM departments WHERE id = ?";

    public static final String GET_ALL_DEPARTMENTS_FROM_DATABASE = "SELECT * FROM departments";
    public static final String GET_ALL_EMPLOYEES_FROM_DATABASE = "SELECT * FROM employees";

    public static final String GET_ALL_EMPLOYEES_OF_DEPARTMENT = "SELECT * FROM employees WHERE department_id = ?";

    public static final String FIND_EMPLOYEE_BY_ID = "SELECT * FROM employees WHERE id = ?";
    public static final String FIND_DEPARTMENT_BY_ID = "SELECT * FROM departments WHERE id = ?";

    public static final String CHANGE_AMOUNT_OF_EMPLOYEES_UP = "UPDATE departments SET amount_of_employees = amount_of_employees + 1 WHERE id = ?";
    public static final String CHANGE_AMOUNT_OF_EMPLOYEES_DOWN = "UPDATE departments SET amount_of_employees = amount_of_employees - 1 WHERE id = ? AND amount_of_employees > 0";

    public static final String DELETE_EMPLOYEES_FROM_DEPARTMENT = "DELETE FROM employees WHERE department_id = ?";

}

package com.company.departments.dao.implementation.mysql;

import com.company.departments.exception.ConnectionException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DataInitializer {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/staff?useSSL=false&serverTimezone=UTC&characterEncoding=UTF-8";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "796914cf23814500fbd9d95678b23ec2";
    private static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";

    private static final String CREATE_EMPLOYEE_TABLE = "CREATE TABLE IF NOT EXISTS employees(" +
            "id BIGINT NOT NULL AUTO_INCREMENT, " +
            "full_name VARCHAR(55) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL, " +
            "email VARCHAR(55) NOT NULL UNIQUE , " +
            "started_work_at DATETIME DEFAULT CURRENT_TIMESTAMP, " +
            "department_id BIGINT NOT NULL, " +
            "PRIMARY KEY(id), " +
            "CONSTRAINT fk_department_id FOREIGN KEY(department_id) REFERENCES departments(id) ON UPDATE CASCADE ON DELETE CASCADE" +
            ") ENGINE=InnoDB";

    private static final String CREATE_DEPARTMENT_TABLE = "CREATE TABLE IF NOT EXISTS departments(" +
            "id BIGINT NOT NULL AUTO_INCREMENT, " +
            "name VARCHAR(55) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL UNIQUE, " +
            "amount_of_employees INT NOT NULL DEFAULT 0, " +
            "PRIMARY KEY(id)" +
            ") ENGINE=InnoDB";

    static Connection getConnection() {
        try {
            Class.forName(DB_DRIVER);
            return DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            throw new ConnectionException(e.getMessage());
        }
    }

    public static void loadData() {
        createTablesInDataBase();
    }

    private static void createTablesInDataBase() {
        Connection connection = null;
        try {
            Class.forName(DB_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
            createDepartments();
            createEmployees();
        } catch (SQLException | ClassNotFoundException e) {
            throw new ConnectionException(e.getMessage());
        } finally {
            try {
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private static void createEmployees() throws SQLException {
        try (Statement statement = getConnection().createStatement()) {
            statement.execute(CREATE_EMPLOYEE_TABLE);
        }
    }

    private static void createDepartments() throws SQLException {
        try (Statement statement = getConnection().createStatement()) {
            statement.execute(CREATE_DEPARTMENT_TABLE);
        }
    }

}

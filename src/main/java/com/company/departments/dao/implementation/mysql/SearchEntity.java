package com.company.departments.dao.implementation.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

class SearchEntity {

    static ResultSet find(Connection connection, String query, Long id) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setLong(1, id);
        return statement.executeQuery();
    }

}

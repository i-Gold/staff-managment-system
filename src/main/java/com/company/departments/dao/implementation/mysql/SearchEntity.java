package com.company.departments.dao.implementation.mysql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.company.departments.dao.implementation.mysql.DataInitializer.getConnection;

class SearchEntity {

    static ResultSet find(String query, Long id) throws SQLException {
        PreparedStatement statement = getConnection().prepareStatement(query);
        statement.setLong(1, id);
        return statement.executeQuery();
    }

}

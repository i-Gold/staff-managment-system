package com.company.departments.dao;

import com.company.departments.dao.implementation.mysql.DAOFactoryImpl;

public interface DAOFactory {

    EmployeeDAO getEmployeeDAO();

    DepartmentDAO getDepartmentDAO();

    static DAOFactory getInstance() {
        return new DAOFactoryImpl();
    }

}

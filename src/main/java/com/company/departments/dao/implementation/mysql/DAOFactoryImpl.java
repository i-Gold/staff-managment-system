package com.company.departments.dao.implementation.mysql;

import com.company.departments.dao.DAOFactory;
import com.company.departments.dao.DepartmentDAO;
import com.company.departments.dao.EmployeeDAO;

public class DAOFactoryImpl implements DAOFactory {

    @Override
    public EmployeeDAO getEmployeeDAO() {
        return new EmployeeDAOImpl();
    }

    @Override
    public DepartmentDAO getDepartmentDAO() {
        return new DepartmentDAOImpl();
    }

}

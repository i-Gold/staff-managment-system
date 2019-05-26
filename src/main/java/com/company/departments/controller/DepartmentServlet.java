package com.company.departments.controller;

import com.company.departments.dao.DAOFactory;
import com.company.departments.dao.implementation.mysql.DAOFactoryImpl;
import com.company.departments.model.Department;
import com.company.departments.model.dto.DepartmentDTO;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

@WebServlet("/")
public class DepartmentServlet extends HttpServlet {

    private static final long serialVersionUID = 3082591028280949051L;
    private final DAOFactory daoFactory = new DAOFactoryImpl();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getServletPath();
        try {
            switch (action) {
                case "/new":
                    showNewForm(request, response);
                    break;
                case "/insert":
                    addNewDepartment(request, response);
                    break;
                case "/delete":
                    deleteDepartment(request, response);
                    break;
                case "/edit":
                    showEditForm(request, response);
                    break;
                case "/update":
                    updateDepartment(request, response);
                    break;
                default:
                    getAllDepartments(request, response);
                    break;
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    private void getAllDepartments(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
        List<DepartmentDTO> departments = daoFactory.getDepartmentDAO()
                .getAll();
        request.setAttribute("listOfDepartments", departments);
        RequestDispatcher dispatcher = request.getRequestDispatcher("departments-view.jsp");
        dispatcher.forward(request, response);
    }

    private void addNewDepartment(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        HashSet<Long> employeeIds = new HashSet<>();
        Enumeration<String> parameters = request.getParameterNames();
        while (parameters.hasMoreElements()) {
            if (parameters.nextElement().equals("employeeId")) {
                employeeIds.add(Long.valueOf(parameters.nextElement()));
            }
        }
        daoFactory.getDepartmentDAO()
                .add(new DepartmentDTO.Builder()
                        .name(request.getParameter("name"))
                        .amountOfEmployees(Objects.nonNull(request.getParameter("amountOfEmployees")) &&
                                !Objects.equals(request.getParameter("amountOfEmployees"), "") ?
                                Integer.valueOf(request.getParameter("amountOfEmployees")) : 0)
                        .employees(employeeIds)
                        .build());
        response.sendRedirect("departments");
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("department-edit.jsp");
        dispatcher.forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        Department department = daoFactory.getDepartmentDAO()
                .findById(Long.valueOf(request.getParameter("id"))).get();
        RequestDispatcher dispatcher = request.getRequestDispatcher("department-edit.jsp");
        request.setAttribute("department", department);
        dispatcher.forward(request, response);
    }

    private void updateDepartment(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        daoFactory.getDepartmentDAO()
                .update(new DepartmentDTO.Builder()
                        .id(Long.valueOf(request.getParameter("id")))
                        .name(request.getParameter("name"))
                        .amountOfEmployees(Objects.nonNull(request.getParameter("amountOfEmployees")) &&
                                !Objects.equals(request.getParameter("amountOfEmployees"), "") ?
                                Integer.valueOf(request.getParameter("amountOfEmployees")) : 0)
                        .build());
        response.sendRedirect("departments");
    }

    private void deleteDepartment(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        daoFactory.getDepartmentDAO()
                .deleteById(Long.valueOf(request.getParameter("id")));
        response.sendRedirect("departments");

    }

}

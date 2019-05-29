package com.company.departments.controller;

import com.company.departments.model.dto.DepartmentDTO;
import com.company.departments.service.DepartmentService;
import org.apache.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

@WebServlet("/")
public class DepartmentServlet extends HttpServlet {

    private static final long serialVersionUID = 3082591028280949051L;

    private final Logger logger = Logger.getLogger(this.getClass().getSimpleName());
    private final DepartmentService departmentService = new DepartmentService();

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
            logger.error(e);
            throw new ServletException(e.getMessage());
        }
    }

    private void getAllDepartments(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
        List<DepartmentDTO> departments = departmentService.getAllDepartments();
        request.setAttribute("listOfDepartments", departments);
        RequestDispatcher dispatcher = request.getRequestDispatcher("departments-view.jsp");
        dispatcher.forward(request, response);
    }

    private void addNewDepartment(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        departmentService.addNewDepartment(new DepartmentDTO.Builder()
                .name(request.getParameter("name"))
                .amountOfEmployees(Objects.nonNull(request.getParameter("amountOfEmployees")) &&
                        !Objects.equals(request.getParameter("amountOfEmployees"), "") ?
                        Integer.valueOf(request.getParameter("amountOfEmployees")) : 0)
                .build());
        response.sendRedirect("departments");
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("department-edit.jsp");
        dispatcher.forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        DepartmentDTO departmentDTO = departmentService.findDepartmentById(Long.valueOf(request.getParameter("id")))
                .orElse(null);
        RequestDispatcher dispatcher = request.getRequestDispatcher("department-edit.jsp");
        request.setAttribute("department", departmentDTO);
        dispatcher.forward(request, response);
    }

    private void updateDepartment(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        departmentService.updateDepartment(new DepartmentDTO.Builder()
                .id(Long.valueOf(request.getParameter("id")))
                .name(request.getParameter("name"))
                .amountOfEmployees(Objects.nonNull(request.getParameter("amountOfEmployees")) &&
                        !Objects.equals(request.getParameter("amountOfEmployees"), "") ?
                        Integer.valueOf(request.getParameter("amountOfEmployees")) : 0)
                .build());
        response.sendRedirect("departments");
    }

    private void deleteDepartment(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        departmentService.deleteDepartment(Long.valueOf(request.getParameter("id")));
        response.sendRedirect("departments");
    }

}

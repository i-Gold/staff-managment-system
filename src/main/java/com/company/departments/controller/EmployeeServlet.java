package com.company.departments.controller;

import com.company.departments.model.Employee;
import com.company.departments.model.dto.EmployeeDTO;
import com.company.departments.service.EmployeeService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@WebServlet(urlPatterns = {"/fromDepartment", "/newEmployee", "/addEmployee", "/deleteEmployee", "/editEmployee", "/updateEmployee", "/allEmployees"})
public class EmployeeServlet extends HttpServlet {

    private static final long serialVersionUID = 3972921947997968896L;
    private final EmployeeService employeeService = new EmployeeService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getServletPath();
        try {
            switch (action) {
                case "/newEmployee":
                    showNewEmployeeForm(request, response);
                    break;
                case "/addEmployee":
                    addNewEmployee(request, response);
                    break;
                case "/deleteEmployee":
                    deleteEmployee(request, response);
                    break;
                case "/editEmployee":
                    showEditEmployeeForm(request, response);
                    break;
                case "/updateEmployee":
                    updateEmployee(request, response);
                    break;
                case "/fromDepartment":
                    employeesFromDepartment(request, response);
                    break;
                case "/allEmployees":
                    getAllEmployees(request, response);
                    break;
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    private void addNewEmployee(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        employeeService.addNewEmployee(new EmployeeDTO.Builder()
                .fullName(request.getParameter("name"))
                .email(request.getParameter("email"))
                .startedWorkAt(LocalDateTime.now())
                .departmentId(Long.valueOf(request.getParameter("departmentId")))
                .build());
        response.sendRedirect("fromDepartment?departmentId=" + request.getParameter("departmentId"));
    }

    private void showNewEmployeeForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("employee-edit.jsp");
        dispatcher.forward(request, response);
    }

    private void showEditEmployeeForm(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        Employee employee = employeeService.findEmployeeById(Long.valueOf(request.getParameter("id")))
                .orElse(null);
        RequestDispatcher dispatcher = request.getRequestDispatcher("employee-edit.jsp");
        request.setAttribute("employee", employee);
        dispatcher.forward(request, response);
    }

    private void updateEmployee(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        employeeService.updateEmployee(new EmployeeDTO.Builder()
                .id(Long.valueOf(request.getParameter("id")))
                .fullName(request.getParameter("name"))
                .email(request.getParameter("email"))
                .build());
        response.sendRedirect("departments");
    }

    private void deleteEmployee(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        employeeService.deleteEmployeeById(Long.valueOf(request.getParameter("id")));
        response.sendRedirect("departments");
    }

    private void employeesFromDepartment(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
        Set<Employee> employees = employeeService.findAllByDepartmentId(Long.valueOf(request.getParameter("departmentId")));
        request.setAttribute("listOfEmployees", employees);
        RequestDispatcher dispatcher = request.getRequestDispatcher("employees-view.jsp");
        dispatcher.forward(request, response);
    }

    private void getAllEmployees(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
        List<EmployeeDTO> employees = employeeService.getAllEmployees();
        request.setAttribute("listOfEmployees", employees);
        RequestDispatcher dispatcher = request.getRequestDispatcher("employees-view.jsp");
        dispatcher.forward(request, response);
    }

}

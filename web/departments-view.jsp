<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Staff Management</title>
</head>
<body>
<div style="text-align: center">
    <h1>Departments</h1>
    <h2>
        <a href="new">Add new department</a>
        &nbsp;&nbsp;&nbsp;&nbsp;
        <a href="allEmployees">Show all employees</a>
    </h2>
</div>
<div align="center">
    <table border="1" cellpadding="5" style="text-align: center">
        <caption><h2>List of Departments</h2></caption>
        <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Amount of employees</th>
            <th>Actions</th>
        </tr>
        <jsp:useBean id="listOfDepartments" scope="request" type="java.util.List"/>
        <c:forEach var="department" items="${listOfDepartments}">
            <tr>
                <td><c:out value="${department.id}"/></td>
                <td><c:out value="${department.name}"/></td>
                <td><c:out value="${department.amountOfEmployees}"/></td>
                <td>
                    <a href="fromDepartment?departmentId=<c:out value='${department.id}' />">Employees</a>
                    &nbsp;&nbsp;&nbsp;&nbsp;
                    <a href="edit?id=<c:out value='${department.id}' />">Edit</a>
                    &nbsp;&nbsp;&nbsp;&nbsp;
                    <a href="delete?id=<c:out value='${department.id}' />">Delete</a>
                </td>
            </tr>
        </c:forEach>
    </table>
</div>
</body>
</html>
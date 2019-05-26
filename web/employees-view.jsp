<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Employees of Department</title>
</head>
<body>
<div style="text-align: center;">
    <h1>Employees</h1>
    <h2>
        <a href="newEmployee">Add new employee</a>
        &nbsp;&nbsp;&nbsp;&nbsp;
        <a href="departments">Back to departments</a>
    </h2>
</div>
<div align="center">
    <table border="1" cellpadding="5" style="text-align: center">
        <caption><h2>List of Employees</h2></caption>
        <tr>
            <th>ID</th>
            <th>Full name</th>
            <th>Email</th>
            <th>Date of start working</th>
            <th>Actions</th>
        </tr>
        <c:forEach var="employee" items="${listOfEmployees}">
            <tr>
                <td><c:out value="${employee.id}"/></td>
                <td><c:out value="${employee.fullName}"/></td>
                <td><c:out value="${employee.email}"/></td>
                <td><c:out value="${employee.startedWorkAt}"/></td>
                <td>
                    <a href="editEmployee?id=<c:out value='${employee.id}' />">Edit</a>
                    &nbsp;&nbsp;&nbsp;&nbsp;
                    <a href="deleteEmployee?id=<c:out value='${employee.id}' />">Delete</a>
                </td>
            </tr>
        </c:forEach>
    </table>
</div>
</body>
</html>
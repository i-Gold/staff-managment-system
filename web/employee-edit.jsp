<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Staff Management</title>
</head>
<body>
<div style="text-align: center;">
    <h1>Employees</h1>
    <h2>
        <a href="newEmployee">Add New Employee</a>
        &nbsp;&nbsp;&nbsp;&nbsp;
        <a href="departments">Back to departments</a>
    </h2>
</div>
<div align="center">
    <c:if test="${employee != null}">
    <form action="updateEmployee" method="post">
        </c:if>
        <c:if test="${employee == null}">
        <form action="addEmployee" method="post">
            </c:if>
            <table border="1" cellpadding="5">
                <caption>
                    <h2>
                        <c:if test="${employee != null}">
                            Edit Employee
                        </c:if>
                        <c:if test="${employee == null}">
                            Add New Employee
                        </c:if>
                    </h2>
                </caption>
                <c:if test="${employee != null}">
                    <input type="hidden" name="id" value="<c:out value='${employee.id}' />"/>
                </c:if>
                <tr>
                    <th>Name:</th>
                    <td>
                        <input type="text" name="name" size="45"
                               value="<c:out value='${employee.fullName}' />"
                        />
                    </td>
                </tr>
                <tr>
                    <th>Email:</th>
                    <td>
                        <input type="text" name="email" size="45"
                               value="<c:out value='${employee.email}' />"
                        />
                    </td>
                </tr>
                <tr>
                    <th>Department's ID:</th>
                    <td>
                        <input type="text" name="departmentId" size="15"
                               value="<c:out value='${employee.department.id}' />"
                        />
                    </td>
                </tr>
                <tr>
                    <td colspan="2" align="center">
                        <input type="submit" value="Save"/>
                    </td>
                </tr>
            </table>
        </form>
</div>
</body>
</html>
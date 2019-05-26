<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Staff Management</title>
</head>
<body>
<div style="text-align: center;">
    <h1>Departments</h1>
    <h2>
        <a href="new">Add new</a>
        &nbsp;&nbsp;&nbsp;&nbsp;
        <a href="departments">Back to departments</a>
    </h2>
</div>
<div align="center">
    <c:if test="${department != null}">
    <form action="update" method="post">
        </c:if>
        <c:if test="${department == null}">
        <form action="insert" method="post">
            </c:if>
            <table border="1" cellpadding="5">
                <caption>
                    <h2>
                        <c:if test="${department != null}">
                            Edit Department
                        </c:if>
                        <c:if test="${department == null}">
                            Add New Department
                        </c:if>
                    </h2>
                </caption>
                <c:if test="${department != null}">
                    <input type="hidden" name="id" value="<c:out value='${department.id}' />"/>
                </c:if>
                <tr>
                    <th>Department's Name:</th>
                    <td>
                        <input type="text" name="name" size="45"
                               value="<c:out value='${department.name}' />"
                        />
                    </td>
                </tr>
                <tr>
                    <th>Amount of employees:</th>
                    <td>
                        <input type="text" name="amountOfEmployees" size="45"
                               value="<c:out value='${department.amountOfEmployees}' />"
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
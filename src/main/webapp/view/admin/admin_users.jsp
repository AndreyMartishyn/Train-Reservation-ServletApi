<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java"
import="ua.martishyn.app.data.entities.User"
%>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/view/static/meta.html" %>
</head>
<body>
<%@ include file="/view/static/header.html" %>
 <div align="center">
        <h1>Welcome to Train Reservation Website Admin Panel</h1>
        <b>(${user.email})</b>
        <br><br>

        <table class="table table-hover" class="m-5">
        <thead>
                        <tr>
                          <th scope="col">User_id</th>
                          <th scope="col">User_first_name</th>
                          <th scope="col">User_last_name</th>
                          <th scope="col">Email</th>
                          <th scope="col">Role</th>
                          <th scope="col">Action</th>
                        </tr>

        </thead>
        <tbody>
        <c:forEach items="${users}" var="user" >
        <tr>
        <td><c:out value="${user.id}"/> </td>
        <td><c:out value="${user.firstName}"/> </td>
        <td><c:out value="${user.lastName}"/> </td>
        <td><c:out value="${user.email}"/> </td>
        <td><c:out value="${user.role}"/> </td>
        <td>
        <a href="user-edit.command?id=<c:out value='${user.id}' />">Edit user</a>
        <a href="user-delete.command?id=<c:out value='${user.id}' />">Delete user</a>
        </td>
        </tr>
        </c:forEach>
        </tbody>
        </table>
        <br><br>
        <a href="logout.command">Logout</a>
    </div>
</body>
</html>
</body>
</html>
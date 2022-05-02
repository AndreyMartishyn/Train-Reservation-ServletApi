<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java"
import="ua.martishyn.app.data.entities.User"
%>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/view/static/meta.jsp" %>
</head>
<body>
<%@ include file="/view/static/header.jsp" %>
<br><br>
 <div align="center">
		<form>
		 <h2>Users</h2>
        <table class="table table-striped table-responsive-md btn-table" >
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
           <a href="user-edit.command?id=<c:out value='${user.id}' />" class="btn btn-outline-primary btn-sm m-0 waves-effect">Edit user</a>
           <a href="user-delete.command?id=<c:out value='${user.id}' />" class="btn btn-outline-primary btn-sm m-0 waves-effect">Delete user</a>
        </td>
        </tr>
        </c:forEach>
        </tbody>
        </table>
       </form>
</body>
</html>
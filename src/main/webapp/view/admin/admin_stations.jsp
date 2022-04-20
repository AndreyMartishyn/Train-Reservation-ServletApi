<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java"
import="ua.martishyn.app.data.entities.Station"
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
                          <th scope="col">Station number</th>
                          <th scope="col">Station name</th>
                          <th scope="col">Code</th>
                          <th scope="col">Action</th>
                        </tr>

        </thead>
        <tbody>
        <c:forEach items="${stations}" var="station" >
        <tr>
        <td><c:out value="${station.id}"/> </td>
        <td><c:out value="${station.name}"/> </td>
        <td><c:out value="${station.code}"/> </td>
        <td>
        <a href="station-edit.command?id=<c:out value='${station.id}' />">Edit station</a>
        <a href="station-delete.command?id=<c:out value='${station.id}' />">Delete station</a>
        </td>
        </tr>
        </c:forEach>
        </tbody>
        </table>
        <br><br>
        <a href="station-add.command">Add new station</a>
        <br><br>
        <a href="logout.command">Logout</a>
    </div>
</body>
</html>
</body>
</html>
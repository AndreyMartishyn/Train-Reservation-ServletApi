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
                          <th scope="col">Route id</th>
                          <th scope="col">Train id</th>
                          <th scope="col">Station</th>
                          <th scope="col">Date of arrival</th>
                          <th scope="col">Date of departure</th>
                          <th scope="col">Action</th>
                        </tr>

        </thead>
        <tbody>
        <c:forEach items="${routes}" var="route" >
        <tr>
        <td><c:out value="${route.id}"/> </td>
        <td><c:out value="${route.trainId}"/> </td>
        <td><c:out value="${route.stationId}"/> </td>
        <td><c:out value="${route.arrival}"/> </td>
        <td><c:out value="${route.departure}"/> </td>
        <td>
        <a href="route-edit.command?id=<c:out value='${route.id}' />">Edit route</a>
        <a href="route-delete.command?id=<c:out value='${route.id}' />">Delete route</a>
        </td>
        </tr>
        </c:forEach>
        </tbody>
        </table>
        <br><br>
        <a href="route-add.command">Add new route</a>
        <br><br>
        <a href="logout.command">Logout</a>
    </div>
</body>
</html>
</body>
</html>
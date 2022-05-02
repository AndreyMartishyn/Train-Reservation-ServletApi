<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java"
import="ua.martishyn.app.data.entities.Station"
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
        <h2>Route-stations</h2>
        <span style ="text-align: center; color:red; font-family:courier; font-size:80%;">${noRoutes}</span>
        <span style ="text-align: center; color:red; font-family:courier; font-size:80%;">${success}</span>

        <table class="table table-striped table-responsive-md btn-table" class="m-5">
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
         <a href="route-edit.command?id=<c:out value='${route.id}'/>" class="btn btn-outline-primary btn-sm m-0 waves-effect">Edit route</a>
         <a href="route-delete.command?id=<c:out value='${route.id}' />" class="btn btn-outline-primary btn-sm m-0 waves-effect">Delete route</a>
        </td>
        </tr>
        </c:forEach>
        </tbody>

        </table>
        </form>
        <a href="route-add.command" class="btn btn-dark">Add route</a>
 </body>
</html>
</body>
</html>
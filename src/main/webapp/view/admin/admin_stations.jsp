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
		 <h2>Stations</h2>
        <table class="table table-striped table-responsive-md btn-table" >
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
           <a href="station-edit.command?id=<c:out value='${station.id}' />" class="btn btn-outline-primary btn-sm m-0 waves-effect">Edit station</a>
           <a href="station-delete.command?id=<c:out value='${station.id}' />" class="btn btn-outline-primary btn-sm m-0 waves-effect">Delete station</a>
        </td>
        </tr>
        </c:forEach>
        </tbody>
        </table>
        </form>
        <a href="station-add.command" class="btn btn-dark">Add station</a>
</body>
</html>
</body>
</html>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/view/static/meta.html"%>
</head>
<body>
<%@ include file="/view/static/header.jsp"%>
<br>
<div class="row justify-content-center">
<div class="col-md-6 col-lg-4">
<form action="customer-train-search.command" method="post">
<h3 class="mb-4 text-center">Train Search</h3>
		<div class="form-group">
        <label>Station From</label>
        <select class="custom-select mr-sm-2" name="stationFrom">
        <c:forEach var="station" items="${stations}">
        <option value="${station.id}">${station.name}</option>
        </c:forEach>
        </select>

        </div>
		<div class="form-group">
        <label>Station To</label>
        <select class="custom-select mr-sm-2" name="stationTo">
        <c:forEach var="station" items="${stations}">
        <option value="${station.id}">${station.name}</option>

        </c:forEach>

        </select>
        </div>

            <button type="submit" class="btn btn-primary">Make search</button>
        </div>
        </div>
        </form>
</body>
</html>
<%@ include file="/view/static/basic_context.jsp" %>
<head>
<%@ include file="/view/static/meta.jsp"%>
</head>
<body>
<%@ include file="/view/static/header.jsp"%>
<br>
<div align="center">
<div class="col-md-5">
<form action="customer-train-search.command" method="post">
<h3 class="mb-4 text-center"><fmt:message key="user.page.book.title"/></h3>
		<div class="form-group">
        <label><fmt:message key="user.page.book.departure"/></label>
        <select class="custom-select mr-sm-2" name="stationFrom">
        <c:forEach var="station" items="${stations}">
        <option value="${station.id}"><fmt:message key="${station.name}"/></option>
        </c:forEach>
        </select>

        </div>
		<div class="form-group">
        <label><fmt:message key="user.page.book.arrival"/></label>
        <select class="custom-select mr-sm-2" name="stationTo">
        <c:forEach var="station" items="${stations}">
        <option value="${station.id}"><fmt:message key="${station.name}"/></option>
        </c:forEach>
        </select>
        		        <span style ="text-align: center; color:red; font-family:courier; font-size:80%;">${noRoutes}</span>
        </div>
            <button type="submit" class="btn btn-dark"><fmt:message key="user.page.book.search"/></button>
        </div>
        </div>
        </form>


 		<c:if test="${sessionScope.suitableRoutes != null}">
 		<br><br>
 		<div align="center">
 		<form>
		<h3 class="mb-4 text-center">Result</h3>
        <table class="table table-striped table-responsive-md btn-table" >
                <thead>
                                <tr>
                                  <th scope="col">route</th>
                                  <th scope="col">train</th>
                                  <th scope="col">departure</th>
                                  <th scope="col">arrival</th>
                                  <th scope="col">road time</th>
                                  <th scope="col">price</th>
                                </tr>
                </thead>
                <tbody>
                <c:forEach items="${suitableRoutes}" var="route" >
                                <tr>
                                <td><c:out value="${route.routeId}"/> </td>
                                <td><c:out value="${route.trainModel}"/> </td>
                                <td><c:out value="${route.departure}"/> </td>
                                <td><c:out value="${route.arrival}"/> </td>
                                <td><c:out value="${route.roadTime}"/> </td>
                                <td><c:out value="${route.price}"/> </td>
                                </tr>
                                </c:forEach>
                </tbody>
                </table>
                </c:if>
                </form>
                </div>
        </body>
</html>
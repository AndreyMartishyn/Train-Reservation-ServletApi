<%@ include file="/view/static/basic_context.jsp" %>
<head>
<%@ include file="/view/static/meta.jsp" %>
</head>
<body>
<%@ include file="/view/static/header.jsp" %>
		<br><br>
        <div align="center">
		<form>
        <h2><fmt:message key="admin.page.route-stations"/></h2>
        <span style ="text-align: center; color:red; font-family:courier; font-size:80%;">${noRoutes}</span>

        <table class="table table-striped table-responsive-md btn-table" class="m-5">
        <thead>
                        <tr>
                          <th scope="col"><fmt:message key="admin.page.route.id"/></th>
                          <th scope="col"><fmt:message key="admin.page.train.id"/></th>
                          <th scope="col"><fmt:message key="admin.page.station.id"/></th>
                          <th scope="col"><fmt:message key="admin.page.arrival"/></th>
                          <th scope="col"><fmt:message key="admin.page.departure"/></th>
                          <th scope="col"><fmt:message key="admin.page.action"/></th>
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
                        <a href="route-edit.command?id=<c:out value='${route.id}'/>" class="btn btn-dark"><fmt:message key="admin.page.edit"/></a>
                        <a href="route-delete.command?id=<c:out value='${route.id}' />" class="btn btn-dark"><fmt:message key="admin.page.delete"/></a>
                        </td>
                        </tr>
                        </c:forEach>
        </tbody>
        </table>
        </form>
                        <a href="route-add.command" class="btn btn-dark"><fmt:message key="admin.page.route.add"/></a>
 </body>
</html>
</body>
</html>
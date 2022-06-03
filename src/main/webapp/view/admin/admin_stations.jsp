<%@ include file="/view/static/basic_context.jsp" %>
<html>
<head>
<%@ include file="/view/static/meta.jsp" %>
</head>
<body>
<%@ include file="/view/static/header.jsp" %>
<br><br>
 <div align="center">
		<form>
		 <h2><fmt:message key="stations"/></h2>
		  <span style ="text-align: center; color:red; font-family:courier; font-size:80%;">${noStations}</span>
        <table class="table table-striped table-responsive-md btn-table" >
        <thead>
                        <tr>
                          <th scope="col"><fmt:message key="admin.page.station.number"/></th>
                          <th scope="col"><fmt:message key="admin.page.station.name"/></th>
                          <th scope="col"><fmt:message key="admin.page.station.code"/></th>
                          <th scope="col"><fmt:message key="admin.page.action"/></th>
                        </tr>

        </thead>
        <tbody>
        <c:forEach items="${stations}" var="station" >
        <tr>
        <td><c:out value="${station.id}"/> </td>
        <td><c:out value="${station.name}"/> </td>
        <td><c:out value="${station.code}"/> </td>
        <td>
           <a href="station-edit.command?id=<c:out value='${station.id}' />" class="btn btn-dark"><fmt:message key="admin.page.edit"/></a>
           <a href="station-delete.command?id=<c:out value='${station.id}' />" class="btn btn-dark"><fmt:message key="admin.page.delete"/></a>
        </td>
        </tr>
        </c:forEach>
        </tbody>
        </table>
        </form>
        <a href="station-add.command" class="btn btn-dark"><fmt:message key="admin.page.station.add"/></a>
</body>
</html>
</body>
</html>
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
		 <h2><fmt:message key="admin.page.users"/></h2>
        <table class="table table-striped table-responsive-md btn-table" >
        <thead>
                        <tr>
                          <th scope="col"><fmt:message key="admin.page.user.id"/></th>
                          <th scope="col"><fmt:message key="admin.page.user.first.name"/></th>
                          <th scope="col"><fmt:message key="admin.page.user.last.name"/></th>
                          <th scope="col"><fmt:message key="admin.page.user.email"/></th>
                          <th scope="col"><fmt:message key="admin.page.user.role"/></th>
                          <th scope="col"><fmt:message key="admin.page.action"/></th>
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
           <a href="user-edit.command?id=<c:out value='${user.id}' />" class="btn btn-dark"><fmt:message key="admin.page.edit"/></a>
           <a href="user-delete.command?id=<c:out value='${user.id}' />" class="btn btn-dark"><fmt:message key="admin.page.delete"/></a>
        </td>
        </tr>
        </c:forEach>
        </tbody>
        </table>
       </form>
</body>
</html>
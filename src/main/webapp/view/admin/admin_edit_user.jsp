<%@ include file="/view/static/basic_context.jsp" %>
<%@ page language="java"
          import="ua.martishyn.app.data.entities.enums.Role"
          import="java.util.List"
          import="java.util.Arrays"
    %>
<head>
<%@ include file="/view/static/meta.jsp" %>
</head>
<body>
<%@ include file="/view/static/header.jsp" %>
<br><br>
<div align="center">
        <form action="user-edit-post.command" method="post">
        <h2><fmt:message key="admin.page.user.edit"/></h2>
        <table style="margin-left:auto;margin-right:auto;">
        <c:if test="${user != null}">
        <input type="hidden" name="id" value="<c:out value='${user.id}' />" />
        </c:if>
                        <tr>
                        <th><fmt:message key="admin.page.user.first.name"/>: </th>
                        <td>
                          <input type="text" name="firstName" size="30"
                          value="<c:out value='${user.firstName}' />" required />
                          </td>
                         </tr>
						<tr>
                        <th><fmt:message key="admin.page.user.last.name"/>: </th>
                        <td>
                            <input type="text" name="lastName" size="30"
                            value="<c:out value='${user.lastName}' />" required />
                         </td>
                         </tr>
						<tr>
                        <th><fmt:message key="admin.page.user.email"/>: </th>
                        <td>
                            <input type="text" name="email" size="30"
                            value="<c:out value='${user.email}' />" required />
                         </td>
                         </tr>
						<tr>
                        <th><fmt:message key="admin.page.user.password"/>: </th>
                        <td>
                            <input type="password" name="password" size="30"
                            value="<c:out value='${user.password}' />" required />
                         </td>
                         </tr>
                         <tr>
                         <th><fmt:message key="admin.page.user.role"/>: </th>
                         <td>
						   <select class="browser-default custom-select"  name="role">
                           <% List<Role> roles = Arrays.asList(Role.values());
                           for (Role role: roles) {%>
                           <option  value="<%=role.toString()%>"><%=role.toString()%></option><% }%></select>
                          </div>
                          </td>
                         </tr>
                        </table>
                        <br>
                        <div class="form-group">
                 	    <input type="submit" class="btn btn-dark" value="<fmt:message key="admin.page.action.save"/>"/>
                         </div>
                         </div>
</form>
</body>
</html>
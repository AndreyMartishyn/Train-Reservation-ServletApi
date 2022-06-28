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
<br>
<div class="wrapper fadeInDown">
    <div id="formContent">
        <form action="user-edit-post.command" method="post">
        <h2><fmt:message key="admin.page.user.edit"/></h2>
        <table style="margin-left:auto;margin-right:auto;">
        <input type="hidden" name="id" value="<c:out value='${id}' />" />
           				        <tr>
                         <th><fmt:message key="admin.page.user.role"/>: </th>
                         <td>
						   <select class="browser-default custom-select"  name="role">
                           <% List<Role> roles = Arrays.asList(Role.values());
                           for (Role role: roles) {%>
                           <option  value="<%=role.toString()%>"><fmt:message key="<%=role.toString()%>"/></option><% }%></select>
                          </div>
                          </td>
                         </tr>
                        </table>
                        <br>
                        <div class="form-group">
                 	    <input type="submit" class="btn btn-dark" value="<fmt:message key="admin.page.action.save"/>"/>
                         </div>
                         </div>
                         </div>
</body>
</html>
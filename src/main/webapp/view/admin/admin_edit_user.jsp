<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java"
import="ua.martishyn.app.data.entities.User"
import="java.util.List"
import="java.util.Arrays"
import="ua.martishyn.app.data.entities.enums.Role"
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
        <form action="user-edit-post.command" method="post">
        <table border="1" cellpadding="5">
        <c:if test="${user != null}">
        <input type="hidden" name="id" value="<c:out value='${user.id}' />" />
        </c:if>
                        <tr>
                        <th>First name: </th>
                        <td>
                          <input type="text" name="firstName" size="45"
                          value="<c:out value='${user.firstName}' />"/>
                          </td>
                         </tr>
						<tr>
                        <th>Last name: </th>
                        <td>
                            <input type="text" name="lastName" size="45"
                            value="<c:out value='${user.lastName}' />"/>
                         </td>
                         </tr>
						<tr>
                        <th>Email: </th>
                        <td>
                            <input type="text" name="email" size="45"
                            value="<c:out value='${user.email}' />"/>
                         </td>
                         </tr>
						<tr>
                        <th>Password: </th>
                        <td>
                            <input type="password" name="password" size="45"
                            value="<c:out value='${user.password}' />"/>
                         </td>
                         </tr>
                         <tr>
                         <th>Role: </th>
                         <td>
						   <select class="browser-default custom-select"  name="role">
                           <% List<Role> roles = Arrays.asList(Role.values());
                           for (Role role: roles) {%>
                           <option  value="<%=role.toString()%>"><%=role.toString()%></option><% }%></select>
                          </div>
                          </td>
                         </tr>
                        <tr>
                        <td colspan="2" align="center">
                            <input type="submit" value="Save" />
                        </td>
                        </tr>
                        </table>
</form>
</div>
</body>
</html>
<%@ include file="/view/static/basic_context.jsp" %>
<%@ page language="java"
          import="ua.martishyn.app.data.entities.enums.ComfortClass"
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
        <form action="wagon-edit-post.command" method="post">
        <h2><fmt:message key="admin.page.wagon.edit"/></h2>
        <table style="margin-left:auto;margin-right:auto;">
                        <input type="hidden" name="id" value="<c:out value='${wagon.id}' />" />
                        <input type="hidden" name="routeId" value="<c:out value='${wagon.routeId}' />" />

		<tr>
		        <th><fmt:message key="admin.page.wagon.type"/>:</th>
                <td>
                <select class="browser-default custom-select"  name="type">
               <% List<ComfortClass> types = Arrays.asList(ComfortClass.values());
               for (ComfortClass type: types) {%>
               <option value="<%=type.toString()%>"><fmt:message key="<%=type.toString()%>"/> </option><% }%></select>
                </td>
		</tr>
		<tr>
                <th><fmt:message key="admin.page.wagon.seats"/>:</th>
                <td>
                <input type="text" class="form-control" name="seats" value="<c:out value='${wagon.numOfSeats}' />"
                required>
                </td>
        </tr>
        <tr>
               <th><fmt:message key="admin.page.wagon.price"/>:</th>
                <td>
                <input type="text" class="form-control" name="price"  value="<c:out value='${wagon.price}' />"
                required>
                </td>
        </tr>
        </table>
           <c:if test="${requestScope.wrongSeats != null}">
                 <span style ="text-align: center; color:red; font-family:courier; font-size:80%;"><fmt:message key="wagon.wrong.seats"/></span>
                 </c:if>

           <c:if test="${requestScope.wrongPrice != null}">
                 <span style ="text-align: center; color:red; font-family:courier; font-size:80%;"><fmt:message key="wagon.wrong.price"/></span>
                 </c:if>

                <br>
               <div class="form-group">
               <input type="submit" class="btn btn-dark" value="<fmt:message key="admin.page.action.save"/>"/>
               </div>
                </div>

</body>
</html>
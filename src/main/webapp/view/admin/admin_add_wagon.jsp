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
        <form action="wagon-add-post.command" method="post">
        <h2><fmt:message key="admin.page.wagon.add"/></h2>
        <table style="margin-left:auto;margin-right:auto;">

		<tr>
        		  <th><fmt:message key="admin.page.wagon.route"/>:</th>
                        <td>
						<select class="browser-default custom-select" name="routeId"  required>
                          <c:forEach items="${routeIds}" var="id" >
                            <option value="${id}">
                                ${id}
                            </option>
                          </c:forEach>
                        </select>
                        </td>
		<tr>
		        <th><fmt:message key="admin.page.wagon.type"/>:</th>
                <td>
                <select class="browser-default custom-select"  name="type" required>
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
               <input type="submit" class="btn btn-dark" value="<fmt:message key="admin.page.wagon.add"/>"/>
               </div>
                </div>

</body>
</html>
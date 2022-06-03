<%@ include file="/view/static/basic_context.jsp" %>
<head>
    <%@ include file="/view/static/meta.jsp" %>
</head>
<body>
<%@ include file="/view/static/header.jsp" %>
<br><br>
    <div align="center">
            <c:if test="${singleRoute != null}">
                <form action="route-edit-post.command" method="post">
                </c:if>
                    <c:if test="${singleRoute == null}">
                <form action="route-add-post.command" method="post">
                </c:if>
                <caption>
               <c:if test="${singleRoute != null}">
              <h2><fmt:message key="admin.page.route.edit.form"/></h2>
               <small>*route id field is immutable</small>
              </c:if>
              <c:if test="${singleRoute == null}">
               <h2><fmt:message key="admin.page.route.add.form"/></h2>
              </c:if>
               </caption>
            <table style="margin-left:auto;margin-right:auto;">
            <tr>
                <th><fmt:message key="admin.page.route.id"/>:</th>
                <td>
                    <c:if test="${singleRoute != null}">
                    <input type="text" name="id" size="30"
                    value="<c:out value='${singleRoute.id}' />" readonly="readonly"/>
                    </c:if>
                    <c:if test="${singleRoute == null}">
                    <input type="text" name="id" size="30"
                    value="<c:out value='${singleRoute.id}' />" />
                    </c:if>
                </td>
            <tr>
                <th><fmt:message key="admin.page.train.id"/>:</th>
                <td>
                    <input type="text" name="trainId" size="30"
                           value="<c:out value='${singleRoute.trainId}' />" required/>
                </td>
            </tr>
            <tr>
                <th><fmt:message key="admin.page.station.id"/>:</th>
                <td>
                    <input type="text" name="stationId" size="30"
                           value="<c:out value='${singleRoute.stationId}' />"required/>
                </td>
            </tr>
            <tr>
                <th><fmt:message key="admin.page.arrival"/>:</th>
                <td>
                    <input type="datetime-local" name="arrival" size="30"
                             value="<c:out value='${singleRoute.arrival}' />" required />
                </td>
            </tr>
            <tr>
                <th><fmt:message key="admin.page.departure"/>:</th>
                <td>
                    <input type="datetime-local" name="departure" size="30"
                           value="<c:out value='${singleRoute.departure}' />" required />
                </td>
            </tr>

             </table>
              <span style ="text-align: center; color:red; font-family:courier; font-size:80%;">${errorValidation}</span>
              <br>
             <div class="form-group">
                 	<input type="submit" class="btn btn-dark" value="<fmt:message key="admin.page.action.save"/>"/>
              </div>
             </div>
</form>
</body>
</html>
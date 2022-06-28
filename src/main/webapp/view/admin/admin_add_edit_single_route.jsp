<%@ include file="/view/static/basic_context.jsp" %>
<head>
    <%@ include file="/view/static/meta.jsp" %>
</head>
<body>
<%@ include file="/view/static/header.jsp" %>
<br>
<div class="wrapper">
<div id="formContent">
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
                  <input type="hidden" name="id" value="<c:out value='${singleRoute.id}' />" />
                    </c:if>
                    <c:if test="${singleRoute == null}">
                    <input type="text" name="id" size="30"
                     value="<c:out value='${singleRoute.id}' />" required />
                    </c:if>
                </td>
            <tr>
                <th><fmt:message key="admin.page.train"/>:</th>
                 <td>
                                						<select class="browser-default custom-select" name="trainId"  required>
                                                            <c:forEach items="${trains}" var="train" >
                                                                          <option value="${train.id}" selected="selected"> ${train.model.name}</option>
                                                                      </c:forEach>
                                                        </select>
                                                        </td>
            </tr>
            <tr>
                <th><fmt:message key="admin.page.station"/>:</th>
                <td>
                                      						<select class="browser-default custom-select" name="stationId"  required>
                                                             <c:forEach items="${stations}" var="station" >
                                                                           <option value="${station.id}" selected="selected"> <fmt:message key="${station.name}"/></option>
                                                                       </c:forEach>
                                                         </select>
                                                         </td>
            </tr>

            <tr>
                <th><fmt:message key="admin.page.arrival"/>:</th>
                <td>
                    <input type="datetime-local" name="arrival" size="30"
                             value="<c:out value='${singleRoute.arrival}' />"
                            required/>
                </td>
            </tr>
            <tr>
                <th><fmt:message key="admin.page.departure"/>:</th>
                <td>
                    <input type="datetime-local" name="departure" size="30"
                           value="<c:out value='${singleRoute.departure}' />"
                           required/>
                </td>
            </tr>

             </table>
                <c:if test="${requestScope.wrongId !=null}">
                   <span style ="text-align: center; color:red; font-family:courier; font-size:70%;"><fmt:message key="route.wrong.id"/></span>
                                                  </c:if>
                   <c:if test="${requestScope.wrongDates !=null}">
                                   <span style ="text-align: center; color:red; font-family:courier; font-size:70%;"><fmt:message key="route.wrong.dates"/></span>
                                                  </c:if>
                                                  <br>
             	<input type="submit" class="btn btn-dark" value="<fmt:message key="admin.page.action.save"/>"/>
               </div>
</form>
</body>
</html>
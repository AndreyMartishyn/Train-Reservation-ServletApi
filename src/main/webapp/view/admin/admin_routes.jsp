<%@ include file="/view/static/basic_context.jsp" %>
<%@ taglib prefix="datef" uri="/WEB-INF/tlds/dateFunction.tld"  %>
<head>
<%@ include file="/view/static/meta.jsp" %>
</head>
<body>
<%@ include file="/view/static/header.jsp" %>
<br>
<div class="wrapper">
 		    <div id="formContent-table">
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
        <c:forEach items="${paginatedEntries}" var="route" >
                        <tr>
                        <td><c:out value="${route.id}"/> </td>
                        <td><c:out value="${route.trainId}"/> </td>
                        <td><c:out value="${route.stationId}"/> </td>
                        <td><c:out value="${datef:formatLocalDateTime(route.arrival, 'dd.MM.yyyy HH:mm')}"/> </td>
                        <td><c:out value="${datef:formatLocalDateTime(route.departure, 'dd.MM.yyyy HH:mm')}"/> </td>
                        <td>
                        <a href="route-edit.command?id=<c:out value='${route.id}'/>&stationId=<c:out value='${route.stationId}' />"  class="btn btn-dark"><fmt:message key="admin.page.edit"/></a>
                        <a href="route-delete.command?id=<c:out value='${route.id}'/>&stationId=<c:out value='${route.stationId}' />"  class="btn btn-dark"><fmt:message key="admin.page.delete"/></a>
                        </td>
                        </tr>
                        </c:forEach>
        </tbody>
        </table>
                        <a href="route-add.command"  class="btn btn-dark"><fmt:message key="admin.page.route.add"/></a>
<br><br>
<%--For displaying Previous link except for the 1st page --%>
  <c:if test="${currentPage != 1}">
      <td><a href="routes-page.command?page=${currentPage - 1}">Previous</a></td>
  </c:if>
             <table style = "border:1px solid black;margin-left:auto;margin-right:auto;" cellpadding="5" cellspacing="5" >
             <tr>
              <c:forEach begin="1" end="${noOfPages}" var="i">
              <c:choose>
              <c:when test="${currentPage eq i}">
              <td>${i}</td>
                                          </c:when>
                                          <c:otherwise>
                                              <td><a href="routes-page.command?page=${i}">${i}</a></td>
                                          </c:otherwise>
                                      </c:choose>
                                  </c:forEach>
                              </tr>
                          </table>

                          <%--For displaying Next link --%>

                          <c:if test="${currentPage lt noOfPages}">
                              <td><a href="routes-page.command?page=${currentPage + 1}">Next</a></td>
                          </c:if>
  </div>
</div>
</html>
</body>
</html>
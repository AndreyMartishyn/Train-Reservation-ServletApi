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
        <h2><fmt:message key="admin.page.wagons"/></h2>
          <c:if test="${requestScope.noEntries !=null}">
      <span style ="text-align: center; color:red; font-family:courier; font-size:70%;"><fmt:message key="admin.no.wagons"/></span>
                                     </c:if>

        <table class="table table-striped table-responsive-md btn-table" class="m-5">
        <thead>
                        <tr>
                          <th scope="col"><fmt:message key="admin.page.wagon.id"/></th>
                          <th scope="col"><fmt:message key="admin.page.route.id"/></th>
                          <th scope="col"><fmt:message key="admin.page.wagon.type"/></th>
                          <th scope="col"><fmt:message key="admin.page.wagon.seats"/></th>
                          <th scope="col"><fmt:message key="admin.page.wagon.price"/></th>
                          <th scope="col"><fmt:message key="admin.page.action"/></th>
                        </tr>
        </thead>
        <tbody>
        <c:forEach items="${paginatedEntries}" var="wagon" >
                        <tr>
                        <td><c:out value="${wagon.id}"/> </td>
                        <td><c:out value="${wagon.routeId}"/> </td>
                        <td><c:out value="${wagon.type}"/> </td>
                        <td><c:out value="${wagon.numOfSeats}"/> </td>
                        <td><c:out value="${wagon.price}"/> </td>
                        <td>
                        <a href="wagon-edit.command?id=<c:out value='${wagon.id}'/>"  class="btn btn-dark"><fmt:message key="admin.page.edit"/></a>
                        <a href="wagon-delete.command?id=<c:out value='${wagon.id}'/>"  class="btn btn-dark"><fmt:message key="admin.page.delete"/></a>
                        </td>
                        </tr>
                        </c:forEach>
        </tbody>
        </table>
                        <a href="wagon-add.command"  class="btn btn-dark"><fmt:message key="admin.page.wagon.add"/></a>
<br><br>
<%--For displaying Previous link except for the 1st page --%>
  <c:if test="${currentPage > 1}">
      <td><a href="wagons-page.command?page=${currentPage - 1}">Previous</a></td>
  </c:if>
             <table style = "border:1px solid black;margin-left:auto;margin-right:auto;" cellpadding="5" cellspacing="5" >
             <tr>
              <c:forEach begin="1" end="${noOfPages}" var="i">
              <c:choose>
              <c:when test="${currentPage eq i}">
              <td>${i}</td>
                                          </c:when>
                                          <c:otherwise>
                                              <td><a href="wagons-page.command?currentPage=${i}">${i}</a></td>
                                          </c:otherwise>
                                      </c:choose>
                                  </c:forEach>
                              </tr>
                          </table>

                          <%--For displaying Next link --%>

                          <c:if test="${currentPage lt noOfPages}">
                              <td><a href="wagons-page.command?currentPage=${currentPage + 1}">Next</a></td>
                          </c:if>
  </div>
</div>
</html>
</body>
</html>
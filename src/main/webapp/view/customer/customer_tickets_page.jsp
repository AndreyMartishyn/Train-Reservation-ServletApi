<%@ include file="/view/static/basic_context.jsp" %>
<head>
<%@ include file="/view/static/meta.jsp"%>
</head>
<body>
<%@ include file="/view/static/header.jsp"%>
<br><br>
 		<div align="center">
 		<div class="bg-white text-dark">

               <c:if test="${requestScope.noTickets !=null}">
            <span style ="text-align: center; color:red; font-family:courier; font-size:70%;"><fmt:message key="user.no.tickets"/></span>
            </c:if>
 		<c:if test="${requestScope.userTickets != null}">
 		<table class="table table-striped table-bordered" >
                <thead>
                                  <tr>
                                  <th scope="col"><fmt:message key="user.page.book.ticket"/>#</th>
                                  <th scope="col"><fmt:message key="user.page.book.train"/>#</th>
                                  <th scope="col"><fmt:message key="admin.page.user.first.name"/></th>
                                  <th scope="col"><fmt:message key="admin.page.user.last.name"/></th>
                                  <th scope="col"><fmt:message key="user.page.book.from"/></th>
                                  <th scope="col"><fmt:message key="user.page.book.info.departure"/></th>
                                  <th scope="col"><fmt:message key="user.page.book.to"/></th>
                                  <th scope="col"><fmt:message key="user.page.book.info.arrival"/></th>
                                  <th scope="col"><fmt:message key="user.page.ticket.wagon"/>#</th>
                                  <th scope="col"><fmt:message key="user.page.ticket.place"/>#</th>
                                  <th scope="col"><fmt:message key="user.page.book.duration"/></th>
                                  <th scope="col"><fmt:message key="user.page.ticket.class"/></th>
                                  <th scope="col"><fmt:message key="user.page.book.price"/></th>
                                  <th scope="col"><fmt:message key="user.page.ticket.status"/></th>
                                  </tr>
                </thead>
                <tbody>
                <c:forEach items="${userTickets}" var="ticket" >
                                <tr>
                                <td style="text-align:center">
                                <strong>
                                <c:out value="${ticket.id}"/>
                                </strong>
                                </td>

                                <td style="text-align:center">
                                <strong>
                                <c:out value="${ticket.train.id}"/>K
                                <br>
                                <c:out value="${ticket.train.model.name}"/>
                                </strong>
                                </td>

                                <td style="text-align:center">
                                <strong>
                                <c:out value="${ticket.firstName}"/>
                                </strong>
                                </td>

                                <td style="text-align:center">
                                <strong>
                                <c:out value="${ticket.lastName}"/>
                                </strong>
                                </td>

                                <td style="text-align:center">
                                <strong>
                                <fmt:message key="${ticket.departureStation.name}"/>
                                </strong>
                                </td>

                                <td style="text-align:center">
                                <strong>
                                <c:out value="${ticket.departureTime}"/>
                                </strong>
                                </td>

                                <td style="text-align:center">
                                <strong>
                                <fmt:message key="${ticket.arrivalStation.name}"/>
                                </strong>
                                </td>

                                <td style="text-align:center">
                                <strong>
                                <c:out value="${ticket.arrivalTime}"/>
                                </strong>
                                </td>

							    <td>
                                <c:out value="${ticket.wagon.id}"/>
                                </td>

                                 <td>
                                <c:out value="${ticket.place}"/>
                                </td>

                                <td>
                                <c:out value="${ticket.duration}"/>
                                </td>

                                 <td>
                                <fmt:message key="${ticket.type}"/>
                                </td>

                                 <td>
                                <c:out value="${ticket.price}"/>
                                </td>

                                <td styles = "text-align:center">
							  <c:choose>
                             <c:when test = "${ticket.paid == false}">
                              <a href="customer-ticket-pay.command?ticketId=<c:out value="${ticket.id}"/>"
                             class="btn btn-dark" ><fmt:message key="user.page.ticket.pay"/></a>
                             </c:when>
                             <c:otherwise>
                             Already paid
                             </c:otherwise>
                             </c:choose>
                             </td>
                             </tr>
                            </c:forEach>
                </tbody>
                </table>
                </c:if>
                </div>
                </div>
        </body>
</html>
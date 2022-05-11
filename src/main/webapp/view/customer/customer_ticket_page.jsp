<%@ include file="/view/static/basic_context.jsp" %>
<head>
<%@ include file="/view/static/meta.jsp"%>
</head>
<body>
<%@ include file="/view/static/header.jsp"%>
<br><br>
 		<div align="center">
 		<form>
 		<span style ="text-align: center; color:red; font-family:courier; font-size:80%;">${noTickets}</span>
 		<c:if test="${requestScope.userTickets != null}">
 		<table class="table table-striped table-responsive-md btn-table" >
                <thead>
                                  <tr>
                                  <th scope="col">TicketId</th>
                                  <th scope="col">TrainId</th>
                                  <th scope="col">First name</th>
                                  <th scope="col">Last name</th>
                                  <th scope="col">From Station</th>
                                  <th scope="col">Departure Time</th>
                                  <th scope="col">To Station</th>
                                  <th scope="col">Arrival Time</th>
                                  <th scope="col">Wagon</th>
                                  <th scope="col">Place</th>
                                  <th scope="col">Class</th>
                                  <th scope="col">Price</th>
                                  <th scope="col">Status</th>

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
                                <c:out value="${ticket.departureStation}"/>
                                </strong>
                                </td>

                                <td style="text-align:center">
                                <strong>
                                <c:out value="${ticket.departureTime}"/>
                                </strong>
                                </td>

                                <td style="text-align:center">
                                <strong>
                                <c:out value="${ticket.arrivalStation}"/>
                                </strong>
                                </td>

                                <td style="text-align:center">
                                <strong>
                                <c:out value="${ticket.arrivalTime}"/>
                                </strong>
                                </td>

							    <td>
                                <c:out value="${ticket.wagon}"/>
                                </td>

                                 <td>
                                <c:out value="${ticket.place}"/>
                                </td>

                                 <td>
                                <c:out value="${ticket.comfortClass}"/>
                                </td>

                                 <td>
                                <c:out value="${ticket.price}"/>
                                </td>

                                <td styles = "text-align:center">
							  <c:choose>
                             <c:when test = "${ticket.paid == false}">
                              <a href="customer-ticket-pay.command?ticketId=<c:out value="${ticket.id}"/>"
                              input type="submit" class="btn btn-light" >MAKE PAYMENT</a>
                             </c:when>
                             <c:otherwise>
                              Paid
                             </c:otherwise>
                             </c:choose>
                             </td>
                             </tr>
                            </c:forEach>
                </tbody>
                </table>
                </c:if>
                </form>
                </div>
        </body>
</html>
<%@ include file="/view/static/basic_context.jsp" %>
<head>
<%@ include file="/view/static/meta.jsp"%>
</head>
<body>
<%@ include file="/view/static/header.jsp"%>
<br><br>
 		<div align="center">
 		<div class="bg-white text-dark">
 		<span style ="text-align: center; color:red; font-family:courier; font-size:80%;">${noTickets}</span>
 		<c:if test="${requestScope.userTickets != null}">
 		<table class="table table-striped table-bordered" >
                <thead>
                                  <tr>
                                  <th scope="col">Ticket#</th>
                                  <th scope="col">Train#</th>
                                  <th scope="col">Name</th>
                                  <th scope="col">Surname</th>
                                  <th scope="col">From</th>
                                  <th scope="col">Departure</th>
                                  <th scope="col">To</th>
                                  <th scope="col">Arrival</th>
                                  <th scope="col">Wagon#</th>
                                  <th scope="col">Place#</th>
                                  <th scope="col">Duration</th>
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
                                <c:out value="${ticket.departureStation.name}"/>
                                </strong>
                                </td>

                                <td style="text-align:center">
                                <strong>
                                <c:out value="${ticket.departureTime}"/>
                                </strong>
                                </td>

                                <td style="text-align:center">
                                <strong>
                                <c:out value="${ticket.arrivalStation.name}"/>
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
                                <c:out value="${ticket.type}"/>
                                </td>

                                 <td>
                                <c:out value="${ticket.price}"/>
                                </td>

                                <td styles = "text-align:center">
							  <c:choose>
                             <c:when test = "${ticket.paid == false}">
                              <a href="customer-ticket-pay.command?ticketId=<c:out value="${ticket.id}"/>"
                              input type="submit" class="btn btn-dark" >MAKE PAYMENT</a>
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
                </div>
                </div>
        </body>
</html>
<%@ include file="/view/static/basic_context.jsp" %>
<head>
<%@ include file="/view/static/meta.jsp"%>
</head>
<body>
<%@ include file="/view/static/header.jsp"%>
<br>
<div align="center">
<div class="col-md-5">
<form action="search-tickets.command" method="get">
<h3 class="mb-4 text-center"><fmt:message key="user.page.book.title"/></h3>
		<div class="form-group">
        <label><fmt:message key="user.page.book.departure"/></label>
        <select class="custom-select mr-sm-2" name="stationFrom">
        <c:forEach var="station" items="${stations}">
        <option value="${station.id}"><fmt:message key="${station.name}"/></option>
        </c:forEach>
        </select>

        </div>
		<div class="form-group">
        <label><fmt:message key="user.page.book.arrival"/></label>
        <select class="custom-select mr-sm-2" name="stationTo">
        <c:forEach var="station" items="${stations}">
        <option value="${station.id}"><fmt:message key="${station.name}"/></option>
        </c:forEach>
        </select>
         <span style ="text-align: center; color:red; font-family:courier; font-size:80%;">${busyPlace}</span>
         <span style ="text-align: center; color:red; font-family:courier; font-size:80%;">${noRoutes}</span>
         <span style ="text-align: center; color:red; font-family:courier; font-size:80%;">${errorValidation}</span>
         <span style ="text-align: center; color:red; font-family:courier; font-size:80%;">${sameStations}</span>
       </div>
            <button type="submit" class="btn btn-dark" value="Search">Search</button>
        </div>
        </div>
        </form>

 		<c:if test="${requestScope.suitableRoutes != null}">
 		<br><br>
 		<div align="center">
 		<form>
 		<table class="table table-striped table-responsive-md btn-table" >
                <thead>
                                  <tr>
                                  <th scope="col">Train</th>
                                  <th scope="col">From/To</th>
                                  <th scope="col">Schedule</th>
                                  <th scope="col">Duration</th>
                                  <th scope="col">Seats available</th>
                                  <th scope="col">Price(UAH)</th>
                                  </tr>
                </thead>
                <tbody>
                <c:forEach items="${suitableRoutes}" var="route" >
                                <tr>
                                <td style="text-align:center">
                                <strong>
                                <c:out value="${route.train.id}"/>K
                                <c:out value="${route.train.model.name}"/>
                                </strong>
                                <br>
                                <a href="show-route.command?route=<c:out value="${route.routeId}"/>">Route</a>
                                </td>

                                <td>
                                <c:out value="${route.departureStation}"/>
								-
                                <c:out value="${route.arrivalStation}"/>
						        </td>

                                <td>
                                <p style = "text-align: left">Departure: <c:out value="${route.departure}"/></p>
								<br>
                                <p style = "text-align: left">Arrival: <c:out value="${route.arrival}"/></p>
                                </td>

                                <td>
                                <c:out value="${route.roadTime}"/>
                                </td>

                                <td styles = "text-align:center">
                                F1  <c:out value="${route.firstClassSeats}"/>
									<c:if test="${route.firstClassSeats != 0}">
                                <a href="customer-ticket-form.command<c:out value="${route.redirectLink}"/>&class=FIRST&price=<c:out value="${route.firstClassTotalPrice}"/>"
                                    class="btn btn-dark" >select</a>
                                    </c:if>
                                <br><br>
                                S2  <c:out value="${route.secondClassSeats}"/>
                                   <c:if test="${route.secondClassSeats != 0}">
                                <a href="customer-ticket-form.command<c:out value="${route.redirectLink}"/>&class=SECOND&price=<c:out value="${route.secondClassTotalPrice}"/>"
                                    class="btn btn-dark" >select</a>
                                    </c:if>
                               </td>

                               <td>
                               <p style = "text-align: center"><c:out value="${route.firstClassTotalPrice}"/></p>
                               <br>
                               <p style = "text-align: center"><c:out value="${route.secondClassTotalPrice}"/></p>
                               </td>
                                </tr>
                                </c:forEach>
                </tbody>
				<span style ="text-align: left; color:red; font-family:courier; font-size:79%;">
				*Only registered users can buy tickets. Login or you will be redirected accordingly
				</span>
				</table>
				</c:if>
                </form>
                </div>
        </body>
</html>
</body>
</html>
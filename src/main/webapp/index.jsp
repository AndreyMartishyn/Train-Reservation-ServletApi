<%@ include file="/view/static/basic_context.jsp" %>
<head>
<%@ include file="/view/static/meta.jsp"%>
</head>
<body>
<%@ include file="/view/static/header.jsp"%>
<div align="center">
<span class="lang">
<form>
                                        <select id="language" name="language" onchange="submit()">
                                            <option value="en" ${language == 'en' ? 'selected' : ''}>EN</option>
                                            <option value="ua" ${language == 'ua' ? 'selected' : ''}>UA</option>
                                        </select>
</form>
</span>
<div class="wrapper fadeInDown">
    <div id="formContent">
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
         <span style ="text-align: center; color:red; font-family:courier; font-size:80%;">${noStations}</span>
         <span style ="text-align: center; color:red; font-family:courier; font-size:80%;">${errorValidation}</span>
         <span style ="text-align: center; color:red; font-family:courier; font-size:80%;">${sameStations}</span>
       </div>
            <button type="submit" class="btn btn-dark" ><fmt:message key="user.page.book.search"/></button>
        </form>
        </div>
        </div>
        <br>

    <div id="formContent-table">
 		<c:if test="${requestScope.suitableRoutes != null}">
 	 		<table class="table table-striped table-responsive-md btn-table" >
                <thead>
                                  <tr>
                                  <th scope="col"><fmt:message key="user.page.book.train"/></th>
                                  <th scope="col"><fmt:message key="user.page.book.from"/>/<fmt:message key="user.page.book.to"/></th>
                                  <th scope="col"><fmt:message key="user.page.book.schedule"/></th>
                                  <th scope="col"><fmt:message key="user.page.book.duration"/></th>
                                  <th scope="col"><fmt:message key="user.page.book.seats"/></th>
                                  <th scope="col"><fmt:message key="user.page.book.price"/>(UAH)</th>
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
                                <a href="show-route.command?route=<c:out value="${route.routeId}"/>"><fmt:message key="user.page.book.info.route"/></a>
                                </td>

                                <td>
                                <c:out value="${route.departureStation}"/>
								-
                                <c:out value="${route.arrivalStation}"/>
						        </td>

                                <td>
                                <p style = "text-align: left"><fmt:message key="user.page.book.info.departure"/>: <c:out value="${route.departure}"/></p>
								<br>
                                <p style = "text-align: left"><fmt:message key="user.page.book.info.arrival"/>: <c:out value="${route.arrival}"/></p>
                                </td>

                                <td>
                                <c:out value="${route.roadTime}"/>
                                </td>

                                <td styles = "text-align:center">
                                F1 <c:out value="${route.firstClassSeats}"/>
									<c:if test="${route.firstClassSeats != 0}">
                                <a href="customer-ticket-form.command<c:out value="${route.redirectLink}"/>&class=FIRST&price=<c:out value="${route.firstClassTotalPrice}"/>"
                                    class="btn btn-dark" ><fmt:message key="user.page.book.info.select"/></a>
                                </c:if>
                                <br>
                                <br>
                                S2 <c:out value="${route.secondClassSeats}"/>
                                   <c:if test="${route.secondClassSeats != 0}">
                                <a href="customer-ticket-form.command<c:out value="${route.redirectLink}"/>&class=SECOND&price=<c:out value="${route.secondClassTotalPrice}"/>"
                                    class="btn btn-dark" ><fmt:message key="user.page.book.info.select"/></a>
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
				<span style ="text-align: left; color:black; font-family:courier; font-size:79%;">
				*<fmt:message key="user.page.book.info.span"/>
				</span>
				</table>
				</c:if>
		</div>
</body>
</html>
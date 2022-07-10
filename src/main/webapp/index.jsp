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

               <c:if test="${requestScope.noStations != null}">
         <span style ="text-align: center; color:red; font-family:courier; font-size:80%;"><fmt:message key="no.stations"/></span>
         </c:if>

               <c:if test="${requestScope.sameStations != null}">
         <span style ="text-align: center; color:red; font-family:courier; font-size:80%;"><fmt:message key="same.stations"/></span>
         </c:if>

               <c:if test="${requestScope.noRoutes != null}">
         <span style ="text-align: center; color:red; font-family:courier; font-size:80%;"><fmt:message key="no.routes"/></span>
         </c:if>
                </div>
                      <c:if test="${requestScope.noStations == null}">
            <button type="submit" class="btn btn-dark" ><fmt:message key="user.page.book.search"/></button>
                     </c:if>

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
                                <fmt:message key="${route.departureStation}"/>
								-
                                <fmt:message key="${route.arrivalStation}"/>
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
                                <c:choose>
                                <c:when test="${route.firstClassSeats != 0}">
                                F1 <c:out value="${route.firstClassSeats}"/>
						           <a href="customer-ticket-form.command<c:out value="${route.redirectLink}"/>&class=FIRST&price=<c:out value="${route.firstClassTotalPrice}"/>"
                                    class="btn btn-dark" ><fmt:message key="user.page.book.info.select"/></a>
                                </c:when>

                                 <c:otherwise>
                                <a href="customer-ticket-form.command<c:out value="${route.redirectLink}"/>&class=FIRST&price=<c:out value="${route.firstClassTotalPrice}"/>"
                                                                    class="btn btn-dark disabled" aria-disabled="true" ><fmt:message key="user.page.book.info.select"/></a>
                                 </c:otherwise>
                                 </c:choose>
                                <br><br>
                                <c:choose>
                                <c:when test="${route.secondClassSeats != 0}">
                                S2 <c:out value="${route.secondClassSeats}"/>
                                <a href="customer-ticket-form.command<c:out value="${route.redirectLink}"/>&class=SECOND&price=<c:out value="${route.secondClassTotalPrice}"/>"
                                    class="btn btn-dark" ><fmt:message key="user.page.book.info.select"/></a>
                               </c:when>

								<c:otherwise>
								<br>
								 <a href="customer-ticket-form.command<c:out value="${route.redirectLink}"/>&class=SECOND&price=<c:out value="${route.secondClassTotalPrice}"/>"
                                                                    class="btn btn-dark disabled" aria-disabled="true"><fmt:message key="user.page.book.info.select"/></a>
                                </c:otherwise>
                                </c:choose>
                               </td>

                               <td>
                               <c:choose>
                               <c:when test="${route.firstClassSeats != 0}">
                               <p style = "text-align: center"><c:out value="${route.firstClassTotalPrice}"/></p>
                               </c:when>
                               <c:otherwise>
                               <p style = "text-align: center"><fmt:message key="no.free.places"/></p>
                                   </c:otherwise>
                                   </c:choose>
                                   <br>
								<c:choose>
                               <c:when test="${route.secondClassSeats != 0}">
                               <p style = "text-align: center"><c:out value="${route.secondClassTotalPrice}"/></p>
                               </c:when>
                               <c:otherwise>
                               <p style = "text-align: center"><fmt:message key="no.free.places"/></p>
                                   </c:otherwise>
                                   </c:choose>
                                 </c:forEach>
                                </td>
                               </tr>
                </tbody>
				<span style ="text-align: left; color:black; font-family:courier; font-size:79%;">
				*<fmt:message key="user.page.book.info.span"/>
				</span>
				</table>
				</c:if>
		</div>
</body>
</html>
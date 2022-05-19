<%@ include file="/view/static/basic_context.jsp" %>
<%@ taglib prefix="datef" uri="/WEB-INF/tlds/dateFunction.tld"  %>
<head>
<%@ include file="/view/static/meta.jsp"%>
</head>
<body>
<%@ include file="/view/static/header.jsp"%>
<br><br>
 		<div align="center">
 		<form>
 		                <h2>Route schedule for route # <c:out value="${routeId}"/></h2>
 		<table class="table table-striped table-responsive-md btn-table" >
                <thead>
                                  <tr>
                                  <th scope="col">Station</th>
                                  <th scope="col">Arrival time</th>
                                  <th scope="col">Departure time</th>
                                  </tr>
                </thead>
                <tbody>
                              <c:forEach items="${routeInfo}" var="routeInfo" >
                              <tr>
                                <td style="text-align:center">
                                <strong>
                                <c:out value="${routeInfo.station.name}"/>
                                <c:out value="${routeInfo.station.code}"/>
                                </strong>
                                </td>

                                 <td style="text-align:center">
                                 <strong>
                                 <c:out value="${datef:formatLocalDateTime(routeInfo.arrivalDate, 'dd.MM.yyyy HH:mm')}"/>
                                 </strong>
                                 </td>

                                 <td style="text-align:center">
                                 <strong>
                                 <c:out value="${datef:formatLocalDateTime(routeInfo.departureDate, 'dd.MM.yyyy HH:mm')}"/>
                                 </strong>
                                 </td>
                                 </tr>
                                 </c:forEach>
                </tbody>
                </table>
                 </form>
                </div>
        </body>
</html>
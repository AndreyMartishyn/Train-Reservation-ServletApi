<%@ include file="/view/static/basic_context.jsp" %>
<head>
<%@ include file="/view/static/meta.jsp"%>
</head>
<body>
<%@ include file="/view/static/header.jsp"%>
<br>
<div class="col-md-5 offset-4">
<form action="customer-train-search.command" method="post">
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
        </div>
            <button type="submit" class="btn btn-dark"><fmt:message key="user.page.book.search"/></button>
        </div>
        </div>
        </form>
</body>
</html>
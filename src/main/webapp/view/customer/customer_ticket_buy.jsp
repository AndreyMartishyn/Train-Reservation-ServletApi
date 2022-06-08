<%@ include file="/view/static/basic_context.jsp" %>
<head>
<%@ include file="/view/static/meta.jsp"%>
</head>
<body>
<%@ include file="/view/static/header.jsp"%>
<div class="wrapper fadeInDown">
<div id="formContent">
<form action="customer-buy-ticket.command" method="post">
<section class="order-form my-4 mx-4">
    <div class="container pt-4">
      <div class="row">
        <div class="col-12">
          <h1><fmt:message key="user.page.order.title"/></h1>
          <span><fmt:message key="user.page.order.info"/> *</span>
          <hr class="mt-1">
        </div>
              <div class="col-12">
              <label class="order-form-label"><fmt:message key="admin.page.user.first.name"/>*</label>
            </div>
            <div class="col-12">
              <input class="order-form-input" placeholder="<fmt:message key="placeholder.name"/>" name="firstName" required/>
            </div>
              <div class="col-12">
             <label class="order-form-label"><fmt:message key="admin.page.user.last.name"/>*</label>
             </div>
            <div class="col-12">
           <input class="order-form-input" placeholder="<fmt:message key="placeholder.surname"/>" name="lastName" required/>
           </div>
            </div>

             <div class="col-12">
              <label class="order-form-label"><fmt:message key="user.page.book.departure"/></label>
            </div>
            <div class="col-12">
              <input class="order-form-input" placeholder="<fmt:message key="${bookingDTO.departureStation}"/>"   readonly="readonly" />
            </div>
            <div class="col-12">
            <label class="order-form-label"><fmt:message key="user.page.book.arrival"/></label>
             </div>
            <div class="col-12">
             <input class="order-form-input" placeholder="<fmt:message key="${bookingDTO.arrivalStation}"/>" readonly="readonly" />
            </div>

             <div class="col-12">
              <label class="order-form-label" for="date-picker-example"><fmt:message key="user.page.book.info.departure.time"/></label>
            </div>
            <div class="col-12">
            <input class="order-form-input" name="dates" value="${bookingDTO.departureTime}" readonly="readonly" />
            </div>
            <div class="col-12">
            <label class="order-form-label" for="date-picker-example"><fmt:message key="user.page.book.info.arrival.time"/></label>
            </div>
            <div class="col-12">
           <input class="order-form-input" name="dates" value="${bookingDTO.arrivalTime}" readonly="readonly" />
           </div>

			<div align="center">
            <div class="col-6">
              <label class="order-form-label"><fmt:message key="user.page.order.wagon"/>*</label>
               <select class="browser-default custom-select" name="wagon">
               <c:forEach items="${bookingDTO.coachesNumbers}" var="coaches">
               <option value="${coaches}">${coaches}</option>
               </c:forEach>
                </select>
            </div>
            </div>

             <div class="col-12">
             <label class="order-form-label" for="date-picker-example"><fmt:message key="user.page.order.place"/>*</label>
             </div>
             <div class="col-12">
             <input class="order-form-input" name="place" placeholder="<fmt:message key="placeholder.choose.place"/>" required>
            </div>
			<br>
            <div class="col-12">
             <input type="hidden" name="trainId" value="<c:out value='${bookingDTO.trainId}' />" />
             <input type="hidden" name="departureStation" value="<c:out value='${bookingDTO.departureStation}' />" />
             <input type="hidden" name="arrivalStation" value="<c:out value='${bookingDTO.arrivalStation}' />" />
             <input type="hidden" name="departureTime" value="<c:out value='${bookingDTO.departureTime}' />" />
             <input type="hidden" name="arrivalTime" value="<c:out value='${bookingDTO.arrivalTime}' />" />
             <input type="hidden" name="class" value="<c:out value='${bookingDTO.comfortClass}' />" />
             <input type="hidden" name="price" value="<c:out value='${bookingDTO.cost}' />" />
             <input type="hidden" name="duration" value="<c:out value='${bookingDTO.duration}' />" />
             <input type="submit" class="btn btn-dark" value="<fmt:message key="user.page.order.book"/>"></button>
            </div>
          </div>
          </div>
      </section>
  </form>
  </div>
  </div>
  </body>
</html>
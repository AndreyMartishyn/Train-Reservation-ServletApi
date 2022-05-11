<%@ include file="/view/static/basic_context.jsp" %>
<head>
<%@ include file="/view/static/meta.jsp"%>
</head>
<body>
<%@ include file="/view/static/header.jsp"%>
<br>
<div align="center">
<form action="customer-buy-ticket.command" method="post">
<section class="order-form my-4 mx-4">
    <div class="container pt-4">

      <div class="row">
        <div class="col-12">
          <h1>Order Form</h1>
          <span>Please, fill information with *</span>
          <hr class="mt-1">
        </div>
        <div class="col-12">

          <div class="row mx-4">
            <div class="col-12 mb-2">
              <label class="order-form-label">First name and Surname*</label>
            </div>
            <div class="col-12 col-sm-6">
              <input class="order-form-input" placeholder="Enter your first name" name="firstName" required/>
            </div>
            <div class="col-12 col-sm-6 mt-2 mt-sm-0">
              <input class="order-form-input" placeholder="Enter your last name" name="lastName" required/>
            </div>
          </div>

          <div class="row mt-3 mx-4">
            <div class="col-12">
              <label class="order-form-label">Departure Station</label>
            </div>
            <div class="col-12">
              <input class="order-form-input" name="departureStation" value="<c:out value='${bookingDTO.departureStation}' />" readonly="readonly" />
            </div>
          </div>

          <div class="row mt-3 mx-4">
            <div class="col-12">
              <label class="order-form-label">Arrival Station</label>
            </div>
            <div class="col-12">
              <input class="order-form-input" name="arrivalStation" value="<c:out value='${bookingDTO.arrivalStation}' />" readonly="readonly" />
            </div>
          </div>

          <div class="row mt-3 mx-4">
            <div class="col-12">
              <label class="order-form-label" for="date-picker-example">Dates of travel</label>
            </div>
            <div class="col-12">
            <input class="order-form-input" name="dates" value="${bookingDTO.departureTime} - ${bookingDTO.arrivalTime}"
             readonly="readonly" />
            </div>
          </div>

          <div class="row mt-3 mx-4">
            <div class="col-12">
              <label class="order-form-label">Choose wagon*</label>
                 </div>
            <div class="col-12">
               <select class="browser-default custom-select" name="wagon">
               <c:forEach items="${bookingDTO.coachesNumbers}" var="coaches">
               <option value="${coaches}">${coaches}</option>
               </c:forEach>
                </select>
            </div>
            </div>

            <div class="row mt-3 mx-4">
             <div class="col-12">
             <label class="order-form-label" for="date-picker-example">Choose place*</label>
             </div>
             <div class="col-12">
             <input class="order-form-input" name="place" placeholder="Enter your place between 1-80" required>
            </div>
           </div>

          <div class="row mt-3">
            <div class="col-12">
             <input type="hidden" name="trainId" value="<c:out value='${bookingDTO.trainId}' />" />
             <input type="hidden" name="departureTime" value="<c:out value='${bookingDTO.departureTime}' />" />
             <input type="hidden" name="arrivalTime" value="<c:out value='${bookingDTO.arrivalTime}' />" />
             <input type="hidden" name="class" value="<c:out value='${bookingDTO.comfortClass}' />" />
             <input type="submit" class="btn btn-dark" ></button>
            </div>
          </div>
        </div>
      </div>
    </div>
  </section>
  </form>
  </div>
  </body>
</html>
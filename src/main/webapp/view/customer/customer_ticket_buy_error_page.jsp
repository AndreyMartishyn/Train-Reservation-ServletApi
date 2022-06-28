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
          <h1><fmt:message key="booking.error.page"/></h1>
          <hr class="mt-1">
        </div>
        <div align="center">
                    <c:if test="${requestScope.notValidPlaceRange != null}">
                              <span style ="text-align: center; color:red; font-family:courier; font-size:80%;"><fmt:message key="booking.not.valid.range"/><c:out value="${numOfPlaces}"/></span>
                              </c:if>
                       <c:if test="${requestScope.wrongName != null}">
                                                     <span style ="text-align: center; color:red; font-family:courier; font-size:80%;"><fmt:message key="booking.wrong.name"/></span>
                                                     </c:if>
                       <c:if test="${requestScope.wrongSurname != null}">
                               <span style ="text-align: center; color:red; font-family:courier; font-size:80%;"><fmt:message key="booking.wrong.surname"/></span>
                               </c:if>
                       <c:if test="${requestScope.wrongPlace != null}">
                                <span style ="text-align: center; color:red; font-family:courier; font-size:80%;"><fmt:message key="booking.wrong.place"/></span>
                                 </c:if>
                          <c:if test="${requestScope.occupiedPlace != null}">
                                 <span style ="text-align: center; color:red; font-family:courier; font-size:80%;"><fmt:message key="booking.occupied"/></span>
                                 </c:if>

			<br>
            <div class="col-12">
              <a href="index.command" class="btn btn-dark" ><fmt:message key="home"/></a>
            </div>
            </div>
          </div>
          </div>
      </section>
  </form>
  </div>
  </div>
  </body>
</html>
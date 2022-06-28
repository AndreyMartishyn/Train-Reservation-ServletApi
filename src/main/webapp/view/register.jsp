<%@ include file="/view/static/basic_context.jsp" %>
<head>
<%@ include file="/view/static/meta.jsp"%>
</head>
<body>
<%@ include file="/view/static/header.jsp"%>
<br>
<div class="wrapper fadeInDown">
    <div id="formContent">
  <form action="register.command" method="post">
  <h3 class="mb-4 text-center"><fmt:message key="register.form"/></h3>
   <div class="form-group">
   <input type="firstName" name="firstName" class="form-control" placeholder="<fmt:message key="enter.your.name"/>"
    title="Cyrillic or latin characters from 1 to 16 without symbols and digits" required>
   </div>
   <div class="form-group">
   <input type="lastName" name="lastName" class="form-control" placeholder="<fmt:message key="enter.your.surname"/>"
    title="Cyrillic or latin characters from 1 to 16 without symbols and digits" required>
   </div>
   <div class="form-group">
    <input type="email" name="email" class="form-control" placeholder="<fmt:message key="enter.your.email"/>"
     title="Your email in format test1@test.com without './_-'" required>
    </div>
    <div class="form-group">
    <input type="password" name="password" class="form-control" placeholder="<fmt:message key="enter.your.password"/>"
     title="Password must contain 8-16 latin characters without symbols and at least 1 digit" required>
    <small><fmt:message key="small.id"/></small>
    </div>
    <div class="form-group">
    <button type="submit" class="btn btn-dark"><fmt:message key="register.user"/></button>
    </div>
     <c:if test="${requestScope.wrongFirstName !=null}">
      <span style ="text-align: center; color:red; font-family:courier; font-size:70%;"><fmt:message key="register.wrong.firstName"/></span>
                </c:if>
        <c:if test="${requestScope.wrongLastName !=null}">
            <span style ="text-align: center; color:red; font-family:courier; font-size:70%;"><fmt:message key="register.wrong.lastName"/></span>
                      </c:if>
        <c:if test="${requestScope.wrongEmail !=null}">
            <span style ="text-align: center; color:red; font-family:courier; font-size:70%;"><fmt:message key="register.wrong.email"/></span>
                      </c:if>
        <c:if test="${requestScope.wrongPass !=null}">
            <span style ="text-align: center; color:red; font-family:courier; font-size:70%;"><fmt:message key="register.wrong.password"/></span>
                      </c:if>
        <c:if test="${requestScope.alreadyExists !=null}">
        <span style ="text-align: center; color:red; font-family:courier; font-size:70%;"><fmt:message key="register.alreadyExists"/></span>
                </c:if>
    </form>
    <div id="formFooter">
    <a class="underlineHover" href="login-page.command"><fmt:message key="login.form"/></a>
    </div>
 </div>
 </div>
 </body>
 </html>
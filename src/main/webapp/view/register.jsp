<%@ include file="/view/static/basic_context.jsp" %>
<head>
<%@ include file="/view/static/meta.jsp"%>
</head>
<body>
<%@ include file="/view/static/header.jsp"%>
<br>
<div class="col-md-5 offset-4">
  <form action="register.command" method="post">
  <h3 class="mb-4 text-center"><fmt:message key="register.form"/></h3>
   <div class="form-group">
   <input type="firstName" name="firstName" class="form-control" placeholder="<fmt:message key="enter.your.name"/>"
   pattern="^[A-Za-z\u0400-\u04ff]{1,16}$" title="Cyrillic or latin characters from 1 to 16 without symbols and digits" required>
   </div>
   <div class="form-group">
   <input type="lastName" name="lastName" class="form-control" placeholder="<fmt:message key="enter.your.surname"/>"
   pattern="^[A-Za-z\u0400-\u04ff]{1,16}$" title="Cyrillic or latin characters from 1 to 16 without symbols and digits" required>
   </div>
   <div class="form-group">
    <input type="email" name="email" class="form-control" placeholder="<fmt:message key="enter.your.email"/>"
    pattern="^[a-zA-Z0-9_.-]{2,20}+@[a-zA-Z-]{3,7}+\.[a-zA-Z.]{2,3}+$" title="Your email in format test1@test.com without './_-'" required>
    </div>
    <div class="form-group">
    <input type="password" name="password" class="form-control" placeholder="<fmt:message key="enter.your.password"/>"
    pattern="^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,16}$" title="Password must contain 8-16 latin characters without symbols and at least 1 digit" required>
    <small><fmt:message key="small.id"/></small>
    </div>
    <div class="form-group">
    <button type="submit" class="btn btn-dark"><fmt:message key="register.user"/></button>
    </div>
      <span style ="text-align: center; color:red; font-family:courier; font-size:70%;">${errorValidation}</span>
    </form>
 </div>
 </div>
 </div>
 </body>
 </html>
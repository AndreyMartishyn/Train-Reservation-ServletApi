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
   <input type="firstName" name="firstName" class="form-control" placeholder="<fmt:message key="enter.your.name"/>" required>
   </div>
   <div class="form-group">
   <input type="lastName" name="lastName" class="form-control" placeholder="<fmt:message key="enter.your.surname"/>" required>
   </div>
   <div class="form-group">
    <input type="email" name="email" class="form-control" placeholder="<fmt:message key="enter.your.email"/>" required>
    </div>
    <div class="form-group">
    <input type="password" name="password" class="form-control" placeholder="<fmt:message key="enter.your.password"/>" required>
    <small id="passwordHelp" ><fmt:message key="small.id"/></small>
    </div>
    <div class="form-group">
    <button type="submit" class="btn btn-dark"><fmt:message key="register.user"/></button>
    </div>
    </form>
  <span style ="text-align: center; color:red; font-family:courier; font-size:70%;">${notValidInput}</span>
  <span style ="text-align: center; color:red; font-family:courier; font-size:70%;">${notCorrectPass}</span>
  <span style ="text-align: center; color:red; font-family:courier; font-size:70%;">${noSuchUser}</span>
 </div>
 </div>
 </div>
 </body>
 </html>
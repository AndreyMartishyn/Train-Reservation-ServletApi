<%@ include file="/view/static/basic_context.jsp" %>
<head>
<%@ include file="/view/static/meta.jsp"%>
</head>
<body>
<%@ include file="/view/static/header.jsp"%>
<br>
<div class="col-md-5 offset-4">
<form action="login.command" method="post">
<h3 class="mb-4 text-center"><fmt:message key="login.form"/></h3>
<div class="form-group">
<input type="email" name="email" class="form-control" placeholder="<fmt:message key="enter.your.email"/>" required>
</div>
<div class="form-group">
<input id="password" type="password" name="password" class="form-control" placeholder="<fmt:message key="enter.your.password"/>" required>
<small id="passwordHelp" ><fmt:message key="small.id"/></small>
</div>
  <span style ="text-align: center; color:red; font-family:courier; font-size:70%;">${notValidInput}</span>
  <span style ="text-align: center; color:red; font-family:courier; font-size:70%;">${notCorrectPass}</span>
  <span style ="text-align: center; color:red; font-family:courier; font-size:70%;">${noSuchUser}</span>
 <div class="form-group">
    <button type="submit" class="btn btn-dark"><fmt:message key="login.user"/></button>
    </div>
</form>
</div>
 </div>
  </body>
 </html>

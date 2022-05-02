<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/view/static/meta.jsp"%>
</head>
<body>
<%@ include file="/view/static/header.jsp"%>
<br>
<div class="row justify-content-center">
<div class="col-md-6 col-lg-4">
<form action="login.command" method="post">
<h3 class="mb-4 text-center">Login Form</h3>
  <span style ="text-align: center; color:red; font-family:courier; font-size:70%;">${notValidInput}</span>
  <span style ="text-align: center; color:red; font-family:courier; font-size:70%;">${notCorrectPass}</span>
  <span style ="text-align: center; color:red; font-family:courier; font-size:70%;">${noSuchUser}</span>
<div class="form-group">
<input type="email" name="email" class="form-control" placeholder="Enter your email" required>
</div>
<div class="form-group">
<input id="password" type="password" name="password" class="form-control" placeholder="Enter your password" required>
<small id="passwordHelp" >We`ll never share your credentials with anyone else</small>
</div>
<div class="form-group">
<button type="submit" class="form-control btn btn-primary submit px-3">Sign In</button>
</div>
</form>
</div>
 </div>
  </body>
 </html>

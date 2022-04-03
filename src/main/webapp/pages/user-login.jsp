<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Bookshop Website</title>
</head>
<body>
<div align="center">
  <h1>Employee Register Form</h1>
  <form action="<%= request.getContextPath() %>/login" method="post">
   <table style="with: 80%">
     <tr>
     <td>Email</td>
     <td><input type="text" name="email" /></td>
    </tr>
    <tr>
     <td>Password</td>
     <td><input type="password" name="password" /></td>
    </tr>
    </table>
    <input type="submit" value="Submit" />
  </form>
  <span style ="text-align: center; color:red; font-family:courier; font-size:70%;">${invalidData}</span>
 </div>
   </body>
</html>

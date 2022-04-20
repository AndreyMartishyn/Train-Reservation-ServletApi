<%@ page language="java" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/view/static/meta.html" %>

</head>
<body>
<%@ include file="/view/static/header.html" %>

<nav class="navbar navbar-expand-lg navbar-light bg-light">
  <div class="container-fluid">
    <a class="navbar-brand" href="#">Welcome, Guest</a>
    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarText" aria-controls="navbarText" aria-expanded="false" aria-label="Toggle navigation">
      <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarText">
      <ul class="navbar-nav me-auto mb-2 mb-lg-0">
        <li class="nav-item">
          <a class="nav-link active" aria-current="page" href="index.command">Home</a>
        </li>
        <li class="nav-item">
          <a class="nav-link" href="register-page.command">Registration</a>
        </li>
        <li class="nav-item">
          <a class="nav-link" href="login-page.command">Login</a>
        </li>
      </ul>
        </div>
  </div>
</nav>

 </div>
 <%@ include file="/view/static/footer.html" %>
</body>
</html>
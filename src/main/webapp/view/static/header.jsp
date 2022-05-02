<div style="text-align: center;background: #E0E0E0;height: 50px;padding: 5px;border-radius: 10px;">
    <h1>Train Reservation System</h1>
</div>
<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <div class="container-fluid">
        <c:choose>
            <c:when test="${sessionScope.user == null}">
                <a class="navbar-brand" href="index.command">Welcome, Guest</a>
            </c:when>
            <c:otherwise>
                <a class="navbar-brand" href="index.command">Welcome, ${user.role}</a>
            </c:otherwise>
        </c:choose>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarText"
                aria-controls="navbarText" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse" id="navbarText">
            <ul class="navbar-nav">
                <li class="nav-item">
                    <a class="nav-link active" aria-current="page" href="index.command">Home</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link active" aria-current="page" href="about-us.command">About us</a>
                </li>
                <c:if test="${sessionScope.user.role == 'CUSTOMER'}">
                <li class="nav-item">
                    <a class="nav-link active" aria-current="page" href="customer-booking.command">Book</a>
                </li>
                </c:if>
                <c:if test="${sessionScope.user.role == 'ADMIN'}">
                <li class="nav-item">
				<a class="nav-link active" aria-current="page" href="routes-page.command">Routes</a>
                </li>
                <li class="nav-item"><a class="nav-link active" aria-current="page" href="stations-page.command">Stations</a>
                </li>
                </li>
                <li class="nav-item"><a class="nav-link active" aria-current="page" href="users-page.command">Users</a>
                </li>
                </c:if>
                </ul>
                <ul class="navbar-nav ml-auto">
                <c:choose>
                    <c:when test="${sessionScope.user == null}">
                        <li class="nav-item">
                            <a class="nav-link active" href="register-page.command">Register</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link active" href="login-page.command">Login</a>
                        </li>
                    </c:when>
                    <c:otherwise>
                        <li class="nav-item">
                            <a class="nav-link active" href="logout.command">Logout</a>
                        </li>
                    </c:otherwise>
                </c:choose>
            </ul>
        </div>
    </div>
</nav>
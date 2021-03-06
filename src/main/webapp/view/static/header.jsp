 <div style="text-align: center; box-sizing: content-box;">
     <cstm:custom/>
 </div>
     <div style="text-align: center;background:#fff;;height: 80px;padding: 5px; border-radius: 10px;">
         <img src="view/images/local_railway.png" height="80"/>
     </div>
</div>
<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <div class="container-fluid">
    <a class="navbar-brand mt-2 mt-lg-0" href="index.command">
                        <img src="view/images/train_header.png"
                                height="30"
                                alt="Logo"
                                loading="lazy"/>
                    </a>
        <c:choose>
            <c:when test="${sessionScope.user == null}">
                <a class="navbar-brand" href="index.command"><fmt:message key="welcome.guest"/></a>
            </c:when>
            <c:otherwise>
                <a class="navbar-brand" href="index.command"><fmt:message key="welcome"/>, ${user.firstName}</a>
            </c:otherwise>
        </c:choose>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarText"
                aria-controls="navbarText" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse" id="navbarText">
            <ul class="navbar-nav">
                <li class="nav-item">
                    <a class="nav-link active" aria-current="page" href="index.command"><fmt:message key="home"/></a>
                </li>
               <c:if test="${sessionScope.user.role == 'CUSTOMER'}">
                <li class="nav-item">
                <a class="nav-link active" aria-current="page" href="customer-tickets-page.command"><fmt:message key="main.page.tickets"/></a>
                </li>
                </c:if>
                <c:if test="${sessionScope.user.role == 'ADMIN'}">
                <li class="nav-item">
				<a class="nav-link active" aria-current="page" href="routes-page.command"><fmt:message key="routes"/></a>
                </li>
                <li class="nav-item"><a class="nav-link active" aria-current="page" href="stations-page.command"><fmt:message key="stations"/></a>
                </li>
                </li>
                <li class="nav-item"><a class="nav-link active" aria-current="page" href="wagons-page.command"><fmt:message key="admin.page.wagons"/></a>
                </li>
                </li>
                <li class="nav-item"><a class="nav-link active" aria-current="page" href="users-page.command"><fmt:message key="users"/></a>
                </li>
               </li>
               <li class="nav-item"><a class="nav-link active" aria-current="page" href="customer-ticket-status.command"><fmt:message key="main.page.reports"/></a>
               </li>


                </c:if>
                </ul>
                <ul class="navbar-nav ml-auto">
                <c:choose>
                    <c:when test="${sessionScope.user == null}">
                        <li class="nav-item">
                            <a class="nav-link active" href="register-page.command"><fmt:message key="register"/></a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link active" href="login-page.command"><fmt:message key="login"/></a>
                        </li>
                    </c:when>
                    <c:otherwise>
                        <li class="nav-item">
                            <a class="nav-link active" href="logout.command"><fmt:message key="logout"/></a>
                        </li>
                    </c:otherwise>
                </c:choose>
            </ul>

                </div>
    </div>
</nav>
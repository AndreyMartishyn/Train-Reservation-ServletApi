<%@ include file="/view/static/basic_context.jsp" %>
<head>
<%@ include file="/view/static/meta.jsp" %>
</head>
<body>
<%@ include file="/view/static/header.jsp" %>
<br>
		<div class="wrapper fadeInDown">
        <div id="formContent">
                <c:if test="${station != null}">
                <form action="station-edit-post.command" method="post">
                </c:if>
                <c:if test="${station == null}">
                    <form action="station-add-post.command" method="post">
                </c:if>
                <c:if test="${station != null}">
                <h2><fmt:message key="admin.page.station.edit"/></h2>
                </c:if>
                <c:if test="${station == null}">
                <h2><fmt:message key="admin.page.station.add.form"/></h2>
                </c:if>
                <c:if test="${station != null}">
                <input type="hidden" name="id" value="<c:out value='${station.id}' />" />
                </c:if>
                <div class="input-group">
                <span class="input-group-text"><fmt:message key="admin.page.station.station-name-code"/></span>
                <input type="text" class="form-control" name="name" value="<c:out value='${station.name}' />"
                title="Station name should contain cyryllic/latic 2 words with hyphen (may include apostrophe)" required>
                <input type="text" class="form-control" name="code"  value="<c:out value='${station.code}' />"
                 title="Station code must contain 3 capitalized letters cyrillic / latic" required>
              	</div>
              	 <c:if test="${requestScope.wrongName != null}">
                      <span style ="text-align: center; color:red; font-family:courier; font-size:70%;"><fmt:message key="station.wrong.name"/></span>
                                </c:if>
                 <c:if test="${requestScope.wrongCode != null}">
                      <span style ="text-align: center; color:red; font-family:courier; font-size:70%;"><fmt:message key="station.wrong.code"/></span>
                                </c:if>
                <br>
               <div class="form-group">
               <input type="submit" class="btn btn-dark" value="<fmt:message key="admin.page.action.save"/>"/>
               </div>
                </div>
                </div>
                </div>

</body>
</html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java"
import="ua.martishyn.app.data.entities.Station"
%>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/view/static/meta.jsp" %>
</head>
<body>
<%@ include file="/view/static/header.jsp" %>

<br><br>
<div align="center">
    <form>
         <caption>
         <c:if test="${station != null}">
         <h2>Edit station</h2>
         </c:if>
         <c:if test="${station == null}">
         <h2>Add new station</h2>
         </c:if>
        </caption>
                <c:if test="${station != null}">
                    <form action="station-edit-post.command" method="post">
                </c:if>
                <c:if test="${station == null}">
                    <form action="station-add-post.command" method="post">
                </c:if>
                 <c:if test="${station != null}">
                 <input type="hidden" name="id" value="<c:out value='${station.id}' />" />
                 </c:if>
                 <div class="input-group">
                   <span class="input-group-text">Station name and code</span>
                      <input type="text" class="form-control" name="name" value="<c:out value='${station.name}' />"/>
                    <input type="text" class="form-control" name="code"  value="<c:out value='${station.code}' />"/>
                 	</div>
                  	<input type="submit" class="btn btn-dark" value="Save"/>
                </form>

            </div>
            </form>
</body>
</html>
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

            <c:if test="${singleRoute != null}">
                <form action="route-edit-post.command" method="post">
                </c:if>
                    <c:if test="${singleRoute == null}">
                <form action="route-add-post.command" method="post">
                </c:if>
                <caption>
               <c:if test="${singleRoute != null}">
              <h2>Edit Route</h2>
              </c:if>
              <c:if test="${singleRoute == null}">
               <h2>Add new route</h2>
              </c:if>
               </caption>
        <table style="border:1px solid black;margin-left:auto;margin-right:auto;">
            <tr>
                <th>Route id:</th>
                <td>
                    <input type="text" name="id" size="45"
                           value="<c:out value='${singleRoute.id}' />" required/>
                </td>
            <tr>
                <th>Train id:</th>
                <td>
                    <input type="text" name="trainId" size="45"
                           value="<c:out value='${singleRoute.trainId}' />" required/>
                </td>
            </tr>
            <tr>
                <th>Station id:</th>
                <td>
                    <input type="text" name="stationId" size="45"
                           value="<c:out value='${singleRoute.stationId}' />"required/>
                </td>
            </tr>
            <tr>
                <th>Arrival Info:</th>
                <td>
                    <input type="datetime-local" name="arrival" size="45"
                             value="<c:out value='${singleRoute.arrival}' />" required />
                </td>
            </tr>
            <tr>
                <th>Departure info:</th>
                <td>
                    <input type="datetime-local" name="departure" size="45"
                           value="<c:out value='${singleRoute.departure}' />" required />
                </td>
            </tr>

             </table>
                <span style ="text-align: center; color:red; font-family:courier; font-size:80%;">${errorValidation}</span>
             	<span style ="text-align: center; color:red; font-family:courier; font-size:80%;">${errorLogic}</span>
             <div class="form-group">
                 	<input type="submit" class="btn btn-dark" value="Save"/>
              </div>
              </div>
</form>
</body>
</html>
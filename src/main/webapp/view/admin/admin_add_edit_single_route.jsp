<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java"
import="ua.martishyn.app.data.entities.Station"
%>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/view/static/meta.html" %>
</head>
<body>
<%@ include file="/view/static/header.html" %>
 <div align="center">
        <h1>Welcome to Train Reservation Website Admin Panel</h1>
         <br><br>
        <div align="center">
         <caption>
         <c:if test="${singleRoute != null}">
         Edit Route
         </c:if>
         <c:if test="${singleRoute == null}">
         Add New Route
         </c:if>
        </caption>
                <c:if test="${singleRoute != null}">
                    <form action="route-edit-post.command" method="post">
                </c:if>
                <c:if test="${singleRoute == null}">
                    <form action="route-add-post.command" method="post">
                </c:if>
                <table border="1" cellpadding="5">
                        <tr>
                        <th>Route id:</th>
                        <td>
                        <input type="text" name="id" size="45"
                        value="<c:out value='${singleRoute.id}' />" />
                        </td>
                        <tr>
                        <th>Train id:</th>
                        <td>
                            <input type="text" name="trainId" size="45"
                                    value="<c:out value='${singleRoute.trainId}' />"
                                />
                        </td>
                    </tr>
                    <tr>
                        <th>Station id:</th>
                        <td>
                            <input type="text" name="stationId" size="45"
                                    value="<c:out value='${singleRoute.stationId}' />"
                            />
                        </td>
                    </tr>
                        <tr>
                        <th>Arrival Info:</th>
                        <td>
                            <input type="text" name="arrival" size="45"
                                    value="<c:out value='${singleRoute.arrival}' />"
                            />
                        </td>
                    </tr>
                        <tr>
                        <th>Departure info:</th>
                        <td>
                            <input type="text" name="departure" size="45"
                                    value="<c:out value='${singleRoute.departure}' />"
                            />
                        </td>
                    </tr>
                     <tr>
                        <td colspan="2" align="center">
                            <input type="submit" value="Save" />
                        </td>
                    </tr>
                </table>

                </form>
            </div>
</body>
</html>
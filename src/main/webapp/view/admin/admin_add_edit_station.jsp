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
         <c:if test="${station != null}">
         Edit Station
         </c:if>
         <c:if test="${station == null}">
         Add New Station
         </c:if>
        </caption>
                <c:if test="${station != null}">
                    <form action="station-edit-post.command" method="post">
                </c:if>
                <c:if test="${station == null}">
                    <form action="station-add-post.command" method="post">
                </c:if>
                <table border="1" cellpadding="5">
                            <c:if test="${station != null}">
                            <input type="hidden" name="id" value="<c:out value='${station.id}' />" />
                        </c:if>
                        <tr>
                        <th>Name: </th>
                        <td>
                            <input type="text" name="name" size="45"
                                    value="<c:out value='${station.name}' />"
                                />
                        </td>
                    </tr>
                    <tr>
                        <th>Code: </th>
                        <td>
                            <input type="text" name="code" size="45"
                                    value="<c:out value='${station.code}' />"
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
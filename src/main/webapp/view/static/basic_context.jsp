<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8" %>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="cstm" uri="/WEB-INF/tlds/customTag.tld"  %>
 <c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : 'ua'}" scope="session" />
 <fmt:setLocale value="${language}" />
 <fmt:setBundle basename="i18n.messages" />
 <!DOCTYPE html>
 <html lang="${language}">
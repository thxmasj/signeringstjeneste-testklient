<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>

<div class="page-header">
    <h1>Testklient <small>Testklient for signering</small></h1>
</div>

<ul class="nav nav-tabs">
    <spring:url value="/client/messages" var="showMessagesUrl" />
    <spring:url value="/client/" var="sendMessageUrl" />
    <li class="${param.sendActive}"><a href="${sendMessageUrl}">Send</a></li>
    <li class="${param.showActive}"><a href="${showMessagesUrl}">Vis</a></li>

</ul>

<br/>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>

<div class="page-header">
    <h1>Testklient <small>Testklient for signering</small></h1>
</div>

<ul class="nav nav-tabs">
    <spring:url value="/client/signatures" var="showSignaturesUrl" />
    <spring:url value="/client/" var="doSignatureUrl" />
    <li class="${param.sendActive}"><a href="${doSignatureUrl}">Signer</a></li>
    <li class="${param.showActive}"><a href="${showSignaturesUrl}">Tidligere signaturer</a></li>

</ul>

<br/>

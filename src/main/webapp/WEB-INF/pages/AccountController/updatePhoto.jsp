<%-- 
    Document   : index
    Created on : Apr 16, 2013, 12:15:39 AM
    Author     : Summers
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../../templates/before_body.jsp" %>
<div class="span2">
    <c:if test="${accountBean.image != null}">
        <img src="data:<c:out value="${accountBean.image.type}"/>;base64,<c:out value="${accountBean.image.imageBase64}"/>">
    </c:if>
    <form action="updatePhoto" enctype="multipart/form-data" method="post">
        <input name="image" type="file"/>
        <input type="submit"/>
    </form>
</div>
<div class="span8">
    <form action="#" >
        <label for="firstName">First Name</label>
        <input name="firstName" value="<c:out value="${accountBean.firstName}"/>" disabled="true">
        <label for="firstName">Last Name</label>
        <input name="lastName" value="<c:out value="${accountBean.lastName}"/>" disabled="true">
        <label for="firstName">Email Address</label>
        <input name="emailAddress" type="email" value="<c:out value="${accountBean.emailAddress}"/>" disabled="true">
        
    </form>
</div>
<%@include file="../../templates/after_body.jsp" %>
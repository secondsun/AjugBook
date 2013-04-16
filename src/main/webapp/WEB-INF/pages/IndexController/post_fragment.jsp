<%-- 
    Document   : post_fragment
    Created on : Apr 16, 2013, 12:13:45 AM
    Author     : Summers
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div class="row-fluid">
<div class="span2">
    <h4 class="success pull-right"><c:out value="${post.authorUser.firstName}"/> <c:out value="${post.authorUser.lastName}"/>Says </h4>
    <small class="muted pull-right"> <cite> <c:out  value="${post.post.posted}"/></cite></small>
</div>
<div class="span4 ">
    <h4 class="success pull-left"><c:out value="${post.post.text}"/></h4>
    <c:if test="${post.post.image != null}"><c:if test="${post.post.image.type != null}">
        <img src="data:<c:out value="${post.post.image.type}"/>;base64,<c:out value="${post.post.image.imageBase64}"/>">
    </c:if></c:if>
</div>

</div>

<hr/>

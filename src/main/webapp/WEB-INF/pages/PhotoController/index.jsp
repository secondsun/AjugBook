<%-- 
    Document   : index
    Created on : Apr 16, 2013, 12:15:39 AM
    Author     : Summers
--%>
<%@include file="../../templates/before_body.jsp" %>
<c:forEach items="${arrayList}" var="image">
    <div class="span4">
        <img src="data:<c:out value="${image.type}"/>;base64,<c:out value="${image.imageBase64}"/>">
        <hr/>
    </div>
</c:forEach>
<%@include file="../../templates/after_body.jsp" %>
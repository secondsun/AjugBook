<%@include file="../../templates/before_body.jsp" %>
<c:if test="${identity.user != null}">
    <div class="row-fluid">
        <div class="span2">
            <h4 class="success pull-right"><c:out value="${identity.user.firstName}"/> <c:out value="${identity.user.lastName}"/>Says </h4>
            <small class="muted pull-right"> <cite> <c:out  value="${post.posted}"/></cite></small>
        </div>
        <div class="span4 ">
            <h4 class="success pull-left"><c:out value="${post.text}"/></h4>
            <c:if test="${post.image != null}"><c:if test="${post.image.type != null}">
                    <img src="data:<c:out value="${post.image.type}"/>;base64,<c:out value="${post.image.imageBase64}"/>">
                </c:if></c:if>
            </div>

        </div>

        <hr/>
</c:if>
<%@include file="../../templates/after_body.jsp" %>
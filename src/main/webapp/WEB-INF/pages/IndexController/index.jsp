<%@include file="../../templates/before_body.jsp" %>

<c:if test="${identity.user == null}">
    <div class="hero-unit">
        <h1>Would you like to register?</h1>
        <p>
            Ajug Book offers lots of features to those who are members.

        </p>
        <p><a href="./register" class="btn btn-primary btn-large">Register</a></p>
    </div>
</c:if>

    <c:forEach items="${indexResponse.posts}" var="post">
            <%@include file="post_fragment.jsp" %>
    </c:forEach>
<%@include file="../../templates/after_body.jsp" %>
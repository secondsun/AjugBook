<%@include file="../../templates/before_body.jsp" %>
<c:if test="${identity.user != null}">
    <form enctype="multipart/form-data" method="post" action="post">
        <label for="text"> What is on your mind?</label>
        <input name="text" type="text"/>
        <label for="image">Attach an image (optional)</label>
        <input name="image" type="file"/>
        <input type="submit"/>
    </form>
</c:if>
<%@include file="../../templates/after_body.jsp" %>
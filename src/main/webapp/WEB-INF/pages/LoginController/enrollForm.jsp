<%@include file="../../templates/before_body.jsp" %>

<c:if test="${identity.user == null}">
 
    <form action="register" method="post" class="">

            <label>Username:</label>
            <input autocomplete="off" type="text" name="aeroGearUser.username"/>
            <label>First Name:</label>
            <input type="text" name="aeroGearUser.firstName"/>
            <label>Last Name:</label>
            <input type="text" name="aeroGearUser.lastName"/>
            <label>Email Address:</label>
            <input type="email" name="aeroGearUser.email"/>
            
            <label>Password:</label>
            <input autocomplete="off" type="password" name="aeroGearUser.password"/>
            <input type="submit"/>

        </form>
 
</c:if>

<%@include file="../../templates/after_body.jsp" %>
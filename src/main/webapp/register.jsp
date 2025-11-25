<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ page session="false" %>

<%-- Formulari de registre --%>
<html>
<head>
    <title>Register</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/register.css" />
</head>
<body>
<div class="container">
    <h1>Create account</h1>
    <c:if test="${not empty error}">
        <div class="feedback error">${error}</div>
    </c:if>
    <form action="${pageContext.request.contextPath}/register" method="post">
        <label for="username">Username</label>
        <input id="username" name="username" required />

        <label for="password">Password</label>
        <input type="password" id="password" name="password" required />

        <button type="submit">Register</button>
    </form>
    <a class="link" href="${pageContext.request.contextPath}/login">Already have an account? Sign in</a>
</div>
</body>
</html>

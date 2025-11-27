<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page session="false" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <title>Login</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css"/>
</head>
<body class="page page-auth">
<div class="card card-auth">
    <div class="auth-header">
        <h1 class="auth-title">Sign in to your account</h1>
        <p class="auth-subtitle">Play the Session Survival Game</p>
    </div>

    <c:if test="${not empty error}">
        <div class="feedback feedback-error">${error}</div>
    </c:if>
    <c:if test="${not empty message}">
        <div class="feedback feedback-success">${message}</div>
    </c:if>

    <form action="${pageContext.request.contextPath}/login" method="post" class="auth-form">
        <div class="form-group">
            <label for="username" class="form-label">Username</label>
            <input id="username" name="username" required class="form-input"/>
        </div>

        <div class="form-group">
            <label for="password" class="form-label">Password</label>
            <input type="password" id="password" name="password" required class="form-input"/>
        </div>

        <button type="submit" class="btn btn-primary btn-full">Log in</button>
    </form>

    <p class="auth-footer">
        Don’t have an account?
        <a class="link link-primary" href="${pageContext.request.contextPath}/register">Create one</a>
    </p>

    <p class="auth-footer small">
        Just want to see the ranking?
        <a class="link" href="${pageContext.request.contextPath}/ranking">View top 10</a>
    </p>
</div>
</body>
</html>

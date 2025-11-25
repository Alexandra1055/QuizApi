<%@ page contentType="text/html;charset=UTF-8" language="java" session="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Game Over</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css"/>
</head>
<body class="page page-final">
<div class="card">
    <h1>Game Over</h1>
    <h2>Your Results</h2>
    <p><strong>Username:</strong> ${ranking.user.username}</p>
    <p><strong>Total Duration:</strong> ${ranking.time} seconds</p>
    <p><strong>Correct Answers:</strong> ${ranking.correctAnswers}</p>
    <p><strong>Incorrect Answers:</strong> ${ranking.wrongAnswers}</p>

    <div class="actions">
        <a class="btn" href="${pageContext.request.contextPath}/ranking">View top 10</a>
        <a class="btn btn-secondary" href="${pageContext.request.contextPath}/login">Play again</a>
    </div>
</div>
</body>
</html>
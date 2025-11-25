<%@ page contentType="text/html;charset=UTF-8" language="java" session="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%--
  Created by IntelliJ IDEA.
  User: alexandra
  Date: 25/11/2025
  Time: 19:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Top 10</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css"/>
</head>
<body class="page page-ranking">
<div class="card">
    <h1>Top 10 Players</h1>

    <table class="table">
        <thead>
        <tr>
            <th>#</th>
            <th>Username</th>
            <th>Time (s)</th>
            <th>Correct</th>
            <th>Wrong</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="r" items="${rankings}" varStatus="st">
            <tr>
                <td>${st.index + 1}</td>
                <td>${r.user.username}</td>
                <td>${r.time}</td>
                <td>${r.correctAnswers}</td>
                <td>${r.wrongAnswers}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

    <div class="actions">
        <a class="btn" href="${pageContext.request.contextPath}/login">Back to login</a>
    </div>
</div>

</body>
</html>

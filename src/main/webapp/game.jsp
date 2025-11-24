<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Game</title>
    <script>
        let remainingTime = ${remainingTime};

        function startCountdown() {
            const countdownElement = document.getElementById('countdown');
            const timer = setInterval(function() {
                if (remainingTime <= 0) {
                    clearInterval(timer);
                    document.getElementById('gameForm').submit();
                }
                countdownElement.innerText = remainingTime + ' seconds remaining';
                remainingTime--;
            }, 1000);
        }

        window.onload = startCountdown;
    </script>
</head>
<body>
<h1>Trivia Challenge</h1>
<h2>${question.question}</h2>
<form id="gameForm" action="game" method="post">
    <c:forEach var="option" items="${question.answers}">
        <input type="radio" name="answer" value="${option}">${option}<br>
    </c:forEach>
    <button type="submit">Submit Answer</button>
</form>
<div id="countdown"></div>
</body>
</html>

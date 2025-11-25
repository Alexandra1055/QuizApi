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
            const remainingTimeInput = document.getElementById('remainingTime'); // Get the hidden input

            const timer = setInterval(function() {
                if (remainingTime <= 0) {
                    clearInterval(timer);
                    countdownElement.innerText = 'Time is up!';
                    document.getElementById('gameForm').submit(); // Automatically submit the form
                } else {
                    countdownElement.innerText = remainingTime + ' seconds remaining';
                    remainingTimeInput.value = remainingTime; // Update the hidden input
                    remainingTime--;
                }
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
        <input type="hidden" name="remainingTime" id="remainingTime" value="${remainingTime}"/>
        <input type="radio" name="answer" value="${option}">${option}<br>
    </c:forEach>
    <button type="submit">Submit Answer</button>
</form>
<div id="countdown"></div>
</body>
</html>

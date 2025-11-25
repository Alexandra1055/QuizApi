<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Game</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css"/>
    <script>
        let remainingTime = ${remainingTime};
        let feedbackShown = false;
        let autoSubmitting = false;

        document.addEventListener('DOMContentLoaded', function () {
            const form = document.getElementById('gameForm');
            const radios = document.querySelectorAll('.option input[type="radio"]');
            const countdownElement = document.getElementById('countdown');
            const remainingTimeInput = document.getElementById('remainingTime');

            const timer = setInterval(function () {
                if (remainingTime <= 0) {
                    clearInterval(timer);
                    countdownElement.innerText = 'Time is up!';
                    remainingTime = 0;
                    remainingTimeInput.value = 0;
                    autoSubmitting = true;
                    form.submit();
                } else {
                    countdownElement.innerText = remainingTime + ' seconds remaining';
                    remainingTimeInput.value = remainingTime;
                    remainingTime--;
                }
            }, 1000);

            form.addEventListener('submit', function (e) {
                if (autoSubmitting) {
                    return;
                }

                if (!feedbackShown) {
                    e.preventDefault();
                    const selected = Array.from(radios).find(r => r.checked);
                    if (!selected) {
                        return;
                    }

                    document.querySelectorAll('.option').forEach(function (label) {
                        label.classList.remove('option-correct', 'option-incorrect');
                    });

                    const label = selected.closest('.option');
                    if (label.dataset.correct === 'true') {
                        label.classList.add('option-correct');
                    } else {
                        label.classList.add('option-incorrect');
                    }

                    feedbackShown = true;
                    const submitBtn = form.querySelector('button[type="submit"]');
                    if (submitBtn) {
                        submitBtn.textContent = 'Next question';
                    }
                }
            });
        });
    </script>
    </script>
</head>
<body class="page page-game">
<div class="card">
    <h1>Trivia Challenge</h1>
    <h2>${question.question}</h2>

    <form id="gameForm" action="${pageContext.request.contextPath}/game" method="post">
        <!-- hidden solo UNA vez -->
        <input type="hidden" name="remainingTime" id="remainingTime" value="${remainingTime}"/>

        <c:forEach var="option" items="${question.answers}">
            <c:set var="isCorrect" value="${option == correctAnswer}" />
            <label class="option" data-correct="${isCorrect}">
                <input type="radio" name="answer" value="${option}" required>
                <span>${option}</span>
            </label>
        </c:forEach>

        <button type="submit" class="btn">Submit Answer</button>
    </form>

    <div id="countdown" class="countdown"></div>
</div>
</body>
</html>

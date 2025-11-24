<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Session Survival Game - Pregunta</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background: #0b1020;
            color: #f5f5f5;
            margin: 0;
            padding: 0;
        }

        .container {
            max-width: 800px;
            margin: 40px auto;
            background: #181c33;
            border-radius: 8px;
            padding: 24px 28px;
            box-shadow: 0 4px 12px rgba(0,0,0,0.4);
        }

        h1 {
            margin-top: 0;
            text-align: center;
        }

        .top-bar {
            display: flex;
            justify-content: space-between;
            margin-bottom: 16px;
            font-size: 0.95rem;
        }

        .timer {
            font-size: 1.1rem;
            font-weight: bold;
        }

        .timer span {
            padding: 4px 10px;
            border-radius: 4px;
            background: #222743;
        }

        .stats span {
            margin-left: 10px;
        }

        .question-text {
            font-size: 1.2rem;
            margin: 20px 0;
        }

        .answers {
            list-style: none;
            padding: 0;
        }

        .answers li {
            margin-bottom: 10px;
            background: #222743;
            padding: 10px 12px;
            border-radius: 4px;
        }

        .answers input {
            margin-right: 8px;
        }

        .actions {
            margin-top: 20px;
            text-align: right;
        }

        button {
            background: #4caf50;
            color: white;
            border: none;
            padding: 10px 18px;
            border-radius: 4px;
            cursor: pointer;
            font-size: 1rem;
        }

        button:hover {
            background: #449a47;
        }

        .warning {
            margin-top: 10px;
            font-size: 0.9rem;
            color: #ffcc80;
        }
    </style>
</head>
<body>

<div class="container">

    <h1>Session Survival Game</h1>

    <div class="top-bar">
        <div class="timer">
            Tiempo restante:
            <span id="countdown">
                <c:out value="${remainingTime}" />
            </span>
            s
        </div>

        <div class="stats">
            <c:if test="${not empty username}">
                Usuario: <strong><c:out value="${username}" /></strong>
            </c:if>
            <span>✔ Correctas: <c:out value="${correctCount}" default="0"/></span>
            <span>✖ Incorrectas: <c:out value="${incorrectCount}" default="0"/></span>
        </div>
    </div>

    <div class="question-section">
        <div class="question-text">
            <c:out value="${questionDto.question}"/>
        </div>

        <form method="post" action="${pageContext.request.contextPath}/game">
            <!-- Identifica la acción en tu servlet -->
            <input type="hidden" name="action" value="answer"/>

            <!-- Lista de respuestas -->
            <ul class="answers">
                <c:forEach var="answer" items="${questionDto.answers}" varStatus="status">
                    <li>
                        <!-- value = texto de la respuesta (o status.index si prefieres índice) -->
                        <label>
                            <input type="radio"
                                   name="selectedAnswer"
                                   value="${answer}"
                                   required />
                            <c:out value="${answer}" />
                        </label>
                    </li>
                </c:forEach>
            </ul>

            <div class="actions">
                <button type="submit">Responder</button>
            </div>

            <p class="warning">
                Si el tiempo llega a 0, la partida termina automáticamente.
            </p>
        </form>
    </div>

</div>

<script>
    // Tiempo restante enviado por el backend
    let remaining = ${remainingTime != null ? remainingTime : 0};

    const countdownEl = document.getElementById("countdown");

    function updateCountdown() {
        if (remaining <= 0) {
            // Redirigir al servlet para finalizar la partida por timeout
            window.location.href = "${pageContext.request.contextPath}/game?action=timeout";
            return;
        }

        remaining--;
        countdownEl.textContent = remaining;
    }

    if (remaining > 0) {
        setInterval(updateCountdown, 1000);
    }
</script>

</body>
</html>

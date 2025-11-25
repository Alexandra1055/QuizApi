<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>Game Over</title>
</head>
<body>
<h1>Game Over</h1>
<h2>Your Results</h2>
<p>Username: ${ranking.user.username}</p>
<p>Total Duration: ${ranking.time} seconds</p>
<p>Correct Answers: ${ranking.correctAnswers}</p>
<p>Incorrect Answers: ${ranking.wrongAnswers}</p>
<a href="ranking.jsp">View top 10</a>
<a href="login.jsp">Play again</a>
</body>
</html>
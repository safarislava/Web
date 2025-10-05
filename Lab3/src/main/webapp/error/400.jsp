<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
    <head>
        <title>400 Bad Request</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/error/css/style.css"/>
    </head>
    <body>
        <div class="error-container">
            <h1>400 - Bad Request</h1>
            <p>Oops! Ты по моему что-то перепутал.</p>
            <video src="video/error.mp4" autoplay></video>
            <br>
            <a href="${pageContext.request.contextPath}/">Вернуться на главную</a>
        </div>
    </body>
</html>

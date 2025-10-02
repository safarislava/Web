<%@ page import="java.util.UUID" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="ctm" %>

<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html lang="ru">
    <head>
        <title>STAR LAB</title>
        <meta charset="utf-8">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
        <link href="https://fonts.cdnfonts.com/css/starjedi-special-edition" rel="stylesheet">

        <%
            String token = UUID.randomUUID().toString();
            session.setAttribute("csrf-token", token);
        %>
        <meta name="csrf-token" content="<%= token %>">
    </head>
    <body>
        <header>
            <div class="header-container">
                <p class="header-item logo">lab2</p>
                <p class="header-item">safarislava</p>
                <p class="header-item">variant467570</p>
            </div>
        </header>
        <main>
            <div class="graph-section">
                <div class="graph-container">
                    <svg id="graph" viewBox="-200 -200 400 400">
                        <path d="M150 0V-37.5L112.5 -100L75 -112.5L112.5 -25L50 -37.5A60 60 0 0 0 -50 -37.5L-112.5 -25L-75 -112.5L-112.5 -100L-150 -37.5V37.5L-112.5 100L-75 112.5L-112.5 25L-50 37.5 A60 60 0 0 0 50 37.5L112.5 25L75 112.5L112.5 100L150 37.5Z" id="graph-path" class="graph-item"></path>
                        <line x1="-165" y1="0" x2="165" y2="0" id="graph-x" class="graph-item"></line>
                        <line y1="-165" x1="0" y2="165" x2="0" id="graph-y" class="graph-item"></line>
                        <polygon points="175,0 165,-5 165,5" id="graph-x-arrow" class="graph-item"></polygon>
                        <polygon points="0,-175 -5,-165 5,-165" id="graph-y-arrow" class="graph-item"></polygon>
                        <text x="185" y="0" id="graph-x-label" class="graph-item">X</text>
                        <text x="0" y="-185" id="graph-y-label" class="graph-item">Y</text>
                        <text x="-150" y="20" id="graph-xr-label" class="graph-item">-R</text>
                        <text x="150" y="20" id="graph-rx-label" class="graph-item">R</text>
                        <text y="150" x="20" id="graph-yr-label" class="graph-item">-R</text>
                        <text y="-150" x="20" id="graph-ry-label" class="graph-item">R</text>
                        <ctm:graph-points scope="points"/>
                    </svg>
                </div>
                <div class="input-form">
                    <h2 class="form-title">Параметры точки</h2>
                    <div class="input-group">
                        <label class="input-group-label">Координата X:</label>
                        <div class="checkbox-group">
                            <div class="checkbox-item">
                                <input type="checkbox" id="x-2" name="x" value="-2">
                                <label for="x-2">-2</label>
                            </div>
                            <div class="checkbox-item">
                                <input type="checkbox" id="x-1.5" name="x" value="-1.5">
                                <label for="x-1.5">-1.5</label>
                            </div>
                            <div class="checkbox-item">
                                <input type="checkbox" id="x-1" name="x" value="-1">
                                <label for="x-1">-1</label>
                            </div>
                            <div class="checkbox-item">
                                <input type="checkbox" id="x-0.5" name="x" value="-0.5">
                                <label for="x-0.5">-0.5</label>
                            </div>
                            <div class="checkbox-item">
                                <input type="checkbox" id="x+0" name="x" value="0">
                                <label for="x+0">0</label>
                            </div>
                            <div class="checkbox-item">
                                <input type="checkbox" id="x+0.5" name="x" value="0.5">
                                <label for="x+0.5">0.5</label>
                            </div>
                            <div class="checkbox-item">
                                <input type="checkbox" id="x+1" name="x" value="1">
                                <label for="x+1">1</label>
                            </div>
                            <div class="checkbox-item">
                                <input type="checkbox" id="x+1.5" name="x" value="+1.5">
                                <label for="x+1.5">+1.5</label>
                            </div>
                            <div class="checkbox-item">
                                <input type="checkbox" id="x+2" name="x" value="2">
                                <label for="x+2">2</label>
                            </div>
                        </div>
                    </div>
                    <div class="input-group">
                        <label class="input-group-label" for="y-data">Координата Y:</label>
                        <input type="text" id="y-data" placeholder="Введите значение Y от -5 до 3" required>
                    </div>
                    <div class="input-group">
                        <label class="input-group-label">Значение R:</label>
                        <div class="checkbox-group">
                            <div class="checkbox-item">
                                <input type="checkbox" id="r+1" name="r" value="1">
                                <label for="r+1">1</label>
                            </div>
                            <div class="checkbox-item">
                                <input type="checkbox" id="r+1.5" name="r" value="1.5">
                                <label for="r+1.5">1.5</label>
                            </div>
                            <div class="checkbox-item">
                                <input type="checkbox" id="r+2" name="r" value="2">
                                <label for="r+2">2</label>
                            </div>
                            <div class="checkbox-item">
                                <input type="checkbox" id="r+2.5" name="r" value="2.5">
                                <label for="r+2.5">2.5</label>
                            </div>
                            <div class="checkbox-item">
                                <input type="checkbox" id="r+3" name="r" value="3">
                                <label for="r+3">3</label>
                            </div>
                        </div>
                    </div>
                    <div class="send-form">
                        <p id="problem-label"></p>
                        <button onclick="redirectCalculationPage()">Проверить точку</button>
                    </div>
                </div>
            </div>
        </main>
        <footer>
            <div class="table-container">
                <table>
                    <thead>
                    <tr>
                        <th scope="col">Номер</th>
                        <th scope="col">X</th>
                        <th scope="col">Y</th>
                        <th scope="col">R</th>
                        <th scope="col">Попал</th>
                        <th scope="col">Время выполнения</th>
                        <th scope="col">Когда</th>
                    </tr>
                    </thead>
                    <tbody id="results-tbody">
                        <ctm:table-contant scope="points-history"/>
                    </tbody>
                </table>
            </div>
        </footer>
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/main.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/script.js"></script>
    </body>
</html>
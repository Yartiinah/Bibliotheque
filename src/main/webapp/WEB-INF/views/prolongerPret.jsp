<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="/WEB-INF/views/navbar.jsp" />
<html>
<head>
    <title>Prolonger un prêt</title>
    <style>
        .page-container { max-width: 600px; margin: 40px auto; background: #fff; padding: 30px; border-radius: 10px; box-shadow: 0 2px 8px #ccc; }
        h2 { color: #2c3e50; text-align: center; }
        form { display: flex; flex-direction: column; gap: 15px; }
        label { font-weight: bold; color: #34495e; }
        input { padding: 8px; border: 1px solid #ccc; border-radius: 4px; }
        button { background: #2980b9; color: #fff; border: none; padding: 10px 20px; border-radius: 4px; font-size: 16px; cursor: pointer; transition: background 0.2s; }
        button:hover { background: #1c5d8c; }
    </style>
</head>
<body>
<div class="page-container">
    <h2>Prolonger un prêt</h2>
    <form method="post" action="${pageContext.request.contextPath}/prets/prolonger">
        <label for="pretId">Choisir un prêt à prolonger :</label>
        <select id="pretId" name="pretId" required>
            <%-- Affiche uniquement les prêts en cours de l'adhérent --%>
            <c:forEach var="pret" items="${prets}">
                <option value="${pret.id}">
                    Livre : ${pret.exemplaire.livre.titre} | Exemplaire : ${pret.exemplaire.reference} | Retour prévu : ${pret.dateRetourPrevue}
                </option>
            </c:forEach>
        </select>
        <button type="submit">Prolonger</button>
    </form>
    <c:if test="${not empty message}">
        <p style="color:blue;">${message}</p>
    </c:if>
</div>
</body>
</html>

<!-- filepath: src/main/webapp/WEB-INF/views/rechercherPret.jsp -->
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Rechercher un prêt par exemplaire</title>
    <style>
        .page-container { max-width: 700px; margin: 40px auto; background: #fff; padding: 30px; border-radius: 10px; box-shadow: 0 2px 8px #ccc; }
        h2 { color: #2c3e50; text-align: center; }
        form { display: flex; flex-direction: column; gap: 15px; }
        label { font-weight: bold; color: #34495e; }
        select, input { padding: 8px; border: 1px solid #ccc; border-radius: 4px; }
        button { background: #27ae60; color: #fff; border: none; padding: 10px 20px; border-radius: 4px; font-size: 16px; cursor: pointer; transition: background 0.2s; }
        button:hover { background: #219150; }
    </style>
</head>
<body>
    <jsp:include page="/WEB-INF/views/navbar.jsp" />
    <div class="page-container">
        <h2>Rechercher un prêt</h2>
        <form method="get" action="${pageContext.request.contextPath}/prets/rechercher">
            <label>Exemplaire :</label>
            <select name="referenceExemplaire" required>
                <c:forEach var="ex" items="${exemplaires}">
                    <option value="${ex.reference}">${ex.reference} - ${ex.livre.titre}</option>
                </c:forEach>
            </select>
            <button type="submit">Rechercher</button>
        </form>
        <c:if test="${not empty pret}">
            <h3>Résultat :</h3>
            <ul>
                <li>Adhérent : ${pret.adherent.nom} ${pret.adherent.prenom}</li>
                <li>Date emprunt : ${pret.dateEmprunt}</li>
                <li>Date retour prévue : ${pret.dateRetourPrevue}</li>
                <li>Statut : ${pret.statut}</li>
                <li>
                    <a href="retour?id=${pret.id}">Faire un retour</a>
                </li>
            </ul>
        </c:if>
        <c:if test="${not empty message}">
            <p style="color:red;">${message}</p>
        </c:if>
    </div>
</body>
</html>
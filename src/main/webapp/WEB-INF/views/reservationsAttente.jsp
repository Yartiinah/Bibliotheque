<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="/WEB-INF/views/navbar.jsp" />
<html>
<head>
    <title>Réservations à valider</title>

</head>
<body>
<div class="page-container">
<h2>Réservations en attente</h2>
<table>
<tr>
    <th>Adhérent</th>
    <th>Livre</th>
    <th>Exemplaire</th>
    <th>Date de demande</th>
    <th>Statut</th>
    <th>Actions</th>
</tr>
<c:forEach var="reservation" items="${reservations}">
    <tr>
        <td>${reservation.adherent.nom} ${reservation.adherent.prenom}</td>
        <td>${reservation.exemplaire.livre.titre}</td>
        <td>${reservation.exemplaire.reference}</td>
        <td>${reservation.dateDemande}</td>
        <td>${reservation.statut}</td>
        <td>
            <form action="${pageContext.request.contextPath}/reservation/valider" method="post" style="display:inline;">
                <input type="hidden" name="id" value="${reservation.id}" />
                <input type="hidden" name="action" value="accepter" />
                <button type="submit">Valider</button>
            </form>
            <form action="${pageContext.request.contextPath}/reservation/valider" method="post" style="display:inline;">
                <input type="hidden" name="id" value="${reservation.id}" />
                <input type="hidden" name="action" value="refuser" />
                <button type="submit" style="background:#c0392b;">Refuser</button>
            </form>
            <a href="${pageContext.request.contextPath}/reservation/detail?id=${reservation.id}">Détail</a>
        </td>
    </tr>
</c:forEach>
</table>
<c:if test="${empty reservations}">
    <p>Aucune réservation en attente.</p>
</c:if>
<c:if test="${not empty message}">
    <p style="color:red;">${message}</p>
</c:if>
</div>
</body>
</html>

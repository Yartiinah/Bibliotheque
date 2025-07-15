<%-- src/main/webapp/WEB-INF/jsp/listeMembresEnAttente.jsp --%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Membres en Attente</title>
    <style>
        table, th, td {
            border: 1px solid black;
            border-collapse: collapse;
            padding: 8px;
        }
    </style>
</head>
<body>
    <h1>Membres en Attente de Validation</h1>

    <c:if test="${not empty message}">
        <p style="color: green;">${message}</p>
    </c:if>
    <c:if test="${not empty erreur}">
        <p style="color: red;">${erreur}</p>
    </c:if>

    <c:if test="${empty membresEnAttente}">
        <p>Aucune inscription de membre en attente.</p>
    </c:if>
    <c:if test="${not empty membresEnAttente}">
        <table>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Nom</th>
                    <th>Prénom</th>
                    <th>Email</th>
                    <th>Date Naissance</th>
                    <th>Profil</th>
                    <th>Type Inscription</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="membre" items="${membresEnAttente}">
                    <tr>
                        <td>${membre.id}</td>
                        <td>${membre.nom}</td>
                        <td>${membre.prenom}</td>
                        <td>${membre.email}</td>
                        <td>${membre.dateNaissance}</td>
                        <td>${membre.profil}</td>
                        <td>${membre.typeInscription}</td>
                        <td>
                            <form action="/membres/valider" method="post" style="display:inline;">
                                <input type="hidden" name="id" value="${membre.id}">
                                <input type="submit" value="Valider">
                            </form>
                            <form action="/membres/refuser" method="post" style="display:inline;">
                                <input type="hidden" name="id" value="${membre.id}">
                                <input type="submit" value="Refuser">
                            </form>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </c:if>
    <br/>
    <a href="/dashboard">Retour au Tableau de Bord</a>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Gestion des adhésions</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <div class="container mt-5">
        <h2>Gestion des adhésions</h2>
        <c:if test="${not empty message}">
            <div class="alert alert-success">${message}</div>
        </c:if>
        <c:if test="${not empty erreur}">
            <div class="alert alert-danger">${erreur}</div>
        </c:if>
        <table class="table">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Nom</th>
                    <th>Prénom</th>
                    <th>Email</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="inscription" items="${inscriptions}">
                    <tr>
                        <td>${inscription.id}</td>
                        <td>${inscription.nom}</td>
                        <td>${inscription.prenom}</td>
                        <td>${inscription.email}</td>
                        <td>
                            <form action="<c:url value='/bibliothecaire/accepter-inscription' />" method="post" style="display:inline;">
                                <input type="hidden" name="id" value="${inscription.id}">
                                <input type="number" name="montantCotisation" class="form-control d-inline-block w-auto" placeholder="Montant" required>
                                <button type="submit" class="btn btn-success btn-sm">Accepter</button>
                            </form>
                            <form action="<c:url value='/bibliothecaire/refuser-inscription' />" method="post" style="display:inline;">
                                <input type="hidden" name="id" value="${inscription.id}">
                                <button type="submit" class="btn btn-danger btn-sm">Refuser</button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
        <a href="<c:url value='/dashboard' />" class="btn btn-secondary">Retour</a>
    </div>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Gestion des prolongations</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <div class="container mt-5">
        <h2>Gestion des prolongations</h2>
        <c:if test="${not empty message}">
            <div class="alert alert-success">${message}</div>
        </c:if>
        <c:if test="${not empty erreur}">
            <div class="alert alert-danger">${erreur}</div>
        </c:if>
        <table class="table">
            <thead>
                <tr>
                    <th>ID Prêt</th>
                    <th>Membre</th>
                    <th>Livre</th>
                    <th>Date Retour Prévue</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="prolongation" items="${prolongations}">
                    <tr>
                        <td>${prolongation.pretId}</td>
                        <td>${prolongation.prenom} ${prolongation.nom}</td>
                        <td>${prolongation.titre}</td>
                        <td>${prolongation.dateRetour}</td>
                        <td>
                            <form action="<c:url value='/prolongation/traiter' />" method="post" style="display:inline;">
                                <input type="hidden" name="pretId" value="${prolongation.pretId}">
                                <input type="hidden" name="accepter" value="true">
                                <button type="submit" class="btn btn-success btn-sm">Accepter</button>
                            </form>
                            <form action="<c:url value='/prolongation/traiter' />" method="post" style="display:inline;">
                                <input type="hidden" name="pretId" value="${prolongation.pretId}">
                                <input type="hidden" name="accepter" value="false">
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
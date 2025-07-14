<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Enregistrer un retour</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <div class="container mt-5">
        <h2>Enregistrer un retour</h2>
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
                    <th>Code Exemplaire</th>
                    <th>Date Emprunt</th>
                    <th>Date Retour Prévue</th>
                    <th>Jours de retard</th>
                    <th>Action</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="pret" items="${prets}">
                    <tr>
                        <td>${pret.pret_id}</td>
                        <td>${pret.membre_prenom} ${pret.membre_nom}</td>
                        <td>${pret.livre_titre}</td>
                        <td>${pret.code_exemplaire}</td>
                        <td>${pret.date_emprunt}</td>
                        <td>${pret.date_retour_prevue}</td>
                        <td>${pret.jours_retard}</td>
                        <td>
                            <form action="<c:url value='/retour/enregistrer' />" method="post">
                                <input type="hidden" name="pretId" value="${pret.pret_id}">
                                <button type="submit" class="btn btn-primary btn-sm">Retour</button>
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
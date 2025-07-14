<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Gestion des catégories</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <div class="container mt-5">
        <h2>Gestion des catégories</h2>
        <c:if test="${not empty message}">
            <div class="alert alert-success">${message}</div>
        </c:if>
        <c:if test="${not empty erreur}">
            <div class="alert alert-danger">${erreur}</div>
        </c:if>
        <form action="<c:url value='/bibliothecaire/ajout-categorie' />" method="post" class="mb-4">
            <div class="mb-3">
                <label for="nom" class="form-label">Nouvelle catégorie</label>
                <input type="text" class="form-control" id="nom" name="nom" required>
            </div>
            <button type="submit" class="btn btn-primary">Ajouter</button>
        </form>
        <table class="table">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Nom</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="categorie" items="${categories}">
                    <tr>
                        <td>${categorie.id}</td>
                        <td>${categorie.nom}</td>
                        <td>
                            <form action="<c:url value='/bibliothecaire/modifier-categorie' />" method="post" style="display:inline;">
                                <input type="hidden" name="id" value="${categorie.id}">
                                <input type="text" name="nom" class="form-control d-inline-block w-auto" value="${categorie.nom}" required>
                                <button type="submit" class="btn btn-warning btn-sm">Modifier</button>
                            </form>
                            <form action="<c:url value='/bibliothecaire/supprimer-categorie' />" method="post" style="display:inline;">
                                <input type="hidden" name="id" value="${categorie.id}">
                                <button type="submit" class="btn btn-danger btn-sm">Supprimer</button>
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
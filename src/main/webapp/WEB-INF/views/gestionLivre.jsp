<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Gestion des livres</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <div class="container mt-5">
        <h2>Gestion des livres</h2>
        <c:if test="${not empty message}">
            <div class="alert alert-success">${message}</div>
        </c:if>
        <c:if test="${not empty erreur}">
            <div class="alert alert-danger">${erreur}</div>
        </c:if>
        <form action="<c:url value='/bibliothecaire/ajout-livre' />" method="post" class="mb-4">
            <div class="mb-3">
                <label for="titre" class="form-label">Titre</label>
                <input type="text" class="form-control" id="titre" name="titre" required>
            </div>
            <div class="mb-3">
                <label for="auteur" class="form-label">Auteur</label>
                <input type="text" class="form-control" id="auteur" name="auteur" required>
            </div>
            <div class="mb-3">
                <label for="categorieId" class="form-label">Catégorie</label>
                <select class="form-control" id="categorieId" name="categorieId" required>
                    <c:forEach var="categorie" items="${categories}">
                        <option value="${categorie.id}">${categorie.nom}</option>
                    </c:forEach>
                </select>
            </div>
            <button type="submit" class="btn btn-primary">Ajouter</button>
        </form>
        <table class="table">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Titre</th>
                    <th>Auteur</th>
                    <th>Catégorie</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="livre" items="${livres}">
                    <tr>
                        <td>${livre.id}</td>
                        <td>${livre.titre}</td>
                        <td>${livre.auteur}</td>
                        <td>${livre.categorie_nom}</td>
                        <td>
                            <form action="<c:url value='/bibliothecaire/modifier-livre' />" method="post" style="display:inline;">
                                <input type="hidden" name="id" value="${livre.id}">
                                <input type="text" name="titre" class="form-control d-inline-block w-auto" value="${livre.titre}" required>
                                <input type="text" name="auteur" class="form-control d-inline-block w-auto" value="${livre.auteur}" required>
                                <select name="categorieId" class="form-control d-inline-block w-auto" required>
                                    <c:forEach var="categorie" items="${categories}">
                                        <option value="${categorie.id}" <c:if test="${categorie.nom == livre.categorie_nom}">selected</c:if>>${categorie.nom}</option>
                                    </c:forEach>
                                </select>
                                <button type="submit" class="btn btn-warning btn-sm">Modifier</button>
                            </form>
                            <form action="<c:url value='/bibliothecaire/supprimer-livre' />" method="post" style="display:inline;">
                                <input type="hidden" name="id" value="${livre.id}">
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
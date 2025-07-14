<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Tableau de bord - Bibliothécaire</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <div class="container mt-5">
        <h2>Bienvenue, ${username}</h2>
        <h3>Tableau de bord</h3>
        <div class="list-group">
            <a href="<c:url value='/bibliothecaire/gestion-adherent' />" class="list-group-item list-group-item-action">Gérer les adhésions</a>
            <a href="<c:url value='/bibliothecaire/gestion-categorie' />" class="list-group-item list-group-item-action">Gérer les catégories</a>
            <a href="<c:url value='/bibliothecaire/gestion-livre' />" class="list-group-item list-group-item-action">Gérer les livres</a>
            <a href="<c:url value='/emprunt/form' />" class="list-group-item list-group-item-action">Effectuer un emprunt</a>
            <a href="<c:url value='/reservation/gestion' />" class="list-group-item list-group-item-action">Gérer les réservations</a>
            <a href="<c:url value='/prolongation/gestion' />" class="list-group-item list-group-item-action">Gérer les prolongations</a>
            <a href="<c:url value='/retour/form' />" class="list-group-item list-group-item-action">Enregistrer un retour</a>
            <a href="<c:url value='/logout' />" class="list-group-item list-group-item-action text-danger">Déconnexion</a>
        </div>
    </div>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
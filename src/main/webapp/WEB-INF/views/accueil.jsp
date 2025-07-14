<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Bibliothèque - Accueil</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <div class="container mt-5">
        <h1>Bienvenue à la Bibliothèque</h1>
        <p>Connectez-vous pour accéder à vos services.</p>
        <div class="row">
            <div class="col-md-6">
                <h3>Membres</h3>
                <a href="<c:url value='/membre/login-form' />" class="btn btn-primary">Connexion Membre</a>
                <a href="<c:url value='/membre/inscription-form-en-ligne' />" class="btn btn-secondary">S'inscrire</a>
            </div>
            <div class="col-md-6">
                <h3>Bibliothécaires</h3>
                <a href="<c:url value='/login' />" class="btn btn-primary">Connexion Bibliothécaire</a>
            </div>
        </div>
    </div>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
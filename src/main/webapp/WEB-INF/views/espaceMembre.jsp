<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Espace Membre</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <div class="container mt-5">
        <h2>Bienvenue, ${membre.prenom} ${membre.nom}</h2>
        <c:if test="${not inscriptionValide}">
            <div class="alert alert-warning">Votre inscription a expiré. Veuillez renouveler votre abonnement.</div>
        </c:if>
        <h3>Vos informations</h3>
        <p>Email : ${membre.email}</p>
        <h3>Vos emprunts</h3>
        <c:forEach var="pret" items="${prets}">
            <div class="card mb-3">
                <div class="card-body">
                    <h5 class="card-title">${pret.livre_titre}</h5>
                    <p class="card-text">Date d'emprunt : ${pret.date_emprunt}</p>
                    <p class="card-text">Date de retour prévue : ${pret.date_retour_prevue}</p>
                    <c:if test="${inscriptionValide}">
                        <form action="<c:url value='/prolongation/demander' />" method="post">
                            <input type="hidden" name="pretId" value="${pret.pret_id}">
                            <button type="submit" class="btn btn-primary">Demander une prolongation</button>
                        </form>
                    </c:if>
                </div>
            </div>
        </c:forEach>
        <a href="<c:url value='/reservation/form' />" class="btn btn-primary">Réserver un livre</a>
        <a href="<c:url value='/membre/logout' />" class="btn btn-secondary">Déconnexion</a>
    </div>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
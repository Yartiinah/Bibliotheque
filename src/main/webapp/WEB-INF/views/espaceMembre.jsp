<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Espace Membre</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <div class="container mt-5">
        <h2>Bienvenue, ${membre.prenom} ${membre.nom}</h2>
        <c:if test="${not inscriptionValide}">
            <div class="alert alert-warning">
                Votre inscription a expiré. Veuillez renouveler votre abonnement.
            </div>
        </c:if>
        <h3>Vos informations</h3>
        <p><strong>Nom:</strong> ${membre.nom}</p>
        <p><strong>Prénom:</strong> ${membre.prenom}</p>
        <p><strong>Email:</strong> ${membre.email}</p>
        <h3>Vos emprunts en cours</h3>
        <table class="table">
            <thead>
                <tr>
                    <th>Titre</th>
                    <th>Date d'emprunt</th>
                    <th>Date de retour prévue</th>
                    <th>Action</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="pret" items="${prets}">
                    <tr>
                        <td>${pret.exemplaire.livre.titre}</td>
                        <td>${pret.dateEmprunt}</td>
                        <td>${pret.dateRetourPrevue}</td>
                        <td>
                            <c:if test="${inscriptionValide}">
                                <form action="<c:url value='/prolongation/demander'/>" method="post">
                                    <input type="hidden" name="pretId" value="${pret.id}"/>
                                    <button type="submit" class="btn btn-primary">Demander une prolongation</button>
                                </form>
                            </c:if>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
        <a href="<c:url value='/reservation/form'/>" class="btn btn-primary">Réserver un livre</a>
        <a href="<c:url value='/membre/logout'/>" class="btn btn-secondary">Déconnexion</a>
    </div>
</body>
</html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Modifier un type d'abonnement</title>
</head>
<body>
<h2>Modifier un type d'abonnement</h2>
<form method="post">
    <label>Libellé : <input type="text" name="libelle" value="${typeAbonnement.libelle}" required></label><br>
    <label>Tarif : <input type="number" step="0.01" name="tarif" value="${typeAbonnement.tarif}" required></label><br>
    <label>Quota livre : <input type="number" name="quotaLivre" value="${typeAbonnement.quotaLivre}" required></label><br>
    <label>Durée prêt (jours) : <input type="number" name="dureePretJour" value="${typeAbonnement.dureePretJour}" required></label><br>
    <label>Quota réservation : <input type="number" name="quotaReservation" value="${typeAbonnement.quotaReservation}" required></label><br>
    <label>Quota prolongement : <input type="number" name="quotaProlongement" value="${typeAbonnement.quotaProlongement}" required></label><br>
    <label>Nb jours prolongement : <input type="number" name="nbJourProlongement" value="${typeAbonnement.nbJourProlongement}" required></label><br>
    <button type="submit">Enregistrer</button>
</form>
<c:if test="${not empty message}">
    <p style="color:green;">${message}</p>
</c:if>
</body>
</html>
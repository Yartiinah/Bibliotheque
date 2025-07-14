<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Réserver un livre</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <div class="container mt-5">
        <h2>Réserver un livre</h2>
        <c:if test="${not empty message}">
            <div class="alert alert-success">${message}</div>
        </c:if>
        <c:if test="${not empty erreur}">
            <div class="alert alert-danger">${erreur}</div>
        </c:if>
        <c:if test="${not inscriptionValide}">
            <div class="alert alert-warning">Votre inscription a expiré. Veuillez renouveler votre abonnement.</div>
        </c:if>
        <form action="<c:url value='/reservation/reserver' />" method="post">
            <div class="mb-3">
                <label for="exemplaireId" class="form-label">Livre</label>
                <select class="form-control" id="exemplaireId" name="exemplaireId" required>
                    <c:forEach var="exemplaire" items="${exemplaires}">
                        <option value="${exemplaire.id}">${exemplaire.titre} (${exemplaire.code_exemplaire})</option>
                    </c:forEach>
                </select>
            </div>
            <button type="submit" class="btn btn-primary" <c:if test="${not inscriptionValide}">disabled</c:if>>Réserver</button>
            <a href="<c:url value='/membre/espace' />" class="btn btn-secondary">Retour</a>
        </form>
    </div>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
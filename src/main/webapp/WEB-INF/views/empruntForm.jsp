<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Effectuer un emprunt</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <div class="container mt-5">
        <h2>Effectuer un emprunt</h2>
        <c:if test="${not empty message}">
            <div class="alert alert-success">${message}</div>
        </c:if>
        <c:if test="${not empty erreur}">
            <div class="alert alert-danger">${erreur}</div>
        </c:if>
        <form action="<c:url value='/emprunt/effectuer' />" method="post">
            <div class="mb-3">
                <label for="membreId" class="form-label">Membre</label>
                <input type="number" class="form-control" id="membreId" name="membreId" required>
            </div>
            <div class="mb-3">
                <label for="exemplaireId" class="form-label">Exemplaire</label>
                <select class="form-control" id="exemplaireId" name="exemplaireId" required>
                    <c:forEach var="exemplaire" items="${exemplaires}">
                        <option value="${exemplaire.id}">${exemplaire.titre} (${exemplaire.code_exemplaire})</option>
                    </c:forEach>
                </select>
            </div>
            <div class="mb-3">
                <label for="typePret" class="form-label">Type de prêt</label>
                <select class="form-control" id="typePret" name="typePret" required>
                    <option value="DOMICILE">À domicile</option>
                    <option value="SUR_PLACE">Sur place</option>
                </select>
            </div>
            <button type="submit" class="btn btn-primary">Effectuer</button>
            <a href="<c:url value='/dashboard' />" class="btn btn-secondary">Retour</a>
        </form>
    </div>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
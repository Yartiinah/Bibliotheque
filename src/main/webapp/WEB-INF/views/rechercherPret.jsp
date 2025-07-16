<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Rechercher un prêt</title>
    <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Merriweather:wght@700&family=Open+Sans:wght@400;600&display=swap">
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        body {
            font-family: 'Open Sans', sans-serif;
            background-color: #F5F5F5;
            color: #333333;
        }

        .main-content {
            max-width: 800px;
            margin: 0 auto;
            padding: 20px;
        }

        .page-header {
            background-color: #1B263B;
            padding: 20px;
            border-radius: 8px;
            margin-bottom: 20px;
            text-align: center;
            box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
        }

        .page-title {
            font-family: 'Merriweather', serif;
            font-size: 2rem;
            color: #FFFFFF;
            font-weight: 700;
        }

        .form-container {
            background-color: #FFFFFF;
            border-radius: 8px;
            padding: 20px;
            box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
            margin-bottom: 20px;
        }

        .form-group {
            margin-bottom: 15px;
        }

        .form-group label {
            display: block;
            font-size: 1rem;
            font-weight: 600;
            color: #1B263B;
            margin-bottom: 5px;
        }

        .form-select, .form-control {
            width: 100%;
            padding: 10px;
            border: 1px solid #D1D1D1;
            border-radius: 6px;
            font-size: 1rem;
            background-color: #F9F9F9;
        }

        .form-select:focus, .form-control:focus {
            outline: none;
            border-color: #2A9D8F;
            box-shadow: 0 0 0 3px rgba(42, 157, 143, 0.2);
        }

        .btn {
            background-color: #2A9D8F;
            color: #FFFFFF;
            border: none;
            padding: 10px;
            border-radius: 6px;
            font-size: 1rem;
            font-weight: 600;
            cursor: pointer;
            width: 100%;
            text-align: center;
        }

        .btn:hover {
            background-color: #21867A;
        }

        .result-container {
            background-color: #FFFFFF;
            border-radius: 8px;
            padding: 20px;
            box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
        }

        .result-container h3 {
            font-family: 'Merriweather', serif;
            font-size: 1.4rem;
            color: #1B263B;
            margin-bottom: 15px;
        }

        .result-container p {
            margin-bottom: 10px;
        }

        .btn-action {
            background-color: #2A9D8F;
            color: #FFFFFF;
            padding: 8px 15px;
            border-radius: 6px;
            text-decoration: none;
            font-weight: 600;
            font-size: 0.9rem;
            border: none;
            cursor: pointer;
            margin: 0 5px;
        }

        .btn-action:hover {
            background-color: #21867A;
        }

        .message {
            background-color: #E6F4F1;
            color: #155724;
            padding: 10px;
            border-radius: 6px;
            margin-bottom: 15px;
            text-align: center;
            border-left: 4px solid #2A9D8F;
        }

        .error-message {
            background-color: #F8D7DA;
            color: #721C24;
            padding: 10px;
            border-radius: 6px;
            margin-bottom: 15px;
            text-align: center;
            border-left: 4px solid #E76F51;
        }

        @media (max-width: 768px) {
            .main-content {
                padding: 10px;
            }

            .page-title {
                font-size: 1.6rem;
            }
        }
    </style>
</head>
<body>
    <jsp:include page="/WEB-INF/views/navbar.jsp" />
    
    <div class="main-content">
        <div class="page-header">
            <h1 class="page-title">Rechercher un prêt</h1>
        </div>
        
        <div class="form-container">
            <form method="post" action="${pageContext.request.contextPath}/prets/rechercher">
                <div class="form-group">
                    <label>Exemplaire :</label>
                    <select name="referenceExemplaire" required class="form-select">
                        <option value="">-- Sélectionnez un exemplaire --</option>
                        <c:forEach var="ex" items="${exemplaires}">
                            <option value="${ex.reference}" ${ex.reference == referenceExemplaire ? 'selected' : ''}>
                                ${ex.reference} - ${ex.livre.titre}
                            </option>
                        </c:forEach>
                    </select>
                </div>
                <div class="form-group">
                    <label for="dateEmprunt">Date d'emprunt :</label>
                    <input type="date" id="dateEmprunt" name="dateEmprunt" class="form-control" />
                </div>
                
                <button type="submit" class="btn">Rechercher</button>
            </form>
            
            <c:if test="${not empty message}">
                <div class="message">${message}</div>
            </c:if>
        </div>
        
        <c:if test="${not empty pret}">
            <div class="result-container">
                <h3>Résultat de la recherche :</h3>
                <p><strong>Adhérent :</strong> ${pret.adherent.nom} ${pret.adherent.prenom}</p>
                <p><strong>Exemplaire :</strong> ${pret.exemplaire.reference} - ${pret.exemplaire.livre.titre}</p>
                <p><strong>Date d'emprunt :</strong> ${pret.dateEmprunt}</p>
                <p><strong>Date de retour prévue :</strong> ${pret.dateRetourPrevue}</p>
                <p><strong>Statut :</strong> ${pret.statut}</p>
                
                <h3>Actions disponibles :</h3>
                <c:if test="${pret.statut == 'en_cours'}">
                    <form method="post" action="${pageContext.request.contextPath}/prets/modifier-date-retour-effective">
                        <div class="form-group">
                            <label for="nouvelleDateRetourEffective">Date de retour effective :</label>
                            <input type="date" id="nouvelleDateRetourEffective" name="nouvelleDateRetourEffective" required class="form-control"
                                   value="${pret.dateRetourEffective != null ? pret.dateRetourEffective.toLocalDate() : ''}" />
                            <input type="hidden" name="pretId" value="${pret.id}" />
                        </div>
                        <button type="submit" class="btn-action">Enregistrer le retour</button>
                    </form>
                    <a href="${pageContext.request.contextPath}/prets/retour?id=${pret.id}" class="btn-action">Effectuer le retour</a>
                </c:if>
            </div>
        </c:if>
        
        <c:if test="${not empty error}">
            <div class="error-message">${error}</div>
        </c:if>
    </div>
</body>
</html>

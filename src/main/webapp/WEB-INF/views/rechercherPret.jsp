<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Rechercher un prêt</title>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background-color: #f8f9fa;
            color: #333;
            line-height: 1.6;
        }

        .main-content {
            padding: 2rem;
            max-width: 800px;
            margin: 0 auto;
        }

        .page-header {
            background: linear-gradient(135deg, #ffffff 0%, #f8f9fa 100%);
            border-radius: 15px;
            padding: 2rem;
            margin-bottom: 2rem;
            box-shadow: 0 4px 20px rgba(0, 0, 0, 0.08);
            border: 1px solid #e3e6f0;
        }

        .page-title {
            font-size: 2rem;
            font-weight: 700;
            color: #2196F3;
            margin-bottom: 0.5rem;
            text-align: center;
        }

        .form-container {
            background: white;
            border-radius: 15px;
            padding: 2rem;
            box-shadow: 0 4px 20px rgba(0, 0, 0, 0.08);
            border: 1px solid #e3e6f0;
        }

        .form-group {
            margin-bottom: 1.5rem;
        }

        .form-group label {
            display: block;
            margin-bottom: 0.5rem;
            font-weight: 600;
            color: #495057;
        }

        .form-select {
            width: 100%;
            padding: 0.8rem 1rem;
            border: 1px solid #ddd;
            border-radius: 8px;
            font-size: 1rem;
            background-color: white;
            transition: all 0.3s;
        }

        .form-select:focus {
            border-color: #2196F3;
            box-shadow: 0 0 0 3px rgba(33, 150, 243, 0.2);
            outline: none;
        }

        .btn-search {
            background: linear-gradient(135deg, #2196F3 0%, #1976D2 100%);
            color: white;
            border: none;
            padding: 1rem 2rem;
            border-radius: 8px;
            font-weight: 600;
            cursor: pointer;
            transition: all 0.3s ease;
            font-size: 1rem;
            width: 100%;
            margin-top: 1rem;
        }

        .btn-search:hover {
            transform: translateY(-2px);
            box-shadow: 0 6px 20px rgba(33, 150, 243, 0.3);
            background: linear-gradient(135deg, #1976D2 0%, #1565C0 100%);
        }

        .result-container {
            background: white;
            border-radius: 15px;
            padding: 2rem;
            margin-top: 2rem;
            box-shadow: 0 4px 20px rgba(0, 0, 0, 0.08);
            border: 1px solid #e3e6f0;
        }

        .result-title {
            font-size: 1.3rem;
            font-weight: 600;
            color: #2196F3;
            margin-bottom: 1.5rem;
        }

        .result-list {
            list-style: none;
        }

        .result-list li {
            padding: 0.8rem 0;
            border-bottom: 1px solid #f1f3f4;
        }

        .result-list li:last-child {
            border-bottom: none;
        }

        .btn-return {
            background: linear-gradient(135deg, #4caf50 0%, #2e7d32 100%);
            color: white;
            border: none;
            padding: 0.8rem 1.5rem;
            border-radius: 8px;
            font-weight: 600;
            cursor: pointer;
            transition: all 0.3s ease;
            font-size: 0.9rem;
            text-decoration: none;
            display: inline-block;
            margin-top: 1rem;
        }

        .btn-return:hover {
            transform: translateY(-2px);
            box-shadow: 0 6px 20px rgba(76, 175, 80, 0.3);
            background: linear-gradient(135deg, #2e7d32 0%, #1b5e20 100%);
        }

        .error-message {
            color: #e53935;
            background-color: #ffebee;
            padding: 1rem;
            border-radius: 8px;
            margin-top: 1.5rem;
            font-weight: 600;
            text-align: center;
            border-left: 4px solid #e53935;
        }

        @media (max-width: 768px) {
            .main-content {
                padding: 1rem;
            }
            
            .page-title {
                font-size: 1.8rem;
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
            <form method="get" action="${pageContext.request.contextPath}/prets/rechercher">
                <div class="form-group">
                    <label>Exemplaire :</label>
                    <select name="referenceExemplaire" required class="form-select">
                        <c:forEach var="ex" items="${exemplaires}">
                            <option value="${ex.reference}">${ex.reference} - ${ex.livre.titre}</option>
                        </c:forEach>
                    </select>
                </div>
                
                <button type="submit" class="btn-search">Rechercher</button>
            </form>
            
            <c:if test="${not empty pret}">
                <div class="result-container">
                    <h3 class="result-title">Résultat :</h3>
                    <ul class="result-list">
                        <li><strong>Adhérent :</strong> ${pret.adherent.nom} ${pret.adherent.prenom}</li>
                        <li><strong>Date emprunt :</strong> ${pret.dateEmprunt}</li>
                        <li><strong>Date retour prévue :</strong> ${pret.dateRetourPrevue}</li>
                        <li><strong>Statut :</strong> ${pret.statut}</li>
                    </ul>
                    <a href="retour?id=${pret.id}" class="btn-return">Faire un retour</a>
                </div>
            </c:if>
            
            <c:if test="${not empty message}">
                <div class="error-message">${message}</div>
            </c:if>
        </div>
    </div>
</body>
</html>
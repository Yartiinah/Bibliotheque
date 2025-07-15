<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Enregistrer un prêt</title>
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

        .btn-submit {
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

        .btn-submit:hover {
            transform: translateY(-2px);
            box-shadow: 0 6px 20px rgba(33, 150, 243, 0.3);
            background: linear-gradient(135deg, #1976D2 0%, #1565C0 100%);
        }

        .message {
            text-align: center;
            padding: 1rem;
            margin-top: 1.5rem;
            border-radius: 8px;
            background-color: #e3f2fd;
            color: #1976D2;
            font-weight: 500;
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
            <h1 class="page-title">Enregistrer un prêt</h1>
        </div>
        
        <div class="form-container">
            <form method="post" action="nouveau">
                <div class="form-group">
                    <label>Adhérent :</label>
                    <select name="adherentId" required class="form-select">
                        <c:forEach var="adherent" items="${adherents}">
                            <option value="${adherent.id}" ${adherent.id == adherentId ? 'selected' : ''}>${adherent.nom} ${adherent.prenom} (${adherent.email})</option>
                        </c:forEach>
                    </select>
                </div>
                
                <div class="form-group">
                    <label>Exemplaire :</label>
                    <select name="referenceExemplaire" required class="form-select">
                        <c:forEach var="ex" items="${exemplaires}">
                            <option value="${ex.reference}" ${ex.reference == referenceExemplaire ? 'selected' : ''}>${ex.reference} (${ex.livre.titre})</option>
                        </c:forEach>
                    </select>
                </div>
                
                <c:if test="${not empty dateEmprunt}">
                    <input type="hidden" name="dateEmprunt" value="${dateEmprunt}" />
                </c:if>
                
                <c:if test="${not empty reservationId}">
                    <input type="hidden" name="reservationId" value="${reservationId}" />
                </c:if>
                
                <div class="form-group">
                    <label>Type de prêt :</label>
                    <select name="typePret" required class="form-select">
                        <option value="emporte">Emporté</option>
                        <option value="sur_place">Sur place</option>
                    </select>
                </div>

                <div class="form-group">
                    <label>Date du pret :</label>
                    <input type="date" name="dateEmprunt" required="true" class="form-control"/>
                </div>
                
                <button type="submit" class="btn-submit">Enregistrer le prêt</button>
            </form>
            
            <c:if test="${not empty message}">
                <div class="message">${message}</div>
            </c:if>
        </div>
    </div>
</body>
</html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Mes prêts</title>
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
            max-width: 1200px;
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

        .table-container {
            background: white;
            border-radius: 15px;
            box-shadow: 0 4px 20px rgba(0, 0, 0, 0.08);
            overflow: hidden;
            border: 1px solid #e3e6f0;
        }

        .table-header {
            background: linear-gradient(135deg, #2196F3 0%, #1976D2 100%);
            color: white;
            padding: 1.5rem;
            text-align: center;
        }

        .table-header h2 {
            font-size: 1.3rem;
            font-weight: 600;
        }

        .my-loans-table {
            width: 100%;
            border-collapse: collapse;
            background: white;
        }

        .my-loans-table th {
            background: linear-gradient(135deg, #f8f9fa 0%, #e9ecef 100%);
            padding: 1rem;
            text-align: left;
            font-weight: 600;
            color: #495057;
            font-size: 0.9rem;
            text-transform: uppercase;
            letter-spacing: 0.05em;
            border-bottom: 2px solid #2196F3;
        }

        .my-loans-table td {
            padding: 1rem;
            border-bottom: 1px solid #f1f3f4;
            vertical-align: middle;
        }

        .my-loans-table tr {
            transition: all 0.3s ease;
        }

        .my-loans-table tr:hover {
            background: rgba(33, 150, 243, 0.05);
        }

        .btn-extend {
            background: linear-gradient(135deg, #4caf50 0%, #2e7d32 100%);
            color: white;
            border: none;
            padding: 0.6rem 1.2rem;
            border-radius: 6px;
            font-weight: 500;
            cursor: pointer;
            transition: all 0.3s ease;
            font-size: 0.85rem;
            text-decoration: none;
            display: inline-block;
        }

        .btn-extend:hover {
            transform: translateY(-2px);
            box-shadow: 0 4px 12px rgba(76, 175, 80, 0.3);
            background: linear-gradient(135deg, #2e7d32 0%, #1b5e20 100%);
        }

        .empty-state {
            text-align: center;
            padding: 3rem;
            color: #6c757d;
        }

        .empty-state h3 {
            font-size: 1.5rem;
            margin-bottom: 1rem;
            color: #495057;
        }

        @media (max-width: 768px) {
            .main-content {
                padding: 1rem;
            }
            
            .my-loans-table {
                display: block;
                overflow-x: auto;
            }
        }
    </style>
</head>
<body>
    <jsp:include page="/WEB-INF/views/navbar.jsp" />
    
    <div class="main-content">
        <div class="page-header">
            <h1 class="page-title">Mes prêts</h1>
        </div>
        
        <div class="table-container">
            <div class="table-header">
                <h2>Historique de mes emprunts</h2>
            </div>
            
            <table class="my-loans-table">
                <thead>
                    <tr>
                        <th>Livre</th>
                        <th>Exemplaire</th>
                        <th>Date d'emprunt</th>
                        <th>Date retour prévue</th>
                        <th>Date retour effective</th>
                        <th>Statut</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="pret" items="${prets}">
                        <tr>
                            <td>${pret.exemplaire.livre.titre}</td>
                            <td>${pret.exemplaire.reference}</td>
                            <td>${pret.dateEmprunt}</td>
                            <td>${pret.dateRetourPrevue}</td>
                            <td>${pret.dateRetourEffective != null ? pret.dateRetourEffective : '-'}</td>
                            <td>
                                <span class="status-badge status-${pret.statut.toLowerCase().replace(' ', '_')}">
                                    ${pret.statut}
                                </span>
                            </td>
                            <td>
                                <c:if test="${pret.statut == 'en_cours'}">
                                    <a href="${pageContext.request.contextPath}/prets/prolonger?id=${pret.id}" class="btn-extend">Prolonger</a>
                                </c:if>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
            
            <c:if test="${empty prets}">
                <div class="empty-state">
                    <h3>Aucun prêt en cours ou passé</h3>
                    <p>Vous n'avez aucun prêt à afficher</p>
                </div>
            </c:if>
        </div>
    </div>
</body>
</html>
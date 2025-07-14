<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Réservations à valider</title>
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

        .reservations-table {
            width: 100%;
            border-collapse: collapse;
            background: white;
        }

        .reservations-table th {
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

        .reservations-table td {
            padding: 1rem;
            border-bottom: 1px solid #f1f3f4;
            vertical-align: middle;
        }

        .reservations-table tr {
            transition: all 0.3s ease;
        }

        .reservations-table tr:hover {
            background: rgba(33, 150, 243, 0.05);
        }

        .btn-validate {
            background: linear-gradient(135deg, #4caf50 0%, #2e7d32 100%);
            color: white;
            border: none;
            padding: 0.6rem 1.2rem;
            border-radius: 6px;
            font-weight: 500;
            cursor: pointer;
            transition: all 0.3s ease;
            font-size: 0.85rem;
        }

        .btn-reject {
            background: linear-gradient(135deg, #e53935 0%, #c62828 100%);
            color: white;
            border: none;
            padding: 0.6rem 1.2rem;
            border-radius: 6px;
            font-weight: 500;
            cursor: pointer;
            transition: all 0.3s ease;
            font-size: 0.85rem;
        }

        .btn-detail {
            background: linear-gradient(135deg, #2196F3 0%, #1976D2 100%);
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

        .btn-validate:hover {
            transform: translateY(-2px);
            box-shadow: 0 4px 12px rgba(76, 175, 80, 0.3);
            background: linear-gradient(135deg, #2e7d32 0%, #1b5e20 100%);
        }

        .btn-reject:hover {
            transform: translateY(-2px);
            box-shadow: 0 4px 12px rgba(229, 57, 53, 0.3);
            background: linear-gradient(135deg, #c62828 0%, #b71c1c 100%);
        }

        .btn-detail:hover {
            transform: translateY(-2px);
            box-shadow: 0 4px 12px rgba(33, 150, 243, 0.3);
            background: linear-gradient(135deg, #1976D2 0%, #1565C0 100%);
        }

        .action-form {
            display: inline-block;
            margin: 0 0.2rem;
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
            
            .reservations-table {
                display: block;
                overflow-x: auto;
            }
            
            .action-form {
                display: block;
                margin: 0.2rem 0;
            }
        }
    </style>
</head>
<body>
    <jsp:include page="/WEB-INF/views/navbar.jsp" />
    
    <div class="main-content">
        <div class="page-header">
            <h1 class="page-title">Réservations en attente</h1>
        </div>
        
        <div class="table-container">
            <div class="table-header">
                <h2>Liste des réservations à valider</h2>
            </div>
            
            <table class="reservations-table">
                <thead>
                    <tr>
                        <th>Adhérent</th>
                        <th>Livre</th>
                        <th>Exemplaire</th>
                        <th>Date de demande</th>
                        <th>Statut</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="reservation" items="${reservations}">
                        <tr>
                            <td>${reservation.adherent.nom} ${reservation.adherent.prenom}</td>
                            <td>${reservation.exemplaire.livre.titre}</td>
                            <td>${reservation.exemplaire.reference}</td>
                            <td>${reservation.dateDemande}</td>
                            <td>${reservation.statut}</td>
                            <td>
                                <form class="action-form" action="${pageContext.request.contextPath}/reservation/valider" method="post">
                                    <input type="hidden" name="id" value="${reservation.id}" />
                                    <input type="hidden" name="action" value="accepter" />
                                    <button type="submit" class="btn-validate">Valider</button>
                                </form>
                                <form class="action-form" action="${pageContext.request.contextPath}/reservation/valider" method="post">
                                    <input type="hidden" name="id" value="${reservation.id}" />
                                    <input type="hidden" name="action" value="refuser" />
                                    <button type="submit" class="btn-reject">Refuser</button>
                                </form>
                                <a href="${pageContext.request.contextPath}/reservation/detail?id=${reservation.id}" class="btn-detail">Détail</a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
            
            <c:if test="${empty reservations}">
                <div class="empty-state">
                    <h3>Aucune réservation en attente</h3>
                    <p>Toutes les réservations ont été traitées</p>
                </div>
            </c:if>
            
            <c:if test="${not empty message}">
                <div class="error-message">${message}</div>
            </c:if>
        </div>
    </div>
</body>
</html>
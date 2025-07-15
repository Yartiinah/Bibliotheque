<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Mes Réservations</title>
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

        .status-badge {
            padding: 0.5rem 1rem;
            border-radius: 20px;
            font-size: 0.8rem;
            font-weight: 600;
            text-transform: uppercase;
            display: inline-block;
        }

        .status-en_attente {
            background: linear-gradient(135deg, #fff9c4 0%, #fff59d 100%);
            color: #f57f17;
        }

        .status-confirmee {
            background: linear-gradient(135deg, #c8e6c9 0%, #a5d6a7 100%);
            color: #2e7d32;
        }

        .status-annulee {
            background: linear-gradient(135deg, #ef9a9a 0%, #e57373 100%);
            color: #c62828;
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
            
            .reservations-table {
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
            <h1 class="page-title">Mes réservations</h1>
        </div>
        
        <div class="table-container">
            <div class="table-header">
                <h2>Historique de mes réservations</h2>
            </div>
            
            <table class="reservations-table">
                <thead>
                    <tr>
                        <th>Livre</th>
                        <th>Exemplaire</th>
                        <th>Date de demande</th>
                        <th>Statut</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="reservation" items="${reservations}">
                        <tr>
                            <td>${reservation.exemplaire.livre.titre}</td>
                            <td>${reservation.exemplaire.reference}</td>
                            <td>${reservation.dateDemande}</td>
                            <td>
                                <span class="status-badge status-${reservation.statut.toLowerCase().replace(' ', '_')}">
                                    ${reservation.statut}
                                </span>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
            
            <c:if test="${empty reservations}">
                <div class="empty-state">
                    <h3>Aucune réservation en cours ou passée</h3>
                    <p>Vous n'avez aucune réservation à afficher</p>
                </div>
            </c:if>
        </div>
    </div>
</body>
</html>
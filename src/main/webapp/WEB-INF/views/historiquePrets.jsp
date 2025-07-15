<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Historique des prêts</title>
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

        .history-table {
            width: 100%;
            border-collapse: collapse;
            background: white;
        }

        .history-table th {
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

        .history-table td {
            padding: 1rem;
            border-bottom: 1px solid #f1f3f4;
            vertical-align: middle;
        }

        .history-table tr {
            transition: all 0.3s ease;
        }

        .history-table tr:hover {
            background: rgba(33, 150, 243, 0.05);
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
            
            .history-table {
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
            <h1 class="page-title">Historique des prêts</h1>
        </div>
        
        <div class="table-container">
            <div class="table-header">
                <h2>Journal des actions</h2>
            </div>
            
            <table class="history-table">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>ID Prêt</th>
                        <th>Action</th>
                        <th>Date action</th>
                        <th>Commentaire</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="hist" items="${historiques}">
                        <tr>
                            <td>${hist.id}</td>
                            <td>${hist.pret.id}</td>
                            <td>${hist.action}</td>
                            <td>${hist.dateAction}</td>
                            <td>${hist.commentaire}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
            
            <c:if test="${empty historiques}">
                <div class="empty-state">
                    <h3>Aucun historique trouvé</h3>
                    <p>Aucune action enregistrée dans le journal</p>
                </div>
            </c:if>
        </div>
    </div>
</body>
</html>
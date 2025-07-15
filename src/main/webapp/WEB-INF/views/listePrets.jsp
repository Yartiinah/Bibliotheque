
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Tous les prêts</title>
    <style>
        :root {
            --primary-color: #5D4037;
            --secondary-color: #8D6E63;
            --accent-color: #D7CCC8;
            --text-color: #3E2723;
            --light-text: #EFEBE9;
            --success-color: #2E7D32;
            --warning-color: #FF8F00;
            --error-color: #C62828;
            --info-color: #0277BD;
            --font-main: 'Open Sans', sans-serif;
            --font-heading: 'Merriweather', serif;
        }

        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        body {
            font-family: var(--font-main);
            color: var(--text-color);
            background-color: #FAF9F7;
            line-height: 1.6;
            display: flex;
            min-height: 100vh;
        }

        .sidebar {
            width: 280px;
            background-color: var(--primary-color);
            color: var(--light-text);
            padding: 20px 0;
            height: 100vh;
            position: fixed;
            box-shadow: 2px 0 10px rgba(0,0,0,0.1);
        }

        .sidebar-header {
            padding: 0 20px 20px;
            border-bottom: 1px solid var(--secondary-color);
            margin-bottom: 20px;
        }

        .sidebar-header h1 {
            font-family: var(--font-heading);
            font-size: 1.8rem;
            margin-bottom: 5px;
        }

        .user-role {
            display: inline-block;
            background-color: var(--secondary-color);
            padding: 3px 10px;
            border-radius: 15px;
            font-size: 0.8rem;
        }

        .nav-section {
            margin-bottom: 25px;
        }

        .nav-section-title {
            padding: 5px 20px;
            font-weight: 600;
            color: var(--accent-color);
            font-size: 0.9rem;
            text-transform: uppercase;
            letter-spacing: 1px;
        }

        .nav-link {
            display: block;
            padding: 10px 20px;
            color: var(--light-text);
            text-decoration: none;
            transition: all 0.3s;
        }

        .nav-link:hover {
            background-color: var(--secondary-color);
            padding-left: 25px;
        }

        .nav-link.active {
            background-color: var(--secondary-color);
            border-left: 4px solid var(--accent-color);
        }

        .logout-section {
            margin-top: 30px;
            padding-top: 20px;
            border-top: 1px solid var(--secondary-color);
        }

        .logout-link {
            color: var(--accent-color);
        }

        .main-content {
            margin-left: 280px;
            padding: 30px;
            width: calc(100% - 280px);
        }

        .page-header {
            margin-bottom: 30px;
        }

        .page-title {
            font-family: var(--font-heading);
            color: var(--primary-color);
            font-size: 2rem;
            margin-bottom: 10px;
        }

        .page-subtitle {
            color: var(--secondary-color);
            font-size: 1.1rem;
        }

        .table-container {
            background-color: white;
            border-radius: 8px;
            padding: 30px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.05);
            overflow-x: auto;
        }

        .table-header {
            margin-bottom: 20px;
        }

        .table-header h2 {
            font-family: var(--font-heading);
            color: var(--primary-color);
            font-size: 1.5rem;
        }

        table {
            width: 100%;
            border-collapse: collapse;
        }

        th, td {
            padding: 12px 15px;
            text-align: left;
            border-bottom: 1px solid #eee;
        }

        th {
            background-color: var(--accent-color);
            color: var(--text-color);
            font-weight: 600;
        }

        tr:hover {
            background-color: #f9f9f9;
        }

        .status-badge {
            display: inline-block;
            padding: 4px 10px;
            border-radius: 15px;
            font-size: 0.8rem;
            font-weight: 600;
        }

        .status-en_cours {
            background-color: #E3F2FD;
            color: var(--info-color);
        }

        .status-termine {
            background-color: #E8F5E9;
            color: var(--success-color);
        }

        .status-en_retard {
            background-color: #FFEBEE;
            color: var(--error-color);
        }

        .status-en_attente {
            background-color: #FFF8E1;
            color: var(--warning-color);
        }

        .form-group {
            margin-bottom: 20px;
        }

        .form-group label {
            display: block;
            margin-bottom: 8px;
            font-weight: 600;
            color: var(--primary-color);
        }

        .form-date {
            width: 100%;
            padding: 10px 15px;
            border: 1px solid #ddd;
            border-radius: 4px;
            font-family: var(--font-main);
            font-size: 1rem;
            transition: border 0.3s;
        }

        .form-date:focus {
            border-color: var(--secondary-color);
            outline: none;
        }

        .btn-submit, .btn-extend {
            background-color: var(--info-color);
            color: white;
            border: none;
            padding: 8px 15px;
            border-radius: 4px;
            cursor: pointer;
            transition: all 0.3s;
        }

        .btn-submit:hover, .btn-extend:hover {
            background-color: #01579B;
        }

        .extend-form {
            display: none;
            margin-top: 10px;
            padding: 10px;
            background-color: #f9f9f9;
            border-radius: 4px;
        }

        .extend-form.active {
            display: block;
        }

        .success-message {
            background-color: #E8F5E9;
            color: var(--success-color);
            padding: 15px;
            border-radius: 4px;
            margin: 20px 0;
            font-weight: 500;
        }

        .error-message {
            background-color: #FFEBEE;
            color: var(--error-color);
            padding: 15px;
            border-radius: 4px;
            margin: 20px 0;
            font-weight: 500;
        }

        .empty-state {
            text-align: center;
            padding: 40px 20px;
            color: var(--secondary-color);
        }

        .empty-state h3 {
            font-family: var(--font-heading);
            margin-bottom: 10px;
        }

        @media (max-width: 768px) {
            .sidebar {
                width: 100%;
                height: auto;
                position: relative;
            }
            
            .main-content {
                margin-left: 0;
                width: 100%;
            }
            
            .table-container {
                overflow-x: auto;
            }
        }
    </style>
    <script>
        document.addEventListener('DOMContentLoaded', function() {
            function showExtendForm(pretId) {
                document.querySelectorAll('.extend-form').forEach(form => form.classList.remove('active'));
                const form = document.getElementById('extend-form-' + pretId);
                if (form) {
                    form.classList.add('active');
                    const inputDate = form.querySelector('.form-date');
                    const today = new Date().toISOString().split('T')[0];
                    inputDate.setAttribute('min', today);
                }
            }

            document.querySelectorAll('.btn-extend').forEach(button => {
                button.addEventListener('click', function() {
                    const pretId = this.getAttribute('data-pret-id');
                    showExtendForm(pretId);
                });
            });
        });
    </script>
</head>
<body>
    <jsp:include page="/WEB-INF/views/navbar.jsp" />
    
    <div class="main-content">
        <div class="page-header">
            <h1 class="page-title">Tous les prêts</h1>
        </div>
        
        <div class="table-container">
            <div class="table-header">
                <h2>Liste complète des prêts</h2>
            </div>
            
            <c:if test="${not empty message}">
                <div class="success-message"><c:out value="${message}" /></div>
            </c:if>
            <c:if test="${not empty error}">
                <div class="error-message"><c:out value="${error}" /></div>
            </c:if>
            
            <table class="loans-table">
                <thead>
                    <tr>
                        <th>Adhérent</th>
                        <th>Livre</th>
                        <th>Exemplaire</th>
                        <th>Date d'emprunt</th>
                        <th>Date retour prévue</th>
                        <th>Date retour effective</th>
                        <th>Statut</th>
                        <th>Prolongement</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="pret" items="${prets}">
                        <tr>
                            <td><c:out value="${pret.adherent.nom} ${pret.adherent.prenom}" /></td>
                            <td><c:out value="${pret.exemplaire.livre.titre}" /></td>
                            <td><c:out value="${pret.exemplaire.reference}" /></td>
                            <td><c:out value="${pret.dateEmprunt}" /></td>
                            <td><c:out value="${pret.dateRetourPrevue}" /></td>
                            <td><c:out value="${pret.dateRetourEffective != null ? pret.dateRetourEffective : '-'}" /></td>
                            <td>
                                <span class="status-badge status-${pret.statut.toLowerCase().replace(' ', '_')}">
                                    <c:out value="${pret.statut}" />
                                </span>
                            </td>
                            <td>
                                <c:out value="${pret.nbProlongements}" />
                                <c:if test="${pret.statut == 'en_cours' && sessionScope.user != null && sessionScope.user.role == 'BIBLIOTHECAIRE'}">
                                    <button class="btn-extend" data-pret-id="${pret.id}">Prolonger</button>
                                    <div id="extend-form-${pret.id}" class="extend-form">
                                        <form method="post" action="${pageContext.request.contextPath}/prets/prolonger">
                                            <input type="hidden" name="pretId" value="${pret.id}">
                                            <input type="hidden" name="redirectTo" value="listePrets">
                                            <div class="form-group">
                                                <label for="nouvelleDateRetourPrevue-${pret.id}">Nouvelle date de retour prévue :</label>
                                                <input type="date" id="nouvelleDateRetourPrevue-${pret.id}" name="nouvelleDateRetourPrevue" class="form-date" required>
                                            </div>
                                            <button type="submit" class="btn-submit">Confirmer</button>
                                        </form>
                                    </div>
                                </c:if>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
            
            <c:if test="${empty prets}">
                <div class="empty-state">
                    <h3>Aucun prêt trouvé</h3>
                    <p>Aucun prêt n'a été enregistré dans le système</p>
                </div>
            </c:if>
        </div>
    </div>
</body>
</html>

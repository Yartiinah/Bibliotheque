
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/WEB-INF/views/navbar.jsp" />
<html>
<head>
    <title>Prolonger un prêt</title>
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

        body {
            font-family: var(--font-main);
            color: var(--text-color);
            background-color: #FAF9F7;
            line-height: 1.6;
            display: flex;
            min-height: 100vh;
        }

        .main-content {
            margin-left: 280px;
            padding: 30px;
            width: calc(100% - 280px);
        }

        .page-container {
            max-width: 800px;
            margin: 0 auto;
            padding: 30px;
            background-color: white;
            border-radius: 8px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
        }

        .page-container h2 {
            font-family: var(--font-heading);
            color: var(--primary-color);
            margin-bottom: 20px;
            text-align: center;
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

        .form-control, .form-select, .form-date {
            width: 100%;
            padding: 10px 15px;
            border: 1px solid #ddd;
            border-radius: 4px;
            font-family: var(--font-main);
            font-size: 1rem;
            transition: border 0.3s;
        }

        .form-control:focus, .form-select:focus, .form-date:focus {
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
            font-size: 1rem;
            transition: all 0.3s;
        }

        .btn-submit:hover, .btn-extend:hover {
            background-color: #01579B;
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

        table {
            width: 100%;
            border-collapse: collapse;
            margin-bottom: 20px;
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

        @media (max-width: 768px) {
            .main-content {
                margin-left: 0;
                width: 100%;
            }

            table {
                display: block;
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
<div class="main-content">
    <div class="page-container">
        <h2>Prolonger un prêt</h2>
        <c:if test="${not empty message}">
            <div class="success-message"><c:out value="${message}" /></div>
        </c:if>
        <c:if test="${not empty error}">
            <div class="error-message"><c:out value="${error}" /></div>
        </c:if>
        <c:if test="${empty prets}">
            <div class="empty-state">
                <h3>Aucun prêt en cours</h3>
                <p>Vous n'avez aucun prêt en cours à prolonger.</p>
            </div>
        </c:if>
        <c:if test="${not empty prets}">
            <table>
                <thead>
                    <tr>
                        <th>Livre</th>
                        <th>Exemplaire</th>
                        <th>Date de retour prévue</th>
                        <th>Prolongement</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="pret" items="${prets}">
                        <tr>
                            <td><c:out value="${pret.exemplaire.livre.titre}" /></td>
                            <td><c:out value="${pret.exemplaire.reference}" /></td>
                            <td><c:out value="${pret.dateRetourPrevue}" /></td>
                            <td>
                                <c:out value="${pret.nbProlongements}" />
                                <button class="btn-extend" data-pret-id="${pret.id}">Prolonger</button>
                                <div id="extend-form-${pret.id}" class="extend-form">
                                    <form method="post" action="${pageContext.request.contextPath}/prets/prolonger">
                                        <input type="hidden" name="pretId" value="${pret.id}">
                                        <input type="hidden" name="redirectTo" value="prolongerPret">
                                        <div class="form-group">
                                            <label for="nouvelleDateRetourPrevue-${pret.id}">Nouvelle date de retour prévue :</label>
                                            <input type="date" id="nouvelleDateRetourPrevue-${pret.id}" name="nouvelleDateRetourPrevue" class="form-date" required>
                                        </div>
                                        <button type="submit" class="btn-submit">Confirmer</button>
                                    </form>
                                </div>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:if>
    </div>
</div>
</body>
</html>

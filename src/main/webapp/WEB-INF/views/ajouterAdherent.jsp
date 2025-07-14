<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Ajouter un adhérent</title>
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

        .form-control {
            width: 100%;
            padding: 0.8rem 1rem;
            border: 1px solid #ddd;
            border-radius: 8px;
            font-size: 1rem;
            transition: all 0.3s;
        }

        .form-control:focus {
            border-color: #2196F3;
            box-shadow: 0 0 0 3px rgba(33, 150, 243, 0.2);
            outline: none;
        }

        .form-select {
            width: 100%;
            padding: 0.8rem 1rem;
            border: 1px solid #ddd;
            border-radius: 8px;
            font-size: 1rem;
            background-color: white;
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
            <h1 class="page-title">Ajouter un nouvel adhérent</h1>
        </div>
        
        <div class="form-container">
            <form:form method="post" modelAttribute="adherent">
                <div class="form-group">
                    <label>Nom :</label>
                    <form:input path="nom" required="true" class="form-control"/>
                </div>
                
                <div class="form-group">
                    <label>Prénom :</label>
                    <form:input path="prenom" required="true" class="form-control"/>
                </div>
                
                <div class="form-group">
                    <label>Email :</label>
                    <form:input path="email" type="email" required="true" class="form-control"/>
                </div>
                
                <div class="form-group">
                    <label>Type d'abonnement :</label>
                    <form:select path="typeAbonnement.id" class="form-select">
                        <form:options items="${typesAbonnement}" itemValue="id" itemLabel="libelle"/>
                    </form:select>
                </div>
                
                <div class="form-group">
                    <label>Adresse :</label>
                    <form:input path="adresse" required="true" class="form-control"/>
                </div>
                
                <div class="form-group">
                    <label>Date d'inscription :</label>
                    <input type="date" name="dateInscription" required="true" class="form-control"/>
                </div>
                
                <div class="form-group">
                    <label>Date d'expiration :</label>
                    <input type="date" name="dateExpiration" required="true" class="form-control"/>
                </div>
                
                <button type="submit" class="btn-submit">Ajouter l'adhérent</button>
            </form:form>
            
            <c:if test="${not empty message}">
                <div class="message">${message}</div>
            </c:if>
        </div>
    </div>
</body>
</html>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Créer un utilisateur</title>
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
            max-width: 600px;
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

        .form-control, .form-select {
            width: 100%;
            padding: 0.8rem 1rem;
            border: 1px solid #ddd;
            border-radius: 8px;
            font-size: 1rem;
            transition: all 0.3s;
        }

        .form-control:focus, .form-select:focus {
            border-color: #2196F3;
            box-shadow: 0 0 0 3px rgba(33, 150, 243, 0.2);
            outline: none;
        }

        .btn-submit {
            background: linear-gradient(135deg, #27ae60 0%, #219150 100%);
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
            box-shadow: 0 6px 20px rgba(39, 174, 96, 0.3);
            background: linear-gradient(135deg, #219150 0%, #1d8045 100%);
        }

        .success-message {
            text-align: center;
            padding: 1rem;
            margin-top: 1.5rem;
            border-radius: 8px;
            background-color: #e8f5e9;
            color: #2e7d32;
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
            <h1 class="page-title">Créer un utilisateur</h1>
        </div>
        
        <div class="form-container">
            <form method="post" action="${pageContext.request.contextPath}/utilisateur/creer">
                <div class="form-group">
                    <label for="adherentId">Adhérent :</label>
                    <select id="adherentId" name="adherentId" required class="form-select">
                        <% 
                            java.util.List adherents = (java.util.List) request.getAttribute("adherents");
                            if (adherents != null) {
                                for (Object obj : adherents) {
                                    model.Adherent adherent = (model.Adherent) obj;
                        %>
                        <option value="<%= adherent.getId() %>"><%= adherent.getNom() %> <%= adherent.getPrenom() %></option>
                        <%      }
                            }
                        %>
                    </select>
                </div>
                
                <div class="form-group">
                    <label for="username">Nom d'utilisateur :</label>
                    <input type="text" id="username" name="username" required class="form-control">
                </div>
                
                <div class="form-group">
                    <label for="password">Mot de passe :</label>
                    <input type="password" id="password" name="password" required class="form-control">
                </div>
                
                <div class="form-group">
                    <label for="role">Rôle :</label>
                    <select id="role" name="role" required class="form-select">
                        <option value="ADHERENT">Adhérent</option>
                        <option value="BIBLIOTHECAIRE">Bibliothécaire</option>
                    </select>
                </div>
                
                <button type="submit" class="btn-submit">Créer l'utilisateur</button>
            </form>
            
            <c:if test="${not empty message}">
                <div class="success-message">${message}</div>
            </c:if>
        </div>
    </div>
</body>
</html>
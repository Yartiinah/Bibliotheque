<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Accueil Adhérent</title>
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

        .welcome-card {
            background: linear-gradient(135deg, #ffffff 0%, #f8f9fa 100%);
            border-radius: 15px;
            padding: 3rem 2rem;
            margin-bottom: 2rem;
            box-shadow: 0 4px 20px rgba(0, 0, 0, 0.08);
            border: 1px solid #e3e6f0;
            text-align: center;
        }

        .welcome-title {
            font-size: 2.5rem;
            font-weight: 700;
            color: #2196F3;
            margin-bottom: 1rem;
        }

        .welcome-message {
            color: #6c757d;
            font-size: 1.2rem;
            margin-bottom: 2rem;
        }

        .quick-links {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
            gap: 1rem;
            margin-top: 2rem;
        }

        .quick-link {
            background: white;
            border-radius: 10px;
            padding: 1.5rem;
            text-align: center;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.05);
            transition: all 0.3s ease;
            border: 1px solid #e3e6f0;
            text-decoration: none;
            color: #2196F3;
            font-weight: 600;
        }

        .quick-link:hover {
            transform: translateY(-5px);
            box-shadow: 0 6px 20px rgba(33, 150, 243, 0.15);
            color: #1976D2;
        }

        .quick-link i {
            font-size: 2rem;
            margin-bottom: 1rem;
            display: block;
            color: #2196F3;
        }

        @media (max-width: 768px) {
            .main-content {
                padding: 1rem;
            }
            
            .welcome-title {
                font-size: 2rem;
            }
            
            .quick-links {
                grid-template-columns: 1fr;
            }
        }
    </style>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
</head>
<body>
    <jsp:include page="/WEB-INF/views/navbar.jsp" />
    
    <div class="main-content">
        <div class="welcome-card">
            <h1 class="welcome-title">Bienvenue sur votre espace adhérent</h1>
            <p class="welcome-message">Accédez à vos prêts, réservations et informations personnelles</p>
            
            <div class="quick-links">
                <a href="${pageContext.request.contextPath}/prets/mes-prets" class="quick-link">
                    <i class="fas fa-book-open"></i>
                    Mes prêts
                </a>
                <a href="${pageContext.request.contextPath}/livres/liste" class="quick-link">
                    <i class="fas fa-search"></i>
                    Chercher un livre
                </a>
                <a href="${pageContext.request.contextPath}/mon-compte" class="quick-link">
                    <i class="fas fa-user-cog"></i>
                    Mon compte
                </a>
            </div>
        </div>
    </div>
</body>
</html>
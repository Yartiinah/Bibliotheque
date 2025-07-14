<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Connexion</title>
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
            display: flex;
            justify-content: center;
            align-items: center;
            min-height: 100vh;
            background: linear-gradient(135deg, #e3f2fd 0%, #bbdefb 100%);
        }

        .login-container {
            width: 100%;
            max-width: 450px;
            margin: 2rem;
        }

        .login-card {
            background: white;
            border-radius: 15px;
            padding: 2.5rem;
            box-shadow: 0 10px 30px rgba(0, 0, 0, 0.1);
            border: 1px solid #e3e6f0;
            text-align: center;
        }

        .login-logo {
            margin-bottom: 1.5rem;
            color: #2196F3;
            font-size: 2.5rem;
        }

        .login-title {
            font-size: 1.8rem;
            font-weight: 700;
            color: #2196F3;
            margin-bottom: 1.5rem;
        }

        .login-form {
            display: flex;
            flex-direction: column;
            gap: 1.5rem;
        }

        .form-group {
            text-align: left;
        }

        .form-group label {
            display: block;
            margin-bottom: 0.5rem;
            font-weight: 600;
            color: #495057;
            font-size: 0.95rem;
        }

        .form-control {
            width: 100%;
            padding: 0.9rem 1.2rem;
            border: 1px solid #e0e0e0;
            border-radius: 8px;
            font-size: 1rem;
            transition: all 0.3s;
        }

        .form-control:focus {
            border-color: #2196F3;
            box-shadow: 0 0 0 3px rgba(33, 150, 243, 0.2);
            outline: none;
        }

        .btn-login {
            background: linear-gradient(135deg, #2196F3 0%, #1976D2 100%);
            color: white;
            border: none;
            padding: 1rem;
            border-radius: 8px;
            font-weight: 600;
            cursor: pointer;
            transition: all 0.3s ease;
            font-size: 1rem;
            margin-top: 0.5rem;
        }

        .btn-login:hover {
            transform: translateY(-2px);
            box-shadow: 0 6px 20px rgba(33, 150, 243, 0.3);
            background: linear-gradient(135deg, #1976D2 0%, #1565C0 100%);
        }

        .error-message {
            color: #e53935;
            background-color: #ffebee;
            padding: 1rem;
            border-radius: 8px;
            margin-top: 1.5rem;
            font-weight: 600;
            border-left: 4px solid #e53935;
        }

        @media (max-width: 480px) {
            .login-card {
                padding: 1.5rem;
            }
            
            .login-title {
                font-size: 1.5rem;
            }
        }
    </style>
</head>
<body>
    <div class="login-container">
        <div class="login-card">
            <div class="login-logo">
                <i class="fas fa-book-open"></i>
            </div>
            <h1 class="login-title">Connexion</h1>
            
            <form method="post" action="${pageContext.request.contextPath}/login/verifierConnexion" class="login-form">
                <div class="form-group">
                    <label for="username">Nom d'utilisateur</label>
                    <input type="text" id="username" name="username" required class="form-control">
                </div>
                
                <div class="form-group">
                    <label for="password">Mot de passe</label>
                    <input type="password" id="password" name="password" required class="form-control">
                </div>
                
                <button type="submit" class="btn-login">Se connecter</button>
            </form>
            
            <c:if test="${not empty error}">
                <div class="error-message">${error}</div>
            </c:if>
        </div>
    </div>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
</body>
</html>
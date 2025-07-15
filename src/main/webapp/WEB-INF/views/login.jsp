<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Connexion</title>
    <style>
        .page-container { max-width: 400px; margin: 60px auto; background: #fff; padding: 30px; border-radius: 10px; box-shadow: 0 2px 8px #ccc; }
        h2 { color: #2c3e50; text-align: center; }
        form { display: flex; flex-direction: column; gap: 15px; }
        label { font-weight: bold; color: #34495e; }
        input { padding: 8px; border: 1px solid #ccc; border-radius: 4px; }
        button { background: #2980b9; color: #fff; border: none; padding: 10px 20px; border-radius: 4px; font-size: 16px; cursor: pointer; transition: background 0.2s; }
        button:hover { background: #1c5d8c; }
    </style>
</head>
<body>
    <div class="page-container">
        <h2>Connexion</h2>
        <form method="post" action="${pageContext.request.contextPath}/login/verifierConnexion">
            <label for="username">Nom d'utilisateur :</label>
            <input type="text" id="username" name="username" required><br>
            <label for="password">Mot de passe :</label>
            <input type="password" id="password" name="password" required><br>
            <button type="submit">Se connecter</button>
        </form>
        <c:if test="${not empty error}">
            <p style="color:red;">${error}</p>
        </c:if>
    </div>
</body>
</html>

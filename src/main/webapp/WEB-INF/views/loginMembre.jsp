<%-- src/main/webapp/WEB-INF/jsp/loginMembre.jsp --%>
<!DOCTYPE html>
<html>
<head>
    <title>Connexion Membre</title>
</head>
<body>
    <h1>Connexion Membre</h1>

    <c:if test="${not empty message}">
        <p style="color: green;">${message}</p>
    </c:if>
    <c:if test="${not empty erreur}">
        <p style="color: red;">${erreur}</p>
    </c:if>

    <form action="/login-membre" method="post"> <%-- Cette URL devrait être gérée par AccController pour l'authentification --%>
        <label for="username">Email:</label><br> <%-- Ou nom d'utilisateur, selon votre DB --%>
        <input type="text" id="username" name="username" required><br><br>

        <label for="password">Mot de passe:</label><br>
        <input type="password" id="password" name="password" required><br><br>

        <input type="submit" value="Se connecter">
    </form>
    <br/>
    <a href="/inscription-form-en-ligne">Pas encore inscrit ?</a>
    <br/>
    <a href="/accueil">Retour à l'accueil</a>
</body>
</html>
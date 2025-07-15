<%-- src/main/webapp/WEB-INF/jsp/inscriptionForm.jsp --%>
<!DOCTYPE html>
<html>
<head>
    <title>Inscription Membre</title>
</head>
<body>
    <h1>Inscription en Ligne</h1>

    <c:if test="${not empty message}">
        <p style="color: green;">${message}</p>
    </c:if>
    <c:if test="${not empty erreur}">
        <p style="color: red;">${erreur}</p>
    </c:if>

    <form action="/inscription-en-ligne" method="post">
        <label for="nom">Nom:</label><br>
        <input type="text" id="nom" name="nom" required><br><br>

        <label for="prenom">Prénom:</label><br>
        <input type="text" id="prenom" name="prenom" required><br><br>

        <label for="email">Email:</label><br>
        <input type="email" id="email" name="email" required><br><br>

        <label for="adresse">Adresse:</label><br>
        <textarea id="adresse" name="adresse" required></textarea><br><br>

        <label for="dateNaissance">Date de Naissance:</label><br>
        <input type="date" id="dateNaissance" name="dateNaissance" required><br><br>

        <label for="profil">Profil:</label><br>
        <select id="profil" name="profil">
            <option value="ENFANT">Enfant</option>
            <option value="ADULTE">Adulte</option>
            <option value="ETUDIANT">Étudiant</option>
        </select><br><br>

        <input type="submit" value="S'inscrire">
    </form>
    <br/>
    <a href="/accueil">Retour à l'accueil</a>
</body>
</html>
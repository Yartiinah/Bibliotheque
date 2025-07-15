<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Liste des livres</title>
    <style>
/* Variables de couleur et police */
:root {
    --primary-color: #5D4037; /* Marron bibliothèque */
    --secondary-color: #8D6E63; /* Marron clair */
    --accent-color: #D7CCC8; /* Beige */
    --text-color: #3E2723; /* Marron foncé */
    --light-text: #EFEBE9; /* Beige clair */
    --success-color: #2E7D32; /* Vert */
    --warning-color: #FF8F00; /* Orange */
    --error-color: #C62828; /* Rouge */
    --info-color: #0277BD; /* Bleu */
    --font-main: 'Open Sans', sans-serif;
    --font-heading: 'Merriweather', serif;
}

/* Reset et base */
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

/* Sidebar/Navbar */
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

/* Contenu principal */
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

/* Cartes */
.welcome-card {
    background-color: white;
    border-radius: 8px;
    padding: 30px;
    box-shadow: 0 2px 10px rgba(0,0,0,0.05);
    margin-bottom: 30px;
}

.welcome-title {
    font-family: var(--font-heading);
    color: var(--primary-color);
    margin-bottom: 15px;
}

.welcome-message {
    color: var(--secondary-color);
    margin-bottom: 20px;
}

.quick-links {
    display: flex;
    gap: 15px;
    flex-wrap: wrap;
}

.quick-link {
    display: flex;
    align-items: center;
    gap: 8px;
    padding: 12px 20px;
    background-color: var(--primary-color);
    color: white;
    border-radius: 6px;
    text-decoration: none;
    transition: all 0.3s;
}

.quick-link:hover {
    background-color: var(--secondary-color);
    transform: translateY(-2px);
}

.quick-link i {
    font-size: 1.2rem;
}

/* Formulaires */
.form-container {
    background-color: white;
    border-radius: 8px;
    padding: 30px;
    box-shadow: 0 2px 10px rgba(0,0,0,0.05);
    max-width: 800px;
    margin: 0 auto;
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

.btn-submit {
    background-color: var(--primary-color);
    color: white;
    border: none;
    padding: 12px 25px;
    border-radius: 4px;
    cursor: pointer;
    font-size: 1rem;
    transition: all 0.3s;
}

.btn-submit:hover {
    background-color: var(--secondary-color);
}

/* Tables */
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

/* Badges */
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

.restriction-badge {
    display: inline-block;
    padding: 4px 10px;
    border-radius: 15px;
    font-size: 0.8rem;
    font-weight: 600;
}

.restriction-none {
    background-color: #E8F5E9;
    color: var(--success-color);
}

.restriction-adult {
    background-color: #F3E5F5;
    color: #6A1B9A;
}

/* Messages */
.message, .success-message, .error-message {
    padding: 15px;
    border-radius: 4px;
    margin: 20px 0;
    font-weight: 500;
}

.message {
    background-color: #E3F2FD;
    color: var(--info-color);
}

.success-message {
    background-color: #E8F5E9;
    color: var(--success-color);
}

.error-message {
    background-color: #FFEBEE;
    color: var(--error-color);
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

/* Boutons spéciaux */
.btn-reserve {
    background-color: var(--primary-color);
    color: white;
    border: none;
    padding: 8px 15px;
    border-radius: 4px;
    cursor: pointer;
    transition: all 0.3s;
}

.btn-reserve:hover {
    background-color: var(--secondary-color);
}

.btn-extend {
    display: inline-block;
    padding: 8px 15px;
    background-color: var(--info-color);
    color: white;
    border-radius: 4px;
    text-decoration: none;
    transition: all 0.3s;
}

.btn-extend:hover {
    background-color: #01579B;
}

/* Animation de chargement */
.loading {
    display: inline-block;
    width: 16px;
    height: 16px;
    border: 2px solid rgba(255,255,255,0.3);
    border-radius: 50%;
    border-top-color: white;
    animation: spin 1s ease-in-out infinite;
}

@keyframes spin {
    to { transform: rotate(360deg); }
}

/* Page de connexion */
.page-container {
    max-width: 400px;
    margin: 50px auto;
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

.page-container label {
    display: block;
    margin-bottom: 8px;
    font-weight: 600;
    color: var(--primary-color);
}

.page-container input[type="text"],
.page-container input[type="password"],
.page-container input[type="date"],
.page-container select {
    width: 100%;
    padding: 10px 15px;
    margin-bottom: 20px;
    border: 1px solid #ddd;
    border-radius: 4px;
    font-family: var(--font-main);
}

.page-container button {
    width: 100%;
    padding: 12px;
    background-color: var(--primary-color);
    color: white;
    border: none;
    border-radius: 4px;
    cursor: pointer;
    font-size: 1rem;
    transition: all 0.3s;
}

.page-container button:hover {
    background-color: var(--secondary-color);
}

/* Responsive */
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
    
    .quick-links {
        flex-direction: column;
    }
}
</style>
</head>
<body>
    <jsp:include page="/WEB-INF/views/navbar.jsp" />
    
    <div class="main-content">
        <div class="page-header">
            <h1 class="page-title">Liste des livres</h1>
            <p class="page-subtitle">Découvrez notre collection de livres disponibles</p>
        </div>
        
        <div class="table-container">
            <div class="table-header">
                <h2>Catalogue des livres</h2>
            </div>
            
            <table class="books-table">
                <thead>
                    <tr>
                        <th>Titre</th>
                        <th>Auteur</th>
                        <th>Categorie</th>
                        <th>ISBN</th>
                        <th>Restriction</th>
                        <th>Action</th>
                    </tr>
                </thead>
                <tbody>
                <% 
                    java.util.List livres = (java.util.List) request.getAttribute("livres");
                    if (livres != null && !livres.isEmpty()) {
                        for (Object obj : livres) {
                            model.Livre livre = (model.Livre) obj;
                %>
                    <tr>
                        <td>
                            <div class="book-title"><%= livre.getTitre() %></div>
                        </td>
                        <td>
                            <div class="book-author"><%= livre.getAuteur() %></div>
                        </td>
                        <td>
                            <% if (livre.getCategorie() != null) { %>
                                <span class="book-category"><%= livre.getCategorie().getNom() %></span>
                            <% } %>
                        </td>
                        <td>
                            <div class="book-isbn"><%= livre.getIsbn() %></div>
                        </td>
                        <td>
                            <% String restriction = livre.getRestriction();
                               if (restriction == null || restriction.isEmpty() || restriction.equalsIgnoreCase("aucune")) { %>
                                <span class="restriction-badge restriction-none">Aucune</span>
                            <% } else { %>
                                <span class="restriction-badge restriction-adult"><%= restriction %></span>
                            <% } %>
                        </td>
                        <td>
                            <% model.Utilisateur user = (model.Utilisateur) session.getAttribute("user");
                               if (user != null && !user.getRole().equals("BIBLIOTHECAIRE")) { %>
                                <form action="${pageContext.request.contextPath}/reservation/choisir-exemplaire" method="get" class="reserve-form">
                                    <input type="hidden" name="livreId" value="<%= livre.getId() %>" />
                                    <button type="submit" class="btn-reserve">
                                        Réserver
                                    </button>
                                </form>
                            <% } %>
                        </td>
                    </tr>
                <%      }
                    } else { %>
                    <tr>
                        <td colspan="6" class="empty-state">
                            <h3>📚 Aucun livre disponible</h3>
                            <p>La collection est actuellement vide.</p>
                        </td>
                    </tr>
                <% } %>
                </tbody>
            </table>
        </div>
    </div>

    
</body>
</html>
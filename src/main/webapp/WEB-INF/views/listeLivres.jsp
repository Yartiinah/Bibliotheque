<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Liste des livres</title>
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
            max-width: 1200px;
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
            font-size: 2.5rem;
            font-weight: 700;
            color: #2196F3;
            margin-bottom: 0.5rem;
            text-align: center;
        }

        .page-subtitle {
            color: #6c757d;
            font-size: 1.1rem;
            text-align: center;
        }

        .table-container {
            background: white;
            border-radius: 15px;
            box-shadow: 0 4px 20px rgba(0, 0, 0, 0.08);
            overflow: hidden;
            border: 1px solid #e3e6f0;
        }

        .table-header {
            background: linear-gradient(135deg, #2196F3 0%, #1976D2 100%);
            color: white;
            padding: 1.5rem;
            text-align: center;
        }

        .table-header h2 {
            font-size: 1.3rem;
            font-weight: 600;
        }

        .books-table {
            width: 100%;
            border-collapse: collapse;
            background: white;
        }

        .books-table th {
            background: linear-gradient(135deg, #f8f9fa 0%, #e9ecef 100%);
            padding: 1rem;
            text-align: left;
            font-weight: 600;
            color: #495057;
            font-size: 0.9rem;
            text-transform: uppercase;
            letter-spacing: 0.05em;
            border-bottom: 2px solid #2196F3;
        }

        .books-table td {
            padding: 1rem;
            border-bottom: 1px solid #f1f3f4;
            vertical-align: middle;
        }

        .books-table tr {
            transition: all 0.3s ease;
        }

        .books-table tr:hover {
            background: rgba(33, 150, 243, 0.05);
            transform: translateY(-2px);
            box-shadow: 0 4px 15px rgba(33, 150, 243, 0.1);
        }

        .books-table tr:last-child td {
            border-bottom: none;
        }

        .book-title {
            font-weight: 600;
            color: #2196F3;
            font-size: 1.1rem;
        }

        .book-author {
            color: #6c757d;
            font-style: italic;
        }

        .book-category {
            background: linear-gradient(135deg, #e3f2fd 0%, #bbdefb 100%);
            color: #1976D2;
            padding: 0.3rem 0.8rem;
            border-radius: 20px;
            font-size: 0.85rem;
            font-weight: 500;
            display: inline-block;
        }

        .book-isbn {
            font-family: 'Courier New', monospace;
            color: #495057;
            font-size: 0.9rem;
        }

        .restriction-badge {
            padding: 0.4rem 0.8rem;
            border-radius: 20px;
            font-size: 0.8rem;
            font-weight: 600;
            text-transform: uppercase;
            letter-spacing: 0.05em;
        }

        .restriction-none {
            background: linear-gradient(135deg, #c8e6c9 0%, #a5d6a7 100%);
            color: #2e7d32;
        }

        .restriction-adult {
            background: linear-gradient(135deg, #ffcdd2 0%, #ef9a9a 100%);
            color: #c62828;
        }

        .btn-reserve {
            background: linear-gradient(135deg, #2196F3 0%, #1976D2 100%);
            color: white;
            border: none;
            padding: 0.7rem 1.5rem;
            border-radius: 25px;
            font-weight: 600;
            cursor: pointer;
            transition: all 0.3s ease;
            text-transform: uppercase;
            letter-spacing: 0.05em;
            font-size: 0.85rem;
        }

        .btn-reserve:hover {
            transform: translateY(-2px);
            box-shadow: 0 6px 20px rgba(33, 150, 243, 0.3);
            background: linear-gradient(135deg, #1976D2 0%, #1565C0 100%);
        }

        .btn-reserve:active {
            transform: translateY(0);
        }

        .btn-reserve:disabled {
            background: #ccc;
            cursor: not-allowed;
            transform: none;
            box-shadow: none;
        }

        .reserve-form {
            margin: 0;
            display: inline-block;
        }

        .empty-state {
            text-align: center;
            padding: 3rem;
            color: #6c757d;
        }

        .empty-state h3 {
            font-size: 1.5rem;
            margin-bottom: 1rem;
            color: #495057;
        }

        /* Responsive */
        @media (max-width: 768px) {
            .main-content {
                padding: 1rem;
            }
            
            .page-title {
                font-size: 2rem;
            }
            
            .table-container {
                overflow-x: auto;
            }
            
            .books-table {
                min-width: 800px;
            }
        }

        /* Loading animation */
        .loading {
            display: inline-block;
            width: 16px;
            height: 16px;
            border: 2px solid rgba(255, 255, 255, 0.3);
            border-radius: 50%;
            border-top-color: #fff;
            animation: spin 1s ease-in-out infinite;
        }

        @keyframes spin {
            to { transform: rotate(360deg); }
        }

        /* Animation d'entrée */
        .books-table tr {
            animation: fadeInUp 0.5s ease forwards;
            opacity: 0;
            transform: translateY(20px);
        }

        .books-table tr:nth-child(1) { animation-delay: 0.1s; }
        .books-table tr:nth-child(2) { animation-delay: 0.2s; }
        .books-table tr:nth-child(3) { animation-delay: 0.3s; }
        .books-table tr:nth-child(4) { animation-delay: 0.4s; }
        .books-table tr:nth-child(5) { animation-delay: 0.5s; }

        @keyframes fadeInUp {
            to {
                opacity: 1;
                transform: translateY(0);
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

    <script>
        document.addEventListener('DOMContentLoaded', function() {
            // Animation pour les boutons de réservation
            const reserveButtons = document.querySelectorAll('.btn-reserve');
            
            reserveButtons.forEach(button => {
                button.addEventListener('click', function(e) {
                    // Animation de chargement
                    const originalText = this.textContent;
                    this.innerHTML = '<span class="loading"></span>';
                    this.disabled = true;
                    
                    // Simulation d'un délai (à supprimer en production)
                    setTimeout(() => {
                        this.textContent = originalText;
                        this.disabled = false;
                    }, 1000);
                });
            });
            
            // Effet de survol amélioré pour les lignes
            const tableRows = document.querySelectorAll('.books-table tbody tr');
            tableRows.forEach(row => {
                row.addEventListener('mouseenter', function() {
                    this.style.borderLeft = '4px solid #2196F3';
                });
                
                row.addEventListener('mouseleave', function() {
                    this.style.borderLeft = 'none';
                });
            });
        });
    </script>
</body>
</html>
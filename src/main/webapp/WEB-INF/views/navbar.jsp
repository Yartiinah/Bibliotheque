<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<style>
    * {
        margin: 0;
        padding: 0;
        box-sizing: border-box;
    }

    body {
        font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
        background-color: #f8f9fa;
        margin-left: 280px;
    }

    .sidebar {
        position: fixed;
        left: 0;
        top: 0;
        width: 280px;
        height: 100vh;
        background: linear-gradient(180deg, #ffffff 0%, #f8f9fa 100%);
        box-shadow: 2px 0 20px rgba(0, 0, 0, 0.1);
        z-index: 1000;
        overflow-y: auto;
        border-right: 1px solid #e3e6f0;
    }

    .sidebar-header {
        padding: 2rem 1.5rem;
        background: linear-gradient(135deg, #2196F3 0%, #1976D2 100%);
        color: white;
        text-align: center;
        box-shadow: 0 2px 10px rgba(33, 150, 243, 0.3);
    }

    .sidebar-header h1 {
        font-size: 1.4rem;
        font-weight: 600;
        margin-bottom: 0.5rem;
        letter-spacing: 0.5px;
    }

    .sidebar-header .user-role {
        font-size: 0.9rem;
        opacity: 0.9;
        background: rgba(255, 255, 255, 0.2);
        padding: 0.3rem 0.8rem;
        border-radius: 20px;
        display: inline-block;
        margin-top: 0.5rem;
    }

    .sidebar-nav {
        padding: 1.5rem 0;
    }

    .nav-section {
        margin-bottom: 1.5rem;
    }

    .nav-section-title {
        font-size: 0.75rem;
        font-weight: 600;
        text-transform: uppercase;
        letter-spacing: 0.1em;
        color: #6c757d;
        margin-bottom: 0.8rem;
        padding: 0 1.5rem;
    }

    .nav-link {
        display: block;
        padding: 0.8rem 1.5rem;
        color: #495057;
        text-decoration: none;
        transition: all 0.3s ease;
        border-left: 3px solid transparent;
        position: relative;
        font-weight: 500;
    }

    .nav-link:hover {
        background: rgba(33, 150, 243, 0.08);
        color: #2196F3;
        border-left-color: #2196F3;
        transform: translateX(5px);
    }

    .nav-link.active {
        background: rgba(33, 150, 243, 0.1);
        color: #2196F3;
        border-left-color: #2196F3;
        font-weight: 600;
    }

    .nav-link::before {
        content: '';
        position: absolute;
        left: 0;
        top: 0;
        height: 100%;
        width: 0;
        background: linear-gradient(135deg, #2196F3 0%, #1976D2 100%);
        transition: width 0.3s ease;
        z-index: -1;
    }

    .nav-link:hover::before {
        width: 100%;
    }

    .nav-link:hover {
        color: white;
        transform: translateX(0);
    }

    .logout-section {
        margin-top: 2rem;
        padding-top: 1.5rem;
        border-top: 1px solid #e3e6f0;
    }

    .logout-link {
        color: #dc3545 !important;
        font-weight: 600;
    }

    .logout-link:hover {
        background: rgba(220, 53, 69, 0.1) !important;
        border-left-color: #dc3545 !important;
    }

    .logout-link:hover::before {
        background: linear-gradient(135deg, #dc3545 0%, #c82333 100%) !important;
    }

    /* Responsive */
    @media (max-width: 768px) {
        .sidebar {
            width: 100%;
            height: auto;
            position: relative;
        }
        
        body {
            margin-left: 0;
        }
    }

    /* Scrollbar */
    .sidebar::-webkit-scrollbar {
        width: 6px;
    }

    .sidebar::-webkit-scrollbar-track {
        background: #f1f1f1;
    }

    .sidebar::-webkit-scrollbar-thumb {
        background: #2196F3;
        border-radius: 3px;
    }

    .sidebar::-webkit-scrollbar-thumb:hover {
        background: #1976D2;
    }
</style>

<%-- Sidebar dynamique selon le rôle utilisateur --%>
<nav class="sidebar">
    <div class="sidebar-header">
        <h1>Bibliothèque</h1>
        <c:choose>
            <c:when test="${sessionScope.user.role eq 'BIBLIOTHECAIRE'}">
                <span class="user-role">Bibliothecaire</span>
            </c:when>
            <c:when test="${sessionScope.user.role eq 'ADHERENT'}">
                <span class="user-role">Adhérent</span>
            </c:when>
            <c:otherwise>
                <span class="user-role">Visiteur</span>
            </c:otherwise>
        </c:choose>
    </div>
    
    <div class="sidebar-nav">
        <c:choose>
            <c:when test="${sessionScope.user.role eq 'BIBLIOTHECAIRE'}">
                <div class="nav-section">
                    <div class="nav-section-title">Gestion des livres</div>
                    <a href="${pageContext.request.contextPath}/livres/liste" class="nav-link">
                        Liste des livres
                    </a>
                </div>
                
                <div class="nav-section">
                    <div class="nav-section-title">Adhérents</div>
                    <a href="${pageContext.request.contextPath}/adherents" class="nav-link">
                        Liste des adhérents
                    </a>
                    <a href="${pageContext.request.contextPath}/adherents/ajouter" class="nav-link">
                        Ajouter un adhérent
                    </a>
                </div>
                
                <div class="nav-section">
                    <div class="nav-section-title">Gestion des prêts</div>
                    <a href="${pageContext.request.contextPath}/prets/liste" class="nav-link">
                        Liste des prêts
                    </a>
                    <a href="${pageContext.request.contextPath}/prets/nouveau" class="nav-link">
                        Nouveau prêt
                    </a>
                    <a href="${pageContext.request.contextPath}/prets/rechercher" class="nav-link">
                        Rechercher un prêt
                    </a>
                </div>
                
                <div class="nav-section">
                    <div class="nav-section-title">Administration</div>
                    <a href="${pageContext.request.contextPath}/utilisateur/creer" class="nav-link">
                        Ajouter un utilisateur
                    </a>
                    <a href="${pageContext.request.contextPath}/paiements" class="nav-link">
                        Paiements
                    </a>
                    <a href="${pageContext.request.contextPath}/reservation/attente" class="nav-link">
                        Réservations à valider
                    </a>
                </div>
            </c:when>
            
            <c:when test="${sessionScope.user.role eq 'ADHERENT'}">
                <div class="nav-section">
                    <div class="nav-section-title">Bibliothèque</div>
                    <a href="${pageContext.request.contextPath}/livres/liste" class="nav-link">
                        Livres disponibles
                    </a>
                </div>
                
                <div class="nav-section">
                    <div class="nav-section-title">Mes activités</div>
                    <a href="${pageContext.request.contextPath}/prets/mes-prets" class="nav-link">
                        Mes prêts
                    </a>
                    <a href="${pageContext.request.contextPath}/reservation/mesReservations" class="nav-link">
                        Mes réservations
                    </a>
                    <a href="${pageContext.request.contextPath}/mon-compte" class="nav-link">
                         Mon compte
                    </a>
                </div>
            </c:when>
            
            <c:otherwise>
                <div class="nav-section">
                    <div class="nav-section-title">Accès</div>
                    <a href="${pageContext.request.contextPath}/login" class="nav-link">
                        Connexion
                    </a>
                </div>
            </c:otherwise>
        </c:choose>
        
        <c:if test="${sessionScope.user != null}">
            <div class="logout-section">
                <a href="${pageContext.request.contextPath}/login/logout" class="nav-link logout-link">
                    Déconnexion
                </a>
            </div>
        </c:if>
    </div>
</nav>

<script>
    // Marquer le lien actuel comme actif
    document.addEventListener('DOMContentLoaded', function() {
        const currentPath = window.location.pathname;
        const navLinks = document.querySelectorAll('.nav-link');
        
        navLinks.forEach(link => {
            if (link.getAttribute('href') && currentPath.includes(link.getAttribute('href').split('/').pop())) {
                link.classList.add('active');
            }
        });
    });
</script>
-- ================================
-- 📁 CATEGORIES ET LIVRES (PostgreSQL)
-- ================================

-- Supprimer les tables si elles existent pour permettre la recréation
DROP TABLE IF EXISTS reservation CASCADE;
DROP TABLE IF EXISTS prolongation CASCADE;
DROP TABLE IF EXISTS pret CASCADE;
DROP TABLE IF EXISTS penalite CASCADE;
DROP TABLE IF EXISTS cotisation CASCADE;
DROP TABLE IF EXISTS adhesion CASCADE;
DROP TABLE IF EXISTS membre CASCADE;
DROP TABLE IF EXISTS exemplaire CASCADE;
DROP TABLE IF EXISTS livre CASCADE;
DROP TABLE IF EXISTS categorie CASCADE;
DROP TABLE IF EXISTS "user" CASCADE; -- "user" est un mot-clé réservé en PostgreSQL, donc il est mis entre guillemets


-- Supprimer les types personnalisés si ils existent
DROP TYPE IF EXISTS EXEMPLAIRE_STATUT;
DROP TYPE IF EXISTS MEMBRE_PROFIL;
DROP TYPE IF EXISTS MEMBRE_TYPE_INSCRIPTION;
DROP TYPE IF EXISTS MEMBRE_STATUT_VALIDATION;
DROP TYPE IF EXISTS PRET_TYPE;
DROP TYPE IF EXISTS PROLONGATION_STATUT;
DROP TYPE IF EXISTS RESERVATION_STATUT;


-- Création des types ENUM personnalisés pour PostgreSQL
CREATE TYPE EXEMPLAIRE_STATUT AS ENUM ('DISPONIBLE', 'EMPRUNTE', 'RESERVE');
CREATE TYPE MEMBRE_PROFIL AS ENUM ('ENFANT', 'ADULTE', 'ETUDIANT');
CREATE TYPE MEMBRE_TYPE_INSCRIPTION AS ENUM ('EN_LIGNE', 'SUR_PLACE');
CREATE TYPE MEMBRE_STATUT_VALIDATION AS ENUM ('EN_ATTENTE', 'VALIDE', 'REJETE');
CREATE TYPE PRET_TYPE AS ENUM ('NORMAL', 'CONSULTATION');
CREATE TYPE PROLONGATION_STATUT AS ENUM ('EN_ATTENTE', 'ACCEPTEE', 'REFUSEE');
CREATE TYPE RESERVATION_STATUT AS ENUM ('EN_ATTENTE', 'CONFIRMEE', 'ANNULEE');


CREATE TABLE "user" (
    id BIGSERIAL PRIMARY KEY, -- BIGSERIAL pour les BIGINT auto-incrémentés
    name VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(20) NOT NULL
);

CREATE TABLE categorie (
    id SERIAL PRIMARY KEY, -- SERIAL pour les INT auto-incrémentés
    nom VARCHAR(50), -- Enfant, +18, Tous publics
    age_minimum INT
);

CREATE TABLE livre (
    id SERIAL PRIMARY KEY,
    titre VARCHAR(150),
    auteur VARCHAR(100),
    categorie_id INT,
    FOREIGN KEY (categorie_id) REFERENCES categorie(id)
);

CREATE TABLE exemplaire (
    id SERIAL PRIMARY KEY,
    livre_id INT,
    code_exemplaire VARCHAR(50) UNIQUE,
    statut EXEMPLAIRE_STATUT DEFAULT 'DISPONIBLE', -- Utilisation du type personnalisé
    localisation VARCHAR(100),
    FOREIGN KEY (livre_id) REFERENCES livre(id)
);

-- ================================
-- 👤 MEMBRES ET INSCRIPTIONS (PostgreSQL)
-- ================================

CREATE TABLE membre (
    id SERIAL PRIMARY KEY,
    nom VARCHAR(100),
    prenom VARCHAR(100),
    email VARCHAR(100) UNIQUE,
    adresse TEXT,
    date_naissance DATE,
    profil MEMBRE_PROFIL NOT NULL, -- Utilisation du type personnalisé
    type_inscription MEMBRE_TYPE_INSCRIPTION NOT NULL, -- Utilisation du type personnalisé
    statut_validation MEMBRE_STATUT_VALIDATION DEFAULT 'EN_ATTENTE' -- Utilisation du type personnalisé
);

CREATE TABLE adhesion (
    id SERIAL PRIMARY KEY,
    membre_id INT,
    date_debut DATE,
    date_expiration DATE,
    FOREIGN KEY (membre_id) REFERENCES membre(id)
);

CREATE TABLE cotisation (
    id SERIAL PRIMARY KEY,
    membre_id INT,
    montant DECIMAL(10, 2),
    date_paiement DATE,
    annee INT,
    FOREIGN KEY (membre_id) REFERENCES membre(id)
);

CREATE TABLE penalite (
    id SERIAL PRIMARY KEY,
    membre_id INT,
    date_debut_penalite DATE,
    date_fin_penalite DATE,
    motif TEXT,
    FOREIGN KEY (membre_id) REFERENCES membre(id)
);

-- ================================
-- 📚 PRÊTS ET RETOURS (PostgreSQL)
-- ================================

CREATE TABLE pret (
    id SERIAL PRIMARY KEY,
    membre_id INT,
    exemplaire_id INT,
    date_emprunt DATE,
    date_retour_prevue DATE,
    date_retour_effective DATE DEFAULT NULL,
    type_pret PRET_TYPE NOT NULL, -- Utilisation du type personnalisé
    FOREIGN KEY (membre_id) REFERENCES membre(id),
    FOREIGN KEY (exemplaire_id) REFERENCES exemplaire(id)
);

CREATE TABLE prolongation (
    id SERIAL PRIMARY KEY,
    pret_id INT,
    date_demande DATE,
    statut PROLONGATION_STATUT DEFAULT 'EN_ATTENTE', -- Utilisation du type personnalisé
    FOREIGN KEY (pret_id) REFERENCES pret(id)
);

CREATE TABLE reservation (
    id SERIAL PRIMARY KEY,
    membre_id INT,
    exemplaire_id INT,
    date_reservation DATE,
    statut RESERVATION_STATUT DEFAULT 'EN_ATTENTE', -- Utilisation du type personnalisé
    FOREIGN KEY (membre_id) REFERENCES membre(id),
    FOREIGN KEY (exemplaire_id) REFERENCES exemplaire(id)
);

-- Données initiales
-- Utilisateurs
INSERT INTO "user" (name, password, role) VALUES
('admin', 'admin', 'ADMIN'),
('bibliothecaire', 'biblio', 'BIBLIOTHECAIRE'),
('membre', 'membre', 'MEMBRE');

-- Catégories
INSERT INTO categorie (nom, age_minimum) VALUES
('Enfant', 0),
('+18', 18),
('Tous publics', 0);

-- Livres
INSERT INTO livre (titre, auteur, categorie_id) VALUES
('Le Petit Prince', 'Antoine de Saint-Exupéry', 1),
('1984', 'George Orwell', 2),
('Fondation', 'Isaac Asimov', 3);

-- Exemplaires
INSERT INTO exemplaire (livre_id, code_exemplaire, statut, localisation) VALUES
(1, 'LP-001', 'DISPONIBLE', 'Étagère A1'),
(1, 'LP-002', 'EMPRUNTE', 'Étagère A1'),
(2, '1984-001', 'RESERVE', 'Étagère B2'),
(3, 'FOND-001', 'DISPONIBLE', 'Étagère C3');

-- Membres
INSERT INTO membre (nom, prenom, email, adresse, date_naissance, profil, type_inscription, statut_validation) VALUES
('Rabe', 'Fara', 'fara@mail.com', 'Lot A, Ambohijatovo', '2000-01-01', 'ADULTE', 'EN_LIGNE', 'VALIDE'),
('Koto', 'Mia', 'mia@mail.com', 'Lot B, Tana', '2010-03-15', 'ENFANT', 'SUR_PLACE', 'VALIDE'),
('Rakoto', 'Jean', 'jean@mail.com', 'Lot C, Tana', '1980-12-20', 'ADULTE', 'SUR_PLACE', 'VALIDE'),
('Andry', 'Nina', 'nina@mail.com', 'Lot Z, Tana', '2005-04-12', 'ETUDIANT', 'EN_LIGNE', 'EN_ATTENTE');

-- Adhésions
INSERT INTO adhesion (membre_id, date_debut, date_expiration) VALUES
(1, '2025-01-01', '2025-12-31'),
(2, '2025-03-01', '2026-02-28'),
(3, '2025-06-01', '2026-05-31');

-- Cotisations
INSERT INTO cotisation (membre_id, montant, date_paiement, annee) VALUES
(1, 10000.00, '2025-01-01', 2025),
(2, 5000.00, '2025-03-01', 2025),
(3, 15000.00, '2025-06-01', 2025);

-- Pénalités (exemple: membre 2 a une pénalité active)
-- INSERT INTO penalite (membre_id, date_debut_penalite, date_fin_penalite, motif) VALUES
-- (2, '2025-07-01', '2025-07-15', 'Retour de livre en retard');

-- Prêts (quelques exemples)
INSERT INTO pret (membre_id, exemplaire_id, date_emprunt, date_retour_prevue, type_pret) VALUES
(1, 1, '2025-07-01', '2025-07-15', 'NORMAL'), -- Rabe Fara emprunte LP-001
(2, 2, '2025-06-20', '2025-07-04', 'NORMAL'); -- Koto Mia emprunte LP-002 (en retard si après le 4/07)

-- Réservations
INSERT INTO reservation (membre_id, exemplaire_id, date_reservation, statut) VALUES
(3, 3, '2025-07-10', 'EN_ATTENTE'); -- Rakoto Jean réserve 1984-001
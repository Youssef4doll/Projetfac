# Livrable 7 — Plan du dossier technique (Word ou LaTeX)

Format conseillé : **Word** (le fichier `DropWear-Dossier-Validation1.docx` généré à partir de cette documentation sert de base), ou LaTeX avec la classe `report`. Volume visé : 35 à 50 pages, annexes comprises. Police 11–12 pt, diagrammes exportés en PNG haute définition depuis `Documentation/Diagrams/png`.

## Pages liminaires

| Section | Contenu à y mettre |
|---------|--------------------|
| Page de garde | Logo de l'école, titre « DropWear — Plateforme e-commerce de vêtements en dropshipping », mention « Mini-projet AWD 2026-2027 — Validation 1 : présentation et modélisation microservices », classe, groupe, membres, encadrant, date |
| Remerciements | Facultatif, 5 lignes |
| Résumé / Abstract | 10 lignes en français et 10 en anglais : problème, solution, architecture, technologies. Mots-clés : microservices, Spring Cloud, Keycloak, Kafka, DDD |
| Table des matières | Générée automatiquement (styles Titre 1 à 3) |
| Liste des figures et des tableaux | Générées automatiquement (légendes « Figure n » et « Tableau n ») |
| Glossaire | SKU, RMA, COD, JWT, OIDC, PKCE, contexte borné, saga, outbox, DLT, circuit breaker |

## Chapitre 1 — Introduction générale (2 pages)

| Section | Contenu |
|---------|---------|
| 1.1 Contexte | Le dropshipping de vêtements, le paiement à la livraison sur le marché visé |
| 1.2 Problématique | Les 5 problèmes du livrable 1 (survente, variantes, retours, COD, support) |
| 1.3 Objectifs | Objectifs fonctionnels et pédagogiques (stack Spring Cloud imposée) |
| 1.4 Organisation du rapport | Une phrase par chapitre |

## Chapitre 2 — Analyse fonctionnelle (6 à 8 pages)

| Section | Contenu |
|---------|---------|
| 2.1 Acteurs | Tableau des acteurs principaux et secondaires (01 §1.4) |
| 2.2 Besoins fonctionnels | User stories par acteur avec priorité MoSCoW (01 §1.5) |
| 2.3 Besoins non fonctionnels | Sécurité (OAuth2/JWT, rôles), disponibilité (résilience), performance (lecture du catalogue), scalabilité, maintenabilité, traçabilité |
| 2.4 Règles de gestion | RG1 à RG8 (01 §1.6) |
| 2.5 Diagrammes de cas d'utilisation | Front-office et back-office, avec 3 lignes de lecture par diagramme |
| 2.6 Périmètre | Tableau dans le périmètre / hors périmètre |

## Chapitre 3 — Architecture microservices (8 à 10 pages)

| Section | Contenu |
|---------|---------|
| 3.1 Pourquoi les microservices | Monolithe vs microservices : avantages et coûts, pertinence ici |
| 3.2 Méthode de découpage (DDD) | Sous-domaines cœur, support et générique, contextes bornés, langage omniprésent |
| 3.3 Tableau de découpage | Service, responsabilité, contexte, base, justification (02 §2.2) |
| 3.4 Justification du découpage | Tableau « même concept, modèles différents ». Critères de cohésion et de couplage. Choix assumés (panier, Fulfillment & Billing, fournisseur) |
| 3.5 Carte des contextes | Diagramme et lecture |
| 3.6 Architecture technique globale | Diagramme d'architecture, tableau des composants, explication des 12 flux (05) |
| 3.7 Stack technique | Tableau des versions (Java 21, Spring Boot 3.4.5, Spring Cloud 2024.0.1, Keycloak 26, Kafka 3.8, PostgreSQL 16, MySQL 8.4, MongoDB 7, Angular 18, Docker) |

## Chapitre 4 — Données et communication (6 à 8 pages)

| Section | Contenu |
|---------|---------|
| 4.1 Base par service | Principe, choix de chaque SGBD (polyglot persistence), pourquoi pas de base partagée, pourquoi pas de Redis en V1 |
| 4.2 Cohérence des données | Snapshots, cohérence à terme, sagas par chorégraphie, Transactional Outbox, consommateurs idempotents |
| 4.3 Communication synchrone | Critères de choix, matrice S1 à S7, sécurité `/internal/**` |
| 4.4 Communication asynchrone | Pourquoi Kafka plutôt que RabbitMQ, catalogue des événements E1 à E11, enveloppe, clés de partition, DLT |
| 4.5 Résilience | Timeout, retry, circuit breaker, fallback : tableau des paramètres et comportement de chaque service en panne |
| 4.6 Diagrammes de séquence | Validation de commande, demande d'échange, avec 4 points de lecture chacun |

## Chapitre 5 — Sécurité (3 pages)

| Section | Contenu |
|---------|---------|
| 5.1 Keycloak | Realm, rôles, clients (frontend public PKCE, services en client credentials) |
| 5.2 Flux d'authentification | Schéma Authorization Code + PKCE, contenu d'un JWT décodé (capture) |
| 5.3 Autorisation | Validation dans la Gateway et dans chaque service, mapping des rôles, `@PreAuthorize`, endpoints internes |

## Chapitre 6 — Conception détaillée des microservices (1 section par service, 3 pages chacune, soit environ 21 pages)

Pour **chaque** service, dans cet ordre : `user-service` (commun), `catalog-service`, `order-service`, `inventory-service`, `return-service`, `ticketing-service`, `fulfillment-service`.

| Sous-section | Contenu (repris de la fiche) |
|--------------|------------------------------|
| x.1 Fiche d'identité | Port, base, routes, responsable |
| x.2 Responsabilités | Fait / ne fait pas, cycle de vie (états) |
| x.3 Modèle de données | Diagramme de classes et tableau des entités |
| x.4 Choix de la base | 3 à 4 arguments |
| x.5 Contrat d'API REST | Tableau ressource / URI / méthode / rôle / corps / codes |
| x.6 Événements | Publiés et consommés |
| x.7 Interactions et pannes | Appelants, appelés, données, comportement en panne |

Pour `ticketing-service`, ajouter **x.8 Technologie avancée : WebSocket/STOMP**.

## Chapitre 7 — Déploiement et outillage (3 pages)

| Section | Contenu |
|---------|---------|
| 7.1 Docker | Dockerfile type (multi-stage), `docker-compose.yml` (services, réseaux, volumes, healthchecks, profils) |
| 7.2 Configuration centralisée | Organisation du Config Server (`application.yml` commun + `<service>.yml`, profil `docker`) |
| 7.3 Découverte | Eureka (capture du tableau de bord avec les 8 services enregistrés) |
| 7.4 Lancement | Procédure du README |

## Chapitre 8 — Gestion de projet (3 pages)

| Section | Contenu |
|---------|---------|
| 8.1 Organisation de l'équipe | Répartition des services et des rôles transverses, contribution de chacun au service commun |
| 8.2 Méthode | Scrum allégé : sprints de 2 semaines, revue à chaque validation |
| 8.3 Outils | GitHub (branches, PR, CODEOWNERS), GitHub Projects (capture du board), conventions de commits |
| 8.4 Planning | Gantt prévisionnel |
| 8.5 Risques | Matrice probabilité × impact avec mitigations |

## Conclusion générale et perspectives (1 page)

Bilan de la Validation 1. Perspectives : implémentation, `notification-service`, Redis (cache de lecture), observabilité (Micrometer Tracing + Zipkin, Prometheus/Grafana), CI/CD (GitHub Actions), Kubernetes.

## Annexes

| Annexe | Contenu |
|--------|---------|
| A | Catalogue complet des événements avec un exemple de JSON pour chacun |
| B | Extrait de `docker-compose.yml` et du realm Keycloak |
| C | Arborescence du dépôt et lien GitHub |
| D | Backlog initial complet |
| E | Questions et réponses préparées (facultatif) |

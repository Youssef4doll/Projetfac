# Livrable 6 — Dépôt Git et gestion de projet

## 6.1 Arborescence du dépôt

Renommer la racine selon la convention `nom-du-projet-classe-groupe`, par exemple `dropwear-4twin2-g5`.

```
dropwear-CLASSE-GROUPE/
├── README.md                         ← sujet, membres, architecture, lancement
├── docker-compose.yml                ← toute la plateforme (profils infra / core / all)
├── .env.example                      ← variables (mots de passe de dev, ports)
├── .gitignore
├── .github/
│   ├── CODEOWNERS                    ← propriétaire de chaque dossier = relecteur obligatoire
│   └── pull_request_template.md
├── Backend/
│   ├── pom.xml                       ← agrégateur Maven (build de tous les projets)
│   ├── config-server/                ← Spring Cloud Config (+ src/main/resources/config/*.yml)
│   ├── eureka-server/
│   ├── api-gateway/
│   ├── user-service/                 ← service commun
│   ├── catalog-service/
│   ├── order-service/
│   ├── inventory-service/
│   ├── return-service/
│   ├── ticketing-service/
│   ├── fulfillment-service/
│   └── infrastructure/
│       ├── keycloak/dropwear-realm.json
│       └── kafka/create-topics.sh
├── Frontend/
│   └── README.md                     ← projet Angular (dropwear-frontend) à générer en sprint 1
└── Documentation/
    ├── 00-synthese-hypotheses-points-attention.md
    ├── 01-sujet-analyse-fonctionnelle.md
    ├── 02-decoupage-microservices.md
    ├── Fiches/03-0 … 03-6 (une fiche par microservice)
    ├── 04-matrice-interactions.md
    ├── 05-architecture-globale.md
    ├── 06-depot-git-gestion-projet.md
    ├── 07-plan-dossier-technique.md
    ├── 09-questions-jury.md
    ├── backlog-initial.csv
    ├── Presentation/
    │   ├── 08-plan-presentation.md
    │   └── scripts-oraux.md
    └── Diagrams/
        ├── src/*.puml                ← sources PlantUML (versionnées)
        ├── png/*.png                 ← exports pour le dossier et les slides
        └── svg/*.svg
```

Chaque microservice est un **projet Spring Boot autonome** (son propre `pom.xml` avec `spring-boot-starter-parent`, son `Dockerfile`, son `src/`). Le `Backend/pom.xml` sert seulement à tout compiler d'un coup (`mvn -f Backend/pom.xml package`). Il n'est le parent d'aucun projet.

## 6.2 Contenu du README

Le fichier [`README.md`](../README.md) à la racine est rédigé. Il contient :
1. Le sujet et la problématique en 5 lignes
2. Les membres et les responsabilités (tableau)
3. L'architecture (diagramme + liste des services, ports et bases)
4. La communication (appels synchrones, événements)
5. Le lancement : prérequis, `docker compose --profile all up -d`, URL utiles, comptes de test
6. L'arborescence et le lien vers la documentation
7. Les conventions Git

## 6.3 Stratégie de branches (Git Flow simplifié)

```
main ───●─────────────────────────●───────────────────●──→   (protégée, tags v0.1-validation1, v1.0-final)
         \                       /                   /
develop ──●──●──●──●──●──●──●──●──●──●──●──●──●──●──●──→    (intégration, protégée)
              \    /  \      /     \        /
feature/order/cart  feature/inventory/reservations  ...
```

| Branche | Rôle | Règles |
|---------|------|--------|
| `main` | Versions présentées au jury | Protégée. Merge uniquement depuis `develop` par PR. **Tag** à chaque validation : `v0.1-validation1`, `v0.2-validation2`, `v1.0-final` |
| `develop` | Intégration continue de l'équipe | Protégée. Merge par **pull request** avec **1 relecture obligatoire** d'un autre membre et build Maven vert |
| `feature/<service>/<sujet>` | Une fonctionnalité | Ex. `feature/order/cart-api`, `feature/infra/gateway-routes`. Courte durée de vie (moins de 3 jours) |
| `docs/<sujet>` | Documentation | Ex. `docs/fiche-inventory`, `docs/diagrams-sequence` |
| `fix/<service>/<sujet>` | Correctif | Ex. `fix/gateway/cors` |

**Binômes de relecture** (croisés, pour que chacun connaisse un autre service) : Youssef ↔ Yasser (Order ↔ Inventory, liés par S2), Eya ↔ Aziz, Med Houssaine ↔ Med Ben Lakhal.

## 6.4 Convention de commits (*Conventional Commits*)

```
<type>(<portée>): <description à l'impératif>

types  : feat, fix, docs, chore, refactor, test, build, ci
portées: user, catalog, order, inventory, return, ticketing, fulfillment, gateway, eureka, config, infra, front, docs
```

Exemples : `feat(order): add cart endpoints skeleton` · `docs(inventory): add class diagram` · `chore(infra): add kafka to docker-compose`.

## 6.5 Plan de commits : Validation 1

Objectif : **chaque membre a au moins 6 commits signés de son nom** sur des fichiers dont il est responsable. Tout le monde configure `git config user.name` et `user.email` avec son vrai nom avant le premier commit.

| Membre | Branches | Commits prévus (exemples) |
|--------|----------|---------------------------|
| **Youssef Jaouani** | `chore/repo-init`, `docs/fiche-order`, `feature/order/skeleton`, `feature/gateway/skeleton` | `chore(repo): init project structure and README` · `docs(order): add order service fiche` · `docs(diagrams): add order validation sequence` · `feat(order): create order-service project with entities` · `feat(gateway): create api-gateway with routes` · `feat(user): add UserProfile entity and /me endpoints` |
| **Eya Frikha** | `docs/analyse-fonctionnelle`, `docs/fiche-catalog`, `feature/catalog/skeleton` | `docs: add functional analysis and user stories` · `docs(diagrams): add use case diagrams` · `docs(catalog): add catalog fiche and class diagram` · `feat(catalog): create catalog-service project with documents` · `feat(user): add Address entity and endpoints` · `docs(front): add frontend structure plan` |
| **Yasser Chebbi** | `docs/decoupage`, `docs/fiche-inventory`, `feature/inventory/skeleton`, `feature/config/skeleton` | `docs: add bounded context decomposition and context map` · `docs(inventory): add inventory fiche (no Redis rationale)` · `feat(inventory): create inventory-service project with entities` · `feat(config): create config-server with shared config` · `feat(user): add SupplierProfile and admin endpoints` · `docs: add planning and gantt` |
| **Aziz Arfaouii** | `docs/interactions`, `docs/fiche-return`, `feature/return/skeleton`, `chore/infra-kafka` | `docs: add interaction matrix and resilience policy` · `docs(diagrams): add return/exchange sequence` · `docs(return): add return fiche` · `feat(return): create return-service project with entities` · `chore(infra): add kafka and topics script` · `feat(user): add keycloak admin client skeleton` |
| **Med Houssaine** | `docs/architecture`, `docs/fiche-ticketing`, `feature/ticketing/skeleton`, `chore/infra-compose` | `docs: add global architecture diagram` · `docs(ticketing): add ticketing fiche with websocket` · `feat(ticketing): create ticketing-service project with documents` · `feat(eureka): create eureka-server` · `chore(infra): add docker-compose with one database per service` · `feat(user): add internal users endpoint and Dockerfile` |
| **Med Ben Lakhal** | `docs/fiche-user`, `docs/fiche-fulfillment`, `feature/fulfillment/skeleton`, `chore/keycloak-realm` | `docs(user): add common service fiche (Keycloak)` · `chore(infra): add keycloak realm export (roles, clients)` · `docs(fulfillment): add fulfillment & billing fiche` · `feat(fulfillment): create fulfillment-service project with entities` · `feat(user): add security config (resource server, roles)` · `docs: add questions and answers for the jury` |

**Le service commun** est réparti pour que chacun y contribue : UserProfile et `/me` (Youssef), Address (Eya), SupplierProfile et admin (Yasser), client Keycloak Admin (Aziz), endpoint interne et Dockerfile (Med Houssaine), configuration de sécurité et realm (Med Ben Lakhal).

### Commandes utiles

```bash
git clone <url> && cd dropwear-CLASSE-GROUPE
git switch develop
git switch -c feature/order/cart-api
git add . && git commit -m "feat(order): add cart endpoints skeleton"
git push -u origin feature/order/cart-api
gh pr create --base develop --fill
git switch main && git merge --no-ff develop && git tag -a v0.1-validation1 -m "Validation 1" && git push --follow-tags
```

## 6.6 Outil de gestion de projet : GitHub Projects

**Choix : GitHub Projects (vue Board + vue Roadmap).** Il est intégré au dépôt : une issue = une tâche, liée à la branche et à la PR. Son statut avance automatiquement à l'ouverture et au merge de la PR, et le jury voit tout au même endroit. Trello ou Jira demanderaient une synchronisation manuelle.

| Colonne | Signification |
|---------|---------------|
| Backlog | Non planifié |
| To do (sprint) | Planifié dans le sprint courant |
| In progress | Branche ouverte |
| In review | PR ouverte |
| Done | PR mergée dans `develop` |

Champs personnalisés : **Service** (label), **Sprint** (itération de 2 semaines), **Estimation** (points), **Priorité** (M/S/C).
Labels : `user`, `catalog`, `order`, `inventory`, `return`, `ticketing`, `fulfillment`, `infra`, `front`, `docs`.

## 6.7 Backlog initial

Le fichier [`backlog-initial.csv`](backlog-initial.csv) reprend ce tableau. Il est importable dans Jira ou Trello, ou peut servir à créer les issues GitHub avec `gh issue create`.

| ID | Tâche | Label | Assigné | Sprint | Prio | Pts |
|----|-------|-------|---------|--------|------|-----|
| DW-01 | Initialiser le dépôt, l'arborescence et le README | docs | Youssef | S0 | M | 2 |
| DW-02 | Analyse fonctionnelle, user stories, cas d'utilisation | docs | Eya | S0 | M | 3 |
| DW-03 | Découpage en contextes bornés et carte des contextes | docs | Yasser | S0 | M | 3 |
| DW-04 | Diagramme d'architecture globale | docs | Med Houssaine | S0 | M | 2 |
| DW-05 | Matrice des interactions et diagrammes de séquence | docs | Aziz | S0 | M | 3 |
| DW-06 | Realm Keycloak : rôles, clients, export JSON | infra | Med Ben Lakhal | S0 | M | 3 |
| DW-07 | Fiche et projet Spring Boot : catalog-service | catalog | Eya | S0 | M | 3 |
| DW-08 | Fiche et projet Spring Boot : order-service | order | Youssef | S0 | M | 3 |
| DW-09 | Fiche et projet Spring Boot : inventory-service | inventory | Yasser | S0 | M | 3 |
| DW-10 | Fiche et projet Spring Boot : return-service | return | Aziz | S0 | M | 3 |
| DW-11 | Fiche et projet Spring Boot : ticketing-service | ticketing | Med Houssaine | S0 | M | 3 |
| DW-12 | Fiche et projet Spring Boot : fulfillment-service | fulfillment | Med Ben Lakhal | S0 | M | 3 |
| DW-13 | Fiche et projet du service commun (répartition à 6) | user | Tous | S0 | M | 5 |
| DW-14 | Slides et répétition de la Validation 1 | docs | Tous | S0 | M | 3 |
| DW-15 | Config Server et configurations par service | infra | Yasser | S1 | M | 3 |
| DW-16 | Eureka Server | infra | Med Houssaine | S1 | M | 1 |
| DW-17 | API Gateway : routes, JWT, CORS, WebSocket | infra | Youssef | S1 | M | 5 |
| DW-18 | Kafka (KRaft), topics, Kafka UI | infra | Aziz | S1 | M | 3 |
| DW-19 | user-service : inscription via Keycloak Admin API | user | Aziz, Med Ben Lakhal | S1 | M | 5 |
| DW-20 | user-service : profil `/me` et adresses | user | Youssef, Eya | S1 | M | 3 |
| DW-21 | user-service : administration, fournisseurs, endpoint interne | user | Yasser, Med Houssaine | S1 | M | 3 |
| DW-22 | Frontend : socle Angular et connexion Keycloak | front | Eya | S1 | M | 5 |
| DW-23 | catalog : CRUD produits, variantes, images, filtres, E1 | catalog | Eya | S2 | M | 8 |
| DW-24 | order : panier et appel S1 vers catalog | order | Youssef | S2 | M | 5 |
| DW-25 | inventory : stocks, réservations, job d'expiration | inventory | Yasser | S2 | M | 8 |
| DW-26 | return : RMA, éligibilité via S3 | return | Aziz | S2 | M | 5 |
| DW-27 | ticketing : tickets et WebSocket STOMP | ticketing | Med Houssaine | S2 | M | 8 |
| DW-28 | fulfillment : consommation de E2, factures et paiements | fulfillment | Med Ben Lakhal | S2 | M | 5 |
| DW-29 | order : validation (S2), outbox, E2 et E3 | order | Youssef | S3 | M | 8 |
| DW-30 | inventory : consommateurs E1, E2, E3, E10 et publication E4, E5 | inventory | Yasser | S3 | M | 5 |
| DW-31 | return : échange (S4), E9, E10, E11 | return | Aziz | S3 | M | 5 |
| DW-32 | ticketing : E8, E9, S5, S6 (mode dégradé) | ticketing | Med Houssaine | S3 | S | 5 |
| DW-33 | fulfillment : expéditions, webhook transporteur, E6, E7, E8, E11 | fulfillment | Med Ben Lakhal | S3 | M | 8 |
| DW-34 | catalog : consommateurs E4 et E5 | catalog | Eya | S3 | S | 2 |
| DW-35 | Résilience commune (Resilience4j) et tests de panne | infra | Aziz | S3 | M | 3 |
| DW-36 | Frontend : écrans client, admin, support, fournisseur | front | Tous | S4 | M | 13 |
| DW-37 | Docker Compose complet et profils | infra | Med Houssaine | S4 | M | 3 |
| DW-38 | Collection Postman et scénario de démonstration | docs | Yasser | S4 | S | 3 |
| DW-39 | Dossier technique final | docs | Eya + tous | S4 | M | 5 |
| DW-40 | Répétition de la soutenance finale | docs | Tous | S4 | M | 2 |

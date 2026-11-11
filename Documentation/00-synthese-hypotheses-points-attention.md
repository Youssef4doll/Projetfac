# 00 — Synthèse, hypothèses et points d'attention

> Document de référence pour toute l'équipe. Les noms de services, d'événements, de topics et d'endpoints définis ici sont les **seuls** à utiliser dans les autres livrables, dans le code et dans la présentation.

## 1. Hypothèses retenues

Les points ci-dessous ont été tranchés sans attendre de réponse. Modifiez-les si l'enseignant impose autre chose.

| # | Hypothèse | Valeur retenue | Où la changer |
|---|-----------|----------------|---------------|
| H1 | Nom du projet | **DropWear** (dossier `dropwear-CLASSE-GROUPE`, à renommer avec votre classe et votre groupe, ex. `dropwear-4twin2-g5`) | Partout (rechercher « DropWear ») |
| H2 | Broker de messages | **Apache Kafka** (mode KRaft, sans Zookeeper) | 04, 05, docker-compose |
| H3 | Front-end | **Angular 18 + keycloak-angular** (React reste possible) | 05, Frontend/README |
| H4 | Versions | Java 21, Spring Boot 3.4.5, Spring Cloud 2024.0.1, Keycloak 26 | Backend/*/pom.xml |
| H5 | Marché | Tunisie / Maghreb : devise **TND**, TVA 19 %, paiement à la livraison (COD) majoritaire | Fiche Fulfillment & Billing |
| H6 | Répartition des services | Proposition ci-dessous (à valider en équipe) | README, 08 |
| H7 | Base du service commun | **PostgreSQL** `user_db` (non précisée dans le sujet) | Fiche user-service |
| H8 | Calendrier | Validation 1 fin de semaine 2 (≈ 20/11/2026), rendu final semaine 10. Dates indicatives | 08 (planning) |

### Répartition proposée

| Membre | Microservice individuel | Rôle transverse |
|--------|------------------------|-----------------|
| Youssef Jaouani | Order Service | Coordination, dépôt Git |
| Eya Frikha | Catalog Service | Analyse fonctionnelle, Frontend (lead) |
| Yasser Chebbi | Inventory Service | Planning, Config Server |
| Aziz Arfaouii | Exchange & Return Service | Communication / résilience, Kafka |
| Med Houssaine | Ticketing & Support Service | Architecture, Docker Compose |
| Med Ben Lakhal | Fulfillment & Billing Service | **Présentateur du service commun**, Keycloak |

Le service commun **Auth & Users** est développé par les 6 membres (une partie chacun, voir 06) et présenté par **Med Ben Lakhal**.

## 2. Conventions de nommage (cohérence stricte)

| Élément | Convention | Exemple |
|---------|-----------|---------|
| Nom de service (`spring.application.name`, Eureka, Docker) | kebab-case anglais, suffixe `-service` | `order-service` |
| Entités, DTO, classes Java | Anglais, PascalCase | `Order`, `StockReservation` |
| Statuts (enums) | Français, MAJUSCULES, sans accent (vocabulaire du sujet) | `EN_ATTENTE`, `CONFIRMEE`, `EXPEDIEE` |
| Événements de domaine | Français au passé, sans accent, suffixe `Event` | `CommandeValideeEvent` (« CommandeValidéeEvent » du sujet) |
| Topics Kafka | `contexte.fait` en minuscules | `commande.validee` |
| Endpoints publics (via Gateway) | `/api/<contexte>/...`, ressources au pluriel | `/api/orders/{orderId}` |
| Endpoints internes (service à service, **non routés par la Gateway**) | `/internal/<contexte>/...` | `/internal/inventory/reservations` |
| Transitions métier | `POST /{id}/<action>` (déclenche des effets) | `POST /api/orders/{orderId}/validate` |
| Mise à jour partielle d'attributs | `PATCH` | `PATCH /api/tickets/{ticketId}/status` |
| Rôles Keycloak (realm `dropwear`) | MAJUSCULES | `CLIENT`, `ADMIN`, `SUPPORT`, `FOURNISSEUR`, `SERVICE`, `TRANSPORTEUR` |

> Pourquoi « CommandeValideeEvent » sans accent : un identifiant Java avec accent compile, mais il pose problème dans les noms de fichiers, Git sous Windows, les sérialiseurs et les topics Kafka. On garde le vocabulaire du sujet sans les accents.

## 3. Référentiel unique

### 3.1 Services et ports

| Composant | Nom technique | Port | Base de données (conteneur) | Responsable |
|-----------|---------------|------|-----------------------------|-------------|
| Front-end | `frontend` | 4200 | – | Eya (lead) + tous |
| API Gateway | `api-gateway` | 8080 | – | Youssef |
| Eureka | `eureka-server` | 8761 | – | Med Houssaine |
| Config Server | `config-server` | 8888 | – (dépôt de config `native`) | Yasser |
| Keycloak | `keycloak` | 8180 | `keycloak-db` (PostgreSQL dédiée) | Med Ben Lakhal |
| Kafka | `kafka` | 9092 | – | Aziz |
| Service commun Auth & Users | `user-service` | 8081 | `user-db` — PostgreSQL `user_db` | Toute l'équipe |
| Catalog Service | `catalog-service` | 8082 | `catalog-db` — MongoDB `catalog_db` | Eya |
| Order Service | `order-service` | 8083 | `order-db` — PostgreSQL `order_db` | Youssef |
| Inventory Service | `inventory-service` | 8084 | `inventory-db` — PostgreSQL `inventory_db` | Yasser |
| Exchange & Return Service | `return-service` | 8085 | `return-db` — MySQL `return_db` | Aziz |
| Ticketing & Support Service | `ticketing-service` | 8086 | `ticketing-db` — MongoDB `ticketing_db` | Med Houssaine |
| Fulfillment & Billing Service | `fulfillment-service` | 8087 | `fulfillment-db` — PostgreSQL `fulfillment_db` | Med Ben Lakhal |

### 3.2 Catalogue des événements (Kafka)

| ID | Événement | Topic | Producteur | Consommateurs |
|----|-----------|-------|-----------|---------------|
| E1 | `VarianteCreeeEvent` | `catalogue.variante-creee` | catalog-service | inventory-service |
| E2 | `CommandeValideeEvent` | `commande.validee` | order-service | inventory-service, fulfillment-service |
| E3 | `CommandeAnnuleeEvent` | `commande.annulee` | order-service | inventory-service, fulfillment-service |
| E4 | `StockFaibleEvent` | `stock.faible` | inventory-service | catalog-service |
| E5 | `StockReapprovisionneEvent` | `stock.reapprovisionne` | inventory-service | catalog-service |
| E6 | `CommandeExpedieeEvent` | `livraison.expediee` | fulfillment-service | order-service |
| E7 | `CommandeLivreeEvent` | `livraison.livree` | fulfillment-service | order-service |
| E8 | `LivraisonRetardeeEvent` | `livraison.retardee` | fulfillment-service | ticketing-service |
| E9 | `RetourApprouveEvent` | `retour.approuve` | return-service | ticketing-service |
| E10 | `RetourRecuEvent` | `retour.recu` | return-service | inventory-service, fulfillment-service |
| E11 | `RetourFinaliseEvent` | `retour.finalise` | fulfillment-service | return-service |

### 3.3 Appels synchrones (OpenFeign + Resilience4j)

| ID | Appelant → Appelé | Endpoint | Quand |
|----|-------------------|----------|-------|
| S1 | order-service → catalog-service | `GET /internal/catalog/variants/{sku}` | Ajout au panier (prix et fournisseur faisant foi) |
| S2 | order-service → inventory-service | `POST /internal/inventory/reservations` | Validation de la commande (vérification + verrouillage) |
| S3 | return-service → order-service | `GET /internal/orders/{orderId}` | Demande de retour (éligibilité 14 jours) |
| S4 | return-service → inventory-service | `POST /internal/inventory/reservations` | Échange (réserver la taille de remplacement) |
| S5 | ticketing-service → order-service | `GET /internal/orders/{orderId}` | Création d'un ticket lié à une commande |
| S6 | ticketing-service → user-service | `GET /internal/users/{userId}` | Ticket automatique (pas de JWT client) |
| S7 | user-service → Keycloak Admin API | `POST /admin/realms/dropwear/users` … | Inscription, changement de rôle |

## 4. Points d'attention détectés dans le découpage initial

Chaque point est classé : 🔴 à corriger avant la validation, 🟠 à justifier à l'oral, 🟢 amélioration recommandée.

### 🔴 P1 — Le rôle « Fournisseur » n'a aucun service dédié
Le sujet cite 4 acteurs mais aucun des 6 services ne parle du fournisseur. Un jury le remarquera.
**Décision** : le fournisseur est un **acteur**, pas un contexte borné. Ses cas d'utilisation sont répartis dans les contextes concernés, sans 7ᵉ service :

| Besoin du fournisseur | Service | Endpoint |
|-----------------------|---------|----------|
| Profil entreprise (raison sociale, matricule fiscal, délai d'expédition) | user-service | `POST /api/users/{userId}/supplier-profile` |
| Ses produits sont rattachés à lui | catalog-service | attribut `Product.supplierId` |
| Mettre à jour le stock de ses SKU | inventory-service | `PUT /api/inventory/stocks/{sku}` |
| Voir ses alertes de réapprovisionnement | inventory-service | `GET /api/inventory/alerts` |
| Voir les expéditions à préparer (commandes dropshipping) | fulfillment-service | `GET /api/fulfillment/shipments/mine` |
| Expédier et saisir le n° de suivi | fulfillment-service | `POST /api/fulfillment/shipments/{shipmentId}/ship` |
| Réceptionner un retour | return-service | `POST /api/returns/{rmaId}/receive` |

Phrase pour l'oral : *« Le fournisseur n'a pas de service dédié parce que ce n'est pas un domaine métier. C'est un acteur qui intervient dans trois contextes : stock, logistique et retours. »*

### 🔴 P2 — Redis en plus de PostgreSQL dans Inventory : **non retenu en V1**
- Redis **ne fait pas partie de la stack imposée** (H2, MySQL, MongoDB, PostgreSQL). L'ajouter expose à une remarque du jury.
- Le besoin (verrou temporaire avec expiration) se couvre en PostgreSQL : table `stock_reservation` avec `expires_at`, verrou optimiste `@Version` (ou `SELECT … FOR UPDATE`) et un job `@Scheduled` qui libère les réservations expirées chaque minute.
- PostgreSQL garantit l'**atomicité** entre « réserver » et « mettre à jour le stock » dans une seule transaction. Avec Redis + PostgreSQL, il faudrait synchroniser deux stockages, donc ajouter un risque d'incohérence.
- **Quand Redis serait justifié** (évolution) : forte charge de lecture des disponibilités (soldes, Black Friday) → cache de lecture devant PostgreSQL, ou TTL natif pour des milliers de réservations par seconde. À présenter comme une **perspective**, pas dans la V1.

### 🟠 P3 — « Microservice 5 (technologie avancée) »
Dans votre liste, le service n° 5 est **Ticketing & Support**. Recommandation : il illustre la technologie avancée avec **WebSocket (STOMP)** pour la messagerie en temps réel entre le client et l'agent support.
- Pourquoi pas le broker : Kafka est une **brique d'infrastructure** utilisée par 5 services sur 7. Il ne caractérise pas un microservice en particulier.
- Alternative si l'enseignant veut que « technologie avancée » = broker : présenter Kafka dans Ticketing (consommation de `LivraisonRetardeeEvent` → ticket automatique) **et** WebSocket.
- ⚠️ **À confirmer par écrit avec l'enseignant** avant la validation (le numéro « 5 » peut renvoyer à un autre ordre que le vôtre).

### 🟠 P4 — Fulfillment & Billing regroupe deux sous-domaines
Logistique et facturation sont deux contextes distincts. Justification du regroupement : avec le **paiement à la livraison**, l'encaissement *est* un événement de livraison (le transporteur encaisse). Séparer imposerait un échange d'événements pour chaque livraison.
Mitigation : deux packages internes indépendants (`logistics`, `billing`) sans dépendance croisée d'entités, donc extractibles plus tard en deux services. À dire à l'oral.

### 🔴 P5 — Les statuts de commande du sujet sont incomplets
« En attente, Confirmée, Expédiée » ne suffit pas : l'éligibilité au retour (**14 jours après livraison**) exige une date de livraison. Ajout de **`LIVREE`** (avec `deliveredAt`) et de **`ANNULEE`**.
Cycle final : `EN_ATTENTE → CONFIRMEE → EXPEDIEE → LIVREE`, et `EN_ATTENTE | CONFIRMEE → ANNULEE`.

### 🔴 P6 — Vérification synchrone seule = risque de survente
Si Order *vérifie* le stock puis Inventory *décrémente* plus tard via l'événement, deux clients peuvent acheter la dernière pièce entre les deux. D'où le **verrouillage temporaire** : l'appel synchrone S2 **réserve** (vérifie et bloque) et l'événement E2 **confirme** (décrémentation définitive). La réservation expire après 30 min. C'est la compensation automatique si la transaction de la commande échoue après la réservation.

### 🟠 P7 — Perte d'événement entre la base et Kafka
« Écrire en base puis publier sur Kafka » n'est pas atomique. Solution : **Transactional Outbox** (table `outbox_event` écrite dans la même transaction, puis relayée vers Kafka par un publieur planifié). Obligatoire dans order-service (événement le plus critique), recommandé dans les autres producteurs. Les consommateurs sont **idempotents** (table `processed_event` avec `eventId`).

### 🟠 P8 — Une base par service = beaucoup de conteneurs
7 bases + Keycloak (+ sa base) + Kafka + 3 briques Spring Cloud + 7 services + front ≈ **21 conteneurs**, soit 8 à 12 Go de RAM.
Mitigation : profils Docker Compose (`infra`, `core`, `all`). Chaque membre lance l'infrastructure et son service. La démo complète tourne sur la machine la plus puissante.

### 🟠 P9 — Commande multi-fournisseurs
Une commande peut contenir des articles de plusieurs fournisseurs, donc plusieurs expéditions. Décision : fulfillment-service crée **une `Shipment` par fournisseur**. Il ne publie `CommandeExpedieeEvent` / `CommandeLivreeEvent` que lorsque **toutes** les expéditions de la commande ont atteint ce statut. Order reste simple.

### 🟢 P10 — Endpoints internes
Les appels service à service passent par `/internal/**`, **non routés par la Gateway** et protégés par le rôle `SERVICE` (jeton *client credentials* Keycloak). Un client ne peut donc pas réserver du stock directement.

### 🟢 P11 — Remboursement d'une commande payée à la livraison
Il n'y a pas de carte à recréditer. Billing émet un **avoir** et enregistre un `Refund` (virement ou bon d'achat). La logique réelle est hors périmètre.

### 🟢 P12 — Cohérence des données entre services
Pas de transaction distribuée (pas de 2PC). Les processus longs (commande, retour) suivent une **saga par chorégraphie** : chaque service réagit aux événements et publie le suivant. Les données des autres services sont copiées sous forme d'**instantanés** (ex. prix et adresse figés dans la commande).

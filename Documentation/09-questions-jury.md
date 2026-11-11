# Livrable 9 — Préparation aux questions du jury

Méthode de réponse : **réponse directe en une phrase → justification → exemple tiré de DropWear**. 30 secondes maximum. Si vous ne savez pas, dites-le et proposez la piste (« nous ne l'avons pas encore traité ; la piste serait… »).

## 9.1 Questions générales (15)

### Q1. Qu'est-ce qu'un contexte borné et comment l'avez-vous appliqué ?
Un contexte borné est une frontière dans laquelle un modèle et un vocabulaire métier ont un sens unique. Nous avons découpé par **processus métier** (commande, stock, après-vente…) et non par table. Exemple : le SKU existe dans 5 services avec 5 modèles différents. Dans le catalogue, c'est une variante avec taille, coupe et images. Dans le stock, ce sont des quantités physique et réservée. Dans la commande, c'est un instantané avec le prix payé. Les services partagent l'identifiant, jamais la table.

### Q2. Pourquoi une base de données par service ?
Pour l'**autonomie** : chaque service peut changer son schéma, choisir sa technologie et être déployé sans casser les autres. Une base partagée crée un couplage caché : une colonne renommée casse trois services. C'est aussi ce qui nous permet d'utiliser MongoDB pour le catalogue et les tickets, MySQL pour les retours et PostgreSQL ailleurs (*polyglot persistence*).

### Q3. Sans base partagée, comment affichez-vous des données qui viennent de plusieurs services ?
Deux techniques. **Composition d'API** : le front ou la Gateway appelle plusieurs services (la page « ma commande » appelle order puis `/api/fulfillment/orders/{id}/tracking`). **Vue locale alimentée par événements** : le catalogue garde un indicateur `lowStock` mis à jour par `StockFaibleEvent`, ce qui évite d'appeler le stock à chaque affichage. On accepte une cohérence à terme de quelques millisecondes.

### Q4. À quoi sert l'API Gateway ?
C'est le **point d'entrée unique** : routage vers les services, validation du JWT, CORS et routage WebSocket. Le front ne connaît qu'une URL, les services ne sont pas exposés, et les endpoints `/internal/**` sont inaccessibles de l'extérieur. Sans Gateway, le front devrait connaître sept adresses et chaque service gérerait lui-même le CORS.

### Q5. La Gateway n'est-elle pas un point unique de défaillance ?
Si, d'où deux précautions. Elle est **sans état** (stateless) : on peut en lancer plusieurs instances derrière un répartiteur de charge. Et elle ne contient aucune logique métier, donc elle tombe rarement. En Docker, `restart: unless-stopped` la relance automatiquement.

### Q6. À quoi sert Eureka, et que se passe-t-il s'il tombe ?
Eureka est l'**annuaire** : chaque instance s'y enregistre et Feign ou la Gateway résolvent `lb://order-service` en adresse réelle, avec répartition de charge. S'il tombe, les clients gardent **en cache** la dernière liste d'instances et continuent à fonctionner. Seules les nouvelles instances ne sont pas découvertes jusqu'à son retour.

### Q7. À quoi sert le Config Server, et que se passe-t-il s'il tombe ?
Il **centralise la configuration** (URL des bases, Kafka, paramètres Resilience4j) par service et par environnement, versionnée dans un seul endroit. Il n'est lu qu'**au démarrage** : les services déjà lancés continuent. Un service qui démarre pendant la panne réessaie (`spring.cloud.config.retry`) avant d'échouer.

### Q8. Comment choisissez-vous entre synchrone et asynchrone ?
Synchrone quand **on ne peut pas répondre à l'utilisateur sans la réponse** : « la taille M est-elle disponible ? » (S2), « la commande est-elle livrée depuis moins de 14 jours ? » (S3). Asynchrone quand on **annonce un fait accompli** auquel plusieurs services réagissent : `CommandeValideeEvent` déclenche le décrément du stock et la préparation du colis, et le client n'a pas à attendre. Résultat : 6 appels synchrones pour 11 événements.

### Q9. Pourquoi Kafka plutôt que RabbitMQ ?
Les deux conviennent. Kafka nous apporte trois choses. (1) **Plusieurs consommateurs indépendants** du même événement grâce aux *consumer groups* (inventory et fulfillment lisent `commande.validee` chacun à leur rythme). (2) La **rétention** : un service arrêté relit ce qu'il a manqué depuis son offset. (3) L'**ordre par clé** (`orderId`) : les événements d'une même commande restent ordonnés. RabbitMQ serait plus simple pour du routage de tâches ponctuelles.

### Q10. Que se passe-t-il quand un service appelé en synchrone est en panne ?
Quatre mécanismes Resilience4j. Un **timeout** (2 à 3 s) libère le thread. Un **retry** (2 tentatives avec backoff, seulement pour les erreurs transitoires et les appels idempotents) absorbe les micro-coupures. Un **circuit breaker** passe à l'état « ouvert » au-delà de 50 % d'échecs sur 10 appels et échoue immédiatement pendant 10 s, puis « demi-ouvert » teste 3 appels. Enfin un **fallback** donne une réponse métier : order renvoie 503 et laisse la commande en attente, ticketing crée le ticket en mode dégradé.

### Q11. Que se passe-t-il si Kafka tombe ?
Rien n'est perdu côté producteur grâce au **Transactional Outbox** : l'événement est écrit dans la base du service, dans la même transaction que la donnée métier, puis un relais le publie dès que Kafka revient. Côté consommateur, chaque service reprend à son dernier offset. L'utilisateur n'est pas bloqué : sa commande est confirmée, seule la préparation est retardée.

### Q12. Comment évitez-vous qu'un événement soit traité deux fois ?
Kafka garantit « au moins une fois », donc des doublons sont possibles. Nos consommateurs sont **idempotents**. Chaque événement porte un `eventId`, enregistré dans une table `processed_event` dans la même transaction que le traitement : un doublon est ignoré. Des contraintes d'unicité métier complètent (une seule facture par `orderId`). Après 3 échecs, le message part dans un topic `.DLT` pour analyse.

### Q13. Comment fonctionne l'authentification avec Keycloak ?
Le front redirige vers Keycloak (*Authorization Code + PKCE*). Après la connexion, Keycloak renvoie un **JWT signé** qui contient l'identifiant (`sub`), l'e-mail et les rôles. Le front l'envoie à chaque requête. La Gateway et les services vérifient la **signature localement** avec les clés publiques (JWKS) mises en cache, sans appeler Keycloak à chaque requête. Les rôles du JWT alimentent `@PreAuthorize("hasRole('ADMIN')")`.

### Q14. Et pour un appel entre deux services, quel jeton est utilisé ?
Un jeton **client credentials** : chaque service appelant (order, return, ticketing) a un client confidentiel dans Keycloak avec le rôle `SERVICE`. Un intercepteur Feign obtient le jeton, le met en cache et l'ajoute aux appels `/internal/**`. L'appelé exige `hasRole('SERVICE')`. C'est indispensable pour les traitements déclenchés par un événement, où il n'y a pas d'utilisateur, par exemple ticketing qui appelle user-service pour un ticket automatique.

### Q15. Comment garantissez-vous la cohérence des données sans transaction distribuée ?
Par des **sagas par chorégraphie** et la **cohérence à terme**. Chaque étape est une transaction locale qui publie un événement. En cas d'échec, une étape de **compensation** défait la précédente : une commande annulée publie `CommandeAnnuleeEvent`, alors Inventory libère la réservation et Fulfillment émet un avoir. La réservation à durée limitée (30 min) est elle-même une compensation automatique si la commande n'aboutit pas. Nous évitons le 2PC, qui bloquerait tous les services si l'un d'eux ne répond pas.

## 9.2 Questions spécifiques par microservice (2 par service)

### Service commun — user-service (Med Ben Lakhal)
**Q. Pourquoi ne pas tout stocker dans Keycloak ?**
Keycloak est un fournisseur d'identité : il est excellent pour les comptes, les mots de passe et les rôles, mais pas pour des données métier requêtables comme les adresses multiples ou le profil fournisseur. On sépare la **sécurité** (Keycloak) des **données de compte** (user-service, PostgreSQL), reliées par un identifiant unique : `UserProfile.id` = `sub` du JWT.

**Q. Que se passe-t-il si la création dans Keycloak réussit mais pas l'insertion en base ?**
On **compense** en supprimant le compte Keycloak dans le bloc d'erreur, et la réponse est 503. Filet de sécurité : au premier appel à `/me`, si le profil n'existe pas, il est créé à partir des claims du JWT (*just-in-time provisioning*). On n'a donc jamais d'utilisateur bloqué.

### Catalog Service (Eya Frikha)
**Q. Pourquoi imbriquer les variantes dans le document produit ?**
Elles sont toujours lues avec le produit (fiche produit, liste filtrée) et un produit a au plus quelques dizaines de variantes. L'imbrication donne une lecture en une requête et une mise à jour atomique. Une collection séparée ne se justifierait qu'avec des milliers de variantes par produit, ce qui n'est pas le cas pour des vêtements.

**Q. Comment le catalogue affiche-t-il « épuisé » sans accéder à la base du stock ?**
Par une **vue locale** : Inventory publie `StockFaibleEvent` et `StockReapprovisionneEvent`, et le catalogue met à jour `lowStock` et `outOfStock` sur la variante. Pour une disponibilité exacte sur la fiche produit, le front appelle `GET /api/inventory/stocks?skus=…`. Et de toute façon, la vérité est vérifiée à la validation (S2).

### Order Service (Youssef Jaouani)
**Q. Pourquoi copier le prix et l'adresse dans la commande au lieu de les référencer ?**
Une commande est un **document historique** : si le prix change demain dans le catalogue ou si le client modifie son adresse, la commande passée doit garder les valeurs d'origine (facture, litige, retour au prix payé). Cela supprime aussi toute dépendance au catalogue ou à user-service à la lecture.

**Q. Qu'est-ce que le pattern Outbox et pourquoi en avez-vous besoin ?**
Sans lui, il y a deux écritures non atomiques : la base, puis Kafka. Si le service plante entre les deux, la commande est confirmée mais personne ne prépare le colis. Avec l'outbox, on écrit la commande **et** l'événement dans la même transaction PostgreSQL (table `outbox_event`). Un relais planifié publie ensuite les lignes non envoyées et les marque `publishedAt`. Garantie : au moins une fois, d'où des consommateurs idempotents.

### Inventory Service (Yasser Chebbi)
**Q. Deux clients valident en même temps la dernière pièce : que se passe-t-il ?**
Les deux requêtes lisent `StockItem` avec la même `version`. La première met à jour `quantityReserved` et incrémente la version. La seconde échoue avec une `OptimisticLockException`, rejoue, relit un disponible nul et reçoit **409** (`unavailableSkus`). Une contrainte `CHECK (quantity_reserved <= quantity_on_hand)` sert de dernier garde-fou en base. Un seul client obtient la pièce.

**Q. Pourquoi une réservation à durée limitée, et si `CommandeValideeEvent` arrive après l'expiration ?**
La durée limitée est une **compensation automatique** : si la commande échoue après la réservation (crash, rollback), le stock n'est pas bloqué indéfiniment. Si l'événement arrive en retard (inventory arrêté plus de 30 min), la commande a bien été vendue, donc on décrémente quand même et on crée une alerte si le stock devient négatif. Le TTL est choisi supérieur à la durée de reprise visée.

### Exchange & Return Service (Aziz Arfaouii)
**Q. Pourquoi un appel synchrone à Order plutôt qu'une copie locale via les événements ?**
L'éligibilité est une **décision** (accepter ou refuser un retour) qui exige des données faisant foi et à jour. L'appel n'a lieu qu'une fois par demande, donc le couplage est faible. Une copie locale alimentée par `CommandeLivreeEvent` est une évolution possible si order devient un goulot. En cas de panne d'Order, nous préférons un 503 explicite à une décision prise sur des données incertaines.

**Q. Comment restent cohérents la RMA, le stock et le remboursement ?**
Par une saga : `RetourRecuEvent` est consommé par Inventory (remise en stock) et par Fulfillment (remboursement ou remplacement). Fulfillment publie `RetourFinaliseEvent`, qui fait passer la RMA à `CLOTUREE`. Chaque étape est idempotente et les identifiants (`rmaId`) relient tout. Si une étape échoue, le message part en DLT et la RMA reste `COLIS_RECU`, visible dans la file de l'admin.

### Ticketing & Support Service (Med Houssaine)
**Q. Pourquoi WebSocket plutôt que du polling REST, et comment sécurisez-vous la connexion ?**
Le polling (une requête toutes les 2 s par client) gaspille des ressources et ajoute de la latence. WebSocket garde **une connexion bidirectionnelle** et le serveur pousse le message instantanément. Sécurité : le JWT est passé dans la trame STOMP `CONNECT` et vérifié par un intercepteur. L'abonnement à `/topic/tickets/{id}` n'est autorisé qu'au propriétaire et à l'agent assigné.

**Q. Les messages imbriqués ne risquent-ils pas de dépasser la limite de 16 Mo de MongoDB ?**
Un message texte fait quelques centaines d'octets et les pièces jointes sont stockées comme des URL, pas en binaire. Il faudrait des dizaines de milliers de messages sur un seul ticket. Si un fil devenait très long, on déplacerait les messages dans une collection `messages` indexée par `ticketId`, sans changer l'API.

### Fulfillment & Billing Service (Med Ben Lakhal)
**Q. Pourquoi regrouper logistique et facturation alors que ce sont deux contextes ?**
Avec le paiement à la livraison, **l'encaissement est un événement de livraison** : le transporteur livre et encaisse au même moment. Les séparer imposerait un échange d'événements à chaque livraison, pour une équipe de 6 qui ne peut pas porter un 8ᵉ service. Mais nous avons isolé deux packages (`logistics`, `billing`) sans relation JPA : l'extraction en `billing-service` est une perspective prévue.

**Q. Comment gérez-vous une commande avec des articles de plusieurs fournisseurs ?**
Chaque ligne de `CommandeValideeEvent` porte son `supplierId`. Nous créons **une `Shipment` par fournisseur**, chacun voit seulement les siennes (`/shipments/mine`). Nous ne publions `CommandeExpedieeEvent` et `CommandeLivreeEvent` que lorsque **toutes** les expéditions ont atteint ce statut : order-service n'a pas à connaître cette complexité.

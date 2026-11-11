# Scripts oraux — 1 minute maximum par microservice

Repères : environ 140 mots par minute, à un débit posé. Chaque script suit le même ordre : **rôle → données et base → API → interactions → panne**. Le jury retrouve ainsi les critères d'évaluation dans le même ordre pour chaque membre. À lire à voix haute avec un chronomètre, au moins 3 fois.

---

## Service commun — Auth & Users (Med Ben Lakhal) · slide 8

> Je vous présente le service commun, développé par les six membres. Il sépare deux responsabilités. **L'authentification est confiée à Keycloak** : il gère les mots de passe et la connexion, et il émet des jetons JWT signés. Notre realm `dropwear` définit quatre rôles humains, Client, Admin, Support et Fournisseur, plus un rôle `SERVICE` pour les appels entre microservices. **Le user-service gère les données métier du compte** : profil, adresses de livraison et profil fournisseur, dans PostgreSQL. À l'inscription, il crée le compte dans Keycloak via l'API d'administration, puis le profil local. Si Keycloak est indisponible, on répond 503 sans créer de données orphelines. La Gateway et chaque service valident le JWT **localement**, grâce aux clés publiques de Keycloak mises en cache. Enfin, si user-service tombe, les clients peuvent toujours se connecter et commander : la commande n'en dépend pas.

*(≈ 145 mots)*

---

## Catalog Service (Eya Frikha) · slide 9

> Le Catalog Service gère les produits, les catégories, les variantes et les images. **Une variante, c'est un SKU** : une combinaison de taille, de coupe (*regular*, *boxy*, *oversize*) et de coloris. J'ai choisi **MongoDB** : une fiche produit, avec ses variantes et ses images imbriquées, se lit en une seule requête, et le schéma flexible accepte des attributs différents pour un jean et pour un t-shirt. L'API expose en public la recherche filtrée par taille, coupe et coloris, et réserve le CRUD à l'admin, avec un 409 si un SKU existe déjà. Le catalogue est **la source de vérité du prix** : order-service l'appelle en OpenFeign à l'ajout au panier. Je publie `VarianteCreeeEvent` pour qu'Inventory crée la ligne de stock, et je consomme `StockFaible` et `StockReapprovisionne` pour afficher le badge « bientôt épuisé ». Si mon service tombe, les commandes déjà validées, les livraisons et les retours continuent.

*(≈ 150 mots)*

---

## Order Service (Youssef Jaouani) · slide 10

> Order Service porte le parcours d'achat : panier, total et cycle de vie de la commande. Une commande passe par cinq statuts : en attente, confirmée, expédiée, livrée ou annulée. **Nous avons ajouté « livrée »**, car le délai de retour de quatorze jours a besoin d'une date de livraison. La base est **PostgreSQL** pour les transactions ACID : la commande, ses lignes et l'événement à publier sont écrits dans la même transaction. C'est le **pattern Outbox**, aucun événement n'est perdu. À la validation, j'appelle Inventory **en synchrone pour réserver le stock** : c'est la seule réponse sans laquelle je ne peux pas confirmer au client. Ensuite je publie `CommandeValideeEvent` : Inventory décrémente et Fulfillment prépare le bordereau, en parallèle. Si Inventory est en panne, Resilience4j fait deux tentatives puis ouvre le circuit : la commande reste en attente et le client reçoit un 503. **Jamais de confirmation sans stock réservé.**

*(≈ 150 mots)*

---

## Inventory Service (Yasser Chebbi) · slide 11

> Inventory Service gère le stock par SKU, le verrouillage temporaire, la décrémentation définitive et les alertes de réapprovisionnement. Quand une commande est validée, je crée une **réservation qui bloque la quantité pendant trente minutes**. À la réception de `CommandeValideeEvent`, la réservation est confirmée et le stock décrémenté. Si la commande est annulée ou si le délai expire, un job planifié libère le stock. **J'ai choisi PostgreSQL seul, sans Redis.** Le cœur du service est une section critique concurrente (deux clients pour la dernière pièce) et PostgreSQL garantit l'atomicité avec un verrou optimiste. Redis n'est pas dans la stack imposée et ajouterait une seconde source de vérité à synchroniser. Il reste une perspective, comme cache de lecture. Les réservations passent par un endpoint interne, non exposé par la Gateway et idempotent. Quand le disponible passe sous le seuil, je publie `StockFaibleEvent` et je crée une alerte pour le fournisseur.

*(≈ 150 mots)*

---

## Exchange & Return Service (Aziz Arfaouii) · slide 12

> Exchange & Return Service gère le **workflow RMA** : demande, approbation avec étiquette de retour, réception, puis clôture. À la demande, j'appelle Order en OpenFeign pour vérifier trois points : la commande appartient au client, elle est livrée, et la livraison date de moins de quatorze jours. Sinon, je réponds 422. Pour un **échange de taille**, j'appelle aussi Inventory pour **réserver tout de suite la taille de remplacement**. Si Order est indisponible, je réponds 503 : je ne décide pas sur des données incertaines. Si c'est Inventory, je propose un remboursement à la place. La base est **MySQL**, adaptée à un workflow à états très structuré avec des contraintes d'intégrité. Ensuite, tout est asynchrone : `RetourApprouveEvent` met à jour le ticket lié, `RetourRecuEvent` déclenche la remise en stock et le remboursement ou le remplacement, et `RetourFinaliseEvent` clôture la RMA. C'est une **saga par chorégraphie**.

*(≈ 150 mots)*

---

## Ticketing & Support Service (Med Houssaine) · slide 13

> Ticketing & Support gère les réclamations : colis endommagé, retard, article manquant. Un ticket suit trois statuts (ouvert, en cours, résolu), avec un agent assigné et une priorité. **C'est notre microservice à technologie avancée** : la messagerie entre le client et l'agent fonctionne en **temps réel grâce à WebSocket et au protocole STOMP**. Le JWT est vérifié dès la connexion, et chaque message est enregistré puis diffusé sur le canal du ticket. La base est **MongoDB** : un ticket et son fil de messages forment un document naturel, et l'ajout d'un message est atomique. Je consomme `LivraisonRetardeeEvent` pour **créer automatiquement un ticket** quand un colis est en retard : le support devient proactif. Pour la résilience, j'ai choisi un **mode dégradé** : si Order ne répond pas quand un client ouvre un ticket, le ticket est quand même créé et marqué « commande non vérifiée ». Un client qui se plaint ne doit jamais recevoir d'erreur.

*(≈ 150 mots)*

---

## Fulfillment & Billing Service (Med Ben Lakhal) · slide 14

> Fulfillment & Billing prend le relais après la commande. À la réception de `CommandeValideeEvent`, je crée la facture, le paiement, et **une expédition par fournisseur** avec son bordereau, car en dropshipping c'est le fournisseur qui expédie. Il déclare l'envoi avec le numéro de suivi, puis le transporteur nous notifie par webhook : en transit, livré ou en retard. Je publie alors `CommandeExpediee` et `CommandeLivree` vers Order, et `LivraisonRetardee` vers le support. **Pourquoi la logistique et la facturation ensemble ?** Avec le paiement à la livraison, l'encaissement est un événement de livraison. Les deux modules n'ont aucune relation entre eux et restent séparables. La base est **PostgreSQL** pour les données financières : transactions ACID et numérotation de factures sans trou. Particularité : **aucun appel synchrone**. Tout arrive par les événements, donc si le service tombe, les commandes attendent dans Kafka et sont traitées au redémarrage, sans perte.

*(≈ 150 mots)*

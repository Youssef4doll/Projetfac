# Frontend DropWear (Angular 18)

Projet à générer au sprint 1 (DW-22) :

```bash
npx @angular/cli@18.2.12 new dropwear-frontend --routing --style=scss --standalone
cd dropwear-frontend && npm install keycloak-angular@16.1.0 keycloak-js@25.0.6 @stomp/rx-stomp@2.0.0 sockjs-client@1.6.1
```

## Connexion Keycloak
- Realm `dropwear`, client **public** `dropwear-frontend` (*Authorization Code + PKCE S256*), URL `http://localhost:8180`.
- Un intercepteur HTTP ajoute `Authorization: Bearer <jwt>` à chaque appel vers `http://localhost:8080/api/**` (Gateway uniquement, jamais un service directement).
- Les gardes de route lisent les rôles du jeton : `CLIENT`, `ADMIN`, `SUPPORT`, `FOURNISSEUR`.

## Structure prévue

```
src/app/
├── core/            auth (keycloak-angular), intercepteur JWT, gardes par rôle, services HTTP par microservice
├── shared/          composants communs (carte produit, badge de statut, pagination)
├── client/          catalogue, fiche produit, panier, checkout, mes commandes, suivi, retours, tickets (chat)
├── admin/           produits/variantes, catégories, utilisateurs, commandes, RMA, factures, alertes de stock
├── support/         file des tickets, chat temps réel (STOMP /topic/tickets/{id}), approbation des RMA
└── supplier/        mes stocks, mes alertes, expéditions à préparer, réception des retours
```

## Appels par écran (via la Gateway)

| Écran | Endpoints |
|-------|-----------|
| Catalogue | `GET /api/catalog/products`, `GET /api/inventory/stocks?skus=` |
| Panier / checkout | `GET/POST /api/cart/items`, `POST /api/orders`, `POST /api/orders/{id}/validate` |
| Suivi | `GET /api/orders/me`, `GET /api/fulfillment/orders/{id}/tracking` |
| Retour / échange | `GET /api/returns/eligibility?orderId=`, `POST /api/returns`, `GET /api/returns/{id}/label` |
| Support (chat) | `POST /api/tickets`, WebSocket `/ws` → `/topic/tickets/{id}` |

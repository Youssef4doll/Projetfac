# Message à envoyer à l'équipe

**À :** Med Ben Lakhal, Aziz Arfaouii, Yasser Chebbi, Med Houssaine, Eya Frikha
**Objet :** AWD — dossier de conception Validation 1 prêt + répartition à valider

---

Salut l'équipe,

Le dossier complet de la Validation 1 est prêt dans le dépôt (`dropwear-CLASSE-GROUPE`) :

- **Documentation/** : les 9 livrables (analyse fonctionnelle, découpage, une fiche par microservice, matrice des interactions, architecture, Git, plan du dossier, présentation, questions du jury) ;
- **Documentation/Diagrams/** : tous les diagrammes PlantUML (sources + PNG/SVG) ;
- **Backend/** : les 10 projets Spring Boot créés (7 services + Gateway, Eureka, Config Server). Ils compilent, démarrent et s'enregistrent dans Eureka, et Keycloak, Kafka et les 7 bases tournent avec `docker compose --profile infra up -d`.

**Répartition proposée** (dites-moi si vous voulez changer) :

| Membre | Service | Transverse |
|--------|---------|------------|
| Youssef | Order | Gateway, Git |
| Eya | Catalog | Front, analyse fonctionnelle |
| Yasser | Inventory | Config Server, planning |
| Aziz | Exchange & Return | Kafka, résilience |
| Med Houssaine | Ticketing & Support (techno avancée : WebSocket) | Eureka, Docker Compose |
| Med Ben Lakhal | Fulfillment & Billing + **présentation du service commun** | Keycloak |

**Ce que chacun doit faire avant la répétition :**
1. Lire `Documentation/00-synthese-hypotheses-points-attention.md` (noms officiels des services, événements et endpoints : on n'en change aucun sans prévenir).
2. Relire **sa fiche** dans `Documentation/Fiches/` et son **script d'1 minute** dans `Documentation/Presentation/scripts-oraux.md`, et le chronométrer.
3. Préparer les **2 questions de son service** et les 15 questions générales (`Documentation/09-questions-jury.md`).
4. Faire **au moins 6 commits** sur sa branche (plan dans `Documentation/06-depot-git-gestion-projet.md`, §6.5), avec `git config user.name` à son vrai nom.

**Deux points à trancher ensemble :**
- Le « microservice 5 (technologie avancée) » : je propose Ticketing avec WebSocket. Il faut le **confirmer avec l'enseignant**.
- Redis : je propose de **ne pas l'utiliser** en V1 (il n'est pas dans la stack imposée). La réservation de stock se fait en PostgreSQL. Il reste une perspective.

Répétition générale proposée : [date / heure], 15 minutes chrono + 10 minutes de questions croisées.

Merci !
Youssef

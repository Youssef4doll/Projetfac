## Objet
<!-- Ce que fait la PR, en une ou deux phrases. Lier l'issue : Closes #… -->

## Service(s) concerné(s)
- [ ] user · [ ] catalog · [ ] order · [ ] inventory · [ ] return · [ ] ticketing · [ ] fulfillment · [ ] infra · [ ] front · [ ] docs

## Vérifications
- [ ] `mvn -f Backend/pom.xml compile` passe
- [ ] Les noms d'endpoints, d'événements et de topics respectent `Documentation/00` (référentiel unique)
- [ ] La fiche du service est à jour si le contrat change
- [ ] Testé localement (Postman / curl)

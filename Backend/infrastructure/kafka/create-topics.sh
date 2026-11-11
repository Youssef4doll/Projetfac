#!/bin/sh
# Crée les 11 topics métier DropWear (3 partitions) et leur topic de lettres mortes (.DLT).
# Lancé automatiquement par le conteneur « kafka-init » (docker compose --profile infra up).
set -e
BOOTSTRAP="${BOOTSTRAP:-kafka:29092}"
KT=/opt/kafka/bin/kafka-topics.sh
for topic in \
  catalogue.variante-creee \
  commande.validee \
  commande.annulee \
  stock.faible \
  stock.reapprovisionne \
  livraison.expediee \
  livraison.livree \
  livraison.retardee \
  retour.approuve \
  retour.recu \
  retour.finalise
do
  $KT --bootstrap-server "$BOOTSTRAP" --create --if-not-exists --topic "$topic" --partitions 3 --replication-factor 1
  $KT --bootstrap-server "$BOOTSTRAP" --create --if-not-exists --topic "$topic.DLT" --partitions 1 --replication-factor 1
done
$KT --bootstrap-server "$BOOTSTRAP" --list

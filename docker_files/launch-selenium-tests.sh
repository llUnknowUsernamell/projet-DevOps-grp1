#!/bin/bash

# Nom du conteneur
CONTAINER_NAME="projet-devops-jdk11"

# Redémarrer le conteneur
docker start $CONTAINER_NAME

# Attendre que le conteneur soit prêt (facultatif)
sleep 5

# Exécuter la commande dans le conteneur
docker exec -it $CONTAINER_NAME bash -c "cd /app && mvn test -Pselenium-tests"

#!/bin/bash

# ce script bash permet "d'attendre" que le fichier .war soit disponible dans le répertoire de build
WAR_FILE=$1

echo "Vérification de la présence du fichier .war..."


while [ ! -f $WAR_FILE ]; do
  echo "Le fichier .war n'est pas encore disponible, on attend..."
  sleep 5  # Vérifier toutes les 5 secondes
done

echo "Fichier .war trouvé !"

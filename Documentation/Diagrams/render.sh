#!/bin/sh
# Régénère tous les diagrammes (PNG + SVG) puis les réinjecte dans la documentation.
# Prérequis : Java 17+, Graphviz (dot) pour les diagrammes de classes et de cas d'utilisation.
set -e
cd "$(dirname "$0")"
JAR="${PLANTUML_JAR:-$HOME/.cache/plantuml-1.2024.7.jar}"
[ -f "$JAR" ] || { mkdir -p "$(dirname "$JAR")"; curl -sSL -o "$JAR" https://repo1.maven.org/maven2/net/sourceforge/plantuml/plantuml/1.2024.7/plantuml-1.2024.7.jar; }
java -jar "$JAR" -tpng -o ../png src/*.puml
java -jar "$JAR" -tsvg -o ../svg src/*.puml
python3 inject.py

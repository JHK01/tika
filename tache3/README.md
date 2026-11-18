# Tâche 3 — Analyse statique avec Semgrep

Auteur : Joe El-Hayek (JHK01)

## Objectif
Détecter les nouvelles vulnérabilités dans Apache Tika sans bloquer les modifications à cause des alertes préexistantes.

## Conception proposée
Le module retenu est tika-core. Le workflow doit comparer le commit analysé à un commit de référence avec Semgrep, retourner un code non nul en présence de nouvelles alertes et conserver les rapports JSON comme artefacts. Une action composite Rickroll doit être appelée uniquement après un échec des tests Maven, sans masquer cet échec.

## Validation prévue
Comparer une analyse initiale et une analyse après modification, vérifier le succès sans nouvelle alerte, puis vérifier l’échec avec une nouvelle alerte. Tester séparément le déclenchement du Rickroll.

## État des résultats
Les sept expériences, les rapports Semgrep et la validation du workflow ne sont pas encore réalisés. Aucun résultat expérimental n’est revendiqué dans cette version.

## Utilisation de l’intelligence artificielle
ChatGPT (OpenAI) a été utilisé pour proposer la conception et rédiger cette documentation initiale. 

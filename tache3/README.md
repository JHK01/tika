# IFT3913 — Tâche 3 : Semgrep et intégration continue

Auteur : Joe El-Hayek (JHK01)

Dépôt : https://github.com/JHK01/tika/tree/tache3

## 1. Fichiers et objectifs

| Fichier | Rôle |
|---|---|
| .github/workflows/tache3-semgrep.yml | Détection différentielle des alertes |
| .github/workflows/tache3-tests.yml | Exécution des tests Maven |
| .github/actions/rickroll/action.yml | Action composite humoristique |

Le module choisi est tika-core.

## 2. Conception du contrôle Semgrep

Le workflow récupère l'historique complet avec fetch-depth: 0. Pour une PR, il calcule le point de divergence avec la branche cible. Pour un push, il utilise le commit précédent fourni par GitHub. Si ce commit est absent, il utilise le point commun avec main.

Un premier scan JSON fournit une vue complète des alertes. Le second utilise --baseline-commit et --error : seules les nouvelles alertes doivent bloquer le job. Les rapports sont publiés même si le contrôle échoue. Les permissions sont limitées à la lecture du dépôt.

La version de Semgrep est fixée à 1.182.1. Le pack p/java reste distant et peut évoluer : la reproductibilité des règles n'est donc pas garantie. Le contrôle est séparé du build Maven et ne constitue pas encore une protection obligatoire de branche.

## 3. Protocole expérimental

Pour valider le contrôle, comparer une révision initiale avec une révision expérimentale isolée, en conservant les mêmes règles. Vérifier le code de sortie sans nouvelle alerte et avec une nouvelle alerte. Conserver les rapports complets et différentiels.

Les sept catégories à étudier sont le hachage faible, le chiffrement non recommandé, la validation de certificat, l'aléatoire non cryptographique, les permissions excessives, la désérialisation et la configuration XML.

Les expériences ne sont pas encore implémentées. Aucun résultat de détection, fichier modifié ou nombre d'alertes n'est revendiqué.

| Mesure | Résultat |
|---|---|
| Rapport initial | À mesurer |
| Rapport final | À mesurer |
| Nouvelles alertes | À mesurer |

## 4. Rickroll

Le workflow de tests lance Maven sur tika-core et ses dépendances. L'action composite affiche un message et un lien vers la vidéo dans le résumé GitHub Actions uniquement lorsque l'étape Maven échoue. Elle ne neutralise pas l'échec du test.

La validation doit vérifier un passage normal et un échec contrôlé. Les exécutions ne sont pas encore confirmées.

## 5. Liens de validation

- [Semgrep](https://github.com/JHK01/tika/actions/workflows/tache3-semgrep.yml)
- [Tests](https://github.com/JHK01/tika/actions/workflows/tache3-tests.yml)

## 6. Utilisation de l'IA

ChatGPT (OpenAI) a aidé à concevoir les workflows, écrire l'action composite et structurer la documentation. Un README d'autres étudiants fourni comme exemple a inspiré l'organisation du rapport ; leurs résultats ne sont pas repris. Les modifications et les résultats doivent être vérifiés avant remise.

[Consignes de déclaration de l'UdeM](https://boite-outils.bib.umontreal.ca/c.php?g=743753&p=5377614).

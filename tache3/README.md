# IFT3913 — Tâche 3 : Semgrep et intégration continue

Auteur : Joe El-Hayek (JHK01)

Dépôt : https://github.com/JHK01/tika/tree/tache3

## 1. Fichiers et objectifs

| Fichier | Rôle |
|---|---|
| .github/workflows/tache3-semgrep.yml | Détection différentielle des alertes |
| .github/workflows/tache3-tests.yml | Exécution des tests Maven |
| .github/actions/rickroll/action.yml | Action composite humoristique |
| tika-core/src/test/java/org/apache/tika/RickrollDemoTest.java | Test JUnit de démonstration |

Le module choisi est tika-core.

## 2. Conception du contrôle Semgrep

Le workflow récupère l'historique complet avec fetch-depth: 0. Pour une PR, il calcule le point de divergence avec la branche cible. Pour un push, il utilise le commit précédent fourni par GitHub. Si ce commit est absent, il utilise le point commun avec main.

Un premier scan JSON fournit une vue complète des alertes. Le second utilise --baseline-commit et --error : seules les nouvelles alertes doivent bloquer le job. Les rapports sont publiés même si le contrôle échoue. Les permissions sont limitées à la lecture du dépôt.

La version de Semgrep est fixée à 1.182.1. Le pack p/java reste distant et peut évoluer : la reproductibilité des règles n'est donc pas garantie. Le contrôle est séparé du build Maven et ne constitue pas encore une protection obligatoire de branche.

## 3. Protocole expérimental

Pour valider le contrôle, comparer une révision initiale avec une révision expérimentale isolée, en conservant les mêmes règles. Vérifier le code de sortie sans nouvelle alerte et avec une nouvelle alerte. Conserver les rapports complets et différentiels.

Les sept catégories à étudier sont le hachage faible, le chiffrement non recommandé, la validation de certificat, l'aléatoire non cryptographique, les permissions excessives, la désérialisation et la configuration XML.

Les sept scénarios et leurs protections sont détaillés dans [le dossier des expériences](experiences/README.md). Les expériences de détection ne sont pas encore implémentées. Aucun résultat de détection, fichier modifié ou nombre d'alertes n'est revendiqué.

| Mesure | Résultat |
|---|---|
| Rapport initial | À mesurer |
| Rapport final | À mesurer |
| Nouvelles alertes | À mesurer |

## 4. Rickroll

Le workflow de tests lance Maven sur tika-core et ses dépendances. L'action composite affiche un message et un lien vers la vidéo dans le résumé GitHub Actions uniquement lorsque l'étape Maven échoue. Elle ne neutralise pas l'échec du test.

Le test RickrollDemoTest est désactivé sauf lorsque TIKA_RICKROLL_DEMO vaut true. Dans Actions, sélectionner Tache 3 - Tests, Run workflow, la branche tache3 et activer force_failure pour demander un échec volontaire. Sans cette option, le test de démonstration est ignoré. Le résultat attendu est un job rouge avec le lien Rickroll dans le résumé lors de l'échec contrôlé. Le déclenchement du Rickroll est confirmé lors de [l'exécution Tests #10](https://github.com/JHK01/tika/actions/runs/44240383447), après un échec Checkstyle. Après correction de la licence et des imports du test (commit e120356), les exécutions Tests #11 et Semgrep #12 sont vertes (47 s et 43 s, respectivement, selon les captures GitHub Actions). L'échec volontaire via force_failure reste à vérifier.

## 5. Liens de validation

Le succès de Semgrep #12 confirme le passage du contrôle sur le commit e120356, mais ne démontre pas encore la détection de sept nouvelles vulnérabilités.

- [Semgrep](https://github.com/JHK01/tika/actions/workflows/tache3-semgrep.yml)
- [Tests](https://github.com/JHK01/tika/actions/workflows/tache3-tests.yml)

## Expérience complémentaire : contrôle des sept marqueurs

Le [workflow expérimental](https://github.com/JHK01/tika/actions/workflows/tache3-experiences.yml) utilise sept [règles locales](experiences/marker-rules.yml) et un [fichier Java isolé](experiences/fixtures/SecurityMarkers.java). Chaque règle correspond à un identifiant CWE distinct, mais détecte uniquement une chaîne de caractères inoffensive.

Le scénario initial contient une classe vide ; le scénario final contient sept marqueurs. Une assertion Python vérifie que les rapports contiennent respectivement zéro et sept résultats. Les deux rapports JSON sont publiés dans l'artefact marker-reports.

Cette expérience vérifie le comptage des résultats et la génération des rapports. Elle ne démontre ni sept vulnérabilités réelles ni leur détection par le pack officiel Java, et ne remplace pas la validation du mode baseline sur du code vulnérable. Les exécutions Experiences #1 (commit 17d1917, 58 s) et #2 (commit 9926a26, 39 s) sont réussies selon les résultats GitHub Actions observés. L'assertion du workflow impose 0 résultat avant et 7 après ; le succès confirme ces valeurs pour les marqueurs pédagogiques. Les rapports sont accessibles dans les artefacts des exécutions. Les marqueurs ne constituent pas des vulnérabilités réelles.

## Audit complémentaire avec les règles Java officielles

L'[exécution Audit Java #1](https://github.com/JHK01/tika/actions/runs/44242499547) (commit a509e53) a réussi en 34 secondes. Le scan en lecture seule de tika-core avec p/java a produit un artefact JSON et trois alertes existantes :

| Règle | Nombre | Risque et correction à examiner |
|---|---:|---|
| java.lang.security.audit.crypto.unencrypted-socket.unencrypted-socket | 1 | Transmission non chiffrée ; privilégier TLS lorsque nécessaire. |
| java.lang.security.audit.xxe.saxparserfactory-disallow-doctype-decl-missing.saxparserfactory-disallow-doctype-decl-missing | 1 | Risque XXE ; vérifier la désactivation des DTD. |
| java.lang.security.xmlinputfactory-possible-xxe.xmlinputfactory-possible-xxe | 1 | Risque XXE ; vérifier la désactivation des entités externes et des DTD. |

Total : 3 alertes statiques observées, sans confirmation de leur exploitabilité. Le rapport est disponible dans l'artefact audit-java. Ce résultat constitue une mesure de référence sur cette révision, et non une comparaison avant/après de sept nouvelles vulnérabilités.

## Validation du blocage différentiel

L'[exécution Validation baseline #1](https://github.com/JHK01/tika/actions/runs/44242964509), commit 7e7027b, a réussi en 26 secondes. Dans un dépôt Git temporaire isolé, une règle locale détecte un marqueur inoffensif.

| Mesure | Résultat observé |
|---|---:|
| Alertes avant | 0 |
| Alertes différentielles après | 1 |
| Code de sortie Semgrep avec --error | 1 |

Les rapports before.json et after.json sont publiés dans l'artefact baseline-demo-reports. Le workflow de démonstration réussit car il vérifie explicitement le code de sortie 1 attendu. Cela valide le mécanisme différentiel sur une règle locale, mais ne prouve pas la détection de sept nouvelles vulnérabilités par p/java.

## Déclaration d'utilisation de l'IA

ChatGPT: assistance à la conception, au code et à la rédaction.

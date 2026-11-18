# Expériences de sécurité — tika-core

Ces scénarios sont des exercices d'analyse, non des modifications du code de production. Les règles indiquées sont des pistes à vérifier avec la configuration Semgrep utilisée.

| CWE | Catégorie | Composant à examiner | Risque | Protection à vérifier |
|---|---|---|---|---|
| 328 | Hachage faible | InputStreamDigester | Collisions | SHA-256 pour les usages d'intégrité |
| 327 | Chiffrement faible | CryptoParser | Divulgation de motifs | Chiffrement authentifié |
| 295 | Validation TLS | NetworkParser | Usurpation du serveur | Validation standard des certificats |
| 330 | Aléatoire faible | TemporaryResources | Prédictibilité | Création sécurisée de fichiers temporaires |
| 732 | Permissions excessives | StreamCache | Accès non autorisé | Permissions minimales |
| 502 | Désérialisation | TrainedModelDetector | Reconstruction d'objets non fiables | Format de données limité et validation |
| 611 | Entités XML externes | MimeTypesFactory | Accès non autorisé aux ressources | Parseur XML sécurisé |

## Protocole

Pour chaque catégorie, relever la règle Semgrep applicable, examiner le code original, identifier la protection présente et consigner le résultat du scan. Ne pas déployer de version volontairement vulnérable.

Les résultats restent à mesurer. Ce document ne prouve pas que sept nouvelles alertes ont été introduites.

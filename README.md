# Notification Service — Bibliothèque (Microservice)

Service responsable d'envoyer :
- des **rappels par e‑mail** aux étudiants dont les livres arrivent à échéance,
- un **rapport** à l'**administrateur** listant les livres **expirés**.

Le service s'exécute **périodiquement** (via Kubernetes CronJob) ou en mode boucle.

## Sommaire
- [Architecture](#architecture)
- [Variables d'environnement](#variables-denvironnement)
- [Lancement local](#lancement-local)
- [Tests & couverture](#tests--couverture)
- [Docker](#docker)
- [Déploiement Kubernetes](#déploiement-kubernetes)
- [Logs](#logs)
- [Sécurité](#sécurité)

## Architecture

```
Notification Service
  ├─ Appelle Lending Management: GET /getExpiredBooks?days_before=X
  │    ↳ JSON attendu: { "dueSoon": [...], "expired": [...] }
  └─ Envoie e-mails via SMTP (TLS 587)
```

> Adaptez le parsing si le payload réel diffère.

## Variables d'environnement

| Nom | Obligatoire | Défaut | Description |
|-----|-------------|--------|-------------|
| `API_URL` | non | `http://lending-management:8080/getExpiredBooks` | Endpoint du service de prêt |
| `DAYS_BEFORE` | non | `3` | Jours avant échéance pour rappeler les utilisateurs |
| `SENDER_EMAIL` | **oui** | | Adresse expéditeur / login SMTP |
| `SMTP_PASSWORD` | **oui** | | Mot de passe ou App Password SMTP |
| `SMTP_USERNAME` | non | `SENDER_EMAIL` | Login SMTP si différent |
| `SMTP_SERVER` | non | `smtp-mail.outlook.com` | Serveur SMTP |
| `SMTP_PORT` | non | `587` | Port TLS |
| `ADMIN_EMAIL` | recommandé | | Destinataire du rapport "expirés" |
| `RUN_MODE` | non | `cron` | `cron` (1 run puis exit) ou `loop` |
| `CHECK_INTERVAL` | non | `86400` | Intervalle en secondes si `RUN_MODE=loop` |
| `REQUEST_TIMEOUT` | non | `10` | Timeout HTTP (s) |
| `VERIFY_TLS` | non | `true` | Vérif TLS (HTTP) |

## Lancement local

```bash
python -m venv .venv && source .venv/bin/activate  # Windows: .venv\Scripts\activate
pip install -r requirements.txt

export API_URL="http://localhost:8080/getExpiredBooks"
export SENDER_EMAIL="notifications@exemple.tld"
export SMTP_PASSWORD="***"
export ADMIN_EMAIL="admin@exemple.tld"

python notif_users_expiration.py
```

## Tests & couverture

```bash
pytest -q --cov=./ --cov-report=term-missing
```

## Docker

Build & run local:
```bash
docker build -t notif:local .
docker run --rm \
  -e API_URL="http://host.docker.internal:8080/getExpiredBooks" \
  -e SENDER_EMAIL="notifications@exemple.tld" \
  -e SMTP_PASSWORD="***" \
  -e ADMIN_EMAIL="admin@exemple.tld" \
  notif:local
```

## Déploiement Kubernetes

1. **Secrets & config** :
```bash
kubectl apply -f k8s/secret-smtp.yaml     # modifiez le mot de passe d'abord
kubectl apply -f k8s/configmap.yaml       # adaptez les emails/URL
```
2. **RBAC + NetworkPolicy** :
```bash
kubectl apply -f k8s/rbac-and-netpol.yaml
```
3. **CronJob** :
   - Remplacez `YOUR_DOCKERHUB` par votre namespace Docker Hub dans `k8s/cronjob.yaml`.
```bash
kubectl apply -f k8s/cronjob.yaml
```
4. **Vérification** :
```bash
kubectl get cronjobs
kubectl get jobs
kubectl logs job/notif-service-<hash>
```

## Logs

Les logs applicatifs sont sur `stdout` (récupérables via `kubectl logs`).  
Niveaux configurables avec `LOG_LEVEL=DEBUG|INFO|WARNING|ERROR`.

## Sécurité

- Conteneur **non-root** (user `app`), rootfs en lecture seule en K8s.
- Secrets SMTP stockés dans `Secret` K8s, non commités.
- `NetworkPolicy` egress minimale (Lending Service + SMTP).
- Scan d'image via **Trivy** dans le pipeline CI.

---

### CI/CD GitHub Actions

- Tests et linting à chaque PR/push
- Build & push image vers Docker Hub (configurer `DOCKERHUB_USERNAME` et `DOCKERHUB_TOKEN` dans *Settings → Secrets and variables → Actions*).

---

### Remarques

- Si vous utilisez Gmail, créez un **App Password** (compte avec 2FA) et utilisez `smtp.gmail.com:587`.
- Si l'API de Lending renvoie un autre schéma, adaptez la fonction `fetch_books()`.

import os
import sys
import time
import logging
from typing import List, Dict, Any, Tuple

import requests
import smtplib
from email.mime.text import MIMEText

# -------- Configuration via variables d'environnement --------
API_URL = os.getenv("API_URL", "http://lending-management:8080/getExpiredBooks")
DAYS_BEFORE = int(os.getenv("DAYS_BEFORE", "3"))

SMTP_SERVER = os.getenv("SMTP_SERVER", "smtp-mail.outlook.com")
SMTP_PORT = int(os.getenv("SMTP_PORT", "587"))
SMTP_USERNAME = os.getenv("SMTP_USERNAME", os.getenv("SENDER_EMAIL", ""))
SENDER_EMAIL = os.getenv("SENDER_EMAIL", "")
SMTP_PASSWORD = os.getenv("SMTP_PASSWORD", os.getenv("MAILPASSWORD", ""))
ADMIN_EMAIL = os.getenv("ADMIN_EMAIL", "")

RUN_MODE = os.getenv("RUN_MODE", "cron").lower()
CHECK_INTERVAL = int(os.getenv("CHECK_INTERVAL", "86400"))

REQUEST_TIMEOUT = float(os.getenv("REQUEST_TIMEOUT", "10"))
VERIFY_TLS = os.getenv("VERIFY_TLS", "true").lower() not in {"0", "false", "no"}

# -------- Logging --------
logging.basicConfig(
    level=os.getenv("LOG_LEVEL", "INFO"),
    format="%(asctime)s %(levelname)s %(message)s",
)
logger = logging.getLogger("notification-service")


def fetch_books(days_before: int) -> Tuple[List[Dict[str, Any]], List[Dict[str, Any]]]:
    """
    Récupère deux listes auprès du Lending Service:
    - dueSoon: livres à rendre dans <= days_before jours
    - expired: livres déjà expirés
    Attendu JSON: { "dueSoon": [...], "expired": [...] }
    """
    try:
        resp = requests.get(
            API_URL,
            params={"days_before": days_before},
            timeout=REQUEST_TIMEOUT,
            verify=VERIFY_TLS,
        )
        resp.raise_for_status()
        data = resp.json()
        due_soon = data.get("dueSoon", [])
        expired = data.get("expired", [])
        if not isinstance(due_soon, list) or not isinstance(expired, list):
            raise ValueError("Payload invalide: 'dueSoon'/'expired' non-liste")
        return due_soon, expired
    except Exception as exc:
        logger.exception("Erreur lors de l'appel Lending Management: %s", exc)
        return [], []


def _smtp_client():
    client = smtplib.SMTP(SMTP_SERVER, SMTP_PORT)
    client.ehlo()
    client.starttls()
    client.login(SMTP_USERNAME or SENDER_EMAIL, SMTP_PASSWORD)
    return client


def send_email(to_email: str, subject: str, body: str) -> None:
    msg = MIMEText(body)
    msg["Subject"] = subject
    msg["From"] = SENDER_EMAIL
    msg["To"] = to_email

    with _smtp_client() as smtp:
        smtp.send_message(msg)


def notify_user_due_soon(items: List[Dict[str, Any]]):
    """Envoie 1 mail par utilisateur avec la liste de ses livres bientôt dus."""
    by_user: Dict[str, List[Dict[str, Any]]] = {}
    for it in items:
        email = it.get("userEmail") or it.get("user_email")
        if not email:
            continue
        by_user.setdefault(email, []).append(it)

    sent = 0
    for email, books in by_user.items():
        lines = []
        for b in books:
            title = b.get("bookTitle", b.get("book_id", "Livre"))
            due = b.get("dueDate") or b.get("due_date", "?")
            lines.append(f"- {title} (à rendre le {due})")
        body = (
            "Bonjour,\n\n"
            "Rappel: les livres suivants arrivent à échéance:\n"
            + "\n".join(lines)
            + "\n\nMerci de les rendre dans les temps."
        )
        try:
            send_email(email, "Rappel de retour de livre", body)
            sent += 1
            logger.info("Rappel envoyé à %s (%d livres).", email, len(books))
        except Exception:
            logger.exception("Échec d'envoi mail utilisateur %s", email)
    return sent


def notify_admin_expired(items: List[Dict[str, Any]]):
    """Envoie un rapport admin pour les livres expirés."""
    if not ADMIN_EMAIL or not items:
        return False

    lines = []
    for b in items:
        user = b.get("userEmail") or b.get("user_email", "?")
        title = b.get("bookTitle", b.get("book_id", "Livre"))
        due = b.get("dueDate") or b.get("due_date", "?")
        lines.append(f"- {title} (due {due}) — emprunteur: {user}")

    body = (
        "Bonjour Admin,\n\n"
        "Livres expirés détectés:\n" + "\n".join(lines) + "\n"
    )
    try:
        send_email(ADMIN_EMAIL, "Rapport — livres expirés", body)
        logger.info("Rapport admin envoyé (%d éléments).", len(items))
        return True
    except Exception:
        logger.exception("Échec d'envoi du rapport admin")
        return False


def run_once() -> int:
    due_soon, expired = fetch_books(DAYS_BEFORE)
    sent_users = notify_user_due_soon(due_soon)
    admin_sent = notify_admin_expired(expired)
    logger.info(
        "Cycle terminé — mails utilisateurs: %d, mail admin: %s",
        sent_users,
        "OK" if admin_sent else "non",
    )
    return sent_users


def main() -> None:
    missing = [k for k in ["SENDER_EMAIL", "SMTP_PASSWORD"] if not globals()[k]]
    if missing:
        logger.error("Variables d'env manquantes: %s", ", ".join(missing))
        sys.exit(2)

    logger.info("Notification Service démarré (mode=%s)", RUN_MODE)

    if RUN_MODE == "loop":
        while True:
            run_once()
            time.sleep(CHECK_INTERVAL)
    else:
        run_once()


if __name__ == "__main__":
    main()

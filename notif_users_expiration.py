import os
import requests
import smtplib
from email.mime.text import MIMEText
from datetime import datetime
import time

# Configuration
API_URL = "https://api.library.com/books"
SMTP_SERVER = "smtp-mail.outlook.com"
SMTP_PORT = 587
SENDER_EMAIL = "SaintBranchs-Biblio@outlook.com"
SENDER_PASSWORD = os.environ["MAILPASSWORD"]
ADMIN_EMAIL = "enzovargaspro83520@gmail.com"
CHECK_INTERVAL = 86400


def fetch_books():
    """Récupérer les livres de l'API"""
    response = requests.get(API_URL)
    return response.json()


def send_email(to_email, subject, body):
    """Envoyer une notification par email"""
    msg = MIMEText(body)
    msg['Subject'] = subject
    msg['From'] = SENDER_EMAIL
    msg['To'] = to_email

    with smtplib.SMTP(SMTP_SERVER, SMTP_PORT) as server:
        server.starttls()
        server.login(SENDER_EMAIL, SENDER_PASSWORD)
        server.send_message(msg)


def check_and_notify():
    """Vérifier les livres et envoyer des notifications"""
    books = fetch_books()
    expired_books = []

    for book in books:
        send_email(
            book['user_email'],
            "Rappel de retour de livre",
            f"Veuillez retourner le livre avec ID {book['book_id']}."
        )
        expired_books.append(book)

    if expired_books:
        admin_notification = "Les livres suivants sont en retard :\n"
        for book in expired_books:
            admin_notification += (
                f"- Livre ID {book['book_id']},"
                f" emprunté par {book['user_email']}\n"
            )
        send_email(ADMIN_EMAIL, "Rapport des livres en retard",
                   admin_notification)


def run_service():
    """Exécuter le service de notification"""
    print("Démarrage du service de notification de la bibliothèque")
    while True:
        print(f"Vérification des livres à {datetime.now()}")
        check_and_notify()
        time.sleep(CHECK_INTERVAL)


if __name__ == "__main__":
    run_service()

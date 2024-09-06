import os
import requests
import smtplib
from email.mime.text import MIMEText
from datetime import datetime, timedelta
import time


# Configuration
API_URL = "https://api.library.com/books"
SMTP_SERVER = "smtp.gmail.com"
SMTP_PORT = 587
SENDER_EMAIL = "noreply.saintbranchs@gmail.com"
SENDER_PASSWORD = os.environ["MAILPASSWORD"]
ADMIN_EMAIL = "admin@library.com"
CHECK_INTERVAL = 86400  # 24 hours in seconds

def fetch_books():
    """Fetch books from the API"""
    response = requests.get(API_URL)
    return response.json()

def send_email(to_email, subject, body):
    """Send an email notification"""
    msg = MIMEText(body)
    msg['Subject'] = subject
    msg['From'] = SENDER_EMAIL
    msg['To'] = to_email

    with smtplib.SMTP(SMTP_SERVER, SMTP_PORT) as server:
        server.starttls()
        server.login(SENDER_EMAIL, SENDER_PASSWORD)
        server.send_message(msg)

def check_and_notify():
    """Check for books nearing due date and send notifications"""
    books = fetch_books()
    today = datetime.now()
    notification_threshold = today + timedelta(days=3)
    expired_books = []

    for book in books:
        due_date = datetime.strptime(book['due_date'], '%Y-%m-%d')
        if today <= due_date <= notification_threshold:
            send_email(
                book['user_email'],
                "Book Return Reminder",
                f"Please return '{book['title']}' by {book['due_date']}."
            )
        elif due_date < today:
            expired_books.append(book)

    if expired_books:
        admin_notification = "The following books are overdue:\n"
        for book in expired_books:
            admin_notification += f"- '{book['title']}' due on {book['due_date']}, borrowed by {book['user_email']}\n"
        send_email(ADMIN_EMAIL, "Overdue Books Report", admin_notification)

def run_service():
    """Run the notification service"""
    print("Starting Library Notification Service")
    while True:
        print(f"Checking books at {datetime.now()}")
        check_and_notify()
        time.sleep(CHECK_INTERVAL)

if __name__ == "__main__":
    run_service()
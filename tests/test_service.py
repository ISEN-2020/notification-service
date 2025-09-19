import notif_users_expiration as svc
from unittest.mock import patch, MagicMock
import responses


@responses.activate
def test_run_once_sends_user_and_admin_emails(monkeypatch):
    payload = {
        "dueSoon": [
            {"userEmail": "u1@mail.com", "bookTitle": "A", "dueDate": "2025-10-01"},
            {"userEmail": "u1@mail.com", "bookTitle": "B", "dueDate": "2025-10-02"},
            {"userEmail": "u2@mail.com", "bookTitle": "C", "dueDate": "2025-10-03"},
        ],
        "expired": [
            {"userEmail": "u3@mail.com", "bookTitle": "X", "dueDate": "2025-09-01"}
        ],
    }
    responses.add(responses.GET, svc.API_URL, json=payload, status=200)

    monkeypatch.setenv("SENDER_EMAIL", "no-reply@test.com")
    monkeypatch.setenv("SMTP_PASSWORD", "secret")
    monkeypatch.setenv("ADMIN_EMAIL", "admin@test.com")

    mock_smtp = MagicMock()
    mock_ctx = MagicMock()
    mock_ctx.__enter__.return_value = mock_smtp
    mock_ctx.__exit__.return_value = False

    with patch.object(svc, "_smtp_client", return_value=mock_ctx):
        sent = svc.run_once()

    assert sent == 2  # u1 + u2
    assert mock_smtp.send_message.call_count == 3  # 2 users + 1 admin


@responses.activate
def test_fetch_books_handles_error():
    responses.add(responses.GET, svc.API_URL, status=500)
    due_soon, expired = svc.fetch_books(3)
    assert due_soon == []
    assert expired == []

# import unittest
# from unittest.mock import patch

# import os

# # Set the environment variable before importing the module
# os.environ['MAILPASSWORD'] = 'Cristaline123'

# from notif_users_expiration import fetch_books
# from notif_users_expiration import send_email
# from notif_users_expiration import check_and_notify

# class TestNotifUsersExpiration(unittest.TestCase):

#     @patch('notif_users_expiration.requests.get')
#     def test_fetch_books(self, mock_get):
#         expected_response = [
#             {"user_email": "rapdpx@gmail.com", "book_id": 123},
#             {"user_email": "rapdrgpx@gmail.com", "book_id": 123456}
#         ]
#         mock_get.return_value.json.return_value = expected_response

#         books = fetch_books()
#         self.assertEqual(books, expected_response)

#     @patch('notif_users_expiration.smtplib.SMTP')
#     def test_send_email(self, mock_smtp):
#         to_email = "test@example.com"
#         subject = "Test Subject"
#         body = "Test Body"

#         send_email(to_email, subject, body)

#         mock_smtp.assert_called_with('smtp-mail.outlook.com', 587)
#         inst = mock_smtp.return_value.__enter__.return_value
#         inst.starttls.assert_called_once()
#         inst.login.assert_called_once_with('SaintBranchs-Biblio@outlook.com',
#                                            "Cristaline123")
#         inst.send_message.assert_called_once()

#     @patch('notif_users_expiration.fetch_books')
#     @patch('notif_users_expiration.send_email')
#     def test_check_and_notify(self, mock_send_email, mock_fetch_books):
#         mock_fetch_books.return_value = [
#             {"user_email": "rapdpx@gmail.com", "book_id": 123},
#             {"user_email": "rapdrgpx@gmail.com", "book_id": 123456}
#         ]
    
#         check_and_notify()
    
#         self.assertEqual(mock_send_email.call_count, 3)
#         mock_send_email.assert_any_call(
#             "rapdpx@gmail.com",
#             "Rappel de retour de livre",
#             "Veuillez retourner le livre avec ID 123."
#         )
#         mock_send_email.assert_any_call(
#             "rapdrgpx@gmail.com",
#             "Rappel de retour de livre",
#             "Veuillez retourner le livre avec ID 123456."
#         )
#         mock_send_email.assert_any_call(
#             "enzovargaspro83520@gmail.com",
#             "Rapport des livres en retard",
#             "Les livres suivants sont en retard :\n"
#             "- Livre ID 123, emprunté par rapdpx@gmail.com\n"
#             "- Livre ID 123456, emprunté par rapdrgpx@gmail.com\n"
#         )


# if __name__ == '__main__':
#     unittest.main()

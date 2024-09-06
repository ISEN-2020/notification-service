# Library Notification Service

This application is a notification service for a library system. It checks for books that are due soon or overdue and sends email notifications to users and the administator e-mail.

## Features

- Fetches overdue book data from a library API
- Sends email notifications to users for books due within 3 days
- Sends a report to the admin for overdue books
- Runs as a continuous service, checking on a daily basis

## Requirements

- Docker
- SMTP server credentials (the app is configured to use Outlook's SMTP server)

## Configuration

The application uses the following environment variables:

- `MAILPASSWORD`: The password for the sender email account

Other configuration parameters are set in the `notif_users_expiration.py` file:

- `API_URL`: The URL of the library API
- `SMTP_SERVER` and `SMTP_PORT`: SMTP server details
- `SENDER_EMAIL`: The email address used to send notifications
- `ADMIN_EMAIL`: The email address of the library administrator
- `CHECK_INTERVAL`: The interval between checks (in seconds, default is 86400 for daily checks)

## Installation and Running

1. Clone this repository to your local machine.
2. Place yourself in it.
3. Deploy your application with the following command:
   ```
   kubectl apply -f deployment.yaml
   ```
4. In order to access your application, apply the following command:
   ```
   kubectl apply -f deployment.yaml
   ```
5. Build the Docker image:
   ```
   docker build -t library-notification-service .
   ```

6. Run the Docker container:
   ```
   docker run -e MAILPASSWORD=Cristaline123 library-notification-service
   ```
By default the password of the sender email is `Cristaline123`

## File Structure

- `notif_users_expiration.py`: The main Python script that runs the notification service
- `requirements.txt`: List of Python dependencies
- `Dockerfile`: Instructions for building the Docker image
- `Notif-docker.yml`: GitHub Actions workflow for building, analyzing, and scanning the application

## Security and code maintainability

This project uses GitHub Actions for:
- Linting with flake8
- Generating Software Bill of Materials (SBOM) for both Python code and Docker image
- Running SonarCloud for code quality and security analysis
- Scanning the Docker image with Trivy for vulnerabilities



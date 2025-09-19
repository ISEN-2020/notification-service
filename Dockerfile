FROM python:3.11-slim

RUN addgroup --system app && adduser --system --ingroup app app
WORKDIR /app

COPY requirements.txt .
RUN pip install --no-cache-dir -r requirements.txt

COPY . .

USER app
ENV RUN_MODE=cron

CMD ["python", "-u", "notif_users_expiration.py"]

# Todo App

A full-stack todo app I built to get properly hands-on with Spring Boot, Spring Security, and React. You can sign up, log in, and manage your own to-do list - and there's an admin role that can manage users.

## What it does

- Register an account and log in
- Create, edit, and delete your own todos, each with a status (pending / in progress / completed / cancelled)
- Admins can list all users, change their role, enable/disable them, or remove them
- Stays logged in across refreshes, and quietly renews your session in the background so you don't get kicked out mid-use

## Tech

- Backend: Java 21, Spring Boot 3.5, Spring Security, Spring Data JPA, PostgreSQL
- Frontend: React, React Router, Axios, Bootstrap
- Auth: JWT — RSA-signed access + refresh tokens

## Running it locally

You'll need Java 21, Node.js, and PostgreSQL.

1. Database — create the database and a user:

```sql
CREATE DATABASE todo_db;
CREATE USER devuser WITH PASSWORD 'devuser';
GRANT ALL PRIVILEGES ON DATABASE todo_db TO devuser;
```

The tables and a starter admin account are created automatically on first startup.

2. Backend — the DB credentials are read from environment variables, so set them and run the app (or just hardcode them):

```
PSQL_USERNAME=devuser
PSQL_PASSWORD=devuser
```

Then start it:

```
# Windows
mvnw.cmd spring-boot:run

# Linux / macOS
sh mvnw spring-boot:run
```

3. Frontend

The frontend repo is on: https://github.com/Ralek018/todo-frontend
```bash
cd frontend
npm install
npm start
```

Runs on `http://localhost:3000`.

### Or with Docker

If you have Docker, you can skip installing PostgreSQL and just run:

```bash
docker compose up --build
```

That starts the database and the API together. Run the frontend with `npm start` as above.

## Logging in

A default admin is created on first run:

- Username: `admin`
- Password: `admin123`

Anyone else can sign up on the register page and gets a normal user account.

## A note on the keys

The RSA keys live in the repo (`src/main/resources/certs/`) so the project runs straight away. In a real deployment I'd keep them out of the codebase and load them from environment variables or a secrets manager - they're committed here only to make the demo easy to run.

## Things I'd add with more time

- Pagination and filtering on the todo list
- Persisting refresh tokens so they can be revoked
- More front-end polish, loading states and friendlier error messages
- Unit tests 

---

Built by Aleksa Radivojevic

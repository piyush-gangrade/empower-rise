# Empower Rise - Fundraising Platform

A full-stack fundraising and donation platform built with Spring Boot (backend) and React + Vite (frontend).

## 📁 Project Structure

```
Empower_rise/
├── frontend/              # React + Vite frontend
│   ├── src/
│   │   ├── pages/         # Page components
│   │   ├── components/    # Reusable UI components
│   │   ├── utils/         # Auth and API helpers
│   │   ├── App.jsx
│   │   ├── App.css
│   │   └── main.jsx
│   ├── public/
│   ├── index.html
│   ├── vite.config.js     # Vite configuration (API proxy)
│   ├── package.json
│   └── tailwind.config.js
├── mvnw                    # Maven wrapper executable
├── mvnw.cmd                # Maven wrapper batch file (Windows)
├── pom.xml                 # Backend Maven configuration
├── .mvn/                   # Maven configuration files
└── src/                    # Backend Java source code
    ├── main/
    │   ├── java/com/empower/
    │   │   ├── EmpowerApplication.java
    │   │   ├── annotation/
    │   │   ├── aspect/
    │   │   ├── config/
    │   │   ├── controller/
    │   │   ├── dto/
    │   │   ├── enums/
    │   │   ├── exceptions/
    │   │   ├── response/
    │   │   └── utils/
    │   └── resources/application.properties
    └── test/
```

## ✅ Requirements

- **Java 17+**
- **Node.js 18+**
- **npm** (comes with Node.js)
- **Maven** (not required globally because the project includes `mvnw` / `mvnw.cmd`)
- **PostgreSQL** (local database server)

## 🗄️ Database Configuration

The backend is configured to use PostgreSQL with the following default connection in `backend/src/main/resources/application.properties`:

- URL: `jdbc:postgresql://localhost:5432/task_management`
- Username: `postgres`
- Password: `root`
- Driver: `org.postgresql.Driver`
- Hibernate DDL: `spring.jpa.hibernate.ddl-auto=update`

If you want to use a different database or credentials, update `backend/src/main/resources/application.properties` accordingly.

## 🚀 Setup Instructions

### 1. Backend Setup

```bash
# From the repository root
# On Windows use mvnw.cmd, on macOS/Linux use ./mvnw
./mvnw clean install
./mvnw spring-boot:run
```

The backend runs on `http://localhost:8080` by default.

### 2. Frontend Setup

```bash
cd frontend
npm install
npm run dev
```

The frontend runs on `http://localhost:5173`.

### 3. Access the App

Open the browser at:

- `http://localhost:5173` for the frontend
- API requests are proxied to `http://localhost:8080`

## 🔧 Developer Workflow

### Running Backend + Frontend Together

Terminal 1:
```bash
./mvnw spring-boot:run
```

Terminal 2:
```bash
cd frontend
npm run dev
```

### Building for Production

Backend:
```bash
./mvnw clean package
```

Frontend:
```bash
cd frontend
npm run build
```

## 🔗 Frontend Proxy Configuration

The Vite development server forwards `/api/*` requests to the backend. This proxy is configured in `frontend/vite.config.js`.

## 🔐 Authentication Flow

- Uses **JWT-based authentication**
- User login is via `POST /api/v1/User/login`
- Admin login is via `POST /api/v1/admin/login`
- Tokens are stored in `localStorage`
- Protected routes redirect to login when unauthorized

## 🧩 Main Features

- User registration and login
- Admin login and role-based admin portal
- Fundraising campaign management
- Donation processing and history
- Blog creation, listing, and viewing
- Category filtering and admin category management

## 📌 Important Notes

- Backend source is at the repository root
- Frontend source is inside `frontend/`
- The app uses localStorage for session data and JWT tokens
- Make sure the backend is running before using the frontend dev server

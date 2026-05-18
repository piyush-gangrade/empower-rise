# Empower Rise Project Structure

## Directory Layout

```
Empower_rise/
├── backend/              # Spring Boot Java backend
│   ├── mvnw
│   ├── mvnw.cmd
│   ├── pom.xml
│   ├── .mvn/
│   └── src/
│       ├── main/
│       │   ├── java/com/empower/...
│       │   └── resources/
│       └── test/
├── frontend/             # React Vite frontend
│   ├── src/
│   ├── public/
│   ├── package.json
│   ├── vite.config.js
│   └── index.html
└── .git/
```

## Running the Project

### Backend (Spring Boot)

```bash
# Navigate to backend directory
cd backend

# Build the project
mvn clean install

# Run the application (default port: 8080)
mvn spring-boot:run
```

### Frontend (React + Vite)

```bash
# Navigate to frontend directory
cd frontend

# Install dependencies
npm install

# Run development server (default port: 5173)
npm run dev

# Build for production
npm run build
```

## API Configuration

The frontend development server is configured with a proxy that forwards API calls to the backend:
- Frontend: `http://localhost:5173`
- Backend: `http://localhost:8080`
- Proxy path: `/api/*` → `http://localhost:8080/api/*`

## Architecture

- **Backend**: Spring Boot REST API
  - User registration & login: `POST /api/v1/User`, `POST /api/v1/User/login`
  - JWT authentication
  - Fund, donation, blog, and category management

- **Frontend**: React + React Router + Vite
  - User registration & login flow
  - Dashboard for authenticated users
  - Fund exploration and donation interface
  - Blog and category browsing

## Authentication Flow

1. User registers → Account created
2. User logs in → JWT token stored in localStorage
3. Token automatically included in API requests
4. Dashboard accessible only when authenticated

# Empower Rise - Fundraising Platform

A full-stack fundraising and donation platform built with Spring Boot (backend) and React (frontend).

## 📁 Project Structure

```
Empower_rise/
├── backend/                # Spring Boot Java backend
│   ├── mvnw               # Maven wrapper executable
│   ├── mvnw.cmd          # Maven wrapper batch file (Windows)
│   ├── pom.xml           # Maven dependencies and configuration
│   ├── .mvn/             # Maven configuration
│   └── src/              # Java source code
│       ├── main/
│       │   ├── java/com/empower/
│       │   │   ├── EmpowerApplication.java
│       │   │   ├── annotation/
│       │   │   ├── aspect/
│       │   │   ├── config/
│       │   │   ├── controller/
│       │   │   ├── dto/
│       │   │   ├── enums/
│       │   │   ├── exceptions/
│       │   │   ├── response/
│       │   │   └── utils/
│       │   └── resources/application.properties
│       └── test/
│
├── frontend/              # React + Vite frontend
│   ├── src/
│   │   ├── pages/         # Page components
│   │   ├── components/    # Reusable components
│   │   ├── lib/           # Utilities
│   │   ├── App.jsx
│   │   ├── App.css
│   │   └── main.jsx
│   ├── public/
│   ├── index.html
│   ├── vite.config.js     # Vite configuration (includes API proxy)
│   ├── package.json
│   └── tailwind.config.js
│
└── PROJECT_STRUCTURE.md   # Detailed structure documentation
```

## 🚀 Quick Start

### Prerequisites

- **Java 17+** (for backend)
- **Node.js 18+** (for frontend)
- **Maven** (bundled with mvnw in backend folder)

### Backend Setup

```bash
# Navigate to backend
cd backend

# Build with Maven
./mvnw clean install

# Run the application (runs on port 8080)
./mvnw spring-boot:run
```

The backend will be available at `http://localhost:8080`

### Frontend Setup

```bash
# Navigate to frontend
cd frontend

# Install dependencies
npm install

# Run development server (runs on port 5173)
npm run dev

# Or build for production
npm run build
```

The frontend will be available at `http://localhost:5173`

## 🔗 API Integration

The frontend Vite dev server is configured with a proxy:
- All requests to `/api/*` are forwarded to `http://localhost:8080/api/*`
- This is configured in `frontend/vite.config.js`

## 📝 Features

### User Management
- User registration with validation
- User login with JWT authentication
- User dashboard and profile management

### Fundraising
- Browse active fundraising campaigns
- Create and manage funds
- Track fundraising progress

### Donations
- Browse donation opportunities
- Make donations to campaigns
- View donation history

### Content
- Blog posts and articles
- News updates
- Category-based organization

## 🔐 Authentication

- JWT-based authentication
- Tokens stored in browser localStorage
- Automatic token refresh and expiration handling
- Protected routes that require authentication

## 📚 API Endpoints

### User Endpoints
- `POST /api/v1/User` - Register new user
- `POST /api/v1/User/login` - User login
- `GET /api/v1/User` - List all users (paginated)
- `GET /api/v1/User/{id}` - Get user by ID
- `PATCH /api/v1/User/{id}` - Update user
- `PATCH /api/v1/User/{id}/update-password` - Change password

### Fund Endpoints
- `POST /api/v1/Fund` - Create new fund
- `GET /api/v1/Fund` - List all funds
- `GET /api/v1/Fund/{id}` - Get fund details

### Donation Endpoints
- `POST /api/v1/Donation` - Create donation
- `GET /api/v1/Donation` - List donations

### Blog Endpoints
- `POST /api/v1/Blog` - Create blog post
- `GET /api/v1/Blog` - List all blogs
- `GET /api/v1/Blog/{id}` - Get blog details

## 🛠️ Development

### Running Both Services

**Terminal 1 - Backend:**
```bash
cd backend
./mvnw spring-boot:run
```

**Terminal 2 - Frontend:**
```bash
cd frontend
npm run dev
```

Then open `http://localhost:5173` in your browser.

### Building for Production

**Backend:**
```bash
cd backend
./mvnw clean package
# JAR file will be in backend/target/
```

**Frontend:**
```bash
cd frontend
npm run build
# Production files will be in frontend/dist/
```

## 📋 Tech Stack

**Backend:**
- Spring Boot 3.x
- Spring Security
- JWT Authentication
- Maven
- Java 17+

**Frontend:**
- React 18+
- Vite
- React Router
- Tailwind CSS
- Axios (via fetch)

## 📝 Notes

- The backend and frontend are now in separate folders for better organization
- Each has its own build configuration and dependencies
- The proxy configuration in `vite.config.js` handles API routing during development
- For production, the frontend build should be served separately or integrated into the backend

## 📄 Documentation

See `PROJECT_STRUCTURE.md` for detailed information about the project organization.

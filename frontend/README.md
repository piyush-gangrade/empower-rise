# Empower Rise Frontend

This frontend is built with **React** and **Vite** and connects to a Spring Boot backend.

## Requirements

- **Node.js 18+**
- **npm**
- Backend service running on `http://localhost:8080`

## Setup

```bash
cd frontend
npm install
npm run dev
```

The app will start at `http://localhost:5173`.

## Build

```bash
npm run build
```

Production files are generated to `frontend/dist/`.

## Development Notes

- API requests to `/api/*` are proxied to the backend by Vite.
- JWT tokens are stored in `localStorage` for authenticated users.
- Admin routes are protected by role-based checks in the frontend.

## Docker

The repository supports full Docker deployment from the project root:

```bash
docker compose up --build
```

This starts PostgreSQL, the Spring Boot backend, and the frontend service.

## Available Scripts

- `npm run dev` — start the developer server
- `npm run build` — build the production app
- `npm run preview` — serve the production build locally
- `npm run lint` — run ESLint

import { Routes, Route } from 'react-router-dom'
import './App.css'
import TopNav from './components/TopNav.jsx'
import HomePage from './pages/HomePage.jsx'
import LoginPage from './pages/LoginPage.jsx'
import RegisterPage from './pages/RegisterPage.jsx'
import AdminLoginPage from './pages/AdminLoginPage.jsx'
import DashboardPage from './pages/DashboardPage.jsx'
import UsersPage from './pages/UsersPage.jsx'
import AdminsPage from './pages/AdminsPage.jsx'
import BlogsPage from './pages/BlogsPage.jsx'
import FundsPage from './pages/FundsPage.jsx'
import DonationsPage from './pages/DonationsPage.jsx'
import CategoriesPage from './pages/CategoriesPage.jsx'
import CommentsPage from './pages/CommentsPage.jsx'
import NotFoundPage from './pages/NotFoundPage.jsx'

function App() {
  return (
    <div className="site-shell">
      <TopNav />
      <main className="page-content">
        <Routes>
          <Route path="/" element={<HomePage />} />
          <Route path="/login" element={<LoginPage />} />
          <Route path="/register" element={<RegisterPage />} />
          <Route path="/dashboard" element={<DashboardPage />} />
          <Route path="/admin-login" element={<AdminLoginPage />} />
          <Route path="/users" element={<UsersPage />} />
          <Route path="/admins" element={<AdminsPage />} />
          <Route path="/blogs" element={<BlogsPage />} />
          <Route path="/funds" element={<FundsPage />} />
          <Route path="/donations" element={<DonationsPage />} />
          <Route path="/categories" element={<CategoriesPage />} />
          <Route path="/comments" element={<CommentsPage />} />
          <Route path="*" element={<NotFoundPage />} />
        </Routes>
      </main>
    </div>
  )
}

export default App

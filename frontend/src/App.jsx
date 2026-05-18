import { Routes, Route } from 'react-router-dom'
import './App.css'
import TopNav from './components/TopNav.jsx'
import HomePage from './pages/HomePage.jsx'
import LoginPage from './pages/LoginPage.jsx'
import RegisterPage from './pages/RegisterPage.jsx'
import AdminLoginPage from './pages/AdminLoginPage.jsx'
import DashboardLayout from './pages/dashboard/DashboardLayout.jsx'
import DashboardProfile from './pages/dashboard/DashboardProfile.jsx'
import DashboardFundraisers from './pages/dashboard/DashboardFundraisers.jsx'
import DashboardDonations from './pages/dashboard/DashboardDonations.jsx'
import DashboardComments from './pages/dashboard/DashboardComments.jsx'
import DashboardSettings from './pages/dashboard/DashboardSettings.jsx'
import CreateFund from './pages/dashboard/CreateFund.jsx'
import FundDetails from './pages/FundDetails.jsx'
import DonatePage from './pages/DonatePage.jsx'
import DonationSuccess from './pages/DonationSuccess.jsx'
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
          <Route path="/dashboard/*" element={<DashboardLayout />}>
            <Route index element={<DashboardProfile />} />
            <Route path="profile" element={<DashboardProfile />} />
            <Route path="fundraisers" element={<DashboardFundraisers />} />
            <Route path="create-fund" element={<CreateFund />} />
            <Route path="donations" element={<DashboardDonations />} />
            <Route path="comments" element={<DashboardComments />} />
            <Route path="settings" element={<DashboardSettings />} />
          </Route>
          <Route path="/admin-login" element={<AdminLoginPage />} />
          <Route path="/users" element={<UsersPage />} />
          <Route path="/admins" element={<AdminsPage />} />
          <Route path="/blogs" element={<BlogsPage />} />
          <Route path="/funds" element={<FundsPage />} />
          <Route path="/funds/:id" element={<FundDetails />} />
          <Route path="/funds/:id/donate" element={<DonatePage />} />
          <Route path="/donation-success" element={<DonationSuccess />} />
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

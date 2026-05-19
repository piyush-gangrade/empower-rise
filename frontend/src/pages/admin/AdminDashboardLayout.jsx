import { NavLink, Outlet, useNavigate } from 'react-router-dom'

const linkClass = ({ isActive }) => (isActive ? 'nav-link nav-link--active' : 'nav-link')

export default function AdminDashboardLayout() {
  const navigate = useNavigate()

  const handleLogout = () => {
    localStorage.removeItem('adminToken')
    localStorage.removeItem('adminUser')
    localStorage.removeItem('adminRole')
    navigate('/admin-login')
  }

  return (
    <div className="dashboard-grid">
      <aside className="dashboard-sidebar">
        <div className="dashboard-brand">Admin Portal</div>
        <nav className="dashboard-nav">
          <NavLink to="users" className={linkClass}>
            User Management
          </NavLink>
          <NavLink to="campaigns" className={linkClass}>
            Campaign Management
          </NavLink>
          <NavLink to="donations" className={linkClass}>
            Donation Audit
          </NavLink>
          <NavLink to="blogs" className={linkClass}>
            Blog Management
          </NavLink>
          <NavLink to="categories" className={linkClass}>
            Category Management
          </NavLink>
          <button className="nav-link nav-link--button" onClick={handleLogout}>
            Logout
          </button>
        </nav>
      </aside>
      <section className="dashboard-main">
        <Outlet />
      </section>
    </div>
  )
}

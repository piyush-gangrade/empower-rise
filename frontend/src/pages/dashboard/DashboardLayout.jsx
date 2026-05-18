import { NavLink, Outlet, useNavigate } from 'react-router-dom'

const linkClass = ({ isActive }) => (isActive ? 'nav-link nav-link--active' : 'nav-link')

export default function DashboardLayout() {
  const navigate = useNavigate()

  const handleLogout = () => {
    localStorage.removeItem('authToken')
    localStorage.removeItem('authUser')
    navigate('/login')
  }

  return (
    <div className="dashboard-grid">
      <aside className="dashboard-sidebar">
        <div className="dashboard-brand">My account</div>
        <nav className="dashboard-nav">
          <NavLink to="profile" className={linkClass}>
            My Profile
          </NavLink>
          <NavLink to="fundraisers" className={linkClass}>
            My Fundraisers
          </NavLink>
          <NavLink to="donations" className={linkClass}>
            My Donations
          </NavLink>
          <NavLink to="comments" className={linkClass}>
            Comments
          </NavLink>
          <NavLink to="settings" className={linkClass}>
            Settings
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

import { useEffect, useState } from 'react'
import { NavLink, useLocation, useNavigate } from 'react-router-dom'

const linkClass = ({ isActive }) =>
  isActive ? 'nav-link nav-link--active' : 'nav-link'

export default function TopNav() {
  const [authenticated, setAuthenticated] = useState(false)
  const location = useLocation()
  const navigate = useNavigate()

  useEffect(() => {
    setAuthenticated(Boolean(localStorage.getItem('authToken')))
  }, [location.pathname])

  const handleLogout = () => {
    localStorage.removeItem('authToken')
    localStorage.removeItem('authUser')
    setAuthenticated(false)
    navigate('/login')
  }

  return (
    <header className="top-nav">
      <div className="top-nav__inner">
        <div className="top-nav__brand">Empower Rise</div>
        <nav className="top-nav__links">
          <NavLink to="/" className={linkClass} end>
            Home
          </NavLink>
          <NavLink to="/funds" className={linkClass}>
            Explore Funds
          </NavLink>
          <NavLink to="/blogs" className={linkClass}>
            Blogs
          </NavLink>
          {authenticated ? (
            <>
              <NavLink to="/dashboard" className={linkClass}>
                Dashboard
              </NavLink>
              <button type="button" className="nav-link nav-link--button" onClick={handleLogout}>
                Logout
              </button>
            </>
          ) : (
            <>
              <NavLink to="/login" className={linkClass}>
                Login
              </NavLink>
              <NavLink to="/register" className={linkClass}>
                Register
              </NavLink>
              <NavLink to="/admin-login" className={linkClass}>
                Admin Login
              </NavLink>
            </>
          )}
        </nav>
      </div>
    </header>
  )
}

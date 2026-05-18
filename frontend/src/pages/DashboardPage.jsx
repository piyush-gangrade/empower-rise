import { useEffect, useState } from 'react'
import { useNavigate } from 'react-router-dom'
import { Button } from '../components/ui/button.jsx'

export default function DashboardPage() {
  const [user, setUser] = useState(null)
  const navigate = useNavigate()

  useEffect(() => {
    const token = localStorage.getItem('authToken')
    const storedUser = localStorage.getItem('authUser')
    if (!token) {
      navigate('/login')
      return
    }

    if (storedUser) {
      try {
        setUser(JSON.parse(storedUser))
      } catch {
        setUser(null)
      }
    }
  }, [navigate])

  const handleLogout = () => {
    localStorage.removeItem('authToken')
    localStorage.removeItem('authUser')
    navigate('/login')
  }

  return (
    <div className="dashboard-page">
      <section className="dashboard-card">
        <h2>Welcome back{user?.username ? `, ${user.username}` : ''}!</h2>
        <p className="dashboard-user">
          {user ? (
            <>
              <span>
                <strong>Email:</strong> {user.email}
              </span>
              <span>
                <strong>Phone:</strong> {user.phoneNumber || 'Not provided'}
              </span>
            </>
          ) : (
            'Your account is authenticated. You can explore campaigns, donate, or update your profile.'
          )}
        </p>
        <div className="dashboard-actions">
          <Button onClick={() => navigate('/funds')}>Explore funds</Button>
          <Button variant="secondary" onClick={handleLogout}>
            Logout
          </Button>
        </div>
      </section>

      <section className="dashboard-card">
        <h2>Your quick actions</h2>
        <p>Use these actions to start a fundraiser or browse donation opportunities.</p>
        <div className="dashboard-actions">
          <Button onClick={() => navigate('/register')}>Create fundraiser</Button>
          <Button variant="secondary" onClick={() => navigate('/donations')}>
            View donations
          </Button>
        </div>
      </section>
    </div>
  )
}

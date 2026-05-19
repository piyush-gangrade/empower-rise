import { Navigate, useLocation } from 'react-router-dom'
import { isAuthenticated } from '../utils/auth.js'

export default function RequireAuth({ children }) {
  const location = useLocation()

  if (!isAuthenticated()) {
    return <Navigate to="/login" replace state={{ from: location.pathname, message: 'Please login to continue.' }} />
  }

  return children
}

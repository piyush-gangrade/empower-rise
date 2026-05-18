import { useEffect, useState } from 'react'
import { Button } from '../../components/ui/button.jsx'

export default function DashboardProfile() {
  const [user, setUser] = useState(null)
  const [loading, setLoading] = useState(false)
  const [error, setError] = useState('')
  const [success, setSuccess] = useState('')

  useEffect(() => {
    const stored = localStorage.getItem('authUser')
    const token = localStorage.getItem('authToken')
    if (!stored || !token) return
    try {
      const parsed = JSON.parse(stored)
      fetchUser(parsed.id, token)
    } catch (e) {
      console.error(e)
    }
  }, [])

  const fetchUser = async (id, token) => {
    setLoading(true)
    setError('')
    try {
      const res = await fetch(`/api/v1/User/${id}`, {
        headers: { Authorization: `Bearer ${token}` },
      })
      const json = await res.json()
      if (!res.ok) throw new Error(json?.message || 'Failed to fetch user')
      setUser(json.data || json)
    } catch (err) {
      setError(err.message)
    } finally {
      setLoading(false)
    }
  }

  const handleSave = async (e) => {
    e.preventDefault()
    setError('')
    setSuccess('')
    const token = localStorage.getItem('authToken')
    if (!user || !token) return setError('Not authenticated')

    try {
      const payload = {
        name: user.username,
        email: user.email,
        phoneNumber: user.phoneNumber,
        address: user.address,
        photoURL: user.photoURL,
      }

      const res = await fetch(`/api/v1/User/${user.id}`, {
        method: 'PATCH',
        headers: {
          'Content-Type': 'application/json',
          Authorization: `Bearer ${token}`,
        },
        body: JSON.stringify(payload),
      })

      const json = await res.json()
      if (!res.ok) throw new Error(json?.message || 'Update failed')
      setSuccess('Profile updated')
      localStorage.setItem('authUser', JSON.stringify(json.data || payload))
    } catch (err) {
      setError(err.message)
    }
  }

  const handlePasswordChange = async (e) => {
    e.preventDefault()
    setError('')
    setSuccess('')
    const token = localStorage.getItem('authToken')
    const oldPassword = e.target.oldPassword.value
    const newPassword = e.target.newPassword.value
    if (!user || !token) return setError('Not authenticated')
    try {
      const res = await fetch(`/api/v1/User/update-password/${user.id}`, {
        method: 'PATCH',
        headers: {
          'Content-Type': 'application/json',
          Authorization: `Bearer ${token}`,
        },
        body: JSON.stringify({ oldPassword, newPassword }),
      })
      const json = await res.json()
      if (!res.ok) throw new Error(json?.message || 'Password update failed')
      setSuccess('Password updated')
      e.target.reset()
    } catch (err) {
      setError(err.message)
    }
  }

  if (loading) return <div>Loading...</div>

  return (
    <div>
      <h2>My Profile</h2>
      {error && <div className="alert alert-error">{error}</div>}
      {success && <div className="alert alert-success">{success}</div>}

      <form className="space-y-4" onSubmit={handleSave}>
        <div>
          <label className="mb-2 block text-sm font-medium text-slate-200">Name</label>
          <input
            className="input"
            value={user?.username || ''}
            onChange={(e) => setUser({ ...user, username: e.target.value })}
            required
          />
        </div>
        <div>
          <label className="mb-2 block text-sm font-medium text-slate-200">Email</label>
          <input
            className="input"
            type="email"
            value={user?.email || ''}
            onChange={(e) => setUser({ ...user, email: e.target.value })}
            required
          />
        </div>
        <div>
          <label className="mb-2 block text-sm font-medium text-slate-200">Phone</label>
          <input
            className="input"
            value={user?.phoneNumber || ''}
            onChange={(e) => setUser({ ...user, phoneNumber: e.target.value })}
          />
        </div>
        <div>
          <label className="mb-2 block text-sm font-medium text-slate-200">Address</label>
          <input
            className="input"
            value={user?.address || ''}
            onChange={(e) => setUser({ ...user, address: e.target.value })}
          />
        </div>
        <div>
          <label className="mb-2 block text-sm font-medium text-slate-200">Profile image URL</label>
          <input
            className="input"
            value={user?.photoURL || ''}
            onChange={(e) => setUser({ ...user, photoURL: e.target.value })}
          />
        </div>
        <Button type="submit">Save profile</Button>
      </form>

      <hr style={{ margin: '28px 0' }} />

      <h3>Change password</h3>
      <form className="space-y-4" onSubmit={handlePasswordChange}>
        <div>
          <label className="mb-2 block text-sm font-medium text-slate-200">Current password</label>
          <input name="oldPassword" type="password" className="input" required />
        </div>
        <div>
          <label className="mb-2 block text-sm font-medium text-slate-200">New password</label>
          <input name="newPassword" type="password" className="input" required />
        </div>
        <Button type="submit">Change password</Button>
      </form>
    </div>
  )
}

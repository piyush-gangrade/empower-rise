import { useEffect, useMemo, useState } from 'react'
import { Button } from '../../components/ui/button.jsx'
import { Card } from '../../components/ui/card.jsx'

const getAuthHeaders = () => {
  const token = localStorage.getItem('adminToken')
  return token ? { Authorization: `Bearer ${token}` } : {}
}

export default function AdminUsersPage() {
  const [users, setUsers] = useState([])
  const [search, setSearch] = useState('')
  const [loading, setLoading] = useState(false)
  const [error, setError] = useState('')

  useEffect(() => {
    async function loadUsers() {
      try {
        setLoading(true)
        setError('')
        const res = await fetch('/api/v1/User?page=1&limit=100', {
          headers: getAuthHeaders(),
        })
        const result = await res.json()
        if (!res.ok) {
          throw new Error(result.message || 'Failed to load users')
        }
        setUsers(result.data?.list || result.data?.data || result.data || [])
      } catch (err) {
        setError(err.message)
      } finally {
        setLoading(false)
      }
    }

    loadUsers()
  }, [])

  const filteredUsers = useMemo(() => {
    if (!search) return users
    return users.filter((user) => {
      return [user.username, user.email, user.phoneNumber]
        .filter(Boolean)
        .some((field) => field.toLowerCase().includes(search.toLowerCase()))
    })
  }, [users, search])

  const toggleStatus = async (id) => {
    try {
      setError('')
      const res = await fetch(`/api/v1/User/update-status/${id}`, {
        method: 'PATCH',
        headers: getAuthHeaders(),
      })
      const result = await res.json()
      if (!res.ok) {
        throw new Error(result.message || 'Unable to update status')
      }
      setUsers((current) =>
        current.map((user) =>
          user.id === id ? { ...user, active: !user.active } : user,
        ),
      )
    } catch (err) {
      setError(err.message)
    }
  }

  const deleteUser = async (id) => {
    if (!window.confirm('Delete this user?')) return
    try {
      setError('')
      const res = await fetch(`/api/v1/User/${id}`, {
        method: 'DELETE',
        headers: getAuthHeaders(),
      })
      const result = await res.json()
      if (!res.ok) {
        throw new Error(result.message || 'Unable to delete user')
      }
      setUsers((current) => current.filter((user) => user.id !== id))
    } catch (err) {
      setError(err.message)
    }
  }

  return (
    <div className="space-y-6">
      <div className="dashboard-header">
        <div>
          <h1>User Management</h1>
          <p>View, search, disable, and remove users from the platform.</p>
        </div>
        <div className="dashboard-metrics">Total users: {users.length}</div>
      </div>

      <Card>
        <div className="admin-search-row">
          <input
            className="input"
            placeholder="Search users by name, email, phone"
            value={search}
            onChange={(event) => setSearch(event.target.value)}
          />
        </div>

        {error ? <p className="text-error">{error}</p> : null}
        {loading ? (
          <p className="text-slate-300">Loading users…</p>
        ) : (
          <div className="admin-table-wrap">
            <table className="admin-table">
              <thead>
                <tr>
                  <th>Name</th>
                  <th>Email</th>
                  <th>Phone</th>
                  <th>Status</th>
                  <th>Actions</th>
                </tr>
              </thead>
              <tbody>
                {filteredUsers.map((user) => (
                  <tr key={user.id}>
                    <td>{user.username || user.name || 'Unknown'}</td>
                    <td>{user.email || '-'}</td>
                    <td>{user.phoneNumber || '-'}</td>
                    <td>{user.active ? 'Active' : 'Disabled'}</td>
                    <td className="admin-actions-cell">
                      <Button size="sm" variant="secondary" onClick={() => toggleStatus(user.id)}>
                        {user.active ? 'Disable' : 'Enable'}
                      </Button>
                      <Button size="sm" variant="ghost" onClick={() => deleteUser(user.id)}>
                        Delete
                      </Button>
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
            {filteredUsers.length === 0 ? <p className="text-slate-300">No users found.</p> : null}
          </div>
        )}
      </Card>
    </div>
  )
}

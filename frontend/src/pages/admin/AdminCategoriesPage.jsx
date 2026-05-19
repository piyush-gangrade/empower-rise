import { useEffect, useMemo, useState } from 'react'
import { Button } from '../../components/ui/button.jsx'
import { Card } from '../../components/ui/card.jsx'
import { Input } from '../../components/ui/input.jsx'

const getAuthHeaders = () => {
  const token = localStorage.getItem('adminToken')
  return token ? { Authorization: `Bearer ${token}`, 'Content-Type': 'application/json' } : { 'Content-Type': 'application/json' }
}

export default function AdminCategoriesPage() {
  const [categories, setCategories] = useState([])
  const [search, setSearch] = useState('')
  const [name, setName] = useState('')
  const [photoURL, setPhotoURL] = useState('')
  const [editingId, setEditingId] = useState(null)
  const [loading, setLoading] = useState(false)
  const [error, setError] = useState('')

  useEffect(() => {
    async function loadCategories() {
      try {
        const res = await fetch('/api/category')
        const result = await res.json()
        setCategories(result.data || [])
      } catch (err) {
        setError(err.message)
      }
    }

    loadCategories()
  }, [])

  const filteredCategories = useMemo(() => {
    if (!search) return categories
    return categories.filter((category) =>
      [category.name, category.photoURL]
        .filter(Boolean)
        .some((value) => value.toLowerCase().includes(search.toLowerCase())),
    )
  }, [categories, search])

  const resetForm = () => {
    setName('')
    setPhotoURL('')
    setEditingId(null)
  }

  const handleSubmit = async (event) => {
    event.preventDefault()
    setError('')
    setLoading(true)

    try {
      const body = JSON.stringify({ name, photoURL })
      const url = editingId ? `/api/category/${editingId}` : '/api/category/'
      const method = editingId ? 'PATCH' : 'POST'
      const res = await fetch(url, {
        method,
        headers: getAuthHeaders(),
        body,
      })
      const result = await res.json()
      if (!res.ok) {
        throw new Error(result.message || 'Failed to save category')
      }
      const newCategory = result.data
      if (editingId) {
        setCategories((current) => current.map((item) => (item.id === editingId ? newCategory : item)))
      } else {
        setCategories((current) => [...current, newCategory])
      }
      resetForm()
    } catch (err) {
      setError(err.message)
    } finally {
      setLoading(false)
    }
  }

  const handleEdit = (category) => {
    setEditingId(category.id)
    setName(category.name)
    setPhotoURL(category.photoURL)
  }

  const handleDelete = async (id) => {
    if (!window.confirm('Delete this category?')) return
    try {
      setError('')
      const res = await fetch(`/api/category/${id}`, {
        method: 'DELETE',
        headers: getAuthHeaders(),
      })
      const result = await res.json()
      if (!res.ok) {
        throw new Error(result.message || 'Unable to delete category')
      }
      setCategories((current) => current.filter((item) => item.id !== id))
    } catch (err) {
      setError(err.message)
    }
  }

  return (
    <div className="space-y-6">
      <div className="dashboard-header">
        <div>
          <h1>Category Management</h1>
          <p>Add and maintain categories used by blogs, campaigns, and donations.</p>
        </div>
        <div className="dashboard-metrics">Total categories: {categories.length}</div>
      </div>

      <Card>
        <form className="space-y-4" onSubmit={handleSubmit}>
          <div className="form-grid">
            <div>
              <label className="mb-2 block text-sm font-medium text-slate-200" htmlFor="name">
                Category name
              </label>
              <Input id="name" value={name} onChange={(event) => setName(event.target.value)} required />
            </div>
            <div>
              <label className="mb-2 block text-sm font-medium text-slate-200" htmlFor="photoURL">
                Photo URL
              </label>
              <Input id="photoURL" value={photoURL} onChange={(event) => setPhotoURL(event.target.value)} required />
            </div>
          </div>
          {error ? <p className="text-error">{error}</p> : null}
          <div className="form-actions">
            <Button type="submit" disabled={loading}>
              {editingId ? 'Save category' : 'Add category'}
            </Button>
            {editingId ? (
              <Button type="button" variant="ghost" onClick={resetForm}>
                Cancel
              </Button>
            ) : null}
          </div>
        </form>

        <div className="admin-search-row">
          <input
            className="input"
            placeholder="Filter categories"
            value={search}
            onChange={(event) => setSearch(event.target.value)}
          />
        </div>

        <div className="admin-table-wrap">
          <table className="admin-table">
            <thead>
              <tr>
                <th>Name</th>
                <th>Photo</th>
                <th>Actions</th>
              </tr>
            </thead>
            <tbody>
              {filteredCategories.map((category) => (
                <tr key={category.id}>
                  <td>{category.name}</td>
                  <td>{category.photoURL ? <a href={category.photoURL} target="_blank" rel="noreferrer">Link</a> : '-'}</td>
                  <td className="admin-actions-cell">
                    <Button size="sm" variant="secondary" onClick={() => handleEdit(category)}>
                      Edit
                    </Button>
                    <Button size="sm" variant="ghost" onClick={() => handleDelete(category.id)}>
                      Delete
                    </Button>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
          {filteredCategories.length === 0 ? <p className="text-slate-300">No categories found.</p> : null}
        </div>
      </Card>
    </div>
  )
}

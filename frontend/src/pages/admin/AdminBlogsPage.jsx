import { useEffect, useMemo, useState } from 'react'
import { Link } from 'react-router-dom'
import { Button } from '../../components/ui/button.jsx'
import { Card } from '../../components/ui/card.jsx'

const getAuthHeaders = () => {
  const token = localStorage.getItem('adminToken')
  return token ? { Authorization: `Bearer ${token}` } : {}
}

export default function AdminBlogsPage() {
  const [blogs, setBlogs] = useState([])
  const [search, setSearch] = useState('')
  const [loading, setLoading] = useState(false)
  const [error, setError] = useState('')

  useEffect(() => {
    async function loadBlogs() {
      try {
        setLoading(true)
        setError('')
        const res = await fetch('/api/v1/blogs/all?page=1&limit=100', {
          headers: getAuthHeaders(),
        })
        const result = await res.json()
        if (!res.ok) {
          throw new Error(result.message || 'Failed to load blogs')
        }
        setBlogs(result.data?.data || result.data?.list || result.data || [])
      } catch (err) {
        setError(err.message)
      } finally {
        setLoading(false)
      }
    }

    loadBlogs()
  }, [])

  const filteredBlogs = useMemo(() => {
    if (!search) return blogs
    return blogs.filter((blog) => {
      return [blog.title, blog.description, blog.category?.name]
        .filter(Boolean)
        .some((field) => field.toLowerCase().includes(search.toLowerCase()))
    })
  }, [blogs, search])

  const deleteBlog = async (id) => {
    if (!window.confirm('Delete this blog post?')) return
    try {
      setError('')
      const res = await fetch(`/api/v1/blogs/${id}`, {
        method: 'DELETE',
        headers: getAuthHeaders(),
      })
      if (!res.ok) {
        const result = await res.json()
        throw new Error(result.message || 'Unable to delete blog')
      }
      setBlogs((current) => current.filter((blog) => blog.id !== id))
    } catch (err) {
      setError(err.message)
    }
  }

  return (
    <div className="space-y-6">
      <div className="dashboard-header">
        <div>
          <h1>Blog Management</h1>
          <p>Create, update, and delete trust-building stories and fundraising guides.</p>
        </div>
        <Button as={Link} to="create" size="sm">
          Publish blog
        </Button>
      </div>

      <Card>
        <div className="admin-search-row">
          <input
            className="input"
            placeholder="Search blog titles or categories"
            value={search}
            onChange={(event) => setSearch(event.target.value)}
          />
        </div>
        {error ? <p className="text-error">{error}</p> : null}
        {loading ? (
          <p className="text-slate-300">Loading blogs…</p>
        ) : (
          <div className="admin-table-wrap">
            <table className="admin-table">
              <thead>
                <tr>
                  <th>Title</th>
                  <th>Category</th>
                  <th>Supporters</th>
                  <th>Views</th>
                  <th>Actions</th>
                </tr>
              </thead>
              <tbody>
                {filteredBlogs.map((blog) => (
                  <tr key={blog.id}>
                    <td>{blog.title}</td>
                    <td>{blog.category?.name || '-'}</td>
                    <td>{blog.totalPeople ?? 0}</td>
                    <td>{blog.totalVisits ?? 0}</td>
                    <td className="admin-actions-cell">
                      <Button size="sm" variant="secondary" as={Link} to={`/admin/blogs/edit/${blog.id}`}>
                        Edit
                      </Button>
                      <Button size="sm" variant="ghost" onClick={() => deleteBlog(blog.id)}>
                        Delete
                      </Button>
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
            {filteredBlogs.length === 0 ? <p className="text-slate-300">No blog posts found.</p> : null}
          </div>
        )}
      </Card>
    </div>
  )
}

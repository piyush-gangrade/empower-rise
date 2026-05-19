import { useEffect, useMemo, useState } from 'react'
import { Link, useNavigate } from 'react-router-dom'
import { Button } from '../components/ui/button.jsx'
import { Card } from '../components/ui/card.jsx'

export default function BlogsPage() {
  const navigate = useNavigate()
  const [blogs, setBlogs] = useState([])
  const [categories, setCategories] = useState([])
  const [search, setSearch] = useState('')
  const [activeCategory, setActiveCategory] = useState('')
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState('')

  const isAdmin = Boolean(localStorage.getItem('adminToken') || localStorage.getItem('role') === 'admin')

  useEffect(() => {
    async function loadCategories() {
      try {
        const res = await fetch('/api/category')
        const data = await res.json()
        setCategories(data)
      } catch (err) {
        console.error(err)
      }
    }

    loadCategories()
  }, [])

  useEffect(() => {
    async function loadBlogs() {
      try {
        setLoading(true)
        setError('')
        const url = activeCategory
          ? `/api/v1/blogs/all-by-category/${activeCategory}?page=1&limit=12`
          : '/api/v1/blogs/all?page=1&limit=12'
        const res = await fetch(url)
        const result = await res.json()
        if (!res.ok) {
          throw new Error(result.message || 'Unable to load blogs')
        }
        setBlogs(result.data?.data || [])
      } catch (err) {
        setError(err.message)
      } finally {
        setLoading(false)
      }
    }

    loadBlogs()
  }, [activeCategory])

  const filteredBlogs = useMemo(() => {
    if (!search) return blogs
    return blogs.filter((blog) =>
      blog.title?.toLowerCase().includes(search.toLowerCase()) ||
      blog.description?.toLowerCase().includes(search.toLowerCase()) ||
      blog.category?.name?.toLowerCase().includes(search.toLowerCase()),
    )
  }, [blogs, search])

  const handleDelete = async (id) => {
    if (!window.confirm('Delete this blog article?')) {
      return
    }

    try {
      const res = await fetch(`/api/v1/blogs/${id}`, { method: 'DELETE' })
      if (!res.ok) {
        throw new Error('Unable to delete blog')
      }
      setBlogs((current) => current.filter((blog) => blog.id !== id))
    } catch (err) {
      setError(err.message)
    }
  }

  return (
    <div className="space-y-6">
      <div className="flex flex-col gap-4 rounded-3xl border border-slate-800 bg-slate-900/80 p-6 shadow-glow sm:flex-row sm:items-center sm:justify-between">
        <div>
          <h1 className="text-3xl font-semibold text-white">Blogs</h1>
          <p className="mt-2 text-sm text-slate-400">
            Browse stories, fundraising tips, and NGO updates that build trust and engagement.
          </p>
        </div>
        <Button as={Link} to="/blogs/create">
          Publish story
        </Button>
      </div>

      <Card>
        <div className="blog-toolbar">
          <div className="search-field">
            <label htmlFor="blog-search" className="sr-only">
              Search blogs
            </label>
            <input
              id="blog-search"
              type="search"
              placeholder="Search stories, tips, or categories"
              value={search}
              onChange={(event) => setSearch(event.target.value)}
              className="input"
            />
          </div>

          <div className="category-filter">
            <Button
              variant={activeCategory ? 'secondary' : 'ghost'}
              onClick={() => setActiveCategory('')}
            >
              All categories
            </Button>
            {categories.map((category) => (
              <Button
                key={category.id}
                variant={activeCategory === category.id.toString() ? 'default' : 'ghost'}
                onClick={() => setActiveCategory(category.id.toString())}
              >
                {category.name}
              </Button>
            ))}
          </div>
        </div>

        {error ? <p className="text-error">{error}</p> : null}
        {loading ? (
          <p className="text-slate-300">Loading blog stories…</p>
        ) : (
          <div className="blog-grid">
            {filteredBlogs.length > 0 ? (
              filteredBlogs.map((blog) => (
                <article key={blog.id} className="blog-card">
                  <div className="blog-card-copy">
                    <p className="blog-category">{blog.category?.name || 'Story'}</p>
                    <h2>{blog.title}</h2>
                    <p>{blog.description?.slice(0, 140)}...</p>
                    <div className="blog-card-meta">
                      <span>{new Date(blog.createdAt).toLocaleDateString()}</span>
                      <span>{blog.totalPeople ?? 0} supporters</span>
                    </div>
                  </div>
                  <div className="blog-card-actions">
                    <Button as={Link} to={`/blogs/${blog.id}`} size="sm">
                      Read article
                    </Button>
                    {isAdmin ? (
                      <div className="blog-admin-actions">
                        <Button as={Link} to={`/blogs/edit/${blog.id}`} variant="secondary" size="sm">
                          Edit
                        </Button>
                        <Button onClick={() => handleDelete(blog.id)} variant="ghost" size="sm">
                          Delete
                        </Button>
                      </div>
                    ) : null}
                  </div>
                </article>
              ))
            ) : (
              <p className="text-slate-300">No articles found. Try a different search or category.</p>
            )}
          </div>
        )}
      </Card>

      {!isAdmin ? (
        <Card description="Admin-only controls">
          <p className="text-slate-300">Create, update, and delete articles from an administrator account.</p>
        </Card>
      ) : null}
    </div>
  )
}

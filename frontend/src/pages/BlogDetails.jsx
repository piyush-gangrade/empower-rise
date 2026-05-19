import { useEffect, useState } from 'react'
import { Link, useParams } from 'react-router-dom'
import { Button } from '../components/ui/button.jsx'
import { Card } from '../components/ui/card.jsx'

export default function BlogDetails() {
  const { id } = useParams()
  const [blog, setBlog] = useState(null)
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState('')

  useEffect(() => {
    async function loadBlog() {
      try {
        const res = await fetch(`/api/v1/blogs/${id}`)
        const result = await res.json()
        if (!res.ok) {
          throw new Error(result.message || 'Unable to load blog')
        }
        setBlog(result.data)
      } catch (err) {
        setError(err.message)
      } finally {
        setLoading(false)
      }
    }

    loadBlog()
  }, [id])

  if (loading) {
    return <Card title="Blog article">Loading article...</Card>
  }

  if (error) {
    return (
      <Card title="Blog article">
        <p className="text-error">{error}</p>
        <Button as={Link} to="/blogs">Back to blogs</Button>
      </Card>
    )
  }

  if (!blog) {
    return (
      <Card title="Blog article">
        <p className="text-slate-300">Article not found.</p>
        <Button as={Link} to="/blogs">Back to blogs</Button>
      </Card>
    )
  }

  const publishedDate = blog.createdAt ? new Date(blog.createdAt).toLocaleDateString() : 'Unknown'
  const imageUrl = blog.images?.length ? blog.images[0] : null

  return (
    <article className="blog-detail-page">
      <div className="blog-hero">
        <div className="blog-hero-copy">
          <p className="blog-category">{blog.category?.name || 'Blog'}</p>
          <h1>{blog.title}</h1>
          <p className="text-slate-300 mt-4">{blog.description?.slice(0, 180)}...</p>
          <div className="blog-meta">
            <span>{publishedDate}</span>
            <span>{blog.totalPeople ?? 0} supporters</span>
            <span>{blog.totalDonation ?? 0} raised</span>
          </div>
          <div className="blog-detail-actions">
            <Button as={Link} to="/blogs">Back to stories</Button>
          </div>
        </div>
        {imageUrl ? <img src={imageUrl} alt={blog.title} className="blog-hero-image" /> : null}
      </div>

      <section className="article-body">
        <Card className="article-card" title="Why this story matters">
          <p>{blog.description}</p>
        </Card>

        <div className="article-stat-grid">
          <div className="stat-card">
            <h3>{blog.totalVisits ?? 0}</h3>
            <p>Views</p>
          </div>
          <div className="stat-card">
            <h3>{blog.totalPeople ?? 0}</h3>
            <p>Supporters</p>
          </div>
          <div className="stat-card">
            <h3>{blog.totalDonation ?? 0}</h3>
            <p>Total donations</p>
          </div>
        </div>
      </section>
    </article>
  )
}

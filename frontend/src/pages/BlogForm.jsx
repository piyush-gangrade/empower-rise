import { useEffect, useState } from 'react'
import { useNavigate, useParams } from 'react-router-dom'
import { Button } from '../components/ui/button.jsx'
import { Card } from '../components/ui/card.jsx'
import { Input } from '../components/ui/input.jsx'

export default function BlogForm() {
  const { id } = useParams()
  const editMode = Boolean(id)
  const navigate = useNavigate()
  const [name, setName] = useState('')
  const [title, setTitle] = useState('')
  const [description, setDescription] = useState('')
  const [categoryId, setCategoryId] = useState('')
  const [images, setImages] = useState([])
  const [existingImages, setExistingImages] = useState([])
  const [categories, setCategories] = useState([])
  const [loading, setLoading] = useState(false)
  const [error, setError] = useState('')
  const [success, setSuccess] = useState('')

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
    if (!editMode) return

    async function loadBlog() {
      try {
        setLoading(true)
        const res = await fetch(`/api/v1/blogs/${id}`)
        const result = await res.json()
        if (!res.ok) {
          throw new Error(result.message || 'Unable to load blog')
        }
        const blog = result.data
        setName(blog.name || '')
        setTitle(blog.title || '')
        setDescription(blog.description || '')
        setCategoryId(blog.category?.id || '')
        setExistingImages(blog.images || [])
      } catch (err) {
        setError(err.message)
      } finally {
        setLoading(false)
      }
    }

    loadBlog()
  }, [editMode, id])

  const handleImagesChange = (event) => {
    setImages(Array.from(event.target.files))
  }

  const handleSubmit = async (event) => {
    event.preventDefault()
    setError('')
    setSuccess('')
    setLoading(true)

    try {
      const formData = new FormData()
      formData.append('name', name)
      formData.append('title', title)
      formData.append('description', description)
      formData.append('categoryId', categoryId)

      if (images.length > 0) {
        images.forEach((file) => formData.append('images', file))
      }

      const url = editMode ? `/api/v1/blogs/update/${id}` : '/api/v1/blogs/create'
      const method = editMode ? 'PATCH' : 'POST'

      const res = await fetch(url, {
        method,
        body: formData,
      })

      const result = await res.json()
      if (!res.ok) {
        throw new Error(result.message || 'Unable to save blog')
      }

      setSuccess(editMode ? 'Blog updated successfully.' : 'Blog created successfully.')
      navigate('/blogs')
    } catch (err) {
      setError(err.message)
    } finally {
      setLoading(false)
    }
  }

  return (
    <Card
      title={editMode ? 'Edit Blog Post' : 'Create Blog Post'}
      description={editMode ? 'Update an existing trust story or fundraising guide.' : 'Publish a new success story, tip article, or NGO update.'}
    >
      <form className="space-y-6" onSubmit={handleSubmit}>
        <div>
          <label className="mb-2 block text-sm font-medium text-slate-200" htmlFor="name">
            Author / Story name
          </label>
          <Input id="name" value={name} onChange={(event) => setName(event.target.value)} required />
        </div>

        <div>
          <label className="mb-2 block text-sm font-medium text-slate-200" htmlFor="title">
            Article headline
          </label>
          <Input id="title" value={title} onChange={(event) => setTitle(event.target.value)} required />
        </div>

        <div>
          <label className="mb-2 block text-sm font-medium text-slate-200" htmlFor="description">
            Story details
          </label>
          <textarea
            id="description"
            value={description}
            onChange={(event) => setDescription(event.target.value)}
            rows="6"
            className="textarea"
            placeholder="Write the full article text here"
            required
          />
        </div>

        <div>
          <label className="mb-2 block text-sm font-medium text-slate-200" htmlFor="category">
            Category
          </label>
          <select
            id="category"
            value={categoryId}
            onChange={(event) => setCategoryId(event.target.value)}
            className="input"
            required
          >
            <option value="">Select a category</option>
            {categories.map((category) => (
              <option key={category.id} value={category.id}>
                {category.name}
              </option>
            ))}
          </select>
        </div>

        <div>
          <label className="mb-2 block text-sm font-medium text-slate-200" htmlFor="images">
            Cover images
          </label>
          <Input id="images" type="file" onChange={handleImagesChange} multiple />
          {existingImages.length > 0 ? (
            <div className="existing-images">
              <p className="text-slate-400">Current uploads</p>
              <div className="image-preview-row">
                {existingImages.map((image) => (
                  <img key={image} src={image} alt="Existing blog" className="image-preview" />
                ))}
              </div>
            </div>
          ) : null}
        </div>

        {error ? <p className="text-error">{error}</p> : null}
        {success ? <p className="text-success">{success}</p> : null}

        <div className="form-actions">
          <Button type="submit" disabled={loading}>
            {loading ? 'Saving…' : editMode ? 'Update article' : 'Publish article'}
          </Button>
        </div>
      </form>
    </Card>
  )
}
